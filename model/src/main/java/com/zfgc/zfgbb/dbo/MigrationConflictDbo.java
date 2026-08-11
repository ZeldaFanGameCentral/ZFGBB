package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class MigrationConflictDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.migration_conflict_id")
    private Integer migrationConflictId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.entity_type")
    private String entityType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.entity_id")
    private Integer entityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.field_name")
    private String fieldName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.candidates")
    private String candidates;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.status")
    private String status;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.resolved_source_type")
    private String resolvedSourceType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.resolved_value")
    private String resolvedValue;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.resolved_by_user_id")
    private Integer resolvedByUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.resolved_ts")
    private OffsetDateTime resolvedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.migration_conflict_id")
    public Integer getMigrationConflictId() {
        return migrationConflictId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.migration_conflict_id")
    public void setMigrationConflictId(Integer migrationConflictId) {
        this.migrationConflictId = migrationConflictId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.entity_type")
    public String getEntityType() {
        return entityType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.entity_type")
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.entity_id")
    public Integer getEntityId() {
        return entityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.entity_id")
    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.field_name")
    public String getFieldName() {
        return fieldName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.field_name")
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.candidates")
    public String getCandidates() {
        return candidates;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.candidates")
    public void setCandidates(String candidates) {
        this.candidates = candidates;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.status")
    public String getStatus() {
        return status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.status")
    public void setStatus(String status) {
        this.status = status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.resolved_source_type")
    public String getResolvedSourceType() {
        return resolvedSourceType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.resolved_source_type")
    public void setResolvedSourceType(String resolvedSourceType) {
        this.resolvedSourceType = resolvedSourceType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.resolved_value")
    public String getResolvedValue() {
        return resolvedValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.resolved_value")
    public void setResolvedValue(String resolvedValue) {
        this.resolvedValue = resolvedValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.resolved_by_user_id")
    public Integer getResolvedByUserId() {
        return resolvedByUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.resolved_by_user_id")
    public void setResolvedByUserId(Integer resolvedByUserId) {
        this.resolvedByUserId = resolvedByUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.resolved_ts")
    public OffsetDateTime getResolvedTs() {
        return resolvedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.resolved_ts")
    public void setResolvedTs(OffsetDateTime resolvedTs) {
        this.resolvedTs = resolvedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.migration_conflict.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.migration_conflict")
    public Integer getPkId() {
        return migrationConflictId;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.migration_conflict")
    public OffsetDateTime getCreatedTime() {
        return createdTs;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.migration_conflict")
    public OffsetDateTime getUpdatedTime() {
        return updatedTs;
    }
}