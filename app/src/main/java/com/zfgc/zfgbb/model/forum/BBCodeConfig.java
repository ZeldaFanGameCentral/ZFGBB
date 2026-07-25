package com.zfgc.zfgbb.model.forum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.zfgc.zfgbb.model.BaseModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BBCodeConfig extends BaseModel {

    public static final String NAMELESS_ATTRIBUTE_NAME = "=";

    public record ParsedAttributes(String attFormat, Map<String, String> attributeValues,
            List<String> namelessValues) {

        public static ParsedAttributes noneWereWritten() {
            return new ParsedAttributes("", Map.of(), List.of());
        }

        public Optional<String> valueOf(String attributeName) {
            return Optional.ofNullable(attributeValues.get(attributeName));
        }

        public Optional<String> theValueOfTheAttributeDeclaredBy(BBCodeAttributeMode mode, String attributeName) {
            for (BBCodeAttribute attribute : mode.getAttributes())
                if (attributeName.equals(attribute.getName()))
                    return valueOf(attributeName);
            return Optional.empty();
        }

        public String namelessValueAt(int index) {
            return index < namelessValues.size() ? namelessValues.get(index) : "";
        }

        public List<String> rawValuesInTheOrder(BBCodeAttributeMode mode) {
            long namelessSlotsInThisMode = mode.getAttributes().stream()
                    .filter(attribute -> NAMELESS_ATTRIBUTE_NAME.equals(attribute.getName())).count();
            int namelessSlotsFilled = 0;
            List<String> rawValues = new ArrayList<>();
            for (BBCodeAttribute attribute : mode.getAttributes())
                rawValues.add(NAMELESS_ATTRIBUTE_NAME.equals(attribute.getName()) && namelessSlotsInThisMode > 1
                        ? namelessValueAt(namelessSlotsFilled++)
                        : attributeValues.get(attribute.getName()));
            return rawValues;
        }
    }

	private Integer bbCodeConfigId;
    private String code;
    private String endTag;
    private Boolean processContentFlag;
    private Boolean selfClosingFlag = false;
    private String allAttributeNamesAsString;
    private String sourceReferenceAttribute;
    private String sourceReferenceResolver;
    private String markdownEquivalent;
    private Boolean markdownCanonicalFlag = false;
    private String implicitItemMarker;
    private String implicitItemCode;
    private Map<String,BBCodeAttributeMode> attributeConfig = new HashMap<>();
    private Map<String,AttributeValuePolicy> valuePolicyByAttributeName = new HashMap<>();

    public boolean referencesSourceContent() {
        return sourceReferenceAttribute != null && sourceReferenceResolver != null;
    }

    public String theNameItsSourceReferenceSlotsUse() {
        return sourceReferenceAttribute.endsWith(NAMELESS_ATTRIBUTE_NAME)
                ? sourceReferenceAttribute.substring(0,
                        sourceReferenceAttribute.length() - NAMELESS_ATTRIBUTE_NAME.length())
                : sourceReferenceAttribute;
    }

    public Optional<MarkdownEquivalent> declaredMarkdownEquivalent() {
        return MarkdownEquivalent.forCode(markdownEquivalent);
    }

    public boolean isTheCanonicalCodeForItsMarkdownEquivalent() {
        return Boolean.TRUE.equals(markdownCanonicalFlag);
    }

    public Optional<String> declaredImplicitItemMarker() {
        return Optional.ofNullable(implicitItemMarker).filter(marker -> !marker.isBlank());
    }

    public Optional<String> declaredImplicitItemCode() {
        return Optional.ofNullable(implicitItemCode).filter(itemCode -> !itemCode.isBlank());
    }

    public Optional<AttributeValuePolicy> valuePolicyOfTheAttributeNamed(String attributeName) {
        return Optional.ofNullable(valuePolicyByAttributeName.get(attributeName));
    }

	@Override
	public Integer getId() {
		return bbCodeConfigId;
	}

	@Override
	public void setId(Integer id) {
		bbCodeConfigId = id;
	}
}
