package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BBCodeConfigDbo;
import com.zfgc.zfgbb.dbo.BBCodeConfigDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BBCodeConfigDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.525936617-04:00", comments="Source Table: zfgbb.bb_code_config")
    long countByExample(BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.525954377-04:00", comments="Source Table: zfgbb.bb_code_config")
    int deleteByExample(BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.525967806-04:00", comments="Source Table: zfgbb.bb_code_config")
    int deleteByPrimaryKey(Integer bbCodeConfigId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.525978366-04:00", comments="Source Table: zfgbb.bb_code_config")
    int insert(BBCodeConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.525989286-04:00", comments="Source Table: zfgbb.bb_code_config")
    int insertSelective(BBCodeConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526001825-04:00", comments="Source Table: zfgbb.bb_code_config")
    List<BBCodeConfigDbo> selectByExample(BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526017055-04:00", comments="Source Table: zfgbb.bb_code_config")
    BBCodeConfigDbo selectByPrimaryKey(Integer bbCodeConfigId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526030894-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByExampleSelective(@Param("row") BBCodeConfigDbo row, @Param("example") BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526045794-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByExample(@Param("row") BBCodeConfigDbo row, @Param("example") BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526066783-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByPrimaryKeySelective(BBCodeConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526086543-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByPrimaryKey(BBCodeConfigDbo row);

    List<BBCodeConfigDbo> selectByExampleWithLimits(BBCodeConfigDboExample example);
}