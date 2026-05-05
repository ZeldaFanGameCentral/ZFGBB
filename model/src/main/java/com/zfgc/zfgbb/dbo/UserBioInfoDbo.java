package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserBioInfoDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425241217-04:00", comments="Source field: zfgbb.user_bio_info.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425302805-04:00", comments="Source field: zfgbb.user_bio_info.custom_title")
    private String customTitle;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425358283-04:00", comments="Source field: zfgbb.user_bio_info.personal_text")
    private String personalText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425406362-04:00", comments="Source field: zfgbb.user_bio_info.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.42547239-04:00", comments="Source field: zfgbb.user_bio_info.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425526778-04:00", comments="Source field: zfgbb.user_bio_info.signature")
    private String signature;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425572337-04:00", comments="Source field: zfgbb.user_bio_info.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425631025-04:00", comments="Source field: zfgbb.user_bio_info.avatar_id")
    private Integer avatarId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425672203-04:00", comments="Source field: zfgbb.user_bio_info.date_registered")
    private LocalDate dateRegistered;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425718572-04:00", comments="Source field: zfgbb.user_bio_info.last_login")
    private LocalDateTime lastLogin;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.42577428-04:00", comments="Source field: zfgbb.user_bio_info.real_name")
    private String realName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425820679-04:00", comments="Source field: zfgbb.user_bio_info.birth_date")
    private LocalDate birthDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425862597-04:00", comments="Source field: zfgbb.user_bio_info.website_title")
    private String websiteTitle;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425907826-04:00", comments="Source field: zfgbb.user_bio_info.website_url")
    private String websiteUrl;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425951524-04:00", comments="Source field: zfgbb.user_bio_info.location")
    private String location;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425998933-04:00", comments="Source field: zfgbb.user_bio_info.time_format")
    private String timeFormat;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426040972-04:00", comments="Source field: zfgbb.user_bio_info.karma_good")
    private Integer karmaGood;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.4260824-04:00", comments="Source field: zfgbb.user_bio_info.karma_bad")
    private Integer karmaBad;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426200917-04:00", comments="Source field: zfgbb.user_bio_info.hide_email_flag")
    private Boolean hideEmailFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426257135-04:00", comments="Source field: zfgbb.user_bio_info.hide_online_status")
    private Boolean hideOnlineStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426301463-04:00", comments="Source field: zfgbb.user_bio_info.gender_id")
    private Integer genderId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426554805-04:00", comments="Source field: zfgbb.user_bio_info.preferred_timezone")
    private String preferredTimezone;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425270296-04:00", comments="Source field: zfgbb.user_bio_info.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425289316-04:00", comments="Source field: zfgbb.user_bio_info.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425319035-04:00", comments="Source field: zfgbb.user_bio_info.custom_title")
    public String getCustomTitle() {
        return customTitle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425339434-04:00", comments="Source field: zfgbb.user_bio_info.custom_title")
    public void setCustomTitle(String customTitle) {
        this.customTitle = customTitle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425375033-04:00", comments="Source field: zfgbb.user_bio_info.personal_text")
    public String getPersonalText() {
        return personalText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425392792-04:00", comments="Source field: zfgbb.user_bio_info.personal_text")
    public void setPersonalText(String personalText) {
        this.personalText = personalText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425422581-04:00", comments="Source field: zfgbb.user_bio_info.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.42545193-04:00", comments="Source field: zfgbb.user_bio_info.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425490849-04:00", comments="Source field: zfgbb.user_bio_info.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425511029-04:00", comments="Source field: zfgbb.user_bio_info.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425541828-04:00", comments="Source field: zfgbb.user_bio_info.signature")
    public String getSignature() {
        return signature;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425558277-04:00", comments="Source field: zfgbb.user_bio_info.signature")
    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425601916-04:00", comments="Source field: zfgbb.user_bio_info.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425619645-04:00", comments="Source field: zfgbb.user_bio_info.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425645374-04:00", comments="Source field: zfgbb.user_bio_info.avatar_id")
    public Integer getAvatarId() {
        return avatarId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425661214-04:00", comments="Source field: zfgbb.user_bio_info.avatar_id")
    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425690723-04:00", comments="Source field: zfgbb.user_bio_info.date_registered")
    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425705452-04:00", comments="Source field: zfgbb.user_bio_info.date_registered")
    public void setDateRegistered(LocalDate dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425744481-04:00", comments="Source field: zfgbb.user_bio_info.last_login")
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425761051-04:00", comments="Source field: zfgbb.user_bio_info.last_login")
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.42578962-04:00", comments="Source field: zfgbb.user_bio_info.real_name")
    public String getRealName() {
        return realName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425807469-04:00", comments="Source field: zfgbb.user_bio_info.real_name")
    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425835748-04:00", comments="Source field: zfgbb.user_bio_info.birth_date")
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425850268-04:00", comments="Source field: zfgbb.user_bio_info.birth_date")
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425876667-04:00", comments="Source field: zfgbb.user_bio_info.website_title")
    public String getWebsiteTitle() {
        return websiteTitle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425893786-04:00", comments="Source field: zfgbb.user_bio_info.website_title")
    public void setWebsiteTitle(String websiteTitle) {
        this.websiteTitle = websiteTitle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425922415-04:00", comments="Source field: zfgbb.user_bio_info.website_url")
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425937975-04:00", comments="Source field: zfgbb.user_bio_info.website_url")
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425966494-04:00", comments="Source field: zfgbb.user_bio_info.location")
    public String getLocation() {
        return location;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.425985913-04:00", comments="Source field: zfgbb.user_bio_info.location")
    public void setLocation(String location) {
        this.location = location;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426013192-04:00", comments="Source field: zfgbb.user_bio_info.time_format")
    public String getTimeFormat() {
        return timeFormat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426028532-04:00", comments="Source field: zfgbb.user_bio_info.time_format")
    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426055141-04:00", comments="Source field: zfgbb.user_bio_info.karma_good")
    public Integer getKarmaGood() {
        return karmaGood;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426069581-04:00", comments="Source field: zfgbb.user_bio_info.karma_good")
    public void setKarmaGood(Integer karmaGood) {
        this.karmaGood = karmaGood;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.42609685-04:00", comments="Source field: zfgbb.user_bio_info.karma_bad")
    public Integer getKarmaBad() {
        return karmaBad;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426182807-04:00", comments="Source field: zfgbb.user_bio_info.karma_bad")
    public void setKarmaBad(Integer karmaBad) {
        this.karmaBad = karmaBad;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426226726-04:00", comments="Source field: zfgbb.user_bio_info.hide_email_flag")
    public Boolean getHideEmailFlag() {
        return hideEmailFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426243525-04:00", comments="Source field: zfgbb.user_bio_info.hide_email_flag")
    public void setHideEmailFlag(Boolean hideEmailFlag) {
        this.hideEmailFlag = hideEmailFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426273184-04:00", comments="Source field: zfgbb.user_bio_info.hide_online_status")
    public Boolean getHideOnlineStatus() {
        return hideOnlineStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426288664-04:00", comments="Source field: zfgbb.user_bio_info.hide_online_status")
    public void setHideOnlineStatus(Boolean hideOnlineStatus) {
        this.hideOnlineStatus = hideOnlineStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.42640267-04:00", comments="Source field: zfgbb.user_bio_info.gender_id")
    public Integer getGenderId() {
        return genderId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426524516-04:00", comments="Source field: zfgbb.user_bio_info.gender_id")
    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426577585-04:00", comments="Source field: zfgbb.user_bio_info.preferred_timezone")
    public String getPreferredTimezone() {
        return preferredTimezone;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.426594604-04:00", comments="Source field: zfgbb.user_bio_info.preferred_timezone")
    public void setPreferredTimezone(String preferredTimezone) {
        this.preferredTimezone = preferredTimezone;
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