package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFLogKarmaDb extends SMFLogKarmaDbKey {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.640854525-04:00", comments="Source field: smf_1log_karma.log_time")
    private Integer logTime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.640999371-04:00", comments="Source field: smf_1log_karma.action")
    private Integer action;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.641174626-04:00", comments="Source field: smf_1log_karma.is_read")
    private Byte isRead;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.640911874-04:00", comments="Source field: smf_1log_karma.log_time")
    public Integer getLogTime() {
        return logTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.640957722-04:00", comments="Source field: smf_1log_karma.log_time")
    public void setLogTime(Integer logTime) {
        this.logTime = logTime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.64104062-04:00", comments="Source field: smf_1log_karma.action")
    public Integer getAction() {
        return action;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.641100728-04:00", comments="Source field: smf_1log_karma.action")
    public void setAction(Integer action) {
        this.action = action;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.641242904-04:00", comments="Source field: smf_1log_karma.is_read")
    public Byte getIsRead() {
        return isRead;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.641291492-04:00", comments="Source field: smf_1log_karma.is_read")
    public void setIsRead(Byte isRead) {
        this.isRead = isRead;
    }
}