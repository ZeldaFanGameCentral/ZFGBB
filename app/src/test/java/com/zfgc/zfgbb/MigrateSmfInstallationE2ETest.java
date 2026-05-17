package com.zfgc.zfgbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import com.zfgc.zfgbb.migrator.jobs.Job;
import com.zfgc.zfgbb.migrator.jobs.JobService;
import com.zfgc.zfgbb.migrator.jobs.JobState;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.jobs.SmfConnectionParams;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MigrateSmfInstallationE2ETest {

	private static final Pattern ATTACH_REF = Pattern.compile("\\[attach=(\\d+)\\]");
	private static final Pattern THREAD_REF = Pattern.compile("\\[thread=(\\d+)(?:\\s|\\])");
	private static final Pattern BOARD_REF = Pattern.compile("\\[board=(\\d+)\\]");
	private static final Pattern MEMBER_REF = Pattern.compile("\\[member=(\\d+)\\]");
	private static final Pattern MSG_REF = Pattern.compile("msg=(\\d+)");

	private static final String SMF_SERVICE = "smf_mysql_fixture";
	private static final String SMF_DATABASE = "smf";
	private static final String SMF_USERNAME = "smf";
	private static final String SMF_PASSWORD = "smfpw";
	private static final String SMF_TABLE_PREFIX = "smf_1";

	private static final int FIXTURE_PORT = freePort();

	@TempDir
	static Path attachmentsTarget;

	@Container
	static PostgreSQLContainer pg = new PostgreSQLContainer("postgres:16")
			.withEnv("PGDATA", "/tmp/postgres-data");

	@Container
	static ComposeContainer smf = new ComposeContainer(composeFile())
			.withEnv("COMPOSE_PROFILES", "fixture")
			.withEnv("SMF_FIXTURE_MYSQL_PORT", String.valueOf(FIXTURE_PORT))
			.waitingFor(SMF_SERVICE, Wait.forLogMessage(".*ready for connections.*", 2))
			.withStartupTimeout(Duration.ofMinutes(3));

	@BeforeAll
	static void waitForFixture() throws InterruptedException {
		waitForSmfMysql(Duration.ofMinutes(2));
	}

	private static void waitForSmfMysql(Duration timeout) throws InterruptedException {
		Instant deadline = Instant.now().plus(timeout);
		Exception last = null;
		while (Instant.now().isBefore(deadline)) {
			try (Connection ignored = DriverManager.getConnection(smfJdbcUrl(), SMF_USERNAME, SMF_PASSWORD)) {
				return;
			} catch (Exception e) {
				last = e;
				Thread.sleep(500);
			}
		}
		throw new IllegalStateException("SMF MySQL on " + smfJdbcUrl() + " not reachable within " + timeout, last);
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
	}

	@Autowired
	private JobService jobService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private JdbcTemplate smfJdbc;

	@Autowired
	void initSmfJdbc() {
		this.smfJdbc = new JdbcTemplate(DataSourceBuilder.create()
				.url(smfJdbcUrl())
				.username(SMF_USERNAME)
				.password(SMF_PASSWORD)
				.build());
	}

	private SmfConnectionParams smfParams() {
		return new SmfConnectionParams(
				smfJdbcUrl(),
				SMF_USERNAME,
				SMF_PASSWORD,
				SMF_TABLE_PREFIX,
				null,
				null,
				attachmentsSource().toString(),
				attachmentsTarget.toString(),
				null,
				false);
	}

	@Test
	@Order(1)
	void migrate_smf_installation_pipeline_runs_all_converters() throws Exception {
		List<Job> submitted = jobService.submit(JobType.MIGRATE_SMF_INSTALLATION, smfParams());
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

		assertNoOrphanRewrittenBbcodes();
		assertAllMigratedFilesPresentInTarget();
	}

	@Test
	@Order(2)
	void rerunning_attachments_does_not_double_rewrite() throws Exception {
		List<String> beforeBodies = jdbcTemplate.queryForList(
				"select message_text from zfgbb.message_history where message_text like '%[attach=%' order by message_history_id",
				String.class);
		Integer markersBefore = jdbcTemplate.queryForObject(
				"select count(*) from zfgbb.migrator_attachment_ref_rewrites", Integer.class);

		Job rerun = jobService.submit(JobType.ATTACHMENTS, smfParams()).get(0);
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
			padding.add(jobService.submit(JobType.CATEGORIES, smfParams()).get(0));
		}
		Job target = jobService.submit(JobType.CATEGORIES, smfParams()).get(0);
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

	private void assertNoOrphanRewrittenBbcodes() {
		assertAllRefsResolve(ATTACH_REF, "select file_attachment_id from zfgbb.file_attachments");
		assertAllRefsResolve(THREAD_REF, "select thread_id from zfgbb.thread");
		assertAllRefsResolve(BOARD_REF, "select board_id from zfgbb.board");
		assertAllRefsResolve(MEMBER_REF, "select user_id from zfgbb.\"user\"");
		assertAllRefsResolve(MSG_REF, "select message_id from zfgbb.message");
	}

	private void assertAllRefsResolve(Pattern bbcodePattern, String validIdsQuery) {
		Set<Integer> validIds = new HashSet<>(jdbcTemplate.queryForList(validIdsQuery, Integer.class));
		List<String> bodies = jdbcTemplate.queryForList(
				"select message_text from zfgbb.message_history", String.class);
		for (String body : bodies) {
			Matcher m = bbcodePattern.matcher(body);
			while (m.find()) {
				int id = Integer.parseInt(m.group(1));
				assertTrue(validIds.contains(id),
						bbcodePattern + " ref " + id + " does not resolve via `" + validIdsQuery + "`: " + body);
			}
		}
	}

	private void assertAllMigratedFilesPresentInTarget() {
		List<Integer> contentResourceIds = jdbcTemplate.queryForList(
				"select cr.content_resource_id from zfgbb.content_resource cr "
						+ "join zfgbb.file_attachments fa on fa.content_resource_id = cr.content_resource_id",
				Integer.class);
		for (Integer id : contentResourceIds) {
			Path expected = attachmentsTarget.resolve(String.valueOf(id));
			assertTrue(Files.exists(expected),
					"attachment content_resource_id=" + id + " should be present at " + expected);
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

	private static String smfJdbcUrl() {
		return "jdbc:mysql://localhost:" + FIXTURE_PORT + "/" + SMF_DATABASE
				+ "?useSSL=false&allowPublicKeyRetrieval=true";
	}

	private static int freePort() {
		try (ServerSocket s = new ServerSocket(0)) {
			return s.getLocalPort();
		} catch (IOException e) {
			throw new IllegalStateException("could not allocate free host port for compose", e);
		}
	}

	private static File composeFile() {
		return resolveFromProjectRoot("docker-compose.service.smf.yml").toFile();
	}

	private static Path attachmentsSource() {
		return resolveFromProjectRoot("app/src/test/resources/smf-fixtures/2.0.15/smf-attachments");
	}

	private static Path resolveFromProjectRoot(String relativePath) {
		Path current = new File("").getAbsoluteFile().toPath();
		while (current != null) {
			Path candidate = current.resolve(relativePath);
			if (Files.exists(candidate)) {
				return candidate;
			}
			current = current.getParent();
		}
		throw new IllegalStateException(
				relativePath + " not found walking up from " + new File("").getAbsolutePath());
	}
}
