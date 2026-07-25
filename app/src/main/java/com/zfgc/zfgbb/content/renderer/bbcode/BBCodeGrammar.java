package com.zfgc.zfgbb.content.renderer.bbcode;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.samskivert.mustache.Template;
import com.zfgc.zfgbb.model.forum.AttributeDataType;
import com.zfgc.zfgbb.model.forum.AttributeValuePolicy;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;
import com.zfgc.zfgbb.model.forum.MarkdownEquivalent;

public record BBCodeGrammar(Map<String, BBCodeConfig> configs,
		Map<String, Boolean> listStyleTypesThatNumberTheirItems, AttributeValuePolicy listStyleValuePolicy,
		List<ImplicitItemExpansion> implicitItemExpansions,
		PreparedSourceReferences sourceReferences,
		PreparedCustomProperties customProperties) {

	public record ImplicitItemExpansion(String containerCode, String marker, String itemCode) {

		public static final int A_MARKER_IS_AN_OPENER_AN_INNER_CHARACTER_AND_A_CLOSER = 3;
	}

	public record PreparedSourceReferences(Map<String, BBCodeConfig> grammar,
			Map<String, Template> theMarkupEverySourceReferencingModeDeclares) {}

	public static final PreparedSourceReferences NO_SOURCE_REFERENCE_IS_DECLARED =
			new PreparedSourceReferences(Map.of(), Map.of());

	public record CustomPropertyBinding(AttributeDataType dataType, AttributeValuePolicy valuePolicy) {}

	public record PreparedCustomProperties(Map<String, CustomPropertyBinding> theBindingOfEachCustomProperty) {}

	public static final PreparedCustomProperties NO_CUSTOM_PROPERTY_IS_DECLARED =
			new PreparedCustomProperties(Map.of());

	public static BBCodeGrammar theGrammarThatDeclaresNothing() {
		return new BBCodeGrammar(Map.of(), Map.of(), AttributeValuePolicy.rejectingEveryValue(""), List.of(),
				NO_SOURCE_REFERENCE_IS_DECLARED, NO_CUSTOM_PROPERTY_IS_DECLARED);
	}

	public Optional<BBCodeConfig> theCanonicalCodeFor(MarkdownEquivalent equivalent) {
		return theCanonicalCodeFor(equivalent, -1);
	}

	public Optional<BBCodeConfig> theCanonicalHeadingCodeForLevel(int headingLevel) {
		return theCanonicalCodeFor(MarkdownEquivalent.HEADING, headingLevel);
	}

	private Optional<BBCodeConfig> theCanonicalCodeFor(MarkdownEquivalent equivalent, int headingLevel) {
		for (BBCodeConfig config : configs.values())
			if (config.isTheCanonicalCodeForItsMarkdownEquivalent()
					&& config.declaredMarkdownEquivalent().filter(equivalent::equals).isPresent()
					&& (equivalent != MarkdownEquivalent.HEADING
							|| headingLevelDeclaredByTheMarkup(config) == headingLevel))
				return Optional.of(config);
		return Optional.empty();
	}

	public boolean listStyleTypeNumbersItsItems(String listStyleType) {
		return listStyleType != null && Boolean.TRUE.equals(listStyleTypesThatNumberTheirItems
				.get(listStyleValuePolicy.apply(listStyleType.trim())));
	}

	public static int headingLevelDeclaredByTheMarkup(BBCodeConfig config) {
		for (var mode : config.getAttributeConfig().values()) {
			int level = theHeadingLevelNamedIn(mode.getOpenTag());
			if (level == -1)
				level = theHeadingLevelNamedIn(mode.getCloseTag());
			if (level != -1)
				return level;
		}
		return theHeadingLevelNamedIn(config.getEndTag());
	}

	private static int theHeadingLevelNamedIn(String markup) {
		for (String elementName : ContentLevel.everyElementNamedIn(markup)) {
			if (elementName.length() != 2 || elementName.charAt(0) != 'h')
				continue;
			int level = Character.digit(elementName.charAt(1), 10);
			if (level >= 1 && level <= 6)
				return level;
		}
		return -1;
	}
}
