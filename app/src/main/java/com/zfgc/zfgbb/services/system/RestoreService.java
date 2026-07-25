package com.zfgc.zfgbb.services.system;

import static com.zfgc.zfgbb.services.system.OperationStorageService.canonicalId;
import static com.zfgc.zfgbb.operations.archive.OperationFiles.deleteTree;
import static com.zfgc.zfgbb.services.system.OperationStorageService.setPrivateFile;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.config.BackupRestoreProperties;
import com.zfgc.zfgbb.operations.archive.BackupArchiveExtractor;
import com.zfgc.zfgbb.operations.archive.BackupArchiveWriter;
import com.zfgc.zfgbb.operations.archive.BackupManifest;
import com.zfgc.zfgbb.operations.archive.InvalidBackupException;
import com.zfgc.zfgbb.operations.postgres.PostgresBackupTool;

import jakarta.annotation.PostConstruct;

@Service
public class RestoreService {
	private static final Logger LOG = LoggerFactory.getLogger(RestoreService.class);
	private static final String INCOMING_PREFIX = ".incoming-";
	private static final String PRE_RESTORE_PREFIX = ".pre-restore-";
	private static final String JOURNAL_SUFFIX = ".journal";
	private static final String JOURNAL_DRAFT_SUFFIX = ".journal.tmp";
	private static final String JOURNAL_MAGIC = "ZFGBB-RESTORE-SWAP 1";
	private static final String OWNER_LOCK_NAME = PRE_RESTORE_PREFIX + "owner.lock";

	private final OperationStorageService storage;
	private final BackupRestoreProperties properties;
	private final PostgresBackupTool postgres;
	private final ContentRoot content;
	private final Flyway flyway;

	public RestoreService(OperationStorageService storage, BackupRestoreProperties properties,
			PostgresBackupTool postgres, ContentRoot content, Flyway flyway) {
		this.storage = storage;
		this.properties = properties;
		this.postgres = postgres;
		this.content = content;
		this.flyway = flyway;
	}

	@PostConstruct
	void reconcileInterruptedContentSwapsOnStartup() {
		try {
			reconcileInterruptedContentSwaps(content.activeContentRoot());
		} catch (IOException | RuntimeException unreconciled) {
			LOG.error("Unable to reconcile an interrupted content restore in the live content "
					+ "root. The content tree may be a mixture of restored and pre-restore files, "
					+ "and further restores will be refused until it is resolved by hand.",
					unreconciled);
		}
	}

	public BackupManifest restoreArchiveWithoutMaintenanceLease(Path archive) {
		return restoreArchiveWithoutMaintenanceLease(archive, manifest -> {});
	}

	public BackupManifest restoreArchiveWithoutMaintenanceLease(Path archive,
			Consumer<BackupManifest> approval) {
		Path staging = null;
		Path safetyDump = null;
		Path live = content.activeContentRoot();
		requireReconciledContentRoot(live);
		try {
			staging = storage.restoreStagingDirectory();
			BackupArchiveExtractor.ExtractedBackup extracted =
					new BackupArchiveExtractor(storage.limits()).extract(archive, staging);
			setPrivateFile(extracted.databaseDump());
			BackupManifest manifest = extracted.validated().manifest();
			postgres.requireArchiveSchemaMatchesApplication(manifest.flywayVersion());
			approval.accept(manifest);
			safetyDump = dumpForRollback();
			ContentSwap swap = swapContent(extracted.contentRoot(), live);
			try {
				postgres.restore(extracted.databaseDump());
			} catch (IOException | RuntimeException restoreFailed) {
				swap.rollback();
				throw restoreFailed;
			}
			swap.commit();
			refreshRepeatableSchemaObjects(safetyDump);
			deleteQuietly(safetyDump);
			return manifest;
		} catch (InvalidBackupException invalid) {
			throw new IllegalStateException("Backup archive is not valid: " + invalid.getMessage(),
					invalid);
		} catch (IOException failure) {
			throw new IllegalStateException(rollbackAdvice(safetyDump), failure);
		} finally {
			if (staging != null)
				deleteTreeQuietly(staging);
		}
	}

