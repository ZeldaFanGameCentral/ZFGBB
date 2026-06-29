package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class BBCodeAttributeDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70723965-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_id")
    private Integer bbCodeAttributeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707267419-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_data_type")
    private Integer attributeDataType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707286779-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_index")
    private Integer attributeIndex;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707313158-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_mode_id")
    private Integer bbCodeAttributeModeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707332517-04:00", comments="Source field: zfgbb.bb_code_attribute.name")
    private String name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707353907-04:00", comments="Source field: zfgbb.bb_code_attribute.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707373986-04:00", comments="Source field: zfgbb.bb_code_attribute.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70725234-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_id")
    public Integer getBbCodeAttributeId() {
        return bbCodeAttributeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70726036-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_id")
    public void setBbCodeAttributeId(Integer bbCodeAttributeId) {
        this.bbCodeAttributeId = bbCodeAttributeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707274009-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_data_type")
    public Integer getAttributeDataType() {
        return attributeDataType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707280589-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_data_type")
    public void setAttributeDataType(Integer attributeDataType) {
        this.attributeDataType = attributeDataType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707293369-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_index")
    public Integer getAttributeIndex() {
        return attributeIndex;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707306608-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_index")
    public void setAttributeIndex(Integer attributeIndex) {
        this.attributeIndex = attributeIndex;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707319658-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_mode_id")
    public Integer getBbCodeAttributeModeId() {
        return bbCodeAttributeModeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707327998-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_mode_id")
    public void setBbCodeAttributeModeId(Integer bbCodeAttributeModeId) {
        this.bbCodeAttributeModeId = bbCodeAttributeModeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707338927-04:00", comments="Source field: zfgbb.bb_code_attribute.name")
    public String getName() {
        return name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707347407-04:00", comments="Source field: zfgbb.bb_code_attribute.name")
    public void setName(String name) {
        this.name = name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707360967-04:00", comments="Source field: zfgbb.bb_code_attribute.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707369366-04:00", comments="Source field: zfgbb.bb_code_attribute.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707382006-04:00", comments="Source field: zfgbb.bb_code_attribute.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707388166-04:00", comments="Source field: zfgbb.bb_code_attribute.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return bbCodeAttributeId;
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