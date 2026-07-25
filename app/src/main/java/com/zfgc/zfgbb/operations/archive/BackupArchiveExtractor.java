package com.zfgc.zfgbb.operations.archive;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

/**
 * Extracts only entries already approved by {@link BackupArchiveValidator}.
 *
 * The caller supplies a new, empty staging directory. No archive path is ever
 * resolved directly: database.dump has one fixed destination and content paths
 * are resolved relative to the staging content directory after confinement
 * checks.
 */
public final class BackupArchiveExtractor {

	private final BackupArchiveValidator validator;

	public BackupArchiveExtractor(BackupLimits limits) {
		this.validator = new BackupArchiveValidator(limits);
	}

	public ExtractedBackup extract(Path archive, Path stagingDirectory)
			throws IOException, InvalidBackupException {
		ValidatedBackup validated = validator.validate(archive);
		Path staging = stagingDirectory.toAbsolutePath().normalize();
		if (Files.exists(staging, LinkOption.NOFOLLOW_LINKS) && !isEmptyDirectory(staging))
			throw new InvalidBackupException("staging directory is not an empty directory");
		Files.createDirectories(staging);
		Path dump = staging.resolve("database.dump");
		Path content = staging.resolve("content");
		Files.createDirectory(content);

		Set<String> extracted = new HashSet<>();
		boolean complete = false;
		try (InputStream file = Files.newInputStream(archive);
				var gzip = new GzipCompressorInputStream(new BufferedInputStream(file));
				var tar = new TarArchiveInputStream(gzip)) {
			TarArchiveEntry entry;
			while ((entry = tar.getNextEntry()) != null) {
				String name = entry.getName();
				if ("manifest.json".equals(name))
					continue;
				BackupManifest.Entry declaration = validated.entries().get(name);
				if (declaration == null || !entry.isFile() || !extracted.add(name))
					throw new InvalidBackupException("archive changed after validation");
				Path destination;
				if ("database.dump".equals(name)) {
					destination = dump;
				} else {
					String relative = name.substring("content/".length());
					destination = content.resolve(relative).normalize();
					if (!destination.startsWith(content))
						throw new InvalidBackupException("content path escaped staging directory");
					Files.createDirectories(destination.getParent());
				}
				writeAtomically(tar, declaration, destination);
			}
			if (!extracted.equals(validated.entries().keySet()))
				throw new InvalidBackupException("archive changed after validation");
			complete = true;
			return new ExtractedBackup(validated, dump, content);
		} finally {
			if (!complete)
				OperationFiles.deleteTree(staging);
		}
	}

	private static boolean isEmptyDirectory(Path staging) throws IOException {
		if (!Files.isDirectory(staging, LinkOption.NOFOLLOW_LINKS))
			return false;
		try (var entries = Files.list(staging)) {
			return entries.findAny().isEmpty();
		}
	}

	private static void writeAtomically(InputStream input, BackupManifest.Entry declaration, Path destination)
			throws IOException, InvalidBackupException {
		Path temporary = Files.createTempFile(destination.getParent(),
				"." + destination.getFileName(), ".tmp");
		try {
			MessageDigest digest = BackupArchiveValidator.sha256();
			DigestInputStream digested = new DigestInputStream(input, digest);
			try (OutputStream output = new BufferedOutputStream(Files.newOutputStream(temporary))) {
				byte[] buffer = new byte[64 * 1024];
				long remaining = declaration.length();
				while (remaining > 0) {
					int read = digested.read(buffer, 0, (int) Math.min(buffer.length, remaining));
					if (read < 0)
						throw new InvalidBackupException("truncated archive entry");
					output.write(buffer, 0, read);
					remaining -= read;
				}
			}
			String written = BackupArchiveValidator.hex(digest.digest());
			if (!written.equals(declaration.sha256()))
				throw new InvalidBackupException("archive entry content changed after validation: "
						+ declaration.path());
			try {
				Files.move(temporary, destination, StandardCopyOption.ATOMIC_MOVE);
			} catch (AtomicMoveNotSupportedException e) {
				Files.move(temporary, destination);
			}
		} finally {
			Files.deleteIfExists(temporary);
		}
	}

	public record ExtractedBackup(ValidatedBackup validated, Path databaseDump, Path contentRoot) {}
}
