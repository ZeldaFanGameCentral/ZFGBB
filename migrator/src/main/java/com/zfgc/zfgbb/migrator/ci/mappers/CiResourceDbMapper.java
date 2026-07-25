package com.zfgc.zfgbb.migrator.ci.mappers;

import com.zfgc.zfgbb.migrator.ci.dbo.CiResourceDb;
import com.zfgc.zfgbb.migrator.ci.dbo.CiResourceDbExample;
import jakarta.annotation.Generated;
import java.util.List;

public interface CiResourceDbMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.725028921-04:00", comments="Source Table: ci_resources_backup")
    int deleteByPrimaryKey(Integer id);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.72506057-04:00", comments="Source Table: ci_resources_backup")
    int insert(CiResourceDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.725082519-04:00", comments="Source Table: ci_resources_backup")
    int insertSelective(CiResourceDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.725100429-04:00", comments="Source Table: ci_resources_backup")
    List<CiResourceDb> selectByExampleWithBLOBs(CiResourceDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.725118208-04:00", comments="Source Table: ci_resources_backup")
    List<CiResourceDb> selectByExample(CiResourceDbExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.725134978-04:00", comments="Source Table: ci_resources_backup")
    CiResourceDb selectByPrimaryKey(Integer id);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.725239804-04:00", comments="Source Table: ci_resources_backup")
    int updateByPrimaryKeySelective(CiResourceDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.725261454-04:00", comments="Source Table: ci_resources_backup")
    int updateByPrimaryKeyWithBLOBs(CiResourceDb row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.725278473-04:00", comments="Source Table: ci_resources_backup")
    int updateByPrimaryKey(CiResourceDb row);
}