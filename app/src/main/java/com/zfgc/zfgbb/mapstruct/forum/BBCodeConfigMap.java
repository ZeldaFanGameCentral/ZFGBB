package com.zfgc.zfgbb.mapstruct.forum;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.BBCodeConfigDbo;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;

@Mapper(config=BBMapperConfig.class, builder=@Builder(disableBuilder=true))
public interface BBCodeConfigMap {

	@Mapping(target="id", ignore=true)
	@Mapping(target="allAttributeNamesAsString", ignore=true)
	@Mapping(target="attributeConfig", ignore=true)
	@Mapping(target="valuePolicyByAttributeName", ignore=true)
	BBCodeConfig toModel(BBCodeConfigDbo dbo);

}
