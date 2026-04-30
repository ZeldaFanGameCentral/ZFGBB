package com.zfgc.zfgbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;
import org.testcontainers.postgresql.PostgreSQLContainer;

import com.zfgc.zfgbb.migrator.jobs.Job;
import com.zfgc.zfgbb.migrator.jobs.JobService;
import com.zfgc.zfgbb.migrator.jobs.JobState;
import com.zfgc.zfgbb.migrator.jobs.JobType;

@SpringBootTest
@Testcontainers
class MigratorEndToEndTest {

	@Container
	static PostgreSQLContainer pg = new PostgreSQLContainer("postgres:16")
			.withEnv("PGDATA", "/tmp/postgres-data");

	@Container
	static MySQLContainer mysql = new MySQLContainer("mysql:8")
			.withInitScript("smf-fixture.sql");

	@DynamicPropertySource
	static void datasources(DynamicPropertyRegistry r) {
		r.add("spring.datasource.url", pg::getJdbcUrl);
		r.add("spring.datasource.username", pg::getUsername);
		r.add("spring.datasource.password", pg::getPassword);
		r.add("spring.flyway.url", pg::getJdbcUrl);
		r.add("spring.flyway.user", pg::getUsername);
		r.add("spring.flyway.password", pg::getPassword);
		r.add("zfgbb.migrator.enabled", () -> "true");
		r.add("zfgbb.migrator.smf.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
		r.add("zfgbb.migrator.smf.datasource.jdbc-url", mysql::getJdbcUrl);
		r.add("zfgbb.migrator.smf.datasource.username", mysql::getUsername);
		r.add("zfgbb.migrator.smf.datasource.password", mysql::getPassword);
	}

	@Autowired
	private JobService jobService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void categories_job_migrates_smf_rows_into_zfgbb() throws Exception {
		Job job = jobService.submit(JobType.CATEGORIES).get(0);
		assertNotNull(job.getId());

		Job finished = waitForTerminal(job, Duration.ofSeconds(30));
		if (finished.getState() != JobState.COMPLETED) {
			fail("Expected COMPLETED, got " + finished.getState() + " (error=" + finished.getError() + ")");
		}

		Integer alphaCount = jdbcTemplate.queryForObject(
				"select count(*) from zfgbb.category where category_id = 1 and category_name = 'Test Category Alpha'",
				Integer.class);
		Integer betaCount = jdbcTemplate.queryForObject(
				"select count(*) from zfgbb.category where category_id = 2 and category_name = 'Test Category Beta'",
				Integer.class);

		assertEquals(1, alphaCount, "Alpha category should be present in zfgbb.category");
		assertEquals(1, betaCount, "Beta category should be present in zfgbb.category");
	}

	@Test
	void cancel_marks_job_cancelled() throws Exception {
		Job job = jobService.submit(JobType.MESSAGES).get(0);
		jobService.cancel(job.getId());
		Job finished = waitForTerminal(job, Duration.ofSeconds(30));
		assertTrue(finished.getState() == JobState.CANCELLED || finished.getState() == JobState.COMPLETED,
				"Expected CANCELLED or COMPLETED (if it finished before the cancel landed); got " + finished.getState());
	}

	private Job waitForTerminal(Job job, Duration timeout) throws InterruptedException {
		Instant deadline = Instant.now().plus(timeout);
		while (Instant.now().isBefore(deadline)) {
			Job current = jobService.get(job.getId()).orElseThrow();
			JobState s = current.getState();
			if (s == JobState.COMPLETED || s == JobState.FAILED || s == JobState.CANCELLED) {
				return current;
			}
			Thread.sleep(100);
		}
		throw new AssertionError("Job " + job.getId() + " did not reach a terminal state within " + timeout);
	}
}
