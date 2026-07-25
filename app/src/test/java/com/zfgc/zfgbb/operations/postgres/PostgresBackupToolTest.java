package com.zfgc.zfgbb.operations.postgres;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;
import org.flywaydb.core.api.MigrationState;
import org.flywaydb.core.api.MigrationVersion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.ObjectProvider;

import com.zfgc.zfgbb.config.BackupRestoreProperties;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;

class PostgresBackupToolTest {

	private static final String APPLIED = "20260728.1";
	private static final String HEAD = "20260729.1";

	@TempDir
	Path temporary;

	@Test
	void dumpExcludesOperationalAndMigrationBookkeepingData() {
		Path destination = temporary.resolve("database.dump");

		assertEquals(List.of(
				"pg_dump",
				"--format=custom",
				"--schema=zfgbb",
				"--exclude-table-data=zfgbb.backup_job",
				"--exclude-table-data=zfgbb.install_run",
				"--exclude-table-data=zfgbb.migration_conflict",
				"--exclude-table-data=zfgbb.migrator_attachment_ref_rewrites",
				"--exclude-table-data=zfgbb.migrator_id_map",
				"--exclude-table-data=zfgbb.quote_strip_audit",
				"--exclude-table-data=zfgbb.quote_strip_run",
				"--no-owner",
				"--no-acl",
				"--file=" + destination.toAbsolutePath().normalize(),
				"zfgbb"),
				PostgresBackupTool.dumpCommand("pg_dump", destination, "zfgbb"));
	}

	@Test
	void archiveBuiltAtTheLiveSchemaVersionIsAcceptedOnlyWhenTheDatabaseIsAtClasspathHead() {
		PostgresBackupTool migrated = tool(HEAD, HEAD);

		assertEquals(HEAD, migrated.expectedSchemaVersion());
		migrated.requireArchiveSchemaMatchesApplication(HEAD);

		ZfgcInvalidRequestException drifted = assertThrows(ZfgcInvalidRequestException.class,
				() -> migrated.requireArchiveSchemaMatchesApplication("19700101.1"));
		assertTrue(drifted.getMessage().contains("19700101.1"), drifted.getMessage());
		assertTrue(drifted.getMessage().contains(HEAD), drifted.getMessage());
		assertTrue(drifted.getMessage().contains("Backup archive was produced"),
				drifted.getMessage());
	}

	@Test
	void databaseBehindClasspathHeadFailsWithItsOwnMessageBeforeTheArchiveIsJudged() {
		PostgresBackupTool behind = tool(APPLIED, HEAD);

		IllegalStateException stale = assertThrows(IllegalStateException.class,
				() -> behind.requireArchiveSchemaMatchesApplication(APPLIED));

		assertTrue(stale.getMessage().contains("This database is at schema version " + APPLIED),
				stale.getMessage());
		assertTrue(stale.getMessage().contains(HEAD), stale.getMessage());
		assertFalse(stale.getMessage().contains("Backup archive"),
				"the database failure must not read as an archive rejection: " + stale.getMessage());
		assertEquals(stale.getMessage(),
				assertThrows(IllegalStateException.class,
						behind::requireDatabaseAtExpectedSchemaVersion).getMessage());
	}

	@Test
	void unmigratedDatabaseReportsThatNoMigrationIsApplied() {
		PostgresBackupTool empty = tool(null, HEAD);

		IllegalStateException unmigrated = assertThrows(IllegalStateException.class,
				() -> empty.requireArchiveSchemaMatchesApplication(HEAD));

		assertTrue(unmigrated.getMessage().contains("records no applied migration"),
				unmigrated.getMessage());
		assertTrue(unmigrated.getMessage().contains(HEAD), unmigrated.getMessage());
	}

