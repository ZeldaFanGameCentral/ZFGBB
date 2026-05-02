package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class AllMessagesInThreadViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.530837366-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.530866885-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_name")
    private String threadName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.530889114-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.locked_flag")
    private Boolean lockedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.530927163-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.pinned_flag")
    private Boolean pinnedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.53101261-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531034399-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531054328-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531073998-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_user_id")
    private Integer createdUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531100337-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.view_count")
    private Integer viewCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531121096-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531140375-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user_id")
    private Integer lastPostedUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531159955-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user")
    private String lastPostedUser;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531194204-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_ts")
    private LocalDateTime postTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531217973-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_in_thread")
    private Integer postInThread;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.530848825-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.530857245-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.530873654-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_name")
    public String getThreadName() {
        return threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.530882414-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_name")
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.530895684-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.locked_flag")
    public Boolean getLockedFlag() {
        return lockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.530902453-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.locked_flag")
    public void setLockedFlag(Boolean lockedFlag) {
        this.lockedFlag = lockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.53099365-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.pinned_flag")
    public Boolean getPinnedFlag() {
        return pinnedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.53100367-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.pinned_flag")
    public void setPinnedFlag(Boolean pinnedFlag) {
        this.pinnedFlag = pinnedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531020849-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531027809-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531041579-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531048129-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531061338-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531067948-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531080597-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_user_id")
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531087057-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_user_id")
    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531107637-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.view_count")
    public Integer getViewCount() {
        return viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531114206-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.view_count")
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531127606-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531133936-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531147205-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user_id")
    public Integer getLastPostedUserId() {
        return lastPostedUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531153675-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user_id")
    public void setLastPostedUserId(Integer lastPostedUserId) {
        this.lastPostedUserId = lastPostedUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531169495-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user")
    public String getLastPostedUser() {
        return lastPostedUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531181034-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user")
    public void setLastPostedUser(String lastPostedUser) {
        this.lastPostedUser = lastPostedUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531204153-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_ts")
    public LocalDateTime getPostTs() {
        return postTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531211033-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_ts")
    public void setPostTs(LocalDateTime postTs) {
        this.postTs = postTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531246282-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_in_thread")
    public Integer getPostInThread() {
        return postInThread;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.531254242-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_in_thread")
    public void setPostInThread(Integer postInThread) {
        this.postInThread = postInThread;
    }

    @Override
    public Integer getPkId() {
        return null;
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