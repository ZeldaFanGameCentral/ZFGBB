package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.AttributeDataTypeDbo;
import com.zfgc.zfgbb.dbo.AttributeDataTypeDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AttributeDataTypeDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706461634-04:00", comments="Source Table: zfgbb.attribute_data_type")
    long countByExample(AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706478134-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int deleteByExample(AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706490874-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int deleteByPrimaryKey(Integer attributeDataTypeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706500803-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int insert(AttributeDataTypeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706511933-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int insertSelective(AttributeDataTypeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706528552-04:00", comments="Source Table: zfgbb.attribute_data_type")
    List<AttributeDataTypeDbo> selectByExample(AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706549242-04:00", comments="Source Table: zfgbb.attribute_data_type")
    AttributeDataTypeDbo selectByPrimaryKey(Integer attributeDataTypeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706564651-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByExampleSelective(@Param("row") AttributeDataTypeDbo row, @Param("example") AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.706581391-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByExample(@Param("row") AttributeDataTypeDbo row, @Param("example") AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70660077-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByPrimaryKeySelective(AttributeDataTypeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70661883-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByPrimaryKey(AttributeDataTypeDbo row);

    List<AttributeDataTypeDbo> selectByExampleWithLimits(AttributeDataTypeDboExample example);
}