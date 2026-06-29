package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class PollChoiceDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69246593-04:00", comments="Source field: zfgbb.poll_choice.poll_choice_id")
    private Integer pollChoiceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692493369-04:00", comments="Source field: zfgbb.poll_choice.poll_id")
    private Integer pollId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692513949-04:00", comments="Source field: zfgbb.poll_choice.choice_text")
    private String choiceText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692536508-04:00", comments="Source field: zfgbb.poll_choice.active_flag")
    private Boolean activeFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692556277-04:00", comments="Source field: zfgbb.poll_choice.votes")
    private Integer votes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692576147-04:00", comments="Source field: zfgbb.poll_choice.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692600266-04:00", comments="Source field: zfgbb.poll_choice.seqno")
    private Integer seqno;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692620055-04:00", comments="Source field: zfgbb.poll_choice.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692641085-04:00", comments="Source field: zfgbb.poll_choice.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69247691-04:00", comments="Source field: zfgbb.poll_choice.poll_choice_id")
    public Integer getPollChoiceId() {
        return pollChoiceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692487419-04:00", comments="Source field: zfgbb.poll_choice.poll_choice_id")
    public void setPollChoiceId(Integer pollChoiceId) {
        this.pollChoiceId = pollChoiceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692500329-04:00", comments="Source field: zfgbb.poll_choice.poll_id")
    public Integer getPollId() {
        return pollId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692507689-04:00", comments="Source field: zfgbb.poll_choice.poll_id")
    public void setPollId(Integer pollId) {
        this.pollId = pollId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692520528-04:00", comments="Source field: zfgbb.poll_choice.choice_text")
    public String getChoiceText() {
        return choiceText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692529788-04:00", comments="Source field: zfgbb.poll_choice.choice_text")
    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692543068-04:00", comments="Source field: zfgbb.poll_choice.active_flag")
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692550057-04:00", comments="Source field: zfgbb.poll_choice.active_flag")
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692562887-04:00", comments="Source field: zfgbb.poll_choice.votes")
    public Integer getVotes() {
        return votes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692570017-04:00", comments="Source field: zfgbb.poll_choice.votes")
    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692582516-04:00", comments="Source field: zfgbb.poll_choice.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692592936-04:00", comments="Source field: zfgbb.poll_choice.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692606896-04:00", comments="Source field: zfgbb.poll_choice.seqno")
    public Integer getSeqno() {
        return seqno;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692613815-04:00", comments="Source field: zfgbb.poll_choice.seqno")
    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692627305-04:00", comments="Source field: zfgbb.poll_choice.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692634475-04:00", comments="Source field: zfgbb.poll_choice.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692647844-04:00", comments="Source field: zfgbb.poll_choice.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.692654774-04:00", comments="Source field: zfgbb.poll_choice.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return pollChoiceId;
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