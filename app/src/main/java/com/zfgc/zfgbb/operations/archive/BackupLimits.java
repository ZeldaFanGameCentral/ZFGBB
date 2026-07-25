package com.zfgc.zfgbb.operations.archive;

public record BackupLimits(
		long compressedBytes,
		long expandedBytes,
		long manifestBytes,
		long dumpBytes,
		long contentBytes,
		int entries,
		int pathBytes) {

	public static BackupLimits defaults() {
		return new BackupLimits(
				2L * 1024 * 1024 * 1024,
				8L * 1024 * 1024 * 1024,
				1024 * 1024,
				4L * 1024 * 1024 * 1024,
				4L * 1024 * 1024 * 1024,
				100_000,
				1024);
	}
}
