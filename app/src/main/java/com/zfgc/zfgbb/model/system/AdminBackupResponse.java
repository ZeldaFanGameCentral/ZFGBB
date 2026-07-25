package com.zfgc.zfgbb.model.system;

import java.time.Instant;

public record AdminBackupResponse(
		String id,
		String state,
		Instant createdAt,
		Instant expiresAt,
		Long archiveBytes,
		String archiveSha256,
		Boolean installerCompatible,
		Integer installerAnchorAdministratorId,
		boolean downloadReady,
		String error) {
}
