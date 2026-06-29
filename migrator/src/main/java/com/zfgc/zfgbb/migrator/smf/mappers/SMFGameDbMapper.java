package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFGameDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFGameDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFGameDbWithBLOBs;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMFGameDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.714224532-04:00", comments="Source Table: smf_1games")
    long countByExample(SMFGameDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.714338939-04:00", comments="Source Table: smf_1games")
    int deleteByExample(SMFGameDbExample example);

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

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.714466655-04:00", comments="Source Table: smf_1games")
    int updateByExampleSelective(@Param("row") SMFGameDbWithBLOBs row, @Param("example") SMFGameDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.714491824-04:00", comments="Source Table: smf_1games")
    int updateByExampleWithBLOBs(@Param("row") SMFGameDbWithBLOBs row, @Param("example") SMFGameDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.714515063-04:00", comments="Source Table: smf_1games")
    int updateByExample(@Param("row") SMFGameDb row, @Param("example") SMFGameDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.714543612-04:00", comments="Source Table: smf_1games")
    int updateByPrimaryKeySelective(SMFGameDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.714568351-04:00", comments="Source Table: smf_1games")
    int updateByPrimaryKeyWithBLOBs(SMFGameDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.714587351-04:00", comments="Source Table: smf_1games")
    int updateByPrimaryKey(SMFGameDb row);
}