package com.zfgc.zfgbb.migrator.mappers;

import java.time.OffsetDateTime;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

public interface MigratorTimestampMapper {

	@Update("""
			update zfgbb.message_history
			set current_flag = false
			where message_history_id = #{messageHistoryId}
			  and migration_hash is not null
			  and current_flag = true
			""")
	int demoteMigratedHistoryCurrent(@Param("messageHistoryId") Integer messageHistoryId);

	@Delete("""
			delete from zfgbb.message_history h
			where h.message_history_id = #{messageHistoryId}
			  and h.migration_hash is not null
			  and not exists (
			    select 1 from zfgbb.migrator_attachment_ref_rewrites l
			    where l.message_history_id = h.message_history_id
			  )
			""")
	int deleteUnreferencedMigratedHistory(@Param("messageHistoryId") Integer messageHistoryId);

	@Update("""
			update zfgbb.message
			set created_ts = #{createdTs},
			    updated_ts = #{updatedTs}
			where message_id = #{messageId}
			""")
	int setMessageTimestamps(
			@Param("messageId") Integer messageId,
			@Param("createdTs") OffsetDateTime createdTs,
			@Param("updatedTs") OffsetDateTime updatedTs);

	@Update("""
			update zfgbb.message_history
			set created_ts = #{createdTs},
			    updated_ts = #{updatedTs}
			where message_history_id = #{messageHistoryId}
			""")
	int setMessageHistoryTimestamps(
			@Param("messageHistoryId") Integer messageHistoryId,
			@Param("createdTs") OffsetDateTime createdTs,
			@Param("updatedTs") OffsetDateTime updatedTs);

	@Update("""
			update zfgbb.message_history
			set created_ts = #{createdTs},
			    updated_ts = #{updatedTs}
			where migration_hash = #{migrationHash}
			""")
	int setMessageHistoryTimestampsByHash(
			@Param("migrationHash") String migrationHash,
			@Param("createdTs") OffsetDateTime createdTs,
			@Param("updatedTs") OffsetDateTime updatedTs);

	@Update("""
			update zfgbb.reaction
			set created_ts = #{createdTs},
			    updated_ts = #{updatedTs}
			where reaction_id = #{reactionId}
			""")
	int setReactionTimestamps(
			@Param("reactionId") Integer reactionId,
			@Param("createdTs") OffsetDateTime createdTs,
			@Param("updatedTs") OffsetDateTime updatedTs);

	@Update("""
			update zfgbb.thread
			set created_ts = #{createdTs},
			    updated_ts = #{updatedTs}
			where thread_id = #{threadId}
			""")
	int setThreadTimestamps(
			@Param("threadId") Integer threadId,
			@Param("createdTs") OffsetDateTime createdTs,
			@Param("updatedTs") OffsetDateTime updatedTs);
}
