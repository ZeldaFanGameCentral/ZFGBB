package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BBCodeAttributeModeDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461279486-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    long countByExample(BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461295946-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int deleteByExample(BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461308315-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int deleteByPrimaryKey(Integer bbCodeAttributeModeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461318215-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int insert(BBCodeAttributeModeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461329385-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int insertSelective(BBCodeAttributeModeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461342854-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    List<BBCodeAttributeModeDbo> selectByExample(BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461357934-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    BBCodeAttributeModeDbo selectByPrimaryKey(Integer bbCodeAttributeModeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461372333-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByExampleSelective(@Param("row") BBCodeAttributeModeDbo row, @Param("example") BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461387943-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByExample(@Param("row") BBCodeAttributeModeDbo row, @Param("example") BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.461439231-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByPrimaryKeySelective(BBCodeAttributeModeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.46146709-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByPrimaryKey(BBCodeAttributeModeDbo row);

    List<BBCodeAttributeModeDbo> selectByExampleWithLimits(BBCodeAttributeModeDboExample example);
}