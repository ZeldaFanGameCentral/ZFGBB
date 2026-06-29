package com.zfgc.zfgbb.migration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.ComposeContainer;

import com.zfgc.zfgbb.migrator.jobs.Job;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.jobs.SmfConnectionParams;
import com.zfgc.zfgbb.testsupport.MigrationTestSupport;

public abstract class MigrationE2E extends MigrationTestSupport {

	static final ComposeContainer pg = devPostgres();
	static final ComposeContainer smf = smfFixture();
	static final Path contentTarget;

	static {
		pg.start();
		smf.start();
		waitForSmf(smf);
		try {
			contentTarget = Files.createTempDirectory("zfgbb-e2e-content");
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	protected static List<Job> smfJobs;
	protected static List<Job> cmsJobs;
	protected static Map<String, Integer> forumCountsAfterSmf;
	protected static int preMigrationWikiPageCount;
	protected static int preMigrationWikiRevisionCount;
	protected static int preMigrationLinkedTemplateCount;

	@DynamicPropertySource
	static void props(DynamicPropertyRegistry r) {
		datasource(r, pg);
		migrationProperties(r, () -> contentTarget.toString());
	}

	protected SmfConnectionParams params() {
		return smfParams(smf, contentTarget.toString());
	}

	@BeforeEach
	void ensureMigrated() throws InterruptedException {
		if (smfJobs != null) {
			return;
		}
		preMigrationWikiPageCount = count("zfgbb.wiki_page");
		preMigrationWikiRevisionCount = count("zfgbb.wiki_page_revision");
		preMigrationLinkedTemplateCount = count("zfgbb.content_template where wiki_page_id is not null");
		smfJobs = runPipeline(JobType.MIGRATE_SMF_INSTALLATION, params());
		forumCountsAfterSmf = new HashMap<>();
		for (String table : List.of("thread", "message", "message_history")) {
			forumCountsAfterSmf.put(table, count("zfgbb." + table));
		}
		cmsJobs = runPipeline(JobType.MIGRATE_CMS_INSTALLATION, params());
	}
}
