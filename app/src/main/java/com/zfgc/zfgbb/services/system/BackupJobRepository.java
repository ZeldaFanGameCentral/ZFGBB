package com.zfgc.zfgbb.services.system;

import static com.zfgc.zfgbb.services.system.OperationStorageService.canonicalId;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.exception.ZfgcConflictException;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.mappers.custom.BackupJobCustomMapper;
import com.zfgc.zfgbb.model.system.BackupJob;
import com.zfgc.zfgbb.model.system.BackupJob.State;

@Repository
public class BackupJobRepository {

	private final BackupJobCustomMapper backupJobCustomMapper;

	private final Clock clock;

	public BackupJobRepository(BackupJobCustomMapper backupJobCustomMapper, Clock clock) {
		this.backupJobCustomMapper = backupJobCustomMapper;
		this.clock = clock;
	}

	public BackupJob create(Integer creatorUserId, Instant now, Instant expiresAt) {
		String id = UUID.randomUUID().toString();
		backupJobCustomMapper.insertJob(id, creatorUserId, now.atOffset(ZoneOffset.UTC),
				expiresAt.atOffset(ZoneOffset.UTC));
		return require(id);
	}

	public Optional<BackupJob> find(String id) {
		if (!canonicalId(id))
			return Optional.empty();
		return Optional.ofNullable(backupJobCustomMapper.findById(id));
	}

	public BackupJob require(String id) {
		return find(id).orElseThrow(ZfgcNotFoundException::new);
	}

	public List<BackupJob> list() {
		return backupJobCustomMapper.listJobs();
	}

	public BackupJob transition(String id, long revision, Set<State> expected, State next,
			String error) {
		if (expected == null || expected.isEmpty())
			throw new IllegalArgumentException("expected backup states are required");
		Set<String> stateNames = expected.stream().map(State::name).collect(Collectors.toSet());
		int updated = backupJobCustomMapper.transitionState(id, revision, stateNames, next.name(), error, now());
		if (updated != 1)
			throw new ZfgcConflictException("Backup state changed concurrently.");
		return require(id);
	}

	public BackupJob complete(String id, long revision, long archiveBytes,
			String archiveSha256, boolean installerCompatible,
			Integer anchorAdministratorId) {
		int updated = backupJobCustomMapper.completeJob(id, revision, archiveBytes, archiveSha256,
				installerCompatible, anchorAdministratorId, now());
		if (updated != 1)
			throw new ZfgcConflictException("Backup state changed concurrently.");
		return require(id);
	}

	public boolean deleteTerminal(String id, long revision, Instant updatedBefore) {
		return backupJobCustomMapper.deleteTerminal(id, revision, updatedBefore.atOffset(ZoneOffset.UTC)) == 1;
	}

	private OffsetDateTime now() {
		return clock.instant().atOffset(ZoneOffset.UTC);
	}
}
