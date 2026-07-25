package com.zfgc.zfgbb.migration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import com.zfgc.zfgbb.dbo.*;
import com.zfgc.zfgbb.mappers.*;
import com.zfgc.zfgbb.migrator.jobs.Job;
import com.zfgc.zfgbb.migrator.jobs.JobState;
import com.zfgc.zfgbb.migrator.jobs.JobType;

@Order(1)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SmfMigrationTest extends MigrationE2E {

	@Autowired private AvatarDboMapper avatarDboMapper;
	@Autowired private AwardDboMapper awardDboMapper;
	@Autowired private BoardDboMapper boardDboMapper;
	@Autowired private BrBoardPermissionDboMapper brBoardPermissionDboMapper;
	@Autowired private BrUserPermissionDboMapper brUserPermissionDboMapper;
	@Autowired private CategoryDboMapper categoryDboMapper;
	@Autowired private EmailAddressDboMapper emailAddressDboMapper;
	@Autowired private MigratorIdMapDboMapper migratorIdMapDboMapper;
	@Autowired private PermissionDboMapper permissionDboMapper;
	@Autowired private ContentResourceDboMapper contentResourceDboMapper;
	@Autowired private FileAttachmentDboMapper fileAttachmentDboMapper;
	@Autowired private IpAddressDboMapper ipAddressDboMapper;
	@Autowired private MigratorAttachmentRefRewriteDboMapper migratorAttachmentRefRewriteDboMapper;
	@Autowired private ModerationLogDboMapper moderationLogDboMapper;
	@Autowired private NotificationSubscriptionDboMapper notificationSubscriptionDboMapper;
	@Autowired private PermissionGroupAssocDboMapper permissionGroupAssocDboMapper;
	@Autowired private PermissionGroupDboMapper permissionGroupDboMapper;
	@Autowired private PersonalMessageConversationDboMapper personalMessageConversationDboMapper;
	@Autowired private PersonalMessageDboMapper personalMessageDboMapper;
	@Autowired private PersonalMessageRecipientDboMapper personalMessageRecipientDboMapper;
	@Autowired private PollChoiceDboMapper pollChoiceDboMapper;
	@Autowired private PollDboMapper pollDboMapper;
	@Autowired private ReactionDboMapper reactionDboMapper;
	@Autowired private ReactionTypeDboMapper reactionTypeDboMapper;
	@Autowired private UserBioInfoDboMapper userBioInfoDboMapper;
	@Autowired private UserContactInfoDboMapper userContactInfoDboMapper;
	@Autowired private UserDboMapper userDboMapper;
	@Autowired private UserPermissionGroupAssocDboMapper userPermissionGroupAssocDboMapper;
	@Autowired private UserPollChoiceDboMapper userPollChoiceDboMapper;
	@Autowired private UserWarningDboMapper userWarningDboMapper;

	@Test
	@Order(1)
	void pipelineMatchesEverySourceCount() {
		assertEquals(JobType.SMF_INSTALLATION_PIPELINE.size(), smfJobs.size(),
				"Pipeline should submit one job per converter type");

		UserDboExample migratedUsers = new UserDboExample();
		migratedUsers.createCriteria().andUserIdNotEqualTo(0).andUserNameNotEqualTo(ADMIN_USER);
		assertSameCount(smf, "smf_1members", userDboMapper.countByExample(migratedUsers));
		assertSameCount(smf, "smf_1categories", categoryDboMapper.countByExample(null));
		assertSameCount(smf, "smf_1boards", boardsMappedFromLegacy("BOARD"));
		assertEquals(1, migratedBoardPermissions(1, "ZFGC_WIKI_MODERATOR"),
				"BoardConverter honors the operator group->permission map: SMF group 9 -> ZFGC_WIKI_MODERATOR on board 1");
		assertEquals(1, migratedBoardPermissions(1, "ZFGC_GUEST"),
				"reserved SMF guest group (-1) maps to ZFGC_GUEST on the migrated board");
		assertSourceMatchesTarget(smf, "select count(*) from smf_1topics", threadCountAfterSmf);
		assertSourceMatchesTarget(smf, "select count(*) from smf_1messages", messageCountAfterSmf);

		UserBioInfoDboExample migratedBios = new UserBioInfoDboExample();
		migratedBios.createCriteria().andUserIdNotEqualTo(1);
		assertSameCount(smf, "smf_1members", userBioInfoDboMapper.countByExample(migratedBios));

		UserContactInfoDboExample migratedContacts = new UserContactInfoDboExample();
		migratedContacts.createCriteria().andUserIdNotEqualTo(1);
		assertSameCount(smf, "smf_1members", userContactInfoDboMapper.countByExample(migratedContacts));

		assertSameCount(smf, "smf_1members", emailAddressesNotHeldByUser(1));
		assertSameCount(smf, "smf_1polls", pollDboMapper.countByExample(null));
		assertSameCount(smf, "smf_1poll_choices", pollChoiceDboMapper.countByExample(null));

		ReactionDboExample messageReactions = new ReactionDboExample();
		messageReactions.createCriteria().andReactableTypeEqualTo("MESSAGE");
		assertSameCount(smf, "smf_1log_karma", reactionDboMapper.countByExample(messageReactions));
		assertEquals(5, reactionTypeDboMapper.countByExample(null), "the reaction vocabulary is seeded");
		assertSourceMatchesTarget(smf, "select count(*) from smf_1log_karma where action = 1",
				reactionsWithCode("LIKE"));
		assertEquals(3, awardDboMapper.countByExample(null), "the award catalog is seeded");
		assertSameCount(smf, "smf_1attachments where id_msg > 0", fileAttachmentDboMapper.countByExample(null));
		assertSameCount(smf, 
				"smf_1members m where m.avatar != '' or exists "
						+ "(select 1 from smf_1attachments a where a.id_member = m.id_member and a.id_msg = 0)",
				avatarDboMapper.countByExample(null));
		assertSameCount(smf, "smf_1membergroups", permissionGroupDboMapper.countByExample(null));
		assertSameCount(smf, "smf_1personal_messages", personalMessageDboMapper.countByExample(null));
		assertSameCount(smf, "smf_1pm_recipients", personalMessageRecipientDboMapper.countByExample(null));
		assertSameCount(smf, "(select distinct id_pm_head from smf_1personal_messages) heads",
				personalMessageConversationDboMapper.countByExample(null));
		assertSameCount(smf, "smf_1log_notify", notificationSubscriptionDboMapper.countByExample(null));
		assertSameCount(smf, "smf_1log_comments where comment_type = 'warning'",
				userWarningDboMapper.countByExample(null));
		assertSourceMatchesTarget(smf, 
				"select (select count(*) from smf_1log_actions where action != 'delete_member') "
						+ "+ (select count(*) from smf_1log_comments where comment_type = 'warning')",
				moderationLogDboMapper.countByExample(null));
		assertSameCount(smf, "smf_1log_comments where comment_type = 'warning'",
				countModerationLog(criteria -> criteria.andActionEqualTo("WARN")));
		assertSourceMatchesTarget(smf, 
				"select count(*) from (select poster_ip from smf_1messages union "
						+ "select postIP from smf_1game_comments union "
						+ "select postIP from smf_1resource_comments) ips",
				ipAddressDboMapper.countByExample(null));
		assertSourceMatchesTarget(smf, 
				"select (select count(*) from smf_1messages) + (select count(*) from smf_1messages_history)",
				messageHistoryCountAfterSmf);
		assertSourceMatchesTarget(smf, "select count(*) from smf_1log_polls where id_member != 0",
				userPollChoiceDboMapper.countByExample(null));

		assertNoOrphanRewrittenBBCodes();
		assertAllMigratedAttachmentsPresent();
	}

	@Test
	@Order(4)
	void socialDataCarriesStateAndRelationships() {
		PersonalMessageDboExample senderDeleted = new PersonalMessageDboExample();
		senderDeleted.createCriteria().andDeletedBySenderEqualTo(true);
		assertEquals(2, personalMessageDboMapper.countByExample(senderDeleted),
				"the sender-deleted PMs keep their mailbox state");
		assertEquals(1, countPmRecipients(criteria -> criteria.andBccEqualTo(true)),
				"the BCC recipient survives with the flag");
		assertEquals(3, countPmRecipients(criteria -> criteria.andReadFlagEqualTo(true)),
				"read state migrates");
		assertEquals(1, countPmRecipients(criteria -> criteria.andDeletedFlagEqualTo(true)),
				"recipient-side deletes migrate");
		assertEquals(2, messagesInConversationSubject("kamehameha"),
				"the reply chain lands in one conversation without Re: prefixes");
		assertEquals(2, countSubscriptions(criteria -> criteria.andBoardIdIsNotNull()),
				"board watches migrate");
		assertEquals(3, countSubscriptions(criteria -> criteria.andThreadIdIsNotNull()),
				"topic watches migrate");

		UserWarningDboExample pointedWarnings = new UserWarningDboExample();
		pointedWarnings.createCriteria().andPointsEqualTo(20);
		assertEquals(1, userWarningDboMapper.countByExample(pointedWarnings),
				"a warning carries its points on the per-warning ledger");
		assertEquals(0, countModerationLog(criteria -> criteria.andActionEqualTo("DELETE_MEMBER")),
				"spam-purge noise is not migrated");
		assertTrue(countModerationLog(
				criteria -> criteria.andActionEqualTo("LOCK_THREAD").andThreadIdIsNotNull()) >= 1,
				"mod actions keep their remapped thread references");

		PermissionGroupDboExample coloredGroups = new PermissionGroupDboExample();
		coloredGroups.createCriteria().andColorIsNotNull();
		assertEquals(2, permissionGroupDboMapper.countByExample(coloredGroups),
				"group colors migrate");
		assertTrue(userPermissionGroupAssocDboMapper.countByExample(null) >= 4,
				"primary, post, and additional group memberships all land");
		assertTrue(permissionGroupAssocDboMapper.countByExample(null) > 0,
				"groups grant permissions via permission_group_assoc");
		assertEquals(1, migratedUserPermissions(3, "ZFGC_WIKI_MODERATOR"),
				"the wiki-maintainer group expands into the enforced br_user_permission");
	}

	@Test
	@Order(2)
	void rerunningAttachmentsDoesNotDoubleRewrite() throws Exception {
		List<String> beforeBodies = attachBodies();
		long markersBefore = migratorAttachmentRefRewriteDboMapper.countByExample(null);

		Job rerun = jobService.submit(JobType.ATTACHMENTS, params()).get(0);
		Job finished = waitForAllTerminal(List.of(rerun), Duration.ofMinutes(2)).get(0);
		assertEquals(JobState.COMPLETED, finished.getState(),
				"re-running ATTACHMENTS should COMPLETE; got " + finished.getState()
						+ " (error=" + finished.getError() + ")");

		assertEquals(beforeBodies, attachBodies(), "[attach=N] bodies must be unchanged on re-run");
		assertEquals(markersBefore, migratorAttachmentRefRewriteDboMapper.countByExample(null),
				"re-run should not add new rewrite markers");
	}

	private Integer permissionIdOf(String permissionCode) {
		PermissionDboExample byCode = new PermissionDboExample();
		byCode.createCriteria().andPermissionCodeEqualTo(permissionCode);
		return permissionDboMapper.selectByExample(byCode).stream()
				.map(PermissionDbo::getPermissionId).findFirst().orElse(null);
	}

	private List<Integer> zfgbbIdsMappedFrom(String entityType, Integer legacyId) {
		MigratorIdMapDboExample mapped = new MigratorIdMapDboExample();
		MigratorIdMapDboExample.Criteria criteria = mapped.createCriteria().andEntityTypeEqualTo(entityType);
		if (legacyId != null)
			criteria.andLegacyIdEqualTo(legacyId);
		return migratorIdMapDboMapper.selectByExample(mapped).stream()
				.map(MigratorIdMapDbo::getZfgbbId).toList();
	}

	private long boardsMappedFromLegacy(String entityType) {
		List<Integer> mappedBoardIds = zfgbbIdsMappedFrom(entityType, null);
		if (mappedBoardIds.isEmpty())
			return 0;
		BoardDboExample migratedBoards = new BoardDboExample();
		migratedBoards.createCriteria().andBoardIdIn(mappedBoardIds);
		return boardDboMapper.countByExample(migratedBoards);
	}

	private long migratedBoardPermissions(int legacyBoardId, String permissionCode) {
		List<Integer> boardIds = zfgbbIdsMappedFrom("BOARD", legacyBoardId);
		Integer permissionId = permissionIdOf(permissionCode);
		if (boardIds.isEmpty() || permissionId == null)
			return 0;
		BrBoardPermissionDboExample grants = new BrBoardPermissionDboExample();
		grants.createCriteria().andBoardIdIn(boardIds).andPermissionIdEqualTo(permissionId);
		return brBoardPermissionDboMapper.countByExample(grants);
	}

	private long migratedUserPermissions(int legacyUserId, String permissionCode) {
		List<Integer> userIds = zfgbbIdsMappedFrom("USER", legacyUserId);
		Integer permissionId = permissionIdOf(permissionCode);
		if (userIds.isEmpty() || permissionId == null)
			return 0;
		BrUserPermissionDboExample grants = new BrUserPermissionDboExample();
		grants.createCriteria().andUserIdIn(userIds).andUserPermissionIdEqualTo(permissionId);
		return brUserPermissionDboMapper.countByExample(grants);
	}

	private long messagesInConversationSubject(String subject) {
		PersonalMessageConversationDboExample bySubject = new PersonalMessageConversationDboExample();
		bySubject.createCriteria().andSubjectEqualTo(subject);
		List<Integer> conversationIds = personalMessageConversationDboMapper.selectByExample(bySubject).stream()
				.map(PersonalMessageConversationDbo::getPersonalMessageConversationId).toList();
		if (conversationIds.isEmpty())
			return 0;
		PersonalMessageDboExample inConversation = new PersonalMessageDboExample();
		inConversation.createCriteria().andPersonalMessageConversationIdIn(conversationIds);
		return personalMessageDboMapper.countByExample(inConversation);
	}

	private long emailAddressesNotHeldByUser(int excludedUserId) {
		UserContactInfoDboExample heldByExcluded = new UserContactInfoDboExample();
		heldByExcluded.createCriteria().andUserIdEqualTo(excludedUserId);
		List<Integer> excludedAddressIds = userContactInfoDboMapper.selectByExample(heldByExcluded).stream()
				.map(UserContactInfoDbo::getEmailAddressId).filter(Objects::nonNull).toList();
		if (excludedAddressIds.isEmpty())
			return emailAddressDboMapper.countByExample(null);
		EmailAddressDboExample others = new EmailAddressDboExample();
		others.createCriteria().andEmailAddressIdNotIn(excludedAddressIds);
		return emailAddressDboMapper.countByExample(others);
	}

	private long reactionsWithCode(String reactionCode) {
		ReactionTypeDboExample byCode = new ReactionTypeDboExample();
		byCode.createCriteria().andCodeEqualTo(reactionCode);
		Integer reactionTypeId = reactionTypeDboMapper.selectByExample(byCode).stream()
				.map(ReactionTypeDbo::getReactionTypeId).findFirst().orElse(null);
		if (reactionTypeId == null)
			return 0;
		ReactionDboExample given = new ReactionDboExample();
		given.createCriteria().andReactionTypeIdEqualTo(reactionTypeId);
		return reactionDboMapper.countByExample(given);
	}

	private long countPmRecipients(Consumer<PersonalMessageRecipientDboExample.Criteria> criteria) {
		PersonalMessageRecipientDboExample example = new PersonalMessageRecipientDboExample();
		criteria.accept(example.createCriteria());
		return personalMessageRecipientDboMapper.countByExample(example);
	}

	private long countSubscriptions(Consumer<NotificationSubscriptionDboExample.Criteria> criteria) {
		NotificationSubscriptionDboExample example = new NotificationSubscriptionDboExample();
		criteria.accept(example.createCriteria());
		return notificationSubscriptionDboMapper.countByExample(example);
	}

	private long countModerationLog(Consumer<ModerationLogDboExample.Criteria> criteria) {
		ModerationLogDboExample example = new ModerationLogDboExample();
		criteria.accept(example.createCriteria());
		return moderationLogDboMapper.countByExample(example);
	}

	private List<String> attachBodies() {
		MessageHistoryDboExample rewrittenBodies = new MessageHistoryDboExample();
		rewrittenBodies.createCriteria().andMessageTextLike("%[attach=%");
		rewrittenBodies.setOrderByClause("message_history_id");
		return messageHistoryDboMapper.selectByExample(rewrittenBodies).stream()
				.map(MessageHistoryDbo::getMessageText).toList();
	}

	private Map<Pattern, Supplier<List<Integer>>> refSources() {
		return Map.of(
				Pattern.compile("\\[attach=(\\d+)\\]"),
				() -> fileAttachmentDboMapper.selectByExample(new FileAttachmentDboExample()).stream()
						.map(FileAttachmentDbo::getFileAttachmentId).toList(),
				Pattern.compile("\\[thread=(\\d+)(?:\\s|\\])"),
				() -> threadDboMapper.selectByExample(new ThreadDboExample()).stream()
						.map(ThreadDbo::getThreadId).toList(),
				Pattern.compile("\\[board=(\\d+)\\]"),
				() -> boardDboMapper.selectByExample(new BoardDboExample()).stream()
						.map(BoardDbo::getBoardId).toList(),
				Pattern.compile("\\[member=(\\d+)\\]"),
				() -> userDboMapper.selectByExample(new UserDboExample()).stream()
						.map(UserDbo::getUserId).toList(),
				Pattern.compile("msg=(\\d+)"),
				() -> messageDboMapper.selectByExample(new MessageDboExample()).stream()
						.map(MessageDbo::getMessageId).toList());
	}

	private void assertNoOrphanRewrittenBBCodes() {
		List<String> bodies = messageHistoryDboMapper.selectByExample(new MessageHistoryDboExample()).stream()
				.map(MessageHistoryDbo::getMessageText).toList();
		assertFalse(bodies.isEmpty(), "the migration must produce message bodies to scan for orphan refs");
		List<String> patternsWithoutCoverage = new ArrayList<>();
		int scannedRefs = 0;
		for (Map.Entry<Pattern, Supplier<List<Integer>>> refSource : refSources().entrySet()) {
			Pattern pattern = refSource.getKey();
			Set<Integer> validIds = new HashSet<>(refSource.getValue().get());
			int matchesForPattern = 0;
			for (String body : bodies) {
				Matcher m = pattern.matcher(body);
				while (m.find()) {
					int id = Integer.parseInt(m.group(1));
					assertTrue(validIds.contains(id),
							pattern + " ref " + id + " does not resolve to a migrated row: " + body);
					matchesForPattern++;
				}
			}
			if (matchesForPattern == 0)
				patternsWithoutCoverage.add(pattern.pattern());
			scannedRefs += matchesForPattern;
		}
		assertTrue(scannedRefs > 0,
				"no migrated body carries any rewritten ref, so this check proves nothing; "
						+ "uncovered patterns: " + patternsWithoutCoverage);
	}

	private void assertAllMigratedAttachmentsPresent() {
		List<FileAttachmentDbo> attachments = fileAttachmentDboMapper.selectByExample(null);
		assertFalse(attachments.isEmpty(), "the migration must produce attachments to verify on disk");
		for (FileAttachmentDbo attachment : attachments) {
			ContentResourceDbo stored =
					contentResourceDboMapper.selectByPrimaryKey(attachment.getContentResourceId());
			assertEquals("forum/attachments", stored.getStorageDir(),
					"attachments must be organized under forum/attachments: " + stored.getContentResourceId());
			assertTrue(Files.exists(contentTarget.resolve(stored.getStorageDir())
					.resolve(String.valueOf(stored.getContentResourceId()))
					.resolve(stored.getFilename())),
					"attachment should keep its original filename on disk: " + stored.getFilename());
		}
	}
}
