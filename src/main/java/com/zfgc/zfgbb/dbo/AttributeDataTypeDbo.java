package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class AttributeDataTypeDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372437259-04:00", comments="Source field: zfgbb.attribute_data_type.attribute_data_type_id")
    private Integer attributeDataTypeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372456779-04:00", comments="Source field: zfgbb.attribute_data_type.type_name")
    private String typeName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372473258-04:00", comments="Source field: zfgbb.attribute_data_type.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372487858-04:00", comments="Source field: zfgbb.attribute_data_type.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372445199-04:00", comments="Source field: zfgbb.attribute_data_type.attribute_data_type_id")
    public Integer getAttributeDataTypeId() {
        return attributeDataTypeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372451599-04:00", comments="Source field: zfgbb.attribute_data_type.attribute_data_type_id")
    public void setAttributeDataTypeId(Integer attributeDataTypeId) {
        this.attributeDataTypeId = attributeDataTypeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372461879-04:00", comments="Source field: zfgbb.attribute_data_type.type_name")
    public String getTypeName() {
        return typeName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372468678-04:00", comments="Source field: zfgbb.attribute_data_type.type_name")
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372478378-04:00", comments="Source field: zfgbb.attribute_data_type.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372483428-04:00", comments="Source field: zfgbb.attribute_data_type.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372492658-04:00", comments="Source field: zfgbb.attribute_data_type.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372497367-04:00", comments="Source field: zfgbb.attribute_data_type.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return attributeDataTypeId;
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