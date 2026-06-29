package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class UserBioInfoDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.668791437-04:00", comments="Source field: zfgbb.user_bio_info.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.668846415-04:00", comments="Source field: zfgbb.user_bio_info.custom_title")
    private String customTitle;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.668892654-04:00", comments="Source field: zfgbb.user_bio_info.personal_text")
    private String personalText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.668938122-04:00", comments="Source field: zfgbb.user_bio_info.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669080808-04:00", comments="Source field: zfgbb.user_bio_info.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669176905-04:00", comments="Source field: zfgbb.user_bio_info.signature")
    private String signature;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669230073-04:00", comments="Source field: zfgbb.user_bio_info.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669290811-04:00", comments="Source field: zfgbb.user_bio_info.avatar_id")
    private Integer avatarId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669357889-04:00", comments="Source field: zfgbb.user_bio_info.date_registered")
    private OffsetDateTime dateRegistered;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669412208-04:00", comments="Source field: zfgbb.user_bio_info.last_login")
    private OffsetDateTime lastLogin;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669457126-04:00", comments="Source field: zfgbb.user_bio_info.real_name")
    private String realName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669516844-04:00", comments="Source field: zfgbb.user_bio_info.birth_date")
    private LocalDate birthDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669559753-04:00", comments="Source field: zfgbb.user_bio_info.website_title")
    private String websiteTitle;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669603522-04:00", comments="Source field: zfgbb.user_bio_info.website_url")
    private String websiteUrl;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.66964855-04:00", comments="Source field: zfgbb.user_bio_info.location")
    private String location;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669690949-04:00", comments="Source field: zfgbb.user_bio_info.time_format")
    private String timeFormat;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669730938-04:00", comments="Source field: zfgbb.user_bio_info.hide_email_flag")
    private Boolean hideEmailFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669769306-04:00", comments="Source field: zfgbb.user_bio_info.hide_online_status")
    private Boolean hideOnlineStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669810675-04:00", comments="Source field: zfgbb.user_bio_info.gender_id")
    private Integer genderId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669852074-04:00", comments="Source field: zfgbb.user_bio_info.preferred_timezone")
    private String preferredTimezone;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.668815136-04:00", comments="Source field: zfgbb.user_bio_info.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.668832816-04:00", comments="Source field: zfgbb.user_bio_info.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.668861795-04:00", comments="Source field: zfgbb.user_bio_info.custom_title")
    public String getCustomTitle() {
        return customTitle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.668878834-04:00", comments="Source field: zfgbb.user_bio_info.custom_title")
    public void setCustomTitle(String customTitle) {
        this.customTitle = customTitle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.668907623-04:00", comments="Source field: zfgbb.user_bio_info.personal_text")
    public String getPersonalText() {
        return personalText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.668925023-04:00", comments="Source field: zfgbb.user_bio_info.personal_text")
    public void setPersonalText(String personalText) {
        this.personalText = personalText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.668967551-04:00", comments="Source field: zfgbb.user_bio_info.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.668988501-04:00", comments="Source field: zfgbb.user_bio_info.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669145476-04:00", comments="Source field: zfgbb.user_bio_info.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669164575-04:00", comments="Source field: zfgbb.user_bio_info.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669197994-04:00", comments="Source field: zfgbb.user_bio_info.signature")
    public String getSignature() {
        return signature;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669218504-04:00", comments="Source field: zfgbb.user_bio_info.signature")
    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669251822-04:00", comments="Source field: zfgbb.user_bio_info.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669276462-04:00", comments="Source field: zfgbb.user_bio_info.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.66932013-04:00", comments="Source field: zfgbb.user_bio_info.avatar_id")
    public Integer getAvatarId() {
        return avatarId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.6693429-04:00", comments="Source field: zfgbb.user_bio_info.avatar_id")
    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669375709-04:00", comments="Source field: zfgbb.user_bio_info.date_registered")
    public OffsetDateTime getDateRegistered() {
        return dateRegistered;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669398468-04:00", comments="Source field: zfgbb.user_bio_info.date_registered")
    public void setDateRegistered(OffsetDateTime dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669429007-04:00", comments="Source field: zfgbb.user_bio_info.last_login")
    public OffsetDateTime getLastLogin() {
        return lastLogin;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669444207-04:00", comments="Source field: zfgbb.user_bio_info.last_login")
    public void setLastLogin(OffsetDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669486255-04:00", comments="Source field: zfgbb.user_bio_info.real_name")
    public String getRealName() {
        return realName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669504045-04:00", comments="Source field: zfgbb.user_bio_info.real_name")
    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669533584-04:00", comments="Source field: zfgbb.user_bio_info.birth_date")
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669547833-04:00", comments="Source field: zfgbb.user_bio_info.birth_date")
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669574152-04:00", comments="Source field: zfgbb.user_bio_info.website_title")
    public String getWebsiteTitle() {
        return websiteTitle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669590542-04:00", comments="Source field: zfgbb.user_bio_info.website_title")
    public void setWebsiteTitle(String websiteTitle) {
        this.websiteTitle = websiteTitle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669617561-04:00", comments="Source field: zfgbb.user_bio_info.website_url")
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669636601-04:00", comments="Source field: zfgbb.user_bio_info.website_url")
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.66966272-04:00", comments="Source field: zfgbb.user_bio_info.location")
    public String getLocation() {
        return location;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669678849-04:00", comments="Source field: zfgbb.user_bio_info.location")
    public void setLocation(String location) {
        this.location = location;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669704518-04:00", comments="Source field: zfgbb.user_bio_info.time_format")
    public String getTimeFormat() {
        return timeFormat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669719308-04:00", comments="Source field: zfgbb.user_bio_info.time_format")
    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669744277-04:00", comments="Source field: zfgbb.user_bio_info.hide_email_flag")
    public Boolean getHideEmailFlag() {
        return hideEmailFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669757777-04:00", comments="Source field: zfgbb.user_bio_info.hide_email_flag")
    public void setHideEmailFlag(Boolean hideEmailFlag) {
        this.hideEmailFlag = hideEmailFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669783206-04:00", comments="Source field: zfgbb.user_bio_info.hide_online_status")
    public Boolean getHideOnlineStatus() {
        return hideOnlineStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669799515-04:00", comments="Source field: zfgbb.user_bio_info.hide_online_status")
    public void setHideOnlineStatus(Boolean hideOnlineStatus) {
        this.hideOnlineStatus = hideOnlineStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669826905-04:00", comments="Source field: zfgbb.user_bio_info.gender_id")
    public Integer getGenderId() {
        return genderId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669840684-04:00", comments="Source field: zfgbb.user_bio_info.gender_id")
    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669865513-04:00", comments="Source field: zfgbb.user_bio_info.preferred_timezone")
    public String getPreferredTimezone() {
        return preferredTimezone;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.669881333-04:00", comments="Source field: zfgbb.user_bio_info.preferred_timezone")
    public void setPreferredTimezone(String preferredTimezone) {
        this.preferredTimezone = preferredTimezone;
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