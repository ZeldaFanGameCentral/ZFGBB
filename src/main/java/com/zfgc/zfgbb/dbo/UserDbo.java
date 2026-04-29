package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class UserDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.345774195-04:00", comments="Source field: zfgbb.user.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.345827633-04:00", comments="Source field: zfgbb.user.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.345869652-04:00", comments="Source field: zfgbb.user.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.3459467-04:00", comments="Source field: zfgbb.user.sso_key")
    private String ssoKey;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.345990978-04:00", comments="Source field: zfgbb.user.active_flag")
    private Boolean activeFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346031507-04:00", comments="Source field: zfgbb.user.display_name")
    private String displayName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346074625-04:00", comments="Source field: zfgbb.user.user_name")
    private String userName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346184132-04:00", comments="Source field: zfgbb.user.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.34622768-04:00", comments="Source field: zfgbb.user.password_hash")
    private String passwordHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346288648-04:00", comments="Source field: zfgbb.user.password_algo")
    private String passwordAlgo;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346340507-04:00", comments="Source field: zfgbb.user.password_salt")
    private String passwordSalt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346380865-04:00", comments="Source field: zfgbb.user.locked_until_ts")
    private LocalDateTime lockedUntilTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346433864-04:00", comments="Source field: zfgbb.user.failed_login_count")
    private Integer failedLoginCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346527711-04:00", comments="Source field: zfgbb.user.password_changed_ts")
    private LocalDateTime passwordChangedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.345797254-04:00", comments="Source field: zfgbb.user.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.345814894-04:00", comments="Source field: zfgbb.user.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.345843113-04:00", comments="Source field: zfgbb.user.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.345857713-04:00", comments="Source field: zfgbb.user.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.345884402-04:00", comments="Source field: zfgbb.user.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.34593171-04:00", comments="Source field: zfgbb.user.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.345961999-04:00", comments="Source field: zfgbb.user.sso_key")
    public String getSsoKey() {
        return ssoKey;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.345978889-04:00", comments="Source field: zfgbb.user.sso_key")
    public void setSsoKey(String ssoKey) {
        this.ssoKey = ssoKey;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346004918-04:00", comments="Source field: zfgbb.user.active_flag")
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346018807-04:00", comments="Source field: zfgbb.user.active_flag")
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346045536-04:00", comments="Source field: zfgbb.user.display_name")
    public String getDisplayName() {
        return displayName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346062406-04:00", comments="Source field: zfgbb.user.display_name")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346152383-04:00", comments="Source field: zfgbb.user.user_name")
    public String getUserName() {
        return userName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346171432-04:00", comments="Source field: zfgbb.user.user_name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346198951-04:00", comments="Source field: zfgbb.user.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346215441-04:00", comments="Source field: zfgbb.user.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.34624177-04:00", comments="Source field: zfgbb.user.password_hash")
    public String getPasswordHash() {
        return passwordHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346256839-04:00", comments="Source field: zfgbb.user.password_hash")
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346310088-04:00", comments="Source field: zfgbb.user.password_algo")
    public String getPasswordAlgo() {
        return passwordAlgo;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346328117-04:00", comments="Source field: zfgbb.user.password_algo")
    public void setPasswordAlgo(String passwordAlgo) {
        this.passwordAlgo = passwordAlgo;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346354086-04:00", comments="Source field: zfgbb.user.password_salt")
    public String getPasswordSalt() {
        return passwordSalt;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346369236-04:00", comments="Source field: zfgbb.user.password_salt")
    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346407065-04:00", comments="Source field: zfgbb.user.locked_until_ts")
    public LocalDateTime getLockedUntilTs() {
        return lockedUntilTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346421534-04:00", comments="Source field: zfgbb.user.locked_until_ts")
    public void setLockedUntilTs(LocalDateTime lockedUntilTs) {
        this.lockedUntilTs = lockedUntilTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346446933-04:00", comments="Source field: zfgbb.user.failed_login_count")
    public Integer getFailedLoginCount() {
        return failedLoginCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346509621-04:00", comments="Source field: zfgbb.user.failed_login_count")
    public void setFailedLoginCount(Integer failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.34656019-04:00", comments="Source field: zfgbb.user.password_changed_ts")
    public LocalDateTime getPasswordChangedTs() {
        return passwordChangedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.346584609-04:00", comments="Source field: zfgbb.user.password_changed_ts")
    public void setPasswordChangedTs(LocalDateTime passwordChangedTs) {
        this.passwordChangedTs = passwordChangedTs;
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