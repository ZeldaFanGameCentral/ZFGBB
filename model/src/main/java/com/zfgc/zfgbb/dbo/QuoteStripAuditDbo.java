package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class QuoteStripAuditDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.quote_strip_audit_id")
    private Integer quoteStripAuditId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.run_id")
    private Object runId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.message_history_id")
    private Integer messageHistoryId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.before_text")
    private String beforeText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.after_text")
    private String afterText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.status")
    private String status;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.planned_ts")
    private OffsetDateTime plannedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.applied_ts")
    private OffsetDateTime appliedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.quote_strip_audit_id")
    public Integer getQuoteStripAuditId() {
        return quoteStripAuditId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.quote_strip_audit_id")
    public void setQuoteStripAuditId(Integer quoteStripAuditId) {
        this.quoteStripAuditId = quoteStripAuditId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.run_id")
    public Object getRunId() {
        return runId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.run_id")
    public void setRunId(Object runId) {
        this.runId = runId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.message_history_id")
    public Integer getMessageHistoryId() {
        return messageHistoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.message_history_id")
    public void setMessageHistoryId(Integer messageHistoryId) {
        this.messageHistoryId = messageHistoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.before_text")
    public String getBeforeText() {
        return beforeText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.before_text")
    public void setBeforeText(String beforeText) {
        this.beforeText = beforeText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.after_text")
    public String getAfterText() {
        return afterText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.after_text")
    public void setAfterText(String afterText) {
        this.afterText = afterText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.status")
    public String getStatus() {
        return status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.status")
    public void setStatus(String status) {
        this.status = status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.planned_ts")
    public OffsetDateTime getPlannedTs() {
        return plannedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.planned_ts")
    public void setPlannedTs(OffsetDateTime plannedTs) {
        this.plannedTs = plannedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.applied_ts")
    public OffsetDateTime getAppliedTs() {
        return appliedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.quote_strip_audit.applied_ts")
    public void setAppliedTs(OffsetDateTime appliedTs) {
        this.appliedTs = appliedTs;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    public Integer getPkId() {
        return quoteStripAuditId;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    public OffsetDateTime getCreatedTime() {
        return null;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    public OffsetDateTime getUpdatedTime() {
        return null;
    }
}