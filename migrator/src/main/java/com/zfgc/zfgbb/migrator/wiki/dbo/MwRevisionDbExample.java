package com.zfgc.zfgbb.migrator.wiki.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class MwRevisionDbExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.727504833-04:00", comments="Source Table: zfgc_wikirevision")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.727520412-04:00", comments="Source Table: zfgc_wikirevision")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.727539202-04:00", comments="Source Table: zfgc_wikirevision")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.727496163-04:00", comments="Source Table: zfgc_wikirevision")
    public MwRevisionDbExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.727510762-04:00", comments="Source Table: zfgc_wikirevision")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.727515542-04:00", comments="Source Table: zfgc_wikirevision")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.727525102-04:00", comments="Source Table: zfgc_wikirevision")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.727529552-04:00", comments="Source Table: zfgc_wikirevision")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.727542681-04:00", comments="Source Table: zfgc_wikirevision")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.727547411-04:00", comments="Source Table: zfgc_wikirevision")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.727552091-04:00", comments="Source Table: zfgc_wikirevision")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.727556551-04:00", comments="Source Table: zfgc_wikirevision")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.727560931-04:00", comments="Source Table: zfgc_wikirevision")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.727565341-04:00", comments="Source Table: zfgc_wikirevision")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.727571381-04:00", comments="Source Table: zfgc_wikirevision")
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

        public Criteria andRevIdIsNull() {
            addCriterion("rev_id is null");
            return (Criteria) this;
        }

        public Criteria andRevIdIsNotNull() {
            addCriterion("rev_id is not null");
            return (Criteria) this;
        }

        public Criteria andRevIdEqualTo(Integer value) {
            addCriterion("rev_id =", value, "revId");
            return (Criteria) this;
        }

        public Criteria andRevIdNotEqualTo(Integer value) {
            addCriterion("rev_id <>", value, "revId");
            return (Criteria) this;
        }

        public Criteria andRevIdGreaterThan(Integer value) {
            addCriterion("rev_id >", value, "revId");
            return (Criteria) this;
        }

        public Criteria andRevIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("rev_id >=", value, "revId");
            return (Criteria) this;
        }

        public Criteria andRevIdLessThan(Integer value) {
            addCriterion("rev_id <", value, "revId");
            return (Criteria) this;
        }

        public Criteria andRevIdLessThanOrEqualTo(Integer value) {
            addCriterion("rev_id <=", value, "revId");
            return (Criteria) this;
        }

        public Criteria andRevIdIn(List<Integer> values) {
            addCriterion("rev_id in", values, "revId");
            return (Criteria) this;
        }

        public Criteria andRevIdNotIn(List<Integer> values) {
            addCriterion("rev_id not in", values, "revId");
            return (Criteria) this;
        }

        public Criteria andRevIdBetween(Integer value1, Integer value2) {
            addCriterion("rev_id between", value1, value2, "revId");
            return (Criteria) this;
        }

        public Criteria andRevIdNotBetween(Integer value1, Integer value2) {
            addCriterion("rev_id not between", value1, value2, "revId");
            return (Criteria) this;
        }

        public Criteria andRevPageIsNull() {
            addCriterion("rev_page is null");
            return (Criteria) this;
        }

        public Criteria andRevPageIsNotNull() {
            addCriterion("rev_page is not null");
            return (Criteria) this;
        }

        public Criteria andRevPageEqualTo(Integer value) {
            addCriterion("rev_page =", value, "revPage");
            return (Criteria) this;
        }

        public Criteria andRevPageNotEqualTo(Integer value) {
            addCriterion("rev_page <>", value, "revPage");
            return (Criteria) this;
        }

        public Criteria andRevPageGreaterThan(Integer value) {
            addCriterion("rev_page >", value, "revPage");
            return (Criteria) this;
        }

        public Criteria andRevPageGreaterThanOrEqualTo(Integer value) {
            addCriterion("rev_page >=", value, "revPage");
            return (Criteria) this;
        }

        public Criteria andRevPageLessThan(Integer value) {
            addCriterion("rev_page <", value, "revPage");
            return (Criteria) this;
        }

        public Criteria andRevPageLessThanOrEqualTo(Integer value) {
            addCriterion("rev_page <=", value, "revPage");
            return (Criteria) this;
        }

        public Criteria andRevPageIn(List<Integer> values) {
            addCriterion("rev_page in", values, "revPage");
            return (Criteria) this;
        }

        public Criteria andRevPageNotIn(List<Integer> values) {
            addCriterion("rev_page not in", values, "revPage");
            return (Criteria) this;
        }

        public Criteria andRevPageBetween(Integer value1, Integer value2) {
            addCriterion("rev_page between", value1, value2, "revPage");
            return (Criteria) this;
        }

        public Criteria andRevPageNotBetween(Integer value1, Integer value2) {
            addCriterion("rev_page not between", value1, value2, "revPage");
            return (Criteria) this;
        }

        public Criteria andRevTextIdIsNull() {
            addCriterion("rev_text_id is null");
            return (Criteria) this;
        }

        public Criteria andRevTextIdIsNotNull() {
            addCriterion("rev_text_id is not null");
            return (Criteria) this;
        }

        public Criteria andRevTextIdEqualTo(Integer value) {
            addCriterion("rev_text_id =", value, "revTextId");
            return (Criteria) this;
        }

        public Criteria andRevTextIdNotEqualTo(Integer value) {
            addCriterion("rev_text_id <>", value, "revTextId");
            return (Criteria) this;
        }

        public Criteria andRevTextIdGreaterThan(Integer value) {
            addCriterion("rev_text_id >", value, "revTextId");
            return (Criteria) this;
        }

        public Criteria andRevTextIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("rev_text_id >=", value, "revTextId");
            return (Criteria) this;
        }

        public Criteria andRevTextIdLessThan(Integer value) {
            addCriterion("rev_text_id <", value, "revTextId");
            return (Criteria) this;
        }

        public Criteria andRevTextIdLessThanOrEqualTo(Integer value) {
            addCriterion("rev_text_id <=", value, "revTextId");
            return (Criteria) this;
        }

        public Criteria andRevTextIdIn(List<Integer> values) {
            addCriterion("rev_text_id in", values, "revTextId");
            return (Criteria) this;
        }

        public Criteria andRevTextIdNotIn(List<Integer> values) {
            addCriterion("rev_text_id not in", values, "revTextId");
            return (Criteria) this;
        }

        public Criteria andRevTextIdBetween(Integer value1, Integer value2) {
            addCriterion("rev_text_id between", value1, value2, "revTextId");
            return (Criteria) this;
        }

        public Criteria andRevTextIdNotBetween(Integer value1, Integer value2) {
            addCriterion("rev_text_id not between", value1, value2, "revTextId");
            return (Criteria) this;
        }

        public Criteria andRevUserIsNull() {
            addCriterion("rev_user is null");
            return (Criteria) this;
        }

        public Criteria andRevUserIsNotNull() {
            addCriterion("rev_user is not null");
            return (Criteria) this;
        }

        public Criteria andRevUserEqualTo(Integer value) {
            addCriterion("rev_user =", value, "revUser");
            return (Criteria) this;
        }

        public Criteria andRevUserNotEqualTo(Integer value) {
            addCriterion("rev_user <>", value, "revUser");
            return (Criteria) this;
        }

        public Criteria andRevUserGreaterThan(Integer value) {
            addCriterion("rev_user >", value, "revUser");
            return (Criteria) this;
        }

        public Criteria andRevUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("rev_user >=", value, "revUser");
            return (Criteria) this;
        }

        public Criteria andRevUserLessThan(Integer value) {
            addCriterion("rev_user <", value, "revUser");
            return (Criteria) this;
        }

        public Criteria andRevUserLessThanOrEqualTo(Integer value) {
            addCriterion("rev_user <=", value, "revUser");
            return (Criteria) this;
        }

        public Criteria andRevUserIn(List<Integer> values) {
            addCriterion("rev_user in", values, "revUser");
            return (Criteria) this;
        }

        public Criteria andRevUserNotIn(List<Integer> values) {
            addCriterion("rev_user not in", values, "revUser");
            return (Criteria) this;
        }

        public Criteria andRevUserBetween(Integer value1, Integer value2) {
            addCriterion("rev_user between", value1, value2, "revUser");
            return (Criteria) this;
        }

        public Criteria andRevUserNotBetween(Integer value1, Integer value2) {
            addCriterion("rev_user not between", value1, value2, "revUser");
            return (Criteria) this;
        }

        public Criteria andRevUserTextIsNull() {
            addCriterion("rev_user_text is null");
            return (Criteria) this;
        }

        public Criteria andRevUserTextIsNotNull() {
            addCriterion("rev_user_text is not null");
            return (Criteria) this;
        }

        public Criteria andRevUserTextEqualTo(String value) {
            addCriterion("rev_user_text =", value, "revUserText");
            return (Criteria) this;
        }

        public Criteria andRevUserTextNotEqualTo(String value) {
            addCriterion("rev_user_text <>", value, "revUserText");
            return (Criteria) this;
        }

        public Criteria andRevUserTextGreaterThan(String value) {
            addCriterion("rev_user_text >", value, "revUserText");
            return (Criteria) this;
        }

        public Criteria andRevUserTextGreaterThanOrEqualTo(String value) {
            addCriterion("rev_user_text >=", value, "revUserText");
            return (Criteria) this;
        }

        public Criteria andRevUserTextLessThan(String value) {
            addCriterion("rev_user_text <", value, "revUserText");
            return (Criteria) this;
        }

        public Criteria andRevUserTextLessThanOrEqualTo(String value) {
            addCriterion("rev_user_text <=", value, "revUserText");
            return (Criteria) this;
        }

        public Criteria andRevUserTextLike(String value) {
            addCriterion("rev_user_text like", value, "revUserText");
            return (Criteria) this;
        }

        public Criteria andRevUserTextNotLike(String value) {
            addCriterion("rev_user_text not like", value, "revUserText");
            return (Criteria) this;
        }

        public Criteria andRevUserTextIn(List<String> values) {
            addCriterion("rev_user_text in", values, "revUserText");
            return (Criteria) this;
        }

        public Criteria andRevUserTextNotIn(List<String> values) {
            addCriterion("rev_user_text not in", values, "revUserText");
            return (Criteria) this;
        }

        public Criteria andRevUserTextBetween(String value1, String value2) {
            addCriterion("rev_user_text between", value1, value2, "revUserText");
            return (Criteria) this;
        }

        public Criteria andRevUserTextNotBetween(String value1, String value2) {
            addCriterion("rev_user_text not between", value1, value2, "revUserText");
            return (Criteria) this;
        }

        public Criteria andRevTimestampIsNull() {
            addCriterion("rev_timestamp is null");
            return (Criteria) this;
        }

        public Criteria andRevTimestampIsNotNull() {
            addCriterion("rev_timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andRevTimestampEqualTo(String value) {
            addCriterion("rev_timestamp =", value, "revTimestamp");
            return (Criteria) this;
        }

        public Criteria andRevTimestampNotEqualTo(String value) {
            addCriterion("rev_timestamp <>", value, "revTimestamp");
            return (Criteria) this;
        }

        public Criteria andRevTimestampGreaterThan(String value) {
            addCriterion("rev_timestamp >", value, "revTimestamp");
            return (Criteria) this;
        }

        public Criteria andRevTimestampGreaterThanOrEqualTo(String value) {
            addCriterion("rev_timestamp >=", value, "revTimestamp");
            return (Criteria) this;
        }

        public Criteria andRevTimestampLessThan(String value) {
            addCriterion("rev_timestamp <", value, "revTimestamp");
            return (Criteria) this;
        }

        public Criteria andRevTimestampLessThanOrEqualTo(String value) {
            addCriterion("rev_timestamp <=", value, "revTimestamp");
            return (Criteria) this;
        }

        public Criteria andRevTimestampIn(List<String> values) {
            addCriterion("rev_timestamp in", values, "revTimestamp");
            return (Criteria) this;
        }

        public Criteria andRevTimestampNotIn(List<String> values) {
            addCriterion("rev_timestamp not in", values, "revTimestamp");
            return (Criteria) this;
        }

        public Criteria andRevTimestampBetween(String value1, String value2) {
            addCriterion("rev_timestamp between", value1, value2, "revTimestamp");
            return (Criteria) this;
        }

        public Criteria andRevTimestampNotBetween(String value1, String value2) {
            addCriterion("rev_timestamp not between", value1, value2, "revTimestamp");
            return (Criteria) this;
        }

        public Criteria andRevMinorEditIsNull() {
            addCriterion("rev_minor_edit is null");
            return (Criteria) this;
        }

        public Criteria andRevMinorEditIsNotNull() {
            addCriterion("rev_minor_edit is not null");
            return (Criteria) this;
        }

        public Criteria andRevMinorEditEqualTo(Integer value) {
            addCriterion("rev_minor_edit =", value, "revMinorEdit");
            return (Criteria) this;
        }

        public Criteria andRevMinorEditNotEqualTo(Integer value) {
            addCriterion("rev_minor_edit <>", value, "revMinorEdit");
            return (Criteria) this;
        }

        public Criteria andRevMinorEditGreaterThan(Integer value) {
            addCriterion("rev_minor_edit >", value, "revMinorEdit");
            return (Criteria) this;
        }

        public Criteria andRevMinorEditGreaterThanOrEqualTo(Integer value) {
            addCriterion("rev_minor_edit >=", value, "revMinorEdit");
            return (Criteria) this;
        }

        public Criteria andRevMinorEditLessThan(Integer value) {
            addCriterion("rev_minor_edit <", value, "revMinorEdit");
            return (Criteria) this;
        }

        public Criteria andRevMinorEditLessThanOrEqualTo(Integer value) {
            addCriterion("rev_minor_edit <=", value, "revMinorEdit");
            return (Criteria) this;
        }

        public Criteria andRevMinorEditIn(List<Integer> values) {
            addCriterion("rev_minor_edit in", values, "revMinorEdit");
            return (Criteria) this;
        }

        public Criteria andRevMinorEditNotIn(List<Integer> values) {
            addCriterion("rev_minor_edit not in", values, "revMinorEdit");
            return (Criteria) this;
        }

        public Criteria andRevMinorEditBetween(Integer value1, Integer value2) {
            addCriterion("rev_minor_edit between", value1, value2, "revMinorEdit");
            return (Criteria) this;
        }

        public Criteria andRevMinorEditNotBetween(Integer value1, Integer value2) {
            addCriterion("rev_minor_edit not between", value1, value2, "revMinorEdit");
            return (Criteria) this;
        }

        public Criteria andRevDeletedIsNull() {
            addCriterion("rev_deleted is null");
            return (Criteria) this;
        }

        public Criteria andRevDeletedIsNotNull() {
            addCriterion("rev_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andRevDeletedEqualTo(Integer value) {
            addCriterion("rev_deleted =", value, "revDeleted");
            return (Criteria) this;
        }

        public Criteria andRevDeletedNotEqualTo(Integer value) {
            addCriterion("rev_deleted <>", value, "revDeleted");
            return (Criteria) this;
        }

        public Criteria andRevDeletedGreaterThan(Integer value) {
            addCriterion("rev_deleted >", value, "revDeleted");
            return (Criteria) this;
        }

        public Criteria andRevDeletedGreaterThanOrEqualTo(Integer value) {
            addCriterion("rev_deleted >=", value, "revDeleted");
            return (Criteria) this;
        }

        public Criteria andRevDeletedLessThan(Integer value) {
            addCriterion("rev_deleted <", value, "revDeleted");
            return (Criteria) this;
        }

        public Criteria andRevDeletedLessThanOrEqualTo(Integer value) {
            addCriterion("rev_deleted <=", value, "revDeleted");
            return (Criteria) this;
        }

        public Criteria andRevDeletedIn(List<Integer> values) {
            addCriterion("rev_deleted in", values, "revDeleted");
            return (Criteria) this;
        }

        public Criteria andRevDeletedNotIn(List<Integer> values) {
            addCriterion("rev_deleted not in", values, "revDeleted");
            return (Criteria) this;
        }

        public Criteria andRevDeletedBetween(Integer value1, Integer value2) {
            addCriterion("rev_deleted between", value1, value2, "revDeleted");
            return (Criteria) this;
        }

        public Criteria andRevDeletedNotBetween(Integer value1, Integer value2) {
            addCriterion("rev_deleted not between", value1, value2, "revDeleted");
            return (Criteria) this;
        }

        public Criteria andRevLenIsNull() {
            addCriterion("rev_len is null");
            return (Criteria) this;
        }

        public Criteria andRevLenIsNotNull() {
            addCriterion("rev_len is not null");
            return (Criteria) this;
        }

        public Criteria andRevLenEqualTo(Integer value) {
            addCriterion("rev_len =", value, "revLen");
            return (Criteria) this;
        }

        public Criteria andRevLenNotEqualTo(Integer value) {
            addCriterion("rev_len <>", value, "revLen");
            return (Criteria) this;
        }

        public Criteria andRevLenGreaterThan(Integer value) {
            addCriterion("rev_len >", value, "revLen");
            return (Criteria) this;
        }

        public Criteria andRevLenGreaterThanOrEqualTo(Integer value) {
            addCriterion("rev_len >=", value, "revLen");
            return (Criteria) this;
        }

        public Criteria andRevLenLessThan(Integer value) {
            addCriterion("rev_len <", value, "revLen");
            return (Criteria) this;
        }

        public Criteria andRevLenLessThanOrEqualTo(Integer value) {
            addCriterion("rev_len <=", value, "revLen");
            return (Criteria) this;
        }

        public Criteria andRevLenIn(List<Integer> values) {
            addCriterion("rev_len in", values, "revLen");
            return (Criteria) this;
        }

        public Criteria andRevLenNotIn(List<Integer> values) {
            addCriterion("rev_len not in", values, "revLen");
            return (Criteria) this;
        }

        public Criteria andRevLenBetween(Integer value1, Integer value2) {
            addCriterion("rev_len between", value1, value2, "revLen");
            return (Criteria) this;
        }

        public Criteria andRevLenNotBetween(Integer value1, Integer value2) {
            addCriterion("rev_len not between", value1, value2, "revLen");
            return (Criteria) this;
        }

        public Criteria andRevParentIdIsNull() {
            addCriterion("rev_parent_id is null");
            return (Criteria) this;
        }

        public Criteria andRevParentIdIsNotNull() {
            addCriterion("rev_parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andRevParentIdEqualTo(Integer value) {
            addCriterion("rev_parent_id =", value, "revParentId");
            return (Criteria) this;
        }

        public Criteria andRevParentIdNotEqualTo(Integer value) {
            addCriterion("rev_parent_id <>", value, "revParentId");
            return (Criteria) this;
        }

        public Criteria andRevParentIdGreaterThan(Integer value) {
            addCriterion("rev_parent_id >", value, "revParentId");
            return (Criteria) this;
        }

        public Criteria andRevParentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("rev_parent_id >=", value, "revParentId");
            return (Criteria) this;
        }

        public Criteria andRevParentIdLessThan(Integer value) {
            addCriterion("rev_parent_id <", value, "revParentId");
            return (Criteria) this;
        }

        public Criteria andRevParentIdLessThanOrEqualTo(Integer value) {
            addCriterion("rev_parent_id <=", value, "revParentId");
            return (Criteria) this;
        }

        public Criteria andRevParentIdIn(List<Integer> values) {
            addCriterion("rev_parent_id in", values, "revParentId");
            return (Criteria) this;
        }

        public Criteria andRevParentIdNotIn(List<Integer> values) {
            addCriterion("rev_parent_id not in", values, "revParentId");
            return (Criteria) this;
        }

        public Criteria andRevParentIdBetween(Integer value1, Integer value2) {
            addCriterion("rev_parent_id between", value1, value2, "revParentId");
            return (Criteria) this;
        }

        public Criteria andRevParentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("rev_parent_id not between", value1, value2, "revParentId");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.72792075-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-06-29T21:26:26.727933639-04:00", comments="Source Table: zfgc_wikirevision")
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