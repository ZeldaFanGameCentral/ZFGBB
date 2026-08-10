package com.zfgc.zfgbb.mapstruct.cms;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDbo;
import com.zfgc.zfgbb.model.cms.WikiRevision;

@Mapper(config=BBMapperConfig.class, builder=@Builder(disableBuilder=true))
public interface WikiRevisionMap {

	@Mapping(target="revisionId", source="wikiPageRevisionId")
	WikiRevision toModel(WikiPageRevisionDbo dbo);
}
