package com.zfgc.zfgbb.services.backup;

import com.zfgc.zfgbb.dao.cms.ContentResourceDao;
import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.services.system.MaintenanceCoordinator;
import com.zfgc.zfgbb.services.backup.BackupJobRepository;
import com.zfgc.zfgbb.services.backup.BackupRestoreService;
import com.zfgc.zfgbb.services.backup.OperationStorageService;
import com.zfgc.zfgbb.services.backup.RestoreService;
import com.zfgc.zfgbb.services.contentstore.ContentRoot;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HexFormat;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.zfgc.zfgbb.config.BackupRestoreProperties;
import com.zfgc.zfgbb.exception.ZfgcConflictException;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.system.BackupJob;
import com.zfgc.zfgbb.model.system.BackupJob.State;
import com.zfgc.zfgbb.operations.archive.BackupArchiveWriter;
import com.zfgc.zfgbb.operations.postgres.PostgresBackupTool;

class BackupRestoreServiceTest {

	@TempDir
	Path temporary;

	private final AtomicReference<AssertionError> offThreadFailure = new AtomicReference<>();

	private void recordOffThreadFailure(AssertionError failure) {
		offThreadFailure.compareAndSet(null, failure);
	}

	@AfterEach
	void noBackgroundThreadBrokeAFixtureInvariant() {
		AssertionError failure = offThreadFailure.get();
		if (failure != null)
			throw failure;
	}

	@Test
	void createAndDownloadLifecycleNeverWritesIntoContentRoot() throws Exception {
		Fixture fixture = fixture();
		User creator = mock(User.class);
		when(creator.getUserId()).thenReturn(7);

		var response = fixture.service().createBackup(creator);
		BackupJob ready = awaitState(fixture.current(), State.READY);
		assertEquals(response.id(), ready.id());
		assertFalse(Files.exists(fixture.content().resolve(".zfgbb")));
		assertTrue(Files.isRegularFile(fixture.storage().storedArchivePath(ready.id())));

		var claim = fixture.service().claimDownload(ready.id());
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		fixture.service().streamDownload(claim, output);

		assertTrue(output.size() > 0);
		assertEquals(State.CONSUMED, fixture.current().get().state());
		assertFalse(Files.exists(fixture.storage().jobPayload(ready.id())));
		assertFalse(Files.exists(fixture.content().resolve(".zfgbb")));
		try (var contentFiles = Files.list(fixture.content())) {
			assertEquals(List.of("asset.txt"), contentFiles
					.map(path -> path.getFileName().toString()).sorted().toList());
		}
	}

	@Test
	void interruptedDownloadReturnsPayloadToReady() throws Exception {
		Fixture fixture = fixture();
		BackupJob ready = fixture.ready("backup".getBytes(StandardCharsets.UTF_8),
				Instant.now().plusSeconds(60));
		var claim = fixture.service().claimDownload(ready.id());
		OutputStream disconnected = new OutputStream() {
			@Override
			public void write(int value) throws IOException {
				throw new IOException("client disconnected");
			}

			@Override
			public void write(byte[] value, int offset, int length) throws IOException {
				throw new IOException("client disconnected");
			}
		};

		try {
			fixture.service().streamDownload(claim, disconnected);
		} catch (IOException expected) {
			// The state transition in finally is the behavior under test.
		}

		assertEquals(State.READY, fixture.current().get().state());
		assertTrue(Files.isRegularFile(fixture.storage().storedArchivePath(ready.id())));
		assertFalse(Files.exists(fixture.content().resolve(".zfgbb")));
	}

	@Test
	void recoveryAndExpiryUseDatabaseStateWithoutReadingFilesystemMetadata()
			throws Exception {
		Fixture fixture = fixture();
		BackupJob creating = fixture.job(State.CREATING, Instant.now().plusSeconds(60));
		assertFalse(Files.exists(fixture.storage().workRoot()),
				"constructing storage must not create operational filesystem state");
		Files.createDirectories(fixture.storage().workRoot());
		Files.writeString(fixture.storage().workRoot().resolve("unrelated-corrupt-debris"),
				"not metadata");

		fixture.service().recoverInterruptedOperations();

		assertEquals(State.FAILED, fixture.current().get().state());
		assertFalse(Files.exists(fixture.content().resolve(".zfgbb")));

		BackupJob expired = fixture.ready("expired".getBytes(StandardCharsets.UTF_8),
				Instant.now().minusSeconds(1));
		fixture.service().cleanupExpiredArtifacts();

		assertEquals(State.EXPIRED, fixture.current().get().state());
		assertFalse(Files.exists(fixture.storage().jobPayload(expired.id())));
		assertFalse(Files.exists(fixture.content().resolve(".zfgbb")));
	}

	@Test
	void missingAndMismatchedReadyPayloadsBecomeFailed() throws Exception {
		Fixture missing = fixture();
		BackupJob missingJob = missing.readyMetadata(10, Instant.now().plusSeconds(60));

		missing.service().recoverInterruptedOperations();

		assertEquals(State.FAILED, missing.current().get().state());
		assertFalse(missing.service().backup(missingJob.id()).downloadReady());

		Fixture mismatched = fixture();
		BackupJob mismatchJob = mismatched.ready(
				"expected".getBytes(StandardCharsets.UTF_8),
				Instant.now().plusSeconds(60));
		Files.writeString(mismatched.storage().storedArchivePath(mismatchJob.id()),
				"different-size");

		assertThrows(ZfgcConflictException.class,
				() -> mismatched.service().claimDownload(mismatchJob.id()));
		assertEquals(State.FAILED, mismatched.current().get().state());
		assertFalse(Files.exists(mismatched.content().resolve(".zfgbb")));
	}

