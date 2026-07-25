package com.zfgc.zfgbb.operations.archive;

import java.time.Instant;
import java.util.List;

public record BackupManifest(
		int formatVersion,
		String application,
		String applicationVersion,
		String flywayVersion,
		int postgresqlMajor,
		String dumpToolVersion,
		boolean installerCompatible,
		int installerAnchorAdministratorId,
		Instant createdAt,
		List<Entry> entries) {

	public record Entry(String type, String path, long length, String sha256) {}
}
