package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class UserWarningDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738135549-04:00", comments="Source field: zfgbb.user_warning.user_warning_id")
    private Integer userWarningId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738154328-04:00", comments="Source field: zfgbb.user_warning.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738170748-04:00", comments="Source field: zfgbb.user_warning.issued_by_user_id")
    private Integer issuedByUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738183157-04:00", comments="Source field: zfgbb.user_warning.issued_by_name")
    private String issuedByName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738205607-04:00", comments="Source field: zfgbb.user_warning.body")
    private String body;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738221206-04:00", comments="Source field: zfgbb.user_warning.points")
    private Integer points;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738235086-04:00", comments="Source field: zfgbb.user_warning.issued_ts")
    private OffsetDateTime issuedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738249575-04:00", comments="Source field: zfgbb.user_warning.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738263565-04:00", comments="Source field: zfgbb.user_warning.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738278444-04:00", comments="Source field: zfgbb.user_warning.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738143929-04:00", comments="Source field: zfgbb.user_warning.user_warning_id")
    public Integer getUserWarningId() {
        return userWarningId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738149518-04:00", comments="Source field: zfgbb.user_warning.user_warning_id")
    public void setUserWarningId(Integer userWarningId) {
        this.userWarningId = userWarningId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738161448-04:00", comments="Source field: zfgbb.user_warning.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738166178-04:00", comments="Source field: zfgbb.user_warning.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738175258-04:00", comments="Source field: zfgbb.user_warning.issued_by_user_id")
    public Integer getIssuedByUserId() {
        return issuedByUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738180217-04:00", comments="Source field: zfgbb.user_warning.issued_by_user_id")
    public void setIssuedByUserId(Integer issuedByUserId) {
        this.issuedByUserId = issuedByUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738187907-04:00", comments="Source field: zfgbb.user_warning.issued_by_name")
    public String getIssuedByName() {
        return issuedByName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738201807-04:00", comments="Source field: zfgbb.user_warning.issued_by_name")
    public void setIssuedByName(String issuedByName) {
        this.issuedByName = issuedByName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738210497-04:00", comments="Source field: zfgbb.user_warning.body")
    public String getBody() {
        return body;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738215536-04:00", comments="Source field: zfgbb.user_warning.body")
    public void setBody(String body) {
        this.body = body;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738225956-04:00", comments="Source field: zfgbb.user_warning.points")
    public Integer getPoints() {
        return points;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738230766-04:00", comments="Source field: zfgbb.user_warning.points")
    public void setPoints(Integer points) {
        this.points = points;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738240366-04:00", comments="Source field: zfgbb.user_warning.issued_ts")
    public OffsetDateTime getIssuedTs() {
        return issuedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738245155-04:00", comments="Source field: zfgbb.user_warning.issued_ts")
    public void setIssuedTs(OffsetDateTime issuedTs) {
        this.issuedTs = issuedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738254535-04:00", comments="Source field: zfgbb.user_warning.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738260515-04:00", comments="Source field: zfgbb.user_warning.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738269745-04:00", comments="Source field: zfgbb.user_warning.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738274175-04:00", comments="Source field: zfgbb.user_warning.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738283024-04:00", comments="Source field: zfgbb.user_warning.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.738287744-04:00", comments="Source field: zfgbb.user_warning.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return userWarningId;
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