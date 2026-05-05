package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class ChildBoardViewDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470837501-04:00", comments="Source Table: zfgbb.child_board_view")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470851431-04:00", comments="Source Table: zfgbb.child_board_view")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.47086876-04:00", comments="Source Table: zfgbb.child_board_view")
    protected List<Criteria> oredCriteria;

    protected Integer limit;

    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470830531-04:00", comments="Source Table: zfgbb.child_board_view")
    public ChildBoardViewDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470842741-04:00", comments="Source Table: zfgbb.child_board_view")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470846951-04:00", comments="Source Table: zfgbb.child_board_view")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470855671-04:00", comments="Source Table: zfgbb.child_board_view")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.47085967-04:00", comments="Source Table: zfgbb.child_board_view")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.47087323-04:00", comments="Source Table: zfgbb.child_board_view")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.47087603-04:00", comments="Source Table: zfgbb.child_board_view")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.47088004-04:00", comments="Source Table: zfgbb.child_board_view")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.47088389-04:00", comments="Source Table: zfgbb.child_board_view")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.47088786-04:00", comments="Source Table: zfgbb.child_board_view")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470891669-04:00", comments="Source Table: zfgbb.child_board_view")
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

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470897669-04:00", comments="Source Table: zfgbb.child_board_view")
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

        public Criteria andBoardIdIsNull() {
            addCriterion("board_id is null");
            return (Criteria) this;
        }

        public Criteria andBoardIdIsNotNull() {
            addCriterion("board_id is not null");
            return (Criteria) this;
        }

        public Criteria andBoardIdEqualTo(Integer value) {
            addCriterion("board_id =", value, "boardId");
            return (Criteria) this;
        }

        public Criteria andBoardIdNotEqualTo(Integer value) {
            addCriterion("board_id <>", value, "boardId");
            return (Criteria) this;
        }

        public Criteria andBoardIdGreaterThan(Integer value) {
            addCriterion("board_id >", value, "boardId");
            return (Criteria) this;
        }

        public Criteria andBoardIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("board_id >=", value, "boardId");
            return (Criteria) this;
        }

        public Criteria andBoardIdLessThan(Integer value) {
            addCriterion("board_id <", value, "boardId");
            return (Criteria) this;
        }

        public Criteria andBoardIdLessThanOrEqualTo(Integer value) {
            addCriterion("board_id <=", value, "boardId");
            return (Criteria) this;
        }

        public Criteria andBoardIdIn(List<Integer> values) {
            addCriterion("board_id in", values, "boardId");
            return (Criteria) this;
        }

        public Criteria andBoardIdNotIn(List<Integer> values) {
            addCriterion("board_id not in", values, "boardId");
            return (Criteria) this;
        }

        public Criteria andBoardIdBetween(Integer value1, Integer value2) {
            addCriterion("board_id between", value1, value2, "boardId");
            return (Criteria) this;
        }

        public Criteria andBoardIdNotBetween(Integer value1, Integer value2) {
            addCriterion("board_id not between", value1, value2, "boardId");
            return (Criteria) this;
        }

        public Criteria andBoardNameIsNull() {
            addCriterion("board_name is null");
            return (Criteria) this;
        }

        public Criteria andBoardNameIsNotNull() {
            addCriterion("board_name is not null");
            return (Criteria) this;
        }

        public Criteria andBoardNameEqualTo(String value) {
            addCriterion("board_name =", value, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameNotEqualTo(String value) {
            addCriterion("board_name <>", value, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameGreaterThan(String value) {
            addCriterion("board_name >", value, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameGreaterThanOrEqualTo(String value) {
            addCriterion("board_name >=", value, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameLessThan(String value) {
            addCriterion("board_name <", value, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameLessThanOrEqualTo(String value) {
            addCriterion("board_name <=", value, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameLike(String value) {
            addCriterion("board_name like", value, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameNotLike(String value) {
            addCriterion("board_name not like", value, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameIn(List<String> values) {
            addCriterion("board_name in", values, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameNotIn(List<String> values) {
            addCriterion("board_name not in", values, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameBetween(String value1, String value2) {
            addCriterion("board_name between", value1, value2, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameNotBetween(String value1, String value2) {
            addCriterion("board_name not between", value1, value2, "boardName");
            return (Criteria) this;
        }

        public Criteria andParentBoardIdIsNull() {
            addCriterion("parent_board_id is null");
            return (Criteria) this;
        }

        public Criteria andParentBoardIdIsNotNull() {
            addCriterion("parent_board_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentBoardIdEqualTo(Integer value) {
            addCriterion("parent_board_id =", value, "parentBoardId");
            return (Criteria) this;
        }

        public Criteria andParentBoardIdNotEqualTo(Integer value) {
            addCriterion("parent_board_id <>", value, "parentBoardId");
            return (Criteria) this;
        }

        public Criteria andParentBoardIdGreaterThan(Integer value) {
            addCriterion("parent_board_id >", value, "parentBoardId");
            return (Criteria) this;
        }

        public Criteria andParentBoardIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("parent_board_id >=", value, "parentBoardId");
            return (Criteria) this;
        }

        public Criteria andParentBoardIdLessThan(Integer value) {
            addCriterion("parent_board_id <", value, "parentBoardId");
            return (Criteria) this;
        }

        public Criteria andParentBoardIdLessThanOrEqualTo(Integer value) {
            addCriterion("parent_board_id <=", value, "parentBoardId");
            return (Criteria) this;
        }

        public Criteria andParentBoardIdIn(List<Integer> values) {
            addCriterion("parent_board_id in", values, "parentBoardId");
            return (Criteria) this;
        }

        public Criteria andParentBoardIdNotIn(List<Integer> values) {
            addCriterion("parent_board_id not in", values, "parentBoardId");
            return (Criteria) this;
        }

        public Criteria andParentBoardIdBetween(Integer value1, Integer value2) {
            addCriterion("parent_board_id between", value1, value2, "parentBoardId");
            return (Criteria) this;
        }

        public Criteria andParentBoardIdNotBetween(Integer value1, Integer value2) {
            addCriterion("parent_board_id not between", value1, value2, "parentBoardId");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.470995806-04:00", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-04T22:43:37.471003336-04:00", comments="Source Table: zfgbb.child_board_view")
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