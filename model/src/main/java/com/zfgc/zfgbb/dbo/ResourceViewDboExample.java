package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResourceViewDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718954456-04:00", comments="Source Table: zfgbb.resource_view")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718967985-04:00", comments="Source Table: zfgbb.resource_view")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718983655-04:00", comments="Source Table: zfgbb.resource_view")
    protected List<Criteria> oredCriteria;

    protected Integer limit;

    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718946436-04:00", comments="Source Table: zfgbb.resource_view")
    public ResourceViewDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718959326-04:00", comments="Source Table: zfgbb.resource_view")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718963575-04:00", comments="Source Table: zfgbb.resource_view")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718970885-04:00", comments="Source Table: zfgbb.resource_view")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718974985-04:00", comments="Source Table: zfgbb.resource_view")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718990595-04:00", comments="Source Table: zfgbb.resource_view")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718995064-04:00", comments="Source Table: zfgbb.resource_view")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.718999784-04:00", comments="Source Table: zfgbb.resource_view")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719004114-04:00", comments="Source Table: zfgbb.resource_view")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719008144-04:00", comments="Source Table: zfgbb.resource_view")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719010634-04:00", comments="Source Table: zfgbb.resource_view")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719016774-04:00", comments="Source Table: zfgbb.resource_view")
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andContentEntityIdIsNull() {
            addCriterion("content_entity_id is null");
            return (Criteria) this;
        }

        public Criteria andContentEntityIdIsNotNull() {
            addCriterion("content_entity_id is not null");
            return (Criteria) this;
        }

        public Criteria andContentEntityIdEqualTo(Integer value) {
            addCriterion("content_entity_id =", value, "contentEntityId");
            return (Criteria) this;
        }

        public Criteria andContentEntityIdNotEqualTo(Integer value) {
            addCriterion("content_entity_id <>", value, "contentEntityId");
            return (Criteria) this;
        }

        public Criteria andContentEntityIdGreaterThan(Integer value) {
            addCriterion("content_entity_id >", value, "contentEntityId");
            return (Criteria) this;
        }

        public Criteria andContentEntityIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("content_entity_id >=", value, "contentEntityId");
            return (Criteria) this;
        }

        public Criteria andContentEntityIdLessThan(Integer value) {
            addCriterion("content_entity_id <", value, "contentEntityId");
            return (Criteria) this;
        }

        public Criteria andContentEntityIdLessThanOrEqualTo(Integer value) {
            addCriterion("content_entity_id <=", value, "contentEntityId");
            return (Criteria) this;
        }

        public Criteria andContentEntityIdIn(List<Integer> values) {
            addCriterion("content_entity_id in", values, "contentEntityId");
            return (Criteria) this;
        }

        public Criteria andContentEntityIdNotIn(List<Integer> values) {
            addCriterion("content_entity_id not in", values, "contentEntityId");
            return (Criteria) this;
        }

        public Criteria andContentEntityIdBetween(Integer value1, Integer value2) {
            addCriterion("content_entity_id between", value1, value2, "contentEntityId");
            return (Criteria) this;
        }

        public Criteria andContentEntityIdNotBetween(Integer value1, Integer value2) {
            addCriterion("content_entity_id not between", value1, value2, "contentEntityId");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andSlugIsNull() {
            addCriterion("slug is null");
            return (Criteria) this;
        }

        public Criteria andSlugIsNotNull() {
            addCriterion("slug is not null");
            return (Criteria) this;
        }

        public Criteria andSlugEqualTo(String value) {
            addCriterion("slug =", value, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugNotEqualTo(String value) {
            addCriterion("slug <>", value, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugGreaterThan(String value) {
            addCriterion("slug >", value, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugGreaterThanOrEqualTo(String value) {
            addCriterion("slug >=", value, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugLessThan(String value) {
            addCriterion("slug <", value, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugLessThanOrEqualTo(String value) {
            addCriterion("slug <=", value, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugLike(String value) {
            addCriterion("slug like", value, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugNotLike(String value) {
            addCriterion("slug not like", value, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugIn(List<String> values) {
            addCriterion("slug in", values, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugNotIn(List<String> values) {
            addCriterion("slug not in", values, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugBetween(String value1, String value2) {
            addCriterion("slug between", value1, value2, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugNotBetween(String value1, String value2) {
            addCriterion("slug not between", value1, value2, "slug");
            return (Criteria) this;
        }

        public Criteria andSummaryIsNull() {
            addCriterion("summary is null");
            return (Criteria) this;
        }

        public Criteria andSummaryIsNotNull() {
            addCriterion("summary is not null");
            return (Criteria) this;
        }

        public Criteria andSummaryEqualTo(String value) {
            addCriterion("summary =", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotEqualTo(String value) {
            addCriterion("summary <>", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryGreaterThan(String value) {
            addCriterion("summary >", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryGreaterThanOrEqualTo(String value) {
            addCriterion("summary >=", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLessThan(String value) {
            addCriterion("summary <", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLessThanOrEqualTo(String value) {
            addCriterion("summary <=", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryLike(String value) {
            addCriterion("summary like", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotLike(String value) {
            addCriterion("summary not like", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryIn(List<String> values) {
            addCriterion("summary in", values, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotIn(List<String> values) {
            addCriterion("summary not in", values, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryBetween(String value1, String value2) {
            addCriterion("summary between", value1, value2, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotBetween(String value1, String value2) {
            addCriterion("summary not between", value1, value2, "summary");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdIsNull() {
            addCriterion("wiki_page_id is null");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdIsNotNull() {
            addCriterion("wiki_page_id is not null");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdEqualTo(Integer value) {
            addCriterion("wiki_page_id =", value, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdNotEqualTo(Integer value) {
            addCriterion("wiki_page_id <>", value, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdGreaterThan(Integer value) {
            addCriterion("wiki_page_id >", value, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("wiki_page_id >=", value, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdLessThan(Integer value) {
            addCriterion("wiki_page_id <", value, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdLessThanOrEqualTo(Integer value) {
            addCriterion("wiki_page_id <=", value, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdIn(List<Integer> values) {
            addCriterion("wiki_page_id in", values, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdNotIn(List<Integer> values) {
            addCriterion("wiki_page_id not in", values, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdBetween(Integer value1, Integer value2) {
            addCriterion("wiki_page_id between", value1, value2, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdNotBetween(Integer value1, Integer value2) {
            addCriterion("wiki_page_id not between", value1, value2, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andThreadIdIsNull() {
            addCriterion("thread_id is null");
            return (Criteria) this;
        }

        public Criteria andThreadIdIsNotNull() {
            addCriterion("thread_id is not null");
            return (Criteria) this;
        }

        public Criteria andThreadIdEqualTo(Integer value) {
            addCriterion("thread_id =", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdNotEqualTo(Integer value) {
            addCriterion("thread_id <>", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdGreaterThan(Integer value) {
            addCriterion("thread_id >", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("thread_id >=", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdLessThan(Integer value) {
            addCriterion("thread_id <", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdLessThanOrEqualTo(Integer value) {
            addCriterion("thread_id <=", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdIn(List<Integer> values) {
            addCriterion("thread_id in", values, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdNotIn(List<Integer> values) {
            addCriterion("thread_id not in", values, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdBetween(Integer value1, Integer value2) {
            addCriterion("thread_id between", value1, value2, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdNotBetween(Integer value1, Integer value2) {
            addCriterion("thread_id not between", value1, value2, "threadId");
            return (Criteria) this;
        }

        public Criteria andPreviewContentResourceIdIsNull() {
            addCriterion("preview_content_resource_id is null");
            return (Criteria) this;
        }

        public Criteria andPreviewContentResourceIdIsNotNull() {
            addCriterion("preview_content_resource_id is not null");
            return (Criteria) this;
        }

        public Criteria andPreviewContentResourceIdEqualTo(Integer value) {
            addCriterion("preview_content_resource_id =", value, "previewContentResourceId");
            return (Criteria) this;
        }

        public Criteria andPreviewContentResourceIdNotEqualTo(Integer value) {
            addCriterion("preview_content_resource_id <>", value, "previewContentResourceId");
            return (Criteria) this;
        }

        public Criteria andPreviewContentResourceIdGreaterThan(Integer value) {
            addCriterion("preview_content_resource_id >", value, "previewContentResourceId");
            return (Criteria) this;
        }

        public Criteria andPreviewContentResourceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("preview_content_resource_id >=", value, "previewContentResourceId");
            return (Criteria) this;
        }

        public Criteria andPreviewContentResourceIdLessThan(Integer value) {
            addCriterion("preview_content_resource_id <", value, "previewContentResourceId");
            return (Criteria) this;
        }

        public Criteria andPreviewContentResourceIdLessThanOrEqualTo(Integer value) {
            addCriterion("preview_content_resource_id <=", value, "previewContentResourceId");
            return (Criteria) this;
        }

        public Criteria andPreviewContentResourceIdIn(List<Integer> values) {
            addCriterion("preview_content_resource_id in", values, "previewContentResourceId");
            return (Criteria) this;
        }

        public Criteria andPreviewContentResourceIdNotIn(List<Integer> values) {
            addCriterion("preview_content_resource_id not in", values, "previewContentResourceId");
            return (Criteria) this;
        }

        public Criteria andPreviewContentResourceIdBetween(Integer value1, Integer value2) {
            addCriterion("preview_content_resource_id between", value1, value2, "previewContentResourceId");
            return (Criteria) this;
        }

        public Criteria andPreviewContentResourceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("preview_content_resource_id not between", value1, value2, "previewContentResourceId");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIdIsNull() {
            addCriterion("created_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIdIsNotNull() {
            addCriterion("created_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIdEqualTo(Integer value) {
            addCriterion("created_user_id =", value, "createdUserId");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIdNotEqualTo(Integer value) {
            addCriterion("created_user_id <>", value, "createdUserId");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIdGreaterThan(Integer value) {
            addCriterion("created_user_id >", value, "createdUserId");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("created_user_id >=", value, "createdUserId");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIdLessThan(Integer value) {
            addCriterion("created_user_id <", value, "createdUserId");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("created_user_id <=", value, "createdUserId");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIdIn(List<Integer> values) {
            addCriterion("created_user_id in", values, "createdUserId");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIdNotIn(List<Integer> values) {
            addCriterion("created_user_id not in", values, "createdUserId");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIdBetween(Integer value1, Integer value2) {
            addCriterion("created_user_id between", value1, value2, "createdUserId");
            return (Criteria) this;
        }

        public Criteria andCreatedUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("created_user_id not between", value1, value2, "createdUserId");
            return (Criteria) this;
        }

        public Criteria andViewCountIsNull() {
            addCriterion("view_count is null");
            return (Criteria) this;
        }

        public Criteria andViewCountIsNotNull() {
            addCriterion("view_count is not null");
            return (Criteria) this;
        }

        public Criteria andViewCountEqualTo(Integer value) {
            addCriterion("view_count =", value, "viewCount");
            return (Criteria) this;
        }

        public Criteria andViewCountNotEqualTo(Integer value) {
            addCriterion("view_count <>", value, "viewCount");
            return (Criteria) this;
        }

        public Criteria andViewCountGreaterThan(Integer value) {
            addCriterion("view_count >", value, "viewCount");
            return (Criteria) this;
        }

        public Criteria andViewCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("view_count >=", value, "viewCount");
            return (Criteria) this;
        }

        public Criteria andViewCountLessThan(Integer value) {
            addCriterion("view_count <", value, "viewCount");
            return (Criteria) this;
        }

        public Criteria andViewCountLessThanOrEqualTo(Integer value) {
            addCriterion("view_count <=", value, "viewCount");
            return (Criteria) this;
        }

        public Criteria andViewCountIn(List<Integer> values) {
            addCriterion("view_count in", values, "viewCount");
            return (Criteria) this;
        }

        public Criteria andViewCountNotIn(List<Integer> values) {
            addCriterion("view_count not in", values, "viewCount");
            return (Criteria) this;
        }

        public Criteria andViewCountBetween(Integer value1, Integer value2) {
            addCriterion("view_count between", value1, value2, "viewCount");
            return (Criteria) this;
        }

        public Criteria andViewCountNotBetween(Integer value1, Integer value2) {
            addCriterion("view_count not between", value1, value2, "viewCount");
            return (Criteria) this;
        }

        public Criteria andDownloadCountIsNull() {
            addCriterion("download_count is null");
            return (Criteria) this;
        }

        public Criteria andDownloadCountIsNotNull() {
            addCriterion("download_count is not null");
            return (Criteria) this;
        }

        public Criteria andDownloadCountEqualTo(Integer value) {
            addCriterion("download_count =", value, "downloadCount");
            return (Criteria) this;
        }

        public Criteria andDownloadCountNotEqualTo(Integer value) {
            addCriterion("download_count <>", value, "downloadCount");
            return (Criteria) this;
        }

        public Criteria andDownloadCountGreaterThan(Integer value) {
            addCriterion("download_count >", value, "downloadCount");
            return (Criteria) this;
        }

        public Criteria andDownloadCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("download_count >=", value, "downloadCount");
            return (Criteria) this;
        }

        public Criteria andDownloadCountLessThan(Integer value) {
            addCriterion("download_count <", value, "downloadCount");
            return (Criteria) this;
        }

        public Criteria andDownloadCountLessThanOrEqualTo(Integer value) {
            addCriterion("download_count <=", value, "downloadCount");
            return (Criteria) this;
        }

        public Criteria andDownloadCountIn(List<Integer> values) {
            addCriterion("download_count in", values, "downloadCount");
            return (Criteria) this;
        }

        public Criteria andDownloadCountNotIn(List<Integer> values) {
            addCriterion("download_count not in", values, "downloadCount");
            return (Criteria) this;
        }

        public Criteria andDownloadCountBetween(Integer value1, Integer value2) {
            addCriterion("download_count between", value1, value2, "downloadCount");
            return (Criteria) this;
        }

        public Criteria andDownloadCountNotBetween(Integer value1, Integer value2) {
            addCriterion("download_count not between", value1, value2, "downloadCount");
            return (Criteria) this;
        }

        public Criteria andRatingIsNull() {
            addCriterion("rating is null");
            return (Criteria) this;
        }

        public Criteria andRatingIsNotNull() {
            addCriterion("rating is not null");
            return (Criteria) this;
        }

        public Criteria andRatingEqualTo(Float value) {
            addCriterion("rating =", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingNotEqualTo(Float value) {
            addCriterion("rating <>", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingGreaterThan(Float value) {
            addCriterion("rating >", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingGreaterThanOrEqualTo(Float value) {
            addCriterion("rating >=", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingLessThan(Float value) {
            addCriterion("rating <", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingLessThanOrEqualTo(Float value) {
            addCriterion("rating <=", value, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingIn(List<Float> values) {
            addCriterion("rating in", values, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingNotIn(List<Float> values) {
            addCriterion("rating not in", values, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingBetween(Float value1, Float value2) {
            addCriterion("rating between", value1, value2, "rating");
            return (Criteria) this;
        }

        public Criteria andRatingNotBetween(Float value1, Float value2) {
            addCriterion("rating not between", value1, value2, "rating");
            return (Criteria) this;
        }

        public Criteria andVoteCountIsNull() {
            addCriterion("vote_count is null");
            return (Criteria) this;
        }

        public Criteria andVoteCountIsNotNull() {
            addCriterion("vote_count is not null");
            return (Criteria) this;
        }

        public Criteria andVoteCountEqualTo(Integer value) {
            addCriterion("vote_count =", value, "voteCount");
            return (Criteria) this;
        }

        public Criteria andVoteCountNotEqualTo(Integer value) {
            addCriterion("vote_count <>", value, "voteCount");
            return (Criteria) this;
        }

        public Criteria andVoteCountGreaterThan(Integer value) {
            addCriterion("vote_count >", value, "voteCount");
            return (Criteria) this;
        }

        public Criteria andVoteCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("vote_count >=", value, "voteCount");
            return (Criteria) this;
        }

        public Criteria andVoteCountLessThan(Integer value) {
            addCriterion("vote_count <", value, "voteCount");
            return (Criteria) this;
        }

        public Criteria andVoteCountLessThanOrEqualTo(Integer value) {
            addCriterion("vote_count <=", value, "voteCount");
            return (Criteria) this;
        }

        public Criteria andVoteCountIn(List<Integer> values) {
            addCriterion("vote_count in", values, "voteCount");
            return (Criteria) this;
        }

        public Criteria andVoteCountNotIn(List<Integer> values) {
            addCriterion("vote_count not in", values, "voteCount");
            return (Criteria) this;
        }

        public Criteria andVoteCountBetween(Integer value1, Integer value2) {
            addCriterion("vote_count between", value1, value2, "voteCount");
            return (Criteria) this;
        }

        public Criteria andVoteCountNotBetween(Integer value1, Integer value2) {
            addCriterion("vote_count not between", value1, value2, "voteCount");
            return (Criteria) this;
        }

        public Criteria andPublishedTsIsNull() {
            addCriterion("published_ts is null");
            return (Criteria) this;
        }

        public Criteria andPublishedTsIsNotNull() {
            addCriterion("published_ts is not null");
            return (Criteria) this;
        }

        public Criteria andPublishedTsEqualTo(OffsetDateTime value) {
            addCriterion("published_ts =", value, "publishedTs");
            return (Criteria) this;
        }

        public Criteria andPublishedTsNotEqualTo(OffsetDateTime value) {
            addCriterion("published_ts <>", value, "publishedTs");
            return (Criteria) this;
        }

        public Criteria andPublishedTsGreaterThan(OffsetDateTime value) {
            addCriterion("published_ts >", value, "publishedTs");
            return (Criteria) this;
        }

        public Criteria andPublishedTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("published_ts >=", value, "publishedTs");
            return (Criteria) this;
        }

        public Criteria andPublishedTsLessThan(OffsetDateTime value) {
            addCriterion("published_ts <", value, "publishedTs");
            return (Criteria) this;
        }

        public Criteria andPublishedTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("published_ts <=", value, "publishedTs");
            return (Criteria) this;
        }

        public Criteria andPublishedTsIn(List<OffsetDateTime> values) {
            addCriterion("published_ts in", values, "publishedTs");
            return (Criteria) this;
        }

        public Criteria andPublishedTsNotIn(List<OffsetDateTime> values) {
            addCriterion("published_ts not in", values, "publishedTs");
            return (Criteria) this;
        }

        public Criteria andPublishedTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("published_ts between", value1, value2, "publishedTs");
            return (Criteria) this;
        }

        public Criteria andPublishedTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("published_ts not between", value1, value2, "publishedTs");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTsIsNull() {
            addCriterion("last_updated_ts is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTsIsNotNull() {
            addCriterion("last_updated_ts is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTsEqualTo(OffsetDateTime value) {
            addCriterion("last_updated_ts =", value, "lastUpdatedTs");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTsNotEqualTo(OffsetDateTime value) {
            addCriterion("last_updated_ts <>", value, "lastUpdatedTs");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTsGreaterThan(OffsetDateTime value) {
            addCriterion("last_updated_ts >", value, "lastUpdatedTs");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("last_updated_ts >=", value, "lastUpdatedTs");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTsLessThan(OffsetDateTime value) {
            addCriterion("last_updated_ts <", value, "lastUpdatedTs");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("last_updated_ts <=", value, "lastUpdatedTs");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTsIn(List<OffsetDateTime> values) {
            addCriterion("last_updated_ts in", values, "lastUpdatedTs");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTsNotIn(List<OffsetDateTime> values) {
            addCriterion("last_updated_ts not in", values, "lastUpdatedTs");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("last_updated_ts between", value1, value2, "lastUpdatedTs");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("last_updated_ts not between", value1, value2, "lastUpdatedTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsIsNull() {
            addCriterion("created_ts is null");
            return (Criteria) this;
        }

        public Criteria andCreatedTsIsNotNull() {
            addCriterion("created_ts is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedTsEqualTo(OffsetDateTime value) {
            addCriterion("created_ts =", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsNotEqualTo(OffsetDateTime value) {
            addCriterion("created_ts <>", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsGreaterThan(OffsetDateTime value) {
            addCriterion("created_ts >", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("created_ts >=", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsLessThan(OffsetDateTime value) {
            addCriterion("created_ts <", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("created_ts <=", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsIn(List<OffsetDateTime> values) {
            addCriterion("created_ts in", values, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsNotIn(List<OffsetDateTime> values) {
            addCriterion("created_ts not in", values, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("created_ts between", value1, value2, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("created_ts not between", value1, value2, "createdTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsIsNull() {
            addCriterion("updated_ts is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsIsNotNull() {
            addCriterion("updated_ts is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsEqualTo(OffsetDateTime value) {
            addCriterion("updated_ts =", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsNotEqualTo(OffsetDateTime value) {
            addCriterion("updated_ts <>", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsGreaterThan(OffsetDateTime value) {
            addCriterion("updated_ts >", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("updated_ts >=", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsLessThan(OffsetDateTime value) {
            addCriterion("updated_ts <", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("updated_ts <=", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsIn(List<OffsetDateTime> values) {
            addCriterion("updated_ts in", values, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsNotIn(List<OffsetDateTime> values) {
            addCriterion("updated_ts not in", values, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("updated_ts between", value1, value2, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("updated_ts not between", value1, value2, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andMigrationHashIsNull() {
            addCriterion("migration_hash is null");
            return (Criteria) this;
        }

        public Criteria andMigrationHashIsNotNull() {
            addCriterion("migration_hash is not null");
            return (Criteria) this;
        }

        public Criteria andMigrationHashEqualTo(String value) {
            addCriterion("migration_hash =", value, "migrationHash");
            return (Criteria) this;
        }

        public Criteria andMigrationHashNotEqualTo(String value) {
            addCriterion("migration_hash <>", value, "migrationHash");
            return (Criteria) this;
        }

        public Criteria andMigrationHashGreaterThan(String value) {
            addCriterion("migration_hash >", value, "migrationHash");
            return (Criteria) this;
        }

        public Criteria andMigrationHashGreaterThanOrEqualTo(String value) {
            addCriterion("migration_hash >=", value, "migrationHash");
            return (Criteria) this;
        }

        public Criteria andMigrationHashLessThan(String value) {
            addCriterion("migration_hash <", value, "migrationHash");
            return (Criteria) this;
        }

        public Criteria andMigrationHashLessThanOrEqualTo(String value) {
            addCriterion("migration_hash <=", value, "migrationHash");
            return (Criteria) this;
        }

        public Criteria andMigrationHashLike(String value) {
            addCriterion("migration_hash like", value, "migrationHash");
            return (Criteria) this;
        }

        public Criteria andMigrationHashNotLike(String value) {
            addCriterion("migration_hash not like", value, "migrationHash");
            return (Criteria) this;
        }

        public Criteria andMigrationHashIn(List<String> values) {
            addCriterion("migration_hash in", values, "migrationHash");
            return (Criteria) this;
        }

        public Criteria andMigrationHashNotIn(List<String> values) {
            addCriterion("migration_hash not in", values, "migrationHash");
            return (Criteria) this;
        }

        public Criteria andMigrationHashBetween(String value1, String value2) {
            addCriterion("migration_hash between", value1, value2, "migrationHash");
            return (Criteria) this;
        }

        public Criteria andMigrationHashNotBetween(String value1, String value2) {
            addCriterion("migration_hash not between", value1, value2, "migrationHash");
            return (Criteria) this;
        }

        public Criteria andAuthorNameIsNull() {
            addCriterion("author_name is null");
            return (Criteria) this;
        }

        public Criteria andAuthorNameIsNotNull() {
            addCriterion("author_name is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorNameEqualTo(String value) {
            addCriterion("author_name =", value, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameNotEqualTo(String value) {
            addCriterion("author_name <>", value, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameGreaterThan(String value) {
            addCriterion("author_name >", value, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameGreaterThanOrEqualTo(String value) {
            addCriterion("author_name >=", value, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameLessThan(String value) {
            addCriterion("author_name <", value, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameLessThanOrEqualTo(String value) {
            addCriterion("author_name <=", value, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameLike(String value) {
            addCriterion("author_name like", value, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameNotLike(String value) {
            addCriterion("author_name not like", value, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameIn(List<String> values) {
            addCriterion("author_name in", values, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameNotIn(List<String> values) {
            addCriterion("author_name not in", values, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameBetween(String value1, String value2) {
            addCriterion("author_name between", value1, value2, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameNotBetween(String value1, String value2) {
            addCriterion("author_name not between", value1, value2, "authorName");
            return (Criteria) this;
        }

        public Criteria andResourceTypeIsNull() {
            addCriterion("resource_type is null");
            return (Criteria) this;
        }

        public Criteria andResourceTypeIsNotNull() {
            addCriterion("resource_type is not null");
            return (Criteria) this;
        }

        public Criteria andResourceTypeEqualTo(String value) {
            addCriterion("resource_type =", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotEqualTo(String value) {
            addCriterion("resource_type <>", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeGreaterThan(String value) {
            addCriterion("resource_type >", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeGreaterThanOrEqualTo(String value) {
            addCriterion("resource_type >=", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeLessThan(String value) {
            addCriterion("resource_type <", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeLessThanOrEqualTo(String value) {
            addCriterion("resource_type <=", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeLike(String value) {
            addCriterion("resource_type like", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotLike(String value) {
            addCriterion("resource_type not like", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeIn(List<String> values) {
            addCriterion("resource_type in", values, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotIn(List<String> values) {
            addCriterion("resource_type not in", values, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeBetween(String value1, String value2) {
            addCriterion("resource_type between", value1, value2, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotBetween(String value1, String value2) {
            addCriterion("resource_type not between", value1, value2, "resourceType");
            return (Criteria) this;
        }

        public Criteria andDownloadContentResourceIdIsNull() {
            addCriterion("download_content_resource_id is null");
            return (Criteria) this;
        }

        public Criteria andDownloadContentResourceIdIsNotNull() {
            addCriterion("download_content_resource_id is not null");
            return (Criteria) this;
        }

        public Criteria andDownloadContentResourceIdEqualTo(Integer value) {
            addCriterion("download_content_resource_id =", value, "downloadContentResourceId");
            return (Criteria) this;
        }

        public Criteria andDownloadContentResourceIdNotEqualTo(Integer value) {
            addCriterion("download_content_resource_id <>", value, "downloadContentResourceId");
            return (Criteria) this;
        }

        public Criteria andDownloadContentResourceIdGreaterThan(Integer value) {
            addCriterion("download_content_resource_id >", value, "downloadContentResourceId");
            return (Criteria) this;
        }

        public Criteria andDownloadContentResourceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("download_content_resource_id >=", value, "downloadContentResourceId");
            return (Criteria) this;
        }

        public Criteria andDownloadContentResourceIdLessThan(Integer value) {
            addCriterion("download_content_resource_id <", value, "downloadContentResourceId");
            return (Criteria) this;
        }

        public Criteria andDownloadContentResourceIdLessThanOrEqualTo(Integer value) {
            addCriterion("download_content_resource_id <=", value, "downloadContentResourceId");
            return (Criteria) this;
        }

        public Criteria andDownloadContentResourceIdIn(List<Integer> values) {
            addCriterion("download_content_resource_id in", values, "downloadContentResourceId");
            return (Criteria) this;
        }

        public Criteria andDownloadContentResourceIdNotIn(List<Integer> values) {
            addCriterion("download_content_resource_id not in", values, "downloadContentResourceId");
            return (Criteria) this;
        }

        public Criteria andDownloadContentResourceIdBetween(Integer value1, Integer value2) {
            addCriterion("download_content_resource_id between", value1, value2, "downloadContentResourceId");
            return (Criteria) this;
        }

        public Criteria andDownloadContentResourceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("download_content_resource_id not between", value1, value2, "downloadContentResourceId");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlIsNull() {
            addCriterion("download_url is null");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlIsNotNull() {
            addCriterion("download_url is not null");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlEqualTo(String value) {
            addCriterion("download_url =", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotEqualTo(String value) {
            addCriterion("download_url <>", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlGreaterThan(String value) {
            addCriterion("download_url >", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlGreaterThanOrEqualTo(String value) {
            addCriterion("download_url >=", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlLessThan(String value) {
            addCriterion("download_url <", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlLessThanOrEqualTo(String value) {
            addCriterion("download_url <=", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlLike(String value) {
            addCriterion("download_url like", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotLike(String value) {
            addCriterion("download_url not like", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlIn(List<String> values) {
            addCriterion("download_url in", values, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotIn(List<String> values) {
            addCriterion("download_url not in", values, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlBetween(String value1, String value2) {
            addCriterion("download_url between", value1, value2, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotBetween(String value1, String value2) {
            addCriterion("download_url not between", value1, value2, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andFileSizeIsNull() {
            addCriterion("file_size is null");
            return (Criteria) this;
        }

        public Criteria andFileSizeIsNotNull() {
            addCriterion("file_size is not null");
            return (Criteria) this;
        }

        public Criteria andFileSizeEqualTo(Long value) {
            addCriterion("file_size =", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeNotEqualTo(Long value) {
            addCriterion("file_size <>", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeGreaterThan(Long value) {
            addCriterion("file_size >", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeGreaterThanOrEqualTo(Long value) {
            addCriterion("file_size >=", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeLessThan(Long value) {
            addCriterion("file_size <", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeLessThanOrEqualTo(Long value) {
            addCriterion("file_size <=", value, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeIn(List<Long> values) {
            addCriterion("file_size in", values, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeNotIn(List<Long> values) {
            addCriterion("file_size not in", values, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeBetween(Long value1, Long value2) {
            addCriterion("file_size between", value1, value2, "fileSize");
            return (Criteria) this;
        }

        public Criteria andFileSizeNotBetween(Long value1, Long value2) {
            addCriterion("file_size not between", value1, value2, "fileSize");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719485879-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.719495839-04:00", comments="Source Table: zfgbb.resource_view")
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}