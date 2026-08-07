package com.zfgc.zfgbb.mappers.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.zfgc.zfgbb.dbo.CurrentMessageDbo;

public interface ForumSearchQueryMapper {

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
	List<SearchMatchRow> searchThreadNames(@Param("pattern") String pattern,
			@Param("permissionIds") List<Integer> permissionIds, @Param("limit") int limit);

	@Select("""
			<script>
			select distinct on (m.thread_id) m.thread_id as refId, cast(m.thread_id as varchar) as slug,
			       t.thread_name as title, b.board_name as context, mh.message_text as body,
			       mh.content_format as contentFormat
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
	List<SearchMatchRow> searchMessages(@Param("pattern") String pattern,
			@Param("permissionIds") List<Integer> permissionIds, @Param("limit") int limit);

	@Select("""
			<script>
			select count(*)
			from zfgbb.thread t
			where (t.thread_name ilike #{pattern}
			       or exists (select 1 from zfgbb.message m
			                  join zfgbb.message_history mh on mh.message_id = m.message_id and mh.current_flag
			                  where m.thread_id = t.thread_id and mh.message_text ilike #{pattern}))
			and exists (select 1 from zfgbb.br_board_permission bp
			            where bp.board_id = t.board_id and bp.permission_id in
			            <foreach item='pid' collection='permissionIds' open='(' separator=',' close=')'>#{pid}</foreach>)
			</script>
			""")
	int countMatchingThreads(@Param("pattern") String pattern,
			@Param("permissionIds") List<Integer> permissionIds);

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
}
