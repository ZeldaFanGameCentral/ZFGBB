package com.zfgc.zfgbb.mapstruct.cms;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.model.cms.WikiFileRef;

@Mapper(config=BBMapperConfig.class, builder=@Builder(disableBuilder=true))
public interface WikiFileRefMap {
	WikiFileRef toRef(ContentResourceDbo dbo);
}
