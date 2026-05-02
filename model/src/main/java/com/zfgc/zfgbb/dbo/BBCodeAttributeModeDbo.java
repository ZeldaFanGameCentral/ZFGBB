package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BBCodeAttributeModeDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524678279-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_attribute_mode_id")
    private Integer bbCodeAttributeModeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524707328-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_config_id")
    private Integer bbCodeConfigId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524726827-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.content_is_attribute_flag")
    private Boolean contentIsAttributeFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524744457-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.open_tag")
    private String openTag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524765796-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.close_tag")
    private String closeTag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524785315-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.output_content_flag")
    private Boolean outputContentFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524803315-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524822834-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524689409-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_attribute_mode_id")
    public Integer getBbCodeAttributeModeId() {
        return bbCodeAttributeModeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524697408-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_attribute_mode_id")
    public void setBbCodeAttributeModeId(Integer bbCodeAttributeModeId) {
        this.bbCodeAttributeModeId = bbCodeAttributeModeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524714218-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_config_id")
    public Integer getBbCodeConfigId() {
        return bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524721018-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_config_id")
    public void setBbCodeConfigId(Integer bbCodeConfigId) {
        this.bbCodeConfigId = bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524732787-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.content_is_attribute_flag")
    public Boolean getContentIsAttributeFlag() {
        return contentIsAttributeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524740087-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.content_is_attribute_flag")
    public void setContentIsAttributeFlag(Boolean contentIsAttributeFlag) {
        this.contentIsAttributeFlag = contentIsAttributeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524750387-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.open_tag")
    public String getOpenTag() {
        return openTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524760256-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.open_tag")
    public void setOpenTag(String openTag) {
        this.openTag = openTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524771956-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.close_tag")
    public String getCloseTag() {
        return closeTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524779306-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.close_tag")
    public void setCloseTag(String closeTag) {
        this.closeTag = closeTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524791265-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.output_content_flag")
    public Boolean getOutputContentFlag() {
        return outputContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524798815-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.output_content_flag")
    public void setOutputContentFlag(Boolean outputContentFlag) {
        this.outputContentFlag = outputContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524811115-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524817264-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524828924-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.524834544-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.updated_ts")
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