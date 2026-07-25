package com.zfgc.zfgbb.migrator.ci.mappers;

import com.zfgc.zfgbb.migrator.ci.dbo.CiPotmDb;
import com.zfgc.zfgbb.migrator.ci.dbo.CiPotmDbExample;
import jakarta.annotation.Generated;
import java.util.List;

public interface CiPotmDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.050249687-04:00", comments="Source Table: ci_potms")
    int deleteByPrimaryKey(Integer id);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.050262097-04:00", comments="Source Table: ci_potms")
    int insert(CiPotmDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.050273096-04:00", comments="Source Table: ci_potms")
    int insertSelective(CiPotmDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.050286196-04:00", comments="Source Table: ci_potms")
    List<CiPotmDb> selectByExample(CiPotmDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.050317155-04:00", comments="Source Table: ci_potms")
    CiPotmDb selectByPrimaryKey(Integer id);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.050380853-04:00", comments="Source Table: ci_potms")
    int updateByPrimaryKeySelective(CiPotmDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.050399722-04:00", comments="Source Table: ci_potms")
    int updateByPrimaryKey(CiPotmDb row);
}