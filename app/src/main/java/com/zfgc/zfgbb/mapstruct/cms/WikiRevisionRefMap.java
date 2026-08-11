package com.zfgc.zfgbb.mapstruct.cms;

import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDbo;
import com.zfgc.zfgbb.dbo.WikiRevisionRefDbo;
import com.zfgc.zfgbb.model.cms.WikiRevisionRef;

@Mapper(config=BBMapperConfig.class, builder=@Builder(disableBuilder=true))
public interface WikiRevisionRefMap {

	@Mapping(target="revisionId", source="wikiPageRevisionId")
	@Mapping(target="page", ignore=true)
	@Mapping(target="authoredTs", ignore=true)
	@Mapping(target="size", ignore=true)
	@Mapping(target="current", ignore=true)
	WikiRevisionRef toRef(WikiRevisionRefDbo dbo);

	@Mapping(target="revisionId", source="wikiPageRevisionId")
	@Mapping(target="page", ignore=true)
	@Mapping(target="authoredTs", ignore=true)
	@Mapping(target="size", ignore=true)
	@Mapping(target="current", ignore=true)
	WikiRevisionRef toRef(WikiPageRevisionDbo dbo);

	@AfterMapping
	default void applyDerivedFields(WikiRevisionRefDbo dbo, @MappingTarget WikiRevisionRef ref) {
		ref.setAuthoredTs(dbo.getAuthoredTs() == null ? dbo.getCreatedTs() : dbo.getAuthoredTs());
		ref.setSize(dbo.getContentSize() == null ? 0 : dbo.getContentSize());
		ref.setCurrent(Boolean.TRUE.equals(dbo.getCurrentFlag()));
	}

	@AfterMapping
	default void applyDerivedFields(WikiPageRevisionDbo dbo, @MappingTarget WikiRevisionRef ref) {
		ref.setAuthoredTs(dbo.getAuthoredTs() == null ? dbo.getCreatedTs() : dbo.getAuthoredTs());
		ref.setSize(dbo.getContentSize() == null ? 0 : dbo.getContentSize());
		ref.setCurrent(Boolean.TRUE.equals(dbo.getCurrentFlag()));
	}
}
