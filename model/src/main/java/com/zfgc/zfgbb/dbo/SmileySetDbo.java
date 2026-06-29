package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class SmileySetDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031177774-04:00", comments="Source field: zfgbb.smiley_set.code")
    private String code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031195913-04:00", comments="Source field: zfgbb.smiley_set.label")
    private String label;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031210043-04:00", comments="Source field: zfgbb.smiley_set.ordinal")
    private Integer ordinal;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031222263-04:00", comments="Source field: zfgbb.smiley_set.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031235052-04:00", comments="Source field: zfgbb.smiley_set.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031185354-04:00", comments="Source field: zfgbb.smiley_set.code")
    public String getCode() {
        return code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031191194-04:00", comments="Source field: zfgbb.smiley_set.code")
    public void setCode(String code) {
        this.code = code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031201313-04:00", comments="Source field: zfgbb.smiley_set.label")
    public String getLabel() {
        return label;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031205973-04:00", comments="Source field: zfgbb.smiley_set.label")
    public void setLabel(String label) {
        this.label = label;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031214143-04:00", comments="Source field: zfgbb.smiley_set.ordinal")
    public Integer getOrdinal() {
        return ordinal;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031218233-04:00", comments="Source field: zfgbb.smiley_set.ordinal")
    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031227013-04:00", comments="Source field: zfgbb.smiley_set.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031231152-04:00", comments="Source field: zfgbb.smiley_set.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031239392-04:00", comments="Source field: zfgbb.smiley_set.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-14T17:25:24.031243342-04:00", comments="Source field: zfgbb.smiley_set.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return null;
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