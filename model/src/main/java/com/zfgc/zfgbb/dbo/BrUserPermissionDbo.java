package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class BrUserPermissionDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.659581764-04:00", comments="Source field: zfgbb.br_user_permission.br_user_permission_id")
    private Integer brUserPermissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.659903914-04:00", comments="Source field: zfgbb.br_user_permission.user_permission_id")
    private Integer userPermissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.66001894-04:00", comments="Source field: zfgbb.br_user_permission.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.659807677-04:00", comments="Source field: zfgbb.br_user_permission.br_user_permission_id")
    public Integer getBrUserPermissionId() {
        return brUserPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.659869115-04:00", comments="Source field: zfgbb.br_user_permission.br_user_permission_id")
    public void setBrUserPermissionId(Integer brUserPermissionId) {
        this.brUserPermissionId = brUserPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.659935793-04:00", comments="Source field: zfgbb.br_user_permission.user_permission_id")
    public Integer getUserPermissionId() {
        return userPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.659990101-04:00", comments="Source field: zfgbb.br_user_permission.user_permission_id")
    public void setUserPermissionId(Integer userPermissionId) {
        this.userPermissionId = userPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.660046989-04:00", comments="Source field: zfgbb.br_user_permission.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.660071858-04:00", comments="Source field: zfgbb.br_user_permission.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public Integer getPkId() {
        return brUserPermissionId;
    }

    @Override
    public OffsetDateTime getCreatedTime() {
        return null;
    }

    @Override
    public OffsetDateTime getUpdatedTime() {
        return null;
    }
}