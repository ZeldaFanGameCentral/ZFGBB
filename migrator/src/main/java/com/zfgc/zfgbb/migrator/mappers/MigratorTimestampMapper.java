package com.zfgc.zfgbb.migrator.mappers;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Param;

public interface MigratorTimestampMapper {

	int setMessageTimestamps(
			@Param("messageId") Integer messageId,
			@Param("createdTs") LocalDateTime createdTs,
			@Param("updatedTs") LocalDateTime updatedTs);

	int setMessageHistoryTimestamps(
			@Param("messageHistoryId") Integer messageHistoryId,
			@Param("createdTs") LocalDateTime createdTs,
			@Param("updatedTs") LocalDateTime updatedTs);

	int setKarmaTimestamps(
			@Param("karmaId") Integer karmaId,
			@Param("createdTs") LocalDateTime createdTs,
			@Param("updatedTs") LocalDateTime updatedTs);
}
