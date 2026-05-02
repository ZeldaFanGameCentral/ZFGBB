package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BBCodeAttributeDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523539507-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_id")
    private Integer bbCodeAttributeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523567856-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_data_type")
    private Integer attributeDataType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523589535-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_index")
    private Integer attributeIndex;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523609294-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_mode_id")
    private Integer bbCodeAttributeModeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523627604-04:00", comments="Source field: zfgbb.bb_code_attribute.name")
    private String name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523650283-04:00", comments="Source field: zfgbb.bb_code_attribute.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523670032-04:00", comments="Source field: zfgbb.bb_code_attribute.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523551576-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_id")
    public Integer getBbCodeAttributeId() {
        return bbCodeAttributeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523560586-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_id")
    public void setBbCodeAttributeId(Integer bbCodeAttributeId) {
        this.bbCodeAttributeId = bbCodeAttributeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523575275-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_data_type")
    public Integer getAttributeDataType() {
        return attributeDataType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523581645-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_data_type")
    public void setAttributeDataType(Integer attributeDataType) {
        this.attributeDataType = attributeDataType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523596155-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_index")
    public Integer getAttributeIndex() {
        return attributeIndex;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523603444-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_index")
    public void setAttributeIndex(Integer attributeIndex) {
        this.attributeIndex = attributeIndex;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523615964-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_mode_id")
    public Integer getBbCodeAttributeModeId() {
        return bbCodeAttributeModeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523621844-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_mode_id")
    public void setBbCodeAttributeModeId(Integer bbCodeAttributeModeId) {
        this.bbCodeAttributeModeId = bbCodeAttributeModeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523633434-04:00", comments="Source field: zfgbb.bb_code_attribute.name")
    public String getName() {
        return name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523644183-04:00", comments="Source field: zfgbb.bb_code_attribute.name")
    public void setName(String name) {
        this.name = name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523657093-04:00", comments="Source field: zfgbb.bb_code_attribute.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523665462-04:00", comments="Source field: zfgbb.bb_code_attribute.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523677872-04:00", comments="Source field: zfgbb.bb_code_attribute.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.523683702-04:00", comments="Source field: zfgbb.bb_code_attribute.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return bbCodeAttributeId;
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