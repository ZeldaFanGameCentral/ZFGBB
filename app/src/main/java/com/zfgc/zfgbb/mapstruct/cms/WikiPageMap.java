package com.zfgc.zfgbb.mapstruct.cms;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.model.cms.WikiPage;

@Mapper(config=BBMapperConfig.class, builder=@Builder(disableBuilder=true))
public interface WikiPageMap {
	@Mapping(target="id", ignore=true)
	@Mapping(target="content", ignore=true)
	@Mapping(target="contentParsed", ignore=true)
	@Mapping(target="contentFormat", ignore=true)
	@Mapping(target="categories", ignore=true)
	@Mapping(target="categoryMembers", ignore=true)
	@Mapping(target="revision", ignore=true)
	@Mapping(target="file", ignore=true)
	@Mapping(target="headings", ignore=true)
	@Mapping(target="toc", ignore=true)
	@Mapping(target="entityUrl", ignore=true)
	WikiPage toModel(WikiPageDbo dbo);
}
