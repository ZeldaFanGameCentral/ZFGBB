package com.zfgc.zfgbb.dao.meta;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.BackupJobDbo;
import com.zfgc.zfgbb.dbo.BackupJobDboExample;
import com.zfgc.zfgbb.mappers.BackupJobDboMapper;
import com.zfgc.zfgbb.mappers.custom.BackupJobCustomMapper;
import com.zfgc.zfgbb.model.system.BackupJob;

@Repository
public class BackupJobDao extends KeyedDao<BackupJobDbo, BackupJobDboExample, Object> {

	private final BackupJobCustomMapper backupJobCustomMapper;

	public BackupJobDao(BackupJobDboMapper mapper, BackupJobCustomMapper backupJobCustomMapper) {
		super(mapper);
		this.backupJobCustomMapper = backupJobCustomMapper;
	}

	public int insertJob(String id, Integer creatorUserId, OffsetDateTime now, OffsetDateTime expiresAt) {
		return backupJobCustomMapper.insertJob(id, creatorUserId, now, expiresAt);
	}

	public Optional<BackupJob> findJob(String id) {
		return Optional.ofNullable(backupJobCustomMapper.findById(id));
	}

	public List<BackupJob> listJobs() {
		return backupJobCustomMapper.listJobs();
	}

	public int transitionState(String id, long revision, Set<String> expected, String next, String error,
			OffsetDateTime now) {
		return backupJobCustomMapper.transitionState(id, revision, expected, next, error, now);
	}

	public int completeJob(String id, long revision, long archiveBytes, String archiveSha256,
			boolean installerCompatible, Integer anchorAdministratorId, OffsetDateTime now) {
		return backupJobCustomMapper.completeJob(id, revision, archiveBytes, archiveSha256,
				installerCompatible, anchorAdministratorId, now);
	}

	public int deleteTerminal(String id, long revision, OffsetDateTime updatedBefore) {
		return backupJobCustomMapper.deleteTerminal(id, revision, updatedBefore);
	}
}
