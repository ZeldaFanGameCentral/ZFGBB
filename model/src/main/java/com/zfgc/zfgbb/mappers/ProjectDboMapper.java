package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ProjectDbo;
import com.zfgc.zfgbb.dbo.ProjectDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715846783-04:00", comments="Source Table: zfgbb.project")
    long countByExample(ProjectDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715860262-04:00", comments="Source Table: zfgbb.project")
    int deleteByExample(ProjectDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715874412-04:00", comments="Source Table: zfgbb.project")
    int deleteByPrimaryKey(Integer contentEntityId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715884521-04:00", comments="Source Table: zfgbb.project")
    int insert(ProjectDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715895621-04:00", comments="Source Table: zfgbb.project")
    int insertSelective(ProjectDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715909091-04:00", comments="Source Table: zfgbb.project")
    List<ProjectDbo> selectByExample(ProjectDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71592354-04:00", comments="Source Table: zfgbb.project")
    ProjectDbo selectByPrimaryKey(Integer contentEntityId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71593909-04:00", comments="Source Table: zfgbb.project")
    int updateByExampleSelective(@Param("row") ProjectDbo row, @Param("example") ProjectDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715967509-04:00", comments="Source Table: zfgbb.project")
    int updateByExample(@Param("row") ProjectDbo row, @Param("example") ProjectDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.715986038-04:00", comments="Source Table: zfgbb.project")
    int updateByPrimaryKeySelective(ProjectDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.716005508-04:00", comments="Source Table: zfgbb.project")
    int updateByPrimaryKey(ProjectDbo row);

    List<ProjectDbo> selectByExampleWithLimits(ProjectDboExample example);
}