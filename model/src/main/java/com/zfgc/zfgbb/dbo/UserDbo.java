package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class UserDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.214542421-04:00", comments="Source field: zfgbb.user.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.21460252-04:00", comments="Source field: zfgbb.user.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.214671087-04:00", comments="Source field: zfgbb.user.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.214989387-04:00", comments="Source field: zfgbb.user.sso_key")
    private String ssoKey;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215289298-04:00", comments="Source field: zfgbb.user.active_flag")
    private Boolean activeFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215376085-04:00", comments="Source field: zfgbb.user.display_name")
    private String displayName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215430113-04:00", comments="Source field: zfgbb.user.user_name")
    private String userName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215496281-04:00", comments="Source field: zfgbb.user.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215567259-04:00", comments="Source field: zfgbb.user.password_hash")
    private String passwordHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215625437-04:00", comments="Source field: zfgbb.user.password_algo")
    private String passwordAlgo;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215679975-04:00", comments="Source field: zfgbb.user.password_salt")
    private String passwordSalt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215758093-04:00", comments="Source field: zfgbb.user.locked_until_ts")
    private LocalDateTime lockedUntilTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215806201-04:00", comments="Source field: zfgbb.user.failed_login_count")
    private Integer failedLoginCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215873359-04:00", comments="Source field: zfgbb.user.password_changed_ts")
    private LocalDateTime passwordChangedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.214566651-04:00", comments="Source field: zfgbb.user.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.21458634-04:00", comments="Source field: zfgbb.user.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.214621889-04:00", comments="Source field: zfgbb.user.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.214639478-04:00", comments="Source field: zfgbb.user.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.214704846-04:00", comments="Source field: zfgbb.user.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.214815473-04:00", comments="Source field: zfgbb.user.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215106174-04:00", comments="Source field: zfgbb.user.sso_key")
    public String getSsoKey() {
        return ssoKey;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215260589-04:00", comments="Source field: zfgbb.user.sso_key")
    public void setSsoKey(String ssoKey) {
        this.ssoKey = ssoKey;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215330066-04:00", comments="Source field: zfgbb.user.active_flag")
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215358756-04:00", comments="Source field: zfgbb.user.active_flag")
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215394334-04:00", comments="Source field: zfgbb.user.display_name")
    public String getDisplayName() {
        return displayName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215414224-04:00", comments="Source field: zfgbb.user.display_name")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215452143-04:00", comments="Source field: zfgbb.user.user_name")
    public String getUserName() {
        return userName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215478852-04:00", comments="Source field: zfgbb.user.user_name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.21552063-04:00", comments="Source field: zfgbb.user.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.21554796-04:00", comments="Source field: zfgbb.user.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215584868-04:00", comments="Source field: zfgbb.user.password_hash")
    public String getPasswordHash() {
        return passwordHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215602968-04:00", comments="Source field: zfgbb.user.password_hash")
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215641487-04:00", comments="Source field: zfgbb.user.password_algo")
    public String getPasswordAlgo() {
        return passwordAlgo;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215658586-04:00", comments="Source field: zfgbb.user.password_algo")
    public void setPasswordAlgo(String passwordAlgo) {
        this.passwordAlgo = passwordAlgo;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215708605-04:00", comments="Source field: zfgbb.user.password_salt")
    public String getPasswordSalt() {
        return passwordSalt;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215726454-04:00", comments="Source field: zfgbb.user.password_salt")
    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215776462-04:00", comments="Source field: zfgbb.user.locked_until_ts")
    public LocalDateTime getLockedUntilTs() {
        return lockedUntilTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215792042-04:00", comments="Source field: zfgbb.user.locked_until_ts")
    public void setLockedUntilTs(LocalDateTime lockedUntilTs) {
        this.lockedUntilTs = lockedUntilTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215830831-04:00", comments="Source field: zfgbb.user.failed_login_count")
    public Integer getFailedLoginCount() {
        return failedLoginCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.21584973-04:00", comments="Source field: zfgbb.user.failed_login_count")
    public void setFailedLoginCount(Integer failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215907798-04:00", comments="Source field: zfgbb.user.password_changed_ts")
    public LocalDateTime getPasswordChangedTs() {
        return passwordChangedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.215943317-04:00", comments="Source field: zfgbb.user.password_changed_ts")
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