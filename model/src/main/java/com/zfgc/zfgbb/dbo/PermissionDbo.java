package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class PermissionDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.654457833-04:00", comments="Source field: zfgbb.permission.permission_id")
    private Integer permissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.654627568-04:00", comments="Source field: zfgbb.permission.permission_name")
    private String permissionName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.654958407-04:00", comments="Source field: zfgbb.permission.permission_code")
    private String permissionCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.655092933-04:00", comments="Source field: zfgbb.permission.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.655275218-04:00", comments="Source field: zfgbb.permission.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.654512971-04:00", comments="Source field: zfgbb.permission.permission_id")
    public Integer getPermissionId() {
        return permissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.654592949-04:00", comments="Source field: zfgbb.permission.permission_id")
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.654663807-04:00", comments="Source field: zfgbb.permission.permission_name")
    public String getPermissionName() {
        return permissionName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.654914699-04:00", comments="Source field: zfgbb.permission.permission_name")
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.655006566-04:00", comments="Source field: zfgbb.permission.permission_code")
    public String getPermissionCode() {
        return permissionCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.655046375-04:00", comments="Source field: zfgbb.permission.permission_code")
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.655176891-04:00", comments="Source field: zfgbb.permission.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.655242749-04:00", comments="Source field: zfgbb.permission.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.655320386-04:00", comments="Source field: zfgbb.permission.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.655356165-04:00", comments="Source field: zfgbb.permission.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return permissionId;
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