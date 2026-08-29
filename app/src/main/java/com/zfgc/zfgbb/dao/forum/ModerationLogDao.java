package com.zfgc.zfgbb.dao.forum;

import java.util.Set;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.ModerationLogDbo;
import com.zfgc.zfgbb.dbo.ModerationLogDboExample;
import com.zfgc.zfgbb.mappers.ModerationLogDboMapper;

@Repository
public class ModerationLogDao extends IdentityDao<ModerationLogDbo, ModerationLogDboExample> {

	private static final String DELETED_NAME = "[deleted]";

	public ModerationLogDao(ModerationLogDboMapper mapper) {
		super(mapper);
	}

	public int nullModerationLogActors(Integer userId) {
		ModerationLogDbo orphaned = new ModerationLogDbo();
		ModerationLogDboExample actedByUser = new ModerationLogDboExample();
		actedByUser.createCriteria().andActorUserIdEqualTo(userId);
		return updateWhereSettingColumns(orphaned, Set.of("actor_user_id"), actedByUser);
	}

	public int scrubModerationLogTargets(Integer userId) {
		ModerationLogDbo scrubbed = new ModerationLogDbo();
		scrubbed.setTargetName(DELETED_NAME);
		ModerationLogDboExample targetingUser = new ModerationLogDboExample();
		targetingUser.createCriteria().andTargetUserIdEqualTo(userId);
		return updateWhereSettingColumns(scrubbed, Set.of("target_user_id", "target_name"), targetingUser);
	}

	public int scrubModerationLogTargetsByName(String userName) {
		ModerationLogDboExample unresolvedTargets = new ModerationLogDboExample();
		unresolvedTargets.createCriteria().andTargetUserIdIsNull().andTargetNameContains(userName);
		List<Integer> exactMatches = get(unresolvedTargets).stream()
				.filter(entry -> userName.equalsIgnoreCase(entry.getTargetName()))
				.map(ModerationLogDbo::getModerationLogId)
				.toList();
		if (exactMatches.isEmpty())
			return 0;

		ModerationLogDbo scrubbed = new ModerationLogDbo();
		scrubbed.setTargetName("[deleted]");
		ModerationLogDboExample byIds = new ModerationLogDboExample();
		byIds.createCriteria().andModerationLogIdIn(exactMatches);
		return updateWhere(scrubbed, byIds);
	}
}
