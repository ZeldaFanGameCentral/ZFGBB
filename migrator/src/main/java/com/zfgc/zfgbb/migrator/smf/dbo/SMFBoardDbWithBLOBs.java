package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFBoardDbWithBLOBs extends SMFBoardDb {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685989475-04:00", comments="Source field: smf_1boards.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.686011395-04:00", comments="Source field: smf_1boards.redirect_target")
    private String redirectTarget;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.686031784-04:00", comments="Source field: smf_1boards.redirect_url")
    private String redirectUrl;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.685998305-04:00", comments="Source field: smf_1boards.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.686006565-04:00", comments="Source field: smf_1boards.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.686018024-04:00", comments="Source field: smf_1boards.redirect_target")
    public String getRedirectTarget() {
        return redirectTarget;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.686025844-04:00", comments="Source field: smf_1boards.redirect_target")
    public void setRedirectTarget(String redirectTarget) {
        this.redirectTarget = redirectTarget;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.686044084-04:00", comments="Source field: smf_1boards.redirect_url")
    public String getRedirectUrl() {
        return redirectUrl;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.686052213-04:00", comments="Source field: smf_1boards.redirect_url")
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}