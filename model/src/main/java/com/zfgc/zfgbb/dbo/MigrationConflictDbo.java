package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class MigrationConflictDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731909052-04:00", comments="Source field: zfgbb.migration_conflict.migration_conflict_id")
    private Integer migrationConflictId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731929102-04:00", comments="Source field: zfgbb.migration_conflict.entity_type")
    private String entityType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731944651-04:00", comments="Source field: zfgbb.migration_conflict.entity_id")
    private Integer entityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731961261-04:00", comments="Source field: zfgbb.migration_conflict.field_name")
    private String fieldName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73198656-04:00", comments="Source field: zfgbb.migration_conflict.candidates")
    private String candidates;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732047378-04:00", comments="Source field: zfgbb.migration_conflict.status")
    private String status;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732065748-04:00", comments="Source field: zfgbb.migration_conflict.resolved_source_type")
    private String resolvedSourceType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732080487-04:00", comments="Source field: zfgbb.migration_conflict.resolved_value")
    private String resolvedValue;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732094487-04:00", comments="Source field: zfgbb.migration_conflict.resolved_by_user_id")
    private Integer resolvedByUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732108616-04:00", comments="Source field: zfgbb.migration_conflict.resolved_ts")
    private OffsetDateTime resolvedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732123206-04:00", comments="Source field: zfgbb.migration_conflict.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732138955-04:00", comments="Source field: zfgbb.migration_conflict.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731917252-04:00", comments="Source field: zfgbb.migration_conflict.migration_conflict_id")
    public Integer getMigrationConflictId() {
        return migrationConflictId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731923892-04:00", comments="Source field: zfgbb.migration_conflict.migration_conflict_id")
    public void setMigrationConflictId(Integer migrationConflictId) {
        this.migrationConflictId = migrationConflictId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731934012-04:00", comments="Source field: zfgbb.migration_conflict.entity_type")
    public String getEntityType() {
        return entityType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731939942-04:00", comments="Source field: zfgbb.migration_conflict.entity_type")
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731949601-04:00", comments="Source field: zfgbb.migration_conflict.entity_id")
    public Integer getEntityId() {
        return entityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731954371-04:00", comments="Source field: zfgbb.migration_conflict.entity_id")
    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731965951-04:00", comments="Source field: zfgbb.migration_conflict.field_name")
    public String getFieldName() {
        return fieldName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73198111-04:00", comments="Source field: zfgbb.migration_conflict.field_name")
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73199174-04:00", comments="Source field: zfgbb.migration_conflict.candidates")
    public String getCandidates() {
        return candidates;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73199683-04:00", comments="Source field: zfgbb.migration_conflict.candidates")
    public void setCandidates(String candidates) {
        this.candidates = candidates;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732054418-04:00", comments="Source field: zfgbb.migration_conflict.status")
    public String getStatus() {
        return status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732060598-04:00", comments="Source field: zfgbb.migration_conflict.status")
    public void setStatus(String status) {
        this.status = status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732070867-04:00", comments="Source field: zfgbb.migration_conflict.resolved_source_type")
    public String getResolvedSourceType() {
        return resolvedSourceType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732075997-04:00", comments="Source field: zfgbb.migration_conflict.resolved_source_type")
    public void setResolvedSourceType(String resolvedSourceType) {
        this.resolvedSourceType = resolvedSourceType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732085127-04:00", comments="Source field: zfgbb.migration_conflict.resolved_value")
    public String getResolvedValue() {
        return resolvedValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732090077-04:00", comments="Source field: zfgbb.migration_conflict.resolved_value")
    public void setResolvedValue(String resolvedValue) {
        this.resolvedValue = resolvedValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732099267-04:00", comments="Source field: zfgbb.migration_conflict.resolved_by_user_id")
    public Integer getResolvedByUserId() {
        return resolvedByUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732103926-04:00", comments="Source field: zfgbb.migration_conflict.resolved_by_user_id")
    public void setResolvedByUserId(Integer resolvedByUserId) {
        this.resolvedByUserId = resolvedByUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732113746-04:00", comments="Source field: zfgbb.migration_conflict.resolved_ts")
    public OffsetDateTime getResolvedTs() {
        return resolvedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732118726-04:00", comments="Source field: zfgbb.migration_conflict.resolved_ts")
    public void setResolvedTs(OffsetDateTime resolvedTs) {
        this.resolvedTs = resolvedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732128366-04:00", comments="Source field: zfgbb.migration_conflict.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732133875-04:00", comments="Source field: zfgbb.migration_conflict.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732144445-04:00", comments="Source field: zfgbb.migration_conflict.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.732149295-04:00", comments="Source field: zfgbb.migration_conflict.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return migrationConflictId;
    }

    @Override
    public OffsetDateTime getCreatedTime() {
        return createdTs;
    }

    @Override
    public OffsetDateTime getUpdatedTime() {
        return updatedTs;
    }
}