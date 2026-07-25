package com.zfgc.zfgbb.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import com.zfgc.zfgbb.dbo.*;
import com.zfgc.zfgbb.mappers.*;
import com.zfgc.zfgbb.services.contentstore.ContentService;
import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;
import com.zfgc.zfgbb.testsupport.mappers.TestSystemInfoMapper;

import tools.jackson.databind.JsonNode;

class AccountDeletionDeepWipeTest extends PostgresIntegrationTest {

	private static final String PASSWORD = "password123";
	private static final Path CONTENT_ROOT = createContentRoot();

	@Autowired
	private ContentService contentService;

	@Autowired
	private TestSystemInfoMapper testSystemInfoMapper;

	@Autowired private AccountDeletionAuditDboMapper accountDeletionAuditDboMapper;
	@Autowired private AccountDeletionRequestDboMapper accountDeletionRequestDboMapper;
	@Autowired private AvatarDboMapper avatarDboMapper;
	@Autowired private BoardSummaryViewDboMapper boardSummaryViewDboMapper;
	@Autowired private ContentCollectionDboMapper contentCollectionDboMapper;
	@Autowired private ContentCollectionItemDboMapper contentCollectionItemDboMapper;
	@Autowired private ContentEntityDboMapper contentEntityDboMapper;
	@Autowired private ContentResourceDboMapper contentResourceDboMapper;
	@Autowired private ContentResourceTypeDboMapper contentResourceTypeDboMapper;
	@Autowired private ContentTemplateDboMapper contentTemplateDboMapper;
	@Autowired private EmailAddressDboMapper emailAddressDboMapper;
	@Autowired private FileAttachmentDboMapper fileAttachmentDboMapper;
	@Autowired private MessageDboMapper messageDboMapper;
	@Autowired private MessageHistoryDboMapper messageHistoryDboMapper;
	@Autowired private MigratorIdMapDboMapper migratorIdMapDboMapper;
	@Autowired private ModerationLogDboMapper moderationLogDboMapper;
	@Autowired private NotificationSubscriptionDboMapper notificationSubscriptionDboMapper;
	@Autowired private PersonalMessageConversationDboMapper personalMessageConversationDboMapper;
	@Autowired private PersonalMessageDboMapper personalMessageDboMapper;
	@Autowired private PersonalMessageRecipientDboMapper personalMessageRecipientDboMapper;
	@Autowired private PollChoiceDboMapper pollChoiceDboMapper;
	@Autowired private PollDboMapper pollDboMapper;
	@Autowired private ProjectDboMapper projectDboMapper;
	@Autowired private ProjectDownloadDboMapper projectDownloadDboMapper;
	@Autowired private ProjectScreenshotDboMapper projectScreenshotDboMapper;
	@Autowired private ReactionDboMapper reactionDboMapper;
	@Autowired private ResourceDboMapper resourceDboMapper;
	@Autowired private ThreadDboMapper threadDboMapper;
	@Autowired private UserBioInfoDboMapper userBioInfoDboMapper;
	@Autowired private UserContactInfoDboMapper userContactInfoDboMapper;
	@Autowired private UserDboMapper userDboMapper;
	@Autowired private UserPollChoiceDboMapper userPollChoiceDboMapper;
	@Autowired private WikiPageCategoryDboMapper wikiPageCategoryDboMapper;
	@Autowired private WikiPageDboMapper wikiPageDboMapper;
	@Autowired private WikiPageRevisionDboMapper wikiPageRevisionDboMapper;

	@DynamicPropertySource
	static void accountDeletionProperties(DynamicPropertyRegistry r) {
		r.add("zfgbb.auth.lockout.failed-attempts", () -> "3");
		r.add("zfgbb.content.path", () -> CONTENT_ROOT.toString());
	}

	@BeforeEach
	void clearMail() {
		mailDispatcher.clear();
	}

