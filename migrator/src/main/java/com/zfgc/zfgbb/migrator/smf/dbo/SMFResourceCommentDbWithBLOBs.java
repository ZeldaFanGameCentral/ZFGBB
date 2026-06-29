package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFResourceCommentDbWithBLOBs extends SMFResourceCommentDb {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033712331-04:00", comments="Source field: smf_1resource_comments.body")
    private String body;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.03375334-04:00", comments="Source field: smf_1resource_comments.postIP")
    private String postip;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033720851-04:00", comments="Source field: smf_1resource_comments.body")
    public String getBody() {
        return body;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.03374411-04:00", comments="Source field: smf_1resource_comments.body")
    public void setBody(String body) {
        this.body = body;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033760369-04:00", comments="Source field: smf_1resource_comments.postIP")
    public String getPostip() {
        return postip;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.033768849-04:00", comments="Source field: smf_1resource_comments.postIP")
    public void setPostip(String postip) {
        this.postip = postip;
    }
}