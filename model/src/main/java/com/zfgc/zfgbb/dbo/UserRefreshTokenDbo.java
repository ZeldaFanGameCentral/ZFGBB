package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class UserRefreshTokenDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.433774405-04:00", comments="Source field: zfgbb.user_refresh_token.user_refresh_token_id")
    private Integer userRefreshTokenId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.433813233-04:00", comments="Source field: zfgbb.user_refresh_token.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.433845552-04:00", comments="Source field: zfgbb.user_refresh_token.token_hash")
    private String tokenHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.433878621-04:00", comments="Source field: zfgbb.user_refresh_token.issued_ts")
    private LocalDateTime issuedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.43391094-04:00", comments="Source field: zfgbb.user_refresh_token.expires_ts")
    private LocalDateTime expiresTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.433940539-04:00", comments="Source field: zfgbb.user_refresh_token.revoked_flag")
    private Boolean revokedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.433968578-04:00", comments="Source field: zfgbb.user_refresh_token.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.434049526-04:00", comments="Source field: zfgbb.user_refresh_token.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.433791434-04:00", comments="Source field: zfgbb.user_refresh_token.user_refresh_token_id")
    public Integer getUserRefreshTokenId() {
        return userRefreshTokenId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.433804384-04:00", comments="Source field: zfgbb.user_refresh_token.user_refresh_token_id")
    public void setUserRefreshTokenId(Integer userRefreshTokenId) {
        this.userRefreshTokenId = userRefreshTokenId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.433824163-04:00", comments="Source field: zfgbb.user_refresh_token.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.433835483-04:00", comments="Source field: zfgbb.user_refresh_token.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.433856602-04:00", comments="Source field: zfgbb.user_refresh_token.token_hash")
    public String getTokenHash() {
        return tokenHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.433869572-04:00", comments="Source field: zfgbb.user_refresh_token.token_hash")
    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.433892381-04:00", comments="Source field: zfgbb.user_refresh_token.issued_ts")
    public LocalDateTime getIssuedTs() {
        return issuedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.433902911-04:00", comments="Source field: zfgbb.user_refresh_token.issued_ts")
    public void setIssuedTs(LocalDateTime issuedTs) {
        this.issuedTs = issuedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.43392195-04:00", comments="Source field: zfgbb.user_refresh_token.expires_ts")
    public LocalDateTime getExpiresTs() {
        return expiresTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.43393235-04:00", comments="Source field: zfgbb.user_refresh_token.expires_ts")
    public void setExpiresTs(LocalDateTime expiresTs) {
        this.expiresTs = expiresTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.433950069-04:00", comments="Source field: zfgbb.user_refresh_token.revoked_flag")
    public Boolean getRevokedFlag() {
        return revokedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.433959559-04:00", comments="Source field: zfgbb.user_refresh_token.revoked_flag")
    public void setRevokedFlag(Boolean revokedFlag) {
        this.revokedFlag = revokedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.433979178-04:00", comments="Source field: zfgbb.user_refresh_token.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.434033336-04:00", comments="Source field: zfgbb.user_refresh_token.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.434067645-04:00", comments="Source field: zfgbb.user_refresh_token.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.434079555-04:00", comments="Source field: zfgbb.user_refresh_token.updated_ts")
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