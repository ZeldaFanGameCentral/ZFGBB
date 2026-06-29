package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFPmRecipientDbKey {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036264017-04:00", comments="Source field: smf_1pm_recipients.id_pm")
    private Integer idPm;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036288036-04:00", comments="Source field: smf_1pm_recipients.id_member")
    private Integer idMember;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036273407-04:00", comments="Source field: smf_1pm_recipients.id_pm")
    public Integer getIdPm() {
        return idPm;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036281147-04:00", comments="Source field: smf_1pm_recipients.id_pm")
    public void setIdPm(Integer idPm) {
        this.idPm = idPm;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036294616-04:00", comments="Source field: smf_1pm_recipients.id_member")
    public Integer getIdMember() {
        return idMember;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036318275-04:00", comments="Source field: smf_1pm_recipients.id_member")
    public void setIdMember(Integer idMember) {
        this.idMember = idMember;
    }
}