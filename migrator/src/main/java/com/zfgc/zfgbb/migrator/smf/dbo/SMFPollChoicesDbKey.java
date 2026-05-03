package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFPollChoicesDbKey {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.662791029-04:00", comments="Source field: smf_1poll_choices.id_poll")
    private Integer idPoll;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.662896656-04:00", comments="Source field: smf_1poll_choices.id_choice")
    private Integer idChoice;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.662831748-04:00", comments="Source field: smf_1poll_choices.id_poll")
    public Integer getIdPoll() {
        return idPoll;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.662867557-04:00", comments="Source field: smf_1poll_choices.id_poll")
    public void setIdPoll(Integer idPoll) {
        this.idPoll = idPoll;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.662931985-04:00", comments="Source field: smf_1poll_choices.id_choice")
    public Integer getIdChoice() {
        return idChoice;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.662965184-04:00", comments="Source field: smf_1poll_choices.id_choice")
    public void setIdChoice(Integer idChoice) {
        this.idChoice = idChoice;
    }
}