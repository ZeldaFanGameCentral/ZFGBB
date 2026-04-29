package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BBCodeAttributeModeDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374507121-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_attribute_mode_id")
    private Integer bbCodeAttributeModeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374529651-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_config_id")
    private Integer bbCodeConfigId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37455318-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.content_is_attribute_flag")
    private Boolean contentIsAttributeFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374582049-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.open_tag")
    private String openTag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374617588-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.close_tag")
    private String closeTag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374644167-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.output_content_flag")
    private Boolean outputContentFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374660956-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374676416-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374517031-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_attribute_mode_id")
    public Integer getBbCodeAttributeModeId() {
        return bbCodeAttributeModeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374523961-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_attribute_mode_id")
    public void setBbCodeAttributeModeId(Integer bbCodeAttributeModeId) {
        this.bbCodeAttributeModeId = bbCodeAttributeModeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37453512-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_config_id")
    public Integer getBbCodeConfigId() {
        return bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37454417-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_config_id")
    public void setBbCodeConfigId(Integer bbCodeConfigId) {
        this.bbCodeConfigId = bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374571719-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.content_is_attribute_flag")
    public Boolean getContentIsAttributeFlag() {
        return contentIsAttributeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374577289-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.content_is_attribute_flag")
    public void setContentIsAttributeFlag(Boolean contentIsAttributeFlag) {
        this.contentIsAttributeFlag = contentIsAttributeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374587989-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.open_tag")
    public String getOpenTag() {
        return openTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374599038-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.open_tag")
    public void setOpenTag(String openTag) {
        this.openTag = openTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374625937-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.close_tag")
    public String getCloseTag() {
        return closeTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374635657-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.close_tag")
    public void setCloseTag(String closeTag) {
        this.closeTag = closeTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374650957-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.output_content_flag")
    public Boolean getOutputContentFlag() {
        return outputContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374656236-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.output_content_flag")
    public void setOutputContentFlag(Boolean outputContentFlag) {
        this.outputContentFlag = outputContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374666276-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374671676-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374681216-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374685575-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return bbCodeAttributeModeId;
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