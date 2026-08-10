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

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.zfgc.zfgbb.dbo.*;
import com.zfgc.zfgbb.mappers.*;
import com.zfgc.zfgbb.model.users.DeletionMode;
import com.zfgc.zfgbb.services.users.UserService;
import com.zfgc.zfgbb.services.mail.MailDispatcher;
import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;
import com.zfgc.zfgbb.testsupport.mappers.TestSystemInfoMapper;

import tools.jackson.databind.JsonNode;

class AccountLifecycleTest extends PostgresIntegrationTest {

	@Autowired
	private UserService userService;

	@Autowired private AccountDeletionAuditDboMapper accountDeletionAuditDboMapper;
	@Autowired private org.springframework.security.oauth2.jwt.JwtEncoder jwtEncoder;
	@Autowired private BoardSummaryViewDboMapper boardSummaryViewDboMapper;
	@Autowired private BrUserPermissionDboMapper brUserPermissionDboMapper;
	@Autowired private ContentEntityDboMapper contentEntityDboMapper;
	@Autowired private EmailAddressDboMapper emailAddressDboMapper;
	@Autowired private MessageDboMapper messageDboMapper;
	@Autowired private MessageHistoryDboMapper messageHistoryDboMapper;
	@Autowired private ModerationLogDboMapper moderationLogDboMapper;
	@Autowired private PermissionDboMapper permissionDboMapper;
	@Autowired private PermissionGroupAssocDboMapper permissionGroupAssocDboMapper;
	@Autowired private PersonalMessageConversationDboMapper personalMessageConversationDboMapper;
	@Autowired private PersonalMessageDboMapper personalMessageDboMapper;
	@Autowired private PersonalMessageRecipientDboMapper personalMessageRecipientDboMapper;
	@Autowired private ThreadDboMapper threadDboMapper;
	@Autowired private UserBioInfoDboMapper userBioInfoDboMapper;
	@Autowired private UserContactInfoDboMapper userContactInfoDboMapper;
	@Autowired private UserDboMapper userDboMapper;
	@Autowired private UserPermissionGroupAssocDboMapper userPermissionGroupAssocDboMapper;
	@Autowired private UserRefreshTokenDboMapper userRefreshTokenDboMapper;
	@Autowired private UserWarningDboMapper userWarningDboMapper;
	@Autowired private TestSystemInfoMapper testSystemInfoMapper;

	@BeforeEach
	void clearMail() {
		mailDispatcher.clear();
	}

	private long countUsers(Consumer<UserDboExample.Criteria> criteria) {
		UserDboExample example = new UserDboExample();
		criteria.accept(example.createCriteria());
		return userDboMapper.countByExample(example);
	}

	private long countMessages(Consumer<MessageDboExample.Criteria> criteria) {
		MessageDboExample example = new MessageDboExample();
		criteria.accept(example.createCriteria());
		return messageDboMapper.countByExample(example);
	}

	private long countWarnings(Consumer<UserWarningDboExample.Criteria> criteria) {
		UserWarningDboExample example = new UserWarningDboExample();
		criteria.accept(example.createCriteria());
		return userWarningDboMapper.countByExample(example);
	}

	private long countDeletionAudits(Consumer<AccountDeletionAuditDboExample.Criteria> criteria) {
		AccountDeletionAuditDboExample example = new AccountDeletionAuditDboExample();
		criteria.accept(example.createCriteria());
		return accountDeletionAuditDboMapper.countByExample(example);
	}

	private Integer permissionIdOf(String permissionCode) {
		PermissionDboExample example = new PermissionDboExample();
		example.createCriteria().andPermissionCodeEqualTo(permissionCode);
		return permissionDboMapper.selectByExample(example).get(0).getPermissionId();
	}

