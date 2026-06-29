package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class InstallRunDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.79092014-04:00", comments="Source field: zfgbb.install_run.install_id")
    private Short installId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.790950219-04:00", comments="Source field: zfgbb.install_run.state")
    private String state;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.790972479-04:00", comments="Source field: zfgbb.install_run.last_completed_state")
    private String lastCompletedState;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.790992908-04:00", comments="Source field: zfgbb.install_run.request_version")
    private Integer requestVersion;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791014767-04:00", comments="Source field: zfgbb.install_run.request_fingerprint")
    private String requestFingerprint;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791033866-04:00", comments="Source field: zfgbb.install_run.admin_user_id")
    private Integer adminUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791145023-04:00", comments="Source field: zfgbb.install_run.content_pack")
    private String contentPack;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791163492-04:00", comments="Source field: zfgbb.install_run.provision_recycle_bin")
    private Boolean provisionRecycleBin;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791181112-04:00", comments="Source field: zfgbb.install_run.site_name")
    private String siteName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791200001-04:00", comments="Source field: zfgbb.install_run.last_error")
    private String lastError;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.7912199-04:00", comments="Source field: zfgbb.install_run.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.79123814-04:00", comments="Source field: zfgbb.install_run.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.79093379-04:00", comments="Source field: zfgbb.install_run.install_id")
    public Short getInstallId() {
        return installId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.79094416-04:00", comments="Source field: zfgbb.install_run.install_id")
    public void setInstallId(Short installId) {
        this.installId = installId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.790957169-04:00", comments="Source field: zfgbb.install_run.state")
    public String getState() {
        return state;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.790965859-04:00", comments="Source field: zfgbb.install_run.state")
    public void setState(String state) {
        this.state = state;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.790978888-04:00", comments="Source field: zfgbb.install_run.last_completed_state")
    public String getLastCompletedState() {
        return lastCompletedState;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.790986598-04:00", comments="Source field: zfgbb.install_run.last_completed_state")
    public void setLastCompletedState(String lastCompletedState) {
        this.lastCompletedState = lastCompletedState;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.790999208-04:00", comments="Source field: zfgbb.install_run.request_version")
    public Integer getRequestVersion() {
        return requestVersion;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791006467-04:00", comments="Source field: zfgbb.install_run.request_version")
    public void setRequestVersion(Integer requestVersion) {
        this.requestVersion = requestVersion;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791020787-04:00", comments="Source field: zfgbb.install_run.request_fingerprint")
    public String getRequestFingerprint() {
        return requestFingerprint;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791027877-04:00", comments="Source field: zfgbb.install_run.request_fingerprint")
    public void setRequestFingerprint(String requestFingerprint) {
        this.requestFingerprint = requestFingerprint;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791131713-04:00", comments="Source field: zfgbb.install_run.admin_user_id")
    public Integer getAdminUserId() {
        return adminUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791139073-04:00", comments="Source field: zfgbb.install_run.admin_user_id")
    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791150823-04:00", comments="Source field: zfgbb.install_run.content_pack")
    public String getContentPack() {
        return contentPack;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791157662-04:00", comments="Source field: zfgbb.install_run.content_pack")
    public void setContentPack(String contentPack) {
        this.contentPack = contentPack;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791169242-04:00", comments="Source field: zfgbb.install_run.provision_recycle_bin")
    public Boolean getProvisionRecycleBin() {
        return provisionRecycleBin;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791175382-04:00", comments="Source field: zfgbb.install_run.provision_recycle_bin")
    public void setProvisionRecycleBin(Boolean provisionRecycleBin) {
        this.provisionRecycleBin = provisionRecycleBin;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791187061-04:00", comments="Source field: zfgbb.install_run.site_name")
    public String getSiteName() {
        return siteName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791194111-04:00", comments="Source field: zfgbb.install_run.site_name")
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791205851-04:00", comments="Source field: zfgbb.install_run.last_error")
    public String getLastError() {
        return lastError;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.7912138-04:00", comments="Source field: zfgbb.install_run.last_error")
    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.79122645-04:00", comments="Source field: zfgbb.install_run.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.79123242-04:00", comments="Source field: zfgbb.install_run.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791244309-04:00", comments="Source field: zfgbb.install_run.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-21T13:12:01.791250299-04:00", comments="Source field: zfgbb.install_run.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return null;
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