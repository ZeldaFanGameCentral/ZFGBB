package com.zfgc.zfgbb.migrator.jobs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;

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
		List<AbstractConverter<?>> converters = converters(entered, release);
		executor = Executors.newSingleThreadExecutor();
		JobService service = new JobService(converters, null, null, executor,
				Optional.empty());
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

	private List<AbstractConverter<?>> converters(CountDownLatch entered, CountDownLatch release) {
		List<AbstractConverter<?>> result = new ArrayList<>();
		for (JobType type : JobType.values()) {
			if (type == JobType.MIGRATE_SMF_INSTALLATION || type == JobType.MIGRATE_CMS_INSTALLATION) continue;
			result.add(new TestConverter(type, type == JobType.CATEGORIES ? () -> {
				entered.countDown();
				if (!release.await(2, TimeUnit.SECONDS)) throw new IllegalStateException("test latch timed out");
			} : () -> {}));
		}
		return result;
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

	private void awaitState(Job job, JobState expected) throws InterruptedException {
		Instant deadline = Instant.now().plus(Duration.ofSeconds(2));
		while (job.getState() != expected && Instant.now().isBefore(deadline)) Thread.sleep(5);
		assertEquals(expected, job.getState());
	}
}
