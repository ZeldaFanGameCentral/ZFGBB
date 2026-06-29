package com.zfgc.zfgbb.migrator.ci.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class CiPotmDbExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.049879569-04:00", comments="Source Table: ci_potms")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.049893929-04:00", comments="Source Table: ci_potms")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.049910758-04:00", comments="Source Table: ci_potms")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.04986997-04:00", comments="Source Table: ci_potms")
    public CiPotmDbExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.049886959-04:00", comments="Source Table: ci_potms")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.049891169-04:00", comments="Source Table: ci_potms")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.049898129-04:00", comments="Source Table: ci_potms")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.049902149-04:00", comments="Source Table: ci_potms")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.049913708-04:00", comments="Source Table: ci_potms")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.049917988-04:00", comments="Source Table: ci_potms")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.049922088-04:00", comments="Source Table: ci_potms")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.049926018-04:00", comments="Source Table: ci_potms")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.049928458-04:00", comments="Source Table: ci_potms")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.049932278-04:00", comments="Source Table: ci_potms")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.049938397-04:00", comments="Source Table: ci_potms")
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPreviewIsNull() {
            addCriterion("preview is null");
            return (Criteria) this;
        }

        public Criteria andPreviewIsNotNull() {
            addCriterion("preview is not null");
            return (Criteria) this;
        }

        public Criteria andPreviewEqualTo(String value) {
            addCriterion("preview =", value, "preview");
            return (Criteria) this;
        }

        public Criteria andPreviewNotEqualTo(String value) {
            addCriterion("preview <>", value, "preview");
            return (Criteria) this;
        }

        public Criteria andPreviewGreaterThan(String value) {
            addCriterion("preview >", value, "preview");
            return (Criteria) this;
        }

        public Criteria andPreviewGreaterThanOrEqualTo(String value) {
            addCriterion("preview >=", value, "preview");
            return (Criteria) this;
        }

        public Criteria andPreviewLessThan(String value) {
            addCriterion("preview <", value, "preview");
            return (Criteria) this;
        }

        public Criteria andPreviewLessThanOrEqualTo(String value) {
            addCriterion("preview <=", value, "preview");
            return (Criteria) this;
        }

        public Criteria andPreviewLike(String value) {
            addCriterion("preview like", value, "preview");
            return (Criteria) this;
        }

        public Criteria andPreviewNotLike(String value) {
            addCriterion("preview not like", value, "preview");
            return (Criteria) this;
        }

        public Criteria andPreviewIn(List<String> values) {
            addCriterion("preview in", values, "preview");
            return (Criteria) this;
        }

        public Criteria andPreviewNotIn(List<String> values) {
            addCriterion("preview not in", values, "preview");
            return (Criteria) this;
        }

        public Criteria andPreviewBetween(String value1, String value2) {
            addCriterion("preview between", value1, value2, "preview");
            return (Criteria) this;
        }

        public Criteria andPreviewNotBetween(String value1, String value2) {
            addCriterion("preview not between", value1, value2, "preview");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNull() {
            addCriterion("project_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNotNull() {
            addCriterion("project_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectIdEqualTo(Integer value) {
            addCriterion("project_id =", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotEqualTo(Integer value) {
            addCriterion("project_id <>", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThan(Integer value) {
            addCriterion("project_id >", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_id >=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThan(Integer value) {
            addCriterion("project_id <", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThanOrEqualTo(Integer value) {
            addCriterion("project_id <=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdIn(List<Integer> values) {
            addCriterion("project_id in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotIn(List<Integer> values) {
            addCriterion("project_id not in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdBetween(Integer value1, Integer value2) {
            addCriterion("project_id between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotBetween(Integer value1, Integer value2) {
            addCriterion("project_id not between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectTitleIsNull() {
            addCriterion("project_title is null");
            return (Criteria) this;
        }

        public Criteria andProjectTitleIsNotNull() {
            addCriterion("project_title is not null");
            return (Criteria) this;
        }

        public Criteria andProjectTitleEqualTo(String value) {
            addCriterion("project_title =", value, "projectTitle");
            return (Criteria) this;
        }

        public Criteria andProjectTitleNotEqualTo(String value) {
            addCriterion("project_title <>", value, "projectTitle");
            return (Criteria) this;
        }

        public Criteria andProjectTitleGreaterThan(String value) {
            addCriterion("project_title >", value, "projectTitle");
            return (Criteria) this;
        }

        public Criteria andProjectTitleGreaterThanOrEqualTo(String value) {
            addCriterion("project_title >=", value, "projectTitle");
            return (Criteria) this;
        }

        public Criteria andProjectTitleLessThan(String value) {
            addCriterion("project_title <", value, "projectTitle");
            return (Criteria) this;
        }

        public Criteria andProjectTitleLessThanOrEqualTo(String value) {
            addCriterion("project_title <=", value, "projectTitle");
            return (Criteria) this;
        }

        public Criteria andProjectTitleLike(String value) {
            addCriterion("project_title like", value, "projectTitle");
            return (Criteria) this;
        }

        public Criteria andProjectTitleNotLike(String value) {
            addCriterion("project_title not like", value, "projectTitle");
            return (Criteria) this;
        }

        public Criteria andProjectTitleIn(List<String> values) {
            addCriterion("project_title in", values, "projectTitle");
            return (Criteria) this;
        }

        public Criteria andProjectTitleNotIn(List<String> values) {
            addCriterion("project_title not in", values, "projectTitle");
            return (Criteria) this;
        }

        public Criteria andProjectTitleBetween(String value1, String value2) {
            addCriterion("project_title between", value1, value2, "projectTitle");
            return (Criteria) this;
        }

        public Criteria andProjectTitleNotBetween(String value1, String value2) {
            addCriterion("project_title not between", value1, value2, "projectTitle");
            return (Criteria) this;
        }

        public Criteria andTimeIsNull() {
            addCriterion("time is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("time is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(Integer value) {
            addCriterion("time =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(Integer value) {
            addCriterion("time <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(Integer value) {
            addCriterion("time >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("time >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(Integer value) {
            addCriterion("time <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(Integer value) {
            addCriterion("time <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<Integer> values) {
            addCriterion("time in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<Integer> values) {
            addCriterion("time not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(Integer value1, Integer value2) {
            addCriterion("time between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("time not between", value1, value2, "time");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.050083923-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.050092192-04:00", comments="Source Table: ci_potms")
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