package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class WikiRevisionRefDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751790074-04:00", comments="Source field: zfgbb.wiki_revision_ref.wiki_page_revision_id")
    private Integer wikiPageRevisionId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751809083-04:00", comments="Source field: zfgbb.wiki_revision_ref.wiki_page_id")
    private Integer wikiPageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751823373-04:00", comments="Source field: zfgbb.wiki_revision_ref.authored_ts")
    private OffsetDateTime authoredTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751840552-04:00", comments="Source field: zfgbb.wiki_revision_ref.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751854952-04:00", comments="Source field: zfgbb.wiki_revision_ref.author_name")
    private String authorName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751868861-04:00", comments="Source field: zfgbb.wiki_revision_ref.summary")
    private String summary;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751883441-04:00", comments="Source field: zfgbb.wiki_revision_ref.current_flag")
    private Boolean currentFlag;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751897031-04:00", comments="Source field: zfgbb.wiki_revision_ref.content_size")
    private Integer contentSize;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.75191043-04:00", comments="Source field: zfgbb.wiki_revision_ref.status")
    private String status;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751798174-04:00", comments="Source field: zfgbb.wiki_revision_ref.wiki_page_revision_id")
    public Integer getWikiPageRevisionId() {
        return wikiPageRevisionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751804143-04:00", comments="Source field: zfgbb.wiki_revision_ref.wiki_page_revision_id")
    public void setWikiPageRevisionId(Integer wikiPageRevisionId) {
        this.wikiPageRevisionId = wikiPageRevisionId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751814043-04:00", comments="Source field: zfgbb.wiki_revision_ref.wiki_page_id")
    public Integer getWikiPageId() {
        return wikiPageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751818923-04:00", comments="Source field: zfgbb.wiki_revision_ref.wiki_page_id")
    public void setWikiPageId(Integer wikiPageId) {
        this.wikiPageId = wikiPageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751829073-04:00", comments="Source field: zfgbb.wiki_revision_ref.authored_ts")
    public OffsetDateTime getAuthoredTs() {
        return authoredTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751833832-04:00", comments="Source field: zfgbb.wiki_revision_ref.authored_ts")
    public void setAuthoredTs(OffsetDateTime authoredTs) {
        this.authoredTs = authoredTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751845652-04:00", comments="Source field: zfgbb.wiki_revision_ref.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751850532-04:00", comments="Source field: zfgbb.wiki_revision_ref.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751859452-04:00", comments="Source field: zfgbb.wiki_revision_ref.author_name")
    public String getAuthorName() {
        return authorName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751864442-04:00", comments="Source field: zfgbb.wiki_revision_ref.author_name")
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751873661-04:00", comments="Source field: zfgbb.wiki_revision_ref.summary")
    public String getSummary() {
        return summary;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751878621-04:00", comments="Source field: zfgbb.wiki_revision_ref.summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751888141-04:00", comments="Source field: zfgbb.wiki_revision_ref.current_flag")
    public Boolean getCurrentFlag() {
        return currentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.751892731-04:00", comments="Source field: zfgbb.wiki_revision_ref.current_flag")
    public void setCurrentFlag(Boolean currentFlag) {
        this.currentFlag = currentFlag;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.75190144-04:00", comments="Source field: zfgbb.wiki_revision_ref.content_size")
    public Integer getContentSize() {
        return contentSize;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.75190624-04:00", comments="Source field: zfgbb.wiki_revision_ref.content_size")
    public void setContentSize(Integer contentSize) {
        this.contentSize = contentSize;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.75191503-04:00", comments="Source field: zfgbb.wiki_revision_ref.status")
    public String getStatus() {
        return status;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.75192008-04:00", comments="Source field: zfgbb.wiki_revision_ref.status")
    public void setStatus(String status) {
        this.status = status;
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
        return null;
    }
}