package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class UserContactInfoDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672637817-04:00", comments="Source field: zfgbb.user_contact_info.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672672716-04:00", comments="Source field: zfgbb.user_contact_info.email_address_id")
    private Integer emailAddressId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672700845-04:00", comments="Source field: zfgbb.user_contact_info.allow_email_flag")
    private Boolean allowEmailFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672725564-04:00", comments="Source field: zfgbb.user_contact_info.allow_pm_flag")
    private Boolean allowPmFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672750354-04:00", comments="Source field: zfgbb.user_contact_info.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672778423-04:00", comments="Source field: zfgbb.user_contact_info.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672803782-04:00", comments="Source field: zfgbb.user_contact_info.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672653367-04:00", comments="Source field: zfgbb.user_contact_info.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672664456-04:00", comments="Source field: zfgbb.user_contact_info.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672683826-04:00", comments="Source field: zfgbb.user_contact_info.email_address_id")
    public Integer getEmailAddressId() {
        return emailAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672693505-04:00", comments="Source field: zfgbb.user_contact_info.email_address_id")
    public void setEmailAddressId(Integer emailAddressId) {
        this.emailAddressId = emailAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672709905-04:00", comments="Source field: zfgbb.user_contact_info.allow_email_flag")
    public Boolean getAllowEmailFlag() {
        return allowEmailFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672718555-04:00", comments="Source field: zfgbb.user_contact_info.allow_email_flag")
    public void setAllowEmailFlag(Boolean allowEmailFlag) {
        this.allowEmailFlag = allowEmailFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672734504-04:00", comments="Source field: zfgbb.user_contact_info.allow_pm_flag")
    public Boolean getAllowPmFlag() {
        return allowPmFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672743214-04:00", comments="Source field: zfgbb.user_contact_info.allow_pm_flag")
    public void setAllowPmFlag(Boolean allowPmFlag) {
        this.allowPmFlag = allowPmFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672760503-04:00", comments="Source field: zfgbb.user_contact_info.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672771423-04:00", comments="Source field: zfgbb.user_contact_info.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672788032-04:00", comments="Source field: zfgbb.user_contact_info.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672796632-04:00", comments="Source field: zfgbb.user_contact_info.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672812742-04:00", comments="Source field: zfgbb.user_contact_info.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.672823641-04:00", comments="Source field: zfgbb.user_contact_info.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return userId;
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