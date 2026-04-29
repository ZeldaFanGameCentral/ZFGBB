package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BBCodeConfigDbo;
import com.zfgc.zfgbb.dbo.BBCodeConfigDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BBCodeConfigDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375545807-04:00", comments="Source Table: zfgbb.bb_code_config")
    long countByExample(BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375570756-04:00", comments="Source Table: zfgbb.bb_code_config")
    int deleteByExample(BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375584386-04:00", comments="Source Table: zfgbb.bb_code_config")
    int deleteByPrimaryKey(Integer bbCodeConfigId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375594166-04:00", comments="Source Table: zfgbb.bb_code_config")
    int insert(BBCodeConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375603105-04:00", comments="Source Table: zfgbb.bb_code_config")
    int insertSelective(BBCodeConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375614055-04:00", comments="Source Table: zfgbb.bb_code_config")
    List<BBCodeConfigDbo> selectByExample(BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375625165-04:00", comments="Source Table: zfgbb.bb_code_config")
    BBCodeConfigDbo selectByPrimaryKey(Integer bbCodeConfigId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375682883-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByExampleSelective(@Param("row") BBCodeConfigDbo row, @Param("example") BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375698062-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByExample(@Param("row") BBCodeConfigDbo row, @Param("example") BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375721931-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByPrimaryKeySelective(BBCodeConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.375739311-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByPrimaryKey(BBCodeConfigDbo row);
}