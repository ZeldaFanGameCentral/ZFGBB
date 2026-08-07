package com.zfgc.zfgbb.dao.forum;

import java.util.List;

import com.zfgc.zfgbb.dao.IdentityDao;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dbo.ThreadDbo;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import com.zfgc.zfgbb.mappers.ThreadDboMapper;
import com.zfgc.zfgbb.mappers.custom.ForumLockMapper;

@Repository
public class ThreadDao extends IdentityDao<ThreadDbo, ThreadDboExample> {

	private final ForumLockMapper forumLockMapper;

	public ThreadDao(ThreadDboMapper mapper, ForumLockMapper forumLockMapper) {
		super(mapper);
		this.forumLockMapper = forumLockMapper;
	}

	public void lockForUpdate(Integer threadId) {
		forumLockMapper.lockThread(threadId);
	}

	public List<Integer> lockForUpdate(List<Integer> threadIds) {
		return forumLockMapper.lockThreads(threadIds);
	}
}
