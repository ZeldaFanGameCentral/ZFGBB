package com.zfgc.zfgbb.dbo;

import jakarta.annotation.Generated;
import java.time.OffsetDateTime;

public class AttributeDataTypeDbo extends AbstractDbo {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.code")
    private String code;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.label")
    private String label;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.ordinal")
    private Integer ordinal;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.validation_pattern")
    private String validationPattern;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.fallback_value")
    private String fallbackValue;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.value_admits_whitespace")
    private Boolean valueAdmitsWhitespace;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.lowercases_value")
    private Boolean lowercasesValue;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.bare_integer_unit")
    private String bareIntegerUnit;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.allowed_values")
    private String allowedValues;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.code")
    public String getCode() {
        return code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.code")
    public void setCode(String code) {
        this.code = code;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.label")
    public String getLabel() {
        return label;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.label")
    public void setLabel(String label) {
        this.label = label;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.ordinal")
    public Integer getOrdinal() {
        return ordinal;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.ordinal")
    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.validation_pattern")
    public String getValidationPattern() {
        return validationPattern;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.validation_pattern")
    public void setValidationPattern(String validationPattern) {
        this.validationPattern = validationPattern;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.fallback_value")
    public String getFallbackValue() {
        return fallbackValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.fallback_value")
    public void setFallbackValue(String fallbackValue) {
        this.fallbackValue = fallbackValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.value_admits_whitespace")
    public Boolean getValueAdmitsWhitespace() {
        return valueAdmitsWhitespace;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.value_admits_whitespace")
    public void setValueAdmitsWhitespace(Boolean valueAdmitsWhitespace) {
        this.valueAdmitsWhitespace = valueAdmitsWhitespace;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.lowercases_value")
    public Boolean getLowercasesValue() {
        return lowercasesValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.lowercases_value")
    public void setLowercasesValue(Boolean lowercasesValue) {
        this.lowercasesValue = lowercasesValue;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.bare_integer_unit")
    public String getBareIntegerUnit() {
        return bareIntegerUnit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.bare_integer_unit")
    public void setBareIntegerUnit(String bareIntegerUnit) {
        this.bareIntegerUnit = bareIntegerUnit;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.allowed_values")
    public String getAllowedValues() {
        return allowedValues;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: zfgbb.attribute_data_type.allowed_values")
    public void setAllowedValues(String allowedValues) {
        this.allowedValues = allowedValues;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    public Integer getPkId() {
        return null;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    public OffsetDateTime getCreatedTime() {
        return null;
    }

    @Override
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: zfgbb.attribute_data_type")
    public OffsetDateTime getUpdatedTime() {
        return null;
    }
}