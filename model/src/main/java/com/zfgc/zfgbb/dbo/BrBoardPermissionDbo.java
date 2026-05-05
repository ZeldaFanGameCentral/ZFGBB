package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BrBoardPermissionDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.211613664-04:00", comments="Source field: zfgbb.br_board_permission.br_board_permission_id")
    private Integer brBoardPermissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.211674882-04:00", comments="Source field: zfgbb.br_board_permission.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.211729931-04:00", comments="Source field: zfgbb.br_board_permission.permission_id")
    private Integer permissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.211639064-04:00", comments="Source field: zfgbb.br_board_permission.br_board_permission_id")
    public Integer getBrBoardPermissionId() {
        return brBoardPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.211658833-04:00", comments="Source field: zfgbb.br_board_permission.br_board_permission_id")
    public void setBrBoardPermissionId(Integer brBoardPermissionId) {
        this.brBoardPermissionId = brBoardPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.211693892-04:00", comments="Source field: zfgbb.br_board_permission.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.211714181-04:00", comments="Source field: zfgbb.br_board_permission.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.21176002-04:00", comments="Source field: zfgbb.br_board_permission.permission_id")
    public Integer getPermissionId() {
        return permissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.211791079-04:00", comments="Source field: zfgbb.br_board_permission.permission_id")
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public Integer getPkId() {
        return brBoardPermissionId;
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