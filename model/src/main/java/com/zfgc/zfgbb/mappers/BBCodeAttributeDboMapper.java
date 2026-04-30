package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BBCodeAttributeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BBCodeAttributeDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373389818-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    long countByExample(BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373405628-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int deleteByExample(BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373417587-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int deleteByPrimaryKey(Integer bbCodeAttributeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373428107-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int insert(BBCodeAttributeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373437547-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int insertSelective(BBCodeAttributeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373450236-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    List<BBCodeAttributeDbo> selectByExample(BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373462056-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    BBCodeAttributeDbo selectByPrimaryKey(Integer bbCodeAttributeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373475835-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByExampleSelective(@Param("row") BBCodeAttributeDbo row, @Param("example") BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373491215-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByExample(@Param("row") BBCodeAttributeDbo row, @Param("example") BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373508014-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByPrimaryKeySelective(BBCodeAttributeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.373531643-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByPrimaryKey(BBCodeAttributeDbo row);
}