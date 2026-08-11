package com.zfgc.zfgbb.model.reactions;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ContentReactionSummary {
	private String reactableType;
	private Integer reactableId;
	private Integer totalPoints;
	private Integer totalCount;
	private Integer userReactionTypeId;
	private List<ReactionTally> tallies = new ArrayList<>();
}
