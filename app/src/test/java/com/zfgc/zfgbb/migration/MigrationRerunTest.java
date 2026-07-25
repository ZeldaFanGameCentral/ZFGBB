package com.zfgc.zfgbb.migration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.zfgc.zfgbb.migrator.converters.MigrationHasher;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.mappers.MigratorTimestampMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.zfgc.zfgbb.dbo.*;
import com.zfgc.zfgbb.mappers.*;

@Order(4)
class MigrationRerunTest extends MigrationE2E {

	@Autowired private MigratorIdMapDboMapper migratorIdMapDboMapper;

	@Test
	void forcedCmsRerunIsStructurallyIdempotentAndRepairsMappedAuthors() throws Exception {
		int wikiPageCountBeforeRerun = (int) wikiPageDboMapper.countByExample(null);
		int threadCountBeforeRerun = (int) threadDboMapper.countByExample(null);

		ProjectViewDboExample pve = new ProjectViewDboExample();
		pve.createCriteria().andSlugEqualTo("ocarina-of-time-3d");
		Integer threadId = projectViewDboMapper.selectByExample(pve).get(0).getThreadId();

		MigratorIdMapDboExample me = new MigratorIdMapDboExample();
		me.createCriteria().andEntityTypeEqualTo("GAME_COMMENT").andLegacyIdEqualTo(36);
		Integer messageId = migratorIdMapDboMapper.selectByExample(me).get(0).getZfgbbId();

		assertEquals(MigrationHasher.hash("gamecomments169"), threadDboMapper.selectByPrimaryKey(threadId).getMigrationHash());

		ThreadDbo tUpdate = threadDboMapper.selectByPrimaryKey(threadId);
		tUpdate.setCreatedUserId(null);
		threadDboMapper.updateByPrimaryKey(tUpdate);
		MessageDbo msg = messageDboMapper.selectByPrimaryKey(messageId);
		msg.setOwnerId(null);
		messageDboMapper.updateByPrimaryKey(msg);

		assertAllCompleted(waitForAllTerminal(
				jobService.submit(JobType.MIGRATE_CMS_INSTALLATION, params().withForce(true)), Duration.ofMinutes(2)));

		assertEquals(wikiPageCountBeforeRerun, wikiPageDboMapper.countByExample(null), "re-run must not duplicate pages");
		assertEquals(threadCountBeforeRerun, threadDboMapper.countByExample(null), "re-run must not duplicate discussion threads");

		assertEquals(6, projectDboMapper.countByExample(null), "re-run must not duplicate projects");
		assertEquals(4, resourceDboMapper.countByExample(null), "re-run must not duplicate resources");

		ThreadDbo thread = threadDboMapper.selectByPrimaryKey(threadId);
		UserDbo threadOwner = userDboMapper.selectByPrimaryKey(thread.getCreatedUserId());
		assertEquals("gm112", threadOwner.getDisplayName(), "forced re-run repairs discussion thread authors");

		MessageDbo message = messageDboMapper.selectByPrimaryKey(messageId);
		UserDbo messageOwner = userDboMapper.selectByPrimaryKey(message.getOwnerId());
		assertEquals("gm112", messageOwner.getDisplayName(), "forced re-run repairs comment authors");
		assertCmsAssets();
	}

	@Autowired private ProjectDboMapper projectDboMapper;
	@Autowired private ResourceDboMapper resourceDboMapper;
	@Autowired private UserDboMapper userDboMapper;
	@Autowired private ContentResourceDboMapper contentResourceDboMapper;
	@Autowired private ProjectViewDboMapper projectViewDboMapper;
	@Autowired private ProjectScreenshotDboMapper projectScreenshotDboMapper;
	@Autowired private ProjectDownloadDboMapper projectDownloadDboMapper;
	@Autowired private ResourceViewDboMapper resourceViewDboMapper;

	private void assertCmsAssets() {
		ContentResourceDboExample resEx = new ContentResourceDboExample();
		resEx.createCriteria().andContentTypeIdIn(List.of(3, 4));
		List<ContentResourceDbo> cmsAssets = contentResourceDboMapper.selectByExample(resEx);
		assertEquals(23, cmsAssets.size());

		ProjectViewDboExample projViewEx = new ProjectViewDboExample();
		projViewEx.createCriteria().andPreviewContentResourceIdIsNotNull();
		assertEquals(6, projectViewDboMapper.countByExample(projViewEx));

		assertEquals(3, projectScreenshotDboMapper.countByExample(null));
		assertEquals(5, projectDownloadDboMapper.countByExample(null));

		ResourceViewDboExample resViewDownloadEx = new ResourceViewDboExample();
		resViewDownloadEx.createCriteria().andDownloadContentResourceIdIsNotNull();
		assertEquals(4, resourceViewDboMapper.countByExample(resViewDownloadEx));

		ResourceViewDboExample resViewPreviewEx = new ResourceViewDboExample();
		resViewPreviewEx.createCriteria().andPreviewContentResourceIdIsNotNull();
		assertEquals(3, resourceViewDboMapper.countByExample(resViewPreviewEx));

		for (ContentResourceDbo asset : cmsAssets) {
			assertTrue(Files.exists(contentTarget.resolve(asset.getStorageDir())
					.resolve(String.valueOf(asset.getContentResourceId()))
					.resolve(asset.getFilename())),
					"migrated binary is missing: " + asset.getFilename());
		}
	}

