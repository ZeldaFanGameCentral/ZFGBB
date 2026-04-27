package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class AllMessagesInThreadViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379193717-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379212327-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_name")
    private String threadName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379227966-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.locked_flag")
    private Boolean lockedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379240906-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.pinned_flag")
    private Boolean pinnedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379254375-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379267875-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379280535-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379293164-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_user_id")
    private Integer createdUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379334653-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.view_count")
    private Integer viewCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379348662-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379360762-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user_id")
    private Integer lastPostedUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379374471-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user")
    private String lastPostedUser;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379389431-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_ts")
    private LocalDateTime postTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37940374-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_in_thread")
    private Integer postInThread;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379201517-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379207347-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379216987-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_name")
    public String getThreadName() {
        return threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379223406-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_name")
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379232296-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.locked_flag")
    public Boolean getLockedFlag() {
        return lockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379236696-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.locked_flag")
    public void setLockedFlag(Boolean lockedFlag) {
        this.lockedFlag = lockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379245016-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.pinned_flag")
    public Boolean getPinnedFlag() {
        return pinnedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379250326-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.pinned_flag")
    public void setPinnedFlag(Boolean pinnedFlag) {
        this.pinnedFlag = pinnedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379259295-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379263805-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379272265-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379276505-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379284724-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379289224-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379314983-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_user_id")
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379327473-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_user_id")
    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379339893-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.view_count")
    public Integer getViewCount() {
        return viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379344492-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.view_count")
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379352672-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379356862-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379365932-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user_id")
    public Integer getLastPostedUserId() {
        return lastPostedUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379370452-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user_id")
    public void setLastPostedUserId(Integer lastPostedUserId) {
        this.lastPostedUserId = lastPostedUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379378691-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user")
    public String getLastPostedUser() {
        return lastPostedUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379385011-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user")
    public void setLastPostedUser(String lastPostedUser) {
        this.lastPostedUser = lastPostedUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379394901-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_ts")
    public LocalDateTime getPostTs() {
        return postTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.379399601-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_ts")
    public void setPostTs(LocalDateTime postTs) {
        this.postTs = postTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37940816-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_in_thread")
    public Integer getPostInThread() {
        return postInThread;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37941266-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_in_thread")
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