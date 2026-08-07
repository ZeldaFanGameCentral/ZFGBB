package com.zfgc.zfgbb.operations.archive;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class BackupArchiveValidator {
	private static final String MANIFEST = "manifest.json";
	private static final String DATABASE = "database.dump";
	private static final String CONTENT_PREFIX = "content/";
	private static final Set<String> ENTRY_TYPES = Set.of("database", "content");

	private final BackupLimits limits;
	private final ObjectMapper json;

	public BackupArchiveValidator(BackupLimits limits) {
		this.limits = limits;
		this.json = new ObjectMapper()
				.findAndRegisterModules()
				.enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION)
				.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.enable(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES);
	}

	public ValidatedBackup validate(Path archive) throws InvalidBackupException {
		try {
			if (!Files.isRegularFile(archive, LinkOption.NOFOLLOW_LINKS))
				throw invalid("archive is not a regular file");
			long compressed = Files.size(archive);
			if (compressed <= 0 || compressed > limits.compressedBytes())
				throw invalid("compressed size limit exceeded");

			String archiveDigest = hash(archive);
			Map<String, SeenEntry> seen = new HashMap<>();
			Map<String, String> portableNames = new HashMap<>();
			long expanded = 0;
			try (InputStream file = Files.newInputStream(archive);
					var gzip = new GzipCompressorInputStream(new BufferedInputStream(file));
					var tar = new TarArchiveInputStream(gzip)) {
				TarArchiveEntry entry;
				while ((entry = tar.getNextEntry()) != null) {
					String name = safeName(entry.getName());
					if (!entry.isFile() || entry.isLink() || entry.isSymbolicLink()
							|| entry.isSparse())
						throw invalid("links, directories, and special entries are forbidden: " + name);
					if (seen.size() >= limits.entries())
						throw invalid("entry count limit exceeded");
					if (seen.containsKey(name))
						throw invalid("duplicate archive entry: " + name);
					String collision = portableName(name);
					String prior = portableNames.putIfAbsent(collision, name);
					if (prior != null)
						throw invalid("archive paths collide on a supported platform: "
								+ prior + " and " + name);
					long size = entry.getSize();
					long entryLimit = MANIFEST.equals(name) ? limits.manifestBytes()
							: DATABASE.equals(name) ? limits.dumpBytes() : limits.contentBytes();
					if (size < 0 || size > entryLimit || expanded > limits.expandedBytes() - size)
						throw invalid("expanded size limit exceeded at " + name);
					MessageDigest digest = sha256();
					byte[] bytes = MANIFEST.equals(name) ? new byte[Math.toIntExact(size)] : null;
					copyEntry(tar, size, digest, bytes);
					seen.put(name, new SeenEntry(size, hex(digest.digest()), bytes));
					expanded += size;
				}
			}

			SeenEntry manifestEntry = seen.get(MANIFEST);
			if (manifestEntry == null)
				throw invalid("manifest.json is missing");
			BackupManifest manifest = parseManifest(manifestEntry.bytes());
			Map<String, BackupManifest.Entry> declarations = validateManifest(manifest);
			if (!seen.keySet().equals(union(declarations.keySet(), MANIFEST)))
				throw invalid("archive entries do not exactly match the manifest");
			for (BackupManifest.Entry declaration : declarations.values()) {
				SeenEntry actual = seen.get(declaration.path());
				if (actual.length() != declaration.length()
						|| !actual.sha256().equals(declaration.sha256()))
					throw invalid("length or checksum mismatch: " + declaration.path());
			}
			return new ValidatedBackup(manifest, archiveDigest, compressed, expanded,
					Map.copyOf(declarations));
		} catch (InvalidBackupException e) {
			throw e;
		} catch (IOException | RuntimeException e) {
			throw new InvalidBackupException("invalid backup archive", e);
		}
	}

	private BackupManifest parseManifest(byte[] bytes) throws InvalidBackupException {
		try {
			return json.readValue(bytes, BackupManifest.class);
		} catch (IOException e) {
			throw new InvalidBackupException("invalid manifest.json", e);
		}
	}

	private Map<String, BackupManifest.Entry> validateManifest(BackupManifest manifest)
			throws InvalidBackupException {
		if (manifest.formatVersion() != 1 || !"ZFGBB".equals(manifest.application()))
			throw invalid("unsupported backup format or application");
		if (manifest.postgresqlMajor() != 18)
			throw invalid("unsupported PostgreSQL source major");
		if (blank(manifest.applicationVersion()) || blank(manifest.flywayVersion())
				|| blank(manifest.dumpToolVersion()) 
				|| manifest.createdAt() == null || manifest.entries() == null)
			throw invalid("required manifest metadata is missing");
		if (manifest.installerCompatible()
				!= (manifest.installerAnchorAdministratorId() > 0))
			throw invalid("installer compatibility metadata is inconsistent");
		Map<String, BackupManifest.Entry> declarations = new HashMap<>();
		long contentTotal = 0;
		for (BackupManifest.Entry entry : manifest.entries()) {
			if (entry == null || !ENTRY_TYPES.contains(entry.type()) || entry.length() < 0
					|| entry.sha256() == null || !entry.sha256().matches("[0-9a-f]{64}"))
				throw invalid("invalid manifest entry");
			String path = safeName(entry.path());
			if (!path.equals(entry.path()))
				throw invalid("manifest path is not canonical: " + entry.path());
			if ("database".equals(entry.type()) != DATABASE.equals(path))
				throw invalid("database entry must be database.dump");
			if ("content".equals(entry.type()) != path.startsWith(CONTENT_PREFIX))
				throw invalid("content entry must be below content/");
			if (declarations.put(path, entry) != null)
				throw invalid("duplicate manifest path: " + path);
			if ("content".equals(entry.type())) {
				if (contentTotal > limits.contentBytes() - entry.length())
					throw invalid("content size limit exceeded");
				contentTotal += entry.length();
			}
		}
		if (!declarations.containsKey(DATABASE))
			throw invalid("database.dump is not declared");
		return declarations;
	}

	private String safeName(String raw) throws InvalidBackupException {
		if (raw == null || raw.isBlank() || raw.indexOf('\0') >= 0 || raw.indexOf('\\') >= 0
				|| raw.getBytes(StandardCharsets.UTF_8).length > limits.pathBytes()
				|| !Normalizer.isNormalized(raw, Normalizer.Form.NFC)
				|| !Normalizer.normalize(raw, Normalizer.Form.NFKC).equals(raw)
				|| raw.codePoints().anyMatch(BackupArchiveValidator::ambiguousCodePoint))
			throw invalid("invalid or ambiguous archive path");
		if (raw.startsWith("/") || raw.endsWith("/") || raw.contains("//"))
			throw invalid("non-canonical archive path: " + raw);
		for (String part : raw.split("/", -1))
			if (part.isEmpty() || part.equals(".") || part.equals(".."))
				throw invalid("archive path traversal: " + raw);
		return raw;
	}

	private static String portableName(String name) {
		return Normalizer.normalize(name, Normalizer.Form.NFC)
				.toLowerCase(Locale.ROOT);
	}

	private static boolean ambiguousCodePoint(int codePoint) {
		int type = Character.getType(codePoint);
		return Character.isISOControl(codePoint)
				|| type == Character.FORMAT
				|| (codePoint & 0xffff) >= 0xfffe
				|| (codePoint >= 0xfdd0 && codePoint <= 0xfdef);
	}

	private static void copyEntry(InputStream input, long size, MessageDigest digest, byte[] capture)
			throws IOException, InvalidBackupException {
		byte[] buffer = new byte[8192];
		long remaining = size;
		int offset = 0;
		while (remaining > 0) {
			int read = input.read(buffer, 0, (int) Math.min(buffer.length, remaining));
			if (read < 0)
				throw invalid("truncated archive entry");
			digest.update(buffer, 0, read);
			if (capture != null) {
				System.arraycopy(buffer, 0, capture, offset, read);
				offset += read;
			}
			remaining -= read;
		}
	}

	private static Set<String> union(Set<String> paths, String extra) {
		Set<String> result = new HashSet<>(paths);
		result.add(extra);
		return result;
	}

	private static boolean blank(String value) {
		return value == null || value.isBlank();
	}

	public static MessageDigest sha256() {
		try {
			return MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException impossible) {
			throw new IllegalStateException(impossible);
		}
	}

	public static String hex(byte[] bytes) {
		return HexFormat.of().formatHex(bytes);
	}

	public static String hash(Path path) throws IOException {
		MessageDigest digest = sha256();
		try (InputStream input = new BufferedInputStream(Files.newInputStream(path))) {
			byte[] buffer = new byte[64 * 1024];
			for (int read; (read = input.read(buffer)) >= 0;)
				digest.update(buffer, 0, read);
		}
		return hex(digest.digest());
	}

	private static InvalidBackupException invalid(String message) {
		return new InvalidBackupException(message);
	}

	private record SeenEntry(long length, String sha256, byte[] bytes) {}
}
