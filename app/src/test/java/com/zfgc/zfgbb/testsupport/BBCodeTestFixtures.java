package com.zfgc.zfgbb.testsupport;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zfgc.zfgbb.content.renderer.RenderedTextEnricher;
import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.renderer.ContentTagResolver;
import com.zfgc.zfgbb.services.forum.QuotedMessageSource;
import com.zfgc.zfgbb.dataprovider.forum.BBCodeDataProvider;
import com.zfgc.zfgbb.model.forum.AttributeDataType;
import com.zfgc.zfgbb.model.forum.AttributeValuePolicy;
import com.zfgc.zfgbb.model.forum.BBCodeAttribute;
import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;

public final class BBCodeTestFixtures {

	public static final String QUOTE_HEADER =
			"<div class=\"bb-q\"><div class=\"bb-qh\">Quote from "
					+ "{{#msg.authorUserId}}<a class=\"bb-resource-link\" href=\"/user/profile/{{msg.authorUserId}}\" "
					+ "data-resource=\"member\" data-user-id=\"{{msg.authorUserId}}\">{{/msg.authorUserId}}"
					+ "{{#msg.author}}{{msg.author}}{{/msg.author}}"
					+ "{{^msg.author}}{{#msg.permitted}}(unknown){{/msg.permitted}}"
					+ "{{^msg.permitted}}(unavailable){{/msg.permitted}}{{/msg.author}}"
					+ "{{#msg.authorUserId}}</a>{{/msg.authorUserId}} on "
					+ "{{#msg.dateIso}}<time class=\"bb-date-long\" datetime=\"{{msg.dateIso}}\">{{msg.dateText}}"
					+ "</time>{{/msg.dateIso}} (<a class=\"bb-resource-link\" href=\""
					+ "{{#msg.threadId}}/forum/thread/{{msg.threadId}}/{{msg.page}}#msg{{msg.sourceId}}"
					+ "{{/msg.threadId}}{{^msg.threadId}}#{{/msg.threadId}}\" data-resource=\"thread\">jump</a>)"
					+ "</div><div class=\"bb-qb\">";

	public static final Path BB_CODE_SEED =
			Path.of("src/main/resources/db/migration/functions/R__03_bbcodes.sql");

	public static final Path LOOKUP_SEED =
			Path.of("src/main/resources/db/migration/functions/R__04_lookups.sql");

	public static final Path CONTENT_TEMPLATE_SEED =
			Path.of("src/main/resources/db/migration/functions/R__05_content_templates.sql");

	private BBCodeTestFixtures() {}

	public static BBCodeAttribute attr(String name, String index) {
		BBCodeAttribute attribute = new BBCodeAttribute();
		attribute.setName(name);
		attribute.setAttributeIndex(index);
		attribute.setDataType(AttributeDataType.TEXT);
		attribute.setValuePolicy(seededAttributeValuePolicies().get(AttributeDataType.TEXT));
		return attribute;
	}

	public static void indexTheValuePoliciesOfEveryDeclaredAttribute(BBCodeConfig config) {
		Map<String, AttributeValuePolicy> byName = new LinkedHashMap<>();
		for (BBCodeAttributeMode declaredMode : config.getAttributeConfig().values())
			for (BBCodeAttribute attribute : declaredMode.getAttributes())
				byName.putIfAbsent(attribute.getName(), attribute.getValuePolicy());
		config.setValuePolicyByAttributeName(Map.copyOf(byName));
	}

	public static BBCodeAttributeMode mode(String openTag, BBCodeAttribute... attributes) {
		BBCodeAttributeMode attributeMode = new BBCodeAttributeMode();
		attributeMode.setOpenTag(openTag);
		attributeMode.setCloseTag("</div></div>");
		attributeMode.setAttributes(new ArrayList<>(Arrays.asList(attributes)));
		return attributeMode;
	}

	public static QuotedMessageSource messageResolver() {
		QuotedMessageSource lookup = org.mockito.Mockito.mock(QuotedMessageSource.class);
		org.mockito.Mockito.when(lookup.resolverCode()).thenReturn(QuotedMessageSource.RESOLVER_CODE);
		return lookup;
	}