	private void requireReconciledContentRoot(Path live) {
		List<Path> stranded;
		try {
			if (!reconcileInterruptedContentSwaps(live))
				throw new IllegalStateException("Another process holds the content swap owner lock "
						+ live.resolve(OWNER_LOCK_NAME) + ", so a content swap is already running "
						+ "against this content root. Wait for it to finish, or remove that file if "
						+ "no other process is alive, before restoring again.");
			stranded = preRestoreDirectories(live);
		} catch (IOException unreadable) {
			throw new IllegalStateException("Unable to inspect the live content root at " + live
					+ " for an interrupted restore.", unreadable);
		}
		if (stranded.isEmpty())
			return;
		throw new IllegalStateException("The live content root still holds pre-restore content "
				+ "left by an interrupted restore: " + stranded + ". That directory is the only "
				+ "copy of the content it holds, so it is never removed automatically. Move its "
				+ "children back into " + live + " and remove it before restoring again.");
	}

	boolean reconcileInterruptedContentSwaps(Path live) throws IOException {
		if (!Files.isDirectory(live, LinkOption.NOFOLLOW_LINKS))
			return true;
		Optional<SwapOwnership> ownership = SwapOwnership.tryAcquire(live);
		if (ownership.isEmpty()) {
			LOG.warn("Another process holds the content swap owner lock {}, so this instance is "
					+ "leaving the live content root alone. Reconciling a swap that a live process "
					+ "is still performing would move its pre-restore originals back over the "
					+ "content it is publishing. This deployment runs a single API replica, so the "
					+ "only expected holder is an older instance that has not exited yet.",
					live.resolve(OWNER_LOCK_NAME));
			return false;
		}
		try (SwapOwnership owned = ownership.get()) {
			reconcileOwnedContentSwaps(live);
		}
		return true;
	}

	void removeContentTree(Path tree) throws IOException {
		deleteTree(tree);
	}

	private void reconcileOwnedContentSwaps(Path live) throws IOException {
		for (SwapJournal journal : journals(live)) {
			try {
				reconcile(journal);
			} catch (IOException unreconciled) {
				LOG.error("Unable to reconcile the interrupted content restore recorded by {}. "
						+ "The pre-restore content at {} is being kept.", journal.file(),
						journal.saved(), unreconciled);
			}
		}
		for (Path debris : swapArtifacts(live, INCOMING_PREFIX, "")) {
			if (journalled(live, debris, INCOMING_PREFIX))
				continue;
			LOG.warn("Removing {}, an unjournaled copy of restored content left inside the live "
					+ "content root.", debris);
			removeContentTree(debris);
		}
		for (Path draft : swapArtifacts(live, PRE_RESTORE_PREFIX, JOURNAL_DRAFT_SUFFIX))
			Files.deleteIfExists(draft);
		for (Path stranded : preRestoreDirectories(live))
			if (!journalled(live, stranded, PRE_RESTORE_PREFIX))
				LOG.error("The live content root holds {}, which is pre-restore content with no "
						+ "swap journal describing it. It may be the only copy of the content it "
						+ "holds, so it will not be removed automatically and restores will be "
						+ "refused until an operator moves its children back into {} and removes "
						+ "it.", stranded, live);
	}

	private static boolean journalled(Path live, Path artifact, String prefix) {
		return Files.exists(new SwapJournal(live, swapId(artifact, prefix, "")).file(),
				LinkOption.NOFOLLOW_LINKS);
	}

