package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class AttributeValueMappingDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    protected Integer limit;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    public AttributeValueMappingDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    public Integer getLimit() {
        return limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    public Integer getOffset() {
        return offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
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

        public Criteria andAttributeValueMappingIdIsNull() {
            addCriterion("attribute_value_mapping_id is null");
            return (Criteria) this;
        }

        public Criteria andAttributeValueMappingIdIsNotNull() {
            addCriterion("attribute_value_mapping_id is not null");
            return (Criteria) this;
        }

        public Criteria andAttributeValueMappingIdEqualTo(Integer value) {
            addCriterion("attribute_value_mapping_id =", value, "attributeValueMappingId");
            return (Criteria) this;
        }

        public Criteria andAttributeValueMappingIdNotEqualTo(Integer value) {
            addCriterion("attribute_value_mapping_id <>", value, "attributeValueMappingId");
            return (Criteria) this;
        }

        public Criteria andAttributeValueMappingIdGreaterThan(Integer value) {
            addCriterion("attribute_value_mapping_id >", value, "attributeValueMappingId");
            return (Criteria) this;
        }

        public Criteria andAttributeValueMappingIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("attribute_value_mapping_id >=", value, "attributeValueMappingId");
            return (Criteria) this;
        }

        public Criteria andAttributeValueMappingIdLessThan(Integer value) {
            addCriterion("attribute_value_mapping_id <", value, "attributeValueMappingId");
            return (Criteria) this;
        }

        public Criteria andAttributeValueMappingIdLessThanOrEqualTo(Integer value) {
            addCriterion("attribute_value_mapping_id <=", value, "attributeValueMappingId");
            return (Criteria) this;
        }

        public Criteria andAttributeValueMappingIdIn(List<Integer> values) {
            addCriterion("attribute_value_mapping_id in", values, "attributeValueMappingId");
            return (Criteria) this;
        }

        public Criteria andAttributeValueMappingIdNotIn(List<Integer> values) {
            addCriterion("attribute_value_mapping_id not in", values, "attributeValueMappingId");
            return (Criteria) this;
        }

        public Criteria andAttributeValueMappingIdBetween(Integer value1, Integer value2) {
            addCriterion("attribute_value_mapping_id between", value1, value2, "attributeValueMappingId");
            return (Criteria) this;
        }

        public Criteria andAttributeValueMappingIdNotBetween(Integer value1, Integer value2) {
            addCriterion("attribute_value_mapping_id not between", value1, value2, "attributeValueMappingId");
            return (Criteria) this;
        }

        public Criteria andAttributeDataTypeIsNull() {
            addCriterion("attribute_data_type is null");
            return (Criteria) this;
        }

        public Criteria andAttributeDataTypeIsNotNull() {
            addCriterion("attribute_data_type is not null");
            return (Criteria) this;
        }

        public Criteria andAttributeDataTypeEqualTo(String value) {
            addCriterion("attribute_data_type =", value, "attributeDataType");
            return (Criteria) this;
        }

        public Criteria andAttributeDataTypeNotEqualTo(String value) {
            addCriterion("attribute_data_type <>", value, "attributeDataType");
            return (Criteria) this;
        }

        public Criteria andAttributeDataTypeGreaterThan(String value) {
            addCriterion("attribute_data_type >", value, "attributeDataType");
            return (Criteria) this;
        }

        public Criteria andAttributeDataTypeGreaterThanOrEqualTo(String value) {
            addCriterion("attribute_data_type >=", value, "attributeDataType");
            return (Criteria) this;
        }

        public Criteria andAttributeDataTypeLessThan(String value) {
            addCriterion("attribute_data_type <", value, "attributeDataType");
            return (Criteria) this;
        }

        public Criteria andAttributeDataTypeLessThanOrEqualTo(String value) {
            addCriterion("attribute_data_type <=", value, "attributeDataType");
            return (Criteria) this;
        }

        public Criteria andAttributeDataTypeLike(String value) {
            addCriterion("attribute_data_type ilike", value, "attributeDataType");
            return (Criteria) this;
        }

        public Criteria andAttributeDataTypeNotLike(String value) {
            addCriterion("attribute_data_type not ilike", value, "attributeDataType");
            return (Criteria) this;
        }

        public Criteria andAttributeDataTypeIn(List<String> values) {
            addCriterion("attribute_data_type in", values, "attributeDataType");
            return (Criteria) this;
        }

        public Criteria andAttributeDataTypeNotIn(List<String> values) {
            addCriterion("attribute_data_type not in", values, "attributeDataType");
            return (Criteria) this;
        }

        public Criteria andAttributeDataTypeBetween(String value1, String value2) {
            addCriterion("attribute_data_type between", value1, value2, "attributeDataType");
            return (Criteria) this;
        }

        public Criteria andAttributeDataTypeNotBetween(String value1, String value2) {
            addCriterion("attribute_data_type not between", value1, value2, "attributeDataType");
            return (Criteria) this;
        }

        public Criteria andFromValueIsNull() {
            addCriterion("from_value is null");
            return (Criteria) this;
        }

        public Criteria andFromValueIsNotNull() {
            addCriterion("from_value is not null");
            return (Criteria) this;
        }

        public Criteria andFromValueEqualTo(String value) {
            addCriterion("from_value =", value, "fromValue");
            return (Criteria) this;
        }

        public Criteria andFromValueNotEqualTo(String value) {
            addCriterion("from_value <>", value, "fromValue");
            return (Criteria) this;
        }

        public Criteria andFromValueGreaterThan(String value) {
            addCriterion("from_value >", value, "fromValue");
            return (Criteria) this;
        }

        public Criteria andFromValueGreaterThanOrEqualTo(String value) {
            addCriterion("from_value >=", value, "fromValue");
            return (Criteria) this;
        }

        public Criteria andFromValueLessThan(String value) {
            addCriterion("from_value <", value, "fromValue");
            return (Criteria) this;
        }

        public Criteria andFromValueLessThanOrEqualTo(String value) {
            addCriterion("from_value <=", value, "fromValue");
            return (Criteria) this;
        }

        public Criteria andFromValueLike(String value) {
            addCriterion("from_value ilike", value, "fromValue");
            return (Criteria) this;
        }

        public Criteria andFromValueNotLike(String value) {
            addCriterion("from_value not ilike", value, "fromValue");
            return (Criteria) this;
        }

        public Criteria andFromValueIn(List<String> values) {
            addCriterion("from_value in", values, "fromValue");
            return (Criteria) this;
        }

        public Criteria andFromValueNotIn(List<String> values) {
            addCriterion("from_value not in", values, "fromValue");
            return (Criteria) this;
        }

        public Criteria andFromValueBetween(String value1, String value2) {
            addCriterion("from_value between", value1, value2, "fromValue");
            return (Criteria) this;
        }

        public Criteria andFromValueNotBetween(String value1, String value2) {
            addCriterion("from_value not between", value1, value2, "fromValue");
            return (Criteria) this;
        }

        public Criteria andToValueIsNull() {
            addCriterion("to_value is null");
            return (Criteria) this;
        }

        public Criteria andToValueIsNotNull() {
            addCriterion("to_value is not null");
            return (Criteria) this;
        }

        public Criteria andToValueEqualTo(String value) {
            addCriterion("to_value =", value, "toValue");
            return (Criteria) this;
        }

        public Criteria andToValueNotEqualTo(String value) {
            addCriterion("to_value <>", value, "toValue");
            return (Criteria) this;
        }

        public Criteria andToValueGreaterThan(String value) {
            addCriterion("to_value >", value, "toValue");
            return (Criteria) this;
        }

        public Criteria andToValueGreaterThanOrEqualTo(String value) {
            addCriterion("to_value >=", value, "toValue");
            return (Criteria) this;
        }

        public Criteria andToValueLessThan(String value) {
            addCriterion("to_value <", value, "toValue");
            return (Criteria) this;
        }

        public Criteria andToValueLessThanOrEqualTo(String value) {
            addCriterion("to_value <=", value, "toValue");
            return (Criteria) this;
        }

        public Criteria andToValueLike(String value) {
            addCriterion("to_value ilike", value, "toValue");
            return (Criteria) this;
        }

        public Criteria andToValueNotLike(String value) {
            addCriterion("to_value not ilike", value, "toValue");
            return (Criteria) this;
        }

        public Criteria andToValueIn(List<String> values) {
            addCriterion("to_value in", values, "toValue");
            return (Criteria) this;
        }

        public Criteria andToValueNotIn(List<String> values) {
            addCriterion("to_value not in", values, "toValue");
            return (Criteria) this;
        }

        public Criteria andToValueBetween(String value1, String value2) {
            addCriterion("to_value between", value1, value2, "toValue");
            return (Criteria) this;
        }

        public Criteria andToValueNotBetween(String value1, String value2) {
            addCriterion("to_value not between", value1, value2, "toValue");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_value_mapping")
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