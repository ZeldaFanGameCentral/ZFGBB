package com.zfgc.zfgbb.dao.forum;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.forum.PersonalMessageRecipientDao;
import com.zfgc.zfgbb.dao.users.PersonalMessageConversationDao;
import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.PersonalMessageDbo;
import com.zfgc.zfgbb.dbo.PersonalMessageDboExample;
import com.zfgc.zfgbb.mappers.PersonalMessageDboMapper;

@Repository
public class PersonalMessageDao extends IdentityDao<PersonalMessageDbo, PersonalMessageDboExample> {

	private static final String DELETED_NAME = "[deleted]";

	private final PersonalMessageRecipientDao personalMessageRecipientDao;

	private final PersonalMessageConversationDao personalMessageConversationDao;

	public PersonalMessageDao(PersonalMessageDboMapper mapper,
			PersonalMessageConversationDao personalMessageConversationDao,
			PersonalMessageRecipientDao personalMessageRecipientDao) {
		super(mapper);
		this.personalMessageConversationDao = personalMessageConversationDao;
		this.personalMessageRecipientDao = personalMessageRecipientDao;
	}

	public List<Integer> findParticipantConversationIds(Integer userId) {
		PersonalMessageDboExample sent = new PersonalMessageDboExample();
		sent.createCriteria().andSenderUserIdEqualTo(userId);
		Set<Integer> conversations = new LinkedHashSet<>(get(sent).stream()
				.map(PersonalMessageDbo::getPersonalMessageConversationId).toList());

		List<Integer> receivedMessageIds = personalMessageRecipientDao.findMessageIdsReceivedBy(userId);
		if (!receivedMessageIds.isEmpty()) {
			PersonalMessageDboExample received = new PersonalMessageDboExample();
			received.createCriteria().andPersonalMessageIdIn(receivedMessageIds);
			for (PersonalMessageDbo message : get(received))
				conversations.add(message.getPersonalMessageConversationId());
		}
		return List.copyOf(conversations);
	}

	public int gcEmptyConversationsAmong(List<Integer> conversationIds) {
		PersonalMessageDboExample stillHoldingMessages = new PersonalMessageDboExample();
		stillHoldingMessages.createCriteria().andPersonalMessageConversationIdIn(conversationIds);
		Set<Integer> nonEmpty = get(stillHoldingMessages).stream()
				.map(PersonalMessageDbo::getPersonalMessageConversationId).collect(Collectors.toSet());
		List<Integer> emptied = conversationIds.stream().filter(id -> !nonEmpty.contains(id)).toList();
		if (emptied.isEmpty())
			return 0;
		return personalMessageConversationDao.deleteByIds(emptied);
	}

	public int scrubSentPersonalMessages(Integer userId) {
		PersonalMessageDbo scrubbed = new PersonalMessageDbo();
		scrubbed.setSenderName(DELETED_NAME);
		PersonalMessageDboExample sentByUser = new PersonalMessageDboExample();
		sentByUser.createCriteria().andSenderUserIdEqualTo(userId);
		return updateWhereSettingColumns(scrubbed, Set.of("sender_user_id", "sender_name"), sentByUser);
	}
}
