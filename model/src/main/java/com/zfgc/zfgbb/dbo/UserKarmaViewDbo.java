package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class UserKarmaViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.18623665-04:00", comments="Source field: zfgbb.user_karma_view.karma_id")
    private Integer karmaId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.18684843-04:00", comments="Source field: zfgbb.user_karma_view.receiving_user_id")
    private Integer receivingUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.186998826-04:00", comments="Source field: zfgbb.user_karma_view.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.187152121-04:00", comments="Source field: zfgbb.user_karma_view.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.187518569-04:00", comments="Source field: zfgbb.user_karma_view.is_positive")
    private Boolean isPositive;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.187738342-04:00", comments="Source field: zfgbb.user_karma_view.karma_given_ts")
    private LocalDateTime karmaGivenTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.187934096-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user_id")
    private Integer commentingUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.18811851-04:00", comments="Source field: zfgbb.user_karma_view.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.188327234-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user")
    private String commentingUser;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.186661936-04:00", comments="Source field: zfgbb.user_karma_view.karma_id")
    public Integer getKarmaId() {
        return karmaId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.186796732-04:00", comments="Source field: zfgbb.user_karma_view.karma_id")
    public void setKarmaId(Integer karmaId) {
        this.karmaId = karmaId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.186893529-04:00", comments="Source field: zfgbb.user_karma_view.receiving_user_id")
    public Integer getReceivingUserId() {
        return receivingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.186956327-04:00", comments="Source field: zfgbb.user_karma_view.receiving_user_id")
    public void setReceivingUserId(Integer receivingUserId) {
        this.receivingUserId = receivingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.187064434-04:00", comments="Source field: zfgbb.user_karma_view.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.187112352-04:00", comments="Source field: zfgbb.user_karma_view.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.187211479-04:00", comments="Source field: zfgbb.user_karma_view.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.187470581-04:00", comments="Source field: zfgbb.user_karma_view.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.187563318-04:00", comments="Source field: zfgbb.user_karma_view.is_positive")
    public Boolean getIsPositive() {
        return isPositive;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.187652945-04:00", comments="Source field: zfgbb.user_karma_view.is_positive")
    public void setIsPositive(Boolean isPositive) {
        this.isPositive = isPositive;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.18780007-04:00", comments="Source field: zfgbb.user_karma_view.karma_given_ts")
    public LocalDateTime getKarmaGivenTs() {
        return karmaGivenTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.187876278-04:00", comments="Source field: zfgbb.user_karma_view.karma_given_ts")
    public void setKarmaGivenTs(LocalDateTime karmaGivenTs) {
        this.karmaGivenTs = karmaGivenTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.187977045-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user_id")
    public Integer getCommentingUserId() {
        return commentingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.188076861-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user_id")
    public void setCommentingUserId(Integer commentingUserId) {
        this.commentingUserId = commentingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.188238606-04:00", comments="Source field: zfgbb.user_karma_view.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.188287065-04:00", comments="Source field: zfgbb.user_karma_view.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.188369502-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user")
    public String getCommentingUser() {
        return commentingUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.188470269-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user")
    public void setCommentingUser(String commentingUser) {
        this.commentingUser = commentingUser;
    }

    @Override
    public Integer getPkId() {
        return null;
    }

    @Override
    public LocalDateTime getCreatedTime() {
        return null;
    }

    @Override
    public LocalDateTime getUpdatedTime() {
        return null;
    }
}