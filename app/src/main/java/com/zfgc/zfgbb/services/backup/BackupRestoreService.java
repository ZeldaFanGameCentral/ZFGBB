package com.zfgc.zfgbb.services.backup;

import com.zfgc.zfgbb.dataprovider.system.BackupJobDataProvider;
import com.zfgc.zfgbb.dao.cms.ContentResourceDao;
import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.services.system.MaintenanceCoordinator;
import com.zfgc.zfgbb.services.contentstore.ContentRoot;
import static com.zfgc.zfgbb.operations.archive.OperationFiles.deleteTree;
import static com.zfgc.zfgbb.services.backup.OperationStorageService.setPrivateFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.config.BackupRestoreProperties;
import com.zfgc.zfgbb.exception.ZfgcConflictException;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.system.AdminBackupResponse;
import com.zfgc.zfgbb.model.system.BackupJob;
import com.zfgc.zfgbb.model.system.BackupJob.State;
import com.zfgc.zfgbb.operations.archive.BackupArchiveValidator;
import com.zfgc.zfgbb.operations.archive.BackupArchiveWriter;
import com.zfgc.zfgbb.operations.archive.ValidatedBackup;
import com.zfgc.zfgbb.operations.postgres.PostgresBackupTool;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
public class BackupRestoreService {
	private static final Logger LOG = LoggerFactory.getLogger(BackupRestoreService.class);
	private static final Set<State> TERMINAL = EnumSet.of(
			State.CONSUMED, State.EXPIRED, State.FAILED);
	private static final int COPY_BUFFER = 64 * 1024;
	private static final int DUMP_AND_ARCHIVE_PASSES = 3;
	private static final String PAYLOAD_MISMATCH_ERROR =
			"Backup payload is missing or does not match its metadata.";

	private final OperationStorageService storage;
	private final BackupJobDataProvider jobs;
	private final BackupRestoreProperties properties;
	private final PostgresBackupTool postgres;
	private final MaintenanceCoordinator maintenance;
	private final ContentRoot content;
	private final UserDataProvider userDataProvider;

	private final ContentResourceDao contentResourceDao;
	private final Clock clock;
	private final Set<String> unfinishedBackupJobIds = ConcurrentHashMap.newKeySet();
	private final Object backupCreationGate = new Object();
	private final ExecutorService backupExecutor = Executors.newSingleThreadExecutor(task -> {
		Thread thread = new Thread(task, "zfgbb-backup");
		thread.setDaemon(true);
		return thread;
	});

	public BackupRestoreService(OperationStorageService storage, BackupJobDataProvider jobs,
			BackupRestoreProperties properties, PostgresBackupTool postgres,
			MaintenanceCoordinator maintenance, ContentRoot content,
			UserDataProvider userDataProvider, ContentResourceDao contentResourceDao, Clock clock) {
		this.storage = storage;
		this.jobs = jobs;
		this.properties = properties;
		this.postgres = postgres;
		this.maintenance = maintenance;
		this.content = content;
		this.userDataProvider = userDataProvider;
		this.contentResourceDao = contentResourceDao;
		this.clock = clock;
		validateTimeouts(properties);
	}

	@PostConstruct
	void recoverInterruptedOperations() {
		for (BackupJob job : jobs.list()) {
			try {
				BackupJob current = expireBackup(job);
				if (current.state() == State.DOWNLOADING) {
					Path archive = storage.storedArchivePath(job.id());
					State recovered = payloadMatches(current, archive,
							PayloadVerification.RECORDED_DIGEST)
									? State.READY : State.FAILED;
					transitionOrReload(current.id(), current.revision(),
							Set.of(State.DOWNLOADING),
							recovered, recovered == State.FAILED
									? "Backup payload was unavailable after restart." : null);
				} else if (current.state() == State.CREATING) {
					transitionOrReload(current.id(), current.revision(),
							Set.of(State.CREATING),
							State.FAILED, "Backup creation was interrupted by restart.");
				} else if (current.state() == State.READY) {
					reconcileReady(current, PayloadVerification.RECORDED_DIGEST);
				} else if (current.state() == State.CONSUMED) {
					deletePayload(current.id());
				}
			} catch (IOException | RuntimeException failure) {
				LOG.warn("Unable to recover backup {}", job.id(), failure);
			}
		}
		cleanupExpiredArtifacts();
	}

