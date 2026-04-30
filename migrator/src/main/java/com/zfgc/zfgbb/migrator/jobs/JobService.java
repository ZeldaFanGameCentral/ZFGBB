package com.zfgc.zfgbb.migrator.jobs;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.migrator.converters.AttachmentFilesConverter;
import com.zfgc.zfgbb.migrator.converters.AttachmentsConverter;
import com.zfgc.zfgbb.migrator.converters.BoardConverter;
import com.zfgc.zfgbb.migrator.converters.CategoryConverter;
import com.zfgc.zfgbb.migrator.converters.IpAddressConverter;
import com.zfgc.zfgbb.migrator.converters.KarmaConverter;
import com.zfgc.zfgbb.migrator.converters.MessageConverter;
import com.zfgc.zfgbb.migrator.converters.MessageHistoryConverter;
import com.zfgc.zfgbb.migrator.converters.PollChoiceConverter;
import com.zfgc.zfgbb.migrator.converters.PollConverter;
import com.zfgc.zfgbb.migrator.converters.ThreadConverter;
import com.zfgc.zfgbb.migrator.converters.UserBioInfoConverter;
import com.zfgc.zfgbb.migrator.converters.UserContactInfoConverter;
import com.zfgc.zfgbb.migrator.converters.UserPollChoiceConverter;
import com.zfgc.zfgbb.migrator.converters.UsersConverter;

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

	@Autowired private UsersConverter usersConverter;
	@Autowired private CategoryConverter categoryConverter;
	@Autowired private BoardConverter boardConverter;
	@Autowired private ThreadConverter threadConverter;
	@Autowired private MessageConverter messageConverter;
	@Autowired private IpAddressConverter ipAddressConverter;
	@Autowired private MessageHistoryConverter messageHistoryConverter;
	@Autowired private UserBioInfoConverter userBioInfoConverter;
	@Autowired private AttachmentsConverter attachmentsConverter;
	@Autowired private AttachmentFilesConverter attachmentFilesConverter;
	@Autowired private UserContactInfoConverter userContactInfoConverter;
	@Autowired private PollConverter pollConverter;
	@Autowired private PollChoiceConverter pollChoiceConverter;
	@Autowired private UserPollChoiceConverter userPollChoiceConverter;
	@Autowired private KarmaConverter karmaConverter;

	public Job submit(JobType type) {
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
		switch (type) {
			case USERS -> usersConverter.convertToZfgbb();
			case CATEGORIES -> categoryConverter.convertToZfgbb();
			case BOARDS -> boardConverter.convertToZfgbb();
			case THREADS -> threadConverter.convertToZfgbb();
			case MESSAGES -> messageConverter.convertToZfgbb();
			case IPS -> ipAddressConverter.convertToZfgbb();
			case MESSAGE_HISTORY -> messageHistoryConverter.convertToZfgbb();
			case USER_BIO_INFO -> userBioInfoConverter.convertToZfgbb();
			case ATTACHMENTS -> attachmentsConverter.convertToZfgbb();
			case ATTACHMENT_FILES -> attachmentFilesConverter.convertToZfgbb();
			case USER_CONTACT_INFO -> userContactInfoConverter.convertToZfgbb();
			case POLLS -> pollConverter.convertToZfgbb();
			case POLL_CHOICES -> pollChoiceConverter.convertToZfgbb();
			case USER_POLL_CHOICES -> userPollChoiceConverter.convertToZfgbb();
			case KARMA -> karmaConverter.convertToZfgbb();
		}
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
