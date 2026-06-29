package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BBCodeAttributeModeDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708504611-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    long countByExample(BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70852045-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int deleteByExample(BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70853328-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int deleteByPrimaryKey(Integer bbCodeAttributeModeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70854337-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int insert(BBCodeAttributeModeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708554319-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int insertSelective(BBCodeAttributeModeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708569309-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    List<BBCodeAttributeModeDbo> selectByExample(BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708585498-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    BBCodeAttributeModeDbo selectByPrimaryKey(Integer bbCodeAttributeModeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708607858-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByExampleSelective(@Param("row") BBCodeAttributeModeDbo row, @Param("example") BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708626057-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByExample(@Param("row") BBCodeAttributeModeDbo row, @Param("example") BBCodeAttributeModeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708657786-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByPrimaryKeySelective(BBCodeAttributeModeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.708676936-04:00", comments="Source Table: zfgbb.bb_code_attribute_mode")
    int updateByPrimaryKey(BBCodeAttributeModeDbo row);

    List<BBCodeAttributeModeDbo> selectByExampleWithLimits(BBCodeAttributeModeDboExample example);
}