package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BBCodeAttributeModeDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254941059-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_attribute_mode_id")
    private Integer bbCodeAttributeModeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254966579-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_config_id")
    private Integer bbCodeConfigId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254987748-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.content_is_attribute_flag")
    private Boolean contentIsAttributeFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255005367-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.open_tag")
    private String openTag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255025687-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.close_tag")
    private String closeTag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255044976-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.output_content_flag")
    private Boolean outputContentFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255062835-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255081555-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254951639-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_attribute_mode_id")
    public Integer getBbCodeAttributeModeId() {
        return bbCodeAttributeModeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254959659-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_attribute_mode_id")
    public void setBbCodeAttributeModeId(Integer bbCodeAttributeModeId) {
        this.bbCodeAttributeModeId = bbCodeAttributeModeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254975368-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_config_id")
    public Integer getBbCodeConfigId() {
        return bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254981858-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.bb_code_config_id")
    public void setBbCodeConfigId(Integer bbCodeConfigId) {
        this.bbCodeConfigId = bbCodeConfigId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254993598-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.content_is_attribute_flag")
    public Boolean getContentIsAttributeFlag() {
        return contentIsAttributeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.254999567-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.content_is_attribute_flag")
    public void setContentIsAttributeFlag(Boolean contentIsAttributeFlag) {
        this.contentIsAttributeFlag = contentIsAttributeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255011297-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.open_tag")
    public String getOpenTag() {
        return openTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255019367-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.open_tag")
    public void setOpenTag(String openTag) {
        this.openTag = openTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255031716-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.close_tag")
    public String getCloseTag() {
        return closeTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255039186-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.close_tag")
    public void setCloseTag(String closeTag) {
        this.closeTag = closeTag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255050996-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.output_content_flag")
    public Boolean getOutputContentFlag() {
        return outputContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255057266-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.output_content_flag")
    public void setOutputContentFlag(Boolean outputContentFlag) {
        this.outputContentFlag = outputContentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255069605-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255075805-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255087635-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.255093284-04:00", comments="Source field: zfgbb.bb_code_attribute_mode.updated_ts")
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