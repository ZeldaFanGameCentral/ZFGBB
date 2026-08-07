package com.zfgc.zfgbb.dbo;

import com.zfgc.zfgbb.persistence.LikePatterns;
import jakarta.annotation.Generated;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class BBCodeConfigDboExample {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    protected String orderByClause;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    protected boolean distinct;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    protected List<Criteria> oredCriteria;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    protected Integer limit;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    protected Integer offset;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    public BBCodeConfigDboExample() {
        oredCriteria = new ArrayList<>();
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    public String getOrderByClause() {
        return orderByClause;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    public boolean isDistinct() {
        return distinct;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    public Integer getLimit() {
        return limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    public Integer getOffset() {
        return offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
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

        public Criteria andBbCodeConfigIdIsNull() {
            addCriterion("bb_code_config_id is null");
            return (Criteria) this;
        }

        public Criteria andBbCodeConfigIdIsNotNull() {
            addCriterion("bb_code_config_id is not null");
            return (Criteria) this;
        }

        public Criteria andBbCodeConfigIdEqualTo(Integer value) {
            addCriterion("bb_code_config_id =", value, "bbCodeConfigId");
            return (Criteria) this;
        }

        public Criteria andBbCodeConfigIdNotEqualTo(Integer value) {
            addCriterion("bb_code_config_id <>", value, "bbCodeConfigId");
            return (Criteria) this;
        }

        public Criteria andBbCodeConfigIdGreaterThan(Integer value) {
            addCriterion("bb_code_config_id >", value, "bbCodeConfigId");
            return (Criteria) this;
        }

        public Criteria andBbCodeConfigIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("bb_code_config_id >=", value, "bbCodeConfigId");
            return (Criteria) this;
        }

        public Criteria andBbCodeConfigIdLessThan(Integer value) {
            addCriterion("bb_code_config_id <", value, "bbCodeConfigId");
            return (Criteria) this;
        }

        public Criteria andBbCodeConfigIdLessThanOrEqualTo(Integer value) {
            addCriterion("bb_code_config_id <=", value, "bbCodeConfigId");
            return (Criteria) this;
        }

        public Criteria andBbCodeConfigIdIn(List<Integer> values) {
            addCriterion("bb_code_config_id in", values, "bbCodeConfigId");
            return (Criteria) this;
        }

        public Criteria andBbCodeConfigIdNotIn(List<Integer> values) {
            addCriterion("bb_code_config_id not in", values, "bbCodeConfigId");
            return (Criteria) this;
        }

        public Criteria andBbCodeConfigIdBetween(Integer value1, Integer value2) {
            addCriterion("bb_code_config_id between", value1, value2, "bbCodeConfigId");
            return (Criteria) this;
        }

        public Criteria andBbCodeConfigIdNotBetween(Integer value1, Integer value2) {
            addCriterion("bb_code_config_id not between", value1, value2, "bbCodeConfigId");
            return (Criteria) this;
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

        public Criteria andEndTagIsNull() {
            addCriterion("end_tag is null");
            return (Criteria) this;
        }

        public Criteria andEndTagIsNotNull() {
            addCriterion("end_tag is not null");
            return (Criteria) this;
        }

        public Criteria andEndTagEqualTo(String value) {
            addCriterion("end_tag =", value, "endTag");
            return (Criteria) this;
        }

        public Criteria andEndTagNotEqualTo(String value) {
            addCriterion("end_tag <>", value, "endTag");
            return (Criteria) this;
        }

        public Criteria andEndTagGreaterThan(String value) {
            addCriterion("end_tag >", value, "endTag");
            return (Criteria) this;
        }

        public Criteria andEndTagGreaterThanOrEqualTo(String value) {
            addCriterion("end_tag >=", value, "endTag");
            return (Criteria) this;
        }

        public Criteria andEndTagLessThan(String value) {
            addCriterion("end_tag <", value, "endTag");
            return (Criteria) this;
        }

        public Criteria andEndTagLessThanOrEqualTo(String value) {
            addCriterion("end_tag <=", value, "endTag");
            return (Criteria) this;
        }

        public Criteria andEndTagLike(String value) {
            addCriterion("end_tag ilike", value, "endTag");
            return (Criteria) this;
        }

        public Criteria andEndTagNotLike(String value) {
            addCriterion("end_tag not ilike", value, "endTag");
            return (Criteria) this;
        }

        public Criteria andEndTagIn(List<String> values) {
            addCriterion("end_tag in", values, "endTag");
            return (Criteria) this;
        }

        public Criteria andEndTagNotIn(List<String> values) {
            addCriterion("end_tag not in", values, "endTag");
            return (Criteria) this;
        }

        public Criteria andEndTagBetween(String value1, String value2) {
            addCriterion("end_tag between", value1, value2, "endTag");
            return (Criteria) this;
        }

        public Criteria andEndTagNotBetween(String value1, String value2) {
            addCriterion("end_tag not between", value1, value2, "endTag");
            return (Criteria) this;
        }

        public Criteria andProcessContentFlagIsNull() {
            addCriterion("process_content_flag is null");
            return (Criteria) this;
        }

        public Criteria andProcessContentFlagIsNotNull() {
            addCriterion("process_content_flag is not null");
            return (Criteria) this;
        }

        public Criteria andProcessContentFlagEqualTo(Boolean value) {
            addCriterion("process_content_flag =", value, "processContentFlag");
            return (Criteria) this;
        }

        public Criteria andProcessContentFlagNotEqualTo(Boolean value) {
            addCriterion("process_content_flag <>", value, "processContentFlag");
            return (Criteria) this;
        }

        public Criteria andProcessContentFlagGreaterThan(Boolean value) {
            addCriterion("process_content_flag >", value, "processContentFlag");
            return (Criteria) this;
        }

        public Criteria andProcessContentFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("process_content_flag >=", value, "processContentFlag");
            return (Criteria) this;
        }

        public Criteria andProcessContentFlagLessThan(Boolean value) {
            addCriterion("process_content_flag <", value, "processContentFlag");
            return (Criteria) this;
        }

        public Criteria andProcessContentFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("process_content_flag <=", value, "processContentFlag");
            return (Criteria) this;
        }

        public Criteria andProcessContentFlagIn(List<Boolean> values) {
            addCriterion("process_content_flag in", values, "processContentFlag");
            return (Criteria) this;
        }

        public Criteria andProcessContentFlagNotIn(List<Boolean> values) {
            addCriterion("process_content_flag not in", values, "processContentFlag");
            return (Criteria) this;
        }

        public Criteria andProcessContentFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("process_content_flag between", value1, value2, "processContentFlag");
            return (Criteria) this;
        }

        public Criteria andProcessContentFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("process_content_flag not between", value1, value2, "processContentFlag");
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

        public Criteria andSelfClosingFlagIsNull() {
            addCriterion("self_closing_flag is null");
            return (Criteria) this;
        }

        public Criteria andSelfClosingFlagIsNotNull() {
            addCriterion("self_closing_flag is not null");
            return (Criteria) this;
        }

        public Criteria andSelfClosingFlagEqualTo(Boolean value) {
            addCriterion("self_closing_flag =", value, "selfClosingFlag");
            return (Criteria) this;
        }

        public Criteria andSelfClosingFlagNotEqualTo(Boolean value) {
            addCriterion("self_closing_flag <>", value, "selfClosingFlag");
            return (Criteria) this;
        }

        public Criteria andSelfClosingFlagGreaterThan(Boolean value) {
            addCriterion("self_closing_flag >", value, "selfClosingFlag");
            return (Criteria) this;
        }

        public Criteria andSelfClosingFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("self_closing_flag >=", value, "selfClosingFlag");
            return (Criteria) this;
        }

        public Criteria andSelfClosingFlagLessThan(Boolean value) {
            addCriterion("self_closing_flag <", value, "selfClosingFlag");
            return (Criteria) this;
        }

        public Criteria andSelfClosingFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("self_closing_flag <=", value, "selfClosingFlag");
            return (Criteria) this;
        }

        public Criteria andSelfClosingFlagIn(List<Boolean> values) {
            addCriterion("self_closing_flag in", values, "selfClosingFlag");
            return (Criteria) this;
        }

        public Criteria andSelfClosingFlagNotIn(List<Boolean> values) {
            addCriterion("self_closing_flag not in", values, "selfClosingFlag");
            return (Criteria) this;
        }

        public Criteria andSelfClosingFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("self_closing_flag between", value1, value2, "selfClosingFlag");
            return (Criteria) this;
        }

        public Criteria andSelfClosingFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("self_closing_flag not between", value1, value2, "selfClosingFlag");
            return (Criteria) this;
        }

        public Criteria andEnabledFlagIsNull() {
            addCriterion("enabled_flag is null");
            return (Criteria) this;
        }

        public Criteria andEnabledFlagIsNotNull() {
            addCriterion("enabled_flag is not null");
            return (Criteria) this;
        }

        public Criteria andEnabledFlagEqualTo(Boolean value) {
            addCriterion("enabled_flag =", value, "enabledFlag");
            return (Criteria) this;
        }

        public Criteria andEnabledFlagNotEqualTo(Boolean value) {
            addCriterion("enabled_flag <>", value, "enabledFlag");
            return (Criteria) this;
        }

        public Criteria andEnabledFlagGreaterThan(Boolean value) {
            addCriterion("enabled_flag >", value, "enabledFlag");
            return (Criteria) this;
        }

        public Criteria andEnabledFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("enabled_flag >=", value, "enabledFlag");
            return (Criteria) this;
        }

        public Criteria andEnabledFlagLessThan(Boolean value) {
            addCriterion("enabled_flag <", value, "enabledFlag");
            return (Criteria) this;
        }

        public Criteria andEnabledFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("enabled_flag <=", value, "enabledFlag");
            return (Criteria) this;
        }

        public Criteria andEnabledFlagIn(List<Boolean> values) {
            addCriterion("enabled_flag in", values, "enabledFlag");
            return (Criteria) this;
        }

        public Criteria andEnabledFlagNotIn(List<Boolean> values) {
            addCriterion("enabled_flag not in", values, "enabledFlag");
            return (Criteria) this;
        }

        public Criteria andEnabledFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("enabled_flag between", value1, value2, "enabledFlag");
            return (Criteria) this;
        }

        public Criteria andEnabledFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("enabled_flag not between", value1, value2, "enabledFlag");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceAttributeIsNull() {
            addCriterion("source_reference_attribute is null");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceAttributeIsNotNull() {
            addCriterion("source_reference_attribute is not null");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceAttributeEqualTo(String value) {
            addCriterion("source_reference_attribute =", value, "sourceReferenceAttribute");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceAttributeNotEqualTo(String value) {
            addCriterion("source_reference_attribute <>", value, "sourceReferenceAttribute");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceAttributeGreaterThan(String value) {
            addCriterion("source_reference_attribute >", value, "sourceReferenceAttribute");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceAttributeGreaterThanOrEqualTo(String value) {
            addCriterion("source_reference_attribute >=", value, "sourceReferenceAttribute");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceAttributeLessThan(String value) {
            addCriterion("source_reference_attribute <", value, "sourceReferenceAttribute");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceAttributeLessThanOrEqualTo(String value) {
            addCriterion("source_reference_attribute <=", value, "sourceReferenceAttribute");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceAttributeLike(String value) {
            addCriterion("source_reference_attribute ilike", value, "sourceReferenceAttribute");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceAttributeNotLike(String value) {
            addCriterion("source_reference_attribute not ilike", value, "sourceReferenceAttribute");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceAttributeIn(List<String> values) {
            addCriterion("source_reference_attribute in", values, "sourceReferenceAttribute");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceAttributeNotIn(List<String> values) {
            addCriterion("source_reference_attribute not in", values, "sourceReferenceAttribute");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceAttributeBetween(String value1, String value2) {
            addCriterion("source_reference_attribute between", value1, value2, "sourceReferenceAttribute");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceAttributeNotBetween(String value1, String value2) {
            addCriterion("source_reference_attribute not between", value1, value2, "sourceReferenceAttribute");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceResolverIsNull() {
            addCriterion("source_reference_resolver is null");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceResolverIsNotNull() {
            addCriterion("source_reference_resolver is not null");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceResolverEqualTo(String value) {
            addCriterion("source_reference_resolver =", value, "sourceReferenceResolver");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceResolverNotEqualTo(String value) {
            addCriterion("source_reference_resolver <>", value, "sourceReferenceResolver");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceResolverGreaterThan(String value) {
            addCriterion("source_reference_resolver >", value, "sourceReferenceResolver");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceResolverGreaterThanOrEqualTo(String value) {
            addCriterion("source_reference_resolver >=", value, "sourceReferenceResolver");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceResolverLessThan(String value) {
            addCriterion("source_reference_resolver <", value, "sourceReferenceResolver");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceResolverLessThanOrEqualTo(String value) {
            addCriterion("source_reference_resolver <=", value, "sourceReferenceResolver");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceResolverLike(String value) {
            addCriterion("source_reference_resolver ilike", value, "sourceReferenceResolver");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceResolverNotLike(String value) {
            addCriterion("source_reference_resolver not ilike", value, "sourceReferenceResolver");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceResolverIn(List<String> values) {
            addCriterion("source_reference_resolver in", values, "sourceReferenceResolver");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceResolverNotIn(List<String> values) {
            addCriterion("source_reference_resolver not in", values, "sourceReferenceResolver");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceResolverBetween(String value1, String value2) {
            addCriterion("source_reference_resolver between", value1, value2, "sourceReferenceResolver");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceResolverNotBetween(String value1, String value2) {
            addCriterion("source_reference_resolver not between", value1, value2, "sourceReferenceResolver");
            return (Criteria) this;
        }

        public Criteria andMarkdownEquivalentIsNull() {
            addCriterion("markdown_equivalent is null");
            return (Criteria) this;
        }

        public Criteria andMarkdownEquivalentIsNotNull() {
            addCriterion("markdown_equivalent is not null");
            return (Criteria) this;
        }

        public Criteria andMarkdownEquivalentEqualTo(String value) {
            addCriterion("markdown_equivalent =", value, "markdownEquivalent");
            return (Criteria) this;
        }

        public Criteria andMarkdownEquivalentNotEqualTo(String value) {
            addCriterion("markdown_equivalent <>", value, "markdownEquivalent");
            return (Criteria) this;
        }

        public Criteria andMarkdownEquivalentGreaterThan(String value) {
            addCriterion("markdown_equivalent >", value, "markdownEquivalent");
            return (Criteria) this;
        }

        public Criteria andMarkdownEquivalentGreaterThanOrEqualTo(String value) {
            addCriterion("markdown_equivalent >=", value, "markdownEquivalent");
            return (Criteria) this;
        }

        public Criteria andMarkdownEquivalentLessThan(String value) {
            addCriterion("markdown_equivalent <", value, "markdownEquivalent");
            return (Criteria) this;
        }

        public Criteria andMarkdownEquivalentLessThanOrEqualTo(String value) {
            addCriterion("markdown_equivalent <=", value, "markdownEquivalent");
            return (Criteria) this;
        }

        public Criteria andMarkdownEquivalentLike(String value) {
            addCriterion("markdown_equivalent ilike", value, "markdownEquivalent");
            return (Criteria) this;
        }

        public Criteria andMarkdownEquivalentNotLike(String value) {
            addCriterion("markdown_equivalent not ilike", value, "markdownEquivalent");
            return (Criteria) this;
        }

        public Criteria andMarkdownEquivalentIn(List<String> values) {
            addCriterion("markdown_equivalent in", values, "markdownEquivalent");
            return (Criteria) this;
        }

        public Criteria andMarkdownEquivalentNotIn(List<String> values) {
            addCriterion("markdown_equivalent not in", values, "markdownEquivalent");
            return (Criteria) this;
        }

        public Criteria andMarkdownEquivalentBetween(String value1, String value2) {
            addCriterion("markdown_equivalent between", value1, value2, "markdownEquivalent");
            return (Criteria) this;
        }

        public Criteria andMarkdownEquivalentNotBetween(String value1, String value2) {
            addCriterion("markdown_equivalent not between", value1, value2, "markdownEquivalent");
            return (Criteria) this;
        }

        public Criteria andMarkdownCanonicalFlagIsNull() {
            addCriterion("markdown_canonical_flag is null");
            return (Criteria) this;
        }

        public Criteria andMarkdownCanonicalFlagIsNotNull() {
            addCriterion("markdown_canonical_flag is not null");
            return (Criteria) this;
        }

        public Criteria andMarkdownCanonicalFlagEqualTo(Boolean value) {
            addCriterion("markdown_canonical_flag =", value, "markdownCanonicalFlag");
            return (Criteria) this;
        }

        public Criteria andMarkdownCanonicalFlagNotEqualTo(Boolean value) {
            addCriterion("markdown_canonical_flag <>", value, "markdownCanonicalFlag");
            return (Criteria) this;
        }

        public Criteria andMarkdownCanonicalFlagGreaterThan(Boolean value) {
            addCriterion("markdown_canonical_flag >", value, "markdownCanonicalFlag");
            return (Criteria) this;
        }

        public Criteria andMarkdownCanonicalFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("markdown_canonical_flag >=", value, "markdownCanonicalFlag");
            return (Criteria) this;
        }

        public Criteria andMarkdownCanonicalFlagLessThan(Boolean value) {
            addCriterion("markdown_canonical_flag <", value, "markdownCanonicalFlag");
            return (Criteria) this;
        }

        public Criteria andMarkdownCanonicalFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("markdown_canonical_flag <=", value, "markdownCanonicalFlag");
            return (Criteria) this;
        }

        public Criteria andMarkdownCanonicalFlagIn(List<Boolean> values) {
            addCriterion("markdown_canonical_flag in", values, "markdownCanonicalFlag");
            return (Criteria) this;
        }

        public Criteria andMarkdownCanonicalFlagNotIn(List<Boolean> values) {
            addCriterion("markdown_canonical_flag not in", values, "markdownCanonicalFlag");
            return (Criteria) this;
        }

        public Criteria andMarkdownCanonicalFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("markdown_canonical_flag between", value1, value2, "markdownCanonicalFlag");
            return (Criteria) this;
        }

        public Criteria andMarkdownCanonicalFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("markdown_canonical_flag not between", value1, value2, "markdownCanonicalFlag");
            return (Criteria) this;
        }

        public Criteria andImplicitItemMarkerIsNull() {
            addCriterion("implicit_item_marker is null");
            return (Criteria) this;
        }

        public Criteria andImplicitItemMarkerIsNotNull() {
            addCriterion("implicit_item_marker is not null");
            return (Criteria) this;
        }

        public Criteria andImplicitItemMarkerEqualTo(String value) {
            addCriterion("implicit_item_marker =", value, "implicitItemMarker");
            return (Criteria) this;
        }

        public Criteria andImplicitItemMarkerNotEqualTo(String value) {
            addCriterion("implicit_item_marker <>", value, "implicitItemMarker");
            return (Criteria) this;
        }

        public Criteria andImplicitItemMarkerGreaterThan(String value) {
            addCriterion("implicit_item_marker >", value, "implicitItemMarker");
            return (Criteria) this;
        }

        public Criteria andImplicitItemMarkerGreaterThanOrEqualTo(String value) {
            addCriterion("implicit_item_marker >=", value, "implicitItemMarker");
            return (Criteria) this;
        }

        public Criteria andImplicitItemMarkerLessThan(String value) {
            addCriterion("implicit_item_marker <", value, "implicitItemMarker");
            return (Criteria) this;
        }

        public Criteria andImplicitItemMarkerLessThanOrEqualTo(String value) {
            addCriterion("implicit_item_marker <=", value, "implicitItemMarker");
            return (Criteria) this;
        }

        public Criteria andImplicitItemMarkerLike(String value) {
            addCriterion("implicit_item_marker ilike", value, "implicitItemMarker");
            return (Criteria) this;
        }

        public Criteria andImplicitItemMarkerNotLike(String value) {
            addCriterion("implicit_item_marker not ilike", value, "implicitItemMarker");
            return (Criteria) this;
        }

        public Criteria andImplicitItemMarkerIn(List<String> values) {
            addCriterion("implicit_item_marker in", values, "implicitItemMarker");
            return (Criteria) this;
        }

        public Criteria andImplicitItemMarkerNotIn(List<String> values) {
            addCriterion("implicit_item_marker not in", values, "implicitItemMarker");
            return (Criteria) this;
        }

        public Criteria andImplicitItemMarkerBetween(String value1, String value2) {
            addCriterion("implicit_item_marker between", value1, value2, "implicitItemMarker");
            return (Criteria) this;
        }

        public Criteria andImplicitItemMarkerNotBetween(String value1, String value2) {
            addCriterion("implicit_item_marker not between", value1, value2, "implicitItemMarker");
            return (Criteria) this;
        }

        public Criteria andImplicitItemCodeIsNull() {
            addCriterion("implicit_item_code is null");
            return (Criteria) this;
        }

        public Criteria andImplicitItemCodeIsNotNull() {
            addCriterion("implicit_item_code is not null");
            return (Criteria) this;
        }

        public Criteria andImplicitItemCodeEqualTo(String value) {
            addCriterion("implicit_item_code =", value, "implicitItemCode");
            return (Criteria) this;
        }

        public Criteria andImplicitItemCodeNotEqualTo(String value) {
            addCriterion("implicit_item_code <>", value, "implicitItemCode");
            return (Criteria) this;
        }

        public Criteria andImplicitItemCodeGreaterThan(String value) {
            addCriterion("implicit_item_code >", value, "implicitItemCode");
            return (Criteria) this;
        }

        public Criteria andImplicitItemCodeGreaterThanOrEqualTo(String value) {
            addCriterion("implicit_item_code >=", value, "implicitItemCode");
            return (Criteria) this;
        }

        public Criteria andImplicitItemCodeLessThan(String value) {
            addCriterion("implicit_item_code <", value, "implicitItemCode");
            return (Criteria) this;
        }

        public Criteria andImplicitItemCodeLessThanOrEqualTo(String value) {
            addCriterion("implicit_item_code <=", value, "implicitItemCode");
            return (Criteria) this;
        }

        public Criteria andImplicitItemCodeLike(String value) {
            addCriterion("implicit_item_code ilike", value, "implicitItemCode");
            return (Criteria) this;
        }

        public Criteria andImplicitItemCodeNotLike(String value) {
            addCriterion("implicit_item_code not ilike", value, "implicitItemCode");
            return (Criteria) this;
        }

        public Criteria andImplicitItemCodeIn(List<String> values) {
            addCriterion("implicit_item_code in", values, "implicitItemCode");
            return (Criteria) this;
        }

        public Criteria andImplicitItemCodeNotIn(List<String> values) {
            addCriterion("implicit_item_code not in", values, "implicitItemCode");
            return (Criteria) this;
        }

        public Criteria andImplicitItemCodeBetween(String value1, String value2) {
            addCriterion("implicit_item_code between", value1, value2, "implicitItemCode");
            return (Criteria) this;
        }

        public Criteria andImplicitItemCodeNotBetween(String value1, String value2) {
            addCriterion("implicit_item_code not between", value1, value2, "implicitItemCode");
            return (Criteria) this;
        }

        public Criteria andHonouredInForumFlagIsNull() {
            addCriterion("honoured_in_forum_flag is null");
            return (Criteria) this;
        }

        public Criteria andHonouredInForumFlagIsNotNull() {
            addCriterion("honoured_in_forum_flag is not null");
            return (Criteria) this;
        }

        public Criteria andHonouredInForumFlagEqualTo(Boolean value) {
            addCriterion("honoured_in_forum_flag =", value, "honouredInForumFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInForumFlagNotEqualTo(Boolean value) {
            addCriterion("honoured_in_forum_flag <>", value, "honouredInForumFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInForumFlagGreaterThan(Boolean value) {
            addCriterion("honoured_in_forum_flag >", value, "honouredInForumFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInForumFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("honoured_in_forum_flag >=", value, "honouredInForumFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInForumFlagLessThan(Boolean value) {
            addCriterion("honoured_in_forum_flag <", value, "honouredInForumFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInForumFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("honoured_in_forum_flag <=", value, "honouredInForumFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInForumFlagIn(List<Boolean> values) {
            addCriterion("honoured_in_forum_flag in", values, "honouredInForumFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInForumFlagNotIn(List<Boolean> values) {
            addCriterion("honoured_in_forum_flag not in", values, "honouredInForumFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInForumFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("honoured_in_forum_flag between", value1, value2, "honouredInForumFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInForumFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("honoured_in_forum_flag not between", value1, value2, "honouredInForumFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInWikiFlagIsNull() {
            addCriterion("honoured_in_wiki_flag is null");
            return (Criteria) this;
        }

        public Criteria andHonouredInWikiFlagIsNotNull() {
            addCriterion("honoured_in_wiki_flag is not null");
            return (Criteria) this;
        }

        public Criteria andHonouredInWikiFlagEqualTo(Boolean value) {
            addCriterion("honoured_in_wiki_flag =", value, "honouredInWikiFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInWikiFlagNotEqualTo(Boolean value) {
            addCriterion("honoured_in_wiki_flag <>", value, "honouredInWikiFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInWikiFlagGreaterThan(Boolean value) {
            addCriterion("honoured_in_wiki_flag >", value, "honouredInWikiFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInWikiFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("honoured_in_wiki_flag >=", value, "honouredInWikiFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInWikiFlagLessThan(Boolean value) {
            addCriterion("honoured_in_wiki_flag <", value, "honouredInWikiFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInWikiFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("honoured_in_wiki_flag <=", value, "honouredInWikiFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInWikiFlagIn(List<Boolean> values) {
            addCriterion("honoured_in_wiki_flag in", values, "honouredInWikiFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInWikiFlagNotIn(List<Boolean> values) {
            addCriterion("honoured_in_wiki_flag not in", values, "honouredInWikiFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInWikiFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("honoured_in_wiki_flag between", value1, value2, "honouredInWikiFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInWikiFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("honoured_in_wiki_flag not between", value1, value2, "honouredInWikiFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInProjectFlagIsNull() {
            addCriterion("honoured_in_project_flag is null");
            return (Criteria) this;
        }

        public Criteria andHonouredInProjectFlagIsNotNull() {
            addCriterion("honoured_in_project_flag is not null");
            return (Criteria) this;
        }

        public Criteria andHonouredInProjectFlagEqualTo(Boolean value) {
            addCriterion("honoured_in_project_flag =", value, "honouredInProjectFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInProjectFlagNotEqualTo(Boolean value) {
            addCriterion("honoured_in_project_flag <>", value, "honouredInProjectFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInProjectFlagGreaterThan(Boolean value) {
            addCriterion("honoured_in_project_flag >", value, "honouredInProjectFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInProjectFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("honoured_in_project_flag >=", value, "honouredInProjectFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInProjectFlagLessThan(Boolean value) {
            addCriterion("honoured_in_project_flag <", value, "honouredInProjectFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInProjectFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("honoured_in_project_flag <=", value, "honouredInProjectFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInProjectFlagIn(List<Boolean> values) {
            addCriterion("honoured_in_project_flag in", values, "honouredInProjectFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInProjectFlagNotIn(List<Boolean> values) {
            addCriterion("honoured_in_project_flag not in", values, "honouredInProjectFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInProjectFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("honoured_in_project_flag between", value1, value2, "honouredInProjectFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInProjectFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("honoured_in_project_flag not between", value1, value2, "honouredInProjectFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInResourceFlagIsNull() {
            addCriterion("honoured_in_resource_flag is null");
            return (Criteria) this;
        }

        public Criteria andHonouredInResourceFlagIsNotNull() {
            addCriterion("honoured_in_resource_flag is not null");
            return (Criteria) this;
        }

        public Criteria andHonouredInResourceFlagEqualTo(Boolean value) {
            addCriterion("honoured_in_resource_flag =", value, "honouredInResourceFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInResourceFlagNotEqualTo(Boolean value) {
            addCriterion("honoured_in_resource_flag <>", value, "honouredInResourceFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInResourceFlagGreaterThan(Boolean value) {
            addCriterion("honoured_in_resource_flag >", value, "honouredInResourceFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInResourceFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("honoured_in_resource_flag >=", value, "honouredInResourceFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInResourceFlagLessThan(Boolean value) {
            addCriterion("honoured_in_resource_flag <", value, "honouredInResourceFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInResourceFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("honoured_in_resource_flag <=", value, "honouredInResourceFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInResourceFlagIn(List<Boolean> values) {
            addCriterion("honoured_in_resource_flag in", values, "honouredInResourceFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInResourceFlagNotIn(List<Boolean> values) {
            addCriterion("honoured_in_resource_flag not in", values, "honouredInResourceFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInResourceFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("honoured_in_resource_flag between", value1, value2, "honouredInResourceFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInResourceFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("honoured_in_resource_flag not between", value1, value2, "honouredInResourceFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInSignatureFlagIsNull() {
            addCriterion("honoured_in_signature_flag is null");
            return (Criteria) this;
        }

        public Criteria andHonouredInSignatureFlagIsNotNull() {
            addCriterion("honoured_in_signature_flag is not null");
            return (Criteria) this;
        }

        public Criteria andHonouredInSignatureFlagEqualTo(Boolean value) {
            addCriterion("honoured_in_signature_flag =", value, "honouredInSignatureFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInSignatureFlagNotEqualTo(Boolean value) {
            addCriterion("honoured_in_signature_flag <>", value, "honouredInSignatureFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInSignatureFlagGreaterThan(Boolean value) {
            addCriterion("honoured_in_signature_flag >", value, "honouredInSignatureFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInSignatureFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("honoured_in_signature_flag >=", value, "honouredInSignatureFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInSignatureFlagLessThan(Boolean value) {
            addCriterion("honoured_in_signature_flag <", value, "honouredInSignatureFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInSignatureFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("honoured_in_signature_flag <=", value, "honouredInSignatureFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInSignatureFlagIn(List<Boolean> values) {
            addCriterion("honoured_in_signature_flag in", values, "honouredInSignatureFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInSignatureFlagNotIn(List<Boolean> values) {
            addCriterion("honoured_in_signature_flag not in", values, "honouredInSignatureFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInSignatureFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("honoured_in_signature_flag between", value1, value2, "honouredInSignatureFlag");
            return (Criteria) this;
        }

        public Criteria andHonouredInSignatureFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("honoured_in_signature_flag not between", value1, value2, "honouredInSignatureFlag");
            return (Criteria) this;
        }

        public Criteria andCodeContains(String value) {
            addCriterion("code ilike", LikePatterns.contains(value), "code");
            return (Criteria) this;
        }

        public Criteria andEndTagContains(String value) {
            addCriterion("end_tag ilike", LikePatterns.contains(value), "endTag");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceAttributeContains(String value) {
            addCriterion("source_reference_attribute ilike", LikePatterns.contains(value), "sourceReferenceAttribute");
            return (Criteria) this;
        }

        public Criteria andSourceReferenceResolverContains(String value) {
            addCriterion("source_reference_resolver ilike", LikePatterns.contains(value), "sourceReferenceResolver");
            return (Criteria) this;
        }

        public Criteria andMarkdownEquivalentContains(String value) {
            addCriterion("markdown_equivalent ilike", LikePatterns.contains(value), "markdownEquivalent");
            return (Criteria) this;
        }

        public Criteria andImplicitItemMarkerContains(String value) {
            addCriterion("implicit_item_marker ilike", LikePatterns.contains(value), "implicitItemMarker");
            return (Criteria) this;
        }

        public Criteria andImplicitItemCodeContains(String value) {
            addCriterion("implicit_item_code ilike", LikePatterns.contains(value), "implicitItemCode");
            return (Criteria) this;
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="do_not_delete_during_merge")
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.bb_code_config")
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