package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogKarmaDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogKarmaDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogKarmaDbKey;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogKarmaDbWithBLOBs;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMFLogKarmaDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.643517136-04:00", comments="Source Table: smf_1log_karma")
    long countByExample(SMFLogKarmaDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.644516686-04:00", comments="Source Table: smf_1log_karma")
    int deleteByExample(SMFLogKarmaDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.644767858-04:00", comments="Source Table: smf_1log_karma")
    int deleteByPrimaryKey(SMFLogKarmaDbKey key);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.64504667-04:00", comments="Source Table: smf_1log_karma")
    int insert(SMFLogKarmaDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.645258584-04:00", comments="Source Table: smf_1log_karma")
    int insertSelective(SMFLogKarmaDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.645551475-04:00", comments="Source Table: smf_1log_karma")
    List<SMFLogKarmaDbWithBLOBs> selectByExampleWithBLOBs(SMFLogKarmaDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.645807087-04:00", comments="Source Table: smf_1log_karma")
    List<SMFLogKarmaDb> selectByExample(SMFLogKarmaDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.64604509-04:00", comments="Source Table: smf_1log_karma")
    SMFLogKarmaDbWithBLOBs selectByPrimaryKey(SMFLogKarmaDbKey key);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.646260484-04:00", comments="Source Table: smf_1log_karma")
    int updateByExampleSelective(@Param("row") SMFLogKarmaDbWithBLOBs row, @Param("example") SMFLogKarmaDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.646491907-04:00", comments="Source Table: smf_1log_karma")
    int updateByExampleWithBLOBs(@Param("row") SMFLogKarmaDbWithBLOBs row, @Param("example") SMFLogKarmaDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.64672571-04:00", comments="Source Table: smf_1log_karma")
    int updateByExample(@Param("row") SMFLogKarmaDb row, @Param("example") SMFLogKarmaDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.647226155-04:00", comments="Source Table: smf_1log_karma")
    int updateByPrimaryKeySelective(SMFLogKarmaDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.647468208-04:00", comments="Source Table: smf_1log_karma")
    int updateByPrimaryKeyWithBLOBs(SMFLogKarmaDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.64773428-04:00", comments="Source Table: smf_1log_karma")
    int updateByPrimaryKey(SMFLogKarmaDb row);
}