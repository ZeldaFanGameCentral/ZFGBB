package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class PollChoiceDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242073398-04:00", comments="Source field: zfgbb.poll_choice.poll_choice_id")
    private Integer pollChoiceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242102477-04:00", comments="Source field: zfgbb.poll_choice.poll_id")
    private Integer pollId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242125346-04:00", comments="Source field: zfgbb.poll_choice.choice_text")
    private String choiceText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242172585-04:00", comments="Source field: zfgbb.poll_choice.active_flag")
    private Boolean activeFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242361289-04:00", comments="Source field: zfgbb.poll_choice.votes")
    private Integer votes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242387688-04:00", comments="Source field: zfgbb.poll_choice.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242411647-04:00", comments="Source field: zfgbb.poll_choice.seqno")
    private Integer seqno;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242431186-04:00", comments="Source field: zfgbb.poll_choice.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242452546-04:00", comments="Source field: zfgbb.poll_choice.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242086087-04:00", comments="Source field: zfgbb.poll_choice.poll_choice_id")
    public Integer getPollChoiceId() {
        return pollChoiceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242096607-04:00", comments="Source field: zfgbb.poll_choice.poll_choice_id")
    public void setPollChoiceId(Integer pollChoiceId) {
        this.pollChoiceId = pollChoiceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242110197-04:00", comments="Source field: zfgbb.poll_choice.poll_id")
    public Integer getPollId() {
        return pollId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242117836-04:00", comments="Source field: zfgbb.poll_choice.poll_id")
    public void setPollId(Integer pollId) {
        this.pollId = pollId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242132446-04:00", comments="Source field: zfgbb.poll_choice.choice_text")
    public String getChoiceText() {
        return choiceText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242141926-04:00", comments="Source field: zfgbb.poll_choice.choice_text")
    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242189224-04:00", comments="Source field: zfgbb.poll_choice.active_flag")
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242341859-04:00", comments="Source field: zfgbb.poll_choice.active_flag")
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242370558-04:00", comments="Source field: zfgbb.poll_choice.votes")
    public Integer getVotes() {
        return votes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242379198-04:00", comments="Source field: zfgbb.poll_choice.votes")
    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242395268-04:00", comments="Source field: zfgbb.poll_choice.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242404847-04:00", comments="Source field: zfgbb.poll_choice.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242418027-04:00", comments="Source field: zfgbb.poll_choice.seqno")
    public Integer getSeqno() {
        return seqno;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242424817-04:00", comments="Source field: zfgbb.poll_choice.seqno")
    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242438776-04:00", comments="Source field: zfgbb.poll_choice.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242446196-04:00", comments="Source field: zfgbb.poll_choice.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242459186-04:00", comments="Source field: zfgbb.poll_choice.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.242465925-04:00", comments="Source field: zfgbb.poll_choice.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return pollChoiceId;
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