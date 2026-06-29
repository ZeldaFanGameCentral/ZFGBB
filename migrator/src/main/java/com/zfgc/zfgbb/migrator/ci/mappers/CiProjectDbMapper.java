package com.zfgc.zfgbb.migrator.ci.mappers;

import com.zfgc.zfgbb.migrator.ci.dbo.CiProjectDb;
import com.zfgc.zfgbb.migrator.ci.dbo.CiProjectDbExample;
import com.zfgc.zfgbb.migrator.ci.dbo.CiProjectDbWithBLOBs;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CiProjectDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.71967185-04:00", comments="Source Table: ci_projects")
    long countByExample(CiProjectDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.71969032-04:00", comments="Source Table: ci_projects")
    int deleteByExample(CiProjectDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.719704419-04:00", comments="Source Table: ci_projects")
    int deleteByPrimaryKey(Integer id);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.719718879-04:00", comments="Source Table: ci_projects")
    int insert(CiProjectDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.719731968-04:00", comments="Source Table: ci_projects")
    int insertSelective(CiProjectDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.719746958-04:00", comments="Source Table: ci_projects")
    List<CiProjectDbWithBLOBs> selectByExampleWithBLOBs(CiProjectDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.719763167-04:00", comments="Source Table: ci_projects")
    List<CiProjectDb> selectByExample(CiProjectDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.719781387-04:00", comments="Source Table: ci_projects")
    CiProjectDbWithBLOBs selectByPrimaryKey(Integer id);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.719798806-04:00", comments="Source Table: ci_projects")
    int updateByExampleSelective(@Param("row") CiProjectDbWithBLOBs row, @Param("example") CiProjectDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.719814006-04:00", comments="Source Table: ci_projects")
    int updateByExampleWithBLOBs(@Param("row") CiProjectDbWithBLOBs row, @Param("example") CiProjectDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.719832845-04:00", comments="Source Table: ci_projects")
    int updateByExample(@Param("row") CiProjectDb row, @Param("example") CiProjectDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.719875884-04:00", comments="Source Table: ci_projects")
    int updateByPrimaryKeySelective(CiProjectDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.719900373-04:00", comments="Source Table: ci_projects")
    int updateByPrimaryKeyWithBLOBs(CiProjectDbWithBLOBs row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.719919012-04:00", comments="Source Table: ci_projects")
    int updateByPrimaryKey(CiProjectDb row);
}