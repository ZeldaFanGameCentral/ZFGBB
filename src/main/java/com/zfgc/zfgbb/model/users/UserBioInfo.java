package com.zfgc.zfgbb.model.users;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zfgc.zfgbb.model.BaseModel;
import com.zfgc.zfgbb.model.forum.BBLocalDate;

import lombok.Data;

@Data
public class UserBioInfo extends BaseModel {

	@JsonIgnore
	private Integer userId;
	private String customTitle;
	private String personalText;
	private String signature;
	private String signatureParsed;
	private String location;
	
	private String birthDate;
	private String lastLogin;
	private String dateRegistered;
	private String dateFormat;
	private String timeFormat;
	private Integer genderId;
	private Integer karmaBad;
	private Integer karmaGood;
	private String websiteTitle;
	private String websiteUrl;
	private String realName;
	private Boolean hideEmailFlag;
	private Boolean hideOnlineStatus;
	private Integer postCount;
	
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