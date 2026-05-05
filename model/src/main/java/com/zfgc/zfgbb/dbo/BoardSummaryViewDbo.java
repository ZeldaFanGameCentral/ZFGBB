package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BoardSummaryViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.469914201-04:00", comments="Source field: zfgbb.board_summary.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470019707-04:00", comments="Source field: zfgbb.board_summary.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470105305-04:00", comments="Source field: zfgbb.board_summary.board_name")
    private String boardName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470195992-04:00", comments="Source field: zfgbb.board_summary.thread_count")
    private Long threadCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470214121-04:00", comments="Source field: zfgbb.board_summary.post_count")
    private Long postCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470228361-04:00", comments="Source field: zfgbb.board_summary.latest_message_id")
    private Integer latestMessageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.47024302-04:00", comments="Source field: zfgbb.board_summary.latest_thread_id")
    private Integer latestThreadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.47025712-04:00", comments="Source field: zfgbb.board_summary.latest_message_owner_id")
    private Integer latestMessageOwnerId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470272709-04:00", comments="Source field: zfgbb.board_summary.latest_message_user_name")
    private String latestMessageUserName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470288359-04:00", comments="Source field: zfgbb.board_summary.latest_message_created_ts")
    private LocalDateTime latestMessageCreatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470303618-04:00", comments="Source field: zfgbb.board_summary.category_id")
    private Integer categoryId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470317688-04:00", comments="Source field: zfgbb.board_summary.parent_board_id")
    private Integer parentBoardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470332137-04:00", comments="Source field: zfgbb.board_summary.thread_name")
    private String threadName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.46993281-04:00", comments="Source field: zfgbb.board_summary.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.469967159-04:00", comments="Source field: zfgbb.board_summary.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470083375-04:00", comments="Source field: zfgbb.board_summary.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470097865-04:00", comments="Source field: zfgbb.board_summary.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470111844-04:00", comments="Source field: zfgbb.board_summary.board_name")
    public String getBoardName() {
        return boardName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470188392-04:00", comments="Source field: zfgbb.board_summary.board_name")
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470201481-04:00", comments="Source field: zfgbb.board_summary.thread_count")
    public Long getThreadCount() {
        return threadCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470206851-04:00", comments="Source field: zfgbb.board_summary.thread_count")
    public void setThreadCount(Long threadCount) {
        this.threadCount = threadCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470218921-04:00", comments="Source field: zfgbb.board_summary.post_count")
    public Long getPostCount() {
        return postCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470223851-04:00", comments="Source field: zfgbb.board_summary.post_count")
    public void setPostCount(Long postCount) {
        this.postCount = postCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.47023312-04:00", comments="Source field: zfgbb.board_summary.latest_message_id")
    public Integer getLatestMessageId() {
        return latestMessageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.47023823-04:00", comments="Source field: zfgbb.board_summary.latest_message_id")
    public void setLatestMessageId(Integer latestMessageId) {
        this.latestMessageId = latestMessageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.47024765-04:00", comments="Source field: zfgbb.board_summary.latest_thread_id")
    public Integer getLatestThreadId() {
        return latestThreadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.47025247-04:00", comments="Source field: zfgbb.board_summary.latest_thread_id")
    public void setLatestThreadId(Integer latestThreadId) {
        this.latestThreadId = latestThreadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.4702617-04:00", comments="Source field: zfgbb.board_summary.latest_message_owner_id")
    public Integer getLatestMessageOwnerId() {
        return latestMessageOwnerId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470266609-04:00", comments="Source field: zfgbb.board_summary.latest_message_owner_id")
    public void setLatestMessageOwnerId(Integer latestMessageOwnerId) {
        this.latestMessageOwnerId = latestMessageOwnerId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470277719-04:00", comments="Source field: zfgbb.board_summary.latest_message_user_name")
    public String getLatestMessageUserName() {
        return latestMessageUserName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470283289-04:00", comments="Source field: zfgbb.board_summary.latest_message_user_name")
    public void setLatestMessageUserName(String latestMessageUserName) {
        this.latestMessageUserName = latestMessageUserName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470293998-04:00", comments="Source field: zfgbb.board_summary.latest_message_created_ts")
    public LocalDateTime getLatestMessageCreatedTs() {
        return latestMessageCreatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470298998-04:00", comments="Source field: zfgbb.board_summary.latest_message_created_ts")
    public void setLatestMessageCreatedTs(LocalDateTime latestMessageCreatedTs) {
        this.latestMessageCreatedTs = latestMessageCreatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470308388-04:00", comments="Source field: zfgbb.board_summary.category_id")
    public Integer getCategoryId() {
        return categoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470313188-04:00", comments="Source field: zfgbb.board_summary.category_id")
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470322378-04:00", comments="Source field: zfgbb.board_summary.parent_board_id")
    public Integer getParentBoardId() {
        return parentBoardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470327567-04:00", comments="Source field: zfgbb.board_summary.parent_board_id")
    public void setParentBoardId(Integer parentBoardId) {
        this.parentBoardId = parentBoardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470336787-04:00", comments="Source field: zfgbb.board_summary.thread_name")
    public String getThreadName() {
        return threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470342057-04:00", comments="Source field: zfgbb.board_summary.thread_name")
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