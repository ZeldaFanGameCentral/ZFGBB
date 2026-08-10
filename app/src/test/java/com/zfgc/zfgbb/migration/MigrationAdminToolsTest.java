package com.zfgc.zfgbb.migration;

import com.zfgc.zfgbb.services.backup.BackupRestoreService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import com.zfgc.zfgbb.migrator.jobs.SmfConnectionParams;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import tools.jackson.databind.JsonNode;

import com.zfgc.zfgbb.dbo.*;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.mappers.*;
import com.zfgc.zfgbb.migrator.mappers.QuoteStripConversionMapper;
import com.zfgc.zfgbb.migrator.web.QuoteStripOperations;
import com.zfgc.zfgbb.migrator.web.QuoteStripOperations.QuoteStripApplyResult;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.cms.ContentMergeSide;
import com.zfgc.zfgbb.model.cms.MergeApplyRequest;
import com.zfgc.zfgbb.model.cms.MergeCandidate;
import com.zfgc.zfgbb.model.cms.Project;
import com.zfgc.zfgbb.model.system.InstallRequest;
import com.zfgc.zfgbb.model.system.InstallResult;
import com.zfgc.zfgbb.model.users.PasswordAlgo;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.operations.archive.BackupArchiveWriter;
import com.zfgc.zfgbb.services.cms.merge.MigrationConflictService;
import com.zfgc.zfgbb.services.cms.catalog.ProjectService;
import com.zfgc.zfgbb.services.auth.ZfgcPasswordEncoder;
import com.zfgc.zfgbb.services.auth.AuthService;
import com.zfgc.zfgbb.services.auth.ZfgcPasswordEncoder;
import com.zfgc.zfgbb.services.auth.AuthService;
import com.zfgc.zfgbb.services.install.InstallService;
import com.zfgc.zfgbb.services.backup.OperationStorageService;
import com.zfgc.zfgbb.operations.postgres.PostgresBackupTool;
import com.zfgc.zfgbb.services.backup.RestoreService;
import com.zfgc.zfgbb.services.system.SystemConfigService;

