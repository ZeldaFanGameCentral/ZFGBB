package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFMembergroupDb;
import jakarta.annotation.Generated;

public interface SMFMembergroupDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037766148-04:00", comments="Source Table: smf_1membergroups")
    int deleteByPrimaryKey(Integer idGroup);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037781197-04:00", comments="Source Table: smf_1membergroups")
    int insert(SMFMembergroupDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037794237-04:00", comments="Source Table: smf_1membergroups")
    int insertSelective(SMFMembergroupDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037839315-04:00", comments="Source Table: smf_1membergroups")
    SMFMembergroupDb selectByPrimaryKey(Integer idGroup);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037911713-04:00", comments="Source Table: smf_1membergroups")
    int updateByPrimaryKeySelective(SMFMembergroupDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037930312-04:00", comments="Source Table: smf_1membergroups")
    int updateByPrimaryKeyWithBLOBs(SMFMembergroupDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037946282-04:00", comments="Source Table: smf_1membergroups")
    int updateByPrimaryKey(SMFMembergroupDb row);
}