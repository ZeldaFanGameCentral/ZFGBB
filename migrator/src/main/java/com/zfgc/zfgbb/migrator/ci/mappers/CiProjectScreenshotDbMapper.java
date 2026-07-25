package com.zfgc.zfgbb.migrator.ci.mappers;

import com.zfgc.zfgbb.migrator.ci.dbo.CiProjectScreenshotDb;
import com.zfgc.zfgbb.migrator.ci.dbo.CiProjectScreenshotDbExample;
import jakarta.annotation.Generated;
import java.util.List;

public interface CiProjectScreenshotDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721388486-04:00", comments="Source Table: ci_project_screenshots")
    int deleteByPrimaryKey(Integer id);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721401776-04:00", comments="Source Table: ci_project_screenshots")
    int insert(CiProjectScreenshotDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721415355-04:00", comments="Source Table: ci_project_screenshots")
    int insertSelective(CiProjectScreenshotDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721433635-04:00", comments="Source Table: ci_project_screenshots")
    List<CiProjectScreenshotDb> selectByExample(CiProjectScreenshotDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721451634-04:00", comments="Source Table: ci_project_screenshots")
    CiProjectScreenshotDb selectByPrimaryKey(Integer id);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721519682-04:00", comments="Source Table: ci_project_screenshots")
    int updateByPrimaryKeySelective(CiProjectScreenshotDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721542811-04:00", comments="Source Table: ci_project_screenshots")
    int updateByPrimaryKey(CiProjectScreenshotDb row);
}