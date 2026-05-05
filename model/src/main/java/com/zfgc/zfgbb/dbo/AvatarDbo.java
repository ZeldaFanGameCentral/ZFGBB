package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class AvatarDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226058406-04:00", comments="Source field: zfgbb.avatar.avatar_id")
    private Integer avatarId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226098815-04:00", comments="Source field: zfgbb.avatar.url")
    private String url;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226132054-04:00", comments="Source field: zfgbb.avatar.active_flag")
    private Boolean activeFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226160693-04:00", comments="Source field: zfgbb.avatar.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226189512-04:00", comments="Source field: zfgbb.avatar.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226230681-04:00", comments="Source field: zfgbb.avatar.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.22626151-04:00", comments="Source field: zfgbb.avatar.content_resource_id")
    private Integer contentResourceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226077895-04:00", comments="Source field: zfgbb.avatar.avatar_id")
    public Integer getAvatarId() {
        return avatarId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226089765-04:00", comments="Source field: zfgbb.avatar.avatar_id")
    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226109954-04:00", comments="Source field: zfgbb.avatar.url")
    public String getUrl() {
        return url;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226123894-04:00", comments="Source field: zfgbb.avatar.url")
    public void setUrl(String url) {
        this.url = url;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226141773-04:00", comments="Source field: zfgbb.avatar.active_flag")
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226152223-04:00", comments="Source field: zfgbb.avatar.active_flag")
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226171552-04:00", comments="Source field: zfgbb.avatar.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226181322-04:00", comments="Source field: zfgbb.avatar.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226211961-04:00", comments="Source field: zfgbb.avatar.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226222221-04:00", comments="Source field: zfgbb.avatar.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.22624138-04:00", comments="Source field: zfgbb.avatar.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.22625343-04:00", comments="Source field: zfgbb.avatar.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226271399-04:00", comments="Source field: zfgbb.avatar.content_resource_id")
    public Integer getContentResourceId() {
        return contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.226281579-04:00", comments="Source field: zfgbb.avatar.content_resource_id")
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