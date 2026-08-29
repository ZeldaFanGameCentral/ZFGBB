package com.zfgc.zfgbb.dao.forum;

import java.util.Set;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.PollDbo;
import com.zfgc.zfgbb.dbo.PollDboExample;
import com.zfgc.zfgbb.mappers.PollDboMapper;

@Repository
public class PollDao extends IdentityDao<PollDbo, PollDboExample> {

	public PollDao(PollDboMapper mapper) {
		super(mapper);
	}

	public List<Integer> findOwnedPollIds(Integer userId) {
		PollDboExample owned = new PollDboExample();
		owned.createCriteria().andCreatedUserIdEqualTo(userId);
		return get(owned).stream().map(PollDbo::getPollId).toList();
	}

	public List<Integer> findPollIdsOnThreads(List<Integer> threadIds) {
		PollDboExample onThreads = new PollDboExample();
		onThreads.createCriteria().andThreadIdIn(threadIds);
		return get(onThreads).stream().map(PollDbo::getPollId).toList();
	}

	public int reassignPolls(Integer userId, Integer sentinelId) {
		PollDbo reassigned = new PollDbo();
		reassigned.setCreatedUserId(sentinelId);
		PollDboExample ownedByUser = new PollDboExample();
		ownedByUser.createCriteria().andCreatedUserIdEqualTo(userId);
		return updateWhereSettingColumns(reassigned, Set.of("created_user_id", "migration_hash"), ownedByUser);
	}
}
