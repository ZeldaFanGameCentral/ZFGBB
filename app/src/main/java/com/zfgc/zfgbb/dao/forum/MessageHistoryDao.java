package com.zfgc.zfgbb.dao.forum;

import java.util.Set;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.meta.MigratorAttachmentRefRewriteDao;
import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import com.zfgc.zfgbb.mappers.MessageHistoryDboMapper;

@Repository
public class MessageHistoryDao extends IdentityDao<MessageHistoryDbo, MessageHistoryDboExample> {

	private final MigratorAttachmentRefRewriteDao migratorAttachmentRefRewriteDao;

	public MessageHistoryDao(MessageHistoryDboMapper mapper,
			MigratorAttachmentRefRewriteDao migratorAttachmentRefRewriteDao) {
		super(mapper);
		this.migratorAttachmentRefRewriteDao = migratorAttachmentRefRewriteDao;
	}

	public void clearCurrentFlag(Integer messageId) {
		MessageHistoryDbo replacement = new MessageHistoryDbo();
		replacement.setCurrentFlag(false);
		MessageHistoryDboExample current = new MessageHistoryDboExample();
		current.createCriteria().andMessageIdEqualTo(messageId).andCurrentFlagEqualTo(true);
		updateWhere(replacement, current);
	}

	public int deleteAttachmentRefRewritesForMessages(List<Integer> messageIds) {
		MessageHistoryDboExample byMessages = new MessageHistoryDboExample();
		byMessages.createCriteria().andMessageIdIn(messageIds);
		List<Integer> historyIds = get(byMessages).stream()
				.map(MessageHistoryDbo::getMessageHistoryId).toList();
		if (historyIds.isEmpty())
			return 0;
		return migratorAttachmentRefRewriteDao.deleteForMessageHistories(historyIds);
	}

	public int deleteHistoryForMessages(List<Integer> messageIds) {
		MessageHistoryDboExample byMessages = new MessageHistoryDboExample();
		byMessages.createCriteria().andMessageIdIn(messageIds);
		return deleteWhere(byMessages);
	}

	public List<Integer> findHistoryIpAddressIds(List<Integer> messageIds) {
		MessageHistoryDboExample withIps = new MessageHistoryDboExample();
		withIps.createCriteria().andIpAddressIdIsNotNull().andMessageIdIn(messageIds);
		return get(withIps).stream().map(MessageHistoryDbo::getIpAddressId).distinct().toList();
	}

	public int scrubHistoryForMessages(List<Integer> messageIds) {
		MessageHistoryDbo scrubbed = new MessageHistoryDbo();
		MessageHistoryDboExample byMessages = new MessageHistoryDboExample();
		byMessages.createCriteria().andMessageIdIn(messageIds);
		return updateWhereSettingColumns(scrubbed, Set.of("ip_address_id", "migration_hash"), byMessages);
	}

	public List<Integer> findReferencedIpAddressIdsAmong(List<Integer> ipAddressIds) {
		MessageHistoryDboExample referencing = new MessageHistoryDboExample();
		referencing.createCriteria().andIpAddressIdIn(ipAddressIds);
		return get(referencing).stream().map(MessageHistoryDbo::getIpAddressId).distinct().toList();
	}
}
