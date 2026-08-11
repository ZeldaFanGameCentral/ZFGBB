package com.zfgc.zfgbb.dbo;

import com.zfgc.zfgbb.persistence.LikePatterns;
import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class WikiNamespaceDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    protected Integer limit;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    public WikiNamespaceDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    public Integer getLimit() {
        return limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    public Integer getOffset() {
        return offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name ilike", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not ilike", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andCaseModeIsNull() {
            addCriterion("case_mode is null");
            return (Criteria) this;
        }

        public Criteria andCaseModeIsNotNull() {
            addCriterion("case_mode is not null");
            return (Criteria) this;
        }

        public Criteria andCaseModeEqualTo(String value) {
            addCriterion("case_mode =", value, "caseMode");
            return (Criteria) this;
        }

        public Criteria andCaseModeNotEqualTo(String value) {
            addCriterion("case_mode <>", value, "caseMode");
            return (Criteria) this;
        }

        public Criteria andCaseModeGreaterThan(String value) {
            addCriterion("case_mode >", value, "caseMode");
            return (Criteria) this;
        }

        public Criteria andCaseModeGreaterThanOrEqualTo(String value) {
            addCriterion("case_mode >=", value, "caseMode");
            return (Criteria) this;
        }

        public Criteria andCaseModeLessThan(String value) {
            addCriterion("case_mode <", value, "caseMode");
            return (Criteria) this;
        }

        public Criteria andCaseModeLessThanOrEqualTo(String value) {
            addCriterion("case_mode <=", value, "caseMode");
            return (Criteria) this;
        }

        public Criteria andCaseModeLike(String value) {
            addCriterion("case_mode ilike", value, "caseMode");
            return (Criteria) this;
        }

        public Criteria andCaseModeNotLike(String value) {
            addCriterion("case_mode not ilike", value, "caseMode");
            return (Criteria) this;
        }

        public Criteria andCaseModeIn(List<String> values) {
            addCriterion("case_mode in", values, "caseMode");
            return (Criteria) this;
        }

        public Criteria andCaseModeNotIn(List<String> values) {
            addCriterion("case_mode not in", values, "caseMode");
            return (Criteria) this;
        }

        public Criteria andCaseModeBetween(String value1, String value2) {
            addCriterion("case_mode between", value1, value2, "caseMode");
            return (Criteria) this;
        }

        public Criteria andCaseModeNotBetween(String value1, String value2) {
            addCriterion("case_mode not between", value1, value2, "caseMode");
            return (Criteria) this;
        }

        public Criteria andSystemManagedIsNull() {
            addCriterion("system_managed is null");
            return (Criteria) this;
        }

        public Criteria andSystemManagedIsNotNull() {
            addCriterion("system_managed is not null");
            return (Criteria) this;
        }

        public Criteria andSystemManagedEqualTo(Boolean value) {
            addCriterion("system_managed =", value, "systemManaged");
            return (Criteria) this;
        }

        public Criteria andSystemManagedNotEqualTo(Boolean value) {
            addCriterion("system_managed <>", value, "systemManaged");
            return (Criteria) this;
        }

        public Criteria andSystemManagedGreaterThan(Boolean value) {
            addCriterion("system_managed >", value, "systemManaged");
            return (Criteria) this;
        }

        public Criteria andSystemManagedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("system_managed >=", value, "systemManaged");
            return (Criteria) this;
        }

        public Criteria andSystemManagedLessThan(Boolean value) {
            addCriterion("system_managed <", value, "systemManaged");
            return (Criteria) this;
        }

        public Criteria andSystemManagedLessThanOrEqualTo(Boolean value) {
            addCriterion("system_managed <=", value, "systemManaged");
            return (Criteria) this;
        }

        public Criteria andSystemManagedIn(List<Boolean> values) {
            addCriterion("system_managed in", values, "systemManaged");
            return (Criteria) this;
        }

        public Criteria andSystemManagedNotIn(List<Boolean> values) {
            addCriterion("system_managed not in", values, "systemManaged");
            return (Criteria) this;
        }

        public Criteria andSystemManagedBetween(Boolean value1, Boolean value2) {
            addCriterion("system_managed between", value1, value2, "systemManaged");
            return (Criteria) this;
        }

        public Criteria andSystemManagedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("system_managed not between", value1, value2, "systemManaged");
            return (Criteria) this;
        }

        public Criteria andEditPermissionCodeIsNull() {
            addCriterion("edit_permission_code is null");
            return (Criteria) this;
        }

        public Criteria andEditPermissionCodeIsNotNull() {
            addCriterion("edit_permission_code is not null");
            return (Criteria) this;
        }

        public Criteria andEditPermissionCodeEqualTo(String value) {
            addCriterion("edit_permission_code =", value, "editPermissionCode");
            return (Criteria) this;
        }

        public Criteria andEditPermissionCodeNotEqualTo(String value) {
            addCriterion("edit_permission_code <>", value, "editPermissionCode");
            return (Criteria) this;
        }

        public Criteria andEditPermissionCodeGreaterThan(String value) {
            addCriterion("edit_permission_code >", value, "editPermissionCode");
            return (Criteria) this;
        }

        public Criteria andEditPermissionCodeGreaterThanOrEqualTo(String value) {
            addCriterion("edit_permission_code >=", value, "editPermissionCode");
            return (Criteria) this;
        }

        public Criteria andEditPermissionCodeLessThan(String value) {
            addCriterion("edit_permission_code <", value, "editPermissionCode");
            return (Criteria) this;
        }

        public Criteria andEditPermissionCodeLessThanOrEqualTo(String value) {
            addCriterion("edit_permission_code <=", value, "editPermissionCode");
            return (Criteria) this;
        }

        public Criteria andEditPermissionCodeLike(String value) {
            addCriterion("edit_permission_code ilike", value, "editPermissionCode");
            return (Criteria) this;
        }

        public Criteria andEditPermissionCodeNotLike(String value) {
            addCriterion("edit_permission_code not ilike", value, "editPermissionCode");
            return (Criteria) this;
        }

        public Criteria andEditPermissionCodeIn(List<String> values) {
            addCriterion("edit_permission_code in", values, "editPermissionCode");
            return (Criteria) this;
        }

        public Criteria andEditPermissionCodeNotIn(List<String> values) {
            addCriterion("edit_permission_code not in", values, "editPermissionCode");
            return (Criteria) this;
        }

        public Criteria andEditPermissionCodeBetween(String value1, String value2) {
            addCriterion("edit_permission_code between", value1, value2, "editPermissionCode");
            return (Criteria) this;
        }

        public Criteria andEditPermissionCodeNotBetween(String value1, String value2) {
            addCriterion("edit_permission_code not between", value1, value2, "editPermissionCode");
            return (Criteria) this;
        }

        public Criteria andEngineRoleIsNull() {
            addCriterion("engine_role is null");
            return (Criteria) this;
        }

        public Criteria andEngineRoleIsNotNull() {
            addCriterion("engine_role is not null");
            return (Criteria) this;
        }

        public Criteria andEngineRoleEqualTo(String value) {
            addCriterion("engine_role =", value, "engineRole");
            return (Criteria) this;
        }

        public Criteria andEngineRoleNotEqualTo(String value) {
            addCriterion("engine_role <>", value, "engineRole");
            return (Criteria) this;
        }

        public Criteria andEngineRoleGreaterThan(String value) {
            addCriterion("engine_role >", value, "engineRole");
            return (Criteria) this;
        }

        public Criteria andEngineRoleGreaterThanOrEqualTo(String value) {
            addCriterion("engine_role >=", value, "engineRole");
            return (Criteria) this;
        }

        public Criteria andEngineRoleLessThan(String value) {
            addCriterion("engine_role <", value, "engineRole");
            return (Criteria) this;
        }

        public Criteria andEngineRoleLessThanOrEqualTo(String value) {
            addCriterion("engine_role <=", value, "engineRole");
            return (Criteria) this;
        }

        public Criteria andEngineRoleLike(String value) {
            addCriterion("engine_role ilike", value, "engineRole");
            return (Criteria) this;
        }

        public Criteria andEngineRoleNotLike(String value) {
            addCriterion("engine_role not ilike", value, "engineRole");
            return (Criteria) this;
        }

        public Criteria andEngineRoleIn(List<String> values) {
            addCriterion("engine_role in", values, "engineRole");
            return (Criteria) this;
        }

        public Criteria andEngineRoleNotIn(List<String> values) {
            addCriterion("engine_role not in", values, "engineRole");
            return (Criteria) this;
        }

        public Criteria andEngineRoleBetween(String value1, String value2) {
            addCriterion("engine_role between", value1, value2, "engineRole");
            return (Criteria) this;
        }

        public Criteria andEngineRoleNotBetween(String value1, String value2) {
            addCriterion("engine_role not between", value1, value2, "engineRole");
            return (Criteria) this;
        }

        public Criteria andNameContains(String value) {
            addCriterion("name ilike", LikePatterns.contains(value), "name");
            return (Criteria) this;
        }

        public Criteria andCaseModeContains(String value) {
            addCriterion("case_mode ilike", LikePatterns.contains(value), "caseMode");
            return (Criteria) this;
        }

        public Criteria andEditPermissionCodeContains(String value) {
            addCriterion("edit_permission_code ilike", LikePatterns.contains(value), "editPermissionCode");
            return (Criteria) this;
        }

        public Criteria andEngineRoleContains(String value) {
            addCriterion("engine_role ilike", LikePatterns.contains(value), "engineRole");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_namespace")
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