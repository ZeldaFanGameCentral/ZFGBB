package com.zfgc.zfgbb.testsupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;

public final class RenderedOutputComparison {

	public enum DivergenceFamily {
		BYTE_IDENTICAL,
		VERBATIM_LINE_BREAK_REWRITE,
		LITERAL_CONTENT_PLACEHOLDER_HIJACK,
		CONTENT_ATTRIBUTE_BODY_BRACKET_LEAK,
		VISIBLE_TEXT_DUPLICATED_INTO_ATTRIBUTE,
		LINE_BREAK_ONLY,
		WHITESPACE_ONLY,
		WHITESPACE_POSITION_ONLY,
		UNATTRIBUTED_VISIBLE_TEXT_CHANGE,
		ATTRIBUTE_VALUE_ONLY,
		ELEMENT_STRUCTURE_ONLY,
		ESCAPING_OR_ORDERING_ONLY
	}

	public static final String CONTENT_PLACEHOLDER = "{{c}}";

	private final Map<String, BBCodeConfig> grammar;
	private final String sourceMarkup;
	private final String expectedHtml;
	private final String actualHtml;

	private RenderedOutputComparison(Map<String, BBCodeConfig> grammar, String sourceMarkup,
			String expectedHtml, String actualHtml) {
		this.grammar = grammar;
		this.sourceMarkup = sourceMarkup;
		this.expectedHtml = expectedHtml;
		this.actualHtml = actualHtml;
	}

	public static RenderedOutputComparison of(Map<String, BBCodeConfig> grammar, String sourceMarkup,
			String expectedHtml, String actualHtml) {
		return new RenderedOutputComparison(grammar, sourceMarkup, expectedHtml, actualHtml);
	}

	public boolean byteIdentical() {
		return expectedHtml.equals(actualHtml);
	}

	public String visibleTextLost() {
		return charactersMissingFrom(wholeVisibleText(expectedHtml), wholeVisibleText(actualHtml));
	}

	public String visibleTextGained() {
		return charactersMissingFrom(wholeVisibleText(actualHtml), wholeVisibleText(expectedHtml));
	}

	public boolean visibleTextIdentical() {
		return visibleTextLost().isEmpty() && visibleTextGained().isEmpty();
	}

	public static List<String> attributeSignature(String html) {
		List<String> signature = new ArrayList<>();
		for (Element element : Jsoup.parseBodyFragment(html).getAllElements())
			for (Attribute attribute : element.attributes())
				signature.add(element.tagName() + "@" + attribute.getKey() + "=" + attribute.getValue());
		signature.sort(String::compareTo);
		return signature;
	}

	public boolean attributeSignaturesMatch() {
		return attributeSignature(expectedHtml).equals(attributeSignature(actualHtml));
	}

	public List<String> attributesLost() {
		return entriesMissingFrom(attributeSignature(expectedHtml), attributeSignature(actualHtml));
	}

	public List<String> attributesGained() {
		return entriesMissingFrom(attributeSignature(actualHtml), attributeSignature(expectedHtml));
	}

	public static final char WORD_PLACEHOLDER = '#';

	public static String whitespaceLayout(String html) {
		StringBuilder layout = new StringBuilder();
		boolean insideWord = false;
		for (char character : wholeVisibleText(html).toCharArray()) {
			if (Character.isWhitespace(character)) {
				layout.append(character);
				insideWord = false;
				continue;
			}
			if (!insideWord)
				layout.append(WORD_PLACEHOLDER);
			insideWord = true;
		}
		return layout.toString();
	}

	public boolean whitespaceLayoutMatches() {
		return whitespaceLayout(expectedHtml).equals(whitespaceLayout(actualHtml));
	}

	public String whitespaceLayoutDifference() {
		return visible(whitespaceLayout(expectedHtml)) + " vs " + visible(whitespaceLayout(actualHtml));
	}

	public boolean elementStructuresMatch() {
		return elementStructure(expectedHtml).equals(elementStructure(actualHtml));
	}

	public DivergenceFamily family() {
		if (byteIdentical())
			return DivergenceFamily.BYTE_IDENTICAL;
		String lost = visibleTextLost();
		String gained = visibleTextGained();
		if (!lost.isEmpty() || !gained.isEmpty())
			return visibleTextChangeFamily(lost, gained);
		if (!whitespaceLayoutMatches())
			return DivergenceFamily.WHITESPACE_POSITION_ONLY;
		if (!attributeSignaturesMatch())
			return DivergenceFamily.ATTRIBUTE_VALUE_ONLY;
		if (!elementStructuresMatch())
			return DivergenceFamily.ELEMENT_STRUCTURE_ONLY;
		return DivergenceFamily.ESCAPING_OR_ORDERING_ONLY;
	}