	@Test
	void aRunningBackupSurvivesCleanupAndRefusesADuplicateRequest() throws Exception {
		AdjustableClock clock = new AdjustableClock(Instant.parse("2030-01-01T12:00:00Z"));
		Fixture fixture = fixture(clock);
		CountDownLatch dumpStarted = new CountDownLatch(1);
		CountDownLatch dumpMayFinish = new CountDownLatch(1);
		AtomicBoolean dumpWasReleasedByTheLatch = new AtomicBoolean();
		doAnswer(call -> {
			Files.writeString(call.getArgument(0), "database-dump");
			dumpStarted.countDown();
			dumpWasReleasedByTheLatch.set(dumpMayFinish.await(30, TimeUnit.SECONDS));
			return null;
		}).when(fixture.postgres()).dump(any(Path.class), any());
		User creator = mock(User.class);
		when(creator.getUserId()).thenReturn(7);

		var running = fixture.service().createBackup(creator);
		assertTrue(dumpStarted.await(30, TimeUnit.SECONDS));
		clock.advance(fixture.properties().getCreationTimeout().plus(Duration.ofHours(1)));

		fixture.service().cleanupExpiredArtifacts();

		assertEquals(State.CREATING, fixture.current().get().state(),
				"the archive and validation passes run past the creation timeout, so a live "
						+ "worker must not be expired out from under itself");
		assertTrue(Files.isDirectory(fixture.storage().jobPayload(running.id())),
				"cleanup must never remove the working directory of a running backup");
		assertThrows(ZfgcConflictException.class, () -> fixture.service().createBackup(creator),
				"a second backup must be refused rather than queued behind the running one");

		dumpMayFinish.countDown();
		BackupJob completed = awaitState(fixture.current(), running.id(), State.READY);
		assertTrue(dumpWasReleasedByTheLatch.get(),
				"the running dump must have been held by the latch and released by it, not let "
						+ "through by its own timeout, or nothing about a live worker was proven");
		assertTrue(Files.isRegularFile(fixture.storage().storedArchivePath(completed.id())));

		var accepted = fixture.service().createBackup(creator);
		assertEquals(State.CREATING.name(), accepted.state(),
				"the duplicate guard must release once the running backup finishes");
		awaitState(fixture.current(), accepted.id(), State.READY);
	}

	@Test
	void creationTimeoutMustCoverTheDumpAndBothArchivePasses() {
		BackupRestoreProperties properties = new BackupRestoreProperties();
		properties.setCommandTimeout(Duration.ofMinutes(30));
		properties.setCreationTimeout(Duration.ofMinutes(31));

		assertThrows(IllegalArgumentException.class, () -> serviceWith(properties),
				"a creation budget that only covers pg_dump expires backups mid-archive");

		properties.setCreationTimeout(Duration.ofMinutes(90));
		assertDoesNotThrow(() -> {
			serviceWith(properties);
		});
	}

	private static BackupRestoreService serviceWith(BackupRestoreProperties properties) {
		return new BackupRestoreService(mock(OperationStorageService.class),
				mock(BackupJobRepository.class), properties, mock(PostgresBackupTool.class),
				mock(MaintenanceCoordinator.class), mock(ContentRoot.class),
				mock(UserDataProvider.class), mock(ContentResourceDao.class), Clock.systemUTC());
	}

	@Test
	void sameSizedBitRotIsCaughtBeforeTheArchiveIsServed() throws Exception {
		Fixture fixture = fixture();
		BackupJob ready = fixture.ready("backup-payload".getBytes(StandardCharsets.UTF_8),
				Instant.now().plusSeconds(60));
		Files.write(fixture.storage().storedArchivePath(ready.id()),
				"backup-corrupt".getBytes(StandardCharsets.UTF_8));

		assertEquals(State.READY.name(), fixture.service().backups().get(0).state(),
				"listing stays a metadata-only check; re-hashing every archive per list() call "
						+ "would be a performance regression");

		assertThrows(ZfgcConflictException.class,
				() -> fixture.service().claimDownload(ready.id()));
		assertEquals(State.FAILED, fixture.current().get().state(),
				"the recorded digest must be re-verified before any bytes reach an administrator");

		Fixture restarted = fixture();
		BackupJob rotted = restarted.ready("backup-payload".getBytes(StandardCharsets.UTF_8),
				Instant.now().plusSeconds(60));
		Files.write(restarted.storage().storedArchivePath(rotted.id()),
				"backup-corrupt".getBytes(StandardCharsets.UTF_8));

		restarted.service().recoverInterruptedOperations();

		assertEquals(State.FAILED, restarted.current().get().state(),
				"restart recovery decides a payload is intact, so it must hash it once");
	}

	@Test
	void anArchiveThatChangesBetweenClaimAndStreamIsNeverReportedAsConsumed() throws Exception {
		Fixture fixture = fixture();
		BackupJob ready = fixture.ready("backup-payload".getBytes(StandardCharsets.UTF_8),
				Instant.now().plusSeconds(60));
		var claim = fixture.service().claimDownload(ready.id());
		Files.write(fixture.storage().storedArchivePath(ready.id()),
				"backup-corrupt".getBytes(StandardCharsets.UTF_8));
		ByteArrayOutputStream delivered = new ByteArrayOutputStream();

		assertThrows(IOException.class, () -> fixture.service().streamDownload(claim, delivered));

		assertEquals(State.FAILED, fixture.current().get().state(),
				"a download whose bytes did not match the recorded digest is not a consumed backup");
		assertEquals(0, delivered.size(),
				"the digest is verified before the first byte is written, because Content-Length is "
						+ "already committed and sent bytes cannot be recalled");
	}

	@Test
	void aRestoredArchiveHasItsRepeatableSchemaObjectsBroughtBackUpToTheApplication()
			throws Exception {
		Fixture fixture = fixture();

		fixture.restore().restoreArchiveWithoutMaintenanceLease(
				archive(fixture, "restored.txt", "restored"));

		verify(fixture.flyway()).migrate();
	}

	@Test
	void aRestoreThatFailsNeverRepublishesSchemaObjectsOverTheRolledBackDatabase() throws Exception {
		Fixture failing = fixture();
		Path failingArchive = archive(failing, "restored.txt", "restored");
		doThrow(new IOException("pg_restore failed"))
				.when(failing.postgres()).restore(any(Path.class));

		assertThrows(IllegalStateException.class,
				() -> failing.restore().restoreArchiveWithoutMaintenanceLease(failingArchive));

		verify(failing.flyway(), never()).migrate();
	}

	@Test
	void aCreatingRowLeftBehindByADeadWorkerDoesNotBlockTheNextBackup() throws Exception {
		Fixture fixture = fixture();
		fixture.job(State.CREATING, Instant.now().plusSeconds(3600));
		User creator = mock(User.class);
		when(creator.getUserId()).thenReturn(7);

		assertDoesNotThrow(() -> fixture.service().createBackup(creator),
				"a CREATING row with no live worker in this JVM is abandoned state, not an "
						+ "in-flight backup, and must not lock backups out for the creation timeout");

		awaitState(fixture.current(), State.READY);
	}

