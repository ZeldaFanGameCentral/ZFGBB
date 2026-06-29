package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class ContentCollectionDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723207793-04:00", comments="Source field: zfgbb.content_collection.content_collection_id")
    private Integer contentCollectionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723227313-04:00", comments="Source field: zfgbb.content_collection.code")
    private String code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723245622-04:00", comments="Source field: zfgbb.content_collection.title")
    private String title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723260542-04:00", comments="Source field: zfgbb.content_collection.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723275211-04:00", comments="Source field: zfgbb.content_collection.kind")
    private String kind;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723289521-04:00", comments="Source field: zfgbb.content_collection.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.72332091-04:00", comments="Source field: zfgbb.content_collection.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723335549-04:00", comments="Source field: zfgbb.content_collection.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723216093-04:00", comments="Source field: zfgbb.content_collection.content_collection_id")
    public Integer getContentCollectionId() {
        return contentCollectionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723223423-04:00", comments="Source field: zfgbb.content_collection.content_collection_id")
    public void setContentCollectionId(Integer contentCollectionId) {
        this.contentCollectionId = contentCollectionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723234873-04:00", comments="Source field: zfgbb.content_collection.code")
    public String getCode() {
        return code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723242232-04:00", comments="Source field: zfgbb.content_collection.code")
    public void setCode(String code) {
        this.code = code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723250512-04:00", comments="Source field: zfgbb.content_collection.title")
    public String getTitle() {
        return title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723255712-04:00", comments="Source field: zfgbb.content_collection.title")
    public void setTitle(String title) {
        this.title = title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723265312-04:00", comments="Source field: zfgbb.content_collection.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723270421-04:00", comments="Source field: zfgbb.content_collection.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723279871-04:00", comments="Source field: zfgbb.content_collection.kind")
    public String getKind() {
        return kind;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723284851-04:00", comments="Source field: zfgbb.content_collection.kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723295081-04:00", comments="Source field: zfgbb.content_collection.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.72331513-04:00", comments="Source field: zfgbb.content_collection.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.72332616-04:00", comments="Source field: zfgbb.content_collection.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.72333226-04:00", comments="Source field: zfgbb.content_collection.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723340339-04:00", comments="Source field: zfgbb.content_collection.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.723345359-04:00", comments="Source field: zfgbb.content_collection.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return contentCollectionId;
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