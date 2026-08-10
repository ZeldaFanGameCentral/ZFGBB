package com.zfgc.zfgbb.migrator.jobs;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.io.TempDir;

import com.zfgc.zfgbb.migrator.converters.AbstractConverter;

class JobServiceLifecycleTest {

	private ExecutorService executor;

	@AfterEach
	void stopExecutor() {
		if (executor != null) executor.shutdownNow();
	}

	@RepeatedTest(20)
	void queuedCancellationCannotDispatchAndAdmissionReopensAfterRunningJobFinishes() throws Exception {
		CountDownLatch entered = new CountDownLatch(1);
		CountDownLatch release = new CountDownLatch(1);
		List<AbstractConverter<?>> converters = converters(Map.of(JobType.CATEGORIES, () -> {
			entered.countDown();
			if (!release.await(2, TimeUnit.SECONDS)) throw new IllegalStateException("test latch timed out");
		}));
		executor = Executors.newSingleThreadExecutor();
		JobService service = new JobService(converters, null, null, executor,
				Optional.empty(), Optional.empty());
		Job running = job(JobType.CATEGORIES);
		Job queued = job(JobType.BOARDS);

		service.enqueuePrepared(List.of(running, queued));
		assertTrue(entered.await(2, TimeUnit.SECONDS));
		assertEquals(JobState.RUNNING, running.getState());
		assertEquals(JobState.QUEUED, queued.getState());
		assertTrue(service.cancel(queued.getId()));
		assertEquals(JobState.CANCELLED, queued.getState());
		assertThrows(IllegalArgumentException.class,
				() -> service.enqueuePrepared(List.of(job(JobType.BOARDS))));

		release.countDown();
		awaitState(running, JobState.COMPLETED);
		Job subsequent = job(JobType.BOARDS);
		service.enqueuePrepared(List.of(subsequent));
		awaitState(subsequent, JobState.COMPLETED);
	}

	@Test
	@Timeout(value = 30, unit = TimeUnit.SECONDS)
	void cancelAllDoesNotHoldTheServiceMonitorWhileTakingJobMonitors() throws Exception {
		CountDownLatch entered = new CountDownLatch(1);
		CountDownLatch release = new CountDownLatch(1);
		CountDownLatch monitorsHeld = new CountDownLatch(1);
		CountDownLatch releaseMonitors = new CountDownLatch(1);
		executor = Executors.newSingleThreadExecutor();
		JobService service = new JobService(converters(Map.of(JobType.CATEGORIES, () -> {
			entered.countDown();
			release.await(10, TimeUnit.SECONDS);
		})), null, null, executor, Optional.empty(), Optional.empty());

		List<Job> submitted = new ArrayList<>();
		for (JobType type : List.of(JobType.CATEGORIES, JobType.BOARDS, JobType.THREADS, JobType.MESSAGES)) {
			submitted.add(job(type));
		}
		service.enqueuePrepared(submitted);
		assertTrue(entered.await(5, TimeUnit.SECONDS));

		Thread holder = daemon("monitor-holder",
				() -> holdMonitors(submitted, 0, monitorsHeld, releaseMonitors));
		assertTrue(monitorsHeld.await(5, TimeUnit.SECONDS), "the test never took the job monitors");
		Thread canceller = daemon("cancel-all", service::cancelAll);
		awaitBlocked(canceller);

		CountDownLatch probeReturned = new CountDownLatch(1);
		daemon("service-monitor-probe", () -> {
			try {
				service.enqueuePrepared(List.of(job(JobType.IPS)));
			} catch (IllegalArgumentException expected) {
			} finally {
				probeReturned.countDown();
			}
		});

		assertTrue(probeReturned.await(5, TimeUnit.SECONDS),
				"cancelAll() is parked on a job monitor while holding the service monitor, "
						+ "which is the lock order that deadlocks against a step finishing");

		releaseMonitors.countDown();
		release.countDown();
		canceller.join(Duration.ofSeconds(10).toMillis());
		assertFalse(canceller.isAlive(), "cancelAll() never returned");
		holder.join(Duration.ofSeconds(5).toMillis());
	}

