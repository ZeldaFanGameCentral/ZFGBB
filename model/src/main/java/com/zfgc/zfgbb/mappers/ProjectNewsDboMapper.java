package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ProjectNewsDbo;
import com.zfgc.zfgbb.dbo.ProjectNewsDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectNewsDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730047291-04:00", comments="Source Table: zfgbb.project_news")
    long countByExample(ProjectNewsDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73006144-04:00", comments="Source Table: zfgbb.project_news")
    int deleteByExample(ProjectNewsDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.7300717-04:00", comments="Source Table: zfgbb.project_news")
    int deleteByPrimaryKey(Integer projectNewsId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730084349-04:00", comments="Source Table: zfgbb.project_news")
    int insert(ProjectNewsDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730092869-04:00", comments="Source Table: zfgbb.project_news")
    int insertSelective(ProjectNewsDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730106719-04:00", comments="Source Table: zfgbb.project_news")
    List<ProjectNewsDbo> selectByExample(ProjectNewsDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730119548-04:00", comments="Source Table: zfgbb.project_news")
    ProjectNewsDbo selectByPrimaryKey(Integer projectNewsId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730131908-04:00", comments="Source Table: zfgbb.project_news")
    int updateByExampleSelective(@Param("row") ProjectNewsDbo row, @Param("example") ProjectNewsDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730147057-04:00", comments="Source Table: zfgbb.project_news")
    int updateByExample(@Param("row") ProjectNewsDbo row, @Param("example") ProjectNewsDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730164867-04:00", comments="Source Table: zfgbb.project_news")
    int updateByPrimaryKeySelective(ProjectNewsDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730180366-04:00", comments="Source Table: zfgbb.project_news")
    int updateByPrimaryKey(ProjectNewsDbo row);

    List<ProjectNewsDbo> selectByExampleWithLimits(ProjectNewsDboExample example);
}