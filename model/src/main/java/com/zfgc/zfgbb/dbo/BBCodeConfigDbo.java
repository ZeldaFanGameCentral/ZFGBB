package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BBCodeConfigDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462143509-04:00", comments="Source field: zfgbb.bb_code_config.bb_code_config_id")
    private Integer bbCodeConfigId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462169818-04:00", comments="Source field: zfgbb.bb_code_config.code")
    private String code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462191667-04:00", comments="Source field: zfgbb.bb_code_config.end_tag")
    private String endTag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462212497-04:00", comments="Source field: zfgbb.bb_code_config.process_content_flag")
    private Boolean processContentFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462234536-04:00", comments="Source field: zfgbb.bb_code_config.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462254015-04:00", comments="Source field: zfgbb.bb_code_config.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462272475-04:00", comments="Source field: zfgbb.bb_code_config.self_closing_flag")
    private Boolean selfClosingFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462154268-04:00", comments="Source field: zfgbb.bb_code_config.bb_code_config_id")
    public Integer getBbCodeConfigId() {
        return bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462162378-04:00", comments="Source field: zfgbb.bb_code_config.bb_code_config_id")
    public void setBbCodeConfigId(Integer bbCodeConfigId) {
        this.bbCodeConfigId = bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462176278-04:00", comments="Source field: zfgbb.bb_code_config.code")
    public String getCode() {
        return code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462185197-04:00", comments="Source field: zfgbb.bb_code_config.code")
    public void setCode(String code) {
        this.code = code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462198087-04:00", comments="Source field: zfgbb.bb_code_config.end_tag")
    public String getEndTag() {
        return endTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462206187-04:00", comments="Source field: zfgbb.bb_code_config.end_tag")
    public void setEndTag(String endTag) {
        this.endTag = endTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462219546-04:00", comments="Source field: zfgbb.bb_code_config.process_content_flag")
    public Boolean getProcessContentFlag() {
        return processContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462226486-04:00", comments="Source field: zfgbb.bb_code_config.process_content_flag")
    public void setProcessContentFlag(Boolean processContentFlag) {
        this.processContentFlag = processContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462241256-04:00", comments="Source field: zfgbb.bb_code_config.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462248025-04:00", comments="Source field: zfgbb.bb_code_config.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462260165-04:00", comments="Source field: zfgbb.bb_code_config.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462268055-04:00", comments="Source field: zfgbb.bb_code_config.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462278954-04:00", comments="Source field: zfgbb.bb_code_config.self_closing_flag")
    public Boolean getSelfClosingFlag() {
        return selfClosingFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462294434-04:00", comments="Source field: zfgbb.bb_code_config.self_closing_flag")
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