package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class QuoteStripRunDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.run_id")
    private Object runId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.status")
    private String status;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.candidate_rows")
    private Integer candidateRows;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.planned_rows")
    private Integer plannedRows;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.planned_quotes")
    private Long plannedQuotes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.lease_owner")
    private Object leaseOwner;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.lease_expires_ts")
    private OffsetDateTime leaseExpiresTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.heartbeat_ts")
    private OffsetDateTime heartbeatTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.attempt_no")
    private Integer attemptNo;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.run_id")
    public Object getRunId() {
        return runId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.run_id")
    public void setRunId(Object runId) {
        this.runId = runId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.status")
    public String getStatus() {
        return status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.status")
    public void setStatus(String status) {
        this.status = status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.candidate_rows")
    public Integer getCandidateRows() {
        return candidateRows;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.candidate_rows")
    public void setCandidateRows(Integer candidateRows) {
        this.candidateRows = candidateRows;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.planned_rows")
    public Integer getPlannedRows() {
        return plannedRows;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.planned_rows")
    public void setPlannedRows(Integer plannedRows) {
        this.plannedRows = plannedRows;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.planned_quotes")
    public Long getPlannedQuotes() {
        return plannedQuotes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.planned_quotes")
    public void setPlannedQuotes(Long plannedQuotes) {
        this.plannedQuotes = plannedQuotes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.lease_owner")
    public Object getLeaseOwner() {
        return leaseOwner;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.lease_owner")
    public void setLeaseOwner(Object leaseOwner) {
        this.leaseOwner = leaseOwner;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.lease_expires_ts")
    public OffsetDateTime getLeaseExpiresTs() {
        return leaseExpiresTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.lease_expires_ts")
    public void setLeaseExpiresTs(OffsetDateTime leaseExpiresTs) {
        this.leaseExpiresTs = leaseExpiresTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.heartbeat_ts")
    public OffsetDateTime getHeartbeatTs() {
        return heartbeatTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.heartbeat_ts")
    public void setHeartbeatTs(OffsetDateTime heartbeatTs) {
        this.heartbeatTs = heartbeatTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.attempt_no")
    public Integer getAttemptNo() {
        return attemptNo;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_run.attempt_no")
    public void setAttemptNo(Integer attemptNo) {
        this.attemptNo = attemptNo;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    public Integer getPkId() {
        return null;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    public OffsetDateTime getCreatedTime() {
        return createdTs;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_run")
    public OffsetDateTime getUpdatedTime() {
        return updatedTs;
    }
}