package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class ResourceDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716620838-04:00", comments="Source field: zfgbb.resource.content_entity_id")
    private Integer contentEntityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716642418-04:00", comments="Source field: zfgbb.resource.resource_type")
    private String resourceType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716658907-04:00", comments="Source field: zfgbb.resource.download_content_resource_id")
    private Integer downloadContentResourceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716674207-04:00", comments="Source field: zfgbb.resource.download_url")
    private String downloadUrl;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716689756-04:00", comments="Source field: zfgbb.resource.file_size")
    private Long fileSize;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716630528-04:00", comments="Source field: zfgbb.resource.content_entity_id")
    public Integer getContentEntityId() {
        return contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716636658-04:00", comments="Source field: zfgbb.resource.content_entity_id")
    public void setContentEntityId(Integer contentEntityId) {
        this.contentEntityId = contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716647758-04:00", comments="Source field: zfgbb.resource.resource_type")
    public String getResourceType() {
        return resourceType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716653787-04:00", comments="Source field: zfgbb.resource.resource_type")
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716664157-04:00", comments="Source field: zfgbb.resource.download_content_resource_id")
    public Integer getDownloadContentResourceId() {
        return downloadContentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716669237-04:00", comments="Source field: zfgbb.resource.download_content_resource_id")
    public void setDownloadContentResourceId(Integer downloadContentResourceId) {
        this.downloadContentResourceId = downloadContentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716679217-04:00", comments="Source field: zfgbb.resource.download_url")
    public String getDownloadUrl() {
        return downloadUrl;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716684696-04:00", comments="Source field: zfgbb.resource.download_url")
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716695026-04:00", comments="Source field: zfgbb.resource.file_size")
    public Long getFileSize() {
        return fileSize;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716700006-04:00", comments="Source field: zfgbb.resource.file_size")
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public Integer getPkId() {
        return contentEntityId;
    }

    @Override
    public OffsetDateTime getCreatedTime() {
        return null;
    }

    @Override
    public OffsetDateTime getUpdatedTime() {
        return null;
    }
}