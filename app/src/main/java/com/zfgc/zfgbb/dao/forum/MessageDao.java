package com.zfgc.zfgbb.dao.forum;

import java.util.Set;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import com.zfgc.zfgbb.mappers.MessageDboMapper;
import com.zfgc.zfgbb.mappers.custom.ForumLockMapper;
import com.zfgc.zfgbb.mappers.custom.MessagePostCountMapper;

@Repository
public class MessageDao extends IdentityDao<MessageDbo, MessageDboExample> {

	private final MessagePostCountMapper postCountMapper;

	private final ForumLockMapper forumLockMapper;

	public MessageDao(MessageDboMapper mapper, MessagePostCountMapper postCountMapper,
			ForumLockMapper forumLockMapper) {
		super(mapper);
		this.postCountMapper = postCountMapper;
		this.forumLockMapper = forumLockMapper;
	}

	public Integer maxPostInThread(Integer threadId) {
		return forumLockMapper.maxPostInThread(threadId);
	}

	public List<MessagePostCountMapper.OwnerPostCount> postCountsByOwnerWithinBoards(
			List<Integer> ownerIds, List<Integer> boardIds) {
		return postCountMapper.postCountsByOwnerWithinBoards(ownerIds, boardIds);
	}

	public List<MessagePostCountMapper.ThreadPostCount> postCountsByThreadIds(List<Integer> threadIds) {
		return postCountMapper.postCountsByThreadIds(threadIds);
	}

	public List<MessagePostCountMapper.LatestMessageUser> latestMessageUsersByThreadIds(
			List<Integer> threadIds) {
		return postCountMapper.latestMessageUsersByThreadIds(threadIds);
	}

	public int countOwnedMessages(Integer userId) {
		MessageDboExample owned = new MessageDboExample();
		owned.createCriteria().andOwnerIdEqualTo(userId);
		return (int) count(owned);
	}

	public int deleteMessagesByIds(List<Integer> messageIds) {
		MessageDboExample byIds = new MessageDboExample();
		byIds.createCriteria().andMessageIdIn(messageIds);
		return deleteWhere(byIds);
	}

	public List<Integer> findOwnedMessageIds(Integer userId, int limit) {
		MessageDboExample owned = new MessageDboExample();
		owned.createCriteria().andOwnerIdEqualTo(userId);
		owned.setOrderByClause("message_id");
		owned.setLimit(limit);
		return get(owned).stream().map(MessageDbo::getMessageId).toList();
	}

	public List<Integer> findThreadIdsForMessages(List<Integer> messageIds) {
		MessageDboExample byIds = new MessageDboExample();
		byIds.createCriteria().andMessageIdIn(messageIds);
		return get(byIds).stream().map(MessageDbo::getThreadId).distinct().toList();
	}

	public int reassignAndScrubMessages(List<Integer> messageIds, Integer sentinelId) {
		MessageDbo reassigned = new MessageDbo();
		reassigned.setOwnerId(sentinelId);
		MessageDboExample byIds = new MessageDboExample();
		byIds.createCriteria().andMessageIdIn(messageIds);
		return updateWhereSettingColumns(reassigned, Set.of("owner_id", "migration_hash"), byIds);
	}

	public int resequencePostInThread(List<Integer> threadIds) {
		int resequenced = 0;
		for (Integer threadId : threadIds) {
			MessageDboExample inThread = new MessageDboExample();
			inThread.createCriteria().andThreadIdEqualTo(threadId);
			inThread.setOrderByClause("post_in_thread, message_id");
			int position = 1;
			for (MessageDbo message : get(inThread)) {
				MessageDbo renumbered = new MessageDbo();
				renumbered.setMessageId(message.getMessageId());
				renumbered.setPostInThread(position++);
				resequenced += updateSelective(renumbered);
			}
		}
		return resequenced;
	}

	public List<Integer> findThreadIdsForMessagesOn(List<Integer> threadIds) {
		MessageDboExample onThreads = new MessageDboExample();
		onThreads.createCriteria().andThreadIdIn(threadIds);
		return get(onThreads).stream().map(MessageDbo::getThreadId).distinct().toList();
	}
}
