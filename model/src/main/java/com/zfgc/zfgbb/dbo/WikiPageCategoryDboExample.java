package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class WikiPageCategoryDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    protected Integer limit;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    public WikiPageCategoryDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    public Integer getLimit() {
        return limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    public Integer getOffset() {
        return offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
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

        public Criteria andWikiPageCategoryIdIsNull() {
            addCriterion("wiki_page_category_id is null");
            return (Criteria) this;
        }

        public Criteria andWikiPageCategoryIdIsNotNull() {
            addCriterion("wiki_page_category_id is not null");
            return (Criteria) this;
        }

        public Criteria andWikiPageCategoryIdEqualTo(Integer value) {
            addCriterion("wiki_page_category_id =", value, "wikiPageCategoryId");
            return (Criteria) this;
        }

        public Criteria andWikiPageCategoryIdNotEqualTo(Integer value) {
            addCriterion("wiki_page_category_id <>", value, "wikiPageCategoryId");
            return (Criteria) this;
        }

        public Criteria andWikiPageCategoryIdGreaterThan(Integer value) {
            addCriterion("wiki_page_category_id >", value, "wikiPageCategoryId");
            return (Criteria) this;
        }

        public Criteria andWikiPageCategoryIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("wiki_page_category_id >=", value, "wikiPageCategoryId");
            return (Criteria) this;
        }

        public Criteria andWikiPageCategoryIdLessThan(Integer value) {
            addCriterion("wiki_page_category_id <", value, "wikiPageCategoryId");
            return (Criteria) this;
        }

        public Criteria andWikiPageCategoryIdLessThanOrEqualTo(Integer value) {
            addCriterion("wiki_page_category_id <=", value, "wikiPageCategoryId");
            return (Criteria) this;
        }

        public Criteria andWikiPageCategoryIdIn(List<Integer> values) {
            addCriterion("wiki_page_category_id in", values, "wikiPageCategoryId");
            return (Criteria) this;
        }

        public Criteria andWikiPageCategoryIdNotIn(List<Integer> values) {
            addCriterion("wiki_page_category_id not in", values, "wikiPageCategoryId");
            return (Criteria) this;
        }

        public Criteria andWikiPageCategoryIdBetween(Integer value1, Integer value2) {
            addCriterion("wiki_page_category_id between", value1, value2, "wikiPageCategoryId");
            return (Criteria) this;
        }

        public Criteria andWikiPageCategoryIdNotBetween(Integer value1, Integer value2) {
            addCriterion("wiki_page_category_id not between", value1, value2, "wikiPageCategoryId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdIsNull() {
            addCriterion("wiki_page_id is null");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdIsNotNull() {
            addCriterion("wiki_page_id is not null");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdEqualTo(Integer value) {
            addCriterion("wiki_page_id =", value, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdNotEqualTo(Integer value) {
            addCriterion("wiki_page_id <>", value, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdGreaterThan(Integer value) {
            addCriterion("wiki_page_id >", value, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("wiki_page_id >=", value, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdLessThan(Integer value) {
            addCriterion("wiki_page_id <", value, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdLessThanOrEqualTo(Integer value) {
            addCriterion("wiki_page_id <=", value, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdIn(List<Integer> values) {
            addCriterion("wiki_page_id in", values, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdNotIn(List<Integer> values) {
            addCriterion("wiki_page_id not in", values, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdBetween(Integer value1, Integer value2) {
            addCriterion("wiki_page_id between", value1, value2, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andWikiPageIdNotBetween(Integer value1, Integer value2) {
            addCriterion("wiki_page_id not between", value1, value2, "wikiPageId");
            return (Criteria) this;
        }

        public Criteria andCategoryNameIsNull() {
            addCriterion("category_name is null");
            return (Criteria) this;
        }

        public Criteria andCategoryNameIsNotNull() {
            addCriterion("category_name is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryNameEqualTo(String value) {
            addCriterion("category_name =", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameNotEqualTo(String value) {
            addCriterion("category_name <>", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameGreaterThan(String value) {
            addCriterion("category_name >", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameGreaterThanOrEqualTo(String value) {
            addCriterion("category_name >=", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameLessThan(String value) {
            addCriterion("category_name <", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameLessThanOrEqualTo(String value) {
            addCriterion("category_name <=", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameLike(String value) {
            addCriterion("category_name ilike", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameNotLike(String value) {
            addCriterion("category_name not ilike", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameIn(List<String> values) {
            addCriterion("category_name in", values, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameNotIn(List<String> values) {
            addCriterion("category_name not in", values, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameBetween(String value1, String value2) {
            addCriterion("category_name between", value1, value2, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameNotBetween(String value1, String value2) {
            addCriterion("category_name not between", value1, value2, "categoryName");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.wiki_page_category")
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