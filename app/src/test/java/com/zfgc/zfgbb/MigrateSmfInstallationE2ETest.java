package com.zfgc.zfgbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MigrateSmfInstallationE2ETest {

	private static final Pattern ATTACH_REF = Pattern.compile("\\[attach=(\\d+)\\]");

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

	private JdbcTemplate smfJdbc;

	@Autowired
	void initSmfJdbc(@Qualifier("smfDataSource") DataSource smfDataSource) {
		this.smfJdbc = new JdbcTemplate(smfDataSource);
	}

	@Test
	@Order(1)
	void migrate_smf_installation_pipeline_runs_all_converters() throws Exception {
		List<Job> submitted = jobService.submit(JobType.MIGRATE_SMF_INSTALLATION);
		assertEquals(JobType.SMF_INSTALLATION_PIPELINE.size(), submitted.size(),
				"Pipeline should submit one job per converter type");

		assertAllCompleted(waitForAllTerminal(submitted, Duration.ofMinutes(2)));

		assertSameCount("smf_1members", "zfgbb.user");
		assertSameCount("smf_1categories", "zfgbb.category");
		assertSameCount("smf_1boards", "zfgbb.board");
		assertSameCount("smf_1topics", "zfgbb.thread");
		assertSameCount("smf_1messages", "zfgbb.message");
		assertSameCount("smf_1members", "zfgbb.user_bio_info");
		assertSameCount("smf_1members", "zfgbb.user_contact_info");
		assertSameCount("smf_1members", "zfgbb.email_address");
		assertSameCount("smf_1polls", "zfgbb.poll");
		assertSameCount("smf_1poll_choices", "zfgbb.poll_choice");
		assertSameCount("smf_1log_karma", "zfgbb.karma");
		assertSameCount("smf_1attachments where id_msg > 0", "zfgbb.file_attachments");

		assertSourceMatchesTarget(
				"select count(distinct poster_ip) from smf_1messages",
				"select count(*) from zfgbb.ip_address");
		assertSourceMatchesTarget(
				"select (select count(*) from smf_1messages) + (select count(*) from smf_1messages_history)",
				"select count(*) from zfgbb.message_history");
		assertSourceMatchesTarget(
				"select count(*) from smf_1log_polls where id_member != 0",
				"select count(*) from zfgbb.user_poll_choice");

		assertNoOrphanAttachmentRefs();
		assertAllMigratedFilesPresentInTarget();
		assertSemanticBbcodesInRewrittenBodies();
	}

	private void assertSemanticBbcodesInRewrittenBodies() {
		String msg5 = jdbcTemplate.queryForObject(
				"select message_text from zfgbb.message_history where message_id = 5 and current_flag = true",
				String.class);
		assertTrue(msg5.contains("[thread=1 msg=2]welcome thread[/thread]"),
				"msg 5 should have rewritten [url=...?topic=1.msg2] to [thread=1 msg=2]; got: " + msg5);
		assertTrue(msg5.contains("[board=1]Announcements board[/board]"),
				"msg 5 should have rewritten [url=...?board=1] to [board=1]; got: " + msg5);
		assertTrue(msg5.contains("[member=2]bob[/member]"),
				"msg 5 should have rewritten [url=...?action=profile;u=2] to [member=2]; got: " + msg5);
		assertTrue(msg5.contains("[thread=1]inline[/thread]"),
				"msg 5 should have rewritten [iurl=...?topic=1.0] to [thread=1]; got: " + msg5);

		String msg4 = jdbcTemplate.queryForObject(
				"select message_text from zfgbb.message_history where message_id = 4 and current_flag = true",
				String.class);
		assertTrue(msg4.contains("[quote author=alice thread=1 msg=1 date=1700100000]"),
				"msg 4 should have rewritten quote link= to thread=/msg= attrs; got: " + msg4);
		assertTrue(msg4.contains("[thread=1]http://www.zfgc.com/index.php?topic=1.0[/thread]"),
				"msg 4 should have rewritten the bare zfgc.com URL to [thread=1]; got: " + msg4);
	}

	@Test
	@Order(2)
	void rerunning_attachments_does_not_double_rewrite() throws Exception {
		List<String> beforeBodies = jdbcTemplate.queryForList(
				"select message_text from zfgbb.message_history where message_text like '%[attach=%' order by message_history_id",
				String.class);
		Integer markersBefore = jdbcTemplate.queryForObject(
				"select count(*) from zfgbb.migrator_attachment_ref_rewrites", Integer.class);

		Job rerun = jobService.submit(JobType.ATTACHMENTS).get(0);
		Job finished = waitForAllTerminal(List.of(rerun), Duration.ofMinutes(2)).get(0);
		assertEquals(JobState.COMPLETED, finished.getState(),
				"re-running ATTACHMENTS should COMPLETE; got " + finished.getState()
						+ " (error=" + finished.getError() + ")");

		List<String> afterBodies = jdbcTemplate.queryForList(
				"select message_text from zfgbb.message_history where message_text like '%[attach=%' order by message_history_id",
				String.class);
		Integer markersAfter = jdbcTemplate.queryForObject(
				"select count(*) from zfgbb.migrator_attachment_ref_rewrites", Integer.class);

		assertEquals(beforeBodies, afterBodies, "[attach=N] bodies must be unchanged on re-run");
		assertEquals(markersBefore, markersAfter,
				"re-run should not add new rewrite markers (already-done rows are skipped)");
	}

	@Test
	@Order(3)
	void cancel_marks_queued_job_as_cancelled() throws Exception {
		List<Job> padding = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			padding.add(jobService.submit(JobType.CATEGORIES).get(0));
		}
		Job target = jobService.submit(JobType.CATEGORIES).get(0);
		jobService.cancel(target.getId());

		Job finished = waitForAllTerminal(List.of(target), Duration.ofMinutes(2)).get(0);
		assertEquals(JobState.CANCELLED, finished.getState(),
				"cancelling a queued job should leave it CANCELLED; got " + finished.getState());

		waitForAllTerminal(padding, Duration.ofMinutes(2));
	}

	private void assertSameCount(String smfFromAndWhere, String zfgbbTable) {
		assertSourceMatchesTarget(
				"select count(*) from " + smfFromAndWhere,
				"select count(*) from " + zfgbbTable);
	}

	private void assertSourceMatchesTarget(String smfQuery, String zfgbbQuery) {
		Integer source = smfJdbc.queryForObject(smfQuery, Integer.class);
		Integer target = jdbcTemplate.queryForObject(zfgbbQuery, Integer.class);
		assertEquals(source, target, smfQuery + "  vs  " + zfgbbQuery);
	}

	private void assertAllCompleted(List<Job> jobs) {
		StringBuilder report = new StringBuilder();
		int failed = 0;
		for (Job j : jobs) {
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
	}

	private void assertNoOrphanAttachmentRefs() {
		Set<Integer> validIds = new HashSet<>(jdbcTemplate.queryForList(
				"select file_attachment_id from zfgbb.file_attachments", Integer.class));
		List<String> bodies = jdbcTemplate.queryForList(
				"select message_text from zfgbb.message_history where message_text like '%[attach=%'",
				String.class);
		assertTrue(!bodies.isEmpty(),
				"fixture should produce at least one [attach=N] body for the rewrite check to mean anything");
		for (String body : bodies) {
			Matcher m = ATTACH_REF.matcher(body);
			while (m.find()) {
				int id = Integer.parseInt(m.group(1));
				assertTrue(validIds.contains(id),
						"[attach=" + id + "] in body does not resolve to a file_attachments row: " + body);
			}
		}
	}

	private void assertAllMigratedFilesPresentInTarget() {
		List<String> filenames = jdbcTemplate.queryForList(
				"select cr.filename from zfgbb.content_resource cr "
						+ "join zfgbb.file_attachments fa on fa.content_resource_id = cr.content_resource_id",
				String.class);
		assertTrue(!filenames.isEmpty(),
				"fixture should produce at least one file attachment for ATTACHMENT_FILES to copy");
		for (String fn : filenames) {
			assertTrue(Files.exists(attachmentsTarget.resolve(fn)),
					fn + " should be present in attachments target dir");
		}
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