	private void grantPermission(Integer permissionId, Integer userId) {
		BrUserPermissionDbo grant = new BrUserPermissionDbo();
		grant.setUserPermissionId(permissionId);
		grant.setUserId(userId);
		brUserPermissionDboMapper.insertSelective(grant);
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

			assertEquals(1, countMessages(criteria -> criteria.andThreadIdEqualTo(survivingThreadId)
					.andOwnerIdEqualTo(userId).andPostInThreadEqualTo(2)),
					"the subject's post must sit in the middle of the surviving thread before deletion");
			assertEquals(List.of(1, 2, 3), postPositionsIn(survivingThreadId));

			userService.eraseUserRecords(userId, DeletionMode.PURGE);

			assertEquals(List.of(1, 2), postPositionsIn(survivingThreadId));

			userService.eraseUserRecords(userId, DeletionMode.PURGE);
			assertEquals(List.of(1, 2), postPositionsIn(survivingThreadId));
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

				Integer ownedMessageId = lowestMessageIdOwnedBy(userId);
				MessageDbo legacyStampedMessage = new MessageDbo();
				legacyStampedMessage.setMigrationHash("legacyhash");
				MessageDboExample messagesOwnedByUser = new MessageDboExample();
				messagesOwnedByUser.createCriteria().andOwnerIdEqualTo(userId);
				messageDboMapper.updateByExampleSelective(legacyStampedMessage, messagesOwnedByUser);

				MessageHistoryDbo legacyRevision = new MessageHistoryDbo();
				legacyRevision.setMessageId(ownedMessageId);
				legacyRevision.setMessageText("legacy body");
				legacyRevision.setCurrentFlag(false);
				legacyRevision.setMigrationHash("legacyhash");
				messageHistoryDboMapper.insertSelective(legacyRevision);

				ThreadDbo legacyStampedThread = new ThreadDbo();
				legacyStampedThread.setThreadId(threadId);
				legacyStampedThread.setMigrationHash("legacyhash");
				threadDboMapper.updateByPrimaryKeySelective(legacyStampedThread);

				UserWarningDbo legacyWarning = new UserWarningDbo();
				legacyWarning.setUserId(userId);
				legacyWarning.setBody("warned");
				legacyWarning.setPoints(0);
				legacyWarning.setMigrationHash("legacyhash");
				userWarningDboMapper.insertSelective(legacyWarning);

				ModerationLogDbo legacyModerationEntry = new ModerationLogDbo();
				legacyModerationEntry.setAction("LEGACY_" + userName);
				legacyModerationEntry.setActorUserId(userId);
				legacyModerationEntry.setMigrationHash("legacyhash");
				moderationLogDboMapper.insertSelective(legacyModerationEntry);

				ContentEntityDbo legacyProject = new ContentEntityDbo();
				legacyProject.setEntityType("PROJECT");
				legacyProject.setTitle("Project " + userName);
				legacyProject.setSlug("proj-" + userName);
				legacyProject.setCreatedUserId(userId);
				legacyProject.setAuthorName(userName);
				legacyProject.setMigrationHash("legacyhash");
				contentEntityDboMapper.insertSelective(legacyProject);

				PersonalMessageConversationDbo legacyConversation = new PersonalMessageConversationDbo();
				legacyConversation.setSubject("Hello " + userName);
				legacyConversation.setStartedTs(OffsetDateTime.now());
				legacyConversation.setMigrationHash("legacyhash");
				personalMessageConversationDboMapper.insertSelective(legacyConversation);
				Integer conversationId = legacyConversation.getPersonalMessageConversationId();

				PersonalMessageDbo legacyReceivedMessage = new PersonalMessageDbo();
				legacyReceivedMessage.setPersonalMessageConversationId(conversationId);
				legacyReceivedMessage.setSenderUserId(counterpartyId);
				legacyReceivedMessage.setSenderName(ADMIN_USER);
				legacyReceivedMessage.setBody("counterparty body");
				legacyReceivedMessage.setSentTs(OffsetDateTime.now());
				legacyReceivedMessage.setMigrationHash("legacyhash");
				personalMessageDboMapper.insertSelective(legacyReceivedMessage);
				Integer receivedMessageId = legacyReceivedMessage.getPersonalMessageId();

				PersonalMessageRecipientDbo legacyRecipient = new PersonalMessageRecipientDbo();
				legacyRecipient.setPersonalMessageId(receivedMessageId);
				legacyRecipient.setRecipientUserId(userId);
				legacyRecipient.setMigrationHash("legacyhash");
				personalMessageRecipientDboMapper.insertSelective(legacyRecipient);

				mockMvc.perform(post("/users/account/delete")
						.header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content(deletionBody("ORPHAN", "password123", userName)))
						.andExpect(status().isAccepted());
				confirmAccountDeletion(lastConfirmationToken(), "10.99.2." + clientIpOctet++)
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.status").value("COMPLETED"));

				UserDbo scrubbedUser = userDboMapper.selectByPrimaryKey(userId);
				assertEquals("[deleted]", scrubbedUser.getDisplayName());
				assertEquals("[deleted]", scrubbedUser.getUserName());
				assertEquals("__deleted__" + userId, scrubbedUser.getSsoKey());
				assertEquals(false, scrubbedUser.getActiveFlag());
				assertNull(scrubbedUser.getPasswordHash());
				assertNull(scrubbedUser.getMigrationHash());
				assertNotNull(scrubbedUser.getTokensValidAfterTs());

				assertEquals(0, countMessages(criteria -> criteria.andOwnerIdEqualTo(userId)));
				assertEquals(2, countMessages(criteria -> criteria.andThreadIdEqualTo(threadId).andOwnerIdEqualTo(0)));
				assertEquals(0, countMessages(criteria -> criteria.andThreadIdEqualTo(threadId).andOwnerIdEqualTo(0)
						.andMigrationHashIsNotNull()));

				MessageHistoryDboExample legacyStampedHistory = new MessageHistoryDboExample();
				legacyStampedHistory.createCriteria().andMessageIdEqualTo(ownedMessageId).andMigrationHashIsNotNull();
				assertEquals(0, messageHistoryDboMapper.countByExample(legacyStampedHistory));

				ThreadDboExample orphanedThread = new ThreadDboExample();
				orphanedThread.createCriteria().andThreadIdEqualTo(threadId).andCreatedUserIdEqualTo(0)
						.andMigrationHashIsNull();
				assertEquals(1, threadDboMapper.countByExample(orphanedThread));

				MessageDboExample postsInThread = new MessageDboExample();
				postsInThread.createCriteria().andThreadIdEqualTo(threadId);
				MessageHistoryDboExample scrubbedRevisions = new MessageHistoryDboExample();
				scrubbedRevisions.createCriteria().andIpAddressIdIsNull()
						.andMessageIdIn(messageDboMapper.selectByExample(postsInThread).stream()
								.map(MessageDbo::getMessageId).toList());
				assertEquals(3, messageHistoryDboMapper.countByExample(scrubbedRevisions));
				assertEquals(1, countWarnings(criteria -> criteria.andUserIdEqualTo(userId).andMigrationHashIsNull()));
				assertEquals(0,
						countWarnings(criteria -> criteria.andUserIdEqualTo(userId).andMigrationHashIsNotNull()));

				ModerationLogDboExample scrubbedModerationEntry = new ModerationLogDboExample();
				scrubbedModerationEntry.createCriteria().andActionEqualTo("LEGACY_" + userName)
						.andActorUserIdIsNull().andMigrationHashIsNull();
				assertEquals(1, moderationLogDboMapper.countByExample(scrubbedModerationEntry));

				ContentEntityDboExample scrubbedProject = new ContentEntityDboExample();
				scrubbedProject.createCriteria().andSlugEqualTo("proj-" + userName).andCreatedUserIdIsNull()
						.andAuthorNameEqualTo("[deleted]").andMigrationHashIsNull();
				assertEquals(1, contentEntityDboMapper.countByExample(scrubbedProject));

				PersonalMessageDboExample retainedMessage = new PersonalMessageDboExample();
				retainedMessage.createCriteria().andPersonalMessageIdEqualTo(receivedMessageId)
						.andSenderUserIdEqualTo(counterpartyId).andBodyEqualTo("counterparty body")
						.andMigrationHashIsNull();
				assertEquals(1, personalMessageDboMapper.countByExample(retainedMessage));

				PersonalMessageConversationDboExample retainedConversation = new PersonalMessageConversationDboExample();
				retainedConversation.createCriteria().andPersonalMessageConversationIdEqualTo(conversationId)
						.andSubjectEqualTo("Hello " + userName).andMigrationHashIsNull();
				assertEquals(1, personalMessageConversationDboMapper.countByExample(retainedConversation));

				PersonalMessageRecipientDboExample droppedRecipient = new PersonalMessageRecipientDboExample();
				droppedRecipient.createCriteria().andRecipientUserIdEqualTo(userId);
				assertEquals(0, personalMessageRecipientDboMapper.countByExample(droppedRecipient));

				UserBioInfoDboExample droppedBio = new UserBioInfoDboExample();
				droppedBio.createCriteria().andUserIdEqualTo(userId);
				assertEquals(0, userBioInfoDboMapper.countByExample(droppedBio));

				UserContactInfoDboExample droppedContact = new UserContactInfoDboExample();
				droppedContact.createCriteria().andUserIdEqualTo(userId);
				assertEquals(0, userContactInfoDboMapper.countByExample(droppedContact));

				EmailAddressDboExample droppedEmail = new EmailAddressDboExample();
				droppedEmail.createCriteria().andEmailAddressEqualTo(userName + "@fake-email.fake.tld.thing");
				assertEquals(0, emailAddressDboMapper.countByExample(droppedEmail));

				UserRefreshTokenDboExample liveRefreshToken = new UserRefreshTokenDboExample();
				liveRefreshToken.createCriteria().andUserIdEqualTo(userId).andRevokedFlagEqualTo(false);
				assertEquals(0, userRefreshTokenDboMapper.countByExample(liveRefreshToken));

				BrUserPermissionDboExample droppedPermission = new BrUserPermissionDboExample();
				droppedPermission.createCriteria().andUserIdEqualTo(userId);
				assertEquals(0, brUserPermissionDboMapper.countByExample(droppedPermission));

				UserPermissionGroupAssocDboExample droppedGroupAssoc = new UserPermissionGroupAssocDboExample();
				droppedGroupAssoc.createCriteria().andUserIdEqualTo(userId);
				assertEquals(0, userPermissionGroupAssocDboMapper.countByExample(droppedGroupAssoc));

				BoardSummaryViewDboExample generalBoard = new BoardSummaryViewDboExample();
				generalBoard.createCriteria().andBoardIdEqualTo(1);
				BoardSummaryViewDbo boardSummary = boardSummaryViewDboMapper.selectByExample(generalBoard).get(0);
				assertEquals(0, boardSummary.getLatestMessageOwnerId(),
						"the retained latest post must be attributed to the sentinel");
				assertEquals("[deleted]", boardSummary.getLatestMessageUserName());
				JsonNode boardAfter = boardNodeOf(fetchForum(adminToken), 1);
				assertEquals("[deleted]", boardAfter.path("latestMessageUserName").asString(),
						"the forum cache must be evicted so retained content renders as [deleted]");
				assertAuditStamped(userId, "ANONYMIZE");

				orphanedUserIds.add(userId);
				lastAccessToken = accessToken;
				lastOriginalRefreshToken = originalRefreshToken;
				lastRotatedRefreshToken = rotatedRefreshToken;
				lastUserId = userId;
			}

