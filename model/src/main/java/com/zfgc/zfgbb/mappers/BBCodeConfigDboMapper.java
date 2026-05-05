package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.BBCodeConfigDbo;
import com.zfgc.zfgbb.dbo.BBCodeConfigDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BBCodeConfigDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256305036-04:00", comments="Source Table: zfgbb.bb_code_config")
    long countByExample(BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256325555-04:00", comments="Source Table: zfgbb.bb_code_config")
    int deleteByExample(BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256350475-04:00", comments="Source Table: zfgbb.bb_code_config")
    int deleteByPrimaryKey(Integer bbCodeConfigId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256364344-04:00", comments="Source Table: zfgbb.bb_code_config")
    int insert(BBCodeConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256375204-04:00", comments="Source Table: zfgbb.bb_code_config")
    int insertSelective(BBCodeConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256389073-04:00", comments="Source Table: zfgbb.bb_code_config")
    List<BBCodeConfigDbo> selectByExample(BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256404703-04:00", comments="Source Table: zfgbb.bb_code_config")
    BBCodeConfigDbo selectByPrimaryKey(Integer bbCodeConfigId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256419432-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByExampleSelective(@Param("row") BBCodeConfigDbo row, @Param("example") BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256446012-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByExample(@Param("row") BBCodeConfigDbo row, @Param("example") BBCodeConfigDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.256477021-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByPrimaryKeySelective(BBCodeConfigDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.25650393-04:00", comments="Source Table: zfgbb.bb_code_config")
    int updateByPrimaryKey(BBCodeConfigDbo row);

    List<BBCodeConfigDbo> selectByExampleWithLimits(BBCodeConfigDboExample example);
}