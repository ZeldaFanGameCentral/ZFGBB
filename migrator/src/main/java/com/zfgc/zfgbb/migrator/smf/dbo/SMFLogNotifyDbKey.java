package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFLogNotifyDbKey {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038856442-04:00", comments="Source field: smf_1log_notify.id_member")
    private Integer idMember;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038891541-04:00", comments="Source field: smf_1log_notify.id_topic")
    private Integer idTopic;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038936059-04:00", comments="Source field: smf_1log_notify.id_board")
    private Integer idBoard;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038869661-04:00", comments="Source field: smf_1log_notify.id_member")
    public Integer getIdMember() {
        return idMember;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038880981-04:00", comments="Source field: smf_1log_notify.id_member")
    public void setIdMember(Integer idMember) {
        this.idMember = idMember;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.03890257-04:00", comments="Source field: smf_1log_notify.id_topic")
    public Integer getIdTopic() {
        return idTopic;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.03891437-04:00", comments="Source field: smf_1log_notify.id_topic")
    public void setIdTopic(Integer idTopic) {
        this.idTopic = idTopic;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038947759-04:00", comments="Source field: smf_1log_notify.id_board")
    public Integer getIdBoard() {
        return idBoard;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038955349-04:00", comments="Source field: smf_1log_notify.id_board")
    public void setIdBoard(Integer idBoard) {
        this.idBoard = idBoard;
    }
}