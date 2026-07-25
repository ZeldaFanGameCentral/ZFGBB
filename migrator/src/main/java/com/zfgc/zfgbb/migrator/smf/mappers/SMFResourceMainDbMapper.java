package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFResourceMainDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFResourceMainDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFResourceMainDbWithBLOBs;
import jakarta.annotation.Generated;
import java.util.List;

public interface SMFResourceMainDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716262678-04:00", comments="Source Table: smf_1resources_main")
    int deleteByPrimaryKey(Integer idResource);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716277267-04:00", comments="Source Table: smf_1resources_main")
    int insert(SMFResourceMainDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716290747-04:00", comments="Source Table: smf_1resources_main")
    int insertSelective(SMFResourceMainDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716307557-04:00", comments="Source Table: smf_1resources_main")
    List<SMFResourceMainDbWithBLOBs> selectByExampleWithBLOBs(SMFResourceMainDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716324506-04:00", comments="Source Table: smf_1resources_main")
    List<SMFResourceMainDb> selectByExample(SMFResourceMainDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716339915-04:00", comments="Source Table: smf_1resources_main")
    SMFResourceMainDbWithBLOBs selectByPrimaryKey(Integer idResource);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716429353-04:00", comments="Source Table: smf_1resources_main")
    int updateByPrimaryKeySelective(SMFResourceMainDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716450692-04:00", comments="Source Table: smf_1resources_main")
    int updateByPrimaryKeyWithBLOBs(SMFResourceMainDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716468191-04:00", comments="Source Table: smf_1resources_main")
    int updateByPrimaryKey(SMFResourceMainDb row);
}