	private void reconcile(SwapJournal journal) throws IOException {
		Optional<SwapPhase> phase = journal.phase();
		if (phase.isEmpty()) {
			LOG.error("The content swap journal {} is unreadable, so the pre-restore content at {} "
					+ "cannot be classified as debris and is being kept.", journal.file(),
					journal.saved());
			return;
		}
		switch (phase.get()) {
			case STAGING, DRAINING_ORIGINALS -> returnOriginals(journal);
			case PUBLISHING -> {
				if (!Files.isDirectory(journal.saved(), LinkOption.NOFOLLOW_LINKS)) {
					LOG.error("The content swap journal {} records a half-published swap but the "
							+ "pre-restore content at {} is gone, so the published children are the "
							+ "only content left and are being kept. Resolve this by hand; the next "
							+ "restore is blocked until the journal is removed.",
							journal.file(), journal.saved());
					return;
				}
				discardPublishedChildren(journal.live());
				journal.write(SwapPhase.DRAINING_ORIGINALS);
				returnOriginals(journal);
			}
			case DATABASE_RESTORED -> {
				removeContentTree(journal.incoming());
				removeContentTree(journal.saved());
				journal.discard();
			}
		}
	}

	private void reconcileQuietly(SwapJournal journal) {
		try {
			reconcile(journal);
		} catch (IOException unreconciled) {
			LOG.error("Unable to finish the content swap recorded by {}. The pre-restore content "
					+ "is at {}; the journal is being kept so the next startup retries this.",
					journal.file(), journal.saved(), unreconciled);
		}
	}

	private void returnOriginals(SwapJournal journal) throws IOException {
		removeContentTree(journal.incoming());
		returnSavedChildren(journal.live(), journal.saved());
		journal.discard();
	}

	private void refreshRepeatableSchemaObjects(Path safetyDump) {
		try {
			flyway.migrate();
		} catch (RuntimeException stale) {
			throw new IllegalStateException("The archive was restored, but its function and "
					+ "trigger definitions could not be brought back up to the application's "
					+ "versions. The database is running the archive's schema objects until "
					+ "Flyway succeeds; a pre-restore safety dump is at " + safetyDump + ".",
					stale);
		}
	}

	private Path dumpForRollback() throws IOException {
		PostgresBackupTool.DatabaseMetadata database = postgres.metadata();
		storage.requireFreeSpace(database.databaseBytes(), properties.getStorageMarginBytes());
		Path dump = storage.safetyDumpPath();
		postgres.dump(dump, database);
		setPrivateFile(dump);
		return dump;
	}

	private static String rollbackAdvice(Path safetyDump) {
		return safetyDump == null
				? "Unable to restore the backup archive."
				: "Unable to restore the backup archive. The content tree was rolled back and the "
						+ "database transaction was discarded; a pre-restore safety dump is at "
						+ safetyDump + ".";
	}

	private ContentSwap swapContent(Path source, Path live) throws IOException {
		storage.requireFreeContentSpace(storage.contentBytes(source),
				properties.getStorageMarginBytes());
		SwapJournal journal = new SwapJournal(live, UUID.randomUUID().toString());
		SwapOwnership ownership = SwapOwnership.tryAcquire(live)
				.orElseThrow(() -> new IOException("another process holds the content swap owner "
						+ "lock " + live.resolve(OWNER_LOCK_NAME)));
		try {
			journal.write(SwapPhase.STAGING);
		} catch (IOException unrecordable) {
			ownership.close();
			throw unrecordable;
		}
		ContentSwap swap = new ContentSwap(journal, ownership);
		try {
			copyTree(source, journal.incoming());
			journal.write(SwapPhase.DRAINING_ORIGINALS);
			Files.createDirectories(journal.saved());
			moveChildren(live, journal.saved(), journal.artifactNames());
			journal.write(SwapPhase.PUBLISHING);
			moveChildren(journal.incoming(), live, Set.of());
			Files.deleteIfExists(journal.incoming());
		} catch (IOException unswappable) {
			swap.rollback();
			throw unswappable;
		}
		return swap;
	}

	private final class ContentSwap {
		private final SwapJournal journal;
		private final SwapOwnership ownership;

		private ContentSwap(SwapJournal journal, SwapOwnership ownership) {
			this.journal = journal;
			this.ownership = ownership;
		}

