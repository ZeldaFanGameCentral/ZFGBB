package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BoardPermissionViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526802779-04:00", comments="Source field: zfgbb.board_permission_view.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526826338-04:00", comments="Source field: zfgbb.board_permission_view.permission_id")
    private Integer permissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526845447-04:00", comments="Source field: zfgbb.board_permission_view.permission_code")
    private String permissionCode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526866437-04:00", comments="Source field: zfgbb.board_permission_view.permission_name")
    private String permissionName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526812279-04:00", comments="Source field: zfgbb.board_permission_view.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526819868-04:00", comments="Source field: zfgbb.board_permission_view.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526832828-04:00", comments="Source field: zfgbb.board_permission_view.permission_id")
    public Integer getPermissionId() {
        return permissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526839428-04:00", comments="Source field: zfgbb.board_permission_view.permission_id")
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526851567-04:00", comments="Source field: zfgbb.board_permission_view.permission_code")
    public String getPermissionCode() {
        return permissionCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526860007-04:00", comments="Source field: zfgbb.board_permission_view.permission_code")
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526873346-04:00", comments="Source field: zfgbb.board_permission_view.permission_name")
    public String getPermissionName() {
        return permissionName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.526880876-04:00", comments="Source field: zfgbb.board_permission_view.permission_name")
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