	@Test
	void liveActiveOperationsIgnoreArtifactExpiry() throws Exception {
		Instant now = Instant.parse("2030-01-01T12:00:00Z");
		Fixture liveCreation = fixture(Clock.fixed(now, ZoneOffset.UTC));
		BackupJob creating = liveCreation.job(State.CREATING,
				now.minus(liveCreation.properties().getCreationTimeout()).plusSeconds(1),
				now.minusSeconds(1));
		Path creatingPayload = liveCreation.storage().jobDirectory(creating.id());
		Files.writeString(creatingPayload.resolve("database.dump.tmp"), "live");

		liveCreation.service().cleanupExpiredArtifacts();

		assertEquals(State.CREATING, liveCreation.current().get().state());
		assertTrue(Files.exists(creatingPayload));

		Fixture liveDownload = fixture(Clock.fixed(now, ZoneOffset.UTC));
		BackupJob ready = liveDownload.ready(
				"backup".getBytes(StandardCharsets.UTF_8), now.minusSeconds(1));
		liveDownload.current().set(copy(ready, State.DOWNLOADING,
				ready.revision() + 1, null,
				now.minus(liveDownload.properties().getDownloadClaimTimeout())
						.plusSeconds(1)));

		liveDownload.service().cleanupExpiredArtifacts();

		assertEquals(State.DOWNLOADING, liveDownload.current().get().state());
		assertTrue(Files.exists(liveDownload.storage().jobPayload(ready.id())));
	}

	@Test
	void staleActiveOperationsExpireAtTheirOwnTimeouts() throws Exception {
		Instant now = Instant.parse("2030-01-01T12:00:00Z");
		Fixture staleDownload = fixture(Clock.fixed(now, ZoneOffset.UTC));
		BackupJob ready = staleDownload.ready(
				"backup".getBytes(StandardCharsets.UTF_8), now.plusSeconds(60));
		staleDownload.current().set(copy(ready, State.DOWNLOADING,
				ready.revision() + 1, null,
				now.minus(staleDownload.properties().getDownloadClaimTimeout())
						.minusSeconds(1)));

		staleDownload.service().cleanupExpiredArtifacts();

		assertEquals(State.EXPIRED, staleDownload.current().get().state());
		assertFalse(Files.exists(staleDownload.storage().jobPayload(ready.id())));

		Fixture staleCreation = fixture(Clock.fixed(now, ZoneOffset.UTC));
		staleCreation.job(State.CREATING,
				now.minus(staleCreation.properties().getCreationTimeout())
						.minusSeconds(1),
				now.plusSeconds(60));
		staleCreation.service().cleanupExpiredArtifacts();
		assertEquals(State.EXPIRED, staleCreation.current().get().state());
	}

	@Test
	void expiryRaceReturnsWinningStateForStatus() throws Exception {
		Fixture fixture = fixture();
		BackupJob ready = fixture.ready("backup".getBytes(StandardCharsets.UTF_8),
				Instant.now().minusSeconds(1));
		when(fixture.jobs().transition(eq(ready.id()), eq(ready.revision()),
				eq(Set.of(State.READY)), eq(State.EXPIRED), isNull())).thenAnswer(call -> {
					fixture.current().set(copy(ready, State.DOWNLOADING,
							ready.revision() + 1, null));
					throw new ZfgcConflictException("simulated expiry race");
				});

		var response = fixture.service().backup(ready.id());

		assertEquals(State.DOWNLOADING.name(), response.state());
		assertFalse(response.downloadReady());
	}

	@Test
	void cleanupDeletesOnlyCanonicalOrphanJobDirectories() throws Exception {
		Fixture fixture = fixture();
		String orphanId = UUID.randomUUID().toString();
		Path orphan = fixture.storage().jobDirectory(orphanId);
		Files.writeString(orphan.resolve("payload"), "orphan");
		Files.setLastModifiedTime(orphan,
				FileTime.from(Instant.now().minus(Duration.ofHours(2))));
		Path nonJob = fixture.storage().workRoot().resolve("jobs/not-a-job-id");
		Files.createDirectory(nonJob);

		fixture.service().cleanupExpiredArtifacts();

		assertFalse(Files.exists(orphan));
		assertTrue(Files.isDirectory(nonJob));
		assertFalse(Files.exists(fixture.content().resolve(".zfgbb")));
	}

	@Test
	void orphanCleanupRechecksDatabaseAfterSnapshot() throws Exception {
		Instant now = Instant.parse("2030-01-01T12:00:00Z");
		Fixture fixture = fixture(Clock.fixed(now, ZoneOffset.UTC));
		String id = UUID.randomUUID().toString();
		Path candidate = fixture.storage().jobDirectory(id);
		Files.writeString(candidate.resolve("payload"), "still-owned");
		Files.setLastModifiedTime(candidate,
				FileTime.from(now.minus(fixture.properties().getOrphanGrace())
						.minusSeconds(1)));
		BackupJob appeared = new BackupJob(id, State.CREATING, 0,
				now, now, now.plusSeconds(60), 1,
				null, null, null, null, null);
		when(fixture.jobs().find(id)).thenAnswer(call -> {
			fixture.current().set(appeared);
			return Optional.of(appeared);
		});

		fixture.service().cleanupExpiredArtifacts();

		assertTrue(Files.isDirectory(candidate));
		assertEquals(id, fixture.current().get().id());
	}

	@Test
	void cleanupPrunesTerminalMetadataAfterRetention() throws Exception {
		Fixture fixture = fixture();
		fixture.properties().setMetadataRetention(Duration.ofDays(7));
		Instant old = Instant.now().minus(Duration.ofDays(8));
		BackupJob terminal = new BackupJob(UUID.randomUUID().toString(), State.FAILED, 3,
				old, old, old, 1, null, null, null, null, "failed");
		fixture.current().set(terminal);

		fixture.service().cleanupExpiredArtifacts();

		assertEquals(null, fixture.current().get());
	}

