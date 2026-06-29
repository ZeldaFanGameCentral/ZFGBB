package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class BBCodeConfigDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.420913469-04:00", comments="Source field: zfgbb.bb_code_config.bb_code_config_id")
    private Integer bbCodeConfigId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.420939738-04:00", comments="Source field: zfgbb.bb_code_config.code")
    private String code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.420960398-04:00", comments="Source field: zfgbb.bb_code_config.end_tag")
    private String endTag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.420979427-04:00", comments="Source field: zfgbb.bb_code_config.process_content_flag")
    private Boolean processContentFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.420997037-04:00", comments="Source field: zfgbb.bb_code_config.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.421015626-04:00", comments="Source field: zfgbb.bb_code_config.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.421033266-04:00", comments="Source field: zfgbb.bb_code_config.self_closing_flag")
    private Boolean selfClosingFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.421050385-04:00", comments="Source field: zfgbb.bb_code_config.enabled_flag")
    private Boolean enabledFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.420923599-04:00", comments="Source field: zfgbb.bb_code_config.bb_code_config_id")
    public Integer getBbCodeConfigId() {
        return bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.420931029-04:00", comments="Source field: zfgbb.bb_code_config.bb_code_config_id")
    public void setBbCodeConfigId(Integer bbCodeConfigId) {
        this.bbCodeConfigId = bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.420946078-04:00", comments="Source field: zfgbb.bb_code_config.code")
    public String getCode() {
        return code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.420954188-04:00", comments="Source field: zfgbb.bb_code_config.code")
    public void setCode(String code) {
        this.code = code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.420966528-04:00", comments="Source field: zfgbb.bb_code_config.end_tag")
    public String getEndTag() {
        return endTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.420973648-04:00", comments="Source field: zfgbb.bb_code_config.end_tag")
    public void setEndTag(String endTag) {
        this.endTag = endTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.420985407-04:00", comments="Source field: zfgbb.bb_code_config.process_content_flag")
    public Boolean getProcessContentFlag() {
        return processContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.420991447-04:00", comments="Source field: zfgbb.bb_code_config.process_content_flag")
    public void setProcessContentFlag(Boolean processContentFlag) {
        this.processContentFlag = processContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.421003477-04:00", comments="Source field: zfgbb.bb_code_config.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.421009497-04:00", comments="Source field: zfgbb.bb_code_config.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.421021796-04:00", comments="Source field: zfgbb.bb_code_config.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.421027616-04:00", comments="Source field: zfgbb.bb_code_config.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.421038756-04:00", comments="Source field: zfgbb.bb_code_config.self_closing_flag")
    public Boolean getSelfClosingFlag() {
        return selfClosingFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.421044826-04:00", comments="Source field: zfgbb.bb_code_config.self_closing_flag")
    public void setSelfClosingFlag(Boolean selfClosingFlag) {
        this.selfClosingFlag = selfClosingFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.421056135-04:00", comments="Source field: zfgbb.bb_code_config.enabled_flag")
    public Boolean getEnabledFlag() {
        return enabledFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-16T23:56:56.421061865-04:00", comments="Source field: zfgbb.bb_code_config.enabled_flag")
    public void setEnabledFlag(Boolean enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    @Override
    public Integer getPkId() {
        return bbCodeConfigId;
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