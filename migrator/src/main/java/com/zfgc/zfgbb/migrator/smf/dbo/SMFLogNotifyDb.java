package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFLogNotifyDb extends SMFLogNotifyDbKey {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038985138-04:00", comments="Source field: smf_1log_notify.sent")
    private Boolean sent;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038994727-04:00", comments="Source field: smf_1log_notify.sent")
    public Boolean getSent() {
        return sent;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039002207-04:00", comments="Source field: smf_1log_notify.sent")
    public void setSent(Boolean sent) {
        this.sent = sent;
    }
}