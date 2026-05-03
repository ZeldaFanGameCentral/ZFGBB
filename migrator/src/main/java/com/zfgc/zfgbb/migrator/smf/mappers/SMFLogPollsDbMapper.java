package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogPollsDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogPollsDbExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMFLogPollsDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.665939245-04:00", comments="Source Table: smf_1log_polls")
    long countByExample(SMFLogPollsDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.665992493-04:00", comments="Source Table: smf_1log_polls")
    int deleteByExample(SMFLogPollsDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.666145709-04:00", comments="Source Table: smf_1log_polls")
    int insert(SMFLogPollsDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.666197607-04:00", comments="Source Table: smf_1log_polls")
    int insertSelective(SMFLogPollsDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.666244176-04:00", comments="Source Table: smf_1log_polls")
    List<SMFLogPollsDb> selectByExample(SMFLogPollsDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.666295784-04:00", comments="Source Table: smf_1log_polls")
    int updateByExampleSelective(@Param("row") SMFLogPollsDb row, @Param("example") SMFLogPollsDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.666341703-04:00", comments="Source Table: smf_1log_polls")
    int updateByExample(@Param("row") SMFLogPollsDb row, @Param("example") SMFLogPollsDbExample example);
}