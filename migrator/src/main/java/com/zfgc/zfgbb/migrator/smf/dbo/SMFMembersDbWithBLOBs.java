package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFMembersDbWithBLOBs extends SMFMembersDb {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.67445834-04:00", comments="Source field: smf_1members.buddy_list")
    private String buddyList;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674519348-04:00", comments="Source field: smf_1members.message_labels")
    private String messageLabels;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674567387-04:00", comments="Source field: smf_1members.openid_uri")
    private String openidUri;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674610786-04:00", comments="Source field: smf_1members.signature")
    private String signature;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674639985-04:00", comments="Source field: smf_1members.ignore_boards")
    private String ignoreBoards;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.67448147-04:00", comments="Source field: smf_1members.buddy_list")
    public String getBuddyList() {
        return buddyList;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674504189-04:00", comments="Source field: smf_1members.buddy_list")
    public void setBuddyList(String buddyList) {
        this.buddyList = buddyList;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674537178-04:00", comments="Source field: smf_1members.message_labels")
    public String getMessageLabels() {
        return messageLabels;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674556337-04:00", comments="Source field: smf_1members.message_labels")
    public void setMessageLabels(String messageLabels) {
        this.messageLabels = messageLabels;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674578397-04:00", comments="Source field: smf_1members.openid_uri")
    public String getOpenidUri() {
        return openidUri;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674591926-04:00", comments="Source field: smf_1members.openid_uri")
    public void setOpenidUri(String openidUri) {
        this.openidUri = openidUri;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674622215-04:00", comments="Source field: smf_1members.signature")
    public String getSignature() {
        return signature;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674633035-04:00", comments="Source field: smf_1members.signature")
    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674648825-04:00", comments="Source field: smf_1members.ignore_boards")
    public String getIgnoreBoards() {
        return ignoreBoards;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.674674584-04:00", comments="Source field: smf_1members.ignore_boards")
    public void setIgnoreBoards(String ignoreBoards) {
        this.ignoreBoards = ignoreBoards;
    }
}