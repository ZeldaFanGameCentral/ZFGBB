package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReactionDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.700801051-04:00", comments="Source Table: zfgbb.reaction")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70081488-04:00", comments="Source Table: zfgbb.reaction")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70083297-04:00", comments="Source Table: zfgbb.reaction")
    protected List<Criteria> oredCriteria;

    protected Integer limit;

    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.700791401-04:00", comments="Source Table: zfgbb.reaction")
    public ReactionDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.700806621-04:00", comments="Source Table: zfgbb.reaction")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70081156-04:00", comments="Source Table: zfgbb.reaction")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70081946-04:00", comments="Source Table: zfgbb.reaction")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70082377-04:00", comments="Source Table: zfgbb.reaction")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70083794-04:00", comments="Source Table: zfgbb.reaction")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.700842559-04:00", comments="Source Table: zfgbb.reaction")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.700846979-04:00", comments="Source Table: zfgbb.reaction")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.700851039-04:00", comments="Source Table: zfgbb.reaction")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.700855619-04:00", comments="Source Table: zfgbb.reaction")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.700859899-04:00", comments="Source Table: zfgbb.reaction")
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

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.700868919-04:00", comments="Source Table: zfgbb.reaction")
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

        public Criteria andReactionIdIsNull() {
            addCriterion("reaction_id is null");
            return (Criteria) this;
        }

        public Criteria andReactionIdIsNotNull() {
            addCriterion("reaction_id is not null");
            return (Criteria) this;
        }

        public Criteria andReactionIdEqualTo(Integer value) {
            addCriterion("reaction_id =", value, "reactionId");
            return (Criteria) this;
        }

        public Criteria andReactionIdNotEqualTo(Integer value) {
            addCriterion("reaction_id <>", value, "reactionId");
            return (Criteria) this;
        }

        public Criteria andReactionIdGreaterThan(Integer value) {
            addCriterion("reaction_id >", value, "reactionId");
            return (Criteria) this;
        }

        public Criteria andReactionIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("reaction_id >=", value, "reactionId");
            return (Criteria) this;
        }

        public Criteria andReactionIdLessThan(Integer value) {
            addCriterion("reaction_id <", value, "reactionId");
            return (Criteria) this;
        }

        public Criteria andReactionIdLessThanOrEqualTo(Integer value) {
            addCriterion("reaction_id <=", value, "reactionId");
            return (Criteria) this;
        }

        public Criteria andReactionIdIn(List<Integer> values) {
            addCriterion("reaction_id in", values, "reactionId");
            return (Criteria) this;
        }

        public Criteria andReactionIdNotIn(List<Integer> values) {
            addCriterion("reaction_id not in", values, "reactionId");
            return (Criteria) this;
        }

        public Criteria andReactionIdBetween(Integer value1, Integer value2) {
            addCriterion("reaction_id between", value1, value2, "reactionId");
            return (Criteria) this;
        }

        public Criteria andReactionIdNotBetween(Integer value1, Integer value2) {
            addCriterion("reaction_id not between", value1, value2, "reactionId");
            return (Criteria) this;
        }

        public Criteria andReactableTypeIsNull() {
            addCriterion("reactable_type is null");
            return (Criteria) this;
        }

        public Criteria andReactableTypeIsNotNull() {
            addCriterion("reactable_type is not null");
            return (Criteria) this;
        }

        public Criteria andReactableTypeEqualTo(String value) {
            addCriterion("reactable_type =", value, "reactableType");
            return (Criteria) this;
        }

        public Criteria andReactableTypeNotEqualTo(String value) {
            addCriterion("reactable_type <>", value, "reactableType");
            return (Criteria) this;
        }

        public Criteria andReactableTypeGreaterThan(String value) {
            addCriterion("reactable_type >", value, "reactableType");
            return (Criteria) this;
        }

        public Criteria andReactableTypeGreaterThanOrEqualTo(String value) {
            addCriterion("reactable_type >=", value, "reactableType");
            return (Criteria) this;
        }

        public Criteria andReactableTypeLessThan(String value) {
            addCriterion("reactable_type <", value, "reactableType");
            return (Criteria) this;
        }

        public Criteria andReactableTypeLessThanOrEqualTo(String value) {
            addCriterion("reactable_type <=", value, "reactableType");
            return (Criteria) this;
        }

        public Criteria andReactableTypeLike(String value) {
            addCriterion("reactable_type like", value, "reactableType");
            return (Criteria) this;
        }

        public Criteria andReactableTypeNotLike(String value) {
            addCriterion("reactable_type not like", value, "reactableType");
            return (Criteria) this;
        }

        public Criteria andReactableTypeIn(List<String> values) {
            addCriterion("reactable_type in", values, "reactableType");
            return (Criteria) this;
        }

        public Criteria andReactableTypeNotIn(List<String> values) {
            addCriterion("reactable_type not in", values, "reactableType");
            return (Criteria) this;
        }

        public Criteria andReactableTypeBetween(String value1, String value2) {
            addCriterion("reactable_type between", value1, value2, "reactableType");
            return (Criteria) this;
        }

        public Criteria andReactableTypeNotBetween(String value1, String value2) {
            addCriterion("reactable_type not between", value1, value2, "reactableType");
            return (Criteria) this;
        }

        public Criteria andReactableIdIsNull() {
            addCriterion("reactable_id is null");
            return (Criteria) this;
        }

        public Criteria andReactableIdIsNotNull() {
            addCriterion("reactable_id is not null");
            return (Criteria) this;
        }

        public Criteria andReactableIdEqualTo(Integer value) {
            addCriterion("reactable_id =", value, "reactableId");
            return (Criteria) this;
        }

        public Criteria andReactableIdNotEqualTo(Integer value) {
            addCriterion("reactable_id <>", value, "reactableId");
            return (Criteria) this;
        }

        public Criteria andReactableIdGreaterThan(Integer value) {
            addCriterion("reactable_id >", value, "reactableId");
            return (Criteria) this;
        }

        public Criteria andReactableIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("reactable_id >=", value, "reactableId");
            return (Criteria) this;
        }

        public Criteria andReactableIdLessThan(Integer value) {
            addCriterion("reactable_id <", value, "reactableId");
            return (Criteria) this;
        }

        public Criteria andReactableIdLessThanOrEqualTo(Integer value) {
            addCriterion("reactable_id <=", value, "reactableId");
            return (Criteria) this;
        }

        public Criteria andReactableIdIn(List<Integer> values) {
            addCriterion("reactable_id in", values, "reactableId");
            return (Criteria) this;
        }

        public Criteria andReactableIdNotIn(List<Integer> values) {
            addCriterion("reactable_id not in", values, "reactableId");
            return (Criteria) this;
        }

        public Criteria andReactableIdBetween(Integer value1, Integer value2) {
            addCriterion("reactable_id between", value1, value2, "reactableId");
            return (Criteria) this;
        }

        public Criteria andReactableIdNotBetween(Integer value1, Integer value2) {
            addCriterion("reactable_id not between", value1, value2, "reactableId");
            return (Criteria) this;
        }

        public Criteria andReactorUserIdIsNull() {
            addCriterion("reactor_user_id is null");
            return (Criteria) this;
        }

        public Criteria andReactorUserIdIsNotNull() {
            addCriterion("reactor_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andReactorUserIdEqualTo(Integer value) {
            addCriterion("reactor_user_id =", value, "reactorUserId");
            return (Criteria) this;
        }

        public Criteria andReactorUserIdNotEqualTo(Integer value) {
            addCriterion("reactor_user_id <>", value, "reactorUserId");
            return (Criteria) this;
        }

        public Criteria andReactorUserIdGreaterThan(Integer value) {
            addCriterion("reactor_user_id >", value, "reactorUserId");
            return (Criteria) this;
        }

        public Criteria andReactorUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("reactor_user_id >=", value, "reactorUserId");
            return (Criteria) this;
        }

        public Criteria andReactorUserIdLessThan(Integer value) {
            addCriterion("reactor_user_id <", value, "reactorUserId");
            return (Criteria) this;
        }

        public Criteria andReactorUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("reactor_user_id <=", value, "reactorUserId");
            return (Criteria) this;
        }

        public Criteria andReactorUserIdIn(List<Integer> values) {
            addCriterion("reactor_user_id in", values, "reactorUserId");
            return (Criteria) this;
        }

        public Criteria andReactorUserIdNotIn(List<Integer> values) {
            addCriterion("reactor_user_id not in", values, "reactorUserId");
            return (Criteria) this;
        }

        public Criteria andReactorUserIdBetween(Integer value1, Integer value2) {
            addCriterion("reactor_user_id between", value1, value2, "reactorUserId");
            return (Criteria) this;
        }

        public Criteria andReactorUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("reactor_user_id not between", value1, value2, "reactorUserId");
            return (Criteria) this;
        }

        public Criteria andReactionTypeIdIsNull() {
            addCriterion("reaction_type_id is null");
            return (Criteria) this;
        }

        public Criteria andReactionTypeIdIsNotNull() {
            addCriterion("reaction_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andReactionTypeIdEqualTo(Integer value) {
            addCriterion("reaction_type_id =", value, "reactionTypeId");
            return (Criteria) this;
        }

        public Criteria andReactionTypeIdNotEqualTo(Integer value) {
            addCriterion("reaction_type_id <>", value, "reactionTypeId");
            return (Criteria) this;
        }

        public Criteria andReactionTypeIdGreaterThan(Integer value) {
            addCriterion("reaction_type_id >", value, "reactionTypeId");
            return (Criteria) this;
        }

        public Criteria andReactionTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("reaction_type_id >=", value, "reactionTypeId");
            return (Criteria) this;
        }

        public Criteria andReactionTypeIdLessThan(Integer value) {
            addCriterion("reaction_type_id <", value, "reactionTypeId");
            return (Criteria) this;
        }

        public Criteria andReactionTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("reaction_type_id <=", value, "reactionTypeId");
            return (Criteria) this;
        }

        public Criteria andReactionTypeIdIn(List<Integer> values) {
            addCriterion("reaction_type_id in", values, "reactionTypeId");
            return (Criteria) this;
        }

        public Criteria andReactionTypeIdNotIn(List<Integer> values) {
            addCriterion("reaction_type_id not in", values, "reactionTypeId");
            return (Criteria) this;
        }

        public Criteria andReactionTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("reaction_type_id between", value1, value2, "reactionTypeId");
            return (Criteria) this;
        }

        public Criteria andReactionTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("reaction_type_id not between", value1, value2, "reactionTypeId");
            return (Criteria) this;
        }

        public Criteria andCommentIsNull() {
            addCriterion("comment is null");
            return (Criteria) this;
        }

        public Criteria andCommentIsNotNull() {
            addCriterion("comment is not null");
            return (Criteria) this;
        }

        public Criteria andCommentEqualTo(String value) {
            addCriterion("comment =", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotEqualTo(String value) {
            addCriterion("comment <>", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentGreaterThan(String value) {
            addCriterion("comment >", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentGreaterThanOrEqualTo(String value) {
            addCriterion("comment >=", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLessThan(String value) {
            addCriterion("comment <", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLessThanOrEqualTo(String value) {
            addCriterion("comment <=", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLike(String value) {
            addCriterion("comment like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotLike(String value) {
            addCriterion("comment not like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentIn(List<String> values) {
            addCriterion("comment in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotIn(List<String> values) {
            addCriterion("comment not in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentBetween(String value1, String value2) {
            addCriterion("comment between", value1, value2, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotBetween(String value1, String value2) {
            addCriterion("comment not between", value1, value2, "comment");
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

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.701123531-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.70113567-04:00", comments="Source Table: zfgbb.reaction")
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