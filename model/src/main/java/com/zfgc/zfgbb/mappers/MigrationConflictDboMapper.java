package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.MigrationConflictDbo;
import com.zfgc.zfgbb.dbo.MigrationConflictDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MigrationConflictDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732187834-04:00", comments="Source Table: zfgbb.migration_conflict")
    long countByExample(MigrationConflictDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732204543-04:00", comments="Source Table: zfgbb.migration_conflict")
    int deleteByExample(MigrationConflictDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732218333-04:00", comments="Source Table: zfgbb.migration_conflict")
    int deleteByPrimaryKey(Integer migrationConflictId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732234662-04:00", comments="Source Table: zfgbb.migration_conflict")
    int insert(MigrationConflictDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732245782-04:00", comments="Source Table: zfgbb.migration_conflict")
    int insertSelective(MigrationConflictDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732261822-04:00", comments="Source Table: zfgbb.migration_conflict")
    List<MigrationConflictDbo> selectByExample(MigrationConflictDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732277011-04:00", comments="Source Table: zfgbb.migration_conflict")
    MigrationConflictDbo selectByPrimaryKey(Integer migrationConflictId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73230699-04:00", comments="Source Table: zfgbb.migration_conflict")
    int updateByExampleSelective(@Param("row") MigrationConflictDbo row, @Param("example") MigrationConflictDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732335519-04:00", comments="Source Table: zfgbb.migration_conflict")
    int updateByExample(@Param("row") MigrationConflictDbo row, @Param("example") MigrationConflictDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732363518-04:00", comments="Source Table: zfgbb.migration_conflict")
    int updateByPrimaryKeySelective(MigrationConflictDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732388958-04:00", comments="Source Table: zfgbb.migration_conflict")
    int updateByPrimaryKey(MigrationConflictDbo row);

    List<MigrationConflictDbo> selectByExampleWithLimits(MigrationConflictDboExample example);
}