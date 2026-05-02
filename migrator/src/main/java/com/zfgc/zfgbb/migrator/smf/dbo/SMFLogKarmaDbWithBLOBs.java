package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFLogKarmaDbWithBLOBs extends SMFLogKarmaDb {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.641643642-04:00", comments="Source field: smf_1log_karma.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.641972872-04:00", comments="Source field: smf_1log_karma.link")
    private String link;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.641728989-04:00", comments="Source field: smf_1log_karma.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.641930063-04:00", comments="Source field: smf_1log_karma.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.642011831-04:00", comments="Source field: smf_1log_karma.link")
    public String getLink() {
        return link;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.642104088-04:00", comments="Source field: smf_1log_karma.link")
    public void setLink(String link) {
        this.link = link;
    }
}