package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class ThreadDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.485546286-04:00", comments="Source field: zfgbb.thread.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.485656873-04:00", comments="Source field: zfgbb.thread.thread_name")
    private String threadName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.485770339-04:00", comments="Source field: zfgbb.thread.locked_flag")
    private Boolean lockedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.485912945-04:00", comments="Source field: zfgbb.thread.pinned_flag")
    private Boolean pinnedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.486007482-04:00", comments="Source field: zfgbb.thread.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.486105089-04:00", comments="Source field: zfgbb.thread.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.486199236-04:00", comments="Source field: zfgbb.thread.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.486337811-04:00", comments="Source field: zfgbb.thread.created_user_id")
    private Integer createdUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.486427478-04:00", comments="Source field: zfgbb.thread.view_count")
    private Integer viewCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.486591743-04:00", comments="Source field: zfgbb.thread.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.485588805-04:00", comments="Source field: zfgbb.thread.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.485626734-04:00", comments="Source field: zfgbb.thread.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.485689752-04:00", comments="Source field: zfgbb.thread.thread_name")
    public String getThreadName() {
        return threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.48573801-04:00", comments="Source field: zfgbb.thread.thread_name")
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.485838187-04:00", comments="Source field: zfgbb.thread.locked_flag")
    public Boolean getLockedFlag() {
        return lockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.485883386-04:00", comments="Source field: zfgbb.thread.locked_flag")
    public void setLockedFlag(Boolean lockedFlag) {
        this.lockedFlag = lockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.485943574-04:00", comments="Source field: zfgbb.thread.pinned_flag")
    public Boolean getPinnedFlag() {
        return pinnedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.485979063-04:00", comments="Source field: zfgbb.thread.pinned_flag")
    public void setPinnedFlag(Boolean pinnedFlag) {
        this.pinnedFlag = pinnedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.486044191-04:00", comments="Source field: zfgbb.thread.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.48607616-04:00", comments="Source field: zfgbb.thread.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.486142267-04:00", comments="Source field: zfgbb.thread.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.486171556-04:00", comments="Source field: zfgbb.thread.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.486243944-04:00", comments="Source field: zfgbb.thread.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.486286573-04:00", comments="Source field: zfgbb.thread.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.48636969-04:00", comments="Source field: zfgbb.thread.created_user_id")
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.486400809-04:00", comments="Source field: zfgbb.thread.created_user_id")
    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.486455457-04:00", comments="Source field: zfgbb.thread.view_count")
    public Integer getViewCount() {
        return viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.486548934-04:00", comments="Source field: zfgbb.thread.view_count")
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.486636012-04:00", comments="Source field: zfgbb.thread.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.4866874-04:00", comments="Source field: zfgbb.thread.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return threadId;
    }

    @Override
    public LocalDateTime getCreatedTime() {
        return createdTs;
    }

    @Override
    public LocalDateTime getUpdatedTime() {
        return updatedTs;
    }
}