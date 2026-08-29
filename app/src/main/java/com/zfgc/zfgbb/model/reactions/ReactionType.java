package com.zfgc.zfgbb.model.reactions;

import lombok.Data;

@Data
public class ReactionType {
	private Integer reactionTypeId;
	private String code;
	private String label;
	private String icon;
	private Integer points;
	private Integer ordinal;
}
