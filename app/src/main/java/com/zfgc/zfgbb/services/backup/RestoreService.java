package com.zfgc.zfgbb.services.backup;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.zfgc.zfgbb.services.contentstore.ContentRoot;
import static com.zfgc.zfgbb.operations.archive.OperationFiles.canonicalId;
import static com.zfgc.zfgbb.operations.archive.OperationFiles.deleteTree;
import static com.zfgc.zfgbb.services.backup.OperationStorageService.setPrivateFile;

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
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.config.BackupRestoreProperties;
import com.zfgc.zfgbb.operations.archive.BackupArchiveExtractor;
import com.zfgc.zfgbb.operations.archive.BackupArchiveWriter;
import com.zfgc.zfgbb.operations.archive.BackupManifest;
import com.zfgc.zfgbb.operations.archive.InvalidBackupException;
import com.zfgc.zfgbb.operations.postgres.PostgresBackupTool;

import jakarta.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestoreService {
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

	@PostConstruct
	void reconcileInterruptedContentSwapsOnStartup() {
		try {
			reconcileInterruptedContentSwaps(content.activeContentRoot());
		} catch (IOException | RuntimeException unreconciled) {
			log.error("interrupted content restore could not be reconciled; restores are blocked",
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
				throw new IllegalStateException("content swap owner lock is held: "
						+ live.resolve(OWNER_LOCK_NAME));
			stranded = preRestoreDirectories(live);
		} catch (IOException unreadable) {
			throw new IllegalStateException("live content root unreadable: " + live, unreadable);
		}
		if (stranded.isEmpty())
			return;
		throw new IllegalStateException("interrupted restore left pre-restore content at " + stranded);
	}

	boolean reconcileInterruptedContentSwaps(Path live) throws IOException {
		if (!Files.isDirectory(live, LinkOption.NOFOLLOW_LINKS))
			return true;
		Optional<SwapOwnership> ownership = SwapOwnership.tryAcquire(live);
		if (ownership.isEmpty()) {
			log.warn("content swap owner lock held, leaving the live content root alone: {}",
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
				log.error("swap recorded by {} not reconciled; pre-restore content kept at {}", journal.file(),
						journal.saved(), unreconciled);
			}
		}
		for (Path debris : swapArtifacts(live, INCOMING_PREFIX, "")) {
			if (journalled(live, debris, INCOMING_PREFIX))
				continue;
			log.warn("Removing {}, an unjournaled copy of restored content left inside the live "
					+ "content root.", debris);
			removeContentTree(debris);
		}
		for (Path draft : swapArtifacts(live, PRE_RESTORE_PREFIX, JOURNAL_DRAFT_SUFFIX))
			Files.deleteIfExists(draft);
		for (Path stranded : preRestoreDirectories(live))
			if (!journalled(live, stranded, PRE_RESTORE_PREFIX))
				log.error("unjournaled pre-restore content at {}; restores are blocked until it is returned to {}",
						stranded, live);
	}

	private static boolean journalled(Path live, Path artifact, String prefix) {
		return Files.exists(new SwapJournal(live, swapId(artifact, prefix, "")).file(),
				LinkOption.NOFOLLOW_LINKS);
	}

	private void reconcile(SwapJournal journal) throws IOException {
		Optional<SwapPhase> phase = journal.phase();
		if (phase.isEmpty()) {
			log.error("content swap journal {} is unreadable; pre-restore content kept at {}", journal.file(),
					journal.saved());
			return;
		}
		switch (phase.get()) {
			case STAGING, DRAINING_ORIGINALS -> returnOriginals(journal);
			case PUBLISHING -> {
				if (!Files.isDirectory(journal.saved(), LinkOption.NOFOLLOW_LINKS)) {
					log.error("journal {} records a half-published swap and the pre-restore content at {} is gone; keeping the published children",
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
			log.error("swap recorded by {} not finished; pre-restore content at {}, retried next startup",
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
			throw new IllegalStateException("flyway could not refresh schema objects after the "
					+ "restore; safety dump: " + safetyDump, stale);
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
					log.error("restore not recorded as committed: journal {} not advanced; pre-restore content kept at {}",
							journal.file(), journal.saved(), unrecordable);
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
				log.warn("unable to remove the content swap owner lock {}", marker, unreleasable);
			}
		}

		private static void closeQuietly(FileChannel channel, Path marker) {
			try {
				channel.close();
			} catch (IOException unclosable) {
				log.warn("Unable to close the content swap owner lock {}", marker, unclosable);
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
			log.warn("unable to flush the directory entries of {}",
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
			log.warn("Unable to remove the pre-restore safety dump {}", file, undeletable);
		}
	}

	private static void deleteTreeQuietly(Path root) {
		try {
			deleteTree(root);
		} catch (IOException undeletable) {
			log.warn("Unable to remove restore staging directory {}", root, undeletable);
		}
	}
}
