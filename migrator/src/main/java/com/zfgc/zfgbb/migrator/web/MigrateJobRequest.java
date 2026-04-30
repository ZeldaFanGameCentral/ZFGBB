package com.zfgc.zfgbb.migrator.web;

import com.zfgc.zfgbb.migrator.jobs.JobType;

import lombok.Data;

@Data
public class MigrateJobRequest {
	private JobType type;
}
