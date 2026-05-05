package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class ContentResourceDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45363184-04:00", comments="Source field: zfgbb.content_resource.content_resource_id")
    private Integer contentResourceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45365985-04:00", comments="Source field: zfgbb.content_resource.content_type_id")
    private Integer contentTypeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453680689-04:00", comments="Source field: zfgbb.content_resource.uploaded_user_id")
    private Integer uploadedUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453709418-04:00", comments="Source field: zfgbb.content_resource.filename")
    private String filename;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453730827-04:00", comments="Source field: zfgbb.content_resource.checksum")
    private String checksum;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453764926-04:00", comments="Source field: zfgbb.content_resource.file_ext")
    private String fileExt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453788215-04:00", comments="Source field: zfgbb.content_resource.mime_type")
    private String mimeType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453811695-04:00", comments="Source field: zfgbb.content_resource.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453834424-04:00", comments="Source field: zfgbb.content_resource.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453868273-04:00", comments="Source field: zfgbb.content_resource.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453890132-04:00", comments="Source field: zfgbb.content_resource.file_size")
    private Long fileSize;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45364408-04:00", comments="Source field: zfgbb.content_resource.content_resource_id")
    public Integer getContentResourceId() {
        return contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45365277-04:00", comments="Source field: zfgbb.content_resource.content_resource_id")
    public void setContentResourceId(Integer contentResourceId) {
        this.contentResourceId = contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453666589-04:00", comments="Source field: zfgbb.content_resource.content_type_id")
    public Integer getContentTypeId() {
        return contentTypeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453673959-04:00", comments="Source field: zfgbb.content_resource.content_type_id")
    public void setContentTypeId(Integer contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453694168-04:00", comments="Source field: zfgbb.content_resource.uploaded_user_id")
    public Integer getUploadedUserId() {
        return uploadedUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453701278-04:00", comments="Source field: zfgbb.content_resource.uploaded_user_id")
    public void setUploadedUserId(Integer uploadedUserId) {
        this.uploadedUserId = uploadedUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453717208-04:00", comments="Source field: zfgbb.content_resource.filename")
    public String getFilename() {
        return filename;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453725907-04:00", comments="Source field: zfgbb.content_resource.filename")
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453749267-04:00", comments="Source field: zfgbb.content_resource.checksum")
    public String getChecksum() {
        return checksum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453759786-04:00", comments="Source field: zfgbb.content_resource.checksum")
    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453771416-04:00", comments="Source field: zfgbb.content_resource.file_ext")
    public String getFileExt() {
        return fileExt;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453781576-04:00", comments="Source field: zfgbb.content_resource.file_ext")
    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453796805-04:00", comments="Source field: zfgbb.content_resource.mime_type")
    public String getMimeType() {
        return mimeType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453805475-04:00", comments="Source field: zfgbb.content_resource.mime_type")
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453821004-04:00", comments="Source field: zfgbb.content_resource.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453829574-04:00", comments="Source field: zfgbb.content_resource.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453853973-04:00", comments="Source field: zfgbb.content_resource.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453861693-04:00", comments="Source field: zfgbb.content_resource.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453874763-04:00", comments="Source field: zfgbb.content_resource.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453883042-04:00", comments="Source field: zfgbb.content_resource.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453896802-04:00", comments="Source field: zfgbb.content_resource.file_size")
    public Long getFileSize() {
        return fileSize;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.453903452-04:00", comments="Source field: zfgbb.content_resource.file_size")
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public Integer getPkId() {
        return contentResourceId;
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