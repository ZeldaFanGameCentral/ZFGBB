package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BBCodeAttributeDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253764187-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_id")
    private Integer bbCodeAttributeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253792746-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_data_type")
    private Integer attributeDataType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253813565-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_index")
    private Integer attributeIndex;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253834594-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_mode_id")
    private Integer bbCodeAttributeModeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253852414-04:00", comments="Source field: zfgbb.bb_code_attribute.name")
    private String name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253872363-04:00", comments="Source field: zfgbb.bb_code_attribute.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253891773-04:00", comments="Source field: zfgbb.bb_code_attribute.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253777496-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_id")
    public Integer getBbCodeAttributeId() {
        return bbCodeAttributeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253785846-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_id")
    public void setBbCodeAttributeId(Integer bbCodeAttributeId) {
        this.bbCodeAttributeId = bbCodeAttributeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253800536-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_data_type")
    public Integer getAttributeDataType() {
        return attributeDataType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253807465-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_data_type")
    public void setAttributeDataType(Integer attributeDataType) {
        this.attributeDataType = attributeDataType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253819905-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_index")
    public Integer getAttributeIndex() {
        return attributeIndex;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253828685-04:00", comments="Source field: zfgbb.bb_code_attribute.attribute_index")
    public void setAttributeIndex(Integer attributeIndex) {
        this.attributeIndex = attributeIndex;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253840464-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_mode_id")
    public Integer getBbCodeAttributeModeId() {
        return bbCodeAttributeModeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253846684-04:00", comments="Source field: zfgbb.bb_code_attribute.bb_code_attribute_mode_id")
    public void setBbCodeAttributeModeId(Integer bbCodeAttributeModeId) {
        this.bbCodeAttributeModeId = bbCodeAttributeModeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253858164-04:00", comments="Source field: zfgbb.bb_code_attribute.name")
    public String getName() {
        return name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253866193-04:00", comments="Source field: zfgbb.bb_code_attribute.name")
    public void setName(String name) {
        this.name = name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253879213-04:00", comments="Source field: zfgbb.bb_code_attribute.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253885903-04:00", comments="Source field: zfgbb.bb_code_attribute.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253897872-04:00", comments="Source field: zfgbb.bb_code_attribute.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.253905602-04:00", comments="Source field: zfgbb.bb_code_attribute.updated_ts")
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