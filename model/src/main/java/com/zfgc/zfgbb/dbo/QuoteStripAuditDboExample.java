package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class QuoteStripAuditDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    protected Integer limit;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    public QuoteStripAuditDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    public Integer getLimit() {
        return limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    public Integer getOffset() {
        return offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
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

        public Criteria andQuoteStripAuditIdIsNull() {
            addCriterion("quote_strip_audit_id is null");
            return (Criteria) this;
        }

        public Criteria andQuoteStripAuditIdIsNotNull() {
            addCriterion("quote_strip_audit_id is not null");
            return (Criteria) this;
        }

        public Criteria andQuoteStripAuditIdEqualTo(Integer value) {
            addCriterion("quote_strip_audit_id =", value, "quoteStripAuditId");
            return (Criteria) this;
        }

        public Criteria andQuoteStripAuditIdNotEqualTo(Integer value) {
            addCriterion("quote_strip_audit_id <>", value, "quoteStripAuditId");
            return (Criteria) this;
        }

        public Criteria andQuoteStripAuditIdGreaterThan(Integer value) {
            addCriterion("quote_strip_audit_id >", value, "quoteStripAuditId");
            return (Criteria) this;
        }

        public Criteria andQuoteStripAuditIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("quote_strip_audit_id >=", value, "quoteStripAuditId");
            return (Criteria) this;
        }

        public Criteria andQuoteStripAuditIdLessThan(Integer value) {
            addCriterion("quote_strip_audit_id <", value, "quoteStripAuditId");
            return (Criteria) this;
        }

        public Criteria andQuoteStripAuditIdLessThanOrEqualTo(Integer value) {
            addCriterion("quote_strip_audit_id <=", value, "quoteStripAuditId");
            return (Criteria) this;
        }

        public Criteria andQuoteStripAuditIdIn(List<Integer> values) {
            addCriterion("quote_strip_audit_id in", values, "quoteStripAuditId");
            return (Criteria) this;
        }

        public Criteria andQuoteStripAuditIdNotIn(List<Integer> values) {
            addCriterion("quote_strip_audit_id not in", values, "quoteStripAuditId");
            return (Criteria) this;
        }

        public Criteria andQuoteStripAuditIdBetween(Integer value1, Integer value2) {
            addCriterion("quote_strip_audit_id between", value1, value2, "quoteStripAuditId");
            return (Criteria) this;
        }

        public Criteria andQuoteStripAuditIdNotBetween(Integer value1, Integer value2) {
            addCriterion("quote_strip_audit_id not between", value1, value2, "quoteStripAuditId");
            return (Criteria) this;
        }

        public Criteria andRunIdIsNull() {
            addCriterion("run_id is null");
            return (Criteria) this;
        }

        public Criteria andRunIdIsNotNull() {
            addCriterion("run_id is not null");
            return (Criteria) this;
        }

        public Criteria andRunIdEqualTo(Object value) {
            addCriterion("run_id =", value, "runId");
            return (Criteria) this;
        }

        public Criteria andRunIdNotEqualTo(Object value) {
            addCriterion("run_id <>", value, "runId");
            return (Criteria) this;
        }

        public Criteria andRunIdGreaterThan(Object value) {
            addCriterion("run_id >", value, "runId");
            return (Criteria) this;
        }

        public Criteria andRunIdGreaterThanOrEqualTo(Object value) {
            addCriterion("run_id >=", value, "runId");
            return (Criteria) this;
        }

        public Criteria andRunIdLessThan(Object value) {
            addCriterion("run_id <", value, "runId");
            return (Criteria) this;
        }

        public Criteria andRunIdLessThanOrEqualTo(Object value) {
            addCriterion("run_id <=", value, "runId");
            return (Criteria) this;
        }

        public Criteria andRunIdIn(List<Object> values) {
            addCriterion("run_id in", values, "runId");
            return (Criteria) this;
        }

        public Criteria andRunIdNotIn(List<Object> values) {
            addCriterion("run_id not in", values, "runId");
            return (Criteria) this;
        }

        public Criteria andRunIdBetween(Object value1, Object value2) {
            addCriterion("run_id between", value1, value2, "runId");
            return (Criteria) this;
        }

        public Criteria andRunIdNotBetween(Object value1, Object value2) {
            addCriterion("run_id not between", value1, value2, "runId");
            return (Criteria) this;
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

        public Criteria andBeforeTextIsNull() {
            addCriterion("before_text is null");
            return (Criteria) this;
        }

        public Criteria andBeforeTextIsNotNull() {
            addCriterion("before_text is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeTextEqualTo(String value) {
            addCriterion("before_text =", value, "beforeText");
            return (Criteria) this;
        }

        public Criteria andBeforeTextNotEqualTo(String value) {
            addCriterion("before_text <>", value, "beforeText");
            return (Criteria) this;
        }

        public Criteria andBeforeTextGreaterThan(String value) {
            addCriterion("before_text >", value, "beforeText");
            return (Criteria) this;
        }

        public Criteria andBeforeTextGreaterThanOrEqualTo(String value) {
            addCriterion("before_text >=", value, "beforeText");
            return (Criteria) this;
        }

        public Criteria andBeforeTextLessThan(String value) {
            addCriterion("before_text <", value, "beforeText");
            return (Criteria) this;
        }

        public Criteria andBeforeTextLessThanOrEqualTo(String value) {
            addCriterion("before_text <=", value, "beforeText");
            return (Criteria) this;
        }

        public Criteria andBeforeTextLike(String value) {
            addCriterion("before_text ilike", value, "beforeText");
            return (Criteria) this;
        }

        public Criteria andBeforeTextNotLike(String value) {
            addCriterion("before_text not ilike", value, "beforeText");
            return (Criteria) this;
        }

        public Criteria andBeforeTextIn(List<String> values) {
            addCriterion("before_text in", values, "beforeText");
            return (Criteria) this;
        }

        public Criteria andBeforeTextNotIn(List<String> values) {
            addCriterion("before_text not in", values, "beforeText");
            return (Criteria) this;
        }

        public Criteria andBeforeTextBetween(String value1, String value2) {
            addCriterion("before_text between", value1, value2, "beforeText");
            return (Criteria) this;
        }

        public Criteria andBeforeTextNotBetween(String value1, String value2) {
            addCriterion("before_text not between", value1, value2, "beforeText");
            return (Criteria) this;
        }

        public Criteria andAfterTextIsNull() {
            addCriterion("after_text is null");
            return (Criteria) this;
        }

        public Criteria andAfterTextIsNotNull() {
            addCriterion("after_text is not null");
            return (Criteria) this;
        }

        public Criteria andAfterTextEqualTo(String value) {
            addCriterion("after_text =", value, "afterText");
            return (Criteria) this;
        }

        public Criteria andAfterTextNotEqualTo(String value) {
            addCriterion("after_text <>", value, "afterText");
            return (Criteria) this;
        }

        public Criteria andAfterTextGreaterThan(String value) {
            addCriterion("after_text >", value, "afterText");
            return (Criteria) this;
        }

        public Criteria andAfterTextGreaterThanOrEqualTo(String value) {
            addCriterion("after_text >=", value, "afterText");
            return (Criteria) this;
        }

        public Criteria andAfterTextLessThan(String value) {
            addCriterion("after_text <", value, "afterText");
            return (Criteria) this;
        }

        public Criteria andAfterTextLessThanOrEqualTo(String value) {
            addCriterion("after_text <=", value, "afterText");
            return (Criteria) this;
        }

        public Criteria andAfterTextLike(String value) {
            addCriterion("after_text ilike", value, "afterText");
            return (Criteria) this;
        }

        public Criteria andAfterTextNotLike(String value) {
            addCriterion("after_text not ilike", value, "afterText");
            return (Criteria) this;
        }

        public Criteria andAfterTextIn(List<String> values) {
            addCriterion("after_text in", values, "afterText");
            return (Criteria) this;
        }

        public Criteria andAfterTextNotIn(List<String> values) {
            addCriterion("after_text not in", values, "afterText");
            return (Criteria) this;
        }

        public Criteria andAfterTextBetween(String value1, String value2) {
            addCriterion("after_text between", value1, value2, "afterText");
            return (Criteria) this;
        }

        public Criteria andAfterTextNotBetween(String value1, String value2) {
            addCriterion("after_text not between", value1, value2, "afterText");
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

        public Criteria andPlannedTsIsNull() {
            addCriterion("planned_ts is null");
            return (Criteria) this;
        }

        public Criteria andPlannedTsIsNotNull() {
            addCriterion("planned_ts is not null");
            return (Criteria) this;
        }

        public Criteria andPlannedTsEqualTo(OffsetDateTime value) {
            addCriterion("planned_ts =", value, "plannedTs");
            return (Criteria) this;
        }

        public Criteria andPlannedTsNotEqualTo(OffsetDateTime value) {
            addCriterion("planned_ts <>", value, "plannedTs");
            return (Criteria) this;
        }

        public Criteria andPlannedTsGreaterThan(OffsetDateTime value) {
            addCriterion("planned_ts >", value, "plannedTs");
            return (Criteria) this;
        }

        public Criteria andPlannedTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("planned_ts >=", value, "plannedTs");
            return (Criteria) this;
        }

        public Criteria andPlannedTsLessThan(OffsetDateTime value) {
            addCriterion("planned_ts <", value, "plannedTs");
            return (Criteria) this;
        }

        public Criteria andPlannedTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("planned_ts <=", value, "plannedTs");
            return (Criteria) this;
        }

        public Criteria andPlannedTsIn(List<OffsetDateTime> values) {
            addCriterion("planned_ts in", values, "plannedTs");
            return (Criteria) this;
        }

        public Criteria andPlannedTsNotIn(List<OffsetDateTime> values) {
            addCriterion("planned_ts not in", values, "plannedTs");
            return (Criteria) this;
        }

        public Criteria andPlannedTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("planned_ts between", value1, value2, "plannedTs");
            return (Criteria) this;
        }

        public Criteria andPlannedTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("planned_ts not between", value1, value2, "plannedTs");
            return (Criteria) this;
        }

        public Criteria andAppliedTsIsNull() {
            addCriterion("applied_ts is null");
            return (Criteria) this;
        }

        public Criteria andAppliedTsIsNotNull() {
            addCriterion("applied_ts is not null");
            return (Criteria) this;
        }

        public Criteria andAppliedTsEqualTo(OffsetDateTime value) {
            addCriterion("applied_ts =", value, "appliedTs");
            return (Criteria) this;
        }

        public Criteria andAppliedTsNotEqualTo(OffsetDateTime value) {
            addCriterion("applied_ts <>", value, "appliedTs");
            return (Criteria) this;
        }

        public Criteria andAppliedTsGreaterThan(OffsetDateTime value) {
            addCriterion("applied_ts >", value, "appliedTs");
            return (Criteria) this;
        }

        public Criteria andAppliedTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("applied_ts >=", value, "appliedTs");
            return (Criteria) this;
        }

        public Criteria andAppliedTsLessThan(OffsetDateTime value) {
            addCriterion("applied_ts <", value, "appliedTs");
            return (Criteria) this;
        }

        public Criteria andAppliedTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("applied_ts <=", value, "appliedTs");
            return (Criteria) this;
        }

        public Criteria andAppliedTsIn(List<OffsetDateTime> values) {
            addCriterion("applied_ts in", values, "appliedTs");
            return (Criteria) this;
        }

        public Criteria andAppliedTsNotIn(List<OffsetDateTime> values) {
            addCriterion("applied_ts not in", values, "appliedTs");
            return (Criteria) this;
        }

        public Criteria andAppliedTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("applied_ts between", value1, value2, "appliedTs");
            return (Criteria) this;
        }

        public Criteria andAppliedTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("applied_ts not between", value1, value2, "appliedTs");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.quote_strip_audit")
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