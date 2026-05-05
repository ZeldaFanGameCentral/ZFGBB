package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class IpAddressDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454878601-04:00", comments="Source field: zfgbb.ip_address.ip_address_id")
    private Integer ipAddressId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45490716-04:00", comments="Source field: zfgbb.ip_address.ip")
    private String ip;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454931579-04:00", comments="Source field: zfgbb.ip_address.ip_v6_flag")
    private Boolean ipV6Flag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454952188-04:00", comments="Source field: zfgbb.ip_address.is_spammer_flag")
    private Boolean isSpammerFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454972028-04:00", comments="Source field: zfgbb.ip_address.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454992347-04:00", comments="Source field: zfgbb.ip_address.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.455032366-04:00", comments="Source field: zfgbb.ip_address.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45489104-04:00", comments="Source field: zfgbb.ip_address.ip_address_id")
    public Integer getIpAddressId() {
        return ipAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45489989-04:00", comments="Source field: zfgbb.ip_address.ip_address_id")
    public void setIpAddressId(Integer ipAddressId) {
        this.ipAddressId = ipAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45491386-04:00", comments="Source field: zfgbb.ip_address.ip")
    public String getIp() {
        return ip;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454922719-04:00", comments="Source field: zfgbb.ip_address.ip")
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454938539-04:00", comments="Source field: zfgbb.ip_address.ip_v6_flag")
    public Boolean getIpV6Flag() {
        return ipV6Flag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454945759-04:00", comments="Source field: zfgbb.ip_address.ip_v6_flag")
    public void setIpV6Flag(Boolean ipV6Flag) {
        this.ipV6Flag = ipV6Flag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454958638-04:00", comments="Source field: zfgbb.ip_address.is_spammer_flag")
    public Boolean getIsSpammerFlag() {
        return isSpammerFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454965678-04:00", comments="Source field: zfgbb.ip_address.is_spammer_flag")
    public void setIsSpammerFlag(Boolean isSpammerFlag) {
        this.isSpammerFlag = isSpammerFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454978878-04:00", comments="Source field: zfgbb.ip_address.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454986197-04:00", comments="Source field: zfgbb.ip_address.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.454998687-04:00", comments="Source field: zfgbb.ip_address.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.455019446-04:00", comments="Source field: zfgbb.ip_address.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.455039806-04:00", comments="Source field: zfgbb.ip_address.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.455049175-04:00", comments="Source field: zfgbb.ip_address.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return ipAddressId;
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