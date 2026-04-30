package com.zfgc.zfgbb.migrator.jobs;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.migrator.converters.AbstractConverter;

import jakarta.annotation.PreDestroy;

@Service
public class JobService {

	private static final Logger log = LoggerFactory.getLogger(JobService.class);

	private final ConcurrentHashMap<UUID, Job> jobs = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<UUID, Future<?>> futures = new ConcurrentHashMap<>();
	private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
		Thread t = new Thread(r, "migrator-job-runner");
		t.setDaemon(true);
		return t;
	});

	private final Map<JobType, AbstractConverter<?>> convertersByType;

	public JobService(List<AbstractConverter<?>> converters) {
		this.convertersByType = converters.stream()
				.collect(Collectors.toMap(AbstractConverter::getType, Function.identity()));
	}

	public List<Job> submit(JobType type) {
		if (type == JobType.MIGRATE_SMF_INSTALLATION) {
			List<Job> submitted = new ArrayList<>(JobType.SMF_INSTALLATION_PIPELINE.size());
			for (JobType step : JobType.SMF_INSTALLATION_PIPELINE) {
				submitted.add(submitOne(step));
			}
			return submitted;
		}
		return List.of(submitOne(type));
	}

	private Job submitOne(JobType type) {
		Job job = new Job();
		job.setId(UUID.randomUUID());
		job.setType(type);
		job.setState(JobState.QUEUED);
		job.setSubmittedAt(Instant.now());
		jobs.put(job.getId(), job);

		Future<?> future = executor.submit(() -> run(job));
		futures.put(job.getId(), future);
		return job;
	}

	private void run(Job job) {
		job.setState(JobState.RUNNING);
		job.setStartedAt(Instant.now());
		try {
			dispatch(job.getType());
			job.setState(JobState.COMPLETED);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			job.setState(JobState.CANCELLED);
		} catch (Exception e) {
			if (Thread.currentThread().isInterrupted() || e.getCause() instanceof InterruptedException) {
				job.setState(JobState.CANCELLED);
			} else {
				log.error("Migrator job {} ({}) failed", job.getId(), job.getType(), e);
				job.setState(JobState.FAILED);
				job.setError(e.getMessage());
			}
		} finally {
			job.setFinishedAt(Instant.now());
			futures.remove(job.getId());
		}
	}

	private void dispatch(JobType type) throws Exception {
		AbstractConverter<?> converter = convertersByType.get(type);
		if (converter == null) {
			throw new IllegalStateException(type + " has no associated converter (likely a pipeline marker)");
		}
		converter.convertToZfgbb();
		if (Thread.currentThread().isInterrupted()) {
			throw new InterruptedException();
		}
	}

	public Optional<Job> get(UUID id) {
		return Optional.ofNullable(jobs.get(id));
	}

	public Collection<Job> list() {
		return jobs.values();
	}

	public boolean cancel(UUID id) {
		Future<?> future = futures.get(id);
		if (future == null) {
			return false;
		}
		boolean cancelled = future.cancel(true);
		Job job = jobs.get(id);
		if (cancelled && job != null && job.getState() == JobState.QUEUED) {
			job.setState(JobState.CANCELLED);
			job.setFinishedAt(Instant.now());
			futures.remove(id);
		}
		return cancelled;
	}

	@PreDestroy
	public void shutdown() {
		executor.shutdownNow();
	}
}
