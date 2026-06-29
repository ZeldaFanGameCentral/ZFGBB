package com.zfgc.zfgbb.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.zfgc.zfgbb.services.core.AccountDeletionService;
import com.zfgc.zfgbb.services.core.MailDispatcher;
import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

import tools.jackson.databind.JsonNode;

class AccountLifecycleTest extends PostgresIntegrationTest {

	@Autowired
	private AccountDeletionService accountDeletionService;

	@BeforeEach
	void clearMail() {
		mailDispatcher.clear();
	}

	@Nested
	class Engine {

		@Test
		void wipeDeletionRemovesAccountContentAndKeepsBoardSummaryCorrect() throws Exception {
			String userName = "wipe_" + suffix;
			register(userName, "password123");
			String accessToken = login(userName, "password123").get("accessToken").asString();
			String adminAccessToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
			int survivingThreadId = postThread(adminAccessToken, "Survivor " + suffix);
			postReply(accessToken, survivingThreadId);
			postReply(adminAccessToken, survivingThreadId);
			int threadId = postThread(accessToken, "Wipe me " + suffix);
			postReply(accessToken, threadId);
			Integer userId = userIdOf(userName);

			assertEquals(1, count("zfgbb.message where thread_id = " + survivingThreadId
					+ " and owner_id = " + userId + " and post_in_thread = 2"),
					"the subject's post must sit in the middle of the surviving thread before deletion");
			assertEquals(List.of(1, 2, 3), postPositionsIn(survivingThreadId));

			Integer requestId = insertConfirmedDeletionRequest(userId, "WIPE");
			accountDeletionService.executeConfirmedDeletion(requestId);

			assertEquals(List.of(1, 2), postPositionsIn(survivingThreadId));
			assertRequestCompleted(requestId);

			accountDeletionService.executeConfirmedDeletion(requestId);
			assertRequestCompleted(requestId);
		}

