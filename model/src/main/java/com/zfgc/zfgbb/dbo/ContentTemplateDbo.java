package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class ContentTemplateDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733120685-04:00", comments="Source field: zfgbb.content_template.content_template_id")
    private Integer contentTemplateId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733140624-04:00", comments="Source field: zfgbb.content_template.code")
    private String code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733158654-04:00", comments="Source field: zfgbb.content_template.content_format")
    private String contentFormat;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733173263-04:00", comments="Source field: zfgbb.content_template.scope")
    private String scope;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733187543-04:00", comments="Source field: zfgbb.content_template.source")
    private String source;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733211802-04:00", comments="Source field: zfgbb.content_template.body")
    private String body;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733228531-04:00", comments="Source field: zfgbb.content_template.wiki_page_id")
    private Integer wikiPageId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733242441-04:00", comments="Source field: zfgbb.content_template.created_ts")
    private OffsetDateTime createdTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733256881-04:00", comments="Source field: zfgbb.content_template.updated_ts")
    private OffsetDateTime updatedTs;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733129285-04:00", comments="Source field: zfgbb.content_template.content_template_id")
    public Integer getContentTemplateId() {
        return contentTemplateId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733135374-04:00", comments="Source field: zfgbb.content_template.content_template_id")
    public void setContentTemplateId(Integer contentTemplateId) {
        this.contentTemplateId = contentTemplateId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733147934-04:00", comments="Source field: zfgbb.content_template.code")
    public String getCode() {
        return code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733153874-04:00", comments="Source field: zfgbb.content_template.code")
    public void setCode(String code) {
        this.code = code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733163674-04:00", comments="Source field: zfgbb.content_template.content_format")
    public String getContentFormat() {
        return contentFormat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733168713-04:00", comments="Source field: zfgbb.content_template.content_format")
    public void setContentFormat(String contentFormat) {
        this.contentFormat = contentFormat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733177963-04:00", comments="Source field: zfgbb.content_template.scope")
    public String getScope() {
        return scope;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733183063-04:00", comments="Source field: zfgbb.content_template.scope")
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733192283-04:00", comments="Source field: zfgbb.content_template.source")
    public String getSource() {
        return source;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733206482-04:00", comments="Source field: zfgbb.content_template.source")
    public void setSource(String source) {
        this.source = source;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733217732-04:00", comments="Source field: zfgbb.content_template.body")
    public String getBody() {
        return body;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733223882-04:00", comments="Source field: zfgbb.content_template.body")
    public void setBody(String body) {
        this.body = body;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733233441-04:00", comments="Source field: zfgbb.content_template.wiki_page_id")
    public Integer getWikiPageId() {
        return wikiPageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733238091-04:00", comments="Source field: zfgbb.content_template.wiki_page_id")
    public void setWikiPageId(Integer wikiPageId) {
        this.wikiPageId = wikiPageId;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733247741-04:00", comments="Source field: zfgbb.content_template.created_ts")
    public OffsetDateTime getCreatedTs() {
        return createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.733252461-04:00", comments="Source field: zfgbb.content_template.created_ts")
    public void setCreatedTs(OffsetDateTime createdTs) {
        this.createdTs = createdTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73326189-04:00", comments="Source field: zfgbb.content_template.updated_ts")
    public OffsetDateTime getUpdatedTs() {
        return updatedTs;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.73326657-04:00", comments="Source field: zfgbb.content_template.updated_ts")
    public void setUpdatedTs(OffsetDateTime updatedTs) {
        this.updatedTs = updatedTs;
    }

    @Override
    public Integer getPkId() {
        return contentTemplateId;
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