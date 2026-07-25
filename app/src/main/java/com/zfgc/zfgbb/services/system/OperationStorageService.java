package com.zfgc.zfgbb.services.system;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.time.Instant;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.config.BackupRestoreProperties;
import com.zfgc.zfgbb.operations.archive.BackupArchiveWriter;
import com.zfgc.zfgbb.operations.archive.BackupLimits;

@Service
public class OperationStorageService {
	private static final String SAFETY_DUMPS = "pre-restore";
	private static final String SAFETY_DUMP_SUFFIX = ".dump";
	private static final Set<PosixFilePermission> DIRECTORY_MODE = EnumSet.of(
			PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE,
			PosixFilePermission.OWNER_EXECUTE);
	private static final Set<PosixFilePermission> FILE_MODE = EnumSet.of(
			PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE);

	private final ContentRoot content;
	private final Path contentRoot;
	private final Path workRoot;
	private final BackupLimits limits;

	public OperationStorageService(ContentRoot content,
			@Value("${zfgbb.operations.work-path}") String workPath,
			BackupRestoreProperties properties) {
		this.content = content;
		this.contentRoot = content.configuredContentRoot();
		this.workRoot = Path.of(workPath).toAbsolutePath().normalize();
		if (workRoot.startsWith(contentRoot) || contentRoot.startsWith(workRoot))
			throw new IllegalArgumentException(
					"Backup work path and content path must be separate.");
		this.limits = new BackupLimits(properties.getCompressedBytes(),
				properties.getExpandedBytes(), properties.getManifestBytes(),
				properties.getDumpBytes(), properties.getContentBytes(),
				properties.getEntries(), properties.getPathBytes());
	}

	Path workRoot() {
		return workRoot;
	}

	public Path jobDirectory(String id) throws IOException {
		validateId(id);
		createPrivateDirectory(workRoot);
		Path jobs = workRoot.resolve("jobs");
		createPrivateDirectory(jobs);
		Path job = jobs.resolve(id);
		createPrivateDirectory(job);
		return job;
	}

	public Path restoreStagingDirectory() throws IOException {
		createPrivateDirectory(workRoot);
		Path restores = workRoot.resolve("restores");
		createPrivateDirectory(restores);
		Path staging = restores.resolve(UUID.randomUUID().toString());
		createPrivateDirectory(staging);
		return staging;
	}

	public Path jobPayload(String id) throws IOException {
		validateId(id);
		return workRoot.resolve("jobs").resolve(id);
	}

	public Path archivePath(String id) throws IOException {
		return jobDirectory(id).resolve("backup.tar.gz");
	}

	public Path storedArchivePath(String id) throws IOException {
		return jobPayload(id).resolve("backup.tar.gz");
	}

	public List<Path> orphanJobDirectories(Set<String> knownJobIds, Instant updatedBefore)
			throws IOException {
		Path jobs = workRoot.resolve("jobs");
		if (!Files.exists(jobs, LinkOption.NOFOLLOW_LINKS))
			return List.of();
		if (!Files.isDirectory(jobs, LinkOption.NOFOLLOW_LINKS))
			throw new IOException("backup jobs path is not a directory");
		try (var paths = Files.list(jobs)) {
			return paths.filter(path -> Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS))
					.filter(path -> canonicalId(path.getFileName().toString()))
					.filter(path -> !knownJobIds.contains(path.getFileName().toString()))
					.filter(path -> lastModifiedBefore(path, updatedBefore))
					.toList();
		}
	}

	public List<Path> abandonedSafetyDumps(Instant writtenBefore) throws IOException {
		Path safety = workRoot.resolve(SAFETY_DUMPS);
		if (!Files.isDirectory(safety, LinkOption.NOFOLLOW_LINKS))
			return List.of();
		try (var paths = Files.list(safety)) {
			return paths.filter(path -> Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS))
					.filter(path -> canonicalSafetyDump(path.getFileName().toString()))
					.filter(path -> lastModifiedBefore(path, writtenBefore))
					.toList();
		}
	}

	public BackupLimits limits() {
		return limits;
	}

	public void requireFreeSpace(long operationBytes, long marginBytes) throws IOException {
		createPrivateDirectory(workRoot);
		requireFreeSpaceOn(workRoot, operationBytes, marginBytes);
	}

	public void requireFreeContentSpace(long operationBytes, long marginBytes) throws IOException {
		requireFreeSpaceOn(content.activeContentRoot(), operationBytes, marginBytes);
	}

	public Path safetyDumpPath() throws IOException {
		createPrivateDirectory(workRoot);
		Path safety = workRoot.resolve(SAFETY_DUMPS);
		createPrivateDirectory(safety);
		return safety.resolve(UUID.randomUUID() + SAFETY_DUMP_SUFFIX);
	}

	private static void requireFreeSpaceOn(Path location, long operationBytes, long marginBytes)
			throws IOException {
		if (operationBytes < 0 || marginBytes < 0)
			throw new IOException("storage size requirements are invalid");
		long required;
		try {
			required = Math.addExact(operationBytes, marginBytes);
		} catch (ArithmeticException overflow) {
			throw new IOException("storage size requirement overflow", overflow);
		}
		if (Files.getFileStore(location).getUsableSpace() < required)
			throw new IOException("insufficient free operational storage");
	}

	public long contentBytes(Path root) throws IOException {
		Path normalized = root.toAbsolutePath().normalize();
		long total = 0;
		try (var paths = Files.walk(normalized)) {
			for (Path path : paths.sorted(Comparator.naturalOrder()).toList()) {
				Path relative = normalized.relativize(path);
				if (relative.getNameCount() > 0
						&& BackupArchiveWriter.isOperationalArtifact(relative.getName(0).toString()))
					continue;
				if (Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS))
					total = Math.addExact(total, Files.size(path));
			}
		} catch (ArithmeticException overflow) {
			throw new IOException("content size overflow", overflow);
		}
		return total;
	}

	private static void validateId(String id) throws IOException {
		if (!canonicalId(id))
			throw new IOException("backup ID is invalid");
	}

	private static boolean canonicalSafetyDump(String name) {
		return name.endsWith(SAFETY_DUMP_SUFFIX) && canonicalId(
				name.substring(0, name.length() - SAFETY_DUMP_SUFFIX.length()));
	}

	public static boolean canonicalId(String id) {
		try {
			return UUID.fromString(id).toString().equals(id);
		} catch (IllegalArgumentException | NullPointerException invalid) {
			return false;
		}
	}

	private static boolean lastModifiedBefore(Path path, Instant cutoff) {
		try {
			return Files.getLastModifiedTime(path, LinkOption.NOFOLLOW_LINKS)
					.toInstant().isBefore(cutoff);
		} catch (IOException unreadable) {
			return false;
		}
	}

	private static void createPrivateDirectory(Path directory) throws IOException {
		if (Files.exists(directory, LinkOption.NOFOLLOW_LINKS)
				&& !Files.isDirectory(directory, LinkOption.NOFOLLOW_LINKS))
			throw new IOException("backup work path is not a directory: " + directory);
		Files.createDirectories(directory);
		setPrivateDirectory(directory);
	}

	public static void setPrivateFile(Path file) throws IOException {
		setMode(file, FILE_MODE);
	}

	public static void setPrivateDirectory(Path directory) throws IOException {
		setMode(directory, DIRECTORY_MODE);
	}

	private static void setMode(Path path, Set<PosixFilePermission> mode) throws IOException {
		try {
			Files.setPosixFilePermissions(path, mode);
		} catch (UnsupportedOperationException nonPosixFilesystemUsesItsOwnAcls) {
		}
	}
}
