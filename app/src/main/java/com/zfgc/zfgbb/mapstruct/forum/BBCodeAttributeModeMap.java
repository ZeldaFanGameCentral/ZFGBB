package com.zfgc.zfgbb.mapstruct.forum;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDbo;
import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;

@Mapper(config=BBMapperConfig.class, builder=@Builder(disableBuilder=true))
public interface BBCodeAttributeModeMap {

	@Mapping(target="id", ignore=true)
	@Mapping(target="attributes", ignore=true)
	BBCodeAttributeMode toModel(BBCodeAttributeModeDbo dbo);

}
