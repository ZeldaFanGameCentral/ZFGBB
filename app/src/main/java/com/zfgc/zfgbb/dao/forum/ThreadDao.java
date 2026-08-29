package com.zfgc.zfgbb.dao.forum;

import java.util.Set;
import java.util.List;

import com.zfgc.zfgbb.dao.forum.MessageDao;
import com.zfgc.zfgbb.dao.IdentityDao;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import com.zfgc.zfgbb.mappers.ThreadDboMapper;
import com.zfgc.zfgbb.mappers.custom.ForumLockMapper;

@Repository
public class ThreadDao extends IdentityDao<ThreadDbo, ThreadDboExample> {

	private final MessageDao messageDao;

	private final ForumLockMapper forumLockMapper;

	public ThreadDao(ThreadDboMapper mapper, ForumLockMapper forumLockMapper,
			MessageDao messageDao) {
		super(mapper);
		this.forumLockMapper = forumLockMapper;
		this.messageDao = messageDao;
	}

	public List<Integer> lockForUpdate(List<Integer> threadIds) {
		return forumLockMapper.lockThreads(threadIds);
	}

	public int countOwnedThreads(Integer userId) {
		ThreadDboExample owned = new ThreadDboExample();
		owned.createCriteria().andCreatedUserIdEqualTo(userId);
		return (int) count(owned);
	}

	public int deleteThreadsByIds(List<Integer> threadIds) {
		ThreadDboExample byIds = new ThreadDboExample();
		byIds.createCriteria().andThreadIdIn(threadIds);
		return deleteWhere(byIds);
	}

	public List<Integer> findEmptyThreadIdsAmong(List<Integer> threadIds) {
		Set<Integer> threadsStillHoldingMessages = Set.copyOf(messageDao.findThreadIdsForMessagesOn(threadIds));
		return threadIds.stream().filter(threadId -> !threadsStillHoldingMessages.contains(threadId)).toList();
	}

	public List<Integer> findOwnedThreadIds(Integer userId) {
		ThreadDboExample owned = new ThreadDboExample();
		owned.createCriteria().andCreatedUserIdEqualTo(userId);
		return get(owned).stream().map(ThreadDbo::getThreadId).toList();
	}

	public int reassignThreads(Integer userId, Integer sentinelId) {
		ThreadDbo reassigned = new ThreadDbo();
		reassigned.setCreatedUserId(sentinelId);
		ThreadDboExample ownedByUser = new ThreadDboExample();
		ownedByUser.createCriteria().andCreatedUserIdEqualTo(userId);
		return updateWhereSettingColumns(reassigned, Set.of("created_user_id", "migration_hash"), ownedByUser);
	}
}
