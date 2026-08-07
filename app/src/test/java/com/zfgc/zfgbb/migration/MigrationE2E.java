package com.zfgc.zfgbb.migration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.ComposeContainer;

import com.zfgc.zfgbb.dbo.ContentTemplateDboExample;
import com.zfgc.zfgbb.mappers.ContentTemplateDboMapper;
import com.zfgc.zfgbb.mappers.MessageDboMapper;
import com.zfgc.zfgbb.mappers.MessageHistoryDboMapper;
import com.zfgc.zfgbb.mappers.ThreadDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageRevisionDboMapper;
import com.zfgc.zfgbb.migrator.jobs.Job;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.jobs.SmfConnectionParams;
import com.zfgc.zfgbb.testsupport.MigrationTestSupport;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class MigrationE2E extends MigrationTestSupport {

	static final ComposeContainer pg = devPostgres();
	static final ComposeContainer smf = smfFixture();
	static final Path contentTarget;
	protected static final Path sampleArchive;

	static {
		pg.start();
		smf.start();
		waitForSmf(smf);
		try {
			contentTarget = Files.createTempDirectory("zfgbb-e2e-content");
			sampleArchive = Files.createTempDirectory("zfgbb-e2e-sample-data")
					.resolve("backup.tar.gz");
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	protected static List<Job> smfJobs;
	protected static List<Job> cmsJobs;
	protected static OffsetDateTime pipelineStartedAt;
	protected static long threadCountAfterSmf;
	protected static long messageCountAfterSmf;
	protected static long messageHistoryCountAfterSmf;
	protected static long preMigrationWikiPageCount;
	protected static long preMigrationWikiRevisionCount;
	protected static long preMigrationLinkedTemplateCount;

	@DynamicPropertySource
	static void props(DynamicPropertyRegistry r) {
		datasource(r, pg);
		migrationProperties(r, () -> contentTarget.toString());
		r.add("zfgbb.install.sample-archive", () -> "file:" + sampleArchive);
	}

	protected SmfConnectionParams params() {
		return smfParams(smf, contentTarget.toString());
	}

	@Autowired
	protected ThreadDboMapper threadDboMapper;

	@Autowired
	protected MessageDboMapper messageDboMapper;

	@Autowired
	protected MessageHistoryDboMapper messageHistoryDboMapper;

	@Autowired
	protected WikiPageDboMapper wikiPageDboMapper;

	@Autowired
	protected WikiPageRevisionDboMapper wikiPageRevisionDboMapper;

	@Autowired
	protected ContentTemplateDboMapper contentTemplateDboMapper;

	@BeforeEach
	void ensureMigrated() throws Exception {
		if (smfJobs != null) {
			return;
		}
		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "adminUserName": "test_admin",
						  "adminDisplayName": "Test Admin",
						  "adminEmail": "test_admin@example.invalid",
						  "adminPassword": "adminpass123",
						  "siteName": "ZFGC Test",
						  "provisionRecycleBin": false
						}
						"""))
				.andExpect(status().isOk());
		ContentTemplateDboExample linkedToAWikiPage = new ContentTemplateDboExample();
		linkedToAWikiPage.createCriteria().andWikiPageIdIsNotNull();
		preMigrationWikiPageCount = wikiPageDboMapper.countByExample(null);
		preMigrationWikiRevisionCount = wikiPageRevisionDboMapper.countByExample(null);
		preMigrationLinkedTemplateCount = contentTemplateDboMapper.countByExample(linkedToAWikiPage);
		pipelineStartedAt = OffsetDateTime.now();
		smfJobs = runPipeline(JobType.MIGRATE_SMF_INSTALLATION, params());
		threadCountAfterSmf = threadDboMapper.countByExample(null);
		messageCountAfterSmf = messageDboMapper.countByExample(null);
		messageHistoryCountAfterSmf = messageHistoryDboMapper.countByExample(null);
		cmsJobs = runPipeline(JobType.MIGRATE_CMS_INSTALLATION, params());
		new ResourceDatabasePopulator(
				new ClassPathResource("fixture-overlays/V1__fixture_identity_and_templates.sql"),
				new ClassPathResource("fixture-overlays/V2__recycle_bin.sql"),
				new ClassPathResource("fixture-overlays/V3__sample_awards.sql"))
				.execute(dataSource);
	}
}
