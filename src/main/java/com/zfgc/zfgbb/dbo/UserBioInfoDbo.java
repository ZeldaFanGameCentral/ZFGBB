package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserBioInfoDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349023099-04:00", comments="Source field: zfgbb.user_bio_info.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349073297-04:00", comments="Source field: zfgbb.user_bio_info.custom_title")
    private String customTitle;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349113386-04:00", comments="Source field: zfgbb.user_bio_info.personal_text")
    private String personalText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349151434-04:00", comments="Source field: zfgbb.user_bio_info.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349190203-04:00", comments="Source field: zfgbb.user_bio_info.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349225972-04:00", comments="Source field: zfgbb.user_bio_info.signature")
    private String signature;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349262821-04:00", comments="Source field: zfgbb.user_bio_info.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349300879-04:00", comments="Source field: zfgbb.user_bio_info.avatar_id")
    private Integer avatarId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349336728-04:00", comments="Source field: zfgbb.user_bio_info.date_registered")
    private LocalDate dateRegistered;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349373447-04:00", comments="Source field: zfgbb.user_bio_info.last_login")
    private LocalDateTime lastLogin;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349409296-04:00", comments="Source field: zfgbb.user_bio_info.real_name")
    private String realName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349446535-04:00", comments="Source field: zfgbb.user_bio_info.birth_date")
    private LocalDate birthDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349483473-04:00", comments="Source field: zfgbb.user_bio_info.website_title")
    private String websiteTitle;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349740625-04:00", comments="Source field: zfgbb.user_bio_info.website_url")
    private String websiteUrl;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349851691-04:00", comments="Source field: zfgbb.user_bio_info.location")
    private String location;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.34989373-04:00", comments="Source field: zfgbb.user_bio_info.time_format")
    private String timeFormat;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349934239-04:00", comments="Source field: zfgbb.user_bio_info.karma_good")
    private Integer karmaGood;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349980007-04:00", comments="Source field: zfgbb.user_bio_info.karma_bad")
    private Integer karmaBad;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350030065-04:00", comments="Source field: zfgbb.user_bio_info.hide_email_flag")
    private Boolean hideEmailFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350069114-04:00", comments="Source field: zfgbb.user_bio_info.hide_online_status")
    private Boolean hideOnlineStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350106263-04:00", comments="Source field: zfgbb.user_bio_info.gender_id")
    private Integer genderId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349044678-04:00", comments="Source field: zfgbb.user_bio_info.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349060307-04:00", comments="Source field: zfgbb.user_bio_info.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349086866-04:00", comments="Source field: zfgbb.user_bio_info.custom_title")
    public String getCustomTitle() {
        return customTitle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349102206-04:00", comments="Source field: zfgbb.user_bio_info.custom_title")
    public void setCustomTitle(String customTitle) {
        this.customTitle = customTitle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349126495-04:00", comments="Source field: zfgbb.user_bio_info.personal_text")
    public String getPersonalText() {
        return personalText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349140475-04:00", comments="Source field: zfgbb.user_bio_info.personal_text")
    public void setPersonalText(String personalText) {
        this.personalText = personalText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349165604-04:00", comments="Source field: zfgbb.user_bio_info.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349179563-04:00", comments="Source field: zfgbb.user_bio_info.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349203373-04:00", comments="Source field: zfgbb.user_bio_info.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349215682-04:00", comments="Source field: zfgbb.user_bio_info.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349238431-04:00", comments="Source field: zfgbb.user_bio_info.signature")
    public String getSignature() {
        return signature;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349252231-04:00", comments="Source field: zfgbb.user_bio_info.signature")
    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.34927546-04:00", comments="Source field: zfgbb.user_bio_info.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.34929002-04:00", comments="Source field: zfgbb.user_bio_info.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349313769-04:00", comments="Source field: zfgbb.user_bio_info.avatar_id")
    public Integer getAvatarId() {
        return avatarId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349326219-04:00", comments="Source field: zfgbb.user_bio_info.avatar_id")
    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349350528-04:00", comments="Source field: zfgbb.user_bio_info.date_registered")
    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349362967-04:00", comments="Source field: zfgbb.user_bio_info.date_registered")
    public void setDateRegistered(LocalDate dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349386637-04:00", comments="Source field: zfgbb.user_bio_info.last_login")
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349398976-04:00", comments="Source field: zfgbb.user_bio_info.last_login")
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349421635-04:00", comments="Source field: zfgbb.user_bio_info.real_name")
    public String getRealName() {
        return realName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349435645-04:00", comments="Source field: zfgbb.user_bio_info.real_name")
    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349459544-04:00", comments="Source field: zfgbb.user_bio_info.birth_date")
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349472364-04:00", comments="Source field: zfgbb.user_bio_info.birth_date")
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349496373-04:00", comments="Source field: zfgbb.user_bio_info.website_title")
    public String getWebsiteTitle() {
        return websiteTitle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349613069-04:00", comments="Source field: zfgbb.user_bio_info.website_title")
    public void setWebsiteTitle(String websiteTitle) {
        this.websiteTitle = websiteTitle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349809923-04:00", comments="Source field: zfgbb.user_bio_info.website_url")
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349836792-04:00", comments="Source field: zfgbb.user_bio_info.website_url")
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349866461-04:00", comments="Source field: zfgbb.user_bio_info.location")
    public String getLocation() {
        return location;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.34988135-04:00", comments="Source field: zfgbb.user_bio_info.location")
    public void setLocation(String location) {
        this.location = location;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349907159-04:00", comments="Source field: zfgbb.user_bio_info.time_format")
    public String getTimeFormat() {
        return timeFormat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349922979-04:00", comments="Source field: zfgbb.user_bio_info.time_format")
    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349947968-04:00", comments="Source field: zfgbb.user_bio_info.karma_good")
    public Integer getKarmaGood() {
        return karmaGood;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349960988-04:00", comments="Source field: zfgbb.user_bio_info.karma_good")
    public void setKarmaGood(Integer karmaGood) {
        this.karmaGood = karmaGood;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.349996087-04:00", comments="Source field: zfgbb.user_bio_info.karma_bad")
    public Integer getKarmaBad() {
        return karmaBad;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350015526-04:00", comments="Source field: zfgbb.user_bio_info.karma_bad")
    public void setKarmaBad(Integer karmaBad) {
        this.karmaBad = karmaBad;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350044295-04:00", comments="Source field: zfgbb.user_bio_info.hide_email_flag")
    public Boolean getHideEmailFlag() {
        return hideEmailFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350058074-04:00", comments="Source field: zfgbb.user_bio_info.hide_email_flag")
    public void setHideEmailFlag(Boolean hideEmailFlag) {
        this.hideEmailFlag = hideEmailFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350082034-04:00", comments="Source field: zfgbb.user_bio_info.hide_online_status")
    public Boolean getHideOnlineStatus() {
        return hideOnlineStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350095073-04:00", comments="Source field: zfgbb.user_bio_info.hide_online_status")
    public void setHideOnlineStatus(Boolean hideOnlineStatus) {
        this.hideOnlineStatus = hideOnlineStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350122362-04:00", comments="Source field: zfgbb.user_bio_info.gender_id")
    public Integer getGenderId() {
        return genderId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.350137902-04:00", comments="Source field: zfgbb.user_bio_info.gender_id")
    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    @Override
    public Integer getPkId() {
        return userId;
    }

    @Override
    public LocalDateTime getCreatedTime() {
        return createdTs;
    }

    @Override
    public LocalDateTime getUpdatedTime() {
        return updatedTs;
    }
}