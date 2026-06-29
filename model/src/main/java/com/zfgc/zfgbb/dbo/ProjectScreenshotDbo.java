package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class ProjectScreenshotDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.720925764-04:00", comments="Source field: zfgbb.project_screenshot.project_screenshot_id")
    private Integer projectScreenshotId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.720946614-04:00", comments="Source field: zfgbb.project_screenshot.content_entity_id")
    private Integer contentEntityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.720970913-04:00", comments="Source field: zfgbb.project_screenshot.content_resource_id")
    private Integer contentResourceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.720986362-04:00", comments="Source field: zfgbb.project_screenshot.caption")
    private String caption;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.721002352-04:00", comments="Source field: zfgbb.project_screenshot.ordinal")
    private Integer ordinal;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.721024131-04:00", comments="Source field: zfgbb.project_screenshot.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.72105167-04:00", comments="Source field: zfgbb.project_screenshot.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.7210676-04:00", comments="Source field: zfgbb.project_screenshot.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.720934774-04:00", comments="Source field: zfgbb.project_screenshot.project_screenshot_id")
    public Integer getProjectScreenshotId() {
        return projectScreenshotId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.720940994-04:00", comments="Source field: zfgbb.project_screenshot.project_screenshot_id")
    public void setProjectScreenshotId(Integer projectScreenshotId) {
        this.projectScreenshotId = projectScreenshotId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.720959843-04:00", comments="Source field: zfgbb.project_screenshot.content_entity_id")
    public Integer getContentEntityId() {
        return contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.720965523-04:00", comments="Source field: zfgbb.project_screenshot.content_entity_id")
    public void setContentEntityId(Integer contentEntityId) {
        this.contentEntityId = contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.720976353-04:00", comments="Source field: zfgbb.project_screenshot.content_resource_id")
    public Integer getContentResourceId() {
        return contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.720981393-04:00", comments="Source field: zfgbb.project_screenshot.content_resource_id")
    public void setContentResourceId(Integer contentResourceId) {
        this.contentResourceId = contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.720991332-04:00", comments="Source field: zfgbb.project_screenshot.caption")
    public String getCaption() {
        return caption;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.720997422-04:00", comments="Source field: zfgbb.project_screenshot.caption")
    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.721007272-04:00", comments="Source field: zfgbb.project_screenshot.ordinal")
    public Integer getOrdinal() {
        return ordinal;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.721012092-04:00", comments="Source field: zfgbb.project_screenshot.ordinal")
    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.721033691-04:00", comments="Source field: zfgbb.project_screenshot.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.721038981-04:00", comments="Source field: zfgbb.project_screenshot.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.72105889-04:00", comments="Source field: zfgbb.project_screenshot.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.72106418-04:00", comments="Source field: zfgbb.project_screenshot.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.72107299-04:00", comments="Source field: zfgbb.project_screenshot.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.72108003-04:00", comments="Source field: zfgbb.project_screenshot.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return projectScreenshotId;
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