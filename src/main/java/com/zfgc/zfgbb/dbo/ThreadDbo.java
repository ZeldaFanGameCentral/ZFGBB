package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class ThreadDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360126804-04:00", comments="Source field: zfgbb.thread.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360150373-04:00", comments="Source field: zfgbb.thread.thread_name")
    private String threadName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360171302-04:00", comments="Source field: zfgbb.thread.locked_flag")
    private Boolean lockedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360189262-04:00", comments="Source field: zfgbb.thread.pinned_flag")
    private Boolean pinnedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360206361-04:00", comments="Source field: zfgbb.thread.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.36023878-04:00", comments="Source field: zfgbb.thread.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360257749-04:00", comments="Source field: zfgbb.thread.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360274669-04:00", comments="Source field: zfgbb.thread.created_user_id")
    private Integer createdUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360292218-04:00", comments="Source field: zfgbb.thread.view_count")
    private Integer viewCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360309368-04:00", comments="Source field: zfgbb.thread.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360136203-04:00", comments="Source field: zfgbb.thread.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360144203-04:00", comments="Source field: zfgbb.thread.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360157183-04:00", comments="Source field: zfgbb.thread.thread_name")
    public String getThreadName() {
        return threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360165732-04:00", comments="Source field: zfgbb.thread.thread_name")
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360177522-04:00", comments="Source field: zfgbb.thread.locked_flag")
    public Boolean getLockedFlag() {
        return lockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360183972-04:00", comments="Source field: zfgbb.thread.locked_flag")
    public void setLockedFlag(Boolean lockedFlag) {
        this.lockedFlag = lockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360195221-04:00", comments="Source field: zfgbb.thread.pinned_flag")
    public Boolean getPinnedFlag() {
        return pinnedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360201351-04:00", comments="Source field: zfgbb.thread.pinned_flag")
    public void setPinnedFlag(Boolean pinnedFlag) {
        this.pinnedFlag = pinnedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360213671-04:00", comments="Source field: zfgbb.thread.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.36023269-04:00", comments="Source field: zfgbb.thread.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.36024627-04:00", comments="Source field: zfgbb.thread.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.3602526-04:00", comments="Source field: zfgbb.thread.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360263749-04:00", comments="Source field: zfgbb.thread.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360269879-04:00", comments="Source field: zfgbb.thread.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360280479-04:00", comments="Source field: zfgbb.thread.created_user_id")
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360286578-04:00", comments="Source field: zfgbb.thread.created_user_id")
    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360298138-04:00", comments="Source field: zfgbb.thread.view_count")
    public Integer getViewCount() {
        return viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360304258-04:00", comments="Source field: zfgbb.thread.view_count")
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360315198-04:00", comments="Source field: zfgbb.thread.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.360326007-04:00", comments="Source field: zfgbb.thread.migration_hash")
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