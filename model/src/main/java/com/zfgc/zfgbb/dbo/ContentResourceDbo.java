package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class ContentResourceDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246742729-04:00", comments="Source field: zfgbb.content_resource.content_resource_id")
    private Integer contentResourceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246773089-04:00", comments="Source field: zfgbb.content_resource.content_type_id")
    private Integer contentTypeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246795568-04:00", comments="Source field: zfgbb.content_resource.uploaded_user_id")
    private Integer uploadedUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246816587-04:00", comments="Source field: zfgbb.content_resource.filename")
    private String filename;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246842816-04:00", comments="Source field: zfgbb.content_resource.checksum")
    private String checksum;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246867726-04:00", comments="Source field: zfgbb.content_resource.file_ext")
    private String fileExt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246889865-04:00", comments="Source field: zfgbb.content_resource.mime_type")
    private String mimeType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246933603-04:00", comments="Source field: zfgbb.content_resource.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.247005851-04:00", comments="Source field: zfgbb.content_resource.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.24704387-04:00", comments="Source field: zfgbb.content_resource.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.247068469-04:00", comments="Source field: zfgbb.content_resource.file_size")
    private Long fileSize;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246755759-04:00", comments="Source field: zfgbb.content_resource.content_resource_id")
    public Integer getContentResourceId() {
        return contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246765439-04:00", comments="Source field: zfgbb.content_resource.content_resource_id")
    public void setContentResourceId(Integer contentResourceId) {
        this.contentResourceId = contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246780768-04:00", comments="Source field: zfgbb.content_resource.content_type_id")
    public Integer getContentTypeId() {
        return contentTypeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246788708-04:00", comments="Source field: zfgbb.content_resource.content_type_id")
    public void setContentTypeId(Integer contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246802658-04:00", comments="Source field: zfgbb.content_resource.uploaded_user_id")
    public Integer getUploadedUserId() {
        return uploadedUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246809947-04:00", comments="Source field: zfgbb.content_resource.uploaded_user_id")
    public void setUploadedUserId(Integer uploadedUserId) {
        this.uploadedUserId = uploadedUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246823327-04:00", comments="Source field: zfgbb.content_resource.filename")
    public String getFilename() {
        return filename;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246832837-04:00", comments="Source field: zfgbb.content_resource.filename")
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246850066-04:00", comments="Source field: zfgbb.content_resource.checksum")
    public String getChecksum() {
        return checksum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246861056-04:00", comments="Source field: zfgbb.content_resource.checksum")
    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246874485-04:00", comments="Source field: zfgbb.content_resource.file_ext")
    public String getFileExt() {
        return fileExt;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246883115-04:00", comments="Source field: zfgbb.content_resource.file_ext")
    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246896625-04:00", comments="Source field: zfgbb.content_resource.mime_type")
    public String getMimeType() {
        return mimeType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246905054-04:00", comments="Source field: zfgbb.content_resource.mime_type")
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246977462-04:00", comments="Source field: zfgbb.content_resource.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.246992532-04:00", comments="Source field: zfgbb.content_resource.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.24702697-04:00", comments="Source field: zfgbb.content_resource.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.24703553-04:00", comments="Source field: zfgbb.content_resource.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.24705095-04:00", comments="Source field: zfgbb.content_resource.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.247061709-04:00", comments="Source field: zfgbb.content_resource.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.247075239-04:00", comments="Source field: zfgbb.content_resource.file_size")
    public Long getFileSize() {
        return fileSize;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.247085359-04:00", comments="Source field: zfgbb.content_resource.file_size")
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