	@Test
	void restoreStagesPrivatelyAndReplacesContentBeforeTouchingTheDatabase() throws Exception {
		assumeTrue(FileSystems.getDefault().supportedFileAttributeViews().contains("posix"));
		Fixture fixture = fixture();
		Path archive = archive(fixture, "restored.txt", "restored");
		AtomicReference<String> dumpMode = new AtomicReference<>();
		AtomicReference<String> stagingMode = new AtomicReference<>();
		AtomicReference<String> restoresMode = new AtomicReference<>();
		AtomicReference<Path> staging = new AtomicReference<>();
		AtomicBoolean contentAlreadyOverlaid = new AtomicBoolean();
		doAnswer(call -> {
			Path dump = call.getArgument(0);
			staging.set(dump.getParent());
			dumpMode.set(mode(dump));
			stagingMode.set(mode(dump.getParent()));
			restoresMode.set(mode(dump.getParent().getParent()));
			contentAlreadyOverlaid.set(Files.exists(fixture.content().resolve("restored.txt")));
			return null;
		}).when(fixture.postgres()).restore(any(Path.class));

		fixture.restore().restoreArchiveWithoutMaintenanceLease(archive);

		assertEquals("rw-------", dumpMode.get(), "the staged dump must not be world readable");
		assertEquals("rwx------", stagingMode.get(), "the staging directory must be private");
		assertEquals("rwx------", restoresMode.get(), "the restore work root must be private");
		assertEquals("rwx------", mode(fixture.storage().workRoot()));
		assertTrue(contentAlreadyOverlaid.get(),
				"content must be in place before the database restore runs, so a failed restore "
						+ "rolls back the cheap half rather than the expensive one");
		assertEquals("restored", Files.readString(fixture.content().resolve("restored.txt")));
		assertFalse(Files.exists(fixture.content().resolve("asset.txt")),
				"a restore replaces the content tree; files absent from the archive must not "
						+ "survive to be referenced by wrong-generation content_resource rows");
		assertFalse(Files.exists(staging.get()), "staging must be removed after the restore");
		assertEquals(List.of(), siblingSwapDirectories(fixture.content()),
				"the pre-restore copy must be removed once the restore commits");
	}

	private static List<Path> siblingSwapDirectories(Path content) throws IOException {
		try (var siblings = Files.list(content.getParent())) {
			return siblings.filter(path -> path.getFileName().toString()
					.startsWith(content.getFileName() + ".")).sorted().toList();
		}
	}

	@Test
	void driftedArchiveIsRefusedBeforeContentAndAFailedRestoreRollsTheContentTreeBack()
			throws Exception {
		Fixture drifted = fixture();
		Path driftedArchive = archive(drifted, "drifted.txt", "drifted");
		doThrow(new ZfgcInvalidRequestException("schema drift"))
				.when(drifted.postgres()).requireArchiveSchemaMatchesApplication(anyString());

		assertThrows(ZfgcInvalidRequestException.class,
				() -> drifted.restore().restoreArchiveWithoutMaintenanceLease(driftedArchive));

		assertFalse(Files.exists(drifted.content().resolve("drifted.txt")),
				"a refused archive must never reach the content root");

		Fixture failing = fixture();
		Path failingArchive = archive(failing, "restored.txt", "restored");
		doThrow(new IOException("pg_restore failed"))
				.when(failing.postgres()).restore(any(Path.class));

		IllegalStateException restoreFailed = assertThrows(IllegalStateException.class,
				() -> failing.restore().restoreArchiveWithoutMaintenanceLease(failingArchive));

		assertEquals("asset", Files.readString(failing.content().resolve("asset.txt")),
				"a failed restore must put the pre-restore content tree back");
		assertFalse(Files.exists(failing.content().resolve("restored.txt")),
				"no bytes from the abandoned archive may survive in the content root");
		assertEquals(List.of(), siblingSwapDirectories(failing.content()),
				"the rollback must not leave a stray swap directory behind");
		assertTrue(restoreFailed.getMessage().contains("safety dump is at"),
				"the operator needs the pre-restore dump location: " + restoreFailed.getMessage());
	}

	@Test
	void aFailedRestoreKeepsItsSafetyDumpButASuccessfulOneRemovesIt() throws Exception {
		Fixture failing = fixture();
		Path failingArchive = archive(failing, "restored.txt", "restored");
		doThrow(new IOException("pg_restore failed"))
				.when(failing.postgres()).restore(any(Path.class));

		assertThrows(IllegalStateException.class,
				() -> failing.restore().restoreArchiveWithoutMaintenanceLease(failingArchive));

		assertEquals(1, safetyDumps(failing).size(),
				"the dump taken before pg_restore --clean is the only way back");

		Fixture succeeding = fixture();
		succeeding.restore().restoreArchiveWithoutMaintenanceLease(
				archive(succeeding, "restored.txt", "restored"));

		assertEquals(List.of(), safetyDumps(succeeding),
				"a committed restore must not leave a full database dump lying around");
	}

	@Test
	void aRestoreOnlyEverMovesTheChildrenOfTheLiveContentRoot() throws Exception {
		Fixture succeeding = fixture();
		Object beforeSuccess = fileKey(succeeding.content());
		assumeTrue(beforeSuccess != null, "the platform must expose a stable file key");
		AtomicReference<Object> duringSuccess = new AtomicReference<>();
		doAnswer(call -> {
			duringSuccess.set(fileKey(succeeding.content()));
			return null;
		}).when(succeeding.postgres()).restore(any(Path.class));

		succeeding.restore().restoreArchiveWithoutMaintenanceLease(
				archive(succeeding, "restored.txt", "restored"));

		assertEquals(beforeSuccess, duringSuccess.get(),
				"the live content root is a volume mount point in the shipped image, where "
						+ "renaming or replacing the directory itself fails with EBUSY, so a "
						+ "restore may only move its children");
		assertEquals(beforeSuccess, fileKey(succeeding.content()));
		assertEquals(List.of("restored.txt"), childNames(succeeding.content()),
				"no staging or pre-restore directory may survive inside the served content");

		Fixture failing = fixture();
		Path failingArchive = archive(failing, "restored.txt", "restored");
		Object beforeFailure = fileKey(failing.content());
		AtomicReference<Object> duringFailure = new AtomicReference<>();
		doAnswer(call -> {
			duringFailure.set(fileKey(failing.content()));
			throw new IOException("pg_restore failed");
		}).when(failing.postgres()).restore(any(Path.class));

		assertThrows(IllegalStateException.class,
				() -> failing.restore().restoreArchiveWithoutMaintenanceLease(failingArchive));

		assertEquals(beforeFailure, duringFailure.get(),
				"the swap that a failed restore rolls back must not have replaced the directory "
						+ "either");
		assertEquals(beforeFailure, fileKey(failing.content()));
		assertEquals(List.of("asset.txt"), childNames(failing.content()));
	}

