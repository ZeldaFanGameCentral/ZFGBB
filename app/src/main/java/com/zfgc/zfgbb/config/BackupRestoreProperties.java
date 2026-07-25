package com.zfgc.zfgbb.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

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

	public String getPgDump() {
		return pgDump;
	}

	public void setPgDump(String pgDump) {
		this.pgDump = pgDump;
	}

	public String getPgRestore() {
		return pgRestore;
	}

	public void setPgRestore(String pgRestore) {
		this.pgRestore = pgRestore;
	}

	public Duration getCommandTimeout() {
		return commandTimeout;
	}

	public void setCommandTimeout(Duration commandTimeout) {
		this.commandTimeout = commandTimeout;
	}

	public Duration getBackupTtl() {
		return backupTtl;
	}

	public Duration getMutationDrainTimeout() {
		return mutationDrainTimeout;
	}

	public void setMutationDrainTimeout(Duration mutationDrainTimeout) {
		this.mutationDrainTimeout = mutationDrainTimeout;
	}

	public void setBackupTtl(Duration backupTtl) {
		this.backupTtl = backupTtl;
	}

	public Duration getCreationTimeout() {
		return creationTimeout;
	}

	public void setCreationTimeout(Duration creationTimeout) {
		this.creationTimeout = creationTimeout;
	}

	public Duration getDownloadClaimTimeout() {
		return downloadClaimTimeout;
	}

	public void setDownloadClaimTimeout(Duration downloadClaimTimeout) {
		this.downloadClaimTimeout = downloadClaimTimeout;
	}

	public Duration getOrphanGrace() {
		return orphanGrace;
	}

	public void setOrphanGrace(Duration orphanGrace) {
		this.orphanGrace = orphanGrace;
	}

	public Duration getMetadataRetention() {
		return metadataRetention;
	}

	public void setMetadataRetention(Duration metadataRetention) {
		this.metadataRetention = metadataRetention;
	}

	public long getStorageMarginBytes() {
		return storageMarginBytes;
	}

	public void setStorageMarginBytes(long storageMarginBytes) {
		this.storageMarginBytes = storageMarginBytes;
	}

	public long getCompressedBytes() {
		return compressedBytes;
	}

	public void setCompressedBytes(long compressedBytes) {
		this.compressedBytes = compressedBytes;
	}

	public long getExpandedBytes() {
		return expandedBytes;
	}

	public void setExpandedBytes(long expandedBytes) {
		this.expandedBytes = expandedBytes;
	}

	public long getManifestBytes() {
		return manifestBytes;
	}

	public void setManifestBytes(long manifestBytes) {
		this.manifestBytes = manifestBytes;
	}

	public long getDumpBytes() {
		return dumpBytes;
	}

	public void setDumpBytes(long dumpBytes) {
		this.dumpBytes = dumpBytes;
	}

	public long getContentBytes() {
		return contentBytes;
	}

	public void setContentBytes(long contentBytes) {
		this.contentBytes = contentBytes;
	}

	public int getEntries() {
		return entries;
	}

	public void setEntries(int entries) {
		this.entries = entries;
	}

	public int getPathBytes() {
		return pathBytes;
	}

	public void setPathBytes(int pathBytes) {
		this.pathBytes = pathBytes;
	}
}
