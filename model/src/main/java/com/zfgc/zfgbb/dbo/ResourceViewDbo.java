package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class ResourceViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719543757-04:00", comments="Source field: zfgbb.resource_view.content_entity_id")
    private Integer contentEntityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719563457-04:00", comments="Source field: zfgbb.resource_view.title")
    private String title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719578086-04:00", comments="Source field: zfgbb.resource_view.slug")
    private String slug;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719593356-04:00", comments="Source field: zfgbb.resource_view.summary")
    private String summary;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719608175-04:00", comments="Source field: zfgbb.resource_view.wiki_page_id")
    private Integer wikiPageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719623065-04:00", comments="Source field: zfgbb.resource_view.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719650604-04:00", comments="Source field: zfgbb.resource_view.preview_content_resource_id")
    private Integer previewContentResourceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719665474-04:00", comments="Source field: zfgbb.resource_view.created_user_id")
    private Integer createdUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719683073-04:00", comments="Source field: zfgbb.resource_view.view_count")
    private Integer viewCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719697933-04:00", comments="Source field: zfgbb.resource_view.download_count")
    private Integer downloadCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719712242-04:00", comments="Source field: zfgbb.resource_view.rating")
    private Float rating;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719737571-04:00", comments="Source field: zfgbb.resource_view.vote_count")
    private Integer voteCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719752291-04:00", comments="Source field: zfgbb.resource_view.published_ts")
    private OffsetDateTime publishedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719768-04:00", comments="Source field: zfgbb.resource_view.last_updated_ts")
    private OffsetDateTime lastUpdatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71978324-04:00", comments="Source field: zfgbb.resource_view.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719798069-04:00", comments="Source field: zfgbb.resource_view.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719812819-04:00", comments="Source field: zfgbb.resource_view.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719828278-04:00", comments="Source field: zfgbb.resource_view.author_name")
    private String authorName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719843078-04:00", comments="Source field: zfgbb.resource_view.resource_type")
    private String resourceType;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719859988-04:00", comments="Source field: zfgbb.resource_view.download_content_resource_id")
    private Integer downloadContentResourceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719888327-04:00", comments="Source field: zfgbb.resource_view.download_url")
    private String downloadUrl;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719911106-04:00", comments="Source field: zfgbb.resource_view.file_size")
    private Long fileSize;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719552177-04:00", comments="Source field: zfgbb.resource_view.content_entity_id")
    public Integer getContentEntityId() {
        return contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719558107-04:00", comments="Source field: zfgbb.resource_view.content_entity_id")
    public void setContentEntityId(Integer contentEntityId) {
        this.contentEntityId = contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719568607-04:00", comments="Source field: zfgbb.resource_view.title")
    public String getTitle() {
        return title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719574646-04:00", comments="Source field: zfgbb.resource_view.title")
    public void setTitle(String title) {
        this.title = title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719583106-04:00", comments="Source field: zfgbb.resource_view.slug")
    public String getSlug() {
        return slug;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719588506-04:00", comments="Source field: zfgbb.resource_view.slug")
    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719598156-04:00", comments="Source field: zfgbb.resource_view.summary")
    public String getSummary() {
        return summary;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719603376-04:00", comments="Source field: zfgbb.resource_view.summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719613135-04:00", comments="Source field: zfgbb.resource_view.wiki_page_id")
    public Integer getWikiPageId() {
        return wikiPageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719618185-04:00", comments="Source field: zfgbb.resource_view.wiki_page_id")
    public void setWikiPageId(Integer wikiPageId) {
        this.wikiPageId = wikiPageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719639654-04:00", comments="Source field: zfgbb.resource_view.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719645504-04:00", comments="Source field: zfgbb.resource_view.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719655464-04:00", comments="Source field: zfgbb.resource_view.preview_content_resource_id")
    public Integer getPreviewContentResourceId() {
        return previewContentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719660534-04:00", comments="Source field: zfgbb.resource_view.preview_content_resource_id")
    public void setPreviewContentResourceId(Integer previewContentResourceId) {
        this.previewContentResourceId = previewContentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719670493-04:00", comments="Source field: zfgbb.resource_view.created_user_id")
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719675603-04:00", comments="Source field: zfgbb.resource_view.created_user_id")
    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719688173-04:00", comments="Source field: zfgbb.resource_view.view_count")
    public Integer getViewCount() {
        return viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719693023-04:00", comments="Source field: zfgbb.resource_view.view_count")
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719702692-04:00", comments="Source field: zfgbb.resource_view.download_count")
    public Integer getDownloadCount() {
        return downloadCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719707512-04:00", comments="Source field: zfgbb.resource_view.download_count")
    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719723912-04:00", comments="Source field: zfgbb.resource_view.rating")
    public Float getRating() {
        return rating;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719732162-04:00", comments="Source field: zfgbb.resource_view.rating")
    public void setRating(Float rating) {
        this.rating = rating;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719742421-04:00", comments="Source field: zfgbb.resource_view.vote_count")
    public Integer getVoteCount() {
        return voteCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719747311-04:00", comments="Source field: zfgbb.resource_view.vote_count")
    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719758231-04:00", comments="Source field: zfgbb.resource_view.published_ts")
    public OffsetDateTime getPublishedTs() {
        return publishedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719763201-04:00", comments="Source field: zfgbb.resource_view.published_ts")
    public void setPublishedTs(OffsetDateTime publishedTs) {
        this.publishedTs = publishedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71977327-04:00", comments="Source field: zfgbb.resource_view.last_updated_ts")
    public OffsetDateTime getLastUpdatedTs() {
        return lastUpdatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71977838-04:00", comments="Source field: zfgbb.resource_view.last_updated_ts")
    public void setLastUpdatedTs(OffsetDateTime lastUpdatedTs) {
        this.lastUpdatedTs = lastUpdatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71978838-04:00", comments="Source field: zfgbb.resource_view.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71979324-04:00", comments="Source field: zfgbb.resource_view.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719804709-04:00", comments="Source field: zfgbb.resource_view.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719809579-04:00", comments="Source field: zfgbb.resource_view.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719817929-04:00", comments="Source field: zfgbb.resource_view.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719824799-04:00", comments="Source field: zfgbb.resource_view.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719833188-04:00", comments="Source field: zfgbb.resource_view.author_name")
    public String getAuthorName() {
        return authorName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719839708-04:00", comments="Source field: zfgbb.resource_view.author_name")
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719850148-04:00", comments="Source field: zfgbb.resource_view.resource_type")
    public String getResourceType() {
        return resourceType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719855268-04:00", comments="Source field: zfgbb.resource_view.resource_type")
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719867357-04:00", comments="Source field: zfgbb.resource_view.download_content_resource_id")
    public Integer getDownloadContentResourceId() {
        return downloadContentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719879647-04:00", comments="Source field: zfgbb.resource_view.download_content_resource_id")
    public void setDownloadContentResourceId(Integer downloadContentResourceId) {
        this.downloadContentResourceId = downloadContentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719898726-04:00", comments="Source field: zfgbb.resource_view.download_url")
    public String getDownloadUrl() {
        return downloadUrl;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719905296-04:00", comments="Source field: zfgbb.resource_view.download_url")
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719916206-04:00", comments="Source field: zfgbb.resource_view.file_size")
    public Long getFileSize() {
        return fileSize;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719921006-04:00", comments="Source field: zfgbb.resource_view.file_size")
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
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