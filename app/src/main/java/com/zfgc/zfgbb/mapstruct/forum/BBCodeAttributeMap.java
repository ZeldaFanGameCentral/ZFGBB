package com.zfgc.zfgbb.mapstruct.forum;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.BBCodeAttributeDbo;
import com.zfgc.zfgbb.model.forum.BBCodeAttribute;

@Mapper(config=BBMapperConfig.class, builder=@Builder(disableBuilder=true))
public interface BBCodeAttributeMap {

	@Mapping(target="id", ignore=true)
	@Mapping(target="dataType", ignore=true)
	BBCodeAttribute toModel(BBCodeAttributeDbo dbo);

}
