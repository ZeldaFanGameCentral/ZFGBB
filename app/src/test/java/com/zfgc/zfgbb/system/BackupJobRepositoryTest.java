package com.zfgc.zfgbb.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.junit.jupiter.Container;

import com.zfgc.zfgbb.exception.ZfgcConflictException;
import com.zfgc.zfgbb.model.system.BackupJob;
import com.zfgc.zfgbb.model.system.BackupJob.State;
import com.zfgc.zfgbb.services.system.BackupJobRepository;
import com.zfgc.zfgbb.services.system.MaintenanceCoordinator;
import com.zfgc.zfgbb.testsupport.ZfgbbIntegrationTest;
import com.zfgc.zfgbb.testsupport.mappers.TestSystemInfoMapper;

class BackupJobRepositoryTest extends ZfgbbIntegrationTest {

	@Container
	static ComposeContainer pg = devPostgres();

	private static final Path CONTENT_ROOT = Path.of(System.getProperty("java.io.tmpdir"),
			"zfgbb-backup-job-content-" + UUID.randomUUID());
	private static final Path WORK_ROOT = Path.of(System.getProperty("java.io.tmpdir"),
			"zfgbb-backup-job-work-" + UUID.randomUUID());

	@Autowired
	private BackupJobRepository jobs;

	@DynamicPropertySource
	static void props(DynamicPropertyRegistry registry) {
		datasource(registry, pg);
		registry.add("zfgbb.content.path", CONTENT_ROOT::toString);
		registry.add("zfgbb.operations.work-path", WORK_ROOT::toString);
		registry.add("zfgbb.operations.mutation-drain-timeout", () -> "500ms");
	}

	@Autowired
	private TestSystemInfoMapper systemInfoMapper;

	@Test
	void persistsAndCompareAndSetsBackupState() {
		assertEquals("zfgbb_bootstrap", systemInfoMapper.getDatabaseOwner());
		assertEquals("zfgcadmin", systemInfoMapper.getSchemaOwner());
		assertEquals(Boolean.FALSE, systemInfoMapper.isCurrentUserSuperuser());
		assertTrue(Boolean.TRUE.equals(systemInfoMapper.hasZfgcAdminRole()));
		assertTrue(Boolean.TRUE.equals(systemInfoMapper.isZfgcAdminOptionGranted()));
		assertTrue(Boolean.TRUE.equals(systemInfoMapper.hasDatabasePrivileges()));

		Instant now = Instant.now();
		var creating = jobs.create(null, now, now.plusSeconds(3600));

		var ready = jobs.complete(creating.id(), creating.revision(), 42,
				"a".repeat(64), false, null);

		assertEquals(State.READY, ready.state());
		assertEquals(42, ready.archiveBytes());
		assertEquals(1, ready.revision());
		assertEquals(ready.id(), jobs.list().getFirst().id());
		assertThrows(ZfgcConflictException.class,
				() -> jobs.transition(ready.id(), creating.revision(),
						Set.of(State.CREATING), State.FAILED, "stale"));

		var downloading = jobs.transition(ready.id(), ready.revision(),
				Set.of(State.READY), State.DOWNLOADING, null);
		assertEquals(State.DOWNLOADING, jobs.require(ready.id()).state());
		assertEquals(2, downloading.revision());

		var failed = jobs.transition(downloading.id(), downloading.revision(),
				Set.of(State.DOWNLOADING), State.FAILED, "failed");
		assertEquals(true, jobs.deleteTerminal(failed.id(), failed.revision(),
				Instant.now().plusSeconds(1)));
		assertEquals(true, jobs.find(failed.id()).isEmpty());
	}

	private static final long ADVISORY_LOCK_CLASS_ID = 0x5A464742L;
	private static final long MAINTENANCE_LOCK_OBJ_ID = 0x424D4149L;

	@Autowired
	private MaintenanceCoordinator coordinator;

	private long exclusiveMaintenanceLocksHeld() {
		return systemInfoMapper.countAdvisoryExclusiveLocks(ADVISORY_LOCK_CLASS_ID,
				MAINTENANCE_LOCK_OBJ_ID);
	}

	@Test
	void anOutstandingWriterBlocksTheMaintenanceWindowUntilItDrains() throws Exception {
		MaintenanceCoordinator.Lease writer = coordinator.tryMutationLease().orElseThrow();

		SQLException drainTimedOut = assertThrows(SQLException.class,
				() -> coordinator.acquireExclusive(Duration.ofMillis(500)));
		assertEquals("timed out draining active application mutations", drainTimedOut.getMessage());
		assertEquals(0, exclusiveMaintenanceLocksHeld(),
				"a window that failed to drain must not leave its advisory lock behind");

		writer.close();

		try (MaintenanceCoordinator.Lease window = coordinator.acquireExclusive(Duration.ofSeconds(10))) {
			assertEquals(1, exclusiveMaintenanceLocksHeld(),
					"the drained window holds the advisory lock so no other replica can dump");
			assertTrue(coordinator.tryMutationLease().isEmpty(),
					"the window must turn new writers away");
		}
		assertEquals(0, exclusiveMaintenanceLocksHeld(), "closing the window releases the lock");
		coordinator.tryMutationLease().orElseThrow().close();
	}