	@Test
	void fullWipeDeletesContentPiiBlobsAndPassesFkCensus() throws Exception {
		String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
		Integer adminId = userIdOf(ADMIN_USER);
		String anchorThreadName = "Anchor " + suffix;
		int anchorThreadId = postThread(adminToken, anchorThreadName);

		String userName = "wipeme_" + suffix;
		register(userName, PASSWORD);
		JsonNode loginJson = login(userName, PASSWORD);
		String accessToken = loginJson.get("accessToken").asString();
		String originalRefreshToken = loginJson.get("refreshToken").asString();
		Integer userId = userIdOf(userName);

		int threadId = postThread(accessToken, "Wipe my thread " + suffix);
		postReply(accessToken, threadId);
		Integer replyMessageId =
				findLatestMessageIdInThreadOwnedBy(threadId, userId);

		Integer attachmentResourceId = insertContentResource("ATC", userId, "evidence.txt", "txt", "text/plain");
		Path attachmentBlob = writeBlobFor(attachmentResourceId);
		FileAttachmentDbo evidenceAttachment = new FileAttachmentDbo();
		evidenceAttachment.setMessageId(replyMessageId);
		evidenceAttachment.setActiveFlag(true);
		evidenceAttachment.setContentResourceId(attachmentResourceId);
		fileAttachmentDboMapper.insertSelective(evidenceAttachment);

		Integer avatarResourceId = insertContentResource("AVR", userId, "avatar.png", "png", "image/png");
		Path avatarBlob = writeBlobFor(avatarResourceId);
		AvatarDbo subjectAvatar = new AvatarDbo();
		subjectAvatar.setActiveFlag(true);
		subjectAvatar.setContentResourceId(avatarResourceId);
		avatarDboMapper.insertSelective(subjectAvatar);
		Integer avatarId = subjectAvatar.getAvatarId();

		UserBioInfoDbo bioInfo = new UserBioInfoDbo();
		bioInfo.setUserId(userId);
		bioInfo.setRealName("Real Name");
		bioInfo.setBirthDate(LocalDate.of(1990, 1, 2));
		bioInfo.setLocation("Hyrule");
		bioInfo.setSignature("my signature");
		bioInfo.setPersonalText("personal text");
		bioInfo.setCustomTitle("custom title");
		bioInfo.setAvatarId(avatarId);
		if (userBioInfoDboMapper.updateByPrimaryKeySelective(bioInfo) == 0)
			userBioInfoDboMapper.insertSelective(bioInfo);

		Integer wikiPageResourceId = insertContentResource("IMG", userId, "page-image.png", "png", "image/png");
		Path wikiPageBlob = writeBlobFor(wikiPageResourceId);
		Integer ownedWikiPageId = insertWikiPage("Wipe Page " + suffix, "wipe-page-" + suffix, userId,
				Optional.of(wikiPageResourceId));
		insertWikiPageRevision(ownedWikiPageId, "subject content", userId, userName, false);
		insertWikiPageRevision(ownedWikiPageId, "admin content", adminId, "Test Admin", true);
		WikiPageCategoryDbo ownedPageCategory = new WikiPageCategoryDbo();
		ownedPageCategory.setWikiPageId(ownedWikiPageId);
		ownedPageCategory.setCategoryName("WipeCategory" + suffix);
		wikiPageCategoryDboMapper.insertSelective(ownedPageCategory);
		insertReaction("WIKI_PAGE", ownedWikiPageId, adminId, 1);
		insertMigratorIdMap("WIKI_PAGE", 91001, ownedWikiPageId);
		Integer retainedEntityId = insertContentEntity("PROJECT", "Retained " + suffix, "retained-" + suffix, adminId,
				entity -> entity.setWikiPageId(ownedWikiPageId));

		Integer templatePageId = insertWikiPage("Template Page " + suffix, "template-page-" + suffix, userId,
				Optional.empty());
		ContentTemplateDbo linkedTemplate = new ContentTemplateDbo();
		linkedTemplate.setCode("tpl_" + suffix);
		linkedTemplate.setBody("template body");
		linkedTemplate.setWikiPageId(templatePageId);
		contentTemplateDboMapper.insertSelective(linkedTemplate);

		Integer revisedOnlyPageId = insertWikiPage("Revised Page " + suffix, "revised-page-" + suffix, adminId,
				Optional.empty());
		Integer subjectRevisionId = insertWikiPageRevision(revisedOnlyPageId, "revision by subject", userId, userName,
				true);

		Integer previewResourceId = insertContentResource("IMG", userId, "preview.png", "png", "image/png");
		Path previewBlob = writeBlobFor(previewResourceId);
		Integer screenshotResourceId = insertContentResource("IMG", userId, "shot.png", "png", "image/png");
		Path screenshotBlob = writeBlobFor(screenshotResourceId);
		Integer downloadResourceId = insertContentResource("DL", userId, "demo.zip", "zip", "application/zip");
		Path downloadBlob = writeBlobFor(downloadResourceId);
		Integer ownedProjectId = insertContentEntity("PROJECT", "Wipe Project " + suffix, "wipe-project-" + suffix,
				userId, entity -> {
					entity.setAuthorName(userName);
					entity.setWikiPageId(ownedWikiPageId);
					entity.setPreviewContentResourceId(previewResourceId);
				});
		ProjectDbo ownedProject = new ProjectDbo();
		ownedProject.setContentEntityId(ownedProjectId);
		projectDboMapper.insertSelective(ownedProject);
		ProjectScreenshotDbo ownedScreenshot = new ProjectScreenshotDbo();
		ownedScreenshot.setContentEntityId(ownedProjectId);
		ownedScreenshot.setContentResourceId(screenshotResourceId);
		projectScreenshotDboMapper.insertSelective(ownedScreenshot);
		ProjectDownloadDbo ownedDownload = new ProjectDownloadDbo();
		ownedDownload.setContentEntityId(ownedProjectId);
		ownedDownload.setContentResourceId(downloadResourceId);
		projectDownloadDboMapper.insertSelective(ownedDownload);
		ContentCollectionDbo featuredCollection = new ContentCollectionDbo();
		featuredCollection.setCode("featured_" + suffix);
		featuredCollection.setTitle("Featured");
		contentCollectionDboMapper.insertSelective(featuredCollection);
		Integer collectionId = featuredCollection.getContentCollectionId();
		ContentCollectionItemDbo featuredItem = new ContentCollectionItemDbo();
		featuredItem.setContentCollectionId(collectionId);
		featuredItem.setContentEntityId(ownedProjectId);
		contentCollectionItemDboMapper.insertSelective(featuredItem);
		insertReaction("PROJECT", ownedProjectId, adminId, 1);
		insertMigratorIdMap("PROJECT", 91002, ownedProjectId);

		Integer resourceDownloadResourceId = insertContentResource("DL", userId, "asset.zip", "zip",
				"application/zip");
		Path resourceDownloadBlob = writeBlobFor(resourceDownloadResourceId);
		Integer ownedResourceId = insertContentEntity("RESOURCE", "Wipe Resource " + suffix, "wipe-resource-" + suffix,
				userId, entity -> entity.setAuthorName(userName));
		ResourceDbo ownedResource = new ResourceDbo();
		ownedResource.setContentEntityId(ownedResourceId);
		ownedResource.setDownloadContentResourceId(resourceDownloadResourceId);
		resourceDboMapper.insertSelective(ownedResource);
		insertReaction("RESOURCE", ownedResourceId, adminId, 1);

		Integer anchorMessageId = findLatestMessageIdInThread(anchorThreadId);
		insertReaction("MESSAGE", anchorMessageId, userId, 1, Optional.of("self-identifying comment"));
		insertReaction("MESSAGE", replyMessageId, adminId, 2);

		Integer ownedEmptyThreadId = insertThread("Owned empty thread " + suffix, userId);
		Integer unrelatedThreadId = insertThread("Unrelated empty thread " + suffix, adminId);
		PollDbo unrelatedPoll = new PollDbo();
		unrelatedPoll.setPollQuestion("Survives?");
		unrelatedPoll.setThreadId(unrelatedThreadId);
		unrelatedPoll.setCreatedUserId(adminId);
		pollDboMapper.insertSelective(unrelatedPoll);
		Integer unrelatedPollId = unrelatedPoll.getPollId();
		PollChoiceDbo unrelatedPollChoice = new PollChoiceDbo();
		unrelatedPollChoice.setPollId(unrelatedPollId);
		unrelatedPollChoice.setChoiceText("Yes");
		unrelatedPollChoice.setActiveFlag(true);
		unrelatedPollChoice.setVotes(1);
		pollChoiceDboMapper.insertSelective(unrelatedPollChoice);
		Integer unrelatedPollChoiceId = unrelatedPollChoice.getPollChoiceId();
		testSystemInfoMapper.resetSequence("zfgbb.user_poll_choice", "user_poll_choice_id");
		UserPollChoiceDbo unrelatedVote = new UserPollChoiceDbo();
		unrelatedVote.setUserId(adminId);
		unrelatedVote.setPollChoiceId(unrelatedPollChoiceId);
		userPollChoiceDboMapper.insertSelective(unrelatedVote);
		NotificationSubscriptionDbo unrelatedWatch = new NotificationSubscriptionDbo();
		unrelatedWatch.setUserId(adminId);
		unrelatedWatch.setThreadId(unrelatedThreadId);
		notificationSubscriptionDboMapper.insertSelective(unrelatedWatch);
		PersonalMessageConversationDbo unrelatedConversation = new PersonalMessageConversationDbo();
		unrelatedConversation.setSubject("Unrelated empty conversation " + suffix);
		unrelatedConversation.setStartedTs(OffsetDateTime.now());
		personalMessageConversationDboMapper.insertSelective(unrelatedConversation);
		Integer unrelatedConversationId = unrelatedConversation.getPersonalMessageConversationId();

		String rotatedRefreshToken = refreshExpectingOk(originalRefreshToken);

		assertEquals(1, boardSummaryViewDboMapper.countByExample(where(new BoardSummaryViewDboExample(),
				example -> example.createCriteria().andBoardIdEqualTo(1).andLatestMessageOwnerIdEqualTo(userId))),
				"the subject's reply must be the board's latest post before deletion");
		JsonNode boardBefore = boardNodeOf(fetchForum(adminToken), 1);
		assertEquals(userName, boardBefore.path("latestMessageUserName").asString(),
				"the forum cache must show the subject as the latest poster before deletion");

		for (String reactableType : List.of("MESSAGE", "WIKI_PAGE", "PROJECT", "RESOURCE"))
			assertTrue(reactionDboMapper.countByExample(where(new ReactionDboExample(),
					example -> example.createCriteria().andReactableTypeEqualTo(reactableType))) > 0,
					"the fixture must seed a " + reactableType
							+ " reaction, or the post-wipe orphan check below passes vacuously");

		mockMvc.perform(post("/users/account/delete/preview")
				.header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageCount").value(2))
				.andExpect(jsonPath("$.threadCount").value(2))
				.andExpect(jsonPath("$.contentResourceCount").value(7))
				.andExpect(jsonPath("$.wikiPageCount").value(2))
				.andExpect(jsonPath("$.projectCount").value(1))
				.andExpect(jsonPath("$.resourceCount").value(1))
				.andExpect(jsonPath("$.adminReplacementRequired").value(false));

		mockMvc.perform(post("/users/account/delete")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(deletionBody("WIPE", PASSWORD, userName)))
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.status").value("PENDING"));
		String confirmationToken = lastConfirmationToken();

		assertEquals(1, userDboMapper.countByExample(where(new UserDboExample(),
				example -> example.createCriteria().andUserIdEqualTo(userId).andActiveFlagEqualTo(true))),
				"the account must stay untouched until the emailed link is used");
		assertEquals(2, messageDboMapper.countByExample(where(new MessageDboExample(),
				example -> example.createCriteria().andOwnerIdEqualTo(userId))),
				"authored content must stay untouched until the emailed link is used");
		assertTrue(Files.exists(attachmentBlob), "the attachment blob must survive until confirmation");

		confirmAccountDeletion(confirmationToken, "10.99.1.1")
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("COMPLETED"));

		assertEquals(0, userDboMapper.countByExample(where(new UserDboExample(),
				example -> example.createCriteria().andUserIdEqualTo(userId))));
		assertEquals(0, messageDboMapper.countByExample(where(new MessageDboExample(),
				example -> example.createCriteria().andOwnerIdEqualTo(userId))));
		assertEquals(0, threadDboMapper.countByExample(where(new ThreadDboExample(),
				example -> example.createCriteria().andThreadIdEqualTo(threadId))));
		assertEquals(0, fileAttachmentDboMapper.countByExample(where(new FileAttachmentDboExample(), example -> {
			example.createCriteria().andContentResourceIdEqualTo(attachmentResourceId);
			example.or().andMessageIdEqualTo(replyMessageId);
		})));
		assertEquals(0, contentResourceDboMapper.countByExample(where(new ContentResourceDboExample(),
				example -> example.createCriteria().andUploadedUserIdEqualTo(userId))));
		assertEquals(0, avatarDboMapper.countByExample(where(new AvatarDboExample(),
				example -> example.createCriteria().andAvatarIdEqualTo(avatarId))));
		assertEquals(0, userBioInfoDboMapper.countByExample(where(new UserBioInfoDboExample(),
				example -> example.createCriteria().andUserIdEqualTo(userId))));
		assertEquals(0, userContactInfoDboMapper.countByExample(where(new UserContactInfoDboExample(),
				example -> example.createCriteria().andUserIdEqualTo(userId))));
		assertEquals(0, emailAddressDboMapper.countByExample(where(new EmailAddressDboExample(),
				example -> example.createCriteria()
						.andEmailAddressEqualTo(userName + "@fake-email.fake.tld.thing"))));
		assertFalse(Files.exists(attachmentBlob), "the attachment blob must be swept from disk");
		assertFalse(Files.exists(avatarBlob), "the avatar blob must be swept from disk");

		assertEquals(0, wikiPageDboMapper.countByExample(where(new WikiPageDboExample(),
				example -> example.createCriteria().andWikiPageIdEqualTo(ownedWikiPageId))),
				"an owned wiki page must be hard-deleted at page granularity");
		assertEquals(0, wikiPageRevisionDboMapper.countByExample(where(new WikiPageRevisionDboExample(),
				example -> example.createCriteria().andWikiPageIdEqualTo(ownedWikiPageId))),
				"every author's revision on an owned page must go with the page");
		assertEquals(0, wikiPageCategoryDboMapper.countByExample(where(new WikiPageCategoryDboExample(),
				example -> example.createCriteria().andWikiPageIdEqualTo(ownedWikiPageId))));
		assertEquals(0, migratorIdMapDboMapper.countByExample(where(new MigratorIdMapDboExample(),
				example -> example.createCriteria().andEntityTypeEqualTo("WIKI_PAGE")
						.andZfgbbIdEqualTo(ownedWikiPageId))));
		assertEquals(0, contentEntityDboMapper.countByExample(where(new ContentEntityDboExample(),
				example -> example.createCriteria()
						.andContentEntityIdIn(List.of(ownedProjectId, ownedResourceId)))),
				"owned projects and resources must be hard-deleted");
		assertEquals(0, projectDboMapper.countByExample(where(new ProjectDboExample(),
				example -> example.createCriteria().andContentEntityIdEqualTo(ownedProjectId))));
		assertEquals(0, projectScreenshotDboMapper.countByExample(where(new ProjectScreenshotDboExample(),
				example -> example.createCriteria().andContentEntityIdEqualTo(ownedProjectId))));
		assertEquals(0, projectDownloadDboMapper.countByExample(where(new ProjectDownloadDboExample(),
				example -> example.createCriteria().andContentEntityIdEqualTo(ownedProjectId))));
		assertEquals(0, resourceDboMapper.countByExample(where(new ResourceDboExample(),
				example -> example.createCriteria().andContentEntityIdEqualTo(ownedResourceId))));
		assertEquals(0, contentCollectionItemDboMapper.countByExample(where(new ContentCollectionItemDboExample(),
				example -> example.createCriteria().andContentEntityIdEqualTo(ownedProjectId))),
				"curated collection links to a deleted entity must be cleaned up");
		assertEquals(1, contentCollectionDboMapper.countByExample(where(new ContentCollectionDboExample(),
				example -> example.createCriteria().andContentCollectionIdEqualTo(collectionId))),
				"the collection itself is retained");
		assertEquals(0, migratorIdMapDboMapper.countByExample(where(new MigratorIdMapDboExample(),
				example -> example.createCriteria().andEntityTypeEqualTo("PROJECT")
						.andZfgbbIdEqualTo(ownedProjectId))));
		assertEquals(1, contentEntityDboMapper.countByExample(where(new ContentEntityDboExample(),
				example -> example.createCriteria().andContentEntityIdEqualTo(retainedEntityId)
						.andWikiPageIdIsNull())),
				"a retained entity linking a deleted page must have its wiki_page_id nulled");
		assertEquals(1, wikiPageDboMapper.countByExample(where(new WikiPageDboExample(),
				example -> example.createCriteria().andWikiPageIdEqualTo(templatePageId)
						.andCreatedUserIdIsNull())),
				"a template-linked owned page must be retained anonymized, never deleted");
		assertEquals(1, contentTemplateDboMapper.countByExample(where(new ContentTemplateDboExample(),
				example -> example.createCriteria().andWikiPageIdEqualTo(templatePageId))),
				"the template link must never be silently nulled");
		assertEquals(1, moderationLogDboMapper.countByExample(where(new ModerationLogDboExample(),
				example -> example.createCriteria().andActionEqualTo("ACCOUNT_DELETION_TEMPLATE_LINKED_WIKI_PAGE")
						.andDetailLike("wiki_page_id=" + templatePageId + " %"))),
				"the retained template-linked page must surface an operator-remediation outcome");
		assertEquals(1, wikiPageDboMapper.countByExample(where(new WikiPageDboExample(),
				example -> example.createCriteria().andWikiPageIdEqualTo(revisedOnlyPageId))),
				"a page the subject only revised must be retained");
		assertEquals(1, wikiPageRevisionDboMapper.countByExample(where(new WikiPageRevisionDboExample(),
				example -> example.createCriteria().andWikiPageRevisionIdEqualTo(subjectRevisionId)
						.andAuthorUserIdIsNull().andAuthorNameEqualTo("[deleted]"))),
				"the subject's revision on a retained page must be kept with attribution scrubbed");
		assertEquals(1, reactionDboMapper.countByExample(where(new ReactionDboExample(),
				example -> example.createCriteria().andReactableTypeEqualTo("MESSAGE")
						.andReactableIdEqualTo(anchorMessageId).andReactorUserIdIsNull().andCommentIsNull())),
				"a reaction the subject gave on retained content must be kept with reactor and comment scrubbed");
		assertFalse(Files.exists(wikiPageBlob), "the owned wiki page blob must be swept from disk");
		assertFalse(Files.exists(previewBlob), "the project preview blob must be swept from disk");
		assertFalse(Files.exists(screenshotBlob), "the project screenshot blob must be swept from disk");
		assertFalse(Files.exists(downloadBlob), "the project download blob must be swept from disk");
		assertFalse(Files.exists(resourceDownloadBlob), "the resource download blob must be swept from disk");

		assertEquals(0, threadDboMapper.countByExample(where(new ThreadDboExample(),
				example -> example.createCriteria().andThreadIdEqualTo(ownedEmptyThreadId))),
				"the subject's own empty thread must be garbage-collected");
		assertEquals(1, threadDboMapper.countByExample(where(new ThreadDboExample(),
				example -> example.createCriteria().andThreadIdEqualTo(unrelatedThreadId))),
				"another user's empty thread must survive the scoped GC");
		assertEquals(1, pollDboMapper.countByExample(where(new PollDboExample(),
				example -> example.createCriteria().andPollIdEqualTo(unrelatedPollId))),
				"another user's poll on an unrelated empty thread must survive");
		assertEquals(1, userPollChoiceDboMapper.countByExample(where(new UserPollChoiceDboExample(),
				example -> example.createCriteria().andPollChoiceIdEqualTo(unrelatedPollChoiceId)
						.andUserIdEqualTo(adminId))),
				"another user's poll vote must survive");
		assertEquals(1, notificationSubscriptionDboMapper.countByExample(where(new NotificationSubscriptionDboExample(),
				example -> example.createCriteria().andThreadIdEqualTo(unrelatedThreadId)
						.andUserIdEqualTo(adminId))),
				"another user's thread watch must survive");
		assertEquals(1, personalMessageConversationDboMapper.countByExample(
				where(new PersonalMessageConversationDboExample(), example -> example.createCriteria()
						.andPersonalMessageConversationIdEqualTo(unrelatedConversationId))),
				"an unrelated empty conversation must survive the scoped GC");

		BoardSummaryViewDbo boardSummary = boardSummaryViewDboMapper.selectByExample(
				where(new BoardSummaryViewDboExample(), example -> example.createCriteria().andBoardIdEqualTo(1)))
				.get(0);
		assertEquals(adminId, boardSummary.getLatestMessageOwnerId(),
				"board_summary must fall back to the prior latest poster after the wipe");
		assertEquals(anchorThreadName, boardSummary.getThreadName());
		JsonNode boardAfter = boardNodeOf(fetchForum(adminToken), 1);
		assertEquals(anchorThreadName, boardAfter.path("threadName").asString(),
				"the forum cache must be evicted so the index shows the surviving latest thread");
		assertEquals("Test Admin", boardAfter.path("latestMessageUserName").asString());

		assertNoRowReferencesUser(userId);
		assertEquals(0, testQueryHelperMapper.countReactionsWithoutMessage());
		assertEquals(0, testQueryHelperMapper.countReactionsWithoutWikiPage());
		assertEquals(0, testQueryHelperMapper.countReactionsWithoutContentEntity());
		assertEquals(0, migratorIdMapDboMapper.countByExample(where(new MigratorIdMapDboExample(),
				example -> example.createCriteria().andEntityTypeEqualTo("USER").andZfgbbIdEqualTo(userId))));
		assertEquals(1, accountDeletionRequestDboMapper.countByExample(where(new AccountDeletionRequestDboExample(),
				example -> example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo("COMPLETED")
						.andPurgeCursorEqualTo("COMPLETED").andRecordedBlobPathsIsNull())));
		assertEquals(1, accountDeletionAuditDboMapper.countByExample(where(new AccountDeletionAuditDboExample(),
				example -> example.createCriteria().andSubjectUserIdSnapshotEqualTo(userId).andModeEqualTo("PURGE")
						.andInitiatedByEqualTo("SELF").andConfirmedTsIsNotNull().andExecutedTsIsNotNull())));

		assertPostDeletionTeardown(userName, PASSWORD, accessToken, originalRefreshToken, rotatedRefreshToken);
	}

