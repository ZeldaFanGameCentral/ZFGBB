package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class WikiPageRevisionDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712679971-04:00", comments="Source field: zfgbb.wiki_page_revision.wiki_page_revision_id")
    private Integer wikiPageRevisionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712771908-04:00", comments="Source field: zfgbb.wiki_page_revision.wiki_page_id")
    private Integer wikiPageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712794097-04:00", comments="Source field: zfgbb.wiki_page_revision.content")
    private String content;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712816907-04:00", comments="Source field: zfgbb.wiki_page_revision.content_format")
    private String contentFormat;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712837066-04:00", comments="Source field: zfgbb.wiki_page_revision.summary")
    private String summary;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712856375-04:00", comments="Source field: zfgbb.wiki_page_revision.author_user_id")
    private Integer authorUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712874655-04:00", comments="Source field: zfgbb.wiki_page_revision.current_flag")
    private Boolean currentFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712892354-04:00", comments="Source field: zfgbb.wiki_page_revision.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712910974-04:00", comments="Source field: zfgbb.wiki_page_revision.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712932513-04:00", comments="Source field: zfgbb.wiki_page_revision.migration_hash")
    private String migrationHash;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712952582-04:00", comments="Source field: zfgbb.wiki_page_revision.authored_ts")
    private OffsetDateTime authoredTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712970612-04:00", comments="Source field: zfgbb.wiki_page_revision.author_name")
    private String authorName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712990761-04:00", comments="Source field: zfgbb.wiki_page_revision.content_size")
    private Integer contentSize;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.713055939-04:00", comments="Source field: zfgbb.wiki_page_revision.status")
    private String status;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712729289-04:00", comments="Source field: zfgbb.wiki_page_revision.wiki_page_revision_id")
    public Integer getWikiPageRevisionId() {
        return wikiPageRevisionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712762338-04:00", comments="Source field: zfgbb.wiki_page_revision.wiki_page_revision_id")
    public void setWikiPageRevisionId(Integer wikiPageRevisionId) {
        this.wikiPageRevisionId = wikiPageRevisionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712780238-04:00", comments="Source field: zfgbb.wiki_page_revision.wiki_page_id")
    public Integer getWikiPageId() {
        return wikiPageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712787828-04:00", comments="Source field: zfgbb.wiki_page_revision.wiki_page_id")
    public void setWikiPageId(Integer wikiPageId) {
        this.wikiPageId = wikiPageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712800677-04:00", comments="Source field: zfgbb.wiki_page_revision.content")
    public String getContent() {
        return content;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712810837-04:00", comments="Source field: zfgbb.wiki_page_revision.content")
    public void setContent(String content) {
        this.content = content;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712823017-04:00", comments="Source field: zfgbb.wiki_page_revision.content_format")
    public String getContentFormat() {
        return contentFormat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712831316-04:00", comments="Source field: zfgbb.wiki_page_revision.content_format")
    public void setContentFormat(String contentFormat) {
        this.contentFormat = contentFormat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712842956-04:00", comments="Source field: zfgbb.wiki_page_revision.summary")
    public String getSummary() {
        return summary;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712850646-04:00", comments="Source field: zfgbb.wiki_page_revision.summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712862395-04:00", comments="Source field: zfgbb.wiki_page_revision.author_user_id")
    public Integer getAuthorUserId() {
        return authorUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712869115-04:00", comments="Source field: zfgbb.wiki_page_revision.author_user_id")
    public void setAuthorUserId(Integer authorUserId) {
        this.authorUserId = authorUserId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712880785-04:00", comments="Source field: zfgbb.wiki_page_revision.current_flag")
    public Boolean getCurrentFlag() {
        return currentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712887175-04:00", comments="Source field: zfgbb.wiki_page_revision.current_flag")
    public void setCurrentFlag(Boolean currentFlag) {
        this.currentFlag = currentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712899204-04:00", comments="Source field: zfgbb.wiki_page_revision.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712905274-04:00", comments="Source field: zfgbb.wiki_page_revision.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712919844-04:00", comments="Source field: zfgbb.wiki_page_revision.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712925753-04:00", comments="Source field: zfgbb.wiki_page_revision.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712938633-04:00", comments="Source field: zfgbb.wiki_page_revision.migration_hash")
    public String getMigrationHash() {
        return migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712946663-04:00", comments="Source field: zfgbb.wiki_page_revision.migration_hash")
    public void setMigrationHash(String migrationHash) {
        this.migrationHash = migrationHash;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712959032-04:00", comments="Source field: zfgbb.wiki_page_revision.authored_ts")
    public OffsetDateTime getAuthoredTs() {
        return authoredTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712965292-04:00", comments="Source field: zfgbb.wiki_page_revision.authored_ts")
    public void setAuthoredTs(OffsetDateTime authoredTs) {
        this.authoredTs = authoredTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712977672-04:00", comments="Source field: zfgbb.wiki_page_revision.author_name")
    public String getAuthorName() {
        return authorName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.712985381-04:00", comments="Source field: zfgbb.wiki_page_revision.author_name")
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.71304126-04:00", comments="Source field: zfgbb.wiki_page_revision.content_size")
    public Integer getContentSize() {
        return contentSize;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.713049779-04:00", comments="Source field: zfgbb.wiki_page_revision.content_size")
    public void setContentSize(Integer contentSize) {
        this.contentSize = contentSize;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.713061699-04:00", comments="Source field: zfgbb.wiki_page_revision.status")
    public String getStatus() {
        return status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.713069919-04:00", comments="Source field: zfgbb.wiki_page_revision.status")
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Integer getPkId() {
        return wikiPageRevisionId;
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