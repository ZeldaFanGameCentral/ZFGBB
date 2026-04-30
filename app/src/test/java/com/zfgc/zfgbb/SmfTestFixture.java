package com.zfgc.zfgbb;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.zfgc.zfgbb.migrator.SmfVersion;

public final class SmfTestFixture {

	public static final String FIXTURE_ROOT = "/smf-fixtures/2.0.15";

	public static final String DELTAS_RESOURCE = FIXTURE_ROOT + "/smf-zfgc-deltas.sql";
	public static final String DATA_RESOURCE = FIXTURE_ROOT + "/smf-data.sql";

	private static final String ZIP_RESOURCE = FIXTURE_ROOT + "/smf_2-0-15_install.zip";
	private static final String SQL_ENTRY = "install_2-0_mysql.sql";
	private static final String[] ATTACHMENT_FILES = { "1_abc123def456", "2_fedcba654321" };

	private static volatile Path cachedSchemaSql;

	private SmfTestFixture() {
	}

	public static Path schemaSql() throws IOException {
		Path cached = cachedSchemaSql;
		if (cached != null && Files.exists(cached)) {
			return cached;
		}
		synchronized (SmfTestFixture.class) {
			if (cachedSchemaSql != null && Files.exists(cachedSchemaSql)) {
				return cachedSchemaSql;
			}
			Path out = Files.createTempFile("smf-schema-", ".sql");
			out.toFile().deleteOnExit();
			String raw = readEntryFromZip();
			String prefixed = raw.replace("{$db_prefix}", SmfVersion.SUPPORTED_TABLE_PREFIX);
			String ddlOnly = stripSeedInserts(prefixed);
			Files.writeString(out, ddlOnly, StandardCharsets.UTF_8);
			cachedSchemaSql = out;
			return out;
		}
	}

	public static void writeAttachmentsTo(Path targetDir) throws IOException {
		Files.createDirectories(targetDir);
		for (String name : ATTACHMENT_FILES) {
			String resource = FIXTURE_ROOT + "/smf-attachments/" + name;
			try (InputStream in = SmfTestFixture.class.getResourceAsStream(resource)) {
				if (in == null) {
					throw new IOException("classpath resource not found: " + resource);
				}
				try (OutputStream out = Files.newOutputStream(targetDir.resolve(name))) {
					in.transferTo(out);
				}
			}
		}
	}

	private static String stripSeedInserts(String sql) {
		StringBuilder result = new StringBuilder(sql.length());
		boolean inInsert = false;
		for (String line : sql.split("\n", -1)) {
			if (!inInsert && line.startsWith("INSERT INTO ")) {
				inInsert = true;
				continue;
			}
			if (inInsert) {
				if (line.trim().endsWith(";")) {
					inInsert = false;
				}
				continue;
			}
			result.append(line).append('\n');
		}
		return result.toString();
	}

	private static String readEntryFromZip() throws IOException {
		try (InputStream zin = SmfTestFixture.class.getResourceAsStream(ZIP_RESOURCE)) {
			if (zin == null) {
				throw new IOException("classpath resource not found: " + ZIP_RESOURCE);
			}
			try (ZipInputStream zip = new ZipInputStream(zin)) {
				ZipEntry entry;
				while ((entry = zip.getNextEntry()) != null) {
					if (SQL_ENTRY.equals(entry.getName())) {
						return new String(zip.readAllBytes(), StandardCharsets.UTF_8);
					}
				}
			}
		}
		throw new IOException("entry not found in zip: " + SQL_ENTRY);
	}
}