	@Test
	void adminConsolePurgeReleasesBlobsAndClearsPersonalMessagesAndResidue() throws Exception {
		String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
		Integer adminId = userIdOf(ADMIN_USER);
		int anchorThreadId = postThread(adminToken, "Console anchor " + suffix);
		Integer anchorMessageId = findLatestMessageIdInThread(anchorThreadId);

		String userName = "conpurge_" + suffix;
		register(userName, PASSWORD);
		String accessToken = login(userName, PASSWORD).get("accessToken").asString();
		Integer userId = userIdOf(userName);
		int ownedThreadId = postThread(accessToken, "Console purge thread " + suffix);
		Integer ownedMessageId = findLatestMessageIdInThreadOwnedBy(ownedThreadId, userId);

		Integer attachmentResourceId =
				insertContentResource("ATC", userId, "console-evidence.txt", "txt", "text/plain");
		Path attachmentBlob = writeBlobFor(attachmentResourceId);
		FileAttachmentDbo evidenceAttachment = new FileAttachmentDbo();
		evidenceAttachment.setMessageId(ownedMessageId);
		evidenceAttachment.setActiveFlag(true);
		evidenceAttachment.setContentResourceId(attachmentResourceId);
		fileAttachmentDboMapper.insertSelective(evidenceAttachment);

		Integer avatarResourceId = insertContentResource("AVR", userId, "console-avatar.png", "png", "image/png");
		Path avatarBlob = writeBlobFor(avatarResourceId);
		AvatarDbo subjectAvatar = new AvatarDbo();
		subjectAvatar.setActiveFlag(true);
		subjectAvatar.setContentResourceId(avatarResourceId);
		avatarDboMapper.insertSelective(subjectAvatar);
		Integer avatarId = subjectAvatar.getAvatarId();
		UserBioInfoDbo bioInfo = new UserBioInfoDbo();
		bioInfo.setUserId(userId);
		bioInfo.setAvatarId(avatarId);
		if (userBioInfoDboMapper.updateByPrimaryKeySelective(bioInfo) == 0)
			userBioInfoDboMapper.insertSelective(bioInfo);

		PersonalMessageConversationDbo conversation = new PersonalMessageConversationDbo();
		conversation.setSubject("Console conversation " + suffix);
		conversation.setStartedTs(OffsetDateTime.now());
		personalMessageConversationDboMapper.insertSelective(conversation);
		PersonalMessageDbo sentPersonalMessage = new PersonalMessageDbo();
		sentPersonalMessage.setPersonalMessageConversationId(conversation.getPersonalMessageConversationId());
		sentPersonalMessage.setSenderUserId(userId);
		sentPersonalMessage.setSenderName(userName);
		sentPersonalMessage.setBody("private body " + suffix);
		personalMessageDboMapper.insertSelective(sentPersonalMessage);
		PersonalMessageRecipientDbo personalMessageRecipient = new PersonalMessageRecipientDbo();
		personalMessageRecipient.setPersonalMessageId(sentPersonalMessage.getPersonalMessageId());
		personalMessageRecipient.setRecipientUserId(adminId);
		personalMessageRecipientDboMapper.insertSelective(personalMessageRecipient);

		insertReaction("MESSAGE", anchorMessageId, userId, 1, Optional.of("console self-identifying comment"));

		assertTrue(Files.exists(attachmentBlob) && Files.exists(avatarBlob),
				"the fixture must stage both blobs on disk, or the post-purge sweep checks pass vacuously");
		assertEquals(1, personalMessageDboMapper.countByExample(where(new PersonalMessageDboExample(),
				example -> example.createCriteria().andSenderUserIdEqualTo(userId)
						.andSenderNameEqualTo(userName))),
				"the fixture must stage a personal message that still names the subject");
		assertEquals(1, reactionDboMapper.countByExample(where(new ReactionDboExample(),
				example -> example.createCriteria().andReactableTypeEqualTo("MESSAGE")
						.andReactableIdEqualTo(anchorMessageId).andReactorUserIdEqualTo(userId)
						.andCommentIsNotNull())),
				"the fixture must stage reaction residue on content the subject does not own");

		mockMvc.perform(post("/admin/users/delete")
				.header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"userId\": " + userId + ", \"mode\": \"PURGE\"}"))
				.andExpect(status().isNoContent());

