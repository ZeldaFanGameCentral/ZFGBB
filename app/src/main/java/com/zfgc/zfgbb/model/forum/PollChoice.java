package com.zfgc.zfgbb.model.forum;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zfgc.zfgbb.model.BaseModel;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder=true)
public class PollChoice extends BaseModel {

	private Integer pollChoiceId;
	private Integer pollId;
	private String choiceText;
	private Boolean activeFlag;
	private Integer votes;
	@JsonIgnore
	private String migrationHash;
	private Integer seqno;
	private Double percentage;
	
	@Override
	public Integer getId() {
		return pollChoiceId;
	}

	@Override
	public void setId(Integer id) {
		pollChoiceId = id;
	}

}
