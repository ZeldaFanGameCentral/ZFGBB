package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFMessageHistoryDb {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679500579-04:00", comments="Source field: smf_1messages_history.id_edit")
    private Integer idEdit;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679530189-04:00", comments="Source field: smf_1messages_history.id_msg")
    private Integer idMsg;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679557148-04:00", comments="Source field: smf_1messages_history.modified_name")
    private String modifiedName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679581597-04:00", comments="Source field: smf_1messages_history.modified_time")
    private Integer modifiedTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679603496-04:00", comments="Source field: smf_1messages_history.body")
    private String body;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679513129-04:00", comments="Source field: smf_1messages_history.id_edit")
    public Integer getIdEdit() {
        return idEdit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679523269-04:00", comments="Source field: smf_1messages_history.id_edit")
    public void setIdEdit(Integer idEdit) {
        this.idEdit = idEdit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679541358-04:00", comments="Source field: smf_1messages_history.id_msg")
    public Integer getIdMsg() {
        return idMsg;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679550268-04:00", comments="Source field: smf_1messages_history.id_msg")
    public void setIdMsg(Integer idMsg) {
        this.idMsg = idMsg;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679564898-04:00", comments="Source field: smf_1messages_history.modified_name")
    public String getModifiedName() {
        return modifiedName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679574917-04:00", comments="Source field: smf_1messages_history.modified_name")
    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679589037-04:00", comments="Source field: smf_1messages_history.modified_time")
    public Integer getModifiedTime() {
        return modifiedTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679597287-04:00", comments="Source field: smf_1messages_history.modified_time")
    public void setModifiedTime(Integer modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679611046-04:00", comments="Source field: smf_1messages_history.body")
    public String getBody() {
        return body;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.679621296-04:00", comments="Source field: smf_1messages_history.body")
    public void setBody(String body) {
        this.body = body;
    }
}