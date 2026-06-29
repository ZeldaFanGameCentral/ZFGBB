package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class SMFLogActionDbExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041068269-04:00", comments="Source Table: smf_1log_actions")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041082229-04:00", comments="Source Table: smf_1log_actions")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041099058-04:00", comments="Source Table: smf_1log_actions")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041059199-04:00", comments="Source Table: smf_1log_actions")
    public SMFLogActionDbExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041072829-04:00", comments="Source Table: smf_1log_actions")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041077579-04:00", comments="Source Table: smf_1log_actions")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041086459-04:00", comments="Source Table: smf_1log_actions")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041090498-04:00", comments="Source Table: smf_1log_actions")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041103668-04:00", comments="Source Table: smf_1log_actions")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041106488-04:00", comments="Source Table: smf_1log_actions")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041110618-04:00", comments="Source Table: smf_1log_actions")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041114838-04:00", comments="Source Table: smf_1log_actions")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041119447-04:00", comments="Source Table: smf_1log_actions")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041125707-04:00", comments="Source Table: smf_1log_actions")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041131077-04:00", comments="Source Table: smf_1log_actions")
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

        public Criteria andIdActionIsNull() {
            addCriterion("id_action is null");
            return (Criteria) this;
        }

        public Criteria andIdActionIsNotNull() {
            addCriterion("id_action is not null");
            return (Criteria) this;
        }

        public Criteria andIdActionEqualTo(Integer value) {
            addCriterion("id_action =", value, "idAction");
            return (Criteria) this;
        }

        public Criteria andIdActionNotEqualTo(Integer value) {
            addCriterion("id_action <>", value, "idAction");
            return (Criteria) this;
        }

        public Criteria andIdActionGreaterThan(Integer value) {
            addCriterion("id_action >", value, "idAction");
            return (Criteria) this;
        }

        public Criteria andIdActionGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_action >=", value, "idAction");
            return (Criteria) this;
        }

        public Criteria andIdActionLessThan(Integer value) {
            addCriterion("id_action <", value, "idAction");
            return (Criteria) this;
        }

        public Criteria andIdActionLessThanOrEqualTo(Integer value) {
            addCriterion("id_action <=", value, "idAction");
            return (Criteria) this;
        }

        public Criteria andIdActionIn(List<Integer> values) {
            addCriterion("id_action in", values, "idAction");
            return (Criteria) this;
        }

        public Criteria andIdActionNotIn(List<Integer> values) {
            addCriterion("id_action not in", values, "idAction");
            return (Criteria) this;
        }

        public Criteria andIdActionBetween(Integer value1, Integer value2) {
            addCriterion("id_action between", value1, value2, "idAction");
            return (Criteria) this;
        }

        public Criteria andIdActionNotBetween(Integer value1, Integer value2) {
            addCriterion("id_action not between", value1, value2, "idAction");
            return (Criteria) this;
        }

        public Criteria andLogTimeIsNull() {
            addCriterion("log_time is null");
            return (Criteria) this;
        }

        public Criteria andLogTimeIsNotNull() {
            addCriterion("log_time is not null");
            return (Criteria) this;
        }

        public Criteria andLogTimeEqualTo(Integer value) {
            addCriterion("log_time =", value, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTimeNotEqualTo(Integer value) {
            addCriterion("log_time <>", value, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTimeGreaterThan(Integer value) {
            addCriterion("log_time >", value, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("log_time >=", value, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTimeLessThan(Integer value) {
            addCriterion("log_time <", value, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTimeLessThanOrEqualTo(Integer value) {
            addCriterion("log_time <=", value, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTimeIn(List<Integer> values) {
            addCriterion("log_time in", values, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTimeNotIn(List<Integer> values) {
            addCriterion("log_time not in", values, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTimeBetween(Integer value1, Integer value2) {
            addCriterion("log_time between", value1, value2, "logTime");
            return (Criteria) this;
        }

        public Criteria andLogTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("log_time not between", value1, value2, "logTime");
            return (Criteria) this;
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

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andActionIsNull() {
            addCriterion("action is null");
            return (Criteria) this;
        }

        public Criteria andActionIsNotNull() {
            addCriterion("action is not null");
            return (Criteria) this;
        }

        public Criteria andActionEqualTo(String value) {
            addCriterion("action =", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionNotEqualTo(String value) {
            addCriterion("action <>", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionGreaterThan(String value) {
            addCriterion("action >", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionGreaterThanOrEqualTo(String value) {
            addCriterion("action >=", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionLessThan(String value) {
            addCriterion("action <", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionLessThanOrEqualTo(String value) {
            addCriterion("action <=", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionLike(String value) {
            addCriterion("action like", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionNotLike(String value) {
            addCriterion("action not like", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionIn(List<String> values) {
            addCriterion("action in", values, "action");
            return (Criteria) this;
        }

        public Criteria andActionNotIn(List<String> values) {
            addCriterion("action not in", values, "action");
            return (Criteria) this;
        }

        public Criteria andActionBetween(String value1, String value2) {
            addCriterion("action between", value1, value2, "action");
            return (Criteria) this;
        }

        public Criteria andActionNotBetween(String value1, String value2) {
            addCriterion("action not between", value1, value2, "action");
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

        public Criteria andIdMsgIsNull() {
            addCriterion("id_msg is null");
            return (Criteria) this;
        }

        public Criteria andIdMsgIsNotNull() {
            addCriterion("id_msg is not null");
            return (Criteria) this;
        }

        public Criteria andIdMsgEqualTo(Integer value) {
            addCriterion("id_msg =", value, "idMsg");
            return (Criteria) this;
        }

        public Criteria andIdMsgNotEqualTo(Integer value) {
            addCriterion("id_msg <>", value, "idMsg");
            return (Criteria) this;
        }

        public Criteria andIdMsgGreaterThan(Integer value) {
            addCriterion("id_msg >", value, "idMsg");
            return (Criteria) this;
        }

        public Criteria andIdMsgGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_msg >=", value, "idMsg");
            return (Criteria) this;
        }

        public Criteria andIdMsgLessThan(Integer value) {
            addCriterion("id_msg <", value, "idMsg");
            return (Criteria) this;
        }

        public Criteria andIdMsgLessThanOrEqualTo(Integer value) {
            addCriterion("id_msg <=", value, "idMsg");
            return (Criteria) this;
        }

        public Criteria andIdMsgIn(List<Integer> values) {
            addCriterion("id_msg in", values, "idMsg");
            return (Criteria) this;
        }

        public Criteria andIdMsgNotIn(List<Integer> values) {
            addCriterion("id_msg not in", values, "idMsg");
            return (Criteria) this;
        }

        public Criteria andIdMsgBetween(Integer value1, Integer value2) {
            addCriterion("id_msg between", value1, value2, "idMsg");
            return (Criteria) this;
        }

        public Criteria andIdMsgNotBetween(Integer value1, Integer value2) {
            addCriterion("id_msg not between", value1, value2, "idMsg");
            return (Criteria) this;
        }

        public Criteria andIdLogIsNull() {
            addCriterion("id_log is null");
            return (Criteria) this;
        }

        public Criteria andIdLogIsNotNull() {
            addCriterion("id_log is not null");
            return (Criteria) this;
        }

        public Criteria andIdLogEqualTo(Integer value) {
            addCriterion("id_log =", value, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdLogNotEqualTo(Integer value) {
            addCriterion("id_log <>", value, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdLogGreaterThan(Integer value) {
            addCriterion("id_log >", value, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdLogGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_log >=", value, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdLogLessThan(Integer value) {
            addCriterion("id_log <", value, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdLogLessThanOrEqualTo(Integer value) {
            addCriterion("id_log <=", value, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdLogIn(List<Integer> values) {
            addCriterion("id_log in", values, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdLogNotIn(List<Integer> values) {
            addCriterion("id_log not in", values, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdLogBetween(Integer value1, Integer value2) {
            addCriterion("id_log between", value1, value2, "idLog");
            return (Criteria) this;
        }

        public Criteria andIdLogNotBetween(Integer value1, Integer value2) {
            addCriterion("id_log not between", value1, value2, "idLog");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041370499-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.041380449-04:00", comments="Source Table: smf_1log_actions")
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