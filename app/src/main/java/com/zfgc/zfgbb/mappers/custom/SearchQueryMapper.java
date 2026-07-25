package com.zfgc.zfgbb.mappers.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.zfgc.zfgbb.dbo.CurrentMessageDbo;

import lombok.Getter;
import lombok.Setter;

public interface SearchQueryMapper {

	@Getter
	@Setter
	class Hit {
		private String refId;
		private String slug;
		private String title;
		private String context;
		private String body;
	}

	@Select("""
			<script>
			select t.thread_id as refId, cast(t.thread_id as varchar) as slug,
			       t.thread_name as title, b.board_name as context, null as body
			from zfgbb.thread t
			join zfgbb.board b on b.board_id = t.board_id
			where t.thread_name ilike #{pattern}
			and exists (select 1 from zfgbb.br_board_permission bp
			            where bp.board_id = t.board_id and bp.permission_id in
			            <foreach item='pid' collection='permissionIds' open='(' separator=',' close=')'>#{pid}</foreach>)
			order by t.thread_id desc
			limit #{limit}
			</script>
			""")
	List<Hit> searchThreadNames(@Param("pattern") String pattern, @Param("permissionIds") List<Integer> permissionIds,
			@Param("limit") int limit);

	@Select("""
			<script>
			select distinct on (m.thread_id) m.thread_id as refId, cast(m.thread_id as varchar) as slug,
			       t.thread_name as title, b.board_name as context, mh.message_text as body
			from zfgbb.message_history mh
			join zfgbb.message m on m.message_id = mh.message_id
			join zfgbb.thread t on t.thread_id = m.thread_id
			join zfgbb.board b on b.board_id = t.board_id
			where mh.current_flag and mh.message_text ilike #{pattern}
			and exists (select 1 from zfgbb.br_board_permission bp
			            where bp.board_id = t.board_id and bp.permission_id in
			            <foreach item='pid' collection='permissionIds' open='(' separator=',' close=')'>#{pid}</foreach>)
			order by m.thread_id desc
			limit #{limit}
			</script>
			""")
	List<Hit> searchMessages(@Param("pattern") String pattern, @Param("permissionIds") List<Integer> permissionIds,
			@Param("limit") int limit);

	@Select("""
			<script>
			select m.message_id as messageId, m.owner_id as ownerId, m.thread_id as threadId,
			       h.message_text as messageText, h.message_history_id as messageHistoryId,
			       m.post_in_thread as postInThread, m.created_ts as createdTs
			from zfgbb.message m
			join zfgbb.message_history h on h.message_id = m.message_id and h.current_flag
			join zfgbb.thread t on t.thread_id = m.thread_id
			where m.owner_id = #{userId}
			and exists (select 1 from zfgbb.br_board_permission bp
			            where bp.board_id = t.board_id and bp.permission_id in
			            <foreach item='pid' collection='permissionIds' open='(' separator=',' close=')'>#{pid}</foreach>)
			order by m.created_ts desc
			limit #{limit} offset #{offset}
			</script>
			""")
	List<CurrentMessageDbo> messagesByUser(@Param("userId") Integer userId,
			@Param("permissionIds") List<Integer> permissionIds, @Param("limit") int limit,
			@Param("offset") int offset);

	@Select("""
			<script>
			select p.wiki_page_id as refId, p.slug as slug, p.title as title,
			       p.namespace as context, r.content as body
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
	List<Hit> searchWiki(@Param("pattern") String pattern,
			@Param("hiddenNamespaces") List<String> hiddenNamespaces, @Param("limit") int limit);

	@Select("""
			select e.content_entity_id as refId, e.slug as slug, e.title as title,
			       e.author_name as context, e.summary as body
			from zfgbb.content_entity e
			where e.entity_type = 'PROJECT' and (e.title ilike #{pattern} or e.summary ilike #{pattern})
			order by (e.title ilike #{pattern}) desc, e.title
			limit #{limit}
			""")
	List<Hit> searchProjects(@Param("pattern") String pattern, @Param("limit") int limit);

	@Select("""
			select e.content_entity_id as refId, e.slug as slug, e.title as title,
			       e.author_name as context, e.summary as body
			from zfgbb.content_entity e
			where e.entity_type = 'RESOURCE' and (e.title ilike #{pattern} or e.summary ilike #{pattern})
			order by (e.title ilike #{pattern}) desc, e.title
			limit #{limit}
			""")
	List<Hit> searchResources(@Param("pattern") String pattern, @Param("limit") int limit);
}
