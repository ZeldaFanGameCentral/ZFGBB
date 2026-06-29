package com.zfgc.zfgbb.migrator.ci.mappers;

import com.zfgc.zfgbb.migrator.ci.dbo.CiProjectDownloadDb;
import com.zfgc.zfgbb.migrator.ci.dbo.CiProjectDownloadDbExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CiProjectDownloadDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.722821491-04:00", comments="Source Table: ci_project_downloads")
    long countByExample(CiProjectDownloadDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.72284004-04:00", comments="Source Table: ci_project_downloads")
    int deleteByExample(CiProjectDownloadDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.72285397-04:00", comments="Source Table: ci_project_downloads")
    int deleteByPrimaryKey(Integer id);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.722868449-04:00", comments="Source Table: ci_project_downloads")
    int insert(CiProjectDownloadDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.722881909-04:00", comments="Source Table: ci_project_downloads")
    int insertSelective(CiProjectDownloadDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.722900978-04:00", comments="Source Table: ci_project_downloads")
    List<CiProjectDownloadDb> selectByExample(CiProjectDownloadDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.722918918-04:00", comments="Source Table: ci_project_downloads")
    CiProjectDownloadDb selectByPrimaryKey(Integer id);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.722935227-04:00", comments="Source Table: ci_project_downloads")
    int updateByExampleSelective(@Param("row") CiProjectDownloadDb row, @Param("example") CiProjectDownloadDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.722953907-04:00", comments="Source Table: ci_project_downloads")
    int updateByExample(@Param("row") CiProjectDownloadDb row, @Param("example") CiProjectDownloadDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.722979136-04:00", comments="Source Table: ci_project_downloads")
    int updateByPrimaryKeySelective(CiProjectDownloadDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.723008395-04:00", comments="Source Table: ci_project_downloads")
    int updateByPrimaryKey(CiProjectDownloadDb row);
}