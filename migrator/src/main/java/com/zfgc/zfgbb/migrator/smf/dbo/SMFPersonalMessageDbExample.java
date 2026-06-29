package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class SMFPersonalMessageDbExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.03464915-04:00", comments="Source Table: smf_1personal_messages")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.03466392-04:00", comments="Source Table: smf_1personal_messages")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.034679849-04:00", comments="Source Table: smf_1personal_messages")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.034639881-04:00", comments="Source Table: smf_1personal_messages")
    public SMFPersonalMessageDbExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.03465491-04:00", comments="Source Table: smf_1personal_messages")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.03465945-04:00", comments="Source Table: smf_1personal_messages")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.03466843-04:00", comments="Source Table: smf_1personal_messages")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.034672599-04:00", comments="Source Table: smf_1personal_messages")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.034684519-04:00", comments="Source Table: smf_1personal_messages")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.034688919-04:00", comments="Source Table: smf_1personal_messages")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.034693269-04:00", comments="Source Table: smf_1personal_messages")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.034697539-04:00", comments="Source Table: smf_1personal_messages")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.034701609-04:00", comments="Source Table: smf_1personal_messages")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.034705638-04:00", comments="Source Table: smf_1personal_messages")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.034711258-04:00", comments="Source Table: smf_1personal_messages")
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

        public Criteria andIdPmIsNull() {
            addCriterion("id_pm is null");
            return (Criteria) this;
        }

        public Criteria andIdPmIsNotNull() {
            addCriterion("id_pm is not null");
            return (Criteria) this;
        }

        public Criteria andIdPmEqualTo(Integer value) {
            addCriterion("id_pm =", value, "idPm");
            return (Criteria) this;
        }

        public Criteria andIdPmNotEqualTo(Integer value) {
            addCriterion("id_pm <>", value, "idPm");
            return (Criteria) this;
        }

        public Criteria andIdPmGreaterThan(Integer value) {
            addCriterion("id_pm >", value, "idPm");
            return (Criteria) this;
        }

        public Criteria andIdPmGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_pm >=", value, "idPm");
            return (Criteria) this;
        }

        public Criteria andIdPmLessThan(Integer value) {
            addCriterion("id_pm <", value, "idPm");
            return (Criteria) this;
        }

        public Criteria andIdPmLessThanOrEqualTo(Integer value) {
            addCriterion("id_pm <=", value, "idPm");
            return (Criteria) this;
        }

        public Criteria andIdPmIn(List<Integer> values) {
            addCriterion("id_pm in", values, "idPm");
            return (Criteria) this;
        }

        public Criteria andIdPmNotIn(List<Integer> values) {
            addCriterion("id_pm not in", values, "idPm");
            return (Criteria) this;
        }

        public Criteria andIdPmBetween(Integer value1, Integer value2) {
            addCriterion("id_pm between", value1, value2, "idPm");
            return (Criteria) this;
        }

        public Criteria andIdPmNotBetween(Integer value1, Integer value2) {
            addCriterion("id_pm not between", value1, value2, "idPm");
            return (Criteria) this;
        }

        public Criteria andIdPmHeadIsNull() {
            addCriterion("id_pm_head is null");
            return (Criteria) this;
        }

        public Criteria andIdPmHeadIsNotNull() {
            addCriterion("id_pm_head is not null");
            return (Criteria) this;
        }

        public Criteria andIdPmHeadEqualTo(Integer value) {
            addCriterion("id_pm_head =", value, "idPmHead");
            return (Criteria) this;
        }

        public Criteria andIdPmHeadNotEqualTo(Integer value) {
            addCriterion("id_pm_head <>", value, "idPmHead");
            return (Criteria) this;
        }

        public Criteria andIdPmHeadGreaterThan(Integer value) {
            addCriterion("id_pm_head >", value, "idPmHead");
            return (Criteria) this;
        }

        public Criteria andIdPmHeadGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_pm_head >=", value, "idPmHead");
            return (Criteria) this;
        }

        public Criteria andIdPmHeadLessThan(Integer value) {
            addCriterion("id_pm_head <", value, "idPmHead");
            return (Criteria) this;
        }

        public Criteria andIdPmHeadLessThanOrEqualTo(Integer value) {
            addCriterion("id_pm_head <=", value, "idPmHead");
            return (Criteria) this;
        }

        public Criteria andIdPmHeadIn(List<Integer> values) {
            addCriterion("id_pm_head in", values, "idPmHead");
            return (Criteria) this;
        }

        public Criteria andIdPmHeadNotIn(List<Integer> values) {
            addCriterion("id_pm_head not in", values, "idPmHead");
            return (Criteria) this;
        }

        public Criteria andIdPmHeadBetween(Integer value1, Integer value2) {
            addCriterion("id_pm_head between", value1, value2, "idPmHead");
            return (Criteria) this;
        }

        public Criteria andIdPmHeadNotBetween(Integer value1, Integer value2) {
            addCriterion("id_pm_head not between", value1, value2, "idPmHead");
            return (Criteria) this;
        }

        public Criteria andIdMemberFromIsNull() {
            addCriterion("id_member_from is null");
            return (Criteria) this;
        }

        public Criteria andIdMemberFromIsNotNull() {
            addCriterion("id_member_from is not null");
            return (Criteria) this;
        }

        public Criteria andIdMemberFromEqualTo(Integer value) {
            addCriterion("id_member_from =", value, "idMemberFrom");
            return (Criteria) this;
        }

        public Criteria andIdMemberFromNotEqualTo(Integer value) {
            addCriterion("id_member_from <>", value, "idMemberFrom");
            return (Criteria) this;
        }

        public Criteria andIdMemberFromGreaterThan(Integer value) {
            addCriterion("id_member_from >", value, "idMemberFrom");
            return (Criteria) this;
        }

        public Criteria andIdMemberFromGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_member_from >=", value, "idMemberFrom");
            return (Criteria) this;
        }

        public Criteria andIdMemberFromLessThan(Integer value) {
            addCriterion("id_member_from <", value, "idMemberFrom");
            return (Criteria) this;
        }

        public Criteria andIdMemberFromLessThanOrEqualTo(Integer value) {
            addCriterion("id_member_from <=", value, "idMemberFrom");
            return (Criteria) this;
        }

        public Criteria andIdMemberFromIn(List<Integer> values) {
            addCriterion("id_member_from in", values, "idMemberFrom");
            return (Criteria) this;
        }

        public Criteria andIdMemberFromNotIn(List<Integer> values) {
            addCriterion("id_member_from not in", values, "idMemberFrom");
            return (Criteria) this;
        }

        public Criteria andIdMemberFromBetween(Integer value1, Integer value2) {
            addCriterion("id_member_from between", value1, value2, "idMemberFrom");
            return (Criteria) this;
        }

        public Criteria andIdMemberFromNotBetween(Integer value1, Integer value2) {
            addCriterion("id_member_from not between", value1, value2, "idMemberFrom");
            return (Criteria) this;
        }

        public Criteria andDeletedBySenderIsNull() {
            addCriterion("deleted_by_sender is null");
            return (Criteria) this;
        }

        public Criteria andDeletedBySenderIsNotNull() {
            addCriterion("deleted_by_sender is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedBySenderEqualTo(Boolean value) {
            addCriterion("deleted_by_sender =", value, "deletedBySender");
            return (Criteria) this;
        }

        public Criteria andDeletedBySenderNotEqualTo(Boolean value) {
            addCriterion("deleted_by_sender <>", value, "deletedBySender");
            return (Criteria) this;
        }

        public Criteria andDeletedBySenderGreaterThan(Boolean value) {
            addCriterion("deleted_by_sender >", value, "deletedBySender");
            return (Criteria) this;
        }

        public Criteria andDeletedBySenderGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deleted_by_sender >=", value, "deletedBySender");
            return (Criteria) this;
        }

        public Criteria andDeletedBySenderLessThan(Boolean value) {
            addCriterion("deleted_by_sender <", value, "deletedBySender");
            return (Criteria) this;
        }

        public Criteria andDeletedBySenderLessThanOrEqualTo(Boolean value) {
            addCriterion("deleted_by_sender <=", value, "deletedBySender");
            return (Criteria) this;
        }

        public Criteria andDeletedBySenderIn(List<Boolean> values) {
            addCriterion("deleted_by_sender in", values, "deletedBySender");
            return (Criteria) this;
        }

        public Criteria andDeletedBySenderNotIn(List<Boolean> values) {
            addCriterion("deleted_by_sender not in", values, "deletedBySender");
            return (Criteria) this;
        }

        public Criteria andDeletedBySenderBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted_by_sender between", value1, value2, "deletedBySender");
            return (Criteria) this;
        }

        public Criteria andDeletedBySenderNotBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted_by_sender not between", value1, value2, "deletedBySender");
            return (Criteria) this;
        }

        public Criteria andFromNameIsNull() {
            addCriterion("from_name is null");
            return (Criteria) this;
        }

        public Criteria andFromNameIsNotNull() {
            addCriterion("from_name is not null");
            return (Criteria) this;
        }

        public Criteria andFromNameEqualTo(String value) {
            addCriterion("from_name =", value, "fromName");
            return (Criteria) this;
        }

        public Criteria andFromNameNotEqualTo(String value) {
            addCriterion("from_name <>", value, "fromName");
            return (Criteria) this;
        }

        public Criteria andFromNameGreaterThan(String value) {
            addCriterion("from_name >", value, "fromName");
            return (Criteria) this;
        }

        public Criteria andFromNameGreaterThanOrEqualTo(String value) {
            addCriterion("from_name >=", value, "fromName");
            return (Criteria) this;
        }

        public Criteria andFromNameLessThan(String value) {
            addCriterion("from_name <", value, "fromName");
            return (Criteria) this;
        }

        public Criteria andFromNameLessThanOrEqualTo(String value) {
            addCriterion("from_name <=", value, "fromName");
            return (Criteria) this;
        }

        public Criteria andFromNameLike(String value) {
            addCriterion("from_name like", value, "fromName");
            return (Criteria) this;
        }

        public Criteria andFromNameNotLike(String value) {
            addCriterion("from_name not like", value, "fromName");
            return (Criteria) this;
        }

        public Criteria andFromNameIn(List<String> values) {
            addCriterion("from_name in", values, "fromName");
            return (Criteria) this;
        }

        public Criteria andFromNameNotIn(List<String> values) {
            addCriterion("from_name not in", values, "fromName");
            return (Criteria) this;
        }

        public Criteria andFromNameBetween(String value1, String value2) {
            addCriterion("from_name between", value1, value2, "fromName");
            return (Criteria) this;
        }

        public Criteria andFromNameNotBetween(String value1, String value2) {
            addCriterion("from_name not between", value1, value2, "fromName");
            return (Criteria) this;
        }

        public Criteria andMsgtimeIsNull() {
            addCriterion("msgtime is null");
            return (Criteria) this;
        }

        public Criteria andMsgtimeIsNotNull() {
            addCriterion("msgtime is not null");
            return (Criteria) this;
        }

        public Criteria andMsgtimeEqualTo(Integer value) {
            addCriterion("msgtime =", value, "msgtime");
            return (Criteria) this;
        }

        public Criteria andMsgtimeNotEqualTo(Integer value) {
            addCriterion("msgtime <>", value, "msgtime");
            return (Criteria) this;
        }

        public Criteria andMsgtimeGreaterThan(Integer value) {
            addCriterion("msgtime >", value, "msgtime");
            return (Criteria) this;
        }

        public Criteria andMsgtimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("msgtime >=", value, "msgtime");
            return (Criteria) this;
        }

        public Criteria andMsgtimeLessThan(Integer value) {
            addCriterion("msgtime <", value, "msgtime");
            return (Criteria) this;
        }

        public Criteria andMsgtimeLessThanOrEqualTo(Integer value) {
            addCriterion("msgtime <=", value, "msgtime");
            return (Criteria) this;
        }

        public Criteria andMsgtimeIn(List<Integer> values) {
            addCriterion("msgtime in", values, "msgtime");
            return (Criteria) this;
        }

        public Criteria andMsgtimeNotIn(List<Integer> values) {
            addCriterion("msgtime not in", values, "msgtime");
            return (Criteria) this;
        }

        public Criteria andMsgtimeBetween(Integer value1, Integer value2) {
            addCriterion("msgtime between", value1, value2, "msgtime");
            return (Criteria) this;
        }

        public Criteria andMsgtimeNotBetween(Integer value1, Integer value2) {
            addCriterion("msgtime not between", value1, value2, "msgtime");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNull() {
            addCriterion("subject is null");
            return (Criteria) this;
        }

        public Criteria andSubjectIsNotNull() {
            addCriterion("subject is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectEqualTo(String value) {
            addCriterion("subject =", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotEqualTo(String value) {
            addCriterion("subject <>", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectGreaterThan(String value) {
            addCriterion("subject >", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectGreaterThanOrEqualTo(String value) {
            addCriterion("subject >=", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLessThan(String value) {
            addCriterion("subject <", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLessThanOrEqualTo(String value) {
            addCriterion("subject <=", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectLike(String value) {
            addCriterion("subject like", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotLike(String value) {
            addCriterion("subject not like", value, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectIn(List<String> values) {
            addCriterion("subject in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotIn(List<String> values) {
            addCriterion("subject not in", values, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectBetween(String value1, String value2) {
            addCriterion("subject between", value1, value2, "subject");
            return (Criteria) this;
        }

        public Criteria andSubjectNotBetween(String value1, String value2) {
            addCriterion("subject not between", value1, value2, "subject");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.034910442-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.034920011-04:00", comments="Source Table: smf_1personal_messages")
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