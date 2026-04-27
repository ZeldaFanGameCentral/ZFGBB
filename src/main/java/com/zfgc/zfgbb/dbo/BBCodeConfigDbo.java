package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BBCodeConfigDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375399862-04:00", comments="Source field: zfgbb.bb_code_config.bb_code_config_id")
    private Integer bbCodeConfigId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375420571-04:00", comments="Source field: zfgbb.bb_code_config.code")
    private String code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375437171-04:00", comments="Source field: zfgbb.bb_code_config.end_tag")
    private String endTag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37545364-04:00", comments="Source field: zfgbb.bb_code_config.process_content_flag")
    private Boolean processContentFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37546697-04:00", comments="Source field: zfgbb.bb_code_config.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375481099-04:00", comments="Source field: zfgbb.bb_code_config.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375409022-04:00", comments="Source field: zfgbb.bb_code_config.bb_code_config_id")
    public Integer getBbCodeConfigId() {
        return bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375415391-04:00", comments="Source field: zfgbb.bb_code_config.bb_code_config_id")
    public void setBbCodeConfigId(Integer bbCodeConfigId) {
        this.bbCodeConfigId = bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375425501-04:00", comments="Source field: zfgbb.bb_code_config.code")
    public String getCode() {
        return code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375432461-04:00", comments="Source field: zfgbb.bb_code_config.code")
    public void setCode(String code) {
        this.code = code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375441711-04:00", comments="Source field: zfgbb.bb_code_config.end_tag")
    public String getEndTag() {
        return endTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37544897-04:00", comments="Source field: zfgbb.bb_code_config.end_tag")
    public void setEndTag(String endTag) {
        this.endTag = endTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37545814-04:00", comments="Source field: zfgbb.bb_code_config.process_content_flag")
    public Boolean getProcessContentFlag() {
        return processContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37546273-04:00", comments="Source field: zfgbb.bb_code_config.process_content_flag")
    public void setProcessContentFlag(Boolean processContentFlag) {
        this.processContentFlag = processContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37547192-04:00", comments="Source field: zfgbb.bb_code_config.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375476919-04:00", comments="Source field: zfgbb.bb_code_config.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375485519-04:00", comments="Source field: zfgbb.bb_code_config.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375489799-04:00", comments="Source field: zfgbb.bb_code_config.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return bbCodeConfigId;
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