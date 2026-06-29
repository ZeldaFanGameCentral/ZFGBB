package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class SMFGameCommentDbExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.031980518-04:00", comments="Source Table: smf_1game_comments")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.031993587-04:00", comments="Source Table: smf_1game_comments")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032011777-04:00", comments="Source Table: smf_1game_comments")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.031970518-04:00", comments="Source Table: smf_1game_comments")
    public SMFGameCommentDbExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.031984448-04:00", comments="Source Table: smf_1game_comments")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.031988978-04:00", comments="Source Table: smf_1game_comments")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.031998107-04:00", comments="Source Table: smf_1game_comments")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032002387-04:00", comments="Source Table: smf_1game_comments")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032016527-04:00", comments="Source Table: smf_1game_comments")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032020966-04:00", comments="Source Table: smf_1game_comments")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032025006-04:00", comments="Source Table: smf_1game_comments")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032029886-04:00", comments="Source Table: smf_1game_comments")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032036466-04:00", comments="Source Table: smf_1game_comments")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032040606-04:00", comments="Source Table: smf_1game_comments")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032046336-04:00", comments="Source Table: smf_1game_comments")
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
            addCriterion("ID_COMMENT is null");
            return (Criteria) this;
        }

        public Criteria andIdCommentIsNotNull() {
            addCriterion("ID_COMMENT is not null");
            return (Criteria) this;
        }

        public Criteria andIdCommentEqualTo(Integer value) {
            addCriterion("ID_COMMENT =", value, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdCommentNotEqualTo(Integer value) {
            addCriterion("ID_COMMENT <>", value, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdCommentGreaterThan(Integer value) {
            addCriterion("ID_COMMENT >", value, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdCommentGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID_COMMENT >=", value, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdCommentLessThan(Integer value) {
            addCriterion("ID_COMMENT <", value, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdCommentLessThanOrEqualTo(Integer value) {
            addCriterion("ID_COMMENT <=", value, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdCommentIn(List<Integer> values) {
            addCriterion("ID_COMMENT in", values, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdCommentNotIn(List<Integer> values) {
            addCriterion("ID_COMMENT not in", values, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdCommentBetween(Integer value1, Integer value2) {
            addCriterion("ID_COMMENT between", value1, value2, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdCommentNotBetween(Integer value1, Integer value2) {
            addCriterion("ID_COMMENT not between", value1, value2, "idComment");
            return (Criteria) this;
        }

        public Criteria andIdGameIsNull() {
            addCriterion("ID_GAME is null");
            return (Criteria) this;
        }

        public Criteria andIdGameIsNotNull() {
            addCriterion("ID_GAME is not null");
            return (Criteria) this;
        }

        public Criteria andIdGameEqualTo(Integer value) {
            addCriterion("ID_GAME =", value, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdGameNotEqualTo(Integer value) {
            addCriterion("ID_GAME <>", value, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdGameGreaterThan(Integer value) {
            addCriterion("ID_GAME >", value, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdGameGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID_GAME >=", value, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdGameLessThan(Integer value) {
            addCriterion("ID_GAME <", value, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdGameLessThanOrEqualTo(Integer value) {
            addCriterion("ID_GAME <=", value, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdGameIn(List<Integer> values) {
            addCriterion("ID_GAME in", values, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdGameNotIn(List<Integer> values) {
            addCriterion("ID_GAME not in", values, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdGameBetween(Integer value1, Integer value2) {
            addCriterion("ID_GAME between", value1, value2, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdGameNotBetween(Integer value1, Integer value2) {
            addCriterion("ID_GAME not between", value1, value2, "idGame");
            return (Criteria) this;
        }

        public Criteria andIdMemberIsNull() {
            addCriterion("ID_MEMBER is null");
            return (Criteria) this;
        }

        public Criteria andIdMemberIsNotNull() {
            addCriterion("ID_MEMBER is not null");
            return (Criteria) this;
        }

        public Criteria andIdMemberEqualTo(Integer value) {
            addCriterion("ID_MEMBER =", value, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberNotEqualTo(Integer value) {
            addCriterion("ID_MEMBER <>", value, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberGreaterThan(Integer value) {
            addCriterion("ID_MEMBER >", value, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID_MEMBER >=", value, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberLessThan(Integer value) {
            addCriterion("ID_MEMBER <", value, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberLessThanOrEqualTo(Integer value) {
            addCriterion("ID_MEMBER <=", value, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberIn(List<Integer> values) {
            addCriterion("ID_MEMBER in", values, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberNotIn(List<Integer> values) {
            addCriterion("ID_MEMBER not in", values, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberBetween(Integer value1, Integer value2) {
            addCriterion("ID_MEMBER between", value1, value2, "idMember");
            return (Criteria) this;
        }

        public Criteria andIdMemberNotBetween(Integer value1, Integer value2) {
            addCriterion("ID_MEMBER not between", value1, value2, "idMember");
            return (Criteria) this;
        }

        public Criteria andPosttimeIsNull() {
            addCriterion("postTime is null");
            return (Criteria) this;
        }

        public Criteria andPosttimeIsNotNull() {
            addCriterion("postTime is not null");
            return (Criteria) this;
        }

        public Criteria andPosttimeEqualTo(Integer value) {
            addCriterion("postTime =", value, "posttime");
            return (Criteria) this;
        }

        public Criteria andPosttimeNotEqualTo(Integer value) {
            addCriterion("postTime <>", value, "posttime");
            return (Criteria) this;
        }

        public Criteria andPosttimeGreaterThan(Integer value) {
            addCriterion("postTime >", value, "posttime");
            return (Criteria) this;
        }

        public Criteria andPosttimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("postTime >=", value, "posttime");
            return (Criteria) this;
        }

        public Criteria andPosttimeLessThan(Integer value) {
            addCriterion("postTime <", value, "posttime");
            return (Criteria) this;
        }

        public Criteria andPosttimeLessThanOrEqualTo(Integer value) {
            addCriterion("postTime <=", value, "posttime");
            return (Criteria) this;
        }

        public Criteria andPosttimeIn(List<Integer> values) {
            addCriterion("postTime in", values, "posttime");
            return (Criteria) this;
        }

        public Criteria andPosttimeNotIn(List<Integer> values) {
            addCriterion("postTime not in", values, "posttime");
            return (Criteria) this;
        }

        public Criteria andPosttimeBetween(Integer value1, Integer value2) {
            addCriterion("postTime between", value1, value2, "posttime");
            return (Criteria) this;
        }

        public Criteria andPosttimeNotBetween(Integer value1, Integer value2) {
            addCriterion("postTime not between", value1, value2, "posttime");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032176811-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.032185511-04:00", comments="Source Table: smf_1game_comments")
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