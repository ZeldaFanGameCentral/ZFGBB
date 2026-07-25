package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogNotifyDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogNotifyDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFLogNotifyDbKey;
import jakarta.annotation.Generated;
import java.util.List;

public interface SMFLogNotifyDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039055525-04:00", comments="Source Table: smf_1log_notify")
    int deleteByPrimaryKey(SMFLogNotifyDbKey key);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039077235-04:00", comments="Source Table: smf_1log_notify")
    int insert(SMFLogNotifyDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039100134-04:00", comments="Source Table: smf_1log_notify")
    int insertSelective(SMFLogNotifyDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039118253-04:00", comments="Source Table: smf_1log_notify")
    List<SMFLogNotifyDb> selectByExample(SMFLogNotifyDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039138123-04:00", comments="Source Table: smf_1log_notify")
    SMFLogNotifyDb selectByPrimaryKey(SMFLogNotifyDbKey key);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.03923168-04:00", comments="Source Table: smf_1log_notify")
    int updateByPrimaryKeySelective(SMFLogNotifyDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039268498-04:00", comments="Source Table: smf_1log_notify")
    int updateByPrimaryKey(SMFLogNotifyDb row);
}