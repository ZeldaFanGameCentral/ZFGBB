package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class UserDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446191313-04:00", comments="Source field: zfgbb.user.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446262671-04:00", comments="Source field: zfgbb.user.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446320319-04:00", comments="Source field: zfgbb.user.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446387537-04:00", comments="Source field: zfgbb.user.sso_key")
    private String ssoKey;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446472314-04:00", comments="Source field: zfgbb.user.active_flag")
    private Boolean activeFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446567791-04:00", comments="Source field: zfgbb.user.display_name")
    private String displayName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446641249-04:00", comments="Source field: zfgbb.user.user_name")
    private String userName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446831983-04:00", comments="Source field: zfgbb.user.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447132153-04:00", comments="Source field: zfgbb.user.password_hash")
    private String passwordHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447190181-04:00", comments="Source field: zfgbb.user.password_algo")
    private String passwordAlgo;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447264179-04:00", comments="Source field: zfgbb.user.password_salt")
    private String passwordSalt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447330457-04:00", comments="Source field: zfgbb.user.locked_until_ts")
    private OffsetDateTime lockedUntilTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447391215-04:00", comments="Source field: zfgbb.user.failed_login_count")
    private Integer failedLoginCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447439263-04:00", comments="Source field: zfgbb.user.password_changed_ts")
    private OffsetDateTime passwordChangedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.44754408-04:00", comments="Source field: zfgbb.user.tokens_valid_after_ts")
    private OffsetDateTime tokensValidAfterTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446221982-04:00", comments="Source field: zfgbb.user.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446244542-04:00", comments="Source field: zfgbb.user.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446284-04:00", comments="Source field: zfgbb.user.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.44630355-04:00", comments="Source field: zfgbb.user.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446340319-04:00", comments="Source field: zfgbb.user.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446367788-04:00", comments="Source field: zfgbb.user.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446406547-04:00", comments="Source field: zfgbb.user.sso_key")
    public String getSsoKey() {
        return ssoKey;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446437496-04:00", comments="Source field: zfgbb.user.sso_key")
    public void setSsoKey(String ssoKey) {
        this.ssoKey = ssoKey;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446510213-04:00", comments="Source field: zfgbb.user.active_flag")
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446538862-04:00", comments="Source field: zfgbb.user.active_flag")
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.44659654-04:00", comments="Source field: zfgbb.user.display_name")
    public String getDisplayName() {
        return displayName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.44662447-04:00", comments="Source field: zfgbb.user.display_name")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446660768-04:00", comments="Source field: zfgbb.user.user_name")
    public String getUserName() {
        return userName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446699457-04:00", comments="Source field: zfgbb.user.user_name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.446999647-04:00", comments="Source field: zfgbb.user.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447108554-04:00", comments="Source field: zfgbb.user.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447152942-04:00", comments="Source field: zfgbb.user.password_hash")
    public String getPasswordHash() {
        return passwordHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447173602-04:00", comments="Source field: zfgbb.user.password_hash")
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447209991-04:00", comments="Source field: zfgbb.user.password_algo")
    public String getPasswordAlgo() {
        return passwordAlgo;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447247069-04:00", comments="Source field: zfgbb.user.password_algo")
    public void setPasswordAlgo(String passwordAlgo) {
        this.passwordAlgo = passwordAlgo;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447294448-04:00", comments="Source field: zfgbb.user.password_salt")
    public String getPasswordSalt() {
        return passwordSalt;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447314757-04:00", comments="Source field: zfgbb.user.password_salt")
    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447349306-04:00", comments="Source field: zfgbb.user.locked_until_ts")
    public OffsetDateTime getLockedUntilTs() {
        return lockedUntilTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447375445-04:00", comments="Source field: zfgbb.user.locked_until_ts")
    public void setLockedUntilTs(OffsetDateTime lockedUntilTs) {
        this.lockedUntilTs = lockedUntilTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447408374-04:00", comments="Source field: zfgbb.user.failed_login_count")
    public Integer getFailedLoginCount() {
        return failedLoginCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447425074-04:00", comments="Source field: zfgbb.user.failed_login_count")
    public void setFailedLoginCount(Integer failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447466302-04:00", comments="Source field: zfgbb.user.password_changed_ts")
    public OffsetDateTime getPasswordChangedTs() {
        return passwordChangedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447498381-04:00", comments="Source field: zfgbb.user.password_changed_ts")
    public void setPasswordChangedTs(OffsetDateTime passwordChangedTs) {
        this.passwordChangedTs = passwordChangedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447564509-04:00", comments="Source field: zfgbb.user.tokens_valid_after_ts")
    public OffsetDateTime getTokensValidAfterTs() {
        return tokensValidAfterTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.447583429-04:00", comments="Source field: zfgbb.user.tokens_valid_after_ts")
    public void setTokensValidAfterTs(OffsetDateTime tokensValidAfterTs) {
        this.tokensValidAfterTs = tokensValidAfterTs;
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