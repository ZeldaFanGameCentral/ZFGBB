package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class UserPollChoiceDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694037481-04:00", comments="Source field: zfgbb.user_poll_choice.user_poll_choice_id")
    private Integer userPollChoiceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69406581-04:00", comments="Source field: zfgbb.user_poll_choice.user_id")
    private Integer userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69408716-04:00", comments="Source field: zfgbb.user_poll_choice.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694110599-04:00", comments="Source field: zfgbb.user_poll_choice.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694130468-04:00", comments="Source field: zfgbb.user_poll_choice.poll_choice_id")
    private Integer pollChoiceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694149568-04:00", comments="Source field: zfgbb.user_poll_choice.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694049201-04:00", comments="Source field: zfgbb.user_poll_choice.user_poll_choice_id")
    public Integer getUserPollChoiceId() {
        return userPollChoiceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.6940598-04:00", comments="Source field: zfgbb.user_poll_choice.user_poll_choice_id")
    public void setUserPollChoiceId(Integer userPollChoiceId) {
        this.userPollChoiceId = userPollChoiceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69407318-04:00", comments="Source field: zfgbb.user_poll_choice.user_id")
    public Integer getUserId() {
        return userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.6940822-04:00", comments="Source field: zfgbb.user_poll_choice.user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694096069-04:00", comments="Source field: zfgbb.user_poll_choice.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694103229-04:00", comments="Source field: zfgbb.user_poll_choice.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694117609-04:00", comments="Source field: zfgbb.user_poll_choice.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694124328-04:00", comments="Source field: zfgbb.user_poll_choice.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694136758-04:00", comments="Source field: zfgbb.user_poll_choice.poll_choice_id")
    public Integer getPollChoiceId() {
        return pollChoiceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694143538-04:00", comments="Source field: zfgbb.user_poll_choice.poll_choice_id")
    public void setPollChoiceId(Integer pollChoiceId) {
        this.pollChoiceId = pollChoiceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694155877-04:00", comments="Source field: zfgbb.user_poll_choice.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.694165907-04:00", comments="Source field: zfgbb.user_poll_choice.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return userPollChoiceId;
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