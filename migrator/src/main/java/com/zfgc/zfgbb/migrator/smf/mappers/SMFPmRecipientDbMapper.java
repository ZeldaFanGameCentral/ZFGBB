package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFPmRecipientDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFPmRecipientDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFPmRecipientDbKey;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMFPmRecipientDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036464191-04:00", comments="Source Table: smf_1pm_recipients")
    long countByExample(SMFPmRecipientDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.03648061-04:00", comments="Source Table: smf_1pm_recipients")
    int deleteByExample(SMFPmRecipientDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.03649333-04:00", comments="Source Table: smf_1pm_recipients")
    int deleteByPrimaryKey(SMFPmRecipientDbKey key);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036507159-04:00", comments="Source Table: smf_1pm_recipients")
    int insert(SMFPmRecipientDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036520209-04:00", comments="Source Table: smf_1pm_recipients")
    int insertSelective(SMFPmRecipientDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036535648-04:00", comments="Source Table: smf_1pm_recipients")
    List<SMFPmRecipientDb> selectByExample(SMFPmRecipientDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036552148-04:00", comments="Source Table: smf_1pm_recipients")
    SMFPmRecipientDb selectByPrimaryKey(SMFPmRecipientDbKey key);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036566897-04:00", comments="Source Table: smf_1pm_recipients")
    int updateByExampleSelective(@Param("row") SMFPmRecipientDb row, @Param("example") SMFPmRecipientDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036586747-04:00", comments="Source Table: smf_1pm_recipients")
    int updateByExample(@Param("row") SMFPmRecipientDb row, @Param("example") SMFPmRecipientDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036607906-04:00", comments="Source Table: smf_1pm_recipients")
    int updateByPrimaryKeySelective(SMFPmRecipientDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036632355-04:00", comments="Source Table: smf_1pm_recipients")
    int updateByPrimaryKey(SMFPmRecipientDb row);
}