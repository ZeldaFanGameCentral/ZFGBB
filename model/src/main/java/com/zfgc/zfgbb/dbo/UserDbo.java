package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class UserDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.421870915-04:00", comments="Source field: zfgbb.user.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.421931293-04:00", comments="Source field: zfgbb.user.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.421993051-04:00", comments="Source field: zfgbb.user.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422121867-04:00", comments="Source field: zfgbb.user.sso_key")
    private String ssoKey;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422306141-04:00", comments="Source field: zfgbb.user.active_flag")
    private Boolean activeFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422385858-04:00", comments="Source field: zfgbb.user.display_name")
    private String displayName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422443296-04:00", comments="Source field: zfgbb.user.user_name")
    private String userName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422500725-04:00", comments="Source field: zfgbb.user.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422566223-04:00", comments="Source field: zfgbb.user.password_hash")
    private String passwordHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.42263447-04:00", comments="Source field: zfgbb.user.password_algo")
    private String passwordAlgo;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422688859-04:00", comments="Source field: zfgbb.user.password_salt")
    private String passwordSalt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422769326-04:00", comments="Source field: zfgbb.user.locked_until_ts")
    private LocalDateTime lockedUntilTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422830684-04:00", comments="Source field: zfgbb.user.failed_login_count")
    private Integer failedLoginCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422878263-04:00", comments="Source field: zfgbb.user.password_changed_ts")
    private LocalDateTime passwordChangedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.421894664-04:00", comments="Source field: zfgbb.user.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.421913433-04:00", comments="Source field: zfgbb.user.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.421950102-04:00", comments="Source field: zfgbb.user.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.421977641-04:00", comments="Source field: zfgbb.user.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.42201245-04:00", comments="Source field: zfgbb.user.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422077408-04:00", comments="Source field: zfgbb.user.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422215954-04:00", comments="Source field: zfgbb.user.sso_key")
    public String getSsoKey() {
        return ssoKey;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422278642-04:00", comments="Source field: zfgbb.user.sso_key")
    public void setSsoKey(String ssoKey) {
        this.ssoKey = ssoKey;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.42233498-04:00", comments="Source field: zfgbb.user.active_flag")
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422361679-04:00", comments="Source field: zfgbb.user.active_flag")
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422406938-04:00", comments="Source field: zfgbb.user.display_name")
    public String getDisplayName() {
        return displayName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422426957-04:00", comments="Source field: zfgbb.user.display_name")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422464446-04:00", comments="Source field: zfgbb.user.user_name")
    public String getUserName() {
        return userName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422484835-04:00", comments="Source field: zfgbb.user.user_name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422530494-04:00", comments="Source field: zfgbb.user.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422552023-04:00", comments="Source field: zfgbb.user.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422583442-04:00", comments="Source field: zfgbb.user.password_hash")
    public String getPasswordHash() {
        return passwordHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422603951-04:00", comments="Source field: zfgbb.user.password_hash")
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.42265412-04:00", comments="Source field: zfgbb.user.password_algo")
    public String getPasswordAlgo() {
        return passwordAlgo;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422674009-04:00", comments="Source field: zfgbb.user.password_algo")
    public void setPasswordAlgo(String passwordAlgo) {
        this.passwordAlgo = passwordAlgo;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422709778-04:00", comments="Source field: zfgbb.user.password_salt")
    public String getPasswordSalt() {
        return passwordSalt;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422730897-04:00", comments="Source field: zfgbb.user.password_salt")
    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422791885-04:00", comments="Source field: zfgbb.user.locked_until_ts")
    public LocalDateTime getLockedUntilTs() {
        return lockedUntilTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422812195-04:00", comments="Source field: zfgbb.user.locked_until_ts")
    public void setLockedUntilTs(LocalDateTime lockedUntilTs) {
        this.lockedUntilTs = lockedUntilTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422849354-04:00", comments="Source field: zfgbb.user.failed_login_count")
    public Integer getFailedLoginCount() {
        return failedLoginCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422864813-04:00", comments="Source field: zfgbb.user.failed_login_count")
    public void setFailedLoginCount(Integer failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422902252-04:00", comments="Source field: zfgbb.user.password_changed_ts")
    public LocalDateTime getPasswordChangedTs() {
        return passwordChangedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.422931791-04:00", comments="Source field: zfgbb.user.password_changed_ts")
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