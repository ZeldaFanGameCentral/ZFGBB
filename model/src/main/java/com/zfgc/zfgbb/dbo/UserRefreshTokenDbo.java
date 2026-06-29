package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class UserRefreshTokenDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.443877581-04:00", comments="Source field: zfgbb.user_refresh_token.user_refresh_token_id")
    private Integer userRefreshTokenId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.44391259-04:00", comments="Source field: zfgbb.user_refresh_token.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.443942329-04:00", comments="Source field: zfgbb.user_refresh_token.token_hash")
    private String tokenHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.443977528-04:00", comments="Source field: zfgbb.user_refresh_token.issued_ts")
    private OffsetDateTime issuedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.444006587-04:00", comments="Source field: zfgbb.user_refresh_token.expires_ts")
    private OffsetDateTime expiresTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.444042966-04:00", comments="Source field: zfgbb.user_refresh_token.revoked_flag")
    private Boolean revokedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.444071525-04:00", comments="Source field: zfgbb.user_refresh_token.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.444100834-04:00", comments="Source field: zfgbb.user_refresh_token.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.444136593-04:00", comments="Source field: zfgbb.user_refresh_token.rotated_ts")
    private OffsetDateTime rotatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.444184901-04:00", comments="Source field: zfgbb.user_refresh_token.family_id")
    private String familyId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.44421765-04:00", comments="Source field: zfgbb.user_refresh_token.successor_id")
    private Integer successorId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.443892291-04:00", comments="Source field: zfgbb.user_refresh_token.user_refresh_token_id")
    public Integer getUserRefreshTokenId() {
        return userRefreshTokenId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.4439037-04:00", comments="Source field: zfgbb.user_refresh_token.user_refresh_token_id")
    public void setUserRefreshTokenId(Integer userRefreshTokenId) {
        this.userRefreshTokenId = userRefreshTokenId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.44392302-04:00", comments="Source field: zfgbb.user_refresh_token.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.443934189-04:00", comments="Source field: zfgbb.user_refresh_token.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.443951959-04:00", comments="Source field: zfgbb.user_refresh_token.token_hash")
    public String getTokenHash() {
        return tokenHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.443968928-04:00", comments="Source field: zfgbb.user_refresh_token.token_hash")
    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.443989027-04:00", comments="Source field: zfgbb.user_refresh_token.issued_ts")
    public OffsetDateTime getIssuedTs() {
        return issuedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.443998887-04:00", comments="Source field: zfgbb.user_refresh_token.issued_ts")
    public void setIssuedTs(OffsetDateTime issuedTs) {
        this.issuedTs = issuedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.444017207-04:00", comments="Source field: zfgbb.user_refresh_token.expires_ts")
    public OffsetDateTime getExpiresTs() {
        return expiresTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.444026866-04:00", comments="Source field: zfgbb.user_refresh_token.expires_ts")
    public void setExpiresTs(OffsetDateTime expiresTs) {
        this.expiresTs = expiresTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.444053725-04:00", comments="Source field: zfgbb.user_refresh_token.revoked_flag")
    public Boolean getRevokedFlag() {
        return revokedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.444063635-04:00", comments="Source field: zfgbb.user_refresh_token.revoked_flag")
    public void setRevokedFlag(Boolean revokedFlag) {
        this.revokedFlag = revokedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.444083134-04:00", comments="Source field: zfgbb.user_refresh_token.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.444092904-04:00", comments="Source field: zfgbb.user_refresh_token.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.444119033-04:00", comments="Source field: zfgbb.user_refresh_token.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.444128923-04:00", comments="Source field: zfgbb.user_refresh_token.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.444146732-04:00", comments="Source field: zfgbb.user_refresh_token.rotated_ts")
    public OffsetDateTime getRotatedTs() {
        return rotatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.444167482-04:00", comments="Source field: zfgbb.user_refresh_token.rotated_ts")
    public void setRotatedTs(OffsetDateTime rotatedTs) {
        this.rotatedTs = rotatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.444196531-04:00", comments="Source field: zfgbb.user_refresh_token.family_id")
    public String getFamilyId() {
        return familyId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.44420932-04:00", comments="Source field: zfgbb.user_refresh_token.family_id")
    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.44422793-04:00", comments="Source field: zfgbb.user_refresh_token.successor_id")
    public Integer getSuccessorId() {
        return successorId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-20T03:28:02.444238629-04:00", comments="Source field: zfgbb.user_refresh_token.successor_id")
    public void setSuccessorId(Integer successorId) {
        this.successorId = successorId;
    }

    @Override
    public Integer getPkId() {
        return userRefreshTokenId;
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