	private DivergenceFamily visibleTextChangeFamily(String lost, String gained) {
		if (rewritesAnAuthoredLineBreakTag(lost, gained))
			return DivergenceFamily.VERBATIM_LINE_BREAK_REWRITE;
		if (consumesALiteralContentPlaceholder())
			return DivergenceFamily.LITERAL_CONTENT_PLACEHOLDER_HIJACK;
		if (leaksAContentAttributeBody(lost, gained))
			return DivergenceFamily.CONTENT_ATTRIBUTE_BODY_BRACKET_LEAK;
		if (duplicatesVisibleTextIntoAnAttribute(lost, gained))
			return DivergenceFamily.VISIBLE_TEXT_DUPLICATED_INTO_ATTRIBUTE;
		if (isWhitespaceOnly(lost) && isWhitespaceOnly(gained))
			return countOccurrencesOfCharacter(whitespaceLayout(expectedHtml), '\n')
					== countOccurrencesOfCharacter(whitespaceLayout(actualHtml), '\n')
							? DivergenceFamily.WHITESPACE_ONLY
							: DivergenceFamily.LINE_BREAK_ONLY;
		return DivergenceFamily.UNATTRIBUTED_VISIBLE_TEXT_CHANGE;
	}

	private static final String BREAK_TAG_CHARACTERS = "<br/>\n\r ";

	private boolean rewritesAnAuthoredLineBreakTag(String lost, String gained) {
		if (!sourceMarkup.toLowerCase(Locale.ROOT).contains("<br"))
			return false;
		if (!isDrawnFrom(lost, BREAK_TAG_CHARACTERS) || !isDrawnFrom(gained, BREAK_TAG_CHARACTERS))
			return false;
		return spellsABreakTag(lost) && gained.indexOf('\n') >= 0
				|| spellsABreakTag(gained) && lost.indexOf('\n') >= 0;
	}

	private static boolean spellsABreakTag(String characters) {
		return characters.indexOf('<') >= 0 && characters.indexOf('b') >= 0
				&& characters.indexOf('r') >= 0 && characters.indexOf('>') >= 0;
	}

	private boolean consumesALiteralContentPlaceholder() {
		if (!sourceMarkup.contains(CONTENT_PLACEHOLDER))
			return false;
		return occurrencesOf(wholeVisibleText(expectedHtml), CONTENT_PLACEHOLDER)
				!= occurrencesOf(wholeVisibleText(actualHtml), CONTENT_PLACEHOLDER);
	}

	private boolean leaksAContentAttributeBody(String lost, String gained) {
		String changed = lost.isEmpty() ? gained : lost;
		if (changed.isEmpty())
			return false;
		for (String body : contentAttributeBodiesInSource())
			if (body.indexOf('[') >= 0 && charactersMissingFrom(changed, body).isEmpty())
				return true;
		return false;
	}

	private boolean duplicatesVisibleTextIntoAnAttribute(String lost, String gained) {
		if (!lost.isEmpty() || gained.isEmpty())
			return false;
		StringBuilder attributeValues = new StringBuilder();
		for (Element element : Jsoup.parseBodyFragment(actualHtml).getAllElements())
			for (Attribute attribute : element.attributes())
				attributeValues.append(attribute.getValue());
		return charactersMissingFrom(gained, attributeValues.toString()).isEmpty();
	}

	private List<String> contentAttributeBodiesInSource() {
		List<String> bodies = new ArrayList<>();
		for (Map.Entry<String, BBCodeConfig> entry : grammar.entrySet()) {
			if (!declaresAContentAttributeMode(entry.getValue()))
				continue;
			String opener = "[" + entry.getKey().toLowerCase(Locale.ROOT) + "]";
			String closer = "[/" + entry.getKey().toLowerCase(Locale.ROOT) + "]";
			String lowered = sourceMarkup.toLowerCase(Locale.ROOT);
			int cursor = 0;
			while (true) {
				int open = lowered.indexOf(opener, cursor);
				if (open < 0)
					break;
				int close = lowered.indexOf(closer, open + opener.length());
				int bodyEnd = close < 0 ? sourceMarkup.length() : close;
				bodies.add(sourceMarkup.substring(open + opener.length(), bodyEnd));
				cursor = close < 0 ? sourceMarkup.length() : close + closer.length();
			}
		}
		return bodies;
	}

	private static boolean declaresAContentAttributeMode(BBCodeConfig config) {
		for (BBCodeAttributeMode mode : config.getAttributeConfig().values())
			if (Boolean.TRUE.equals(mode.getContentIsAttributeFlag()))
				return true;
		return false;
	}