	@Scheduled(fixedDelayString = "${zfgbb.operations.cleanup-interval:1h}",
			initialDelayString = "${zfgbb.operations.cleanup-interval:1h}")
	void cleanupExpiredArtifacts() {
		for (BackupJob job : jobs.list()) {
			try {
				cleanupBackup(job);
			} catch (IOException | RuntimeException failure) {
				LOG.warn("Unable to clean expired backup {}", job.id(), failure);
			}
		}
		cleanupOrphanDirectories();
	}

	public AdminBackupResponse createBackup(User creator) {
		synchronized (backupCreationGate) {
			requireNoBackupInProgress();
			Instant now = clock.instant();
			BackupJob job = jobs.create(creator.getUserId(), now,
					now.plus(properties.getBackupTtl()));
			unfinishedBackupJobIds.add(job.id());
			try {
				backupExecutor.submit(() -> createBackup(job.id()));
			} catch (RejectedExecutionException unavailable) {
				unfinishedBackupJobIds.remove(job.id());
				transitionOrReload(job.id(), job.revision(), Set.of(State.CREATING),
						State.FAILED, "Backup creation could not be scheduled.");
				throw new ZfgcConflictException("Backups are not accepting new work.");
			}
			return backupResponse(job);
		}
	}

	private void requireNoBackupInProgress() {
		for (BackupJob job : jobs.list()) {
			if (job.state() != State.CREATING)
				continue;
			if (unfinishedBackupJobIds.contains(job.id()))
				throw new ZfgcConflictException("a backup is already being created");
			if (stabilize(job).state() == State.CREATING)
				transitionOrReload(job.id(), job.revision(), Set.of(State.CREATING),
						State.FAILED, "Backup creation ended without recording an outcome.");
		}
	}

	public List<AdminBackupResponse> backups() {
		return jobs.list().stream()
				.sorted(Comparator.comparing(BackupJob::createdAt).reversed())
				.map(this::stabilize)
				.map(this::backupResponse)
				.toList();
	}

	public AdminBackupResponse backup(String id) {
		return backupResponse(stabilize(jobs.require(id)));
	}

	public DownloadClaim claimDownload(String id) {
		BackupJob job = stabilize(jobs.require(id));
		if (job.state() != State.READY)
			throw new ZfgcConflictException("Backup is not ready for download.");
		try {
			Path archive = storage.storedArchivePath(id);
			if (!payloadMatches(job, archive, PayloadVerification.RECORDED_DIGEST)) {
				markPayloadFailed(job);
				throw new ZfgcConflictException("Backup payload is unavailable.");
			}
			BackupJob claimed = jobs.transition(id, job.revision(), Set.of(State.READY),
					State.DOWNLOADING, null);
			return new DownloadClaim(claimed, archive);
		} catch (IOException unverifiable) {
			throw new IllegalStateException("Unable to claim backup download", unverifiable);
		}
	}

	public void streamDownload(DownloadClaim claim, OutputStream response) throws IOException {
		DownloadOutcome outcome = DownloadOutcome.INTERRUPTED;
		try (FileChannel archive = FileChannel.open(claim.archive(), StandardOpenOption.READ)) {
			if (!digestMatches(claim.job().archiveSha256(), sha256(archive))) {
				outcome = DownloadOutcome.CORRUPTED;
				throw new IOException("Backup payload no longer matches its recorded digest.");
			}
			archive.position(0);
			ByteBuffer buffer = ByteBuffer.allocate(COPY_BUFFER);
			while (archive.read(buffer) >= 0) {
				buffer.flip();
				response.write(buffer.array(), buffer.arrayOffset(), buffer.limit());
				buffer.clear();
			}
			response.flush();
			outcome = DownloadOutcome.COMPLETED;
		} finally {
			finishDownload(claim, outcome);
		}
	}

