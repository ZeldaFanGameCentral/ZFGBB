package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogPollsDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogPollsDbExample;
import jakarta.annotation.Generated;
import java.util.List;

public interface SMFLogPollsDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.666145709-04:00", comments="Source Table: smf_1log_polls")
    int insert(SMFLogPollsDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.666197607-04:00", comments="Source Table: smf_1log_polls")
    int insertSelective(SMFLogPollsDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.666244176-04:00", comments="Source Table: smf_1log_polls")
    List<SMFLogPollsDb> selectByExample(SMFLogPollsDbExample example);

}