		void commit() {
			try (SwapOwnership owned = ownership) {
				try {
					journal.write(SwapPhase.DATABASE_RESTORED);
				} catch (IOException unrecordable) {
					LOG.error("The database was restored, but the content swap journal {} could "
							+ "not be advanced, so this restore cannot be recorded as committed. "
							+ "The pre-restore content at {} is being kept and will be moved back "
							+ "over the restored content if this process stops before an operator "
							+ "intervenes: it is the only copy of itself, while the archive can be "
							+ "restored again.", journal.file(), journal.saved(), unrecordable);
					return;
				}
				reconcileQuietly(journal);
			}
		}

		void rollback() {
			try (SwapOwnership owned = ownership) {
				reconcileQuietly(journal);
			}
		}
	}

	private record SwapJournal(Path live, String swapId) {
		Path file() {
			return live.resolve(PRE_RESTORE_PREFIX + swapId + JOURNAL_SUFFIX);
		}

		Path draft() {
			return live.resolve(PRE_RESTORE_PREFIX + swapId + JOURNAL_DRAFT_SUFFIX);
		}

		Path incoming() {
			return live.resolve(INCOMING_PREFIX + swapId);
		}

		Path saved() {
			return live.resolve(PRE_RESTORE_PREFIX + swapId);
		}

		Set<String> artifactNames() {
			return Set.of(name(incoming()), name(saved()), name(file()), name(draft()),
					OWNER_LOCK_NAME);
		}

		void write(SwapPhase phase) throws IOException {
			Files.write(draft(), (JOURNAL_MAGIC + "\n" + phase.name() + "\n")
					.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE,
					StandardOpenOption.SYNC);
			setPrivateFile(draft());
			BackupArchiveWriter.moveAtomically(draft(), file());
			flushDirectoryEntries(live);
		}

		Optional<SwapPhase> phase() {
			try {
				List<String> recorded = Files.readAllLines(file(), StandardCharsets.UTF_8);
				if (recorded.size() < 2 || !JOURNAL_MAGIC.equals(recorded.get(0)))
					return Optional.empty();
				return Optional.of(SwapPhase.valueOf(recorded.get(1).trim()));
			} catch (IOException | IllegalArgumentException unreadable) {
				return Optional.empty();
			}
		}

		void discard() throws IOException {
			Files.deleteIfExists(draft());
			Files.deleteIfExists(file());
		}

		private static String name(Path path) {
			return path.getFileName().toString();
		}
	}

	private record SwapOwnership(Path marker, FileChannel channel) implements AutoCloseable {
		static Optional<SwapOwnership> tryAcquire(Path live) throws IOException {
			Path marker = live.resolve(OWNER_LOCK_NAME);
			FileChannel channel = FileChannel.open(marker, StandardOpenOption.CREATE,
					StandardOpenOption.WRITE);
			FileLock lock;
			try {
				setPrivateFile(marker);
				lock = channel.tryLock();
			} catch (OverlappingFileLockException heldByThisJvm) {
				lock = null;
			} catch (IOException | RuntimeException unlockable) {
				closeQuietly(channel, marker);
				throw unlockable;
			}
			if (lock == null || !Files.exists(marker, LinkOption.NOFOLLOW_LINKS)) {
				closeQuietly(channel, marker);
				return Optional.empty();
			}
			return Optional.of(new SwapOwnership(marker, channel));
		}

		@Override
		public void close() {
			try (FileChannel released = channel) {
				Files.deleteIfExists(marker);
			} catch (IOException unreleasable) {
				LOG.warn("Unable to remove the content swap owner lock {}. It will be reused by "
						+ "the next swap, so this leaves no swap owned; only the file itself is "
						+ "left behind.", marker, unreleasable);
			}
		}

		private static void closeQuietly(FileChannel channel, Path marker) {
			try {
				channel.close();
			} catch (IOException unclosable) {
				LOG.warn("Unable to close the content swap owner lock {}", marker, unclosable);
			}
		}
	}

	private enum SwapPhase {
		STAGING,
		DRAINING_ORIGINALS,
		PUBLISHING,
		DATABASE_RESTORED
	}

