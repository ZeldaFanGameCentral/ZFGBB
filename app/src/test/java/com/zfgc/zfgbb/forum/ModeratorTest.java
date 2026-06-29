package com.zfgc.zfgbb.forum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.zfgc.zfgbb.services.core.AccountDeletionService;
import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

import tools.jackson.databind.JsonNode;

class ModeratorTest extends PostgresIntegrationTest {

	@Autowired
	private AccountDeletionService accountDeletionService;

	@Nested
	class RecycleAndRestore {

		@Test
		void ownerRecyclesMiddlePostAndOnlyModeratorMayPurgeIt() throws Exception {
			String ownerName = "midp_" + suffix;
			register(ownerName, "password123");
			String ownerToken = login(ownerName, "password123").get("accessToken").asString();
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
			int binBoardId = recycleBoardId();

			int threadId = postThread(ownerToken, "Middle post " + suffix, "OP body " + suffix);
			for (int replyNumber = 2; replyNumber <= 11; replyNumber++)
				postReply(ownerToken, threadId, "reply number " + replyNumber);
			int middleMessageId = messageIdAt(threadId, 6);
			String secretBody = "SECRET_MIDDLE_BODY_" + suffix;
			jdbcTemplate.update("update zfgbb.message_history set message_text = ? where message_id = ?",
					secretBody, middleMessageId);

			long board1PostsBefore = boardSummaryValue(1, "post_count");
			long binPostsBefore = boardSummaryValue(binBoardId, "post_count");

			MvcResult recycleResult = mockMvc.perform(delete("/message/" + middleMessageId)
					.header("Authorization", "Bearer " + ownerToken))
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
			assertEquals(1, count("zfgbb.thread where thread_id = " + wrapperThreadId
					+ " and board_id = " + binBoardId
					+ " and recycled_from_board_id = 1 and recycled_from_thread_id = " + threadId),
					"wrapper thread must live in the bin and carry origin provenance");
			assertEquals(1, count("zfgbb.message where message_id = " + middleMessageId
					+ " and thread_id = " + wrapperThreadId + " and board_id = " + binBoardId
					+ " and post_in_thread = 1"),
					"recycled message must be re-parented to position 1 of the wrapper");

			assertEquals(board1PostsBefore - 1, boardSummaryValue(1, "post_count"),
					"board_summary post_count of the origin board must drop by one");
			assertEquals(binPostsBefore + 1, boardSummaryValue(binBoardId, "post_count"),
					"board_summary post_count of the bin must grow by one");

			assertEquals(1, count("zfgbb.moderation_log where action = 'MESSAGE_RECYCLED'"
					+ " and message_id = " + middleMessageId + " and thread_id = " + threadId
					+ " and board_id = 1 and actor_user_id = " + userIdOf(ownerName)
					+ " and target_user_id = " + userIdOf(ownerName)));
			String logDetail = jdbcTemplate.queryForObject(
					"select detail from zfgbb.moderation_log where action = 'MESSAGE_RECYCLED' and message_id = ?",
					String.class, middleMessageId);
			assertNotNull(logDetail);
			assertFalse(logDetail.contains(secretBody), "the moderation log must never contain the post body");

			mockMvc.perform(delete("/message/" + middleMessageId)
					.header("Authorization", "Bearer " + ownerToken))
					.andExpect(status().isForbidden());
			assertEquals(1, count("zfgbb.message where message_id = " + middleMessageId),
					"a non-moderator second delete must not destroy the binned message");

			mockMvc.perform(delete("/message/" + middleMessageId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("PURGED"))
					.andExpect(jsonPath("$.originThreadDeleted").value(true))
					.andExpect(jsonPath("$.boardId").value(binBoardId));
			assertEquals(0, count("zfgbb.message where message_id = " + middleMessageId));
			assertEquals(0, count("zfgbb.message_history where message_id = " + middleMessageId));
			assertEquals(0, count("zfgbb.thread where thread_id = " + wrapperThreadId),
					"the emptied wrapper thread must be garbage-collected by the purge");
			assertEquals(1, count("zfgbb.moderation_log where action = 'MESSAGE_PURGED'"
					+ " and detail like '%message_id=" + middleMessageId + " %'"));
		}

		@Test
		void ownerDeletesOpWithRepliesAndTheThreadSurvivesHeadless() throws Exception {
			String ownerName = "opdel_" + suffix;
			register(ownerName, "password123");
			String ownerToken = login(ownerName, "password123").get("accessToken").asString();
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
			int binBoardId = recycleBoardId();

			int threadId = postThread(ownerToken, "Headless " + suffix, "The doomed OP");
			postReply(adminToken, threadId, "first reply becomes the new OP");
			postReply(adminToken, threadId, "second reply");
			int opMessageId = messageIdAt(threadId, 1);
			int promotedMessageId = messageIdAt(threadId, 2);

			mockMvc.perform(delete("/message/" + opMessageId)
					.header("Authorization", "Bearer " + ownerToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("RECYCLED"))
					.andExpect(jsonPath("$.originThreadRecycled").value(false))
					.andExpect(jsonPath("$.threadId").value(threadId));

			assertEquals(1, count("zfgbb.thread where thread_id = " + threadId + " and board_id = 1"),
					"the origin thread must survive its OP's deletion");
			assertEquals(List.of(1, 2), postPositionsIn(threadId));
			assertEquals(1, count("zfgbb.message where message_id = " + promotedMessageId
					+ " and thread_id = " + threadId + " and post_in_thread = 1"),
					"the old post 2 must be promoted to post 1");
			assertEquals(1, count("zfgbb.message where message_id = " + opMessageId
					+ " and board_id = " + binBoardId + " and post_in_thread = 1"),
					"the recycled OP must sit in a wrapper thread in the bin");
		}

		@Test
		void solePostDeleteRecyclesTheThreadAndTheForumCacheReflectsIt() throws Exception {
			String ownerName = "sole_" + suffix;
			register(ownerName, "password123");
			String ownerToken = login(ownerName, "password123").get("accessToken").asString();
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
			int binBoardId = recycleBoardId();

			int survivorThreadId = postThread(ownerToken, "Survivor sole " + suffix, "I stay behind");
			int doomedThreadId = postThread(ownerToken, "Doomed sole " + suffix, "I am the only post");
			int soleMessageId = messageIdAt(doomedThreadId, 1);

			long board1ThreadsBefore = boardSummaryValue(1, "thread_count");
			assertEquals(doomedThreadId, (int) boardSummaryValue(1, "latest_thread_id"),
					"the doomed thread must hold the board's latest post before deletion");

			JsonNode board1Before = forumBoardSummary(adminToken, 1);
			long cachedPostCount = board1Before.get("postCount").asLong();
			long cachedThreadCount = board1Before.get("threadCount").asLong();

			mockMvc.perform(delete("/message/" + soleMessageId)
					.header("Authorization", "Bearer " + ownerToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("RECYCLED"))
					.andExpect(jsonPath("$.originThreadRecycled").value(true))
					.andExpect(jsonPath("$.boardId").value(1))
					.andExpect(jsonPath("$.recycleThreadId").value(doomedThreadId));

			assertEquals(1, count("zfgbb.thread where thread_id = " + doomedThreadId
					+ " and board_id = " + binBoardId
					+ " and recycled_from_board_id = 1 and recycled_from_thread_id is null"));
			assertEquals(board1ThreadsBefore - 1, boardSummaryValue(1, "thread_count"),
					"board_summary thread_count must drop when the sole-post thread is recycled");
			assertEquals(survivorThreadId, (int) boardSummaryValue(1, "latest_thread_id"),
					"the latest post must promote to the surviving thread");

			JsonNode board1After = forumBoardSummary(adminToken, 1);
			assertEquals(cachedPostCount - 1, board1After.get("postCount").asLong(),
					"the forum cache must be evicted so the index reflects the recycled post");
			assertEquals(cachedThreadCount - 1, board1After.get("threadCount").asLong(),
					"the forum cache must be evicted so the index reflects the recycled thread");
		}

		@Test
		void attachmentAndPollBearingThreadRecyclesWithoutFkAbortAndPurgeDestroysEverything() throws Exception {
			String ownerName = "atpol_" + suffix;
			register(ownerName, "password123");
			String ownerToken = login(ownerName, "password123").get("accessToken").asString();
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
			int binBoardId = recycleBoardId();
			int ownerId = userIdOf(ownerName);
			int adminId = userIdOf(ADMIN_USER);

			int threadId = postThread(ownerToken, "Loaded thread " + suffix, "OP with attachment");
			postReply(ownerToken, threadId, "a reply riding along");
			int opMessageId = messageIdAt(threadId, 1);
			int replyMessageId = messageIdAt(threadId, 2);
			Integer opHistoryId = jdbcTemplate.queryForObject(
					"select min(message_history_id) from zfgbb.message_history where message_id = ?",
					Integer.class, opMessageId);

			Integer resourceId = jdbcTemplate.queryForObject(
					"""
							insert into zfgbb.content_resource (content_type_id, uploaded_user_id, filename, checksum,
								file_ext, mime_type, file_size)
							values ((select content_resource_type_id from zfgbb.content_resource_type where content_code = 'ATC'),
								?, 'evidence.png', 'cafebabe', 'png', 'image/png', 123)
							returning content_resource_id
							""",
					Integer.class, ownerId);
			Integer attachmentId = jdbcTemplate.queryForObject(
					"insert into zfgbb.file_attachments (message_id, active_flag, content_resource_id)"
							+ " values (?, true, ?) returning file_attachment_id",
					Integer.class, opMessageId, resourceId);
			jdbcTemplate.update("insert into zfgbb.migrator_attachment_ref_rewrites (message_history_id) values (?)",
					opHistoryId);
			Integer pollId = jdbcTemplate.queryForObject(
					"insert into zfgbb.poll (poll_question, thread_id, created_user_id)"
							+ " values ('Best sword?', ?, ?) returning poll_id",
					Integer.class, threadId, ownerId);
			Integer pollChoiceId = jdbcTemplate.queryForObject(
					"insert into zfgbb.poll_choice (poll_id, choice_text, active_flag, votes)"
							+ " values (?, 'Master Sword', true, 1) returning poll_choice_id",
					Integer.class, pollId);
			jdbcTemplate.update("insert into zfgbb.user_poll_choice (user_poll_choice_id, user_id, poll_choice_id)"
					+ " values ((select coalesce(max(user_poll_choice_id), 0) + 1 from zfgbb.user_poll_choice), ?, ?)",
					adminId, pollChoiceId);
			jdbcTemplate.update("insert into zfgbb.reaction (reactable_type, reactable_id, reactor_user_id,"
					+ " reaction_type_id) values ('MESSAGE', ?, ?,"
					+ " (select min(reaction_type_id) from zfgbb.reaction_type))", opMessageId, adminId);
			jdbcTemplate.update("insert into zfgbb.migrator_id_map (entity_type, legacy_id, zfgbb_id) values"
					+ " ('MESSAGE', 910001, ?), ('THREAD', 910002, ?), ('ATTACHMENT', 910003, ?), ('POLL', 910004, ?)",
					opMessageId, threadId, attachmentId, pollId);
			int historyRowsBefore = count("zfgbb.message_history where message_id in (" + opMessageId + ", "
					+ replyMessageId + ")");

			mockMvc.perform(delete("/thread/" + threadId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("RECYCLED"))
					.andExpect(jsonPath("$.boardId").value(1))
					.andExpect(jsonPath("$.recycleThreadId").value(threadId));

			assertEquals(1, count("zfgbb.thread where thread_id = " + threadId + " and board_id = " + binBoardId
					+ " and recycled_from_board_id = 1 and recycled_from_thread_id is null"));
			assertEquals(2, count("zfgbb.message where thread_id = " + threadId + " and board_id = " + binBoardId),
					"both messages must follow the thread into the bin");
			assertEquals(1, count("zfgbb.file_attachments where file_attachment_id = " + attachmentId),
					"recycling must not touch attachments");
			assertEquals(1, count("zfgbb.migrator_attachment_ref_rewrites where message_history_id = " + opHistoryId));
			assertEquals(1, count("zfgbb.poll where poll_id = " + pollId), "recycling must not touch the poll");
			assertEquals(1, count("zfgbb.reaction where reactable_type = 'MESSAGE' and reactable_id = " + opMessageId),
					"reactions must move with the recycled thread");
			assertEquals(historyRowsBefore, count("zfgbb.message_history where message_id in (" + opMessageId + ", "
					+ replyMessageId + ")"));

			mockMvc.perform(delete("/thread/" + threadId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("PURGED"))
					.andExpect(jsonPath("$.boardId").value(binBoardId));

			assertEquals(0, count("zfgbb.thread where thread_id = " + threadId));
			assertEquals(0, count("zfgbb.message where thread_id = " + threadId));
			assertEquals(0, count("zfgbb.message_history where message_id in (" + opMessageId + ", "
					+ replyMessageId + ")"));
			assertEquals(0, count("zfgbb.file_attachments where file_attachment_id = " + attachmentId));
			assertEquals(0, count("zfgbb.content_resource where content_resource_id = " + resourceId),
					"the unreferenced attachment blob row must be released");
			assertEquals(0, count("zfgbb.migrator_attachment_ref_rewrites where message_history_id = " + opHistoryId));
			assertEquals(0, count("zfgbb.reaction where reactable_type = 'MESSAGE' and reactable_id = " + opMessageId));
			assertEquals(0, count("zfgbb.poll where poll_id = " + pollId));
			assertEquals(0, count("zfgbb.poll_choice where poll_id = " + pollId));
			assertEquals(0, count("zfgbb.user_poll_choice where poll_choice_id = " + pollChoiceId));
			assertEquals(0, count("zfgbb.migrator_id_map where legacy_id in (910001, 910002, 910003, 910004)"));
			assertEquals(1, count("zfgbb.moderation_log where action = 'THREAD_PURGED'"
					+ " and detail like 'thread_id=" + threadId + " %'"));

			mockMvc.perform(delete("/thread/" + threadId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isNotFound());
		}

		@Test
		void restoreCoversLivingOriginGoneOriginAndNonRecycledRejection() throws Exception {
			String ownerName = "resto_" + suffix;
			register(ownerName, "password123");
			String ownerToken = login(ownerName, "password123").get("accessToken").asString();
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();

			int livingOriginId = postThread(ownerToken, "Living origin " + suffix, "OP");
			postReply(ownerToken, livingOriginId, "middle post to recycle");
			postReply(ownerToken, livingOriginId, "tail post");
			int recycledMessageId = messageIdAt(livingOriginId, 2);
			mockMvc.perform(delete("/message/" + recycledMessageId)
					.header("Authorization", "Bearer " + ownerToken))
					.andExpect(status().isOk());
			Integer wrapperThreadId = jdbcTemplate.queryForObject(
					"select thread_id from zfgbb.message where message_id = ?", Integer.class, recycledMessageId);

			mockMvc.perform(put("/message/" + recycledMessageId + "/restore")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.mode").value("MERGED_INTO_ORIGIN"))
					.andExpect(jsonPath("$.threadId").value(livingOriginId))
					.andExpect(jsonPath("$.boardId").value(1))
					.andExpect(jsonPath("$.postInThread").value(3));
			assertEquals(1, count("zfgbb.message where message_id = " + recycledMessageId
					+ " and thread_id = " + livingOriginId + " and board_id = 1 and post_in_thread = 3"),
					"the restored post must be appended at the end of the living origin thread");
			assertEquals(List.of(1, 2, 3), postPositionsIn(livingOriginId));
			assertEquals(0, count("zfgbb.thread where thread_id = " + wrapperThreadId),
					"the emptied wrapper must be garbage-collected on restore");
			assertEquals(1, count("zfgbb.moderation_log where action = 'MESSAGE_RESTORED'"
					+ " and message_id = " + recycledMessageId));

			int goneOriginId = postThread(ownerToken, "Gone origin " + suffix, "OP");
			postReply(ownerToken, goneOriginId, "orphan-to-be");
			int orphanMessageId = messageIdAt(goneOriginId, 2);
			mockMvc.perform(delete("/message/" + orphanMessageId)
					.header("Authorization", "Bearer " + ownerToken))
					.andExpect(status().isOk());
			Integer orphanWrapperId = jdbcTemplate.queryForObject(
					"select thread_id from zfgbb.message where message_id = ?", Integer.class, orphanMessageId);
			mockMvc.perform(delete("/thread/" + goneOriginId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("RECYCLED"));
			mockMvc.perform(delete("/thread/" + goneOriginId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("PURGED"));
			assertEquals(1, count("zfgbb.thread where thread_id = " + orphanWrapperId
					+ " and recycled_from_thread_id is null and recycled_from_board_id = 1"),
					"purging the origin must null the wrapper's thread provenance via the SET NULL FK");

			mockMvc.perform(put("/message/" + orphanMessageId + "/restore")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isConflict())
					.andExpect(content().string("RESTORE_THREAD_INSTEAD"));
			mockMvc.perform(put("/thread/" + orphanWrapperId + "/restore")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.mode").value("THREAD_RESTORED"))
					.andExpect(jsonPath("$.threadId").value(orphanWrapperId))
					.andExpect(jsonPath("$.boardId").value(1));
			assertEquals(1, count("zfgbb.thread where thread_id = " + orphanWrapperId
					+ " and board_id = 1 and recycled_from_board_id is null and recycled_from_thread_id is null"),
					"the orphaned wrapper must come back as a live thread on the origin board");
			assertEquals(1, count("zfgbb.message where message_id = " + orphanMessageId + " and board_id = 1"));

			mockMvc.perform(put("/thread/" + livingOriginId + "/restore")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isConflict())
					.andExpect(content().string("NOT_RECYCLED"));
			mockMvc.perform(put("/message/" + recycledMessageId + "/restore")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isConflict())
					.andExpect(content().string("NOT_RECYCLED"));

			for (String action : new String[] { "THREAD_RECYCLED", "THREAD_RESTORED" })
				assertTrue(count("zfgbb.moderation_log where action = '" + action + "'") >= 1,
						action + " must be written to the moderation log");
		}
	}

	@Nested
	class Authority {

		@Test
		void moderatorOverridesLocksAndTogglesThreadLock() throws Exception {
			String ownerName = "authz_" + suffix;
			register(ownerName, "password123");
			String ownerToken = login(ownerName, "password123").get("accessToken").asString();
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();

			int threadId = postThread(ownerToken, "Authz matrix " + suffix, "OP");
			postReply(ownerToken, threadId, "first reply");
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
			String ownerName = "rb_" + suffix;
			register(ownerName, "password123");
			String ownerToken = login(ownerName, "password123").get("accessToken").asString();
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
			int recycleBoardId = recycleBoardId();

			int threadId = postThread(ownerToken, "Recycle smoke " + suffix, "First post!");
			int replyMessageId = postAndRecycle(ownerToken, threadId, "A reply!");

			mockMvc.perform(put("/message/" + replyMessageId + "/restore")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.mode").value("MERGED_INTO_ORIGIN"));

			mockMvc.perform(delete("/message/" + replyMessageId)
					.header("Authorization", "Bearer " + ownerToken))
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
			assertEquals(0, count("zfgbb.message where message_id = " + replyMessageId),
					"purged message row must be gone");
			assertEquals(0, count("zfgbb.message_history where message_id = " + replyMessageId),
					"purged message history must be gone");
		}

		@Test
		void moderatorMovesStickiesAndSplitsThreadsOverHttp() throws Exception {
			String ownerName = "modop_" + suffix;
			register(ownerName, "password123");
			String ownerToken = login(ownerName, "password123").get("accessToken").asString();
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();

			int threadId = postThread(ownerToken, "Mod ops " + suffix, "OP");
			for (int replyNumber = 2; replyNumber <= 5; replyNumber++)
				postReply(ownerToken, threadId, "reply number " + replyNumber);
			int splitTailStartId = messageIdAt(threadId, 4);
			int splitTailEndId = messageIdAt(threadId, 5);

			mockMvc.perform(put("/thread/" + threadId + "/stickyToggle")
					.header("Authorization", "Bearer " + ownerToken))
					.andExpect(status().isForbidden());

			mockMvc.perform(put("/thread/" + threadId + "/stickyToggle")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.pinnedFlag").value(true));
			assertEquals(1, count("zfgbb.thread where thread_id = " + threadId + " and pinned_flag"));
			mockMvc.perform(put("/thread/" + threadId + "/stickyToggle")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.pinnedFlag").value(false));

			mockMvc.perform(put("/thread/" + threadId + "/move/2")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.boardId").value(2));
			assertEquals(1, count("zfgbb.thread where thread_id = " + threadId + " and board_id = 2"));
			assertEquals(5, count("zfgbb.message where thread_id = " + threadId + " and board_id = 2"),
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

			assertEquals(1, count("zfgbb.thread where thread_id = " + splitThreadId
					+ " and board_id = 1 and thread_name = 'Split off " + suffix + "'"),
					"the split must open a fresh thread on the requested board");
			assertEquals(2, count("zfgbb.message where thread_id = " + splitThreadId + " and board_id = 1"),
					"the selected tail messages must move into the split thread");
			assertEquals(3, count("zfgbb.message where thread_id = " + threadId),
					"the origin thread must keep its unselected messages");
			assertEquals(List.of(1, 2), postPositionsIn(splitThreadId),
					"the split thread must be densely resequenced");
			assertEquals(List.of(1, 2, 3), postPositionsIn(threadId),
					"the origin thread must be densely resequenced");
		}

		@Test
		void splitResequencesBothThreadsAndKeepsEveryPageReachable() throws Exception {
			String ownerName = "splitseq_" + suffix;
			register(ownerName, "password123");
			String ownerToken = login(ownerName, "password123").get("accessToken").asString();
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();

			int threadId = postThread(ownerToken, "Split resequence " + suffix, "OP body");
			for (int replyNumber = 2; replyNumber <= 12; replyNumber++)
				postReply(ownerToken, threadId, "reply number " + replyNumber);

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

			assertEquals(1, count("zfgbb.moderation_log where action = 'THREAD_SPLIT' and thread_id = " + splitThreadId
					+ " and detail like '%thread_id=" + threadId + " split:%'"));
		}

		@Test
		void splitIgnoresForeignMessagesAndGarbageCollectsAnEmptiedSource() throws Exception {
			String ownerName = "splitfgn_" + suffix;
			register(ownerName, "password123");
			String ownerToken = login(ownerName, "password123").get("accessToken").asString();
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();

			int threadA = postThread(ownerToken, "Split A " + suffix, "A op");
			postReply(ownerToken, threadA, "A reply 1");
			postReply(ownerToken, threadA, "A reply 2");
			int threadB = postThread(ownerToken, "Split B " + suffix, "B op");
			postReply(ownerToken, threadB, "B reply 1");
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

			assertEquals(1, count("zfgbb.message where message_id = " + foreignId + " and thread_id = " + threadB),
					"a foreign message must not be dragged out of its own thread");
			assertEquals(1, count("zfgbb.message where thread_id = " + dragSplitThreadId),
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

			int threadC = postThread(ownerToken, "Split C " + suffix, "C op");
			postReply(ownerToken, threadC, "C reply 1");
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

			assertEquals(0, count("zfgbb.thread where thread_id = " + threadC),
					"a fully-emptied source thread must be garbage-collected");
			assertEquals(List.of(1, 2), postPositionsIn(fullSplitThreadId),
					"the new thread must hold every moved post densely resequenced");
			assertEquals(1, count("zfgbb.moderation_log where action = 'THREAD_SPLIT' and thread_id = "
					+ fullSplitThreadId),
					"the split log must reference the surviving new thread, not the deleted source");
		}
	}

	@Nested
	class Policies {

		@Test
		void withoutARecycleBinOwnerDeletesPurgePermanently() throws Exception {
			String ownerName = "nobin_" + suffix;
			register(ownerName, "password123");
			String ownerToken = login(ownerName, "password123").get("accessToken").asString();
			String configuredBin = jdbcTemplate.queryForObject(
					"select config_value from zfgbb.system_config where config_key = 'recycle_board_id'", String.class);
			jdbcTemplate.update("delete from zfgbb.system_config where config_key = 'recycle_board_id'");
			try {
				int threadId = postThread(ownerToken, "No bin " + suffix, "OP survives one round");
				postReply(ownerToken, threadId, "purged straight away");
				int replyMessageId = messageIdAt(threadId, 2);
				int opMessageId = messageIdAt(threadId, 1);
				long binThreadsBefore = count("zfgbb.thread where board_id = " + Integer.parseInt(configuredBin));

				mockMvc.perform(delete("/message/" + replyMessageId)
						.header("Authorization", "Bearer " + ownerToken))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.outcome").value("PURGED"))
						.andExpect(jsonPath("$.originThreadDeleted").value(false))
						.andExpect(jsonPath("$.threadId").value(threadId))
						.andExpect(jsonPath("$.pageCount").value(1));
				assertEquals(0, count("zfgbb.message where message_id = " + replyMessageId),
						"without a bin the owner delete must destroy the message");
				assertEquals(0, count("zfgbb.message_history where message_id = " + replyMessageId));
				assertEquals(binThreadsBefore,
						count("zfgbb.thread where board_id = " + Integer.parseInt(configuredBin)),
						"no wrapper thread may be created in no-bin mode");
				assertEquals(List.of(1), postPositionsIn(threadId));

				mockMvc.perform(delete("/message/" + opMessageId)
						.header("Authorization", "Bearer " + ownerToken))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.outcome").value("PURGED"))
						.andExpect(jsonPath("$.originThreadDeleted").value(true))
						.andExpect(jsonPath("$.boardId").value(1));
				assertEquals(0, count("zfgbb.message where message_id = " + opMessageId));
				assertEquals(0, count("zfgbb.thread where thread_id = " + threadId),
						"the emptied thread must be garbage-collected in no-bin mode");
				assertEquals(2, count("zfgbb.moderation_log where action = 'MESSAGE_PURGED'"
						+ " and message_id is null and detail like '%thread_id=" + threadId + " %'"));
			} finally {
				jdbcTemplate.update("insert into zfgbb.system_config (config_key, config_value)"
						+ " values ('recycle_board_id', ?)", configuredBin);
			}
		}

		@Test
		void accountWipePurgesTheSubjectsRecycledPosts() throws Exception {
			String victimName = "wiperc_" + suffix;
			register(victimName, "password123");
			String victimToken = login(victimName, "password123").get("accessToken").asString();
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
			int victimId = userIdOf(victimName);

			int hostThreadId = postThread(adminToken, "Host thread " + suffix, "Admin OP");
			postReply(victimToken, hostThreadId, "recycled before the wipe");
			postReply(victimToken, hostThreadId, "still live at wipe time");
			int recycledMessageId = messageIdAt(hostThreadId, 2);
			mockMvc.perform(delete("/message/" + recycledMessageId)
					.header("Authorization", "Bearer " + victimToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("RECYCLED"));
			Integer wrapperThreadId = jdbcTemplate.queryForObject(
					"select thread_id from zfgbb.message where message_id = ?", Integer.class, recycledMessageId);
			assertEquals(1, count("zfgbb.message where message_id = " + recycledMessageId
					+ " and owner_id = " + victimId),
					"the recycled post must still belong to its author while it sits in the bin");

			Integer requestId = jdbcTemplate.queryForObject(
					"""
							insert into zfgbb.account_deletion_request (user_id, mode, status, token_sha256, requested_ts, expires_ts)
							values (?, 'WIPE', 'CONFIRMED', ?, current_timestamp, current_timestamp + interval '24 hours')
							returning account_deletion_request_id
							""",
					Integer.class, victimId, UUID.randomUUID().toString());
			accountDeletionService.executeConfirmedDeletion(requestId);

			assertEquals(0, count("zfgbb.\"user\" where user_id = " + victimId));
			assertEquals(0, count("zfgbb.message where message_id = " + recycledMessageId),
					"account deletion must purge the recycled post out of the bin");
			assertEquals(0, count("zfgbb.message where owner_id = " + victimId));
			assertEquals(0, count("zfgbb.thread where thread_id = " + wrapperThreadId),
					"the emptied wrapper thread must be garbage-collected by the wipe");
			assertEquals(1, count("zfgbb.thread where thread_id = " + hostThreadId));
		}
	}

	private int recycleBoardId() {
		Integer boardId = jdbcTemplate.queryForObject(
				"select config_value::integer from zfgbb.system_config where config_key = 'recycle_board_id'",
				Integer.class);
		assertNotNull(boardId, "installSampleData must configure the recycle bin");
		return boardId;
	}

	private long boardSummaryValue(int boardId, String column) {
		Long value = jdbcTemplate.queryForObject(
				"select " + column + " from zfgbb.board_summary where board_id = " + boardId, Long.class);
		assertNotNull(value);
		return value;
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