	@Test
	void theSwapJournalOnlyRecordsSuccessAfterTheDatabaseRestoreReturns() throws Exception {
		Fixture fixture = fixture();
		AtomicReference<String> duringDatabaseRestore = new AtomicReference<>();
		doAnswer(call -> {
			duringDatabaseRestore.set(swapJournalPhase(fixture.content()));
			return null;
		}).when(fixture.postgres()).restore(any(Path.class));

		fixture.restore().restoreArchiveWithoutMaintenanceLease(
				archive(fixture, "restored.txt", "restored"));

		assertEquals("PUBLISHING", duringDatabaseRestore.get(),
				"a kill inside pg_restore must reconcile as a rollback, so the journal may only "
						+ "record success once the database restore has actually returned");
		assertEquals(List.of("restored.txt"), childNames(fixture.content()),
				"a committed restore leaves neither the pre-restore copy nor its journal behind");
	}

	@Test
	void startupReconciliationRollsBackASwapThatDiedBeforeTheDatabaseWasRestored() throws Exception {
		Fixture fixture = fixture();
		String swapId = UUID.randomUUID().toString();
		Path incoming = Files.createDirectory(
				fixture.content().resolve(".incoming-" + swapId));
		Files.writeString(incoming.resolve("not-yet-published.txt"), "archive");
		Path saved = Files.createDirectory(fixture.content().resolve(".pre-restore-" + swapId));
		Files.move(fixture.content().resolve("asset.txt"), saved.resolve("asset.txt"));
		Files.writeString(saved.resolve("uploads.txt"), "irreplaceable-upload");
		Files.writeString(fixture.content().resolve("restored.txt"), "archive");
		writeSwapJournal(fixture.content(), swapId, "PUBLISHING");

		fixture.restore().reconcileInterruptedContentSwaps(fixture.content());

		assertEquals(List.of("asset.txt", "uploads.txt"), childNames(fixture.content()),
				"the database still holds the pre-restore data because pg_restore never ran, so "
						+ "the half published archive content must be discarded and the originals "
						+ "moved back out of the pre-restore directory");
		assertEquals("asset", Files.readString(fixture.content().resolve("asset.txt")));
		assertEquals("irreplaceable-upload",
				Files.readString(fixture.content().resolve("uploads.txt")));
	}

	@Test
	void startupReconciliationFinishesASwapWhoseDatabaseWasAlreadyRestored() throws Exception {
		Fixture fixture = fixture();
		String swapId = UUID.randomUUID().toString();
		Path saved = Files.createDirectory(fixture.content().resolve(".pre-restore-" + swapId));
		Files.move(fixture.content().resolve("asset.txt"), saved.resolve("asset.txt"));
		Files.writeString(fixture.content().resolve("restored.txt"), "archive");
		writeSwapJournal(fixture.content(), swapId, "DATABASE_RESTORED");

		fixture.restore().reconcileInterruptedContentSwaps(fixture.content());

		assertEquals(List.of("restored.txt"), childNames(fixture.content()),
				"the database already carries the archive's data, so the pre-restore tree is "
						+ "debris; moving it back would leave the site serving files that no "
						+ "content_resource row describes");
		assertEquals("archive", Files.readString(fixture.content().resolve("restored.txt")));
	}

	@Test
	void reconciliationInterruptedMidRollbackKeepsTheOriginalsItAlreadyReturned() throws Exception {
		Fixture fixture = fixture();
		String swapId = UUID.randomUUID().toString();
		Path incoming = Files.createDirectory(fixture.content().resolve(".incoming-" + swapId));
		Files.writeString(incoming.resolve("half-copied.txt"), "archive");
		Path saved = Files.createDirectory(fixture.content().resolve(".pre-restore-" + swapId));
		Files.writeString(saved.resolve("second.txt"), "second-original");
		writeSwapJournal(fixture.content(), swapId, "DRAINING_ORIGINALS");

		fixture.restore().reconcileInterruptedContentSwaps(fixture.content());

		assertEquals(List.of("asset.txt", "second.txt"), childNames(fixture.content()),
				"a rollback that was itself interrupted has already moved originals back into the "
						+ "live root, so resuming it must never treat them as published archive "
						+ "content");
		assertEquals("asset", Files.readString(fixture.content().resolve("asset.txt")));
		assertEquals("second-original", Files.readString(fixture.content().resolve("second.txt")));

		fixture.restore().reconcileInterruptedContentSwaps(fixture.content());

		assertEquals(List.of("asset.txt", "second.txt"), childNames(fixture.content()),
				"reconciliation runs on every startup, so it has to be idempotent");
	}

	@Test
	void preRestoreContentWithNoJournalIsQuarantinedAndBlocksTheNextRestore() throws Exception {
		Fixture fixture = fixture();
		Path archive = archive(fixture, "restored.txt", "restored");
		Path stranded = Files.createDirectory(
				fixture.content().resolve(".pre-restore-" + UUID.randomUUID()));
		Files.writeString(stranded.resolve("only-copy.txt"), "irreplaceable");
		Path debris = Files.createDirectory(
				fixture.content().resolve(".incoming-" + UUID.randomUUID()));
		Files.writeString(debris.resolve("archive-copy.txt"), "archive");

		fixture.restore().reconcileInterruptedContentSwaps(fixture.content());

		assertEquals("irreplaceable", Files.readString(stranded.resolve("only-copy.txt")),
				"pre-restore content with no journal cannot be proven to be debris, and it may be "
						+ "the only copy of the site's content, so it is never swept");
		assertFalse(Files.exists(debris),
				"an unjournaled incoming directory only ever holds a second copy of archive "
						+ "content, so it is safe to reclaim");

		IllegalStateException refused = assertThrows(IllegalStateException.class,
				() -> fixture.restore().restoreArchiveWithoutMaintenanceLease(archive));

		assertTrue(refused.getMessage().contains(stranded.getFileName().toString()),
				"the operator needs to be told which directory to resolve: "
						+ refused.getMessage());
		assertEquals("irreplaceable", Files.readString(stranded.resolve("only-copy.txt")),
				"a restore that ran on top of the quarantine would sweep it away at commit");
		assertEquals("asset", Files.readString(fixture.content().resolve("asset.txt")));
		verify(fixture.postgres(), never()).restore(any(Path.class));
	}

