package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BrBoardPermissionDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.486544879-04:00", comments="Source field: zfgbb.br_board_permission.br_board_permission_id")
    private Integer brBoardPermissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.486605497-04:00", comments="Source field: zfgbb.br_board_permission.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.486664645-04:00", comments="Source field: zfgbb.br_board_permission.permission_id")
    private Integer permissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.486569288-04:00", comments="Source field: zfgbb.br_board_permission.br_board_permission_id")
    public Integer getBrBoardPermissionId() {
        return brBoardPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.486589108-04:00", comments="Source field: zfgbb.br_board_permission.br_board_permission_id")
    public void setBrBoardPermissionId(Integer brBoardPermissionId) {
        this.brBoardPermissionId = brBoardPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.486630206-04:00", comments="Source field: zfgbb.br_board_permission.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.486649406-04:00", comments="Source field: zfgbb.br_board_permission.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.486685234-04:00", comments="Source field: zfgbb.br_board_permission.permission_id")
    public Integer getPermissionId() {
        return permissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.486803071-04:00", comments="Source field: zfgbb.br_board_permission.permission_id")
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