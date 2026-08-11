package com.zfgc.zfgbb.mapstruct.forum;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.PollChoiceDbo;
import com.zfgc.zfgbb.dbo.PollDbo;
import com.zfgc.zfgbb.model.forum.Poll;
import com.zfgc.zfgbb.model.forum.PollChoice;
import org.mapstruct.Mapping;

@Mapper(config=BBMapperConfig.class)
public interface PollMap {

	Poll toModel(PollDbo dbo, List<PollChoiceDbo> answers);

	@Mapping(target = "percentage", ignore = true)
	PollChoice toModel(PollChoiceDbo dbo);

	@Mapping(target = "answerText", ignore = true)
	PollDbo toDbo(Poll model);

	@AfterMapping
	default void computeChoicePercentages(@MappingTarget Poll poll) {
		if(poll.getAnswers() == null) {
			return;
		}
		int totalVotes = poll.getVotes();
		poll.getAnswers().forEach(answer ->
				answer.setPercentage(totalVotes > 0
						? answer.getVotes() * 100.0 / totalVotes : 0.0));
	}

}
