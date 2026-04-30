package com.zfgc.zfgbb.mapstruct.users;

import org.mapstruct.Mapper;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.EmailAddressDbo;
import com.zfgc.zfgbb.model.users.EmailAddress;

@Mapper(config=BBMapperConfig.class)
public interface EmailAddressMap {

	EmailAddress toModel(EmailAddressDbo dbo);
}
