package com.zfgc.zfgbb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class SmfFixtureSchemaDriftTest {

	@Test
	void committed_smf_schema_matches_zip_extracted() throws IOException {
		Path runtimeExtracted = SmfTestFixture.schemaSql();
		String runtime = Files.readString(runtimeExtracted, StandardCharsets.UTF_8);

		String committed;
		try (InputStream in = SmfFixtureSchemaDriftTest.class.getResourceAsStream(
				SmfTestFixture.FIXTURE_ROOT + "/smf-schema.sql")) {
			if (in == null) {
				throw new IOException("smf-schema.sql not found on classpath");
			}
			committed = new String(in.readAllBytes(), StandardCharsets.UTF_8);
		}

		assertEquals(committed, runtime,
				"smf-schema.sql is out of date. Regenerate it from the zip — see "
						+ "app/src/test/resources/smf-fixtures/2.0.15/SOURCE.md for the command.");
	}
}
