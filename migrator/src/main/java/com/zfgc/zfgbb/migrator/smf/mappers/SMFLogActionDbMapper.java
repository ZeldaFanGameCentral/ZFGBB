package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogActionDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogActionDbExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMFLogActionDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041794565-04:00", comments="Source Table: smf_1log_actions")
    long countByExample(SMFLogActionDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041809995-04:00", comments="Source Table: smf_1log_actions")
    int deleteByExample(SMFLogActionDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041824764-04:00", comments="Source Table: smf_1log_actions")
    int deleteByPrimaryKey(Integer idAction);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041836604-04:00", comments="Source Table: smf_1log_actions")
    int insert(SMFLogActionDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041849583-04:00", comments="Source Table: smf_1log_actions")
    int insertSelective(SMFLogActionDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041863463-04:00", comments="Source Table: smf_1log_actions")
    List<SMFLogActionDb> selectByExampleWithBLOBs(SMFLogActionDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041881022-04:00", comments="Source Table: smf_1log_actions")
    List<SMFLogActionDb> selectByExample(SMFLogActionDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041896002-04:00", comments="Source Table: smf_1log_actions")
    SMFLogActionDb selectByPrimaryKey(Integer idAction);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041913151-04:00", comments="Source Table: smf_1log_actions")
    int updateByExampleSelective(@Param("row") SMFLogActionDb row, @Param("example") SMFLogActionDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041989539-04:00", comments="Source Table: smf_1log_actions")
    int updateByExampleWithBLOBs(@Param("row") SMFLogActionDb row, @Param("example") SMFLogActionDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.042008578-04:00", comments="Source Table: smf_1log_actions")
    int updateByExample(@Param("row") SMFLogActionDb row, @Param("example") SMFLogActionDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.042032997-04:00", comments="Source Table: smf_1log_actions")
    int updateByPrimaryKeySelective(SMFLogActionDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.042053167-04:00", comments="Source Table: smf_1log_actions")
    int updateByPrimaryKeyWithBLOBs(SMFLogActionDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.042069426-04:00", comments="Source Table: smf_1log_actions")
    int updateByPrimaryKey(SMFLogActionDb row);
}