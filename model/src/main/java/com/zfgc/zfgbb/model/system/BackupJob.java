package com.zfgc.zfgbb.model.system;

import java.time.Instant;

public record BackupJob(
		String id,
		State state,
		long revision,
		Instant createdAt,
		Instant updatedAt,
		Instant expiresAt,
		Integer creatorUserId,
		Long archiveBytes,
		String archiveSha256,
		Boolean installerCompatible,
		Integer installerAnchorAdministratorId,
		String lastError) {

	public enum State {
		CREATING,
		READY,
		DOWNLOADING,
		CONSUMED,
		EXPIRED,
		FAILED
	}
}
