package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFPollsDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFPollsDbExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMFPollsDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.660301334-04:00", comments="Source Table: smf_1polls")
    long countByExample(SMFPollsDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.660353482-04:00", comments="Source Table: smf_1polls")
    int deleteByExample(SMFPollsDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.6604263-04:00", comments="Source Table: smf_1polls")
    int deleteByPrimaryKey(Integer idPoll);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.660476559-04:00", comments="Source Table: smf_1polls")
    int insert(SMFPollsDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.660605075-04:00", comments="Source Table: smf_1polls")
    int insertSelective(SMFPollsDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.660668253-04:00", comments="Source Table: smf_1polls")
    List<SMFPollsDb> selectByExample(SMFPollsDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.660720571-04:00", comments="Source Table: smf_1polls")
    SMFPollsDb selectByPrimaryKey(Integer idPoll);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.66075845-04:00", comments="Source Table: smf_1polls")
    int updateByExampleSelective(@Param("row") SMFPollsDb row, @Param("example") SMFPollsDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.660801599-04:00", comments="Source Table: smf_1polls")
    int updateByExample(@Param("row") SMFPollsDb row, @Param("example") SMFPollsDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.660855037-04:00", comments="Source Table: smf_1polls")
    int updateByPrimaryKeySelective(SMFPollsDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.660910026-04:00", comments="Source Table: smf_1polls")
    int updateByPrimaryKey(SMFPollsDb row);
}