package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFPollChoicesDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFPollChoicesDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFPollChoicesDbKey;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMFPollChoicesDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.663284754-04:00", comments="Source Table: smf_1poll_choices")
    long countByExample(SMFPollChoicesDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.663330693-04:00", comments="Source Table: smf_1poll_choices")
    int deleteByExample(SMFPollChoicesDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.663384282-04:00", comments="Source Table: smf_1poll_choices")
    int deleteByPrimaryKey(SMFPollChoicesDbKey key);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.66343239-04:00", comments="Source Table: smf_1poll_choices")
    int insert(SMFPollChoicesDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.663472409-04:00", comments="Source Table: smf_1poll_choices")
    int insertSelective(SMFPollChoicesDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.663524597-04:00", comments="Source Table: smf_1poll_choices")
    List<SMFPollChoicesDb> selectByExample(SMFPollChoicesDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.663577306-04:00", comments="Source Table: smf_1poll_choices")
    SMFPollChoicesDb selectByPrimaryKey(SMFPollChoicesDbKey key);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.663627374-04:00", comments="Source Table: smf_1poll_choices")
    int updateByExampleSelective(@Param("row") SMFPollChoicesDb row, @Param("example") SMFPollChoicesDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.663687332-04:00", comments="Source Table: smf_1poll_choices")
    int updateByExample(@Param("row") SMFPollChoicesDb row, @Param("example") SMFPollChoicesDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.66375976-04:00", comments="Source Table: smf_1poll_choices")
    int updateByPrimaryKeySelective(SMFPollChoicesDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.663829548-04:00", comments="Source Table: smf_1poll_choices")
    int updateByPrimaryKey(SMFPollChoicesDb row);
}