	private static void flushDirectoryEntries(Path directory) {
		try (FileChannel entries = FileChannel.open(directory, StandardOpenOption.READ)) {
			entries.force(true);
		} catch (IOException | RuntimeException unflushable) {
			LOG.warn("Unable to flush the directory entries of {} to stable storage, so a content "
					+ "swap journal phase advance survives a process crash but is only as durable "
					+ "across a machine crash as this filesystem's own rename ordering.",
					directory, unflushable);
		}
	}

	private static List<SwapJournal> journals(Path live) throws IOException {
		List<SwapJournal> found = new ArrayList<>();
		for (Path artifact : swapArtifacts(live, PRE_RESTORE_PREFIX, JOURNAL_SUFFIX))
			found.add(new SwapJournal(live, swapId(artifact, PRE_RESTORE_PREFIX, JOURNAL_SUFFIX)));
		return found;
	}

	private static List<Path> preRestoreDirectories(Path live) throws IOException {
		return swapArtifacts(live, PRE_RESTORE_PREFIX, "");
	}

	private static List<Path> swapArtifacts(Path live, String prefix, String suffix)
			throws IOException {
		if (!Files.isDirectory(live, LinkOption.NOFOLLOW_LINKS))
			return List.of();
		List<Path> found = new ArrayList<>();
		for (Path child : children(live)) {
			boolean directory = Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS);
			if (directory != suffix.isEmpty() || swapId(child, prefix, suffix) == null)
				continue;
			found.add(child);
		}
		return found;
	}

	private static String swapId(Path artifact, String prefix, String suffix) {
		String name = artifact.getFileName().toString();
		if (!name.startsWith(prefix) || !name.endsWith(suffix)
				|| name.length() < prefix.length() + suffix.length())
			return null;
		String id = name.substring(prefix.length(), name.length() - suffix.length());
		return canonicalId(id) ? id : null;
	}

	private void discardPublishedChildren(Path live) throws IOException {
		for (Path child : children(live))
			if (!BackupArchiveWriter.isOperationalArtifact(child.getFileName().toString()))
				removeContentTree(child);
	}

	private static void returnSavedChildren(Path live, Path saved) throws IOException {
		if (!Files.isDirectory(saved, LinkOption.NOFOLLOW_LINKS))
			return;
		moveChildren(saved, live, Set.of());
		Files.deleteIfExists(saved);
	}

	private static void moveChildren(Path from, Path to, Set<String> retained) throws IOException {
		for (Path child : children(from)) {
			String name = child.getFileName().toString();
			if (retained.contains(name))
				continue;
			Files.move(child, to.resolve(name), StandardCopyOption.REPLACE_EXISTING);
		}
	}

	private static List<Path> children(Path directory) throws IOException {
		try (var entries = Files.list(directory)) {
			return entries.sorted(Comparator.naturalOrder()).toList();
		}
	}

	private static void copyTree(Path source, Path destination) throws IOException {
		Path from = source.toAbsolutePath().normalize();
		Path to = destination.toAbsolutePath().normalize();
		Files.createDirectories(to);
		try (var paths = Files.walk(from)) {
			for (Path path : paths.sorted(Comparator.naturalOrder()).toList()) {
				Path relative = from.relativize(path);
				if (relative.getNameCount() > 0 && BackupArchiveWriter.isOperationalArtifact(
						relative.getName(0).toString()))
					continue;
				Path target = to.resolve(relative.toString()).normalize();
				if (!target.startsWith(to))
					throw new IOException("restored content escaped the content root");
				if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS))
					Files.createDirectories(target);
				else
					Files.copy(path, target, StandardCopyOption.REPLACE_EXISTING);
			}
		}
	}

	private static void deleteQuietly(Path file) {
		try {
			Files.deleteIfExists(file);
		} catch (IOException undeletable) {
			LOG.warn("Unable to remove the pre-restore safety dump {}", file, undeletable);
		}
	}

	private static void deleteTreeQuietly(Path root) {
		try {
			deleteTree(root);
		} catch (IOException undeletable) {
			LOG.warn("Unable to remove restore staging directory {}", root, undeletable);
		}
	}
}
