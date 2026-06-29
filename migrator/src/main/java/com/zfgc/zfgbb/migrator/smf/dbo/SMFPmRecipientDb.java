package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFPmRecipientDb extends SMFPmRecipientDbKey {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036345964-04:00", comments="Source field: smf_1pm_recipients.labels")
    private String labels;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036374223-04:00", comments="Source field: smf_1pm_recipients.bcc")
    private Boolean bcc;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036393163-04:00", comments="Source field: smf_1pm_recipients.is_read")
    private Integer isRead;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036410852-04:00", comments="Source field: smf_1pm_recipients.deleted")
    private Boolean deleted;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036430232-04:00", comments="Source field: smf_1pm_recipients.is_new")
    private Integer isNew;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036358084-04:00", comments="Source field: smf_1pm_recipients.labels")
    public String getLabels() {
        return labels;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036367584-04:00", comments="Source field: smf_1pm_recipients.labels")
    public void setLabels(String labels) {
        this.labels = labels;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036380563-04:00", comments="Source field: smf_1pm_recipients.bcc")
    public Boolean getBcc() {
        return bcc;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036387113-04:00", comments="Source field: smf_1pm_recipients.bcc")
    public void setBcc(Boolean bcc) {
        this.bcc = bcc;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036398973-04:00", comments="Source field: smf_1pm_recipients.is_read")
    public Integer getIsRead() {
        return isRead;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036405062-04:00", comments="Source field: smf_1pm_recipients.is_read")
    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036418742-04:00", comments="Source field: smf_1pm_recipients.deleted")
    public Boolean getDeleted() {
        return deleted;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036424762-04:00", comments="Source field: smf_1pm_recipients.deleted")
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036435861-04:00", comments="Source field: smf_1pm_recipients.is_new")
    public Integer getIsNew() {
        return isNew;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036441811-04:00", comments="Source field: smf_1pm_recipients.is_new")
    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }
}