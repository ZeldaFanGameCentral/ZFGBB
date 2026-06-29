package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class NotificationSubscriptionDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737190898-04:00", comments="Source field: zfgbb.notification_subscription.notification_subscription_id")
    private Integer notificationSubscriptionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737211428-04:00", comments="Source field: zfgbb.notification_subscription.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737226267-04:00", comments="Source field: zfgbb.notification_subscription.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737240177-04:00", comments="Source field: zfgbb.notification_subscription.board_id")
    private Integer boardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737254206-04:00", comments="Source field: zfgbb.notification_subscription.last_notified_ts")
    private OffsetDateTime lastNotifiedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737268846-04:00", comments="Source field: zfgbb.notification_subscription.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737284435-04:00", comments="Source field: zfgbb.notification_subscription.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737307745-04:00", comments="Source field: zfgbb.notification_subscription.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737200018-04:00", comments="Source field: zfgbb.notification_subscription.notification_subscription_id")
    public Integer getNotificationSubscriptionId() {
        return notificationSubscriptionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737206008-04:00", comments="Source field: zfgbb.notification_subscription.notification_subscription_id")
    public void setNotificationSubscriptionId(Integer notificationSubscriptionId) {
        this.notificationSubscriptionId = notificationSubscriptionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737216527-04:00", comments="Source field: zfgbb.notification_subscription.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737221617-04:00", comments="Source field: zfgbb.notification_subscription.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737231047-04:00", comments="Source field: zfgbb.notification_subscription.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737235767-04:00", comments="Source field: zfgbb.notification_subscription.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737244707-04:00", comments="Source field: zfgbb.notification_subscription.board_id")
    public Integer getBoardId() {
        return boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737249446-04:00", comments="Source field: zfgbb.notification_subscription.board_id")
    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737259466-04:00", comments="Source field: zfgbb.notification_subscription.last_notified_ts")
    public OffsetDateTime getLastNotifiedTs() {
        return lastNotifiedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737264426-04:00", comments="Source field: zfgbb.notification_subscription.last_notified_ts")
    public void setLastNotifiedTs(OffsetDateTime lastNotifiedTs) {
        this.lastNotifiedTs = lastNotifiedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737275306-04:00", comments="Source field: zfgbb.notification_subscription.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737280065-04:00", comments="Source field: zfgbb.notification_subscription.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737289055-04:00", comments="Source field: zfgbb.notification_subscription.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737293435-04:00", comments="Source field: zfgbb.notification_subscription.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737316974-04:00", comments="Source field: zfgbb.notification_subscription.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.737323304-04:00", comments="Source field: zfgbb.notification_subscription.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return notificationSubscriptionId;
    }

    @Override
    public OffsetDateTime getCreatedTime() {
        return createdTs;
    }

    @Override
    public OffsetDateTime getUpdatedTime() {
        return updatedTs;
    }
}