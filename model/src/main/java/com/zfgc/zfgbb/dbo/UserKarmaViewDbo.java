package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class UserKarmaViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.468526958-04:00", comments="Source field: zfgbb.user_karma_view.karma_id")
    private Integer karmaId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.46908702-04:00", comments="Source field: zfgbb.user_karma_view.receiving_user_id")
    private Integer receivingUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.469211457-04:00", comments="Source field: zfgbb.user_karma_view.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.469337692-04:00", comments="Source field: zfgbb.user_karma_view.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.469711431-04:00", comments="Source field: zfgbb.user_karma_view.is_positive")
    private Boolean isPositive;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.469947013-04:00", comments="Source field: zfgbb.user_karma_view.karma_given_ts")
    private LocalDateTime karmaGivenTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.470098638-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user_id")
    private Integer commentingUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.470242294-04:00", comments="Source field: zfgbb.user_karma_view.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.470382429-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user")
    private String commentingUser;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.468851228-04:00", comments="Source field: zfgbb.user_karma_view.karma_id")
    public Integer getKarmaId() {
        return karmaId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.469038922-04:00", comments="Source field: zfgbb.user_karma_view.karma_id")
    public void setKarmaId(Integer karmaId) {
        this.karmaId = karmaId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.469130809-04:00", comments="Source field: zfgbb.user_karma_view.receiving_user_id")
    public Integer getReceivingUserId() {
        return receivingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.469174518-04:00", comments="Source field: zfgbb.user_karma_view.receiving_user_id")
    public void setReceivingUserId(Integer receivingUserId) {
        this.receivingUserId = receivingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.469251535-04:00", comments="Source field: zfgbb.user_karma_view.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.469291174-04:00", comments="Source field: zfgbb.user_karma_view.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.469379351-04:00", comments="Source field: zfgbb.user_karma_view.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.469637323-04:00", comments="Source field: zfgbb.user_karma_view.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.469837457-04:00", comments="Source field: zfgbb.user_karma_view.is_positive")
    public Boolean getIsPositive() {
        return isPositive;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.469884475-04:00", comments="Source field: zfgbb.user_karma_view.is_positive")
    public void setIsPositive(Boolean isPositive) {
        this.isPositive = isPositive;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.470003521-04:00", comments="Source field: zfgbb.user_karma_view.karma_given_ts")
    public LocalDateTime getKarmaGivenTs() {
        return karmaGivenTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.47004538-04:00", comments="Source field: zfgbb.user_karma_view.karma_given_ts")
    public void setKarmaGivenTs(LocalDateTime karmaGivenTs) {
        this.karmaGivenTs = karmaGivenTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.470148887-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user_id")
    public Integer getCommentingUserId() {
        return commentingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.470207225-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user_id")
    public void setCommentingUserId(Integer commentingUserId) {
        this.commentingUserId = commentingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.470289272-04:00", comments="Source field: zfgbb.user_karma_view.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.470339011-04:00", comments="Source field: zfgbb.user_karma_view.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.470424668-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user")
    public String getCommentingUser() {
        return commentingUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-30T03:02:15.470516995-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user")
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