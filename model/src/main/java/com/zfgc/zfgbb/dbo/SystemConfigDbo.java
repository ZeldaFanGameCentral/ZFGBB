package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class SystemConfigDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452567245-04:00", comments="Source field: zfgbb.system_config.config_key")
    private String configKey;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452596434-04:00", comments="Source field: zfgbb.system_config.config_value")
    private String configValue;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452618623-04:00", comments="Source field: zfgbb.system_config.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452639132-04:00", comments="Source field: zfgbb.system_config.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452577714-04:00", comments="Source field: zfgbb.system_config.config_key")
    public String getConfigKey() {
        return configKey;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452588154-04:00", comments="Source field: zfgbb.system_config.config_key")
    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452603733-04:00", comments="Source field: zfgbb.system_config.config_value")
    public String getConfigValue() {
        return configValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452612123-04:00", comments="Source field: zfgbb.system_config.config_value")
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452625923-04:00", comments="Source field: zfgbb.system_config.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452632943-04:00", comments="Source field: zfgbb.system_config.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452645662-04:00", comments="Source field: zfgbb.system_config.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.452651982-04:00", comments="Source field: zfgbb.system_config.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return null;
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