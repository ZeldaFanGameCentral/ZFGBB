package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class PollDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240349082-04:00", comments="Source field: zfgbb.poll.poll_id")
    private Integer pollId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.24042483-04:00", comments="Source field: zfgbb.poll.poll_question")
    private String pollQuestion;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240451679-04:00", comments="Source field: zfgbb.poll.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240473148-04:00", comments="Source field: zfgbb.poll.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240499548-04:00", comments="Source field: zfgbb.poll.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240520147-04:00", comments="Source field: zfgbb.poll.voting_locked_flag")
    private Boolean votingLockedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240543436-04:00", comments="Source field: zfgbb.poll.expire_time")
    private LocalDateTime expireTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240563086-04:00", comments="Source field: zfgbb.poll.hide_results_flag")
    private Boolean hideResultsFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240582175-04:00", comments="Source field: zfgbb.poll.change_vote_flag")
    private Boolean changeVoteFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240601524-04:00", comments="Source field: zfgbb.poll.created_user_id")
    private Integer createdUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240620244-04:00", comments="Source field: zfgbb.poll.guest_vote_flag")
    private Boolean guestVoteFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240638463-04:00", comments="Source field: zfgbb.poll.guest_vote_count")
    private Integer guestVoteCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240657473-04:00", comments="Source field: zfgbb.poll.reset_poll")
    private Integer resetPoll;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240677752-04:00", comments="Source field: zfgbb.poll.max_votes")
    private Integer maxVotes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240697111-04:00", comments="Source field: zfgbb.poll.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240716341-04:00", comments="Source field: zfgbb.poll.answer_text")
    private String answerText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240399251-04:00", comments="Source field: zfgbb.poll.poll_id")
    public Integer getPollId() {
        return pollId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.24041562-04:00", comments="Source field: zfgbb.poll.poll_id")
    public void setPollId(Integer pollId) {
        this.pollId = pollId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.24043239-04:00", comments="Source field: zfgbb.poll.poll_question")
    public String getPollQuestion() {
        return pollQuestion;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240442229-04:00", comments="Source field: zfgbb.poll.poll_question")
    public void setPollQuestion(String pollQuestion) {
        this.pollQuestion = pollQuestion;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240459079-04:00", comments="Source field: zfgbb.poll.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240466589-04:00", comments="Source field: zfgbb.poll.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240485338-04:00", comments="Source field: zfgbb.poll.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240493068-04:00", comments="Source field: zfgbb.poll.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240507347-04:00", comments="Source field: zfgbb.poll.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240514077-04:00", comments="Source field: zfgbb.poll.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240527617-04:00", comments="Source field: zfgbb.poll.voting_locked_flag")
    public Boolean getVotingLockedFlag() {
        return votingLockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240536996-04:00", comments="Source field: zfgbb.poll.voting_locked_flag")
    public void setVotingLockedFlag(Boolean votingLockedFlag) {
        this.votingLockedFlag = votingLockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240550516-04:00", comments="Source field: zfgbb.poll.expire_time")
    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240557086-04:00", comments="Source field: zfgbb.poll.expire_time")
    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240569495-04:00", comments="Source field: zfgbb.poll.hide_results_flag")
    public Boolean getHideResultsFlag() {
        return hideResultsFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240576125-04:00", comments="Source field: zfgbb.poll.hide_results_flag")
    public void setHideResultsFlag(Boolean hideResultsFlag) {
        this.hideResultsFlag = hideResultsFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240589005-04:00", comments="Source field: zfgbb.poll.change_vote_flag")
    public Boolean getChangeVoteFlag() {
        return changeVoteFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240595585-04:00", comments="Source field: zfgbb.poll.change_vote_flag")
    public void setChangeVoteFlag(Boolean changeVoteFlag) {
        this.changeVoteFlag = changeVoteFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240607704-04:00", comments="Source field: zfgbb.poll.created_user_id")
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240614334-04:00", comments="Source field: zfgbb.poll.created_user_id")
    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240626344-04:00", comments="Source field: zfgbb.poll.guest_vote_flag")
    public Boolean getGuestVoteFlag() {
        return guestVoteFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240632543-04:00", comments="Source field: zfgbb.poll.guest_vote_flag")
    public void setGuestVoteFlag(Boolean guestVoteFlag) {
        this.guestVoteFlag = guestVoteFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240644443-04:00", comments="Source field: zfgbb.poll.guest_vote_count")
    public Integer getGuestVoteCount() {
        return guestVoteCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240651633-04:00", comments="Source field: zfgbb.poll.guest_vote_count")
    public void setGuestVoteCount(Integer guestVoteCount) {
        this.guestVoteCount = guestVoteCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240665062-04:00", comments="Source field: zfgbb.poll.reset_poll")
    public Integer getResetPoll() {
        return resetPoll;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240671792-04:00", comments="Source field: zfgbb.poll.reset_poll")
    public void setResetPoll(Integer resetPoll) {
        this.resetPoll = resetPoll;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240684142-04:00", comments="Source field: zfgbb.poll.max_votes")
    public Integer getMaxVotes() {
        return maxVotes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240690742-04:00", comments="Source field: zfgbb.poll.max_votes")
    public void setMaxVotes(Integer maxVotes) {
        this.maxVotes = maxVotes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240703081-04:00", comments="Source field: zfgbb.poll.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240711481-04:00", comments="Source field: zfgbb.poll.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.240722881-04:00", comments="Source field: zfgbb.poll.answer_text")
    public String getAnswerText() {
        return answerText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.24073078-04:00", comments="Source field: zfgbb.poll.answer_text")
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