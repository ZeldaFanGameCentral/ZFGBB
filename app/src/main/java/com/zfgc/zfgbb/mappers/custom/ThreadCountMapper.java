package com.zfgc.zfgbb.mappers.custom;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ThreadCountMapper {
	class BoardThreadCount {
		private Integer boardId;
		private long threadCount;

		public Integer getBoardId() {
			return boardId;
		}
		public void setBoardId(Integer boardId) {
			this.boardId = boardId;
		}
		public long getThreadCount() {
			return threadCount;
		}
		public void setThreadCount(long threadCount) {
			this.threadCount = threadCount;
		}
	}

	@Select({
		"<script>",
		"SELECT board_id AS boardId, COUNT(*) AS threadCount",
		"FROM thread",
		"WHERE board_id IN",
		"<foreach item='item' index='index' collection='boardIds' open='(' separator=',' close=')'>",
		"#{item}",
		"</foreach>",
		"GROUP BY board_id",
		"</script>"
	})
	List<BoardThreadCount> countThreadsByBoardIds(@Param("boardIds") List<Integer> boardIds);
}
