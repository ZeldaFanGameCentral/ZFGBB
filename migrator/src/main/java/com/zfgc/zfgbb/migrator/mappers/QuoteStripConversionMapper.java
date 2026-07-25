package com.zfgc.zfgbb.migrator.mappers;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zfgc.zfgbb.operations.maintenance.DistributedJobRepository;

import lombok.Getter;
import lombok.Setter;

public interface QuoteStripConversionMapper extends DistributedJobRepository {

	String LEASE_INTERVAL = "2 minutes";
	int SAMPLE_CAP = 20;

	@Getter
	@Setter
	class QuoteStripAuditRow {
		private Integer messageHistoryId;
		private Integer messageId;
		private String beforeText;
		private String afterText;
	}

	@Getter
	@Setter
	class QuoteStripRunSummary {
		private String status;
		private int candidateRows;
		private int plannedRows;
		private long plannedQuotes;
	}

	@Getter
	@Setter
	class QuoterTimestampRow {
		private String messageText;
		private OffsetDateTime createdTs;
	}

	@Insert("insert into zfgbb.quote_strip_run "
			+ "(run_id, status, lease_owner, lease_expires_ts, heartbeat_ts, attempt_no) "
			+ "values (#{runId}, #{status}, #{owner}, current_timestamp + interval '" + LEASE_INTERVAL
			+ "', current_timestamp, 1) on conflict do nothing")
	int insertRun(@Param("runId") UUID runId, @Param("status") String status, @Param("owner") UUID owner);

	@Update("update zfgbb.quote_strip_run set status = #{status}, candidate_rows = #{candidateRows}, "
			+ "planned_rows = #{plannedRows}, planned_quotes = #{plannedQuotes}, lease_owner = null, "
			+ "lease_expires_ts = null, updated_ts = current_timestamp "
			+ "where run_id = #{runId} and status = #{expectedStatus} and lease_owner = #{owner}")
	int markRunPlanned(@Param("runId") UUID runId, @Param("status") String status,
			@Param("candidateRows") int candidateRows, @Param("plannedRows") int plannedRows,
			@Param("plannedQuotes") long plannedQuotes, @Param("expectedStatus") String expectedStatus,
			@Param("owner") UUID owner);

	@Update("<script>"
			+ "update zfgbb.quote_strip_run set status = #{claimed}, lease_owner = #{owner}, "
			+ "lease_expires_ts = current_timestamp + interval '" + LEASE_INTERVAL + "', "
			+ "heartbeat_ts = current_timestamp, attempt_no = attempt_no + 1, updated_ts = current_timestamp "
			+ "where run_id = #{runId} and (status in "
			+ "<foreach item='expectedStatus' collection='expectedStatuses' open='(' separator=',' close=')'>"
			+ "#{expectedStatus}</foreach> "
			+ "or (status = #{claimed} and (lease_expires_ts is null or lease_expires_ts &lt;= current_timestamp)))"
			+ "</script>")
	int claimRun(@Param("runId") UUID runId, @Param("claimed") String claimed, @Param("owner") UUID owner,
			@Param("expectedStatuses") List<String> expectedStatuses);

	@Update("update zfgbb.quote_strip_run set "
			+ "lease_expires_ts = current_timestamp + interval '" + LEASE_INTERVAL + "', "
			+ "heartbeat_ts = current_timestamp, updated_ts = current_timestamp "
			+ "where run_id = #{runId} and status = #{status} and lease_owner = #{owner} "
			+ "and lease_expires_ts > current_timestamp")
	int heartbeat(@Param("runId") UUID runId, @Param("status") String status, @Param("owner") UUID owner);

	@Select("select count(*) from zfgbb.quote_strip_run where run_id = #{runId} "
			+ "and status = #{status} and lease_owner = #{owner} and lease_expires_ts > current_timestamp")
	Integer countOwnedLease(@Param("runId") UUID runId, @Param("status") String status, @Param("owner") UUID owner);

	@Update("update zfgbb.quote_strip_run set status = #{status}, updated_ts = current_timestamp"
			+ ", lease_owner = null, lease_expires_ts = null where run_id = #{runId} and status = #{expectedStatus} "
			+ "and lease_owner = #{owner} and lease_expires_ts > current_timestamp")
	int finishRun(@Param("runId") UUID runId, @Param("status") String status,
			@Param("expectedStatus") String expectedStatus, @Param("owner") UUID owner);

	@Select("select status, candidate_rows as candidateRows, planned_rows as plannedRows, "
			+ "planned_quotes as plannedQuotes from zfgbb.quote_strip_run where run_id = #{runId}")
	QuoteStripRunSummary loadRunSummary(@Param("runId") UUID runId);

