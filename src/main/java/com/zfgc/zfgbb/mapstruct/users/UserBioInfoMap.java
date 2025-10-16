package com.zfgc.zfgbb.mapstruct.users;

import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.UserBioInfoDbo;
import com.zfgc.zfgbb.model.users.UserBioInfo;

@Mapper(config=BBMapperConfig.class, imports=DateTimeFormatter.class)
public interface UserBioInfoMap {

	@Mapping(target = "postCount", ignore = true)
	@Mapping(target="avatar", ignore=true)
	@Mapping(target="id", source="pkId")
	@Mapping(target="signatureParsed", ignore=true)
	@Mapping(target="dateFormat", ignore=true)
	@Mapping(target="createdTs", source="createdTime")
	@Mapping(target="updatedTs", source="updatedTime")
	//todo: move these to the service so they can be formatted by the user's actual preferred format
	@Mapping(target="birthDate", expression="java(dbo.getBirthDate().format(DateTimeFormatter.ofPattern(\"MM/dd/yyyy\")))")
	@Mapping(target="dateRegistered", expression="java(dbo.getDateRegistered().format(DateTimeFormatter.ofPattern(\"MM/dd/yyyy\")))")
	UserBioInfo toModel(UserBioInfoDbo dbo);
	
	@Mapping(target="migrationHash", ignore=true)
	@Mapping(target="avatarId", source="avatar.avatarId")
	UserBioInfoDbo toDbo(UserBioInfo model);
	
}
