package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class SMFPmRecipientDbExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.035963037-04:00", comments="Source Table: smf_1pm_recipients")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.035978637-04:00", comments="Source Table: smf_1pm_recipients")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.035995846-04:00", comments="Source Table: smf_1pm_recipients")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.035950657-04:00", comments="Source Table: smf_1pm_recipients")
    public SMFPmRecipientDbExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.035968937-04:00", comments="Source Table: smf_1pm_recipients")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.035974127-04:00", comments="Source Table: smf_1pm_recipients")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.035982996-04:00", comments="Source Table: smf_1pm_recipients")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.035987136-04:00", comments="Source Table: smf_1pm_recipients")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.035999046-04:00", comments="Source Table: smf_1pm_recipients")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036003896-04:00", comments="Source Table: smf_1pm_recipients")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036008546-04:00", comments="Source Table: smf_1pm_recipients")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036014995-04:00", comments="Source Table: smf_1pm_recipients")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036019135-04:00", comments="Source Table: smf_1pm_recipients")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036023345-04:00", comments="Source Table: smf_1pm_recipients")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036029125-04:00", comments="Source Table: smf_1pm_recipients")
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

        public Criteria andLabelsIsNull() {
            addCriterion("labels is null");
            return (Criteria) this;
        }

        public Criteria andLabelsIsNotNull() {
            addCriterion("labels is not null");
            return (Criteria) this;
        }

        public Criteria andLabelsEqualTo(String value) {
            addCriterion("labels =", value, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsNotEqualTo(String value) {
            addCriterion("labels <>", value, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsGreaterThan(String value) {
            addCriterion("labels >", value, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsGreaterThanOrEqualTo(String value) {
            addCriterion("labels >=", value, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsLessThan(String value) {
            addCriterion("labels <", value, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsLessThanOrEqualTo(String value) {
            addCriterion("labels <=", value, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsLike(String value) {
            addCriterion("labels like", value, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsNotLike(String value) {
            addCriterion("labels not like", value, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsIn(List<String> values) {
            addCriterion("labels in", values, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsNotIn(List<String> values) {
            addCriterion("labels not in", values, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsBetween(String value1, String value2) {
            addCriterion("labels between", value1, value2, "labels");
            return (Criteria) this;
        }

        public Criteria andLabelsNotBetween(String value1, String value2) {
            addCriterion("labels not between", value1, value2, "labels");
            return (Criteria) this;
        }

        public Criteria andBccIsNull() {
            addCriterion("bcc is null");
            return (Criteria) this;
        }

        public Criteria andBccIsNotNull() {
            addCriterion("bcc is not null");
            return (Criteria) this;
        }

        public Criteria andBccEqualTo(Boolean value) {
            addCriterion("bcc =", value, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccNotEqualTo(Boolean value) {
            addCriterion("bcc <>", value, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccGreaterThan(Boolean value) {
            addCriterion("bcc >", value, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccGreaterThanOrEqualTo(Boolean value) {
            addCriterion("bcc >=", value, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccLessThan(Boolean value) {
            addCriterion("bcc <", value, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccLessThanOrEqualTo(Boolean value) {
            addCriterion("bcc <=", value, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccIn(List<Boolean> values) {
            addCriterion("bcc in", values, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccNotIn(List<Boolean> values) {
            addCriterion("bcc not in", values, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccBetween(Boolean value1, Boolean value2) {
            addCriterion("bcc between", value1, value2, "bcc");
            return (Criteria) this;
        }

        public Criteria andBccNotBetween(Boolean value1, Boolean value2) {
            addCriterion("bcc not between", value1, value2, "bcc");
            return (Criteria) this;
        }

        public Criteria andIsReadIsNull() {
            addCriterion("is_read is null");
            return (Criteria) this;
        }

        public Criteria andIsReadIsNotNull() {
            addCriterion("is_read is not null");
            return (Criteria) this;
        }

        public Criteria andIsReadEqualTo(Integer value) {
            addCriterion("is_read =", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadNotEqualTo(Integer value) {
            addCriterion("is_read <>", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadGreaterThan(Integer value) {
            addCriterion("is_read >", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_read >=", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadLessThan(Integer value) {
            addCriterion("is_read <", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadLessThanOrEqualTo(Integer value) {
            addCriterion("is_read <=", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadIn(List<Integer> values) {
            addCriterion("is_read in", values, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadNotIn(List<Integer> values) {
            addCriterion("is_read not in", values, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadBetween(Integer value1, Integer value2) {
            addCriterion("is_read between", value1, value2, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadNotBetween(Integer value1, Integer value2) {
            addCriterion("is_read not between", value1, value2, "isRead");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNull() {
            addCriterion("deleted is null");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNotNull() {
            addCriterion("deleted is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedEqualTo(Boolean value) {
            addCriterion("deleted =", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(Boolean value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(Boolean value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(Boolean value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedIn(List<Boolean> values) {
            addCriterion("deleted in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotIn(List<Boolean> values) {
            addCriterion("deleted not in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted not between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andIsNewIsNull() {
            addCriterion("is_new is null");
            return (Criteria) this;
        }

        public Criteria andIsNewIsNotNull() {
            addCriterion("is_new is not null");
            return (Criteria) this;
        }

        public Criteria andIsNewEqualTo(Integer value) {
            addCriterion("is_new =", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewNotEqualTo(Integer value) {
            addCriterion("is_new <>", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewGreaterThan(Integer value) {
            addCriterion("is_new >", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_new >=", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewLessThan(Integer value) {
            addCriterion("is_new <", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewLessThanOrEqualTo(Integer value) {
            addCriterion("is_new <=", value, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewIn(List<Integer> values) {
            addCriterion("is_new in", values, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewNotIn(List<Integer> values) {
            addCriterion("is_new not in", values, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewBetween(Integer value1, Integer value2) {
            addCriterion("is_new between", value1, value2, "isNew");
            return (Criteria) this;
        }

        public Criteria andIsNewNotBetween(Integer value1, Integer value2) {
            addCriterion("is_new not between", value1, value2, "isNew");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036214839-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.036223398-04:00", comments="Source Table: smf_1pm_recipients")
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