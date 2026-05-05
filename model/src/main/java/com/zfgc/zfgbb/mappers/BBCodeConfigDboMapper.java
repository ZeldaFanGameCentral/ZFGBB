package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BBCodeConfigDbo;
import com.zfgc.zfgbb.dbo.BBCodeConfigDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BBCodeConfigDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462335223-04:00", comments="Source Table: zfgbb.bb_code_config")
    long countByExample(BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462349582-04:00", comments="Source Table: zfgbb.bb_code_config")
    int deleteByExample(BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462362392-04:00", comments="Source Table: zfgbb.bb_code_config")
    int deleteByPrimaryKey(Integer bbCodeConfigId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462372471-04:00", comments="Source Table: zfgbb.bb_code_config")
    int insert(BBCodeConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462383471-04:00", comments="Source Table: zfgbb.bb_code_config")
    int insertSelective(BBCodeConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462396971-04:00", comments="Source Table: zfgbb.bb_code_config")
    List<BBCodeConfigDbo> selectByExample(BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.4624126-04:00", comments="Source Table: zfgbb.bb_code_config")
    BBCodeConfigDbo selectByPrimaryKey(Integer bbCodeConfigId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.46242594-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByExampleSelective(@Param("row") BBCodeConfigDbo row, @Param("example") BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462443399-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByExample(@Param("row") BBCodeConfigDbo row, @Param("example") BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462467418-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByPrimaryKeySelective(BBCodeConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.462486028-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByPrimaryKey(BBCodeConfigDbo row);

    List<BBCodeConfigDbo> selectByExampleWithLimits(BBCodeConfigDboExample example);
}