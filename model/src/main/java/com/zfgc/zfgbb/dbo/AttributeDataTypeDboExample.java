package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

public class AttributeDataTypeDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    protected Integer limit;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    public AttributeDataTypeDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    public Integer getLimit() {
        return limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    public Integer getOffset() {
        return offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
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

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code ilike", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not ilike", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andLabelIsNull() {
            addCriterion("label is null");
            return (Criteria) this;
        }

        public Criteria andLabelIsNotNull() {
            addCriterion("label is not null");
            return (Criteria) this;
        }

        public Criteria andLabelEqualTo(String value) {
            addCriterion("label =", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotEqualTo(String value) {
            addCriterion("label <>", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelGreaterThan(String value) {
            addCriterion("label >", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelGreaterThanOrEqualTo(String value) {
            addCriterion("label >=", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelLessThan(String value) {
            addCriterion("label <", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelLessThanOrEqualTo(String value) {
            addCriterion("label <=", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelLike(String value) {
            addCriterion("label ilike", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotLike(String value) {
            addCriterion("label not ilike", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelIn(List<String> values) {
            addCriterion("label in", values, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotIn(List<String> values) {
            addCriterion("label not in", values, "label");
            return (Criteria) this;
        }

        public Criteria andLabelBetween(String value1, String value2) {
            addCriterion("label between", value1, value2, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotBetween(String value1, String value2) {
            addCriterion("label not between", value1, value2, "label");
            return (Criteria) this;
        }

        public Criteria andOrdinalIsNull() {
            addCriterion("ordinal is null");
            return (Criteria) this;
        }

        public Criteria andOrdinalIsNotNull() {
            addCriterion("ordinal is not null");
            return (Criteria) this;
        }

        public Criteria andOrdinalEqualTo(Integer value) {
            addCriterion("ordinal =", value, "ordinal");
            return (Criteria) this;
        }

        public Criteria andOrdinalNotEqualTo(Integer value) {
            addCriterion("ordinal <>", value, "ordinal");
            return (Criteria) this;
        }

        public Criteria andOrdinalGreaterThan(Integer value) {
            addCriterion("ordinal >", value, "ordinal");
            return (Criteria) this;
        }

        public Criteria andOrdinalGreaterThanOrEqualTo(Integer value) {
            addCriterion("ordinal >=", value, "ordinal");
            return (Criteria) this;
        }

        public Criteria andOrdinalLessThan(Integer value) {
            addCriterion("ordinal <", value, "ordinal");
            return (Criteria) this;
        }

        public Criteria andOrdinalLessThanOrEqualTo(Integer value) {
            addCriterion("ordinal <=", value, "ordinal");
            return (Criteria) this;
        }

        public Criteria andOrdinalIn(List<Integer> values) {
            addCriterion("ordinal in", values, "ordinal");
            return (Criteria) this;
        }

        public Criteria andOrdinalNotIn(List<Integer> values) {
            addCriterion("ordinal not in", values, "ordinal");
            return (Criteria) this;
        }

        public Criteria andOrdinalBetween(Integer value1, Integer value2) {
            addCriterion("ordinal between", value1, value2, "ordinal");
            return (Criteria) this;
        }

        public Criteria andOrdinalNotBetween(Integer value1, Integer value2) {
            addCriterion("ordinal not between", value1, value2, "ordinal");
            return (Criteria) this;
        }

        public Criteria andValidationPatternIsNull() {
            addCriterion("validation_pattern is null");
            return (Criteria) this;
        }

        public Criteria andValidationPatternIsNotNull() {
            addCriterion("validation_pattern is not null");
            return (Criteria) this;
        }

        public Criteria andValidationPatternEqualTo(String value) {
            addCriterion("validation_pattern =", value, "validationPattern");
            return (Criteria) this;
        }

        public Criteria andValidationPatternNotEqualTo(String value) {
            addCriterion("validation_pattern <>", value, "validationPattern");
            return (Criteria) this;
        }

        public Criteria andValidationPatternGreaterThan(String value) {
            addCriterion("validation_pattern >", value, "validationPattern");
            return (Criteria) this;
        }

        public Criteria andValidationPatternGreaterThanOrEqualTo(String value) {
            addCriterion("validation_pattern >=", value, "validationPattern");
            return (Criteria) this;
        }

        public Criteria andValidationPatternLessThan(String value) {
            addCriterion("validation_pattern <", value, "validationPattern");
            return (Criteria) this;
        }

        public Criteria andValidationPatternLessThanOrEqualTo(String value) {
            addCriterion("validation_pattern <=", value, "validationPattern");
            return (Criteria) this;
        }

        public Criteria andValidationPatternLike(String value) {
            addCriterion("validation_pattern ilike", value, "validationPattern");
            return (Criteria) this;
        }

        public Criteria andValidationPatternNotLike(String value) {
            addCriterion("validation_pattern not ilike", value, "validationPattern");
            return (Criteria) this;
        }

        public Criteria andValidationPatternIn(List<String> values) {
            addCriterion("validation_pattern in", values, "validationPattern");
            return (Criteria) this;
        }

        public Criteria andValidationPatternNotIn(List<String> values) {
            addCriterion("validation_pattern not in", values, "validationPattern");
            return (Criteria) this;
        }

        public Criteria andValidationPatternBetween(String value1, String value2) {
            addCriterion("validation_pattern between", value1, value2, "validationPattern");
            return (Criteria) this;
        }

        public Criteria andValidationPatternNotBetween(String value1, String value2) {
            addCriterion("validation_pattern not between", value1, value2, "validationPattern");
            return (Criteria) this;
        }

        public Criteria andFallbackValueIsNull() {
            addCriterion("fallback_value is null");
            return (Criteria) this;
        }

        public Criteria andFallbackValueIsNotNull() {
            addCriterion("fallback_value is not null");
            return (Criteria) this;
        }

        public Criteria andFallbackValueEqualTo(String value) {
            addCriterion("fallback_value =", value, "fallbackValue");
            return (Criteria) this;
        }

        public Criteria andFallbackValueNotEqualTo(String value) {
            addCriterion("fallback_value <>", value, "fallbackValue");
            return (Criteria) this;
        }

        public Criteria andFallbackValueGreaterThan(String value) {
            addCriterion("fallback_value >", value, "fallbackValue");
            return (Criteria) this;
        }

        public Criteria andFallbackValueGreaterThanOrEqualTo(String value) {
            addCriterion("fallback_value >=", value, "fallbackValue");
            return (Criteria) this;
        }

        public Criteria andFallbackValueLessThan(String value) {
            addCriterion("fallback_value <", value, "fallbackValue");
            return (Criteria) this;
        }

        public Criteria andFallbackValueLessThanOrEqualTo(String value) {
            addCriterion("fallback_value <=", value, "fallbackValue");
            return (Criteria) this;
        }

        public Criteria andFallbackValueLike(String value) {
            addCriterion("fallback_value ilike", value, "fallbackValue");
            return (Criteria) this;
        }

        public Criteria andFallbackValueNotLike(String value) {
            addCriterion("fallback_value not ilike", value, "fallbackValue");
            return (Criteria) this;
        }

        public Criteria andFallbackValueIn(List<String> values) {
            addCriterion("fallback_value in", values, "fallbackValue");
            return (Criteria) this;
        }

        public Criteria andFallbackValueNotIn(List<String> values) {
            addCriterion("fallback_value not in", values, "fallbackValue");
            return (Criteria) this;
        }

        public Criteria andFallbackValueBetween(String value1, String value2) {
            addCriterion("fallback_value between", value1, value2, "fallbackValue");
            return (Criteria) this;
        }

        public Criteria andFallbackValueNotBetween(String value1, String value2) {
            addCriterion("fallback_value not between", value1, value2, "fallbackValue");
            return (Criteria) this;
        }

        public Criteria andValueAdmitsWhitespaceIsNull() {
            addCriterion("value_admits_whitespace is null");
            return (Criteria) this;
        }

        public Criteria andValueAdmitsWhitespaceIsNotNull() {
            addCriterion("value_admits_whitespace is not null");
            return (Criteria) this;
        }

        public Criteria andValueAdmitsWhitespaceEqualTo(Boolean value) {
            addCriterion("value_admits_whitespace =", value, "valueAdmitsWhitespace");
            return (Criteria) this;
        }

        public Criteria andValueAdmitsWhitespaceNotEqualTo(Boolean value) {
            addCriterion("value_admits_whitespace <>", value, "valueAdmitsWhitespace");
            return (Criteria) this;
        }

        public Criteria andValueAdmitsWhitespaceGreaterThan(Boolean value) {
            addCriterion("value_admits_whitespace >", value, "valueAdmitsWhitespace");
            return (Criteria) this;
        }

        public Criteria andValueAdmitsWhitespaceGreaterThanOrEqualTo(Boolean value) {
            addCriterion("value_admits_whitespace >=", value, "valueAdmitsWhitespace");
            return (Criteria) this;
        }

        public Criteria andValueAdmitsWhitespaceLessThan(Boolean value) {
            addCriterion("value_admits_whitespace <", value, "valueAdmitsWhitespace");
            return (Criteria) this;
        }

        public Criteria andValueAdmitsWhitespaceLessThanOrEqualTo(Boolean value) {
            addCriterion("value_admits_whitespace <=", value, "valueAdmitsWhitespace");
            return (Criteria) this;
        }

        public Criteria andValueAdmitsWhitespaceIn(List<Boolean> values) {
            addCriterion("value_admits_whitespace in", values, "valueAdmitsWhitespace");
            return (Criteria) this;
        }

        public Criteria andValueAdmitsWhitespaceNotIn(List<Boolean> values) {
            addCriterion("value_admits_whitespace not in", values, "valueAdmitsWhitespace");
            return (Criteria) this;
        }

        public Criteria andValueAdmitsWhitespaceBetween(Boolean value1, Boolean value2) {
            addCriterion("value_admits_whitespace between", value1, value2, "valueAdmitsWhitespace");
            return (Criteria) this;
        }

        public Criteria andValueAdmitsWhitespaceNotBetween(Boolean value1, Boolean value2) {
            addCriterion("value_admits_whitespace not between", value1, value2, "valueAdmitsWhitespace");
            return (Criteria) this;
        }

        public Criteria andLowercasesValueIsNull() {
            addCriterion("lowercases_value is null");
            return (Criteria) this;
        }

        public Criteria andLowercasesValueIsNotNull() {
            addCriterion("lowercases_value is not null");
            return (Criteria) this;
        }

        public Criteria andLowercasesValueEqualTo(Boolean value) {
            addCriterion("lowercases_value =", value, "lowercasesValue");
            return (Criteria) this;
        }

        public Criteria andLowercasesValueNotEqualTo(Boolean value) {
            addCriterion("lowercases_value <>", value, "lowercasesValue");
            return (Criteria) this;
        }

        public Criteria andLowercasesValueGreaterThan(Boolean value) {
            addCriterion("lowercases_value >", value, "lowercasesValue");
            return (Criteria) this;
        }

        public Criteria andLowercasesValueGreaterThanOrEqualTo(Boolean value) {
            addCriterion("lowercases_value >=", value, "lowercasesValue");
            return (Criteria) this;
        }

        public Criteria andLowercasesValueLessThan(Boolean value) {
            addCriterion("lowercases_value <", value, "lowercasesValue");
            return (Criteria) this;
        }

        public Criteria andLowercasesValueLessThanOrEqualTo(Boolean value) {
            addCriterion("lowercases_value <=", value, "lowercasesValue");
            return (Criteria) this;
        }

        public Criteria andLowercasesValueIn(List<Boolean> values) {
            addCriterion("lowercases_value in", values, "lowercasesValue");
            return (Criteria) this;
        }

        public Criteria andLowercasesValueNotIn(List<Boolean> values) {
            addCriterion("lowercases_value not in", values, "lowercasesValue");
            return (Criteria) this;
        }

        public Criteria andLowercasesValueBetween(Boolean value1, Boolean value2) {
            addCriterion("lowercases_value between", value1, value2, "lowercasesValue");
            return (Criteria) this;
        }

        public Criteria andLowercasesValueNotBetween(Boolean value1, Boolean value2) {
            addCriterion("lowercases_value not between", value1, value2, "lowercasesValue");
            return (Criteria) this;
        }

        public Criteria andBareIntegerUnitIsNull() {
            addCriterion("bare_integer_unit is null");
            return (Criteria) this;
        }

        public Criteria andBareIntegerUnitIsNotNull() {
            addCriterion("bare_integer_unit is not null");
            return (Criteria) this;
        }

        public Criteria andBareIntegerUnitEqualTo(String value) {
            addCriterion("bare_integer_unit =", value, "bareIntegerUnit");
            return (Criteria) this;
        }

        public Criteria andBareIntegerUnitNotEqualTo(String value) {
            addCriterion("bare_integer_unit <>", value, "bareIntegerUnit");
            return (Criteria) this;
        }

        public Criteria andBareIntegerUnitGreaterThan(String value) {
            addCriterion("bare_integer_unit >", value, "bareIntegerUnit");
            return (Criteria) this;
        }

        public Criteria andBareIntegerUnitGreaterThanOrEqualTo(String value) {
            addCriterion("bare_integer_unit >=", value, "bareIntegerUnit");
            return (Criteria) this;
        }

        public Criteria andBareIntegerUnitLessThan(String value) {
            addCriterion("bare_integer_unit <", value, "bareIntegerUnit");
            return (Criteria) this;
        }

        public Criteria andBareIntegerUnitLessThanOrEqualTo(String value) {
            addCriterion("bare_integer_unit <=", value, "bareIntegerUnit");
            return (Criteria) this;
        }

        public Criteria andBareIntegerUnitLike(String value) {
            addCriterion("bare_integer_unit ilike", value, "bareIntegerUnit");
            return (Criteria) this;
        }

        public Criteria andBareIntegerUnitNotLike(String value) {
            addCriterion("bare_integer_unit not ilike", value, "bareIntegerUnit");
            return (Criteria) this;
        }

        public Criteria andBareIntegerUnitIn(List<String> values) {
            addCriterion("bare_integer_unit in", values, "bareIntegerUnit");
            return (Criteria) this;
        }

        public Criteria andBareIntegerUnitNotIn(List<String> values) {
            addCriterion("bare_integer_unit not in", values, "bareIntegerUnit");
            return (Criteria) this;
        }

        public Criteria andBareIntegerUnitBetween(String value1, String value2) {
            addCriterion("bare_integer_unit between", value1, value2, "bareIntegerUnit");
            return (Criteria) this;
        }

        public Criteria andBareIntegerUnitNotBetween(String value1, String value2) {
            addCriterion("bare_integer_unit not between", value1, value2, "bareIntegerUnit");
            return (Criteria) this;
        }

        public Criteria andAllowedValuesIsNull() {
            addCriterion("allowed_values is null");
            return (Criteria) this;
        }

        public Criteria andAllowedValuesIsNotNull() {
            addCriterion("allowed_values is not null");
            return (Criteria) this;
        }

        public Criteria andAllowedValuesEqualTo(String value) {
            addCriterion("allowed_values =", value, "allowedValues");
            return (Criteria) this;
        }

        public Criteria andAllowedValuesNotEqualTo(String value) {
            addCriterion("allowed_values <>", value, "allowedValues");
            return (Criteria) this;
        }

        public Criteria andAllowedValuesGreaterThan(String value) {
            addCriterion("allowed_values >", value, "allowedValues");
            return (Criteria) this;
        }

        public Criteria andAllowedValuesGreaterThanOrEqualTo(String value) {
            addCriterion("allowed_values >=", value, "allowedValues");
            return (Criteria) this;
        }

        public Criteria andAllowedValuesLessThan(String value) {
            addCriterion("allowed_values <", value, "allowedValues");
            return (Criteria) this;
        }

        public Criteria andAllowedValuesLessThanOrEqualTo(String value) {
            addCriterion("allowed_values <=", value, "allowedValues");
            return (Criteria) this;
        }

        public Criteria andAllowedValuesLike(String value) {
            addCriterion("allowed_values ilike", value, "allowedValues");
            return (Criteria) this;
        }

        public Criteria andAllowedValuesNotLike(String value) {
            addCriterion("allowed_values not ilike", value, "allowedValues");
            return (Criteria) this;
        }

        public Criteria andAllowedValuesIn(List<String> values) {
            addCriterion("allowed_values in", values, "allowedValues");
            return (Criteria) this;
        }

        public Criteria andAllowedValuesNotIn(List<String> values) {
            addCriterion("allowed_values not in", values, "allowedValues");
            return (Criteria) this;
        }

        public Criteria andAllowedValuesBetween(String value1, String value2) {
            addCriterion("allowed_values between", value1, value2, "allowedValues");
            return (Criteria) this;
        }

        public Criteria andAllowedValuesNotBetween(String value1, String value2) {
            addCriterion("allowed_values not between", value1, value2, "allowedValues");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
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