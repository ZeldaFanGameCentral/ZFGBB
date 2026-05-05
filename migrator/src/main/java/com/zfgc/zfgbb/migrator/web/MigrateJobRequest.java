package com.zfgc.zfgbb.migrator.web;

import com.zfgc.zfgbb.migrator.jobs.JobType;

import lombok.Data;

@Data
public class MigrateJobRequest {
	private JobType type;
	private String smfHost;
	private Integer smfPort = 3306;
	private String smfDatabase;
	private String smfUser;
	private String smfPassword;
	private String smfTablePrefix;
	private String smfLegacyHost;
	private String appBaseUrl;
	private String attachmentsSourcePath;
	private String attachmentsTargetPath;
	private String avatarsSourcePath;
	private Boolean force;
}
