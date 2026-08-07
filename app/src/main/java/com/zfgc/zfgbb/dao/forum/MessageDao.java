package com.zfgc.zfgbb.dao.forum;

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
}
