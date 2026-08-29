package com.zfgc.zfgbb.dbo;

import com.zfgc.zfgbb.persistence.LikePatterns;
import jakarta.annotation.Generated;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class WikiRevisionRefDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    protected Integer limit;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    public WikiRevisionRefDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    public Integer getLimit() {
        return limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    public Integer getOffset() {
        return offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
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

        public Criteria andWikiPageRevisionIdIsNull() {
            addCriterion("wiki_page_revision_id is null");
            return (Criteria) this;
        }

        public Criteria andWikiPageRevisionIdIsNotNull() {
            addCriterion("wiki_page_revision_id is not null");
            return (Criteria) this;
        }

        public Criteria andWikiPageRevisionIdEqualTo(Integer value) {
            addCriterion("wiki_page_revision_id =", value, "wikiPageRevisionId");
            return (Criteria) this;
        }

        public Criteria andWikiPageRevisionIdNotEqualTo(Integer value) {
            addCriterion("wiki_page_revision_id <>", value, "wikiPageRevisionId");
            return (Criteria) this;
        }

        public Criteria andWikiPageRevisionIdGreaterThan(Integer value) {
            addCriterion("wiki_page_revision_id >", value, "wikiPageRevisionId");
            return (Criteria) this;
        }

        public Criteria andWikiPageRevisionIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("wiki_page_revision_id >=", value, "wikiPageRevisionId");
            return (Criteria) this;
        }

        public Criteria andWikiPageRevisionIdLessThan(Integer value) {
            addCriterion("wiki_page_revision_id <", value, "wikiPageRevisionId");
            return (Criteria) this;
        }

        public Criteria andWikiPageRevisionIdLessThanOrEqualTo(Integer value) {
            addCriterion("wiki_page_revision_id <=", value, "wikiPageRevisionId");
            return (Criteria) this;
        }

        public Criteria andWikiPageRevisionIdIn(List<Integer> values) {
            addCriterion("wiki_page_revision_id in", values, "wikiPageRevisionId");
            return (Criteria) this;
        }

        public Criteria andWikiPageRevisionIdNotIn(List<Integer> values) {
            addCriterion("wiki_page_revision_id not in", values, "wikiPageRevisionId");
            return (Criteria) this;
        }

        public Criteria andWikiPageRevisionIdBetween(Integer value1, Integer value2) {
            addCriterion("wiki_page_revision_id between", value1, value2, "wikiPageRevisionId");
            return (Criteria) this;
        }

        public Criteria andWikiPageRevisionIdNotBetween(Integer value1, Integer value2) {
            addCriterion("wiki_page_revision_id not between", value1, value2, "wikiPageRevisionId");
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

        public Criteria andAuthoredTsIsNull() {
            addCriterion("authored_ts is null");
            return (Criteria) this;
        }

        public Criteria andAuthoredTsIsNotNull() {
            addCriterion("authored_ts is not null");
            return (Criteria) this;
        }

        public Criteria andAuthoredTsEqualTo(OffsetDateTime value) {
            addCriterion("authored_ts =", value, "authoredTs");
            return (Criteria) this;
        }

        public Criteria andAuthoredTsNotEqualTo(OffsetDateTime value) {
            addCriterion("authored_ts <>", value, "authoredTs");
            return (Criteria) this;
        }

        public Criteria andAuthoredTsGreaterThan(OffsetDateTime value) {
            addCriterion("authored_ts >", value, "authoredTs");
            return (Criteria) this;
        }

        public Criteria andAuthoredTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("authored_ts >=", value, "authoredTs");
            return (Criteria) this;
        }

        public Criteria andAuthoredTsLessThan(OffsetDateTime value) {
            addCriterion("authored_ts <", value, "authoredTs");
            return (Criteria) this;
        }

        public Criteria andAuthoredTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("authored_ts <=", value, "authoredTs");
            return (Criteria) this;
        }

        public Criteria andAuthoredTsIn(List<OffsetDateTime> values) {
            addCriterion("authored_ts in", values, "authoredTs");
            return (Criteria) this;
        }

        public Criteria andAuthoredTsNotIn(List<OffsetDateTime> values) {
            addCriterion("authored_ts not in", values, "authoredTs");
            return (Criteria) this;
        }

        public Criteria andAuthoredTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("authored_ts between", value1, value2, "authoredTs");
            return (Criteria) this;
        }

        public Criteria andAuthoredTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("authored_ts not between", value1, value2, "authoredTs");
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
            addCriterion("author_name ilike", value, "authorName");
            return (Criteria) this;
        }

        public Criteria andAuthorNameNotLike(String value) {
            addCriterion("author_name not ilike", value, "authorName");
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
            addCriterion("summary ilike", value, "summary");
            return (Criteria) this;
        }

        public Criteria andSummaryNotLike(String value) {
            addCriterion("summary not ilike", value, "summary");
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

        public Criteria andCurrentFlagIsNull() {
            addCriterion("current_flag is null");
            return (Criteria) this;
        }

        public Criteria andCurrentFlagIsNotNull() {
            addCriterion("current_flag is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentFlagEqualTo(Boolean value) {
            addCriterion("current_flag =", value, "currentFlag");
            return (Criteria) this;
        }

        public Criteria andCurrentFlagNotEqualTo(Boolean value) {
            addCriterion("current_flag <>", value, "currentFlag");
            return (Criteria) this;
        }

        public Criteria andCurrentFlagGreaterThan(Boolean value) {
            addCriterion("current_flag >", value, "currentFlag");
            return (Criteria) this;
        }

        public Criteria andCurrentFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("current_flag >=", value, "currentFlag");
            return (Criteria) this;
        }

        public Criteria andCurrentFlagLessThan(Boolean value) {
            addCriterion("current_flag <", value, "currentFlag");
            return (Criteria) this;
        }

        public Criteria andCurrentFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("current_flag <=", value, "currentFlag");
            return (Criteria) this;
        }

        public Criteria andCurrentFlagIn(List<Boolean> values) {
            addCriterion("current_flag in", values, "currentFlag");
            return (Criteria) this;
        }

        public Criteria andCurrentFlagNotIn(List<Boolean> values) {
            addCriterion("current_flag not in", values, "currentFlag");
            return (Criteria) this;
        }

        public Criteria andCurrentFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("current_flag between", value1, value2, "currentFlag");
            return (Criteria) this;
        }

        public Criteria andCurrentFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("current_flag not between", value1, value2, "currentFlag");
            return (Criteria) this;
        }

        public Criteria andContentSizeIsNull() {
            addCriterion("content_size is null");
            return (Criteria) this;
        }

        public Criteria andContentSizeIsNotNull() {
            addCriterion("content_size is not null");
            return (Criteria) this;
        }

        public Criteria andContentSizeEqualTo(Integer value) {
            addCriterion("content_size =", value, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeNotEqualTo(Integer value) {
            addCriterion("content_size <>", value, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeGreaterThan(Integer value) {
            addCriterion("content_size >", value, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("content_size >=", value, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeLessThan(Integer value) {
            addCriterion("content_size <", value, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeLessThanOrEqualTo(Integer value) {
            addCriterion("content_size <=", value, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeIn(List<Integer> values) {
            addCriterion("content_size in", values, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeNotIn(List<Integer> values) {
            addCriterion("content_size not in", values, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeBetween(Integer value1, Integer value2) {
            addCriterion("content_size between", value1, value2, "contentSize");
            return (Criteria) this;
        }

        public Criteria andContentSizeNotBetween(Integer value1, Integer value2) {
            addCriterion("content_size not between", value1, value2, "contentSize");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status ilike", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not ilike", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andAuthorNameContains(String value) {
            addCriterion("author_name ilike", LikePatterns.contains(value), "authorName");
            return (Criteria) this;
        }

        public Criteria andSummaryContains(String value) {
            addCriterion("summary ilike", LikePatterns.contains(value), "summary");
            return (Criteria) this;
        }

        public Criteria andStatusContains(String value) {
            addCriterion("status ilike", LikePatterns.contains(value), "status");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_revision_ref")
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