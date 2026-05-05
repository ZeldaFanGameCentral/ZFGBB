package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.AttributeDataTypeDbo;
import com.zfgc.zfgbb.dbo.AttributeDataTypeDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AttributeDataTypeDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252755939-04:00", comments="Source Table: zfgbb.attribute_data_type")
    long countByExample(AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252816077-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int deleteByExample(AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252835366-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int deleteByPrimaryKey(Integer attributeDataTypeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252848256-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int insert(AttributeDataTypeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252857905-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int insertSelective(AttributeDataTypeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252872755-04:00", comments="Source Table: zfgbb.attribute_data_type")
    List<AttributeDataTypeDbo> selectByExample(AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252897444-04:00", comments="Source Table: zfgbb.attribute_data_type")
    AttributeDataTypeDbo selectByPrimaryKey(Integer attributeDataTypeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252918203-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByExampleSelective(@Param("row") AttributeDataTypeDbo row, @Param("example") AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252948782-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByExample(@Param("row") AttributeDataTypeDbo row, @Param("example") AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252976912-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByPrimaryKeySelective(AttributeDataTypeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.252996171-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByPrimaryKey(AttributeDataTypeDbo row);

    List<AttributeDataTypeDbo> selectByExampleWithLimits(AttributeDataTypeDboExample example);
}