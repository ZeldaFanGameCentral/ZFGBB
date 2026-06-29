package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class ContentEntityDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714348389-04:00", comments="Source field: zfgbb.content_entity.content_entity_id")
    private Integer contentEntityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714377988-04:00", comments="Source field: zfgbb.content_entity.entity_type")
    private String entityType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714474425-04:00", comments="Source field: zfgbb.content_entity.title")
    private String title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714492715-04:00", comments="Source field: zfgbb.content_entity.slug")
    private String slug;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714508874-04:00", comments="Source field: zfgbb.content_entity.summary")
    private String summary;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714524144-04:00", comments="Source field: zfgbb.content_entity.wiki_page_id")
    private Integer wikiPageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714539243-04:00", comments="Source field: zfgbb.content_entity.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714555793-04:00", comments="Source field: zfgbb.content_entity.preview_content_resource_id")
    private Integer previewContentResourceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714573402-04:00", comments="Source field: zfgbb.content_entity.created_user_id")
    private Integer createdUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714589292-04:00", comments="Source field: zfgbb.content_entity.view_count")
    private Integer viewCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714605201-04:00", comments="Source field: zfgbb.content_entity.download_count")
    private Integer downloadCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714621571-04:00", comments="Source field: zfgbb.content_entity.rating")
    private Float rating;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71463694-04:00", comments="Source field: zfgbb.content_entity.vote_count")
    private Integer voteCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714656119-04:00", comments="Source field: zfgbb.content_entity.author_name")
    private String authorName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714670129-04:00", comments="Source field: zfgbb.content_entity.published_ts")
    private OffsetDateTime publishedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714688718-04:00", comments="Source field: zfgbb.content_entity.last_updated_ts")
    private OffsetDateTime lastUpdatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714704348-04:00", comments="Source field: zfgbb.content_entity.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714719338-04:00", comments="Source field: zfgbb.content_entity.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714737207-04:00", comments="Source field: zfgbb.content_entity.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714361529-04:00", comments="Source field: zfgbb.content_entity.content_entity_id")
    public Integer getContentEntityId() {
        return contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714371428-04:00", comments="Source field: zfgbb.content_entity.content_entity_id")
    public void setContentEntityId(Integer contentEntityId) {
        this.contentEntityId = contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714383758-04:00", comments="Source field: zfgbb.content_entity.entity_type")
    public String getEntityType() {
        return entityType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714467035-04:00", comments="Source field: zfgbb.content_entity.entity_type")
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714480315-04:00", comments="Source field: zfgbb.content_entity.title")
    public String getTitle() {
        return title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714487175-04:00", comments="Source field: zfgbb.content_entity.title")
    public void setTitle(String title) {
        this.title = title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714498024-04:00", comments="Source field: zfgbb.content_entity.slug")
    public String getSlug() {
        return slug;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714503724-04:00", comments="Source field: zfgbb.content_entity.slug")
    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714513964-04:00", comments="Source field: zfgbb.content_entity.summary")
    public String getSummary() {
        return summary;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714519294-04:00", comments="Source field: zfgbb.content_entity.summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714529293-04:00", comments="Source field: zfgbb.content_entity.wiki_page_id")
    public Integer getWikiPageId() {
        return wikiPageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714534413-04:00", comments="Source field: zfgbb.content_entity.wiki_page_id")
    public void setWikiPageId(Integer wikiPageId) {
        this.wikiPageId = wikiPageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714545633-04:00", comments="Source field: zfgbb.content_entity.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714550853-04:00", comments="Source field: zfgbb.content_entity.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714560912-04:00", comments="Source field: zfgbb.content_entity.preview_content_resource_id")
    public Integer getPreviewContentResourceId() {
        return previewContentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714568382-04:00", comments="Source field: zfgbb.content_entity.preview_content_resource_id")
    public void setPreviewContentResourceId(Integer previewContentResourceId) {
        this.previewContentResourceId = previewContentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714578452-04:00", comments="Source field: zfgbb.content_entity.created_user_id")
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714583582-04:00", comments="Source field: zfgbb.content_entity.created_user_id")
    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714594271-04:00", comments="Source field: zfgbb.content_entity.view_count")
    public Integer getViewCount() {
        return viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714599841-04:00", comments="Source field: zfgbb.content_entity.view_count")
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714610751-04:00", comments="Source field: zfgbb.content_entity.download_count")
    public Integer getDownloadCount() {
        return downloadCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714616631-04:00", comments="Source field: zfgbb.content_entity.download_count")
    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.7146256-04:00", comments="Source field: zfgbb.content_entity.rating")
    public Float getRating() {
        return rating;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71463238-04:00", comments="Source field: zfgbb.content_entity.rating")
    public void setRating(Float rating) {
        this.rating = rating;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71464204-04:00", comments="Source field: zfgbb.content_entity.vote_count")
    public Integer getVoteCount() {
        return voteCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71465077-04:00", comments="Source field: zfgbb.content_entity.vote_count")
    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714661249-04:00", comments="Source field: zfgbb.content_entity.author_name")
    public String getAuthorName() {
        return authorName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714666769-04:00", comments="Source field: zfgbb.content_entity.author_name")
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714677289-04:00", comments="Source field: zfgbb.content_entity.published_ts")
    public OffsetDateTime getPublishedTs() {
        return publishedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714682949-04:00", comments="Source field: zfgbb.content_entity.published_ts")
    public void setPublishedTs(OffsetDateTime publishedTs) {
        this.publishedTs = publishedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714694308-04:00", comments="Source field: zfgbb.content_entity.last_updated_ts")
    public OffsetDateTime getLastUpdatedTs() {
        return lastUpdatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714699388-04:00", comments="Source field: zfgbb.content_entity.last_updated_ts")
    public void setLastUpdatedTs(OffsetDateTime lastUpdatedTs) {
        this.lastUpdatedTs = lastUpdatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714709568-04:00", comments="Source field: zfgbb.content_entity.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714714398-04:00", comments="Source field: zfgbb.content_entity.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714724627-04:00", comments="Source field: zfgbb.content_entity.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714729487-04:00", comments="Source field: zfgbb.content_entity.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714742557-04:00", comments="Source field: zfgbb.content_entity.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.714748137-04:00", comments="Source field: zfgbb.content_entity.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return contentEntityId;
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