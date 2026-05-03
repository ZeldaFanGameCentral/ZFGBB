package com.zfgc.zfgbb.migrator.jobs;

import java.sql.Connection;
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

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
		List<JobType> missing = new ArrayList<>();
		for (JobType type : JobType.values()) {
			if (type == JobType.MIGRATE_SMF_INSTALLATION) {
				continue;
			}
			if (!convertersByType.containsKey(type)) {
				missing.add(type);
			}
		}
		if (!missing.isEmpty()) {
			throw new IllegalStateException(
					"No AbstractConverter registered for JobType(s): " + missing);
		}
	}

	public List<Job> submit(JobType type, SmfConnectionParams params) {
		validateConnection(params);
		if (type == JobType.MIGRATE_SMF_INSTALLATION) {
			List<Job> submitted = new ArrayList<>(JobType.SMF_INSTALLATION_PIPELINE.size());
			for (JobType step : JobType.SMF_INSTALLATION_PIPELINE) {
				submitted.add(submitOne(step, params));
			}
			return submitted;
		}
		return List.of(submitOne(type, params));
	}

	private void validateConnection(SmfConnectionParams params) {
		DataSource ds = buildDataSource(params.jdbcUrl(), params.username(), params.password());
		try (Connection conn = ds.getConnection()) {
			// connection validated
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot connect to SMF database: " + e.getMessage(), e);
		} finally {
			closeDataSource(ds);
		}
	}

	private Job submitOne(JobType type, SmfConnectionParams params) {
		Job job = new Job();
		job.setId(UUID.randomUUID());
		job.setType(type);
		job.setState(JobState.QUEUED);
		job.setSubmittedAt(Instant.now());
		job.setSmfJdbcUrl(params.jdbcUrl());
		job.setSmfUser(params.username());
		job.setSmfPassword(params.password());
		job.setSmfTablePrefix(params.smfTablePrefix());
		job.setSmfLegacyHost(params.smfLegacyHost());
		job.setAppBaseUrl(params.appBaseUrl());
		job.setAttachmentsSourcePath(params.attachmentsSourcePath());
		job.setAttachmentsTargetPath(params.attachmentsTargetPath());
		job.setForce(params.force());
		jobs.put(job.getId(), job);
		futures.put(job.getId(), executor.submit(() -> run(job)));
		return job;
	}

	private void run(Job job) {
		DataSource ds = buildDataSource(job.getSmfJdbcUrl(), job.getSmfUser(), job.getSmfPassword());
		JobContextHolder.set(ds, job.getAttachmentsSourcePath(), job.getAttachmentsTargetPath(),
				job.getSmfTablePrefix(), job.getSmfLegacyHost(), job.getAppBaseUrl(), job.isForce());
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
			JobContextHolder.clear();
			closeDataSource(ds);
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

	private DataSource buildDataSource(String jdbcUrl, String username, String password) {
		return DataSourceBuilder.create()
				.url(jdbcUrl)
				.username(username)
				.password(password)
				.build();
	}

	private void closeDataSource(DataSource ds) {
		if (ds instanceof AutoCloseable closeable) {
			try {
				closeable.close();
			} catch (Exception ignored) {
			}
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
