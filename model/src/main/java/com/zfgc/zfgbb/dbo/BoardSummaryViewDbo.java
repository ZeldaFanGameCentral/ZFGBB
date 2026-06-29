package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class BoardSummaryViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750167444-04:00", comments="Source field: zfgbb.board_summary.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750186534-04:00", comments="Source field: zfgbb.board_summary.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750201313-04:00", comments="Source field: zfgbb.board_summary.board_name")
    private String boardName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750217353-04:00", comments="Source field: zfgbb.board_summary.thread_count")
    private Long threadCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750261411-04:00", comments="Source field: zfgbb.board_summary.post_count")
    private Long postCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750274281-04:00", comments="Source field: zfgbb.board_summary.latest_message_id")
    private Integer latestMessageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750287361-04:00", comments="Source field: zfgbb.board_summary.latest_thread_id")
    private Integer latestThreadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.75031176-04:00", comments="Source field: zfgbb.board_summary.latest_message_owner_id")
    private Integer latestMessageOwnerId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750325379-04:00", comments="Source field: zfgbb.board_summary.latest_message_user_name")
    private String latestMessageUserName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750338579-04:00", comments="Source field: zfgbb.board_summary.latest_message_created_ts")
    private OffsetDateTime latestMessageCreatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750351939-04:00", comments="Source field: zfgbb.board_summary.category_id")
    private Integer categoryId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750364988-04:00", comments="Source field: zfgbb.board_summary.parent_board_id")
    private Integer parentBoardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750378308-04:00", comments="Source field: zfgbb.board_summary.thread_name")
    private String threadName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750175794-04:00", comments="Source field: zfgbb.board_summary.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750181534-04:00", comments="Source field: zfgbb.board_summary.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750191554-04:00", comments="Source field: zfgbb.board_summary.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750196783-04:00", comments="Source field: zfgbb.board_summary.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750208233-04:00", comments="Source field: zfgbb.board_summary.board_name")
    public String getBoardName() {
        return boardName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750213063-04:00", comments="Source field: zfgbb.board_summary.board_name")
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750221823-04:00", comments="Source field: zfgbb.board_summary.thread_count")
    public Long getThreadCount() {
        return threadCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750257191-04:00", comments="Source field: zfgbb.board_summary.thread_count")
    public void setThreadCount(Long threadCount) {
        this.threadCount = threadCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750265921-04:00", comments="Source field: zfgbb.board_summary.post_count")
    public Long getPostCount() {
        return postCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750270051-04:00", comments="Source field: zfgbb.board_summary.post_count")
    public void setPostCount(Long postCount) {
        this.postCount = postCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750278741-04:00", comments="Source field: zfgbb.board_summary.latest_message_id")
    public Integer getLatestMessageId() {
        return latestMessageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750283291-04:00", comments="Source field: zfgbb.board_summary.latest_message_id")
    public void setLatestMessageId(Integer latestMessageId) {
        this.latestMessageId = latestMessageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.7502917-04:00", comments="Source field: zfgbb.board_summary.latest_thread_id")
    public Integer getLatestThreadId() {
        return latestThreadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.7503063-04:00", comments="Source field: zfgbb.board_summary.latest_thread_id")
    public void setLatestThreadId(Integer latestThreadId) {
        this.latestThreadId = latestThreadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.75031663-04:00", comments="Source field: zfgbb.board_summary.latest_message_owner_id")
    public Integer getLatestMessageOwnerId() {
        return latestMessageOwnerId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750321169-04:00", comments="Source field: zfgbb.board_summary.latest_message_owner_id")
    public void setLatestMessageOwnerId(Integer latestMessageOwnerId) {
        this.latestMessageOwnerId = latestMessageOwnerId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750329829-04:00", comments="Source field: zfgbb.board_summary.latest_message_user_name")
    public String getLatestMessageUserName() {
        return latestMessageUserName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750334439-04:00", comments="Source field: zfgbb.board_summary.latest_message_user_name")
    public void setLatestMessageUserName(String latestMessageUserName) {
        this.latestMessageUserName = latestMessageUserName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750343539-04:00", comments="Source field: zfgbb.board_summary.latest_message_created_ts")
    public OffsetDateTime getLatestMessageCreatedTs() {
        return latestMessageCreatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750347979-04:00", comments="Source field: zfgbb.board_summary.latest_message_created_ts")
    public void setLatestMessageCreatedTs(OffsetDateTime latestMessageCreatedTs) {
        this.latestMessageCreatedTs = latestMessageCreatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750356538-04:00", comments="Source field: zfgbb.board_summary.category_id")
    public Integer getCategoryId() {
        return categoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750360968-04:00", comments="Source field: zfgbb.board_summary.category_id")
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750369428-04:00", comments="Source field: zfgbb.board_summary.parent_board_id")
    public Integer getParentBoardId() {
        return parentBoardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750374178-04:00", comments="Source field: zfgbb.board_summary.parent_board_id")
    public void setParentBoardId(Integer parentBoardId) {
        this.parentBoardId = parentBoardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750382688-04:00", comments="Source field: zfgbb.board_summary.thread_name")
    public String getThreadName() {
        return threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.750389427-04:00", comments="Source field: zfgbb.board_summary.thread_name")
    public void setThreadName(String threadName) {
        this.threadName = threadName;
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