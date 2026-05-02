package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class CurrentMessageDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528379797-04:00", comments="Source field: zfgbb.current_message_view.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528407146-04:00", comments="Source field: zfgbb.current_message_view.owner_id")
    private Integer ownerId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528426055-04:00", comments="Source field: zfgbb.current_message_view.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528446084-04:00", comments="Source field: zfgbb.current_message_view.message_text")
    private String messageText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528467004-04:00", comments="Source field: zfgbb.current_message_view.message_history_id")
    private Integer messageHistoryId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528486383-04:00", comments="Source field: zfgbb.current_message_view.post_in_thread")
    private Integer postInThread;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528505203-04:00", comments="Source field: zfgbb.current_message_view.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528391646-04:00", comments="Source field: zfgbb.current_message_view.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528399956-04:00", comments="Source field: zfgbb.current_message_view.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528414166-04:00", comments="Source field: zfgbb.current_message_view.owner_id")
    public Integer getOwnerId() {
        return ownerId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528421035-04:00", comments="Source field: zfgbb.current_message_view.owner_id")
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528433295-04:00", comments="Source field: zfgbb.current_message_view.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528441515-04:00", comments="Source field: zfgbb.current_message_view.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528452314-04:00", comments="Source field: zfgbb.current_message_view.message_text")
    public String getMessageText() {
        return messageText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528460624-04:00", comments="Source field: zfgbb.current_message_view.message_text")
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528473424-04:00", comments="Source field: zfgbb.current_message_view.message_history_id")
    public Integer getMessageHistoryId() {
        return messageHistoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528481693-04:00", comments="Source field: zfgbb.current_message_view.message_history_id")
    public void setMessageHistoryId(Integer messageHistoryId) {
        this.messageHistoryId = messageHistoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528492553-04:00", comments="Source field: zfgbb.current_message_view.post_in_thread")
    public Integer getPostInThread() {
        return postInThread;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528500593-04:00", comments="Source field: zfgbb.current_message_view.post_in_thread")
    public void setPostInThread(Integer postInThread) {
        this.postInThread = postInThread;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528513732-04:00", comments="Source field: zfgbb.current_message_view.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.528522522-04:00", comments="Source field: zfgbb.current_message_view.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
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
        return null;
    }
}