		@Test
		void doubleOrphanScrubsIdentityKeepsContentAndSurvivesRerun() throws Exception {
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
			Integer counterpartyId = userIdOf(ADMIN_USER);
			String[] userNames = { "orphA_" + suffix, "orphB_" + suffix };
			List<Integer> orphanedUserIds = new ArrayList<>();
			String lastAccessToken = null;
			String lastOriginalRefreshToken = null;
			String lastRotatedRefreshToken = null;
			Integer lastUserId = null;
			int clientIpOctet = 1;

			for (String userName : userNames) {
				register(userName, "password123");
				JsonNode loginJson = login(userName, "password123");
				String accessToken = loginJson.get("accessToken").asString();
				String originalRefreshToken = loginJson.get("refreshToken").asString();
				String rotatedRefreshToken = refreshExpectingOk(originalRefreshToken);
				Integer userId = userIdOf(userName);
				int threadId = postThread(accessToken, "Keep my posts " + userName);
				postReply(accessToken, threadId);

				Integer ownedMessageId = jdbcTemplate.queryForObject(
						"select min(message_id) from zfgbb.message where owner_id = ?", Integer.class, userId);
				jdbcTemplate.update("update zfgbb.message set migration_hash = 'legacyhash' where owner_id = ?",
						userId);
				jdbcTemplate.update(
						"insert into zfgbb.message_history (message_id, message_text, current_flag, migration_hash,"
								+ " created_ts, updated_ts)"
								+ " values (?, 'legacy body', false, 'legacyhash', current_timestamp, current_timestamp)",
						ownedMessageId);
				jdbcTemplate.update("update zfgbb.thread set migration_hash = 'legacyhash' where thread_id = ?",
						threadId);
				jdbcTemplate.update(
						"insert into zfgbb.user_warning (user_id, body, points, migration_hash) values (?, 'warned', 0, 'legacyhash')",
						userId);
				jdbcTemplate.update(
						"insert into zfgbb.moderation_log (action, actor_user_id, migration_hash) values (?, ?, 'legacyhash')",
						"LEGACY_" + userName, userId);
				jdbcTemplate.update(
						"insert into zfgbb.content_entity (entity_type, title, slug, created_user_id, author_name, migration_hash)"
								+ " values ('PROJECT', ?, ?, ?, ?, 'legacyhash')",
						"Project " + userName, "proj-" + userName, userId, userName);
				Integer conversationId = jdbcTemplate.queryForObject(
						"insert into zfgbb.personal_message_conversation (subject, started_ts, migration_hash)"
								+ " values (?, current_timestamp, 'legacyhash') returning personal_message_conversation_id",
						Integer.class, "Hello " + userName);
				Integer receivedMessageId = jdbcTemplate.queryForObject(
						"insert into zfgbb.personal_message (personal_message_conversation_id, sender_user_id, sender_name,"
								+ " body, sent_ts, migration_hash)"
								+ " values (?, ?, ?, 'counterparty body', current_timestamp, 'legacyhash')"
								+ " returning personal_message_id",
						Integer.class, conversationId, counterpartyId, ADMIN_USER);
				jdbcTemplate.update(
						"insert into zfgbb.personal_message_recipient (personal_message_id, recipient_user_id, migration_hash)"
								+ " values (?, ?, 'legacyhash')",
						receivedMessageId, userId);

				mockMvc.perform(post("/users/account/delete")
						.header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content(deletionBody("ORPHAN", "password123", userName)))
						.andExpect(status().isAccepted());
				confirmAccountDeletion(lastConfirmationToken(), "10.99.2." + clientIpOctet++)
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.status").value("COMPLETED"));

				Map<String, Object> userRow = jdbcTemplate.queryForMap(
						"select display_name, user_name, sso_key, active_flag, password_hash, migration_hash,"
								+ " tokens_valid_after_ts from zfgbb.\"user\" where user_id = ?",
						userId);
				assertEquals("[deleted]", userRow.get("display_name"));
				assertEquals("[deleted]", userRow.get("user_name"));
				assertEquals("__deleted__" + userId, userRow.get("sso_key"));
				assertEquals(false, userRow.get("active_flag"));
				assertNull(userRow.get("password_hash"));
				assertNull(userRow.get("migration_hash"));
				assertNotNull(userRow.get("tokens_valid_after_ts"));

				assertEquals(0, count("zfgbb.message where owner_id = " + userId));
				assertEquals(2, count("zfgbb.message where thread_id = " + threadId + " and owner_id = 0"));
				assertEquals(0, count("zfgbb.message where thread_id = " + threadId
						+ " and owner_id = 0 and migration_hash is not null"));
				assertEquals(0, count("zfgbb.message_history where message_id = " + ownedMessageId
						+ " and migration_hash is not null"));
				assertEquals(1, count("zfgbb.thread where thread_id = " + threadId
						+ " and created_user_id = 0 and migration_hash is null"));
				assertEquals(3, count("zfgbb.message_history mh where mh.ip_address_id is null and mh.message_id in"
						+ " (select message_id from zfgbb.message where thread_id = " + threadId + ")"));
				assertEquals(1, count("zfgbb.user_warning where user_id = " + userId + " and migration_hash is null"));
				assertEquals(0,
						count("zfgbb.user_warning where user_id = " + userId + " and migration_hash is not null"));
				assertEquals(1, count("zfgbb.moderation_log where action = 'LEGACY_" + userName
						+ "' and actor_user_id is null and migration_hash is null"));
				assertEquals(1, count("zfgbb.content_entity where slug = 'proj-" + userName
						+ "' and created_user_id is null and author_name = '[deleted]' and migration_hash is null"));
				assertEquals(1, count("zfgbb.personal_message where personal_message_id = " + receivedMessageId
						+ " and sender_user_id = " + counterpartyId
						+ " and body = 'counterparty body' and migration_hash is null"));
				assertEquals(1, count("zfgbb.personal_message_conversation where personal_message_conversation_id = "
						+ conversationId + " and subject = 'Hello " + userName + "' and migration_hash is null"));
				assertEquals(0, count("zfgbb.personal_message_recipient where recipient_user_id = " + userId));
				assertEquals(0, count("zfgbb.user_bio_info where user_id = " + userId));
				assertEquals(0, count("zfgbb.user_contact_info where user_id = " + userId));
				assertEquals(0,
						count("zfgbb.email_address where email_address = '" + userName
								+ "@fake-email.fake.tld.thing'"));
				assertEquals(0,
						count("zfgbb.user_refresh_token where user_id = " + userId + " and revoked_flag = false"));
				assertEquals(0, count("zfgbb.br_user_permission where user_id = " + userId));
				assertEquals(0, count("zfgbb.user_permission_group_assoc where user_id = " + userId));

				Map<String, Object> boardSummary = jdbcTemplate.queryForMap(
						"select latest_message_owner_id, latest_message_user_name from zfgbb.board_summary where board_id = 1");
				assertEquals(0, boardSummary.get("latest_message_owner_id"),
						"the retained latest post must be attributed to the sentinel");
				assertEquals("[deleted]", boardSummary.get("latest_message_user_name"));
				JsonNode boardAfter = boardNodeOf(fetchForum(adminToken), 1);
				assertEquals("[deleted]", boardAfter.path("latestMessageUserName").asText(),
						"the forum cache must be evicted so retained content renders as [deleted]");
				assertAuditStamped(userId, "ANONYMIZE");

				orphanedUserIds.add(userId);
				lastAccessToken = accessToken;
				lastOriginalRefreshToken = originalRefreshToken;
				lastRotatedRefreshToken = rotatedRefreshToken;
				lastUserId = userId;
			}

			assertEquals(1, count("pg_indexes where schemaname = 'zfgbb' and indexname = 'ux_user_sso_key'"));
			assertEquals(3,
					count("zfgbb.\"user\" where sso_key in ('__deleted__', '__deleted__" + orphanedUserIds.get(0)
							+ "', '__deleted__" + orphanedUserIds.get(1) + "')"),
					"the sentinel and both orphaned accounts must coexist under the unique sso_key index");

			Integer completedRequestId = jdbcTemplate.queryForObject(
					"select account_deletion_request_id from zfgbb.account_deletion_request where user_id = ? and status = 'COMPLETED'",
					Integer.class, lastUserId);
			accountDeletionService.executeConfirmedDeletion(completedRequestId);
			assertEquals(1,
					count("zfgbb.account_deletion_request where account_deletion_request_id = " + completedRequestId
							+ " and status = 'COMPLETED'"));

			jdbcTemplate.update(
					"update zfgbb.account_deletion_request set status = 'CONFIRMED', purge_cursor = null where account_deletion_request_id = ?",
					completedRequestId);
			accountDeletionService.executeConfirmedDeletion(completedRequestId);
			assertEquals(1,
					count("zfgbb.account_deletion_request where account_deletion_request_id = " + completedRequestId
							+ " and status = 'COMPLETED'"));
			assertEquals("__deleted__" + lastUserId, jdbcTemplate.queryForObject(
					"select sso_key from zfgbb.\"user\" where user_id = ?", String.class, lastUserId),
					"a full re-run against already-deleted state must be idempotent under the sso_key unique index");

			assertPostDeletionTeardown(userNames[1], "password123", lastAccessToken, lastOriginalRefreshToken,
					lastRotatedRefreshToken);
		}

