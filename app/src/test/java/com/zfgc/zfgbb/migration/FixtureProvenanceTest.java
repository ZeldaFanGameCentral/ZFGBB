package com.zfgc.zfgbb.migration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.flywaydb.core.api.MigrationVersion;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;

import com.zfgc.zfgbb.dbo.UserAwardDboExample;
import com.zfgc.zfgbb.mappers.ContentResourceDboMapper;
import com.zfgc.zfgbb.mappers.MigratorIdMapDboMapper;
import com.zfgc.zfgbb.mappers.ModerationLogDboMapper;
import com.zfgc.zfgbb.mappers.PersonalMessageDboMapper;
import com.zfgc.zfgbb.mappers.SystemConfigDboMapper;
import com.zfgc.zfgbb.mappers.UserAwardDboMapper;
import com.zfgc.zfgbb.mappers.UserWarningDboMapper;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.system.AdminBackupResponse;
import com.zfgc.zfgbb.operations.archive.BackupArchiveValidator;
import com.zfgc.zfgbb.operations.archive.BackupLimits;
import com.zfgc.zfgbb.operations.archive.BackupManifest;
import com.zfgc.zfgbb.operations.archive.ValidatedBackup;
import com.zfgc.zfgbb.services.backup.BackupRestoreService;
import com.zfgc.zfgbb.services.backup.OperationStorageService;
import com.zfgc.zfgbb.operations.postgres.PostgresBackupTool;
import com.zfgc.zfgbb.testsupport.FixtureSemanticInventory;

@Order(0)
class FixtureProvenanceTest extends MigrationE2E {

	static final String REGENERATE_SHIPPED_ARCHIVE_PROPERTY = "zfgbb.regenerate.preview.archive";
	static final String REGENERATE_APPROVED_INVENTORY_PROPERTY =
			"zfgbb.regenerate.fixture.inventory";
	static final String SHIPPED_ARCHIVE_DIRECTORY =
			"app/src/main/resources/sample-data";
	static final String SHIPPED_ARCHIVE_FILE_NAME = "backup.tar.gz";
	static final String APPROVED_INVENTORY_FILE =
			"app/src/test/resources/fixture-semantic-inventory.tsv";
	private static final List<String> MIGRATION_LOCATIONS = List.of(
			"app/src/main/resources/db/setup",
			"app/src/main/resources/db/migration");

	@Test
	void immutableLegacyMigrationAndReviewedOverlaysMatchTheApprovedInventory()
			throws Exception {
		if (Boolean.getBoolean(REGENERATE_APPROVED_INVENTORY_PROPERTY)) {
			Path approvedInventory = resolveFromProjectRoot(APPROVED_INVENTORY_FILE);
			var captured = FixtureSemanticInventory.capture(dataSource, contentTarget);
			FixtureSemanticInventory.approve(approvedInventory, captured);
			assertFalse(captured.isEmpty(),
					() -> "refusing to approve an empty fixture inventory at " + approvedInventory);
			assertEquals(captured, FixtureSemanticInventory.expected(),
					"the rewritten inventory must read back as the corpus it was captured from");
		} else
			assertFixtureMatchesApprovedInventory();

		assertEquals("MG-Zero", findDisplayNameByName("mgzero"));
		assertEquals(4, personalMessageDboMapper.countByExample(null));
		assertEquals(1, userWarningDboMapper.countByExample(null));
		assertEquals(2, moderationLogDboMapper.countByExample(null));
		assertEquals(314, migratorIdMapDboMapper.countByExample(null),
				"285 original legacy identities plus the 29 imported corpus wiki articles");
		UserAwardDboExample grantedByAdministrator = new UserAwardDboExample();
		grantedByAdministrator.createCriteria().andGrantedByUserIdEqualTo(1);
		assertEquals(2, userAwardDboMapper.countByExample(grantedByAdministrator));
		assertEquals(27, contentResourceDboMapper.countByExample(null));
		assertNotNull(systemConfigDboMapper.selectByPrimaryKey("recycle_board_id"));
	}

