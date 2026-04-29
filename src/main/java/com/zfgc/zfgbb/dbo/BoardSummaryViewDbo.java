package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BoardSummaryViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380465216-04:00", comments="Source field: zfgbb.board_summary.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380485605-04:00", comments="Source field: zfgbb.board_summary.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380511734-04:00", comments="Source field: zfgbb.board_summary.board_name")
    private String boardName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380553283-04:00", comments="Source field: zfgbb.board_summary.thread_count")
    private Long threadCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380588492-04:00", comments="Source field: zfgbb.board_summary.post_count")
    private Long postCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380601201-04:00", comments="Source field: zfgbb.board_summary.latest_message_id")
    private Integer latestMessageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380613861-04:00", comments="Source field: zfgbb.board_summary.latest_thread_id")
    private Integer latestThreadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.38062635-04:00", comments="Source field: zfgbb.board_summary.latest_message_owner_id")
    private Integer latestMessageOwnerId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.3806388-04:00", comments="Source field: zfgbb.board_summary.latest_message_user_name")
    private String latestMessageUserName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380653279-04:00", comments="Source field: zfgbb.board_summary.latest_message_created_ts")
    private LocalDateTime latestMessageCreatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380738337-04:00", comments="Source field: zfgbb.board_summary.category_id")
    private Integer categoryId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380920081-04:00", comments="Source field: zfgbb.board_summary.parent_board_id")
    private Integer parentBoardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.381003458-04:00", comments="Source field: zfgbb.board_summary.thread_name")
    private String threadName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380474055-04:00", comments="Source field: zfgbb.board_summary.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380480175-04:00", comments="Source field: zfgbb.board_summary.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380491165-04:00", comments="Source field: zfgbb.board_summary.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380501564-04:00", comments="Source field: zfgbb.board_summary.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380521904-04:00", comments="Source field: zfgbb.board_summary.board_name")
    public String getBoardName() {
        return boardName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380543523-04:00", comments="Source field: zfgbb.board_summary.board_name")
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380575222-04:00", comments="Source field: zfgbb.board_summary.thread_count")
    public Long getThreadCount() {
        return threadCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380583592-04:00", comments="Source field: zfgbb.board_summary.thread_count")
    public void setThreadCount(Long threadCount) {
        this.threadCount = threadCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380592791-04:00", comments="Source field: zfgbb.board_summary.post_count")
    public Long getPostCount() {
        return postCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380597121-04:00", comments="Source field: zfgbb.board_summary.post_count")
    public void setPostCount(Long postCount) {
        this.postCount = postCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380605281-04:00", comments="Source field: zfgbb.board_summary.latest_message_id")
    public Integer getLatestMessageId() {
        return latestMessageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380609941-04:00", comments="Source field: zfgbb.board_summary.latest_message_id")
    public void setLatestMessageId(Integer latestMessageId) {
        this.latestMessageId = latestMessageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380617811-04:00", comments="Source field: zfgbb.board_summary.latest_thread_id")
    public Integer getLatestThreadId() {
        return latestThreadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.38062221-04:00", comments="Source field: zfgbb.board_summary.latest_thread_id")
    public void setLatestThreadId(Integer latestThreadId) {
        this.latestThreadId = latestThreadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.38063049-04:00", comments="Source field: zfgbb.board_summary.latest_message_owner_id")
    public Integer getLatestMessageOwnerId() {
        return latestMessageOwnerId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.38063479-04:00", comments="Source field: zfgbb.board_summary.latest_message_owner_id")
    public void setLatestMessageOwnerId(Integer latestMessageOwnerId) {
        this.latestMessageOwnerId = latestMessageOwnerId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.38064299-04:00", comments="Source field: zfgbb.board_summary.latest_message_user_name")
    public String getLatestMessageUserName() {
        return latestMessageUserName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.38064914-04:00", comments="Source field: zfgbb.board_summary.latest_message_user_name")
    public void setLatestMessageUserName(String latestMessageUserName) {
        this.latestMessageUserName = latestMessageUserName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380658389-04:00", comments="Source field: zfgbb.board_summary.latest_message_created_ts")
    public LocalDateTime getLatestMessageCreatedTs() {
        return latestMessageCreatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380662809-04:00", comments="Source field: zfgbb.board_summary.latest_message_created_ts")
    public void setLatestMessageCreatedTs(LocalDateTime latestMessageCreatedTs) {
        this.latestMessageCreatedTs = latestMessageCreatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380764186-04:00", comments="Source field: zfgbb.board_summary.category_id")
    public Integer getCategoryId() {
        return categoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.380798785-04:00", comments="Source field: zfgbb.board_summary.category_id")
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.38093704-04:00", comments="Source field: zfgbb.board_summary.parent_board_id")
    public Integer getParentBoardId() {
        return parentBoardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.38094931-04:00", comments="Source field: zfgbb.board_summary.parent_board_id")
    public void setParentBoardId(Integer parentBoardId) {
        this.parentBoardId = parentBoardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.381070596-04:00", comments="Source field: zfgbb.board_summary.thread_name")
    public String getThreadName() {
        return threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.381109665-04:00", comments="Source field: zfgbb.board_summary.thread_name")
    public void setThreadName(String threadName) {
        this.threadName = threadName;
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