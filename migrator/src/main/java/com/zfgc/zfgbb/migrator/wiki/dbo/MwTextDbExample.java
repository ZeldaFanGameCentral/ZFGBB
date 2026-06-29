package com.zfgc.zfgbb.migrator.wiki.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class MwTextDbExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728959767-04:00", comments="Source Table: zfgc_wikitext")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728975136-04:00", comments="Source Table: zfgc_wikitext")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728993416-04:00", comments="Source Table: zfgc_wikitext")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728953337-04:00", comments="Source Table: zfgc_wikitext")
    public MwTextDbExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728965407-04:00", comments="Source Table: zfgc_wikitext")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728970236-04:00", comments="Source Table: zfgc_wikitext")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728979836-04:00", comments="Source Table: zfgc_wikitext")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728984306-04:00", comments="Source Table: zfgc_wikitext")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.728996966-04:00", comments="Source Table: zfgc_wikitext")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729001795-04:00", comments="Source Table: zfgc_wikitext")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729006495-04:00", comments="Source Table: zfgc_wikitext")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729011365-04:00", comments="Source Table: zfgc_wikitext")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729015865-04:00", comments="Source Table: zfgc_wikitext")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729020225-04:00", comments="Source Table: zfgc_wikitext")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.729024685-04:00", comments="Source Table: zfgc_wikitext")
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

        public Criteria andOldIdIsNull() {
            addCriterion("old_id is null");
            return (Criteria) this;
        }

        public Criteria andOldIdIsNotNull() {
            addCriterion("old_id is not null");
            return (Criteria) this;
        }

        public Criteria andOldIdEqualTo(Integer value) {
            addCriterion("old_id =", value, "oldId");
            return (Criteria) this;
        }

        public Criteria andOldIdNotEqualTo(Integer value) {
            addCriterion("old_id <>", value, "oldId");
            return (Criteria) this;
        }

        public Criteria andOldIdGreaterThan(Integer value) {
            addCriterion("old_id >", value, "oldId");
            return (Criteria) this;
        }

        public Criteria andOldIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("old_id >=", value, "oldId");
            return (Criteria) this;
        }

        public Criteria andOldIdLessThan(Integer value) {
            addCriterion("old_id <", value, "oldId");
            return (Criteria) this;
        }

        public Criteria andOldIdLessThanOrEqualTo(Integer value) {
            addCriterion("old_id <=", value, "oldId");
            return (Criteria) this;
        }

        public Criteria andOldIdIn(List<Integer> values) {
            addCriterion("old_id in", values, "oldId");
            return (Criteria) this;
        }

        public Criteria andOldIdNotIn(List<Integer> values) {
            addCriterion("old_id not in", values, "oldId");
            return (Criteria) this;
        }

        public Criteria andOldIdBetween(Integer value1, Integer value2) {
            addCriterion("old_id between", value1, value2, "oldId");
            return (Criteria) this;
        }

        public Criteria andOldIdNotBetween(Integer value1, Integer value2) {
            addCriterion("old_id not between", value1, value2, "oldId");
            return (Criteria) this;
        }

        public Criteria andOldFlagsIsNull() {
            addCriterion("old_flags is null");
            return (Criteria) this;
        }

        public Criteria andOldFlagsIsNotNull() {
            addCriterion("old_flags is not null");
            return (Criteria) this;
        }

        public Criteria andOldFlagsEqualTo(String value) {
            addCriterion("old_flags =", value, "oldFlags");
            return (Criteria) this;
        }

        public Criteria andOldFlagsNotEqualTo(String value) {
            addCriterion("old_flags <>", value, "oldFlags");
            return (Criteria) this;
        }

        public Criteria andOldFlagsGreaterThan(String value) {
            addCriterion("old_flags >", value, "oldFlags");
            return (Criteria) this;
        }

        public Criteria andOldFlagsGreaterThanOrEqualTo(String value) {
            addCriterion("old_flags >=", value, "oldFlags");
            return (Criteria) this;
        }

        public Criteria andOldFlagsLessThan(String value) {
            addCriterion("old_flags <", value, "oldFlags");
            return (Criteria) this;
        }

        public Criteria andOldFlagsLessThanOrEqualTo(String value) {
            addCriterion("old_flags <=", value, "oldFlags");
            return (Criteria) this;
        }

        public Criteria andOldFlagsIn(List<String> values) {
            addCriterion("old_flags in", values, "oldFlags");
            return (Criteria) this;
        }

        public Criteria andOldFlagsNotIn(List<String> values) {
            addCriterion("old_flags not in", values, "oldFlags");
            return (Criteria) this;
        }

        public Criteria andOldFlagsBetween(String value1, String value2) {
            addCriterion("old_flags between", value1, value2, "oldFlags");
            return (Criteria) this;
        }

        public Criteria andOldFlagsNotBetween(String value1, String value2) {
            addCriterion("old_flags not between", value1, value2, "oldFlags");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.72917502-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.72918641-04:00", comments="Source Table: zfgc_wikitext")
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