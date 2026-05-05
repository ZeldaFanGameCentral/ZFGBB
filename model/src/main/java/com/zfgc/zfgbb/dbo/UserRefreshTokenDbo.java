package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class UserRefreshTokenDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227607367-04:00", comments="Source field: zfgbb.user_refresh_token.user_refresh_token_id")
    private Integer userRefreshTokenId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227643716-04:00", comments="Source field: zfgbb.user_refresh_token.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227683654-04:00", comments="Source field: zfgbb.user_refresh_token.token_hash")
    private String tokenHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227714083-04:00", comments="Source field: zfgbb.user_refresh_token.issued_ts")
    private LocalDateTime issuedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227762342-04:00", comments="Source field: zfgbb.user_refresh_token.expires_ts")
    private LocalDateTime expiresTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227796281-04:00", comments="Source field: zfgbb.user_refresh_token.revoked_flag")
    private Boolean revokedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.22782743-04:00", comments="Source field: zfgbb.user_refresh_token.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227907867-04:00", comments="Source field: zfgbb.user_refresh_token.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227622136-04:00", comments="Source field: zfgbb.user_refresh_token.user_refresh_token_id")
    public Integer getUserRefreshTokenId() {
        return userRefreshTokenId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227634556-04:00", comments="Source field: zfgbb.user_refresh_token.user_refresh_token_id")
    public void setUserRefreshTokenId(Integer userRefreshTokenId) {
        this.userRefreshTokenId = userRefreshTokenId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227661185-04:00", comments="Source field: zfgbb.user_refresh_token.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227675095-04:00", comments="Source field: zfgbb.user_refresh_token.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227695994-04:00", comments="Source field: zfgbb.user_refresh_token.token_hash")
    public String getTokenHash() {
        return tokenHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227708044-04:00", comments="Source field: zfgbb.user_refresh_token.token_hash")
    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227726663-04:00", comments="Source field: zfgbb.user_refresh_token.issued_ts")
    public LocalDateTime getIssuedTs() {
        return issuedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227748872-04:00", comments="Source field: zfgbb.user_refresh_token.issued_ts")
    public void setIssuedTs(LocalDateTime issuedTs) {
        this.issuedTs = issuedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227775171-04:00", comments="Source field: zfgbb.user_refresh_token.expires_ts")
    public LocalDateTime getExpiresTs() {
        return expiresTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227788581-04:00", comments="Source field: zfgbb.user_refresh_token.expires_ts")
    public void setExpiresTs(LocalDateTime expiresTs) {
        this.expiresTs = expiresTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.22780748-04:00", comments="Source field: zfgbb.user_refresh_token.revoked_flag")
    public Boolean getRevokedFlag() {
        return revokedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.22781763-04:00", comments="Source field: zfgbb.user_refresh_token.revoked_flag")
    public void setRevokedFlag(Boolean revokedFlag) {
        this.revokedFlag = revokedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227838059-04:00", comments="Source field: zfgbb.user_refresh_token.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227887738-04:00", comments="Source field: zfgbb.user_refresh_token.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227930697-04:00", comments="Source field: zfgbb.user_refresh_token.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.227950156-04:00", comments="Source field: zfgbb.user_refresh_token.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return userRefreshTokenId;
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