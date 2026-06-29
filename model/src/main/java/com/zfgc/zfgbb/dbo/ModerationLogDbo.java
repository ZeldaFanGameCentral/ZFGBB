package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class ModerationLogDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739194656-04:00", comments="Source field: zfgbb.moderation_log.moderation_log_id")
    private Integer moderationLogId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739219875-04:00", comments="Source field: zfgbb.moderation_log.actor_user_id")
    private Integer actorUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739234465-04:00", comments="Source field: zfgbb.moderation_log.action")
    private String action;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739250054-04:00", comments="Source field: zfgbb.moderation_log.target_user_id")
    private Integer targetUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739317642-04:00", comments="Source field: zfgbb.moderation_log.target_name")
    private String targetName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739395709-04:00", comments="Source field: zfgbb.moderation_log.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739423889-04:00", comments="Source field: zfgbb.moderation_log.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739443718-04:00", comments="Source field: zfgbb.moderation_log.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739469307-04:00", comments="Source field: zfgbb.moderation_log.detail")
    private String detail;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739503136-04:00", comments="Source field: zfgbb.moderation_log.logged_ts")
    private OffsetDateTime loggedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739523476-04:00", comments="Source field: zfgbb.moderation_log.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739542815-04:00", comments="Source field: zfgbb.moderation_log.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739562144-04:00", comments="Source field: zfgbb.moderation_log.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739208165-04:00", comments="Source field: zfgbb.moderation_log.moderation_log_id")
    public Integer getModerationLogId() {
        return moderationLogId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739214395-04:00", comments="Source field: zfgbb.moderation_log.moderation_log_id")
    public void setModerationLogId(Integer moderationLogId) {
        this.moderationLogId = moderationLogId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739224825-04:00", comments="Source field: zfgbb.moderation_log.actor_user_id")
    public Integer getActorUserId() {
        return actorUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739229715-04:00", comments="Source field: zfgbb.moderation_log.actor_user_id")
    public void setActorUserId(Integer actorUserId) {
        this.actorUserId = actorUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739239314-04:00", comments="Source field: zfgbb.moderation_log.action")
    public String getAction() {
        return action;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739244644-04:00", comments="Source field: zfgbb.moderation_log.action")
    public void setAction(String action) {
        this.action = action;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739255514-04:00", comments="Source field: zfgbb.moderation_log.target_user_id")
    public Integer getTargetUserId() {
        return targetUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739264764-04:00", comments="Source field: zfgbb.moderation_log.target_user_id")
    public void setTargetUserId(Integer targetUserId) {
        this.targetUserId = targetUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739356941-04:00", comments="Source field: zfgbb.moderation_log.target_name")
    public String getTargetName() {
        return targetName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73937877-04:00", comments="Source field: zfgbb.moderation_log.target_name")
    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739404949-04:00", comments="Source field: zfgbb.moderation_log.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739412389-04:00", comments="Source field: zfgbb.moderation_log.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739430908-04:00", comments="Source field: zfgbb.moderation_log.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739437528-04:00", comments="Source field: zfgbb.moderation_log.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739450138-04:00", comments="Source field: zfgbb.moderation_log.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739457408-04:00", comments="Source field: zfgbb.moderation_log.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739482137-04:00", comments="Source field: zfgbb.moderation_log.detail")
    public String getDetail() {
        return detail;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739491287-04:00", comments="Source field: zfgbb.moderation_log.detail")
    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739511436-04:00", comments="Source field: zfgbb.moderation_log.logged_ts")
    public OffsetDateTime getLoggedTs() {
        return loggedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739517666-04:00", comments="Source field: zfgbb.moderation_log.logged_ts")
    public void setLoggedTs(OffsetDateTime loggedTs) {
        this.loggedTs = loggedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739530465-04:00", comments="Source field: zfgbb.moderation_log.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739536785-04:00", comments="Source field: zfgbb.moderation_log.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739549485-04:00", comments="Source field: zfgbb.moderation_log.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739555675-04:00", comments="Source field: zfgbb.moderation_log.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739568494-04:00", comments="Source field: zfgbb.moderation_log.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.739575444-04:00", comments="Source field: zfgbb.moderation_log.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return moderationLogId;
    }

    @Override
    public OffsetDateTime getCreatedTime() {
        return createdTs;
    }

    @Override
    public OffsetDateTime getUpdatedTime() {
        return updatedTs;
    }
}