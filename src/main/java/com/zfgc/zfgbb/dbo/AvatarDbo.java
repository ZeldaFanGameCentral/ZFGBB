package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class AvatarDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.355918692-04:00", comments="Source field: zfgbb.avatar.avatar_id")
    private Integer avatarId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.35596556-04:00", comments="Source field: zfgbb.avatar.url")
    private String url;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356002519-04:00", comments="Source field: zfgbb.avatar.active_flag")
    private Boolean activeFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356028328-04:00", comments="Source field: zfgbb.avatar.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356075247-04:00", comments="Source field: zfgbb.avatar.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356101556-04:00", comments="Source field: zfgbb.avatar.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356124595-04:00", comments="Source field: zfgbb.avatar.content_resource_id")
    private Integer contentResourceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.355938471-04:00", comments="Source field: zfgbb.avatar.avatar_id")
    public Integer getAvatarId() {
        return avatarId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.355955031-04:00", comments="Source field: zfgbb.avatar.avatar_id")
    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.35598094-04:00", comments="Source field: zfgbb.avatar.url")
    public String getUrl() {
        return url;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.355995569-04:00", comments="Source field: zfgbb.avatar.url")
    public void setUrl(String url) {
        this.url = url;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356010519-04:00", comments="Source field: zfgbb.avatar.active_flag")
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356020059-04:00", comments="Source field: zfgbb.avatar.active_flag")
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356049998-04:00", comments="Source field: zfgbb.avatar.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356060097-04:00", comments="Source field: zfgbb.avatar.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356087206-04:00", comments="Source field: zfgbb.avatar.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356094896-04:00", comments="Source field: zfgbb.avatar.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356108666-04:00", comments="Source field: zfgbb.avatar.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356118945-04:00", comments="Source field: zfgbb.avatar.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356131735-04:00", comments="Source field: zfgbb.avatar.content_resource_id")
    public Integer getContentResourceId() {
        return contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.356138845-04:00", comments="Source field: zfgbb.avatar.content_resource_id")
    public void setContentResourceId(Integer contentResourceId) {
        this.contentResourceId = contentResourceId;
    }

    @Override
    public Integer getPkId() {
        return avatarId;
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