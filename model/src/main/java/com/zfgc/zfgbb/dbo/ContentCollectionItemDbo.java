package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class ContentCollectionItemDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730847466-04:00", comments="Source field: zfgbb.content_collection_item.content_collection_item_id")
    private Integer contentCollectionItemId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730868585-04:00", comments="Source field: zfgbb.content_collection_item.content_collection_id")
    private Integer contentCollectionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730883234-04:00", comments="Source field: zfgbb.content_collection_item.content_entity_id")
    private Integer contentEntityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730897264-04:00", comments="Source field: zfgbb.content_collection_item.ordinal")
    private Integer ordinal;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730910874-04:00", comments="Source field: zfgbb.content_collection_item.awarded_ts")
    private OffsetDateTime awardedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730927303-04:00", comments="Source field: zfgbb.content_collection_item.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730941493-04:00", comments="Source field: zfgbb.content_collection_item.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730956642-04:00", comments="Source field: zfgbb.content_collection_item.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730857545-04:00", comments="Source field: zfgbb.content_collection_item.content_collection_item_id")
    public Integer getContentCollectionItemId() {
        return contentCollectionItemId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730863415-04:00", comments="Source field: zfgbb.content_collection_item.content_collection_item_id")
    public void setContentCollectionItemId(Integer contentCollectionItemId) {
        this.contentCollectionItemId = contentCollectionItemId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730873725-04:00", comments="Source field: zfgbb.content_collection_item.content_collection_id")
    public Integer getContentCollectionId() {
        return contentCollectionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730878635-04:00", comments="Source field: zfgbb.content_collection_item.content_collection_id")
    public void setContentCollectionId(Integer contentCollectionId) {
        this.contentCollectionId = contentCollectionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730888034-04:00", comments="Source field: zfgbb.content_collection_item.content_entity_id")
    public Integer getContentEntityId() {
        return contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730892874-04:00", comments="Source field: zfgbb.content_collection_item.content_entity_id")
    public void setContentEntityId(Integer contentEntityId) {
        this.contentEntityId = contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730902014-04:00", comments="Source field: zfgbb.content_collection_item.ordinal")
    public Integer getOrdinal() {
        return ordinal;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730906514-04:00", comments="Source field: zfgbb.content_collection_item.ordinal")
    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730916083-04:00", comments="Source field: zfgbb.content_collection_item.awarded_ts")
    public OffsetDateTime getAwardedTs() {
        return awardedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730920813-04:00", comments="Source field: zfgbb.content_collection_item.awarded_ts")
    public void setAwardedTs(OffsetDateTime awardedTs) {
        this.awardedTs = awardedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730932323-04:00", comments="Source field: zfgbb.content_collection_item.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730936953-04:00", comments="Source field: zfgbb.content_collection_item.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730946602-04:00", comments="Source field: zfgbb.content_collection_item.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730951322-04:00", comments="Source field: zfgbb.content_collection_item.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730961592-04:00", comments="Source field: zfgbb.content_collection_item.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730967382-04:00", comments="Source field: zfgbb.content_collection_item.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return contentCollectionItemId;
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