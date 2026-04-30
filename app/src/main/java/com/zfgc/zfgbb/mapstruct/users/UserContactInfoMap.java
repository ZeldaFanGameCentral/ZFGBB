package com.zfgc.zfgbb.mapstruct.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.EmailAddressDbo;
import com.zfgc.zfgbb.dbo.UserContactInfoDbo;
import com.zfgc.zfgbb.model.users.UserContactInfo;

@Mapper(config=BBMapperConfig.class)
public interface UserContactInfoMap {

	@Mapping(target="emailAddress", source="emailAddress")
	@Mapping(target="createdTs", source="dbo.createdTs")
	@Mapping(target="updatedTs", source="dbo.updatedTs")
	UserContactInfo toModel(UserContactInfoDbo dbo, EmailAddressDbo emailAddress);
	
}
