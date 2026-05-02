package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFLogKarmaDbKey {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.639886904-04:00", comments="Source field: smf_1log_karma.id_target")
    private Integer idTarget;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.640324111-04:00", comments="Source field: smf_1log_karma.id_executor")
    private Integer idExecutor;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.640174886-04:00", comments="Source field: smf_1log_karma.id_target")
    public Integer getIdTarget() {
        return idTarget;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.640273363-04:00", comments="Source field: smf_1log_karma.id_target")
    public void setIdTarget(Integer idTarget) {
        this.idTarget = idTarget;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.640382-04:00", comments="Source field: smf_1log_karma.id_executor")
    public Integer getIdExecutor() {
        return idExecutor;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.640431408-04:00", comments="Source field: smf_1log_karma.id_executor")
    public void setIdExecutor(Integer idExecutor) {
        this.idExecutor = idExecutor;
    }
}