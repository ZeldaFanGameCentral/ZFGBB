package com.zfgc.zfgbb.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.zfgc.zfgbb.mappers.ModerationLogDboMapper;
import com.zfgc.zfgbb.mappers.UserBioInfoDboMapper;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserBioInfoDboExample;
import com.zfgc.zfgbb.dbo.UserBioInfoDbo;
import com.zfgc.zfgbb.dbo.ModerationLogDboExample;
import com.zfgc.zfgbb.dbo.ModerationLogDbo;
import com.zfgc.zfgbb.dbo.NotificationSubscriptionDbo;
import com.zfgc.zfgbb.dbo.NotificationSubscriptionDboExample;
import com.zfgc.zfgbb.dbo.PersonalMessageConversationDbo;
import com.zfgc.zfgbb.dbo.PersonalMessageDbo;
import com.zfgc.zfgbb.dbo.PersonalMessageRecipientDbo;
import com.zfgc.zfgbb.dbo.PersonalMessageRecipientDboExample;
import com.zfgc.zfgbb.dbo.ReactionDbo;
import com.zfgc.zfgbb.dbo.ReactionDboExample;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.dbo.UserSettingsDbo;
import com.zfgc.zfgbb.dbo.UserSettingsDboExample;
import com.zfgc.zfgbb.dbo.UserWarningDbo;
import com.zfgc.zfgbb.dbo.UserWarningDboExample;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDboExample;
import com.zfgc.zfgbb.mappers.NotificationSubscriptionDboMapper;
import com.zfgc.zfgbb.mappers.PersonalMessageConversationDboMapper;
import com.zfgc.zfgbb.mappers.PersonalMessageDboMapper;
import com.zfgc.zfgbb.mappers.PersonalMessageRecipientDboMapper;
import com.zfgc.zfgbb.mappers.ReactionDboMapper;
import com.zfgc.zfgbb.mappers.ReactionTypeDboMapper;
import com.zfgc.zfgbb.mappers.UserDboMapper;
import com.zfgc.zfgbb.mappers.UserSettingsDboMapper;
import com.zfgc.zfgbb.mappers.UserWarningDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageRevisionDboMapper;
import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

class AccountLifecycleTest extends PostgresIntegrationTest {

	@Autowired private NotificationSubscriptionDboMapper notificationSubscriptionDboMapper;
	@Autowired private PersonalMessageConversationDboMapper personalMessageConversationDboMapper;
	@Autowired private PersonalMessageDboMapper personalMessageDboMapper;
	@Autowired private PersonalMessageRecipientDboMapper personalMessageRecipientDboMapper;
	@Autowired private ReactionDboMapper reactionDboMapper;
	@Autowired private ReactionTypeDboMapper reactionTypeDboMapper;
	@Autowired private ModerationLogDboMapper moderationLogDboMapper;
	@Autowired private UserBioInfoDboMapper userBioInfoDboMapper;
	@Autowired private UserDboMapper userDboMapper;
	@Autowired private UserSettingsDboMapper userSettingsDboMapper;
	@Autowired private UserWarningDboMapper userWarningDboMapper;
	@Autowired private WikiPageDboMapper wikiPageDboMapper;
	@Autowired private WikiPageRevisionDboMapper wikiPageRevisionDboMapper;

	@Test
	void purgingAMemberDeletesEveryExplicitlyOwnedRowNowThatNoUserFkCascades() throws Exception {
		TestUser subject = createUser("purge_" + suffix);
		TestUser bystander = createUser("stays_" + suffix);
		String adminToken = getAdminToken();

		UserSettingsDbo settings = new UserSettingsDbo();
		settings.setUserId(subject.id());
		userSettingsDboMapper.insertSelective(settings);

		UserWarningDbo warning = new UserWarningDbo();
		warning.setUserId(subject.id());
		warning.setBody("subject warning " + suffix);
		userWarningDboMapper.insertSelective(warning);

		int threadId = postThread(bystander.token(), "Purge fixture " + suffix, "op body");

		NotificationSubscriptionDbo subscription = new NotificationSubscriptionDbo();
		subscription.setUserId(subject.id());
		subscription.setThreadId(threadId);
		notificationSubscriptionDboMapper.insertSelective(subscription);

		PersonalMessageConversationDbo conversation = new PersonalMessageConversationDbo();
		conversation.setSubject("purge pm " + suffix);
		personalMessageConversationDboMapper.insertSelective(conversation);
		PersonalMessageDbo personalMessage = new PersonalMessageDbo();
		personalMessage.setPersonalMessageConversationId(conversation.getPersonalMessageConversationId());
		personalMessage.setSenderUserId(bystander.id());
		personalMessage.setBody("a message the subject received");
		personalMessageDboMapper.insertSelective(personalMessage);
		PersonalMessageRecipientDbo recipient = new PersonalMessageRecipientDbo();
		recipient.setPersonalMessageId(personalMessage.getPersonalMessageId());
		recipient.setRecipientUserId(subject.id());
		personalMessageRecipientDboMapper.insertSelective(recipient);

		mockMvc.perform(post("/wiki/meta/revisions")
				.header("Authorization", "Bearer " + subject.token())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"slug\": \"Purgeprobe" + suffix + "\", \"content\": \"wiki contribution body\"}"))
				.andExpect(status().isOk());

