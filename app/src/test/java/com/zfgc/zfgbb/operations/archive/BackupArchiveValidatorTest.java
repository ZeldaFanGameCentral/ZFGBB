package com.zfgc.zfgbb.operations.archive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class BackupArchiveValidatorTest {
	@TempDir
	Path temp;

	@Test
	void acceptsExactDeclaredRegularEntries() throws Exception {
		byte[] dump = "PGDMP".getBytes(StandardCharsets.US_ASCII);
		byte[] content = "image".getBytes(StandardCharsets.UTF_8);
		Path archive = archive(List.of(
				item("manifest.json", manifest(dump, content)),
				item("database.dump", dump),
				item("content/images/a.png", content)));

		ValidatedBackup result = validator().validate(archive);

		assertEquals(1, result.manifest().formatVersion());
		assertEquals(3, result.entries().size() + 1);
	}

	@Test
	void rejectsDuplicateTarNames() throws Exception {
		byte[] dump = "PGDMP".getBytes(StandardCharsets.US_ASCII);
		byte[] content = "image".getBytes(StandardCharsets.UTF_8);
		Path archive = archive(List.of(
				item("manifest.json", manifest(dump, content)),
				item("database.dump", dump),
				item("database.dump", dump),
				item("content/images/a.png", content)));

		assertThrows(InvalidBackupException.class, () -> validator().validate(archive));
	}

	@Test
	void rejectsTraversalBeforeExtraction() throws Exception {
		Path archive = archive(List.of(item("../manifest.json", "{}".getBytes(StandardCharsets.UTF_8))));
		assertThrows(InvalidBackupException.class, () -> validator().validate(archive));
	}

	@Test
	void rejectsUnknownManifestFields() throws Exception {
		byte[] dump = "PGDMP".getBytes(StandardCharsets.US_ASCII);
		byte[] content = "image".getBytes(StandardCharsets.UTF_8);
		String original = new String(manifest(dump, content), StandardCharsets.UTF_8);
		byte[] changed = original.replace("\"formatVersion\":1",
				"\"formatVersion\":1,\"securityOverride\":true").getBytes(StandardCharsets.UTF_8);
		Path archive = archive(List.of(
				item("manifest.json", changed),
				item("database.dump", dump),
				item("content/images/a.png", content)));

		assertThrows(InvalidBackupException.class, () -> validator().validate(archive));
	}

	@Test
	void rejectsBackupsFromAnUnsupportedPostgresqlMajor() throws Exception {
		byte[] dump = "PGDMP".getBytes(StandardCharsets.US_ASCII);
		byte[] content = "image".getBytes(StandardCharsets.UTF_8);
		String original = new String(manifest(dump, content), StandardCharsets.UTF_8);
		byte[] changed = original.replace("\"postgresqlMajor\":18",
				"\"postgresqlMajor\":17").getBytes(StandardCharsets.UTF_8);
		Path archive = archive(List.of(
				item("manifest.json", changed),
				item("database.dump", dump),
				item("content/images/a.png", content)));

		assertThrows(InvalidBackupException.class, () -> validator().validate(archive));
	}

	@Test
	void rejectsDuplicateJsonKeys() throws Exception {
		byte[] dump = "PGDMP".getBytes(StandardCharsets.US_ASCII);
		byte[] content = "image".getBytes(StandardCharsets.UTF_8);
		String original = new String(manifest(dump, content), StandardCharsets.UTF_8);
		byte[] changed = original.replace("\"formatVersion\":1",
				"\"formatVersion\":1,\"formatVersion\":1").getBytes(StandardCharsets.UTF_8);
		Path archive = archive(List.of(
				item("manifest.json", changed),
				item("database.dump", dump),
				item("content/images/a.png", content)));

		assertThrows(InvalidBackupException.class, () -> validator().validate(archive));
	}

	@Test
	void rejectsLinksAndSpecialFiles() throws Exception {
		Path symbolicLink = archive(List.of(
				item("manifest.json", "{}".getBytes(StandardCharsets.UTF_8)),
				special("content/link", TarConstants.LF_SYMLINK)));
		Path fifo = archive(List.of(
				item("manifest.json", "{}".getBytes(StandardCharsets.UTF_8)),
				special("content/pipe", TarConstants.LF_FIFO)));

		assertThrows(InvalidBackupException.class,
				() -> validator().validate(symbolicLink));
		assertThrows(InvalidBackupException.class, () -> validator().validate(fifo));
	}

	@Test
	void rejectsPortablePathCollisionsAndAmbiguousUnicode() throws Exception {
		Path caseCollision = archive(List.of(
				item("manifest.json", "{}".getBytes(StandardCharsets.UTF_8)),
				item("MANIFEST.json", "{}".getBytes(StandardCharsets.UTF_8))));
		Path compatibilityName = archive(List.of(
				item("manifest.json", "{}".getBytes(StandardCharsets.UTF_8)),
				item("content/\uff21.png", new byte[] { 1 })));
		Path bidiName = archive(List.of(
				item("manifest.json", "{}".getBytes(StandardCharsets.UTF_8)),
				item("content/a\u202eb.png", new byte[] { 1 })));

		assertThrows(InvalidBackupException.class,
				() -> validator().validate(caseCollision));
		assertThrows(InvalidBackupException.class,
				() -> validator().validate(compatibilityName));
		assertThrows(InvalidBackupException.class, () -> validator().validate(bidiName));
	}

	@Test
	void rejectsChecksumFailuresAndUndeclaredEntries() throws Exception {
		byte[] dump = "PGDMP".getBytes(StandardCharsets.US_ASCII);
		byte[] changedDump = "BAD!!".getBytes(StandardCharsets.US_ASCII);
		byte[] content = "image".getBytes(StandardCharsets.UTF_8);
		byte[] declaration = manifest(dump, content);
		Path checksumFailure = archive(List.of(
				item("manifest.json", declaration),
				item("database.dump", changedDump),
				item("content/images/a.png", content)));
		Path undeclared = archive(List.of(
				item("manifest.json", declaration),
				item("database.dump", dump),
				item("content/images/a.png", content),
				item("content/images/extra.png", content)));

		assertThrows(InvalidBackupException.class,
				() -> validator().validate(checksumFailure));
		assertThrows(InvalidBackupException.class, () -> validator().validate(undeclared));
	}

	@Test
	void rejectsExpandedSizeAndEntryCountBombsBeforeManifestParsing() throws Exception {
		byte[] large = new byte[600_000];
		Path expanded = archive(List.of(
				item("manifest.json", "{}".getBytes(StandardCharsets.UTF_8)),
				item("database.dump", large),
				item("content/a", large)));
		ArrayList<Item> many = new ArrayList<>();
		many.add(item("manifest.json", "{}".getBytes(StandardCharsets.UTF_8)));
		for (int index = 0; index < 21; index++)
			many.add(item("content/" + index, new byte[] { 1 }));
		Path entries = archive(many);

		assertThrows(InvalidBackupException.class, () -> validator().validate(expanded));
		assertThrows(InvalidBackupException.class, () -> validator().validate(entries));
	}

	private BackupArchiveValidator validator() {
		return new BackupArchiveValidator(new BackupLimits(
				1024 * 1024, 1024 * 1024, 64 * 1024, 64 * 1024, 64 * 1024, 20, 256));
	}

	private Path archive(List<Item> items) throws IOException {
		Path path = temp.resolve("backup-" + System.nanoTime() + ".tar.gz");
		try (OutputStream file = Files.newOutputStream(path);
				var gzip = new GzipCompressorOutputStream(file);
				var tar = new TarArchiveOutputStream(gzip)) {
			tar.setLongFileMode(TarArchiveOutputStream.LONGFILE_ERROR);
			for (Item item : items) {
				TarArchiveEntry entry = item.type() == TarConstants.LF_NORMAL
						? new TarArchiveEntry(item.name())
						: new TarArchiveEntry(item.name(), item.type());
				entry.setSize(item.type() == TarConstants.LF_NORMAL
						? item.bytes().length : 0);
				entry.setMode(0600);
				entry.setModTime(0);
				tar.putArchiveEntry(entry);
				if (item.type() == TarConstants.LF_NORMAL)
					tar.write(item.bytes());
				tar.closeArchiveEntry();
			}
		}
		return path;
	}

	private static byte[] manifest(byte[] dump, byte[] content) {
		String json = """
				{"formatVersion":1,"application":"ZFGBB","applicationVersion":"test",
				"flywayVersion":"1","postgresqlMajor":18,"dumpToolVersion":"18",
				"installerCompatible":false,"installerAnchorAdministratorId":0,
				"createdAt":"2026-07-28T00:00:00Z",
				"entries":[
				{"type":"database","path":"database.dump","length":%d,"sha256":"%s"},
				{"type":"content","path":"content/images/a.png","length":%d,"sha256":"%s"}]}
				""".formatted(dump.length, sha(dump), content.length, sha(content));
		return json.getBytes(StandardCharsets.UTF_8);
	}

	private static String sha(byte[] bytes) {
		try {
			return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));
		} catch (Exception impossible) {
			throw new IllegalStateException(impossible);
		}
	}

	private static Item item(String name, byte[] bytes) {
		return new Item(name, bytes, TarConstants.LF_NORMAL);
	}

	private static Item special(String name, byte type) {
		return new Item(name, new byte[0], type);
	}

	private record Item(String name, byte[] bytes, byte type) {}
}
