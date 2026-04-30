package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class UserKarmaViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.520523638-04:00", comments="Source field: zfgbb.user_karma_view.karma_id")
    private Integer karmaId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.520966404-04:00", comments="Source field: zfgbb.user_karma_view.receiving_user_id")
    private Integer receivingUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.521062221-04:00", comments="Source field: zfgbb.user_karma_view.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.521160388-04:00", comments="Source field: zfgbb.user_karma_view.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.521377541-04:00", comments="Source field: zfgbb.user_karma_view.is_positive")
    private Boolean isPositive;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.521484688-04:00", comments="Source field: zfgbb.user_karma_view.karma_given_ts")
    private LocalDateTime karmaGivenTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.521608964-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user_id")
    private Integer commentingUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.52171038-04:00", comments="Source field: zfgbb.user_karma_view.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.521785998-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user")
    private String commentingUser;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.520825329-04:00", comments="Source field: zfgbb.user_karma_view.karma_id")
    public Integer getKarmaId() {
        return karmaId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.520929355-04:00", comments="Source field: zfgbb.user_karma_view.karma_id")
    public void setKarmaId(Integer karmaId) {
        this.karmaId = karmaId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.520996943-04:00", comments="Source field: zfgbb.user_karma_view.receiving_user_id")
    public Integer getReceivingUserId() {
        return receivingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.521028932-04:00", comments="Source field: zfgbb.user_karma_view.receiving_user_id")
    public void setReceivingUserId(Integer receivingUserId) {
        this.receivingUserId = receivingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.52109713-04:00", comments="Source field: zfgbb.user_karma_view.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.521125919-04:00", comments="Source field: zfgbb.user_karma_view.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.521189507-04:00", comments="Source field: zfgbb.user_karma_view.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.521344062-04:00", comments="Source field: zfgbb.user_karma_view.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.52140866-04:00", comments="Source field: zfgbb.user_karma_view.is_positive")
    public Boolean getIsPositive() {
        return isPositive;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.521436469-04:00", comments="Source field: zfgbb.user_karma_view.is_positive")
    public void setIsPositive(Boolean isPositive) {
        this.isPositive = isPositive;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.521516807-04:00", comments="Source field: zfgbb.user_karma_view.karma_given_ts")
    public LocalDateTime getKarmaGivenTs() {
        return karmaGivenTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.521546096-04:00", comments="Source field: zfgbb.user_karma_view.karma_given_ts")
    public void setKarmaGivenTs(LocalDateTime karmaGivenTs) {
        this.karmaGivenTs = karmaGivenTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.521638103-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user_id")
    public Integer getCommentingUserId() {
        return commentingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.521686321-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user_id")
    public void setCommentingUserId(Integer commentingUserId) {
        this.commentingUserId = commentingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.52173709-04:00", comments="Source field: zfgbb.user_karma_view.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.521762219-04:00", comments="Source field: zfgbb.user_karma_view.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.521812167-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user")
    public String getCommentingUser() {
        return commentingUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T03:41:48.521861396-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user")
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