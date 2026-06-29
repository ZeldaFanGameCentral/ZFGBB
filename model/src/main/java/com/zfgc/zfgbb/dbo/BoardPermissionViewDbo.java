package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class BoardPermissionViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744012136-04:00", comments="Source field: zfgbb.board_permission_view.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744041875-04:00", comments="Source field: zfgbb.board_permission_view.permission_id")
    private Integer permissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744057984-04:00", comments="Source field: zfgbb.board_permission_view.permission_code")
    private String permissionCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744073784-04:00", comments="Source field: zfgbb.board_permission_view.permission_name")
    private String permissionName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744021376-04:00", comments="Source field: zfgbb.board_permission_view.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744036075-04:00", comments="Source field: zfgbb.board_permission_view.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744047375-04:00", comments="Source field: zfgbb.board_permission_view.permission_id")
    public Integer getPermissionId() {
        return permissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744052735-04:00", comments="Source field: zfgbb.board_permission_view.permission_id")
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744063014-04:00", comments="Source field: zfgbb.board_permission_view.permission_code")
    public String getPermissionCode() {
        return permissionCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744068724-04:00", comments="Source field: zfgbb.board_permission_view.permission_code")
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744078704-04:00", comments="Source field: zfgbb.board_permission_view.permission_name")
    public String getPermissionName() {
        return permissionName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.744084094-04:00", comments="Source field: zfgbb.board_permission_view.permission_name")
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
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