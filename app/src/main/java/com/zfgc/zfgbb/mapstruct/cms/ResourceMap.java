package com.zfgc.zfgbb.mapstruct.cms;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.ResourceViewDbo;
import com.zfgc.zfgbb.model.cms.Resource;

@Mapper(config=BBMapperConfig.class, builder=@Builder(disableBuilder=true))
public interface ResourceMap {
	@Mapping(target="id", ignore=true)
	@Mapping(target="resourceId", ignore=true)
	@Mapping(target="downloadFilename", ignore=true)
	@Mapping(target="author", ignore=true)
	@Mapping(target="page", ignore=true)
	Resource toModel(ResourceViewDbo dbo);
}
