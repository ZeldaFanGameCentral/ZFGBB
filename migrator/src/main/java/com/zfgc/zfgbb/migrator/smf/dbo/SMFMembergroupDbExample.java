package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class SMFMembergroupDbExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037008393-04:00", comments="Source Table: smf_1membergroups")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037022592-04:00", comments="Source Table: smf_1membergroups")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037037932-04:00", comments="Source Table: smf_1membergroups")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037000473-04:00", comments="Source Table: smf_1membergroups")
    public SMFMembergroupDbExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037013582-04:00", comments="Source Table: smf_1membergroups")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037017932-04:00", comments="Source Table: smf_1membergroups")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037026892-04:00", comments="Source Table: smf_1membergroups")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037029642-04:00", comments="Source Table: smf_1membergroups")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037042642-04:00", comments="Source Table: smf_1membergroups")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037047171-04:00", comments="Source Table: smf_1membergroups")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037051491-04:00", comments="Source Table: smf_1membergroups")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037057791-04:00", comments="Source Table: smf_1membergroups")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037061971-04:00", comments="Source Table: smf_1membergroups")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037066001-04:00", comments="Source Table: smf_1membergroups")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.037071471-04:00", comments="Source Table: smf_1membergroups")
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

        public Criteria andIdGroupIsNull() {
            addCriterion("id_group is null");
            return (Criteria) this;
        }

        public Criteria andIdGroupIsNotNull() {
            addCriterion("id_group is not null");
            return (Criteria) this;
        }

        public Criteria andIdGroupEqualTo(Integer value) {
            addCriterion("id_group =", value, "idGroup");
            return (Criteria) this;
        }

        public Criteria andIdGroupNotEqualTo(Integer value) {
            addCriterion("id_group <>", value, "idGroup");
            return (Criteria) this;
        }

        public Criteria andIdGroupGreaterThan(Integer value) {
            addCriterion("id_group >", value, "idGroup");
            return (Criteria) this;
        }

        public Criteria andIdGroupGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_group >=", value, "idGroup");
            return (Criteria) this;
        }

        public Criteria andIdGroupLessThan(Integer value) {
            addCriterion("id_group <", value, "idGroup");
            return (Criteria) this;
        }

        public Criteria andIdGroupLessThanOrEqualTo(Integer value) {
            addCriterion("id_group <=", value, "idGroup");
            return (Criteria) this;
        }

        public Criteria andIdGroupIn(List<Integer> values) {
            addCriterion("id_group in", values, "idGroup");
            return (Criteria) this;
        }

        public Criteria andIdGroupNotIn(List<Integer> values) {
            addCriterion("id_group not in", values, "idGroup");
            return (Criteria) this;
        }

        public Criteria andIdGroupBetween(Integer value1, Integer value2) {
            addCriterion("id_group between", value1, value2, "idGroup");
            return (Criteria) this;
        }

        public Criteria andIdGroupNotBetween(Integer value1, Integer value2) {
            addCriterion("id_group not between", value1, value2, "idGroup");
            return (Criteria) this;
        }

        public Criteria andGroupNameIsNull() {
            addCriterion("group_name is null");
            return (Criteria) this;
        }

        public Criteria andGroupNameIsNotNull() {
            addCriterion("group_name is not null");
            return (Criteria) this;
        }

        public Criteria andGroupNameEqualTo(String value) {
            addCriterion("group_name =", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotEqualTo(String value) {
            addCriterion("group_name <>", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameGreaterThan(String value) {
            addCriterion("group_name >", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameGreaterThanOrEqualTo(String value) {
            addCriterion("group_name >=", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLessThan(String value) {
            addCriterion("group_name <", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLessThanOrEqualTo(String value) {
            addCriterion("group_name <=", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLike(String value) {
            addCriterion("group_name like", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotLike(String value) {
            addCriterion("group_name not like", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameIn(List<String> values) {
            addCriterion("group_name in", values, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotIn(List<String> values) {
            addCriterion("group_name not in", values, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameBetween(String value1, String value2) {
            addCriterion("group_name between", value1, value2, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotBetween(String value1, String value2) {
            addCriterion("group_name not between", value1, value2, "groupName");
            return (Criteria) this;
        }

        public Criteria andOnlineColorIsNull() {
            addCriterion("online_color is null");
            return (Criteria) this;
        }

        public Criteria andOnlineColorIsNotNull() {
            addCriterion("online_color is not null");
            return (Criteria) this;
        }

        public Criteria andOnlineColorEqualTo(String value) {
            addCriterion("online_color =", value, "onlineColor");
            return (Criteria) this;
        }

        public Criteria andOnlineColorNotEqualTo(String value) {
            addCriterion("online_color <>", value, "onlineColor");
            return (Criteria) this;
        }

        public Criteria andOnlineColorGreaterThan(String value) {
            addCriterion("online_color >", value, "onlineColor");
            return (Criteria) this;
        }

        public Criteria andOnlineColorGreaterThanOrEqualTo(String value) {
            addCriterion("online_color >=", value, "onlineColor");
            return (Criteria) this;
        }

        public Criteria andOnlineColorLessThan(String value) {
            addCriterion("online_color <", value, "onlineColor");
            return (Criteria) this;
        }

        public Criteria andOnlineColorLessThanOrEqualTo(String value) {
            addCriterion("online_color <=", value, "onlineColor");
            return (Criteria) this;
        }

        public Criteria andOnlineColorLike(String value) {
            addCriterion("online_color like", value, "onlineColor");
            return (Criteria) this;
        }

        public Criteria andOnlineColorNotLike(String value) {
            addCriterion("online_color not like", value, "onlineColor");
            return (Criteria) this;
        }

        public Criteria andOnlineColorIn(List<String> values) {
            addCriterion("online_color in", values, "onlineColor");
            return (Criteria) this;
        }

        public Criteria andOnlineColorNotIn(List<String> values) {
            addCriterion("online_color not in", values, "onlineColor");
            return (Criteria) this;
        }

        public Criteria andOnlineColorBetween(String value1, String value2) {
            addCriterion("online_color between", value1, value2, "onlineColor");
            return (Criteria) this;
        }

        public Criteria andOnlineColorNotBetween(String value1, String value2) {
            addCriterion("online_color not between", value1, value2, "onlineColor");
            return (Criteria) this;
        }

        public Criteria andMinPostsIsNull() {
            addCriterion("min_posts is null");
            return (Criteria) this;
        }

        public Criteria andMinPostsIsNotNull() {
            addCriterion("min_posts is not null");
            return (Criteria) this;
        }

        public Criteria andMinPostsEqualTo(Integer value) {
            addCriterion("min_posts =", value, "minPosts");
            return (Criteria) this;
        }

        public Criteria andMinPostsNotEqualTo(Integer value) {
            addCriterion("min_posts <>", value, "minPosts");
            return (Criteria) this;
        }

        public Criteria andMinPostsGreaterThan(Integer value) {
            addCriterion("min_posts >", value, "minPosts");
            return (Criteria) this;
        }

        public Criteria andMinPostsGreaterThanOrEqualTo(Integer value) {
            addCriterion("min_posts >=", value, "minPosts");
            return (Criteria) this;
        }

        public Criteria andMinPostsLessThan(Integer value) {
            addCriterion("min_posts <", value, "minPosts");
            return (Criteria) this;
        }

        public Criteria andMinPostsLessThanOrEqualTo(Integer value) {
            addCriterion("min_posts <=", value, "minPosts");
            return (Criteria) this;
        }

        public Criteria andMinPostsIn(List<Integer> values) {
            addCriterion("min_posts in", values, "minPosts");
            return (Criteria) this;
        }

        public Criteria andMinPostsNotIn(List<Integer> values) {
            addCriterion("min_posts not in", values, "minPosts");
            return (Criteria) this;
        }

        public Criteria andMinPostsBetween(Integer value1, Integer value2) {
            addCriterion("min_posts between", value1, value2, "minPosts");
            return (Criteria) this;
        }

        public Criteria andMinPostsNotBetween(Integer value1, Integer value2) {
            addCriterion("min_posts not between", value1, value2, "minPosts");
            return (Criteria) this;
        }

        public Criteria andMaxMessagesIsNull() {
            addCriterion("max_messages is null");
            return (Criteria) this;
        }

        public Criteria andMaxMessagesIsNotNull() {
            addCriterion("max_messages is not null");
            return (Criteria) this;
        }

        public Criteria andMaxMessagesEqualTo(Integer value) {
            addCriterion("max_messages =", value, "maxMessages");
            return (Criteria) this;
        }

        public Criteria andMaxMessagesNotEqualTo(Integer value) {
            addCriterion("max_messages <>", value, "maxMessages");
            return (Criteria) this;
        }

        public Criteria andMaxMessagesGreaterThan(Integer value) {
            addCriterion("max_messages >", value, "maxMessages");
            return (Criteria) this;
        }

        public Criteria andMaxMessagesGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_messages >=", value, "maxMessages");
            return (Criteria) this;
        }

        public Criteria andMaxMessagesLessThan(Integer value) {
            addCriterion("max_messages <", value, "maxMessages");
            return (Criteria) this;
        }

        public Criteria andMaxMessagesLessThanOrEqualTo(Integer value) {
            addCriterion("max_messages <=", value, "maxMessages");
            return (Criteria) this;
        }

        public Criteria andMaxMessagesIn(List<Integer> values) {
            addCriterion("max_messages in", values, "maxMessages");
            return (Criteria) this;
        }

        public Criteria andMaxMessagesNotIn(List<Integer> values) {
            addCriterion("max_messages not in", values, "maxMessages");
            return (Criteria) this;
        }

        public Criteria andMaxMessagesBetween(Integer value1, Integer value2) {
            addCriterion("max_messages between", value1, value2, "maxMessages");
            return (Criteria) this;
        }

        public Criteria andMaxMessagesNotBetween(Integer value1, Integer value2) {
            addCriterion("max_messages not between", value1, value2, "maxMessages");
            return (Criteria) this;
        }

        public Criteria andStarsIsNull() {
            addCriterion("stars is null");
            return (Criteria) this;
        }

        public Criteria andStarsIsNotNull() {
            addCriterion("stars is not null");
            return (Criteria) this;
        }

        public Criteria andStarsEqualTo(String value) {
            addCriterion("stars =", value, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsNotEqualTo(String value) {
            addCriterion("stars <>", value, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsGreaterThan(String value) {
            addCriterion("stars >", value, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsGreaterThanOrEqualTo(String value) {
            addCriterion("stars >=", value, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsLessThan(String value) {
            addCriterion("stars <", value, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsLessThanOrEqualTo(String value) {
            addCriterion("stars <=", value, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsLike(String value) {
            addCriterion("stars like", value, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsNotLike(String value) {
            addCriterion("stars not like", value, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsIn(List<String> values) {
            addCriterion("stars in", values, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsNotIn(List<String> values) {
            addCriterion("stars not in", values, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsBetween(String value1, String value2) {
            addCriterion("stars between", value1, value2, "stars");
            return (Criteria) this;
        }

        public Criteria andStarsNotBetween(String value1, String value2) {
            addCriterion("stars not between", value1, value2, "stars");
            return (Criteria) this;
        }

        public Criteria andGroupmodoptionsIsNull() {
            addCriterion("GroupModOptions is null");
            return (Criteria) this;
        }

        public Criteria andGroupmodoptionsIsNotNull() {
            addCriterion("GroupModOptions is not null");
            return (Criteria) this;
        }

        public Criteria andGroupmodoptionsEqualTo(String value) {
            addCriterion("GroupModOptions =", value, "groupmodoptions");
            return (Criteria) this;
        }

        public Criteria andGroupmodoptionsNotEqualTo(String value) {
            addCriterion("GroupModOptions <>", value, "groupmodoptions");
            return (Criteria) this;
        }

        public Criteria andGroupmodoptionsGreaterThan(String value) {
            addCriterion("GroupModOptions >", value, "groupmodoptions");
            return (Criteria) this;
        }

        public Criteria andGroupmodoptionsGreaterThanOrEqualTo(String value) {
            addCriterion("GroupModOptions >=", value, "groupmodoptions");
            return (Criteria) this;
        }

        public Criteria andGroupmodoptionsLessThan(String value) {
            addCriterion("GroupModOptions <", value, "groupmodoptions");
            return (Criteria) this;
        }

        public Criteria andGroupmodoptionsLessThanOrEqualTo(String value) {
            addCriterion("GroupModOptions <=", value, "groupmodoptions");
            return (Criteria) this;
        }

        public Criteria andGroupmodoptionsLike(String value) {
            addCriterion("GroupModOptions like", value, "groupmodoptions");
            return (Criteria) this;
        }

        public Criteria andGroupmodoptionsNotLike(String value) {
            addCriterion("GroupModOptions not like", value, "groupmodoptions");
            return (Criteria) this;
        }

        public Criteria andGroupmodoptionsIn(List<String> values) {
            addCriterion("GroupModOptions in", values, "groupmodoptions");
            return (Criteria) this;
        }

        public Criteria andGroupmodoptionsNotIn(List<String> values) {
            addCriterion("GroupModOptions not in", values, "groupmodoptions");
            return (Criteria) this;
        }

        public Criteria andGroupmodoptionsBetween(String value1, String value2) {
            addCriterion("GroupModOptions between", value1, value2, "groupmodoptions");
            return (Criteria) this;
        }

        public Criteria andGroupmodoptionsNotBetween(String value1, String value2) {
            addCriterion("GroupModOptions not between", value1, value2, "groupmodoptions");
            return (Criteria) this;
        }

        public Criteria andMonitorgroupIsNull() {
            addCriterion("monitorGroup is null");
            return (Criteria) this;
        }

        public Criteria andMonitorgroupIsNotNull() {
            addCriterion("monitorGroup is not null");
            return (Criteria) this;
        }

        public Criteria andMonitorgroupEqualTo(Byte value) {
            addCriterion("monitorGroup =", value, "monitorgroup");
            return (Criteria) this;
        }

        public Criteria andMonitorgroupNotEqualTo(Byte value) {
            addCriterion("monitorGroup <>", value, "monitorgroup");
            return (Criteria) this;
        }

        public Criteria andMonitorgroupGreaterThan(Byte value) {
            addCriterion("monitorGroup >", value, "monitorgroup");
            return (Criteria) this;
        }

        public Criteria andMonitorgroupGreaterThanOrEqualTo(Byte value) {
            addCriterion("monitorGroup >=", value, "monitorgroup");
            return (Criteria) this;
        }

        public Criteria andMonitorgroupLessThan(Byte value) {
            addCriterion("monitorGroup <", value, "monitorgroup");
            return (Criteria) this;
        }

        public Criteria andMonitorgroupLessThanOrEqualTo(Byte value) {
            addCriterion("monitorGroup <=", value, "monitorgroup");
            return (Criteria) this;
        }

        public Criteria andMonitorgroupIn(List<Byte> values) {
            addCriterion("monitorGroup in", values, "monitorgroup");
            return (Criteria) this;
        }

        public Criteria andMonitorgroupNotIn(List<Byte> values) {
            addCriterion("monitorGroup not in", values, "monitorgroup");
            return (Criteria) this;
        }

        public Criteria andMonitorgroupBetween(Byte value1, Byte value2) {
            addCriterion("monitorGroup between", value1, value2, "monitorgroup");
            return (Criteria) this;
        }

        public Criteria andMonitorgroupNotBetween(Byte value1, Byte value2) {
            addCriterion("monitorGroup not between", value1, value2, "monitorgroup");
            return (Criteria) this;
        }

        public Criteria andGroupTypeIsNull() {
            addCriterion("group_type is null");
            return (Criteria) this;
        }

        public Criteria andGroupTypeIsNotNull() {
            addCriterion("group_type is not null");
            return (Criteria) this;
        }

        public Criteria andGroupTypeEqualTo(Integer value) {
            addCriterion("group_type =", value, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupTypeNotEqualTo(Integer value) {
            addCriterion("group_type <>", value, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupTypeGreaterThan(Integer value) {
            addCriterion("group_type >", value, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("group_type >=", value, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupTypeLessThan(Integer value) {
            addCriterion("group_type <", value, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupTypeLessThanOrEqualTo(Integer value) {
            addCriterion("group_type <=", value, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupTypeIn(List<Integer> values) {
            addCriterion("group_type in", values, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupTypeNotIn(List<Integer> values) {
            addCriterion("group_type not in", values, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupTypeBetween(Integer value1, Integer value2) {
            addCriterion("group_type between", value1, value2, "groupType");
            return (Criteria) this;
        }

        public Criteria andGroupTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("group_type not between", value1, value2, "groupType");
            return (Criteria) this;
        }

        public Criteria andHiddenIsNull() {
            addCriterion("hidden is null");
            return (Criteria) this;
        }

        public Criteria andHiddenIsNotNull() {
            addCriterion("hidden is not null");
            return (Criteria) this;
        }

        public Criteria andHiddenEqualTo(Integer value) {
            addCriterion("hidden =", value, "hidden");
            return (Criteria) this;
        }

        public Criteria andHiddenNotEqualTo(Integer value) {
            addCriterion("hidden <>", value, "hidden");
            return (Criteria) this;
        }

        public Criteria andHiddenGreaterThan(Integer value) {
            addCriterion("hidden >", value, "hidden");
            return (Criteria) this;
        }

        public Criteria andHiddenGreaterThanOrEqualTo(Integer value) {
            addCriterion("hidden >=", value, "hidden");
            return (Criteria) this;
        }

        public Criteria andHiddenLessThan(Integer value) {
            addCriterion("hidden <", value, "hidden");
            return (Criteria) this;
        }

        public Criteria andHiddenLessThanOrEqualTo(Integer value) {
            addCriterion("hidden <=", value, "hidden");
            return (Criteria) this;
        }

        public Criteria andHiddenIn(List<Integer> values) {
            addCriterion("hidden in", values, "hidden");
            return (Criteria) this;
        }

        public Criteria andHiddenNotIn(List<Integer> values) {
            addCriterion("hidden not in", values, "hidden");
            return (Criteria) this;
        }

        public Criteria andHiddenBetween(Integer value1, Integer value2) {
            addCriterion("hidden between", value1, value2, "hidden");
            return (Criteria) this;
        }

        public Criteria andHiddenNotBetween(Integer value1, Integer value2) {
            addCriterion("hidden not between", value1, value2, "hidden");
            return (Criteria) this;
        }

        public Criteria andIdParentIsNull() {
            addCriterion("id_parent is null");
            return (Criteria) this;
        }

        public Criteria andIdParentIsNotNull() {
            addCriterion("id_parent is not null");
            return (Criteria) this;
        }

        public Criteria andIdParentEqualTo(Integer value) {
            addCriterion("id_parent =", value, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentNotEqualTo(Integer value) {
            addCriterion("id_parent <>", value, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentGreaterThan(Integer value) {
            addCriterion("id_parent >", value, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_parent >=", value, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentLessThan(Integer value) {
            addCriterion("id_parent <", value, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentLessThanOrEqualTo(Integer value) {
            addCriterion("id_parent <=", value, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentIn(List<Integer> values) {
            addCriterion("id_parent in", values, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentNotIn(List<Integer> values) {
            addCriterion("id_parent not in", values, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentBetween(Integer value1, Integer value2) {
            addCriterion("id_parent between", value1, value2, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentNotBetween(Integer value1, Integer value2) {
            addCriterion("id_parent not between", value1, value2, "idParent");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.03738401-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-07-11T12:19:31.03739531-04:00", comments="Source Table: smf_1membergroups")
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