	@Test
	void absentFlywayBeanStillConstructsAndOnlyFailsWhenVersionsAreResolved() {
		ObjectProvider<Flyway> absent = provider(null);

		PostgresBackupTool tool = new PostgresBackupTool(mock(DataSource.class), absent,
				"jdbc:postgresql://localhost:5432/zfgbb", "zfgbb", "secret",
				new BackupRestoreProperties());

		IllegalStateException unavailable = assertThrows(IllegalStateException.class,
				tool::expectedSchemaVersion);
		assertTrue(unavailable.getMessage().contains("spring.flyway.enabled"),
				unavailable.getMessage());
		assertThrows(IllegalStateException.class,
				() -> tool.requireArchiveSchemaMatchesApplication(HEAD));
	}

	@Test
	void aTableOfContentsTooLargeToReadWholeIsRefusedRatherThanValidatedAsAPrefix() throws Exception {
		Path listing = tocScript(4000);
		BackupRestoreProperties budgeted = new BackupRestoreProperties();
		budgeted.setPgRestore(listing.toString());
		budgeted.setEntries(4);

		ZfgcInvalidRequestException refused = assertThrows(ZfgcInvalidRequestException.class,
				() -> toolWith(budgeted).validateToc(temporary.resolve("database.dump")));

		assertTrue(refused.getMessage().contains("cannot be confined to the application schema"),
				"a listing the reader could not hold whole must be refused, not silently validated "
						+ "up to the cut: " + refused.getMessage());
	}

	@Test
	void aTableOfContentsInsideTheEntryBudgetIsReadWholeAndValidated() throws Exception {
		Path listing = tocScript(40);
		BackupRestoreProperties generous = new BackupRestoreProperties();
		generous.setPgRestore(listing.toString());

		assertEquals(40, toolWith(generous).validateToc(temporary.resolve("database.dump")),
				"the default entry budget must read a realistic listing whole");
	}

	private Path tocScript(int entries) throws Exception {
		Path script = temporary.resolve("fake-pg-restore-" + entries + ".sh");
		Files.writeString(script, """
				#!/bin/sh
				i=1
				while [ $i -le %d ]; do
				  echo "$i; 1259 $((16000 + i)) TABLE zfgbb probe_table_$i zfgcadmin"
				  i=$((i + 1))
				done
				""".formatted(entries));
		script.toFile().setExecutable(true);
		return script;
	}

	private static PostgresBackupTool toolWith(BackupRestoreProperties properties) {
		return new PostgresBackupTool(mock(DataSource.class), provider(mock(Flyway.class)),
				"jdbc:postgresql://localhost:5432/zfgbb", "zfgbb", "secret", properties);
	}

	private static PostgresBackupTool tool(String appliedVersion, String classpathHeadVersion) {
		MigrationInfoService migrations = mock(MigrationInfoService.class);
		MigrationInfo[] applied = appliedVersion == null
				? new MigrationInfo[0]
				: new MigrationInfo[] {migration(appliedVersion, MigrationState.SUCCESS)};
		MigrationInfo[] all = appliedVersion == null
				|| appliedVersion.equals(classpathHeadVersion)
						? new MigrationInfo[] {migration(classpathHeadVersion, MigrationState.SUCCESS)}
						: new MigrationInfo[] {applied[0],
								migration(classpathHeadVersion, MigrationState.PENDING)};
		when(migrations.applied()).thenReturn(applied);
		when(migrations.all()).thenReturn(all);
		Flyway flyway = mock(Flyway.class);
		when(flyway.info()).thenReturn(migrations);
		return new PostgresBackupTool(mock(DataSource.class), provider(flyway),
				"jdbc:postgresql://localhost:5432/zfgbb", "zfgbb", "secret",
				new BackupRestoreProperties());
	}

	private static MigrationInfo migration(String version, MigrationState state) {
		MigrationInfo migration = mock(MigrationInfo.class);
		when(migration.getVersion()).thenReturn(MigrationVersion.fromVersion(version));
		when(migration.getState()).thenReturn(state);
		return migration;
	}

	private static ObjectProvider<Flyway> provider(Flyway flyway) {
		@SuppressWarnings("unchecked")
		ObjectProvider<Flyway> provider = mock(ObjectProvider.class);
		when(provider.getIfAvailable()).thenReturn(flyway);
		return provider;
	}
}