		private Integer insertConfirmedDeletionRequest(Integer userId, String mode) {
			return jdbcTemplate.queryForObject(
					"""
							insert into zfgbb.account_deletion_request (user_id, mode, status, token_sha256, requested_ts, expires_ts)
							values (?, ?, 'CONFIRMED', ?, current_timestamp, current_timestamp + interval '24 hours')
							returning account_deletion_request_id
							""",
					Integer.class, userId, mode, UUID.randomUUID().toString());
		}

		private void assertRequestCompleted(Integer requestId) {
			Map<String, Object> requestRow = jdbcTemplate.queryForMap(
					"select status, purge_cursor, recorded_blob_paths from zfgbb.account_deletion_request"
							+ " where account_deletion_request_id = ?",
					requestId);
			assertEquals("COMPLETED", requestRow.get("status"));
			assertEquals("COMPLETED", requestRow.get("purge_cursor"));
			assertNull(requestRow.get("recorded_blob_paths"));
		}

		private void assertAuditStamped(Integer userId, String mode) {
			assertEquals(1, count("zfgbb.account_deletion_audit where subject_user_id_snapshot = " + userId
					+ " and mode = '" + mode + "' and initiated_by = 'SELF'"
					+ " and confirmed_ts is not null and executed_ts is not null"));
		}
	}

