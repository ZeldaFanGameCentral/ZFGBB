package com.zfgc.zfgbb.operations.archive;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public final class BackupArchiveWriter {
	private static final long CANONICAL_MTIME_MILLIS = 0;

	public static boolean isOperationalArtifact(String topLevelName) {
		return ".zfgbb".equals(topLevelName) || topLevelName.startsWith(".incoming-")
				|| topLevelName.startsWith(".pre-restore-");
	}

	private final BackupLimits limits;
	private final ObjectMapper json;

	public BackupArchiveWriter(BackupLimits limits) {
		this.limits = limits;
		this.json = com.fasterxml.jackson.databind.json.JsonMapper.builder()
				.findAndAddModules()
				.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
				.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.build();
	}

	public ValidatedBackup write(Request request, Path destination)
			throws IOException, InvalidBackupException {
		requireRegularFile(request.databaseDump(), "database dump");
		requireDirectory(request.contentRoot(), "content root");
		if (Files.size(request.databaseDump()) > limits.dumpBytes())
			throw new InvalidBackupException("database dump size limit exceeded");

		List<SourceEntry> sources = collectSources(request.databaseDump(), request.contentRoot());
		List<BackupManifest.Entry> declarations = sources.stream()
				.map(SourceEntry::declaration)
				.toList();
		BackupManifest manifest = new BackupManifest(
				1,
				"ZFGBB",
				required(request.applicationVersion(), "application version"),
				required(request.flywayVersion(), "Flyway version"),
				request.postgresqlMajor(),
				required(request.dumpToolVersion(), "dump tool version"),
				request.installerCompatible(),
				request.installerAnchorAdministratorId() == null
						? 0 : request.installerAnchorAdministratorId(),
				request.createdAt() == null ? Instant.now() : request.createdAt(),
				declarations);
		byte[] manifestBytes = json.writeValueAsBytes(manifest);
		if (manifestBytes.length > limits.manifestBytes())
			throw new InvalidBackupException("manifest size limit exceeded");

		Path absolute = destination.toAbsolutePath().normalize();
		Path parent = absolute.getParent();
		if (parent == null)
			throw new InvalidBackupException("backup destination has no parent");
		Files.createDirectories(parent);
		Path temporary = Files.createTempFile(parent, "." + absolute.getFileName(), ".tmp");
		try {
			writeTar(temporary, manifestBytes, sources);
			if (Files.size(temporary) > limits.compressedBytes())
				throw new InvalidBackupException("compressed size limit exceeded");
			ValidatedBackup validated = new BackupArchiveValidator(limits)
					.validate(temporary);
			moveAtomically(temporary, absolute);
			return validated;
		} finally {
			Files.deleteIfExists(temporary);
		}
	}

	private List<SourceEntry> collectSources(Path dump, Path contentRoot)
			throws IOException, InvalidBackupException {
		List<SourceEntry> result = new ArrayList<>();
		result.add(source("database", "database.dump", dump));
		Path normalizedRoot = contentRoot.toAbsolutePath().normalize();
		Files.walkFileTree(normalizedRoot, new SimpleFileVisitor<>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
					throws IOException {
				if (!dir.equals(normalizedRoot) && Files.isSymbolicLink(dir))
					throw new IOException("content tree contains a symbolic link: " + dir);
				Path relative = normalizedRoot.relativize(dir);
				if (relative.getNameCount() > 0
						&& isOperationalArtifact(relative.getName(0).toString()))
					return FileVisitResult.SKIP_SUBTREE;
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
					throws IOException {
				if (!attrs.isRegularFile() || Files.isSymbolicLink(file))
					throw new IOException("content tree contains a link or special file: " + file);
				String relative = normalizedRoot.relativize(file).toString().replace('\\', '/');
				try {
					result.add(source("content", "content/" + relative, file));
				} catch (InvalidBackupException e) {
					throw new IOException(e.getMessage(), e);
				}
				return FileVisitResult.CONTINUE;
			}
		});
		result.sort(Comparator.comparing(item -> item.declaration().path()));
		if (result.size() > limits.entries())
			throw new InvalidBackupException("entry count limit exceeded");
		long expanded = 0;
		long content = 0;
		for (SourceEntry source : result) {
			long length = source.declaration().length();
			if (expanded > limits.expandedBytes() - length)
				throw new InvalidBackupException("expanded size limit exceeded");
			expanded += length;
			if ("content".equals(source.declaration().type())) {
				if (content > limits.contentBytes() - length)
					throw new InvalidBackupException("content size limit exceeded");
				content += length;
			}
		}
		return List.copyOf(result);
	}

	private SourceEntry source(String type, String archivePath, Path source)
			throws IOException, InvalidBackupException {
		byte[] pathBytes = archivePath.getBytes(StandardCharsets.UTF_8);
		if (pathBytes.length > limits.pathBytes())
			throw new InvalidBackupException("path size limit exceeded: " + archivePath);
		long size = Files.size(source);
		return new SourceEntry(source,
				new BackupManifest.Entry(type, archivePath, size, BackupArchiveValidator.hash(source)));
	}

	private void writeTar(Path target, byte[] manifest, List<SourceEntry> sources)
			throws IOException {
		try (OutputStream file = new BufferedOutputStream(Files.newOutputStream(target));
				var gzip = new GzipCompressorOutputStream(file);
				var tar = new TarArchiveOutputStream(gzip)) {
			tar.setLongFileMode(TarArchiveOutputStream.LONGFILE_ERROR);
			writeEntry(tar, "manifest.json", manifest);
			byte[] buffer = new byte[64 * 1024];
			for (SourceEntry source : sources) {
				TarArchiveEntry entry = canonicalEntry(source.declaration().path(),
						source.declaration().length());
				tar.putArchiveEntry(entry);
				try (InputStream input = new BufferedInputStream(Files.newInputStream(source.source()))) {
					for (int read; (read = input.read(buffer)) >= 0;)
						tar.write(buffer, 0, read);
				}
				tar.closeArchiveEntry();
			}
			tar.finish();
		}
	}

	private static void writeEntry(TarArchiveOutputStream tar, String name, byte[] bytes)
			throws IOException {
		TarArchiveEntry entry = canonicalEntry(name, bytes.length);
		tar.putArchiveEntry(entry);
		tar.write(bytes);
		tar.closeArchiveEntry();
	}

	private static TarArchiveEntry canonicalEntry(String name, long length) {
		TarArchiveEntry entry = new TarArchiveEntry(name);
		entry.setSize(length);
		entry.setMode(0600);
		entry.setModTime(CANONICAL_MTIME_MILLIS);
		entry.setUserId(0);
		entry.setGroupId(0);
		entry.setUserName("");
		entry.setGroupName("");
		return entry;
	}

	private static void requireRegularFile(Path path, String label) throws InvalidBackupException {
		if (path == null || !Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS))
			throw new InvalidBackupException(label + " is not a regular file");
	}

	private static void requireDirectory(Path path, String label) throws InvalidBackupException {
		if (path == null || !Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS))
			throw new InvalidBackupException(label + " is not a directory");
	}

	private static String required(String value, String label) throws InvalidBackupException {
		if (value == null || value.isBlank())
			throw new InvalidBackupException(label + " is required");
		return value;
	}

	public static void moveAtomically(Path source, Path destination) throws IOException {
		try {
			Files.move(source, destination, StandardCopyOption.ATOMIC_MOVE,
					StandardCopyOption.REPLACE_EXISTING);
		} catch (AtomicMoveNotSupportedException e) {
			Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	public record Request(
			Path databaseDump,
			Path contentRoot,
			String applicationVersion,
			String flywayVersion,
			int postgresqlMajor,
			String dumpToolVersion,
			boolean installerCompatible,
			Integer installerAnchorAdministratorId,
			Instant createdAt) {}

	private record SourceEntry(Path source, BackupManifest.Entry declaration) {}
}