	@Autowired private MigratorTimestampMapper migratorTimestampMapper;

	@Test
	void restampingAMigratedRowWithTheTimestampItAlreadyHasKeepsTheLegacyClock() {
		List<MessageDbo> posts = migratedPostsStampedBeforeTheMigrationRan(MIGRATED_ROW_SAMPLE_SIZE);
		List<MessageHistoryDbo> revisions = migratedRevisionsStampedBeforeTheMigrationRan(MIGRATED_ROW_SAMPLE_SIZE);
		List<ThreadDbo> threads = migratedThreadsStampedBeforeTheMigrationRan(MIGRATED_ROW_SAMPLE_SIZE);
		assertFalse(posts.isEmpty(), "the migrated corpus must hold posts carrying their legacy clock");
		assertFalse(revisions.isEmpty(), "the migrated corpus must hold post revisions carrying their legacy clock");
		assertFalse(threads.isEmpty(), "the migrated corpus must hold threads carrying their legacy clock");

		for (int stampingPass = 1; stampingPass <= FORCED_REMIGRATION_STAMPING_PASSES; stampingPass++) {
			for (MessageDbo post : posts) {
				migratorTimestampMapper.setMessageTimestamps(post.getMessageId(), post.getCreatedTs(), post.getUpdatedTs());
				assertEquals(post.getUpdatedTs().toInstant(),
						messageDboMapper.selectByPrimaryKey(post.getMessageId()).getUpdatedTs().toInstant(),
						legacyClockLost("post", post.getMessageId(), stampingPass));
			}
			for (MessageHistoryDbo revision : revisions) {
				migratorTimestampMapper.setMessageHistoryTimestamps(revision.getMessageHistoryId(),
						revision.getCreatedTs(), revision.getUpdatedTs());
				assertEquals(revision.getUpdatedTs().toInstant(),
						messageHistoryDboMapper.selectByPrimaryKey(revision.getMessageHistoryId()).getUpdatedTs().toInstant(),
						legacyClockLost("post revision", revision.getMessageHistoryId(), stampingPass));
			}
			for (ThreadDbo thread : threads) {
				migratorTimestampMapper.setThreadTimestamps(thread.getThreadId(), thread.getCreatedTs(), thread.getUpdatedTs());
				assertEquals(thread.getUpdatedTs().toInstant(),
						threadDboMapper.selectByPrimaryKey(thread.getThreadId()).getUpdatedTs().toInstant(),
						legacyClockLost("thread", thread.getThreadId(), stampingPass));
			}
		}
	}

	private static final int MIGRATED_ROW_SAMPLE_SIZE = 5;
	private static final int FORCED_REMIGRATION_STAMPING_PASSES = 2;

	private String legacyClockLost(String rowKind, Integer rowId, int stampingPass) {
		return "stamping pass " + stampingPass + " wrote " + rowKind + " " + rowId
				+ " the historical updated_ts it already held, exactly as a forced re-migration does; "
				+ "touch_updated_ts must not read that as an edit and replace it with today's clock";
	}

	private List<MessageDbo> migratedPostsStampedBeforeTheMigrationRan(int sampleSize) {
		MessageDboExample stampedByTheMigrator = new MessageDboExample();
		stampedByTheMigrator.createCriteria().andMigrationHashIsNotNull().andUpdatedTsLessThan(pipelineStartedAt);
		stampedByTheMigrator.setOrderByClause("message_id");
		stampedByTheMigrator.setLimit(sampleSize);
		stampedByTheMigrator.setOffset(0);
		return messageDboMapper.selectByExample(stampedByTheMigrator);
	}

	private List<MessageHistoryDbo> migratedRevisionsStampedBeforeTheMigrationRan(int sampleSize) {
		MessageHistoryDboExample stampedByTheMigrator = new MessageHistoryDboExample();
		stampedByTheMigrator.createCriteria().andMigrationHashIsNotNull().andUpdatedTsLessThan(pipelineStartedAt);
		stampedByTheMigrator.setOrderByClause("message_history_id");
		stampedByTheMigrator.setLimit(sampleSize);
		stampedByTheMigrator.setOffset(0);
		return messageHistoryDboMapper.selectByExample(stampedByTheMigrator);
	}

