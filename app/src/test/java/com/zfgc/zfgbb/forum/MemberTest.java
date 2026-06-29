package com.zfgc.zfgbb.forum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

import tools.jackson.databind.JsonNode;

class MemberTest extends PostgresIntegrationTest {

	@Nested
	class Guest {

		private static final int GUEST_PERMISSION_ID = 2;
		private static final int READ_ONLY_PERMISSION_ID = 9;
		private static final int ZFGC_USER_PERMISSION_ID = 1;
		private static final int VISIBLE_BOARD_ID = 1;
		private static final String OCARINA_SLUG = "ocarina-of-time";
		private static final int OCARINA_ENTITY_ID = 1;

		@Test
		void postCountExcludesHiddenAndRecycleBoards() throws Exception {
			String ownerName = "vis_" + suffix;
			register(ownerName, "password123");
			String ownerToken = login(ownerName, "password123").get("accessToken").asString();
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
			int ownerId = userIdOf(ownerName);

			postThread(ownerToken, "Vis A " + suffix, "op body");
			int threadB = postThread(ownerToken, "Vis B " + suffix, "op body");
			postReply(ownerToken, threadB, "reply to recycle");

			int hiddenBoardId = insertBoard("Hidden " + suffix);
			grantBoardPermission(hiddenBoardId, ZFGC_USER_PERMISSION_ID);
			int hiddenThreadId = insertThread("Hidden thread " + suffix, hiddenBoardId, ownerId);
			insertMessage(ownerId, hiddenThreadId, hiddenBoardId);

			int recycledMessageId = messageIdAt(threadB, 2);
			mockMvc.perform(delete("/message/" + recycledMessageId)
					.header("Authorization", "Bearer " + ownerToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("RECYCLED"));

			mockMvc.perform(get("/user-profile/" + ownerId))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.bioInfo.postCount").value(2));

			mockMvc.perform(get("/user-profile/" + ownerId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.bioInfo.postCount").value(2));
		}

		@Test
		void threadPageRendersEveryDistinctAuthorCardCorrectly() throws Exception {
			String authorAName = "aAuth_" + suffix;
			String authorBName = "bAuth_" + suffix;
			register(authorAName, "password123");
			register(authorBName, "password123");
			String tokenA = login(authorAName, "password123").get("accessToken").asString();
			String tokenB = login(authorBName, "password123").get("accessToken").asString();
			int authorAId = userIdOf(authorAName);
			int authorBId = userIdOf(authorBName);

			Integer moderatorPermissionId = jdbcTemplate.queryForObject(
					"select permission_id from zfgbb.permission where permission_code = 'ZFGC_SITE_MODERATOR'",
					Integer.class);
			assertNotNull(moderatorPermissionId);
			jdbcTemplate.update("insert into zfgbb.br_user_permission (user_id, user_permission_id) values (?, ?)",
					authorBId, moderatorPermissionId);

			jdbcTemplate.update("update zfgbb.user_bio_info set signature = ? where user_id = ?",
					"[b]author A signature[/b]", authorAId);
			jdbcTemplate.update("update zfgbb.user_bio_info set signature = ? where user_id = ?",
					"[b]author B signature[/b]", authorBId);

			int threadId = postThread(tokenA, "Multi author thread " + suffix, "opening body by A");
			postReply(tokenB, threadId, "reply body by B");
			postReply(tokenA, threadId, "second reply body by A");

			int hiddenBoardId = insertBoard("Hidden multi " + suffix);
			grantBoardPermission(hiddenBoardId, ZFGC_USER_PERMISSION_ID);
			int hiddenThreadId = insertThread("Hidden multi thread " + suffix, hiddenBoardId, authorAId);
			insertMessage(authorAId, hiddenThreadId, hiddenBoardId);

			int authorBMessageId = messageIdAt(threadId, 2);
			Integer reactionTypeId = jdbcTemplate.queryForObject(
					"select min(reaction_type_id) from zfgbb.reaction_type", Integer.class);
			assertNotNull(reactionTypeId);
			mockMvc.perform(post("/reactions")
					.header("Authorization", "Bearer " + tokenA)
					.contentType(MediaType.APPLICATION_JSON)
					.content("""
							{"reactableType": "MESSAGE", "reactableId": %d, "reactionTypeId": %d}
							""".formatted(authorBMessageId, reactionTypeId)))
					.andExpect(status().isOk());

			MvcResult result = mockMvc.perform(get("/thread/" + threadId)
					.param("page", "1")
					.param("pageSize", "10"))
					.andExpect(status().isOk())
					.andReturn();
			JsonNode messages = json.readTree(result.getResponse().getContentAsString()).get("messages");
			assertNotNull(messages, "the thread response must carry a messages array");

			JsonNode authorACard = authorCardForPost(messages, 1);
			JsonNode authorBCard = authorCardForPost(messages, 2);
			JsonNode authorASecondCard = authorCardForPost(messages, 3);

			assertEquals(authorAName, authorACard.get("displayName").asText());
			assertEquals(authorBName, authorBCard.get("displayName").asText());
			assertEquals(authorAName, authorASecondCard.get("displayName").asText());

			assertTrue(permissionCodesOf(authorACard).isEmpty(),
					"a plain member's public card must expose no rank permissions after retain");
			assertEquals(Set.of("ZFGC_SITE_MODERATOR"), permissionCodesOf(authorBCard),
					"a batch-seeded author must be retained down to only public rank permissions");

			assertSignatureHiddenButParsed(authorACard);
			assertSignatureHiddenButParsed(authorBCard);

			assertEquals(2, authorACard.get("bioInfo").get("postCount").asInt(),
					"the guest-filtered post count must exclude the hidden-board post");
			assertEquals(1, authorBCard.get("bioInfo").get("postCount").asInt());

			assertTrue(authorACard.get("awards").isArray() && authorACard.get("awards").isEmpty(),
					"awards must serialize as an empty array, not null");
			assertTrue(authorBCard.get("awards").isArray() && authorBCard.get("awards").isEmpty());

			assertEquals(1, authorBCard.get("reactionSummary").get("reactionCount").asInt(),
					"a reaction on a batch-seeded author's message must surface in the reaction summary");
		}

		private JsonNode authorCardForPost(JsonNode messages, int postInThread) {
			for (JsonNode message : messages)
				if (message.hasNonNull("postInThread") && message.get("postInThread").asInt() == postInThread)
					return message.get("createdUser");
			throw new AssertionError("no message at post_in_thread " + postInThread);
		}

		private void assertSignatureHiddenButParsed(JsonNode createdUser) {
			JsonNode bioInfo = createdUser.get("bioInfo");
			assertNotNull(bioInfo, "a public author card must carry bio info");
			JsonNode signature = bioInfo.get("signature");
			assertTrue(signature == null || signature.isNull(), "the raw signature must be stripped from public cards");
			JsonNode signatureParsed = bioInfo.get("signatureParsed");
			assertTrue(signatureParsed != null && signatureParsed.isTextual() && !signatureParsed.asText().isBlank(),
					"the rendered signature must survive the strip");
		}

		private Set<String> permissionCodesOf(JsonNode createdUser) {
			Set<String> codes = new HashSet<>();
			JsonNode permissions = createdUser.get("permissions");
			if (permissions != null && !permissions.isNull())
				for (JsonNode permission : permissions)
					codes.add(permission.get("permissionCode").asText());
			return codes;
		}

		@Test
		void readOnlyOnlyBoardCountsTowardGuestBaseline() throws Exception {
			String ownerName = "ro_" + suffix;
			register(ownerName, "password123");
			String ownerToken = login(ownerName, "password123").get("accessToken").asString();
			int ownerId = userIdOf(ownerName);

			postThread(ownerToken, "Read only base " + suffix, "op body");

			int readOnlyBoardId = insertBoard("Read only " + suffix);
			grantBoardPermission(readOnlyBoardId, READ_ONLY_PERMISSION_ID);
			int readOnlyThreadId = insertThread("Read only thread " + suffix, readOnlyBoardId, ownerId);
			insertMessage(ownerId, readOnlyThreadId, readOnlyBoardId);

			mockMvc.perform(get("/user-profile/" + ownerId))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.bioInfo.postCount").value(2));
		}

		@Test
		void projectNewsHidesHiddenBoardThreadTitle() throws Exception {
			String authorName = "news_" + suffix;
			register(authorName, "password123");
			int authorId = userIdOf(authorName);

			int publicThreadId = insertThread("PUBLIC_TITLE", VISIBLE_BOARD_ID, authorId);
			int hiddenBoardId = insertBoard("Secret board " + suffix);
			grantBoardPermission(hiddenBoardId, ZFGC_USER_PERMISSION_ID);
			int secretThreadId = insertThread("SECRET_TITLE", hiddenBoardId, authorId);

			insertProjectNews(OCARINA_ENTITY_ID, publicThreadId, "Public update " + suffix, "public body");
			insertProjectNews(OCARINA_ENTITY_ID, secretThreadId, "Hidden update " + suffix, "hidden body");

			MvcResult result = mockMvc.perform(get("/projects/" + OCARINA_SLUG)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andReturn();
			String body = result.getResponse().getContentAsString();
			assertFalse(body.contains("SECRET_TITLE"), "hidden-board thread title must not leak in the response");

			JsonNode news = json.readTree(body).get("news");
			JsonNode publicEntry = newsEntryFor(news, publicThreadId);
			JsonNode secretEntry = newsEntryFor(news, secretThreadId);
			assertEquals("PUBLIC_TITLE", publicEntry.get("threadName").asText());
			assertTrue(secretEntry.get("threadName") == null || secretEntry.get("threadName").isNull(),
					"hidden-board news entry must not resolve a thread name");
			assertEquals(publicThreadId, publicEntry.get("threadId").asInt());
			assertEquals(secretThreadId, secretEntry.get("threadId").asInt());
		}

		@Test
		void hiddenBoardThreadIsSearchableOnlyWithTheBoardPermission() throws Exception {
			String memberName = "srch_" + suffix;
			register(memberName, "password123");
			String memberToken = login(memberName, "password123").get("accessToken").asString();
			int memberId = userIdOf(memberName);

			String uniqueTitle = "SearchVisHidden" + suffix;
			int hiddenBoardId = insertBoard("Search hidden " + suffix);
			grantBoardPermission(hiddenBoardId, ZFGC_USER_PERMISSION_ID);
			insertThread(uniqueTitle, hiddenBoardId, memberId);

			MvcResult anonymousResult = mockMvc.perform(get("/search").param("q", uniqueTitle))
					.andExpect(status().isOk())
					.andReturn();
			assertFalse(forumGroupHasThreadTitled(anonymousResult.getResponse().getContentAsString(), uniqueTitle),
					"anonymous search must not surface a thread from a board it lacks permission for");

			MvcResult memberResult = mockMvc.perform(get("/search")
					.param("q", uniqueTitle)
					.header("Authorization", "Bearer " + memberToken))
					.andExpect(status().isOk())
					.andReturn();
			assertTrue(forumGroupHasThreadTitled(memberResult.getResponse().getContentAsString(), uniqueTitle),
					"a member holding the board permission must find the hidden-board thread");
		}

		@Test
		void forumIndexCacheServesDeepCopiesAndEvictsOnWrites() throws Exception {
			Integer adminPermissionId = jdbcTemplate.queryForObject(
					"select permission_id from zfgbb.permission where permission_code = 'ZFGC_SITE_ADMIN'",
					Integer.class);
			jdbcTemplate.update("""
					insert into zfgbb.board (board_id, board_name, category_id, seqno, created_ts, updated_ts)
					values (999, 'Restricted Cache Probe', 1, 99, now(), now())
					on conflict (board_id) do nothing
					""");
			jdbcTemplate.update("""
					insert into zfgbb.br_board_permission (br_board_permission_id, board_id, permission_id)
					values (999, 999, ?)
					on conflict (br_board_permission_id) do nothing
					""", adminPermissionId);

			String memberName = "cache_" + suffix;
			register(memberName, "password123");
			String memberToken = login(memberName, "password123").get("accessToken").asString();
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();

			JsonNode adminFirstView = fetchForum(adminToken);
			assertTrue(boardIdsOf(adminFirstView).contains(999),
					"admin must see the restricted board on the forum index");

			JsonNode memberView = fetchForum(memberToken);
			assertFalse(boardIdsOf(memberView).contains(999),
					"member must not see the restricted board on the forum index");

			JsonNode adminSecondView = fetchForum(adminToken);
			assertEquals(adminFirstView, adminSecondView,
					"a filtered member request must not corrupt the cached unfiltered forum");

			String evictionProbeName = "Eviction probe " + suffix;
			String threadBody = """
					{"title": "%s", "body": "Cache eviction probe post"}
					""".formatted(evictionProbeName);
			mockMvc.perform(post("/thread")
					.param("boardId", "1")
					.header("Authorization", "Bearer " + memberToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(threadBody))
					.andExpect(status().isOk());

			JsonNode adminAfterPostView = fetchForum(adminToken);
			assertEquals(evictionProbeName, latestThreadNameOfBoard(adminAfterPostView, 1),
					"posting a thread must evict the forum cache so the index shows the new latest post");
		}

		@Test
		void anonymousWriteAttemptsAreRejectedEverywhere() throws Exception {
			String payload = """
					{"reactableType": "MESSAGE", "reactableId": 1, "reactionTypeId": 1}
					""";
			mockMvc.perform(post("/reactions")
					.contentType(MediaType.APPLICATION_JSON)
					.content(payload))
					.andExpect(status().isUnauthorized());

			mockMvc.perform(delete("/message/1"))
					.andExpect(status().isUnauthorized());
		}

		@Test
		void hiddenChildBoardSummaryDoesNotLeakToGuests() throws Exception {
			String ownerName = "childleak_" + suffix;
			register(ownerName, "password123");
			int ownerId = userIdOf(ownerName);
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
			int siteAdminPermissionId = siteAdminPermissionId();

			int parentBoardId = insertBoard("Parent leak " + suffix);
			grantBoardPermission(parentBoardId, GUEST_PERMISSION_ID);
			grantBoardPermission(parentBoardId, siteAdminPermissionId);

			int hiddenChildId = insertChildBoard("Hidden child " + suffix, parentBoardId);
			grantBoardPermission(hiddenChildId, siteAdminPermissionId);
			String hiddenThreadTitle = "HIDDENCHILDLEAK" + suffix;
			int hiddenThreadId = insertThread(hiddenThreadTitle, hiddenChildId, ownerId);
			insertMessage(ownerId, hiddenThreadId, hiddenChildId);

			int visibleChildId = insertChildBoard("Visible child " + suffix, parentBoardId);
			grantBoardPermission(visibleChildId, GUEST_PERMISSION_ID);
			grantBoardPermission(visibleChildId, siteAdminPermissionId);
			int visibleThreadId = insertThread("VisibleChildThread" + suffix, visibleChildId, ownerId);
			insertMessage(ownerId, visibleThreadId, visibleChildId);

			MvcResult anonymousResult = mockMvc.perform(get("/board/" + parentBoardId))
					.andExpect(status().isOk())
					.andReturn();
			String anonymousBody = anonymousResult.getResponse().getContentAsString();
			assertFalse(anonymousBody.contains(hiddenThreadTitle),
					"a hidden child board's latest thread title must not leak to guests");
			Set<Integer> anonymousChildIds = childBoardIdsOf(anonymousBody);
			assertFalse(anonymousChildIds.contains(hiddenChildId),
					"the hidden child board must not appear in the guest child list");
			assertTrue(anonymousChildIds.contains(visibleChildId),
					"the guest-readable sibling child must survive the filter");

			MvcResult adminResult = mockMvc.perform(get("/board/" + parentBoardId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andReturn();
			Set<Integer> adminChildIds = childBoardIdsOf(adminResult.getResponse().getContentAsString());
			assertTrue(adminChildIds.contains(hiddenChildId), "an admin must still see the hidden child board");
			assertTrue(adminChildIds.contains(visibleChildId), "an admin must still see the visible child board");
		}

		@Test
		void hiddenThreadReadsReturn404NotDistinguishableFromMissing() throws Exception {
			String ownerName = "hidthr_" + suffix;
			register(ownerName, "password123");
			int ownerId = userIdOf(ownerName);
			int siteAdminPermissionId = siteAdminPermissionId();

			int hiddenBoardId = insertBoard("Hidden thread board " + suffix);
			grantBoardPermission(hiddenBoardId, siteAdminPermissionId);
			int hiddenThreadId = insertThread("Hidden thread " + suffix, hiddenBoardId, ownerId);
			insertMessage(ownerId, hiddenThreadId, hiddenBoardId);
			Integer hiddenMessageId = jdbcTemplate.queryForObject(
					"select message_id from zfgbb.message where thread_id = ? and post_in_thread = 1",
					Integer.class, hiddenThreadId);
			assertNotNull(hiddenMessageId);

			int missingThreadId = 2_000_000_000;

			mockMvc.perform(get("/thread/" + hiddenThreadId).param("page", "1").param("pageSize", "10"))
					.andExpect(status().isNotFound());
			mockMvc.perform(get("/thread/" + missingThreadId).param("page", "1").param("pageSize", "10"))
					.andExpect(status().isNotFound());
			mockMvc.perform(get("/thread/" + hiddenThreadId + "/allowed-actions"))
					.andExpect(status().isNotFound());
			mockMvc.perform(get("/message/" + hiddenMessageId + "/allowed-actions"))
					.andExpect(status().isNotFound());

			int visibleThreadId = insertThread("Visible thread " + suffix, VISIBLE_BOARD_ID, ownerId);
			insertMessage(ownerId, visibleThreadId, VISIBLE_BOARD_ID);
			mockMvc.perform(get("/thread/" + visibleThreadId + "/allowed-actions"))
					.andExpect(status().isOk());
		}

		@Test
		void hugePageNumberOnBoardListingReturnsEmptyPageNot500() throws Exception {
			mockMvc.perform(get("/board/" + VISIBLE_BOARD_ID).param("page", "2000000000"))
					.andExpect(status().isOk());
		}

		private int siteAdminPermissionId() {
			Integer permissionId = jdbcTemplate.queryForObject(
					"select permission_id from zfgbb.permission where permission_code = 'ZFGC_SITE_ADMIN'",
					Integer.class);
			assertNotNull(permissionId);
			return permissionId;
		}

		private int insertChildBoard(String boardName, int parentBoardId) {
			Integer boardId = jdbcTemplate.queryForObject(
					"insert into zfgbb.board (board_name, seqno, parent_board_id) values (?, 0, ?) returning board_id",
					Integer.class, boardName, parentBoardId);
			assertNotNull(boardId);
			return boardId;
		}

		private Set<Integer> childBoardIdsOf(String boardResponseBody) {
			Set<Integer> childBoardIds = new HashSet<>();
			JsonNode childBoards = json.readTree(boardResponseBody).get("childBoards");
			if (childBoards != null && !childBoards.isNull())
				for (JsonNode childBoard : childBoards)
					childBoardIds.add(childBoard.get("boardId").asInt());
			return childBoardIds;
		}

		private Set<Integer> boardIdsOf(JsonNode forum) {
			Set<Integer> boardIds = new HashSet<>();
			for (JsonNode category : forum.get("categories"))
				for (JsonNode board : category.get("boards"))
					boardIds.add(board.get("boardId").asInt());
			return boardIds;
		}

		private String latestThreadNameOfBoard(JsonNode forum, int boardId) {
			for (JsonNode category : forum.get("categories"))
				for (JsonNode board : category.get("boards"))
					if (board.get("boardId").asInt() == boardId)
						return board.get("threadName").asString();
			return null;
		}

		private boolean forumGroupHasThreadTitled(String responseBody, String title) {
			JsonNode groups = json.readTree(responseBody).get("groups");
			assertNotNull(groups, "search response must carry a groups array");
			for (JsonNode group : groups) {
				if (!"forum".equals(group.get("type").asText()))
					continue;
				for (JsonNode hit : group.get("hits"))
					if (hit.hasNonNull("title") && title.equals(hit.get("title").asText()))
						return true;
			}
			return false;
		}

		private JsonNode newsEntryFor(JsonNode news, int threadId) {
			assertNotNull(news, "project response must carry a news array");
			for (JsonNode entry : news)
				if (entry.hasNonNull("threadId") && entry.get("threadId").asInt() == threadId)
					return entry;
			throw new AssertionError("no project-news entry linked thread " + threadId);
		}

		private int insertBoard(String boardName) {
			Integer boardId = jdbcTemplate.queryForObject(
					"insert into zfgbb.board (board_name, seqno) values (?, 0) returning board_id",
					Integer.class, boardName);
			assertNotNull(boardId);
			return boardId;
		}

		private void grantBoardPermission(int boardId, int permissionId) {
			jdbcTemplate.update("insert into zfgbb.br_board_permission (board_id, permission_id) values (?, ?)",
					boardId, permissionId);
		}

		private int insertThread(String threadName, int boardId, int createdUserId) {
			Integer threadId = jdbcTemplate.queryForObject(
					"insert into zfgbb.thread (thread_name, board_id, created_user_id) values (?, ?, ?) returning thread_id",
					Integer.class, threadName, boardId, createdUserId);
			assertNotNull(threadId);
			return threadId;
		}

		private void insertMessage(int ownerId, int threadId, int boardId) {
			jdbcTemplate.update(
					"insert into zfgbb.message (owner_id, thread_id, board_id, post_in_thread) values (?, ?, ?, 1)",
					ownerId, threadId, boardId);
		}

		private void insertProjectNews(int contentEntityId, int threadId, String subject, String body) {
			jdbcTemplate
					.update("insert into zfgbb.project_news (content_entity_id, thread_id, subject, body, published_ts)"
							+ " values (?, ?, ?, ?, current_timestamp)", contentEntityId, threadId, subject, body);
		}
	}

	@Nested
	class Posting {

		@Test
		void registerLoginThreadAndReply() throws Exception {
			String userName = "it_" + suffix;
			register(userName, "password123");

			JsonNode loginJson = login(userName, "password123");
			String accessToken = loginJson.get("accessToken").asString();

			String threadBody = """
					{"title": "Hello world", "body": "First post!"}
					""";
			MvcResult threadResult = mockMvc.perform(post("/thread")
					.param("boardId", "1")
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(threadBody))
					.andExpect(status().isOk())
					.andReturn();
			int threadId = json.readTree(threadResult.getResponse().getContentAsString()).get("id").asInt();

			String replyBody = """
					{"body": "A reply!"}
					""";
			mockMvc.perform(post("/message/" + threadId)
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(replyBody))
					.andExpect(status().isOk());

			assertEquals(2, count("zfgbb.message where thread_id = " + threadId + " and board_id = 1"),
					"posted messages must carry their board's id for the board_summary view");
		}

		@Test
		void memberEditsTheirOwnPostAndTheRevisionIsRecorded() throws Exception {
			String editorName = "edit_" + suffix;
			register(editorName, "password123");
			String accessToken = login(editorName, "password123").get("accessToken").asString();
			int threadId = postThread(accessToken, "Editable " + suffix, "Original body");
			int messageId = messageIdAt(threadId, 1);

			mockMvc.perform(put("/message/" + messageId)
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"body\": \"Edited body\"}"))
					.andExpect(status().isOk());

			assertEquals("Edited body", jdbcTemplate.queryForObject(
					"select message_text from zfgbb.message_history where message_id = ? and current_flag = true",
					String.class, messageId),
					"editing must replace the current revision body");
			assertEquals(2, count("zfgbb.message_history where message_id = " + messageId),
					"editing must record a new revision instead of overwriting history");
		}

		@Test
		void memberListsOwnPostHistoryPaged() throws Exception {
			String memberName = "hist_" + suffix;
			register(memberName, "password123");
			String memberToken = login(memberName, "password123").get("accessToken").asString();
			int memberId = userIdOf(memberName);

			String openingBody = "history op body " + suffix;
			int threadId = postThread(memberToken, "History " + suffix, openingBody);
			for (int replyNumber = 2; replyNumber <= 4; replyNumber++)
				postReply(memberToken, threadId, "history reply " + replyNumber + " " + suffix);

			MvcResult firstPageResult = mockMvc.perform(get("/message/user/" + memberId)
					.param("page", "1")
					.param("pageSize", "3")
					.header("Authorization", "Bearer " + memberToken))
					.andExpect(status().isOk())
					.andReturn();
			MvcResult secondPageResult = mockMvc.perform(get("/message/user/" + memberId)
					.param("page", "2")
					.param("pageSize", "3")
					.header("Authorization", "Bearer " + memberToken))
					.andExpect(status().isOk())
					.andReturn();

			JsonNode firstPage = json.readTree(firstPageResult.getResponse().getContentAsString());
			JsonNode secondPage = json.readTree(secondPageResult.getResponse().getContentAsString());
			assertEquals(3, firstPage.size(), "the first page must hold exactly pageSize messages");
			assertEquals(1, secondPage.size(), "the second page must hold the remaining message");

			Set<Integer> pagedMessageIds = new HashSet<>();
			for (JsonNode message : firstPage)
				pagedMessageIds.add(message.get("id").asInt());
			for (JsonNode message : secondPage)
				pagedMessageIds.add(message.get("id").asInt());
			assertEquals(4, pagedMessageIds.size(), "the two pages must partition the member's post history");
			assertTrue(secondPage.get(0).get("currentMessage").get("messageText").asText().contains(openingBody),
					"newest-first paging must leave the opening post on the last page, rendered");
		}

		@Test
		void memberPreviewsBbcodeWithQuoteScope() throws Exception {
			String memberName = "prev_" + suffix;
			register(memberName, "password123");
			String memberToken = login(memberName, "password123").get("accessToken").asString();

			String sourceBody = "Preview quote source " + suffix;
			int threadId = postThread(memberToken, "Preview thread " + suffix, sourceBody);
			int sourceMessageId = messageIdAt(threadId, 1);

			String previewPayload = """
					{"content": "[quote msg=%d][/quote][b]bold preview[/b]", "scope": "FORUM"}
					""".formatted(sourceMessageId);
			MvcResult previewResult = mockMvc.perform(post("/content/preview")
					.header("Authorization", "Bearer " + memberToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(previewPayload))
					.andExpect(status().isOk())
					.andReturn();

			String contentParsed = json.readTree(previewResult.getResponse().getContentAsString())
					.get("contentParsed").asString();
			assertTrue(contentParsed.contains(sourceBody),
					"the preview must splice the quoted source body through the quote scope");
			assertTrue(contentParsed.contains("bb-code-b"), "the preview must render bbcode to html");
			assertFalse(contentParsed.contains("[b]"), "no raw bbcode may survive in the rendered preview");
		}

		@Test
		void memberStartsDiscussionThread() throws Exception {
			jdbcTemplate.update("""
					insert into zfgbb.system_config (config_key, config_value)
					values ('cms_discussion_board_id', '1')
					on conflict (config_key) do update set config_value = '1'
					""");
			String memberName = "disc_" + suffix;
			register(memberName, "password123");
			String memberToken = login(memberName, "password123").get("accessToken").asString();

			MvcResult projectResult = mockMvc.perform(post("/projects/majora-s-mask-3d/discussion")
					.header("Authorization", "Bearer " + memberToken))
					.andExpect(status().isOk())
					.andReturn();
			int projectThreadId = json.readTree(projectResult.getResponse().getContentAsString())
					.get("threadId").asInt();
			assertEquals(1, count("zfgbb.thread where thread_id = " + projectThreadId + " and board_id = 1"),
					"the project discussion thread must open on the configured discussion board");
			assertEquals(1, count("zfgbb.content_entity where slug = 'majora-s-mask-3d'"
					+ " and thread_id = " + projectThreadId),
					"the project must link its freshly opened discussion thread");

			mockMvc.perform(post("/projects/majora-s-mask-3d/discussion")
					.header("Authorization", "Bearer " + memberToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.threadId").value(projectThreadId));

			MvcResult resourceResult = mockMvc.perform(post("/resources/eiji-aonuma-zelda-credits-list/discussion")
					.header("Authorization", "Bearer " + memberToken))
					.andExpect(status().isOk())
					.andReturn();
			int resourceThreadId = json.readTree(resourceResult.getResponse().getContentAsString())
					.get("threadId").asInt();
			assertEquals(1, count("zfgbb.thread where thread_id = " + resourceThreadId + " and board_id = 1"),
					"the resource discussion thread must open on the configured discussion board");
			assertEquals(1, count("zfgbb.content_entity where slug = 'eiji-aonuma-zelda-credits-list'"
					+ " and thread_id = " + resourceThreadId),
					"the resource must link its freshly opened discussion thread");
		}

		@Test
		void readOnlyMemberCannotStartCmsDiscussion() throws Exception {
			jdbcTemplate.update("""
					insert into zfgbb.system_config (config_key, config_value)
					values ('cms_discussion_board_id', '1')
					on conflict (config_key) do update set config_value = '1'
					""");
			jdbcTemplate.update("update zfgbb.content_entity set thread_id = null where slug = 'majora-s-mask-3d'");

			String memberName = "rodisc_" + suffix;
			register(memberName, "password123");
			String memberToken = login(memberName, "password123").get("accessToken").asString();
			int memberId = userIdOf(memberName);
			jdbcTemplate.update(
					"insert into zfgbb.br_user_permission (user_id, user_permission_id) values (?, 9)", memberId);

			long board1ThreadsBefore = count("zfgbb.thread where board_id = 1");

			mockMvc.perform(post("/projects/majora-s-mask-3d/discussion")
					.header("Authorization", "Bearer " + memberToken))
					.andExpect(status().isForbidden());

			assertEquals(board1ThreadsBefore, count("zfgbb.thread where board_id = 1"),
					"a read-only member must not open a CMS discussion thread");
			assertEquals(1, count("zfgbb.content_entity where slug = 'majora-s-mask-3d' and thread_id is null"),
					"the project must remain unlinked after the rejected read-only discussion attempt");
		}
	}

	@Nested
	class Reactions {

		@Test
		void authenticatedUserCanReactToAMessage() throws Exception {
			String memberName = "react_" + suffix;
			register(memberName, "password123");
			String memberToken = login(memberName, "password123").get("accessToken").asString();

			int threadId = postThread(memberToken, "React thread " + suffix, "op body");
			int messageId = openingMessageId(threadId);
			int reactionTypeId = anyReactionTypeId();

			String payload = """
					{"reactableType": "MESSAGE", "reactableId": %d, "reactionTypeId": %d}
					""".formatted(messageId, reactionTypeId);

			mockMvc.perform(post("/reactions")
					.header("Authorization", "Bearer " + memberToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(payload))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.reactableType").value("MESSAGE"))
					.andExpect(jsonPath("$.reactableId").value(messageId))
					.andExpect(jsonPath("$.totalCount").value(1))
					.andExpect(jsonPath("$.userReactionTypeId").value(reactionTypeId));
		}

		@Test
		void readOnlyMemberCannotReact() throws Exception {
			String memberName = "roreact_" + suffix;
			register(memberName, "password123");
			String memberToken = login(memberName, "password123").get("accessToken").asString();
			int memberId = userIdOf(memberName);

			int threadId = postThread(memberToken, "RO react thread " + suffix, "op body");
			int messageId = openingMessageId(threadId);
			int reactionTypeId = anyReactionTypeId();

			String payload = """
					{"reactableType": "MESSAGE", "reactableId": %d, "reactionTypeId": %d}
					""".formatted(messageId, reactionTypeId);

			mockMvc.perform(post("/reactions")
					.header("Authorization", "Bearer " + memberToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(payload))
					.andExpect(status().isOk());

			jdbcTemplate.update(
					"insert into zfgbb.br_user_permission (user_id, user_permission_id) values (?, 9)", memberId);

			mockMvc.perform(post("/reactions")
					.header("Authorization", "Bearer " + memberToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(payload))
					.andExpect(status().isForbidden());
		}

		@Test
		void unknownReactionTypeIsRejectedWithBadRequest() throws Exception {
			String memberName = "badtype_" + suffix;
			register(memberName, "password123");
			String memberToken = login(memberName, "password123").get("accessToken").asString();

			int threadId = postThread(memberToken, "Bad type thread " + suffix, "op body");
			int messageId = openingMessageId(threadId);

			String payload = """
					{"reactableType": "MESSAGE", "reactableId": %d, "reactionTypeId": 999999}
					""".formatted(messageId);

			mockMvc.perform(post("/reactions")
					.header("Authorization", "Bearer " + memberToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(payload))
					.andExpect(status().isBadRequest());
		}

		private int openingMessageId(int threadId) {
			Integer messageId = jdbcTemplate.queryForObject(
					"select message_id from zfgbb.message where thread_id = ? and post_in_thread = 1",
					Integer.class, threadId);
			assertNotNull(messageId);
			return messageId;
		}

		private int anyReactionTypeId() {
			Integer reactionTypeId = jdbcTemplate.queryForObject(
					"select min(reaction_type_id) from zfgbb.reaction_type", Integer.class);
			assertNotNull(reactionTypeId);
			return reactionTypeId;
		}
	}

	@Nested
	class PostingLimits {

		@Test
		void ownerCannotRestoreOrDoubleDeleteAndLockedThreadsBlockOwnerDeletes() throws Exception {
			String ownerName = "authz_" + suffix;
			register(ownerName, "password123");
			String ownerToken = login(ownerName, "password123").get("accessToken").asString();
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();

			int threadId = postThread(ownerToken, "Authz matrix " + suffix, "OP");
			postReply(ownerToken, threadId, "first reply");
			postReply(ownerToken, threadId, "second reply");
			int firstReplyId = messageIdAt(threadId, 2);
			int secondReplyId = messageIdAt(threadId, 3);

			mockMvc.perform(put("/thread/" + threadId + "/lockToggle")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk());
			mockMvc.perform(delete("/message/" + firstReplyId)
					.header("Authorization", "Bearer " + ownerToken))
					.andExpect(status().isForbidden());
			mockMvc.perform(put("/thread/" + threadId + "/lockToggle")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk());

			mockMvc.perform(delete("/message/" + secondReplyId)
					.header("Authorization", "Bearer " + ownerToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("RECYCLED"));
			mockMvc.perform(delete("/message/" + secondReplyId)
					.header("Authorization", "Bearer " + ownerToken))
					.andExpect(status().isForbidden());

			Integer wrapperThreadId = jdbcTemplate.queryForObject(
					"select thread_id from zfgbb.message where message_id = ?", Integer.class, secondReplyId);
			mockMvc.perform(put("/message/" + secondReplyId + "/restore")
					.header("Authorization", "Bearer " + ownerToken))
					.andExpect(status().isForbidden());
			mockMvc.perform(put("/thread/" + wrapperThreadId + "/restore")
					.header("Authorization", "Bearer " + ownerToken))
					.andExpect(status().isForbidden());
		}

		@Test
		void memberCannotModifyAnotherMembersPost() throws Exception {
			String ownerName = "authz_" + suffix;
			String strangerName = "strgr_" + suffix;
			register(ownerName, "password123");
			register(strangerName, "password123");
			String ownerToken = login(ownerName, "password123").get("accessToken").asString();
			String strangerToken = login(strangerName, "password123").get("accessToken").asString();

			int threadId = postThread(ownerToken, "Authz matrix " + suffix, "OP");
			postReply(ownerToken, threadId, "first reply");
			int firstReplyId = messageIdAt(threadId, 2);

			mockMvc.perform(delete("/message/" + firstReplyId)
					.header("Authorization", "Bearer " + strangerToken))
					.andExpect(status().isForbidden());
		}
	}
}
