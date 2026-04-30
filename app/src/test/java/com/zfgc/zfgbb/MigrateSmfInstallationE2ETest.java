package com.zfgc.zfgbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.MountableFile;

import com.zfgc.zfgbb.migrator.jobs.Job;
import com.zfgc.zfgbb.migrator.jobs.JobService;
import com.zfgc.zfgbb.migrator.jobs.JobState;
import com.zfgc.zfgbb.migrator.jobs.JobType;

@SpringBootTest
@Testcontainers
class MigrateSmfInstallationE2ETest {

	@TempDir
	static Path attachmentsSource;

	@TempDir
	static Path attachmentsTarget;

	@Container
	static PostgreSQLContainer pg = new PostgreSQLContainer("postgres:16")
			.withEnv("PGDATA", "/tmp/postgres-data");

	@Container
	static MySQLContainer mysql = new MySQLContainer("mysql:8")
			.withCommand(
					"--character-set-server=utf8mb3",
					"--collation-server=utf8mb3_general_ci",
					"--sql-mode=NO_ENGINE_SUBSTITUTION")
			.withCopyFileToContainer(
					MountableFile.forHostPath(schemaSqlPath()),
					"/docker-entrypoint-initdb.d/01-smf-schema.sql")
			.withCopyFileToContainer(
					MountableFile.forClasspathResource(SmfTestFixture.DELTAS_RESOURCE),
					"/docker-entrypoint-initdb.d/02-zfgc-deltas.sql")
			.withCopyFileToContainer(
					MountableFile.forClasspathResource(SmfTestFixture.DATA_RESOURCE),
					"/docker-entrypoint-initdb.d/03-data.sql");

	@BeforeAll
	static void stageAttachments() throws IOException {
		SmfTestFixture.writeAttachmentsTo(attachmentsSource);
	}

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
		r.add("zfgbb.migrator.attachments.source-path", attachmentsSource::toString);
		r.add("zfgbb.migrator.attachments.target-path", attachmentsTarget::toString);
	}

	@Autowired
	private JobService jobService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void migrate_smf_installation_pipeline_runs_all_converters() throws Exception {
		List<Job> submitted = jobService.submit(JobType.MIGRATE_SMF_INSTALLATION);
		assertEquals(JobType.SMF_INSTALLATION_PIPELINE.size(), submitted.size(),
				"Pipeline should submit one job per converter type");

		List<Job> finished = waitForAllTerminal(submitted, Duration.ofMinutes(2));

		StringBuilder report = new StringBuilder();
		int failed = 0;
		for (Job j : finished) {
			report.append("  ").append(j.getType()).append(" -> ").append(j.getState());
			if (j.getError() != null) {
				report.append(" (").append(j.getError()).append(")");
				failed++;
			}
			report.append('\n');
		}
		if (failed > 0) {
			fail("Pipeline had " + failed + " failed job(s):\n" + report);
		}

		assertEquals(2, count("zfgbb.user where user_name in ('alice', 'bob')"), "users migrated");
		assertEquals(2, count("zfgbb.category where category_name in ('General', 'Off-Topic')"), "categories migrated");
		assertEquals(3, count("zfgbb.board where board_name in ('Announcements', 'Sub-Board', 'Random Chat')"), "boards migrated");
		assertEquals(2, count("zfgbb.thread where thread_name in ('Welcome', 'Random thought')"), "threads migrated");
		assertEquals(5, count("zfgbb.message"), "messages migrated");
		assertTrue(count("zfgbb.message_history") >= 2, "at least the two edits + current snapshots in message_history");
		assertEquals(1, count("zfgbb.poll where poll_question = 'Favorite color?'"), "poll migrated");
		assertEquals(2, count("zfgbb.poll_choice where choice_text in ('Red', 'Blue')"), "poll choices migrated");
		assertEquals(2, count("zfgbb.user_poll_choice"), "individual user votes migrated");
		assertEquals(2, count("zfgbb.karma"), "karma rows migrated");
		assertEquals(2, count("zfgbb.file_attachments"), "file attachment rows migrated");

		assertTrue(Files.exists(attachmentsTarget.resolve("hello.txt")),
				"hello.txt should be written by ATTACHMENT_FILES");
		assertTrue(Files.exists(attachmentsTarget.resolve("sunset.png")),
				"sunset.png should be written by ATTACHMENT_FILES");
		assertEquals("hello", Files.readString(attachmentsTarget.resolve("hello.txt")));
	}

	private int count(String fromAndWhere) {
		Integer n = jdbcTemplate.queryForObject("select count(*) from " + fromAndWhere, Integer.class);
		return n == null ? 0 : n;
	}

	private List<Job> waitForAllTerminal(List<Job> jobs, Duration timeout) throws InterruptedException {
		Instant deadline = Instant.now().plus(timeout);
		while (Instant.now().isBefore(deadline)) {
			boolean allTerminal = jobs.stream().allMatch(j -> {
				JobState s = jobService.get(j.getId()).orElseThrow().getState();
				return s == JobState.COMPLETED || s == JobState.FAILED || s == JobState.CANCELLED;
			});
			if (allTerminal) {
				return jobs.stream()
						.map(j -> jobService.get(j.getId()).orElseThrow())
						.toList();
			}
			Thread.sleep(200);
		}
		throw new AssertionError("Some jobs did not reach terminal state within " + timeout
				+ ". Last seen: " + jobs.stream()
						.map(j -> jobService.get(j.getId()).orElseThrow())
						.map(j -> j.getType() + "=" + j.getState()).toList());
	}

	private static Path schemaSqlPath() {
		try {
			return SmfTestFixture.schemaSql();
		} catch (IOException e) {
			throw new IllegalStateException("could not stage SMF schema", e);
		}
	}
}
