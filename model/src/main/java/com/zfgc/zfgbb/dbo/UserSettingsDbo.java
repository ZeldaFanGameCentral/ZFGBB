package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class UserSettingsDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032827448-04:00", comments="Source field: zfgbb.user_settings.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032899206-04:00", comments="Source field: zfgbb.user_settings.theme")
    private String theme;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032920065-04:00", comments="Source field: zfgbb.user_settings.smiley_set")
    private String smileySet;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032936885-04:00", comments="Source field: zfgbb.user_settings.notify_announcements_flag")
    private Boolean notifyAnnouncementsFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032952735-04:00", comments="Source field: zfgbb.user_settings.notify_send_body_flag")
    private Boolean notifySendBodyFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032969044-04:00", comments="Source field: zfgbb.user_settings.send_happy_birthday_flag")
    private Boolean sendHappyBirthdayFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032984424-04:00", comments="Source field: zfgbb.user_settings.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.033003383-04:00", comments="Source field: zfgbb.user_settings.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.033019423-04:00", comments="Source field: zfgbb.user_settings.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032858107-04:00", comments="Source field: zfgbb.user_settings.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032886406-04:00", comments="Source field: zfgbb.user_settings.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032907806-04:00", comments="Source field: zfgbb.user_settings.theme")
    public String getTheme() {
        return theme;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032914486-04:00", comments="Source field: zfgbb.user_settings.theme")
    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032925595-04:00", comments="Source field: zfgbb.user_settings.smiley_set")
    public String getSmileySet() {
        return smileySet;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032931295-04:00", comments="Source field: zfgbb.user_settings.smiley_set")
    public void setSmileySet(String smileySet) {
        this.smileySet = smileySet;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032942335-04:00", comments="Source field: zfgbb.user_settings.notify_announcements_flag")
    public Boolean getNotifyAnnouncementsFlag() {
        return notifyAnnouncementsFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032947605-04:00", comments="Source field: zfgbb.user_settings.notify_announcements_flag")
    public void setNotifyAnnouncementsFlag(Boolean notifyAnnouncementsFlag) {
        this.notifyAnnouncementsFlag = notifyAnnouncementsFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032957774-04:00", comments="Source field: zfgbb.user_settings.notify_send_body_flag")
    public Boolean getNotifySendBodyFlag() {
        return notifySendBodyFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032962894-04:00", comments="Source field: zfgbb.user_settings.notify_send_body_flag")
    public void setNotifySendBodyFlag(Boolean notifySendBodyFlag) {
        this.notifySendBodyFlag = notifySendBodyFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032974194-04:00", comments="Source field: zfgbb.user_settings.send_happy_birthday_flag")
    public Boolean getSendHappyBirthdayFlag() {
        return sendHappyBirthdayFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032979454-04:00", comments="Source field: zfgbb.user_settings.send_happy_birthday_flag")
    public void setSendHappyBirthdayFlag(Boolean sendHappyBirthdayFlag) {
        this.sendHappyBirthdayFlag = sendHappyBirthdayFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032989604-04:00", comments="Source field: zfgbb.user_settings.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032995193-04:00", comments="Source field: zfgbb.user_settings.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.033009333-04:00", comments="Source field: zfgbb.user_settings.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.033014473-04:00", comments="Source field: zfgbb.user_settings.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.033024783-04:00", comments="Source field: zfgbb.user_settings.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.033029782-04:00", comments="Source field: zfgbb.user_settings.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return userId;
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