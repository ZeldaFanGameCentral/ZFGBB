package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFMembersDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMembersDbWithBLOBs;
import jakarta.annotation.Generated;

public interface SMFMembersDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674767211-04:00", comments="Source Table: smf_1members")
    int deleteByPrimaryKey(Integer idMember);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674783101-04:00", comments="Source Table: smf_1members")
    int insert(SMFMembersDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.67479784-04:00", comments="Source Table: smf_1members")
    int insertSelective(SMFMembersDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674856008-04:00", comments="Source Table: smf_1members")
    SMFMembersDbWithBLOBs selectByPrimaryKey(Integer idMember);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674997304-04:00", comments="Source Table: smf_1members")
    int updateByPrimaryKeySelective(SMFMembersDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.675045563-04:00", comments="Source Table: smf_1members")
    int updateByPrimaryKeyWithBLOBs(SMFMembersDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.675075302-04:00", comments="Source Table: smf_1members")
    int updateByPrimaryKey(SMFMembersDb row);
}