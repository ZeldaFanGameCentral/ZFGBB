package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class UserRefreshTokenDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357021076-04:00", comments="Source field: zfgbb.user_refresh_token.user_refresh_token_id")
    private Integer userRefreshTokenId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357048925-04:00", comments="Source field: zfgbb.user_refresh_token.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357068804-04:00", comments="Source field: zfgbb.user_refresh_token.token_hash")
    private String tokenHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357089374-04:00", comments="Source field: zfgbb.user_refresh_token.issued_ts")
    private LocalDateTime issuedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357117723-04:00", comments="Source field: zfgbb.user_refresh_token.expires_ts")
    private LocalDateTime expiresTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357139312-04:00", comments="Source field: zfgbb.user_refresh_token.revoked_flag")
    private Boolean revokedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357158161-04:00", comments="Source field: zfgbb.user_refresh_token.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357179301-04:00", comments="Source field: zfgbb.user_refresh_token.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357033845-04:00", comments="Source field: zfgbb.user_refresh_token.user_refresh_token_id")
    public Integer getUserRefreshTokenId() {
        return userRefreshTokenId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357042805-04:00", comments="Source field: zfgbb.user_refresh_token.user_refresh_token_id")
    public void setUserRefreshTokenId(Integer userRefreshTokenId) {
        this.userRefreshTokenId = userRefreshTokenId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357056295-04:00", comments="Source field: zfgbb.user_refresh_token.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357063564-04:00", comments="Source field: zfgbb.user_refresh_token.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357075344-04:00", comments="Source field: zfgbb.user_refresh_token.token_hash")
    public String getTokenHash() {
        return tokenHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357084014-04:00", comments="Source field: zfgbb.user_refresh_token.token_hash")
    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357104893-04:00", comments="Source field: zfgbb.user_refresh_token.issued_ts")
    public LocalDateTime getIssuedTs() {
        return issuedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357112443-04:00", comments="Source field: zfgbb.user_refresh_token.issued_ts")
    public void setIssuedTs(LocalDateTime issuedTs) {
        this.issuedTs = issuedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357125602-04:00", comments="Source field: zfgbb.user_refresh_token.expires_ts")
    public LocalDateTime getExpiresTs() {
        return expiresTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357132612-04:00", comments="Source field: zfgbb.user_refresh_token.expires_ts")
    public void setExpiresTs(LocalDateTime expiresTs) {
        this.expiresTs = expiresTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357146362-04:00", comments="Source field: zfgbb.user_refresh_token.revoked_flag")
    public Boolean getRevokedFlag() {
        return revokedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357153082-04:00", comments="Source field: zfgbb.user_refresh_token.revoked_flag")
    public void setRevokedFlag(Boolean revokedFlag) {
        this.revokedFlag = revokedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357167581-04:00", comments="Source field: zfgbb.user_refresh_token.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.357174241-04:00", comments="Source field: zfgbb.user_refresh_token.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.35718645-04:00", comments="Source field: zfgbb.user_refresh_token.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.35719285-04:00", comments="Source field: zfgbb.user_refresh_token.updated_ts")
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