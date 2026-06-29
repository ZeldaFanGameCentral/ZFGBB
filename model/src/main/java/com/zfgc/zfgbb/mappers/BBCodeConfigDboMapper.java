package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BBCodeConfigDbo;
import com.zfgc.zfgbb.dbo.BBCodeConfigDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BBCodeConfigDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.709572038-04:00", comments="Source Table: zfgbb.bb_code_config")
    long countByExample(BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.709585787-04:00", comments="Source Table: zfgbb.bb_code_config")
    int deleteByExample(BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.709598357-04:00", comments="Source Table: zfgbb.bb_code_config")
    int deleteByPrimaryKey(Integer bbCodeConfigId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.709608197-04:00", comments="Source Table: zfgbb.bb_code_config")
    int insert(BBCodeConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.709619356-04:00", comments="Source Table: zfgbb.bb_code_config")
    int insertSelective(BBCodeConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.709632556-04:00", comments="Source Table: zfgbb.bb_code_config")
    List<BBCodeConfigDbo> selectByExample(BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.709648275-04:00", comments="Source Table: zfgbb.bb_code_config")
    BBCodeConfigDbo selectByPrimaryKey(Integer bbCodeConfigId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.709719753-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByExampleSelective(@Param("row") BBCodeConfigDbo row, @Param("example") BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.709739852-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByExample(@Param("row") BBCodeConfigDbo row, @Param("example") BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.709760872-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByPrimaryKeySelective(BBCodeConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.709781431-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByPrimaryKey(BBCodeConfigDbo row);

    List<BBCodeConfigDbo> selectByExampleWithLimits(BBCodeConfigDboExample example);
}