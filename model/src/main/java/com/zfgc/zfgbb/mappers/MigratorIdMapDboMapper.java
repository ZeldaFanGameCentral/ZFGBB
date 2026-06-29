package com.zfgc.zfgbb.mappers;

import com.zfgc.zfgbb.dbo.MigratorIdMapDbo;
import com.zfgc.zfgbb.dbo.MigratorIdMapDboExample;
import jakarta.annotation.Generated;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MigratorIdMapDboMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826408122-04:00", comments="Source Table: zfgbb.migrator_id_map")
    long countByExample(MigratorIdMapDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826424682-04:00", comments="Source Table: zfgbb.migrator_id_map")
    int deleteByExample(MigratorIdMapDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826435401-04:00", comments="Source Table: zfgbb.migrator_id_map")
    int deleteByPrimaryKey(Long migratorIdMapId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826445741-04:00", comments="Source Table: zfgbb.migrator_id_map")
    int insert(MigratorIdMapDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826453741-04:00", comments="Source Table: zfgbb.migrator_id_map")
    int insertSelective(MigratorIdMapDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.82646695-04:00", comments="Source Table: zfgbb.migrator_id_map")
    List<MigratorIdMapDbo> selectByExample(MigratorIdMapDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.82648012-04:00", comments="Source Table: zfgbb.migrator_id_map")
    MigratorIdMapDbo selectByPrimaryKey(Long migratorIdMapId);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826491869-04:00", comments="Source Table: zfgbb.migrator_id_map")
    int updateByExampleSelective(@Param("row") MigratorIdMapDbo row, @Param("example") MigratorIdMapDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826505769-04:00", comments="Source Table: zfgbb.migrator_id_map")
    int updateByExample(@Param("row") MigratorIdMapDbo row, @Param("example") MigratorIdMapDboExample example);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826553977-04:00", comments="Source Table: zfgbb.migrator_id_map")
    int updateByPrimaryKeySelective(MigratorIdMapDbo row);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826572947-04:00", comments="Source Table: zfgbb.migrator_id_map")
    int updateByPrimaryKey(MigratorIdMapDbo row);

    List<MigratorIdMapDbo> selectByExampleWithLimits(MigratorIdMapDboExample example);
}