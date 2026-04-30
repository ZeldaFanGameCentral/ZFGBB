package com.zfgc.zfgbb.mapstruct.users;

import org.mapstruct.Mapper;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.AvatarDbo;
import com.zfgc.zfgbb.model.users.Avatar;

@Mapper(config=BBMapperConfig.class)
public interface AvatarMap {

	Avatar toModel(AvatarDbo dbo);
}
