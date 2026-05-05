package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class KarmaDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456083182-04:00", comments="Source field: zfgbb.karma.karma_id")
    private Integer karmaId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456119011-04:00", comments="Source field: zfgbb.karma.commenting_user_id")
    private Integer commentingUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45614055-04:00", comments="Source field: zfgbb.karma.is_positive")
    private Boolean isPositive;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.4561602-04:00", comments="Source field: zfgbb.karma.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456202878-04:00", comments="Source field: zfgbb.karma.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456222978-04:00", comments="Source field: zfgbb.karma.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456242317-04:00", comments="Source field: zfgbb.karma.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456261107-04:00", comments="Source field: zfgbb.karma.migration_id")
    private String migrationId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456099752-04:00", comments="Source field: zfgbb.karma.karma_id")
    public Integer getKarmaId() {
        return karmaId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456110971-04:00", comments="Source field: zfgbb.karma.karma_id")
    public void setKarmaId(Integer karmaId) {
        this.karmaId = karmaId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456126511-04:00", comments="Source field: zfgbb.karma.commenting_user_id")
    public Integer getCommentingUserId() {
        return commentingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456133911-04:00", comments="Source field: zfgbb.karma.commenting_user_id")
    public void setCommentingUserId(Integer commentingUserId) {
        this.commentingUserId = commentingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45614708-04:00", comments="Source field: zfgbb.karma.is_positive")
    public Boolean getIsPositive() {
        return isPositive;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45615391-04:00", comments="Source field: zfgbb.karma.is_positive")
    public void setIsPositive(Boolean isPositive) {
        this.isPositive = isPositive;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.45616665-04:00", comments="Source field: zfgbb.karma.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456193799-04:00", comments="Source field: zfgbb.karma.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456210038-04:00", comments="Source field: zfgbb.karma.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456216988-04:00", comments="Source field: zfgbb.karma.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456229688-04:00", comments="Source field: zfgbb.karma.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456236247-04:00", comments="Source field: zfgbb.karma.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456248987-04:00", comments="Source field: zfgbb.karma.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456255167-04:00", comments="Source field: zfgbb.karma.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456267516-04:00", comments="Source field: zfgbb.karma.migration_id")
    public String getMigrationId() {
        return migrationId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.456275266-04:00", comments="Source field: zfgbb.karma.migration_id")
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