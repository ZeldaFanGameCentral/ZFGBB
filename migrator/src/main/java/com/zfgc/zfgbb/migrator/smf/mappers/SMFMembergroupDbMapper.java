package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFMembergroupDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMembergroupDbExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMFMembergroupDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037735959-04:00", comments="Source Table: smf_1membergroups")
    long countByExample(SMFMembergroupDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037753268-04:00", comments="Source Table: smf_1membergroups")
    int deleteByExample(SMFMembergroupDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037766148-04:00", comments="Source Table: smf_1membergroups")
    int deleteByPrimaryKey(Integer idGroup);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037781197-04:00", comments="Source Table: smf_1membergroups")
    int insert(SMFMembergroupDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037794237-04:00", comments="Source Table: smf_1membergroups")
    int insertSelective(SMFMembergroupDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037808756-04:00", comments="Source Table: smf_1membergroups")
    List<SMFMembergroupDb> selectByExampleWithBLOBs(SMFMembergroupDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037824086-04:00", comments="Source Table: smf_1membergroups")
    List<SMFMembergroupDb> selectByExample(SMFMembergroupDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037839315-04:00", comments="Source Table: smf_1membergroups")
    SMFMembergroupDb selectByPrimaryKey(Integer idGroup);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037856315-04:00", comments="Source Table: smf_1membergroups")
    int updateByExampleSelective(@Param("row") SMFMembergroupDb row, @Param("example") SMFMembergroupDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037874674-04:00", comments="Source Table: smf_1membergroups")
    int updateByExampleWithBLOBs(@Param("row") SMFMembergroupDb row, @Param("example") SMFMembergroupDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037888694-04:00", comments="Source Table: smf_1membergroups")
    int updateByExample(@Param("row") SMFMembergroupDb row, @Param("example") SMFMembergroupDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037911713-04:00", comments="Source Table: smf_1membergroups")
    int updateByPrimaryKeySelective(SMFMembergroupDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037930312-04:00", comments="Source Table: smf_1membergroups")
    int updateByPrimaryKeyWithBLOBs(SMFMembergroupDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037946282-04:00", comments="Source Table: smf_1membergroups")
    int updateByPrimaryKey(SMFMembergroupDb row);
}