	public static BBCodeConfig quoteConfig() {
		BBCodeConfig quote = new BBCodeConfig();
		quote.setCode("quote");
		quote.setProcessContentFlag(true);
		quote.setEndTag("</div></div>");
		quote.setAllAttributeNamesAsString("author=,thread=,msg=");
		quote.setSourceReferenceAttribute("msg=");
		quote.setSourceReferenceResolver(QuotedMessageSource.RESOLVER_CODE);
		quote.setAttributeConfig(new HashMap<>());
		quote.getAttributeConfig().put("", mode("<div class=\"bb-q\"><div class=\"bb-qb\">"));
		quote.getAttributeConfig().put("author=",
				mode("<div class=\"bb-q\"><div class=\"bb-qh\">Quote from {{0}}</div><div class=\"bb-qb\">",
						attr("author=", "{{0}}")));
		quote.getAttributeConfig().put("msg=", mode(QUOTE_HEADER, attr("msg=", "{{0}}")));
		quote.getAttributeConfig().put("thread=msg=",
				mode(QUOTE_HEADER, attr("thread=", "{{0}}"), attr("msg=", "{{1}}")));
		quote.getAttributeConfig().put("author=thread=msg=",
				mode(QUOTE_HEADER, attr("author=", "{{0}}"), attr("thread=", "{{1}}"), attr("msg=", "{{2}}")));
		indexTheValuePoliciesOfEveryDeclaredAttribute(quote);
		return quote;
	}

	public static BBCodeConfig templateConfig() {
		BBCodeConfig template = new BBCodeConfig();
		template.setCode("template");
		template.setProcessContentFlag(false);
		template.setEndTag("</div>");
		template.setAllAttributeNamesAsString(BBCodeConfig.NAMELESS_ATTRIBUTE_NAME);
		template.setAttributeConfig(new HashMap<>());
		BBCodeAttributeMode invocation = new BBCodeAttributeMode();
		invocation.setOpenTag("<div class=\"bb-code-template\" data-resource=\"template\" "
				+ "data-template-name=\"{{0}}\">");
		invocation.setCloseTag("</div>");
		invocation.setAttributes(new ArrayList<>(List.of(
				attr(BBCodeConfig.NAMELESS_ATTRIBUTE_NAME, "{{0}}"))));
		template.getAttributeConfig().put(BBCodeConfig.NAMELESS_ATTRIBUTE_NAME, invocation);
		indexTheValuePoliciesOfEveryDeclaredAttribute(template);
		return template;
	}

	public static BBCodeConfig codeConfig() {
		return simpleTag("code", "<code>", "</code>", false);
	}

	public static BBCodeConfig simpleTag(String code, String openTag, String closeTag, boolean processContent) {
		BBCodeConfig config = new BBCodeConfig();
		config.setCode(code);
		config.setProcessContentFlag(processContent);
		config.setEndTag(closeTag);
		config.setAllAttributeNamesAsString("");
		config.setAttributeConfig(new HashMap<>());
		BBCodeAttributeMode attributeMode = new BBCodeAttributeMode();
		attributeMode.setOpenTag(openTag);
		attributeMode.setCloseTag(closeTag);
		attributeMode.setAttributes(new ArrayList<>());
		config.getAttributeConfig().put("", attributeMode);
		indexTheValuePoliciesOfEveryDeclaredAttribute(config);
		return config;
	}

	public static ContentTagResolver.Resolved resolved(boolean permitted, OffsetDateTime revisionTs, String body) {
		NavigableMap<OffsetDateTime, ContentTagResolver.SourceRevision> revisions = new TreeMap<>();
		revisions.put(revisionTs, new ContentTagResolver.SourceRevision(body, ContentFormat.BBCODE));
		return new ContentTagResolver.Resolved("Alice", 7, revisionTs, 42, 1, 3, permitted, revisions);
	}

