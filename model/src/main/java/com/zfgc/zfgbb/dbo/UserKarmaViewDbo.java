package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class UserKarmaViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.463253309-04:00", comments="Source field: zfgbb.user_karma_view.karma_id")
    private Integer karmaId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.463712044-04:00", comments="Source field: zfgbb.user_karma_view.receiving_user_id")
    private Integer receivingUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.463854669-04:00", comments="Source field: zfgbb.user_karma_view.message_id")
    private Integer messageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.463974365-04:00", comments="Source field: zfgbb.user_karma_view.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.464309294-04:00", comments="Source field: zfgbb.user_karma_view.is_positive")
    private Boolean isPositive;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.464499098-04:00", comments="Source field: zfgbb.user_karma_view.karma_given_ts")
    private LocalDateTime karmaGivenTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.464641273-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user_id")
    private Integer commentingUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.464822547-04:00", comments="Source field: zfgbb.user_karma_view.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.465060009-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user")
    private String commentingUser;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.463542219-04:00", comments="Source field: zfgbb.user_karma_view.karma_id")
    public Integer getKarmaId() {
        return karmaId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.463661355-04:00", comments="Source field: zfgbb.user_karma_view.karma_id")
    public void setKarmaId(Integer karmaId) {
        this.karmaId = karmaId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.463755422-04:00", comments="Source field: zfgbb.user_karma_view.receiving_user_id")
    public Integer getReceivingUserId() {
        return receivingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.4638129-04:00", comments="Source field: zfgbb.user_karma_view.receiving_user_id")
    public void setReceivingUserId(Integer receivingUserId) {
        this.receivingUserId = receivingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.463895388-04:00", comments="Source field: zfgbb.user_karma_view.message_id")
    public Integer getMessageId() {
        return messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.463936746-04:00", comments="Source field: zfgbb.user_karma_view.message_id")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.464014434-04:00", comments="Source field: zfgbb.user_karma_view.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.464249696-04:00", comments="Source field: zfgbb.user_karma_view.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.464388871-04:00", comments="Source field: zfgbb.user_karma_view.is_positive")
    public Boolean getIsPositive() {
        return isPositive;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.46443862-04:00", comments="Source field: zfgbb.user_karma_view.is_positive")
    public void setIsPositive(Boolean isPositive) {
        this.isPositive = isPositive;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.464547736-04:00", comments="Source field: zfgbb.user_karma_view.karma_given_ts")
    public LocalDateTime getKarmaGivenTs() {
        return karmaGivenTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.464603444-04:00", comments="Source field: zfgbb.user_karma_view.karma_given_ts")
    public void setKarmaGivenTs(LocalDateTime karmaGivenTs) {
        this.karmaGivenTs = karmaGivenTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.464705881-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user_id")
    public Integer getCommentingUserId() {
        return commentingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.464785378-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user_id")
    public void setCommentingUserId(Integer commentingUserId) {
        this.commentingUserId = commentingUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.464976412-04:00", comments="Source field: zfgbb.user_karma_view.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.46502441-04:00", comments="Source field: zfgbb.user_karma_view.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.465100998-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user")
    public String getCommentingUser() {
        return commentingUser;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T13:05:48.465190565-04:00", comments="Source field: zfgbb.user_karma_view.commenting_user")
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