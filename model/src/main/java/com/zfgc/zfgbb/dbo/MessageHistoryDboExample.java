package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageHistoryDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.441719441-04:00", comments="Source Table: zfgbb.message_history")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44174177-04:00", comments="Source Table: zfgbb.message_history")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44176418-04:00", comments="Source Table: zfgbb.message_history")
    protected List<Criteria> oredCriteria;

    protected Integer limit;

    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.441709401-04:00", comments="Source Table: zfgbb.message_history")
    public MessageHistoryDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.441725481-04:00", comments="Source Table: zfgbb.message_history")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.441728971-04:00", comments="Source Table: zfgbb.message_history")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44174881-04:00", comments="Source Table: zfgbb.message_history")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.44175406-04:00", comments="Source Table: zfgbb.message_history")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.441771459-04:00", comments="Source Table: zfgbb.message_history")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.441776649-04:00", comments="Source Table: zfgbb.message_history")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.441781619-04:00", comments="Source Table: zfgbb.message_history")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.441786399-04:00", comments="Source Table: zfgbb.message_history")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.441789499-04:00", comments="Source Table: zfgbb.message_history")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.441794049-04:00", comments="Source Table: zfgbb.message_history")
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

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.441800528-04:00", comments="Source Table: zfgbb.message_history")
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

        public Criteria andMessageHistoryIdIsNull() {
            addCriterion("message_history_id is null");
            return (Criteria) this;
        }

        public Criteria andMessageHistoryIdIsNotNull() {
            addCriterion("message_history_id is not null");
            return (Criteria) this;
        }

        public Criteria andMessageHistoryIdEqualTo(Integer value) {
            addCriterion("message_history_id =", value, "messageHistoryId");
            return (Criteria) this;
        }

        public Criteria andMessageHistoryIdNotEqualTo(Integer value) {
            addCriterion("message_history_id <>", value, "messageHistoryId");
            return (Criteria) this;
        }

        public Criteria andMessageHistoryIdGreaterThan(Integer value) {
            addCriterion("message_history_id >", value, "messageHistoryId");
            return (Criteria) this;
        }

        public Criteria andMessageHistoryIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("message_history_id >=", value, "messageHistoryId");
            return (Criteria) this;
        }

        public Criteria andMessageHistoryIdLessThan(Integer value) {
            addCriterion("message_history_id <", value, "messageHistoryId");
            return (Criteria) this;
        }

        public Criteria andMessageHistoryIdLessThanOrEqualTo(Integer value) {
            addCriterion("message_history_id <=", value, "messageHistoryId");
            return (Criteria) this;
        }

        public Criteria andMessageHistoryIdIn(List<Integer> values) {
            addCriterion("message_history_id in", values, "messageHistoryId");
            return (Criteria) this;
        }

        public Criteria andMessageHistoryIdNotIn(List<Integer> values) {
            addCriterion("message_history_id not in", values, "messageHistoryId");
            return (Criteria) this;
        }

        public Criteria andMessageHistoryIdBetween(Integer value1, Integer value2) {
            addCriterion("message_history_id between", value1, value2, "messageHistoryId");
            return (Criteria) this;
        }

        public Criteria andMessageHistoryIdNotBetween(Integer value1, Integer value2) {
            addCriterion("message_history_id not between", value1, value2, "messageHistoryId");
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

        public Criteria andMessageTextIsNull() {
            addCriterion("message_text is null");
            return (Criteria) this;
        }

        public Criteria andMessageTextIsNotNull() {
            addCriterion("message_text is not null");
            return (Criteria) this;
        }

        public Criteria andMessageTextEqualTo(String value) {
            addCriterion("message_text =", value, "messageText");
            return (Criteria) this;
        }

        public Criteria andMessageTextNotEqualTo(String value) {
            addCriterion("message_text <>", value, "messageText");
            return (Criteria) this;
        }

        public Criteria andMessageTextGreaterThan(String value) {
            addCriterion("message_text >", value, "messageText");
            return (Criteria) this;
        }

        public Criteria andMessageTextGreaterThanOrEqualTo(String value) {
            addCriterion("message_text >=", value, "messageText");
            return (Criteria) this;
        }

        public Criteria andMessageTextLessThan(String value) {
            addCriterion("message_text <", value, "messageText");
            return (Criteria) this;
        }

        public Criteria andMessageTextLessThanOrEqualTo(String value) {
            addCriterion("message_text <=", value, "messageText");
            return (Criteria) this;
        }

        public Criteria andMessageTextLike(String value) {
            addCriterion("message_text like", value, "messageText");
            return (Criteria) this;
        }

        public Criteria andMessageTextNotLike(String value) {
            addCriterion("message_text not like", value, "messageText");
            return (Criteria) this;
        }

        public Criteria andMessageTextIn(List<String> values) {
            addCriterion("message_text in", values, "messageText");
            return (Criteria) this;
        }

        public Criteria andMessageTextNotIn(List<String> values) {
            addCriterion("message_text not in", values, "messageText");
            return (Criteria) this;
        }

        public Criteria andMessageTextBetween(String value1, String value2) {
            addCriterion("message_text between", value1, value2, "messageText");
            return (Criteria) this;
        }

        public Criteria andMessageTextNotBetween(String value1, String value2) {
            addCriterion("message_text not between", value1, value2, "messageText");
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

        public Criteria andIpAddressIdIsNull() {
            addCriterion("ip_address_id is null");
            return (Criteria) this;
        }

        public Criteria andIpAddressIdIsNotNull() {
            addCriterion("ip_address_id is not null");
            return (Criteria) this;
        }

        public Criteria andIpAddressIdEqualTo(Integer value) {
            addCriterion("ip_address_id =", value, "ipAddressId");
            return (Criteria) this;
        }

        public Criteria andIpAddressIdNotEqualTo(Integer value) {
            addCriterion("ip_address_id <>", value, "ipAddressId");
            return (Criteria) this;
        }

        public Criteria andIpAddressIdGreaterThan(Integer value) {
            addCriterion("ip_address_id >", value, "ipAddressId");
            return (Criteria) this;
        }

        public Criteria andIpAddressIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ip_address_id >=", value, "ipAddressId");
            return (Criteria) this;
        }

        public Criteria andIpAddressIdLessThan(Integer value) {
            addCriterion("ip_address_id <", value, "ipAddressId");
            return (Criteria) this;
        }

        public Criteria andIpAddressIdLessThanOrEqualTo(Integer value) {
            addCriterion("ip_address_id <=", value, "ipAddressId");
            return (Criteria) this;
        }

        public Criteria andIpAddressIdIn(List<Integer> values) {
            addCriterion("ip_address_id in", values, "ipAddressId");
            return (Criteria) this;
        }

        public Criteria andIpAddressIdNotIn(List<Integer> values) {
            addCriterion("ip_address_id not in", values, "ipAddressId");
            return (Criteria) this;
        }

        public Criteria andIpAddressIdBetween(Integer value1, Integer value2) {
            addCriterion("ip_address_id between", value1, value2, "ipAddressId");
            return (Criteria) this;
        }

        public Criteria andIpAddressIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ip_address_id not between", value1, value2, "ipAddressId");
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

        public Criteria andLegacyIdIsNull() {
            addCriterion("legacy_id is null");
            return (Criteria) this;
        }

        public Criteria andLegacyIdIsNotNull() {
            addCriterion("legacy_id is not null");
            return (Criteria) this;
        }

        public Criteria andLegacyIdEqualTo(Integer value) {
            addCriterion("legacy_id =", value, "legacyId");
            return (Criteria) this;
        }

        public Criteria andLegacyIdNotEqualTo(Integer value) {
            addCriterion("legacy_id <>", value, "legacyId");
            return (Criteria) this;
        }

        public Criteria andLegacyIdGreaterThan(Integer value) {
            addCriterion("legacy_id >", value, "legacyId");
            return (Criteria) this;
        }

        public Criteria andLegacyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("legacy_id >=", value, "legacyId");
            return (Criteria) this;
        }

        public Criteria andLegacyIdLessThan(Integer value) {
            addCriterion("legacy_id <", value, "legacyId");
            return (Criteria) this;
        }

        public Criteria andLegacyIdLessThanOrEqualTo(Integer value) {
            addCriterion("legacy_id <=", value, "legacyId");
            return (Criteria) this;
        }

        public Criteria andLegacyIdIn(List<Integer> values) {
            addCriterion("legacy_id in", values, "legacyId");
            return (Criteria) this;
        }

        public Criteria andLegacyIdNotIn(List<Integer> values) {
            addCriterion("legacy_id not in", values, "legacyId");
            return (Criteria) this;
        }

        public Criteria andLegacyIdBetween(Integer value1, Integer value2) {
            addCriterion("legacy_id between", value1, value2, "legacyId");
            return (Criteria) this;
        }

        public Criteria andLegacyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("legacy_id not between", value1, value2, "legacyId");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442183476-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.442195576-04:00", comments="Source Table: zfgbb.message_history")
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