@Order(5)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class MigrationAdminToolsTest extends MigrationE2E {

	private static final Pattern EMPTIED_QUOTE_SHELL = Pattern.compile("\\[quote[^\\]]*msg=\\d+[^\\]]*\\]\\[/quote\\]",
			Pattern.CASE_INSENSITIVE);

	@Autowired
	private ProjectService projectService;

	@Autowired
	private MigrationConflictService migrationConflictService;

	@Autowired
	private SystemConfigService systemConfigService;

	@Autowired
	private QuoteStripOperations quoteStripService;

	@Autowired
	private BoardDboMapper boardDboMapper;
	@Autowired
	private BrUserPermissionDboMapper brUserPermissionDboMapper;
	@Autowired
	private CategoryDboMapper categoryDboMapper;
	@Autowired
	private ContentResourceDboMapper contentResourceDboMapper;
	@Autowired
	private CurrentMessageDboMapper currentMessageDboMapper;
	@Autowired
	private EmailAddressDboMapper emailAddressDboMapper;
	@Autowired
	private InstallRunDboMapper installRunDboMapper;
	@Autowired
	private PermissionDboMapper permissionDboMapper;
	@Autowired
	private PersonalMessageDboMapper personalMessageDboMapper;
	@Autowired
	private ContentEntityDboMapper contentEntityDboMapper;
	@Autowired
	private MigrationConflictDboMapper migrationConflictDboMapper;
	@Autowired
	private ProjectDboMapper projectDboMapper;
	@Autowired
	private ProjectDownloadDboMapper projectDownloadDboMapper;
	@Autowired
	private ProjectScreenshotDboMapper projectScreenshotDboMapper;
	@Autowired
	private ProjectViewDboMapper projectViewDboMapper;
	@Autowired
	private QuoteStripAuditDboMapper quoteStripAuditDboMapper;
	@Autowired
	private QuoteStripConversionMapper quoteStripConversionMapper;
	@Autowired
	private QuoteStripRunDboMapper quoteStripRunDboMapper;
	@Autowired
	private SystemConfigDboMapper systemConfigDboMapper;
	@Autowired
	private UserContactInfoDboMapper userContactInfoDboMapper;
	@Autowired
	private UserDboMapper userDboMapper;
	@Autowired
	private UserRefreshTokenDboMapper userRefreshTokenDboMapper;

	private long screenshotsOf(Integer contentEntityId) {
		ProjectScreenshotDboExample onEntity = new ProjectScreenshotDboExample();
		onEntity.createCriteria().andContentEntityIdEqualTo(contentEntityId);
		return projectScreenshotDboMapper.countByExample(onEntity);
	}

	private long downloadsOf(Integer contentEntityId) {
		ProjectDownloadDboExample onEntity = new ProjectDownloadDboExample();
		onEntity.createCriteria().andContentEntityIdEqualTo(contentEntityId);
		return projectDownloadDboMapper.countByExample(onEntity);
	}

	private ProjectViewDbo projectViewByContentEntityId(Integer contentEntityId) {
		ProjectViewDboExample byContentEntityId = new ProjectViewDboExample();
		byContentEntityId.createCriteria().andContentEntityIdEqualTo(contentEntityId);
		return projectViewDboMapper.selectByExample(byContentEntityId).get(0);
	}

	@Test
	@Order(1)
	void discussionThreadsAndMergeCenter() {
		Integer boardId = boardDboMapper.selectByExample(null).stream()
				.map(BoardDbo::getBoardId).min(Integer::compareTo).orElseThrow();
		testQueryHelperMapper.grantBoardPermissionIfAbsent(boardId, 1);
		systemConfigService.set(SystemConfigService.Keys.CMS_DISCUSSION_BOARD_ID, boardId.toString());
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
		try {
			User user = new User();
			user.setUserId(userDboMapper.selectByExample(null).stream()
					.map(UserDbo::getUserId).min(Integer::compareTo).orElseThrow());
			Permission postPermission = new Permission();
			postPermission.setId(1);
			postPermission.setPermissionCode("ZFGC_USER");
			user.getPermissions().add(postPermission);

			ProjectViewDboExample ocarinaOfTime3d = new ProjectViewDboExample();
			ocarinaOfTime3d.createCriteria().andSlugEqualTo("ocarina-of-time-3d");
			Integer migratedCommentThread = projectViewDboMapper.selectByExample(ocarinaOfTime3d)
					.get(0).getThreadId();
			assertEquals(migratedCommentThread,
					projectService.startProjectDiscussion("ocarina-of-time-3d", user).getThreadId(),
					"projects with migrated comment threads must reuse them");

			ProjectViewDboExample unlinkedProjects = new ProjectViewDboExample();
			unlinkedProjects.createCriteria().andThreadIdIsNull();
			unlinkedProjects.setOrderByClause("content_entity_id");
			String unlinkedSlug = projectViewDboMapper.selectByExample(unlinkedProjects).get(0).getSlug();
			Project unlinked = projectService.startProjectDiscussion(unlinkedSlug, user);
			assertTrue(unlinked.getThreadId() != null, "start discussion should create and link a thread");
			assertEquals(unlinked.getThreadId(),
					projectService.startProjectDiscussion(unlinkedSlug, user).getThreadId(),
					"second call must reuse the existing thread");
			CurrentMessageDboExample discussionPosts = new CurrentMessageDboExample();
			discussionPosts.createCriteria().andThreadIdEqualTo(unlinked.getThreadId());
			discussionPosts.setOrderByClause("post_in_thread");
			List<CurrentMessageDbo> seededPosts = currentMessageDboMapper.selectByExample(discussionPosts);
			assertEquals(1, seededPosts.size(),
					"starting a discussion must seed exactly one post, and reusing it must not append");
			CurrentMessageDbo firstPost = seededPosts.get(0);
			assertTrue(firstPost.getMessageText().contains("[project="),
					"first post should link back to the project: " + firstPost.getMessageText());
			assertEquals(Integer.valueOf(1), firstPost.getPostInThread(),
					"first post must be 1-based so pagination can see it");

			List<MergeCandidate> candidates = migrationConflictService.getMergeCandidates();
			assertTrue(candidates.stream().noneMatch(c -> "Ocarina_of_Time".equals(c.targetSlug())),
					"corpus-linked articles are already adopted and must not resurface as candidates");
			MergeCandidate articleCandidate = candidates.stream()
					.filter(c -> c.sourceType() == ContentMergeSide.PROJECT
							&& c.targetType() == ContentMergeSide.WIKI_PAGE
							&& "ocarina-of-time-3d".equals(c.sourceSlug())
							&& "Ocarina_of_Time_3D".equals(c.targetSlug()))
					.findFirst().orElseThrow(() -> new AssertionError("expected Ocarina of Time 3D wiki candidate"));
			MergeCandidate duplicateProject = candidates.stream()
					.filter(c -> c.sourceType() == ContentMergeSide.PROJECT
							&& c.targetType() == ContentMergeSide.PROJECT)
					.findFirst().orElseThrow(() -> new AssertionError("expected duplicate-project candidate"));

			Integer entityPageId = projectViewByContentEntityId(articleCandidate.sourceId()).getWikiPageId();
			long pagesBeforeAdoption = wikiPageDboMapper.countByExample(null);
			migrationConflictService.apply(new MergeApplyRequest(ContentMergeSide.PROJECT, articleCandidate.sourceId(),
					ContentMergeSide.WIKI_PAGE, articleCandidate.targetId()));
			assertEquals(entityPageId, projectViewByContentEntityId(articleCandidate.sourceId()).getWikiPageId(),
					"the entity page stays the project's page");
			assertEquals(pagesBeforeAdoption, wikiPageDboMapper.countByExample(null),
					"adoption keeps both pages (article becomes redirect)");
			WikiPageDboExample adoptedArticle = new WikiPageDboExample();
			adoptedArticle.createCriteria().andRedirectToIsNotNull().andWikiPageIdEqualTo(articleCandidate.targetId());
			assertEquals("Ocarina_of_Time_3D", wikiPageDboMapper.selectByExample(adoptedArticle).get(0).getSlug(),
					"the article should redirect to the entity page");
			String adoptedContentParsed = projectService.getProject("ocarina-of-time-3d").getPage().getContentParsed();
			assertTrue(adoptedContentParsed.contains("Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
					"the project page should now render the adopted article content");
			assertFalse(adoptedContentParsed.contains("Testing It's"),
					"the adopted article must replace the pre-adoption entity content");

			Integer duplicatePageId = projectViewByContentEntityId(duplicateProject.sourceId()).getWikiPageId();
			Integer survivingPageId = projectViewByContentEntityId(duplicateProject.targetId()).getWikiPageId();
			ProjectScreenshotDbo seededScreenshot = new ProjectScreenshotDbo();
			seededScreenshot.setContentEntityId(duplicateProject.sourceId());
			seededScreenshot.setContentResourceId(contentResourceDboMapper.selectByExample(null).stream()
					.map(ContentResourceDbo::getContentResourceId).min(Integer::compareTo).orElseThrow());
			seededScreenshot.setCaption("re-point probe");
			seededScreenshot.setOrdinal(99);
			projectScreenshotDboMapper.insertSelective(seededScreenshot);
			ProjectDownloadDbo seededDownload = new ProjectDownloadDbo();
			seededDownload.setContentEntityId(duplicateProject.sourceId());
			seededDownload.setLabel("re-point probe");
			seededDownload.setUrl("https://example.invalid/re-point-probe.zip");
			seededDownload.setOrdinal(99);
			projectDownloadDboMapper.insertSelective(seededDownload);

			long sourceScreenshotsBefore = screenshotsOf(duplicateProject.sourceId());
			long targetScreenshotsBefore = screenshotsOf(duplicateProject.targetId());
			long sourceDownloadsBefore = downloadsOf(duplicateProject.sourceId());
			long targetDownloadsBefore = downloadsOf(duplicateProject.targetId());
			assertTrue(sourceScreenshotsBefore > 0,
					"the merged-away project must own screenshots, or the re-point check below proves nothing");
			assertTrue(sourceDownloadsBefore > 0,
					"the merged-away project must own downloads, or the re-point check below proves nothing");
			migrationConflictService.apply(new MergeApplyRequest(ContentMergeSide.PROJECT, duplicateProject.sourceId(),
					ContentMergeSide.PROJECT, duplicateProject.targetId()));
			assertEquals(5, projectDboMapper.countByExample(null), "duplicate project should be merged away");
			assertEquals(pagesBeforeAdoption, wikiPageDboMapper.countByExample(null),
					"merged project's page becomes a redirect, not a deletion");
			if (duplicatePageId != null && !duplicatePageId.equals(survivingPageId)) {
				assertNotNull(wikiPageDboMapper.selectByPrimaryKey(duplicatePageId).getRedirectTo(),
						"merged project's page should redirect to the surviving project's page");
			}
			assertEquals(0, screenshotsOf(duplicateProject.sourceId()),
					"source screenshots must leave the merged-away project");
			assertEquals(targetScreenshotsBefore + sourceScreenshotsBefore,
					screenshotsOf(duplicateProject.targetId()),
					"source screenshots must be re-pointed onto the survivor, not deleted");
			assertEquals(targetDownloadsBefore + sourceDownloadsBefore,
					downloadsOf(duplicateProject.targetId()),
					"source downloads must be re-pointed onto the survivor, not cascade-deleted with it");
		} finally {
			RequestContextHolder.resetRequestAttributes();
		}
	}

	@Test
	@Order(2)
	void corpusQuoteStripApplyStripsFaithfulQuotesEndToEnd() {
		assertTrue(quoteStripService.report(null).plannedRows() > 0,
				"the migrated corpus must contain at least one faithful strippable quote");

		UUID runId = UUID.randomUUID();
		QuoteStripOperations.QuoteStripReport planned = quoteStripService.report(runId);
		assertEquals("PLANNED", planned.status());
		assertTrue(planned.plannedRows() > 0, "the migrated corpus must yield at least one strippable row");
		assertTrue(planned.plannedQuotes() > 0, "the plan must count at least one faithful quote to strip");

		QuoteStripApplyResult applied = quoteStripService.apply(runId);
		assertEquals(0, applied.refused());
		assertTrue(applied.applied() > 0, "apply must strip at least one faithful quote in place");
		assertEquals("APPLIED", quoteStripService.report(runId).status());

		QuoteStripAuditDboExample appliedAudit = new QuoteStripAuditDboExample();
		appliedAudit.createCriteria().andRunIdEqualTo(runId).andStatusEqualTo("APPLIED");
		appliedAudit.setOrderByClause("message_history_id");
		QuoteStripAuditDbo auditRow = quoteStripAuditDboMapper.selectByExample(appliedAudit).get(0);
		String beforeText = auditRow.getBeforeText();
		String afterText = auditRow.getAfterText();
		assertTrue(afterText.length() < beforeText.length(),
				"the stripped body must have lost its embedded quote text");
		assertTrue(EMPTIED_QUOTE_SHELL.matcher(afterText).find(),
				"the emptied [quote msg=N][/quote] shell must remain in the stripped body");
		assertFalse(EMPTIED_QUOTE_SHELL.matcher(beforeText).find(),
				"the planned before-image must still carry the embedded quote text");
		assertEquals(afterText,
				messageHistoryDboMapper.selectByPrimaryKey(auditRow.getMessageHistoryId()).getMessageText(),
				"the live message_history body must match the applied after-image");
	}

	@Test
	@Order(3)
	void unsupportedMergePairsAreRefusedThroughTheAdminApiWithoutTouchingEitherEntity() throws Exception {
		List<ContentEntityDbo> projects = contentEntityDboMapper.selectByExample(projectEntities());
		Integer sourceId = projects.get(1).getContentEntityId();
		Integer targetId = projects.get(0).getContentEntityId();
		String adminToken = adminToken();

		refuseMerge(adminToken, "RESOURCE", sourceId, "PROJECT", targetId);
		refuseMerge(adminToken, "PROJECT", sourceId, "RESOURCE", targetId);
		refuseMerge(adminToken, "BOARD", sourceId, "PROJECT", targetId);

		assertNotNull(contentEntityDboMapper.selectByPrimaryKey(sourceId),
				"a refused pair must leave the source entity alone; a project-into-project merge deletes it");
		assertNotNull(contentEntityDboMapper.selectByPrimaryKey(targetId));
	}

	@Test
	@Order(4)
	void migrationConflictsResolveThroughTheAdminApiFromTheirPersistedCandidateJson() throws Exception {
		ContentEntityDbo project = contentEntityDboMapper.selectByExample(projectEntities()).get(0);
		MigrationConflictDboExample onThatProject = new MigrationConflictDboExample();
		onThatProject.createCriteria().andEntityTypeEqualTo("PROJECT")
				.andEntityIdEqualTo(project.getContentEntityId()).andFieldNameEqualTo("author_name");
		migrationConflictDboMapper.deleteByExample(onThatProject);
		MigrationConflictDbo seeded = new MigrationConflictDbo();
		seeded.setEntityType("PROJECT");
		seeded.setEntityId(project.getContentEntityId());
		seeded.setFieldName("author_name");
		seeded.setStatus("OPEN");
		seeded.setCandidates("[{\"sourceType\":\"CMS\",\"sourceRef\":\"project.author_name\","
				+ "\"value\":\"Cms Author\",\"label\":\"CMS record\"},"
				+ "{\"sourceType\":\"THREAD\",\"sourceRef\":\"thread:1\","
				+ "\"value\":\"Thread Author\",\"label\":\"Forum thread\"}]");
		migrationConflictDboMapper.insertSelective(seeded);
		String adminToken = adminToken();

		MvcResult listing = mockMvc.perform(get("/admin/migrate/conflicts")
				.header("Authorization", "Bearer " + adminToken))
				.andExpect(status().isOk())
				.andReturn();
		JsonNode conflict = null;
		for (JsonNode row : json.readTree(listing.getResponse().getContentAsString()))
			if (project.getContentEntityId().intValue() == row.get("entityId").asInt())
				conflict = row;
		assertNotNull(conflict, "the seeded conflict must come back from the admin list endpoint");
		assertEquals("author_name", conflict.get("fieldName").asString());
		assertEquals(project.getTitle(), conflict.get("entityLabel").asString());
		JsonNode candidates = conflict.get("candidates");
		assertEquals(2, candidates.size(),
				"candidate JSON written before the conflict DTOs became records must still deserialize: " + conflict);
		assertEquals("CMS", candidates.get(0).get("sourceType").asString());
		assertEquals("project.author_name", candidates.get(0).get("sourceRef").asString());
		assertEquals("Cms Author", candidates.get(0).get("value").asString());
		assertEquals("CMS record", candidates.get(0).get("label").asString());
		assertNotNull(conflict.get("detectedTs"), "the list view must still carry the detection timestamp");

		MvcResult resolution = mockMvc.perform(
				post("/admin/migrate/conflicts/" + conflict.get("id").asInt() + "/resolve")
						.header("Authorization", "Bearer " + adminToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"sourceType\":\"THREAD\"}"))
				.andExpect(status().isOk())
				.andReturn();
		JsonNode resolved = json.readTree(resolution.getResponse().getContentAsString());
		assertEquals("RESOLVED", resolved.get("status").asString());
		assertEquals("THREAD", resolved.get("resolvedSourceType").asString());
		assertEquals("Thread Author", resolved.get("resolvedValue").asString());
		assertEquals("Thread Author",
				contentEntityDboMapper.selectByPrimaryKey(project.getContentEntityId()).getAuthorName(),
				"resolving must write the chosen candidate's value onto the project");
	}

	private ContentEntityDboExample projectEntities() {
		ContentEntityDboExample projectEntities = new ContentEntityDboExample();
		projectEntities.createCriteria().andEntityTypeEqualTo("PROJECT");
		projectEntities.setOrderByClause("content_entity_id");
		return projectEntities;
	}

	private String adminToken() throws Exception {
		return login("test_admin", "adminpass123").get("accessToken").asString();
	}

	private void refuseMerge(String adminToken, String sourceType, Integer sourceId, String targetType,
			Integer targetId) throws Exception {
		mockMvc.perform(post("/admin/cms/merge")
				.header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"sourceType\":\"%s\",\"sourceId\":%d,\"targetType\":\"%s\",\"targetId\":%d}"
						.formatted(sourceType, sourceId, targetType, targetId)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@Order(5)
	void anUpperCaseQuoteOpenerStillReachesTheLiveSpliceGuard() {
		MessageHistoryDboExample quoteFreeCurrentRevision = new MessageHistoryDboExample();
		quoteFreeCurrentRevision.createCriteria().andCurrentFlagEqualTo(true).andMessageTextNotLike("%[quote%");
		quoteFreeCurrentRevision.setLimit(1);
		List<MessageHistoryDbo> quoteFreeHosts = messageHistoryDboMapper
				.selectByExampleWithLimits(quoteFreeCurrentRevision);
		assertFalse(quoteFreeHosts.isEmpty(),
				"the migrated corpus must offer a current revision carrying no quote markup to host the seeded quoter");
		MessageHistoryDbo displacedHostRevision = quoteFreeHosts.get(0);

		QuoteStripOperations.QuoteStripReport before = quoteStripService.report(null);
		long liveSpliceSourcesBefore = pregateCount(before, "live-splice-source");

		MessageHistoryDbo upperCaseQuoter = new MessageHistoryDbo();
		upperCaseQuoter.setMessageId(displacedHostRevision.getMessageId());
		upperCaseQuoter.setMessageText(
				"[QUOTE msg=" + displacedHostRevision.getMessageId() + "]legacy shouting[/QUOTE]");
		upperCaseQuoter.setCurrentFlag(true);
		upperCaseQuoter.setMigrationHash("upper-case-quote-opener");
		messageHistoryDboMapper.insertSelective(upperCaseQuoter);
		try {
			assertTrue(quoteStripConversionMapper.loadQuoterTimestampRows().stream()
					.anyMatch(quoter -> upperCaseQuoter.getMessageText().equals(quoter.getMessageText())),
					"a current revision whose opener is [QUOTE rather than [quote must still reach the quoter "
							+ "index, or the live-splice guard never learns the quoted post is spliced into it");

			QuoteStripOperations.QuoteStripReport after = quoteStripService.report(null);
			assertEquals(before.candidateRows() + 1, after.candidateRows(),
					"the upper-case opener must be collected as a strip candidate, so a lost pregate can only "
							+ "come from the quoter index and not from the candidate query");
			assertEquals(liveSpliceSourcesBefore + 1, pregateCount(after, "live-splice-source"),
					"the seeded quoter floors onto its own current revision, so that revision must be pregated "
							+ "as a live splice source instead of being offered up for stripping");
		} finally {
			messageHistoryDboMapper.deleteByPrimaryKey(upperCaseQuoter.getMessageHistoryId());
			MessageHistoryDbo restoredHostRevision = new MessageHistoryDbo();
			restoredHostRevision.setMessageHistoryId(displacedHostRevision.getMessageHistoryId());
			restoredHostRevision.setCurrentFlag(true);
			messageHistoryDboMapper.updateByPrimaryKeySelective(restoredHostRevision);
		}
	}

	private static long pregateCount(QuoteStripOperations.QuoteStripReport report, String pregateReason) {
		Long pregated = report.pregateHistogram().get(pregateReason);
		return pregated == null ? 0 : pregated;
	}

	@Nested
	@Order(4)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	class ConflictReview {

		@Test
		@Order(1)
		void bulkResolveCommitsEachItemSeparatelyEvenWhenASiblingPoisonsTheBatch() throws Exception {
			List<ContentEntityDbo> projects = contentEntityDboMapper.selectByExample(projectEntities());
			assertTrue(projects.size() >= 2, "this test needs two projects to conflict over");
			ContentEntityDbo winner = projects.get(0);
			ContentEntityDbo poisoned = projects.get(1);
			String survivingValue = "Bulk Winner " + UUID.randomUUID();
			int winnerConflict = seedConflict(winner.getContentEntityId(), twoCandidates());
			int poisonConflict = seedConflict(poisoned.getContentEntityId(), twoCandidates());
			String adminToken = adminToken();

			MvcResult bulk = mockMvc.perform(post("/admin/migrate/conflicts/resolve")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(("[{\"id\":%d,\"sourceType\":\"CUSTOM\",\"customValue\":\"%s\"},"
							+ "{\"id\":%d,\"sourceType\":\"CUSTOM\",\"customValue\":\"%s\"}]")
							.formatted(winnerConflict, survivingValue, poisonConflict, "x".repeat(200))))
					.andExpect(status().isOk())
					.andReturn();

			JsonNode outcomes = json.readTree(bulk.getResponse().getContentAsString());
			assertEquals(2, outcomes.size(), "every requested id must be accounted for: " + outcomes);
			assertTrue(outcomes.get(0).get("ok").asBoolean(), "the resolvable conflict should report ok");
			assertFalse(outcomes.get(1).get("ok").asBoolean(),
					"the oversize value must fail on its own instead of failing the batch");

			assertEquals(survivingValue,
					contentEntityDboMapper.selectByPrimaryKey(winner.getContentEntityId()).getAuthorName(),
					"a sibling's SQL failure must not roll back an item that already succeeded");
			assertEquals("RESOLVED", conflictById(winnerConflict).getStatus());
			assertEquals("OPEN", conflictById(poisonConflict).getStatus(),
					"the poisoned item must not be recorded as resolved");
		}

		@Test
		@Order(2)
		void bulkResolveNamesTheFailureForAnUnknownConflictId() throws Exception {
			ContentEntityDbo project = contentEntityDboMapper.selectByExample(projectEntities()).get(0);
			int conflictId = seedConflict(project.getContentEntityId(), twoCandidates());

			MvcResult bulk = mockMvc.perform(post("/admin/migrate/conflicts/resolve")
					.header("Authorization", "Bearer " + adminToken())
					.contentType(MediaType.APPLICATION_JSON)
					.content("[{\"id\":%d,\"sourceType\":\"THREAD\"},{\"id\":999999,\"sourceType\":\"CMS\"}]"
							.formatted(conflictId)))
					.andExpect(status().isOk())
					.andReturn();

			JsonNode outcomes = json.readTree(bulk.getResponse().getContentAsString());
			assertTrue(outcomes.get(0).get("ok").asBoolean());
			assertFalse(outcomes.get(1).get("ok").asBoolean());
			assertEquals("ZfgcNotFoundException", outcomes.get(1).get("error").asString(),
					"a failed row must name why it failed so the admin screen can show it");
		}

		@Test
		@Order(3)
		void reopeningClearsThePinWithoutRevertingWhatWasAlreadyWritten() throws Exception {
			ContentEntityDbo project = contentEntityDboMapper.selectByExample(projectEntities()).get(0);
			int conflictId = seedConflict(project.getContentEntityId(), twoCandidates());
			String adminToken = adminToken();
			mockMvc.perform(post("/admin/migrate/conflicts/" + conflictId + "/resolve")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"sourceType\":\"CMS\"}"))
					.andExpect(status().isOk());

			MvcResult reopened = mockMvc.perform(post("/admin/migrate/conflicts/" + conflictId + "/reopen")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andReturn();

			JsonNode view = json.readTree(reopened.getResponse().getContentAsString());
			assertEquals("OPEN", view.get("status").asString());
			assertEquals("Cms Author",
					contentEntityDboMapper.selectByPrimaryKey(project.getContentEntityId()).getAuthorName(),
					"reopening reconsiders the choice; it must not silently rewrite the entity");
			MigrationConflictDbo row = conflictById(conflictId);
			assertNull(row.getResolvedSourceType(), "reopening must clear the pinned source");
			assertNull(row.getResolvedValue(), "reopening must clear the pinned value");
			assertNull(row.getResolvedByUserId(), "reopening must clear who resolved it");
			assertNull(row.getResolvedTs(), "reopening must clear when it was resolved");
		}

		@Test
		@Order(4)
		void aConflictWhoseCandidateJsonCannotBeReadStillListsInsteadOfBreakingTheScreen() throws Exception {
			ContentEntityDbo project = contentEntityDboMapper.selectByExample(projectEntities()).get(0);
			int conflictId = seedConflict(project.getContentEntityId(), "this is not candidate json");
			String adminToken = adminToken();

			MvcResult listing = mockMvc.perform(get("/admin/migrate/conflicts")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andReturn();

			JsonNode damaged = null;
			for (JsonNode row : json.readTree(listing.getResponse().getContentAsString()))
				if (conflictId == row.get("id").asInt()) damaged = row;
			assertNotNull(damaged, "one unreadable row must not take the whole conflict list down");
			assertEquals(0, damaged.get("candidates").size(),
					"an unreadable row should surface with no candidates rather than throw");

			mockMvc.perform(post("/admin/migrate/conflicts/" + conflictId + "/resolve")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"sourceType\":\"CMS\"}"))
					.andExpect(status().isBadRequest());

			MigrationConflictDboExample seeded = new MigrationConflictDboExample();
			seeded.createCriteria().andMigrationConflictIdEqualTo(conflictId);
			migrationConflictDboMapper.deleteByExample(seeded);
		}

		private String twoCandidates() {
			return "[{\"sourceType\":\"CMS\",\"sourceRef\":\"project.author_name\","
					+ "\"value\":\"Cms Author\",\"label\":\"CMS record\"},"
					+ "{\"sourceType\":\"THREAD\",\"sourceRef\":\"thread:1\","
					+ "\"value\":\"Thread Author\",\"label\":\"Forum thread\"}]";
		}

		private int seedConflict(Integer entityId, String candidates) {
			MigrationConflictDboExample existing = new MigrationConflictDboExample();
			existing.createCriteria().andEntityTypeEqualTo("PROJECT").andEntityIdEqualTo(entityId)
					.andFieldNameEqualTo("author_name");
			migrationConflictDboMapper.deleteByExample(existing);
			MigrationConflictDbo seeded = new MigrationConflictDbo();
			seeded.setEntityType("PROJECT");
			seeded.setEntityId(entityId);
			seeded.setFieldName("author_name");
			seeded.setStatus("OPEN");
			seeded.setCandidates(candidates);
			migrationConflictDboMapper.insertSelective(seeded);
			return migrationConflictDboMapper.selectByExample(existing).get(0).getMigrationConflictId();
		}

		private MigrationConflictDbo conflictById(int id) {
			return migrationConflictDboMapper.selectByPrimaryKey(id);
		}
	}

	@Nested
	@Order(1)
	class StatementTimeout {

		@Autowired
		@Qualifier("sqlSessionFactory")
		private SqlSessionFactory migrationSqlSessionFactory;

		@Autowired
		@Qualifier("smfSqlSessionFactory")
		private SqlSessionFactory legacySourceSqlSessionFactory;

		@Test
		void migrationJobsRunWithoutTheRequestPathStatementTimeout() {
			assertNull(migrationSqlSessionFactory.getConfiguration().getDefaultStatementTimeout(),
					"the migrator builds its own SqlSessionFactory, so a migration statement that "
							+ "runs for minutes must never inherit the request-path timeout");
			assertNull(legacySourceSqlSessionFactory.getConfiguration().getDefaultStatementTimeout(),
					"reads from the legacy SMF/CMS/wiki source must run unbounded");
			assertNull(migrationSqlSessionFactory.getConfiguration().getMappedStatement(
					"com.zfgc.zfgbb.mappers.MessageDboMapper.insertSelective").getTimeout(),
					"the generated mappers the converters write through must carry no timeout");
		}
	}

	@Nested
	@Order(1)
	class Lease {

		@Test
		void expiredOwnerCanBeTakenOverWithoutAbaCompletion() {
			UUID runId = UUID.randomUUID();
			UUID staleOwner = UUID.randomUUID();
			OffsetDateTime now = OffsetDateTime.now();
			insertApplyingRun(runId, staleOwner, now.minusMinutes(1), now.minusMinutes(1), 4);

			QuoteStripApplyResult result = quoteStripService.apply(runId);

			assertEquals(0, result.refused());
			QuoteStripRunDbo takenOver = quoteStripRun(runId);
			assertEquals("APPLIED", takenOver.getStatus());
			assertEquals(Integer.valueOf(5), takenOver.getAttemptNo());
			QuoteStripRunDboExample stillLeased = new QuoteStripRunDboExample();
			stillLeased.createCriteria().andRunIdEqualTo(runId).andLeaseOwnerIsNotNull();
			assertEquals(0, quoteStripRunDboMapper.countByExample(stillLeased));
		}

		@Test
		void liveLeaseRefusesTakeoverAndEvenForcedPurge() {
			UUID runId = UUID.randomUUID();
			UUID owner = UUID.randomUUID();
			insertApplyingRun(runId, owner, OffsetDateTime.now().plusMinutes(2), OffsetDateTime.now(), 1);

			assertEquals(1, quoteStripService.apply(runId).refused());
			assertTrue(quoteStripService.purge(runId, true).refused());
			assertEquals(owner.toString(), String.valueOf(quoteStripRun(runId).getLeaseOwner()));

			QuoteStripRunDbo expireLease = new QuoteStripRunDbo();
			expireLease.setRunId(runId);
			expireLease.setLeaseExpiresTs(OffsetDateTime.now().minusMinutes(1));
			quoteStripRunDboMapper.updateByPrimaryKeySelective(expireLease);
			assertFalse(quoteStripService.purge(runId, true).refused());
			QuoteStripRunDboExample purgedRun = new QuoteStripRunDboExample();
			purgedRun.createCriteria().andRunIdEqualTo(runId);
			assertEquals(0, quoteStripRunDboMapper.countByExample(purgedRun));
		}

		private QuoteStripRunDbo quoteStripRun(UUID runId) {
			QuoteStripRunDboExample example = new QuoteStripRunDboExample();
			example.createCriteria().andRunIdEqualTo(runId);
			return quoteStripRunDboMapper.selectByExample(example).get(0);
		}

		private void insertApplyingRun(UUID runId, UUID leaseOwner, OffsetDateTime leaseExpiresTs,
				OffsetDateTime heartbeatTs, int attemptNo) {
			QuoteStripRunDbo run = new QuoteStripRunDbo();
			run.setRunId(runId);
			run.setStatus("APPLYING");
			run.setLeaseOwner(leaseOwner);
			run.setLeaseExpiresTs(leaseExpiresTs);
			run.setHeartbeatTs(heartbeatTs);
			run.setAttemptNo(attemptNo);
			quoteStripRunDboMapper.insertSelective(run);
		}
	}

	@Nested
	@Order(9)
	class DatabaseRestore {

		@Autowired
		private PostgresBackupTool postgres;

		@Autowired
		private RestoreService restoreService;

		@Autowired
		private OperationStorageService operationStorage;

		@Autowired
		private InstallService installService;

		@Autowired
		private BackupRestoreService installabilityClassifier;

		@Autowired
		private AuthService authService;

		@Autowired
		private ZfgcPasswordEncoder passwordEncoder;

		@Test
		@Order(1)
		void dumpRestoresOntoTheLiveMigratedSchema() throws Exception {
			long threadsBefore = threadDboMapper.countByExample(null);
			long messagesBefore = messageDboMapper.countByExample(null);
			long wikiPagesBefore = wikiPageDboMapper.countByExample(null);
			String probeName = "probe-" + UUID.randomUUID().toString().substring(0, 8);

			Path dump = Files.createTempDirectory("zfgbb-restore-probe").resolve("database.dump");
			postgres.dump(dump);

			insertProbeCategory(probeName);
			assertEquals(1, categoryDboMapper.countByExample(categoriesNamed(probeName)));

			postgres.restore(dump);

			assertEquals(0, categoryDboMapper.countByExample(categoriesNamed(probeName)),
					"restore must roll the schema back to the dumped state");
			assertEquals(threadsBefore, threadDboMapper.countByExample(null));
			assertEquals(messagesBefore, messageDboMapper.countByExample(null));
			assertEquals(wikiPagesBefore, wikiPageDboMapper.countByExample(null));
			assertTrue(testSystemInfoMapper.countFlywaySchemaHistoryRows() > 0,
					"restore must preserve the flyway history the archive carries");
		}

		@Test
		@Order(2)
		void archiveRestoreAcceptsAnArchiveBuiltAtTheApplicationSchemaVersion() throws Exception {
			long threadsBefore = threadDboMapper.countByExample(null);
			long messagesBefore = messageDboMapper.countByExample(null);
			String probeName = "probe-" + UUID.randomUUID().toString().substring(0, 8);
			String liveSchemaVersion = postgres.metadata().schemaVersion();
			assertEquals(postgres.expectedSchemaVersion(), liveSchemaVersion,
					"a freshly migrated database must sit at the version the application expects");
			assertEquals(liveSchemaVersion, postgres.requireDatabaseAtExpectedSchemaVersion(),
					"the restore guard must accept the database that stamped the archive");
			Path archive = writeArchive(liveSchemaVersion);

			insertProbeCategory(probeName);
			assertEquals(1, categoryDboMapper.countByExample(categoriesNamed(probeName)));

			restoreService.restoreArchiveWithoutMaintenanceLease(archive);

			assertEquals(0, categoryDboMapper.countByExample(categoriesNamed(probeName)),
					"a matching archive must be accepted and applied");
			assertEquals(threadsBefore, threadDboMapper.countByExample(null));
			assertEquals(messagesBefore, messageDboMapper.countByExample(null));
		}

		@Test
		@Order(3)
		void archiveRestoreRejectsAnArchiveBuiltAtAnotherSchemaVersion() throws Exception {
			String expectedSchemaVersion = postgres.expectedSchemaVersion();
			String probeName = "probe-" + UUID.randomUUID().toString().substring(0, 8);
			Path archive = writeArchive("19700101.1");

			insertProbeCategory(probeName);
			long threadsBefore = threadDboMapper.countByExample(null);
			long messagesBefore = messageDboMapper.countByExample(null);

			ZfgcInvalidRequestException rejected = assertThrows(ZfgcInvalidRequestException.class,
					() -> restoreService.restoreArchiveWithoutMaintenanceLease(archive));

			assertTrue(rejected.getMessage().contains("19700101.1"),
					"the failure must name the archive schema version: " + rejected.getMessage());
			assertTrue(rejected.getMessage().contains(expectedSchemaVersion),
					"the failure must name the expected schema version: " + rejected.getMessage());
			assertEquals(1, categoryDboMapper.countByExample(categoriesNamed(probeName)),
					"a drifted archive must be refused before anything destructive happens");
			assertEquals(threadsBefore, threadDboMapper.countByExample(null));
			assertEquals(messagesBefore, messageDboMapper.countByExample(null));
			assertEquals(expectedSchemaVersion, postgres.metadata().schemaVersion(),
					"the refused restore must leave the live schema history untouched");
			categoryDboMapper.deleteByExample(categoriesNamed(probeName));
		}

		@Test
		@Order(4)
		void reconciliationRefusesAnAmbiguousUserNameAndAnEmailHeldByAnother() {
			int administratorUserId = findUserIdByName("test_admin");
			String userNameHeldByAnother = testQueryHelperMapper.findUsableUserNameOtherThan(administratorUserId);
			String emailHeldByAnother = testQueryHelperMapper.findEmailAddressHeldByAnother(administratorUserId);
			assertNotNull(userNameHeldByAnother, "the restored corpus must contain another usable username");
			assertNotNull(emailHeldByAnother, "the restored corpus must contain another usable email address");

			UserDbo ambiguousTwin = new UserDbo();
			ambiguousTwin.setSsoKey(userNameHeldByAnother + "__ambiguous");
			ambiguousTwin.setUserName(userNameHeldByAnother);
			ambiguousTwin.setDisplayName("Ambiguous Twin");
			ambiguousTwin.setActiveFlag(true);
			ambiguousTwin.setFailedLoginCount(0);
			userDboMapper.insertSelective(ambiguousTwin);
			try {
				ZfgcInvalidRequestException ambiguousUserName = assertThrows(
						ZfgcInvalidRequestException.class,
						() -> installService.reconcileAdministratorIdentity(
								installRequest(userNameHeldByAnother, "Site Owner",
										"site_owner@example.invalid", "ownerpass456"),
								administratorUserId));
				assertTrue(ambiguousUserName.getMessage().contains("Username already taken"),
						"a username held by more than one restored account must never be adopted: "
								+ ambiguousUserName.getMessage());
			} finally {
				UserDboExample ambiguousTwinRow = new UserDboExample();
				ambiguousTwinRow.createCriteria().andSsoKeyEqualTo(userNameHeldByAnother + "__ambiguous");
				userDboMapper.deleteByExample(ambiguousTwinRow);
			}

			ZfgcInvalidRequestException takenEmail = assertThrows(ZfgcInvalidRequestException.class,
					() -> installService.reconcileAdministratorIdentity(
							installRequest("site_owner", "Site Owner", emailHeldByAnother, "ownerpass456"),
							administratorUserId));
			assertTrue(takenEmail.getMessage().contains("Email already registered"),
					"the refusal must name the collision: " + takenEmail.getMessage());

			assertEquals("test_admin", userDboMapper.selectByPrimaryKey(administratorUserId).getUserName(),
					"a refused reconciliation must leave the administrator identity alone");
			assertEquals("test_admin@example.invalid", administratorEmail(administratorUserId));
			assertNull(findUserIdByName("site_owner"),
					"a refused reconciliation must not create a second administrator");
			assertEquals(Integer.valueOf(administratorUserId),
					authService.reauthenticate("test_admin", "adminpass123").getUserId(),
					"the generation-time administrator must still authenticate after a refusal");
		}

		@Test
		@Order(5)
		void reconciliationRewritesTheRestoredAdministratorIdentity() {
			int administratorUserId = findUserIdByName("test_admin");
			long usersBefore = userDboMapper.countByExample(null);
			assertEquals(1, siteAdminGrants(administratorUserId),
					"the restored administrator must hold the site administrator grant to begin with");

			User administrator = installService.reconcileAdministratorIdentity(
					installRequest("site_owner", "Site Owner", "site_owner@example.invalid", "ownerpass456"),
					administratorUserId);

			assertEquals(Integer.valueOf(administratorUserId), administrator.getUserId());
			assertEquals(usersBefore, userDboMapper.countByExample(null),
					"reconciliation must rewrite the administrator instead of adding one");
			UserDbo reconciled = userDboMapper.selectByPrimaryKey(administratorUserId);
			assertEquals("site_owner", reconciled.getUserName());
			assertEquals("site_owner", reconciled.getSsoKey());
			assertEquals("Site Owner", reconciled.getDisplayName());
			assertEquals("site_owner@example.invalid", administratorEmail(administratorUserId));
			assertNull(findUserIdByName("test_admin"),
					"the generation-time administrator identity must be gone");
			assertEquals(1, siteAdminGrants(administratorUserId),
					"the site administrator grant must survive reconciliation");

			assertTrue(passwordEncoder.verify("ownerpass456", reconciled.getPasswordHash(),
					PasswordAlgo.valueOf(reconciled.getPasswordAlgo()), reconciled.getPasswordSalt()),
					"the requested password must verify against the stored credential");
			assertEquals(Integer.valueOf(administratorUserId),
					authService.reauthenticate("site_owner", "ownerpass456").getUserId(),
					"the requested password must authenticate through the real login path");
			assertThrows(AuthenticationException.class,
					() -> authService.reauthenticate("test_admin", "adminpass123"),
					"the generation-time administrator credentials must stop working");
		}

		@Test
		@Order(6)
		void reconciliationRefusesTheSentinelAndEveryNonAnchorUser() {
			int administratorUserId = findUserIdByName("site_owner");
			UserDboExample nonAnchorUsers = new UserDboExample();
			nonAnchorUsers.createCriteria().andUserIdGreaterThan(0).andUserIdNotEqualTo(administratorUserId);
			nonAnchorUsers.setOrderByClause("user_id");
			int nonAnchorUserId = userDboMapper.selectByExample(nonAnchorUsers).get(0).getUserId();
			UserDboExample sentinel = new UserDboExample();
			sentinel.createCriteria().andUserIdEqualTo(0).andSsoKeyEqualTo("__deleted__");
			assertEquals(1, userDboMapper.countByExample(sentinel),
					"the anonymization sentinel must be present to begin with");

			ZfgcInvalidRequestException sentinelRefusal = assertThrows(ZfgcInvalidRequestException.class,
					() -> installService.reconcileAdministratorIdentity(
							installRequest("site_owner", "Site Owner", "site_owner@example.invalid",
									"ownerpass456"),
							0));
			assertTrue(sentinelRefusal.getMessage().contains("real user account"),
					"the refusal must name the guard: " + sentinelRefusal.getMessage());
			ZfgcInvalidRequestException negative = assertThrows(ZfgcInvalidRequestException.class,
					() -> installService.reconcileAdministratorIdentity(
							installRequest("site_owner", "Site Owner", "site_owner@example.invalid",
									"ownerpass456"),
							-1));
			assertTrue(negative.getMessage().contains("real user account"),
					"the refusal must name the guard: " + negative.getMessage());
			ZfgcInvalidRequestException nonAnchor = assertThrows(ZfgcInvalidRequestException.class,
					() -> installService.reconcileAdministratorIdentity(
							installRequest("site_owner", "Site Owner", "site_owner@example.invalid",
									"ownerpass456"),
							nonAnchorUserId));
			assertTrue(nonAnchor.getMessage().contains("not the restored site administrator anchor"),
					"the refusal must name the guard: " + nonAnchor.getMessage());

			UserDboExample untouchedSentinel = new UserDboExample();
			untouchedSentinel.createCriteria().andUserIdEqualTo(0).andSsoKeyEqualTo("__deleted__")
					.andUserNameEqualTo("__deleted__").andDisplayNameEqualTo("[deleted]")
					.andActiveFlagEqualTo(false).andPasswordHashIsNull().andPasswordAlgoIsNull()
					.andPasswordSaltIsNull();
			assertEquals(1, userDboMapper.countByExample(untouchedSentinel),
					"the refused reconciliation must leave the sentinel untouched");
			assertEquals(0, siteAdminGrants(0), "the sentinel must never receive a site admin grant");
			assertNull(userContactInfoDboMapper.selectByPrimaryKey(0),
					"the sentinel must not acquire an email address");
			assertEquals(1, siteAdminGrants(administratorUserId));
			assertEquals(Integer.valueOf(administratorUserId),
					authService.reauthenticate("site_owner", "ownerpass456").getUserId(),
					"the real administrator must still authenticate after the refusals");
		}

		@Test
		@Order(7)
		void reconciliationAdoptsAnUnreferencedEmailAddressRow() {
			int administratorUserId = findUserIdByName("site_owner");
			String unreferencedAddress = "orphaned_owner@example.invalid";
			EmailAddressDbo unreferenced = new EmailAddressDbo();
			unreferenced.setEmailAddress(unreferencedAddress);
			unreferenced.setSpammerFlag(false);
			emailAddressDboMapper.insertSelective(unreferenced);
			EmailAddressDboExample unreferencedRows = new EmailAddressDboExample();
			unreferencedRows.createCriteria().andEmailAddressEqualTo(unreferencedAddress);
			int unreferencedEmailAddressId = emailAddressDboMapper.selectByExample(unreferencedRows)
					.get(0).getEmailAddressId();
			UserContactInfoDboExample unreferencedOwners = new UserContactInfoDboExample();
			unreferencedOwners.createCriteria().andEmailAddressIdEqualTo(unreferencedEmailAddressId);
			assertEquals(0, userContactInfoDboMapper.countByExample(unreferencedOwners),
					"the row must start out owned by nobody");

			User administrator = installService.reconcileAdministratorIdentity(
					installRequest("site_owner", "Site Owner", unreferencedAddress, "ownerpass456"),
					administratorUserId);

			assertEquals(Integer.valueOf(administratorUserId), administrator.getUserId());
			assertEquals(unreferencedAddress, administratorEmail(administratorUserId));
			assertEquals(Integer.valueOf(unreferencedEmailAddressId), contactEmailAddressId(administratorUserId),
					"the unreferenced row must be adopted rather than duplicated");
			assertEquals(1, emailAddressDboMapper.countByExample(unreferencedRows));
		}

		@Test
		@Order(8)
		void siteAdminGrantsAreUniqueAndReconciliationStaysIdempotent() {
			int administratorUserId = findUserIdByName("site_owner");
			assertEquals(1, testSystemInfoMapper.countIndexesOnTable("zfgbb", "br_user_permission",
					"ux_br_user_permission_user_id_user_permission_id"),
					"the duplicate-grant backstop index must exist");

			BrUserPermissionDboExample grantsHeld = new BrUserPermissionDboExample();
			grantsHeld.createCriteria().andUserIdEqualTo(administratorUserId);
			BrUserPermissionDbo duplicateGrant = brUserPermissionDboMapper.selectByExample(grantsHeld).get(0);
			RuntimeException duplicate = assertThrows(RuntimeException.class,
					() -> brUserPermissionDboMapper.insertSelective(duplicateGrant));
			assertTrue(duplicate.getMessage()
					.contains("ux_br_user_permission_user_id_user_permission_id"),
					"the database must reject the duplicate grant: " + duplicate.getMessage());

			installService.reconcileAdministratorIdentity(
					installRequest("site_owner", "Site Owner", "orphaned_owner@example.invalid",
							"ownerpass456"),
					administratorUserId);

			assertEquals(1, siteAdminGrants(administratorUserId),
					"a repeated reconciliation must not duplicate the grant");
			assertEquals(Integer.valueOf(administratorUserId),
					authService.reauthenticate("site_owner", "ownerpass456").getUserId());
		}

		@Test
		@Order(9)
		void installingFromACorpusArchiveRestoresTheCorpusOntoTheRequestedAdministrator()
				throws Exception {
			BackupRestoreService.ArchiveInstallability classification = installabilityClassifier
					.classifyInstallability(contentTarget);
			assertTrue(classification.compatible(),
					() -> "the migrated corpus must be shippable as installation content: "
							+ classification.reason());
			int anchorAdministratorId = findUserIdByName("site_owner");
			assertEquals(Integer.valueOf(anchorAdministratorId),
					classification.anchorAdministratorId());
			writeContentPackArchive(postgres.metadata().schemaVersion(), classification);

			long threadsBefore = threadDboMapper.countByExample(null);
			long messagesBefore = messageDboMapper.countByExample(null);
			long wikiPagesBefore = wikiPageDboMapper.countByExample(null);
			long usersBefore = userDboMapper.countByExample(null);
			String probeName = "probe-" + UUID.randomUUID().toString().substring(0, 8);
			insertProbeCategory(probeName);
			resetInstallRunToReady();

			InstallResult installed = installService.install(new InstallRequest("corpus_owner",
					"Corpus Owner", "corpus_owner@example.invalid", "corpuspass789",
					"Restored Corpus", true, false, true, null));

			assertEquals(Integer.valueOf(anchorAdministratorId), installed.response().adminUserId());
			assertEquals("Restored Corpus", installed.response().siteName());
			assertEquals(0, categoryDboMapper.countByExample(categoriesNamed(probeName)),
					"installing from the archive must restore over whatever the database held");
			assertEquals(threadsBefore, threadDboMapper.countByExample(null));
			assertEquals(messagesBefore, messageDboMapper.countByExample(null));
			assertEquals(wikiPagesBefore, wikiPageDboMapper.countByExample(null));
			assertEquals(usersBefore, userDboMapper.countByExample(null),
					"the restored corpus keeps its members and gains no new administrator");
			assertEquals("corpus_owner", userDboMapper.selectByPrimaryKey(anchorAdministratorId).getUserName());
			assertNull(findUserIdByName("site_owner"));
			assertEquals(1, siteAdminGrants(anchorAdministratorId));
			assertEquals(0, userRefreshTokenDboMapper.countByExample(null),
					"installing from the archive must not import generation-time sessions");
			InstallRunDboExample corpusInstall = new InstallRunDboExample();
			corpusInstall.createCriteria().andInstallIdEqualTo((short) 1).andStateEqualTo("INSTALLED")
					.andSiteNameEqualTo("Restored Corpus")
					.andProvisionRecycleBinEqualTo(true).andAdminUserIdEqualTo(anchorAdministratorId)
					.andLastErrorIsNull();
			assertEquals(1, installRunDboMapper.countByExample(corpusInstall));
			assertEquals("Restored Corpus", systemConfigValue("site_name"));
			assertEquals(String.valueOf(anchorAdministratorId), systemConfigValue("installed_by_user_id"));
			assertEquals("true", systemConfigValue("installed"));
			assertRecycleBinProvisioned();

			assertEquals(Integer.valueOf(anchorAdministratorId),
					authService.reauthenticate("corpus_owner", "corpuspass789").getUserId(),
					"the requested administrator must authenticate through the real login path");
			assertThrows(AuthenticationException.class,
					() -> authService.reauthenticate("site_owner", "ownerpass456"),
					"the generation-time administrator credentials must stop working");
		}

		@Test
		@Order(10)
		void installingAsARestoredCorpusMemberAdoptsThatMemberAndRetiresTheAnchor() {
			int anchorAdministratorId = findUserIdByName("corpus_owner");
			String adoptedUserName = testQueryHelperMapper.findMostProlificMember(0, anchorAdministratorId);
			assertNotNull(adoptedUserName, "the corpus must contain an adoptable member");
			int adoptedUserId = findUserIdByName(adoptedUserName);
			MessageDboExample messagesOwned = new MessageDboExample();
			messagesOwned.createCriteria().andOwnerIdEqualTo(adoptedUserId);
			ThreadDboExample threadsOwned = new ThreadDboExample();
			threadsOwned.createCriteria().andCreatedUserIdEqualTo(adoptedUserId);
			PersonalMessageDboExample personalMessagesSent = new PersonalMessageDboExample();
			personalMessagesSent.createCriteria().andSenderUserIdEqualTo(adoptedUserId);
			long messagesOwnedBeforeAdoption = messageDboMapper.countByExample(messagesOwned);
			long threadsOwnedBeforeAdoption = threadDboMapper.countByExample(threadsOwned);
			long personalMessagesSentBeforeAdoption = personalMessageDboMapper.countByExample(personalMessagesSent);
			long usersBeforeAdoption = userDboMapper.countByExample(null);
			assertTrue(messagesOwnedBeforeAdoption > 0, "the adopted member must already own posts");
			assertTrue(threadsOwnedBeforeAdoption > 0, "the adopted member must already own threads");
			resetInstallRunToReady();

			InstallResult installed = installService.install(new InstallRequest(adoptedUserName,
					"Adopted Owner", "adopted_owner@example.invalid", "adoptedpass789",
					"Adopted Site", true, false, true, null));

			assertEquals(Integer.valueOf(adoptedUserId), installed.response().adminUserId(),
					"the installation must report the adopted member as its administrator");
			assertEquals(usersBeforeAdoption, userDboMapper.countByExample(null),
					"adoption must reuse the corpus account instead of adding an administrator");
			UserDbo adopted = userDboMapper.selectByPrimaryKey(adoptedUserId);
			assertEquals(adoptedUserName, adopted.getUserName());
			assertEquals(adoptedUserName, adopted.getSsoKey());
			assertEquals("Adopted Owner", adopted.getDisplayName());
			assertEquals("adopted_owner@example.invalid", administratorEmail(adoptedUserId));
			assertEquals(1, siteAdminGrants(adoptedUserId),
					"the adopted member must hold exactly one site administrator grant");
			assertEquals(Integer.valueOf(adoptedUserId),
					authService.reauthenticate(adoptedUserName, "adoptedpass789").getUserId(),
					"the requested password must authenticate through the real login path");
			assertEquals(messagesOwnedBeforeAdoption, messageDboMapper.countByExample(messagesOwned),
					"the adopted member keeps their posts");
			assertEquals(threadsOwnedBeforeAdoption, threadDboMapper.countByExample(threadsOwned),
					"the adopted member keeps their threads");
			assertEquals(personalMessagesSentBeforeAdoption,
					personalMessageDboMapper.countByExample(personalMessagesSent),
					"the adopted member keeps their personal messages");
			InstallRunDboExample adoptedInstall = new InstallRunDboExample();
			adoptedInstall.createCriteria().andInstallIdEqualTo((short) 1).andStateEqualTo("INSTALLED")
					.andAdminUserIdEqualTo(adoptedUserId).andLastErrorIsNull();
			assertEquals(1, installRunDboMapper.countByExample(adoptedInstall));

			UserDboExample anonymizedAnchor = new UserDboExample();
			anonymizedAnchor.createCriteria().andUserIdEqualTo(anchorAdministratorId)
					.andUserNameEqualTo("[deleted]").andDisplayNameEqualTo("[deleted]")
					.andSsoKeyEqualTo("__deleted__" + anchorAdministratorId).andActiveFlagEqualTo(false)
					.andPasswordHashIsNull().andPasswordAlgoIsNull().andPasswordSaltIsNull();
			assertEquals(1, userDboMapper.countByExample(anonymizedAnchor),
					"the superseded generation anchor must be anonymized in place, not deleted");
			assertEquals(0, siteAdminGrants(anchorAdministratorId),
					"the superseded generation anchor must not remain a site administrator");
			assertNull(userContactInfoDboMapper.selectByPrimaryKey(anchorAdministratorId),
					"the superseded generation anchor must release its email address");
			assertThrows(AuthenticationException.class,
					() -> authService.reauthenticate("site_owner", "ownerpass456"),
					"the anchor the archive shipped must not stay a second credentialed administrator");
		}

		@Test
		@Order(11)
		void adoptingAMemberWhoSharesAnEmailAddressRowLeavesTheOtherOwnerIntact() {
			int administratorUserId = installRunDboMapper.selectByPrimaryKey((short) 1).getAdminUserId();
			List<Integer> members = testQueryHelperMapper
					.findMembersHoldingAnEmailAddressOtherThan(administratorUserId);
			assertTrue(members.size() >= 2,
					"the corpus must hold two members with email addresses to share one");
			int adoptedUserId = members.get(0);
			int emailSharingUserId = members.get(1);
			String adoptedUserName = userDboMapper.selectByPrimaryKey(adoptedUserId).getUserName();
			int sharedEmailAddressId = contactEmailAddressId(emailSharingUserId);
			String sharedAddress = emailAddressDboMapper.selectByPrimaryKey(sharedEmailAddressId).getEmailAddress();
			UserContactInfoDbo shareTheRow = new UserContactInfoDbo();
			shareTheRow.setUserId(adoptedUserId);
			shareTheRow.setEmailAddressId(sharedEmailAddressId);
			userContactInfoDboMapper.updateByPrimaryKeySelective(shareTheRow);
			UserContactInfoDboExample sharingOwners = new UserContactInfoDboExample();
			sharingOwners.createCriteria().andEmailAddressIdEqualTo(sharedEmailAddressId);
			assertEquals(2, userContactInfoDboMapper.countByExample(sharingOwners),
					"the adopted member must start out sharing another member's email address row");

			User administrator = installService.reconcileAdministratorIdentity(
					installRequest(adoptedUserName, "Shared Email Owner",
							"shared_owner@example.invalid", "sharedpass789"),
					administratorUserId);

			assertEquals(Integer.valueOf(adoptedUserId), administrator.getUserId());
			assertEquals("shared_owner@example.invalid", administratorEmail(adoptedUserId));
			EmailAddressDboExample requestedAddress = new EmailAddressDboExample();
			requestedAddress.createCriteria().andEmailAddressEqualTo("shared_owner@example.invalid");
			assertEquals(1, emailAddressDboMapper.countByExample(requestedAddress),
					"the requested address must exist exactly once");
			assertEquals(sharedAddress, administratorEmail(emailSharingUserId),
					"the other owner of the shared row keeps its address");
			assertEquals(Integer.valueOf(sharedEmailAddressId), contactEmailAddressId(emailSharingUserId));
			assertNotEquals(Integer.valueOf(sharedEmailAddressId), contactEmailAddressId(adoptedUserId),
					"the adopted member must move off the shared row instead of rewriting it");
			assertEquals(1, siteAdminGrants(adoptedUserId));
			assertEquals(Integer.valueOf(adoptedUserId),
					authService.reauthenticate(adoptedUserName, "sharedpass789").getUserId());
			UserDboExample supersededAdministrator = new UserDboExample();
			supersededAdministrator.createCriteria().andUserIdEqualTo(administratorUserId)
					.andUserNameEqualTo("[deleted]").andPasswordHashIsNull();
			assertEquals(1, userDboMapper.countByExample(supersededAdministrator),
					"the superseded administrator must be anonymized in place");
			assertEquals(0, siteAdminGrants(administratorUserId));
		}

		private void writeContentPackArchive(String schemaVersion,
				BackupRestoreService.ArchiveInstallability classification) throws Exception {
			Path workspace = Files.createTempDirectory("zfgbb-sample-data-archive");
			Path dump = workspace.resolve("database.dump");
			PostgresBackupTool.DatabaseMetadata metadata = postgres.dump(dump);
			Path destination = sampleArchive;
			Files.createDirectories(destination.getParent());
			new BackupArchiveWriter(operationStorage.limits()).write(
					new BackupArchiveWriter.Request(dump, contentTarget, "test", schemaVersion,
							metadata.serverMajor(), metadata.dumpToolVersion(),
							classification.compatible(), classification.anchorAdministratorId(),
							Instant.now()),
					destination);
		}

		private InstallRequest installRequest(String userName, String displayName, String email,
				String password) {
			return new InstallRequest(userName, displayName, email, password, "ZFGC Test", null,
					false, false, null);
		}

		private CategoryDboExample categoriesNamed(String categoryName) {
			CategoryDboExample named = new CategoryDboExample();
			named.createCriteria().andCategoryNameEqualTo(categoryName);
			return named;
		}

		private void insertProbeCategory(String categoryName) {
			CategoryDbo probe = new CategoryDbo();
			probe.setCategoryName(categoryName);
			categoryDboMapper.insertSelective(probe);
		}

		private Integer contactEmailAddressId(int userId) {
			return userContactInfoDboMapper.selectByPrimaryKey(userId).getEmailAddressId();
		}

		private String systemConfigValue(String configKey) {
			return systemConfigDboMapper.selectByPrimaryKey(configKey).getConfigValue();
		}

		private void resetInstallRunToReady() {
			InstallRunDbo installRun = installRunDboMapper.selectByPrimaryKey((short) 1);
			if (installRun == null)
				return;
			installRun.setState("READY");
			installRun.setLastCompletedState("READY");
			installRun.setRequestFingerprint(null);
			installRun.setProvisionRecycleBin(null);
			installRun.setSiteName(null);
			installRun.setAdminUserId(null);
			installRun.setLastError(null);
			installRunDboMapper.updateByPrimaryKey(installRun);
		}

		private String administratorEmail(int administratorUserId) {
			UserContactInfoDbo contact = userContactInfoDboMapper.selectByPrimaryKey(administratorUserId);
			return contact == null ? null
					: emailAddressDboMapper.selectByPrimaryKey(contact.getEmailAddressId()).getEmailAddress();
		}

		private long siteAdminGrants(int administratorUserId) {
			PermissionDboExample siteAdminPermission = new PermissionDboExample();
			siteAdminPermission.createCriteria().andPermissionCodeEqualTo("ZFGC_SITE_ADMIN");
			BrUserPermissionDboExample grants = new BrUserPermissionDboExample();
			grants.createCriteria().andUserIdEqualTo(administratorUserId).andUserPermissionIdIn(
					permissionDboMapper.selectByExample(siteAdminPermission).stream()
							.map(PermissionDbo::getPermissionId).toList());
			return brUserPermissionDboMapper.countByExample(grants);
		}

		private Path writeArchive(String schemaVersion) throws Exception {
			Path workspace = Files.createTempDirectory("zfgbb-restore-archive");
			Path dump = workspace.resolve("database.dump");
			postgres.dump(dump);
			Path archive = workspace.resolve("backup.tar.gz");
			new BackupArchiveWriter(operationStorage.limits()).write(
					new BackupArchiveWriter.Request(dump, contentTarget, "test", schemaVersion, 18,
							"pg_dump (PostgreSQL) 18.0", false, null,
							Instant.now()),
					archive);
			return archive;
		}
	}

	@Nested
	@Order(1)
	class SourceConnection {

		@ParameterizedTest
		@ValueSource(strings = {
				"zfgc?allowLoadLocalInfile=true&allowUrlInLocalInfile=true",
				"zfgc?autoDeserialize=true",
				"zfgc&useSSL=false",
				"zfgc/../other",
				"zfgc#fragment",
				"zfgc db",
				"" })
		void aDatabaseNameCannotSmuggleConnectorProperties(String database) {
			assertThrows(IllegalArgumentException.class,
					() -> SmfConnectionParams.smfJdbcUrl("db.example.com", 3306, database),
					"a database name carrying '?', '&', '/' or '#' would append MySQL connector "
							+ "properties such as allowLoadLocalInfile or autoDeserialize to the URL, "
							+ "which lets a hostile source server read files off this host");
		}

		@ParameterizedTest
		@ValueSource(strings = {
				"db.example.com?autoDeserialize=true",
				"db.example.com/x",
				"db.example.com:3307",
				"db.example.com#f",
				"-leading.hyphen",
				"trailing.dot.",
				"" })
		void aHostCannotSmuggleConnectorPropertiesOrExtraAuthority(String host) {
			assertThrows(IllegalArgumentException.class,
					() -> SmfConnectionParams.smfJdbcUrl(host, 3306, "zfgc"));
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, -1, 65536 })
		void aPortOutsideTheTcpRangeIsRefused(int port) {
			assertThrows(IllegalArgumentException.class,
					() -> SmfConnectionParams.smfJdbcUrl("db.example.com", port, "zfgc"));
		}

		@Test
		void theOperatorStillChoosesAnyLegitimateSourceHost() {
			assertEquals("jdbc:mysql://db.example.com:3306/zfgc",
					SmfConnectionParams.smfJdbcUrl("db.example.com", 3306, "zfgc"),
					"migrating from an operator-hosted legacy database is the whole feature; "
							+ "validation constrains the shape of the parts, never which host is reachable");
			assertEquals("jdbc:mysql://10.0.0.5:3307/smf_legacy-1",
					SmfConnectionParams.smfJdbcUrl("10.0.0.5", 3307, "smf_legacy-1"));
			assertEquals("jdbc:mysql://[::1]:3306/zfgc",
					SmfConnectionParams.smfJdbcUrl("[::1]", null, "zfgc"),
					"an IPv6 literal and the default port must both still work");
		}
	}
}
