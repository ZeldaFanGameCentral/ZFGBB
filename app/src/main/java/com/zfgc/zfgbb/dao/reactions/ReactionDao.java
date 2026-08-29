package com.zfgc.zfgbb.dao.reactions;

import java.util.Set;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.ReactionDbo;
import com.zfgc.zfgbb.dbo.ReactionDboExample;
import com.zfgc.zfgbb.mappers.ReactionDboMapper;

@Repository
public class ReactionDao extends IdentityDao<ReactionDbo, ReactionDboExample> {

	public ReactionDao(ReactionDboMapper mapper) {
		super(mapper);
	}

	public int upsert(String reactableType, Integer reactableId, Integer reactorUserId, Integer reactionTypeId) {
		ReactionDboExample target = targetExample(reactableType, reactableId, reactorUserId);
		if (getOne(target).isPresent())
			return updateType(target, reactionTypeId);
		ReactionDbo row = new ReactionDbo();
		row.setReactableType(reactableType);
		row.setReactableId(reactableId);
		row.setReactorUserId(reactorUserId);
		row.setReactionTypeId(reactionTypeId);
		try {
			insertSelective(row);
			return 1;
		} catch (DuplicateKeyException lostInsertRace) {
			if (getOne(target).isEmpty())
				return 0;
			return updateType(target, reactionTypeId);
		}
	}

	private ReactionDboExample targetExample(String reactableType, Integer reactableId, Integer reactorUserId) {
		ReactionDboExample target = new ReactionDboExample();
		target.createCriteria()
				.andReactableTypeEqualTo(reactableType)
				.andReactableIdEqualTo(reactableId)
				.andReactorUserIdEqualTo(reactorUserId);
		return target;
	}

	private int updateType(ReactionDboExample target, Integer reactionTypeId) {
		ReactionDbo change = new ReactionDbo();
		change.setReactionTypeId(reactionTypeId);
		return updateWhere(change, target);
	}

	public int scrubGivenReactions(Integer userId) {
		ReactionDbo scrubbed = new ReactionDbo();
		ReactionDboExample givenByUser = new ReactionDboExample();
		givenByUser.createCriteria().andReactorUserIdEqualTo(userId);
		return updateWhereSettingColumns(scrubbed, Set.of("reactor_user_id", "comment"), givenByUser);
	}
}
