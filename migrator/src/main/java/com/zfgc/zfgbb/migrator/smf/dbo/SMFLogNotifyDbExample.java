package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class SMFLogNotifyDbExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038475434-04:00", comments="Source Table: smf_1log_notify")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038492794-04:00", comments="Source Table: smf_1log_notify")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038517133-04:00", comments="Source Table: smf_1log_notify")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038465875-04:00", comments="Source Table: smf_1log_notify")
    public SMFLogNotifyDbExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038481174-04:00", comments="Source Table: smf_1log_notify")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038485604-04:00", comments="Source Table: smf_1log_notify")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038498314-04:00", comments="Source Table: smf_1log_notify")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038505123-04:00", comments="Source Table: smf_1log_notify")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038522323-04:00", comments="Source Table: smf_1log_notify")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038528973-04:00", comments="Source Table: smf_1log_notify")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038536672-04:00", comments="Source Table: smf_1log_notify")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038543672-04:00", comments="Source Table: smf_1log_notify")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038548112-04:00", comments="Source Table: smf_1log_notify")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038556232-04:00", comments="Source Table: smf_1log_notify")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038565511-04:00", comments="Source Table: smf_1log_notify")
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

        public Criteria andIdMemberIsNull() {
            addCriterion("id_member is null");
            return (Criteria) this;
        }

        public Criteria andIdMemberIsNotNull() {
            addCriterion("id_member is not null");
            return (Criteria) this;
        }

        public Criteria andIdMemberEqualTo(Integer value) {
            addCriterion("id_member =", value, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberNotEqualTo(Integer value) {
            addCriterion("id_member <>", value, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberGreaterThan(Integer value) {
            addCriterion("id_member >", value, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_member >=", value, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberLessThan(Integer value) {
            addCriterion("id_member <", value, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberLessThanOrEqualTo(Integer value) {
            addCriterion("id_member <=", value, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberIn(List<Integer> values) {
            addCriterion("id_member in", values, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberNotIn(List<Integer> values) {
            addCriterion("id_member not in", values, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberBetween(Integer value1, Integer value2) {
            addCriterion("id_member between", value1, value2, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberNotBetween(Integer value1, Integer value2) {
            addCriterion("id_member not between", value1, value2, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdTopicIsNull() {
            addCriterion("id_topic is null");
            return (Criteria) this;
        }

        public Criteria andIdTopicIsNotNull() {
            addCriterion("id_topic is not null");
            return (Criteria) this;
        }

        public Criteria andIdTopicEqualTo(Integer value) {
            addCriterion("id_topic =", value, "idTopic");
            return (Criteria) this;
        }

        public Criteria andIdTopicNotEqualTo(Integer value) {
            addCriterion("id_topic <>", value, "idTopic");
            return (Criteria) this;
        }

        public Criteria andIdTopicGreaterThan(Integer value) {
            addCriterion("id_topic >", value, "idTopic");
            return (Criteria) this;
        }

        public Criteria andIdTopicGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_topic >=", value, "idTopic");
            return (Criteria) this;
        }

        public Criteria andIdTopicLessThan(Integer value) {
            addCriterion("id_topic <", value, "idTopic");
            return (Criteria) this;
        }

        public Criteria andIdTopicLessThanOrEqualTo(Integer value) {
            addCriterion("id_topic <=", value, "idTopic");
            return (Criteria) this;
        }

        public Criteria andIdTopicIn(List<Integer> values) {
            addCriterion("id_topic in", values, "idTopic");
            return (Criteria) this;
        }

        public Criteria andIdTopicNotIn(List<Integer> values) {
            addCriterion("id_topic not in", values, "idTopic");
            return (Criteria) this;
        }

        public Criteria andIdTopicBetween(Integer value1, Integer value2) {
            addCriterion("id_topic between", value1, value2, "idTopic");
            return (Criteria) this;
        }

        public Criteria andIdTopicNotBetween(Integer value1, Integer value2) {
            addCriterion("id_topic not between", value1, value2, "idTopic");
            return (Criteria) this;
        }

        public Criteria andIdBoardIsNull() {
            addCriterion("id_board is null");
            return (Criteria) this;
        }

        public Criteria andIdBoardIsNotNull() {
            addCriterion("id_board is not null");
            return (Criteria) this;
        }

        public Criteria andIdBoardEqualTo(Integer value) {
            addCriterion("id_board =", value, "idBoard");
            return (Criteria) this;
        }

        public Criteria andIdBoardNotEqualTo(Integer value) {
            addCriterion("id_board <>", value, "idBoard");
            return (Criteria) this;
        }

        public Criteria andIdBoardGreaterThan(Integer value) {
            addCriterion("id_board >", value, "idBoard");
            return (Criteria) this;
        }

        public Criteria andIdBoardGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_board >=", value, "idBoard");
            return (Criteria) this;
        }

        public Criteria andIdBoardLessThan(Integer value) {
            addCriterion("id_board <", value, "idBoard");
            return (Criteria) this;
        }

        public Criteria andIdBoardLessThanOrEqualTo(Integer value) {
            addCriterion("id_board <=", value, "idBoard");
            return (Criteria) this;
        }

        public Criteria andIdBoardIn(List<Integer> values) {
            addCriterion("id_board in", values, "idBoard");
            return (Criteria) this;
        }

        public Criteria andIdBoardNotIn(List<Integer> values) {
            addCriterion("id_board not in", values, "idBoard");
            return (Criteria) this;
        }

        public Criteria andIdBoardBetween(Integer value1, Integer value2) {
            addCriterion("id_board between", value1, value2, "idBoard");
            return (Criteria) this;
        }

        public Criteria andIdBoardNotBetween(Integer value1, Integer value2) {
            addCriterion("id_board not between", value1, value2, "idBoard");
            return (Criteria) this;
        }

        public Criteria andSentIsNull() {
            addCriterion("sent is null");
            return (Criteria) this;
        }

        public Criteria andSentIsNotNull() {
            addCriterion("sent is not null");
            return (Criteria) this;
        }

        public Criteria andSentEqualTo(Boolean value) {
            addCriterion("sent =", value, "sent");
            return (Criteria) this;
        }

        public Criteria andSentNotEqualTo(Boolean value) {
            addCriterion("sent <>", value, "sent");
            return (Criteria) this;
        }

        public Criteria andSentGreaterThan(Boolean value) {
            addCriterion("sent >", value, "sent");
            return (Criteria) this;
        }

        public Criteria andSentGreaterThanOrEqualTo(Boolean value) {
            addCriterion("sent >=", value, "sent");
            return (Criteria) this;
        }

        public Criteria andSentLessThan(Boolean value) {
            addCriterion("sent <", value, "sent");
            return (Criteria) this;
        }

        public Criteria andSentLessThanOrEqualTo(Boolean value) {
            addCriterion("sent <=", value, "sent");
            return (Criteria) this;
        }

        public Criteria andSentIn(List<Boolean> values) {
            addCriterion("sent in", values, "sent");
            return (Criteria) this;
        }

        public Criteria andSentNotIn(List<Boolean> values) {
            addCriterion("sent not in", values, "sent");
            return (Criteria) this;
        }

        public Criteria andSentBetween(Boolean value1, Boolean value2) {
            addCriterion("sent between", value1, value2, "sent");
            return (Criteria) this;
        }

        public Criteria andSentNotBetween(Boolean value1, Boolean value2) {
            addCriterion("sent not between", value1, value2, "sent");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038779104-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.038801354-04:00", comments="Source Table: smf_1log_notify")
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