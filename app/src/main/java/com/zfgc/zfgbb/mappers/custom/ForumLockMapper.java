package com.zfgc.zfgbb.mappers.custom;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ForumLockMapper {

	@Select("select thread_id from zfgbb.thread where thread_id = #{threadId} for update")
	Integer lockThread(@Param("threadId") Integer threadId);

	@Select("select board_id from zfgbb.board where board_id = #{boardId} for update")
	Integer lockBoard(@Param("boardId") Integer boardId);

	@Select("select coalesce(max(post_in_thread), 0) from zfgbb.message where thread_id = #{threadId}")
	Integer maxPostInThread(@Param("threadId") Integer threadId);
}
