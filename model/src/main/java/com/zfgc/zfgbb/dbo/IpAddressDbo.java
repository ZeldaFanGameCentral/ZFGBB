package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class IpAddressDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370323219-04:00", comments="Source field: zfgbb.ip_address.ip_address_id")
    private Integer ipAddressId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370362747-04:00", comments="Source field: zfgbb.ip_address.ip")
    private String ip;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370392666-04:00", comments="Source field: zfgbb.ip_address.ip_v6_flag")
    private Boolean ipV6Flag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370414586-04:00", comments="Source field: zfgbb.ip_address.is_spammer_flag")
    private Boolean isSpammerFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370436805-04:00", comments="Source field: zfgbb.ip_address.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370464874-04:00", comments="Source field: zfgbb.ip_address.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370494583-04:00", comments="Source field: zfgbb.ip_address.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370334768-04:00", comments="Source field: zfgbb.ip_address.ip_address_id")
    public Integer getIpAddressId() {
        return ipAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370348948-04:00", comments="Source field: zfgbb.ip_address.ip_address_id")
    public void setIpAddressId(Integer ipAddressId) {
        this.ipAddressId = ipAddressId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370373357-04:00", comments="Source field: zfgbb.ip_address.ip")
    public String getIp() {
        return ip;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370384807-04:00", comments="Source field: zfgbb.ip_address.ip")
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370400016-04:00", comments="Source field: zfgbb.ip_address.ip_v6_flag")
    public Boolean getIpV6Flag() {
        return ipV6Flag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370407916-04:00", comments="Source field: zfgbb.ip_address.ip_v6_flag")
    public void setIpV6Flag(Boolean ipV6Flag) {
        this.ipV6Flag = ipV6Flag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370422335-04:00", comments="Source field: zfgbb.ip_address.is_spammer_flag")
    public Boolean getIsSpammerFlag() {
        return isSpammerFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370429645-04:00", comments="Source field: zfgbb.ip_address.is_spammer_flag")
    public void setIsSpammerFlag(Boolean isSpammerFlag) {
        this.isSpammerFlag = isSpammerFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370446305-04:00", comments="Source field: zfgbb.ip_address.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370455864-04:00", comments="Source field: zfgbb.ip_address.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370474654-04:00", comments="Source field: zfgbb.ip_address.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370486173-04:00", comments="Source field: zfgbb.ip_address.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370504963-04:00", comments="Source field: zfgbb.ip_address.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.370515762-04:00", comments="Source field: zfgbb.ip_address.migration_hash")
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