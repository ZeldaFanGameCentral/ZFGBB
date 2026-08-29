package com.zfgc.zfgbb.mapstruct.cms;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.model.cms.WikiPageRef;

@Mapper(config=BBMapperConfig.class, builder=@Builder(disableBuilder=true))
public interface WikiPageRefMap {
	WikiPageRef toRef(WikiPageDbo dbo);
}
