package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class UserAwardDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.703907644-04:00", comments="Source field: zfgbb.user_award.user_award_id")
    private Integer userAwardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.703939373-04:00", comments="Source field: zfgbb.user_award.award_id")
    private Integer awardId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.703973282-04:00", comments="Source field: zfgbb.user_award.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.703992471-04:00", comments="Source field: zfgbb.user_award.content_entity_id")
    private Integer contentEntityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704011251-04:00", comments="Source field: zfgbb.user_award.granted_by_user_id")
    private Integer grantedByUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70403403-04:00", comments="Source field: zfgbb.user_award.reason")
    private String reason;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704063509-04:00", comments="Source field: zfgbb.user_award.granted_ts")
    private OffsetDateTime grantedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704084059-04:00", comments="Source field: zfgbb.user_award.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704103258-04:00", comments="Source field: zfgbb.user_award.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704121987-04:00", comments="Source field: zfgbb.user_award.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.703918334-04:00", comments="Source field: zfgbb.user_award.user_award_id")
    public Integer getUserAwardId() {
        return userAwardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.703931683-04:00", comments="Source field: zfgbb.user_award.user_award_id")
    public void setUserAwardId(Integer userAwardId) {
        this.userAwardId = userAwardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.703959072-04:00", comments="Source field: zfgbb.user_award.award_id")
    public Integer getAwardId() {
        return awardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.703966762-04:00", comments="Source field: zfgbb.user_award.award_id")
    public void setAwardId(Integer awardId) {
        this.awardId = awardId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.703979672-04:00", comments="Source field: zfgbb.user_award.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.703986302-04:00", comments="Source field: zfgbb.user_award.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.703998781-04:00", comments="Source field: zfgbb.user_award.content_entity_id")
    public Integer getContentEntityId() {
        return contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704005281-04:00", comments="Source field: zfgbb.user_award.content_entity_id")
    public void setContentEntityId(Integer contentEntityId) {
        this.contentEntityId = contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704019671-04:00", comments="Source field: zfgbb.user_award.granted_by_user_id")
    public Integer getGrantedByUserId() {
        return grantedByUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70402584-04:00", comments="Source field: zfgbb.user_award.granted_by_user_id")
    public void setGrantedByUserId(Integer grantedByUserId) {
        this.grantedByUserId = grantedByUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.7040404-04:00", comments="Source field: zfgbb.user_award.reason")
    public String getReason() {
        return reason;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704055909-04:00", comments="Source field: zfgbb.user_award.reason")
    public void setReason(String reason) {
        this.reason = reason;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704070889-04:00", comments="Source field: zfgbb.user_award.granted_ts")
    public OffsetDateTime getGrantedTs() {
        return grantedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704077809-04:00", comments="Source field: zfgbb.user_award.granted_ts")
    public void setGrantedTs(OffsetDateTime grantedTs) {
        this.grantedTs = grantedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704090708-04:00", comments="Source field: zfgbb.user_award.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704096998-04:00", comments="Source field: zfgbb.user_award.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704109728-04:00", comments="Source field: zfgbb.user_award.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704115928-04:00", comments="Source field: zfgbb.user_award.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704128107-04:00", comments="Source field: zfgbb.user_award.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.704137157-04:00", comments="Source field: zfgbb.user_award.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return userAwardId;
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