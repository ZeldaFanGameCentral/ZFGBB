package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class UserReactionSummaryViewDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.625929581-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.626554401-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.626709327-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    protected List<Criteria> oredCriteria;

    protected Integer limit;

    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.62466548-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    public UserReactionSummaryViewDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.626140304-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.626195613-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.62660488-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.626644889-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.626762415-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.626895461-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.626965778-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.627015027-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.627064015-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.627103664-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
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

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.627263029-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andReputationPointsIsNull() {
            addCriterion("reputation_points is null");
            return (Criteria) this;
        }

        public Criteria andReputationPointsIsNotNull() {
            addCriterion("reputation_points is not null");
            return (Criteria) this;
        }

        public Criteria andReputationPointsEqualTo(Long value) {
            addCriterion("reputation_points =", value, "reputationPoints");
            return (Criteria) this;
        }

        public Criteria andReputationPointsNotEqualTo(Long value) {
            addCriterion("reputation_points <>", value, "reputationPoints");
            return (Criteria) this;
        }

        public Criteria andReputationPointsGreaterThan(Long value) {
            addCriterion("reputation_points >", value, "reputationPoints");
            return (Criteria) this;
        }

        public Criteria andReputationPointsGreaterThanOrEqualTo(Long value) {
            addCriterion("reputation_points >=", value, "reputationPoints");
            return (Criteria) this;
        }

        public Criteria andReputationPointsLessThan(Long value) {
            addCriterion("reputation_points <", value, "reputationPoints");
            return (Criteria) this;
        }

        public Criteria andReputationPointsLessThanOrEqualTo(Long value) {
            addCriterion("reputation_points <=", value, "reputationPoints");
            return (Criteria) this;
        }

        public Criteria andReputationPointsIn(List<Long> values) {
            addCriterion("reputation_points in", values, "reputationPoints");
            return (Criteria) this;
        }

        public Criteria andReputationPointsNotIn(List<Long> values) {
            addCriterion("reputation_points not in", values, "reputationPoints");
            return (Criteria) this;
        }

        public Criteria andReputationPointsBetween(Long value1, Long value2) {
            addCriterion("reputation_points between", value1, value2, "reputationPoints");
            return (Criteria) this;
        }

        public Criteria andReputationPointsNotBetween(Long value1, Long value2) {
            addCriterion("reputation_points not between", value1, value2, "reputationPoints");
            return (Criteria) this;
        }

        public Criteria andPositiveCountIsNull() {
            addCriterion("positive_count is null");
            return (Criteria) this;
        }

        public Criteria andPositiveCountIsNotNull() {
            addCriterion("positive_count is not null");
            return (Criteria) this;
        }

        public Criteria andPositiveCountEqualTo(Long value) {
            addCriterion("positive_count =", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountNotEqualTo(Long value) {
            addCriterion("positive_count <>", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountGreaterThan(Long value) {
            addCriterion("positive_count >", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountGreaterThanOrEqualTo(Long value) {
            addCriterion("positive_count >=", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountLessThan(Long value) {
            addCriterion("positive_count <", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountLessThanOrEqualTo(Long value) {
            addCriterion("positive_count <=", value, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountIn(List<Long> values) {
            addCriterion("positive_count in", values, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountNotIn(List<Long> values) {
            addCriterion("positive_count not in", values, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountBetween(Long value1, Long value2) {
            addCriterion("positive_count between", value1, value2, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andPositiveCountNotBetween(Long value1, Long value2) {
            addCriterion("positive_count not between", value1, value2, "positiveCount");
            return (Criteria) this;
        }

        public Criteria andNegativeCountIsNull() {
            addCriterion("negative_count is null");
            return (Criteria) this;
        }

        public Criteria andNegativeCountIsNotNull() {
            addCriterion("negative_count is not null");
            return (Criteria) this;
        }

        public Criteria andNegativeCountEqualTo(Long value) {
            addCriterion("negative_count =", value, "negativeCount");
            return (Criteria) this;
        }

        public Criteria andNegativeCountNotEqualTo(Long value) {
            addCriterion("negative_count <>", value, "negativeCount");
            return (Criteria) this;
        }

        public Criteria andNegativeCountGreaterThan(Long value) {
            addCriterion("negative_count >", value, "negativeCount");
            return (Criteria) this;
        }

        public Criteria andNegativeCountGreaterThanOrEqualTo(Long value) {
            addCriterion("negative_count >=", value, "negativeCount");
            return (Criteria) this;
        }

        public Criteria andNegativeCountLessThan(Long value) {
            addCriterion("negative_count <", value, "negativeCount");
            return (Criteria) this;
        }

        public Criteria andNegativeCountLessThanOrEqualTo(Long value) {
            addCriterion("negative_count <=", value, "negativeCount");
            return (Criteria) this;
        }

        public Criteria andNegativeCountIn(List<Long> values) {
            addCriterion("negative_count in", values, "negativeCount");
            return (Criteria) this;
        }

        public Criteria andNegativeCountNotIn(List<Long> values) {
            addCriterion("negative_count not in", values, "negativeCount");
            return (Criteria) this;
        }

        public Criteria andNegativeCountBetween(Long value1, Long value2) {
            addCriterion("negative_count between", value1, value2, "negativeCount");
            return (Criteria) this;
        }

        public Criteria andNegativeCountNotBetween(Long value1, Long value2) {
            addCriterion("negative_count not between", value1, value2, "negativeCount");
            return (Criteria) this;
        }

        public Criteria andReactionCountIsNull() {
            addCriterion("reaction_count is null");
            return (Criteria) this;
        }

        public Criteria andReactionCountIsNotNull() {
            addCriterion("reaction_count is not null");
            return (Criteria) this;
        }

        public Criteria andReactionCountEqualTo(Long value) {
            addCriterion("reaction_count =", value, "reactionCount");
            return (Criteria) this;
        }

        public Criteria andReactionCountNotEqualTo(Long value) {
            addCriterion("reaction_count <>", value, "reactionCount");
            return (Criteria) this;
        }

        public Criteria andReactionCountGreaterThan(Long value) {
            addCriterion("reaction_count >", value, "reactionCount");
            return (Criteria) this;
        }

        public Criteria andReactionCountGreaterThanOrEqualTo(Long value) {
            addCriterion("reaction_count >=", value, "reactionCount");
            return (Criteria) this;
        }

        public Criteria andReactionCountLessThan(Long value) {
            addCriterion("reaction_count <", value, "reactionCount");
            return (Criteria) this;
        }

        public Criteria andReactionCountLessThanOrEqualTo(Long value) {
            addCriterion("reaction_count <=", value, "reactionCount");
            return (Criteria) this;
        }

        public Criteria andReactionCountIn(List<Long> values) {
            addCriterion("reaction_count in", values, "reactionCount");
            return (Criteria) this;
        }

        public Criteria andReactionCountNotIn(List<Long> values) {
            addCriterion("reaction_count not in", values, "reactionCount");
            return (Criteria) this;
        }

        public Criteria andReactionCountBetween(Long value1, Long value2) {
            addCriterion("reaction_count between", value1, value2, "reactionCount");
            return (Criteria) this;
        }

        public Criteria andReactionCountNotBetween(Long value1, Long value2) {
            addCriterion("reaction_count not between", value1, value2, "reactionCount");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.629886158-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T20:08:12.630022853-04:00", comments="Source Table: zfgbb.user_reaction_summary_view")
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