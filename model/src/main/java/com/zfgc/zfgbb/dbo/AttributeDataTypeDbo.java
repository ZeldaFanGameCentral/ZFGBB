package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class AttributeDataTypeDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252563095-04:00", comments="Source field: zfgbb.attribute_data_type.attribute_data_type_id")
    private Integer attributeDataTypeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252589074-04:00", comments="Source field: zfgbb.attribute_data_type.type_name")
    private String typeName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252633453-04:00", comments="Source field: zfgbb.attribute_data_type.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252654992-04:00", comments="Source field: zfgbb.attribute_data_type.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252574064-04:00", comments="Source field: zfgbb.attribute_data_type.attribute_data_type_id")
    public Integer getAttributeDataTypeId() {
        return attributeDataTypeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252583344-04:00", comments="Source field: zfgbb.attribute_data_type.attribute_data_type_id")
    public void setAttributeDataTypeId(Integer attributeDataTypeId) {
        this.attributeDataTypeId = attributeDataTypeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252597344-04:00", comments="Source field: zfgbb.attribute_data_type.type_name")
    public String getTypeName() {
        return typeName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252626273-04:00", comments="Source field: zfgbb.attribute_data_type.type_name")
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252642452-04:00", comments="Source field: zfgbb.attribute_data_type.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252649012-04:00", comments="Source field: zfgbb.attribute_data_type.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252676601-04:00", comments="Source field: zfgbb.attribute_data_type.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252687391-04:00", comments="Source field: zfgbb.attribute_data_type.updated_ts")
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