package com.zfgc.zfgbb.migrator.smf.mappers;

import com.zfgc.zfgbb.migrator.smf.dbo.SMFBoardDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFBoardDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFBoardDbWithBLOBs;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SMFBoardDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.686075783-04:00", comments="Source Table: smf_1boards")
    long countByExample(SMFBoardDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.686093102-04:00", comments="Source Table: smf_1boards")
    int deleteByExample(SMFBoardDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.686111322-04:00", comments="Source Table: smf_1boards")
    int deleteByPrimaryKey(Integer idBoard);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.686124651-04:00", comments="Source Table: smf_1boards")
    int insert(SMFBoardDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.686137341-04:00", comments="Source Table: smf_1boards")
    int insertSelective(SMFBoardDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.68615149-04:00", comments="Source Table: smf_1boards")
    List<SMFBoardDbWithBLOBs> selectByExampleWithBLOBs(SMFBoardDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.68616715-04:00", comments="Source Table: smf_1boards")
    List<SMFBoardDb> selectByExample(SMFBoardDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.68618249-04:00", comments="Source Table: smf_1boards")
    SMFBoardDbWithBLOBs selectByPrimaryKey(Integer idBoard);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.686198529-04:00", comments="Source Table: smf_1boards")
    int updateByExampleSelective(@Param("row") SMFBoardDbWithBLOBs row, @Param("example") SMFBoardDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.686220608-04:00", comments="Source Table: smf_1boards")
    int updateByExampleWithBLOBs(@Param("row") SMFBoardDbWithBLOBs row, @Param("example") SMFBoardDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.686237348-04:00", comments="Source Table: smf_1boards")
    int updateByExample(@Param("row") SMFBoardDb row, @Param("example") SMFBoardDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.686263477-04:00", comments="Source Table: smf_1boards")
    int updateByPrimaryKeySelective(SMFBoardDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.686289506-04:00", comments="Source Table: smf_1boards")
    int updateByPrimaryKeyWithBLOBs(SMFBoardDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.686309216-04:00", comments="Source Table: smf_1boards")
    int updateByPrimaryKey(SMFBoardDb row);
}