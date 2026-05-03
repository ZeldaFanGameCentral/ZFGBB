package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class SystemConfigDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516286226-04:00", comments="Source field: zfgbb.system_config.config_key")
    private String configKey;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516314445-04:00", comments="Source field: zfgbb.system_config.config_value")
    private String configValue;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516337285-04:00", comments="Source field: zfgbb.system_config.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516359244-04:00", comments="Source field: zfgbb.system_config.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516297076-04:00", comments="Source field: zfgbb.system_config.config_key")
    public String getConfigKey() {
        return configKey;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516308336-04:00", comments="Source field: zfgbb.system_config.config_key")
    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516322045-04:00", comments="Source field: zfgbb.system_config.config_value")
    public String getConfigValue() {
        return configValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516332195-04:00", comments="Source field: zfgbb.system_config.config_value")
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516346294-04:00", comments="Source field: zfgbb.system_config.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516353024-04:00", comments="Source field: zfgbb.system_config.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516365674-04:00", comments="Source field: zfgbb.system_config.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.516381793-04:00", comments="Source field: zfgbb.system_config.updated_ts")
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