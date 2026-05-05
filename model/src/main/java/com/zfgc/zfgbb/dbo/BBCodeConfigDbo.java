package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BBCodeConfigDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256110402-04:00", comments="Source field: zfgbb.bb_code_config.bb_code_config_id")
    private Integer bbCodeConfigId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256140921-04:00", comments="Source field: zfgbb.bb_code_config.code")
    private String code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.25616253-04:00", comments="Source field: zfgbb.bb_code_config.end_tag")
    private String endTag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.25618292-04:00", comments="Source field: zfgbb.bb_code_config.process_content_flag")
    private Boolean processContentFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256201129-04:00", comments="Source field: zfgbb.bb_code_config.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256219539-04:00", comments="Source field: zfgbb.bb_code_config.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256238688-04:00", comments="Source field: zfgbb.bb_code_config.self_closing_flag")
    private Boolean selfClosingFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256124822-04:00", comments="Source field: zfgbb.bb_code_config.bb_code_config_id")
    public Integer getBbCodeConfigId() {
        return bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256134981-04:00", comments="Source field: zfgbb.bb_code_config.bb_code_config_id")
    public void setBbCodeConfigId(Integer bbCodeConfigId) {
        this.bbCodeConfigId = bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256147271-04:00", comments="Source field: zfgbb.bb_code_config.code")
    public String getCode() {
        return code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256156101-04:00", comments="Source field: zfgbb.bb_code_config.code")
    public void setCode(String code) {
        this.code = code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.2561688-04:00", comments="Source field: zfgbb.bb_code_config.end_tag")
    public String getEndTag() {
        return endTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.25617695-04:00", comments="Source field: zfgbb.bb_code_config.end_tag")
    public void setEndTag(String endTag) {
        this.endTag = endTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.25618913-04:00", comments="Source field: zfgbb.bb_code_config.process_content_flag")
    public Boolean getProcessContentFlag() {
        return processContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256195419-04:00", comments="Source field: zfgbb.bb_code_config.process_content_flag")
    public void setProcessContentFlag(Boolean processContentFlag) {
        this.processContentFlag = processContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256207809-04:00", comments="Source field: zfgbb.bb_code_config.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256213969-04:00", comments="Source field: zfgbb.bb_code_config.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256227778-04:00", comments="Source field: zfgbb.bb_code_config.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256234128-04:00", comments="Source field: zfgbb.bb_code_config.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256246238-04:00", comments="Source field: zfgbb.bb_code_config.self_closing_flag")
    public Boolean getSelfClosingFlag() {
        return selfClosingFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256252108-04:00", comments="Source field: zfgbb.bb_code_config.self_closing_flag")
    public void setSelfClosingFlag(Boolean selfClosingFlag) {
        this.selfClosingFlag = selfClosingFlag;
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