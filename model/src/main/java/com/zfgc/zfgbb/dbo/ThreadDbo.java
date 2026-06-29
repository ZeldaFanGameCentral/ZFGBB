package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class ThreadDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860207028-04:00", comments="Source field: zfgbb.thread.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860239427-04:00", comments="Source field: zfgbb.thread.thread_name")
    private String threadName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860263846-04:00", comments="Source field: zfgbb.thread.locked_flag")
    private Boolean lockedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860286505-04:00", comments="Source field: zfgbb.thread.pinned_flag")
    private Boolean pinnedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860322054-04:00", comments="Source field: zfgbb.thread.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860354523-04:00", comments="Source field: zfgbb.thread.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860376932-04:00", comments="Source field: zfgbb.thread.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860398302-04:00", comments="Source field: zfgbb.thread.created_user_id")
    private Integer createdUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860417581-04:00", comments="Source field: zfgbb.thread.view_count")
    private Integer viewCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.86043761-04:00", comments="Source field: zfgbb.thread.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.86046354-04:00", comments="Source field: zfgbb.thread.recycled_from_board_id")
    private Integer recycledFromBoardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860486169-04:00", comments="Source field: zfgbb.thread.recycled_from_thread_id")
    private Integer recycledFromThreadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860219397-04:00", comments="Source field: zfgbb.thread.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860231907-04:00", comments="Source field: zfgbb.thread.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860247126-04:00", comments="Source field: zfgbb.thread.thread_name")
    public String getThreadName() {
        return threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860257216-04:00", comments="Source field: zfgbb.thread.thread_name")
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860270776-04:00", comments="Source field: zfgbb.thread.locked_flag")
    public Boolean getLockedFlag() {
        return lockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860278145-04:00", comments="Source field: zfgbb.thread.locked_flag")
    public void setLockedFlag(Boolean lockedFlag) {
        this.lockedFlag = lockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860294205-04:00", comments="Source field: zfgbb.thread.pinned_flag")
    public Boolean getPinnedFlag() {
        return pinnedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860307184-04:00", comments="Source field: zfgbb.thread.pinned_flag")
    public void setPinnedFlag(Boolean pinnedFlag) {
        this.pinnedFlag = pinnedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860337424-04:00", comments="Source field: zfgbb.thread.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860347533-04:00", comments="Source field: zfgbb.thread.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860362523-04:00", comments="Source field: zfgbb.thread.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860370392-04:00", comments="Source field: zfgbb.thread.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860383912-04:00", comments="Source field: zfgbb.thread.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860391692-04:00", comments="Source field: zfgbb.thread.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860405351-04:00", comments="Source field: zfgbb.thread.created_user_id")
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860412791-04:00", comments="Source field: zfgbb.thread.created_user_id")
    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860424421-04:00", comments="Source field: zfgbb.thread.view_count")
    public Integer getViewCount() {
        return viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860433071-04:00", comments="Source field: zfgbb.thread.view_count")
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.86044712-04:00", comments="Source field: zfgbb.thread.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.86045674-04:00", comments="Source field: zfgbb.thread.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860471839-04:00", comments="Source field: zfgbb.thread.recycled_from_board_id")
    public Integer getRecycledFromBoardId() {
        return recycledFromBoardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860479869-04:00", comments="Source field: zfgbb.thread.recycled_from_board_id")
    public void setRecycledFromBoardId(Integer recycledFromBoardId) {
        this.recycledFromBoardId = recycledFromBoardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860493059-04:00", comments="Source field: zfgbb.thread.recycled_from_thread_id")
    public Integer getRecycledFromThreadId() {
        return recycledFromThreadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-18T05:48:45.860500368-04:00", comments="Source field: zfgbb.thread.recycled_from_thread_id")
    public void setRecycledFromThreadId(Integer recycledFromThreadId) {
        this.recycledFromThreadId = recycledFromThreadId;
    }

    @Override
    public Integer getPkId() {
        return threadId;
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