package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFResourceMainDbWithBLOBs extends SMFResourceMainDb {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716091933-04:00", comments="Source field: smf_1resources_main.title")
    private String title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716116963-04:00", comments="Source field: smf_1resources_main.votes")
    private String votes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716141962-04:00", comments="Source field: smf_1resources_main.voters")
    private String voters;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716165061-04:00", comments="Source field: smf_1resources_main.body")
    private String body;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.71618646-04:00", comments="Source field: smf_1resources_main.postIP")
    private String postip;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716100213-04:00", comments="Source field: smf_1resources_main.title")
    public String getTitle() {
        return title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716109803-04:00", comments="Source field: smf_1resources_main.title")
    public void setTitle(String title) {
        this.title = title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716124002-04:00", comments="Source field: smf_1resources_main.votes")
    public String getVotes() {
        return votes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716132712-04:00", comments="Source field: smf_1resources_main.votes")
    public void setVotes(String votes) {
        this.votes = votes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716149302-04:00", comments="Source field: smf_1resources_main.voters")
    public String getVoters() {
        return voters;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716158051-04:00", comments="Source field: smf_1resources_main.voters")
    public void setVoters(String voters) {
        this.voters = voters;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716171831-04:00", comments="Source field: smf_1resources_main.body")
    public String getBody() {
        return body;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.716180101-04:00", comments="Source field: smf_1resources_main.body")
    public void setBody(String body) {
        this.body = body;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.71619301-04:00", comments="Source field: smf_1resources_main.postIP")
    public String getPostip() {
        return postip;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.71620079-04:00", comments="Source field: smf_1resources_main.postIP")
    public void setPostip(String postip) {
        this.postip = postip;
    }
}