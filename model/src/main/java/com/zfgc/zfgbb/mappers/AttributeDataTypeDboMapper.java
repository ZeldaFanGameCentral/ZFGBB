package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.AttributeDataTypeDbo;
import com.zfgc.zfgbb.dbo.AttributeDataTypeDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AttributeDataTypeDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.522566909-04:00", comments="Source Table: zfgbb.attribute_data_type")
    long countByExample(AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.522624417-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int deleteByExample(AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.522645316-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int deleteByPrimaryKey(Integer attributeDataTypeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.522659386-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int insert(AttributeDataTypeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.522672145-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int insertSelective(AttributeDataTypeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.522695385-04:00", comments="Source Table: zfgbb.attribute_data_type")
    List<AttributeDataTypeDbo> selectByExample(AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.522715014-04:00", comments="Source Table: zfgbb.attribute_data_type")
    AttributeDataTypeDbo selectByPrimaryKey(Integer attributeDataTypeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.522731523-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByExampleSelective(@Param("row") AttributeDataTypeDbo row, @Param("example") AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.522757542-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByExample(@Param("row") AttributeDataTypeDbo row, @Param("example") AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.522779002-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByPrimaryKeySelective(AttributeDataTypeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.522799311-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByPrimaryKey(AttributeDataTypeDbo row);

    List<AttributeDataTypeDbo> selectByExampleWithLimits(AttributeDataTypeDboExample example);
}