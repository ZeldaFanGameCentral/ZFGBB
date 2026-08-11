package com.zfgc.zfgbb.exception;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zfgc.zfgbb.content.renderer.bbcode.ContentLevel;
import com.zfgc.zfgbb.model.forum.AttributeDataType;
import com.zfgc.zfgbb.model.forum.BBCodeAttribute;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;

public class InvalidBBCodeGrammarException extends RuntimeException {

	private InvalidBBCodeGrammarException(String message) {
		super(message);
	}

	public static InvalidBBCodeGrammarException codeDeclaresBothContentLevels(
			Map<String, Set<ContentLevel>> mixed) {
		return new InvalidBBCodeGrammarException("bb code declares both block and inline content levels: " + mixed);
	}

	public static InvalidBBCodeGrammarException equivalentHasNoCanonicalCode(String markdownSlot,
			List<String> codesBoundToIt) {
		return new InvalidBBCodeGrammarException("no markdown_canonical_flag among the codes bound to "
				+ markdownSlot + ": " + codesBoundToIt);
	}

	public static InvalidBBCodeGrammarException equivalentHasTwoCanonicalCodes(String markdownSlot,
			List<String> canonicalCodes) {
		return new InvalidBBCodeGrammarException("more than one markdown_canonical_flag bound to " + markdownSlot
				+ ": " + canonicalCodes);
	}

	public static InvalidBBCodeGrammarException codeBoundAcrossContentLevels(Map<String, String> mismatchedCodes) {
		return new InvalidBBCodeGrammarException("markdown binding sits at the wrong content level: "
				+ mismatchedCodes);
	}

	public static InvalidBBCodeGrammarException implicitMarkerHasNoItemCode(BBCodeConfig config, String marker) {
		return new InvalidBBCodeGrammarException(theRowNaming(config) + " declares implicit_item_marker '" + marker
				+ "' with no implicit_item_code");
	}

	public static InvalidBBCodeGrammarException implicitMarkerOfTheWrongShape(BBCodeConfig config, String marker,
			int theOnlyLengthAMarkerCanCarry) {
		return new InvalidBBCodeGrammarException(theRowNaming(config) + " declares implicit_item_marker '" + marker
				+ "' of length " + marker.length() + "; a marker carries length " + theOnlyLengthAMarkerCanCarry);
	}

	public static InvalidBBCodeGrammarException unknownSourceReferenceResolver(BBCodeConfig config,
			Collection<String> registeredResolvers) {
		return new InvalidBBCodeGrammarException(theRowNaming(config)
				+ " declares unknown source_reference_resolver '" + config.getSourceReferenceResolver()
				+ "'; registered resolvers are " + registeredResolvers);
	}

	public static InvalidBBCodeGrammarException moreThanOneSourceReferenceResolver(
			Collection<String> declaredResolvers) {
		return new InvalidBBCodeGrammarException("more than one source_reference_resolver declared: "
				+ declaredResolvers);
	}

	public static InvalidBBCodeGrammarException sourceReferenceDeclaredWithoutItsPair(BBCodeConfig config) {
		return new InvalidBBCodeGrammarException(theRowNaming(config) + " declares only one of "
				+ "source_reference_attribute and source_reference_resolver; it needs both");
	}

	public static InvalidBBCodeGrammarException unknownAttributeDataType(BBCodeAttribute attribute) {
		return new InvalidBBCodeGrammarException("bb_code_attribute " + attribute.getBbCodeAttributeId() + " ('"
				+ attribute.getName() + "' on attribute mode " + attribute.getBbCodeAttributeModeId()
				+ ") declares unknown attribute_data_type '" + attribute.getAttributeDataType()
				+ "'; known codes are " + AttributeDataType.knownCodes());
	}

	public static InvalidBBCodeGrammarException customPropertyFilledFromTwoDataTypes(String customProperty,
			AttributeDataType alreadyBound, BBCodeAttribute conflicting) {
		return new InvalidBBCodeGrammarException("custom property '" + customProperty + "' is filled from "
				+ alreadyBound + " and from " + conflicting.getDataType() + " (bb_code_attribute "
				+ conflicting.getBbCodeAttributeId() + " on attribute mode "
				+ conflicting.getBbCodeAttributeModeId() + ")");
	}

	private static String theRowNaming(BBCodeConfig config) {
		return "bb_code_config " + config.getBbCodeConfigId() + " ('" + config.getCode() + "')";
	}
}
