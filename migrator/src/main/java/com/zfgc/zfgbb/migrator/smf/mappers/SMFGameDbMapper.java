package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFGameDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFGameDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFGameDbWithBLOBs;
import jakarta.annotation.Generated;
import java.util.List;

public interface SMFGameDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.714369738-04:00", comments="Source Table: smf_1games")
    int deleteByPrimaryKey(Integer idGame);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.714386247-04:00", comments="Source Table: smf_1games")
    int insert(SMFGameDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.714399987-04:00", comments="Source Table: smf_1games")
    int insertSelective(SMFGameDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.714417936-04:00", comments="Source Table: smf_1games")
    List<SMFGameDbWithBLOBs> selectByExampleWithBLOBs(SMFGameDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.714433116-04:00", comments="Source Table: smf_1games")
    List<SMFGameDb> selectByExample(SMFGameDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.714450845-04:00", comments="Source Table: smf_1games")
    SMFGameDbWithBLOBs selectByPrimaryKey(Integer idGame);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.714543612-04:00", comments="Source Table: smf_1games")
    int updateByPrimaryKeySelective(SMFGameDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.714568351-04:00", comments="Source Table: smf_1games")
    int updateByPrimaryKeyWithBLOBs(SMFGameDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.714587351-04:00", comments="Source Table: smf_1games")
    int updateByPrimaryKey(SMFGameDb row);
}