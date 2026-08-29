package com.zfgc.zfgbb.mapstruct.users;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.UserReactionSummaryViewDbo;
import com.zfgc.zfgbb.model.users.ReactionSummary;

@Mapper(config = BBMapperConfig.class)
public interface ReactionSummaryMap {

	ReactionSummary toModel(UserReactionSummaryViewDbo dbo);

	@AfterMapping
	default void zeroTheAbsentCounts(@MappingTarget ReactionSummary summary) {
		if (summary.getReputationPoints() == null)
			summary.setReputationPoints(0);
		if (summary.getPositiveCount() == null)
			summary.setPositiveCount(0);
		if (summary.getNegativeCount() == null)
			summary.setNegativeCount(0);
		if (summary.getReactionCount() == null)
			summary.setReactionCount(0);
	}
}
