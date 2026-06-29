package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class ReactionDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701209828-04:00", comments="Source field: zfgbb.reaction.reaction_id")
    private Integer reactionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701236627-04:00", comments="Source field: zfgbb.reaction.reactable_type")
    private String reactableType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701262666-04:00", comments="Source field: zfgbb.reaction.reactable_id")
    private Integer reactableId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701281186-04:00", comments="Source field: zfgbb.reaction.reactor_user_id")
    private Integer reactorUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701310275-04:00", comments="Source field: zfgbb.reaction.reaction_type_id")
    private Integer reactionTypeId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701344804-04:00", comments="Source field: zfgbb.reaction.comment")
    private String comment;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701378343-04:00", comments="Source field: zfgbb.reaction.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701401292-04:00", comments="Source field: zfgbb.reaction.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701420841-04:00", comments="Source field: zfgbb.reaction.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701221358-04:00", comments="Source field: zfgbb.reaction.reaction_id")
    public Integer getReactionId() {
        return reactionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701229577-04:00", comments="Source field: zfgbb.reaction.reaction_id")
    public void setReactionId(Integer reactionId) {
        this.reactionId = reactionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701243537-04:00", comments="Source field: zfgbb.reaction.reactable_type")
    public String getReactableType() {
        return reactableType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701255927-04:00", comments="Source field: zfgbb.reaction.reactable_type")
    public void setReactableType(String reactableType) {
        this.reactableType = reactableType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701269406-04:00", comments="Source field: zfgbb.reaction.reactable_id")
    public Integer getReactableId() {
        return reactableId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701276416-04:00", comments="Source field: zfgbb.reaction.reactable_id")
    public void setReactableId(Integer reactableId) {
        this.reactableId = reactableId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701287526-04:00", comments="Source field: zfgbb.reaction.reactor_user_id")
    public Integer getReactorUserId() {
        return reactorUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701304855-04:00", comments="Source field: zfgbb.reaction.reactor_user_id")
    public void setReactorUserId(Integer reactorUserId) {
        this.reactorUserId = reactorUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701330224-04:00", comments="Source field: zfgbb.reaction.reaction_type_id")
    public Integer getReactionTypeId() {
        return reactionTypeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701338304-04:00", comments="Source field: zfgbb.reaction.reaction_type_id")
    public void setReactionTypeId(Integer reactionTypeId) {
        this.reactionTypeId = reactionTypeId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701351244-04:00", comments="Source field: zfgbb.reaction.comment")
    public String getComment() {
        return comment;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701360013-04:00", comments="Source field: zfgbb.reaction.comment")
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701387203-04:00", comments="Source field: zfgbb.reaction.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701394572-04:00", comments="Source field: zfgbb.reaction.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701408272-04:00", comments="Source field: zfgbb.reaction.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701414702-04:00", comments="Source field: zfgbb.reaction.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701427301-04:00", comments="Source field: zfgbb.reaction.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701435621-04:00", comments="Source field: zfgbb.reaction.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return reactionId;
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