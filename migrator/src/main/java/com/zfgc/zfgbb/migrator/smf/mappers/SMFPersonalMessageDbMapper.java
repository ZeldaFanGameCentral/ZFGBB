package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFPersonalMessageDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFPersonalMessageDbExample;
import jakarta.annotation.Generated;
import java.util.List;

public interface SMFPersonalMessageDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.035180333-04:00", comments="Source Table: smf_1personal_messages")
    int deleteByPrimaryKey(Integer idPm);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.035194172-04:00", comments="Source Table: smf_1personal_messages")
    int insert(SMFPersonalMessageDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.035209542-04:00", comments="Source Table: smf_1personal_messages")
    int insertSelective(SMFPersonalMessageDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.035224801-04:00", comments="Source Table: smf_1personal_messages")
    List<SMFPersonalMessageDb> selectByExampleWithBLOBs(SMFPersonalMessageDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.035240691-04:00", comments="Source Table: smf_1personal_messages")
    List<SMFPersonalMessageDb> selectByExample(SMFPersonalMessageDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.03525678-04:00", comments="Source Table: smf_1personal_messages")
    SMFPersonalMessageDb selectByPrimaryKey(Integer idPm);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.035356917-04:00", comments="Source Table: smf_1personal_messages")
    int updateByPrimaryKeySelective(SMFPersonalMessageDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.035387456-04:00", comments="Source Table: smf_1personal_messages")
    int updateByPrimaryKeyWithBLOBs(SMFPersonalMessageDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.035459764-04:00", comments="Source Table: smf_1personal_messages")
    int updateByPrimaryKey(SMFPersonalMessageDb row);
}