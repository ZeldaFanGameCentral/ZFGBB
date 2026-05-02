package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFLogPollsDb {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.665663423-04:00", comments="Source field: smf_1log_polls.id_poll")
    private Integer idPoll;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.665755841-04:00", comments="Source field: smf_1log_polls.id_member")
    private Integer idMember;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.665834018-04:00", comments="Source field: smf_1log_polls.id_choice")
    private Integer idChoice;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.665695252-04:00", comments="Source field: smf_1log_polls.id_poll")
    public Integer getIdPoll() {
        return idPoll;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.665729981-04:00", comments="Source field: smf_1log_polls.id_poll")
    public void setIdPoll(Integer idPoll) {
        this.idPoll = idPoll;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.66578491-04:00", comments="Source field: smf_1log_polls.id_member")
    public Integer getIdMember() {
        return idMember;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.665812799-04:00", comments="Source field: smf_1log_polls.id_member")
    public void setIdMember(Integer idMember) {
        this.idMember = idMember;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.665861627-04:00", comments="Source field: smf_1log_polls.id_choice")
    public Integer getIdChoice() {
        return idChoice;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.665889797-04:00", comments="Source field: smf_1log_polls.id_choice")
    public void setIdChoice(Integer idChoice) {
        this.idChoice = idChoice;
    }
}