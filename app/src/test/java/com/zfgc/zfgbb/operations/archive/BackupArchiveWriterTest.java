package com.zfgc.zfgbb.operations.archive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class BackupArchiveWriterTest {
	@TempDir
	Path temp;

	@Test
	void writesAValidatedCanonicalArchiveAndExcludesOperationalState() throws Exception {
		Path dump = temp.resolve("database.dump");
		Files.writeString(dump, "PGDMP-test", StandardCharsets.UTF_8);
		Path content = temp.resolve("content");
		Files.createDirectories(content.resolve("images"));
		Files.writeString(content.resolve("images/2"), "two", StandardCharsets.UTF_8);
		Files.writeString(content.resolve("images/1"), "one", StandardCharsets.UTF_8);
		Files.createDirectories(content.resolve(".zfgbb/backups"));
		Files.writeString(content.resolve(".zfgbb/backups/private"), "excluded",
				StandardCharsets.UTF_8);
		Path output = temp.resolve("site.tar.gz");

		ValidatedBackup result = new BackupArchiveWriter(BackupLimits.defaults()).write(
				new BackupArchiveWriter.Request(
						dump, content, "test", "12", 18, "18.4",
						false, null,
						Instant.parse("2026-07-28T00:00:00Z")),
				output);

		assertEquals(3, result.entries().size());
		assertFalse(result.entries().containsKey("content/.zfgbb/backups/private"));
	}
}
