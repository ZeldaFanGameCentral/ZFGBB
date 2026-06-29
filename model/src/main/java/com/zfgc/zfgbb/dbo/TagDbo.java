package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class TagDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.728251256-04:00", comments="Source field: zfgbb.tag.tag_id")
    private Integer tagId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.728294795-04:00", comments="Source field: zfgbb.tag.name")
    private String name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.728326814-04:00", comments="Source field: zfgbb.tag.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.728260126-04:00", comments="Source field: zfgbb.tag.tag_id")
    public Integer getTagId() {
        return tagId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.728266236-04:00", comments="Source field: zfgbb.tag.tag_id")
    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.728314334-04:00", comments="Source field: zfgbb.tag.name")
    public String getName() {
        return name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.728321364-04:00", comments="Source field: zfgbb.tag.name")
    public void setName(String name) {
        this.name = name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.728331974-04:00", comments="Source field: zfgbb.tag.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.728337554-04:00", comments="Source field: zfgbb.tag.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return tagId;
    }

    @Override
    public OffsetDateTime getCreatedTime() {
        return null;
    }

    @Override
    public OffsetDateTime getUpdatedTime() {
        return null;
    }
}