package com.zfgc.zfgbb.mappers.custom;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface ForumLockMapper {

	@Select("select thread_id from zfgbb.thread where thread_id = #{threadId} for update")
	Integer lockThread(@Param("threadId") Integer threadId);

	@Select({"<script>",
			"select thread_id from zfgbb.thread where thread_id in ",
			"<foreach item='item' collection='threadIds' open='(' separator=',' close=')'>#{item}</foreach> ",
			"for update",
			"</script>"})
	List<Integer> lockThreads(@Param("threadIds") List<Integer> threadIds);

	@Select({"<script>",
			"select board_id from zfgbb.board where board_id in ",
			"<foreach item='item' collection='boardIds' open='(' separator=',' close=')'>#{item}</foreach> ",
			"for update",
			"</script>"})
	List<Integer> lockBoards(@Param("boardIds") List<Integer> boardIds);

	@Select("select coalesce(max(post_in_thread), 0) from zfgbb.message where thread_id = #{threadId}")
	Integer maxPostInThread(@Param("threadId") Integer threadId);
}
