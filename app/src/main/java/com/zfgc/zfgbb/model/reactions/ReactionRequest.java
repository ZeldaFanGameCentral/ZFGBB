package com.zfgc.zfgbb.model.reactions;

import lombok.Data;

@Data
public class ReactionRequest {
	private String reactableType;
	private Integer reactableId;
	private Integer reactionTypeId;
}
