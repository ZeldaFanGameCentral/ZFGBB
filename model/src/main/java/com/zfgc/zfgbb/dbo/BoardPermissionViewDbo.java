package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BoardPermissionViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463122598-04:00", comments="Source field: zfgbb.board_permission_view.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463146137-04:00", comments="Source field: zfgbb.board_permission_view.permission_id")
    private Integer permissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463166066-04:00", comments="Source field: zfgbb.board_permission_view.permission_code")
    private String permissionCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463187025-04:00", comments="Source field: zfgbb.board_permission_view.permission_name")
    private String permissionName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463131697-04:00", comments="Source field: zfgbb.board_permission_view.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463139317-04:00", comments="Source field: zfgbb.board_permission_view.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463152637-04:00", comments="Source field: zfgbb.board_permission_view.permission_id")
    public Integer getPermissionId() {
        return permissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463159836-04:00", comments="Source field: zfgbb.board_permission_view.permission_id")
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463172186-04:00", comments="Source field: zfgbb.board_permission_view.permission_code")
    public String getPermissionCode() {
        return permissionCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463180716-04:00", comments="Source field: zfgbb.board_permission_view.permission_code")
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463193785-04:00", comments="Source field: zfgbb.board_permission_view.permission_name")
    public String getPermissionName() {
        return permissionName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.463201815-04:00", comments="Source field: zfgbb.board_permission_view.permission_name")
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public Integer getPkId() {
        return null;
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