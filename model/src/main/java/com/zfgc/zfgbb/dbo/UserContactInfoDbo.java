package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class UserContactInfoDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222444281-04:00", comments="Source field: zfgbb.user_contact_info.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222486179-04:00", comments="Source field: zfgbb.user_contact_info.email_address_id")
    private Integer emailAddressId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222516998-04:00", comments="Source field: zfgbb.user_contact_info.allow_email_flag")
    private Boolean allowEmailFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222547147-04:00", comments="Source field: zfgbb.user_contact_info.allow_pm_flag")
    private Boolean allowPmFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222573637-04:00", comments="Source field: zfgbb.user_contact_info.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222611215-04:00", comments="Source field: zfgbb.user_contact_info.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222639625-04:00", comments="Source field: zfgbb.user_contact_info.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.22246003-04:00", comments="Source field: zfgbb.user_contact_info.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.22247193-04:00", comments="Source field: zfgbb.user_contact_info.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222497929-04:00", comments="Source field: zfgbb.user_contact_info.email_address_id")
    public Integer getEmailAddressId() {
        return emailAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222508749-04:00", comments="Source field: zfgbb.user_contact_info.email_address_id")
    public void setEmailAddressId(Integer emailAddressId) {
        this.emailAddressId = emailAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222526798-04:00", comments="Source field: zfgbb.user_contact_info.allow_email_flag")
    public Boolean getAllowEmailFlag() {
        return allowEmailFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222539038-04:00", comments="Source field: zfgbb.user_contact_info.allow_email_flag")
    public void setAllowEmailFlag(Boolean allowEmailFlag) {
        this.allowEmailFlag = allowEmailFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222556687-04:00", comments="Source field: zfgbb.user_contact_info.allow_pm_flag")
    public Boolean getAllowPmFlag() {
        return allowPmFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222566197-04:00", comments="Source field: zfgbb.user_contact_info.allow_pm_flag")
    public void setAllowPmFlag(Boolean allowPmFlag) {
        this.allowPmFlag = allowPmFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222583856-04:00", comments="Source field: zfgbb.user_contact_info.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222602026-04:00", comments="Source field: zfgbb.user_contact_info.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222622305-04:00", comments="Source field: zfgbb.user_contact_info.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222632145-04:00", comments="Source field: zfgbb.user_contact_info.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222648794-04:00", comments="Source field: zfgbb.user_contact_info.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.222659744-04:00", comments="Source field: zfgbb.user_contact_info.migration_hash")
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