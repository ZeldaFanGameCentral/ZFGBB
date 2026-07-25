package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class AccountDeletionRequestDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.account_deletion_request_id")
    private Integer accountDeletionRequestId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.mode")
    private String mode;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.status")
    private String status;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.token_sha256")
    private String tokenSha256;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.requested_ts")
    private OffsetDateTime requestedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.expires_ts")
    private OffsetDateTime expiresTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.confirmed_ts")
    private OffsetDateTime confirmedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.resend_count")
    private Integer resendCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.last_sent_ts")
    private OffsetDateTime lastSentTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.avatar_id_snapshot")
    private Integer avatarIdSnapshot;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.purge_cursor")
    private String purgeCursor;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.recorded_blob_paths")
    private String recordedBlobPaths;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.account_deletion_request_id")
    public Integer getAccountDeletionRequestId() {
        return accountDeletionRequestId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.account_deletion_request_id")
    public void setAccountDeletionRequestId(Integer accountDeletionRequestId) {
        this.accountDeletionRequestId = accountDeletionRequestId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.mode")
    public String getMode() {
        return mode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.mode")
    public void setMode(String mode) {
        this.mode = mode;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.status")
    public String getStatus() {
        return status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.status")
    public void setStatus(String status) {
        this.status = status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.token_sha256")
    public String getTokenSha256() {
        return tokenSha256;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.token_sha256")
    public void setTokenSha256(String tokenSha256) {
        this.tokenSha256 = tokenSha256;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.requested_ts")
    public OffsetDateTime getRequestedTs() {
        return requestedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.requested_ts")
    public void setRequestedTs(OffsetDateTime requestedTs) {
        this.requestedTs = requestedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.expires_ts")
    public OffsetDateTime getExpiresTs() {
        return expiresTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.expires_ts")
    public void setExpiresTs(OffsetDateTime expiresTs) {
        this.expiresTs = expiresTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.confirmed_ts")
    public OffsetDateTime getConfirmedTs() {
        return confirmedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.confirmed_ts")
    public void setConfirmedTs(OffsetDateTime confirmedTs) {
        this.confirmedTs = confirmedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.resend_count")
    public Integer getResendCount() {
        return resendCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.resend_count")
    public void setResendCount(Integer resendCount) {
        this.resendCount = resendCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.last_sent_ts")
    public OffsetDateTime getLastSentTs() {
        return lastSentTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.last_sent_ts")
    public void setLastSentTs(OffsetDateTime lastSentTs) {
        this.lastSentTs = lastSentTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.avatar_id_snapshot")
    public Integer getAvatarIdSnapshot() {
        return avatarIdSnapshot;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.avatar_id_snapshot")
    public void setAvatarIdSnapshot(Integer avatarIdSnapshot) {
        this.avatarIdSnapshot = avatarIdSnapshot;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.purge_cursor")
    public String getPurgeCursor() {
        return purgeCursor;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.purge_cursor")
    public void setPurgeCursor(String purgeCursor) {
        this.purgeCursor = purgeCursor;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.recorded_blob_paths")
    public String getRecordedBlobPaths() {
        return recordedBlobPaths;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.recorded_blob_paths")
    public void setRecordedBlobPaths(String recordedBlobPaths) {
        this.recordedBlobPaths = recordedBlobPaths;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.account_deletion_request.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    public Integer getPkId() {
        return accountDeletionRequestId;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    public OffsetDateTime getCreatedTime() {
        return createdTs;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.account_deletion_request")
    public OffsetDateTime getUpdatedTime() {
        return updatedTs;
    }
}