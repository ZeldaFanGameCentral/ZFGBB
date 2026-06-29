package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class SmileyDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031909414-04:00", comments="Source field: zfgbb.smiley.smiley_id")
    private Integer smileyId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031926653-04:00", comments="Source field: zfgbb.smiley.code")
    private String code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031940233-04:00", comments="Source field: zfgbb.smiley.name")
    private String name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031952672-04:00", comments="Source field: zfgbb.smiley.label")
    private String label;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031963502-04:00", comments="Source field: zfgbb.smiley.ordinal")
    private Integer ordinal;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031976032-04:00", comments="Source field: zfgbb.smiley.hidden_flag")
    private Boolean hiddenFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031988711-04:00", comments="Source field: zfgbb.smiley.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032001801-04:00", comments="Source field: zfgbb.smiley.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031916903-04:00", comments="Source field: zfgbb.smiley.smiley_id")
    public Integer getSmileyId() {
        return smileyId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031922223-04:00", comments="Source field: zfgbb.smiley.smiley_id")
    public void setSmileyId(Integer smileyId) {
        this.smileyId = smileyId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031931023-04:00", comments="Source field: zfgbb.smiley.code")
    public String getCode() {
        return code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031935813-04:00", comments="Source field: zfgbb.smiley.code")
    public void setCode(String code) {
        this.code = code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031944273-04:00", comments="Source field: zfgbb.smiley.name")
    public String getName() {
        return name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031948723-04:00", comments="Source field: zfgbb.smiley.name")
    public void setName(String name) {
        this.name = name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031956712-04:00", comments="Source field: zfgbb.smiley.label")
    public String getLabel() {
        return label;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031960962-04:00", comments="Source field: zfgbb.smiley.label")
    public void setLabel(String label) {
        this.label = label;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031967692-04:00", comments="Source field: zfgbb.smiley.ordinal")
    public Integer getOrdinal() {
        return ordinal;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031972022-04:00", comments="Source field: zfgbb.smiley.ordinal")
    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031980322-04:00", comments="Source field: zfgbb.smiley.hidden_flag")
    public Boolean getHiddenFlag() {
        return hiddenFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031984632-04:00", comments="Source field: zfgbb.smiley.hidden_flag")
    public void setHiddenFlag(Boolean hiddenFlag) {
        this.hiddenFlag = hiddenFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031993471-04:00", comments="Source field: zfgbb.smiley.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031997601-04:00", comments="Source field: zfgbb.smiley.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032006111-04:00", comments="Source field: zfgbb.smiley.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.032010221-04:00", comments="Source field: zfgbb.smiley.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return smileyId;
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