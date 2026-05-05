package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserBioInfoDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219054048-04:00", comments="Source field: zfgbb.user_bio_info.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219138526-04:00", comments="Source field: zfgbb.user_bio_info.custom_title")
    private String customTitle;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219191134-04:00", comments="Source field: zfgbb.user_bio_info.personal_text")
    private String personalText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219234753-04:00", comments="Source field: zfgbb.user_bio_info.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219275571-04:00", comments="Source field: zfgbb.user_bio_info.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219319-04:00", comments="Source field: zfgbb.user_bio_info.signature")
    private String signature;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219359619-04:00", comments="Source field: zfgbb.user_bio_info.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219401477-04:00", comments="Source field: zfgbb.user_bio_info.avatar_id")
    private Integer avatarId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219439366-04:00", comments="Source field: zfgbb.user_bio_info.date_registered")
    private LocalDate dateRegistered;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219502184-04:00", comments="Source field: zfgbb.user_bio_info.last_login")
    private LocalDateTime lastLogin;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219546033-04:00", comments="Source field: zfgbb.user_bio_info.real_name")
    private String realName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219586561-04:00", comments="Source field: zfgbb.user_bio_info.birth_date")
    private LocalDate birthDate;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.21962889-04:00", comments="Source field: zfgbb.user_bio_info.website_title")
    private String websiteTitle;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219671049-04:00", comments="Source field: zfgbb.user_bio_info.website_url")
    private String websiteUrl;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219712597-04:00", comments="Source field: zfgbb.user_bio_info.location")
    private String location;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219764666-04:00", comments="Source field: zfgbb.user_bio_info.time_format")
    private String timeFormat;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219803825-04:00", comments="Source field: zfgbb.user_bio_info.karma_good")
    private Integer karmaGood;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219842103-04:00", comments="Source field: zfgbb.user_bio_info.karma_bad")
    private Integer karmaBad;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220058086-04:00", comments="Source field: zfgbb.user_bio_info.hide_email_flag")
    private Boolean hideEmailFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220319668-04:00", comments="Source field: zfgbb.user_bio_info.hide_online_status")
    private Boolean hideOnlineStatus;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220384566-04:00", comments="Source field: zfgbb.user_bio_info.gender_id")
    private Integer genderId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220442114-04:00", comments="Source field: zfgbb.user_bio_info.preferred_timezone")
    private String preferredTimezone;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219102417-04:00", comments="Source field: zfgbb.user_bio_info.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219124586-04:00", comments="Source field: zfgbb.user_bio_info.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219153225-04:00", comments="Source field: zfgbb.user_bio_info.custom_title")
    public String getCustomTitle() {
        return customTitle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219176974-04:00", comments="Source field: zfgbb.user_bio_info.custom_title")
    public void setCustomTitle(String customTitle) {
        this.customTitle = customTitle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219205214-04:00", comments="Source field: zfgbb.user_bio_info.personal_text")
    public String getPersonalText() {
        return personalText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219222193-04:00", comments="Source field: zfgbb.user_bio_info.personal_text")
    public void setPersonalText(String personalText) {
        this.personalText = personalText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219250062-04:00", comments="Source field: zfgbb.user_bio_info.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219264072-04:00", comments="Source field: zfgbb.user_bio_info.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219294541-04:00", comments="Source field: zfgbb.user_bio_info.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.21930743-04:00", comments="Source field: zfgbb.user_bio_info.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.2193325-04:00", comments="Source field: zfgbb.user_bio_info.signature")
    public String getSignature() {
        return signature;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219347779-04:00", comments="Source field: zfgbb.user_bio_info.signature")
    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219373038-04:00", comments="Source field: zfgbb.user_bio_info.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219389278-04:00", comments="Source field: zfgbb.user_bio_info.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219415027-04:00", comments="Source field: zfgbb.user_bio_info.avatar_id")
    public Integer getAvatarId() {
        return avatarId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219428256-04:00", comments="Source field: zfgbb.user_bio_info.avatar_id")
    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219454976-04:00", comments="Source field: zfgbb.user_bio_info.date_registered")
    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219471865-04:00", comments="Source field: zfgbb.user_bio_info.date_registered")
    public void setDateRegistered(LocalDate dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219519594-04:00", comments="Source field: zfgbb.user_bio_info.last_login")
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219534663-04:00", comments="Source field: zfgbb.user_bio_info.last_login")
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219558942-04:00", comments="Source field: zfgbb.user_bio_info.real_name")
    public String getRealName() {
        return realName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219574512-04:00", comments="Source field: zfgbb.user_bio_info.real_name")
    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219601651-04:00", comments="Source field: zfgbb.user_bio_info.birth_date")
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219617041-04:00", comments="Source field: zfgbb.user_bio_info.birth_date")
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.21964235-04:00", comments="Source field: zfgbb.user_bio_info.website_title")
    public String getWebsiteTitle() {
        return websiteTitle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219658079-04:00", comments="Source field: zfgbb.user_bio_info.website_title")
    public void setWebsiteTitle(String websiteTitle) {
        this.websiteTitle = websiteTitle;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219684698-04:00", comments="Source field: zfgbb.user_bio_info.website_url")
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219700868-04:00", comments="Source field: zfgbb.user_bio_info.website_url")
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219725497-04:00", comments="Source field: zfgbb.user_bio_info.location")
    public String getLocation() {
        return location;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219751566-04:00", comments="Source field: zfgbb.user_bio_info.location")
    public void setLocation(String location) {
        this.location = location;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219778175-04:00", comments="Source field: zfgbb.user_bio_info.time_format")
    public String getTimeFormat() {
        return timeFormat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219792645-04:00", comments="Source field: zfgbb.user_bio_info.time_format")
    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219816824-04:00", comments="Source field: zfgbb.user_bio_info.karma_good")
    public Integer getKarmaGood() {
        return karmaGood;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219831044-04:00", comments="Source field: zfgbb.user_bio_info.karma_good")
    public void setKarmaGood(Integer karmaGood) {
        this.karmaGood = karmaGood;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219855243-04:00", comments="Source field: zfgbb.user_bio_info.karma_bad")
    public Integer getKarmaBad() {
        return karmaBad;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.219998008-04:00", comments="Source field: zfgbb.user_bio_info.karma_bad")
    public void setKarmaBad(Integer karmaBad) {
        this.karmaBad = karmaBad;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220202052-04:00", comments="Source field: zfgbb.user_bio_info.hide_email_flag")
    public Boolean getHideEmailFlag() {
        return hideEmailFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220296449-04:00", comments="Source field: zfgbb.user_bio_info.hide_email_flag")
    public void setHideEmailFlag(Boolean hideEmailFlag) {
        this.hideEmailFlag = hideEmailFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220343427-04:00", comments="Source field: zfgbb.user_bio_info.hide_online_status")
    public Boolean getHideOnlineStatus() {
        return hideOnlineStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220366687-04:00", comments="Source field: zfgbb.user_bio_info.hide_online_status")
    public void setHideOnlineStatus(Boolean hideOnlineStatus) {
        this.hideOnlineStatus = hideOnlineStatus;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220403975-04:00", comments="Source field: zfgbb.user_bio_info.gender_id")
    public Integer getGenderId() {
        return genderId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220424275-04:00", comments="Source field: zfgbb.user_bio_info.gender_id")
    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220473143-04:00", comments="Source field: zfgbb.user_bio_info.preferred_timezone")
    public String getPreferredTimezone() {
        return preferredTimezone;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.220497752-04:00", comments="Source field: zfgbb.user_bio_info.preferred_timezone")
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