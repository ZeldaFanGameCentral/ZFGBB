package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class UserContactInfoDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352344869-04:00", comments="Source field: zfgbb.user_contact_info.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352389548-04:00", comments="Source field: zfgbb.user_contact_info.email_address_id")
    private Integer emailAddressId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352424717-04:00", comments="Source field: zfgbb.user_contact_info.allow_email_flag")
    private Boolean allowEmailFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352455256-04:00", comments="Source field: zfgbb.user_contact_info.allow_pm_flag")
    private Boolean allowPmFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352486595-04:00", comments="Source field: zfgbb.user_contact_info.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352518934-04:00", comments="Source field: zfgbb.user_contact_info.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352550653-04:00", comments="Source field: zfgbb.user_contact_info.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352364729-04:00", comments="Source field: zfgbb.user_contact_info.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352378678-04:00", comments="Source field: zfgbb.user_contact_info.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352402868-04:00", comments="Source field: zfgbb.user_contact_info.email_address_id")
    public Integer getEmailAddressId() {
        return emailAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352414667-04:00", comments="Source field: zfgbb.user_contact_info.email_address_id")
    public void setEmailAddressId(Integer emailAddressId) {
        this.emailAddressId = emailAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352435327-04:00", comments="Source field: zfgbb.user_contact_info.allow_email_flag")
    public Boolean getAllowEmailFlag() {
        return allowEmailFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352445516-04:00", comments="Source field: zfgbb.user_contact_info.allow_email_flag")
    public void setAllowEmailFlag(Boolean allowEmailFlag) {
        this.allowEmailFlag = allowEmailFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352465995-04:00", comments="Source field: zfgbb.user_contact_info.allow_pm_flag")
    public Boolean getAllowPmFlag() {
        return allowPmFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352477665-04:00", comments="Source field: zfgbb.user_contact_info.allow_pm_flag")
    public void setAllowPmFlag(Boolean allowPmFlag) {
        this.allowPmFlag = allowPmFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352498454-04:00", comments="Source field: zfgbb.user_contact_info.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352510284-04:00", comments="Source field: zfgbb.user_contact_info.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352530683-04:00", comments="Source field: zfgbb.user_contact_info.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352541823-04:00", comments="Source field: zfgbb.user_contact_info.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352572152-04:00", comments="Source field: zfgbb.user_contact_info.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.352586342-04:00", comments="Source field: zfgbb.user_contact_info.migration_hash")
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