package com.zfgc.zfgbb.content.renderer.bbcode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.content.renderer.ContentOutputSanitizer;
import com.zfgc.zfgbb.content.renderer.RenderedTextEnricher;
import com.zfgc.zfgbb.content.renderer.SourceReferenceService;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammar.ImplicitItemExpansion;
import com.zfgc.zfgbb.dataprovider.forum.BBCodeDataProvider;
import com.zfgc.zfgbb.exception.InvalidBBCodeGrammarException;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.model.forum.AttributeDataType;
import com.zfgc.zfgbb.model.forum.AttributeValuePolicy;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;
import com.zfgc.zfgbb.model.forum.MarkdownEquivalent;

import jakarta.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class BBCodeGrammarLoader {

	private Logger LOGGER = LogManager.getLogger(BBCodeGrammarLoader.class);

	private final BBCodeDataProvider bbCodeDataProvider;

	private final SourceReferenceService sourceReferenceService;

	private final ContentOutputSanitizer outputSanitizer;

	private final BBCodeGrammarHolder grammarHolder;

	private final RenderedTextEnricher enricher;

	public BBCodeGrammarLoader(BBCodeDataProvider bbCodeDataProvider, SourceReferenceService sourceReferenceService,
			ContentOutputSanitizer outputSanitizer, BBCodeGrammarHolder grammarHolder,
			RenderedTextEnricher enricher) {
		this.bbCodeDataProvider = bbCodeDataProvider;
		this.sourceReferenceService = sourceReferenceService;
		this.outputSanitizer = outputSanitizer;
		this.grammarHolder = grammarHolder;
		this.enricher = enricher;
	}

	@PostConstruct
	public void loadBBCodeConfig() {
		LOGGER.info("Loading BBCode config...");

		grammarHolder.publish(theGrammarPreparedFromTheDatabase());

		LOGGER.info("Finished loading BBCode config.");
	}

	public List<BBCodeDataProvider.BBCodeToggle> listBBCodes() {
		return bbCodeDataProvider.getBBCodeToggles();
	}

	public Collection<BBCodeConfig> theDeclaredConfigs() {
		return grammarHolder.current().configs().values();
	}

	public void reloadFromTheDatabase() {
		loadBBCodeConfig();
		enricher.loadSmilies();
	}

	@Transactional
	public BBCodeDataProvider.BBCodeToggle setBBCodeEnabled(String code, boolean enabled) {
		BBCodeDataProvider.BBCodeToggle toggled = bbCodeDataProvider.setBBCodeEnabled(code, enabled)
				.orElseThrow(ZfgcNotFoundException::new);
		grammarHolder.publish(theGrammarPreparedFromTheDatabase());
		return toggled;
	}

	private BBCodeGrammar theGrammarPreparedFromTheDatabase() {
		return theGrammarPreparedFrom(bbCodeDataProvider.getBBCodeConfig(),
				bbCodeDataProvider.theDeclaredListStyleTypes(),
				bbCodeDataProvider.compileTheDeclaredValuePolicies()
						.getOrDefault(AttributeDataType.LIST_TYPE, AttributeValuePolicy.rejectingEveryValue("")));
	}

	private BBCodeGrammar theGrammarPreparedFrom(Map<String, BBCodeConfig> configs,
			Map<String, Boolean> listStyleTypesThatNumberTheirItems, AttributeValuePolicy listStyleValuePolicy) {
		requireEveryCodeToDeclareOneContentLevel(configs);
		requireOneCanonicalCodePerMarkdownEquivalent(configs);
		requireEveryMarkdownBindingToMatchItsContentLevel(configs);
		return new BBCodeGrammar(configs, listStyleTypesThatNumberTheirItems, listStyleValuePolicy,
				implicitItemExpansionsDeclaredBy(configs),
				sourceReferenceService.theSourceReferencesPreparedFrom(configs),
				outputSanitizer.theCustomPropertiesPreparedFrom(configs));
	}

	static List<ImplicitItemExpansion> implicitItemExpansionsDeclaredBy(Map<String, BBCodeConfig> grammar) {
		List<ImplicitItemExpansion> declared = new ArrayList<>();
		for (BBCodeConfig config : grammar.values())
			config.declaredImplicitItemMarker().ifPresent(marker -> declared.add(new ImplicitItemExpansion(
					config.getCode(), requireTheDeclaredMarkerShape(config, marker),
					config.declaredImplicitItemCode().orElseThrow(
							() -> InvalidBBCodeGrammarException.implicitMarkerHasNoItemCode(config, marker)))));
		declared.sort(Comparator.comparing(ImplicitItemExpansion::containerCode));
		return List.copyOf(declared);
	}

	private static String requireTheDeclaredMarkerShape(BBCodeConfig config, String marker) {
		if (marker.length() != ImplicitItemExpansion.A_MARKER_IS_AN_OPENER_AN_INNER_CHARACTER_AND_A_CLOSER)
			throw InvalidBBCodeGrammarException.implicitMarkerOfTheWrongShape(config, marker,
					ImplicitItemExpansion.A_MARKER_IS_AN_OPENER_AN_INNER_CHARACTER_AND_A_CLOSER);
		return marker;
	}

	static void requireEveryCodeToDeclareOneContentLevel(Map<String, BBCodeConfig> grammar) {
		Map<String, Set<ContentLevel>> mixed = new TreeMap<>();
		for (Map.Entry<String, BBCodeConfig> entry : grammar.entrySet()) {
			Set<ContentLevel> declared = ContentLevel.everyContentLevelDeclaredBy(entry.getValue());
			if (declared.size() > 1)
				mixed.put(entry.getKey(), declared);
		}
		if (!mixed.isEmpty())
			throw InvalidBBCodeGrammarException.codeDeclaresBothContentLevels(mixed);
	}

	private static String markdownBindingSlotOf(BBCodeConfig config, MarkdownEquivalent equivalent) {
		return equivalent == MarkdownEquivalent.HEADING
				? equivalent.name() + " level " + BBCodeGrammar.headingLevelDeclaredByTheMarkup(config)
				: equivalent.name();
	}

	static void requireOneCanonicalCodePerMarkdownEquivalent(Map<String, BBCodeConfig> grammar) {
		Map<String, List<String>> canonicalCodesBySlot = new TreeMap<>();
		Map<String, List<String>> everyCodeBySlot = new TreeMap<>();
		for (BBCodeConfig config : grammar.values()) {
			Optional<MarkdownEquivalent> equivalent = config.declaredMarkdownEquivalent();
			if (equivalent.isEmpty())
				continue;
			String slot = markdownBindingSlotOf(config, equivalent.get());
			everyCodeBySlot.computeIfAbsent(slot, key -> new ArrayList<>()).add(config.getCode());
			if (config.isTheCanonicalCodeForItsMarkdownEquivalent())
				canonicalCodesBySlot.computeIfAbsent(slot, key -> new ArrayList<>()).add(config.getCode());
		}
		for (Map.Entry<String, List<String>> slot : everyCodeBySlot.entrySet()) {
			List<String> canonical = canonicalCodesBySlot.getOrDefault(slot.getKey(), List.of());
			if (canonical.isEmpty())
				throw InvalidBBCodeGrammarException.equivalentHasNoCanonicalCode(slot.getKey(), slot.getValue());
			if (canonical.size() > 1)
				throw InvalidBBCodeGrammarException.equivalentHasTwoCanonicalCodes(slot.getKey(), canonical);
		}
	}

	static void requireEveryMarkdownBindingToMatchItsContentLevel(Map<String, BBCodeConfig> grammar) {
		Map<String, String> mismatched = new TreeMap<>();
		for (BBCodeConfig config : grammar.values()) {
			Optional<MarkdownEquivalent> equivalent = config.declaredMarkdownEquivalent();
			if (equivalent.isEmpty())
				continue;
			ContentLevel required = equivalent.get().markdownReadsItInsideAParagraph()
					? ContentLevel.INLINE
					: ContentLevel.BLOCK;
			Set<ContentLevel> declared = ContentLevel.everyContentLevelDeclaredBy(config);
			if (!declared.equals(Set.of(required)))
				mismatched.put(config.getCode(), equivalent.get().name() + " needs " + required + " but the code "
						+ "opens " + declared);
		}
		if (!mismatched.isEmpty())
			throw InvalidBBCodeGrammarException.codeBoundAcrossContentLevels(mismatched);
	}

}