	@Test
	void aCommitThatCannotRemoveThePreRestoreCopyIsFinishedByTheNextStartup() throws Exception {
		Fixture fixture = fixture();
		Path archive = archive(fixture, "restored.txt", "restored");
		RestoreService cleanupCannotRemoveIt = new RestoreService(fixture.storage(),
				fixture.properties(), fixture.postgres(), fixture.contentRoot(), fixture.flyway()) {
			@Override
			void removeContentTree(Path tree) throws IOException {
				if (tree.getFileName().toString().startsWith(".pre-restore-"))
					throw new IOException("the pre-restore copy could not be removed");
				super.removeContentTree(tree);
			}
		};

		cleanupCannotRemoveIt.restoreArchiveWithoutMaintenanceLease(archive);

		Path saved = preRestoreDirectory(fixture.content());
		assertTrue(Files.isDirectory(saved),
				"this test is about the commit that could not remove the pre-restore copy");
		assertEquals("restored", Files.readString(fixture.content().resolve("restored.txt")));
		assertEquals("DATABASE_RESTORED", swapJournalPhase(fixture.content()),
				"a commit that cannot delete the pre-restore copy must leave the journal behind "
						+ "saying the swap succeeded, or the next startup rolls a committed "
						+ "restore back");

		fixture.restore().reconcileInterruptedContentSwaps(fixture.content());

		assertEquals(List.of("restored.txt"), childNames(fixture.content()),
				"the leftover pre-restore tree is retried at startup instead of being logged once "
						+ "and stranded forever");
	}

	@Test
	void aCommittedRestoreIsNeverReportedAsFailedBecauseItsCleanupBlewUp() throws Exception {
		Fixture fixture = fixture();
		Path archive = archive(fixture, "restored.txt", "restored");
		RestoreService cleanupBlowsUp = new RestoreService(fixture.storage(), fixture.properties(),
				fixture.postgres(), fixture.contentRoot(), fixture.flyway()) {
			@Override
			void removeContentTree(Path tree) throws IOException {
				if (tree.getFileName().toString().startsWith(".pre-restore-"))
					throw new IOException("the pre-restore copy could not be walked");
				super.removeContentTree(tree);
			}
		};

		assertDoesNotThrow(() -> cleanupBlowsUp.restoreArchiveWithoutMaintenanceLease(archive),
				"the database has already been restored by the time the pre-restore copy is swept, "
						+ "so an I/O error there is a cleanup problem to log, not a failed restore "
						+ "to report");

		verify(fixture.flyway()).migrate();
		assertEquals("restored", Files.readString(fixture.content().resolve("restored.txt")));
		assertEquals("DATABASE_RESTORED", swapJournalPhase(fixture.content()),
				"the journal must survive the failed sweep so the next startup retries it");
	}

	@Test
	void aReconcilerLeavesASwapAnotherProcessIsRunningAlone() throws Exception {
		Fixture fixture = fixture();
		Path archive = archive(fixture, "restored.txt", "restored");
		String swapId = UUID.randomUUID().toString();
		Path saved = Files.createDirectory(fixture.content().resolve(".pre-restore-" + swapId));
		Files.move(fixture.content().resolve("asset.txt"), saved.resolve("asset.txt"));
		Files.writeString(fixture.content().resolve("restored.txt"), "archive");
		writeSwapJournal(fixture.content(), swapId, "PUBLISHING");
		Path ownerLock = fixture.content().resolve(".pre-restore-owner.lock");

		try (FileChannel liveSwap = FileChannel.open(ownerLock, StandardOpenOption.CREATE,
				StandardOpenOption.WRITE);
				FileLock heldWhileTheSwapRuns = liveSwap.lock()) {
			boolean reconciled =
					fixture.restore().reconcileInterruptedContentSwaps(fixture.content());

			assertEquals("asset", Files.readString(saved.resolve("asset.txt")),
					"rolling the originals back over a live swap would destroy the content the "
							+ "owning process is publishing");
			assertEquals("archive", Files.readString(fixture.content().resolve("restored.txt")),
					"the published children belong to the swap the owning process is running");
			assertEquals("PUBLISHING", swapJournalPhase(fixture.content()),
					"the journal belongs to the process that owns the swap");
			assertFalse(reconciled,
					"a booting replica cannot tell an abandoned swap from one another process is "
							+ "running right now, so it must report that it reconciled nothing");

			IllegalStateException refused = assertThrows(IllegalStateException.class,
					() -> fixture.restore().restoreArchiveWithoutMaintenanceLease(archive));

			assertTrue(refused.getMessage().contains("owner lock"),
					"a restore that cannot take the swap owner lock must say so rather than blame "
							+ "the live swap's directory on an operator: " + refused.getMessage());
			verify(fixture.postgres(), never()).restore(any(Path.class));
		}

		assertTrue(fixture.restore().reconcileInterruptedContentSwaps(fixture.content()),
				"once the owning process is gone the swap is abandoned and must be reconciled");
		assertEquals(List.of("asset.txt"), childNames(fixture.content()),
				"the guard only defers reconciliation; it must not strand the swap forever");
	}

	private static void writeSwapJournal(Path content, String swapId, String phase)
			throws IOException {
		Files.writeString(content.resolve(".pre-restore-" + swapId + ".journal"),
				"ZFGBB-RESTORE-SWAP 1\n" + phase + "\n");
	}

	private static String swapJournalPhase(Path content) throws IOException {
		try (var children = Files.list(content)) {
			Path journal = children
					.filter(child -> child.getFileName().toString().startsWith(".pre-restore-"))
					.filter(child -> child.getFileName().toString().endsWith(".journal"))
					.findFirst()
					.orElseThrow(() -> new AssertionError("no swap journal in " + content));
			return Files.readAllLines(journal).get(1);
		}
	}