	@Test
	void aLeaseTakenOnOneThreadCanBeReleasedOnAnother() throws Exception {
		MaintenanceCoordinator.Lease lease = coordinator.acquireMutationLease();
		AtomicReference<Throwable> releaseFailure = new AtomicReference<>();
		Thread releaser = new Thread(() -> {
			try {
				lease.close();
			} catch (SQLException unreleasable) {
				releaseFailure.set(unreleasable);
			}
		});
		releaser.setUncaughtExceptionHandler((thread, escaped) -> releaseFailure.set(escaped));
		releaser.start();
		releaser.join(Duration.ofSeconds(5).toMillis());

		assertFalse(releaser.isAlive(), "the releasing thread must not still be closing the lease");
		Throwable escaped = releaseFailure.get();
		if (escaped != null)
			throw new AssertionError("the lease could not be released from another thread", escaped);

		try (MaintenanceCoordinator.Lease window = coordinator.acquireExclusive(Duration.ofSeconds(5))) {
			assertEquals(1, exclusiveMaintenanceLocksHeld(),
					"the migrator releases its pipeline lease from a worker thread, not the "
							+ "request thread that took it");
		}
	}

	@Test
	void aWriterWaitingOnMaintenanceGivesUpRatherThanBlockingForever() throws Exception {
		try (MaintenanceCoordinator.Lease window = coordinator.acquireExclusive(Duration.ofSeconds(10))) {
			long startedAt = System.nanoTime();
			assertThrows(IllegalStateException.class, () -> coordinator.acquireMutationLease());
			Duration waited = Duration.ofNanos(System.nanoTime() - startedAt);
			assertTrue(waited.compareTo(Duration.ofMillis(400)) >= 0,
					"a held window must make the writer actually wait out the configured timeout, "
							+ "not fail instantly: waited " + waited.toMillis() + "ms");
			assertTrue(waited.compareTo(Duration.ofSeconds(5)) < 0,
					"the wait must be bounded by the 500ms test timeout, not the 30s default: "
							+ "waited " + waited.toMillis() + "ms");
		}
	}

	@Test
	void aQueuedWindowTurnsAwayRequestWritersButNotAnAlreadyRunningPipeline() throws Exception {
		CountDownLatch requestAdmitted = new CountDownLatch(1);
		CountDownLatch windowIsQueued = new CountDownLatch(1);
		CountDownLatch requestFinished = new CountDownLatch(1);
		AtomicBoolean pipelineAdmitted = new AtomicBoolean();
		AtomicReference<Throwable> pipelineRefusal = new AtomicReference<>();
		Thread request = new Thread(() -> {
			try (MaintenanceCoordinator.Lease held = coordinator.tryMutationLease().orElseThrow()) {
				requestAdmitted.countDown();
				windowIsQueued.await(15, TimeUnit.SECONDS);
				coordinator.acquireMutationLease().close();
				pipelineAdmitted.set(true);
			} catch (Exception refused) {
				pipelineRefusal.set(refused);
				pipelineAdmitted.set(false);
			} finally {
				requestFinished.countDown();
			}
		});
		request.start();
		assertTrue(requestAdmitted.await(15, TimeUnit.SECONDS));

		AtomicBoolean windowDrained = new AtomicBoolean();
		AtomicReference<Throwable> windowFailure = new AtomicReference<>();
		Thread window = new Thread(() -> {
			try (MaintenanceCoordinator.Lease ignored =
					coordinator.acquireExclusive(Duration.ofSeconds(10))) {
				assertEquals(1, exclusiveMaintenanceLocksHeld(),
						"the window that finally drains must hold the advisory lock it queued for");
				windowDrained.set(true);
			} catch (SQLException | InterruptedException drainFailed) {
				windowFailure.set(drainFailed);
			}
		});
		window.setUncaughtExceptionHandler((thread, escaped) -> windowFailure.set(escaped));
		window.start();

		long deadline = System.nanoTime() + Duration.ofSeconds(10).toNanos();
		Optional<MaintenanceCoordinator.Lease> probe;
		while ((probe = coordinator.tryMutationLease()).isPresent()
				&& System.nanoTime() < deadline)
			probe.get().close();
		assertTrue(probe.isEmpty(),
				"this thread holds no permit, so a queued window must turn it away rather than let "
						+ "it barge; an unconditional barge would starve the drain");
		assertThrows(IllegalStateException.class, () -> coordinator.acquireMutationLease(),
				"a background writer on a thread holding no permit must wait its turn behind the "
						+ "queued window; barging unconditionally here is what starves the drain");
		windowIsQueued.countDown();

		assertTrue(requestFinished.await(15, TimeUnit.SECONDS));
		assertTrue(pipelineAdmitted.get(), () -> "a migration pipeline is submitted from inside a "
				+ "request that already holds a permit, so it must not deadlock against the queued "
				+ "window: " + pipelineRefusal.get());
		window.join(Duration.ofSeconds(20).toMillis());
		assertFalse(window.isAlive(),
				"the window must not still be queued once the pipeline released its permit");
		Throwable escaped = windowFailure.get();
		if (escaped != null)
			throw new AssertionError(
					"the queued maintenance window did not complete its drain", escaped);
		assertTrue(windowDrained.get(),
				"a window thread that died part way through proves nothing; it must reach the end "
						+ "of the lease it queued for");
	}

	@Test
	void poolConnectionsCarryTheApplicationNameTheReplicaCheckFiltersOn() {
		assertEquals("zfgbb-api", systemInfoMapper.getApplicationName(),
				"assertSingleReplica counts backends by application_name, so it silently degrades "
						+ "to a no-op if the pool stops setting one");
	}

	@Test
	void aHeldWindowStillRefusesAPipelineWriter() throws Exception {
		try (MaintenanceCoordinator.Lease window = coordinator.acquireExclusive(Duration.ofSeconds(10))) {
			assertThrows(IllegalStateException.class, () -> coordinator.acquireMutationLease(),
					"barging past a queued window must not extend to an open one");
		}
		coordinator.acquireMutationLease().close();
	}
}