	private void createBackup(String id) {
		Path dump = null;
		try (MaintenanceCoordinator.Lease mutationsDrained = maintenance.acquireExclusive(
				properties.getMutationDrainTimeout())) {
			BackupJob job = jobs.require(id);
			Path directory = storage.jobDirectory(id);
			dump = directory.resolve("database.dump.tmp");
			Path activeContent = content.activeContentRoot();
			PostgresBackupTool.DatabaseMetadata database = postgres.metadata();
			long operationBytes;
			try {
				operationBytes = Math.addExact(Math.multiplyExact(database.databaseBytes(), 2),
						storage.contentBytes(activeContent));
			} catch (ArithmeticException overflow) {
				throw new IOException("backup storage estimate overflow", overflow);
			}
			storage.requireFreeSpace(operationBytes, properties.getStorageMarginBytes());
			postgres.dump(dump, database);
			Path archive = storage.archivePath(id);
			ArchiveInstallability classification = classifyInstallability(activeContent);
			ValidatedBackup validated = new BackupArchiveWriter(storage.limits()).write(
					new BackupArchiveWriter.Request(dump, activeContent,
							applicationVersion(), database.schemaVersion(), database.serverMajor(),
							database.dumpToolVersion(), classification.compatible(),
							classification.anchorAdministratorId(), clock.instant()),
					archive);
			setPrivateFile(archive);
			jobs.complete(id, job.revision(), validated.compressedBytes(),
					validated.archiveSha256(), classification.compatible(),
					classification.anchorAdministratorId());
		} catch (Exception failure) {
			LOG.error("Backup creation failed for job {}", id, failure);
			try {
				BackupJob current = jobs.require(id);
				if (current.state() == State.CREATING)
					jobs.transition(id, current.revision(), Set.of(State.CREATING),
							State.FAILED, "Backup creation failed.");
			} catch (RuntimeException originalFailureStaysAuthoritative) {
			}
		} finally {
			unfinishedBackupJobIds.remove(id);
			if (dump != null) {
				try {
					Files.deleteIfExists(dump);
				} catch (IOException expiryCleanupRemovesTheJobDirectory) {
				}
			}
		}
	}

	private BackupJob expireBackup(BackupJob job) {
		Instant now = clock.instant();
		if (!shouldExpire(job, now))
			return job;
		BackupJob latest = jobs.require(job.id());
		if (!shouldExpire(latest, now))
			return latest;
		return transitionOrReload(latest.id(), latest.revision(), Set.of(latest.state()),
				State.EXPIRED, null);
	}

	private void cleanupBackup(BackupJob job) throws IOException {
		if (unfinishedBackupJobIds.contains(job.id()))
			return;
		BackupJob latest = stabilize(job);
		if (TERMINAL.contains(latest.state()))
			deletePayload(latest.id());
		Instant retentionCutoff = clock.instant().minus(properties.getMetadataRetention());
		if (TERMINAL.contains(latest.state())
				&& latest.updatedAt().isBefore(retentionCutoff))
			jobs.deleteTerminal(latest.id(), latest.revision(), retentionCutoff);
	}

	private void finishDownload(DownloadClaim claim, DownloadOutcome outcome) throws IOException {
		BackupJob latest = jobs.require(claim.job().id());
		if (latest.state() != State.DOWNLOADING)
			return;
		switch (outcome) {
			case COMPLETED -> {
				BackupJob consumed = transitionOrReload(latest.id(), latest.revision(),
						Set.of(State.DOWNLOADING), State.CONSUMED, null);
				if (consumed.state() == State.CONSUMED)
					deletePayload(consumed.id());
			}
			case CORRUPTED -> transitionOrReload(latest.id(), latest.revision(),
					Set.of(State.DOWNLOADING), State.FAILED, PAYLOAD_MISMATCH_ERROR);
			case INTERRUPTED -> transitionOrReload(latest.id(), latest.revision(),
					Set.of(State.DOWNLOADING), State.READY, null);
		}
	}

	private BackupJob stabilize(BackupJob job) {
		BackupJob reconciled = job.state() == State.READY
				? reconcileReady(job, PayloadVerification.RECORDED_SIZE) : job;
		return expireBackup(reconciled);
	}

	private BackupJob reconcileReady(BackupJob job, PayloadVerification verification) {
		if (job.state() != State.READY)
			return job;
		try {
			if (payloadMatches(job, storage.storedArchivePath(job.id()), verification))
				return job;
		} catch (IOException unreadablePayloadFailsLikeAMissingOne) {
		}
		return markPayloadFailed(job);
	}

