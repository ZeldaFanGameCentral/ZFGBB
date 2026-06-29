package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class UserPermissionViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744735693-04:00", comments="Source field: zfgbb.user_permission_view.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744755973-04:00", comments="Source field: zfgbb.user_permission_view.permission_id")
    private Integer permissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744770942-04:00", comments="Source field: zfgbb.user_permission_view.permission_code")
    private String permissionCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744744523-04:00", comments="Source field: zfgbb.user_permission_view.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744750523-04:00", comments="Source field: zfgbb.user_permission_view.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744761153-04:00", comments="Source field: zfgbb.user_permission_view.permission_id")
    public Integer getPermissionId() {
        return permissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744766322-04:00", comments="Source field: zfgbb.user_permission_view.permission_id")
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744775902-04:00", comments="Source field: zfgbb.user_permission_view.permission_code")
    public String getPermissionCode() {
        return permissionCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744781302-04:00", comments="Source field: zfgbb.user_permission_view.permission_code")
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    @Override
    public Integer getPkId() {
        return null;
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