package com.zfgc.zfgbb.migrator.jobs;

public record SmfConnectionParams(
		String jdbcUrl,
		String username,
		String password,
		String smfTablePrefix,
		String smfLegacyHost,
		String appBaseUrl,
		String attachmentsSourcePath,
		String attachmentsTargetPath,
		String avatarsSourcePath,
		boolean force) {
}