	@Nested
	class EndpointFlow {

		@Test
		void requestConfirmFlowNeutralizesAccountAndCutsOffLiveTokens() throws Exception {
			String userName = "delend_" + suffix;
			register(userName, "password123");
			String accessToken = login(userName, "password123").get("accessToken").asString();
			Integer userId = userIdOf(userName);

			mockMvc.perform(post("/users/account/delete/preview")
					.header("Authorization", "Bearer " + accessToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.adminReplacementRequired").value(false))
					.andExpect(jsonPath("$.messageCount").isNumber());

			mockMvc.perform(post("/users/account/delete")
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(deletionBody("ORPHAN", "password123", userName.toUpperCase())))
					.andExpect(status().isAccepted())
					.andExpect(jsonPath("$.status").value("PENDING"));
			assertEquals(1,
					count("zfgbb.account_deletion_request where user_id = " + userId + " and status = 'PENDING'"));
			assertEquals(1, count("zfgbb.account_deletion_audit where subject_user_id_snapshot = " + userId
					+ " and requested_ts is not null and confirmed_ts is null"));
			String confirmationToken = lastConfirmationToken();

			int sentBefore = mailDispatcher.sentMessages().size();
			mockMvc.perform(post("/users/account/delete")
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(deletionBody("ORPHAN", "password123", userName)))
					.andExpect(status().isAccepted())
					.andExpect(jsonPath("$.status").value("PENDING"));
			assertEquals(sentBefore, mailDispatcher.sentMessages().size(),
					"repeating a request with the same mode must not dispatch another email");

			mockMvc.perform(post("/users/account/delete/confirm")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"token\": \"bogus-token-value\"}"))
					.andExpect(status().isBadRequest());

			mockMvc.perform(post("/users/account/delete/confirm")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"token\": \"" + confirmationToken + "\"}"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.status").value("COMPLETED"));

			assertEquals(1, count("zfgbb.\"user\" where user_id = " + userId
					+ " and active_flag = false and display_name = '[deleted]' and tokens_valid_after_ts is not null"),
					"the subject must be neutralized after confirmation");

			mockMvc.perform(post("/users/account/delete/preview")
					.header("Authorization", "Bearer " + accessToken))
					.andExpect(status().isUnauthorized());

			mockMvc.perform(post("/users/account/delete/confirm")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"token\": \"" + confirmationToken + "\"}"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.status").value("COMPLETED"));

			List<MailDispatcher.OutboundMail> sent = mailDispatcher.sentMessages();
			assertTrue(sent.stream().anyMatch(mail -> mail.subject().contains("has been deleted")),
					"a courtesy completion notice must be dispatched");
			assertEquals(1, count("zfgbb.account_deletion_audit where subject_user_id_snapshot = " + userId
					+ " and confirmed_ts is not null and executed_ts is not null"));
		}

		@Test
		void cancelKillsThePendingToken() throws Exception {
			String userName = "cancl_" + suffix;
			register(userName, "password123");
			String accessToken = login(userName, "password123").get("accessToken").asString();

			mockMvc.perform(post("/users/account/delete")
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(deletionBody("WIPE", "password123", userName)))
					.andExpect(status().isAccepted());
			String confirmationToken = lastConfirmationToken();

			mockMvc.perform(post("/users/account/delete/cancel")
					.header("Authorization", "Bearer " + accessToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.status").value("CANCELLED"));

			mockMvc.perform(post("/users/account/delete/confirm")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"token\": \"" + confirmationToken + "\"}"))
					.andExpect(status().isBadRequest());

			mockMvc.perform(post("/users/account/delete/preview")
					.header("Authorization", "Bearer " + accessToken))
					.andExpect(status().isOk());
		}

		@Test
		void stateEndpointExposesPendingRequestAndExpiredRequestBlocksResend() throws Exception {
			String userName = "state_" + suffix;
			register(userName, "password123");
			String accessToken = login(userName, "password123").get("accessToken").asString();
			Integer userId = userIdOf(userName);

			mockMvc.perform(get("/users/account/delete")
					.header("Authorization", "Bearer " + accessToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.status").value("NONE"));

			mockMvc.perform(post("/users/account/delete")
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(deletionBody("ORPHAN", "password123", userName)))
					.andExpect(status().isAccepted());

			mockMvc.perform(get("/users/account/delete")
					.header("Authorization", "Bearer " + accessToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.status").value("PENDING"))
					.andExpect(jsonPath("$.mode").value("ANONYMIZE"));

			jdbcTemplate.update("update zfgbb.account_deletion_request"
					+ " set expires_ts = current_timestamp - interval '1 hour', last_sent_ts = null"
					+ " where user_id = ? and status = 'PENDING'", userId);

			mockMvc.perform(post("/users/account/delete/resend")
					.header("Authorization", "Bearer " + accessToken))
					.andExpect(status().isNotFound());
		}

		@Test
		void emailConfirmationGateControlsExecution() throws Exception {
			String userName = "gated_" + suffix;
			register(userName, "password123");
			String accessToken = login(userName, "password123").get("accessToken").asString();
			Integer userId = userIdOf(userName);
			postThread(accessToken, "Gate probe " + suffix);

			mockMvc.perform(post("/users/account/delete")
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(deletionBody("ORPHAN", "password123", userName)))
					.andExpect(status().isAccepted());
			String firstToken = lastConfirmationToken();
			MailDispatcher.OutboundMail confirmationMail = mailDispatcher.sentMessages().get(0);
			assertEquals(userName + "@fake-email.fake.tld.thing", confirmationMail.toEmailAddress());

			mockMvc.perform(post("/users/account/delete/resend")
					.header("Authorization", "Bearer " + accessToken))
					.andExpect(status().isTooManyRequests());

			jdbcTemplate.update(
					"update zfgbb.account_deletion_request set expires_ts = current_timestamp - interval '1 hour' where user_id = ? and status = 'PENDING'",
					userId);
			confirmAccountDeletion(firstToken, "10.99.3.1").andExpect(status().isBadRequest());
			assertEquals(1,
					count("zfgbb.account_deletion_request where user_id = " + userId + " and status = 'PENDING'"),
					"an expired confirmation must leave the request inert");
			assertEquals(1, count("zfgbb.\"user\" where user_id = " + userId + " and active_flag = true"));

			jdbcTemplate.update(
					"update zfgbb.account_deletion_request set expires_ts = current_timestamp + interval '24 hours',"
							+ " last_sent_ts = current_timestamp - interval '6 minutes' where user_id = ? and status = 'PENDING'",
					userId);
			mockMvc.perform(post("/users/account/delete/resend")
					.header("Authorization", "Bearer " + accessToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.resendCount").value(1));
			String rotatedToken = lastConfirmationToken();
			assertNotEquals(firstToken, rotatedToken);
			confirmAccountDeletion(firstToken, "10.99.3.2").andExpect(status().isBadRequest());

			jdbcTemplate.update(
					"update zfgbb.account_deletion_request set resend_count = 3, last_sent_ts = current_timestamp - interval '6 minutes'"
							+ " where user_id = ? and status = 'PENDING'",
					userId);
			mockMvc.perform(post("/users/account/delete/resend")
					.header("Authorization", "Bearer " + accessToken))
					.andExpect(status().isTooManyRequests());

			mockMvc.perform(post("/users/account/delete/cancel")
					.header("Authorization", "Bearer " + accessToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.status").value("CANCELLED"));
			confirmAccountDeletion(rotatedToken, "10.99.3.3").andExpect(status().isBadRequest());
			assertEquals(1,
					count("zfgbb.\"user\" where user_id = " + userId
							+ " and active_flag = true and password_hash is not null"),
					"a cancelled request must leave the account untouched");

			mockMvc.perform(post("/users/account/delete")
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(deletionBody("ORPHAN", "password123", userName)))
					.andExpect(status().isAccepted());
			String finalToken = lastConfirmationToken();
			confirmAccountDeletion(finalToken, "10.99.3.4")
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.status").value("COMPLETED"));
			assertEquals(1, count("zfgbb.\"user\" where user_id = " + userId
					+ " and active_flag = false and display_name = '[deleted]'"));
		}

		@Test
		void confirmRetryResumesStalledDeletions() throws Exception {
			String userName = "stall_" + suffix;
			register(userName, "password123");
			String accessToken = login(userName, "password123").get("accessToken").asString();
			Integer userId = userIdOf(userName);
			postThread(accessToken, "Stalled deletion " + suffix);

			mockMvc.perform(post("/users/account/delete")
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(deletionBody("ORPHAN", "password123", userName)))
					.andExpect(status().isAccepted());
			String confirmationToken = lastConfirmationToken();
			jdbcTemplate.update(
					"update zfgbb.account_deletion_request set status = 'CONFIRMED', confirmed_ts = current_timestamp"
							+ " where user_id = ? and status = 'PENDING'",
					userId);

			confirmAccountDeletion(confirmationToken, "10.99.5.1")
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.status").value("COMPLETED"));
			assertEquals(1, count("zfgbb.\"user\" where user_id = " + userId
					+ " and active_flag = false and display_name = '[deleted]' and password_hash is null"),
					"a confirm retry against a stalled CONFIRMED request must actually execute the deletion");
			assertEquals(1,
					count("zfgbb.account_deletion_request where user_id = " + userId + " and status = 'COMPLETED'"));

			jdbcTemplate.update(
					"update zfgbb.account_deletion_request set status = 'EXECUTING', purge_cursor = null where user_id = ?",
					userId);
			confirmAccountDeletion(confirmationToken, "10.99.5.2")
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.status").value("COMPLETED"));
			assertEquals(1,
					count("zfgbb.account_deletion_request where user_id = " + userId + " and status = 'COMPLETED'"),
					"a confirm retry against a stalled EXECUTING request must re-drive the purge to completion");
		}

		@Test
		void deletionEndpointsAreSelfOnlyAndPhraseChecksPrecedePassword() throws Exception {
			String targetName = "target_" + suffix;
			String intruderName = "intrud_" + suffix;
			register(targetName, "targetpass123");
			register(intruderName, "intruderpass123");
			String targetToken = login(targetName, "targetpass123").get("accessToken").asString();
			String intruderToken = login(intruderName, "intruderpass123").get("accessToken").asString();
			Integer targetId = userIdOf(targetName);
			Integer intruderId = userIdOf(intruderName);

			mockMvc.perform(post("/users/account/delete")
					.header("Authorization", "Bearer " + targetToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(deletionBody("WIPE", "targetpass123", targetName)))
					.andExpect(status().isAccepted());

			MvcResult phraseFailure = mockMvc.perform(post("/users/account/delete")
					.header("Authorization", "Bearer " + intruderToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(deletionBody("WIPE", "targetpass123", targetName)))
					.andExpect(status().isBadRequest())
					.andReturn();
			assertEquals(0, count("zfgbb.account_deletion_request where user_id = " + intruderId),
					"a request naming another user's phrase must not create a request for anyone");
			assertEquals(1, count("zfgbb.\"user\" where user_id = " + intruderId + " and failed_login_count = 0"),
					"a mismatched confirmation phrase must be rejected before the password is evaluated");

			mockMvc.perform(post("/users/account/delete/cancel")
					.header("Authorization", "Bearer " + intruderToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.status").value("NONE"));
			mockMvc.perform(post("/users/account/delete/resend")
					.header("Authorization", "Bearer " + intruderToken))
					.andExpect(status().isNotFound());
			assertEquals(1,
					count("zfgbb.account_deletion_request where user_id = " + targetId + " and status = 'PENDING'"),
					"another user's session must not be able to cancel or rotate the subject's request");

			mockMvc.perform(post("/system/users/delete")
					.header("Authorization", "Bearer " + intruderToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"userId\": " + targetId + ", \"mode\": \"PURGE\"}"))
					.andExpect(status().isForbidden());
			assertEquals(1, count("zfgbb.\"user\" where user_id = " + targetId + " and active_flag = true"));

			MvcResult passwordFailure = mockMvc.perform(post("/users/account/delete")
					.header("Authorization", "Bearer " + intruderToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(deletionBody("WIPE", "wrongpassword", intruderName)))
					.andExpect(status().isBadRequest())
					.andReturn();
			assertEquals(1, count("zfgbb.\"user\" where user_id = " + intruderId + " and failed_login_count = 1"),
					"a wrong password behind a correct phrase must ride the login lockout counter");
			assertEquals(phraseFailure.getResponse().getContentAsString(),
					passwordFailure.getResponse().getContentAsString(),
					"phrase and password failures must be indistinguishable");

			mockMvc.perform(post("/users/account/delete/cancel")
					.header("Authorization", "Bearer " + targetToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.status").value("CANCELLED"));
		}

		@Test
		void lastSiteAdminIsBlockedAtRequestAndAtConfirm() throws Exception {
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
			Integer adminId = userIdOf(ADMIN_USER);
			Integer siteAdminPermissionId = jdbcTemplate.queryForObject(
					"select permission_id from zfgbb.permission where permission_code = 'ZFGC_SITE_ADMIN'",
					Integer.class);
			jdbcTemplate.update("delete from zfgbb.br_user_permission where user_permission_id = ? and user_id <> ?",
					siteAdminPermissionId, adminId);
			jdbcTemplate.update("""
					delete from zfgbb.user_permission_group_assoc
					where user_id <> ?
					and permission_group_id in (
					  select permission_group_id from zfgbb.permission_group_assoc where permission_id = ?)
					""", adminId, siteAdminPermissionId);

			mockMvc.perform(post("/users/account/delete/preview")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.adminReplacementRequired").value(true));
			mockMvc.perform(post("/users/account/delete")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(deletionBody("ORPHAN", ADMIN_PASSWORD, ADMIN_USER)))
					.andExpect(status().isBadRequest());
			assertEquals(0, count("zfgbb.account_deletion_request where user_id = " + adminId),
					"the sole site admin must be blocked at request time");

			String secondAdminName = "adm2_" + suffix;
			register(secondAdminName, "password123");
			Integer secondAdminId = userIdOf(secondAdminName);
			jdbcTemplate.update("insert into zfgbb.br_user_permission (user_permission_id, user_id) values (?, ?)",
					siteAdminPermissionId, secondAdminId);
			String secondAdminToken = login(secondAdminName, "password123").get("accessToken").asString();

			mockMvc.perform(post("/users/account/delete/preview")
					.header("Authorization", "Bearer " + secondAdminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.adminReplacementRequired").value(false));
			mockMvc.perform(post("/users/account/delete")
					.header("Authorization", "Bearer " + secondAdminToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(deletionBody("ORPHAN", "password123", secondAdminName)))
					.andExpect(status().isAccepted());
			String confirmationToken = lastConfirmationToken();

			jdbcTemplate.update("delete from zfgbb.br_user_permission where user_permission_id = ? and user_id = ?",
					siteAdminPermissionId, adminId);
			try {
				confirmAccountDeletion(confirmationToken, "10.99.4.1").andExpect(status().isBadRequest());
				assertEquals(1, count("zfgbb.account_deletion_request where user_id = " + secondAdminId
						+ " and status = 'CANCELLED'"),
						"a confirm that trips the last-admin re-check must cancel the request");
				assertEquals(1, count("zfgbb.\"user\" where user_id = " + secondAdminId
						+ " and active_flag = true and password_hash is not null and user_name = '" + secondAdminName
						+ "'"),
						"the roster re-check at confirm time must leave the account untouched");
			} finally {
				jdbcTemplate.update("insert into zfgbb.br_user_permission (user_permission_id, user_id) values (?, ?)",
						siteAdminPermissionId, adminId);
			}
		}
	}

	@Nested
	class FkCensus {

		private static final Set<String> RESTRICT_REFERRERS_COVERED_BY_DELETION_PLAN = Set.of(
				"content_resource_uploaded_user_id_fkey",
				"fk_message_user_id",
				"poll_created_user_id_fkey",
				"fk_thread_created_user_id",
				"user_bio_info_avatar_id_fkey",
				"avatar_content_resource_id_fkey",
				"content_entity_preview_content_resource_id_fkey",
				"file_attachments_content_resource_id_fkey",
				"permission_group_star_image_fkey",
				"project_download_content_resource_id_fkey",
				"project_screenshot_content_resource_id_fkey",
				"resource_download_content_resource_id_fkey",
				"wiki_page_content_resource_id_fkey",
				"user_contact_info_email_address_id_fkey",
				"file_attachments_message_id_fkey",
				"fk_message_history_message_id",
				"migrator_attachment_ref_rewrites_message_history_id_fkey",
				"fk_message_thread_id",
				"poll_thread_id_fkey",
				"content_entity_wiki_page_id_fkey",
				"content_template_wiki_page_id_fkey",
				"wiki_page_revision_wiki_page_id_fkey");

		@Test
		void everyRestrictReferrerOfCensusTargetsHasADeletionPlanStep() throws IOException {
			Set<String> liveRestrictReferrers = new TreeSet<>(jdbcTemplate.queryForList(loadCensusSql(), String.class));
			assertEquals(new TreeSet<>(RESTRICT_REFERRERS_COVERED_BY_DELETION_PLAN), liveRestrictReferrers);
		}

		@Test
		void sentinelUserIsReservedAtFixedIdWithInertCredentials() {
			var sentinel = jdbcTemplate.queryForMap(
					"select sso_key, user_name, display_name, active_flag, password_hash from zfgbb.\"user\" where user_id = 0");
			assertEquals("__deleted__", sentinel.get("sso_key"));
			assertEquals("__deleted__", sentinel.get("user_name"));
			assertEquals("[deleted]", sentinel.get("display_name"));
			assertEquals(false, sentinel.get("active_flag"));
			assertEquals(null, sentinel.get("password_hash"));
		}

		private String loadCensusSql() throws IOException {
			try (InputStream censusSqlStream = getClass().getResourceAsStream("/db/census/restrict_fk_census.sql")) {
				return new String(censusSqlStream.readAllBytes(), StandardCharsets.UTF_8);
			}
		}
	}
}
