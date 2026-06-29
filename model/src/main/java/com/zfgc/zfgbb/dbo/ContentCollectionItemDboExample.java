package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ContentCollectionItemDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730530735-04:00", comments="Source Table: zfgbb.content_collection_item")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730544215-04:00", comments="Source Table: zfgbb.content_collection_item")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730560785-04:00", comments="Source Table: zfgbb.content_collection_item")
    protected List<Criteria> oredCriteria;

    protected Integer limit;

    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730521376-04:00", comments="Source Table: zfgbb.content_collection_item")
    public ContentCollectionItemDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730535805-04:00", comments="Source Table: zfgbb.content_collection_item")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730539965-04:00", comments="Source Table: zfgbb.content_collection_item")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730548335-04:00", comments="Source Table: zfgbb.content_collection_item")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730552335-04:00", comments="Source Table: zfgbb.content_collection_item")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730563624-04:00", comments="Source Table: zfgbb.content_collection_item")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730567824-04:00", comments="Source Table: zfgbb.content_collection_item")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730571864-04:00", comments="Source Table: zfgbb.content_collection_item")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730575874-04:00", comments="Source Table: zfgbb.content_collection_item")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730579714-04:00", comments="Source Table: zfgbb.content_collection_item")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730583704-04:00", comments="Source Table: zfgbb.content_collection_item")
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

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730588814-04:00", comments="Source Table: zfgbb.content_collection_item")
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

        public Criteria andContentCollectionItemIdIsNull() {
            addCriterion("content_collection_item_id is null");
            return (Criteria) this;
        }

        public Criteria andContentCollectionItemIdIsNotNull() {
            addCriterion("content_collection_item_id is not null");
            return (Criteria) this;
        }

        public Criteria andContentCollectionItemIdEqualTo(Integer value) {
            addCriterion("content_collection_item_id =", value, "contentCollectionItemId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionItemIdNotEqualTo(Integer value) {
            addCriterion("content_collection_item_id <>", value, "contentCollectionItemId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionItemIdGreaterThan(Integer value) {
            addCriterion("content_collection_item_id >", value, "contentCollectionItemId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionItemIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("content_collection_item_id >=", value, "contentCollectionItemId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionItemIdLessThan(Integer value) {
            addCriterion("content_collection_item_id <", value, "contentCollectionItemId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionItemIdLessThanOrEqualTo(Integer value) {
            addCriterion("content_collection_item_id <=", value, "contentCollectionItemId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionItemIdIn(List<Integer> values) {
            addCriterion("content_collection_item_id in", values, "contentCollectionItemId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionItemIdNotIn(List<Integer> values) {
            addCriterion("content_collection_item_id not in", values, "contentCollectionItemId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionItemIdBetween(Integer value1, Integer value2) {
            addCriterion("content_collection_item_id between", value1, value2, "contentCollectionItemId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionItemIdNotBetween(Integer value1, Integer value2) {
            addCriterion("content_collection_item_id not between", value1, value2, "contentCollectionItemId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionIdIsNull() {
            addCriterion("content_collection_id is null");
            return (Criteria) this;
        }

        public Criteria andContentCollectionIdIsNotNull() {
            addCriterion("content_collection_id is not null");
            return (Criteria) this;
        }

        public Criteria andContentCollectionIdEqualTo(Integer value) {
            addCriterion("content_collection_id =", value, "contentCollectionId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionIdNotEqualTo(Integer value) {
            addCriterion("content_collection_id <>", value, "contentCollectionId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionIdGreaterThan(Integer value) {
            addCriterion("content_collection_id >", value, "contentCollectionId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("content_collection_id >=", value, "contentCollectionId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionIdLessThan(Integer value) {
            addCriterion("content_collection_id <", value, "contentCollectionId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionIdLessThanOrEqualTo(Integer value) {
            addCriterion("content_collection_id <=", value, "contentCollectionId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionIdIn(List<Integer> values) {
            addCriterion("content_collection_id in", values, "contentCollectionId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionIdNotIn(List<Integer> values) {
            addCriterion("content_collection_id not in", values, "contentCollectionId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionIdBetween(Integer value1, Integer value2) {
            addCriterion("content_collection_id between", value1, value2, "contentCollectionId");
            return (Criteria) this;
        }

        public Criteria andContentCollectionIdNotBetween(Integer value1, Integer value2) {
            addCriterion("content_collection_id not between", value1, value2, "contentCollectionId");
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

        public Criteria andAwardedTsIsNull() {
            addCriterion("awarded_ts is null");
            return (Criteria) this;
        }

        public Criteria andAwardedTsIsNotNull() {
            addCriterion("awarded_ts is not null");
            return (Criteria) this;
        }

        public Criteria andAwardedTsEqualTo(OffsetDateTime value) {
            addCriterion("awarded_ts =", value, "awardedTs");
            return (Criteria) this;
        }

        public Criteria andAwardedTsNotEqualTo(OffsetDateTime value) {
            addCriterion("awarded_ts <>", value, "awardedTs");
            return (Criteria) this;
        }

        public Criteria andAwardedTsGreaterThan(OffsetDateTime value) {
            addCriterion("awarded_ts >", value, "awardedTs");
            return (Criteria) this;
        }

        public Criteria andAwardedTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("awarded_ts >=", value, "awardedTs");
            return (Criteria) this;
        }

        public Criteria andAwardedTsLessThan(OffsetDateTime value) {
            addCriterion("awarded_ts <", value, "awardedTs");
            return (Criteria) this;
        }

        public Criteria andAwardedTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("awarded_ts <=", value, "awardedTs");
            return (Criteria) this;
        }

        public Criteria andAwardedTsIn(List<OffsetDateTime> values) {
            addCriterion("awarded_ts in", values, "awardedTs");
            return (Criteria) this;
        }

        public Criteria andAwardedTsNotIn(List<OffsetDateTime> values) {
            addCriterion("awarded_ts not in", values, "awardedTs");
            return (Criteria) this;
        }

        public Criteria andAwardedTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("awarded_ts between", value1, value2, "awardedTs");
            return (Criteria) this;
        }

        public Criteria andAwardedTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("awarded_ts not between", value1, value2, "awardedTs");
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
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730791837-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.730799957-04:00", comments="Source Table: zfgbb.content_collection_item")
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