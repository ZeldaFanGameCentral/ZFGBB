package com.zfgc.zfgbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import com.zfgc.zfgbb.migrator.jobs.JobService;
import com.zfgc.zfgbb.migrator.jobs.JobState;
import com.zfgc.zfgbb.migrator.jobs.JobType;

@SpringBootTest
@Testcontainers
class MigratorAutoConfigEnabledTest {

	@Container
	static PostgreSQLContainer pg = new PostgreSQLContainer("postgres:16")
			.withEnv("PGDATA", "/tmp/postgres-data");

	@DynamicPropertySource
	static void datasource(DynamicPropertyRegistry r) {
		r.add("spring.datasource.url", pg::getJdbcUrl);
		r.add("spring.datasource.username", pg::getUsername);
		r.add("spring.datasource.password", pg::getPassword);
		r.add("zfgbb.migrator.enabled", () -> "true");
		r.add("zfgbb.migrator.smf.datasource.driver-class-name", () -> "org.postgresql.Driver");
		r.add("zfgbb.migrator.smf.datasource.jdbc-url", pg::getJdbcUrl);
		r.add("zfgbb.migrator.smf.datasource.username", pg::getUsername);
		r.add("zfgbb.migrator.smf.datasource.password", pg::getPassword);
	}

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private JobService jobService;

	@Autowired
	@Qualifier("smfDataSource")
	private DataSource smfDataSource;

	@Test
	void migrator_autoconfig_wires_when_enabled() {
		assertNotNull(jobService, "JobService should be present when zfgbb.migrator.enabled=true");
		assertNotNull(smfDataSource, "Secondary smfDataSource should be defined");
		assertTrue(ctx.containsBean("smfSqlSessionTemplate"), "smfSqlSessionTemplate should be defined");

		var jobs = jobService.submit(JobType.CATEGORIES);
		assertEquals(1, jobs.size());
		var job = jobs.get(0);
		assertNotNull(job.getId());
		var state = job.getState();
		assertTrue(state == JobState.QUEUED || state == JobState.RUNNING,
				"Newly submitted job should start in QUEUED or RUNNING; was " + state);
		jobService.cancel(job.getId());
	}
}