			assertEquals(1, testSystemInfoMapper.countIndexes("zfgbb", "ux_user_sso_key"));
			assertEquals(3,
					countUsers(criteria -> criteria.andSsoKeyIn(List.of("__deleted__",
							"__deleted__" + orphanedUserIds.get(0), "__deleted__" + orphanedUserIds.get(1)))),
					"the sentinel and both orphaned accounts must coexist under the unique sso_key index");

			Integer rerunUserId = lastUserId;
			userService.eraseUserRecords(rerunUserId, DeletionMode.ANONYMIZE);
			assertEquals("__deleted__" + lastUserId, userDboMapper.selectByPrimaryKey(lastUserId).getSsoKey(),
					"a full re-run against already-deleted state must be idempotent under the sso_key unique index");

			assertPostDeletionTeardown(userNames[1], "password123", lastAccessToken, lastOriginalRefreshToken,
					lastRotatedRefreshToken);
		}

		private Integer lowestMessageIdOwnedBy(Integer userId) {
			MessageDboExample ownedMessages = new MessageDboExample();
			ownedMessages.createCriteria().andOwnerIdEqualTo(userId);
			ownedMessages.setOrderByClause("message_id");
			return messageDboMapper.selectByExample(ownedMessages).stream()
					.map(MessageDbo::getMessageId).findFirst().orElse(null);
		}

		private void assertAuditStamped(Integer userId, String mode) {
			assertEquals(1, countDeletionAudits(criteria -> criteria.andSubjectUserIdSnapshotEqualTo(userId)
					.andModeEqualTo(mode).andInitiatedByEqualTo("SELF")
					.andConfirmedTsIsNotNull().andExecutedTsIsNotNull()));
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

			assertEquals(1, countDeletionAudits(criteria -> criteria.andSubjectUserIdSnapshotEqualTo(userId)
					.andRequestedTsIsNotNull().andConfirmedTsIsNull()));
			String confirmationToken = lastConfirmationToken();

			int sentBefore = mailDispatcher.sentMessages().size();
			mockMvc.perform(post("/users/account/delete")
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(deletionBody("ORPHAN", "password123", userName)))
					.andExpect(status().isAccepted())
					.andExpect(jsonPath("$.status").value("PENDING"));
			assertEquals(sentBefore + 1, mailDispatcher.sentMessages().size(),
					"repeating a request re-sends the confirmation email; that is the resend mechanism now");

			mockMvc.perform(post("/users/account/delete/confirm")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"token\": \"bogus-token-value\"}"))
					.andExpect(status().isBadRequest());

			mockMvc.perform(post("/users/account/delete/confirm")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"token\": \"" + confirmationToken + "\"}"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.status").value("COMPLETED"));

			assertEquals(1, countUsers(criteria -> criteria.andUserIdEqualTo(userId).andActiveFlagEqualTo(false)
					.andDisplayNameEqualTo("[deleted]").andTokensValidAfterTsIsNotNull()),
					"the subject must be neutralized after confirmation");

			mockMvc.perform(post("/users/account/delete/preview")
					.header("Authorization", "Bearer " + accessToken))
					.andExpect(status().isUnauthorized());

			mockMvc.perform(post("/users/account/delete/confirm")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"token\": \"" + confirmationToken + "\"}"))
					.andExpect(status().isBadRequest());

			List<MailDispatcher.OutboundMail> sent = mailDispatcher.sentMessages();
			assertTrue(sent.stream().anyMatch(mail -> mail.subject().contains("has been deleted")),
					"a courtesy completion notice must be dispatched");
			assertEquals(1, countDeletionAudits(criteria -> criteria.andSubjectUserIdSnapshotEqualTo(userId)
					.andConfirmedTsIsNotNull().andExecutedTsIsNotNull()));
		}

		@Test
		void adminDeletionKillsAnOutstandingConfirmationLink() throws Exception {
			String userName = "cancl_" + suffix;
			register(userName, "password123");
			String accessToken = login(userName, "password123").get("accessToken").asString();
			Integer userId = userIdOf(userName);

			mockMvc.perform(post("/users/account/delete")
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(deletionBody("WIPE", "password123", userName)))
					.andExpect(status().isAccepted());
			String confirmationToken = lastConfirmationToken();

			userService.eraseUserRecords(userId, DeletionMode.PURGE);

			mockMvc.perform(post("/users/account/delete/confirm")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"token\": \"" + confirmationToken + "\"}"))
					.andExpect(status().isBadRequest());
			assertEquals(0, countUsers(criteria -> criteria.andUserIdEqualTo(userId)),
					"an admin deletion must leave an outstanding self-service link inert");
		}

		@Test
		void expiredConfirmationLinkIsInert() throws Exception {
			String userName = "state_" + suffix;
			register(userName, "password123");
			Integer userId = userIdOf(userName);

			java.time.Instant past = java.time.Instant.now().minusSeconds(7200);
			org.springframework.security.oauth2.jwt.JwtClaimsSet claims =
					org.springframework.security.oauth2.jwt.JwtClaimsSet.builder()
							.subject("account-deletion:" + userId)
							.claim("mode", "ANONYMIZE")
							.issuedAt(past)
							.expiresAt(past.plusSeconds(3600))
							.build();
			String expiredToken = jwtEncoder.encode(org.springframework.security.oauth2.jwt.JwtEncoderParameters
					.from(org.springframework.security.oauth2.jwt.JwsHeader.with(() -> "HS256").build(), claims))
					.getTokenValue();

			mockMvc.perform(post("/users/account/delete/confirm")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"token\": \"" + expiredToken + "\"}"))
					.andExpect(status().isBadRequest());
			assertEquals(1, countUsers(criteria -> criteria.andUserIdEqualTo(userId).andActiveFlagEqualTo(true)),
					"an expired confirmation link must be inert");
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

			mockMvc.perform(get("/users/loggedInUser")
					.header("Authorization", "Bearer " + firstToken))
					.andExpect(status().isUnauthorized());

			confirmAccountDeletion(accessToken, "10.99.3.1").andExpect(status().isBadRequest());
			assertEquals(1, countUsers(criteria -> criteria.andUserIdEqualTo(userId).andActiveFlagEqualTo(true)),
					"an access token must never confirm a deletion");

			mockMvc.perform(post("/users/account/delete")
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(deletionBody("ORPHAN", "password123", userName)))
					.andExpect(status().isAccepted());
			String secondToken = lastConfirmationToken();

			confirmAccountDeletion(secondToken, "10.99.3.4")
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.status").value("COMPLETED"));
			assertEquals(1, countUsers(criteria -> criteria.andUserIdEqualTo(userId).andActiveFlagEqualTo(false)
					.andDisplayNameEqualTo("[deleted]")));
		}

		@Test
		void usedConfirmationLinkIsSingleUse() throws Exception {
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

			confirmAccountDeletion(confirmationToken, "10.99.5.1")
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.status").value("COMPLETED"));
			assertEquals(1, countUsers(criteria -> criteria.andUserIdEqualTo(userId).andActiveFlagEqualTo(false)
					.andDisplayNameEqualTo("[deleted]").andPasswordHashIsNull()),
					"a confirmed deletion must execute in the same transaction as the confirm");
			confirmAccountDeletion(confirmationToken, "10.99.5.2").andExpect(status().isBadRequest());
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
			assertEquals(1,
					countUsers(criteria -> criteria.andUserIdEqualTo(intruderId).andFailedLoginCountEqualTo(0)),
					"a mismatched confirmation phrase must be rejected before the password is evaluated");

			mockMvc.perform(post("/admin/users/delete")
					.header("Authorization", "Bearer " + intruderToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"userId\": " + targetId + ", \"mode\": \"PURGE\"}"))
					.andExpect(status().isForbidden());
			assertEquals(1, countUsers(criteria -> criteria.andUserIdEqualTo(targetId).andActiveFlagEqualTo(true)));

			MvcResult passwordFailure = mockMvc.perform(post("/users/account/delete")
					.header("Authorization", "Bearer " + intruderToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(deletionBody("WIPE", "wrongpassword", intruderName)))
					.andExpect(status().isBadRequest())
					.andReturn();
			assertEquals(1,
					countUsers(criteria -> criteria.andUserIdEqualTo(intruderId).andFailedLoginCountEqualTo(1)),
					"a wrong password behind a correct phrase must ride the login lockout counter");
			assertEquals(phraseFailure.getResponse().getContentAsString(),
					passwordFailure.getResponse().getContentAsString(),
					"phrase and password failures must be indistinguishable");
		}

		@Test
		void lastSiteAdminIsBlockedAtRequestAndAtConfirm() throws Exception {
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
			Integer adminId = userIdOf(ADMIN_USER);
			Integer siteAdminPermissionId = permissionIdOf("ZFGC_SITE_ADMIN");

			BrUserPermissionDboExample otherAdminGrants = new BrUserPermissionDboExample();
			otherAdminGrants.createCriteria().andUserPermissionIdEqualTo(siteAdminPermissionId)
					.andUserIdNotEqualTo(adminId);
			PermissionGroupAssocDboExample siteAdminGroups = new PermissionGroupAssocDboExample();
			siteAdminGroups.createCriteria().andPermissionIdEqualTo(siteAdminPermissionId);
			List<Integer> siteAdminGroupIds = permissionGroupAssocDboMapper.selectByExample(siteAdminGroups).stream()
					.map(PermissionGroupAssocDbo::getPermissionGroupId).toList();
			UserPermissionGroupAssocDboExample otherAdminMemberships = new UserPermissionGroupAssocDboExample();
			otherAdminMemberships.createCriteria().andUserIdNotEqualTo(adminId)
					.andPermissionGroupIdIn(siteAdminGroupIds.isEmpty() ? List.of(-1) : siteAdminGroupIds);

			List<BrUserPermissionDbo> strippedGrants =
					brUserPermissionDboMapper.selectByExample(otherAdminGrants);
			List<UserPermissionGroupAssocDbo> strippedMemberships =
					userPermissionGroupAssocDboMapper.selectByExample(otherAdminMemberships);
			try {
				brUserPermissionDboMapper.deleteByExample(otherAdminGrants);
				userPermissionGroupAssocDboMapper.deleteByExample(otherAdminMemberships);
				mockMvc.perform(post("/users/account/delete/preview")
						.header("Authorization", "Bearer " + adminToken))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.adminReplacementRequired").value(true));
				mockMvc.perform(post("/users/account/delete")
						.header("Authorization", "Bearer " + adminToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content(deletionBody("ORPHAN", ADMIN_PASSWORD, ADMIN_USER)))
						.andExpect(status().isBadRequest());

				String secondAdminName = "adm2_" + suffix;
				register(secondAdminName, "password123");
				Integer secondAdminId = userIdOf(secondAdminName);
				grantPermission(siteAdminPermissionId, secondAdminId);
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

				BrUserPermissionDboExample adminGrant = new BrUserPermissionDboExample();
				adminGrant.createCriteria().andUserPermissionIdEqualTo(siteAdminPermissionId)
						.andUserIdEqualTo(adminId);
				brUserPermissionDboMapper.deleteByExample(adminGrant);
				try {
					confirmAccountDeletion(confirmationToken, "10.99.4.1").andExpect(status().isBadRequest());
					assertEquals(1, countUsers(criteria -> criteria.andUserIdEqualTo(secondAdminId)
							.andActiveFlagEqualTo(true).andPasswordHashIsNotNull()
							.andUserNameEqualTo(secondAdminName)),
							"the roster re-check at confirm time must leave the account untouched");
				} finally {
					grantPermission(siteAdminPermissionId, adminId);
				}
			} finally {
				for (BrUserPermissionDbo grant : strippedGrants)
					brUserPermissionDboMapper.insertSelective(grant);
				for (UserPermissionGroupAssocDbo membership : strippedMemberships)
					userPermissionGroupAssocDboMapper.insertSelective(membership);
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
		void everyRestrictReferrerOfCensusTargetsHasADeletionPlanStep() {
			Set<String> liveRestrictReferrers =
					new TreeSet<>(testSystemInfoMapper.listRestrictReferrersOfCensusTargets());
			assertEquals(new TreeSet<>(RESTRICT_REFERRERS_COVERED_BY_DELETION_PLAN), liveRestrictReferrers);
		}

		@Test
		void sentinelUserIsReservedAtFixedIdWithInertCredentials() {
			var sentinel = userDboMapper.selectByPrimaryKey(0);
			assertEquals("__deleted__", sentinel.getSsoKey());
			assertEquals("__deleted__", sentinel.getUserName());
			assertEquals("[deleted]", sentinel.getDisplayName());
			assertEquals(false, sentinel.getActiveFlag());
			assertNull(sentinel.getPasswordHash());
		}

	}
}
