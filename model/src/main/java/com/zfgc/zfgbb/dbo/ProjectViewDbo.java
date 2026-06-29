package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class ProjectViewDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.717932198-04:00", comments="Source field: zfgbb.project_view.content_entity_id")
    private Integer contentEntityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.717962877-04:00", comments="Source field: zfgbb.project_view.title")
    private String title;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.717985046-04:00", comments="Source field: zfgbb.project_view.slug")
    private String slug;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718000445-04:00", comments="Source field: zfgbb.project_view.summary")
    private String summary;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718015635-04:00", comments="Source field: zfgbb.project_view.wiki_page_id")
    private Integer wikiPageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718030414-04:00", comments="Source field: zfgbb.project_view.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718045304-04:00", comments="Source field: zfgbb.project_view.preview_content_resource_id")
    private Integer previewContentResourceId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718060114-04:00", comments="Source field: zfgbb.project_view.created_user_id")
    private Integer createdUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718074823-04:00", comments="Source field: zfgbb.project_view.view_count")
    private Integer viewCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718089493-04:00", comments="Source field: zfgbb.project_view.download_count")
    private Integer downloadCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718104122-04:00", comments="Source field: zfgbb.project_view.rating")
    private Float rating;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718117572-04:00", comments="Source field: zfgbb.project_view.vote_count")
    private Integer voteCount;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718134371-04:00", comments="Source field: zfgbb.project_view.published_ts")
    private OffsetDateTime publishedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718149841-04:00", comments="Source field: zfgbb.project_view.last_updated_ts")
    private OffsetDateTime lastUpdatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71816794-04:00", comments="Source field: zfgbb.project_view.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71818288-04:00", comments="Source field: zfgbb.project_view.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718198329-04:00", comments="Source field: zfgbb.project_view.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718213459-04:00", comments="Source field: zfgbb.project_view.author_name")
    private String authorName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718228588-04:00", comments="Source field: zfgbb.project_view.status")
    private String status;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718243508-04:00", comments="Source field: zfgbb.project_view.progress")
    private Short progress;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718258197-04:00", comments="Source field: zfgbb.project_view.language")
    private String language;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718271437-04:00", comments="Source field: zfgbb.project_view.requirements")
    private String requirements;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718288726-04:00", comments="Source field: zfgbb.project_view.team_id")
    private Integer teamId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.717945047-04:00", comments="Source field: zfgbb.project_view.content_entity_id")
    public Integer getContentEntityId() {
        return contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.717954667-04:00", comments="Source field: zfgbb.project_view.content_entity_id")
    public void setContentEntityId(Integer contentEntityId) {
        this.contentEntityId = contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.717970716-04:00", comments="Source field: zfgbb.project_view.title")
    public String getTitle() {
        return title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.717979496-04:00", comments="Source field: zfgbb.project_view.title")
    public void setTitle(String title) {
        this.title = title;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.717989986-04:00", comments="Source field: zfgbb.project_view.slug")
    public String getSlug() {
        return slug;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.717995436-04:00", comments="Source field: zfgbb.project_view.slug")
    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718005265-04:00", comments="Source field: zfgbb.project_view.summary")
    public String getSummary() {
        return summary;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718010515-04:00", comments="Source field: zfgbb.project_view.summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718020515-04:00", comments="Source field: zfgbb.project_view.wiki_page_id")
    public Integer getWikiPageId() {
        return wikiPageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718025565-04:00", comments="Source field: zfgbb.project_view.wiki_page_id")
    public void setWikiPageId(Integer wikiPageId) {
        this.wikiPageId = wikiPageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718035484-04:00", comments="Source field: zfgbb.project_view.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718040364-04:00", comments="Source field: zfgbb.project_view.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718050354-04:00", comments="Source field: zfgbb.project_view.preview_content_resource_id")
    public Integer getPreviewContentResourceId() {
        return previewContentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718055314-04:00", comments="Source field: zfgbb.project_view.preview_content_resource_id")
    public void setPreviewContentResourceId(Integer previewContentResourceId) {
        this.previewContentResourceId = previewContentResourceId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718065083-04:00", comments="Source field: zfgbb.project_view.created_user_id")
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718069943-04:00", comments="Source field: zfgbb.project_view.created_user_id")
    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718079763-04:00", comments="Source field: zfgbb.project_view.view_count")
    public Integer getViewCount() {
        return viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718084633-04:00", comments="Source field: zfgbb.project_view.view_count")
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718094612-04:00", comments="Source field: zfgbb.project_view.download_count")
    public Integer getDownloadCount() {
        return downloadCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718099342-04:00", comments="Source field: zfgbb.project_view.download_count")
    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718109302-04:00", comments="Source field: zfgbb.project_view.rating")
    public Float getRating() {
        return rating;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718114282-04:00", comments="Source field: zfgbb.project_view.rating")
    public void setRating(Float rating) {
        this.rating = rating;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718122342-04:00", comments="Source field: zfgbb.project_view.vote_count")
    public Integer getVoteCount() {
        return voteCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718131011-04:00", comments="Source field: zfgbb.project_view.vote_count")
    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718141381-04:00", comments="Source field: zfgbb.project_view.published_ts")
    public OffsetDateTime getPublishedTs() {
        return publishedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718146511-04:00", comments="Source field: zfgbb.project_view.published_ts")
    public void setPublishedTs(OffsetDateTime publishedTs) {
        this.publishedTs = publishedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71815787-04:00", comments="Source field: zfgbb.project_view.last_updated_ts")
    public OffsetDateTime getLastUpdatedTs() {
        return lastUpdatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71816305-04:00", comments="Source field: zfgbb.project_view.last_updated_ts")
    public void setLastUpdatedTs(OffsetDateTime lastUpdatedTs) {
        this.lastUpdatedTs = lastUpdatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71817332-04:00", comments="Source field: zfgbb.project_view.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71817815-04:00", comments="Source field: zfgbb.project_view.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71818831-04:00", comments="Source field: zfgbb.project_view.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718193439-04:00", comments="Source field: zfgbb.project_view.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718203169-04:00", comments="Source field: zfgbb.project_view.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718208579-04:00", comments="Source field: zfgbb.project_view.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718218589-04:00", comments="Source field: zfgbb.project_view.author_name")
    public String getAuthorName() {
        return authorName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718223798-04:00", comments="Source field: zfgbb.project_view.author_name")
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718233488-04:00", comments="Source field: zfgbb.project_view.status")
    public String getStatus() {
        return status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718238688-04:00", comments="Source field: zfgbb.project_view.status")
    public void setStatus(String status) {
        this.status = status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718248458-04:00", comments="Source field: zfgbb.project_view.progress")
    public Short getProgress() {
        return progress;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718253318-04:00", comments="Source field: zfgbb.project_view.progress")
    public void setProgress(Short progress) {
        this.progress = progress;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718262947-04:00", comments="Source field: zfgbb.project_view.language")
    public String getLanguage() {
        return language;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718268157-04:00", comments="Source field: zfgbb.project_view.language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718276337-04:00", comments="Source field: zfgbb.project_view.requirements")
    public String getRequirements() {
        return requirements;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718285387-04:00", comments="Source field: zfgbb.project_view.requirements")
    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718293656-04:00", comments="Source field: zfgbb.project_view.team_id")
    public Integer getTeamId() {
        return teamId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718312656-04:00", comments="Source field: zfgbb.project_view.team_id")
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
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