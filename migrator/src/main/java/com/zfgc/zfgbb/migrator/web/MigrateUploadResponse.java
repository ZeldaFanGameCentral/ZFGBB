package com.zfgc.zfgbb.migrator.web;

public record MigrateUploadResponse(
		String uploadId,
		String attachmentsSourcePath,
		String avatarsSourcePath) {
}
