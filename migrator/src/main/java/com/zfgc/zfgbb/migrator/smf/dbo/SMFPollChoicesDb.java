package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFPollChoicesDb extends SMFPollChoicesDbKey {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.663018552-04:00", comments="Source field: smf_1poll_choices.label")
    private String label;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.663149289-04:00", comments="Source field: smf_1poll_choices.votes")
    private Short votes;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.663056731-04:00", comments="Source field: smf_1poll_choices.label")
    public String getLabel() {
        return label;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.663119389-04:00", comments="Source field: smf_1poll_choices.label")
    public void setLabel(String label) {
        this.label = label;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.663185297-04:00", comments="Source field: smf_1poll_choices.votes")
    public Short getVotes() {
        return votes;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.663220796-04:00", comments="Source field: smf_1poll_choices.votes")
    public void setVotes(Short votes) {
        this.votes = votes;
    }
}