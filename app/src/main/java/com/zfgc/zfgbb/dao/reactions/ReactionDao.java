package com.zfgc.zfgbb.dao.reactions;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.ReactionDbo;
import com.zfgc.zfgbb.dbo.ReactionDboExample;
import com.zfgc.zfgbb.mappers.ReactionDboMapper;
import com.zfgc.zfgbb.mappers.custom.ProjectMergeMapper;
import com.zfgc.zfgbb.mappers.custom.ReactionUpsertMapper;

@Repository
public class ReactionDao extends IdentityDao<ReactionDbo, ReactionDboExample> {

	private final ReactionUpsertMapper reactionUpsertMapper;

	private final ProjectMergeMapper projectMergeMapper;

	public ReactionDao(ReactionDboMapper mapper, ReactionUpsertMapper reactionUpsertMapper,
			ProjectMergeMapper projectMergeMapper) {
		super(mapper);
		this.reactionUpsertMapper = reactionUpsertMapper;
		this.projectMergeMapper = projectMergeMapper;
	}

	public int upsert(String reactableType, Integer reactableId, Integer reactorUserId, Integer reactionTypeId) {
		return reactionUpsertMapper.upsertReaction(reactableType, reactableId, reactorUserId, reactionTypeId);
	}

	public int deleteDuplicateProjectReactions(Integer sourceId, Integer targetId) {
		return projectMergeMapper.deleteDuplicateProjectReactions(sourceId, targetId);
	}
}
