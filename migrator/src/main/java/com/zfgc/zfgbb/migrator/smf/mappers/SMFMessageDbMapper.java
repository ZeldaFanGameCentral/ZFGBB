package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageDbWithBLOBs;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMFMessageDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.681609986-04:00", comments="Source Table: smf_1messages")
    long countByExample(SMFMessageDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.681637226-04:00", comments="Source Table: smf_1messages")
    int deleteByExample(SMFMessageDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.681662105-04:00", comments="Source Table: smf_1messages")
    int deleteByPrimaryKey(Integer idMsg);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.681680784-04:00", comments="Source Table: smf_1messages")
    int insert(SMFMessageDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.681704004-04:00", comments="Source Table: smf_1messages")
    int insertSelective(SMFMessageDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.681729853-04:00", comments="Source Table: smf_1messages")
    List<SMFMessageDbWithBLOBs> selectByExampleWithBLOBs(SMFMessageDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.681750302-04:00", comments="Source Table: smf_1messages")
    List<SMFMessageDb> selectByExample(SMFMessageDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.681788171-04:00", comments="Source Table: smf_1messages")
    SMFMessageDbWithBLOBs selectByPrimaryKey(Integer idMsg);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.68181633-04:00", comments="Source Table: smf_1messages")
    int updateByExampleSelective(@Param("row") SMFMessageDbWithBLOBs row, @Param("example") SMFMessageDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.68183925-04:00", comments="Source Table: smf_1messages")
    int updateByExampleWithBLOBs(@Param("row") SMFMessageDbWithBLOBs row, @Param("example") SMFMessageDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.681858219-04:00", comments="Source Table: smf_1messages")
    int updateByExample(@Param("row") SMFMessageDb row, @Param("example") SMFMessageDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.681905978-04:00", comments="Source Table: smf_1messages")
    int updateByPrimaryKeySelective(SMFMessageDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.681936787-04:00", comments="Source Table: smf_1messages")
    int updateByPrimaryKeyWithBLOBs(SMFMessageDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.681959126-04:00", comments="Source Table: smf_1messages")
    int updateByPrimaryKey(SMFMessageDb row);
}