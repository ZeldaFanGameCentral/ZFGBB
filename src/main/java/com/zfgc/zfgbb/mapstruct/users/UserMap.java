package com.zfgc.zfgbb.mapstruct.users;

import org.mapstruct.Mapper;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.UserBioInfoDbo;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.model.User;
import org.mapstruct.Mapping;

@Mapper(config=BBMapperConfig.class)
public interface UserMap {

	@Mapping(target="userId", source="dbo.userId")
	@Mapping(target="createdTs", source="dbo.createdTs")
	@Mapping(target="updatedTs", source="dbo.updatedTs")
	User toModel(UserDbo dbo, UserBioInfoDbo bioInfo);
}
