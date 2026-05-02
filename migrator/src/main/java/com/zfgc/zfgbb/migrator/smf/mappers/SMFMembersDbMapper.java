package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFMembersDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMembersDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMembersDbWithBLOBs;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMFMembersDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674720442-04:00", comments="Source Table: smf_1members")
    long countByExample(SMFMembersDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674749372-04:00", comments="Source Table: smf_1members")
    int deleteByExample(SMFMembersDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674767211-04:00", comments="Source Table: smf_1members")
    int deleteByPrimaryKey(Integer idMember);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674783101-04:00", comments="Source Table: smf_1members")
    int insert(SMFMembersDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.67479784-04:00", comments="Source Table: smf_1members")
    int insertSelective(SMFMembersDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.67481669-04:00", comments="Source Table: smf_1members")
    List<SMFMembersDbWithBLOBs> selectByExampleWithBLOBs(SMFMembersDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674837539-04:00", comments="Source Table: smf_1members")
    List<SMFMembersDb> selectByExample(SMFMembersDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674856008-04:00", comments="Source Table: smf_1members")
    SMFMembersDbWithBLOBs selectByPrimaryKey(Integer idMember);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674875588-04:00", comments="Source Table: smf_1members")
    int updateByExampleSelective(@Param("row") SMFMembersDbWithBLOBs row, @Param("example") SMFMembersDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674894047-04:00", comments="Source Table: smf_1members")
    int updateByExampleWithBLOBs(@Param("row") SMFMembersDbWithBLOBs row, @Param("example") SMFMembersDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674912457-04:00", comments="Source Table: smf_1members")
    int updateByExample(@Param("row") SMFMembersDb row, @Param("example") SMFMembersDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674997304-04:00", comments="Source Table: smf_1members")
    int updateByPrimaryKeySelective(SMFMembersDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.675045563-04:00", comments="Source Table: smf_1members")
    int updateByPrimaryKeyWithBLOBs(SMFMembersDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.675075302-04:00", comments="Source Table: smf_1members")
    int updateByPrimaryKey(SMFMembersDb row);
}