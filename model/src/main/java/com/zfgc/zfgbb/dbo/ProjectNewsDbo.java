package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class ProjectNewsDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729859106-04:00", comments="Source field: zfgbb.project_news.project_news_id")
    private Integer projectNewsId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729881116-04:00", comments="Source field: zfgbb.project_news.content_entity_id")
    private Integer contentEntityId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729895765-04:00", comments="Source field: zfgbb.project_news.thread_id")
    private Integer threadId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729923584-04:00", comments="Source field: zfgbb.project_news.subject")
    private String subject;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729939384-04:00", comments="Source field: zfgbb.project_news.body")
    private String body;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729954053-04:00", comments="Source field: zfgbb.project_news.author_user_id")
    private Integer authorUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729967903-04:00", comments="Source field: zfgbb.project_news.author_name")
    private String authorName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729981963-04:00", comments="Source field: zfgbb.project_news.published_ts")
    private OffsetDateTime publishedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729996492-04:00", comments="Source field: zfgbb.project_news.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729867756-04:00", comments="Source field: zfgbb.project_news.project_news_id")
    public Integer getProjectNewsId() {
        return projectNewsId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729873666-04:00", comments="Source field: zfgbb.project_news.project_news_id")
    public void setProjectNewsId(Integer projectNewsId) {
        this.projectNewsId = projectNewsId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729886116-04:00", comments="Source field: zfgbb.project_news.content_entity_id")
    public Integer getContentEntityId() {
        return contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729891315-04:00", comments="Source field: zfgbb.project_news.content_entity_id")
    public void setContentEntityId(Integer contentEntityId) {
        this.contentEntityId = contentEntityId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729913505-04:00", comments="Source field: zfgbb.project_news.thread_id")
    public Integer getThreadId() {
        return threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729918895-04:00", comments="Source field: zfgbb.project_news.thread_id")
    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729928594-04:00", comments="Source field: zfgbb.project_news.subject")
    public String getSubject() {
        return subject;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729934644-04:00", comments="Source field: zfgbb.project_news.subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729944454-04:00", comments="Source field: zfgbb.project_news.body")
    public String getBody() {
        return body;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729949664-04:00", comments="Source field: zfgbb.project_news.body")
    public void setBody(String body) {
        this.body = body;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729958723-04:00", comments="Source field: zfgbb.project_news.author_user_id")
    public Integer getAuthorUserId() {
        return authorUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729963483-04:00", comments="Source field: zfgbb.project_news.author_user_id")
    public void setAuthorUserId(Integer authorUserId) {
        this.authorUserId = authorUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729972573-04:00", comments="Source field: zfgbb.project_news.author_name")
    public String getAuthorName() {
        return authorName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729977433-04:00", comments="Source field: zfgbb.project_news.author_name")
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729987252-04:00", comments="Source field: zfgbb.project_news.published_ts")
    public OffsetDateTime getPublishedTs() {
        return publishedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.729992062-04:00", comments="Source field: zfgbb.project_news.published_ts")
    public void setPublishedTs(OffsetDateTime publishedTs) {
        this.publishedTs = publishedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730001152-04:00", comments="Source field: zfgbb.project_news.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730006062-04:00", comments="Source field: zfgbb.project_news.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Override
    public Integer getPkId() {
        return projectNewsId;
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