package com.zfgc.zfgbb.migrator.jobs;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Job {
	private UUID id;
	private JobType type;
	private JobState state;
	private Instant submittedAt;
	private Instant startedAt;
	private Instant finishedAt;
	private String error;

	@JsonIgnore private String smfJdbcUrl;
	@JsonIgnore private String smfUser;
	@JsonIgnore private String smfPassword;
	@JsonIgnore private String smfTablePrefix;
	@JsonIgnore private String smfLegacyHost;
	@JsonIgnore private String appBaseUrl;
	@JsonIgnore private String attachmentsSourcePath;
	@JsonIgnore private String attachmentsTargetPath;
	@JsonIgnore private boolean force;
}