	@Test
	void theShippedArchiveIsInstallableAtTheCommittedSchemaVersion() throws Exception {
		assumeFalse(Boolean.getBoolean(REGENERATE_SHIPPED_ARCHIVE_PROPERTY),
				"the regeneration run rewrites the shipped archive, so it must not also be the "
						+ "guard that decides whether the shipped archive is current");

		Path shippedArchive = resolveFromProjectRoot(SHIPPED_ARCHIVE_DIRECTORY)
				.resolve(SHIPPED_ARCHIVE_FILE_NAME);
		assertTrue(Files.isRegularFile(shippedArchive),
				() -> "this deployment must ship a sample data archive at " + shippedArchive);

		BackupManifest shipped = new BackupArchiveValidator(BackupLimits.defaults())
				.validate(shippedArchive).manifest();

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
		for (String location : MIGRATION_LOCATIONS) {
			try (var paths = Files.walk(resolveFromProjectRoot(location))) {
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

	@Autowired
	private PersonalMessageDboMapper personalMessageDboMapper;

	@Autowired
	private UserWarningDboMapper userWarningDboMapper;

	@Autowired
	private ModerationLogDboMapper moderationLogDboMapper;

	@Autowired
	private MigratorIdMapDboMapper migratorIdMapDboMapper;

	@Autowired
	private UserAwardDboMapper userAwardDboMapper;

	@Autowired
	private ContentResourceDboMapper contentResourceDboMapper;

	@Autowired
	private SystemConfigDboMapper systemConfigDboMapper;

	private void assertFixtureMatchesApprovedInventory() throws Exception {
		var expected = FixtureSemanticInventory.expected();
		var actual = FixtureSemanticInventory.capture(dataSource, contentTarget);
		assertTrue(expected.equals(actual),
				() -> "the migrated fixture or one of its binary assets drifted: "
						+ FixtureSemanticInventory.describeDifference(expected, actual));
	}

	@Nested
	@EnabledIfSystemProperty(named = REGENERATE_SHIPPED_ARCHIVE_PROPERTY, matches = "true")
	class ShippedPreviewArchive {

		@Autowired
		private BackupRestoreService backupRestoreService;

		@Autowired
		private OperationStorageService operationStorage;

		@Autowired
		private BackupRestoreService installabilityClassifier;

		@Autowired
		private PostgresBackupTool postgres;

		@Test
		void regeneratesTheArchiveTheZfgcContentPackShips() throws Exception {
			assertFalse(Boolean.getBoolean(REGENERATE_APPROVED_INVENTORY_PROPERTY),
					"the approved inventory cannot be rewritten in the same run that cuts the "
							+ "shipped archive, or the archive would be approved against itself");
			assertFixtureMatchesApprovedInventory();

			BackupRestoreService.ArchiveInstallability classification =
					installabilityClassifier.classifyInstallability(contentTarget);
			assertTrue(classification.compatible(),
					() -> "the migrated preview corpus is not shippable as installation content: "
							+ classification.reason());
			Integer anchorAdministratorId = classification.anchorAdministratorId();
			assertNotNull(anchorAdministratorId);
			assertTrue(anchorAdministratorId > 0);
			String expectedSchemaVersion = postgres.expectedSchemaVersion();
			assertEquals(expectedSchemaVersion, postgres.metadata().schemaVersion(),
					"the preview corpus must sit at the schema version this build expects");

			User administrator = User.builder().userId(anchorAdministratorId).build();
			String backupId = backupRestoreService.createBackup(administrator).id();
			AdminBackupResponse produced = awaitTerminalBackup(backupId, Duration.ofMinutes(20));
			assertEquals("READY", produced.state(),
					() -> "the preview backup did not complete: " + produced.error());
			assertEquals(Boolean.TRUE, produced.installerCompatible());
			assertEquals(anchorAdministratorId, produced.installerAnchorAdministratorId());

			Path destination = resolveFromProjectRoot(SHIPPED_ARCHIVE_DIRECTORY)
					.resolve(SHIPPED_ARCHIVE_FILE_NAME);
			Files.copy(operationStorage.storedArchivePath(backupId), destination,
					StandardCopyOption.REPLACE_EXISTING);
			Files.setPosixFilePermissions(destination,
					PosixFilePermissions.fromString("rw-r--r--"));

			ValidatedBackup shipped = new BackupArchiveValidator(operationStorage.limits())
					.validate(destination);
			assertTrue(shipped.manifest().installerCompatible());
			assertEquals(anchorAdministratorId.intValue(),
					shipped.manifest().installerAnchorAdministratorId());
			assertEquals(expectedSchemaVersion, shipped.manifest().flywayVersion());
			assertEquals(Files.size(destination), shipped.compressedBytes());
			assertTrue(shipped.compressedBytes() > 0,
					() -> "the shipped preview archive at " + destination + " is empty");
		}

		private AdminBackupResponse awaitTerminalBackup(String backupId, Duration timeout)
				throws InterruptedException {
			Instant deadline = Instant.now().plus(timeout);
			AdminBackupResponse latest = backupRestoreService.backup(backupId);
			while (Instant.now().isBefore(deadline)) {
				if (!"CREATING".equals(latest.state()))
					return latest;
				Thread.sleep(500);
				latest = backupRestoreService.backup(backupId);
			}
			throw new AssertionError("the preview backup never left CREATING within " + timeout);
		}
	}
}
