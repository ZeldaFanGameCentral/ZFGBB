package com.zfgc.zfgbb.model.users;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zfgc.zfgbb.model.BaseModel;

import lombok.Data;

@Data
public class UserBioInfo extends BaseModel {

	@JsonIgnore
	private Integer userId;
	private String customTitle;
	private String personalText;
	private String signature;
	private String signatureParsed;
	private LocalDate birthDate;
	private Integer genderId;
	
	private Avatar avatar;

	@Override
	public Integer getId() {
		return userId;
	}

	@Override
	public void setId(Integer id) {
		userId = id;
	}
}