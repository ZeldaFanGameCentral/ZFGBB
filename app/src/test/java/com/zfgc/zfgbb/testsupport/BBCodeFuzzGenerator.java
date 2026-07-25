package com.zfgc.zfgbb.testsupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.zfgc.zfgbb.content.renderer.RenderedTextEnricher;
import com.zfgc.zfgbb.model.forum.AttributeDataType;
import com.zfgc.zfgbb.model.forum.BBCodeAttribute;
import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;

public final class BBCodeFuzzGenerator {

	public enum Alphabet {
		SEEDED_GRAMMAR_WIDE,
		HOSTILE_LITERALS
	}

	public static final long[] REPRODUCIBLE_SEEDS = { 42L, 1L, 2L, 3L, 7L, 99L, 2024L, 31337L };

	public static final int MINIMUM_TOKENS_PER_INPUT = 1;

	public static final int MAXIMUM_TOKENS_PER_INPUT = 7;

	private static final String QUOTE_SENTINEL_OPEN = "\uE000";

	private static final String QUOTE_SENTINEL_CLOSE = "\uE001";

	private final String[] alphabet;
	private final Random random;

	public BBCodeFuzzGenerator(Alphabet alphabet, Map<String, BBCodeConfig> grammar,
			List<RenderedTextEnricher.SmileyToken> smilies, long seed) {
		this.alphabet = tokensFor(alphabet, grammar, smilies);
		this.random = new Random(seed);
	}

	public int alphabetSize() {
		return alphabet.length;
	}

	public String nextInput() {
		StringBuilder input = new StringBuilder();
		int tokens = MINIMUM_TOKENS_PER_INPUT
				+ random.nextInt(MAXIMUM_TOKENS_PER_INPUT - MINIMUM_TOKENS_PER_INPUT + 1);
		for (int token = 0; token < tokens; token++)
			input.append(alphabet[random.nextInt(alphabet.length)]);
		return input.toString();
	}

	public static String[] tokensFor(Alphabet alphabet, Map<String, BBCodeConfig> grammar,
			List<RenderedTextEnricher.SmileyToken> smilies) {
		Set<String> tokens = new LinkedHashSet<>(everySeededTagForm(grammar));
		tokens.addAll(alphabet == Alphabet.SEEDED_GRAMMAR_WIDE ? dilutingLiterals() : hostileLiterals());
		for (RenderedTextEnricher.SmileyToken smiley : smilies)
			tokens.add(smiley.code());
		return tokens.toArray(new String[0]);
	}

	public static Set<String> codesCoveredBy(String[] alphabet, Map<String, BBCodeConfig> grammar) {
		Set<String> covered = new LinkedHashSet<>();
		for (String code : grammar.keySet())
			for (String token : alphabet)
				if (opensTag(token, code)) {
					covered.add(code);
					break;
				}
		return covered;
	}

	public static boolean opensTag(String token, String code) {
		String lowered = token.toLowerCase(Locale.ROOT);
		String opener = "[" + code.toLowerCase(Locale.ROOT);
		for (int cursor = lowered.indexOf(opener); cursor >= 0; cursor = lowered.indexOf(opener, cursor + 1)) {
			int afterCode = cursor + opener.length();
			if (afterCode >= lowered.length())
				continue;
			char delimiter = lowered.charAt(afterCode);
			if (delimiter == ']' || delimiter == '=' || Character.isWhitespace(delimiter))
				return true;
		}
		return false;
	}

