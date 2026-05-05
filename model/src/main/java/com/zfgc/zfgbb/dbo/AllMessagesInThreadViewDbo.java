package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class AllMessagesInThreadViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.467551596-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.467603064-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_name")
    private String threadName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.467648183-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.locked_flag")
    private Boolean lockedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.46775047-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.pinned_flag")
    private Boolean pinnedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.467870336-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.467940154-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.46804841-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468084089-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_user_id")
    private Integer createdUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468128208-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.view_count")
    private Integer viewCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468159857-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468197375-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user_id")
    private Integer lastPostedUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468239334-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user")
    private String lastPostedUser;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468284383-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_ts")
    private LocalDateTime postTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468323141-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_in_thread")
    private Integer postInThread;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.467570215-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.467585295-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.467617174-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_name")
    public String getThreadName() {
        return threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.467633823-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_name")
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.467660862-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.locked_flag")
    public Boolean getLockedFlag() {
        return lockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.467673312-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.locked_flag")
    public void setLockedFlag(Boolean lockedFlag) {
        this.lockedFlag = lockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.467802498-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.pinned_flag")
    public Boolean getPinnedFlag() {
        return pinnedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.467838697-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.pinned_flag")
    public void setPinnedFlag(Boolean pinnedFlag) {
        this.pinnedFlag = pinnedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.467888035-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.467911794-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.467990182-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468034011-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.4680609-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468072209-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468104278-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_user_id")
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468116748-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_user_id")
    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468138217-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.view_count")
    public Integer getViewCount() {
        return viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468148917-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.view_count")
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468170076-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468180546-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468208665-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user_id")
    public Integer getLastPostedUserId() {
        return lastPostedUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468228334-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user_id")
    public void setLastPostedUserId(Integer lastPostedUserId) {
        this.lastPostedUserId = lastPostedUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468256163-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user")
    public String getLastPostedUser() {
        return lastPostedUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468271723-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user")
    public void setLastPostedUser(String lastPostedUser) {
        this.lastPostedUser = lastPostedUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468299342-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_ts")
    public LocalDateTime getPostTs() {
        return postTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468311982-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_ts")
    public void setPostTs(LocalDateTime postTs) {
        this.postTs = postTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468333871-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_in_thread")
    public Integer getPostInThread() {
        return postInThread;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.468345591-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_in_thread")
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