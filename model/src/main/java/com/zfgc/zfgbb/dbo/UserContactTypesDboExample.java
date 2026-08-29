package com.zfgc.zfgbb.dbo;

import com.zfgc.zfgbb.persistence.LikePatterns;
import jakarta.annotation.Generated;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserContactTypesDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    protected Integer limit;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    public UserContactTypesDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    public Integer getLimit() {
        return limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    public Integer getOffset() {
        return offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
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

        public Criteria andUserContactTypesIdIsNull() {
            addCriterion("user_contact_types_id is null");
            return (Criteria) this;
        }

        public Criteria andUserContactTypesIdIsNotNull() {
            addCriterion("user_contact_types_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserContactTypesIdEqualTo(Integer value) {
            addCriterion("user_contact_types_id =", value, "userContactTypesId");
            return (Criteria) this;
        }

        public Criteria andUserContactTypesIdNotEqualTo(Integer value) {
            addCriterion("user_contact_types_id <>", value, "userContactTypesId");
            return (Criteria) this;
        }

        public Criteria andUserContactTypesIdGreaterThan(Integer value) {
            addCriterion("user_contact_types_id >", value, "userContactTypesId");
            return (Criteria) this;
        }

        public Criteria andUserContactTypesIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_contact_types_id >=", value, "userContactTypesId");
            return (Criteria) this;
        }

        public Criteria andUserContactTypesIdLessThan(Integer value) {
            addCriterion("user_contact_types_id <", value, "userContactTypesId");
            return (Criteria) this;
        }

        public Criteria andUserContactTypesIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_contact_types_id <=", value, "userContactTypesId");
            return (Criteria) this;
        }

        public Criteria andUserContactTypesIdIn(List<Integer> values) {
            addCriterion("user_contact_types_id in", values, "userContactTypesId");
            return (Criteria) this;
        }

        public Criteria andUserContactTypesIdNotIn(List<Integer> values) {
            addCriterion("user_contact_types_id not in", values, "userContactTypesId");
            return (Criteria) this;
        }

        public Criteria andUserContactTypesIdBetween(Integer value1, Integer value2) {
            addCriterion("user_contact_types_id between", value1, value2, "userContactTypesId");
            return (Criteria) this;
        }

        public Criteria andUserContactTypesIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_contact_types_id not between", value1, value2, "userContactTypesId");
            return (Criteria) this;
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

        public Criteria andContactTypeIdIsNull() {
            addCriterion("contact_type_id is null");
            return (Criteria) this;
        }

        public Criteria andContactTypeIdIsNotNull() {
            addCriterion("contact_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andContactTypeIdEqualTo(Integer value) {
            addCriterion("contact_type_id =", value, "contactTypeId");
            return (Criteria) this;
        }

        public Criteria andContactTypeIdNotEqualTo(Integer value) {
            addCriterion("contact_type_id <>", value, "contactTypeId");
            return (Criteria) this;
        }

        public Criteria andContactTypeIdGreaterThan(Integer value) {
            addCriterion("contact_type_id >", value, "contactTypeId");
            return (Criteria) this;
        }

        public Criteria andContactTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("contact_type_id >=", value, "contactTypeId");
            return (Criteria) this;
        }

        public Criteria andContactTypeIdLessThan(Integer value) {
            addCriterion("contact_type_id <", value, "contactTypeId");
            return (Criteria) this;
        }

        public Criteria andContactTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("contact_type_id <=", value, "contactTypeId");
            return (Criteria) this;
        }

        public Criteria andContactTypeIdIn(List<Integer> values) {
            addCriterion("contact_type_id in", values, "contactTypeId");
            return (Criteria) this;
        }

        public Criteria andContactTypeIdNotIn(List<Integer> values) {
            addCriterion("contact_type_id not in", values, "contactTypeId");
            return (Criteria) this;
        }

        public Criteria andContactTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("contact_type_id between", value1, value2, "contactTypeId");
            return (Criteria) this;
        }

        public Criteria andContactTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("contact_type_id not between", value1, value2, "contactTypeId");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomTitleIsNull() {
            addCriterion("contact_type_custom_title is null");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomTitleIsNotNull() {
            addCriterion("contact_type_custom_title is not null");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomTitleEqualTo(String value) {
            addCriterion("contact_type_custom_title =", value, "contactTypeCustomTitle");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomTitleNotEqualTo(String value) {
            addCriterion("contact_type_custom_title <>", value, "contactTypeCustomTitle");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomTitleGreaterThan(String value) {
            addCriterion("contact_type_custom_title >", value, "contactTypeCustomTitle");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomTitleGreaterThanOrEqualTo(String value) {
            addCriterion("contact_type_custom_title >=", value, "contactTypeCustomTitle");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomTitleLessThan(String value) {
            addCriterion("contact_type_custom_title <", value, "contactTypeCustomTitle");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomTitleLessThanOrEqualTo(String value) {
            addCriterion("contact_type_custom_title <=", value, "contactTypeCustomTitle");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomTitleLike(String value) {
            addCriterion("contact_type_custom_title ilike", value, "contactTypeCustomTitle");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomTitleNotLike(String value) {
            addCriterion("contact_type_custom_title not ilike", value, "contactTypeCustomTitle");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomTitleIn(List<String> values) {
            addCriterion("contact_type_custom_title in", values, "contactTypeCustomTitle");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomTitleNotIn(List<String> values) {
            addCriterion("contact_type_custom_title not in", values, "contactTypeCustomTitle");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomTitleBetween(String value1, String value2) {
            addCriterion("contact_type_custom_title between", value1, value2, "contactTypeCustomTitle");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomTitleNotBetween(String value1, String value2) {
            addCriterion("contact_type_custom_title not between", value1, value2, "contactTypeCustomTitle");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomDescriptionIsNull() {
            addCriterion("contact_type_custom_description is null");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomDescriptionIsNotNull() {
            addCriterion("contact_type_custom_description is not null");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomDescriptionEqualTo(String value) {
            addCriterion("contact_type_custom_description =", value, "contactTypeCustomDescription");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomDescriptionNotEqualTo(String value) {
            addCriterion("contact_type_custom_description <>", value, "contactTypeCustomDescription");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomDescriptionGreaterThan(String value) {
            addCriterion("contact_type_custom_description >", value, "contactTypeCustomDescription");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("contact_type_custom_description >=", value, "contactTypeCustomDescription");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomDescriptionLessThan(String value) {
            addCriterion("contact_type_custom_description <", value, "contactTypeCustomDescription");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomDescriptionLessThanOrEqualTo(String value) {
            addCriterion("contact_type_custom_description <=", value, "contactTypeCustomDescription");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomDescriptionLike(String value) {
            addCriterion("contact_type_custom_description ilike", value, "contactTypeCustomDescription");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomDescriptionNotLike(String value) {
            addCriterion("contact_type_custom_description not ilike", value, "contactTypeCustomDescription");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomDescriptionIn(List<String> values) {
            addCriterion("contact_type_custom_description in", values, "contactTypeCustomDescription");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomDescriptionNotIn(List<String> values) {
            addCriterion("contact_type_custom_description not in", values, "contactTypeCustomDescription");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomDescriptionBetween(String value1, String value2) {
            addCriterion("contact_type_custom_description between", value1, value2, "contactTypeCustomDescription");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomDescriptionNotBetween(String value1, String value2) {
            addCriterion("contact_type_custom_description not between", value1, value2, "contactTypeCustomDescription");
            return (Criteria) this;
        }

        public Criteria andCreatedTsIsNull() {
            addCriterion("created_ts is null");
            return (Criteria) this;
        }

        public Criteria andCreatedTsIsNotNull() {
            addCriterion("created_ts is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedTsEqualTo(OffsetDateTime value) {
            addCriterion("created_ts =", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsNotEqualTo(OffsetDateTime value) {
            addCriterion("created_ts <>", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsGreaterThan(OffsetDateTime value) {
            addCriterion("created_ts >", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("created_ts >=", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsLessThan(OffsetDateTime value) {
            addCriterion("created_ts <", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("created_ts <=", value, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsIn(List<OffsetDateTime> values) {
            addCriterion("created_ts in", values, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsNotIn(List<OffsetDateTime> values) {
            addCriterion("created_ts not in", values, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("created_ts between", value1, value2, "createdTs");
            return (Criteria) this;
        }

        public Criteria andCreatedTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("created_ts not between", value1, value2, "createdTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsIsNull() {
            addCriterion("updated_ts is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsIsNotNull() {
            addCriterion("updated_ts is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsEqualTo(OffsetDateTime value) {
            addCriterion("updated_ts =", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsNotEqualTo(OffsetDateTime value) {
            addCriterion("updated_ts <>", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsGreaterThan(OffsetDateTime value) {
            addCriterion("updated_ts >", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsGreaterThanOrEqualTo(OffsetDateTime value) {
            addCriterion("updated_ts >=", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsLessThan(OffsetDateTime value) {
            addCriterion("updated_ts <", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsLessThanOrEqualTo(OffsetDateTime value) {
            addCriterion("updated_ts <=", value, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsIn(List<OffsetDateTime> values) {
            addCriterion("updated_ts in", values, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsNotIn(List<OffsetDateTime> values) {
            addCriterion("updated_ts not in", values, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("updated_ts between", value1, value2, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andUpdatedTsNotBetween(OffsetDateTime value1, OffsetDateTime value2) {
            addCriterion("updated_ts not between", value1, value2, "updatedTs");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomTitleContains(String value) {
            addCriterion("contact_type_custom_title ilike", LikePatterns.contains(value), "contactTypeCustomTitle");
            return (Criteria) this;
        }

        public Criteria andContactTypeCustomDescriptionContains(String value) {
            addCriterion("contact_type_custom_description ilike", LikePatterns.contains(value), "contactTypeCustomDescription");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.user_contact_types")
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