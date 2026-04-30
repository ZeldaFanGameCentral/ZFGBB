package com.zfgc.zfgbb.migrator.jobs;

import java.time.Instant;
import java.util.UUID;

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
}
