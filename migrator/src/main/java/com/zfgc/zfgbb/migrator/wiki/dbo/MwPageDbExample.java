package com.zfgc.zfgbb.migrator.wiki.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class MwPageDbExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.725948582-04:00", comments="Source Table: zfgc_wikipage")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.725964281-04:00", comments="Source Table: zfgc_wikipage")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.725985871-04:00", comments="Source Table: zfgc_wikipage")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.725939662-04:00", comments="Source Table: zfgc_wikipage")
    public MwPageDbExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.725954422-04:00", comments="Source Table: zfgc_wikipage")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.725959352-04:00", comments="Source Table: zfgc_wikipage")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.725972741-04:00", comments="Source Table: zfgc_wikipage")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.725976041-04:00", comments="Source Table: zfgc_wikipage")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.725991241-04:00", comments="Source Table: zfgc_wikipage")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.72599633-04:00", comments="Source Table: zfgc_wikipage")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.7260012-04:00", comments="Source Table: zfgc_wikipage")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.72600613-04:00", comments="Source Table: zfgc_wikipage")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.72601079-04:00", comments="Source Table: zfgc_wikipage")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.72601375-04:00", comments="Source Table: zfgc_wikipage")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.72602001-04:00", comments="Source Table: zfgc_wikipage")
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

        public Criteria andPageIdIsNull() {
            addCriterion("page_id is null");
            return (Criteria) this;
        }

        public Criteria andPageIdIsNotNull() {
            addCriterion("page_id is not null");
            return (Criteria) this;
        }

        public Criteria andPageIdEqualTo(Integer value) {
            addCriterion("page_id =", value, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdNotEqualTo(Integer value) {
            addCriterion("page_id <>", value, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdGreaterThan(Integer value) {
            addCriterion("page_id >", value, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("page_id >=", value, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdLessThan(Integer value) {
            addCriterion("page_id <", value, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdLessThanOrEqualTo(Integer value) {
            addCriterion("page_id <=", value, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdIn(List<Integer> values) {
            addCriterion("page_id in", values, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdNotIn(List<Integer> values) {
            addCriterion("page_id not in", values, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdBetween(Integer value1, Integer value2) {
            addCriterion("page_id between", value1, value2, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageIdNotBetween(Integer value1, Integer value2) {
            addCriterion("page_id not between", value1, value2, "pageId");
            return (Criteria) this;
        }

        public Criteria andPageNamespaceIsNull() {
            addCriterion("page_namespace is null");
            return (Criteria) this;
        }

        public Criteria andPageNamespaceIsNotNull() {
            addCriterion("page_namespace is not null");
            return (Criteria) this;
        }

        public Criteria andPageNamespaceEqualTo(Integer value) {
            addCriterion("page_namespace =", value, "pageNamespace");
            return (Criteria) this;
        }

        public Criteria andPageNamespaceNotEqualTo(Integer value) {
            addCriterion("page_namespace <>", value, "pageNamespace");
            return (Criteria) this;
        }

        public Criteria andPageNamespaceGreaterThan(Integer value) {
            addCriterion("page_namespace >", value, "pageNamespace");
            return (Criteria) this;
        }

        public Criteria andPageNamespaceGreaterThanOrEqualTo(Integer value) {
            addCriterion("page_namespace >=", value, "pageNamespace");
            return (Criteria) this;
        }

        public Criteria andPageNamespaceLessThan(Integer value) {
            addCriterion("page_namespace <", value, "pageNamespace");
            return (Criteria) this;
        }

        public Criteria andPageNamespaceLessThanOrEqualTo(Integer value) {
            addCriterion("page_namespace <=", value, "pageNamespace");
            return (Criteria) this;
        }

        public Criteria andPageNamespaceIn(List<Integer> values) {
            addCriterion("page_namespace in", values, "pageNamespace");
            return (Criteria) this;
        }

        public Criteria andPageNamespaceNotIn(List<Integer> values) {
            addCriterion("page_namespace not in", values, "pageNamespace");
            return (Criteria) this;
        }

        public Criteria andPageNamespaceBetween(Integer value1, Integer value2) {
            addCriterion("page_namespace between", value1, value2, "pageNamespace");
            return (Criteria) this;
        }

        public Criteria andPageNamespaceNotBetween(Integer value1, Integer value2) {
            addCriterion("page_namespace not between", value1, value2, "pageNamespace");
            return (Criteria) this;
        }

        public Criteria andPageTitleIsNull() {
            addCriterion("page_title is null");
            return (Criteria) this;
        }

        public Criteria andPageTitleIsNotNull() {
            addCriterion("page_title is not null");
            return (Criteria) this;
        }

        public Criteria andPageTitleEqualTo(String value) {
            addCriterion("page_title =", value, "pageTitle");
            return (Criteria) this;
        }

        public Criteria andPageTitleNotEqualTo(String value) {
            addCriterion("page_title <>", value, "pageTitle");
            return (Criteria) this;
        }

        public Criteria andPageTitleGreaterThan(String value) {
            addCriterion("page_title >", value, "pageTitle");
            return (Criteria) this;
        }

        public Criteria andPageTitleGreaterThanOrEqualTo(String value) {
            addCriterion("page_title >=", value, "pageTitle");
            return (Criteria) this;
        }

        public Criteria andPageTitleLessThan(String value) {
            addCriterion("page_title <", value, "pageTitle");
            return (Criteria) this;
        }

        public Criteria andPageTitleLessThanOrEqualTo(String value) {
            addCriterion("page_title <=", value, "pageTitle");
            return (Criteria) this;
        }

        public Criteria andPageTitleLike(String value) {
            addCriterion("page_title like", value, "pageTitle");
            return (Criteria) this;
        }

        public Criteria andPageTitleNotLike(String value) {
            addCriterion("page_title not like", value, "pageTitle");
            return (Criteria) this;
        }

        public Criteria andPageTitleIn(List<String> values) {
            addCriterion("page_title in", values, "pageTitle");
            return (Criteria) this;
        }

        public Criteria andPageTitleNotIn(List<String> values) {
            addCriterion("page_title not in", values, "pageTitle");
            return (Criteria) this;
        }

        public Criteria andPageTitleBetween(String value1, String value2) {
            addCriterion("page_title between", value1, value2, "pageTitle");
            return (Criteria) this;
        }

        public Criteria andPageTitleNotBetween(String value1, String value2) {
            addCriterion("page_title not between", value1, value2, "pageTitle");
            return (Criteria) this;
        }

        public Criteria andPageIsRedirectIsNull() {
            addCriterion("page_is_redirect is null");
            return (Criteria) this;
        }

        public Criteria andPageIsRedirectIsNotNull() {
            addCriterion("page_is_redirect is not null");
            return (Criteria) this;
        }

        public Criteria andPageIsRedirectEqualTo(Integer value) {
            addCriterion("page_is_redirect =", value, "pageIsRedirect");
            return (Criteria) this;
        }

        public Criteria andPageIsRedirectNotEqualTo(Integer value) {
            addCriterion("page_is_redirect <>", value, "pageIsRedirect");
            return (Criteria) this;
        }

        public Criteria andPageIsRedirectGreaterThan(Integer value) {
            addCriterion("page_is_redirect >", value, "pageIsRedirect");
            return (Criteria) this;
        }

        public Criteria andPageIsRedirectGreaterThanOrEqualTo(Integer value) {
            addCriterion("page_is_redirect >=", value, "pageIsRedirect");
            return (Criteria) this;
        }

        public Criteria andPageIsRedirectLessThan(Integer value) {
            addCriterion("page_is_redirect <", value, "pageIsRedirect");
            return (Criteria) this;
        }

        public Criteria andPageIsRedirectLessThanOrEqualTo(Integer value) {
            addCriterion("page_is_redirect <=", value, "pageIsRedirect");
            return (Criteria) this;
        }

        public Criteria andPageIsRedirectIn(List<Integer> values) {
            addCriterion("page_is_redirect in", values, "pageIsRedirect");
            return (Criteria) this;
        }

        public Criteria andPageIsRedirectNotIn(List<Integer> values) {
            addCriterion("page_is_redirect not in", values, "pageIsRedirect");
            return (Criteria) this;
        }

        public Criteria andPageIsRedirectBetween(Integer value1, Integer value2) {
            addCriterion("page_is_redirect between", value1, value2, "pageIsRedirect");
            return (Criteria) this;
        }

        public Criteria andPageIsRedirectNotBetween(Integer value1, Integer value2) {
            addCriterion("page_is_redirect not between", value1, value2, "pageIsRedirect");
            return (Criteria) this;
        }

        public Criteria andPageIsNewIsNull() {
            addCriterion("page_is_new is null");
            return (Criteria) this;
        }

        public Criteria andPageIsNewIsNotNull() {
            addCriterion("page_is_new is not null");
            return (Criteria) this;
        }

        public Criteria andPageIsNewEqualTo(Integer value) {
            addCriterion("page_is_new =", value, "pageIsNew");
            return (Criteria) this;
        }

        public Criteria andPageIsNewNotEqualTo(Integer value) {
            addCriterion("page_is_new <>", value, "pageIsNew");
            return (Criteria) this;
        }

        public Criteria andPageIsNewGreaterThan(Integer value) {
            addCriterion("page_is_new >", value, "pageIsNew");
            return (Criteria) this;
        }

        public Criteria andPageIsNewGreaterThanOrEqualTo(Integer value) {
            addCriterion("page_is_new >=", value, "pageIsNew");
            return (Criteria) this;
        }

        public Criteria andPageIsNewLessThan(Integer value) {
            addCriterion("page_is_new <", value, "pageIsNew");
            return (Criteria) this;
        }

        public Criteria andPageIsNewLessThanOrEqualTo(Integer value) {
            addCriterion("page_is_new <=", value, "pageIsNew");
            return (Criteria) this;
        }

        public Criteria andPageIsNewIn(List<Integer> values) {
            addCriterion("page_is_new in", values, "pageIsNew");
            return (Criteria) this;
        }

        public Criteria andPageIsNewNotIn(List<Integer> values) {
            addCriterion("page_is_new not in", values, "pageIsNew");
            return (Criteria) this;
        }

        public Criteria andPageIsNewBetween(Integer value1, Integer value2) {
            addCriterion("page_is_new between", value1, value2, "pageIsNew");
            return (Criteria) this;
        }

        public Criteria andPageIsNewNotBetween(Integer value1, Integer value2) {
            addCriterion("page_is_new not between", value1, value2, "pageIsNew");
            return (Criteria) this;
        }

        public Criteria andPageRandomIsNull() {
            addCriterion("page_random is null");
            return (Criteria) this;
        }

        public Criteria andPageRandomIsNotNull() {
            addCriterion("page_random is not null");
            return (Criteria) this;
        }

        public Criteria andPageRandomEqualTo(Double value) {
            addCriterion("page_random =", value, "pageRandom");
            return (Criteria) this;
        }

        public Criteria andPageRandomNotEqualTo(Double value) {
            addCriterion("page_random <>", value, "pageRandom");
            return (Criteria) this;
        }

        public Criteria andPageRandomGreaterThan(Double value) {
            addCriterion("page_random >", value, "pageRandom");
            return (Criteria) this;
        }

        public Criteria andPageRandomGreaterThanOrEqualTo(Double value) {
            addCriterion("page_random >=", value, "pageRandom");
            return (Criteria) this;
        }

        public Criteria andPageRandomLessThan(Double value) {
            addCriterion("page_random <", value, "pageRandom");
            return (Criteria) this;
        }

        public Criteria andPageRandomLessThanOrEqualTo(Double value) {
            addCriterion("page_random <=", value, "pageRandom");
            return (Criteria) this;
        }

        public Criteria andPageRandomIn(List<Double> values) {
            addCriterion("page_random in", values, "pageRandom");
            return (Criteria) this;
        }

        public Criteria andPageRandomNotIn(List<Double> values) {
            addCriterion("page_random not in", values, "pageRandom");
            return (Criteria) this;
        }

        public Criteria andPageRandomBetween(Double value1, Double value2) {
            addCriterion("page_random between", value1, value2, "pageRandom");
            return (Criteria) this;
        }

        public Criteria andPageRandomNotBetween(Double value1, Double value2) {
            addCriterion("page_random not between", value1, value2, "pageRandom");
            return (Criteria) this;
        }

        public Criteria andPageTouchedIsNull() {
            addCriterion("page_touched is null");
            return (Criteria) this;
        }

        public Criteria andPageTouchedIsNotNull() {
            addCriterion("page_touched is not null");
            return (Criteria) this;
        }

        public Criteria andPageTouchedEqualTo(String value) {
            addCriterion("page_touched =", value, "pageTouched");
            return (Criteria) this;
        }

        public Criteria andPageTouchedNotEqualTo(String value) {
            addCriterion("page_touched <>", value, "pageTouched");
            return (Criteria) this;
        }

        public Criteria andPageTouchedGreaterThan(String value) {
            addCriterion("page_touched >", value, "pageTouched");
            return (Criteria) this;
        }

        public Criteria andPageTouchedGreaterThanOrEqualTo(String value) {
            addCriterion("page_touched >=", value, "pageTouched");
            return (Criteria) this;
        }

        public Criteria andPageTouchedLessThan(String value) {
            addCriterion("page_touched <", value, "pageTouched");
            return (Criteria) this;
        }

        public Criteria andPageTouchedLessThanOrEqualTo(String value) {
            addCriterion("page_touched <=", value, "pageTouched");
            return (Criteria) this;
        }

        public Criteria andPageTouchedIn(List<String> values) {
            addCriterion("page_touched in", values, "pageTouched");
            return (Criteria) this;
        }

        public Criteria andPageTouchedNotIn(List<String> values) {
            addCriterion("page_touched not in", values, "pageTouched");
            return (Criteria) this;
        }

        public Criteria andPageTouchedBetween(String value1, String value2) {
            addCriterion("page_touched between", value1, value2, "pageTouched");
            return (Criteria) this;
        }

        public Criteria andPageTouchedNotBetween(String value1, String value2) {
            addCriterion("page_touched not between", value1, value2, "pageTouched");
            return (Criteria) this;
        }

        public Criteria andPageLatestIsNull() {
            addCriterion("page_latest is null");
            return (Criteria) this;
        }

        public Criteria andPageLatestIsNotNull() {
            addCriterion("page_latest is not null");
            return (Criteria) this;
        }

        public Criteria andPageLatestEqualTo(Integer value) {
            addCriterion("page_latest =", value, "pageLatest");
            return (Criteria) this;
        }

        public Criteria andPageLatestNotEqualTo(Integer value) {
            addCriterion("page_latest <>", value, "pageLatest");
            return (Criteria) this;
        }

        public Criteria andPageLatestGreaterThan(Integer value) {
            addCriterion("page_latest >", value, "pageLatest");
            return (Criteria) this;
        }

        public Criteria andPageLatestGreaterThanOrEqualTo(Integer value) {
            addCriterion("page_latest >=", value, "pageLatest");
            return (Criteria) this;
        }

        public Criteria andPageLatestLessThan(Integer value) {
            addCriterion("page_latest <", value, "pageLatest");
            return (Criteria) this;
        }

        public Criteria andPageLatestLessThanOrEqualTo(Integer value) {
            addCriterion("page_latest <=", value, "pageLatest");
            return (Criteria) this;
        }

        public Criteria andPageLatestIn(List<Integer> values) {
            addCriterion("page_latest in", values, "pageLatest");
            return (Criteria) this;
        }

        public Criteria andPageLatestNotIn(List<Integer> values) {
            addCriterion("page_latest not in", values, "pageLatest");
            return (Criteria) this;
        }

        public Criteria andPageLatestBetween(Integer value1, Integer value2) {
            addCriterion("page_latest between", value1, value2, "pageLatest");
            return (Criteria) this;
        }

        public Criteria andPageLatestNotBetween(Integer value1, Integer value2) {
            addCriterion("page_latest not between", value1, value2, "pageLatest");
            return (Criteria) this;
        }

        public Criteria andPageLenIsNull() {
            addCriterion("page_len is null");
            return (Criteria) this;
        }

        public Criteria andPageLenIsNotNull() {
            addCriterion("page_len is not null");
            return (Criteria) this;
        }

        public Criteria andPageLenEqualTo(Integer value) {
            addCriterion("page_len =", value, "pageLen");
            return (Criteria) this;
        }

        public Criteria andPageLenNotEqualTo(Integer value) {
            addCriterion("page_len <>", value, "pageLen");
            return (Criteria) this;
        }

        public Criteria andPageLenGreaterThan(Integer value) {
            addCriterion("page_len >", value, "pageLen");
            return (Criteria) this;
        }

        public Criteria andPageLenGreaterThanOrEqualTo(Integer value) {
            addCriterion("page_len >=", value, "pageLen");
            return (Criteria) this;
        }

        public Criteria andPageLenLessThan(Integer value) {
            addCriterion("page_len <", value, "pageLen");
            return (Criteria) this;
        }

        public Criteria andPageLenLessThanOrEqualTo(Integer value) {
            addCriterion("page_len <=", value, "pageLen");
            return (Criteria) this;
        }

        public Criteria andPageLenIn(List<Integer> values) {
            addCriterion("page_len in", values, "pageLen");
            return (Criteria) this;
        }

        public Criteria andPageLenNotIn(List<Integer> values) {
            addCriterion("page_len not in", values, "pageLen");
            return (Criteria) this;
        }

        public Criteria andPageLenBetween(Integer value1, Integer value2) {
            addCriterion("page_len between", value1, value2, "pageLen");
            return (Criteria) this;
        }

        public Criteria andPageLenNotBetween(Integer value1, Integer value2) {
            addCriterion("page_len not between", value1, value2, "pageLen");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726268652-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.726279021-04:00", comments="Source Table: zfgc_wikipage")
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