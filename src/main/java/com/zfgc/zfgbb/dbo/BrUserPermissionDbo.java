package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BrUserPermissionDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.342107086-04:00", comments="Source field: zfgbb.br_user_permission.br_user_permission_id")
    private Integer brUserPermissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.342223412-04:00", comments="Source field: zfgbb.br_user_permission.user_permission_id")
    private Integer userPermissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.34229301-04:00", comments="Source field: zfgbb.br_user_permission.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.342151484-04:00", comments="Source field: zfgbb.br_user_permission.br_user_permission_id")
    public Integer getBrUserPermissionId() {
        return brUserPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.342203053-04:00", comments="Source field: zfgbb.br_user_permission.br_user_permission_id")
    public void setBrUserPermissionId(Integer brUserPermissionId) {
        this.brUserPermissionId = brUserPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.342241031-04:00", comments="Source field: zfgbb.br_user_permission.user_permission_id")
    public Integer getUserPermissionId() {
        return userPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.342256911-04:00", comments="Source field: zfgbb.br_user_permission.user_permission_id")
    public void setUserPermissionId(Integer userPermissionId) {
        this.userPermissionId = userPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.342322919-04:00", comments="Source field: zfgbb.br_user_permission.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.342341058-04:00", comments="Source field: zfgbb.br_user_permission.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public Integer getPkId() {
        return brUserPermissionId;
    }

    @Override
    public LocalDateTime getCreatedTime() {
        return null;
    }

    @Override
    public LocalDateTime getUpdatedTime() {
        return null;
    }
}