package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class UserKarmaViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.395351702-04:00", comments="Source field: zfgbb.user_karma_view.karma_id")
    private Integer karmaId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.395833126-04:00", comments="Source field: zfgbb.user_karma_view.receiving_user_id")
    private Integer receivingUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.395993511-04:00", comments="Source field: zfgbb.user_karma_view.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.396130947-04:00", comments="Source field: zfgbb.user_karma_view.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.396500895-04:00", comments="Source field: zfgbb.user_karma_view.is_positive")
    private Boolean isPositive;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.396720068-04:00", comments="Source field: zfgbb.user_karma_view.karma_given_ts")
    private LocalDateTime karmaGivenTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.396900062-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user_id")
    private Integer commentingUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.397079606-04:00", comments="Source field: zfgbb.user_karma_view.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.39729019-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user")
    private String commentingUser;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.395647782-04:00", comments="Source field: zfgbb.user_karma_view.karma_id")
    public Integer getKarmaId() {
        return karmaId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.395778498-04:00", comments="Source field: zfgbb.user_karma_view.karma_id")
    public void setKarmaId(Integer karmaId) {
        this.karmaId = karmaId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.395883015-04:00", comments="Source field: zfgbb.user_karma_view.receiving_user_id")
    public Integer getReceivingUserId() {
        return receivingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.395947003-04:00", comments="Source field: zfgbb.user_karma_view.receiving_user_id")
    public void setReceivingUserId(Integer receivingUserId) {
        this.receivingUserId = receivingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.39604073-04:00", comments="Source field: zfgbb.user_karma_view.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.396088408-04:00", comments="Source field: zfgbb.user_karma_view.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.396178035-04:00", comments="Source field: zfgbb.user_karma_view.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.396436687-04:00", comments="Source field: zfgbb.user_karma_view.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.396588842-04:00", comments="Source field: zfgbb.user_karma_view.is_positive")
    public Boolean getIsPositive() {
        return isPositive;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.3966416-04:00", comments="Source field: zfgbb.user_karma_view.is_positive")
    public void setIsPositive(Boolean isPositive) {
        this.isPositive = isPositive;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.396781036-04:00", comments="Source field: zfgbb.user_karma_view.karma_given_ts")
    public LocalDateTime getKarmaGivenTs() {
        return karmaGivenTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.396853784-04:00", comments="Source field: zfgbb.user_karma_view.karma_given_ts")
    public void setKarmaGivenTs(LocalDateTime karmaGivenTs) {
        this.karmaGivenTs = karmaGivenTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.39695463-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user_id")
    public Integer getCommentingUserId() {
        return commentingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.397037198-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user_id")
    public void setCommentingUserId(Integer commentingUserId) {
        this.commentingUserId = commentingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.397204442-04:00", comments="Source field: zfgbb.user_karma_view.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.397251901-04:00", comments="Source field: zfgbb.user_karma_view.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.397332628-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user")
    public String getCommentingUser() {
        return commentingUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.397413446-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user")
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