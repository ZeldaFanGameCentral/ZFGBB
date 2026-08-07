package com.zfgc.zfgbb.mappers.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ContentEntitySearchQueryMapper {

	@Select("""
			select e.content_entity_id as refId, e.slug as slug, e.title as title,
			       e.author_name as context, e.summary as body
			from zfgbb.content_entity e
			where e.entity_type = #{entityType} and (e.title ilike #{pattern} or e.summary ilike #{pattern})
			order by (e.title ilike #{pattern}) desc, e.title
			limit #{limit}
			""")
	List<SearchMatchRow> searchContentEntities(@Param("entityType") String entityType,
			@Param("pattern") String pattern, @Param("limit") int limit);

	@Select("""
			select count(*)
			from zfgbb.content_entity e
			where e.entity_type = #{entityType} and (e.title ilike #{pattern} or e.summary ilike #{pattern})
			""")
	int countMatchingContentEntities(@Param("entityType") String entityType, @Param("pattern") String pattern);
}
