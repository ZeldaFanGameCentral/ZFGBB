package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class UserPollChoiceDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515061777-04:00", comments="Source field: zfgbb.user_poll_choice.user_poll_choice_id")
    private Integer userPollChoiceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515092966-04:00", comments="Source field: zfgbb.user_poll_choice.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515117095-04:00", comments="Source field: zfgbb.user_poll_choice.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515140054-04:00", comments="Source field: zfgbb.user_poll_choice.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515177663-04:00", comments="Source field: zfgbb.user_poll_choice.poll_choice_id")
    private Integer pollChoiceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515198462-04:00", comments="Source field: zfgbb.user_poll_choice.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515073176-04:00", comments="Source field: zfgbb.user_poll_choice.user_poll_choice_id")
    public Integer getUserPollChoiceId() {
        return userPollChoiceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515084606-04:00", comments="Source field: zfgbb.user_poll_choice.user_poll_choice_id")
    public void setUserPollChoiceId(Integer userPollChoiceId) {
        this.userPollChoiceId = userPollChoiceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515100335-04:00", comments="Source field: zfgbb.user_poll_choice.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515109145-04:00", comments="Source field: zfgbb.user_poll_choice.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515125495-04:00", comments="Source field: zfgbb.user_poll_choice.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515132794-04:00", comments="Source field: zfgbb.user_poll_choice.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515146804-04:00", comments="Source field: zfgbb.user_poll_choice.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515169563-04:00", comments="Source field: zfgbb.user_poll_choice.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515185043-04:00", comments="Source field: zfgbb.user_poll_choice.poll_choice_id")
    public Integer getPollChoiceId() {
        return pollChoiceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515192132-04:00", comments="Source field: zfgbb.user_poll_choice.poll_choice_id")
    public void setPollChoiceId(Integer pollChoiceId) {
        this.pollChoiceId = pollChoiceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515204622-04:00", comments="Source field: zfgbb.user_poll_choice.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.515214662-04:00", comments="Source field: zfgbb.user_poll_choice.migration_hash")
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