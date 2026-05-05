package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class UserContactInfoDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428362617-04:00", comments="Source field: zfgbb.user_contact_info.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428407746-04:00", comments="Source field: zfgbb.user_contact_info.email_address_id")
    private Integer emailAddressId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428444525-04:00", comments="Source field: zfgbb.user_contact_info.allow_email_flag")
    private Boolean allowEmailFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428472624-04:00", comments="Source field: zfgbb.user_contact_info.allow_pm_flag")
    private Boolean allowPmFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428506623-04:00", comments="Source field: zfgbb.user_contact_info.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428550251-04:00", comments="Source field: zfgbb.user_contact_info.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.42858105-04:00", comments="Source field: zfgbb.user_contact_info.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428380277-04:00", comments="Source field: zfgbb.user_contact_info.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428391867-04:00", comments="Source field: zfgbb.user_contact_info.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428425135-04:00", comments="Source field: zfgbb.user_contact_info.email_address_id")
    public Integer getEmailAddressId() {
        return emailAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428436405-04:00", comments="Source field: zfgbb.user_contact_info.email_address_id")
    public void setEmailAddressId(Integer emailAddressId) {
        this.emailAddressId = emailAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428454005-04:00", comments="Source field: zfgbb.user_contact_info.allow_email_flag")
    public Boolean getAllowEmailFlag() {
        return allowEmailFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428463124-04:00", comments="Source field: zfgbb.user_contact_info.allow_email_flag")
    public void setAllowEmailFlag(Boolean allowEmailFlag) {
        this.allowEmailFlag = allowEmailFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428481694-04:00", comments="Source field: zfgbb.user_contact_info.allow_pm_flag")
    public Boolean getAllowPmFlag() {
        return allowPmFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428490573-04:00", comments="Source field: zfgbb.user_contact_info.allow_pm_flag")
    public void setAllowPmFlag(Boolean allowPmFlag) {
        this.allowPmFlag = allowPmFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428524502-04:00", comments="Source field: zfgbb.user_contact_info.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428539112-04:00", comments="Source field: zfgbb.user_contact_info.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428564121-04:00", comments="Source field: zfgbb.user_contact_info.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.428573821-04:00", comments="Source field: zfgbb.user_contact_info.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.42859031-04:00", comments="Source field: zfgbb.user_contact_info.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.42860147-04:00", comments="Source field: zfgbb.user_contact_info.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return userId;
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