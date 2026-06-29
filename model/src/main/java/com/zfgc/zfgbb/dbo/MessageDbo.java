package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class MessageDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330547515-04:00", comments="Source field: zfgbb.message.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330575464-04:00", comments="Source field: zfgbb.message.owner_id")
    private Integer ownerId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330595544-04:00", comments="Source field: zfgbb.message.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330616543-04:00", comments="Source field: zfgbb.message.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330638642-04:00", comments="Source field: zfgbb.message.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330659472-04:00", comments="Source field: zfgbb.message.post_in_thread")
    private Integer postInThread;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330679601-04:00", comments="Source field: zfgbb.message.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.33070423-04:00", comments="Source field: zfgbb.message.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330559435-04:00", comments="Source field: zfgbb.message.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330568595-04:00", comments="Source field: zfgbb.message.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330582854-04:00", comments="Source field: zfgbb.message.owner_id")
    public Integer getOwnerId() {
        return ownerId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330590714-04:00", comments="Source field: zfgbb.message.owner_id")
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330602723-04:00", comments="Source field: zfgbb.message.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330610203-04:00", comments="Source field: zfgbb.message.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330624553-04:00", comments="Source field: zfgbb.message.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330632362-04:00", comments="Source field: zfgbb.message.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330647672-04:00", comments="Source field: zfgbb.message.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330654812-04:00", comments="Source field: zfgbb.message.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330666251-04:00", comments="Source field: zfgbb.message.post_in_thread")
    public Integer getPostInThread() {
        return postInThread;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330673521-04:00", comments="Source field: zfgbb.message.post_in_thread")
    public void setPostInThread(Integer postInThread) {
        this.postInThread = postInThread;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.330686191-04:00", comments="Source field: zfgbb.message.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.33069541-04:00", comments="Source field: zfgbb.message.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.33071164-04:00", comments="Source field: zfgbb.message.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T12:50:27.33072151-04:00", comments="Source field: zfgbb.message.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Override
    public Integer getPkId() {
        return messageId;
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