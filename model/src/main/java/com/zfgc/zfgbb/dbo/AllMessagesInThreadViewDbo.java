package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class AllMessagesInThreadViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748097869-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748122008-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_name")
    private String threadName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748136698-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.locked_flag")
    private Boolean lockedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748150817-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.pinned_flag")
    private Boolean pinnedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748164057-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748177886-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748192286-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748207425-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_user_id")
    private Integer createdUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748223535-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.view_count")
    private Integer viewCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748237794-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748253164-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user_id")
    private Integer lastPostedUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748266833-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user")
    private String lastPostedUser;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748281753-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_ts")
    private OffsetDateTime postTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748307572-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_in_thread")
    private Integer postInThread;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748110608-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748116868-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748126918-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_name")
    public String getThreadName() {
        return threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748132218-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.thread_name")
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748141117-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.locked_flag")
    public Boolean getLockedFlag() {
        return lockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748145537-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.locked_flag")
    public void setLockedFlag(Boolean lockedFlag) {
        this.lockedFlag = lockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748155357-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.pinned_flag")
    public Boolean getPinnedFlag() {
        return pinnedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748159667-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.pinned_flag")
    public void setPinnedFlag(Boolean pinnedFlag) {
        this.pinnedFlag = pinnedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748169097-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748173746-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748183806-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748189326-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748198936-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748203975-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748214145-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_user_id")
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748218755-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.created_user_id")
    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748228165-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.view_count")
    public Integer getViewCount() {
        return viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748233485-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.view_count")
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748244444-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748248924-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748258074-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user_id")
    public Integer getLastPostedUserId() {
        return lastPostedUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748262614-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user_id")
    public void setLastPostedUserId(Integer lastPostedUserId) {
        this.lastPostedUserId = lastPostedUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748271203-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user")
    public String getLastPostedUser() {
        return lastPostedUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748276833-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.last_posted_user")
    public void setLastPostedUser(String lastPostedUser) {
        this.lastPostedUser = lastPostedUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748286853-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_ts")
    public OffsetDateTime getPostTs() {
        return postTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748291403-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_ts")
    public void setPostTs(OffsetDateTime postTs) {
        this.postTs = postTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748314252-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_in_thread")
    public Integer getPostInThread() {
        return postInThread;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.748319122-04:00", comments="Source field: zfgbb.all_messages_in_thread_view.post_in_thread")
    public void setPostInThread(Integer postInThread) {
        this.postInThread = postInThread;
    }

    @Override
    public Integer getPkId() {
        return null;
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