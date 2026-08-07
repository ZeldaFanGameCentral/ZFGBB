package com.zfgc.zfgbb.mappers.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface WikiSearchQueryMapper {

	@Select("""
			<script>
			select p.wiki_page_id as refId, p.slug as slug, p.title as title,
			       p.namespace as context, r.content as body, r.content_format as contentFormat
			from zfgbb.wiki_page p
			join zfgbb.wiki_page_revision r on r.wiki_page_id = p.wiki_page_id and r.current_flag
			where p.redirect_to is null
			  <if test="hiddenNamespaces != null and !hiddenNamespaces.isEmpty()">
			  and p.namespace not in
			  <foreach item="namespace" collection="hiddenNamespaces" open="(" separator="," close=")">
			  #{namespace}
			  </foreach>
			  </if>
			  and (p.title ilike #{pattern} or r.content ilike #{pattern})
			order by (p.title ilike #{pattern}) desc, p.title
			limit #{limit}
			</script>
			""")
	List<SearchMatchRow> searchWikiPages(@Param("pattern") String pattern,
			@Param("hiddenNamespaces") List<String> hiddenNamespaces, @Param("limit") int limit);

	@Select("""
			<script>
			select count(*)
			from zfgbb.wiki_page p
			join zfgbb.wiki_page_revision r on r.wiki_page_id = p.wiki_page_id and r.current_flag
			where p.redirect_to is null
			  <if test="hiddenNamespaces != null and !hiddenNamespaces.isEmpty()">
			  and p.namespace not in
			  <foreach item="namespace" collection="hiddenNamespaces" open="(" separator="," close=")">
			  #{namespace}
			  </foreach>
			  </if>
			  and (p.title ilike #{pattern} or r.content ilike #{pattern})
			</script>
			""")
	int countMatchingWikiPages(@Param("pattern") String pattern,
			@Param("hiddenNamespaces") List<String> hiddenNamespaces);
}
