package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class KarmaDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.249132124-04:00", comments="Source Table: zfgbb.karma")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.249146723-04:00", comments="Source Table: zfgbb.karma")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.249163403-04:00", comments="Source Table: zfgbb.karma")
    protected List<Criteria> oredCriteria;

    protected Integer limit;

    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.249125884-04:00", comments="Source Table: zfgbb.karma")
    public KarmaDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.249137784-04:00", comments="Source Table: zfgbb.karma")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.249142103-04:00", comments="Source Table: zfgbb.karma")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.249150993-04:00", comments="Source Table: zfgbb.karma")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.249155073-04:00", comments="Source Table: zfgbb.karma")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.249166693-04:00", comments="Source Table: zfgbb.karma")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.249171442-04:00", comments="Source Table: zfgbb.karma")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.249175712-04:00", comments="Source Table: zfgbb.karma")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.249179842-04:00", comments="Source Table: zfgbb.karma")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.249183812-04:00", comments="Source Table: zfgbb.karma")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.249187742-04:00", comments="Source Table: zfgbb.karma")
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

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.249193392-04:00", comments="Source Table: zfgbb.karma")
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

        public Criteria andKarmaIdIsNull() {
            addCriterion("karma_id is null");
            return (Criteria) this;
        }

        public Criteria andKarmaIdIsNotNull() {
            addCriterion("karma_id is not null");
            return (Criteria) this;
        }

        public Criteria andKarmaIdEqualTo(Integer value) {
            addCriterion("karma_id =", value, "karmaId");
            return (Criteria) this;
        }

        public Criteria andKarmaIdNotEqualTo(Integer value) {
            addCriterion("karma_id <>", value, "karmaId");
            return (Criteria) this;
        }

        public Criteria andKarmaIdGreaterThan(Integer value) {
            addCriterion("karma_id >", value, "karmaId");
            return (Criteria) this;
        }

        public Criteria andKarmaIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("karma_id >=", value, "karmaId");
            return (Criteria) this;
        }

        public Criteria andKarmaIdLessThan(Integer value) {
            addCriterion("karma_id <", value, "karmaId");
            return (Criteria) this;
        }

        public Criteria andKarmaIdLessThanOrEqualTo(Integer value) {
            addCriterion("karma_id <=", value, "karmaId");
            return (Criteria) this;
        }

        public Criteria andKarmaIdIn(List<Integer> values) {
            addCriterion("karma_id in", values, "karmaId");
            return (Criteria) this;
        }

        public Criteria andKarmaIdNotIn(List<Integer> values) {
            addCriterion("karma_id not in", values, "karmaId");
            return (Criteria) this;
        }

        public Criteria andKarmaIdBetween(Integer value1, Integer value2) {
            addCriterion("karma_id between", value1, value2, "karmaId");
            return (Criteria) this;
        }

        public Criteria andKarmaIdNotBetween(Integer value1, Integer value2) {
            addCriterion("karma_id not between", value1, value2, "karmaId");
            return (Criteria) this;
        }

        public Criteria andCommentingUserIdIsNull() {
            addCriterion("commenting_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCommentingUserIdIsNotNull() {
            addCriterion("commenting_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCommentingUserIdEqualTo(Integer value) {
            addCriterion("commenting_user_id =", value, "commentingUserId");
            return (Criteria) this;
        }

        public Criteria andCommentingUserIdNotEqualTo(Integer value) {
            addCriterion("commenting_user_id <>", value, "commentingUserId");
            return (Criteria) this;
        }

        public Criteria andCommentingUserIdGreaterThan(Integer value) {
            addCriterion("commenting_user_id >", value, "commentingUserId");
            return (Criteria) this;
        }

        public Criteria andCommentingUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("commenting_user_id >=", value, "commentingUserId");
            return (Criteria) this;
        }

        public Criteria andCommentingUserIdLessThan(Integer value) {
            addCriterion("commenting_user_id <", value, "commentingUserId");
            return (Criteria) this;
        }

        public Criteria andCommentingUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("commenting_user_id <=", value, "commentingUserId");
            return (Criteria) this;
        }

        public Criteria andCommentingUserIdIn(List<Integer> values) {
            addCriterion("commenting_user_id in", values, "commentingUserId");
            return (Criteria) this;
        }

        public Criteria andCommentingUserIdNotIn(List<Integer> values) {
            addCriterion("commenting_user_id not in", values, "commentingUserId");
            return (Criteria) this;
        }

        public Criteria andCommentingUserIdBetween(Integer value1, Integer value2) {
            addCriterion("commenting_user_id between", value1, value2, "commentingUserId");
            return (Criteria) this;
        }

        public Criteria andCommentingUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("commenting_user_id not between", value1, value2, "commentingUserId");
            return (Criteria) this;
        }

        public Criteria andIsPositiveIsNull() {
            addCriterion("is_positive is null");
            return (Criteria) this;
        }

        public Criteria andIsPositiveIsNotNull() {
            addCriterion("is_positive is not null");
            return (Criteria) this;
        }

        public Criteria andIsPositiveEqualTo(Boolean value) {
            addCriterion("is_positive =", value, "isPositive");
            return (Criteria) this;
        }

        public Criteria andIsPositiveNotEqualTo(Boolean value) {
            addCriterion("is_positive <>", value, "isPositive");
            return (Criteria) this;
        }

        public Criteria andIsPositiveGreaterThan(Boolean value) {
            addCriterion("is_positive >", value, "isPositive");
            return (Criteria) this;
        }

        public Criteria andIsPositiveGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_positive >=", value, "isPositive");
            return (Criteria) this;
        }

        public Criteria andIsPositiveLessThan(Boolean value) {
            addCriterion("is_positive <", value, "isPositive");
            return (Criteria) this;
        }

        public Criteria andIsPositiveLessThanOrEqualTo(Boolean value) {
            addCriterion("is_positive <=", value, "isPositive");
            return (Criteria) this;
        }

        public Criteria andIsPositiveIn(List<Boolean> values) {
            addCriterion("is_positive in", values, "isPositive");
            return (Criteria) this;
        }

        public Criteria andIsPositiveNotIn(List<Boolean> values) {
            addCriterion("is_positive not in", values, "isPositive");
            return (Criteria) this;
        }

        public Criteria andIsPositiveBetween(Boolean value1, Boolean value2) {
            addCriterion("is_positive between", value1, value2, "isPositive");
            return (Criteria) this;
        }

        public Criteria andIsPositiveNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_positive not between", value1, value2, "isPositive");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andMessageIdIsNull() {
            addCriterion("message_id is null");
            return (Criteria) this;
        }

        public Criteria andMessageIdIsNotNull() {
            addCriterion("message_id is not null");
            return (Criteria) this;
        }

        public Criteria andMessageIdEqualTo(Integer value) {
            addCriterion("message_id =", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdNotEqualTo(Integer value) {
            addCriterion("message_id <>", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdGreaterThan(Integer value) {
            addCriterion("message_id >", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("message_id >=", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdLessThan(Integer value) {
            addCriterion("message_id <", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdLessThanOrEqualTo(Integer value) {
            addCriterion("message_id <=", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdIn(List<Integer> values) {
            addCriterion("message_id in", values, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdNotIn(List<Integer> values) {
            addCriterion("message_id not in", values, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdBetween(Integer value1, Integer value2) {
            addCriterion("message_id between", value1, value2, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdNotBetween(Integer value1, Integer value2) {
            addCriterion("message_id not between", value1, value2, "messageId");
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

        public Criteria andCreatedTsEqualTo(LocalDateTime value) {
            addCriterion("created_ts =", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsNotEqualTo(LocalDateTime value) {
            addCriterion("created_ts <>", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsGreaterThan(LocalDateTime value) {
            addCriterion("created_ts >", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("created_ts >=", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsLessThan(LocalDateTime value) {
            addCriterion("created_ts <", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("created_ts <=", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsIn(List<LocalDateTime> values) {
            addCriterion("created_ts in", values, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsNotIn(List<LocalDateTime> values) {
            addCriterion("created_ts not in", values, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("created_ts between", value1, value2, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsNotBetween(LocalDateTime value1, LocalDateTime value2) {
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

        public Criteria andUpdatedTsEqualTo(LocalDateTime value) {
            addCriterion("updated_ts =", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsNotEqualTo(LocalDateTime value) {
            addCriterion("updated_ts <>", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsGreaterThan(LocalDateTime value) {
            addCriterion("updated_ts >", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("updated_ts >=", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsLessThan(LocalDateTime value) {
            addCriterion("updated_ts <", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("updated_ts <=", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsIn(List<LocalDateTime> values) {
            addCriterion("updated_ts in", values, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsNotIn(List<LocalDateTime> values) {
            addCriterion("updated_ts not in", values, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("updated_ts between", value1, value2, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("updated_ts not between", value1, value2, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andMigrationIdIsNull() {
            addCriterion("migration_id is null");
            return (Criteria) this;
        }

        public Criteria andMigrationIdIsNotNull() {
            addCriterion("migration_id is not null");
            return (Criteria) this;
        }

        public Criteria andMigrationIdEqualTo(String value) {
            addCriterion("migration_id =", value, "migrationId");
            return (Criteria) this;
        }

        public Criteria andMigrationIdNotEqualTo(String value) {
            addCriterion("migration_id <>", value, "migrationId");
            return (Criteria) this;
        }

        public Criteria andMigrationIdGreaterThan(String value) {
            addCriterion("migration_id >", value, "migrationId");
            return (Criteria) this;
        }

        public Criteria andMigrationIdGreaterThanOrEqualTo(String value) {
            addCriterion("migration_id >=", value, "migrationId");
            return (Criteria) this;
        }

        public Criteria andMigrationIdLessThan(String value) {
            addCriterion("migration_id <", value, "migrationId");
            return (Criteria) this;
        }

        public Criteria andMigrationIdLessThanOrEqualTo(String value) {
            addCriterion("migration_id <=", value, "migrationId");
            return (Criteria) this;
        }

        public Criteria andMigrationIdLike(String value) {
            addCriterion("migration_id like", value, "migrationId");
            return (Criteria) this;
        }

        public Criteria andMigrationIdNotLike(String value) {
            addCriterion("migration_id not like", value, "migrationId");
            return (Criteria) this;
        }

        public Criteria andMigrationIdIn(List<String> values) {
            addCriterion("migration_id in", values, "migrationId");
            return (Criteria) this;
        }

        public Criteria andMigrationIdNotIn(List<String> values) {
            addCriterion("migration_id not in", values, "migrationId");
            return (Criteria) this;
        }

        public Criteria andMigrationIdBetween(String value1, String value2) {
            addCriterion("migration_id between", value1, value2, "migrationId");
            return (Criteria) this;
        }

        public Criteria andMigrationIdNotBetween(String value1, String value2) {
            addCriterion("migration_id not between", value1, value2, "migrationId");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.249383996-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-05T08:54:46.249392275-04:00", comments="Source Table: zfgbb.karma")
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