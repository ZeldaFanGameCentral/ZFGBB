package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProjectNewsDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    protected Integer limit;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    public ProjectNewsDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    public Integer getLimit() {
        return limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    public Integer getOffset() {
        return offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
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

        public Criteria andProjectNewsIdIsNull() {
            addCriterion("project_news_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectNewsIdIsNotNull() {
            addCriterion("project_news_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectNewsIdEqualTo(Integer value) {
            addCriterion("project_news_id =", value, "projectNewsId");
            return (Criteria) this;
        }

        public Criteria andProjectNewsIdNotEqualTo(Integer value) {
            addCriterion("project_news_id <>", value, "projectNewsId");
            return (Criteria) this;
        }

        public Criteria andProjectNewsIdGreaterThan(Integer value) {
            addCriterion("project_news_id >", value, "projectNewsId");
            return (Criteria) this;
        }

        public Criteria andProjectNewsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_news_id >=", value, "projectNewsId");
            return (Criteria) this;
        }

        public Criteria andProjectNewsIdLessThan(Integer value) {
            addCriterion("project_news_id <", value, "projectNewsId");
            return (Criteria) this;
        }

        public Criteria andProjectNewsIdLessThanOrEqualTo(Integer value) {
            addCriterion("project_news_id <=", value, "projectNewsId");
            return (Criteria) this;
        }

        public Criteria andProjectNewsIdIn(List<Integer> values) {
            addCriterion("project_news_id in", values, "projectNewsId");
            return (Criteria) this;
        }

        public Criteria andProjectNewsIdNotIn(List<Integer> values) {
            addCriterion("project_news_id not in", values, "projectNewsId");
            return (Criteria) this;
        }

        public Criteria andProjectNewsIdBetween(Integer value1, Integer value2) {
            addCriterion("project_news_id between", value1, value2, "projectNewsId");
            return (Criteria) this;
        }

        public Criteria andProjectNewsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("project_news_id not between", value1, value2, "projectNewsId");
            return (Criteria) this;
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

        public Criteria andSubjectIsNull() {
            addCriterion("subject is null");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNotNull() {
            addCriterion("subject is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectEqualTo(String value) {
            addCriterion("subject =", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotEqualTo(String value) {
            addCriterion("subject <>", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectGreaterThan(String value) {
            addCriterion("subject >", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectGreaterThanOrEqualTo(String value) {
            addCriterion("subject >=", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLessThan(String value) {
            addCriterion("subject <", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLessThanOrEqualTo(String value) {
            addCriterion("subject <=", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLike(String value) {
            addCriterion("subject ilike", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotLike(String value) {
            addCriterion("subject not ilike", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectIn(List<String> values) {
            addCriterion("subject in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotIn(List<String> values) {
            addCriterion("subject not in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectBetween(String value1, String value2) {
            addCriterion("subject between", value1, value2, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotBetween(String value1, String value2) {
            addCriterion("subject not between", value1, value2, "subject");
            return (Criteria) this;
        }

        public Criteria andBodyIsNull() {
            addCriterion("body is null");
            return (Criteria) this;
        }

        public Criteria andBodyIsNotNull() {
            addCriterion("body is not null");
            return (Criteria) this;
        }

        public Criteria andBodyEqualTo(String value) {
            addCriterion("body =", value, "body");
            return (Criteria) this;
        }

        public Criteria andBodyNotEqualTo(String value) {
            addCriterion("body <>", value, "body");
            return (Criteria) this;
        }

        public Criteria andBodyGreaterThan(String value) {
            addCriterion("body >", value, "body");
            return (Criteria) this;
        }

        public Criteria andBodyGreaterThanOrEqualTo(String value) {
            addCriterion("body >=", value, "body");
            return (Criteria) this;
        }

        public Criteria andBodyLessThan(String value) {
            addCriterion("body <", value, "body");
            return (Criteria) this;
        }

        public Criteria andBodyLessThanOrEqualTo(String value) {
            addCriterion("body <=", value, "body");
            return (Criteria) this;
        }

        public Criteria andBodyLike(String value) {
            addCriterion("body ilike", value, "body");
            return (Criteria) this;
        }

        public Criteria andBodyNotLike(String value) {
            addCriterion("body not ilike", value, "body");
            return (Criteria) this;
        }

        public Criteria andBodyIn(List<String> values) {
            addCriterion("body in", values, "body");
            return (Criteria) this;
        }

        public Criteria andBodyNotIn(List<String> values) {
            addCriterion("body not in", values, "body");
            return (Criteria) this;
        }

        public Criteria andBodyBetween(String value1, String value2) {
            addCriterion("body between", value1, value2, "body");
            return (Criteria) this;
        }

        public Criteria andBodyNotBetween(String value1, String value2) {
            addCriterion("body not between", value1, value2, "body");
            return (Criteria) this;
        }

        public Criteria andAuthorUserIdIsNull() {
            addCriterion("author_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAuthorUserIdIsNotNull() {
            addCriterion("author_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorUserIdEqualTo(Integer value) {
            addCriterion("author_user_id =", value, "authorUserId");
            return (Criteria) this;
        }

        public Criteria andAuthorUserIdNotEqualTo(Integer value) {
            addCriterion("author_user_id <>", value, "authorUserId");
            return (Criteria) this;
        }

        public Criteria andAuthorUserIdGreaterThan(Integer value) {
            addCriterion("author_user_id >", value, "authorUserId");
            return (Criteria) this;
        }

        public Criteria andAuthorUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("author_user_id >=", value, "authorUserId");
            return (Criteria) this;
        }

        public Criteria andAuthorUserIdLessThan(Integer value) {
            addCriterion("author_user_id <", value, "authorUserId");
            return (Criteria) this;
        }

        public Criteria andAuthorUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("author_user_id <=", value, "authorUserId");
            return (Criteria) this;
        }

        public Criteria andAuthorUserIdIn(List<Integer> values) {
            addCriterion("author_user_id in", values, "authorUserId");
            return (Criteria) this;
        }

        public Criteria andAuthorUserIdNotIn(List<Integer> values) {
            addCriterion("author_user_id not in", values, "authorUserId");
            return (Criteria) this;
        }

        public Criteria andAuthorUserIdBetween(Integer value1, Integer value2) {
            addCriterion("author_user_id between", value1, value2, "authorUserId");
            return (Criteria) this;
        }

        public Criteria andAuthorUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("author_user_id not between", value1, value2, "authorUserId");
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
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_news")
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