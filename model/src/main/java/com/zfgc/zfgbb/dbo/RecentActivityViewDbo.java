package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class RecentActivityViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196459949-04:00", comments="Source field: zfgbb.recent_activity_view.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196473788-04:00", comments="Source field: zfgbb.recent_activity_view.thread_name")
    private String threadName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196483748-04:00", comments="Source field: zfgbb.recent_activity_view.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196492698-04:00", comments="Source field: zfgbb.recent_activity_view.board_name")
    private String boardName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196501277-04:00", comments="Source field: zfgbb.recent_activity_view.last_poster")
    private String lastPoster;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196509487-04:00", comments="Source field: zfgbb.recent_activity_view.last_poster_id")
    private Integer lastPosterId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196517717-04:00", comments="Source field: zfgbb.recent_activity_view.last_post_ts")
    private OffsetDateTime lastPostTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196466328-04:00", comments="Source field: zfgbb.recent_activity_view.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196470428-04:00", comments="Source field: zfgbb.recent_activity_view.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196477108-04:00", comments="Source field: zfgbb.recent_activity_view.thread_name")
    public String getThreadName() {
        return threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196480848-04:00", comments="Source field: zfgbb.recent_activity_view.thread_name")
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196486938-04:00", comments="Source field: zfgbb.recent_activity_view.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196489998-04:00", comments="Source field: zfgbb.recent_activity_view.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196495587-04:00", comments="Source field: zfgbb.recent_activity_view.board_name")
    public String getBoardName() {
        return boardName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196498627-04:00", comments="Source field: zfgbb.recent_activity_view.board_name")
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196503977-04:00", comments="Source field: zfgbb.recent_activity_view.last_poster")
    public String getLastPoster() {
        return lastPoster;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196506877-04:00", comments="Source field: zfgbb.recent_activity_view.last_poster")
    public void setLastPoster(String lastPoster) {
        this.lastPoster = lastPoster;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196512267-04:00", comments="Source field: zfgbb.recent_activity_view.last_poster_id")
    public Integer getLastPosterId() {
        return lastPosterId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196515067-04:00", comments="Source field: zfgbb.recent_activity_view.last_poster_id")
    public void setLastPosterId(Integer lastPosterId) {
        this.lastPosterId = lastPosterId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196521007-04:00", comments="Source field: zfgbb.recent_activity_view.last_post_ts")
    public OffsetDateTime getLastPostTs() {
        return lastPostTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T16:19:39.196523747-04:00", comments="Source field: zfgbb.recent_activity_view.last_post_ts")
    public void setLastPostTs(OffsetDateTime lastPostTs) {
        this.lastPostTs = lastPostTs;
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