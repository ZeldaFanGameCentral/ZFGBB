package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmailAddressDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.353599898-04:00", comments="Source Table: zfgbb.email_address")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.353629597-04:00", comments="Source Table: zfgbb.email_address")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.353660496-04:00", comments="Source Table: zfgbb.email_address")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.353584219-04:00", comments="Source Table: zfgbb.email_address")
    public EmailAddressDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.353610938-04:00", comments="Source Table: zfgbb.email_address")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.353620598-04:00", comments="Source Table: zfgbb.email_address")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.353638857-04:00", comments="Source Table: zfgbb.email_address")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.353647577-04:00", comments="Source Table: zfgbb.email_address")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.353670806-04:00", comments="Source Table: zfgbb.email_address")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.353680406-04:00", comments="Source Table: zfgbb.email_address")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.353689295-04:00", comments="Source Table: zfgbb.email_address")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.353698775-04:00", comments="Source Table: zfgbb.email_address")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.353707675-04:00", comments="Source Table: zfgbb.email_address")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.353715014-04:00", comments="Source Table: zfgbb.email_address")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.353723864-04:00", comments="Source Table: zfgbb.email_address")
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

        public Criteria andEmailAddressIdIsNull() {
            addCriterion("email_address_id is null");
            return (Criteria) this;
        }

        public Criteria andEmailAddressIdIsNotNull() {
            addCriterion("email_address_id is not null");
            return (Criteria) this;
        }

        public Criteria andEmailAddressIdEqualTo(Integer value) {
            addCriterion("email_address_id =", value, "emailAddressId");
            return (Criteria) this;
        }

        public Criteria andEmailAddressIdNotEqualTo(Integer value) {
            addCriterion("email_address_id <>", value, "emailAddressId");
            return (Criteria) this;
        }

        public Criteria andEmailAddressIdGreaterThan(Integer value) {
            addCriterion("email_address_id >", value, "emailAddressId");
            return (Criteria) this;
        }

        public Criteria andEmailAddressIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("email_address_id >=", value, "emailAddressId");
            return (Criteria) this;
        }

        public Criteria andEmailAddressIdLessThan(Integer value) {
            addCriterion("email_address_id <", value, "emailAddressId");
            return (Criteria) this;
        }

        public Criteria andEmailAddressIdLessThanOrEqualTo(Integer value) {
            addCriterion("email_address_id <=", value, "emailAddressId");
            return (Criteria) this;
        }

        public Criteria andEmailAddressIdIn(List<Integer> values) {
            addCriterion("email_address_id in", values, "emailAddressId");
            return (Criteria) this;
        }

        public Criteria andEmailAddressIdNotIn(List<Integer> values) {
            addCriterion("email_address_id not in", values, "emailAddressId");
            return (Criteria) this;
        }

        public Criteria andEmailAddressIdBetween(Integer value1, Integer value2) {
            addCriterion("email_address_id between", value1, value2, "emailAddressId");
            return (Criteria) this;
        }

        public Criteria andEmailAddressIdNotBetween(Integer value1, Integer value2) {
            addCriterion("email_address_id not between", value1, value2, "emailAddressId");
            return (Criteria) this;
        }

        public Criteria andEmailAddressIsNull() {
            addCriterion("email_address is null");
            return (Criteria) this;
        }

        public Criteria andEmailAddressIsNotNull() {
            addCriterion("email_address is not null");
            return (Criteria) this;
        }

        public Criteria andEmailAddressEqualTo(String value) {
            addCriterion("email_address =", value, "emailAddress");
            return (Criteria) this;
        }

        public Criteria andEmailAddressNotEqualTo(String value) {
            addCriterion("email_address <>", value, "emailAddress");
            return (Criteria) this;
        }

        public Criteria andEmailAddressGreaterThan(String value) {
            addCriterion("email_address >", value, "emailAddress");
            return (Criteria) this;
        }

        public Criteria andEmailAddressGreaterThanOrEqualTo(String value) {
            addCriterion("email_address >=", value, "emailAddress");
            return (Criteria) this;
        }

        public Criteria andEmailAddressLessThan(String value) {
            addCriterion("email_address <", value, "emailAddress");
            return (Criteria) this;
        }

        public Criteria andEmailAddressLessThanOrEqualTo(String value) {
            addCriterion("email_address <=", value, "emailAddress");
            return (Criteria) this;
        }

        public Criteria andEmailAddressLike(String value) {
            addCriterion("email_address like", value, "emailAddress");
            return (Criteria) this;
        }

        public Criteria andEmailAddressNotLike(String value) {
            addCriterion("email_address not like", value, "emailAddress");
            return (Criteria) this;
        }

        public Criteria andEmailAddressIn(List<String> values) {
            addCriterion("email_address in", values, "emailAddress");
            return (Criteria) this;
        }

        public Criteria andEmailAddressNotIn(List<String> values) {
            addCriterion("email_address not in", values, "emailAddress");
            return (Criteria) this;
        }

        public Criteria andEmailAddressBetween(String value1, String value2) {
            addCriterion("email_address between", value1, value2, "emailAddress");
            return (Criteria) this;
        }

        public Criteria andEmailAddressNotBetween(String value1, String value2) {
            addCriterion("email_address not between", value1, value2, "emailAddress");
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

        public Criteria andSpammerFlagIsNull() {
            addCriterion("spammer_flag is null");
            return (Criteria) this;
        }

        public Criteria andSpammerFlagIsNotNull() {
            addCriterion("spammer_flag is not null");
            return (Criteria) this;
        }

        public Criteria andSpammerFlagEqualTo(Boolean value) {
            addCriterion("spammer_flag =", value, "spammerFlag");
            return (Criteria) this;
        }

        public Criteria andSpammerFlagNotEqualTo(Boolean value) {
            addCriterion("spammer_flag <>", value, "spammerFlag");
            return (Criteria) this;
        }

        public Criteria andSpammerFlagGreaterThan(Boolean value) {
            addCriterion("spammer_flag >", value, "spammerFlag");
            return (Criteria) this;
        }

        public Criteria andSpammerFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("spammer_flag >=", value, "spammerFlag");
            return (Criteria) this;
        }

        public Criteria andSpammerFlagLessThan(Boolean value) {
            addCriterion("spammer_flag <", value, "spammerFlag");
            return (Criteria) this;
        }

        public Criteria andSpammerFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("spammer_flag <=", value, "spammerFlag");
            return (Criteria) this;
        }

        public Criteria andSpammerFlagIn(List<Boolean> values) {
            addCriterion("spammer_flag in", values, "spammerFlag");
            return (Criteria) this;
        }

        public Criteria andSpammerFlagNotIn(List<Boolean> values) {
            addCriterion("spammer_flag not in", values, "spammerFlag");
            return (Criteria) this;
        }

        public Criteria andSpammerFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("spammer_flag between", value1, value2, "spammerFlag");
            return (Criteria) this;
        }

        public Criteria andSpammerFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("spammer_flag not between", value1, value2, "spammerFlag");
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

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.354036244-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-04-26T00:49:43.354062423-04:00", comments="Source Table: zfgbb.email_address")
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