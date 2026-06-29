package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class PollDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.690809372-04:00", comments="Source field: zfgbb.poll.poll_id")
    private Integer pollId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.690879819-04:00", comments="Source field: zfgbb.poll.poll_question")
    private String pollQuestion;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.690906339-04:00", comments="Source field: zfgbb.poll.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.690928408-04:00", comments="Source field: zfgbb.poll.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.690982266-04:00", comments="Source field: zfgbb.poll.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691004796-04:00", comments="Source field: zfgbb.poll.voting_locked_flag")
    private Boolean votingLockedFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691024965-04:00", comments="Source field: zfgbb.poll.expire_time")
    private OffsetDateTime expireTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691052234-04:00", comments="Source field: zfgbb.poll.hide_results_flag")
    private Boolean hideResultsFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691073013-04:00", comments="Source field: zfgbb.poll.change_vote_flag")
    private Boolean changeVoteFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691092273-04:00", comments="Source field: zfgbb.poll.created_user_id")
    private Integer createdUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691111232-04:00", comments="Source field: zfgbb.poll.guest_vote_flag")
    private Boolean guestVoteFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691130672-04:00", comments="Source field: zfgbb.poll.guest_vote_count")
    private Integer guestVoteCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691151761-04:00", comments="Source field: zfgbb.poll.reset_poll")
    private Integer resetPoll;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69117133-04:00", comments="Source field: zfgbb.poll.max_votes")
    private Integer maxVotes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69118936-04:00", comments="Source field: zfgbb.poll.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691214299-04:00", comments="Source field: zfgbb.poll.answer_text")
    private String answerText;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69085523-04:00", comments="Source field: zfgbb.poll.poll_id")
    public Integer getPollId() {
        return pollId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69087081-04:00", comments="Source field: zfgbb.poll.poll_id")
    public void setPollId(Integer pollId) {
        this.pollId = pollId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.690887579-04:00", comments="Source field: zfgbb.poll.poll_question")
    public String getPollQuestion() {
        return pollQuestion;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.690897869-04:00", comments="Source field: zfgbb.poll.poll_question")
    public void setPollQuestion(String pollQuestion) {
        this.pollQuestion = pollQuestion;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.690913558-04:00", comments="Source field: zfgbb.poll.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.690921748-04:00", comments="Source field: zfgbb.poll.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.690960667-04:00", comments="Source field: zfgbb.poll.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.690971807-04:00", comments="Source field: zfgbb.poll.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.690990296-04:00", comments="Source field: zfgbb.poll.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.690997896-04:00", comments="Source field: zfgbb.poll.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691011385-04:00", comments="Source field: zfgbb.poll.voting_locked_flag")
    public Boolean getVotingLockedFlag() {
        return votingLockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691018555-04:00", comments="Source field: zfgbb.poll.voting_locked_flag")
    public void setVotingLockedFlag(Boolean votingLockedFlag) {
        this.votingLockedFlag = votingLockedFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691037645-04:00", comments="Source field: zfgbb.poll.expire_time")
    public OffsetDateTime getExpireTime() {
        return expireTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691045724-04:00", comments="Source field: zfgbb.poll.expire_time")
    public void setExpireTime(OffsetDateTime expireTime) {
        this.expireTime = expireTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691059664-04:00", comments="Source field: zfgbb.poll.hide_results_flag")
    public Boolean getHideResultsFlag() {
        return hideResultsFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691066534-04:00", comments="Source field: zfgbb.poll.hide_results_flag")
    public void setHideResultsFlag(Boolean hideResultsFlag) {
        this.hideResultsFlag = hideResultsFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691079433-04:00", comments="Source field: zfgbb.poll.change_vote_flag")
    public Boolean getChangeVoteFlag() {
        return changeVoteFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691086243-04:00", comments="Source field: zfgbb.poll.change_vote_flag")
    public void setChangeVoteFlag(Boolean changeVoteFlag) {
        this.changeVoteFlag = changeVoteFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691098433-04:00", comments="Source field: zfgbb.poll.created_user_id")
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691105212-04:00", comments="Source field: zfgbb.poll.created_user_id")
    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691117742-04:00", comments="Source field: zfgbb.poll.guest_vote_flag")
    public Boolean getGuestVoteFlag() {
        return guestVoteFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691124592-04:00", comments="Source field: zfgbb.poll.guest_vote_flag")
    public void setGuestVoteFlag(Boolean guestVoteFlag) {
        this.guestVoteFlag = guestVoteFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691137801-04:00", comments="Source field: zfgbb.poll.guest_vote_count")
    public Integer getGuestVoteCount() {
        return guestVoteCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691144841-04:00", comments="Source field: zfgbb.poll.guest_vote_count")
    public void setGuestVoteCount(Integer guestVoteCount) {
        this.guestVoteCount = guestVoteCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691158411-04:00", comments="Source field: zfgbb.poll.reset_poll")
    public Integer getResetPoll() {
        return resetPoll;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691165201-04:00", comments="Source field: zfgbb.poll.reset_poll")
    public void setResetPoll(Integer resetPoll) {
        this.resetPoll = resetPoll;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69117787-04:00", comments="Source field: zfgbb.poll.max_votes")
    public Integer getMaxVotes() {
        return maxVotes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69118469-04:00", comments="Source field: zfgbb.poll.max_votes")
    public void setMaxVotes(Integer maxVotes) {
        this.maxVotes = maxVotes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.69119574-04:00", comments="Source field: zfgbb.poll.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691207199-04:00", comments="Source field: zfgbb.poll.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691220869-04:00", comments="Source field: zfgbb.poll.answer_text")
    public String getAnswerText() {
        return answerText;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.691229279-04:00", comments="Source field: zfgbb.poll.answer_text")
    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @Override
    public Integer getPkId() {
        return pollId;
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