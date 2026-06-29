package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class SMFLogCommentDbExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039602047-04:00", comments="Source Table: smf_1log_comments")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039615867-04:00", comments="Source Table: smf_1log_comments")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039631836-04:00", comments="Source Table: smf_1log_comments")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039593468-04:00", comments="Source Table: smf_1log_comments")
    public SMFLogCommentDbExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039607237-04:00", comments="Source Table: smf_1log_comments")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039611607-04:00", comments="Source Table: smf_1log_comments")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039620027-04:00", comments="Source Table: smf_1log_comments")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039622617-04:00", comments="Source Table: smf_1log_comments")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039636396-04:00", comments="Source Table: smf_1log_comments")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039640566-04:00", comments="Source Table: smf_1log_comments")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039644786-04:00", comments="Source Table: smf_1log_comments")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039648796-04:00", comments="Source Table: smf_1log_comments")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039652766-04:00", comments="Source Table: smf_1log_comments")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039656526-04:00", comments="Source Table: smf_1log_comments")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039661425-04:00", comments="Source Table: smf_1log_comments")
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

        public Criteria andIdCommentIsNull() {
            addCriterion("id_comment is null");
            return (Criteria) this;
        }

        public Criteria andIdCommentIsNotNull() {
            addCriterion("id_comment is not null");
            return (Criteria) this;
        }

        public Criteria andIdCommentEqualTo(Integer value) {
            addCriterion("id_comment =", value, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdCommentNotEqualTo(Integer value) {
            addCriterion("id_comment <>", value, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdCommentGreaterThan(Integer value) {
            addCriterion("id_comment >", value, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdCommentGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_comment >=", value, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdCommentLessThan(Integer value) {
            addCriterion("id_comment <", value, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdCommentLessThanOrEqualTo(Integer value) {
            addCriterion("id_comment <=", value, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdCommentIn(List<Integer> values) {
            addCriterion("id_comment in", values, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdCommentNotIn(List<Integer> values) {
            addCriterion("id_comment not in", values, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdCommentBetween(Integer value1, Integer value2) {
            addCriterion("id_comment between", value1, value2, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdCommentNotBetween(Integer value1, Integer value2) {
            addCriterion("id_comment not between", value1, value2, "idComment");
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

        public Criteria andMemberNameIsNull() {
            addCriterion("member_name is null");
            return (Criteria) this;
        }

        public Criteria andMemberNameIsNotNull() {
            addCriterion("member_name is not null");
            return (Criteria) this;
        }

        public Criteria andMemberNameEqualTo(String value) {
            addCriterion("member_name =", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameNotEqualTo(String value) {
            addCriterion("member_name <>", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameGreaterThan(String value) {
            addCriterion("member_name >", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameGreaterThanOrEqualTo(String value) {
            addCriterion("member_name >=", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameLessThan(String value) {
            addCriterion("member_name <", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameLessThanOrEqualTo(String value) {
            addCriterion("member_name <=", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameLike(String value) {
            addCriterion("member_name like", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameNotLike(String value) {
            addCriterion("member_name not like", value, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameIn(List<String> values) {
            addCriterion("member_name in", values, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameNotIn(List<String> values) {
            addCriterion("member_name not in", values, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameBetween(String value1, String value2) {
            addCriterion("member_name between", value1, value2, "memberName");
            return (Criteria) this;
        }

        public Criteria andMemberNameNotBetween(String value1, String value2) {
            addCriterion("member_name not between", value1, value2, "memberName");
            return (Criteria) this;
        }

        public Criteria andCommentTypeIsNull() {
            addCriterion("comment_type is null");
            return (Criteria) this;
        }

        public Criteria andCommentTypeIsNotNull() {
            addCriterion("comment_type is not null");
            return (Criteria) this;
        }

        public Criteria andCommentTypeEqualTo(String value) {
            addCriterion("comment_type =", value, "commentType");
            return (Criteria) this;
        }

        public Criteria andCommentTypeNotEqualTo(String value) {
            addCriterion("comment_type <>", value, "commentType");
            return (Criteria) this;
        }

        public Criteria andCommentTypeGreaterThan(String value) {
            addCriterion("comment_type >", value, "commentType");
            return (Criteria) this;
        }

        public Criteria andCommentTypeGreaterThanOrEqualTo(String value) {
            addCriterion("comment_type >=", value, "commentType");
            return (Criteria) this;
        }

        public Criteria andCommentTypeLessThan(String value) {
            addCriterion("comment_type <", value, "commentType");
            return (Criteria) this;
        }

        public Criteria andCommentTypeLessThanOrEqualTo(String value) {
            addCriterion("comment_type <=", value, "commentType");
            return (Criteria) this;
        }

        public Criteria andCommentTypeLike(String value) {
            addCriterion("comment_type like", value, "commentType");
            return (Criteria) this;
        }

        public Criteria andCommentTypeNotLike(String value) {
            addCriterion("comment_type not like", value, "commentType");
            return (Criteria) this;
        }

        public Criteria andCommentTypeIn(List<String> values) {
            addCriterion("comment_type in", values, "commentType");
            return (Criteria) this;
        }

        public Criteria andCommentTypeNotIn(List<String> values) {
            addCriterion("comment_type not in", values, "commentType");
            return (Criteria) this;
        }

        public Criteria andCommentTypeBetween(String value1, String value2) {
            addCriterion("comment_type between", value1, value2, "commentType");
            return (Criteria) this;
        }

        public Criteria andCommentTypeNotBetween(String value1, String value2) {
            addCriterion("comment_type not between", value1, value2, "commentType");
            return (Criteria) this;
        }

        public Criteria andIdRecipientIsNull() {
            addCriterion("id_recipient is null");
            return (Criteria) this;
        }

        public Criteria andIdRecipientIsNotNull() {
            addCriterion("id_recipient is not null");
            return (Criteria) this;
        }

        public Criteria andIdRecipientEqualTo(Integer value) {
            addCriterion("id_recipient =", value, "idRecipient");
            return (Criteria) this;
        }

        public Criteria andIdRecipientNotEqualTo(Integer value) {
            addCriterion("id_recipient <>", value, "idRecipient");
            return (Criteria) this;
        }

        public Criteria andIdRecipientGreaterThan(Integer value) {
            addCriterion("id_recipient >", value, "idRecipient");
            return (Criteria) this;
        }

        public Criteria andIdRecipientGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_recipient >=", value, "idRecipient");
            return (Criteria) this;
        }

        public Criteria andIdRecipientLessThan(Integer value) {
            addCriterion("id_recipient <", value, "idRecipient");
            return (Criteria) this;
        }

        public Criteria andIdRecipientLessThanOrEqualTo(Integer value) {
            addCriterion("id_recipient <=", value, "idRecipient");
            return (Criteria) this;
        }

        public Criteria andIdRecipientIn(List<Integer> values) {
            addCriterion("id_recipient in", values, "idRecipient");
            return (Criteria) this;
        }

        public Criteria andIdRecipientNotIn(List<Integer> values) {
            addCriterion("id_recipient not in", values, "idRecipient");
            return (Criteria) this;
        }

        public Criteria andIdRecipientBetween(Integer value1, Integer value2) {
            addCriterion("id_recipient between", value1, value2, "idRecipient");
            return (Criteria) this;
        }

        public Criteria andIdRecipientNotBetween(Integer value1, Integer value2) {
            addCriterion("id_recipient not between", value1, value2, "idRecipient");
            return (Criteria) this;
        }

        public Criteria andRecipientNameIsNull() {
            addCriterion("recipient_name is null");
            return (Criteria) this;
        }

        public Criteria andRecipientNameIsNotNull() {
            addCriterion("recipient_name is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientNameEqualTo(String value) {
            addCriterion("recipient_name =", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameNotEqualTo(String value) {
            addCriterion("recipient_name <>", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameGreaterThan(String value) {
            addCriterion("recipient_name >", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_name >=", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameLessThan(String value) {
            addCriterion("recipient_name <", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameLessThanOrEqualTo(String value) {
            addCriterion("recipient_name <=", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameLike(String value) {
            addCriterion("recipient_name like", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameNotLike(String value) {
            addCriterion("recipient_name not like", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameIn(List<String> values) {
            addCriterion("recipient_name in", values, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameNotIn(List<String> values) {
            addCriterion("recipient_name not in", values, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameBetween(String value1, String value2) {
            addCriterion("recipient_name between", value1, value2, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameNotBetween(String value1, String value2) {
            addCriterion("recipient_name not between", value1, value2, "recipientName");
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

        public Criteria andIdNoticeIsNull() {
            addCriterion("id_notice is null");
            return (Criteria) this;
        }

        public Criteria andIdNoticeIsNotNull() {
            addCriterion("id_notice is not null");
            return (Criteria) this;
        }

        public Criteria andIdNoticeEqualTo(Integer value) {
            addCriterion("id_notice =", value, "idNotice");
            return (Criteria) this;
        }

        public Criteria andIdNoticeNotEqualTo(Integer value) {
            addCriterion("id_notice <>", value, "idNotice");
            return (Criteria) this;
        }

        public Criteria andIdNoticeGreaterThan(Integer value) {
            addCriterion("id_notice >", value, "idNotice");
            return (Criteria) this;
        }

        public Criteria andIdNoticeGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_notice >=", value, "idNotice");
            return (Criteria) this;
        }

        public Criteria andIdNoticeLessThan(Integer value) {
            addCriterion("id_notice <", value, "idNotice");
            return (Criteria) this;
        }

        public Criteria andIdNoticeLessThanOrEqualTo(Integer value) {
            addCriterion("id_notice <=", value, "idNotice");
            return (Criteria) this;
        }

        public Criteria andIdNoticeIn(List<Integer> values) {
            addCriterion("id_notice in", values, "idNotice");
            return (Criteria) this;
        }

        public Criteria andIdNoticeNotIn(List<Integer> values) {
            addCriterion("id_notice not in", values, "idNotice");
            return (Criteria) this;
        }

        public Criteria andIdNoticeBetween(Integer value1, Integer value2) {
            addCriterion("id_notice between", value1, value2, "idNotice");
            return (Criteria) this;
        }

        public Criteria andIdNoticeNotBetween(Integer value1, Integer value2) {
            addCriterion("id_notice not between", value1, value2, "idNotice");
            return (Criteria) this;
        }

        public Criteria andCounterIsNull() {
            addCriterion("counter is null");
            return (Criteria) this;
        }

        public Criteria andCounterIsNotNull() {
            addCriterion("counter is not null");
            return (Criteria) this;
        }

        public Criteria andCounterEqualTo(Integer value) {
            addCriterion("counter =", value, "counter");
            return (Criteria) this;
        }

        public Criteria andCounterNotEqualTo(Integer value) {
            addCriterion("counter <>", value, "counter");
            return (Criteria) this;
        }

        public Criteria andCounterGreaterThan(Integer value) {
            addCriterion("counter >", value, "counter");
            return (Criteria) this;
        }

        public Criteria andCounterGreaterThanOrEqualTo(Integer value) {
            addCriterion("counter >=", value, "counter");
            return (Criteria) this;
        }

        public Criteria andCounterLessThan(Integer value) {
            addCriterion("counter <", value, "counter");
            return (Criteria) this;
        }

        public Criteria andCounterLessThanOrEqualTo(Integer value) {
            addCriterion("counter <=", value, "counter");
            return (Criteria) this;
        }

        public Criteria andCounterIn(List<Integer> values) {
            addCriterion("counter in", values, "counter");
            return (Criteria) this;
        }

        public Criteria andCounterNotIn(List<Integer> values) {
            addCriterion("counter not in", values, "counter");
            return (Criteria) this;
        }

        public Criteria andCounterBetween(Integer value1, Integer value2) {
            addCriterion("counter between", value1, value2, "counter");
            return (Criteria) this;
        }

        public Criteria andCounterNotBetween(Integer value1, Integer value2) {
            addCriterion("counter not between", value1, value2, "counter");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039905677-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.039916197-04:00", comments="Source Table: smf_1log_comments")
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