package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.AttributeDataTypeDbo;
import com.zfgc.zfgbb.dbo.AttributeDataTypeDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AttributeDataTypeDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372530966-04:00", comments="Source Table: zfgbb.attribute_data_type")
    long countByExample(AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372544186-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int deleteByExample(AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372563475-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int deleteByPrimaryKey(Integer attributeDataTypeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372576225-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int insert(AttributeDataTypeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372585085-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int insertSelective(AttributeDataTypeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372605554-04:00", comments="Source Table: zfgbb.attribute_data_type")
    List<AttributeDataTypeDbo> selectByExample(AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372617923-04:00", comments="Source Table: zfgbb.attribute_data_type")
    AttributeDataTypeDbo selectByPrimaryKey(Integer attributeDataTypeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372636493-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByExampleSelective(@Param("row") AttributeDataTypeDbo row, @Param("example") AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372657112-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByExample(@Param("row") AttributeDataTypeDbo row, @Param("example") AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372675972-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByPrimaryKeySelective(AttributeDataTypeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.372690931-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByPrimaryKey(AttributeDataTypeDbo row);
}