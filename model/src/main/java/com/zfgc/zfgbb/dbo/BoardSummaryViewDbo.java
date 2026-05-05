package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class BoardSummaryViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.263922264-04:00", comments="Source field: zfgbb.board_summary.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.263993692-04:00", comments="Source field: zfgbb.board_summary.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264022621-04:00", comments="Source field: zfgbb.board_summary.board_name")
    private String boardName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264085609-04:00", comments="Source field: zfgbb.board_summary.thread_count")
    private Long threadCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264129738-04:00", comments="Source field: zfgbb.board_summary.post_count")
    private Long postCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264145227-04:00", comments="Source field: zfgbb.board_summary.latest_message_id")
    private Integer latestMessageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264160247-04:00", comments="Source field: zfgbb.board_summary.latest_thread_id")
    private Integer latestThreadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264175076-04:00", comments="Source field: zfgbb.board_summary.latest_message_owner_id")
    private Integer latestMessageOwnerId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264190006-04:00", comments="Source field: zfgbb.board_summary.latest_message_user_name")
    private String latestMessageUserName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264206715-04:00", comments="Source field: zfgbb.board_summary.latest_message_created_ts")
    private LocalDateTime latestMessageCreatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264222815-04:00", comments="Source field: zfgbb.board_summary.category_id")
    private Integer categoryId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264237504-04:00", comments="Source field: zfgbb.board_summary.parent_board_id")
    private Integer parentBoardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264252594-04:00", comments="Source field: zfgbb.board_summary.thread_name")
    private String threadName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.263931844-04:00", comments="Source field: zfgbb.board_summary.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.263959833-04:00", comments="Source field: zfgbb.board_summary.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264006962-04:00", comments="Source field: zfgbb.board_summary.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264016611-04:00", comments="Source field: zfgbb.board_summary.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.26406663-04:00", comments="Source field: zfgbb.board_summary.board_name")
    public String getBoardName() {
        return boardName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264079329-04:00", comments="Source field: zfgbb.board_summary.board_name")
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264115178-04:00", comments="Source field: zfgbb.board_summary.thread_count")
    public Long getThreadCount() {
        return threadCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264124458-04:00", comments="Source field: zfgbb.board_summary.thread_count")
    public void setThreadCount(Long threadCount) {
        this.threadCount = threadCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264134958-04:00", comments="Source field: zfgbb.board_summary.post_count")
    public Long getPostCount() {
        return postCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264140467-04:00", comments="Source field: zfgbb.board_summary.post_count")
    public void setPostCount(Long postCount) {
        this.postCount = postCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264150097-04:00", comments="Source field: zfgbb.board_summary.latest_message_id")
    public Integer getLatestMessageId() {
        return latestMessageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264155687-04:00", comments="Source field: zfgbb.board_summary.latest_message_id")
    public void setLatestMessageId(Integer latestMessageId) {
        this.latestMessageId = latestMessageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264165107-04:00", comments="Source field: zfgbb.board_summary.latest_thread_id")
    public Integer getLatestThreadId() {
        return latestThreadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264170376-04:00", comments="Source field: zfgbb.board_summary.latest_thread_id")
    public void setLatestThreadId(Integer latestThreadId) {
        this.latestThreadId = latestThreadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264180036-04:00", comments="Source field: zfgbb.board_summary.latest_message_owner_id")
    public Integer getLatestMessageOwnerId() {
        return latestMessageOwnerId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264185356-04:00", comments="Source field: zfgbb.board_summary.latest_message_owner_id")
    public void setLatestMessageOwnerId(Integer latestMessageOwnerId) {
        this.latestMessageOwnerId = latestMessageOwnerId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264194666-04:00", comments="Source field: zfgbb.board_summary.latest_message_user_name")
    public String getLatestMessageUserName() {
        return latestMessageUserName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264201785-04:00", comments="Source field: zfgbb.board_summary.latest_message_user_name")
    public void setLatestMessageUserName(String latestMessageUserName) {
        this.latestMessageUserName = latestMessageUserName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264212355-04:00", comments="Source field: zfgbb.board_summary.latest_message_created_ts")
    public LocalDateTime getLatestMessageCreatedTs() {
        return latestMessageCreatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264217915-04:00", comments="Source field: zfgbb.board_summary.latest_message_created_ts")
    public void setLatestMessageCreatedTs(LocalDateTime latestMessageCreatedTs) {
        this.latestMessageCreatedTs = latestMessageCreatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264227765-04:00", comments="Source field: zfgbb.board_summary.category_id")
    public Integer getCategoryId() {
        return categoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264233034-04:00", comments="Source field: zfgbb.board_summary.category_id")
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264242324-04:00", comments="Source field: zfgbb.board_summary.parent_board_id")
    public Integer getParentBoardId() {
        return parentBoardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264247744-04:00", comments="Source field: zfgbb.board_summary.parent_board_id")
    public void setParentBoardId(Integer parentBoardId) {
        this.parentBoardId = parentBoardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264257314-04:00", comments="Source field: zfgbb.board_summary.thread_name")
    public String getThreadName() {
        return threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.264265233-04:00", comments="Source field: zfgbb.board_summary.thread_name")
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