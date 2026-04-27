package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class ContentResourceDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369191106-04:00", comments="Source field: zfgbb.content_resource.content_resource_id")
    private Integer contentResourceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369212865-04:00", comments="Source field: zfgbb.content_resource.content_type_id")
    private Integer contentTypeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369227995-04:00", comments="Source field: zfgbb.content_resource.uploaded_user_id")
    private Integer uploadedUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369242064-04:00", comments="Source field: zfgbb.content_resource.filename")
    private String filename;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369258114-04:00", comments="Source field: zfgbb.content_resource.checksum")
    private String checksum;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369273033-04:00", comments="Source field: zfgbb.content_resource.file_ext")
    private String fileExt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369287703-04:00", comments="Source field: zfgbb.content_resource.mime_type")
    private String mimeType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369302032-04:00", comments="Source field: zfgbb.content_resource.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369317252-04:00", comments="Source field: zfgbb.content_resource.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369331231-04:00", comments="Source field: zfgbb.content_resource.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369347911-04:00", comments="Source field: zfgbb.content_resource.file_size")
    private Long fileSize;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369200076-04:00", comments="Source field: zfgbb.content_resource.content_resource_id")
    public Integer getContentResourceId() {
        return contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369207245-04:00", comments="Source field: zfgbb.content_resource.content_resource_id")
    public void setContentResourceId(Integer contentResourceId) {
        this.contentResourceId = contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369217915-04:00", comments="Source field: zfgbb.content_resource.content_type_id")
    public Integer getContentTypeId() {
        return contentTypeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369223345-04:00", comments="Source field: zfgbb.content_resource.content_type_id")
    public void setContentTypeId(Integer contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369232685-04:00", comments="Source field: zfgbb.content_resource.uploaded_user_id")
    public Integer getUploadedUserId() {
        return uploadedUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369237754-04:00", comments="Source field: zfgbb.content_resource.uploaded_user_id")
    public void setUploadedUserId(Integer uploadedUserId) {
        this.uploadedUserId = uploadedUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369246634-04:00", comments="Source field: zfgbb.content_resource.filename")
    public String getFilename() {
        return filename;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369253494-04:00", comments="Source field: zfgbb.content_resource.filename")
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369262584-04:00", comments="Source field: zfgbb.content_resource.checksum")
    public String getChecksum() {
        return checksum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369268573-04:00", comments="Source field: zfgbb.content_resource.checksum")
    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369277413-04:00", comments="Source field: zfgbb.content_resource.file_ext")
    public String getFileExt() {
        return fileExt;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369283223-04:00", comments="Source field: zfgbb.content_resource.file_ext")
    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369292023-04:00", comments="Source field: zfgbb.content_resource.mime_type")
    public String getMimeType() {
        return mimeType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369297572-04:00", comments="Source field: zfgbb.content_resource.mime_type")
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369307612-04:00", comments="Source field: zfgbb.content_resource.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369312702-04:00", comments="Source field: zfgbb.content_resource.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369322232-04:00", comments="Source field: zfgbb.content_resource.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369326942-04:00", comments="Source field: zfgbb.content_resource.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369335611-04:00", comments="Source field: zfgbb.content_resource.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369341641-04:00", comments="Source field: zfgbb.content_resource.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.369352781-04:00", comments="Source field: zfgbb.content_resource.file_size")
    public Long getFileSize() {
        return fileSize;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.36935854-04:00", comments="Source field: zfgbb.content_resource.file_size")
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