	private static Path preRestoreDirectory(Path content) throws IOException {
		try (var children = Files.list(content)) {
			return children.filter(child -> Files.isDirectory(child))
					.filter(child -> child.getFileName().toString().startsWith(".pre-restore-"))
					.findFirst()
					.orElseThrow(() -> new AssertionError("no pre-restore directory in " + content));
		}
	}

	@Test
	void cleanupAgesOutAnAbandonedSafetyDumpWithoutTouchingAJustReportedOne() throws Exception {
		Fixture fixture = fixture();
		Path failingArchive = archive(fixture, "restored.txt", "restored");
		doThrow(new IOException("pg_restore failed"))
				.when(fixture.postgres()).restore(any(Path.class));
		assertThrows(IllegalStateException.class,
				() -> fixture.restore().restoreArchiveWithoutMaintenanceLease(failingArchive));
		Path dump = safetyDumps(fixture).get(0);

		fixture.service().cleanupExpiredArtifacts();

		assertTrue(Files.isRegularFile(dump),
				"the dump a failed restore just reported to the operator is the only way back");

		Path operatorCopy = dump.resolveSibling("keep-this-by-hand");
		Files.copy(dump, operatorCopy);
		Path insideARunningRestore = dump.resolveSibling(UUID.randomUUID() + ".dump");
		Files.copy(dump, insideARunningRestore);
		Instant sweptAt = Instant.now();
		FileTime abandoned = FileTime.from(sweptAt
				.minus(fixture.properties().getOrphanGrace())
				.minus(fixture.properties().getCommandTimeout())
				.minusSeconds(1));
		Files.setLastModifiedTime(dump, abandoned);
		Files.setLastModifiedTime(operatorCopy, abandoned);
		Files.setLastModifiedTime(insideARunningRestore, FileTime.from(sweptAt
				.minus(fixture.properties().getOrphanGrace())
				.minusSeconds(1)));

		fixture.service().cleanupExpiredArtifacts();

		assertFalse(Files.exists(dump),
				"an aged out safety dump is a stranded full database dump, not an operator artifact");
		assertTrue(Files.isRegularFile(operatorCopy),
				"only the dumps this application named may be swept");
		assertTrue(Files.isRegularFile(insideARunningRestore),
				"a dump written just past the orphan grace can still be the rollback artifact of a "
						+ "restore that is between its pg_dump and its pg_restore, so the sweep "
						+ "must hold it for a further command timeout");
	}

	private static Object fileKey(Path path) throws IOException {
		return Files.readAttributes(path, BasicFileAttributes.class).fileKey();
	}

	private static List<String> childNames(Path directory) throws IOException {
		try (var children = Files.list(directory)) {
			return children.map(child -> child.getFileName().toString()).sorted().toList();
		}
	}

	private static List<Path> safetyDumps(Fixture fixture) throws IOException {
		Path safety = fixture.storage().workRoot().resolve("pre-restore");
		if (!Files.isDirectory(safety))
			return List.of();
		try (var dumps = Files.list(safety)) {
			return dumps.sorted().toList();
		}
	}

	private Path archive(Fixture fixture, String contentName, String contentBody)
			throws Exception {
		Path workspace = Files.createDirectory(temporary.resolve("archive-" + UUID.randomUUID()));
		Path dump = workspace.resolve("database.dump");
		Files.writeString(dump, "database-dump");
		Path content = Files.createDirectory(workspace.resolve("content"));
		Files.writeString(content.resolve(contentName), contentBody);
		Path archive = workspace.resolve("backup.tar.gz");
		new BackupArchiveWriter(fixture.storage().limits()).write(
				new BackupArchiveWriter.Request(dump, content, "test", "20260729.1", 18,
						"pg_dump (PostgreSQL) 18.0", false, null,
						Instant.now()),
				archive);
		return archive;
	}

	private static String mode(Path path) throws IOException {
		return PosixFilePermissions.toString(Files.getPosixFilePermissions(path));
	}

	private Fixture fixture() throws Exception {
		return fixture(Clock.systemUTC());
	}

