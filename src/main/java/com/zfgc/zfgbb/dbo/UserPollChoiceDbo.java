package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class UserPollChoiceDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.367897329-04:00", comments="Source field: zfgbb.user_poll_choice.user_poll_choice_id")
    private Integer userPollChoiceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.367940987-04:00", comments="Source field: zfgbb.user_poll_choice.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.367977336-04:00", comments="Source field: zfgbb.user_poll_choice.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368016115-04:00", comments="Source field: zfgbb.user_poll_choice.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368046724-04:00", comments="Source field: zfgbb.user_poll_choice.poll_choice_id")
    private Integer pollChoiceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368076413-04:00", comments="Source field: zfgbb.user_poll_choice.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.367910498-04:00", comments="Source field: zfgbb.user_poll_choice.user_poll_choice_id")
    public Integer getUserPollChoiceId() {
        return userPollChoiceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.367931677-04:00", comments="Source field: zfgbb.user_poll_choice.user_poll_choice_id")
    public void setUserPollChoiceId(Integer userPollChoiceId) {
        this.userPollChoiceId = userPollChoiceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.367952907-04:00", comments="Source field: zfgbb.user_poll_choice.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.367964076-04:00", comments="Source field: zfgbb.user_poll_choice.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.367992005-04:00", comments="Source field: zfgbb.user_poll_choice.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368003455-04:00", comments="Source field: zfgbb.user_poll_choice.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368027574-04:00", comments="Source field: zfgbb.user_poll_choice.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368037434-04:00", comments="Source field: zfgbb.user_poll_choice.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368055833-04:00", comments="Source field: zfgbb.user_poll_choice.poll_choice_id")
    public Integer getPollChoiceId() {
        return pollChoiceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368066583-04:00", comments="Source field: zfgbb.user_poll_choice.poll_choice_id")
    public void setPollChoiceId(Integer pollChoiceId) {
        this.pollChoiceId = pollChoiceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368085112-04:00", comments="Source field: zfgbb.user_poll_choice.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.368095252-04:00", comments="Source field: zfgbb.user_poll_choice.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return userPollChoiceId;
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