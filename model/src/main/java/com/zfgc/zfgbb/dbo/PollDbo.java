package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class PollDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44644727-04:00", comments="Source field: zfgbb.poll.poll_id")
    private Integer pollId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446568436-04:00", comments="Source field: zfgbb.poll.poll_question")
    private String pollQuestion;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446616655-04:00", comments="Source field: zfgbb.poll.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446661523-04:00", comments="Source field: zfgbb.poll.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446717241-04:00", comments="Source field: zfgbb.poll.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44677265-04:00", comments="Source field: zfgbb.poll.voting_locked_flag")
    private Boolean votingLockedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446817098-04:00", comments="Source field: zfgbb.poll.expire_time")
    private LocalDateTime expireTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446856037-04:00", comments="Source field: zfgbb.poll.hide_results_flag")
    private Boolean hideResultsFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446899095-04:00", comments="Source field: zfgbb.poll.change_vote_flag")
    private Boolean changeVoteFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446941684-04:00", comments="Source field: zfgbb.poll.created_user_id")
    private Integer createdUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446987043-04:00", comments="Source field: zfgbb.poll.guest_vote_flag")
    private Boolean guestVoteFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447045111-04:00", comments="Source field: zfgbb.poll.guest_vote_count")
    private Integer guestVoteCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447094099-04:00", comments="Source field: zfgbb.poll.reset_poll")
    private Integer resetPoll;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447137368-04:00", comments="Source field: zfgbb.poll.max_votes")
    private Integer maxVotes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447173537-04:00", comments="Source field: zfgbb.poll.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447213305-04:00", comments="Source field: zfgbb.poll.answer_text")
    private String answerText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446521228-04:00", comments="Source field: zfgbb.poll.poll_id")
    public Integer getPollId() {
        return pollId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446550737-04:00", comments="Source field: zfgbb.poll.poll_id")
    public void setPollId(Integer pollId) {
        this.pollId = pollId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446584166-04:00", comments="Source field: zfgbb.poll.poll_question")
    public String getPollQuestion() {
        return pollQuestion;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446602935-04:00", comments="Source field: zfgbb.poll.poll_question")
    public void setPollQuestion(String pollQuestion) {
        this.pollQuestion = pollQuestion;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446633544-04:00", comments="Source field: zfgbb.poll.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446648214-04:00", comments="Source field: zfgbb.poll.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446685642-04:00", comments="Source field: zfgbb.poll.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446702262-04:00", comments="Source field: zfgbb.poll.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446740441-04:00", comments="Source field: zfgbb.poll.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44675754-04:00", comments="Source field: zfgbb.poll.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446787119-04:00", comments="Source field: zfgbb.poll.voting_locked_flag")
    public Boolean getVotingLockedFlag() {
        return votingLockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446803419-04:00", comments="Source field: zfgbb.poll.voting_locked_flag")
    public void setVotingLockedFlag(Boolean votingLockedFlag) {
        this.votingLockedFlag = votingLockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446830328-04:00", comments="Source field: zfgbb.poll.expire_time")
    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446843117-04:00", comments="Source field: zfgbb.poll.expire_time")
    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446869996-04:00", comments="Source field: zfgbb.poll.hide_results_flag")
    public Boolean getHideResultsFlag() {
        return hideResultsFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446885036-04:00", comments="Source field: zfgbb.poll.hide_results_flag")
    public void setHideResultsFlag(Boolean hideResultsFlag) {
        this.hideResultsFlag = hideResultsFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446913435-04:00", comments="Source field: zfgbb.poll.change_vote_flag")
    public Boolean getChangeVoteFlag() {
        return changeVoteFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446927855-04:00", comments="Source field: zfgbb.poll.change_vote_flag")
    public void setChangeVoteFlag(Boolean changeVoteFlag) {
        this.changeVoteFlag = changeVoteFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446956854-04:00", comments="Source field: zfgbb.poll.created_user_id")
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.446972863-04:00", comments="Source field: zfgbb.poll.created_user_id")
    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447014412-04:00", comments="Source field: zfgbb.poll.guest_vote_flag")
    public Boolean getGuestVoteFlag() {
        return guestVoteFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447029941-04:00", comments="Source field: zfgbb.poll.guest_vote_flag")
    public void setGuestVoteFlag(Boolean guestVoteFlag) {
        this.guestVoteFlag = guestVoteFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44705863-04:00", comments="Source field: zfgbb.poll.guest_vote_count")
    public Integer getGuestVoteCount() {
        return guestVoteCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44707401-04:00", comments="Source field: zfgbb.poll.guest_vote_count")
    public void setGuestVoteCount(Integer guestVoteCount) {
        this.guestVoteCount = guestVoteCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447108359-04:00", comments="Source field: zfgbb.poll.reset_poll")
    public Integer getResetPoll() {
        return resetPoll;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447125498-04:00", comments="Source field: zfgbb.poll.reset_poll")
    public void setResetPoll(Integer resetPoll) {
        this.resetPoll = resetPoll;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447148988-04:00", comments="Source field: zfgbb.poll.max_votes")
    public Integer getMaxVotes() {
        return maxVotes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447161077-04:00", comments="Source field: zfgbb.poll.max_votes")
    public void setMaxVotes(Integer maxVotes) {
        this.maxVotes = maxVotes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447186106-04:00", comments="Source field: zfgbb.poll.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447201646-04:00", comments="Source field: zfgbb.poll.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447224795-04:00", comments="Source field: zfgbb.poll.answer_text")
    public String getAnswerText() {
        return answerText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.447240055-04:00", comments="Source field: zfgbb.poll.answer_text")
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