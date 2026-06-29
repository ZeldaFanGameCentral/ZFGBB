package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class BBCodeAttributeModeDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708284228-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_attribute_mode_id")
    private Integer bbCodeAttributeModeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708330966-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_config_id")
    private Integer bbCodeConfigId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708353536-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.content_is_attribute_flag")
    private Boolean contentIsAttributeFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708372135-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.open_tag")
    private String openTag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708393404-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.close_tag")
    private String closeTag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708413774-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.output_content_flag")
    private Boolean outputContentFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708432173-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708453533-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708294668-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_attribute_mode_id")
    public Integer getBbCodeAttributeModeId() {
        return bbCodeAttributeModeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708309327-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_attribute_mode_id")
    public void setBbCodeAttributeModeId(Integer bbCodeAttributeModeId) {
        this.bbCodeAttributeModeId = bbCodeAttributeModeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708339406-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_config_id")
    public Integer getBbCodeConfigId() {
        return bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708346846-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_config_id")
    public void setBbCodeConfigId(Integer bbCodeConfigId) {
        this.bbCodeConfigId = bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708359835-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.content_is_attribute_flag")
    public Boolean getContentIsAttributeFlag() {
        return contentIsAttributeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708366185-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.content_is_attribute_flag")
    public void setContentIsAttributeFlag(Boolean contentIsAttributeFlag) {
        this.contentIsAttributeFlag = contentIsAttributeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708378305-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.open_tag")
    public String getOpenTag() {
        return openTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708386815-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.open_tag")
    public void setOpenTag(String openTag) {
        this.openTag = openTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708399974-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.close_tag")
    public String getCloseTag() {
        return closeTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708407724-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.close_tag")
    public void setCloseTag(String closeTag) {
        this.closeTag = closeTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708419934-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.output_content_flag")
    public Boolean getOutputContentFlag() {
        return outputContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708426313-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.output_content_flag")
    public void setOutputContentFlag(Boolean outputContentFlag) {
        this.outputContentFlag = outputContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708439033-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708445393-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708460002-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708465992-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return bbCodeAttributeModeId;
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