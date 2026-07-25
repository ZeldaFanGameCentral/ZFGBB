package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class RecentActivityViewDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    protected Integer limit;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    public RecentActivityViewDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    public Integer getLimit() {
        return limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    public Integer getOffset() {
        return offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
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

        public Criteria andThreadIdIsNull() {
            addCriterion("thread_id is null");
            return (Criteria) this;
        }

        public Criteria andThreadIdIsNotNull() {
            addCriterion("thread_id is not null");
            return (Criteria) this;
        }

        public Criteria andThreadIdEqualTo(Integer value) {
            addCriterion("thread_id =", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdNotEqualTo(Integer value) {
            addCriterion("thread_id <>", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdGreaterThan(Integer value) {
            addCriterion("thread_id >", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("thread_id >=", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdLessThan(Integer value) {
            addCriterion("thread_id <", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdLessThanOrEqualTo(Integer value) {
            addCriterion("thread_id <=", value, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdIn(List<Integer> values) {
            addCriterion("thread_id in", values, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdNotIn(List<Integer> values) {
            addCriterion("thread_id not in", values, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdBetween(Integer value1, Integer value2) {
            addCriterion("thread_id between", value1, value2, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadIdNotBetween(Integer value1, Integer value2) {
            addCriterion("thread_id not between", value1, value2, "threadId");
            return (Criteria) this;
        }

        public Criteria andThreadNameIsNull() {
            addCriterion("thread_name is null");
            return (Criteria) this;
        }

        public Criteria andThreadNameIsNotNull() {
            addCriterion("thread_name is not null");
            return (Criteria) this;
        }

        public Criteria andThreadNameEqualTo(String value) {
            addCriterion("thread_name =", value, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameNotEqualTo(String value) {
            addCriterion("thread_name <>", value, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameGreaterThan(String value) {
            addCriterion("thread_name >", value, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameGreaterThanOrEqualTo(String value) {
            addCriterion("thread_name >=", value, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameLessThan(String value) {
            addCriterion("thread_name <", value, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameLessThanOrEqualTo(String value) {
            addCriterion("thread_name <=", value, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameLike(String value) {
            addCriterion("thread_name ilike", value, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameNotLike(String value) {
            addCriterion("thread_name not ilike", value, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameIn(List<String> values) {
            addCriterion("thread_name in", values, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameNotIn(List<String> values) {
            addCriterion("thread_name not in", values, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameBetween(String value1, String value2) {
            addCriterion("thread_name between", value1, value2, "threadName");
            return (Criteria) this;
        }

        public Criteria andThreadNameNotBetween(String value1, String value2) {
            addCriterion("thread_name not between", value1, value2, "threadName");
            return (Criteria) this;
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
            addCriterion("board_name ilike", value, "boardName");
            return (Criteria) this;
        }

        public Criteria andBoardNameNotLike(String value) {
            addCriterion("board_name not ilike", value, "boardName");
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

        public Criteria andLastPosterIsNull() {
            addCriterion("last_poster is null");
            return (Criteria) this;
        }

        public Criteria andLastPosterIsNotNull() {
            addCriterion("last_poster is not null");
            return (Criteria) this;
        }

        public Criteria andLastPosterEqualTo(String value) {
            addCriterion("last_poster =", value, "lastPoster");
            return (Criteria) this;
        }

        public Criteria andLastPosterNotEqualTo(String value) {
            addCriterion("last_poster <>", value, "lastPoster");
            return (Criteria) this;
        }

        public Criteria andLastPosterGreaterThan(String value) {
            addCriterion("last_poster >", value, "lastPoster");
            return (Criteria) this;
        }

        public Criteria andLastPosterGreaterThanOrEqualTo(String value) {
            addCriterion("last_poster >=", value, "lastPoster");
            return (Criteria) this;
        }

        public Criteria andLastPosterLessThan(String value) {
            addCriterion("last_poster <", value, "lastPoster");
            return (Criteria) this;
        }

        public Criteria andLastPosterLessThanOrEqualTo(String value) {
            addCriterion("last_poster <=", value, "lastPoster");
            return (Criteria) this;
        }

        public Criteria andLastPosterLike(String value) {
            addCriterion("last_poster ilike", value, "lastPoster");
            return (Criteria) this;
        }

        public Criteria andLastPosterNotLike(String value) {
            addCriterion("last_poster not ilike", value, "lastPoster");
            return (Criteria) this;
        }

        public Criteria andLastPosterIn(List<String> values) {
            addCriterion("last_poster in", values, "lastPoster");
            return (Criteria) this;
        }

        public Criteria andLastPosterNotIn(List<String> values) {
            addCriterion("last_poster not in", values, "lastPoster");
            return (Criteria) this;
        }

        public Criteria andLastPosterBetween(String value1, String value2) {
            addCriterion("last_poster between", value1, value2, "lastPoster");
            return (Criteria) this;
        }

        public Criteria andLastPosterNotBetween(String value1, String value2) {
            addCriterion("last_poster not between", value1, value2, "lastPoster");
            return (Criteria) this;
        }

        public Criteria andLastPosterIdIsNull() {
            addCriterion("last_poster_id is null");
            return (Criteria) this;
        }

        public Criteria andLastPosterIdIsNotNull() {
            addCriterion("last_poster_id is not null");
            return (Criteria) this;
        }

        public Criteria andLastPosterIdEqualTo(Integer value) {
            addCriterion("last_poster_id =", value, "lastPosterId");
            return (Criteria) this;
        }

        public Criteria andLastPosterIdNotEqualTo(Integer value) {
            addCriterion("last_poster_id <>", value, "lastPosterId");
            return (Criteria) this;
        }

        public Criteria andLastPosterIdGreaterThan(Integer value) {
            addCriterion("last_poster_id >", value, "lastPosterId");
            return (Criteria) this;
        }

        public Criteria andLastPosterIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("last_poster_id >=", value, "lastPosterId");
            return (Criteria) this;
        }

        public Criteria andLastPosterIdLessThan(Integer value) {
            addCriterion("last_poster_id <", value, "lastPosterId");
            return (Criteria) this;
        }

        public Criteria andLastPosterIdLessThanOrEqualTo(Integer value) {
            addCriterion("last_poster_id <=", value, "lastPosterId");
            return (Criteria) this;
        }

        public Criteria andLastPosterIdIn(List<Integer> values) {
            addCriterion("last_poster_id in", values, "lastPosterId");
            return (Criteria) this;
        }

        public Criteria andLastPosterIdNotIn(List<Integer> values) {
            addCriterion("last_poster_id not in", values, "lastPosterId");
            return (Criteria) this;
        }

        public Criteria andLastPosterIdBetween(Integer value1, Integer value2) {
            addCriterion("last_poster_id between", value1, value2, "lastPosterId");
            return (Criteria) this;
        }

        public Criteria andLastPosterIdNotBetween(Integer value1, Integer value2) {
            addCriterion("last_poster_id not between", value1, value2, "lastPosterId");
            return (Criteria) this;
        }

        public Criteria andLastPostTsIsNull() {
            addCriterion("last_post_ts is null");
            return (Criteria) this;
        }

        public Criteria andLastPostTsIsNotNull() {
            addCriterion("last_post_ts is not null");
            return (Criteria) this;
        }

        public Criteria andLastPostTsEqualTo(OffsetDateTime value) {
            addCriterion("last_post_ts =", value, "lastPostTs");
            return (Criteria) this;
        }

        public Criteria andLastPostTsNotEqualTo(OffsetDateTime value) {
            addCriterion("last_post_ts <>", value, "lastPostTs");
            return (Criteria) this;
        }

        public Criteria andLastPostTsGreaterThan(OffsetDateTime value) {
            addCriterion("last_post_ts >", value, "lastPostTs");
            return (Criteria) this;
        }

        public Criteria andLastPostTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("last_post_ts >=", value, "lastPostTs");
            return (Criteria) this;
        }

        public Criteria andLastPostTsLessThan(OffsetDateTime value) {
            addCriterion("last_post_ts <", value, "lastPostTs");
            return (Criteria) this;
        }

        public Criteria andLastPostTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("last_post_ts <=", value, "lastPostTs");
            return (Criteria) this;
        }

        public Criteria andLastPostTsIn(List<OffsetDateTime> values) {
            addCriterion("last_post_ts in", values, "lastPostTs");
            return (Criteria) this;
        }

        public Criteria andLastPostTsNotIn(List<OffsetDateTime> values) {
            addCriterion("last_post_ts not in", values, "lastPostTs");
            return (Criteria) this;
        }

        public Criteria andLastPostTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("last_post_ts between", value1, value2, "lastPostTs");
            return (Criteria) this;
        }

        public Criteria andLastPostTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("last_post_ts not between", value1, value2, "lastPostTs");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.recent_activity_view")
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