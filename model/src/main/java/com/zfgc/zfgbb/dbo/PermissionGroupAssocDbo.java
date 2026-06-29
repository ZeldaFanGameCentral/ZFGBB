package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class PermissionGroupAssocDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742031738-04:00", comments="Source field: zfgbb.permission_group_assoc.permission_group_assoc_id")
    private Integer permissionGroupAssocId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742054427-04:00", comments="Source field: zfgbb.permission_group_assoc.permission_group_id")
    private Integer permissionGroupId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742069966-04:00", comments="Source field: zfgbb.permission_group_assoc.permission_id")
    private Integer permissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742086306-04:00", comments="Source field: zfgbb.permission_group_assoc.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742102365-04:00", comments="Source field: zfgbb.permission_group_assoc.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742118705-04:00", comments="Source field: zfgbb.permission_group_assoc.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742041907-04:00", comments="Source field: zfgbb.permission_group_assoc.permission_group_assoc_id")
    public Integer getPermissionGroupAssocId() {
        return permissionGroupAssocId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742048737-04:00", comments="Source field: zfgbb.permission_group_assoc.permission_group_assoc_id")
    public void setPermissionGroupAssocId(Integer permissionGroupAssocId) {
        this.permissionGroupAssocId = permissionGroupAssocId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742059887-04:00", comments="Source field: zfgbb.permission_group_assoc.permission_group_id")
    public Integer getPermissionGroupId() {
        return permissionGroupId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742065077-04:00", comments="Source field: zfgbb.permission_group_assoc.permission_group_id")
    public void setPermissionGroupId(Integer permissionGroupId) {
        this.permissionGroupId = permissionGroupId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742075306-04:00", comments="Source field: zfgbb.permission_group_assoc.permission_id")
    public Integer getPermissionId() {
        return permissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742080386-04:00", comments="Source field: zfgbb.permission_group_assoc.permission_id")
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742092026-04:00", comments="Source field: zfgbb.permission_group_assoc.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742097636-04:00", comments="Source field: zfgbb.permission_group_assoc.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742109075-04:00", comments="Source field: zfgbb.permission_group_assoc.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742113925-04:00", comments="Source field: zfgbb.permission_group_assoc.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742123925-04:00", comments="Source field: zfgbb.permission_group_assoc.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.742129715-04:00", comments="Source field: zfgbb.permission_group_assoc.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return permissionGroupAssocId;
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