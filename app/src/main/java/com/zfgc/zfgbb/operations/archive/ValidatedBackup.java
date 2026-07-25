package com.zfgc.zfgbb.operations.archive;

import java.util.Map;

public record ValidatedBackup(BackupManifest manifest, String archiveSha256,
		long compressedBytes, long expandedBytes, Map<String, BackupManifest.Entry> entries) {}