	public static Map<String, BBCodeConfig> seededBBCodeGrammar() {
		String seed = readSeed(BB_CODE_SEED);
		List<BBCodeConfig> enabledConfigs = new ArrayList<>();
		Map<Integer, List<BBCodeAttributeMode>> modesByConfigId = new LinkedHashMap<>();
		Map<Integer, List<BBCodeAttribute>> attributesByModeId = new LinkedHashMap<>();
		Map<String, Integer> configIdByCode = new HashMap<>();

		for (SeedCall call : seedCalls(seed,
				"create_bbcode_config", "create_bbcode_attr_mode", "create_bbcode_attr")) {
			List<String> arguments = call.arguments();
			switch (call.function()) {
				case "create_bbcode_config" -> {
					List<String> positional = call.positional();
					BBCodeConfig config = new BBCodeConfig();
					config.setBbCodeConfigId(Integer.parseInt(positional.get(0).trim()));
					config.setCode(literal(positional.get(1)));
					config.setEndTag(literal(positional.get(2)));
					config.setProcessContentFlag(Boolean.parseBoolean(positional.get(3).trim()));
					config.setSelfClosingFlag(positional.size() > 4 && Boolean.parseBoolean(positional.get(4).trim()));
					if (positional.size() > 6)
						config.setSourceReferenceAttribute(literal(positional.get(6)));
					if (positional.size() > 7)
						config.setSourceReferenceResolver(literal(positional.get(7)));
					config.setMarkdownEquivalent(positional.size() > 8
							? literal(positional.get(8))
							: literal(call.named("p_markdown_equivalent")));
					config.setMarkdownCanonicalFlag(positional.size() > 9
							? Boolean.parseBoolean(positional.get(9).trim())
							: Boolean.parseBoolean(String.valueOf(literal(call.named("p_markdown_canonical")))));
					config.setImplicitItemMarker(literal(call.named("p_implicit_item_marker")));
					config.setImplicitItemCode(literal(call.named("p_implicit_item_code")));
					configIdByCode.put(config.getCode(), config.getBbCodeConfigId());
					modesByConfigId.computeIfAbsent(config.getBbCodeConfigId(), key -> new ArrayList<>());
					boolean enabled = positional.size() <= 5 || Boolean.parseBoolean(positional.get(5).trim());
					if (enabled)
						enabledConfigs.add(config);
				}
				case "create_bbcode_attr_mode" -> {
					BBCodeAttributeMode attributeMode = new BBCodeAttributeMode();
					attributeMode.setBbCodeAttributeModeId(Integer.parseInt(arguments.get(0).trim()));
					Integer configId = configIdByCode.get(literal(arguments.get(1)));
					attributeMode.setBbCodeConfigId(configId);
					attributeMode.setOpenTag(literal(arguments.get(2)));
					attributeMode.setCloseTag(literal(arguments.get(3)));
					attributeMode.setContentIsAttributeFlag(Boolean.parseBoolean(arguments.get(4).trim()));
					attributeMode.setOutputContentFlag(Boolean.parseBoolean(arguments.get(5).trim()));
					if (arguments.size() > 6)
						attributeMode.setContentSemanticRole(literal(arguments.get(6)));
					modesByConfigId.computeIfAbsent(configId, key -> new ArrayList<>()).add(attributeMode);
					attributesByModeId.computeIfAbsent(attributeMode.getBbCodeAttributeModeId(),
							key -> new ArrayList<>());
				}
				default -> {
					BBCodeAttribute attribute = new BBCodeAttribute();
					attribute.setBbCodeAttributeId(Integer.parseInt(arguments.get(0).trim()));
					attribute.setAttributeIndex(arguments.get(1).trim());
					int modeId = Integer.parseInt(arguments.get(2).trim());
					attribute.setBbCodeAttributeModeId(modeId);
					attribute.setName(literal(arguments.get(3)));
					attribute.setAttributeDataType(literal(arguments.get(4)));
					if (arguments.size() > 5)
						attribute.setSemanticRole(literal(arguments.get(5)));
					attributesByModeId.computeIfAbsent(modeId, key -> new ArrayList<>()).add(attribute);
				}
			}
		}

		BBCodeDataProvider provider = mock(BBCodeDataProvider.class, CALLS_REAL_METHODS);
		doReturn(seededAttributeValuePolicies()).when(provider).compileTheDeclaredValuePolicies();
		doReturn(enabledConfigs).when(provider).getValidBBCodes();
		for (Map.Entry<Integer, List<BBCodeAttributeMode>> entry : modesByConfigId.entrySet())
			doReturn(new ArrayList<>(entry.getValue())).when(provider).getAttributeModesByBBCode(entry.getKey());
		for (Map.Entry<Integer, List<BBCodeAttribute>> entry : attributesByModeId.entrySet())
			doReturn(new ArrayList<>(entry.getValue())).when(provider).getAttributesByMode(entry.getKey());
		return provider.getBBCodeConfig();
	}

	public static List<RenderedTextEnricher.SmileyToken> seededSmilies() {
		List<RenderedTextEnricher.SmileyToken> tokens = new ArrayList<>();
		for (SeedCall call : seedCalls(readSeed(LOOKUP_SEED), "create_smiley"))
			tokens.add(new RenderedTextEnricher.SmileyToken(literal(call.arguments().get(0)),
					literal(call.arguments().get(1)), literal(call.arguments().get(2))));
		return tokens;
	}

