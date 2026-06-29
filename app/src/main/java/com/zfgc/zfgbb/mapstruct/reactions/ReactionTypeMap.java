package com.zfgc.zfgbb.mapstruct.reactions;

import org.mapstruct.Mapper;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.ReactionTypeDbo;
import com.zfgc.zfgbb.model.reactions.ReactionType;

@Mapper(config=BBMapperConfig.class)
public interface ReactionTypeMap {
	ReactionType toModel(ReactionTypeDbo dbo);
}
