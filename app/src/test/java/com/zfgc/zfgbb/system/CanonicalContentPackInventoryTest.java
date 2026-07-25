package com.zfgc.zfgbb.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.flywaydb.core.api.MigrationVersion;
import org.junit.jupiter.api.Test;

import com.zfgc.zfgbb.operations.archive.BackupArchiveValidator;
import com.zfgc.zfgbb.operations.archive.BackupLimits;
import com.zfgc.zfgbb.operations.archive.BackupManifest;

class CanonicalContentPackInventoryTest {

	private static final Path CONTENT_PACK = Path.of("src/main/resources/content-packs/zfgc");
	private static final List<Path> MIGRATION_LOCATIONS = List.of(
			Path.of("src/main/resources/db/setup"),
			Path.of("src/main/resources/db/migration"));

	@Test
	void theShippedInstallationArchiveIsInstallableAtTheCommittedSchemaVersion() throws Exception {
		Path archive = CONTENT_PACK.resolve("v1/backup.tar.gz");
		assertTrue(Files.isRegularFile(archive),
				() -> "the zfgc content pack must ship an installation archive at " + archive);

		BackupManifest shipped = new BackupArchiveValidator(BackupLimits.defaults())
				.validate(archive).manifest();

		assertTrue(shipped.installerCompatible(),
				"the shipped archive must classify as installer compatible");
		assertTrue(shipped.installerAnchorAdministratorId() > 0,
				"the shipped archive must name the administrator the installer reconciles onto");
		assertEquals(highestResolvedSchemaVersion(), shipped.flywayVersion(),
				"the shipped archive must be regenerated whenever a migration is added, or the "
						+ "restore drift guard will refuse every installation");
		assertTrue(shipped.entries().stream().anyMatch(entry -> "database".equals(entry.type())),
				"the shipped archive must carry a database dump");
		assertTrue(shipped.entries().stream().anyMatch(entry -> "content".equals(entry.type())),
				"the shipped archive must carry the preview content resources");
	}

	private static String highestResolvedSchemaVersion() throws Exception {
		MigrationVersion highest = null;
		for (Path location : MIGRATION_LOCATIONS) {
			try (var paths = Files.walk(location)) {
				for (Path migration : paths.filter(Files::isRegularFile).toList()) {
					MigrationVersion version = versionOf(migration.getFileName().toString());
					if (version != null && (highest == null || version.compareTo(highest) > 0))
						highest = version;
				}
			}
		}
		if (highest == null)
			throw new AssertionError("no versioned migration was found under " + MIGRATION_LOCATIONS);
		return highest.getVersion();
	}

	private static MigrationVersion versionOf(String fileName) {
		int separator = fileName.indexOf("__");
		if (!fileName.startsWith("V") || !fileName.endsWith(".sql") || separator < 2)
			return null;
		return MigrationVersion.fromVersion(fileName.substring(1, separator));
	}
}
