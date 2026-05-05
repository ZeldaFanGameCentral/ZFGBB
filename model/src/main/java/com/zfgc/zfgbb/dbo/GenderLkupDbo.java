package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;

public class GenderLkupDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.25112817-04:00", comments="Source field: zfgbb.gender_lkup.gender_id")
    private Integer genderId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251161809-04:00", comments="Source field: zfgbb.gender_lkup.code")
    private String code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251188758-04:00", comments="Source field: zfgbb.gender_lkup.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251212508-04:00", comments="Source field: zfgbb.gender_lkup.seqno")
    private Integer seqno;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251233897-04:00", comments="Source field: zfgbb.gender_lkup.created_ts")
    private LocalDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251256436-04:00", comments="Source field: zfgbb.gender_lkup.updated_ts")
    private LocalDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.25114326-04:00", comments="Source field: zfgbb.gender_lkup.gender_id")
    public Integer getGenderId() {
        return genderId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.25115325-04:00", comments="Source field: zfgbb.gender_lkup.gender_id")
    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251169849-04:00", comments="Source field: zfgbb.gender_lkup.code")
    public String getCode() {
        return code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251180739-04:00", comments="Source field: zfgbb.gender_lkup.code")
    public void setCode(String code) {
        this.code = code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251196308-04:00", comments="Source field: zfgbb.gender_lkup.description")
    public String getDescription() {
        return description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251205308-04:00", comments="Source field: zfgbb.gender_lkup.description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251219757-04:00", comments="Source field: zfgbb.gender_lkup.seqno")
    public Integer getSeqno() {
        return seqno;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251226997-04:00", comments="Source field: zfgbb.gender_lkup.seqno")
    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251241837-04:00", comments="Source field: zfgbb.gender_lkup.created_ts")
    public LocalDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251249587-04:00", comments="Source field: zfgbb.gender_lkup.created_ts")
    public void setCreatedTs(LocalDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251263606-04:00", comments="Source field: zfgbb.gender_lkup.updated_ts")
    public LocalDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.251270746-04:00", comments="Source field: zfgbb.gender_lkup.updated_ts")
    public void setUpdatedTs(LocalDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return genderId;
    }

    @Override
    public LocalDateTime getCreatedTime() {
        return createdTs;
    }

    @Override
    public LocalDateTime getUpdatedTime() {
        return updatedTs;
    }
}