package com.zfgc.zfgbb.dbo;

import com.zfgc.zfgbb.persistence.LikePatterns;
import jakarta.annotation.Generated;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class WikiPageDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    protected Integer limit;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    public WikiPageDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    public Integer getLimit() {
        return limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    public Integer getOffset() {
        return offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
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

        public Criteria andNamespaceIsNull() {
            addCriterion("namespace is null");
            return (Criteria) this;
        }

        public Criteria andNamespaceIsNotNull() {
            addCriterion("namespace is not null");
            return (Criteria) this;
        }

        public Criteria andNamespaceEqualTo(String value) {
            addCriterion("namespace =", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceNotEqualTo(String value) {
            addCriterion("namespace <>", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceGreaterThan(String value) {
            addCriterion("namespace >", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceGreaterThanOrEqualTo(String value) {
            addCriterion("namespace >=", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceLessThan(String value) {
            addCriterion("namespace <", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceLessThanOrEqualTo(String value) {
            addCriterion("namespace <=", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceLike(String value) {
            addCriterion("namespace ilike", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceNotLike(String value) {
            addCriterion("namespace not ilike", value, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceIn(List<String> values) {
            addCriterion("namespace in", values, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceNotIn(List<String> values) {
            addCriterion("namespace not in", values, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceBetween(String value1, String value2) {
            addCriterion("namespace between", value1, value2, "namespace");
            return (Criteria) this;
        }

        public Criteria andNamespaceNotBetween(String value1, String value2) {
            addCriterion("namespace not between", value1, value2, "namespace");
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
            addCriterion("title ilike", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not ilike", value, "title");
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
            addCriterion("slug ilike", value, "slug");
            return (Criteria) this;
        }

        public Criteria andSlugNotLike(String value) {
            addCriterion("slug not ilike", value, "slug");
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

        public Criteria andRedirectToIsNull() {
            addCriterion("redirect_to is null");
            return (Criteria) this;
        }

        public Criteria andRedirectToIsNotNull() {
            addCriterion("redirect_to is not null");
            return (Criteria) this;
        }

        public Criteria andRedirectToEqualTo(String value) {
            addCriterion("redirect_to =", value, "redirectTo");
            return (Criteria) this;
        }

        public Criteria andRedirectToNotEqualTo(String value) {
            addCriterion("redirect_to <>", value, "redirectTo");
            return (Criteria) this;
        }

        public Criteria andRedirectToGreaterThan(String value) {
            addCriterion("redirect_to >", value, "redirectTo");
            return (Criteria) this;
        }

        public Criteria andRedirectToGreaterThanOrEqualTo(String value) {
            addCriterion("redirect_to >=", value, "redirectTo");
            return (Criteria) this;
        }

        public Criteria andRedirectToLessThan(String value) {
            addCriterion("redirect_to <", value, "redirectTo");
            return (Criteria) this;
        }

        public Criteria andRedirectToLessThanOrEqualTo(String value) {
            addCriterion("redirect_to <=", value, "redirectTo");
            return (Criteria) this;
        }

        public Criteria andRedirectToLike(String value) {
            addCriterion("redirect_to ilike", value, "redirectTo");
            return (Criteria) this;
        }

        public Criteria andRedirectToNotLike(String value) {
            addCriterion("redirect_to not ilike", value, "redirectTo");
            return (Criteria) this;
        }

        public Criteria andRedirectToIn(List<String> values) {
            addCriterion("redirect_to in", values, "redirectTo");
            return (Criteria) this;
        }

        public Criteria andRedirectToNotIn(List<String> values) {
            addCriterion("redirect_to not in", values, "redirectTo");
            return (Criteria) this;
        }

        public Criteria andRedirectToBetween(String value1, String value2) {
            addCriterion("redirect_to between", value1, value2, "redirectTo");
            return (Criteria) this;
        }

        public Criteria andRedirectToNotBetween(String value1, String value2) {
            addCriterion("redirect_to not between", value1, value2, "redirectTo");
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
            addCriterion("migration_hash ilike", value, "migrationHash");
            return (Criteria) this;
        }

        public Criteria andMigrationHashNotLike(String value) {
            addCriterion("migration_hash not ilike", value, "migrationHash");
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

        public Criteria andContentResourceIdIsNull() {
            addCriterion("content_resource_id is null");
            return (Criteria) this;
        }

        public Criteria andContentResourceIdIsNotNull() {
            addCriterion("content_resource_id is not null");
            return (Criteria) this;
        }

        public Criteria andContentResourceIdEqualTo(Integer value) {
            addCriterion("content_resource_id =", value, "contentResourceId");
            return (Criteria) this;
        }

        public Criteria andContentResourceIdNotEqualTo(Integer value) {
            addCriterion("content_resource_id <>", value, "contentResourceId");
            return (Criteria) this;
        }

        public Criteria andContentResourceIdGreaterThan(Integer value) {
            addCriterion("content_resource_id >", value, "contentResourceId");
            return (Criteria) this;
        }

        public Criteria andContentResourceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("content_resource_id >=", value, "contentResourceId");
            return (Criteria) this;
        }

        public Criteria andContentResourceIdLessThan(Integer value) {
            addCriterion("content_resource_id <", value, "contentResourceId");
            return (Criteria) this;
        }

        public Criteria andContentResourceIdLessThanOrEqualTo(Integer value) {
            addCriterion("content_resource_id <=", value, "contentResourceId");
            return (Criteria) this;
        }

        public Criteria andContentResourceIdIn(List<Integer> values) {
            addCriterion("content_resource_id in", values, "contentResourceId");
            return (Criteria) this;
        }

        public Criteria andContentResourceIdNotIn(List<Integer> values) {
            addCriterion("content_resource_id not in", values, "contentResourceId");
            return (Criteria) this;
        }

        public Criteria andContentResourceIdBetween(Integer value1, Integer value2) {
            addCriterion("content_resource_id between", value1, value2, "contentResourceId");
            return (Criteria) this;
        }

        public Criteria andContentResourceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("content_resource_id not between", value1, value2, "contentResourceId");
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

        public Criteria andNamespaceContains(String value) {
            addCriterion("namespace ilike", LikePatterns.contains(value), "namespace");
            return (Criteria) this;
        }

        public Criteria andTitleContains(String value) {
            addCriterion("title ilike", LikePatterns.contains(value), "title");
            return (Criteria) this;
        }

        public Criteria andSlugContains(String value) {
            addCriterion("slug ilike", LikePatterns.contains(value), "slug");
            return (Criteria) this;
        }

        public Criteria andRedirectToContains(String value) {
            addCriterion("redirect_to ilike", LikePatterns.contains(value), "redirectTo");
            return (Criteria) this;
        }

        public Criteria andMigrationHashContains(String value) {
            addCriterion("migration_hash ilike", LikePatterns.contains(value), "migrationHash");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page")
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