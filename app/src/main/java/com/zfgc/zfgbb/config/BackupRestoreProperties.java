package com.zfgc.zfgbb.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "zfgbb.operations")
public class BackupRestoreProperties {
	private String pgDump = "pg_dump";
	private String pgRestore = "pg_restore";
	private Duration commandTimeout = Duration.ofMinutes(30);
	private Duration mutationDrainTimeout = Duration.ofSeconds(30);
	private Duration backupTtl = Duration.ofHours(24);
	private Duration creationTimeout = Duration.ofHours(4);
	private Duration downloadClaimTimeout = Duration.ofHours(6);
	private Duration orphanGrace = Duration.ofHours(1);
	private Duration metadataRetention = Duration.ofDays(7);
	private long storageMarginBytes = 512L * 1024 * 1024;
	private long compressedBytes = 2L * 1024 * 1024 * 1024;
	private long expandedBytes = 8L * 1024 * 1024 * 1024;
	private long manifestBytes = 1024 * 1024;
	private long dumpBytes = 4L * 1024 * 1024 * 1024;
	private long contentBytes = 4L * 1024 * 1024 * 1024;
	private int entries = 100_000;
	private int pathBytes = 1024;
}
