package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class FileAttachmentDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.443787965-04:00", comments="Source field: zfgbb.file_attachments.file_attachment_id")
    private Integer fileAttachmentId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.443853663-04:00", comments="Source field: zfgbb.file_attachments.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.443892392-04:00", comments="Source field: zfgbb.file_attachments.active_flag")
    private Boolean activeFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44392711-04:00", comments="Source field: zfgbb.file_attachments.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44395259-04:00", comments="Source field: zfgbb.file_attachments.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.443974899-04:00", comments="Source field: zfgbb.file_attachments.content_resource_id")
    private Integer contentResourceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.443998278-04:00", comments="Source field: zfgbb.file_attachments.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.444022887-04:00", comments="Source field: zfgbb.file_attachments.downloads")
    private Integer downloads;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.443804374-04:00", comments="Source field: zfgbb.file_attachments.file_attachment_id")
    public Integer getFileAttachmentId() {
        return fileAttachmentId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.443840623-04:00", comments="Source field: zfgbb.file_attachments.file_attachment_id")
    public void setFileAttachmentId(Integer fileAttachmentId) {
        this.fileAttachmentId = fileAttachmentId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.443866892-04:00", comments="Source field: zfgbb.file_attachments.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.443882232-04:00", comments="Source field: zfgbb.file_attachments.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.443906831-04:00", comments="Source field: zfgbb.file_attachments.active_flag")
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.443920981-04:00", comments="Source field: zfgbb.file_attachments.active_flag")
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44393773-04:00", comments="Source field: zfgbb.file_attachments.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44394575-04:00", comments="Source field: zfgbb.file_attachments.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.443960599-04:00", comments="Source field: zfgbb.file_attachments.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.443968409-04:00", comments="Source field: zfgbb.file_attachments.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.443982179-04:00", comments="Source field: zfgbb.file_attachments.content_resource_id")
    public Integer getContentResourceId() {
        return contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.443991218-04:00", comments="Source field: zfgbb.file_attachments.content_resource_id")
    public void setContentResourceId(Integer contentResourceId) {
        this.contentResourceId = contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.444005718-04:00", comments="Source field: zfgbb.file_attachments.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.444015928-04:00", comments="Source field: zfgbb.file_attachments.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.444031167-04:00", comments="Source field: zfgbb.file_attachments.downloads")
    public Integer getDownloads() {
        return downloads;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.444039377-04:00", comments="Source field: zfgbb.file_attachments.downloads")
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