package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BrBoardPermissionDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.419483451-04:00", comments="Source field: zfgbb.br_board_permission.br_board_permission_id")
    private Integer brBoardPermissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.419543499-04:00", comments="Source field: zfgbb.br_board_permission.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.419598157-04:00", comments="Source field: zfgbb.br_board_permission.permission_id")
    private Integer permissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.41950706-04:00", comments="Source field: zfgbb.br_board_permission.br_board_permission_id")
    public Integer getBrBoardPermissionId() {
        return brBoardPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.41952786-04:00", comments="Source field: zfgbb.br_board_permission.br_board_permission_id")
    public void setBrBoardPermissionId(Integer brBoardPermissionId) {
        this.brBoardPermissionId = brBoardPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.419566408-04:00", comments="Source field: zfgbb.br_board_permission.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.419584568-04:00", comments="Source field: zfgbb.br_board_permission.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.419618787-04:00", comments="Source field: zfgbb.br_board_permission.permission_id")
    public Integer getPermissionId() {
        return permissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.419647076-04:00", comments="Source field: zfgbb.br_board_permission.permission_id")
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