package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFGameCommentDbWithBLOBs extends SMFGameCommentDb {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032375155-04:00", comments="Source field: smf_1game_comments.body")
    private String body;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032399674-04:00", comments="Source field: smf_1game_comments.postIP")
    private String postip;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032383755-04:00", comments="Source field: smf_1game_comments.body")
    public String getBody() {
        return body;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032393244-04:00", comments="Source field: smf_1game_comments.body")
    public void setBody(String body) {
        this.body = body;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032406094-04:00", comments="Source field: smf_1game_comments.postIP")
    public String getPostip() {
        return postip;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032414364-04:00", comments="Source field: smf_1game_comments.postIP")
    public void setPostip(String postip) {
        this.postip = postip;
    }
}