	private Fixture fixture(Clock clock) throws Exception {
		Path content = Files.createDirectory(temporary.resolve("content-" + UUID.randomUUID()));
		Files.writeString(content.resolve("asset.txt"), "asset");
		Path work = temporary.resolve("work-" + UUID.randomUUID());
		BackupRestoreProperties properties = new BackupRestoreProperties();
		OperationStorageService storage = new OperationStorageService(
				new ContentRoot(content.toString()), work.toString(), properties);
		BackupJobRepository jobs = mock(BackupJobRepository.class);
		AtomicReference<BackupJob> current = new AtomicReference<>();
		when(jobs.create(anyInt(), any(Instant.class), any(Instant.class))).thenAnswer(call -> {
			Instant created = call.getArgument(1);
			BackupJob job = new BackupJob(UUID.randomUUID().toString(), State.CREATING, 0,
					created, created, call.getArgument(2), call.getArgument(0),
					null, null, null, null, null);
			current.set(job);
			return job;
		});
		when(jobs.require(anyString())).thenAnswer(call -> current.get());
		when(jobs.find(anyString())).thenAnswer(call -> {
			BackupJob found = current.get();
			return found != null && found.id().equals(call.getArgument(0))
					? Optional.of(found) : Optional.empty();
		});
		when(jobs.list()).thenAnswer(call ->
				current.get() == null ? List.of() : List.of(current.get()));
		when(jobs.transition(anyString(), anyLong(), any(), any(State.class),
				nullable(String.class)))
				.thenAnswer(call -> {
					BackupJob before = current.get();
					@SuppressWarnings("unchecked")
					Set<State> expected = call.getArgument(2);
					long expectedRevision = call.<Long>getArgument(1);
					if (before == null || !expected.contains(before.state())
							|| before.revision() != expectedRevision) {
						recordOffThreadFailure(new AssertionError("a transition to "
								+ call.getArgument(3) + " compared and set against " + expected
								+ " at revision " + expectedRevision + ", but the job was "
								+ (before == null ? "absent" : before.state() + " at revision "
										+ before.revision())));
						throw new ZfgcConflictException("backup job changed since it was read");
					}
					BackupJob after = copy(before, call.getArgument(3),
							before.revision() + 1, call.getArgument(4), clock.instant());
					current.set(after);
					return after;
				});
		when(jobs.complete(anyString(), anyLong(), anyLong(), anyString(),
				anyBoolean(), nullable(Integer.class))).thenAnswer(call -> {
					BackupJob before = current.get();
					if (before == null || !before.id().equals(call.getArgument(0))
							|| before.revision() != call.<Long>getArgument(1)) {
						recordOffThreadFailure(new AssertionError("a completion was recorded for "
								+ call.getArgument(0) + " at revision " + call.getArgument(1)
								+ ", but the job was " + before));
						throw new ZfgcConflictException("backup job changed since it was read");
					}
					BackupJob after = new BackupJob(before.id(), State.READY,
							before.revision() + 1, before.createdAt(), clock.instant(),
							before.expiresAt(), before.creatorUserId(), call.getArgument(2),
							call.getArgument(3), call.getArgument(4), call.getArgument(5), null);
					current.set(after);
					return after;
				});
		when(jobs.deleteTerminal(anyString(), anyLong(), any(Instant.class)))
				.thenAnswer(call -> {
					BackupJob before = current.get();
					if (before == null || !before.id().equals(call.getArgument(0))
							|| before.revision() != call.<Long>getArgument(1))
						return false;
					current.set(null);
					return true;
				});

		PostgresBackupTool postgres = mock(PostgresBackupTool.class);
		var metadata = new PostgresBackupTool.DatabaseMetadata("zfgbb", 18,
				"pg_dump (PostgreSQL) 18.0", 18, "20260729.1", 1);
		when(postgres.metadata()).thenReturn(metadata);
		doAnswer(call -> {
			Files.writeString(call.getArgument(0), "database-dump");
			return null;
		}).when(postgres).dump(any(Path.class), eq(metadata));
		MaintenanceCoordinator maintenance = mock(MaintenanceCoordinator.class);
		when(maintenance.acquireExclusive(any())).thenReturn(null);
		ContentRoot contentRoot = mock(ContentRoot.class);
		when(contentRoot.activeContentRoot()).thenReturn(content);
		UserDataProvider userDataProvider = mock(UserDataProvider.class);
		when(userDataProvider.siteAdministratorIdsWithUsableCredentials()).thenReturn(List.of());
		Flyway flyway = mock(Flyway.class);
		BackupRestoreService service = new BackupRestoreService(storage, jobs, properties,
				postgres, maintenance, contentRoot, userDataProvider, mock(ContentResourceDao.class), clock);
		RestoreService restore = new RestoreService(storage, properties, postgres, contentRoot,
				flyway);
		return new Fixture(content, storage, jobs, current, properties, postgres, contentRoot,
				flyway, service, restore);
	}

	private static String sha256Hex(byte[] payload) {
		try {
			return HexFormat.of().formatHex(
					MessageDigest.getInstance("SHA-256").digest(payload));
		} catch (NoSuchAlgorithmException unavailable) {
			throw new IllegalStateException(unavailable);
		}
	}

	private static final class AdjustableClock extends Clock {
		private final AtomicReference<Instant> instant;

		private AdjustableClock(Instant start) {
			this.instant = new AtomicReference<>(start);
		}

		@Override
		public ZoneId getZone() {
			return ZoneOffset.UTC;
		}

		@Override
		public Clock withZone(ZoneId zone) {
			return this;
		}

		@Override
		public Instant instant() {
			return instant.get();
		}

		private void advance(Duration amount) {
			instant.updateAndGet(current -> current.plus(amount));
		}
	}

	private static BackupJob awaitState(AtomicReference<BackupJob> current, State state)
			throws InterruptedException {
		return awaitState(current, null, state);
	}

	private static BackupJob awaitState(AtomicReference<BackupJob> current, String id, State state)
			throws InterruptedException {
		for (int attempt = 0; attempt < 500; attempt++) {
			BackupJob job = current.get();
			if (job != null && job.state() == state && (id == null || job.id().equals(id)))
				return job;
			Thread.sleep(10);
		}
		throw new AssertionError("backup did not reach " + state);
	}

	private static BackupJob copy(BackupJob before, State state, long revision,
			String error) {
		return copy(before, state, revision, error, Instant.now());
	}

	private static BackupJob copy(BackupJob before, State state, long revision,
			String error, Instant updatedAt) {
		return new BackupJob(before.id(), state, revision, before.createdAt(),
				updatedAt, before.expiresAt(), before.creatorUserId(),
				before.archiveBytes(), before.archiveSha256(),
				before.installerCompatible(), before.installerAnchorAdministratorId(),
				error);
	}

	private record Fixture(Path content, OperationStorageService storage,
			BackupJobRepository jobs, AtomicReference<BackupJob> current,
			BackupRestoreProperties properties, PostgresBackupTool postgres,
			ContentRoot contentRoot, Flyway flyway, BackupRestoreService service,
			RestoreService restore) {
		BackupJob job(State state, Instant expiresAt) {
			Instant now = Instant.now();
			return job(state, now, expiresAt);
		}

		BackupJob job(State state, Instant updatedAt, Instant expiresAt) {
			BackupJob job = new BackupJob(UUID.randomUUID().toString(), state, 0,
					updatedAt, updatedAt, expiresAt, 1,
					null, null, null, null, null);
			current.set(job);
			return job;
		}

		BackupJob ready(byte[] payload, Instant expiresAt) throws IOException {
			BackupJob job = readyMetadata(payload.length, sha256Hex(payload), expiresAt);
			Path archive = storage.archivePath(job.id());
			Files.write(archive, payload);
			return job;
		}

		BackupJob readyMetadata(long bytes, Instant expiresAt) {
			return readyMetadata(bytes, "digest", expiresAt);
		}

		BackupJob readyMetadata(long bytes, String archiveSha256, Instant expiresAt) {
			BackupJob job = job(State.READY, expiresAt);
			BackupJob ready = new BackupJob(job.id(), State.READY, job.revision(),
					job.createdAt(), job.updatedAt(), job.expiresAt(), job.creatorUserId(),
					bytes, archiveSha256, false, null, null);
			current.set(ready);
			return ready;
		}
	}
}
