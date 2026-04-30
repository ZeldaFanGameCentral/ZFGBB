package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BBCodeAttributeDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373167885-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_id")
    private Integer bbCodeAttributeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373187935-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_data_type")
    private Integer attributeDataType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373204134-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_index")
    private Integer attributeIndex;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373217694-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_mode_id")
    private Integer bbCodeAttributeModeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373261122-04:00", comments="Source field: zfgbb.bb_code_attribute.name")
    private String name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373289111-04:00", comments="Source field: zfgbb.bb_code_attribute.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373316471-04:00", comments="Source field: zfgbb.bb_code_attribute.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373175925-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_id")
    public Integer getBbCodeAttributeId() {
        return bbCodeAttributeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373182415-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_id")
    public void setBbCodeAttributeId(Integer bbCodeAttributeId) {
        this.bbCodeAttributeId = bbCodeAttributeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373192655-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_data_type")
    public Integer getAttributeDataType() {
        return attributeDataType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373199224-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_data_type")
    public void setAttributeDataType(Integer attributeDataType) {
        this.attributeDataType = attributeDataType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373208564-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_index")
    public Integer getAttributeIndex() {
        return attributeIndex;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373213524-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_index")
    public void setAttributeIndex(Integer attributeIndex) {
        this.attributeIndex = attributeIndex;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373237333-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_mode_id")
    public Integer getBbCodeAttributeModeId() {
        return bbCodeAttributeModeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373252243-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_mode_id")
    public void setBbCodeAttributeModeId(Integer bbCodeAttributeModeId) {
        this.bbCodeAttributeModeId = bbCodeAttributeModeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373270432-04:00", comments="Source field: zfgbb.bb_code_attribute.name")
    public String getName() {
        return name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373282302-04:00", comments="Source field: zfgbb.bb_code_attribute.name")
    public void setName(String name) {
        this.name = name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373298941-04:00", comments="Source field: zfgbb.bb_code_attribute.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373309761-04:00", comments="Source field: zfgbb.bb_code_attribute.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37332692-04:00", comments="Source field: zfgbb.bb_code_attribute.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37333808-04:00", comments="Source field: zfgbb.bb_code_attribute.updated_ts")
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