package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFTopicDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFTopicDbExample;
import jakarta.annotation.Generated;
import java.util.List;

public interface SMFTopicDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.684004705-04:00", comments="Source Table: smf_1topics")
    int deleteByPrimaryKey(Integer idTopic);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.684018244-04:00", comments="Source Table: smf_1topics")
    int insert(SMFTopicDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.684030044-04:00", comments="Source Table: smf_1topics")
    int insertSelective(SMFTopicDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.684049753-04:00", comments="Source Table: smf_1topics")
    List<SMFTopicDb> selectByExample(SMFTopicDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.684064883-04:00", comments="Source Table: smf_1topics")
    SMFTopicDb selectByPrimaryKey(Integer idTopic);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.684123251-04:00", comments="Source Table: smf_1topics")
    int updateByPrimaryKeySelective(SMFTopicDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.684147021-04:00", comments="Source Table: smf_1topics")
    int updateByPrimaryKey(SMFTopicDb row);
}