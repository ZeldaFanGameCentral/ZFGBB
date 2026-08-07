package com.zfgc.zfgbb.dbo;

import com.zfgc.zfgbb.persistence.LikePatterns;
import jakarta.annotation.Generated;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProjectScreenshotDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    protected Integer limit;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    public ProjectScreenshotDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    public Integer getLimit() {
        return limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    public Integer getOffset() {
        return offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
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

        public Criteria andProjectScreenshotIdIsNull() {
            addCriterion("project_screenshot_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectScreenshotIdIsNotNull() {
            addCriterion("project_screenshot_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectScreenshotIdEqualTo(Integer value) {
            addCriterion("project_screenshot_id =", value, "projectScreenshotId");
            return (Criteria) this;
        }

        public Criteria andProjectScreenshotIdNotEqualTo(Integer value) {
            addCriterion("project_screenshot_id <>", value, "projectScreenshotId");
            return (Criteria) this;
        }

        public Criteria andProjectScreenshotIdGreaterThan(Integer value) {
            addCriterion("project_screenshot_id >", value, "projectScreenshotId");
            return (Criteria) this;
        }

        public Criteria andProjectScreenshotIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_screenshot_id >=", value, "projectScreenshotId");
            return (Criteria) this;
        }

        public Criteria andProjectScreenshotIdLessThan(Integer value) {
            addCriterion("project_screenshot_id <", value, "projectScreenshotId");
            return (Criteria) this;
        }

        public Criteria andProjectScreenshotIdLessThanOrEqualTo(Integer value) {
            addCriterion("project_screenshot_id <=", value, "projectScreenshotId");
            return (Criteria) this;
        }

        public Criteria andProjectScreenshotIdIn(List<Integer> values) {
            addCriterion("project_screenshot_id in", values, "projectScreenshotId");
            return (Criteria) this;
        }

        public Criteria andProjectScreenshotIdNotIn(List<Integer> values) {
            addCriterion("project_screenshot_id not in", values, "projectScreenshotId");
            return (Criteria) this;
        }

        public Criteria andProjectScreenshotIdBetween(Integer value1, Integer value2) {
            addCriterion("project_screenshot_id between", value1, value2, "projectScreenshotId");
            return (Criteria) this;
        }

        public Criteria andProjectScreenshotIdNotBetween(Integer value1, Integer value2) {
            addCriterion("project_screenshot_id not between", value1, value2, "projectScreenshotId");
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

        public Criteria andCaptionIsNull() {
            addCriterion("caption is null");
            return (Criteria) this;
        }

        public Criteria andCaptionIsNotNull() {
            addCriterion("caption is not null");
            return (Criteria) this;
        }

        public Criteria andCaptionEqualTo(String value) {
            addCriterion("caption =", value, "caption");
            return (Criteria) this;
        }

        public Criteria andCaptionNotEqualTo(String value) {
            addCriterion("caption <>", value, "caption");
            return (Criteria) this;
        }

        public Criteria andCaptionGreaterThan(String value) {
            addCriterion("caption >", value, "caption");
            return (Criteria) this;
        }

        public Criteria andCaptionGreaterThanOrEqualTo(String value) {
            addCriterion("caption >=", value, "caption");
            return (Criteria) this;
        }

        public Criteria andCaptionLessThan(String value) {
            addCriterion("caption <", value, "caption");
            return (Criteria) this;
        }

        public Criteria andCaptionLessThanOrEqualTo(String value) {
            addCriterion("caption <=", value, "caption");
            return (Criteria) this;
        }

        public Criteria andCaptionLike(String value) {
            addCriterion("caption ilike", value, "caption");
            return (Criteria) this;
        }

        public Criteria andCaptionNotLike(String value) {
            addCriterion("caption not ilike", value, "caption");
            return (Criteria) this;
        }

        public Criteria andCaptionIn(List<String> values) {
            addCriterion("caption in", values, "caption");
            return (Criteria) this;
        }

        public Criteria andCaptionNotIn(List<String> values) {
            addCriterion("caption not in", values, "caption");
            return (Criteria) this;
        }

        public Criteria andCaptionBetween(String value1, String value2) {
            addCriterion("caption between", value1, value2, "caption");
            return (Criteria) this;
        }

        public Criteria andCaptionNotBetween(String value1, String value2) {
            addCriterion("caption not between", value1, value2, "caption");
            return (Criteria) this;
        }

        public Criteria andOrdinalIsNull() {
            addCriterion("ordinal is null");
            return (Criteria) this;
        }

        public Criteria andOrdinalIsNotNull() {
            addCriterion("ordinal is not null");
            return (Criteria) this;
        }

        public Criteria andOrdinalEqualTo(Integer value) {
            addCriterion("ordinal =", value, "ordinal");
            return (Criteria) this;
        }

        public Criteria andOrdinalNotEqualTo(Integer value) {
            addCriterion("ordinal <>", value, "ordinal");
            return (Criteria) this;
        }

        public Criteria andOrdinalGreaterThan(Integer value) {
            addCriterion("ordinal >", value, "ordinal");
            return (Criteria) this;
        }

        public Criteria andOrdinalGreaterThanOrEqualTo(Integer value) {
            addCriterion("ordinal >=", value, "ordinal");
            return (Criteria) this;
        }

        public Criteria andOrdinalLessThan(Integer value) {
            addCriterion("ordinal <", value, "ordinal");
            return (Criteria) this;
        }

        public Criteria andOrdinalLessThanOrEqualTo(Integer value) {
            addCriterion("ordinal <=", value, "ordinal");
            return (Criteria) this;
        }

        public Criteria andOrdinalIn(List<Integer> values) {
            addCriterion("ordinal in", values, "ordinal");
            return (Criteria) this;
        }

        public Criteria andOrdinalNotIn(List<Integer> values) {
            addCriterion("ordinal not in", values, "ordinal");
            return (Criteria) this;
        }

        public Criteria andOrdinalBetween(Integer value1, Integer value2) {
            addCriterion("ordinal between", value1, value2, "ordinal");
            return (Criteria) this;
        }

        public Criteria andOrdinalNotBetween(Integer value1, Integer value2) {
            addCriterion("ordinal not between", value1, value2, "ordinal");
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

        public Criteria andCaptionContains(String value) {
            addCriterion("caption ilike", LikePatterns.contains(value), "caption");
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

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.project_screenshot")
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