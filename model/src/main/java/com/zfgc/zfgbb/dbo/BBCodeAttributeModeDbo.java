package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BBCodeAttributeModeDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461066613-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_attribute_mode_id")
    private Integer bbCodeAttributeModeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461091962-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_config_id")
    private Integer bbCodeConfigId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461112032-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.content_is_attribute_flag")
    private Boolean contentIsAttributeFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461131701-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.open_tag")
    private String openTag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.46115421-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.close_tag")
    private String closeTag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.46117524-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.output_content_flag")
    private Boolean outputContentFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461194149-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461215718-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461076653-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_attribute_mode_id")
    public Integer getBbCodeAttributeModeId() {
        return bbCodeAttributeModeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461084952-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_attribute_mode_id")
    public void setBbCodeAttributeModeId(Integer bbCodeAttributeModeId) {
        this.bbCodeAttributeModeId = bbCodeAttributeModeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461098752-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_config_id")
    public Integer getBbCodeConfigId() {
        return bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461105762-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_config_id")
    public void setBbCodeConfigId(Integer bbCodeConfigId) {
        this.bbCodeConfigId = bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461118901-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.content_is_attribute_flag")
    public Boolean getContentIsAttributeFlag() {
        return contentIsAttributeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461125801-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.content_is_attribute_flag")
    public void setContentIsAttributeFlag(Boolean contentIsAttributeFlag) {
        this.contentIsAttributeFlag = contentIsAttributeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461138381-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.open_tag")
    public String getOpenTag() {
        return openTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.46114705-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.open_tag")
    public void setOpenTag(String openTag) {
        this.openTag = openTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.46116116-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.close_tag")
    public String getCloseTag() {
        return closeTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.46116868-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.close_tag")
    public void setCloseTag(String closeTag) {
        this.closeTag = closeTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461181469-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.output_content_flag")
    public Boolean getOutputContentFlag() {
        return outputContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461188259-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.output_content_flag")
    public void setOutputContentFlag(Boolean outputContentFlag) {
        this.outputContentFlag = outputContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461200639-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461209758-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461222038-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461228498-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.updated_ts")
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