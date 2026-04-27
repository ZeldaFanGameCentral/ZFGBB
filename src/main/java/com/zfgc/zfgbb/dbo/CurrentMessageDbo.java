package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class CurrentMessageDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377648008-04:00", comments="Source field: zfgbb.current_message_view.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377667588-04:00", comments="Source field: zfgbb.current_message_view.owner_id")
    private Integer ownerId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377682167-04:00", comments="Source field: zfgbb.current_message_view.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377695697-04:00", comments="Source field: zfgbb.current_message_view.message_text")
    private String messageText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377710486-04:00", comments="Source field: zfgbb.current_message_view.message_history_id")
    private Integer messageHistoryId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377724436-04:00", comments="Source field: zfgbb.current_message_view.post_in_thread")
    private Integer postInThread;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377737115-04:00", comments="Source field: zfgbb.current_message_view.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377655508-04:00", comments="Source field: zfgbb.current_message_view.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377662058-04:00", comments="Source field: zfgbb.current_message_view.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377672487-04:00", comments="Source field: zfgbb.current_message_view.owner_id")
    public Integer getOwnerId() {
        return ownerId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377677687-04:00", comments="Source field: zfgbb.current_message_view.owner_id")
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377686807-04:00", comments="Source field: zfgbb.current_message_view.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377691487-04:00", comments="Source field: zfgbb.current_message_view.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377699977-04:00", comments="Source field: zfgbb.current_message_view.message_text")
    public String getMessageText() {
        return messageText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377706056-04:00", comments="Source field: zfgbb.current_message_view.message_text")
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377714856-04:00", comments="Source field: zfgbb.current_message_view.message_history_id")
    public Integer getMessageHistoryId() {
        return messageHistoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377719356-04:00", comments="Source field: zfgbb.current_message_view.message_history_id")
    public void setMessageHistoryId(Integer messageHistoryId) {
        this.messageHistoryId = messageHistoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377728816-04:00", comments="Source field: zfgbb.current_message_view.post_in_thread")
    public Integer getPostInThread() {
        return postInThread;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377733275-04:00", comments="Source field: zfgbb.current_message_view.post_in_thread")
    public void setPostInThread(Integer postInThread) {
        this.postInThread = postInThread;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377741765-04:00", comments="Source field: zfgbb.current_message_view.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.377746095-04:00", comments="Source field: zfgbb.current_message_view.created_ts")
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