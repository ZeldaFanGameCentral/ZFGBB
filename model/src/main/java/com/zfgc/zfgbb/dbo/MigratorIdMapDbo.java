package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class MigratorIdMapDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826285976-04:00", comments="Source field: zfgbb.migrator_id_map.migrator_id_map_id")
    private Long migratorIdMapId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826307896-04:00", comments="Source field: zfgbb.migrator_id_map.entity_type")
    private String entityType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826332605-04:00", comments="Source field: zfgbb.migrator_id_map.legacy_id")
    private Integer legacyId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826346874-04:00", comments="Source field: zfgbb.migrator_id_map.zfgbb_id")
    private Integer zfgbbId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826359974-04:00", comments="Source field: zfgbb.migrator_id_map.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826295946-04:00", comments="Source field: zfgbb.migrator_id_map.migrator_id_map_id")
    public Long getMigratorIdMapId() {
        return migratorIdMapId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826302196-04:00", comments="Source field: zfgbb.migrator_id_map.migrator_id_map_id")
    public void setMigratorIdMapId(Long migratorIdMapId) {
        this.migratorIdMapId = migratorIdMapId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826312916-04:00", comments="Source field: zfgbb.migrator_id_map.entity_type")
    public String getEntityType() {
        return entityType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826326725-04:00", comments="Source field: zfgbb.migrator_id_map.entity_type")
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826337605-04:00", comments="Source field: zfgbb.migrator_id_map.legacy_id")
    public Integer getLegacyId() {
        return legacyId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826342605-04:00", comments="Source field: zfgbb.migrator_id_map.legacy_id")
    public void setLegacyId(Integer legacyId) {
        this.legacyId = legacyId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826351334-04:00", comments="Source field: zfgbb.migrator_id_map.zfgbb_id")
    public Integer getZfgbbId() {
        return zfgbbId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826355794-04:00", comments="Source field: zfgbb.migrator_id_map.zfgbb_id")
    public void setZfgbbId(Integer zfgbbId) {
        this.zfgbbId = zfgbbId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826365014-04:00", comments="Source field: zfgbb.migrator_id_map.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.826371714-04:00", comments="Source field: zfgbb.migrator_id_map.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Override
    public Integer getPkId() {
        return null;
    }

    @Override
    public OffsetDateTime getCreatedTime() {
        return createdTs;
    }

    @Override
    public OffsetDateTime getUpdatedTime() {
        return null;
    }
}