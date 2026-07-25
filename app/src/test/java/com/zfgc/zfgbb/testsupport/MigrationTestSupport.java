package com.zfgc.zfgbb.testsupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import com.zfgc.zfgbb.migrator.jobs.Job;
import com.zfgc.zfgbb.migrator.jobs.JobService;
import com.zfgc.zfgbb.migrator.jobs.JobState;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.jobs.SmfConnectionParams;
import com.zfgc.zfgbb.testsupport.mappers.TestSystemInfoMapper;

public abstract class MigrationTestSupport extends ZfgbbIntegrationTest {

	protected static final String SMF_SERVICE = "smf_mysql_fixture";
	protected static final String SMF_DATABASE = "smf";
	protected static final String SMF_USERNAME = "smf";
	protected static final String SMF_PASSWORD = "smfpw";
	protected static final String SMF_TABLE_PREFIX = "smf_1";

	@Autowired
	protected JobService jobService;

	private JdbcTemplate smfJdbc;
	private static final Path LEGACY_ASSETS = extractLegacyAssets();

	@SuppressWarnings("resource")
	protected static ComposeContainer smfFixture() {
		int port = freePort();
		ComposeContainer smf = new ComposeContainer(projectFile("docker-compose.service.smf.yml"))
				.withEnv("COMPOSE_PROJECT_NAME", "zfgbb-smf-test-" + UUID.randomUUID().toString().substring(0, 8))
				.withEnv("COMPOSE_PROFILES", "fixture")
				.withEnv("SMF_FIXTURE_MYSQL_PORT", String.valueOf(port))
				.waitingFor(SMF_SERVICE, Wait.forLogMessage(".*ready for connections.*", 2)
						.withStartupTimeout(Duration.ofMinutes(3)));
		registerHostPort(smf, port);
		return smf;
	}

	protected static void migrationProperties(DynamicPropertyRegistry r, Supplier<String> contentPath) {
		r.add("zfgbb.migrator.enabled", () -> "true");
		r.add("zfgbb.content.path", contentPath::get);
	}

	protected JdbcTemplate smfJdbc(ComposeContainer smf) {
		if (smfJdbc == null) {
			smfJdbc = new JdbcTemplate(DataSourceBuilder.create()
					.url(smfJdbcUrl(smf))
					.username(SMF_USERNAME)
					.password(SMF_PASSWORD)
					.build());
		}
		return smfJdbc;
	}

	protected static String smfJdbcUrl(ComposeContainer smf) {
		return "jdbc:mysql://localhost:" + hostPort(smf) + "/" + SMF_DATABASE
				+ "?useSSL=false&allowPublicKeyRetrieval=true";
	}

	protected static void waitForSmf(ComposeContainer smf) {
		Instant deadline = Instant.now().plus(Duration.ofMinutes(3));
		SQLException lastFailure = null;
		while (Instant.now().isBefore(deadline)) {
			try (Connection ignored = DriverManager.getConnection(smfJdbcUrl(smf), SMF_USERNAME, SMF_PASSWORD)) {
				return;
			} catch (SQLException e) {
				lastFailure = e;
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new IllegalStateException(e);
			}
		}
		throw new IllegalStateException("SMF fixture never became reachable at " + smfJdbcUrl(smf), lastFailure);
	}

	protected SmfConnectionParams smfParams(ComposeContainer smf, String attachmentsTarget) {
		return new SmfConnectionParams(
				smfJdbcUrl(smf),
				SMF_USERNAME,
				SMF_PASSWORD,
				SMF_TABLE_PREFIX,
				"localhost:8090",
				"http://localhost:5173",
				LEGACY_ASSETS.resolve("smf/attachments").toString(),
				attachmentsTarget,
				LEGACY_ASSETS.resolve("smf/avatars").toString(),
				LEGACY_ASSETS.resolve("cms/uploads").toString(),
				LEGACY_ASSETS.resolve("wiki/images").toString(),
				true,
				true,
				4,
				5,
				Map.of("User", 1),
				Map.of(9, List.of("ZFGC_WIKI_MODERATOR")),
				null,
				null,
				Map.of(
						4, "ZFGCpedia",
						5, "ZFGCpedia_talk",
						100, "KOT",
						101, "KOT_talk"),
				"zfgc.com");
	}

	private static Path extractLegacyAssets() {
		try {
			Path target = Files.createTempDirectory("zfgbb-legacy-assets-");
			Path archive = resolveFromProjectRoot("app/src/test/resources/legacy-assets.tar.gz");
			Process process = new ProcessBuilder("tar", "-xzf", archive.toString(), "-C", target.toString())
					.redirectErrorStream(true)
					.start();
			if (process.waitFor() != 0)
				throw new IllegalStateException("Unable to extract " + archive + ": "
						+ new String(process.getInputStream().readAllBytes()));
			target.toFile().deleteOnExit();
			return target;
		} catch (IOException e) {
			throw new ExceptionInInitializerError(e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new ExceptionInInitializerError(e);
		}
	}

	protected List<Job> runPipeline(JobType type, SmfConnectionParams params)
			throws InterruptedException {
		List<Job> submitted = jobService.submit(type, params);
		List<Job> finished = waitForAllTerminal(submitted, Duration.ofMinutes(3));
		assertAllCompleted(finished);
		return finished;
	}

	protected List<Job> waitForAllTerminal(List<Job> jobs, Duration timeout) throws InterruptedException {
		Instant deadline = Instant.now().plus(timeout);
		while (Instant.now().isBefore(deadline)) {
			boolean allTerminal = jobs.stream().allMatch(j -> isTerminal(state(j)));
			if (allTerminal) {
				return jobs.stream().map(j -> jobService.get(j.getId()).orElseThrow()).toList();
			}
			Thread.sleep(200);
		}
		throw new AssertionError("Jobs did not reach a terminal state within " + timeout + ": "
				+ jobs.stream().map(j -> j.getType() + "=" + state(j)).toList());
	}

	private JobState state(Job job) {
		return jobService.get(job.getId()).orElseThrow().getState();
	}

	private static boolean isTerminal(JobState state) {
		return state == JobState.COMPLETED || state == JobState.FAILED || state == JobState.CANCELLED;
	}

	protected void assertAllCompleted(List<Job> jobs) {
		StringBuilder report = new StringBuilder();
		int failed = 0;
		for (Job job : jobs) {
			report.append("  ").append(job.getType()).append(" -> ").append(job.getState());
			if (job.getError() != null) {
				report.append(" (").append(job.getError()).append(")");
				failed++;
			}
			report.append('\n');
		}
		if (failed > 0) {
			fail("Pipeline had " + failed + " failed job(s):\n" + report);
		}
	}

	@Autowired
	protected TestSystemInfoMapper testSystemInfoMapper;

	protected void assertSameCount(ComposeContainer smf, String smfFromAndWhere, long zfgbbCount) {
		assertSourceMatchesTarget(smf, "select count(*) from " + smfFromAndWhere, zfgbbCount);
	}

	protected void assertSourceMatchesTarget(ComposeContainer smf, String smfQuery, long zfgbbCount) {
		Long source = smfJdbc(smf).queryForObject(smfQuery, Long.class);
		assertEquals(source.longValue(), zfgbbCount, smfQuery);
	}
}