	private void awaitBlocked(Thread thread) {
		Instant deadline = Instant.now().plus(Duration.ofSeconds(5));
		while (thread.getState() != Thread.State.BLOCKED && Instant.now().isBefore(deadline))
			Thread.onSpinWait();
		assertEquals(Thread.State.BLOCKED, thread.getState(),
				"cancelAll() never reached a job monitor, so the probe below would prove nothing");
	}

	private void holdMonitors(List<Job> jobs, int index, CountDownLatch held, CountDownLatch release)
			throws InterruptedException {
		if (index == jobs.size()) {
			held.countDown();
			release.await(10, TimeUnit.SECONDS);
			return;
		}
		synchronized (jobs.get(index)) {
			holdMonitors(jobs, index + 1, held, release);
		}
	}

	private Thread daemon(String name, CheckedRunnable body) {
		Thread thread = new Thread(() -> {
			try {
				body.run();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}, name);
		thread.setDaemon(true);
		thread.start();
		return thread;
	}

	@Test
	void aStepThatFailsCancelsTheStepsStillQueuedBehindIt() throws Exception {
		executor = Executors.newSingleThreadExecutor();
		JobService service = new JobService(converters(Map.of(JobType.CATEGORIES, () -> {
			throw new IllegalStateException("smf table is missing a column");
		})), null, null, executor, Optional.empty(), Optional.empty());
		Job failing = job(JobType.CATEGORIES);
		Job next = job(JobType.BOARDS);
		Job last = job(JobType.THREADS);

		service.enqueuePrepared(List.of(failing, next, last));

		awaitState(failing, JobState.FAILED);
		awaitState(next, JobState.CANCELLED);
		awaitState(last, JobState.CANCELLED);
		assertEquals("smf table is missing a column", failing.getError());
		assertTrue(next.getError().contains("CATEGORIES failed earlier"),
				"a cancelled step should name the step that failed; was: " + next.getError());
	}

	@Test
	void aStepThatThrowsAnErrorIsRecordedAsFailedNotCompleted() throws Exception {
		executor = Executors.newSingleThreadExecutor();
		JobService service = new JobService(converters(Map.of(JobType.CATEGORIES, () -> {
			throw new SimulatedError();
		})), null, null, executor, Optional.empty(), Optional.empty());
		Job failing = job(JobType.CATEGORIES);
		Job next = job(JobType.BOARDS);

		service.enqueuePrepared(List.of(failing, next));

		awaitState(failing, JobState.FAILED);
		awaitState(next, JobState.CANCELLED);
		assertTrue(failing.getError().contains("SimulatedError"),
				"the recorded error should carry the Error type; was: " + failing.getError());
	}

	private List<AbstractConverter<?>> converters(Map<JobType, CheckedRunnable> behaviours) {
		List<AbstractConverter<?>> result = new ArrayList<>();
		for (JobType type : JobType.values()) {
			if (type.isPipeline()) continue;
			result.add(new TestConverter(type, behaviours.getOrDefault(type, () -> {})));
		}
		return result;
	}

	@Nested
	class AssetRootValidation {

		@TempDir
		Path readable;

		@Test
		void copyingAttachmentsNeedsASource() {
			assertRejects(JobType.ATTACHMENT_FILES, "attachmentsSourcePath",
					paths(map("attachmentsSourcePath", "   ")));
			assertRejects(JobType.ATTACHMENT_FILES, "attachmentsSourcePath", paths(map()));
			assertDoesNotThrow(() -> service().validateAssetRoots(JobType.ATTACHMENT_FILES,
					paths(map("attachmentsSourcePath", readable.toString()))),
					"the server supplies the destination, so a readable source is all the admin owes us");
		}

		@Test
		void avatarsAreValidatedAgainstTheStepThatActuallyReadsThem() {
			assertRejects(JobType.USER_BIO_INFO, "avatarsSourcePath",
					paths(map("avatarsSourcePath", readable.resolve("gone").toString())));
			assertDoesNotThrow(() -> service().validateAssetRoots(JobType.ATTACHMENT_FILES,
					paths(map("attachmentsSourcePath", readable.toString(),
							"avatarsSourcePath", readable.resolve("gone").toString()))),
					"ATTACHMENT_FILES never reads avatarsSourcePath, so it must not gate on it");
		}

		@Test
		void theAdminIsNeverAskedWhereToCopyFilesTo() {
			assertDoesNotThrow(() -> service().validateAssetRoots(JobType.USER_BIO_INFO,
					paths(map("avatarsSourcePath", readable.toString()))));
			assertDoesNotThrow(() -> service().validateAssetRoots(JobType.WIKI_PAGES,
					paths(map("wikiImagesSourcePath", readable.toString()))));
			assertDoesNotThrow(() -> service().validateAssetRoots(JobType.WIKI_PAGES, paths(map())));
		}

		@Test
		void pipelineSubmissionsAreCheckedBeforeAnyConnectionIsAttempted() {
			IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
					() -> new JobService(converters(Map.of()), null, null, null, Optional.empty(), Optional.empty())
							.submit(JobType.MIGRATE_EVERYTHING, paths(map())));
			assertTrue(thrown.getMessage().contains("attachmentsSourcePath"),
					"a pipeline must expand to its steps and fail on cheap local checks before dialling "
							+ "the source database; was: " + thrown.getMessage());
		}

		@Test
		void aStepThatCopiesNoFilesIsNotBlockedByUnconfiguredPaths() {
			assertDoesNotThrow(() -> service().validateAssetRoots(JobType.MEMBER_GROUPS, paths(map())));
		}

		@Test
		void anUnusablePathIsRejectedWithTheFieldThatCarriesIt() throws Exception {
			Path file = Files.createFile(readable.resolve("not-a-directory"));
			assertRejects(JobType.WIKI_PAGES, "must be an absolute path",
					paths(map("wikiImagesSourcePath", "relative/images")));
			assertRejects(JobType.WIKI_PAGES, "is not a directory",
					paths(map("wikiImagesSourcePath", file.toString())));
		}

		private void assertRejects(JobType type, String expected, SmfConnectionParams params) {
			IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
					() -> service().validateAssetRoots(type, params));
			assertTrue(thrown.getMessage().contains(expected),
					"expected a message naming " + expected + "; was: " + thrown.getMessage());
		}

