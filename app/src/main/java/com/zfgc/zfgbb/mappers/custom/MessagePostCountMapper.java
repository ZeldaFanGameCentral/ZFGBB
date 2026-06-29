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
}
