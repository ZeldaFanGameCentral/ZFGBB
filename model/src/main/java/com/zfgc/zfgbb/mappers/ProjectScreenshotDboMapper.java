package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ProjectScreenshotDbo;
import com.zfgc.zfgbb.dbo.ProjectScreenshotDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectScreenshotDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.721157167-04:00", comments="Source Table: zfgbb.project_screenshot")
    long countByExample(ProjectScreenshotDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.721171247-04:00", comments="Source Table: zfgbb.project_screenshot")
    int deleteByExample(ProjectScreenshotDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.721183866-04:00", comments="Source Table: zfgbb.project_screenshot")
    int deleteByPrimaryKey(Integer projectScreenshotId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.721193476-04:00", comments="Source Table: zfgbb.project_screenshot")
    int insert(ProjectScreenshotDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.721204246-04:00", comments="Source Table: zfgbb.project_screenshot")
    int insertSelective(ProjectScreenshotDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.721217405-04:00", comments="Source Table: zfgbb.project_screenshot")
    List<ProjectScreenshotDbo> selectByExample(ProjectScreenshotDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.721254634-04:00", comments="Source Table: zfgbb.project_screenshot")
    ProjectScreenshotDbo selectByPrimaryKey(Integer projectScreenshotId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.721269934-04:00", comments="Source Table: zfgbb.project_screenshot")
    int updateByExampleSelective(@Param("row") ProjectScreenshotDbo row, @Param("example") ProjectScreenshotDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.721303443-04:00", comments="Source Table: zfgbb.project_screenshot")
    int updateByExample(@Param("row") ProjectScreenshotDbo row, @Param("example") ProjectScreenshotDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.721330332-04:00", comments="Source Table: zfgbb.project_screenshot")
    int updateByPrimaryKeySelective(ProjectScreenshotDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.721347931-04:00", comments="Source Table: zfgbb.project_screenshot")
    int updateByPrimaryKey(ProjectScreenshotDbo row);

    List<ProjectScreenshotDbo> selectByExampleWithLimits(ProjectScreenshotDboExample example);
}