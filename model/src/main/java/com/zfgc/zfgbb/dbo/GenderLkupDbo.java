package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class GenderLkupDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705254362-04:00", comments="Source field: zfgbb.gender_lkup.gender_id")
    private Integer genderId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705281391-04:00", comments="Source field: zfgbb.gender_lkup.code")
    private String code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70533286-04:00", comments="Source field: zfgbb.gender_lkup.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705357409-04:00", comments="Source field: zfgbb.gender_lkup.seqno")
    private Integer seqno;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705377198-04:00", comments="Source field: zfgbb.gender_lkup.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705396708-04:00", comments="Source field: zfgbb.gender_lkup.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705266062-04:00", comments="Source field: zfgbb.gender_lkup.gender_id")
    public Integer getGenderId() {
        return genderId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705274311-04:00", comments="Source field: zfgbb.gender_lkup.gender_id")
    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705288221-04:00", comments="Source field: zfgbb.gender_lkup.code")
    public String getCode() {
        return code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70531158-04:00", comments="Source field: zfgbb.gender_lkup.code")
    public void setCode(String code) {
        this.code = code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705341219-04:00", comments="Source field: zfgbb.gender_lkup.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705350459-04:00", comments="Source field: zfgbb.gender_lkup.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705364139-04:00", comments="Source field: zfgbb.gender_lkup.seqno")
    public Integer getSeqno() {
        return seqno;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705370798-04:00", comments="Source field: zfgbb.gender_lkup.seqno")
    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705384258-04:00", comments="Source field: zfgbb.gender_lkup.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705390648-04:00", comments="Source field: zfgbb.gender_lkup.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705403197-04:00", comments="Source field: zfgbb.gender_lkup.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.705409497-04:00", comments="Source field: zfgbb.gender_lkup.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return genderId;
    }

    @Override
    public OffsetDateTime getCreatedTime() {
        return createdTs;
    }

    @Override
    public OffsetDateTime getUpdatedTime() {
        return updatedTs;
    }
}