		WikiPageDboExample ownedPage = new WikiPageDboExample();
		ownedPage.createCriteria().andSlugEqualTo("Purgeprobe" + suffix).andCreatedUserIdEqualTo(subject.id());
		assertEquals(1, wikiPageDboMapper.countByExample(ownedPage),
				"the submitted page must record the subject as its creator, or the purge below proves nothing");

		int reactionTypeId = reactionTypeDboMapper.selectByExample(null).get(0).getReactionTypeId();
		int reactedMessageId = messageIdAt(threadId, 1);
		mockMvc.perform(post("/reactions")
				.header("Authorization", "Bearer " + subject.token())
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"reactableType": "MESSAGE", "reactableId": %d, "reactionTypeId": %d}
						""".formatted(reactedMessageId, reactionTypeId)))
				.andExpect(status().isOk());

		mockMvc.perform(post("/admin/users/delete")
				.header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"userId\": " + subject.id() + ", \"mode\": \"PURGE\"}"))
				.andExpect(status().isNoContent());

		UserDboExample subjectRow = new UserDboExample();
		subjectRow.createCriteria().andUserIdEqualTo(subject.id());
		assertEquals(0, userDboMapper.countByExample(subjectRow),
				"the purge must end with the user row itself deleted");

		UserSettingsDboExample settingsRow = new UserSettingsDboExample();
		settingsRow.createCriteria().andUserIdEqualTo(subject.id());
		assertEquals(0, userSettingsDboMapper.countByExample(settingsRow),
				"user_settings no longer cascades, so the purge must delete it explicitly");

		UserWarningDboExample warningRows = new UserWarningDboExample();
		warningRows.createCriteria().andUserIdEqualTo(subject.id());
		assertEquals(0, userWarningDboMapper.countByExample(warningRows),
				"user_warning subject rows no longer cascade, so the purge must delete them explicitly");

		NotificationSubscriptionDboExample subscriptionRows = new NotificationSubscriptionDboExample();
		subscriptionRows.createCriteria().andUserIdEqualTo(subject.id());
		assertEquals(0, notificationSubscriptionDboMapper.countByExample(subscriptionRows),
				"notification_subscription no longer cascades, so the purge must delete it explicitly");

		PersonalMessageRecipientDboExample recipientRows = new PersonalMessageRecipientDboExample();
		recipientRows.createCriteria().andRecipientUserIdEqualTo(subject.id());
		assertEquals(0, personalMessageRecipientDboMapper.countByExample(recipientRows),
				"personal_message_recipient rows no longer cascade, so the purge must delete them explicitly");

		WikiPageDboExample retainedPage = new WikiPageDboExample();
		retainedPage.createCriteria().andSlugEqualTo("Purgeprobe" + suffix);
		assertEquals(0, wikiPageDboMapper.countByExample(retainedPage),
				"the purge must hard-delete a wiki page the subject created and nothing else references");

		WikiPageRevisionDboExample retainedContributions = new WikiPageRevisionDboExample();
		retainedContributions.createCriteria().andAuthorUserIdEqualTo(subject.id());
		assertEquals(0, wikiPageRevisionDboMapper.countByExample(retainedContributions),
				"no wiki revision may still name the purged member as its author");

		ReactionDboExample givenReactions = new ReactionDboExample();
		givenReactions.createCriteria().andReactableTypeEqualTo("MESSAGE")
				.andReactableIdIn(List.of(reactedMessageId)).andReactorUserIdIsNull();
		assertEquals(1, reactionDboMapper.countByExample(givenReactions),
				"the given reaction survives with its reactor scrubbed to null");
	}

	@Test
	void anonymisingAMemberScrubsEveryColumnTheEraseContractNames() throws Exception {
		TestUser subject = createUser("anon_" + suffix);
		TestUser bystander = createUser("keeps_" + suffix);
		String adminToken = getAdminToken();

		UserBioInfoDbo bio = new UserBioInfoDbo();
		bio.setSignature("[b]sig " + suffix + "[/b]");
		bio.setLocation("Hyrule");
		bio.setRealName("Real " + suffix);
		UserBioInfoDboExample subjectBio = new UserBioInfoDboExample();
		subjectBio.createCriteria().andUserIdEqualTo(subject.id());
		userBioInfoDboMapper.updateByExampleSelective(bio, subjectBio);

		int threadId = postThread(bystander.token(), "Anon fixture " + suffix, "op body");
		int reactedMessageId = messageIdAt(threadId, 1);
		int reactionTypeId = reactionTypeDboMapper.selectByExample(null).get(0).getReactionTypeId();
		mockMvc.perform(post("/reactions")
				.header("Authorization", "Bearer " + subject.token())
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{"reactableType": "MESSAGE", "reactableId": %d, "reactionTypeId": %d}
						""".formatted(reactedMessageId, reactionTypeId)))
				.andExpect(status().isOk());

		ReactionDbo authoredComment = new ReactionDbo();
		authoredComment.setComment("reaction comment " + suffix);
		ReactionDboExample subjectReaction = new ReactionDboExample();
		subjectReaction.createCriteria().andReactableIdEqualTo(reactedMessageId).andReactorUserIdEqualTo(subject.id());
		reactionDboMapper.updateByExampleSelective(authoredComment, subjectReaction);

		ModerationLogDbo namedByCase = new ModerationLogDbo();
		namedByCase.setAction("WARN");
		namedByCase.setTargetName(subject.userName().toUpperCase());
		moderationLogDboMapper.insertSelective(namedByCase);

		ModerationLogDbo merelyContainsTheName = new ModerationLogDbo();
		merelyContainsTheName.setAction("WARN");
		merelyContainsTheName.setTargetName("x" + subject.userName() + "x");
		moderationLogDboMapper.insertSelective(merelyContainsTheName);

		mockMvc.perform(post("/admin/users/delete")
				.header("Authorization", "Bearer " + adminToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"userId\": " + subject.id() + ", \"mode\": \"ANONYMIZE\"}"))
				.andExpect(status().isNoContent());

		UserDboExample subjectRow = new UserDboExample();
		subjectRow.createCriteria().andUserIdEqualTo(subject.id());
		UserDbo neutralized = userDboMapper.selectByExample(subjectRow).get(0);
		assertEquals("[deleted]", neutralized.getUserName(), "the anonymised row keeps its id but loses its name");
		assertEquals("[deleted]", neutralized.getDisplayName());
		assertNull(neutralized.getPasswordHash(), "credentials must not survive anonymisation");
		assertFalse(neutralized.getActiveFlag(), "an anonymised account must not stay active");

		UserBioInfoDboExample bioRow = new UserBioInfoDboExample();
		bioRow.createCriteria().andUserIdEqualTo(subject.id());
		assertEquals(0, userBioInfoDboMapper.countByExample(bioRow),
				"anonymise drops the bio row outright via purgeBioInfoAndAvatar, so the member's free "
						+ "text cannot survive; UserDataProvider.neutralizeIdentity's later scrubUserBioInfo "
						+ "runs against an already-deleted row and updates nothing");

		ReactionDboExample givenReaction = new ReactionDboExample();
		givenReaction.createCriteria().andReactableIdEqualTo(reactedMessageId).andReactorUserIdIsNull();
		ReactionDbo scrubbedReaction = reactionDboMapper.selectByExample(givenReaction).get(0);
		assertNull(scrubbedReaction.getReactorUserId(), "the reactor must be detached from the reaction");
		assertNull(scrubbedReaction.getComment(),
				"a reaction comment is member-authored text, so scrubbing the reactor is not enough");

		ModerationLogDboExample byName = new ModerationLogDboExample();
		byName.createCriteria().andModerationLogIdEqualTo(namedByCase.getModerationLogId());
		assertEquals("[deleted]", moderationLogDboMapper.selectByExample(byName).get(0).getTargetName(),
				"an unresolved moderation target naming the member must be scrubbed regardless of case");

		ModerationLogDboExample bySubstring = new ModerationLogDboExample();
		bySubstring.createCriteria().andModerationLogIdEqualTo(merelyContainsTheName.getModerationLogId());
		assertEquals("x" + subject.userName() + "x",
				moderationLogDboMapper.selectByExample(bySubstring).get(0).getTargetName(),
				"the ilike narrowing is a superset, so a name that merely contains the member's must survive");
	}
}
