package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.ProjectDownloadDbo;
import com.zfgc.zfgbb.dbo.ProjectDownloadDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectDownloadDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.722418608-04:00", comments="Source Table: zfgbb.project_download")
    long countByExample(ProjectDownloadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.722442857-04:00", comments="Source Table: zfgbb.project_download")
    int deleteByExample(ProjectDownloadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.722453817-04:00", comments="Source Table: zfgbb.project_download")
    int deleteByPrimaryKey(Integer projectDownloadId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.722465526-04:00", comments="Source Table: zfgbb.project_download")
    int insert(ProjectDownloadDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.722475876-04:00", comments="Source Table: zfgbb.project_download")
    int insertSelective(ProjectDownloadDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.722488476-04:00", comments="Source Table: zfgbb.project_download")
    List<ProjectDownloadDbo> selectByExample(ProjectDownloadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.722501715-04:00", comments="Source Table: zfgbb.project_download")
    ProjectDownloadDbo selectByPrimaryKey(Integer projectDownloadId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.722516315-04:00", comments="Source Table: zfgbb.project_download")
    int updateByExampleSelective(@Param("row") ProjectDownloadDbo row, @Param("example") ProjectDownloadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.722531474-04:00", comments="Source Table: zfgbb.project_download")
    int updateByExample(@Param("row") ProjectDownloadDbo row, @Param("example") ProjectDownloadDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.722548364-04:00", comments="Source Table: zfgbb.project_download")
    int updateByPrimaryKeySelective(ProjectDownloadDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.722564923-04:00", comments="Source Table: zfgbb.project_download")
    int updateByPrimaryKey(ProjectDownloadDbo row);

    List<ProjectDownloadDbo> selectByExampleWithLimits(ProjectDownloadDboExample example);
}