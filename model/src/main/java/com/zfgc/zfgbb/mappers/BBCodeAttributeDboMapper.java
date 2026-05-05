package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BBCodeAttributeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BBCodeAttributeDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.460127993-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    long countByExample(BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.460144463-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int deleteByExample(BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.460158012-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int deleteByPrimaryKey(Integer bbCodeAttributeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.460170762-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int insert(BBCodeAttributeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.460180971-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int insertSelective(BBCodeAttributeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.460197231-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    List<BBCodeAttributeDbo> selectByExample(BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.46021438-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    BBCodeAttributeDbo selectByPrimaryKey(Integer bbCodeAttributeId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.46022837-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByExampleSelective(@Param("row") BBCodeAttributeDbo row, @Param("example") BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.460244199-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByExample(@Param("row") BBCodeAttributeDbo row, @Param("example") BBCodeAttributeDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.460282458-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByPrimaryKeySelective(BBCodeAttributeDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.460303697-04:00", comments="Source Table: zfgbb.bb_code_attribute")
    int updateByPrimaryKey(BBCodeAttributeDbo row);

    List<BBCodeAttributeDbo> selectByExampleWithLimits(BBCodeAttributeDboExample example);
}