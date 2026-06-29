package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class MigrationConflictDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731511955-04:00", comments="Source Table: zfgbb.migration_conflict")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731528714-04:00", comments="Source Table: zfgbb.migration_conflict")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731551114-04:00", comments="Source Table: zfgbb.migration_conflict")
    protected List<Criteria> oredCriteria;

    protected Integer limit;

    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731504925-04:00", comments="Source Table: zfgbb.migration_conflict")
    public MigrationConflictDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731517825-04:00", comments="Source Table: zfgbb.migration_conflict")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731522214-04:00", comments="Source Table: zfgbb.migration_conflict")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731533084-04:00", comments="Source Table: zfgbb.migration_conflict")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731536984-04:00", comments="Source Table: zfgbb.migration_conflict")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731555633-04:00", comments="Source Table: zfgbb.migration_conflict")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731558343-04:00", comments="Source Table: zfgbb.migration_conflict")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731563283-04:00", comments="Source Table: zfgbb.migration_conflict")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731567423-04:00", comments="Source Table: zfgbb.migration_conflict")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731572093-04:00", comments="Source Table: zfgbb.migration_conflict")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731576423-04:00", comments="Source Table: zfgbb.migration_conflict")
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

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731582213-04:00", comments="Source Table: zfgbb.migration_conflict")
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

        public Criteria andMigrationConflictIdIsNull() {
            addCriterion("migration_conflict_id is null");
            return (Criteria) this;
        }

        public Criteria andMigrationConflictIdIsNotNull() {
            addCriterion("migration_conflict_id is not null");
            return (Criteria) this;
        }

        public Criteria andMigrationConflictIdEqualTo(Integer value) {
            addCriterion("migration_conflict_id =", value, "migrationConflictId");
            return (Criteria) this;
        }

        public Criteria andMigrationConflictIdNotEqualTo(Integer value) {
            addCriterion("migration_conflict_id <>", value, "migrationConflictId");
            return (Criteria) this;
        }

        public Criteria andMigrationConflictIdGreaterThan(Integer value) {
            addCriterion("migration_conflict_id >", value, "migrationConflictId");
            return (Criteria) this;
        }

        public Criteria andMigrationConflictIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("migration_conflict_id >=", value, "migrationConflictId");
            return (Criteria) this;
        }

        public Criteria andMigrationConflictIdLessThan(Integer value) {
            addCriterion("migration_conflict_id <", value, "migrationConflictId");
            return (Criteria) this;
        }

        public Criteria andMigrationConflictIdLessThanOrEqualTo(Integer value) {
            addCriterion("migration_conflict_id <=", value, "migrationConflictId");
            return (Criteria) this;
        }

        public Criteria andMigrationConflictIdIn(List<Integer> values) {
            addCriterion("migration_conflict_id in", values, "migrationConflictId");
            return (Criteria) this;
        }

        public Criteria andMigrationConflictIdNotIn(List<Integer> values) {
            addCriterion("migration_conflict_id not in", values, "migrationConflictId");
            return (Criteria) this;
        }

        public Criteria andMigrationConflictIdBetween(Integer value1, Integer value2) {
            addCriterion("migration_conflict_id between", value1, value2, "migrationConflictId");
            return (Criteria) this;
        }

        public Criteria andMigrationConflictIdNotBetween(Integer value1, Integer value2) {
            addCriterion("migration_conflict_id not between", value1, value2, "migrationConflictId");
            return (Criteria) this;
        }

        public Criteria andEntityTypeIsNull() {
            addCriterion("entity_type is null");
            return (Criteria) this;
        }

        public Criteria andEntityTypeIsNotNull() {
            addCriterion("entity_type is not null");
            return (Criteria) this;
        }

        public Criteria andEntityTypeEqualTo(String value) {
            addCriterion("entity_type =", value, "entityType");
            return (Criteria) this;
        }

        public Criteria andEntityTypeNotEqualTo(String value) {
            addCriterion("entity_type <>", value, "entityType");
            return (Criteria) this;
        }

        public Criteria andEntityTypeGreaterThan(String value) {
            addCriterion("entity_type >", value, "entityType");
            return (Criteria) this;
        }

        public Criteria andEntityTypeGreaterThanOrEqualTo(String value) {
            addCriterion("entity_type >=", value, "entityType");
            return (Criteria) this;
        }

        public Criteria andEntityTypeLessThan(String value) {
            addCriterion("entity_type <", value, "entityType");
            return (Criteria) this;
        }

        public Criteria andEntityTypeLessThanOrEqualTo(String value) {
            addCriterion("entity_type <=", value, "entityType");
            return (Criteria) this;
        }

        public Criteria andEntityTypeLike(String value) {
            addCriterion("entity_type like", value, "entityType");
            return (Criteria) this;
        }

        public Criteria andEntityTypeNotLike(String value) {
            addCriterion("entity_type not like", value, "entityType");
            return (Criteria) this;
        }

        public Criteria andEntityTypeIn(List<String> values) {
            addCriterion("entity_type in", values, "entityType");
            return (Criteria) this;
        }

        public Criteria andEntityTypeNotIn(List<String> values) {
            addCriterion("entity_type not in", values, "entityType");
            return (Criteria) this;
        }

        public Criteria andEntityTypeBetween(String value1, String value2) {
            addCriterion("entity_type between", value1, value2, "entityType");
            return (Criteria) this;
        }

        public Criteria andEntityTypeNotBetween(String value1, String value2) {
            addCriterion("entity_type not between", value1, value2, "entityType");
            return (Criteria) this;
        }

        public Criteria andEntityIdIsNull() {
            addCriterion("entity_id is null");
            return (Criteria) this;
        }

        public Criteria andEntityIdIsNotNull() {
            addCriterion("entity_id is not null");
            return (Criteria) this;
        }

        public Criteria andEntityIdEqualTo(Integer value) {
            addCriterion("entity_id =", value, "entityId");
            return (Criteria) this;
        }

        public Criteria andEntityIdNotEqualTo(Integer value) {
            addCriterion("entity_id <>", value, "entityId");
            return (Criteria) this;
        }

        public Criteria andEntityIdGreaterThan(Integer value) {
            addCriterion("entity_id >", value, "entityId");
            return (Criteria) this;
        }

        public Criteria andEntityIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("entity_id >=", value, "entityId");
            return (Criteria) this;
        }

        public Criteria andEntityIdLessThan(Integer value) {
            addCriterion("entity_id <", value, "entityId");
            return (Criteria) this;
        }

        public Criteria andEntityIdLessThanOrEqualTo(Integer value) {
            addCriterion("entity_id <=", value, "entityId");
            return (Criteria) this;
        }

        public Criteria andEntityIdIn(List<Integer> values) {
            addCriterion("entity_id in", values, "entityId");
            return (Criteria) this;
        }

        public Criteria andEntityIdNotIn(List<Integer> values) {
            addCriterion("entity_id not in", values, "entityId");
            return (Criteria) this;
        }

        public Criteria andEntityIdBetween(Integer value1, Integer value2) {
            addCriterion("entity_id between", value1, value2, "entityId");
            return (Criteria) this;
        }

        public Criteria andEntityIdNotBetween(Integer value1, Integer value2) {
            addCriterion("entity_id not between", value1, value2, "entityId");
            return (Criteria) this;
        }

        public Criteria andFieldNameIsNull() {
            addCriterion("field_name is null");
            return (Criteria) this;
        }

        public Criteria andFieldNameIsNotNull() {
            addCriterion("field_name is not null");
            return (Criteria) this;
        }

        public Criteria andFieldNameEqualTo(String value) {
            addCriterion("field_name =", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameNotEqualTo(String value) {
            addCriterion("field_name <>", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameGreaterThan(String value) {
            addCriterion("field_name >", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameGreaterThanOrEqualTo(String value) {
            addCriterion("field_name >=", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameLessThan(String value) {
            addCriterion("field_name <", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameLessThanOrEqualTo(String value) {
            addCriterion("field_name <=", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameLike(String value) {
            addCriterion("field_name like", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameNotLike(String value) {
            addCriterion("field_name not like", value, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameIn(List<String> values) {
            addCriterion("field_name in", values, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameNotIn(List<String> values) {
            addCriterion("field_name not in", values, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameBetween(String value1, String value2) {
            addCriterion("field_name between", value1, value2, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNameNotBetween(String value1, String value2) {
            addCriterion("field_name not between", value1, value2, "fieldName");
            return (Criteria) this;
        }

        public Criteria andCandidatesIsNull() {
            addCriterion("candidates is null");
            return (Criteria) this;
        }

        public Criteria andCandidatesIsNotNull() {
            addCriterion("candidates is not null");
            return (Criteria) this;
        }

        public Criteria andCandidatesEqualTo(String value) {
            addCriterion("candidates =", value, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesNotEqualTo(String value) {
            addCriterion("candidates <>", value, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesGreaterThan(String value) {
            addCriterion("candidates >", value, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesGreaterThanOrEqualTo(String value) {
            addCriterion("candidates >=", value, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesLessThan(String value) {
            addCriterion("candidates <", value, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesLessThanOrEqualTo(String value) {
            addCriterion("candidates <=", value, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesLike(String value) {
            addCriterion("candidates like", value, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesNotLike(String value) {
            addCriterion("candidates not like", value, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesIn(List<String> values) {
            addCriterion("candidates in", values, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesNotIn(List<String> values) {
            addCriterion("candidates not in", values, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesBetween(String value1, String value2) {
            addCriterion("candidates between", value1, value2, "candidates");
            return (Criteria) this;
        }

        public Criteria andCandidatesNotBetween(String value1, String value2) {
            addCriterion("candidates not between", value1, value2, "candidates");
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
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
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

        public Criteria andResolvedSourceTypeIsNull() {
            addCriterion("resolved_source_type is null");
            return (Criteria) this;
        }

        public Criteria andResolvedSourceTypeIsNotNull() {
            addCriterion("resolved_source_type is not null");
            return (Criteria) this;
        }

        public Criteria andResolvedSourceTypeEqualTo(String value) {
            addCriterion("resolved_source_type =", value, "resolvedSourceType");
            return (Criteria) this;
        }

        public Criteria andResolvedSourceTypeNotEqualTo(String value) {
            addCriterion("resolved_source_type <>", value, "resolvedSourceType");
            return (Criteria) this;
        }

        public Criteria andResolvedSourceTypeGreaterThan(String value) {
            addCriterion("resolved_source_type >", value, "resolvedSourceType");
            return (Criteria) this;
        }

        public Criteria andResolvedSourceTypeGreaterThanOrEqualTo(String value) {
            addCriterion("resolved_source_type >=", value, "resolvedSourceType");
            return (Criteria) this;
        }

        public Criteria andResolvedSourceTypeLessThan(String value) {
            addCriterion("resolved_source_type <", value, "resolvedSourceType");
            return (Criteria) this;
        }

        public Criteria andResolvedSourceTypeLessThanOrEqualTo(String value) {
            addCriterion("resolved_source_type <=", value, "resolvedSourceType");
            return (Criteria) this;
        }

        public Criteria andResolvedSourceTypeLike(String value) {
            addCriterion("resolved_source_type like", value, "resolvedSourceType");
            return (Criteria) this;
        }

        public Criteria andResolvedSourceTypeNotLike(String value) {
            addCriterion("resolved_source_type not like", value, "resolvedSourceType");
            return (Criteria) this;
        }

        public Criteria andResolvedSourceTypeIn(List<String> values) {
            addCriterion("resolved_source_type in", values, "resolvedSourceType");
            return (Criteria) this;
        }

        public Criteria andResolvedSourceTypeNotIn(List<String> values) {
            addCriterion("resolved_source_type not in", values, "resolvedSourceType");
            return (Criteria) this;
        }

        public Criteria andResolvedSourceTypeBetween(String value1, String value2) {
            addCriterion("resolved_source_type between", value1, value2, "resolvedSourceType");
            return (Criteria) this;
        }

        public Criteria andResolvedSourceTypeNotBetween(String value1, String value2) {
            addCriterion("resolved_source_type not between", value1, value2, "resolvedSourceType");
            return (Criteria) this;
        }

        public Criteria andResolvedValueIsNull() {
            addCriterion("resolved_value is null");
            return (Criteria) this;
        }

        public Criteria andResolvedValueIsNotNull() {
            addCriterion("resolved_value is not null");
            return (Criteria) this;
        }

        public Criteria andResolvedValueEqualTo(String value) {
            addCriterion("resolved_value =", value, "resolvedValue");
            return (Criteria) this;
        }

        public Criteria andResolvedValueNotEqualTo(String value) {
            addCriterion("resolved_value <>", value, "resolvedValue");
            return (Criteria) this;
        }

        public Criteria andResolvedValueGreaterThan(String value) {
            addCriterion("resolved_value >", value, "resolvedValue");
            return (Criteria) this;
        }

        public Criteria andResolvedValueGreaterThanOrEqualTo(String value) {
            addCriterion("resolved_value >=", value, "resolvedValue");
            return (Criteria) this;
        }

        public Criteria andResolvedValueLessThan(String value) {
            addCriterion("resolved_value <", value, "resolvedValue");
            return (Criteria) this;
        }

        public Criteria andResolvedValueLessThanOrEqualTo(String value) {
            addCriterion("resolved_value <=", value, "resolvedValue");
            return (Criteria) this;
        }

        public Criteria andResolvedValueLike(String value) {
            addCriterion("resolved_value like", value, "resolvedValue");
            return (Criteria) this;
        }

        public Criteria andResolvedValueNotLike(String value) {
            addCriterion("resolved_value not like", value, "resolvedValue");
            return (Criteria) this;
        }

        public Criteria andResolvedValueIn(List<String> values) {
            addCriterion("resolved_value in", values, "resolvedValue");
            return (Criteria) this;
        }

        public Criteria andResolvedValueNotIn(List<String> values) {
            addCriterion("resolved_value not in", values, "resolvedValue");
            return (Criteria) this;
        }

        public Criteria andResolvedValueBetween(String value1, String value2) {
            addCriterion("resolved_value between", value1, value2, "resolvedValue");
            return (Criteria) this;
        }

        public Criteria andResolvedValueNotBetween(String value1, String value2) {
            addCriterion("resolved_value not between", value1, value2, "resolvedValue");
            return (Criteria) this;
        }

        public Criteria andResolvedByUserIdIsNull() {
            addCriterion("resolved_by_user_id is null");
            return (Criteria) this;
        }

        public Criteria andResolvedByUserIdIsNotNull() {
            addCriterion("resolved_by_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andResolvedByUserIdEqualTo(Integer value) {
            addCriterion("resolved_by_user_id =", value, "resolvedByUserId");
            return (Criteria) this;
        }

        public Criteria andResolvedByUserIdNotEqualTo(Integer value) {
            addCriterion("resolved_by_user_id <>", value, "resolvedByUserId");
            return (Criteria) this;
        }

        public Criteria andResolvedByUserIdGreaterThan(Integer value) {
            addCriterion("resolved_by_user_id >", value, "resolvedByUserId");
            return (Criteria) this;
        }

        public Criteria andResolvedByUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("resolved_by_user_id >=", value, "resolvedByUserId");
            return (Criteria) this;
        }

        public Criteria andResolvedByUserIdLessThan(Integer value) {
            addCriterion("resolved_by_user_id <", value, "resolvedByUserId");
            return (Criteria) this;
        }

        public Criteria andResolvedByUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("resolved_by_user_id <=", value, "resolvedByUserId");
            return (Criteria) this;
        }

        public Criteria andResolvedByUserIdIn(List<Integer> values) {
            addCriterion("resolved_by_user_id in", values, "resolvedByUserId");
            return (Criteria) this;
        }

        public Criteria andResolvedByUserIdNotIn(List<Integer> values) {
            addCriterion("resolved_by_user_id not in", values, "resolvedByUserId");
            return (Criteria) this;
        }

        public Criteria andResolvedByUserIdBetween(Integer value1, Integer value2) {
            addCriterion("resolved_by_user_id between", value1, value2, "resolvedByUserId");
            return (Criteria) this;
        }

        public Criteria andResolvedByUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("resolved_by_user_id not between", value1, value2, "resolvedByUserId");
            return (Criteria) this;
        }

        public Criteria andResolvedTsIsNull() {
            addCriterion("resolved_ts is null");
            return (Criteria) this;
        }

        public Criteria andResolvedTsIsNotNull() {
            addCriterion("resolved_ts is not null");
            return (Criteria) this;
        }

        public Criteria andResolvedTsEqualTo(OffsetDateTime value) {
            addCriterion("resolved_ts =", value, "resolvedTs");
            return (Criteria) this;
        }

        public Criteria andResolvedTsNotEqualTo(OffsetDateTime value) {
            addCriterion("resolved_ts <>", value, "resolvedTs");
            return (Criteria) this;
        }

        public Criteria andResolvedTsGreaterThan(OffsetDateTime value) {
            addCriterion("resolved_ts >", value, "resolvedTs");
            return (Criteria) this;
        }

        public Criteria andResolvedTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("resolved_ts >=", value, "resolvedTs");
            return (Criteria) this;
        }

        public Criteria andResolvedTsLessThan(OffsetDateTime value) {
            addCriterion("resolved_ts <", value, "resolvedTs");
            return (Criteria) this;
        }

        public Criteria andResolvedTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("resolved_ts <=", value, "resolvedTs");
            return (Criteria) this;
        }

        public Criteria andResolvedTsIn(List<OffsetDateTime> values) {
            addCriterion("resolved_ts in", values, "resolvedTs");
            return (Criteria) this;
        }

        public Criteria andResolvedTsNotIn(List<OffsetDateTime> values) {
            addCriterion("resolved_ts not in", values, "resolvedTs");
            return (Criteria) this;
        }

        public Criteria andResolvedTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("resolved_ts between", value1, value2, "resolvedTs");
            return (Criteria) this;
        }

        public Criteria andResolvedTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("resolved_ts not between", value1, value2, "resolvedTs");
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
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731852264-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.731860394-04:00", comments="Source Table: zfgbb.migration_conflict")
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