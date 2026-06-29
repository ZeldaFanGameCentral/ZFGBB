package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class IpAddressDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698290489-04:00", comments="Source field: zfgbb.ip_address.ip_address_id")
    private Integer ipAddressId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698335217-04:00", comments="Source field: zfgbb.ip_address.ip")
    private String ip;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698367776-04:00", comments="Source field: zfgbb.ip_address.ip_v6_flag")
    private Boolean ipV6Flag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698396505-04:00", comments="Source field: zfgbb.ip_address.is_spammer_flag")
    private Boolean isSpammerFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698424255-04:00", comments="Source field: zfgbb.ip_address.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698452024-04:00", comments="Source field: zfgbb.ip_address.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698479073-04:00", comments="Source field: zfgbb.ip_address.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698312428-04:00", comments="Source field: zfgbb.ip_address.ip_address_id")
    public Integer getIpAddressId() {
        return ipAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698324348-04:00", comments="Source field: zfgbb.ip_address.ip_address_id")
    public void setIpAddressId(Integer ipAddressId) {
        this.ipAddressId = ipAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698345027-04:00", comments="Source field: zfgbb.ip_address.ip")
    public String getIp() {
        return ip;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698358047-04:00", comments="Source field: zfgbb.ip_address.ip")
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698377316-04:00", comments="Source field: zfgbb.ip_address.ip_v6_flag")
    public Boolean getIpV6Flag() {
        return ipV6Flag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698386926-04:00", comments="Source field: zfgbb.ip_address.ip_v6_flag")
    public void setIpV6Flag(Boolean ipV6Flag) {
        this.ipV6Flag = ipV6Flag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698405635-04:00", comments="Source field: zfgbb.ip_address.is_spammer_flag")
    public Boolean getIsSpammerFlag() {
        return isSpammerFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698415375-04:00", comments="Source field: zfgbb.ip_address.is_spammer_flag")
    public void setIsSpammerFlag(Boolean isSpammerFlag) {
        this.isSpammerFlag = isSpammerFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698434054-04:00", comments="Source field: zfgbb.ip_address.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698443374-04:00", comments="Source field: zfgbb.ip_address.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698461163-04:00", comments="Source field: zfgbb.ip_address.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698470283-04:00", comments="Source field: zfgbb.ip_address.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698488203-04:00", comments="Source field: zfgbb.ip_address.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.698499562-04:00", comments="Source field: zfgbb.ip_address.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return ipAddressId;
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