	private static List<String> everySeededTagForm(Map<String, BBCodeConfig> grammar) {
		List<String> forms = new ArrayList<>();
		for (Map.Entry<String, BBCodeConfig> entry : grammar.entrySet()) {
			String code = entry.getKey().toLowerCase(Locale.ROOT);
			BBCodeConfig config = entry.getValue();
			forms.add("[" + code + "]");
			forms.add("[/" + code + "]");
			forms.add("[/" + code.toUpperCase(Locale.ROOT) + "]");
			forms.add("[/" + code + " stray=1]");
			if (Boolean.TRUE.equals(config.getSelfClosingFlag()))
				forms.add("[" + code + " ]");
			for (BBCodeAttributeMode mode : config.getAttributeConfig().values()) {
				if (Boolean.TRUE.equals(mode.getContentIsAttributeFlag()))
					forms.add("[" + code + "]body [nested] text[/" + code + "]");
				if (mode.getAttributes().isEmpty())
					continue;
				StringBuilder opener = new StringBuilder("[").append(code);
				for (BBCodeAttribute attribute : mode.getAttributes()) {
					String value = sampleValueFor(attribute);
					if ("=".equals(attribute.getName()))
						opener.append('=').append(value);
					else
						opener.append(' ').append(attribute.getName()).append(value);
				}
				forms.add(opener.append(']').toString());
			}
			if (Boolean.FALSE.equals(config.getProcessContentFlag()))
				forms.add("[" + code + "]<br/>verbatim [b]body[/b]<br>[/" + code + "]");
		}
		return forms;
	}

	private static String sampleValueFor(BBCodeAttribute attribute) {
		AttributeDataType dataType = AttributeDataType.forCode(attribute.getAttributeDataType())
				.orElse(AttributeDataType.TEXT);
		return switch (dataType) {
			case INTEGER -> "7";
			case COLOR -> "red";
			case URL -> "http://zfgc.com/a";
			case TIMESTAMP -> "1494552503";
			case SIZE -> "3";
			case DIMENSION -> "5";
			case FONT_NAME -> "arial";
			case IDENTIFIER -> "abc123";
			case LIST_TYPE -> "decimal";
			default -> "Bob";
		};
	}

	private static List<String> dilutingLiterals() {
		return Arrays.asList(
				"plain ", "text ", "word", "\n", "\n\n", "\r\n", "\r", "  ", "\t",
				"[", "]", "[[", "]]", "[/", "[*]",
				"{{c}}", "{{0}}", "{{1}}", "{{msg.author}}", "{{msg.date}}",
				"[[File:Foo.png]]", "[[Image:Bar.jpg|thumb]]",
				QUOTE_SENTINEL_OPEN, QUOTE_SENTINEL_CLOSE, QUOTE_SENTINEL_OPEN + "0" + QUOTE_SENTINEL_CLOSE,
				"<b>raw</b>", "<div class=\"p-4\">", "</div>", "<br/>", "<br>",
				"&amp;", "&", "<", ">", "http://zfgc.com", "a&b", "\"q\"", "'s", "\\[b]",
				"[unknowncode]", "[b=x]", "[b ]", "[b\n]", "[QUOTE]", "[/QUOTE]", "[B]");
	}

	private static List<String> hostileLiterals() {
		List<String> literals = new ArrayList<>(dilutingLiterals());
		literals.addAll(Arrays.asList(
				"<script>x</script>", "[img]x\" onerror=\"alert(1)[/img]", "[url=javascript:alert(1)]",
				"[pre][code]", "[/code][/pre]", "[code][pre]", "[/pre][/code]",
				"[code][pre]nested verbatim[/pre][/code]", "[pre]a[/pre foo]b",
				"[pre foo]", "[/pre foo]", "[/code bar=1]",
				"[code]:-[[/code]", "[pre]text [[/pre]", "[template=x]body[[/template]",
				"[b][i]", "[/i][/b]", "[b][quote link=b]", "[list][*]one[*]two[/list]",
				"[color=#ff0000]", "[size=99]", "[b=\"x\"]", "[b='x']",
				QUOTE_SENTINEL_OPEN + QUOTE_SENTINEL_CLOSE, "\uDBFF\uDFFD", "\uFFFD", "\u00A0", "\u2028",
				"{{c}} and [img]body[/img]", "{{=<% %>=}}", "{{#found}}", "{{/found}}",
				"\\", "\"", "'"));
		return literals;
	}
}