		assertNull(userDboMapper.selectByPrimaryKey(userId), "an admin purge must remove the account outright");
		assertEquals(0, contentResourceDboMapper.countByExample(where(new ContentResourceDboExample(),
				example -> example.createCriteria()
						.andContentResourceIdIn(List.of(attachmentResourceId, avatarResourceId)))),
				"an admin purge must delete the released content_resource rows, not hand them to the sentinel");
		assertFalse(Files.exists(attachmentBlob), "an admin purge must sweep the attachment blob from disk");
		assertFalse(Files.exists(avatarBlob), "an admin purge must sweep the avatar blob from disk");
		assertEquals(0, avatarDboMapper.countByExample(where(new AvatarDboExample(),
				example -> example.createCriteria().andAvatarIdEqualTo(avatarId))),
				"an admin purge must delete the subject's avatar row");
		assertEquals(1, personalMessageDboMapper.countByExample(where(new PersonalMessageDboExample(),
				example -> example.createCriteria()
						.andPersonalMessageIdEqualTo(sentPersonalMessage.getPersonalMessageId())
						.andSenderUserIdIsNull().andSenderNameEqualTo("[deleted]"))),
				"an admin purge must scrub the sender name off the subject's personal messages");
		assertEquals(1, reactionDboMapper.countByExample(where(new ReactionDboExample(),
				example -> example.createCriteria().andReactableTypeEqualTo("MESSAGE")
						.andReactableIdEqualTo(anchorMessageId).andReactorUserIdIsNull().andCommentIsNull())),
				"an admin purge must scrub the reaction residue the subject left on retained content");
		assertEquals(0, threadDboMapper.countByExample(where(new ThreadDboExample(),
				example -> example.createCriteria().andThreadIdEqualTo(ownedThreadId))),
				"an admin purge must garbage-collect the thread the subject emptied");
	}

	@Test
	void adminConsolePurgeAuditsTheSharedEmailAddressItCannotRelease() throws Exception {
		String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();

		String subjectUserName = "sharedpurge_" + suffix;
		register(subjectUserName, PASSWORD);
		Integer subjectUserId = userIdOf(subjectUserName);
		String coOwnerUserName = "sharedkeep_" + suffix;
		register(coOwnerUserName, PASSWORD);
		Integer coOwnerUserId = userIdOf(coOwnerUserName);

		Integer sharedEmailAddressId =
				userContactInfoDboMapper.selectByPrimaryKey(subjectUserId).getEmailAddressId();
		UserContactInfoDbo shareTheRow = new UserContactInfoDbo();
		shareTheRow.setUserId(coOwnerUserId);
		shareTheRow.setEmailAddressId(sharedEmailAddressId);
		userContactInfoDboMapper.updateByPrimaryKeySelective(shareTheRow);
		assertEquals(2, userContactInfoDboMapper.countByExample(where(new UserContactInfoDboExample(),
				example -> example.createCriteria().andEmailAddressIdEqualTo(sharedEmailAddressId))),
				"the fixture must put two accounts on one email_address row, "
						+ "or the retention checks below pass vacuously");
		assertEquals(0, moderationLogDboMapper.countByExample(where(new ModerationLogDboExample(),
				example -> example.createCriteria().andActionEqualTo("ACCOUNT_DELETION_SHARED_EMAIL")
						.andDetailLike("email_address_id=" + sharedEmailAddressId + " %"))),
				"the shared address must not already carry a remediation record before the purge");

		mockMvc.perform(post("/admin/users/delete")
				.header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"userId\": " + subjectUserId + ", \"mode\": \"PURGE\"}"))
				.andExpect(status().isNoContent());

		assertNull(userDboMapper.selectByPrimaryKey(subjectUserId),
				"an admin purge must remove the account outright");
		assertEquals(1, emailAddressDboMapper.countByExample(where(new EmailAddressDboExample(),
				example -> example.createCriteria().andEmailAddressIdEqualTo(sharedEmailAddressId))),
				"an address another live account still holds must survive the purge");
		assertEquals(sharedEmailAddressId,
				userContactInfoDboMapper.selectByPrimaryKey(coOwnerUserId).getEmailAddressId(),
				"the other owner of the shared row must keep its address");
		assertEquals(1, moderationLogDboMapper.countByExample(where(new ModerationLogDboExample(),
				example -> example.createCriteria().andActionEqualTo("ACCOUNT_DELETION_SHARED_EMAIL")
						.andDetailLike("email_address_id=" + sharedEmailAddressId + " %"))),
				"an admin purge that retains a shared address must leave the same operator-remediation record "
						+ "the self-service path leaves");
	}

	@Test
	void adminConsoleAnonymizeKeepsContentButClosesTheAccountAndDropsThePii() throws Exception {
		String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();

		String userName = "conanon_" + suffix;
		String emailAddress = userName + "@fake-email.fake.tld.thing";
		register(userName, PASSWORD);
		JsonNode loginJson = login(userName, PASSWORD);
		String accessToken = loginJson.get("accessToken").asString();
		String originalRefreshToken = loginJson.get("refreshToken").asString();
		String rotatedRefreshToken = refreshExpectingOk(originalRefreshToken);
		Integer userId = userIdOf(userName);

		String keptBody = "console anonymize body " + suffix;
		int ownedThreadId = postThread(accessToken, "Console anonymize thread " + suffix, keptBody);
		Integer ownedMessageId = findLatestMessageIdInThreadOwnedBy(ownedThreadId, userId);
		Integer attachmentResourceId =
				insertContentResource("ATC", userId, "console-anon.txt", "txt", "text/plain");
		Path attachmentBlob = writeBlobFor(attachmentResourceId);
		FileAttachmentDbo evidenceAttachment = new FileAttachmentDbo();
		evidenceAttachment.setMessageId(ownedMessageId);
		evidenceAttachment.setActiveFlag(true);
		evidenceAttachment.setContentResourceId(attachmentResourceId);
		fileAttachmentDboMapper.insertSelective(evidenceAttachment);

		List<EmailAddressDbo> stagedEmailRows = emailAddressDboMapper.selectByExample(where(
				new EmailAddressDboExample(),
				example -> example.createCriteria().andEmailAddressEqualTo(emailAddress)));
		assertEquals(1, stagedEmailRows.size(),
				"the fixture must stage the subject's email row, or the leak check below passes vacuously");
		Integer releasedEmailAddressId = stagedEmailRows.get(0).getEmailAddressId();

		mockMvc.perform(post("/admin/users/delete")
				.header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"userId\": " + userId + ", \"mode\": \"ANONYMIZE\"}"))
				.andExpect(status().isNoContent());

		UserDbo anonymized = userDboMapper.selectByPrimaryKey(userId);
		assertNotNull(anonymized,
				"an admin anonymize keeps the content, so the row the content still points at must survive");
		assertEquals("[deleted]", anonymized.getUserName());
		assertEquals("[deleted]", anonymized.getDisplayName());
		assertEquals("__deleted__" + userId, anonymized.getSsoKey());
		assertEquals(false, anonymized.getActiveFlag());
		assertNull(anonymized.getPasswordHash(),
				"a surviving row must not survive with usable credentials on it");
		assertNotNull(anonymized.getTokensValidAfterTs());

		assertPostDeletionTeardown(userName, PASSWORD, accessToken, originalRefreshToken, rotatedRefreshToken);

		assertEquals(0, emailAddressDboMapper.countByExample(where(new EmailAddressDboExample(),
				example -> example.createCriteria().andEmailAddressEqualTo(emailAddress))),
				"an admin anonymize must release the subject's email address row");
		assertEquals(0, moderationLogDboMapper.countByExample(where(new ModerationLogDboExample(),
				example -> example.createCriteria().andActionEqualTo("ACCOUNT_DELETION_SHARED_EMAIL")
						.andDetailLike("email_address_id=" + releasedEmailAddressId + " %"))),
				"an address that was actually released must never be reported as retained and shared");
		assertEquals(0, userContactInfoDboMapper.countByExample(where(new UserContactInfoDboExample(),
				example -> example.createCriteria().andUserIdEqualTo(userId))));
		assertEquals(0, userBioInfoDboMapper.countByExample(where(new UserBioInfoDboExample(),
				example -> example.createCriteria().andUserIdEqualTo(userId))));

		Integer sentinelId = userDboMapper.selectByExample(where(new UserDboExample(),
				example -> example.createCriteria().andSsoKeyEqualTo("__deleted__"))).get(0).getUserId();
		assertEquals(0, messageDboMapper.countByExample(where(new MessageDboExample(),
				example -> example.createCriteria().andOwnerIdEqualTo(userId))),
				"no retained post may still name the anonymized account");
		assertEquals(1, messageDboMapper.countByExample(where(new MessageDboExample(),
				example -> example.createCriteria().andMessageIdEqualTo(ownedMessageId)
						.andOwnerIdEqualTo(sentinelId))),
				"anonymize keeps the post and hands it to the sentinel rather than deleting it");
		assertEquals(1, threadDboMapper.countByExample(where(new ThreadDboExample(),
				example -> example.createCriteria().andThreadIdEqualTo(ownedThreadId)
						.andCreatedUserIdEqualTo(sentinelId))),
				"anonymize keeps the thread and hands it to the sentinel rather than deleting it");
		assertEquals(keptBody, messageHistoryDboMapper.selectByExample(where(new MessageHistoryDboExample(),
				example -> example.createCriteria().andMessageIdEqualTo(ownedMessageId)
						.andCurrentFlagEqualTo(true))).get(0).getMessageText(),
				"the retained post must keep the body the subject wrote");
		assertEquals(1, contentResourceDboMapper.countByExample(where(new ContentResourceDboExample(),
				example -> example.createCriteria().andContentResourceIdEqualTo(attachmentResourceId)
						.andUploadedUserIdEqualTo(sentinelId))),
				"anonymize must reassign the uploaded resource instead of releasing it");
		assertTrue(Files.exists(attachmentBlob),
				"anonymize keeps content, so the attachment blob must stay on disk");
	}

	@Test
	void repeatedWrongPasswordsLockTheAccount() throws Exception {
		String userName = "lockme_" + suffix;
		register(userName, PASSWORD);
		String accessToken = login(userName, PASSWORD).get("accessToken").asString();
		Integer userId = userIdOf(userName);

		for (int attempt = 0; attempt < 3; attempt++)
			mockMvc.perform(post("/users/account/delete")
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(deletionBody("WIPE", "wrongpassword", userName)))
					.andExpect(status().isBadRequest());

		assertEquals(1, userDboMapper.countByExample(where(new UserDboExample(),
				example -> example.createCriteria().andUserIdEqualTo(userId)
						.andFailedLoginCountGreaterThanOrEqualTo(3).andLockedUntilTsIsNotNull())),
				"wrong-password deletion requests must lock the account at the login threshold");

		mockMvc.perform(post("/users/account/delete")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(deletionBody("WIPE", PASSWORD, userName)))
				.andExpect(status().isBadRequest());
		assertEquals(0, accountDeletionRequestDboMapper.countByExample(where(new AccountDeletionRequestDboExample(),
				example -> example.createCriteria().andUserIdEqualTo(userId))),
				"a locked account must not be able to create a deletion request even with the right password");

		mockMvc.perform(post("/users/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\": \"" + userName + "\", \"password\": \"" + PASSWORD
						+ "\", \"useTokens\": true}"))
				.andExpect(status().isUnauthorized());
	}

	private void assertNoRowReferencesUser(Integer userId) {
		List<Map<String, Object>> referrers = testSystemInfoMapper.listUserReferrerColumns();
		Set<String> censusColumns = referrers.stream()
				.map(referrer -> referrer.get("referrer_table") + "." + referrer.get("referrer_column"))
				.collect(Collectors.toSet());
		assertTrue(censusColumns.containsAll(Set.of("zfgbb.message.owner_id", "zfgbb.thread.created_user_id")),
				"the live FK census must enumerate the user-referencing columns, but found: " + censusColumns);
		for (Map<String, Object> referrer : referrers) {
			String referrerTable = (String) referrer.get("referrer_table");
			String referrerColumn = (String) referrer.get("referrer_column");
			assertEquals(0, testSystemInfoMapper.countRowsReferencingUser(referrerTable, referrerColumn, userId),
					referrerTable + "." + referrerColumn + " must not reference the wiped user");
		}
	}

	private static <E> E where(E example, Consumer<E> criteria) {
		criteria.accept(example);
		return example;
	}

	private Integer insertWikiPage(String title, String slug, Integer createdUserId,
			Optional<Integer> contentResourceId) {
		WikiPageDbo page = new WikiPageDbo();
		page.setNamespace("MAIN");
		page.setTitle(title);
		page.setSlug(slug);
		page.setCreatedUserId(createdUserId);
		contentResourceId.ifPresent(page::setContentResourceId);
		wikiPageDboMapper.insertSelective(page);
		return page.getWikiPageId();
	}

	private Integer insertWikiPageRevision(Integer wikiPageId, String content, Integer authorUserId, String authorName,
			boolean currentFlag) {
		WikiPageRevisionDbo revision = new WikiPageRevisionDbo();
		revision.setWikiPageId(wikiPageId);
		revision.setContent(content);
		revision.setAuthorUserId(authorUserId);
		revision.setAuthorName(authorName);
		revision.setCurrentFlag(currentFlag);
		revision.setStatus("APPROVED");
		wikiPageRevisionDboMapper.insertSelective(revision);
		return revision.getWikiPageRevisionId();
	}

	private Integer insertContentEntity(String entityType, String title, String slug, Integer createdUserId,
			Consumer<ContentEntityDbo> extraValues) {
		ContentEntityDbo entity = new ContentEntityDbo();
		entity.setEntityType(entityType);
		entity.setTitle(title);
		entity.setSlug(slug);
		entity.setCreatedUserId(createdUserId);
		extraValues.accept(entity);
		contentEntityDboMapper.insertSelective(entity);
		return entity.getContentEntityId();
	}

	private Integer insertThread(String threadName, Integer createdUserId) {
		ThreadDbo thread = new ThreadDbo();
		thread.setThreadName(threadName);
		thread.setBoardId(1);
		thread.setCreatedUserId(createdUserId);
		threadDboMapper.insertSelective(thread);
		return thread.getThreadId();
	}

	private void insertReaction(String reactableType, Integer reactableId, Integer reactorUserId,
			Integer reactionTypeId) {
		insertReaction(reactableType, reactableId, reactorUserId, reactionTypeId, Optional.empty());
	}

	private void insertReaction(String reactableType, Integer reactableId, Integer reactorUserId,
			Integer reactionTypeId, Optional<String> comment) {
		ReactionDbo reaction = new ReactionDbo();
		reaction.setReactableType(reactableType);
		reaction.setReactableId(reactableId);
		reaction.setReactorUserId(reactorUserId);
		reaction.setReactionTypeId(reactionTypeId);
		comment.ifPresent(reaction::setComment);
		reactionDboMapper.insertSelective(reaction);
	}

	private void insertMigratorIdMap(String entityType, Integer legacyId, Integer zfgbbId) {
		MigratorIdMapDbo idMap = new MigratorIdMapDbo();
		idMap.setEntityType(entityType);
		idMap.setLegacyId(legacyId);
		idMap.setZfgbbId(zfgbbId);
		migratorIdMapDboMapper.insertSelective(idMap);
	}

	private Integer insertContentResource(String contentCode, Integer userId, String filename, String fileExt,
			String mimeType) {
		ContentResourceDbo dbo = new ContentResourceDbo();
		dbo.setContentTypeId(contentResourceTypeDboMapper.selectByExample(where(new ContentResourceTypeDboExample(),
				example -> example.createCriteria().andContentCodeEqualTo(contentCode))).get(0)
				.getContentResourceTypeId());
		dbo.setUploadedUserId(userId);
		dbo.setFilename(filename);
		dbo.setChecksum("test-checksum");
		dbo.setFileExt(fileExt);
		dbo.setMimeType(mimeType);
		contentResourceDboMapper.insertSelective(dbo);
		return dbo.getContentResourceId();
	}

	private Path writeBlobFor(Integer contentResourceId) throws IOException {
		ContentResourceDbo resource = contentService.getContentResourceDbo(contentResourceId).orElseThrow();
		Path blobPath = contentService.storedFile(resource);
		Files.createDirectories(blobPath.getParent());
		Files.writeString(blobPath, "blob-" + contentResourceId);
		return blobPath;
	}

	private static Path createContentRoot() {
		try {
			return Files.createTempDirectory("zfgbb-account-deletion-content");
		} catch (IOException contentRootFailure) {
			throw new IllegalStateException("could not create the content root temp directory", contentRootFailure);
		}
	}
}