	private List<ThreadDbo> migratedThreadsStampedBeforeTheMigrationRan(int sampleSize) {
		ThreadDboExample stampedByTheMigrator = new ThreadDboExample();
		stampedByTheMigrator.createCriteria().andMigrationHashIsNotNull().andUpdatedTsLessThan(pipelineStartedAt);
		stampedByTheMigrator.setOrderByClause("thread_id");
		stampedByTheMigrator.setLimit(sampleSize);
		stampedByTheMigrator.setOffset(0);
		return threadDboMapper.selectByExample(stampedByTheMigrator);
	}

	@Autowired private Flyway migrationRunner;
	@Autowired @Qualifier("sqlSessionFactory") private SqlSessionFactory migrationSqlSessionFactory;

	private static final String PROBE_TABLE = "updated_ts_trigger_probe";

	public interface UpdatedTsTriggerProbeMapper {

		@Update("""
				create table zfgbb.updated_ts_trigger_probe (
					probe_id integer primary key,
					label text,
					updated_ts timestamptz not null default current_timestamp)
				""")
		void createTableTheWayALaterMigrationWould();

		@Update("drop table if exists zfgbb.updated_ts_trigger_probe")
		void dropTable();

		@Insert("insert into zfgbb.updated_ts_trigger_probe (probe_id, label) values (1, 'as inserted')")
		void insertRow();

		@Update("update zfgbb.updated_ts_trigger_probe set label = 'as edited' where probe_id = 1")
		void editRowLabel();

		@Select("select updated_ts from zfgbb.updated_ts_trigger_probe where probe_id = 1")
		OffsetDateTime findRowUpdatedTs();
	}

	@Test
	void aTableCreatedAfterTheTriggerMigrationStillHasItsUpdatedTsMaintained() {
		Configuration mybatisConfiguration = migrationSqlSessionFactory.getConfiguration();
		if (!mybatisConfiguration.hasMapper(UpdatedTsTriggerProbeMapper.class))
			mybatisConfiguration.addMapper(UpdatedTsTriggerProbeMapper.class);

		try (SqlSession session = migrationSqlSessionFactory.openSession(true)) {
			UpdatedTsTriggerProbeMapper probe = session.getMapper(UpdatedTsTriggerProbeMapper.class);
			probe.dropTable();
			probe.createTableTheWayALaterMigrationWould();
			try {
				assertTrue(testQueryHelperMapper.findTablesMissingUpdatedTsTrigger().contains(PROBE_TABLE),
						"a table that did not exist when the trigger migration ran starts out without the trigger");

				migrationRunner.migrate();

				assertFalse(testQueryHelperMapper.findTablesMissingUpdatedTsTrigger().contains(PROBE_TABLE),
						"every migrate run must re-attach touch_updated_ts, otherwise a table introduced by a "
								+ "migration newer than the trigger migration never maintains updated_ts");

				probe.insertRow();
				OffsetDateTime stampedByTheInsertDefault = probe.findRowUpdatedTs();
				probe.editRowLabel();
				assertTrue(probe.findRowUpdatedTs().isAfter(stampedByTheInsertDefault),
						"the re-attached trigger must stamp updated_ts on an ordinary update");
			} finally {
				probe.dropTable();
			}
		}
	}

	@Test
	void triggerAttachmentAlsoRunsFromAReRunnableScriptNotOnlyTheVersionedMigration()
			throws IOException {
		Path migrations = resolveFromProjectRoot("app/src/main/resources/db/migration");
		List<String> reRunnableAttachments = new ArrayList<>();
		List<String> versionedAttachments = new ArrayList<>();
		try (Stream<Path> scripts = Files.walk(migrations)) {
			for (Path script : scripts.filter(Files::isRegularFile).toList()) {
				String fileName = script.getFileName().toString();
				if (!fileName.endsWith(".sql") || !Files.readString(script).contains(TRIGGER_ATTACHMENT_CALL))
					continue;
				if (fileName.startsWith("afterMigrate") || fileName.startsWith("R__"))
					reRunnableAttachments.add(fileName);
				else
					versionedAttachments.add(fileName);
			}
		}
		assertFalse(reRunnableAttachments.isEmpty(),
				"a versioned migration attaches triggers once, at the point in history where it runs, so "
						+ "every table added by a later migration silently loses its updated_ts maintenance "
						+ "unless a re-runnable script re-attaches them; the only attachments found were "
						+ versionedAttachments);
		assertTrue(Files.readString(migrations.resolve("functions/R__01_triggers.sql"))
						.contains("create or replace function zfgbb.touch_updated_ts"),
				"the authoritative trigger body must live in a repeatable script so it can be fixed "
						+ "forward; the copy in V20260801.4 is frozen by the shipped installer archive's "
						+ "recorded flyway checksum and can never be edited");
	}

	private static final String TRIGGER_ATTACHMENT_CALL = "select zfgbb.attach_updated_ts_triggers()";
}
