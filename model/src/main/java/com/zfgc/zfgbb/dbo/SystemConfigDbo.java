package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class SystemConfigDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.695315271-04:00", comments="Source field: zfgbb.system_config.config_key")
    private String configKey;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69534464-04:00", comments="Source field: zfgbb.system_config.config_value")
    private String configValue;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69536766-04:00", comments="Source field: zfgbb.system_config.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.695390859-04:00", comments="Source field: zfgbb.system_config.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.695326451-04:00", comments="Source field: zfgbb.system_config.config_key")
    public String getConfigKey() {
        return configKey;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.695337351-04:00", comments="Source field: zfgbb.system_config.config_key")
    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69535166-04:00", comments="Source field: zfgbb.system_config.config_value")
    public String getConfigValue() {
        return configValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69536068-04:00", comments="Source field: zfgbb.system_config.config_value")
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69537511-04:00", comments="Source field: zfgbb.system_config.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.695382299-04:00", comments="Source field: zfgbb.system_config.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.695398739-04:00", comments="Source field: zfgbb.system_config.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.695405499-04:00", comments="Source field: zfgbb.system_config.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return null;
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