	@Select("select count(*) from zfgbb.quote_strip_run where run_id = #{runId}")
	Integer countRun(@Param("runId") UUID runId);

	@Select("select count(*) from zfgbb.quote_strip_run where run_id = #{runId} "
			+ "and status in (#{planningStatus}, #{applyingStatus}, #{revertingStatus}) "
			+ "and lease_expires_ts > current_timestamp")
	Integer countLiveLease(@Param("runId") UUID runId, @Param("planningStatus") String planningStatus,
			@Param("applyingStatus") String applyingStatus, @Param("revertingStatus") String revertingStatus);

	@Delete("delete from zfgbb.quote_strip_run where run_id = #{runId}")
	int deleteRun(@Param("runId") UUID runId);

	@Insert("insert into zfgbb.quote_strip_audit "
			+ "(run_id, message_history_id, message_id, before_text, after_text, status, planned_ts) "
			+ "values (#{runId}, #{messageHistoryId}, #{messageId}, #{beforeText}, #{afterText}, #{status}, "
			+ "#{plannedTs}) on conflict (run_id, message_history_id) do nothing")
	int insertAudit(@Param("runId") UUID runId, @Param("messageHistoryId") Integer messageHistoryId,
			@Param("messageId") Integer messageId, @Param("beforeText") String beforeText,
			@Param("afterText") String afterText, @Param("status") String status,
			@Param("plannedTs") OffsetDateTime plannedTs);

	@Update("update zfgbb.quote_strip_audit set status = #{status}, applied_ts = current_timestamp "
			+ "where run_id = #{runId} and message_history_id = #{messageHistoryId} and status = #{expectedStatus} "
			+ "and exists (select 1 from zfgbb.quote_strip_run where run_id = #{runId} and status = #{runStatus} "
			+ "and lease_owner = #{owner} and lease_expires_ts > current_timestamp)")
	int markAuditApplied(@Param("status") String status, @Param("runId") UUID runId,
			@Param("messageHistoryId") Integer messageHistoryId, @Param("expectedStatus") String expectedStatus,
			@Param("runStatus") String runStatus, @Param("owner") UUID owner);

	@Update("update zfgbb.quote_strip_audit set status = #{status}, applied_ts = null "
			+ "where run_id = #{runId} and message_history_id = #{messageHistoryId} and status = #{expectedStatus} "
			+ "and exists (select 1 from zfgbb.quote_strip_run where run_id = #{runId} and status = #{runStatus} "
			+ "and lease_owner = #{owner} and lease_expires_ts > current_timestamp)")
	int markAuditReverted(@Param("status") String status, @Param("runId") UUID runId,
			@Param("messageHistoryId") Integer messageHistoryId, @Param("expectedStatus") String expectedStatus,
			@Param("runStatus") String runStatus, @Param("owner") UUID owner);

	@Select("select message_history_id as messageHistoryId, message_id as messageId, "
			+ "before_text as beforeText, after_text as afterText "
			+ "from zfgbb.quote_strip_audit where run_id = #{runId} and status = #{status} order by message_history_id")
	List<QuoteStripAuditRow> loadAudit(@Param("runId") UUID runId, @Param("status") String status);

	@Select("select message_history_id as messageHistoryId, message_id as messageId, "
			+ "before_text as beforeText, after_text as afterText "
			+ "from zfgbb.quote_strip_audit where run_id = #{runId} order by message_history_id limit " + SAMPLE_CAP)
	List<QuoteStripAuditRow> sampleAudit(@Param("runId") UUID runId);

	@Select("select count(*) from zfgbb.quote_strip_audit where run_id = #{runId} and status = #{status}")
	Integer countAudit(@Param("runId") UUID runId, @Param("status") String status);

	@Delete("delete from zfgbb.quote_strip_audit where run_id = #{runId}")
	int deleteAudit(@Param("runId") UUID runId);

	@Update("update zfgbb.message_history set message_text = #{newText}, updated_ts = current_timestamp "
			+ "where message_history_id = #{messageHistoryId} and message_text = #{expectedText} and exists "
			+ "(select 1 from zfgbb.quote_strip_run where run_id = #{runId} and status = #{runStatus} "
			+ "and lease_owner = #{owner} and lease_expires_ts > current_timestamp)")
	int updateMessageHistoryBody(@Param("newText") String newText,
			@Param("messageHistoryId") Integer messageHistoryId, @Param("expectedText") String expectedText,
			@Param("runId") UUID runId, @Param("runStatus") String runStatus, @Param("owner") UUID owner);


	@Select("select message_text as messageText, created_ts as createdTs from zfgbb.message_history "
			+ "where current_flag = true and message_text ilike '%[quote%'")
	List<QuoterTimestampRow> loadQuoterTimestampRows();
}