	private BackupJob markPayloadFailed(BackupJob job) {
		return transitionOrReload(job.id(), job.revision(), Set.of(State.READY),
				State.FAILED, PAYLOAD_MISMATCH_ERROR);
	}

	private static boolean payloadMatches(BackupJob job, Path archive,
			PayloadVerification verification) throws IOException {
		if (job.archiveBytes() == null
				|| !Files.isRegularFile(archive, LinkOption.NOFOLLOW_LINKS)
				|| Files.size(archive) != job.archiveBytes())
			return false;
		return verification == PayloadVerification.RECORDED_SIZE
				|| digestMatches(job.archiveSha256(), BackupArchiveValidator.hash(archive));
	}

	private static boolean digestMatches(String recorded, String observed) {
		return recorded != null && !recorded.isBlank() && recorded.equals(observed);
	}

	private static String sha256(FileChannel archive) throws IOException {
		MessageDigest digest = BackupArchiveValidator.sha256();
		ByteBuffer buffer = ByteBuffer.allocate(COPY_BUFFER);
		while (archive.read(buffer) >= 0) {
			buffer.flip();
			digest.update(buffer);
			buffer.clear();
		}
		return BackupArchiveValidator.hex(digest.digest());
	}

	private BackupJob transitionOrReload(String id, long revision, Set<State> expected,
			State next, String error) {
		try {
			return jobs.transition(id, revision, expected, next, error);
		} catch (ZfgcConflictException race) {
			return jobs.require(id);
		}
	}

	private void cleanupOrphanDirectories() {
		try {
			Set<String> known = new HashSet<>();
			for (BackupJob job : jobs.list())
				known.add(job.id());
			Instant cutoff = clock.instant().minus(properties.getOrphanGrace());
			for (Path orphan : storage.orphanJobDirectories(known, cutoff)) {
				String id = orphan.getFileName().toString();
				if (jobs.find(id).isEmpty())
					deleteTree(orphan);
			}
			Instant noRestoreStillHoldsItBefore = cutoff.minus(properties.getCommandTimeout());
			for (Path dump : storage.abandonedSafetyDumps(noRestoreStillHoldsItBefore))
				Files.deleteIfExists(dump);
		} catch (IOException | RuntimeException failure) {
			LOG.warn("Unable to clean orphaned backup artifacts", failure);
		}
	}

	private boolean shouldExpire(BackupJob job, Instant now) {
		return switch (job.state()) {
			case READY -> job.expiresAt() != null && now.isAfter(job.expiresAt());
			case CREATING -> !unfinishedBackupJobIds.contains(job.id())
					&& stale(job.updatedAt(), properties.getCreationTimeout(), now);
			case DOWNLOADING -> stale(job.updatedAt(),
					properties.getDownloadClaimTimeout(), now);
			default -> false;
		};
	}

	private static boolean stale(Instant updatedAt, Duration timeout, Instant now) {
		return updatedAt != null && now.isAfter(updatedAt.plus(timeout));
	}

	private static void validateTimeouts(BackupRestoreProperties properties) {
		requirePositive(properties.getCommandTimeout(), "command timeout");
		requirePositive(properties.getCreationTimeout(), "creation timeout");
		requirePositive(properties.getDownloadClaimTimeout(), "download claim timeout");
		requirePositive(properties.getOrphanGrace(), "orphan grace");
		if (properties.getCreationTimeout().compareTo(
				properties.getCommandTimeout().multipliedBy(DUMP_AND_ARCHIVE_PASSES)) < 0)
			throw new IllegalArgumentException("backup creation timeout must be at least " + DUMP_AND_ARCHIVE_PASSES
					+ "x the database command timeout");
	}

	private static void requirePositive(Duration value, String name) {
		if (value == null || value.isZero() || value.isNegative())
			throw new IllegalArgumentException(name + " must be positive.");
	}

	private void deletePayload(String id) throws IOException {
		deleteTree(storage.jobPayload(id));
	}

