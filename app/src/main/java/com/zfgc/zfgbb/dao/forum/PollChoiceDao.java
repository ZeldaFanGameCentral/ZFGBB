package com.zfgc.zfgbb.dao.forum;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.forum.PollDao;
import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.PollChoiceDbo;
import com.zfgc.zfgbb.dbo.PollChoiceDboExample;
import com.zfgc.zfgbb.mappers.PollChoiceDboMapper;

@Repository
public class PollChoiceDao extends IdentityDao<PollChoiceDbo, PollChoiceDboExample> {

	private final PollDao pollDao;

	public PollChoiceDao(PollChoiceDboMapper mapper,
			PollDao pollDao) {
		super(mapper);
		this.pollDao = pollDao;
	}

	public int deleteChoicesForPollsOnThreads(List<Integer> threadIds) {
		List<Integer> pollIds = pollDao.findPollIdsOnThreads(threadIds);
		if (pollIds.isEmpty())
			return 0;
		PollChoiceDboExample onThreadPolls = new PollChoiceDboExample();
		onThreadPolls.createCriteria().andPollIdIn(pollIds);
		return deleteWhere(onThreadPolls);
	}

	public int deleteUserPollChoices(Integer userId) {
		List<Integer> ownedPollIds = pollDao.findOwnedPollIds(userId);
		if (ownedPollIds.isEmpty())
			return 0;
		PollChoiceDboExample onOwnedPolls = new PollChoiceDboExample();
		onOwnedPolls.createCriteria().andPollIdIn(ownedPollIds);
		return deleteWhere(onOwnedPolls);
	}

	public List<Integer> findChoiceIdsForPolls(List<Integer> pollIds) {
		if (pollIds.isEmpty())
			return List.of();
		PollChoiceDboExample onPolls = new PollChoiceDboExample();
		onPolls.createCriteria().andPollIdIn(pollIds);
		return get(onPolls).stream().map(PollChoiceDbo::getPollChoiceId).toList();
	}
}
