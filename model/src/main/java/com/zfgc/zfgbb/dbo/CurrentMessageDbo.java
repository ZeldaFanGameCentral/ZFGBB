package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class CurrentMessageDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392130248-04:00", comments="Source field: zfgbb.current_message_view.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392149357-04:00", comments="Source field: zfgbb.current_message_view.owner_id")
    private Integer ownerId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392163466-04:00", comments="Source field: zfgbb.current_message_view.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392177076-04:00", comments="Source field: zfgbb.current_message_view.message_text")
    private String messageText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392190826-04:00", comments="Source field: zfgbb.current_message_view.message_history_id")
    private Integer messageHistoryId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392204555-04:00", comments="Source field: zfgbb.current_message_view.post_in_thread")
    private Integer postInThread;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392217835-04:00", comments="Source field: zfgbb.current_message_view.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392231684-04:00", comments="Source field: zfgbb.current_message_view.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392245414-04:00", comments="Source field: zfgbb.current_message_view.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392138617-04:00", comments="Source field: zfgbb.current_message_view.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392144427-04:00", comments="Source field: zfgbb.current_message_view.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392154227-04:00", comments="Source field: zfgbb.current_message_view.owner_id")
    public Integer getOwnerId() {
        return ownerId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392158887-04:00", comments="Source field: zfgbb.current_message_view.owner_id")
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392168026-04:00", comments="Source field: zfgbb.current_message_view.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392172646-04:00", comments="Source field: zfgbb.current_message_view.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392181586-04:00", comments="Source field: zfgbb.current_message_view.message_text")
    public String getMessageText() {
        return messageText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392186456-04:00", comments="Source field: zfgbb.current_message_view.message_text")
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392195485-04:00", comments="Source field: zfgbb.current_message_view.message_history_id")
    public Integer getMessageHistoryId() {
        return messageHistoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392200125-04:00", comments="Source field: zfgbb.current_message_view.message_history_id")
    public void setMessageHistoryId(Integer messageHistoryId) {
        this.messageHistoryId = messageHistoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392209015-04:00", comments="Source field: zfgbb.current_message_view.post_in_thread")
    public Integer getPostInThread() {
        return postInThread;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392213455-04:00", comments="Source field: zfgbb.current_message_view.post_in_thread")
    public void setPostInThread(Integer postInThread) {
        this.postInThread = postInThread;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392222745-04:00", comments="Source field: zfgbb.current_message_view.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392227374-04:00", comments="Source field: zfgbb.current_message_view.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392236594-04:00", comments="Source field: zfgbb.current_message_view.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392240944-04:00", comments="Source field: zfgbb.current_message_view.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392249814-04:00", comments="Source field: zfgbb.current_message_view.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.392254224-04:00", comments="Source field: zfgbb.current_message_view.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
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