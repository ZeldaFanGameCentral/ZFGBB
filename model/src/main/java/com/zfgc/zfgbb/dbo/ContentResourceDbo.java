package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class ContentResourceDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696781316-04:00", comments="Source field: zfgbb.content_resource.content_resource_id")
    private Integer contentResourceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696814155-04:00", comments="Source field: zfgbb.content_resource.content_type_id")
    private Integer contentTypeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696837464-04:00", comments="Source field: zfgbb.content_resource.uploaded_user_id")
    private Integer uploadedUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696859573-04:00", comments="Source field: zfgbb.content_resource.filename")
    private String filename;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696881513-04:00", comments="Source field: zfgbb.content_resource.checksum")
    private String checksum;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696906722-04:00", comments="Source field: zfgbb.content_resource.file_ext")
    private String fileExt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696929981-04:00", comments="Source field: zfgbb.content_resource.mime_type")
    private String mimeType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69695646-04:00", comments="Source field: zfgbb.content_resource.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.6969799-04:00", comments="Source field: zfgbb.content_resource.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697011289-04:00", comments="Source field: zfgbb.content_resource.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697035338-04:00", comments="Source field: zfgbb.content_resource.file_size")
    private Long fileSize;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697054947-04:00", comments="Source field: zfgbb.content_resource.storage_dir")
    private String storageDir;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696794555-04:00", comments="Source field: zfgbb.content_resource.content_resource_id")
    public Integer getContentResourceId() {
        return contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696807735-04:00", comments="Source field: zfgbb.content_resource.content_resource_id")
    public void setContentResourceId(Integer contentResourceId) {
        this.contentResourceId = contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696823625-04:00", comments="Source field: zfgbb.content_resource.content_type_id")
    public Integer getContentTypeId() {
        return contentTypeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696832324-04:00", comments="Source field: zfgbb.content_resource.content_type_id")
    public void setContentTypeId(Integer contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696845894-04:00", comments="Source field: zfgbb.content_resource.uploaded_user_id")
    public Integer getUploadedUserId() {
        return uploadedUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696853154-04:00", comments="Source field: zfgbb.content_resource.uploaded_user_id")
    public void setUploadedUserId(Integer uploadedUserId) {
        this.uploadedUserId = uploadedUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696866213-04:00", comments="Source field: zfgbb.content_resource.filename")
    public String getFilename() {
        return filename;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696876153-04:00", comments="Source field: zfgbb.content_resource.filename")
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696888272-04:00", comments="Source field: zfgbb.content_resource.checksum")
    public String getChecksum() {
        return checksum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696897172-04:00", comments="Source field: zfgbb.content_resource.checksum")
    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696913292-04:00", comments="Source field: zfgbb.content_resource.file_ext")
    public String getFileExt() {
        return fileExt;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696922431-04:00", comments="Source field: zfgbb.content_resource.file_ext")
    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696936471-04:00", comments="Source field: zfgbb.content_resource.mime_type")
    public String getMimeType() {
        return mimeType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696945991-04:00", comments="Source field: zfgbb.content_resource.mime_type")
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69696606-04:00", comments="Source field: zfgbb.content_resource.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69697343-04:00", comments="Source field: zfgbb.content_resource.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.696996949-04:00", comments="Source field: zfgbb.content_resource.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697004859-04:00", comments="Source field: zfgbb.content_resource.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697017768-04:00", comments="Source field: zfgbb.content_resource.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697026398-04:00", comments="Source field: zfgbb.content_resource.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697042568-04:00", comments="Source field: zfgbb.content_resource.file_size")
    public Long getFileSize() {
        return fileSize;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697050267-04:00", comments="Source field: zfgbb.content_resource.file_size")
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697061137-04:00", comments="Source field: zfgbb.content_resource.storage_dir")
    public String getStorageDir() {
        return storageDir;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.697070677-04:00", comments="Source field: zfgbb.content_resource.storage_dir")
    public void setStorageDir(String storageDir) {
        this.storageDir = storageDir;
    }

    @Override
    public Integer getPkId() {
        return contentResourceId;
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