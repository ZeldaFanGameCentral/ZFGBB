package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class EmailAddressDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497229896-04:00", comments="Source field: zfgbb.email_address.email_address_id")
    private Integer emailAddressId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497319483-04:00", comments="Source field: zfgbb.email_address.email_address")
    private String emailAddress;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497356382-04:00", comments="Source field: zfgbb.email_address.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497394381-04:00", comments="Source field: zfgbb.email_address.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.49742738-04:00", comments="Source field: zfgbb.email_address.spammer_flag")
    private Boolean spammerFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497456639-04:00", comments="Source field: zfgbb.email_address.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497255935-04:00", comments="Source field: zfgbb.email_address.email_address_id")
    public Integer getEmailAddressId() {
        return emailAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497279954-04:00", comments="Source field: zfgbb.email_address.email_address_id")
    public void setEmailAddressId(Integer emailAddressId) {
        this.emailAddressId = emailAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497333723-04:00", comments="Source field: zfgbb.email_address.email_address")
    public String getEmailAddress() {
        return emailAddress;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497347392-04:00", comments="Source field: zfgbb.email_address.email_address")
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497367862-04:00", comments="Source field: zfgbb.email_address.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497384561-04:00", comments="Source field: zfgbb.email_address.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.49740602-04:00", comments="Source field: zfgbb.email_address.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.49741882-04:00", comments="Source field: zfgbb.email_address.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497437859-04:00", comments="Source field: zfgbb.email_address.spammer_flag")
    public Boolean getSpammerFlag() {
        return spammerFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497447579-04:00", comments="Source field: zfgbb.email_address.spammer_flag")
    public void setSpammerFlag(Boolean spammerFlag) {
        this.spammerFlag = spammerFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497466438-04:00", comments="Source field: zfgbb.email_address.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.497477978-04:00", comments="Source field: zfgbb.email_address.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return emailAddressId;
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