	public String describe() {
		StringBuilder description = new StringBuilder();
		description.append("family=").append(family()).append('\n');
		description.append("  source   =").append(visible(sourceMarkup)).append('\n');
		description.append("  expected =").append(visible(expectedHtml)).append('\n');
		description.append("  actual   =").append(visible(actualHtml)).append('\n');
		description.append("  visible text lost   =").append(visible(visibleTextLost())).append('\n');
		description.append("  visible text gained =").append(visible(visibleTextGained())).append('\n');
		description.append("  attributes lost     =").append(attributesLost()).append('\n');
		description.append("  attributes gained   =").append(attributesGained()).append('\n');
		description.append("  whitespace layout   =").append(whitespaceLayoutDifference()).append('\n');
		description.append("  element structure   =").append(elementStructure(expectedHtml)).append(" vs ")
				.append(elementStructure(actualHtml));
		return description.toString();
	}

	public static String visible(String text) {
		StringBuilder shown = new StringBuilder();
		for (int index = 0; index < text.length(); index++) {
			char character = text.charAt(index);
			shown.append(switch (character) {
				case '\n' -> "\\n";
				case '\r' -> "\\r";
				case '\t' -> "\\t";
				default -> character < 0x20 || (character >= 0xE000 && character <= 0xF8FF)
						? String.format("\\u%04X", (int) character)
						: String.valueOf(character);
			});
		}
		return shown.toString();
	}

	public static String wholeVisibleText(String html) {
		Document document = Jsoup.parse(html);
		document.outputSettings().prettyPrint(false);
		return document.wholeText();
	}

	public static String charactersMissingFrom(String reference, String candidate) {
		Map<Character, Integer> referenceCounts = characterMultiset(reference);
		Map<Character, Integer> candidateCounts = characterMultiset(candidate);
		StringBuilder missing = new StringBuilder();
		for (Map.Entry<Character, Integer> entry : referenceCounts.entrySet()) {
			int deficit = entry.getValue() - candidateCounts.getOrDefault(entry.getKey(), 0);
			for (int repeat = 0; repeat < deficit; repeat++)
				missing.append(entry.getKey());
		}
		return missing.toString();
	}

	private static Map<Character, Integer> characterMultiset(String text) {
		Map<Character, Integer> counts = new TreeMap<>();
		for (int index = 0; index < text.length(); index++)
			counts.merge(text.charAt(index), 1, Integer::sum);
		return counts;
	}

	private static List<String> entriesMissingFrom(List<String> reference, List<String> candidate) {
		List<String> remaining = new ArrayList<>(candidate);
		List<String> missing = new ArrayList<>();
		for (String entry : reference)
			if (!remaining.remove(entry))
				missing.add(entry);
		return missing;
	}

	public static String elementStructure(String html) {
		StringBuilder structure = new StringBuilder();
		for (Element element : Jsoup.parseBodyFragment(html).getAllElements())
			structure.append(nestingDepthOf(element)).append(':').append(element.tagName()).append('/');
		return structure.toString();
	}

	private static int nestingDepthOf(Element element) {
		int depth = 0;
		for (Element ancestor = element.parent(); ancestor != null; ancestor = ancestor.parent())
			depth++;
		return depth;
	}

	private static boolean isDrawnFrom(String characters, String allowed) {
		for (char character : characters.toCharArray())
			if (allowed.indexOf(character) < 0)
				return false;
		return true;
	}

	private static boolean isWhitespaceOnly(String characters) {
		for (char character : characters.toCharArray())
			if (!Character.isWhitespace(character))
				return false;
		return true;
	}

	private static int countOccurrencesOfCharacter(String text, char needle) {
		int count = 0;
		for (char character : text.toCharArray())
			if (character == needle)
				count++;
		return count;
	}

	private static int occurrencesOf(String text, String needle) {
		int count = 0;
		int cursor = text.indexOf(needle);
		while (cursor >= 0) {
			count++;
			cursor = text.indexOf(needle, cursor + needle.length());
		}
		return count;
	}

	public Optional<String> firstDivergingByteOffset() {
		int limit = Math.min(expectedHtml.length(), actualHtml.length());
		for (int index = 0; index < limit; index++)
			if (expectedHtml.charAt(index) != actualHtml.charAt(index))
				return Optional.of("offset " + index + ": expected '" + visible(String.valueOf(expectedHtml.charAt(index)))
						+ "' actual '" + visible(String.valueOf(actualHtml.charAt(index))) + "'");
		if (expectedHtml.length() == actualHtml.length())
			return Optional.empty();
		return Optional.of("common prefix of " + limit + " characters, then expected length "
				+ expectedHtml.length() + " vs actual length " + actualHtml.length());
	}
}
