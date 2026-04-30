package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BBCodeAttributeModeDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374725554-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    long countByExample(BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374740934-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int deleteByExample(BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374751683-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int deleteByPrimaryKey(Integer bbCodeAttributeModeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374761023-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int insert(BBCodeAttributeModeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374770143-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int insertSelective(BBCodeAttributeModeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374781372-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    List<BBCodeAttributeModeDbo> selectByExample(BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374792782-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    BBCodeAttributeModeDbo selectByPrimaryKey(Integer bbCodeAttributeModeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374804172-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByExampleSelective(@Param("row") BBCodeAttributeModeDbo row, @Param("example") BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374815901-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByExample(@Param("row") BBCodeAttributeModeDbo row, @Param("example") BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.374830991-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByPrimaryKeySelective(BBCodeAttributeModeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.37484715-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByPrimaryKey(BBCodeAttributeModeDbo row);
}