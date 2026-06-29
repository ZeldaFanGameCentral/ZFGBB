package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class AttributeDataTypeDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706322879-04:00", comments="Source field: zfgbb.attribute_data_type.attribute_data_type_id")
    private Integer attributeDataTypeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706349768-04:00", comments="Source field: zfgbb.attribute_data_type.type_name")
    private String typeName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706372547-04:00", comments="Source field: zfgbb.attribute_data_type.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706393087-04:00", comments="Source field: zfgbb.attribute_data_type.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706334478-04:00", comments="Source field: zfgbb.attribute_data_type.attribute_data_type_id")
    public Integer getAttributeDataTypeId() {
        return attributeDataTypeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706342508-04:00", comments="Source field: zfgbb.attribute_data_type.attribute_data_type_id")
    public void setAttributeDataTypeId(Integer attributeDataTypeId) {
        this.attributeDataTypeId = attributeDataTypeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706356728-04:00", comments="Source field: zfgbb.attribute_data_type.type_name")
    public String getTypeName() {
        return typeName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706365937-04:00", comments="Source field: zfgbb.attribute_data_type.type_name")
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706380087-04:00", comments="Source field: zfgbb.attribute_data_type.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706386927-04:00", comments="Source field: zfgbb.attribute_data_type.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706399646-04:00", comments="Source field: zfgbb.attribute_data_type.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706417616-04:00", comments="Source field: zfgbb.attribute_data_type.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return attributeDataTypeId;
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