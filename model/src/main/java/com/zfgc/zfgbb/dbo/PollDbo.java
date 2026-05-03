package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class PollDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512182882-04:00", comments="Source field: zfgbb.poll.poll_id")
    private Integer pollId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512256509-04:00", comments="Source field: zfgbb.poll.poll_question")
    private String pollQuestion;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512281689-04:00", comments="Source field: zfgbb.poll.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512302348-04:00", comments="Source field: zfgbb.poll.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512329337-04:00", comments="Source field: zfgbb.poll.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512349216-04:00", comments="Source field: zfgbb.poll.voting_locked_flag")
    private Boolean votingLockedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512369906-04:00", comments="Source field: zfgbb.poll.expire_time")
    private LocalDateTime expireTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512401185-04:00", comments="Source field: zfgbb.poll.hide_results_flag")
    private Boolean hideResultsFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512425544-04:00", comments="Source field: zfgbb.poll.change_vote_flag")
    private Boolean changeVoteFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512443863-04:00", comments="Source field: zfgbb.poll.created_user_id")
    private Integer createdUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512476022-04:00", comments="Source field: zfgbb.poll.guest_vote_flag")
    private Boolean guestVoteFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512499021-04:00", comments="Source field: zfgbb.poll.guest_vote_count")
    private Integer guestVoteCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512519231-04:00", comments="Source field: zfgbb.poll.reset_poll")
    private Integer resetPoll;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512539-04:00", comments="Source field: zfgbb.poll.max_votes")
    private Integer maxVotes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512557959-04:00", comments="Source field: zfgbb.poll.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512579059-04:00", comments="Source field: zfgbb.poll.answer_text")
    private String answerText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.51223117-04:00", comments="Source field: zfgbb.poll.poll_id")
    public Integer getPollId() {
        return pollId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.51224712-04:00", comments="Source field: zfgbb.poll.poll_id")
    public void setPollId(Integer pollId) {
        this.pollId = pollId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512264389-04:00", comments="Source field: zfgbb.poll.poll_question")
    public String getPollQuestion() {
        return pollQuestion;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512274249-04:00", comments="Source field: zfgbb.poll.poll_question")
    public void setPollQuestion(String pollQuestion) {
        this.pollQuestion = pollQuestion;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512288288-04:00", comments="Source field: zfgbb.poll.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512295488-04:00", comments="Source field: zfgbb.poll.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512313908-04:00", comments="Source field: zfgbb.poll.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512322367-04:00", comments="Source field: zfgbb.poll.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512336147-04:00", comments="Source field: zfgbb.poll.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512342887-04:00", comments="Source field: zfgbb.poll.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512355416-04:00", comments="Source field: zfgbb.poll.voting_locked_flag")
    public Boolean getVotingLockedFlag() {
        return votingLockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512363076-04:00", comments="Source field: zfgbb.poll.voting_locked_flag")
    public void setVotingLockedFlag(Boolean votingLockedFlag) {
        this.votingLockedFlag = votingLockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512385995-04:00", comments="Source field: zfgbb.poll.expire_time")
    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512393655-04:00", comments="Source field: zfgbb.poll.expire_time")
    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512411064-04:00", comments="Source field: zfgbb.poll.hide_results_flag")
    public Boolean getHideResultsFlag() {
        return hideResultsFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512418734-04:00", comments="Source field: zfgbb.poll.hide_results_flag")
    public void setHideResultsFlag(Boolean hideResultsFlag) {
        this.hideResultsFlag = hideResultsFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512432374-04:00", comments="Source field: zfgbb.poll.change_vote_flag")
    public Boolean getChangeVoteFlag() {
        return changeVoteFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512439113-04:00", comments="Source field: zfgbb.poll.change_vote_flag")
    public void setChangeVoteFlag(Boolean changeVoteFlag) {
        this.changeVoteFlag = changeVoteFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512450833-04:00", comments="Source field: zfgbb.poll.created_user_id")
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512469372-04:00", comments="Source field: zfgbb.poll.created_user_id")
    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512483672-04:00", comments="Source field: zfgbb.poll.guest_vote_flag")
    public Boolean getGuestVoteFlag() {
        return guestVoteFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512491702-04:00", comments="Source field: zfgbb.poll.guest_vote_flag")
    public void setGuestVoteFlag(Boolean guestVoteFlag) {
        this.guestVoteFlag = guestVoteFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512506241-04:00", comments="Source field: zfgbb.poll.guest_vote_count")
    public Integer getGuestVoteCount() {
        return guestVoteCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512513121-04:00", comments="Source field: zfgbb.poll.guest_vote_count")
    public void setGuestVoteCount(Integer guestVoteCount) {
        this.guestVoteCount = guestVoteCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512526011-04:00", comments="Source field: zfgbb.poll.reset_poll")
    public Integer getResetPoll() {
        return resetPoll;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.51253278-04:00", comments="Source field: zfgbb.poll.reset_poll")
    public void setResetPoll(Integer resetPoll) {
        this.resetPoll = resetPoll;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.51254531-04:00", comments="Source field: zfgbb.poll.max_votes")
    public Integer getMaxVotes() {
        return maxVotes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.51255207-04:00", comments="Source field: zfgbb.poll.max_votes")
    public void setMaxVotes(Integer maxVotes) {
        this.maxVotes = maxVotes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512563999-04:00", comments="Source field: zfgbb.poll.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512572669-04:00", comments="Source field: zfgbb.poll.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512585419-04:00", comments="Source field: zfgbb.poll.answer_text")
    public String getAnswerText() {
        return answerText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.512593318-04:00", comments="Source field: zfgbb.poll.answer_text")
    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @Override
    public Integer getPkId() {
        return pollId;
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