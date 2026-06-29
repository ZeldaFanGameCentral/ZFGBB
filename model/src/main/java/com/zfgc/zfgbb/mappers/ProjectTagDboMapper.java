package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ProjectTagDbo;
import com.zfgc.zfgbb.dbo.ProjectTagDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectTagDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.72910689-04:00", comments="Source Table: zfgbb.project_tag")
    long countByExample(ProjectTagDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729120199-04:00", comments="Source Table: zfgbb.project_tag")
    int deleteByExample(ProjectTagDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729132669-04:00", comments="Source Table: zfgbb.project_tag")
    int deleteByPrimaryKey(Integer projectTagId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729142729-04:00", comments="Source Table: zfgbb.project_tag")
    int insert(ProjectTagDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729152098-04:00", comments="Source Table: zfgbb.project_tag")
    int insertSelective(ProjectTagDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729164908-04:00", comments="Source Table: zfgbb.project_tag")
    List<ProjectTagDbo> selectByExample(ProjectTagDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729179457-04:00", comments="Source Table: zfgbb.project_tag")
    ProjectTagDbo selectByPrimaryKey(Integer projectTagId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729191837-04:00", comments="Source Table: zfgbb.project_tag")
    int updateByExampleSelective(@Param("row") ProjectTagDbo row, @Param("example") ProjectTagDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729208907-04:00", comments="Source Table: zfgbb.project_tag")
    int updateByExample(@Param("row") ProjectTagDbo row, @Param("example") ProjectTagDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729227106-04:00", comments="Source Table: zfgbb.project_tag")
    int updateByPrimaryKeySelective(ProjectTagDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729244825-04:00", comments="Source Table: zfgbb.project_tag")
    int updateByPrimaryKey(ProjectTagDbo row);

    List<ProjectTagDbo> selectByExampleWithLimits(ProjectTagDboExample example);
}