package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class WikiPageDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710801349-04:00", comments="Source field: zfgbb.wiki_page.wiki_page_id")
    private Integer wikiPageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710827219-04:00", comments="Source field: zfgbb.wiki_page.namespace")
    private String namespace;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710849028-04:00", comments="Source field: zfgbb.wiki_page.title")
    private String title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710869987-04:00", comments="Source field: zfgbb.wiki_page.slug")
    private String slug;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710893517-04:00", comments="Source field: zfgbb.wiki_page.redirect_to")
    private String redirectTo;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710914326-04:00", comments="Source field: zfgbb.wiki_page.created_user_id")
    private Integer createdUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710934905-04:00", comments="Source field: zfgbb.wiki_page.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710967954-04:00", comments="Source field: zfgbb.wiki_page.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710990304-04:00", comments="Source field: zfgbb.wiki_page.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.711052782-04:00", comments="Source field: zfgbb.wiki_page.content_resource_id")
    private Integer contentResourceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71111042-04:00", comments="Source field: zfgbb.wiki_page.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710812599-04:00", comments="Source field: zfgbb.wiki_page.wiki_page_id")
    public Integer getWikiPageId() {
        return wikiPageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710820519-04:00", comments="Source field: zfgbb.wiki_page.wiki_page_id")
    public void setWikiPageId(Integer wikiPageId) {
        this.wikiPageId = wikiPageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710833598-04:00", comments="Source field: zfgbb.wiki_page.namespace")
    public String getNamespace() {
        return namespace;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710842478-04:00", comments="Source field: zfgbb.wiki_page.namespace")
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710855418-04:00", comments="Source field: zfgbb.wiki_page.title")
    public String getTitle() {
        return title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710863558-04:00", comments="Source field: zfgbb.wiki_page.title")
    public void setTitle(String title) {
        this.title = title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710876277-04:00", comments="Source field: zfgbb.wiki_page.slug")
    public String getSlug() {
        return slug;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710886247-04:00", comments="Source field: zfgbb.wiki_page.slug")
    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710901126-04:00", comments="Source field: zfgbb.wiki_page.redirect_to")
    public String getRedirectTo() {
        return redirectTo;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710909696-04:00", comments="Source field: zfgbb.wiki_page.redirect_to")
    public void setRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710921276-04:00", comments="Source field: zfgbb.wiki_page.created_user_id")
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710929425-04:00", comments="Source field: zfgbb.wiki_page.created_user_id")
    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710956195-04:00", comments="Source field: zfgbb.wiki_page.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710963284-04:00", comments="Source field: zfgbb.wiki_page.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710978174-04:00", comments="Source field: zfgbb.wiki_page.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710984544-04:00", comments="Source field: zfgbb.wiki_page.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.710996313-04:00", comments="Source field: zfgbb.wiki_page.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.711003983-04:00", comments="Source field: zfgbb.wiki_page.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.711068931-04:00", comments="Source field: zfgbb.wiki_page.content_resource_id")
    public Integer getContentResourceId() {
        return contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71110209-04:00", comments="Source field: zfgbb.wiki_page.content_resource_id")
    public void setContentResourceId(Integer contentResourceId) {
        this.contentResourceId = contentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71111848-04:00", comments="Source field: zfgbb.wiki_page.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.711126149-04:00", comments="Source field: zfgbb.wiki_page.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Override
    public Integer getPkId() {
        return wikiPageId;
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