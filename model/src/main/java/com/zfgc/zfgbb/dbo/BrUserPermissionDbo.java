package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BrUserPermissionDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.417366309-04:00", comments="Source field: zfgbb.br_user_permission.br_user_permission_id")
    private Integer brUserPermissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.417433446-04:00", comments="Source field: zfgbb.br_user_permission.user_permission_id")
    private Integer userPermissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.417584432-04:00", comments="Source field: zfgbb.br_user_permission.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.417392638-04:00", comments="Source field: zfgbb.br_user_permission.br_user_permission_id")
    public Integer getBrUserPermissionId() {
        return brUserPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.417414887-04:00", comments="Source field: zfgbb.br_user_permission.br_user_permission_id")
    public void setBrUserPermissionId(Integer brUserPermissionId) {
        this.brUserPermissionId = brUserPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.417537443-04:00", comments="Source field: zfgbb.br_user_permission.user_permission_id")
    public Integer getUserPermissionId() {
        return userPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.417564922-04:00", comments="Source field: zfgbb.br_user_permission.user_permission_id")
    public void setUserPermissionId(Integer userPermissionId) {
        this.userPermissionId = userPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.417605801-04:00", comments="Source field: zfgbb.br_user_permission.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.41762501-04:00", comments="Source field: zfgbb.br_user_permission.user_id")
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