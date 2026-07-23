package com.zfgc.zfgbb.mappers.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MessagePostCountMapper {

	class OwnerPostCount {
		private Integer ownerId;
		private long postCount;

		public Integer getOwnerId() {
			return ownerId;
		}

		public void setOwnerId(Integer ownerId) {
			this.ownerId = ownerId;
		}

		public long getPostCount() {
			return postCount;
		}

		public void setPostCount(long postCount) {
			this.postCount = postCount;
		}
	}

	class ThreadPostCount {
		private Integer threadId;
		private int postCount;

		public Integer getThreadId() {
			return threadId;
		}

		public void setThreadId(Integer threadId) {
			this.threadId = threadId;
		}

		public int getPostCount() {
			return postCount;
		}

		public void setPostCount(int postCount) {
			this.postCount = postCount;
		}
	}

	class LatestMessageUser {
		private Integer threadId;
		private Integer lastPostedUserId;
		private String lastPostedUser;

		public Integer getThreadId() {
			return threadId;
		}

		public void setThreadId(Integer threadId) {
			this.threadId = threadId;
		}

		public Integer getLastPostedUserId() {
			return lastPostedUserId;
		}

		public void setLastPostedUserId(Integer lastPostedUserId) {
			this.lastPostedUserId = lastPostedUserId;
		}

		public String getLastPostedUser() {
			return lastPostedUser;
		}

		public void setLastPostedUser(String lastPostedUser) {
			this.lastPostedUser = lastPostedUser;
		}
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
