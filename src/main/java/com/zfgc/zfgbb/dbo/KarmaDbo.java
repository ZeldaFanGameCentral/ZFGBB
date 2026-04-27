package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class KarmaDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371525619-04:00", comments="Source field: zfgbb.karma.karma_id")
    private Integer karmaId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371546019-04:00", comments="Source field: zfgbb.karma.commenting_user_id")
    private Integer commentingUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371572478-04:00", comments="Source field: zfgbb.karma.is_positive")
    private Boolean isPositive;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371586337-04:00", comments="Source field: zfgbb.karma.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371602207-04:00", comments="Source field: zfgbb.karma.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371616176-04:00", comments="Source field: zfgbb.karma.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371630036-04:00", comments="Source field: zfgbb.karma.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371706543-04:00", comments="Source field: zfgbb.karma.migration_id")
    private String migrationId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371533779-04:00", comments="Source field: zfgbb.karma.karma_id")
    public Integer getKarmaId() {
        return karmaId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371540779-04:00", comments="Source field: zfgbb.karma.karma_id")
    public void setKarmaId(Integer karmaId) {
        this.karmaId = karmaId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371551139-04:00", comments="Source field: zfgbb.karma.commenting_user_id")
    public Integer getCommentingUserId() {
        return commentingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371566478-04:00", comments="Source field: zfgbb.karma.commenting_user_id")
    public void setCommentingUserId(Integer commentingUserId) {
        this.commentingUserId = commentingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371577288-04:00", comments="Source field: zfgbb.karma.is_positive")
    public Boolean getIsPositive() {
        return isPositive;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371582138-04:00", comments="Source field: zfgbb.karma.is_positive")
    public void setIsPositive(Boolean isPositive) {
        this.isPositive = isPositive;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371590817-04:00", comments="Source field: zfgbb.karma.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371597637-04:00", comments="Source field: zfgbb.karma.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371606997-04:00", comments="Source field: zfgbb.karma.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371611907-04:00", comments="Source field: zfgbb.karma.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371621286-04:00", comments="Source field: zfgbb.karma.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371626016-04:00", comments="Source field: zfgbb.karma.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371694944-04:00", comments="Source field: zfgbb.karma.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371701444-04:00", comments="Source field: zfgbb.karma.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371711343-04:00", comments="Source field: zfgbb.karma.migration_id")
    public String getMigrationId() {
        return migrationId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.371717783-04:00", comments="Source field: zfgbb.karma.migration_id")
    public void setMigrationId(String migrationId) {
        this.migrationId = migrationId;
    }

    @Override
    public Integer getPkId() {
        return karmaId;
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