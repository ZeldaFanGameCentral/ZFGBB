package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class EmailAddressDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430393803-04:00", comments="Source field: zfgbb.email_address.email_address_id")
    private Integer emailAddressId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.43047003-04:00", comments="Source field: zfgbb.email_address.email_address")
    private String emailAddress;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430516929-04:00", comments="Source field: zfgbb.email_address.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430554338-04:00", comments="Source field: zfgbb.email_address.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430589016-04:00", comments="Source field: zfgbb.email_address.spammer_flag")
    private Boolean spammerFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430636235-04:00", comments="Source field: zfgbb.email_address.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430423222-04:00", comments="Source field: zfgbb.email_address.email_address_id")
    public Integer getEmailAddressId() {
        return emailAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430450231-04:00", comments="Source field: zfgbb.email_address.email_address_id")
    public void setEmailAddressId(Integer emailAddressId) {
        this.emailAddressId = emailAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.43048543-04:00", comments="Source field: zfgbb.email_address.email_address")
    public String getEmailAddress() {
        return emailAddress;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430501479-04:00", comments="Source field: zfgbb.email_address.email_address")
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430530378-04:00", comments="Source field: zfgbb.email_address.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430541838-04:00", comments="Source field: zfgbb.email_address.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430571487-04:00", comments="Source field: zfgbb.email_address.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430582237-04:00", comments="Source field: zfgbb.email_address.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430612846-04:00", comments="Source field: zfgbb.email_address.spammer_flag")
    public Boolean getSpammerFlag() {
        return spammerFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430627325-04:00", comments="Source field: zfgbb.email_address.spammer_flag")
    public void setSpammerFlag(Boolean spammerFlag) {
        this.spammerFlag = spammerFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430647005-04:00", comments="Source field: zfgbb.email_address.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.430661454-04:00", comments="Source field: zfgbb.email_address.migration_hash")
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