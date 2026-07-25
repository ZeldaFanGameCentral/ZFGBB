package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFMessageHistoryDb;
import jakarta.annotation.Generated;

public interface SMFMessageHistoryDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679693314-04:00", comments="Source Table: smf_1messages_history")
    int deleteByPrimaryKey(Integer idEdit);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679708663-04:00", comments="Source Table: smf_1messages_history")
    int insert(SMFMessageHistoryDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679724323-04:00", comments="Source Table: smf_1messages_history")
    int insertSelective(SMFMessageHistoryDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679780951-04:00", comments="Source Table: smf_1messages_history")
    SMFMessageHistoryDb selectByPrimaryKey(Integer idEdit);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679871168-04:00", comments="Source Table: smf_1messages_history")
    int updateByPrimaryKeySelective(SMFMessageHistoryDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679899838-04:00", comments="Source Table: smf_1messages_history")
    int updateByPrimaryKeyWithBLOBs(SMFMessageHistoryDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679936146-04:00", comments="Source Table: smf_1messages_history")
    int updateByPrimaryKey(SMFMessageHistoryDb row);
}