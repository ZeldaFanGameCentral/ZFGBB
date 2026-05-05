package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BrUserPermissionDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.209257529-04:00", comments="Source field: zfgbb.br_user_permission.br_user_permission_id")
    private Integer brUserPermissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.209344336-04:00", comments="Source field: zfgbb.br_user_permission.user_permission_id")
    private Integer userPermissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.20956001-04:00", comments="Source field: zfgbb.br_user_permission.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.209291918-04:00", comments="Source field: zfgbb.br_user_permission.br_user_permission_id")
    public Integer getBrUserPermissionId() {
        return brUserPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.209325137-04:00", comments="Source field: zfgbb.br_user_permission.br_user_permission_id")
    public void setBrUserPermissionId(Integer brUserPermissionId) {
        this.brUserPermissionId = brUserPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.209483712-04:00", comments="Source field: zfgbb.br_user_permission.user_permission_id")
    public Integer getUserPermissionId() {
        return userPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.20953999-04:00", comments="Source field: zfgbb.br_user_permission.user_permission_id")
    public void setUserPermissionId(Integer userPermissionId) {
        this.userPermissionId = userPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.209585309-04:00", comments="Source field: zfgbb.br_user_permission.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.209606938-04:00", comments="Source field: zfgbb.br_user_permission.user_id")
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