	private static final Pattern DECLARED_TEMPLATE_BODY =
			Pattern.compile("(\\w+)\\s+text\\s*:=\\s*(?=E?')");

	public static Map<String, String> seededBBCodeTemplateBodies() {
		String seed = readSeed(CONTENT_TEMPLATE_SEED);
		Map<String, String> bodiesByTemplateCode = new LinkedHashMap<>();
		for (SeedCall call : seedCalls(seed, "seed_content_template")) {
			List<String> arguments = call.arguments();
			if (arguments.size() < 5 || !"BBCODE".equals(literal(arguments.get(1))))
				continue;
			if (isQuotedLiteral(arguments.get(0)) && isQuotedLiteral(arguments.get(4)))
				bodiesByTemplateCode.put(literal(arguments.get(0)), literal(arguments.get(4)));
		}
		Matcher declaration = DECLARED_TEMPLATE_BODY.matcher(seed);
		while (declaration.find())
			bodiesByTemplateCode.put(declaration.group(1),
					literal(seed.substring(declaration.end(), endOfLiteral(seed, declaration.end()) + 1)));
		return bodiesByTemplateCode;
	}

	private record SeedCall(String function, List<String> arguments) {

		private List<String> positional() {
			List<String> positional = new ArrayList<>();
			for (String argument : arguments)
				if (!argument.contains("=>"))
					positional.add(argument);
			return positional;
		}

		private String named(String parameter) {
			for (String argument : arguments) {
				int arrow = argument.indexOf("=>");
				if (arrow >= 0 && argument.substring(0, arrow).trim().equals(parameter))
					return argument.substring(arrow + "=>".length());
			}
			return null;
		}
	}

	public static Map<AttributeDataType, AttributeValuePolicy> seededAttributeValuePolicies() {
		String seed = readSeed(BB_CODE_SEED);
		Map<AttributeDataType, Map<String, String>> mappings = new LinkedHashMap<>();
		for (SeedCall call : seedCalls(seed, "create_attribute_value_mapping"))
			AttributeDataType.forCode(literal(call.arguments().get(0)))
					.ifPresent(type -> mappings.computeIfAbsent(type, key -> new LinkedHashMap<>())
							.put(literal(call.arguments().get(1)), literal(call.arguments().get(2))));
		String listStyleCodes = String.join(",", seededListStyleTypes().keySet());
		Map<AttributeDataType, AttributeValuePolicy> policies = new LinkedHashMap<>();
		for (SeedCall call : seedCalls(seed, "create_attribute_data_type")) {
			List<String> arguments = call.arguments();
			Optional<AttributeDataType> type = AttributeDataType.forCode(literal(arguments.get(0)));
			if (type.isEmpty())
				continue;
			String declaredPattern = literal(arguments.get(3));
			String declaredAllowedValues = type.get() == AttributeDataType.LIST_TYPE
					? listStyleCodes
					: literal(arguments.get(8));
			policies.put(type.get(), new AttributeValuePolicy(
					declaredPattern == null ? Optional.empty() : Optional.of(Pattern.compile(declaredPattern)),
					literal(arguments.get(4)) == null ? "" : literal(arguments.get(4)),
					Boolean.parseBoolean(arguments.get(5).trim()),
					Boolean.parseBoolean(arguments.get(6).trim()),
					Optional.ofNullable(literal(arguments.get(7))),
					declaredAllowedValues == null ? Set.of() : Set.of(declaredAllowedValues.split(",")),
					mappings.getOrDefault(type.get(), Map.of())));
		}
		return Map.copyOf(policies);
	}

	public static Map<String, Boolean> seededListStyleTypes() {
		Map<String, Boolean> numbersItemsByCode = new LinkedHashMap<>();
		for (SeedCall call : seedCalls(readSeed(BB_CODE_SEED), "create_list_style_type"))
			numbersItemsByCode.put(literal(call.arguments().get(0)),
					Boolean.parseBoolean(call.arguments().get(3).trim()));
		return Map.copyOf(numbersItemsByCode);
	}

	private static String readSeed(Path seed) {
		try {
			return Files.readString(seed);
		} catch (IOException unreadable) {
			throw new UncheckedIOException("the harness reads the real seed from " + seed.toAbsolutePath()
					+ "; surefire must run with the app module as its working directory", unreadable);
		}
	}

