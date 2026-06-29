package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ProjectViewDbo;
import com.zfgc.zfgbb.dbo.ProjectViewDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectViewDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718359124-04:00", comments="Source Table: zfgbb.project_view")
    long countByExample(ProjectViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718390683-04:00", comments="Source Table: zfgbb.project_view")
    int deleteByExample(ProjectViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718405683-04:00", comments="Source Table: zfgbb.project_view")
    int insert(ProjectViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718423052-04:00", comments="Source Table: zfgbb.project_view")
    int insertSelective(ProjectViewDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718438832-04:00", comments="Source Table: zfgbb.project_view")
    List<ProjectViewDbo> selectByExample(ProjectViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718465861-04:00", comments="Source Table: zfgbb.project_view")
    int updateByExampleSelective(@Param("row") ProjectViewDbo row, @Param("example") ProjectViewDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71848361-04:00", comments="Source Table: zfgbb.project_view")
    int updateByExample(@Param("row") ProjectViewDbo row, @Param("example") ProjectViewDboExample example);

    List<ProjectViewDbo> selectByExampleWithLimits(ProjectViewDboExample example);
}