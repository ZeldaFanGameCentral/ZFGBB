package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.AttributeDataTypeDbo;
import com.zfgc.zfgbb.dbo.AttributeDataTypeDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AttributeDataTypeDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.458874523-04:00", comments="Source Table: zfgbb.attribute_data_type")
    long countByExample(AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.458936871-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int deleteByExample(AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45895897-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int deleteByPrimaryKey(Integer attributeDataTypeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45898177-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int insert(AttributeDataTypeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.459016099-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int insertSelective(AttributeDataTypeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.459041398-04:00", comments="Source Table: zfgbb.attribute_data_type")
    List<AttributeDataTypeDbo> selectByExample(AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.459067027-04:00", comments="Source Table: zfgbb.attribute_data_type")
    AttributeDataTypeDbo selectByPrimaryKey(Integer attributeDataTypeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.459088686-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByExampleSelective(@Param("row") AttributeDataTypeDbo row, @Param("example") AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.459115005-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByExample(@Param("row") AttributeDataTypeDbo row, @Param("example") AttributeDataTypeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.459139515-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByPrimaryKeySelective(AttributeDataTypeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.459159864-04:00", comments="Source Table: zfgbb.attribute_data_type")
    int updateByPrimaryKey(AttributeDataTypeDbo row);

    List<AttributeDataTypeDbo> selectByExampleWithLimits(AttributeDataTypeDboExample example);
}