package com.zfgc.zfgbb.migrator.converters;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.zfgc.zfgbb.dbo.PersonalMessageConversationDbo;
import com.zfgc.zfgbb.dbo.PersonalMessageDbo;
import com.zfgc.zfgbb.dbo.PersonalMessageRecipientDbo;
import com.zfgc.zfgbb.dbo.PersonalMessageRecipientDboExample;
import com.zfgc.zfgbb.mappers.PersonalMessageConversationDboMapper;
import com.zfgc.zfgbb.mappers.PersonalMessageDboMapper;
import com.zfgc.zfgbb.mappers.PersonalMessageRecipientDboMapper;
import com.zfgc.zfgbb.migrator.SmfTimes;
import com.zfgc.zfgbb.migrator.jobs.JobContextHolder;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.jobs.LegacyEntityType;
import com.zfgc.zfgbb.migrator.jobs.MigratorIdMapService;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFPersonalMessageDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFPersonalMessageDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFPmRecipientDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFPmRecipientDbExample;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFPersonalMessageDbMapper;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFPmRecipientDbMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PersonalMessagesConverter extends AbstractConverter<Void> {

	private final SMFPersonalMessageDbMapper smfPmMapper;
	private final SMFPmRecipientDbMapper smfRecipientMapper;
	private final PersonalMessageConversationDboMapper conversationMapper;
	private final PersonalMessageDboMapper pmMapper;
	private final PersonalMessageRecipientDboMapper recipientMapper;
	private final MigratorIdMapService idMap;

	@Override
	public JobType getType() {
		return JobType.PERSONAL_MESSAGES;
	}

	private Map<Integer, Integer> userMap;
	private Map<Integer, Integer> conversationMap;
	private Map<Integer, Integer> pmMap;

	@Override
	@Transactional
	public Void convertToZfgbb() {
		List<SMFPersonalMessageDb> pms = smfPmMapper.selectByExampleWithBLOBs(new SMFPersonalMessageDbExample());
		pms.sort(Comparator.comparing(SMFPersonalMessageDb::getIdPm));

		Map<Integer, List<SMFPmRecipientDb>> recipientsByPm = smfRecipientMapper
				.selectByExample(new SMFPmRecipientDbExample()).stream()
				.collect(Collectors.groupingBy(SMFPmRecipientDb::getIdPm));

		userMap = idMap.getAllForType(LegacyEntityType.USER);
		conversationMap = new HashMap<>(idMap.getAllForType(LegacyEntityType.PM_CONVERSATION));
		pmMap = new HashMap<>(idMap.getAllForType(LegacyEntityType.PERSONAL_MESSAGE));

		for (SMFPersonalMessageDb smfPm : pms) {
			Cancellable.check();
			convertOne(smfPm, recipientsByPm.getOrDefault(smfPm.getIdPm(), List.of()));
		}
		return null;
	}

	private void convertOne(SMFPersonalMessageDb smfPm, List<SMFPmRecipientDb> smfRecipients) {
		Integer conversationId = ensureConversation(smfPm);

		PersonalMessageDbo pm = new PersonalMessageDbo();
		pm.setPersonalMessageConversationId(conversationId);
		Integer smfSender = smfPm.getIdMemberFrom();
		pm.setSenderUserId(smfSender == null || smfSender == 0
				? null
				: userMap.get(smfSender));
		pm.setSenderName(smfPm.getFromName());
		pm.setBody(HtmlUtils.htmlUnescape(smfPm.getBody()));
		pm.setSentTs(SmfTimes.fromEpochSeconds(smfPm.getMsgtime()));
		pm.setDeletedBySender(Boolean.TRUE.equals(smfPm.getDeletedBySender()));

		String recipientSignature = smfRecipients.stream()
				.map(r -> r.getIdMember() + ":" + r.getBcc() + ":" + r.getIsRead() + ":" + r.getDeleted())
				.collect(Collectors.joining(","));
		pm.setMigrationHash(MigrationHasher.hash(smfPm.getIdPm().toString()
				+ smfPm.getIdMemberFrom()
				+ smfPm.getMsgtime()
				+ smfPm.getSubject()
				+ pm.getBody()
				+ pm.getDeletedBySender()
				+ recipientSignature));

		Integer existingId = pmMap.get(smfPm.getIdPm());
		if (existingId == null) {
			pmMapper.insert(pm);
			idMap.record(LegacyEntityType.PERSONAL_MESSAGE, smfPm.getIdPm(), pm.getPersonalMessageId());
			pmMap.put(smfPm.getIdPm(), pm.getPersonalMessageId());
			insertRecipients(pm.getPersonalMessageId(), smfRecipients);
			return;
		}

		PersonalMessageDbo existing = pmMapper.selectByPrimaryKey(existingId);
		if (existing == null) {
			pmMapper.insert(pm);
			idMap.record(LegacyEntityType.PERSONAL_MESSAGE, smfPm.getIdPm(), pm.getPersonalMessageId());
			pmMap.put(smfPm.getIdPm(), pm.getPersonalMessageId());
			insertRecipients(pm.getPersonalMessageId(), smfRecipients);
		} else if (JobContextHolder.isForce()
				|| !Objects.equals(existing.getMigrationHash(), pm.getMigrationHash())) {
			pm.setPersonalMessageId(existingId);
			pmMapper.updateByPrimaryKey(pm);
			PersonalMessageRecipientDboExample ex = new PersonalMessageRecipientDboExample();
			ex.createCriteria().andPersonalMessageIdEqualTo(existingId);
			recipientMapper.deleteByExample(ex);
			insertRecipients(existingId, smfRecipients);
		}
	}

	private Integer ensureConversation(SMFPersonalMessageDb smfPm) {
		Integer head = smfPm.getIdPmHead();
		Integer conversationKey = head == null || head == 0 ? smfPm.getIdPm() : head;
		Integer existing = conversationMap.get(conversationKey);
		if (existing != null) {
			return existing;
		}
		PersonalMessageConversationDbo conversation = new PersonalMessageConversationDbo();
		conversation.setSubject(conversationSubject(smfPm.getSubject()));
		conversation.setStartedTs(SmfTimes.fromEpochSeconds(smfPm.getMsgtime()));
		conversation.setMigrationHash(MigrationHasher.hash("pmconv" + conversationKey));
		conversationMapper.insert(conversation);
		idMap.record(LegacyEntityType.PM_CONVERSATION, conversationKey,
				conversation.getPersonalMessageConversationId());
		conversationMap.put(conversationKey, conversation.getPersonalMessageConversationId());
		return conversation.getPersonalMessageConversationId();
	}

	private void insertRecipients(Integer personalMessageId, List<SMFPmRecipientDb> smfRecipients) {
		for (SMFPmRecipientDb smfRecipient : smfRecipients) {
			Integer recipientUserId = userMap.get(smfRecipient.getIdMember());
			if (recipientUserId == null) {
				continue;
			}
			PersonalMessageRecipientDbo recipient = new PersonalMessageRecipientDbo();
			recipient.setPersonalMessageId(personalMessageId);
			recipient.setRecipientUserId(recipientUserId);
			recipient.setBcc(Boolean.TRUE.equals(smfRecipient.getBcc()));
			recipient.setReadFlag(isReadBitSet(smfRecipient.getIsRead()));
			recipient.setDeletedFlag(Boolean.TRUE.equals(smfRecipient.getDeleted()));
			recipient.setMigrationHash(MigrationHasher.hash("pmrcpt" + personalMessageId
					+ "u" + recipientUserId));
			recipientMapper.insert(recipient);
		}
	}

	private boolean isReadBitSet(Integer readBitfield) {
		return readBitfield != null && (readBitfield & 1) == 1;
	}

	private String conversationSubject(String subject) {
		if (subject == null) {
			return null;
		}
		String unescaped = HtmlUtils.htmlUnescape(subject);
		return unescaped.replaceFirst("^(?i)re:\\s*", "");
	}
}
