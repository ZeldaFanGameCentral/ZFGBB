package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class FileAttachmentDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688488194-04:00", comments="Source field: zfgbb.file_attachments.file_attachment_id")
    private Integer fileAttachmentId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688520573-04:00", comments="Source field: zfgbb.file_attachments.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688545532-04:00", comments="Source field: zfgbb.file_attachments.active_flag")
    private Boolean activeFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688572541-04:00", comments="Source field: zfgbb.file_attachments.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.6885986-04:00", comments="Source field: zfgbb.file_attachments.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.68862435-04:00", comments="Source field: zfgbb.file_attachments.content_resource_id")
    private Integer contentResourceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688646519-04:00", comments="Source field: zfgbb.file_attachments.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688674288-04:00", comments="Source field: zfgbb.file_attachments.downloads")
    private Integer downloads;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688501233-04:00", comments="Source field: zfgbb.file_attachments.file_attachment_id")
    public Integer getFileAttachmentId() {
        return fileAttachmentId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688511863-04:00", comments="Source field: zfgbb.file_attachments.file_attachment_id")
    public void setFileAttachmentId(Integer fileAttachmentId) {
        this.fileAttachmentId = fileAttachmentId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688529213-04:00", comments="Source field: zfgbb.file_attachments.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688538302-04:00", comments="Source field: zfgbb.file_attachments.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688553512-04:00", comments="Source field: zfgbb.file_attachments.active_flag")
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688564801-04:00", comments="Source field: zfgbb.file_attachments.active_flag")
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688582951-04:00", comments="Source field: zfgbb.file_attachments.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688591731-04:00", comments="Source field: zfgbb.file_attachments.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.68860715-04:00", comments="Source field: zfgbb.file_attachments.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.68861627-04:00", comments="Source field: zfgbb.file_attachments.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688631719-04:00", comments="Source field: zfgbb.file_attachments.content_resource_id")
    public Integer getContentResourceId() {
        return contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688640139-04:00", comments="Source field: zfgbb.file_attachments.content_resource_id")
    public void setContentResourceId(Integer contentResourceId) {
        this.contentResourceId = contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688654349-04:00", comments="Source field: zfgbb.file_attachments.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688665068-04:00", comments="Source field: zfgbb.file_attachments.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688683208-04:00", comments="Source field: zfgbb.file_attachments.downloads")
    public Integer getDownloads() {
        return downloads;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.688698407-04:00", comments="Source field: zfgbb.file_attachments.downloads")
    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Override
    public Integer getPkId() {
        return fileAttachmentId;
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