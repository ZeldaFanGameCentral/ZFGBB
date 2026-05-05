package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class FileAttachmentDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238291088-04:00", comments="Source field: zfgbb.file_attachments.file_attachment_id")
    private Integer fileAttachmentId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238320717-04:00", comments="Source field: zfgbb.file_attachments.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238351526-04:00", comments="Source field: zfgbb.file_attachments.active_flag")
    private Boolean activeFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238373385-04:00", comments="Source field: zfgbb.file_attachments.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238396104-04:00", comments="Source field: zfgbb.file_attachments.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238427143-04:00", comments="Source field: zfgbb.file_attachments.content_resource_id")
    private Integer contentResourceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238449013-04:00", comments="Source field: zfgbb.file_attachments.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238473042-04:00", comments="Source field: zfgbb.file_attachments.downloads")
    private Integer downloads;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238302947-04:00", comments="Source field: zfgbb.file_attachments.file_attachment_id")
    public Integer getFileAttachmentId() {
        return fileAttachmentId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238311907-04:00", comments="Source field: zfgbb.file_attachments.file_attachment_id")
    public void setFileAttachmentId(Integer fileAttachmentId) {
        this.fileAttachmentId = fileAttachmentId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238328857-04:00", comments="Source field: zfgbb.file_attachments.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238343476-04:00", comments="Source field: zfgbb.file_attachments.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238358766-04:00", comments="Source field: zfgbb.file_attachments.active_flag")
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238367055-04:00", comments="Source field: zfgbb.file_attachments.active_flag")
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238381705-04:00", comments="Source field: zfgbb.file_attachments.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238389735-04:00", comments="Source field: zfgbb.file_attachments.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238411464-04:00", comments="Source field: zfgbb.file_attachments.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238420094-04:00", comments="Source field: zfgbb.file_attachments.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238434603-04:00", comments="Source field: zfgbb.file_attachments.content_resource_id")
    public Integer getContentResourceId() {
        return contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238442423-04:00", comments="Source field: zfgbb.file_attachments.content_resource_id")
    public void setContentResourceId(Integer contentResourceId) {
        this.contentResourceId = contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238455953-04:00", comments="Source field: zfgbb.file_attachments.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238466172-04:00", comments="Source field: zfgbb.file_attachments.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238480842-04:00", comments="Source field: zfgbb.file_attachments.downloads")
    public Integer getDownloads() {
        return downloads;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.238488372-04:00", comments="Source field: zfgbb.file_attachments.downloads")
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