		private JobService service() {
			return new JobService(converters(Map.of()), null, null, null, Optional.empty(), Optional.empty());
		}

		private Map<String, String> map(String... keyThenValue) {
			Map<String, String> result = new java.util.HashMap<>();
			for (int i = 0; i < keyThenValue.length; i += 2) result.put(keyThenValue[i], keyThenValue[i + 1]);
			return result;
		}

		private SmfConnectionParams paths(Map<String, String> configured) {
			return new SmfConnectionParams("jdbc:mysql://127.0.0.1:3306/smf", "root", "pw", "smf_1",
					"zfgc.com", "http://localhost:5173",
					configured.get("attachmentsSourcePath"),
					configured.get("avatarsSourcePath"), configured.get("cmsFilesSourcePath"),
					configured.get("wikiImagesSourcePath"), false, false, null, null,
					Map.of(), Map.of(), Map.of(), Map.of(), Map.of(), "zfgc.com");
		}
	}

	private Job job(JobType type) {
		Job job = new Job();
		job.setId(UUID.randomUUID());
		job.setType(type);
		job.setSmfJdbcUrl("jdbc:postgresql://127.0.0.1/test");
		job.setSubmittedAt(Instant.now());
		job.setState(JobState.QUEUED);
		return job;
	}

	private static final class TestConverter extends AbstractConverter<Void> {
		private final JobType type;
		private final CheckedRunnable action;
		private TestConverter(JobType type, CheckedRunnable action) { this.type = type; this.action = action; }
		@Override public JobType getType() { return type; }
		@Override public Void convertToZfgbb() throws Exception { action.run(); return null; }
	}

	@FunctionalInterface
	private interface CheckedRunnable { void run() throws Exception; }

	private static final class SimulatedError extends Error {}

	private void awaitState(Job job, JobState expected) throws InterruptedException {
		Instant deadline = Instant.now().plus(Duration.ofSeconds(2));
		while (job.getState() != expected && Instant.now().isBefore(deadline)) Thread.sleep(5);
		assertEquals(expected, job.getState());
	}
}
