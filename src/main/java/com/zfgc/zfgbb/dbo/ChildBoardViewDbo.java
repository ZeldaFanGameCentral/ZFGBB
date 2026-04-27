package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class ChildBoardViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.381875299-04:00", comments="Source field: zfgbb.child_board_view.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.381931088-04:00", comments="Source field: zfgbb.child_board_view.board_name")
    private String boardName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.381950847-04:00", comments="Source field: zfgbb.child_board_view.parent_board_id")
    private Integer parentBoardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.381891459-04:00", comments="Source field: zfgbb.child_board_view.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.381903068-04:00", comments="Source field: zfgbb.child_board_view.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.381938707-04:00", comments="Source field: zfgbb.child_board_view.board_name")
    public String getBoardName() {
        return boardName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.381946237-04:00", comments="Source field: zfgbb.child_board_view.board_name")
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.381955447-04:00", comments="Source field: zfgbb.child_board_view.parent_board_id")
    public Integer getParentBoardId() {
        return parentBoardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.381960107-04:00", comments="Source field: zfgbb.child_board_view.parent_board_id")
    public void setParentBoardId(Integer parentBoardId) {
        this.parentBoardId = parentBoardId;
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