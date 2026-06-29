package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class AvatarDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.675990263-04:00", comments="Source field: zfgbb.avatar.avatar_id")
    private Integer avatarId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676043751-04:00", comments="Source field: zfgbb.avatar.url")
    private String url;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.67607698-04:00", comments="Source field: zfgbb.avatar.active_flag")
    private Boolean activeFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676107439-04:00", comments="Source field: zfgbb.avatar.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676143708-04:00", comments="Source field: zfgbb.avatar.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676173277-04:00", comments="Source field: zfgbb.avatar.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676202426-04:00", comments="Source field: zfgbb.avatar.content_resource_id")
    private Integer contentResourceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676014522-04:00", comments="Source field: zfgbb.avatar.avatar_id")
    public Integer getAvatarId() {
        return avatarId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676032211-04:00", comments="Source field: zfgbb.avatar.avatar_id")
    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676054461-04:00", comments="Source field: zfgbb.avatar.url")
    public String getUrl() {
        return url;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.67606861-04:00", comments="Source field: zfgbb.avatar.url")
    public void setUrl(String url) {
        this.url = url;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.67608831-04:00", comments="Source field: zfgbb.avatar.active_flag")
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676098869-04:00", comments="Source field: zfgbb.avatar.active_flag")
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676118119-04:00", comments="Source field: zfgbb.avatar.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676127159-04:00", comments="Source field: zfgbb.avatar.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676156218-04:00", comments="Source field: zfgbb.avatar.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676165747-04:00", comments="Source field: zfgbb.avatar.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676182847-04:00", comments="Source field: zfgbb.avatar.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676194446-04:00", comments="Source field: zfgbb.avatar.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676211886-04:00", comments="Source field: zfgbb.avatar.content_resource_id")
    public Integer getContentResourceId() {
        return contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.676237975-04:00", comments="Source field: zfgbb.avatar.content_resource_id")
    public void setContentResourceId(Integer contentResourceId) {
        this.contentResourceId = contentResourceId;
    }

    @Override
    public Integer getPkId() {
        return avatarId;
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