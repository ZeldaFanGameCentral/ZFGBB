package com.zfgc.zfgbb.migrator.ci.mappers;

import com.zfgc.zfgbb.migrator.ci.dbo.CiProjectScreenshotDb;
import com.zfgc.zfgbb.migrator.ci.dbo.CiProjectScreenshotDbExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CiProjectScreenshotDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721352787-04:00", comments="Source Table: ci_project_screenshots")
    long countByExample(CiProjectScreenshotDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721373306-04:00", comments="Source Table: ci_project_screenshots")
    int deleteByExample(CiProjectScreenshotDbExample example);

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

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721468213-04:00", comments="Source Table: ci_project_screenshots")
    int updateByExampleSelective(@Param("row") CiProjectScreenshotDb row, @Param("example") CiProjectScreenshotDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721493873-04:00", comments="Source Table: ci_project_screenshots")
    int updateByExample(@Param("row") CiProjectScreenshotDb row, @Param("example") CiProjectScreenshotDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721519682-04:00", comments="Source Table: ci_project_screenshots")
    int updateByPrimaryKeySelective(CiProjectScreenshotDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.721542811-04:00", comments="Source Table: ci_project_screenshots")
    int updateByPrimaryKey(CiProjectScreenshotDb row);
}