package com.zfgc.zfgbb.mapstruct.forum;

import java.util.List;

import org.mapstruct.Mapper;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.PollChoiceDbo;
import com.zfgc.zfgbb.dbo.PollDbo;
import com.zfgc.zfgbb.model.forum.Poll;
import org.mapstruct.Mapping;

@Mapper(config=BBMapperConfig.class)
public interface PollMap {

	Poll toModel(PollDbo dbo, List<PollChoiceDbo> answers);
	@Mapping(target = "answerText", ignore = true)
	PollDbo toDbo(Poll model);
	
}
