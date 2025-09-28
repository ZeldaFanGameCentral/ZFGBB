package com.zfgc.zfgbb.mapstruct.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.zfgc.zfgbb.dbo.UserBioInfoDbo;
import com.zfgc.zfgbb.model.users.UserBioInfo;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface UserBioInfoMap {

	@Mapping(target="avatar", ignore=true)
	@Mapping(target="id", source="userId")
	@Mapping(target="signatureParsed", ignore=true)
	@Mapping(target="dateFormat", ignore=true)
	UserBioInfo toModel(UserBioInfoDbo dbo);
	
	@Mapping(target="migrationHash", ignore=true)
	UserBioInfoDbo toDbo(UserBioInfo model);
	
}
