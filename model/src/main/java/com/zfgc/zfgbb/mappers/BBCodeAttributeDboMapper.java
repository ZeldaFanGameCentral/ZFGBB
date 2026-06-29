package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BBCodeAttributeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BBCodeAttributeDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707444724-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    long countByExample(BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707460243-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int deleteByExample(BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707473193-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int deleteByPrimaryKey(Integer bbCodeAttributeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707484943-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int insert(BBCodeAttributeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707494302-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int insertSelective(BBCodeAttributeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707511932-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    List<BBCodeAttributeDbo> selectByExample(BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707526491-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    BBCodeAttributeDbo selectByPrimaryKey(Integer bbCodeAttributeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707540361-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByExampleSelective(@Param("row") BBCodeAttributeDbo row, @Param("example") BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70755666-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByExample(@Param("row") BBCodeAttributeDbo row, @Param("example") BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70757651-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByPrimaryKeySelective(BBCodeAttributeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.707603519-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByPrimaryKey(BBCodeAttributeDbo row);

    List<BBCodeAttributeDbo> selectByExampleWithLimits(BBCodeAttributeDboExample example);
}