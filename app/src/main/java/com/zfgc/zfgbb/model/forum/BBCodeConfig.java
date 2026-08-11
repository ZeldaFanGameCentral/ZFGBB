package com.zfgc.zfgbb.model.forum;

import java.util.ArrayList;
import com.zfgc.zfgbb.content.ContentScope;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import com.zfgc.zfgbb.model.BaseModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BBCodeConfig extends BaseModel {

    public static final String NAMELESS_ATTRIBUTE_NAME = "=";

    public static Set<String> codesTooStructuralToScope(Map<String, BBCodeConfig> declared) {
        Set<String> tooStructural = new TreeSet<>();
        for (Map.Entry<String, BBCodeConfig> declaredCode : declared.entrySet()) {
            BBCodeConfig config = declaredCode.getValue();
            if (config.suppressesTheParsingOfItsOwnBody())
                tooStructural.add(declaredCode.getKey());
            config.declaredImplicitItemCode().ifPresent(itemCode -> {
                tooStructural.add(declaredCode.getKey());
                tooStructural.add(itemCode.toUpperCase(Locale.ROOT));
            });
        }
        return Set.copyOf(tooStructural);
    }

    private boolean suppressesTheParsingOfItsOwnBody() {
        if (!Boolean.FALSE.equals(getProcessContentFlag()) || Boolean.TRUE.equals(getSelfClosingFlag()))
            return false;
        return getAttributeConfig().values().stream()
                .anyMatch(mode -> !Boolean.TRUE.equals(mode.getContentIsAttributeFlag()));
    }

    public record ParsedAttributes(String attFormat, Map<String, String> attributeValues,
            List<String> namelessValues) {

        public static ParsedAttributes noneWereWritten() {
            return new ParsedAttributes("", Map.of(), List.of());
        }

        public Optional<String> valueOf(String attributeName) {
            return Optional.ofNullable(attributeValues.get(attributeName));
        }

        public Optional<String> valueOfTheAttributeDeclaredBy(BBCodeAttributeMode mode, String attributeName) {
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
    private Boolean honouredInForumFlag = true;
    private Boolean honouredInWikiFlag = true;
    private Boolean honouredInProjectFlag = true;
    private Boolean honouredInResourceFlag = true;
    private Boolean honouredInSignatureFlag = true;

    public boolean isHonouredOn(ContentScope surface) {
        if (surface == null)
            return true;
        return switch (surface) {
            case FORUM -> !Boolean.FALSE.equals(honouredInForumFlag);
            case WIKI -> !Boolean.FALSE.equals(honouredInWikiFlag);
            case PROJECT -> !Boolean.FALSE.equals(honouredInProjectFlag);
            case RESOURCE -> !Boolean.FALSE.equals(honouredInResourceFlag);
            case SIGNATURE -> !Boolean.FALSE.equals(honouredInSignatureFlag);
            case ALL -> true;
        };
    }

    private Map<String,BBCodeAttributeMode> attributeConfig = new HashMap<>();
    private Map<String,AttributeValuePolicy> valuePolicyByAttributeName = new HashMap<>();

    public boolean referencesSourceContent() {
        return sourceReferenceAttribute != null && sourceReferenceResolver != null;
    }

    public String sourceReferenceSlotName() {
        return sourceReferenceAttribute.endsWith(NAMELESS_ATTRIBUTE_NAME)
                ? sourceReferenceAttribute.substring(0,
                        sourceReferenceAttribute.length() - NAMELESS_ATTRIBUTE_NAME.length())
                : sourceReferenceAttribute;
    }

    public Optional<MarkdownEquivalent> declaredMarkdownEquivalent() {
        return MarkdownEquivalent.forCode(markdownEquivalent);
    }

    public boolean isCanonicalForItsMarkdownEquivalent() {
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
