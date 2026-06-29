package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class AccountDeletionRequestDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463407999-04:00", comments="Source field: zfgbb.account_deletion_request.account_deletion_request_id")
    private Integer accountDeletionRequestId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463494907-04:00", comments="Source field: zfgbb.account_deletion_request.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463579034-04:00", comments="Source field: zfgbb.account_deletion_request.mode")
    private String mode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463611423-04:00", comments="Source field: zfgbb.account_deletion_request.status")
    private String status;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463639122-04:00", comments="Source field: zfgbb.account_deletion_request.token_sha256")
    private String tokenSha256;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463665671-04:00", comments="Source field: zfgbb.account_deletion_request.requested_ts")
    private OffsetDateTime requestedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.46369669-04:00", comments="Source field: zfgbb.account_deletion_request.expires_ts")
    private OffsetDateTime expiresTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463758358-04:00", comments="Source field: zfgbb.account_deletion_request.confirmed_ts")
    private OffsetDateTime confirmedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463785917-04:00", comments="Source field: zfgbb.account_deletion_request.resend_count")
    private Integer resendCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463810656-04:00", comments="Source field: zfgbb.account_deletion_request.last_sent_ts")
    private OffsetDateTime lastSentTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463835136-04:00", comments="Source field: zfgbb.account_deletion_request.avatar_id_snapshot")
    private Integer avatarIdSnapshot;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463858335-04:00", comments="Source field: zfgbb.account_deletion_request.purge_cursor")
    private String purgeCursor;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463883464-04:00", comments="Source field: zfgbb.account_deletion_request.recorded_blob_paths")
    private String recordedBlobPaths;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463923363-04:00", comments="Source field: zfgbb.account_deletion_request.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463949202-04:00", comments="Source field: zfgbb.account_deletion_request.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463448248-04:00", comments="Source field: zfgbb.account_deletion_request.account_deletion_request_id")
    public Integer getAccountDeletionRequestId() {
        return accountDeletionRequestId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463478517-04:00", comments="Source field: zfgbb.account_deletion_request.account_deletion_request_id")
    public void setAccountDeletionRequestId(Integer accountDeletionRequestId) {
        this.accountDeletionRequestId = accountDeletionRequestId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463556415-04:00", comments="Source field: zfgbb.account_deletion_request.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463569934-04:00", comments="Source field: zfgbb.account_deletion_request.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463591343-04:00", comments="Source field: zfgbb.account_deletion_request.mode")
    public String getMode() {
        return mode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463603263-04:00", comments="Source field: zfgbb.account_deletion_request.mode")
    public void setMode(String mode) {
        this.mode = mode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463620822-04:00", comments="Source field: zfgbb.account_deletion_request.status")
    public String getStatus() {
        return status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463631312-04:00", comments="Source field: zfgbb.account_deletion_request.status")
    public void setStatus(String status) {
        this.status = status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463648242-04:00", comments="Source field: zfgbb.account_deletion_request.token_sha256")
    public String getTokenSha256() {
        return tokenSha256;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463658371-04:00", comments="Source field: zfgbb.account_deletion_request.token_sha256")
    public void setTokenSha256(String tokenSha256) {
        this.tokenSha256 = tokenSha256;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463675711-04:00", comments="Source field: zfgbb.account_deletion_request.requested_ts")
    public OffsetDateTime getRequestedTs() {
        return requestedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.46368844-04:00", comments="Source field: zfgbb.account_deletion_request.requested_ts")
    public void setRequestedTs(OffsetDateTime requestedTs) {
        this.requestedTs = requestedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463737369-04:00", comments="Source field: zfgbb.account_deletion_request.expires_ts")
    public OffsetDateTime getExpiresTs() {
        return expiresTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463751998-04:00", comments="Source field: zfgbb.account_deletion_request.expires_ts")
    public void setExpiresTs(OffsetDateTime expiresTs) {
        this.expiresTs = expiresTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463769908-04:00", comments="Source field: zfgbb.account_deletion_request.confirmed_ts")
    public OffsetDateTime getConfirmedTs() {
        return confirmedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463778707-04:00", comments="Source field: zfgbb.account_deletion_request.confirmed_ts")
    public void setConfirmedTs(OffsetDateTime confirmedTs) {
        this.confirmedTs = confirmedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463794027-04:00", comments="Source field: zfgbb.account_deletion_request.resend_count")
    public Integer getResendCount() {
        return resendCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463803617-04:00", comments="Source field: zfgbb.account_deletion_request.resend_count")
    public void setResendCount(Integer resendCount) {
        this.resendCount = resendCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463819626-04:00", comments="Source field: zfgbb.account_deletion_request.last_sent_ts")
    public OffsetDateTime getLastSentTs() {
        return lastSentTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463827826-04:00", comments="Source field: zfgbb.account_deletion_request.last_sent_ts")
    public void setLastSentTs(OffsetDateTime lastSentTs) {
        this.lastSentTs = lastSentTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463843245-04:00", comments="Source field: zfgbb.account_deletion_request.avatar_id_snapshot")
    public Integer getAvatarIdSnapshot() {
        return avatarIdSnapshot;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463851405-04:00", comments="Source field: zfgbb.account_deletion_request.avatar_id_snapshot")
    public void setAvatarIdSnapshot(Integer avatarIdSnapshot) {
        this.avatarIdSnapshot = avatarIdSnapshot;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463866155-04:00", comments="Source field: zfgbb.account_deletion_request.purge_cursor")
    public String getPurgeCursor() {
        return purgeCursor;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463876294-04:00", comments="Source field: zfgbb.account_deletion_request.purge_cursor")
    public void setPurgeCursor(String purgeCursor) {
        this.purgeCursor = purgeCursor;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463905163-04:00", comments="Source field: zfgbb.account_deletion_request.recorded_blob_paths")
    public String getRecordedBlobPaths() {
        return recordedBlobPaths;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463915703-04:00", comments="Source field: zfgbb.account_deletion_request.recorded_blob_paths")
    public void setRecordedBlobPaths(String recordedBlobPaths) {
        this.recordedBlobPaths = recordedBlobPaths;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463932472-04:00", comments="Source field: zfgbb.account_deletion_request.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463943402-04:00", comments="Source field: zfgbb.account_deletion_request.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463959342-04:00", comments="Source field: zfgbb.account_deletion_request.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-17T15:39:43.463967261-04:00", comments="Source field: zfgbb.account_deletion_request.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return accountDeletionRequestId;
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