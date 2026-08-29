package com.zfgc.zfgbb.dao.forum;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.forum.PollDao;
import com.zfgc.zfgbb.dao.forum.PollChoiceDao;
import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.PollChoiceDbo;
import com.zfgc.zfgbb.dbo.UserPollChoiceDbo;
import com.zfgc.zfgbb.dbo.UserPollChoiceDboExample;
import com.zfgc.zfgbb.mappers.UserPollChoiceDboMapper;

@Repository
public class UserPollChoiceDao extends IdentityDao<UserPollChoiceDbo, UserPollChoiceDboExample> {

	private final PollDao pollDao;

	private final PollChoiceDao pollChoiceDao;

	public UserPollChoiceDao(UserPollChoiceDboMapper mapper,
			PollChoiceDao pollChoiceDao,
			PollDao pollDao) {
		super(mapper);
		this.pollChoiceDao = pollChoiceDao;
		this.pollDao = pollDao;
	}

	public int deleteUserPollVotes(Integer userId) {
		return deleteVotesForChoices(pollChoiceDao.findChoiceIdsForPolls(pollDao.findOwnedPollIds(userId)));
	}

	public int deleteVotesForPollsOnThreads(List<Integer> threadIds) {
		return deleteVotesForChoices(pollChoiceDao.findChoiceIdsForPolls(pollDao.findPollIdsOnThreads(threadIds)));
	}

	public List<Integer> findVotedPollChoiceIds(Integer userId) {
		UserPollChoiceDboExample voted = new UserPollChoiceDboExample();
		voted.createCriteria().andUserIdEqualTo(userId).andPollChoiceIdIsNotNull();
		return get(voted).stream().map(UserPollChoiceDbo::getPollChoiceId).distinct().toList();
	}

	private int deleteVotesForChoices(List<Integer> pollChoiceIds) {
		if (pollChoiceIds.isEmpty())
			return 0;
		UserPollChoiceDboExample byChoices = new UserPollChoiceDboExample();
		byChoices.createCriteria().andPollChoiceIdIn(pollChoiceIds);
		return deleteWhere(byChoices);
	}

	public int recountPollChoiceVotes(List<Integer> pollChoiceIds) {
		int recounted = 0;
		for (Integer pollChoiceId : pollChoiceIds) {
			UserPollChoiceDboExample votesForChoice = new UserPollChoiceDboExample();
			votesForChoice.createCriteria().andPollChoiceIdEqualTo(pollChoiceId);
			PollChoiceDbo tallied = new PollChoiceDbo();
			tallied.setPollChoiceId(pollChoiceId);
			tallied.setVotes((int) count(votesForChoice));
			recounted += pollChoiceDao.updateSelective(tallied);
		}
		return recounted;
	}
}
