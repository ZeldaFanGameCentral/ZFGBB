package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class AttributeValueMappingDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_value_mapping.attribute_value_mapping_id")
    private Integer attributeValueMappingId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_value_mapping.attribute_data_type")
    private String attributeDataType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_value_mapping.from_value")
    private String fromValue;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_value_mapping.to_value")
    private String toValue;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_value_mapping.attribute_value_mapping_id")
    public Integer getAttributeValueMappingId() {
        return attributeValueMappingId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_value_mapping.attribute_value_mapping_id")
    public void setAttributeValueMappingId(Integer attributeValueMappingId) {
        this.attributeValueMappingId = attributeValueMappingId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_value_mapping.attribute_data_type")
    public String getAttributeDataType() {
        return attributeDataType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_value_mapping.attribute_data_type")
    public void setAttributeDataType(String attributeDataType) {
        this.attributeDataType = attributeDataType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_value_mapping.from_value")
    public String getFromValue() {
        return fromValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_value_mapping.from_value")
    public void setFromValue(String fromValue) {
        this.fromValue = fromValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_value_mapping.to_value")
    public String getToValue() {
        return toValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_value_mapping.to_value")
    public void setToValue(String toValue) {
        this.toValue = toValue;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    public Integer getPkId() {
        return attributeValueMappingId;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    public OffsetDateTime getCreatedTime() {
        return null;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    public OffsetDateTime getUpdatedTime() {
        return null;
    }
}