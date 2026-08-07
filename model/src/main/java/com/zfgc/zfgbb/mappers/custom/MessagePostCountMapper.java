package com.zfgc.zfgbb.mappers.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import lombok.Getter;
import lombok.Setter;

public interface MessagePostCountMapper {

	@Getter
	@Setter
	class OwnerPostCount {
		private Integer ownerId;
		private long postCount;
	}

	@Getter
	@Setter
	class ThreadPostCount {
		private Integer threadId;
		private int postCount;
	}

	@Getter
	@Setter
	class LatestMessageUser {
		private Integer threadId;
		private Integer lastPostedUserId;
		private String lastPostedUser;
	}

	@Select("""
			<script>
			select owner_id as ownerId, count(*) as postCount
			from zfgbb.message
			where owner_id in
			<foreach item="ownerId" collection="ownerIds" open="(" separator="," close=")">#{ownerId}</foreach>
			and board_id in
			<foreach item="boardId" collection="boardIds" open="(" separator="," close=")">#{boardId}</foreach>
			group by owner_id
			</script>
			""")
	List<OwnerPostCount> postCountsByOwnerWithinBoards(@Param("ownerIds") List<Integer> ownerIds,
			@Param("boardIds") List<Integer> boardIds);

	@Select("""
			<script>
			select thread_id as threadId, count(*) as postCount
			from zfgbb.message
			where thread_id in
			<foreach item="threadId" collection="threadIds" open="(" separator="," close=")">#{threadId}</foreach>
			group by thread_id
			</script>
			""")
	List<ThreadPostCount> postCountsByThreadIds(@Param("threadIds") List<Integer> threadIds);

	@Select("""
			<script>
			select distinct on (thread_id) thread_id as threadId, last_posted_user_id as lastPostedUserId, last_posted_user as lastPostedUser
			from zfgbb.all_messages_in_thread_view
			where thread_id in
			<foreach item="threadId" collection="threadIds" open="(" separator="," close=")">#{threadId}</foreach>
			order by thread_id, post_ts desc
			</script>
			""")
	List<LatestMessageUser> latestMessageUsersByThreadIds(@Param("threadIds") List<Integer> threadIds);
}
