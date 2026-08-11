package com.zfgc.zfgbb.testsupport.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestSystemInfoMapper {

	@Select("select zfgbb.wiki_title_key(#{namespace}, #{title}, #{caseMode})")
	String wikiTitleKey(@Param("namespace") String namespace, @Param("title") String title,
			@Param("caseMode") String caseMode);
}
