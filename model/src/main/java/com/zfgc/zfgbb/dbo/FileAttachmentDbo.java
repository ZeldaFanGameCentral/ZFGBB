package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class FileAttachmentDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510458449-04:00", comments="Source field: zfgbb.file_attachments.file_attachment_id")
    private Integer fileAttachmentId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510526537-04:00", comments="Source field: zfgbb.file_attachments.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510553536-04:00", comments="Source field: zfgbb.file_attachments.active_flag")
    private Boolean activeFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510576905-04:00", comments="Source field: zfgbb.file_attachments.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510598734-04:00", comments="Source field: zfgbb.file_attachments.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510621634-04:00", comments="Source field: zfgbb.file_attachments.content_resource_id")
    private Integer contentResourceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510664292-04:00", comments="Source field: zfgbb.file_attachments.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510689901-04:00", comments="Source field: zfgbb.file_attachments.downloads")
    private Integer downloads;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510479388-04:00", comments="Source field: zfgbb.file_attachments.file_attachment_id")
    public Integer getFileAttachmentId() {
        return fileAttachmentId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510513737-04:00", comments="Source field: zfgbb.file_attachments.file_attachment_id")
    public void setFileAttachmentId(Integer fileAttachmentId) {
        this.fileAttachmentId = fileAttachmentId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510535566-04:00", comments="Source field: zfgbb.file_attachments.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510545956-04:00", comments="Source field: zfgbb.file_attachments.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510561365-04:00", comments="Source field: zfgbb.file_attachments.active_flag")
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510570255-04:00", comments="Source field: zfgbb.file_attachments.active_flag")
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510584905-04:00", comments="Source field: zfgbb.file_attachments.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510592354-04:00", comments="Source field: zfgbb.file_attachments.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510605994-04:00", comments="Source field: zfgbb.file_attachments.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510615584-04:00", comments="Source field: zfgbb.file_attachments.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510628413-04:00", comments="Source field: zfgbb.file_attachments.content_resource_id")
    public Integer getContentResourceId() {
        return contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510654832-04:00", comments="Source field: zfgbb.file_attachments.content_resource_id")
    public void setContentResourceId(Integer contentResourceId) {
        this.contentResourceId = contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510671802-04:00", comments="Source field: zfgbb.file_attachments.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510682821-04:00", comments="Source field: zfgbb.file_attachments.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510696851-04:00", comments="Source field: zfgbb.file_attachments.downloads")
    public Integer getDownloads() {
        return downloads;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.510704971-04:00", comments="Source field: zfgbb.file_attachments.downloads")
    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Override
    public Integer getPkId() {
        return fileAttachmentId;
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