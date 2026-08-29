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

		public static final int MARKER_LENGTH = 3;
	}

	public record PreparedSourceReferences(Map<String, BBCodeConfig> grammar,
			Map<String, Template> markupEverySourceReferencingModeDeclares) {}

	public static final PreparedSourceReferences NO_SOURCE_REFERENCE_IS_DECLARED =
			new PreparedSourceReferences(Map.of(), Map.of());

	public record CustomPropertyBinding(AttributeDataType dataType, AttributeValuePolicy valuePolicy) {}

	public record PreparedCustomProperties(Map<String, CustomPropertyBinding> bindingByPropertyName) {}

	public static final PreparedCustomProperties NO_CUSTOM_PROPERTY_IS_DECLARED =
			new PreparedCustomProperties(Map.of());

	public static BBCodeGrammar theGrammarThatDeclaresNothing() {
		return new BBCodeGrammar(Map.of(), Map.of(), AttributeValuePolicy.rejectingEveryValue(""), List.of(),
				NO_SOURCE_REFERENCE_IS_DECLARED, NO_CUSTOM_PROPERTY_IS_DECLARED);
	}

	public Optional<BBCodeConfig> canonicalCodeFor(MarkdownEquivalent equivalent) {
		return canonicalCodeFor(equivalent, -1);
	}

	public Optional<BBCodeConfig> canonicalHeadingCodeForLevel(int headingLevel) {
		return canonicalCodeFor(MarkdownEquivalent.HEADING, headingLevel);
	}

	private Optional<BBCodeConfig> canonicalCodeFor(MarkdownEquivalent equivalent, int headingLevel) {
		for (BBCodeConfig config : configs.values())
			if (config.isCanonicalForItsMarkdownEquivalent()
					&& config.declaredMarkdownEquivalent().filter(equivalent::equals).isPresent()
					&& (equivalent != MarkdownEquivalent.HEADING
							|| headingLevelDeclaredByTheMarkup(config) == headingLevel))
				return Optional.of(config);
		return Optional.empty();
	}

	public boolean listStyleTypeNumbersItems(String listStyleType) {
		return Boolean.TRUE.equals(listStyleTypesThatNumberTheirItems
				.get(listStyleValuePolicy.apply(listStyleType.trim())));
	}

	public static int headingLevelDeclaredByTheMarkup(BBCodeConfig config) {
		for (var mode : config.getAttributeConfig().values()) {
			int level = headingLevelNamedIn(mode.getOpenTag());
			if (level == -1)
				level = headingLevelNamedIn(mode.getCloseTag());
			if (level != -1)
				return level;
		}
		return headingLevelNamedIn(config.getEndTag());
	}

	private static int headingLevelNamedIn(String markup) {
		for (String elementName : ContentLevel.elementNamesIn(markup)) {
			if (elementName.length() != 2 || elementName.charAt(0) != 'h')
				continue;
			int level = Character.digit(elementName.charAt(1), 10);
			if (level >= 1 && level <= 6)
				return level;
		}
		return -1;
	}
}