	private AdminBackupResponse backupResponse(BackupJob job) {
		return new AdminBackupResponse(job.id(), job.state().name(),
				job.createdAt(), job.expiresAt(), job.archiveBytes(),
				job.archiveSha256(), job.installerCompatible(),
				job.installerAnchorAdministratorId(), job.state() == State.READY,
				job.lastError());
	}

	private static String applicationVersion() {
		String version = BackupRestoreService.class.getPackage().getImplementationVersion();
		return version == null ? "development" : version;
	}

	@PreDestroy
	void shutdown() {
		backupExecutor.shutdownNow();
	}

	private enum PayloadVerification {
		RECORDED_SIZE,
		RECORDED_DIGEST
	}

	private enum DownloadOutcome {
		COMPLETED,
		INTERRUPTED,
		CORRUPTED
	}

	public record DownloadClaim(BackupJob job, Path archive) {}

	public ArchiveInstallability classifyInstallability(Path contentRoot) {
		try {
			List<Integer> anchors = userDataProvider.siteAdministratorIdsWithUsableCredentials();
			if (anchors.size() != 1 || anchors.get(0) == null || anchors.get(0) <= 0)
				return ArchiveInstallability.notInstallable("site administrator anchor is not unique");
			int anchor = anchors.get(0);
			if (userDataProvider.hasUsableCredentialsOutside(anchor))
				return ArchiveInstallability.notInstallable(
						"usable authentication state exists outside the anchor administrator");
			verifyContentResourcesOnDisk(contentRoot);
			return ArchiveInstallability.installable(anchor);
		} catch (IOException | RuntimeException unprovable) {
			LOG.warn("archive installability proof failed", unprovable);
			return ArchiveInstallability.notInstallable("installability proof failed");
		}
	}

	private void verifyContentResourcesOnDisk(Path contentRoot) throws IOException {
		Path normalizedRoot = contentRoot.toAbsolutePath().normalize();
		if (!Files.isDirectory(normalizedRoot, LinkOption.NOFOLLOW_LINKS))
			throw new IOException("content root is unavailable: " + normalizedRoot);
		ContentResourceDboExample everyResource = new ContentResourceDboExample();
		everyResource.setOrderByClause("content_resource_id");
		for (ContentResourceDbo resource : contentResourceDao.get(everyResource)) {
			Path file = contentResourcePath(normalizedRoot, resource);
			requireNoSymbolicLink(normalizedRoot, file);
			if (!Files.isRegularFile(file, LinkOption.NOFOLLOW_LINKS))
				throw new IOException("content resource is missing: " + file);
			Long expectedSize = resource.getFileSize() == null ? null
					: resource.getFileSize().longValue();
			if (expectedSize != null && expectedSize.longValue() != Files.size(file))
				throw new IOException("content resource size differs: " + file);
		}
	}

	private static Path contentResourcePath(Path root, ContentResourceDbo resource)
			throws IOException {
		String storageDirectory = resource.getStorageDir();
		String identifier = Integer.toString(resource.getContentResourceId());
		Path candidate;
		if (storageDirectory == null || storageDirectory.isBlank())
			candidate = root.resolve("images").resolve(identifier);
		else {
			if (resource.getFilename() == null || resource.getFilename().isBlank())
				throw new IOException("content resource filename is missing: " + identifier);
			candidate = root.resolve(storageDirectory).resolve(identifier)
					.resolve(resource.getFilename());
		}
		Path normalized = candidate.toAbsolutePath().normalize();
		if (!normalized.startsWith(root))
			throw new IOException("content resource path escapes the content root: " + normalized);
		return normalized;
	}

	private static void requireNoSymbolicLink(Path root, Path file) throws IOException {
		Path current = root;
		for (Path component : root.relativize(file)) {
			current = current.resolve(component);
			if (Files.isSymbolicLink(current))
				throw new IOException("content resource path contains a symbolic link: " + current);
		}
	}

	public record ArchiveInstallability(boolean compatible, Integer anchorAdministratorId,
			String reason) {
		static ArchiveInstallability installable(int anchor) {
			return new ArchiveInstallability(true, anchor, null);
		}

		static ArchiveInstallability notInstallable(String reason) {
			return new ArchiveInstallability(false, null, reason);
		}
	}
}
