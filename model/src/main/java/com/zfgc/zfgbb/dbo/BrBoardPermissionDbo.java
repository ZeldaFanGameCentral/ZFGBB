package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class BrBoardPermissionDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.662450084-04:00", comments="Source field: zfgbb.br_board_permission.br_board_permission_id")
    private Integer brBoardPermissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.662521852-04:00", comments="Source field: zfgbb.br_board_permission.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.66257486-04:00", comments="Source field: zfgbb.br_board_permission.permission_id")
    private Integer permissionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.662482803-04:00", comments="Source field: zfgbb.br_board_permission.br_board_permission_id")
    public Integer getBrBoardPermissionId() {
        return brBoardPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.662505222-04:00", comments="Source field: zfgbb.br_board_permission.br_board_permission_id")
    public void setBrBoardPermissionId(Integer brBoardPermissionId) {
        this.brBoardPermissionId = brBoardPermissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.662540691-04:00", comments="Source field: zfgbb.br_board_permission.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.662559191-04:00", comments="Source field: zfgbb.br_board_permission.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.66259307-04:00", comments="Source field: zfgbb.br_board_permission.permission_id")
    public Integer getPermissionId() {
        return permissionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.662611279-04:00", comments="Source field: zfgbb.br_board_permission.permission_id")
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public Integer getPkId() {
        return brBoardPermissionId;
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