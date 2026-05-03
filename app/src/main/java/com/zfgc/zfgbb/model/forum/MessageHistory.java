package com.zfgc.zfgbb.model.forum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zfgc.zfgbb.model.BaseModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
public class MessageHistory extends BaseModel {
	@JsonIgnore
	private Integer messageHistoryId;
	private Integer messageId;
	private String messageText;
	private String unparsedText;
	@Builder.Default
	private Boolean currentFlag = true;
	private Integer ipAddressId;
	
	@Override
	public Integer getId() {
		return messageHistoryId;
	}
	@Override
	public void setId(Integer id) {
		messageHistoryId = id;
	}
}