	private static List<SeedCall> seedCalls(String seed, String... functionNames) {
		Pattern invocation = Pattern.compile(
				"(?:select|perform)\\s+zfgbb\\.(" + String.join("|", functionNames) + ")\\(");
		Matcher matcher = invocation.matcher(seed);
		List<SeedCall> calls = new ArrayList<>();
		while (matcher.find()) {
			int open = matcher.end() - 1;
			int close = matchingCloseParen(seed, open);
			calls.add(new SeedCall(matcher.group(1), splitTopLevelArguments(seed.substring(open + 1, close))));
		}
		return calls;
	}

	private static int matchingCloseParen(String seed, int open) {
		int depth = 0;
		for (int index = open; index < seed.length(); index++) {
			char character = seed.charAt(index);
			if (character == '\'') {
				index = endOfLiteral(seed, index);
				continue;
			}
			if (character == '(')
				depth++;
			else if (character == ')' && --depth == 0)
				return index;
		}
		throw new IllegalStateException("unbalanced parentheses in seed starting at offset " + open);
	}

	private static int endOfLiteral(String seed, int quoteOrPrefix) {
		int open = seed.charAt(quoteOrPrefix) == '\'' ? quoteOrPrefix : quoteOrPrefix + 1;
		boolean escaped = carriesEscapeStringPrefix(seed, open);
		for (int index = open + 1; index < seed.length(); index++) {
			char character = seed.charAt(index);
			if (escaped && character == '\\') {
				index++;
				continue;
			}
			if (character != '\'')
				continue;
			if (index + 1 < seed.length() && seed.charAt(index + 1) == '\'') {
				index++;
				continue;
			}
			return index;
		}
		throw new IllegalStateException("unterminated string literal in seed at offset " + open);
	}

	private static boolean carriesEscapeStringPrefix(String seed, int openQuote) {
		if (openQuote == 0 || Character.toUpperCase(seed.charAt(openQuote - 1)) != 'E')
			return false;
		return openQuote < 2 || !Character.isLetterOrDigit(seed.charAt(openQuote - 2))
				&& seed.charAt(openQuote - 2) != '_';
	}

	private static boolean isQuotedLiteral(String rawArgument) {
		String trimmed = rawArgument.trim();
		String quoted = trimmed.length() > 1 && Character.toUpperCase(trimmed.charAt(0)) == 'E'
				? trimmed.substring(1)
				: trimmed;
		return quoted.length() > 1 && quoted.startsWith("'") && quoted.endsWith("'");
	}

	private static List<String> splitTopLevelArguments(String callBody) {
		List<String> arguments = new ArrayList<>();
		StringBuilder current = new StringBuilder();
		int depth = 0;
		for (int index = 0; index < callBody.length(); index++) {
			char character = callBody.charAt(index);
			if (character == '\'') {
				int close = endOfLiteral(callBody, index);
				current.append(callBody, index, close + 1);
				index = close;
				continue;
			}
			if (character == '(')
				depth++;
			if (character == ')')
				depth--;
			if (character == ',' && depth == 0) {
				arguments.add(current.toString());
				current.setLength(0);
				continue;
			}
			current.append(character);
		}
		arguments.add(current.toString());
		return arguments;
	}

	private static String literal(String rawArgument) {
		if (rawArgument == null)
			return null;
		String trimmed = rawArgument.trim();
		if (trimmed.equals("null"))
			return null;
		boolean escaped = trimmed.length() > 1 && Character.toUpperCase(trimmed.charAt(0)) == 'E';
		String quoted = escaped ? trimmed.substring(1) : trimmed;
		if (!quoted.startsWith("'") || !quoted.endsWith("'"))
			return trimmed;
		String body = quoted.substring(1, quoted.length() - 1).replace("''", "'");
		if (!escaped)
			return body;
		StringBuilder unescaped = new StringBuilder();
		for (int index = 0; index < body.length(); index++) {
			char character = body.charAt(index);
			if (character != '\\' || index + 1 >= body.length()) {
				unescaped.append(character);
				continue;
			}
			char escape = body.charAt(++index);
			unescaped.append(switch (escape) {
				case 'n' -> '\n';
				case 'r' -> '\r';
				case 't' -> '\t';
				default -> escape;
			});
		}
		return unescaped.toString();
	}
}
