package com.zfgc.zfgbb.forum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.zfgc.zfgbb.dbo.*;
import com.zfgc.zfgbb.mappers.*;
import com.zfgc.zfgbb.model.users.DeletionMode;
import com.zfgc.zfgbb.services.users.UserService;
import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

import tools.jackson.databind.JsonNode;

class ModeratorTest extends PostgresIntegrationTest {

	private static final String RECYCLE_BOARD_CONFIG_KEY = "recycle_board_id";

	@Autowired private UserService userService;
	@Autowired private BoardSummaryViewDboMapper boardSummaryViewDboMapper;
	@Autowired private ContentResourceDboMapper contentResourceDboMapper;
	@Autowired private ContentResourceTypeDboMapper contentResourceTypeDboMapper;
	@Autowired private FileAttachmentDboMapper fileAttachmentDboMapper;
	@Autowired private MessageDboMapper messageDboMapper;
	@Autowired private MessageHistoryDboMapper messageHistoryDboMapper;
	@Autowired private MigratorAttachmentRefRewriteDboMapper migratorAttachmentRefRewriteDboMapper;
	@Autowired private MigratorIdMapDboMapper migratorIdMapDboMapper;
	@Autowired private ModerationLogDboMapper moderationLogDboMapper;
	@Autowired private PollChoiceDboMapper pollChoiceDboMapper;
	@Autowired private PollDboMapper pollDboMapper;
	@Autowired private ReactionDboMapper reactionDboMapper;
	@Autowired private ReactionTypeDboMapper reactionTypeDboMapper;
	@Autowired private SystemConfigDboMapper systemConfigDboMapper;
	@Autowired private ThreadDboMapper threadDboMapper;
	@Autowired private UserDboMapper userDboMapper;
	@Autowired private UserPollChoiceDboMapper userPollChoiceDboMapper;

	@Nested
	class RecycleAndRestore {

		@Test
		void ownerRecyclesMiddlePostAndOnlyModeratorMayPurgeIt() throws Exception {
			TestUser owner = createUser("midp_" + suffix);
			String adminToken = getAdminToken();
			int binBoardId = recycleBoardId();

			int threadId = postThread(owner.token(), "Middle post " + suffix, "OP body " + suffix);
			for (int replyNumber = 2; replyNumber <= 11; replyNumber++)
				postReply(owner.token(), threadId, "reply number " + replyNumber);
			int middleMessageId = messageIdAt(threadId, 6);
			String secretBody = "SECRET_MIDDLE_BODY_" + suffix;
			MessageHistoryDbo secretRevision = new MessageHistoryDbo();
			secretRevision.setMessageText(secretBody);
			messageHistoryDboMapper.updateByExampleSelective(secretRevision,
					messageHistoryWhere(criteria -> criteria.andMessageIdEqualTo(middleMessageId)));

			long board1PostsBefore = boardSummaryValue(1, BoardSummaryViewDbo::getPostCount);
			long binPostsBefore = boardSummaryValue(binBoardId, BoardSummaryViewDbo::getPostCount);

			MvcResult recycleResult = mockMvc.perform(delete("/message/" + middleMessageId)
					.header("Authorization", "Bearer " + owner.token()))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("RECYCLED"))
					.andExpect(jsonPath("$.originThreadRecycled").value(false))
					.andExpect(jsonPath("$.originThreadDeleted").value(false))
					.andExpect(jsonPath("$.threadId").value(threadId))
					.andExpect(jsonPath("$.boardId").value(1))
					.andExpect(jsonPath("$.pageCount").value(1))
					.andReturn();
			int wrapperThreadId = json.readTree(recycleResult.getResponse().getContentAsString())
					.get("recycleThreadId").asInt();

			assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), postPositionsIn(threadId),
					"origin thread must be densely resequenced after the middle post moves out");
			assertEquals(1, threadCount(criteria -> criteria.andThreadIdEqualTo(wrapperThreadId)
					.andBoardIdEqualTo(binBoardId).andRecycledFromBoardIdEqualTo(1)
					.andRecycledFromThreadIdEqualTo(threadId)),
					"wrapper thread must live in the bin and carry origin provenance");
			assertEquals(1, messageCount(criteria -> criteria.andMessageIdEqualTo(middleMessageId)
					.andThreadIdEqualTo(wrapperThreadId).andBoardIdEqualTo(binBoardId).andPostInThreadEqualTo(1)),
					"recycled message must be re-parented to position 1 of the wrapper");

			assertEquals(board1PostsBefore - 1, boardSummaryValue(1, BoardSummaryViewDbo::getPostCount),
					"board_summary post_count of the origin board must drop by one");
			assertEquals(binPostsBefore + 1, boardSummaryValue(binBoardId, BoardSummaryViewDbo::getPostCount),
					"board_summary post_count of the bin must grow by one");

			int ownerUserId = userIdOf(owner.userName());
			assertEquals(1, moderationLogs(criteria -> criteria.andActionEqualTo("MESSAGE_RECYCLED")
					.andMessageIdEqualTo(middleMessageId).andThreadIdEqualTo(threadId).andBoardIdEqualTo(1)
					.andActorUserIdEqualTo(ownerUserId).andTargetUserIdEqualTo(ownerUserId)).size());
			String logDetail = moderationLogs(criteria -> criteria.andActionEqualTo("MESSAGE_RECYCLED")
					.andMessageIdEqualTo(middleMessageId)).get(0).getDetail();
			assertNotNull(logDetail);
			assertFalse(logDetail.contains(secretBody), "the moderation log must never contain the post body");

			mockMvc.perform(delete("/message/" + middleMessageId)
					.header("Authorization", "Bearer " + owner.token()))
					.andExpect(status().isForbidden());
			assertEquals(1, messageCount(criteria -> criteria.andMessageIdEqualTo(middleMessageId)),
					"a non-moderator second delete must not destroy the binned message");

			mockMvc.perform(delete("/message/" + middleMessageId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("PURGED"))
					.andExpect(jsonPath("$.originThreadDeleted").value(true))
					.andExpect(jsonPath("$.boardId").value(binBoardId));
			assertEquals(0, messageCount(criteria -> criteria.andMessageIdEqualTo(middleMessageId)));
			assertEquals(0, messageHistoryCount(criteria -> criteria.andMessageIdEqualTo(middleMessageId)));
			assertEquals(0, threadCount(criteria -> criteria.andThreadIdEqualTo(wrapperThreadId)),
					"the emptied wrapper thread must be garbage-collected by the purge");
			assertEquals(1, moderationLogCount("MESSAGE_PURGED",
					log -> log.getDetail().contains("message_id=" + middleMessageId + " ")));
		}

		@Test
		void ownerDeletesOpWithRepliesAndTheThreadSurvivesHeadless() throws Exception {
			TestUser owner = createUser("opdel_" + suffix);
			String adminToken = getAdminToken();
			int binBoardId = recycleBoardId();

			int threadId = postThread(owner.token(), "Headless " + suffix, "The doomed OP");
			postReply(adminToken, threadId, "first reply becomes the new OP");
			postReply(adminToken, threadId, "second reply");
			int opMessageId = messageIdAt(threadId, 1);
			int promotedMessageId = messageIdAt(threadId, 2);

			mockMvc.perform(delete("/message/" + opMessageId)
					.header("Authorization", "Bearer " + owner.token()))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("RECYCLED"))
					.andExpect(jsonPath("$.originThreadRecycled").value(false))
					.andExpect(jsonPath("$.threadId").value(threadId));

			assertEquals(1, threadCount(criteria -> criteria.andThreadIdEqualTo(threadId).andBoardIdEqualTo(1)),
					"the origin thread must survive its OP's deletion");
			assertEquals(List.of(1, 2), postPositionsIn(threadId));
			assertEquals(1, messageCount(criteria -> criteria.andMessageIdEqualTo(promotedMessageId)
					.andThreadIdEqualTo(threadId).andPostInThreadEqualTo(1)),
					"the old post 2 must be promoted to post 1");
			assertEquals(1, messageCount(criteria -> criteria.andMessageIdEqualTo(opMessageId)
					.andBoardIdEqualTo(binBoardId).andPostInThreadEqualTo(1)),
					"the recycled OP must sit in a wrapper thread in the bin");
		}

		@Test
		void solePostDeleteRecyclesTheThreadAndTheForumCacheReflectsIt() throws Exception {
			TestUser owner = createUser("sole_" + suffix);
			String adminToken = getAdminToken();
			int binBoardId = recycleBoardId();

			int survivorThreadId = postThread(owner.token(), "Survivor sole " + suffix, "I stay behind");
			int doomedThreadId = postThread(owner.token(), "Doomed sole " + suffix, "I am the only post");
			int soleMessageId = messageIdAt(doomedThreadId, 1);

			long board1ThreadsBefore = boardSummaryValue(1, BoardSummaryViewDbo::getThreadCount);
			assertEquals(doomedThreadId, (int) boardSummaryValue(1, BoardSummaryViewDbo::getLatestThreadId),
					"the doomed thread must hold the board's latest post before deletion");

			JsonNode board1Before = forumBoardSummary(adminToken, 1);
			long cachedPostCount = board1Before.get("postCount").asLong();
			long cachedThreadCount = board1Before.get("threadCount").asLong();

			mockMvc.perform(delete("/message/" + soleMessageId)
					.header("Authorization", "Bearer " + owner.token()))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("RECYCLED"))
					.andExpect(jsonPath("$.originThreadRecycled").value(true))
					.andExpect(jsonPath("$.boardId").value(1))
					.andExpect(jsonPath("$.recycleThreadId").value(doomedThreadId));

			assertEquals(1, threadCount(criteria -> criteria.andThreadIdEqualTo(doomedThreadId)
					.andBoardIdEqualTo(binBoardId).andRecycledFromBoardIdEqualTo(1)
					.andRecycledFromThreadIdIsNull()));
			assertEquals(board1ThreadsBefore - 1, boardSummaryValue(1, BoardSummaryViewDbo::getThreadCount),
					"board_summary thread_count must drop when the sole-post thread is recycled");
			assertEquals(survivorThreadId, (int) boardSummaryValue(1, BoardSummaryViewDbo::getLatestThreadId),
					"the latest post must promote to the surviving thread");

			JsonNode board1After = forumBoardSummary(adminToken, 1);
			assertEquals(cachedPostCount - 1, board1After.get("postCount").asLong(),
					"the forum cache must be evicted so the index reflects the recycled post");
			assertEquals(cachedThreadCount - 1, board1After.get("threadCount").asLong(),
					"the forum cache must be evicted so the index reflects the recycled thread");
		}

		@Test
		void attachmentAndPollBearingThreadRecyclesWithoutFkAbortAndPurgeDestroysEverything() throws Exception {
			TestUser owner = createUser("atpol_" + suffix);
			String adminToken = getAdminToken();
			int binBoardId = recycleBoardId();
			int adminId = userIdOf(ADMIN_USER);

			int threadId = postThread(owner.token(), "Loaded thread " + suffix, "OP with attachment");
			postReply(owner.token(), threadId, "a reply riding along");
			int opMessageId = messageIdAt(threadId, 1);
			int replyMessageId = messageIdAt(threadId, 2);
			Integer opHistoryId = messageHistoryDboMapper
					.selectByExample(messageHistoryWhere(criteria -> criteria.andMessageIdEqualTo(opMessageId)))
					.stream().map(MessageHistoryDbo::getMessageHistoryId).min(Integer::compareTo).orElse(null);

			ContentResourceTypeDboExample attachmentTypeScope = new ContentResourceTypeDboExample();
			attachmentTypeScope.createCriteria().andContentCodeEqualTo("ATC");
			ContentResourceDbo resource = new ContentResourceDbo();
			resource.setContentTypeId(contentResourceTypeDboMapper.selectByExample(attachmentTypeScope).get(0)
					.getContentResourceTypeId());
			resource.setUploadedUserId(owner.id());
			resource.setFilename("evidence.png");
			resource.setChecksum("cafebabe");
			resource.setFileExt("png");
			resource.setMimeType("image/png");
			resource.setFileSize(123L);
			contentResourceDboMapper.insertSelective(resource);
			Integer resourceId = resource.getContentResourceId();

			FileAttachmentDbo attachment = new FileAttachmentDbo();
			attachment.setMessageId(opMessageId);
			attachment.setActiveFlag(true);
			attachment.setContentResourceId(resourceId);
			fileAttachmentDboMapper.insertSelective(attachment);
			Integer attachmentId = attachment.getFileAttachmentId();

			MigratorAttachmentRefRewriteDbo attachmentRefRewrite = new MigratorAttachmentRefRewriteDbo();
			attachmentRefRewrite.setMessageHistoryId(opHistoryId);
			migratorAttachmentRefRewriteDboMapper.insertSelective(attachmentRefRewrite);

			PollDbo poll = new PollDbo();
			poll.setPollQuestion("Best sword?");
			poll.setThreadId(threadId);
			poll.setCreatedUserId(owner.id());
			pollDboMapper.insertSelective(poll);
			Integer pollId = poll.getPollId();

			PollChoiceDbo pollChoice = new PollChoiceDbo();
			pollChoice.setPollId(pollId);
			pollChoice.setChoiceText("Master Sword");
			pollChoice.setActiveFlag(true);
			pollChoice.setVotes(1);
			pollChoiceDboMapper.insertSelective(pollChoice);
			Integer pollChoiceId = pollChoice.getPollChoiceId();

			UserPollChoiceDbo adminVote = new UserPollChoiceDbo();
			adminVote.setUserId(adminId);
			adminVote.setPollChoiceId(pollChoiceId);
			userPollChoiceDboMapper.insertSelective(adminVote);

			ReactionDbo adminReaction = new ReactionDbo();
			adminReaction.setReactableType("MESSAGE");
			adminReaction.setReactableId(opMessageId);
			adminReaction.setReactorUserId(adminId);
			adminReaction.setReactionTypeId(anyReactionTypeId());
			reactionDboMapper.insertSelective(adminReaction);

			insertLegacyIdMapping("MESSAGE", 910001, opMessageId);
			insertLegacyIdMapping("THREAD", 910002, threadId);
			insertLegacyIdMapping("ATTACHMENT", 910003, attachmentId);
			insertLegacyIdMapping("POLL", 910004, pollId);

			ContentResourceDboExample resourceScope = new ContentResourceDboExample();
			resourceScope.createCriteria().andContentResourceIdEqualTo(resourceId);
			FileAttachmentDboExample attachmentScope = new FileAttachmentDboExample();
			attachmentScope.createCriteria().andFileAttachmentIdEqualTo(attachmentId);
			MigratorAttachmentRefRewriteDboExample rewriteScope = new MigratorAttachmentRefRewriteDboExample();
			rewriteScope.createCriteria().andMessageHistoryIdEqualTo(opHistoryId);
			PollDboExample pollScope = new PollDboExample();
			pollScope.createCriteria().andPollIdEqualTo(pollId);
			PollChoiceDboExample pollChoiceScope = new PollChoiceDboExample();
			pollChoiceScope.createCriteria().andPollIdEqualTo(pollId);
			UserPollChoiceDboExample voteScope = new UserPollChoiceDboExample();
			voteScope.createCriteria().andPollChoiceIdEqualTo(pollChoiceId);
			ReactionDboExample reactionScope = new ReactionDboExample();
			reactionScope.createCriteria().andReactableTypeEqualTo("MESSAGE").andReactableIdEqualTo(opMessageId);
			MigratorIdMapDboExample legacyIdScope = new MigratorIdMapDboExample();
			legacyIdScope.createCriteria().andLegacyIdIn(List.of(910001, 910002, 910003, 910004));
			long historyRowsBefore = messageHistoryCount(
					criteria -> criteria.andMessageIdIn(List.of(opMessageId, replyMessageId)));

			mockMvc.perform(delete("/thread/" + threadId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("RECYCLED"))
					.andExpect(jsonPath("$.boardId").value(1))
					.andExpect(jsonPath("$.recycleThreadId").value(threadId));

			assertEquals(1, threadCount(criteria -> criteria.andThreadIdEqualTo(threadId)
					.andBoardIdEqualTo(binBoardId).andRecycledFromBoardIdEqualTo(1)
					.andRecycledFromThreadIdIsNull()));
			assertEquals(2, messageCount(criteria -> criteria.andThreadIdEqualTo(threadId)
					.andBoardIdEqualTo(binBoardId)),
					"both messages must follow the thread into the bin");
			assertEquals(1, fileAttachmentDboMapper.countByExample(attachmentScope),
					"recycling must not touch attachments");
			assertEquals(1, migratorAttachmentRefRewriteDboMapper.countByExample(rewriteScope));
			assertEquals(1, pollDboMapper.countByExample(pollScope), "recycling must not touch the poll");
			assertEquals(1, reactionDboMapper.countByExample(reactionScope),
					"reactions must move with the recycled thread");
			assertEquals(historyRowsBefore, messageHistoryCount(
					criteria -> criteria.andMessageIdIn(List.of(opMessageId, replyMessageId))));

			mockMvc.perform(delete("/thread/" + threadId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("PURGED"))
					.andExpect(jsonPath("$.boardId").value(binBoardId));

			assertEquals(0, threadCount(criteria -> criteria.andThreadIdEqualTo(threadId)));
			assertEquals(0, messageCount(criteria -> criteria.andThreadIdEqualTo(threadId)));
			assertEquals(0, messageHistoryCount(
					criteria -> criteria.andMessageIdIn(List.of(opMessageId, replyMessageId))));
			assertEquals(0, fileAttachmentDboMapper.countByExample(attachmentScope));
			assertEquals(0, contentResourceDboMapper.countByExample(resourceScope),
					"the unreferenced attachment blob row must be released");
			assertEquals(0, migratorAttachmentRefRewriteDboMapper.countByExample(rewriteScope));
			assertEquals(0, reactionDboMapper.countByExample(reactionScope));
			assertEquals(0, pollDboMapper.countByExample(pollScope));
			assertEquals(0, pollChoiceDboMapper.countByExample(pollChoiceScope));
			assertEquals(0, userPollChoiceDboMapper.countByExample(voteScope));
			assertEquals(0, migratorIdMapDboMapper.countByExample(legacyIdScope));
			assertEquals(1, moderationLogCount("THREAD_PURGED",
					log -> log.getDetail().startsWith("thread_id=" + threadId + " ")));

			mockMvc.perform(delete("/thread/" + threadId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isNotFound());
		}

		@Test
		void restoreCoversLivingOriginGoneOriginAndNonRecycledRejection() throws Exception {
			TestUser owner = createUser("resto_" + suffix);
			String adminToken = getAdminToken();

			int livingOriginId = postThread(owner.token(), "Living origin " + suffix, "OP");
			postReply(owner.token(), livingOriginId, "middle post to recycle");
			postReply(owner.token(), livingOriginId, "tail post");
			int recycledMessageId = messageIdAt(livingOriginId, 2);
			mockMvc.perform(delete("/message/" + recycledMessageId)
					.header("Authorization", "Bearer " + owner.token()))
					.andExpect(status().isOk());
			int wrapperThreadId = messageDboMapper.selectByPrimaryKey(recycledMessageId).getThreadId();

			mockMvc.perform(put("/message/" + recycledMessageId + "/restore")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.mode").value("MERGED_INTO_ORIGIN"))
					.andExpect(jsonPath("$.threadId").value(livingOriginId))
					.andExpect(jsonPath("$.boardId").value(1))
					.andExpect(jsonPath("$.postInThread").value(3));

			assertEquals(1, messageCount(criteria -> criteria.andMessageIdEqualTo(recycledMessageId)
					.andThreadIdEqualTo(livingOriginId).andBoardIdEqualTo(1).andPostInThreadEqualTo(3)),
					"the restored post must be appended at the end of the living origin thread");
			assertEquals(List.of(1, 2, 3), postPositionsIn(livingOriginId));

			assertEquals(0, threadCount(criteria -> criteria.andThreadIdEqualTo(wrapperThreadId)),
					"the emptied wrapper must be garbage-collected on restore");

			assertEquals(1, moderationLogs(criteria -> criteria.andActionEqualTo("MESSAGE_RESTORED")
					.andMessageIdEqualTo(recycledMessageId)).size());

			int goneOriginId = postThread(owner.token(), "Gone origin " + suffix, "OP");
			postReply(owner.token(), goneOriginId, "orphan-to-be");
			int orphanMessageId = messageIdAt(goneOriginId, 2);
			mockMvc.perform(delete("/message/" + orphanMessageId)
					.header("Authorization", "Bearer " + owner.token()))
					.andExpect(status().isOk());
			int orphanWrapperId = messageDboMapper.selectByPrimaryKey(orphanMessageId).getThreadId();
			mockMvc.perform(delete("/thread/" + goneOriginId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("RECYCLED"));
			mockMvc.perform(delete("/thread/" + goneOriginId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("PURGED"));

			ThreadDbo orphanThread = threadDboMapper.selectByPrimaryKey(orphanWrapperId);
			assertNotNull(orphanThread);
			assertNull(orphanThread.getRecycledFromThreadId());
			assertEquals(1, orphanThread.getRecycledFromBoardId());

			mockMvc.perform(put("/message/" + orphanMessageId + "/restore")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isConflict())
					.andExpect(jsonPath("$.detail").value("RESTORE_THREAD_INSTEAD"));
			mockMvc.perform(put("/thread/" + orphanWrapperId + "/restore")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.mode").value("THREAD_RESTORED"))
					.andExpect(jsonPath("$.threadId").value(orphanWrapperId))
					.andExpect(jsonPath("$.boardId").value(1));

			ThreadDbo restoredOrphanThread = threadDboMapper.selectByPrimaryKey(orphanWrapperId);
			assertEquals(1, restoredOrphanThread.getBoardId());
			assertNull(restoredOrphanThread.getRecycledFromBoardId());
			assertNull(restoredOrphanThread.getRecycledFromThreadId());

			MessageDbo restoredMsg = messageDboMapper.selectByPrimaryKey(orphanMessageId);
			assertEquals(1, restoredMsg.getBoardId());

			mockMvc.perform(put("/thread/" + livingOriginId + "/restore")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isConflict())
					.andExpect(jsonPath("$.detail").value("NOT_RECYCLED"));
			mockMvc.perform(put("/message/" + recycledMessageId + "/restore")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isConflict())
					.andExpect(jsonPath("$.detail").value("NOT_RECYCLED"));

			for (String action : new String[] { "THREAD_RECYCLED", "THREAD_RESTORED" })
				assertTrue(!moderationLogs(criteria -> criteria.andActionEqualTo(action)).isEmpty(),
						action + " must be written to the moderation log");
		}
	}

	@Nested
	class Authority {

		@Test
		void moderatorOverridesLocksAndTogglesThreadLock() throws Exception {
			TestUser owner = createUser("authz_" + suffix);
			String adminToken = getAdminToken();

			int threadId = postThread(owner.token(), "Authz matrix " + suffix, "OP");
			postReply(owner.token(), threadId, "first reply");
			int firstReplyId = messageIdAt(threadId, 2);

			mockMvc.perform(put("/thread/" + threadId + "/lockToggle")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk());
			mockMvc.perform(delete("/message/" + firstReplyId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("RECYCLED"));

			mockMvc.perform(put("/thread/" + threadId + "/lockToggle")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk());

			mockMvc.perform(delete("/message/" + firstReplyId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("PURGED"));
			mockMvc.perform(delete("/message/" + firstReplyId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isNotFound());
		}

		@Test
		void reRecyclingARestoredMessageRecyclesAndPurgesAgain() throws Exception {
			TestUser owner = createUser("rb_" + suffix);
			String adminToken = getAdminToken();
			int recycleBoardId = recycleBoardId();

			int threadId = postThread(owner.token(), "Recycle smoke " + suffix, "First post!");
			int replyMessageId = postAndRecycle(owner.token(), threadId, "A reply!");

			mockMvc.perform(put("/message/" + replyMessageId + "/restore")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.mode").value("MERGED_INTO_ORIGIN"));

			mockMvc.perform(delete("/message/" + replyMessageId)
					.header("Authorization", "Bearer " + owner.token()))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("RECYCLED"));

			mockMvc.perform(delete("/message/" + replyMessageId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("PURGED"))
					.andExpect(jsonPath("$.originThreadDeleted").value(true))
					.andExpect(jsonPath("$.boardId").value(recycleBoardId));

			mockMvc.perform(delete("/message/" + replyMessageId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isNotFound());
			assertEquals(0, messageCount(criteria -> criteria.andMessageIdEqualTo(replyMessageId)),
					"purged message row must be gone");
			assertEquals(0, messageHistoryCount(criteria -> criteria.andMessageIdEqualTo(replyMessageId)),
					"purged message history must be gone");
		}

		@Test
		void moderatorMovesStickiesAndSplitsThreadsOverHttp() throws Exception {
			TestUser owner = createUser("modop_" + suffix);
			String adminToken = getAdminToken();

			int threadId = postThread(owner.token(), "Mod ops " + suffix, "OP");
			for (int replyNumber = 2; replyNumber <= 5; replyNumber++)
				postReply(owner.token(), threadId, "reply number " + replyNumber);
			int splitTailStartId = messageIdAt(threadId, 4);
			int splitTailEndId = messageIdAt(threadId, 5);

			mockMvc.perform(put("/thread/" + threadId + "/stickyToggle")
					.header("Authorization", "Bearer " + owner.token()))
					.andExpect(status().isForbidden());

			mockMvc.perform(put("/thread/" + threadId + "/stickyToggle")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.pinnedFlag").value(true));
			assertEquals(1, threadCount(criteria -> criteria.andThreadIdEqualTo(threadId)
					.andPinnedFlagEqualTo(true)));
			mockMvc.perform(put("/thread/" + threadId + "/stickyToggle")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.pinnedFlag").value(false));

			mockMvc.perform(put("/thread/" + threadId + "/move/2")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.boardId").value(2));
			assertEquals(1, threadCount(criteria -> criteria.andThreadIdEqualTo(threadId).andBoardIdEqualTo(2)));
			assertEquals(5, messageCount(criteria -> criteria.andThreadIdEqualTo(threadId).andBoardIdEqualTo(2)),
					"every message must follow its thread to the destination board");

			String splitBody = """
					{"threadId": %d, "boardId": 1, "messageIdsToMove": [%d, %d], "newThreadTitle": "Split off %s"}
					""".formatted(threadId, splitTailStartId, splitTailEndId, suffix);
			MvcResult splitResult = mockMvc.perform(post("/thread/" + threadId + "/split")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(splitBody))
					.andExpect(status().isOk())
					.andReturn();
			int splitThreadId = json.readTree(splitResult.getResponse().getContentAsString())
					.get("id").asInt();

			assertEquals(1, threadCount(criteria -> criteria.andThreadIdEqualTo(splitThreadId).andBoardIdEqualTo(1)
					.andThreadNameEqualTo("Split off " + suffix)),
					"the split must open a fresh thread on the requested board");
			assertEquals(2, messageCount(criteria -> criteria.andThreadIdEqualTo(splitThreadId).andBoardIdEqualTo(1)),
					"the selected tail messages must move into the split thread");
			assertEquals(3, messageCount(criteria -> criteria.andThreadIdEqualTo(threadId)),
					"the origin thread must keep its unselected messages");
			assertEquals(List.of(1, 2), postPositionsIn(splitThreadId),
					"the split thread must be densely resequenced");
			assertEquals(List.of(1, 2, 3), postPositionsIn(threadId),
					"the origin thread must be densely resequenced");
		}

		@Test
		void splitResequencesBothThreadsAndKeepsEveryPageReachable() throws Exception {
			TestUser owner = createUser("splitseq_" + suffix);
			String adminToken = getAdminToken();

			int threadId = postThread(owner.token(), "Split resequence " + suffix, "OP body");
			for (int replyNumber = 2; replyNumber <= 12; replyNumber++)
				postReply(owner.token(), threadId, "reply number " + replyNumber);

			int highSourcePost11Id = messageIdAt(threadId, 11);
			int highSourcePost12Id = messageIdAt(threadId, 12);
			String splitBody = """
					{"threadId": %d, "boardId": 1, "messageIdsToMove": [%d, %d, %d, %d, %d, %d], "newThreadTitle": "Split middle %s"}
					"""
					.formatted(threadId, messageIdAt(threadId, 5), messageIdAt(threadId, 6), messageIdAt(threadId, 7),
							messageIdAt(threadId, 8), messageIdAt(threadId, 9), messageIdAt(threadId, 10), suffix);
			MvcResult splitResult = mockMvc.perform(post("/thread/" + threadId + "/split")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(splitBody))
					.andExpect(status().isOk())
					.andReturn();
			int splitThreadId = json.readTree(splitResult.getResponse().getContentAsString()).get("id").asInt();

			assertEquals(List.of(1, 2, 3, 4, 5, 6), postPositionsIn(splitThreadId),
					"the destination thread must be densely resequenced from 1");
			assertEquals(List.of(1, 2, 3, 4, 5, 6), postPositionsIn(threadId),
					"the gapped source thread must be compacted");

			MvcResult sourcePage = mockMvc.perform(get("/thread/" + threadId).param("page", "1").param("pageSize", "10")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andReturn();
			JsonNode sourceMessages = json.readTree(sourcePage.getResponse().getContentAsString()).get("messages");
			assertEquals(6, sourceMessages.size(), "every surviving source post must be reachable on page 1");
			boolean reachesFormerPost11 = false;
			boolean reachesFormerPost12 = false;
			for (JsonNode message : sourceMessages) {
				int messageId = message.get("id").asInt();
				if (messageId == highSourcePost11Id)
					reachesFormerPost11 = true;
				if (messageId == highSourcePost12Id)
					reachesFormerPost12 = true;
			}
			assertTrue(reachesFormerPost11 && reachesFormerPost12,
					"the formerly-high-positioned source posts must now be reachable on page 1");

			MvcResult splitPage = mockMvc.perform(get("/thread/" + splitThreadId).param("page", "1")
					.param("pageSize", "10")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andReturn();
			assertEquals(6, json.readTree(splitPage.getResponse().getContentAsString()).get("messages").size(),
					"all moved posts must be reachable on page 1 of the split thread");

			assertEquals(1, moderationLogCount("THREAD_SPLIT",
					log -> log.getThreadId() != null && log.getThreadId() == splitThreadId
							&& log.getDetail().contains("thread_id=" + threadId + " split:")));
		}

		@Test
		void splitIgnoresForeignMessagesAndGarbageCollectsAnEmptiedSource() throws Exception {
			TestUser owner = createUser("splitfgn_" + suffix);
			String adminToken = getAdminToken();

			int threadA = postThread(owner.token(), "Split A " + suffix, "A op");
			postReply(owner.token(), threadA, "A reply 1");
			postReply(owner.token(), threadA, "A reply 2");
			int threadB = postThread(owner.token(), "Split B " + suffix, "B op");
			postReply(owner.token(), threadB, "B reply 1");
			int foreignId = messageIdAt(threadB, 2);
			int validAtailId = messageIdAt(threadA, 3);

			String foreignDragBody = """
					{"threadId": %d, "boardId": 1, "messageIdsToMove": [%d, %d], "newThreadTitle": "Foreign drag %s"}
					""".formatted(threadA, validAtailId, foreignId, suffix);
			MvcResult dragResult = mockMvc.perform(post("/thread/" + threadA + "/split")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(foreignDragBody))
					.andExpect(status().isOk())
					.andReturn();
			int dragSplitThreadId = json.readTree(dragResult.getResponse().getContentAsString()).get("id").asInt();

			assertEquals(1, messageCount(criteria -> criteria.andMessageIdEqualTo(foreignId)
					.andThreadIdEqualTo(threadB)),
					"a foreign message must not be dragged out of its own thread");
			assertEquals(1, messageCount(criteria -> criteria.andThreadIdEqualTo(dragSplitThreadId)),
					"only the source-thread member may move into the split thread");
			assertEquals(List.of(1, 2), postPositionsIn(threadB), "the foreign thread must stay intact");

			String foreignOnlyBody = """
					{"threadId": %d, "boardId": 1, "messageIdsToMove": [%d], "newThreadTitle": "Foreign only %s"}
					""".formatted(threadA, foreignId, suffix);
			mockMvc.perform(post("/thread/" + threadA + "/split")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(foreignOnlyBody))
					.andExpect(status().isBadRequest());
			assertEquals(List.of(1, 2), postPositionsIn(threadB),
					"a rejected foreign-only split must not touch the foreign thread");

			int threadC = postThread(owner.token(), "Split C " + suffix, "C op");
			postReply(owner.token(), threadC, "C reply 1");
			String fullSplitBody = """
					{"threadId": %d, "boardId": 1, "messageIdsToMove": [%d, %d], "newThreadTitle": "Full split %s"}
					""".formatted(threadC, messageIdAt(threadC, 1), messageIdAt(threadC, 2), suffix);
			MvcResult fullResult = mockMvc.perform(post("/thread/" + threadC + "/split")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(fullSplitBody))
					.andExpect(status().isOk())
					.andReturn();
			int fullSplitThreadId = json.readTree(fullResult.getResponse().getContentAsString()).get("id").asInt();

			assertEquals(0, threadCount(criteria -> criteria.andThreadIdEqualTo(threadC)),
					"a fully-emptied source thread must be garbage-collected");
			assertEquals(List.of(1, 2), postPositionsIn(fullSplitThreadId),
					"the new thread must hold every moved post densely resequenced");
			assertEquals(1, moderationLogs(criteria -> criteria.andActionEqualTo("THREAD_SPLIT")
					.andThreadIdEqualTo(fullSplitThreadId)).size(),
					"the split log must reference the surviving new thread, not the deleted source");
		}
	}

	@Nested
	class Policies {

		@Test
		void withoutARecycleBinOwnerDeletesPurgePermanently() throws Exception {
			TestUser owner = createUser("nobin_" + suffix);
			String configuredBin = systemConfigDboMapper.selectByPrimaryKey(RECYCLE_BOARD_CONFIG_KEY)
					.getConfigValue();
			systemConfigDboMapper.deleteByPrimaryKey(RECYCLE_BOARD_CONFIG_KEY);
			try {
				int threadId = postThread(owner.token(), "No bin " + suffix, "OP survives one round");
				postReply(owner.token(), threadId, "purged straight away");
				int replyMessageId = messageIdAt(threadId, 2);
				int opMessageId = messageIdAt(threadId, 1);
				int configuredBinId = Integer.parseInt(configuredBin);
				long binThreadsBefore = threadCount(criteria -> criteria.andBoardIdEqualTo(configuredBinId));

				mockMvc.perform(delete("/message/" + replyMessageId)
						.header("Authorization", "Bearer " + owner.token()))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.outcome").value("PURGED"))
						.andExpect(jsonPath("$.originThreadDeleted").value(false))
						.andExpect(jsonPath("$.threadId").value(threadId))
						.andExpect(jsonPath("$.pageCount").value(1));
				assertEquals(0, messageCount(criteria -> criteria.andMessageIdEqualTo(replyMessageId)),
						"without a bin the owner delete must destroy the message");
				assertEquals(0, messageHistoryCount(criteria -> criteria.andMessageIdEqualTo(replyMessageId)));
				assertEquals(binThreadsBefore, threadCount(criteria -> criteria.andBoardIdEqualTo(configuredBinId)),
						"no wrapper thread may be created in no-bin mode");
				assertEquals(List.of(1), postPositionsIn(threadId));

				mockMvc.perform(delete("/message/" + opMessageId)
						.header("Authorization", "Bearer " + owner.token()))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.outcome").value("PURGED"))
						.andExpect(jsonPath("$.originThreadDeleted").value(true))
						.andExpect(jsonPath("$.boardId").value(1));
				assertEquals(0, messageCount(criteria -> criteria.andMessageIdEqualTo(opMessageId)));
				assertEquals(0, threadCount(criteria -> criteria.andThreadIdEqualTo(threadId)),
						"the emptied thread must be garbage-collected in no-bin mode");
				assertEquals(2, moderationLogCount("MESSAGE_PURGED", log -> log.getMessageId() == null
						&& log.getDetail().contains("thread_id=" + threadId + " ")));
			} finally {
				SystemConfigDbo restoredBin = new SystemConfigDbo();
				restoredBin.setConfigKey(RECYCLE_BOARD_CONFIG_KEY);
				restoredBin.setConfigValue(configuredBin);
				systemConfigDboMapper.insertSelective(restoredBin);
			}
		}

		@Test
		void accountWipePurgesTheSubjectsRecycledPosts() throws Exception {
			TestUser victim = createUser("wiperc_" + suffix);
			String adminToken = getAdminToken();

			int hostThreadId = postThread(adminToken, "Host thread " + suffix, "Admin OP");
			postReply(victim.token(), hostThreadId, "recycled before the wipe");
			postReply(victim.token(), hostThreadId, "still live at wipe time");
			int recycledMessageId = messageIdAt(hostThreadId, 2);
			mockMvc.perform(delete("/message/" + recycledMessageId)
					.header("Authorization", "Bearer " + victim.token()))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("RECYCLED"));
			Integer wrapperThreadId = messageDboMapper.selectByPrimaryKey(recycledMessageId).getThreadId();
			assertEquals(1, messageCount(criteria -> criteria.andMessageIdEqualTo(recycledMessageId)
					.andOwnerIdEqualTo(victim.id())),
					"the recycled post must still belong to its author while it sits in the bin");

			userService.eraseUserRecords(victim.id(), DeletionMode.PURGE);

			UserDboExample victimScope = new UserDboExample();
			victimScope.createCriteria().andUserIdEqualTo(victim.id());
			assertEquals(0, userDboMapper.countByExample(victimScope));
			assertEquals(0, messageCount(criteria -> criteria.andMessageIdEqualTo(recycledMessageId)),
					"account deletion must purge the recycled post out of the bin");
			assertEquals(0, messageCount(criteria -> criteria.andOwnerIdEqualTo(victim.id())));
			assertEquals(0, threadCount(criteria -> criteria.andThreadIdEqualTo(wrapperThreadId)),
					"the emptied wrapper thread must be garbage-collected by the wipe");
			assertEquals(1, threadCount(criteria -> criteria.andThreadIdEqualTo(hostThreadId)));
		}
	}

	private int recycleBoardId() {
		SystemConfigDbo recycleBin = systemConfigDboMapper.selectByPrimaryKey(RECYCLE_BOARD_CONFIG_KEY);
		assertNotNull(recycleBin, "installSampleData must configure the recycle bin");
		return Integer.parseInt(recycleBin.getConfigValue());
	}

	private int anyReactionTypeId() {
		Integer reactionTypeId = reactionTypeDboMapper.selectByExample(new ReactionTypeDboExample()).stream()
				.map(ReactionTypeDbo::getReactionTypeId).min(Integer::compareTo).orElse(null);
		assertNotNull(reactionTypeId);
		return reactionTypeId;
	}

	private void insertLegacyIdMapping(String entityType, int legacyId, int zfgbbId) {
		MigratorIdMapDbo mapping = new MigratorIdMapDbo();
		mapping.setEntityType(entityType);
		mapping.setLegacyId(legacyId);
		mapping.setZfgbbId(zfgbbId);
		migratorIdMapDboMapper.insertSelective(mapping);
	}

	private long threadCount(Consumer<ThreadDboExample.Criteria> criteria) {
		ThreadDboExample example = new ThreadDboExample();
		criteria.accept(example.createCriteria());
		return threadDboMapper.countByExample(example);
	}

	private long messageCount(Consumer<MessageDboExample.Criteria> criteria) {
		MessageDboExample example = new MessageDboExample();
		criteria.accept(example.createCriteria());
		return messageDboMapper.countByExample(example);
	}

	private MessageHistoryDboExample messageHistoryWhere(Consumer<MessageHistoryDboExample.Criteria> criteria) {
		MessageHistoryDboExample example = new MessageHistoryDboExample();
		criteria.accept(example.createCriteria());
		return example;
	}

	private long messageHistoryCount(Consumer<MessageHistoryDboExample.Criteria> criteria) {
		return messageHistoryDboMapper.countByExample(messageHistoryWhere(criteria));
	}

	private List<ModerationLogDbo> moderationLogs(Consumer<ModerationLogDboExample.Criteria> criteria) {
		ModerationLogDboExample example = new ModerationLogDboExample();
		criteria.accept(example.createCriteria());
		return moderationLogDboMapper.selectByExample(example);
	}

	private long moderationLogCount(String action, Predicate<ModerationLogDbo> matcher) {
		return moderationLogs(criteria -> criteria.andActionEqualTo(action)).stream()
				.filter(log -> log.getDetail() != null).filter(matcher).count();
	}

	private long boardSummaryValue(int boardId, Function<BoardSummaryViewDbo, Number> field) {
		BoardSummaryViewDboExample example = new BoardSummaryViewDboExample();
		example.createCriteria().andBoardIdEqualTo(boardId);
		List<BoardSummaryViewDbo> summaries = boardSummaryViewDboMapper.selectByExample(example);
		assertEquals(1, summaries.size(), "board_summary must carry exactly one row per board");
		Number value = field.apply(summaries.get(0));
		assertNotNull(value);
		return value.longValue();
	}

	private JsonNode forumBoardSummary(String accessToken, int boardId) throws Exception {
		MvcResult forumResult = mockMvc.perform(get("/board/forum")
				.header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isOk())
				.andReturn();
		JsonNode forum = json.readTree(forumResult.getResponse().getContentAsString());
		for (JsonNode category : forum.get("categories"))
			for (JsonNode board : category.get("boards"))
				if (board.get("boardId").asInt() == boardId)
					return board;
		throw new AssertionError("board " + boardId + " not present in the forum payload");
	}
}
