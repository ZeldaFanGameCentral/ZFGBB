package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class UserPermissionGroupAssocDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743108894-04:00", comments="Source field: zfgbb.user_permission_group_assoc.user_permission_group_assoc_id")
    private Integer userPermissionGroupAssocId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743133053-04:00", comments="Source field: zfgbb.user_permission_group_assoc.permission_group_id")
    private Integer permissionGroupId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743149453-04:00", comments="Source field: zfgbb.user_permission_group_assoc.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743164792-04:00", comments="Source field: zfgbb.user_permission_group_assoc.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743182562-04:00", comments="Source field: zfgbb.user_permission_group_assoc.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743197381-04:00", comments="Source field: zfgbb.user_permission_group_assoc.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743118514-04:00", comments="Source field: zfgbb.user_permission_group_assoc.user_permission_group_assoc_id")
    public Integer getUserPermissionGroupAssocId() {
        return userPermissionGroupAssocId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743125173-04:00", comments="Source field: zfgbb.user_permission_group_assoc.user_permission_group_assoc_id")
    public void setUserPermissionGroupAssocId(Integer userPermissionGroupAssocId) {
        this.userPermissionGroupAssocId = userPermissionGroupAssocId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743138873-04:00", comments="Source field: zfgbb.user_permission_group_assoc.permission_group_id")
    public Integer getPermissionGroupId() {
        return permissionGroupId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743145983-04:00", comments="Source field: zfgbb.user_permission_group_assoc.permission_group_id")
    public void setPermissionGroupId(Integer permissionGroupId) {
        this.permissionGroupId = permissionGroupId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743154763-04:00", comments="Source field: zfgbb.user_permission_group_assoc.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743161342-04:00", comments="Source field: zfgbb.user_permission_group_assoc.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743172122-04:00", comments="Source field: zfgbb.user_permission_group_assoc.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743177432-04:00", comments="Source field: zfgbb.user_permission_group_assoc.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743187861-04:00", comments="Source field: zfgbb.user_permission_group_assoc.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743192661-04:00", comments="Source field: zfgbb.user_permission_group_assoc.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743202351-04:00", comments="Source field: zfgbb.user_permission_group_assoc.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.743208521-04:00", comments="Source field: zfgbb.user_permission_group_assoc.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return userPermissionGroupAssocId;
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