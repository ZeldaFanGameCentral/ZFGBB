package com.zfgc.zfgbb.content.renderer;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.dataprovider.forum.BBCodeDataProvider;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.model.forum.BBCodeAttribute;
import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;
import com.zfgc.zfgbb.util.ZfgcStringUtils;

import jakarta.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class BBCodeService {

	public volatile Map<String, BBCodeConfig> validBbCodes = new HashMap<>();
	private Logger LOGGER = LogManager.getLogger(BBCodeService.class);

	@Autowired
	BBCodeDataProvider bbCodeDataProvider;

	@Autowired
	public BBCodeOutputSanitizer outputSanitizer;

	@Autowired
	public QuotedMessageLookup quotedMessageLookup;

	private static final Pattern QUOTE_OPENER = Pattern.compile(
			"\\[quote\\b([^\\]]*)\\]", Pattern.CASE_INSENSITIVE);
	private static final DateTimeFormatter QUOTE_DATE_FORMATTER =
			DateTimeFormatter.ofPattern("h:mm:ss a");
	private static final DateTimeFormatter QUOTE_DATE_FORMATTER_FULL =
			DateTimeFormatter.ofPattern("MMMM d, yyyy, h:mm:ss a");

	private static final String QUOTE_UNAVAILABLE_PLACEHOLDER = "(quoted message unavailable)";
	private static final char SENTINEL_OPEN = '\uE000';
	private static final char SENTINEL_CLOSE = '\uE001';
	private static final Pattern SENTINEL_PATTERN = Pattern.compile(SENTINEL_OPEN + "(\\d+)" + SENTINEL_CLOSE);

	private static final int MAX_SOURCE_QUOTE_EXPANSION_DEPTH = 1;

	public record QuotingPost(String rawText, OffsetDateTime createdTs) {}

	private record RevisionKey(Integer messageId, OffsetDateTime revisionCreatedTs) {}

	private record QuoteScope(Map<Integer, QuotedMessageLookup.Resolved> byMsgId,
			Map<RevisionKey, String> renderedBodyHtml) {}

	private record ParsedAttributes(String attFormat, Map<String, String> attributeValues) {}

	private final ThreadLocal<Map<Integer, QuotedMessageLookup.Resolved>> resolvedQuotes = new ThreadLocal<>();
	private final ThreadLocal<QuoteScope> quoteScope = new ThreadLocal<>();
	private final ThreadLocal<Integer> expandDepth = ThreadLocal.withInitial(() -> 0);

	public String parseText(String input) {
		return parseText(input, null);
	}

	public String parseText(String input, OffsetDateTime quotingCreatedTs) {
		if (input == null) {
			return "";
		}

		QuoteScope scope = quoteScope.get();
		Map<Integer, QuotedMessageLookup.Resolved> resolvedByMsgId =
				scope != null ? scope.byMsgId() : resolveQuotedMessages(input);
		resolvedQuotes.set(resolvedByMsgId);
		try {
			List<String> splicedBodies = new ArrayList<>();
			String prepared = input;
			if (scope != null && quotingCreatedTs != null && expandDepth.get() < MAX_SOURCE_QUOTE_EXPANSION_DEPTH) {
				prepared = spliceMsgQuotes(input, quotingCreatedTs, scope, splicedBodies);
			} else if (scope == null && expandDepth.get() < MAX_SOURCE_QUOTE_EXPANSION_DEPTH) {
				prepared = spliceEmptyMsgQuoteFallback(input, resolvedByMsgId);
			}
			String rendered = parseTextInternal(prepared);
			return splicedBodies.isEmpty() ? rendered : substituteSentinels(rendered, splicedBodies);
		} finally {
			resolvedQuotes.remove();
		}
	}

	public void openQuoteScope(Collection<QuotingPost> posts, Set<Integer> visibleBoardIds) {
		Set<Integer> topLevelIds = new HashSet<>();
		if (posts != null) {
			for (QuotingPost post : posts) {
				collectQuotedMsgIds(post.rawText(), topLevelIds);
			}
		}
		Map<Integer, QuotedMessageLookup.Resolved> byMsgId = new HashMap<>(resolveIds(topLevelIds, visibleBoardIds));

		Map<RevisionKey, String> neededBodies = new LinkedHashMap<>();
		Set<Integer> innerIds = new HashSet<>();
		if (posts != null) {
			for (QuotingPost post : posts) {
				if (post.createdTs() == null) {
					continue;
				}
				Set<Integer> postIds = new HashSet<>();
				collectQuotedMsgIds(post.rawText(), postIds);
				for (Integer msgId : postIds) {
					QuotedMessageLookup.Resolved resolved = byMsgId.get(msgId);
					if (resolved == null || !resolved.permitted() || resolved.revisionsByCreatedTs() == null) {
						continue;
					}
					Map.Entry<OffsetDateTime, String> floor =
							resolved.revisionsByCreatedTs().floorEntry(post.createdTs());
					if (floor == null || floor.getValue() == null || floor.getValue().isBlank()) {
						continue;
					}
					RevisionKey key = new RevisionKey(msgId, floor.getKey());
					if (!neededBodies.containsKey(key)) {
						neededBodies.put(key, floor.getValue());
						collectQuotedMsgIds(floor.getValue(), innerIds);
					}
				}
			}
		}

		innerIds.removeAll(byMsgId.keySet());
		if (!innerIds.isEmpty()) {
			resolveIds(innerIds, visibleBoardIds).forEach(byMsgId::putIfAbsent);
		}

		Map<RevisionKey, String> renderedBodyHtml = new HashMap<>();
		quoteScope.set(new QuoteScope(byMsgId, renderedBodyHtml));
		for (Map.Entry<RevisionKey, String> needed : neededBodies.entrySet()) {
			renderedBodyHtml.put(needed.getKey(), renderSourceBody(needed.getValue()));
		}
	}

	public void closeQuoteScope() {
		quoteScope.remove();
		expandDepth.remove();
	}

	private String renderSourceBody(String rawBody) {
		expandDepth.set(MAX_SOURCE_QUOTE_EXPANSION_DEPTH);
		try {
			return parseText(rawBody, null);
		} finally {
			expandDepth.set(0);
		}
	}

	private Map<Integer, QuotedMessageLookup.Resolved> resolveQuotedMessages(String input) {
		Set<Integer> ids = new HashSet<>();
		collectQuotedMsgIds(input, ids);
		return resolveIds(ids, null);
	}

	private void collectQuotedMsgIds(String input, Set<Integer> ids) {
		if (input == null) {
			return;
		}
		Matcher openerMatcher = QUOTE_OPENER.matcher(input);
		while (openerMatcher.find()) {
			Integer msgId = extractQuoteMsgId(openerMatcher.group(1));
			if (msgId != null) {
				ids.add(msgId);
			}
		}
	}

	public Set<Integer> collectQuotedMsgIds(String input) {
		Set<Integer> ids = new HashSet<>();
		collectQuotedMsgIds(input, ids);
		return ids;
	}

	public Integer extractQuoteMsgId(String openerAttributeText) {
		if (openerAttributeText == null) {
			return null;
		}
		BBCodeConfig quote = validBbCodes.get("QUOTE");
		if (quote == null || quote.getAllAttributeNamesAsString() == null) {
			return null;
		}
		ParsedAttributes parsed = parseAttributeValues(quote, openerAttributeText);
		BBCodeAttributeMode mode = quote.getAttributeConfig().get(parsed.attFormat());
		if (mode == null || !modeHasMsgIdAttribute(mode)) {
			return null;
		}
		String msgValue = parsed.attributeValues().get("msg=");
		if (msgValue == null) {
			return null;
		}
		try {
			return Integer.parseInt(msgValue.trim());
		} catch (NumberFormatException ignore) {
			return null;
		}
	}

	private static boolean modeHasMsgIdAttribute(BBCodeAttributeMode mode) {
		for (BBCodeAttribute attribute : mode.getAttributes()) {
			if ("msg=".equals(attribute.getName())) {
				return true;
			}
		}
		return false;
	}

	private Map<Integer, QuotedMessageLookup.Resolved> resolveIds(Set<Integer> ids, Set<Integer> visibleBoardIds) {
		if (ids.isEmpty() || quotedMessageLookup == null) {
			return Map.of();
		}
		return quotedMessageLookup.resolve(ids, visibleBoardIds);
	}

	private static final Pattern LIST_ITEM_BLOCK = Pattern.compile(
			"(\\[list[^\\]]*\\])((?:(?!\\[list[^\\]]*\\]|\\[/list\\]).)*)(\\[/list\\])",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private String convertListItems(String input) {
		if (input == null || input.indexOf("[*]") < 0) {
			return input;
		}
		String current = input;
		String previous;
		int guard = 0;
		do {
			previous = current;
			Matcher matcher = LIST_ITEM_BLOCK.matcher(current);
			StringBuilder out = new StringBuilder();
			while (matcher.find()) {
				String body = matcher.group(2);
				if (body.indexOf("[*]") >= 0) {
					String[] parts = body.split("\\[\\*\\]", -1);
					StringBuilder rebuilt = new StringBuilder(parts[0]);
					for (int i = 1; i < parts.length; i++) {
						String item = parts[i].trim();
						if (!item.isEmpty()) {
							rebuilt.append("[li]").append(item).append("[/li]");
						}
					}
					body = rebuilt.toString();
				}
				matcher.appendReplacement(out, Matcher.quoteReplacement(matcher.group(1) + body + matcher.group(3)));
			}
			matcher.appendTail(out);
			current = out.toString();
		} while (!current.equals(previous) && current.indexOf("[*]") >= 0 && ++guard < 5);
		return current;
	}

	private String parseTextInternal(String input) {
		input = convertListItems(input);
		char[] inputChars = null;

		try {
			inputChars = ZfgcStringUtils.getUnderlyingStringArray(input.replace("\n", "<br/>"));
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			LOGGER.error("bbcode input buffer reflection failure", e);
			throw new RuntimeException(e);
		}
		final int length = inputChars.length;
		final MutableInt negativeSentinel = new MutableInt(-1);
		StringBuilder output = new StringBuilder();
		StringBuilder currentBuffer = new StringBuilder();
		StringBuilder sideBuffer = new StringBuilder();
		String currentState = null;
		String currentCode = null;
		int lastKnownFreshPosition = 0;
		int i = 0;
		int openBracePos = -1;
		int closeBracePos = -1;
		int attributeBeginPos = -1;
		Stack<String> states = new Stack<>();
		Stack<String> codes = new Stack<>();
		Stack<String> closeTags = new Stack<>();
		MutableInt contentAttPos = new MutableInt(-1);
		Map<String, Integer> counts = new HashMap<>();
		for (String code : validBbCodes.keySet()) {
			counts.put(code, 0);
		}
		MutableBoolean outputContent = new MutableBoolean(true);

		for (i = 0; i < length; i++) {
			boolean isClosingBrace = false;
			if (inputChars[i] == '[') {
				String candidateCode = "";
				i++;

				if (i >= length) {
					currentBuffer.append(inputChars, lastKnownFreshPosition, length - lastKnownFreshPosition);
					break;
				}

				if (inputChars[i] == '/') {
					isClosingBrace = true;
					closeBracePos = i - 1;
					i++;
				} else {
					openBracePos = i - 1;
				}

				do {
					if (i >= length) {
						break;
					}

					candidateCode += inputChars[i];
					i++;
				} while (i < length
						&& ((Character.toLowerCase(inputChars[i]) >= 'a' && Character.toLowerCase(inputChars[i]) <= 'z')
								|| (inputChars[i] >= '0' && inputChars[i] <= '9'))
						&& (inputChars[i] != ' ' && inputChars[i] != '='));

				candidateCode = candidateCode.toUpperCase();
				if (validBbCodes.containsKey(candidateCode)) {
					attributeBeginPos = i;
					boolean foundBbCode = true;
					while (i < length && inputChars[i] != ']') {
						if (inputChars[i] == '[') {
							currentBuffer.append(inputChars, lastKnownFreshPosition, (--i) - lastKnownFreshPosition);
							foundBbCode = false;
							break;
						}
						i++;
					}
					if (i >= length) {
						foundBbCode = false;
					}

					if (foundBbCode) {
						if (isClosingBrace) {
							if (currentCode == null)
								continue;

							if (states.size() == 0 || (!currentCode.equals(candidateCode)
									&& validBbCodes.get(currentCode).getProcessContentFlag())) {
								output.append(inputChars, lastKnownFreshPosition, i - lastKnownFreshPosition + 1);
								lastKnownFreshPosition = i + 1;

								if (states.size() > 0) {
									output.append(closeTags.isEmpty()
											? validBbCodes.get(candidateCode).getEndTag()
											: closeTags.pop());
									counts.replace(candidateCode, counts.get(candidateCode) - 1);
									states.pop();
									codes.pop();
									if (states.size() == 0) {
										currentState = "";
										currentCode = "";
									} else {
										currentState = states.peek();
										currentCode = codes.peek();
									}
								}
							} else if (currentCode.equals(candidateCode)) {
								if (validBbCodes.get(currentCode).getProcessContentFlag()
										|| (currentCode + "0").equals(states.peek())) {

									if (contentAttPos.compareTo(negativeSentinel) > 0) {
										sideBuffer.append(inputChars, lastKnownFreshPosition,
												closeBracePos - lastKnownFreshPosition);
										output.replace(contentAttPos.intValue(), contentAttPos.intValue() + 5,
												sideBuffer.toString());
										contentAttPos.setValue(-1);
										sideBuffer.delete(0, sideBuffer.length());
									}

									if (outputContent.booleanValue()) {
										if (!validBbCodes.get(currentCode).getProcessContentFlag()) {
											appendLiteral(output, inputChars, lastKnownFreshPosition,
													closeBracePos - lastKnownFreshPosition);
										} else {
											output.append(inputChars, lastKnownFreshPosition,
													closeBracePos - lastKnownFreshPosition);
										}
									}
									output.append(closeTags.isEmpty()
											? validBbCodes.get(candidateCode).getEndTag()
											: closeTags.pop());
									lastKnownFreshPosition = i + 1;
									outputContent.setValue(true);
								}

								counts.replace(candidateCode, counts.get(candidateCode) - 1);
								states.pop();
								codes.pop();
								if (states.size() == 0) {
									currentState = "";
									currentCode = "";
								} else {
									currentState = states.peek();
									currentCode = codes.peek();
								}
							}
						} else if (Boolean.TRUE.equals(validBbCodes.get(candidateCode).getSelfClosingFlag())
								&& (currentCode == null || currentCode.equals("")
										|| validBbCodes.get(currentCode).getProcessContentFlag())) {
							if (lastKnownFreshPosition != openBracePos) {
								output.append(inputChars, lastKnownFreshPosition,
										openBracePos - lastKnownFreshPosition);
							}
							output.append(validBbCodes.get(candidateCode).getEndTag());
							lastKnownFreshPosition = i + 1;
						} else {
							if (currentCode == null || currentCode.equals("")
									|| validBbCodes.get(currentCode).getProcessContentFlag()) {
								char[] attributes = new char[i - attributeBeginPos];
								for (int j = 0; j < i - attributeBeginPos; j++) {
									attributes[j] = inputChars[attributeBeginPos + j];
								}

								String[] matchedCloseTag = new String[] { null };
								String parsedTag = processAttributes(validBbCodes.get(candidateCode), attributes,
										contentAttPos, matchedCloseTag, outputContent);

								if (parsedTag == null) {
									if (states.size() == 0 || validBbCodes.get(currentCode).getProcessContentFlag()) {
										if (lastKnownFreshPosition != openBracePos) {
											output.append(inputChars, lastKnownFreshPosition,
													openBracePos - lastKnownFreshPosition);
										}
										output.append(inputChars, openBracePos, i - openBracePos + 1);
										lastKnownFreshPosition = i + 1;
									}
									continue;
								}

								if (states.size() == 0 || validBbCodes.get(currentCode).getProcessContentFlag()) {
									if (lastKnownFreshPosition != openBracePos) {
										output.append(inputChars, lastKnownFreshPosition,
												openBracePos - lastKnownFreshPosition);
									}
									output.append(parsedTag);

									contentAttPos.setValue(output.indexOf("{{c}}"));
									currentCode = candidateCode;
									lastKnownFreshPosition = i + 1;
								}

								currentState = candidateCode + counts.get(candidateCode);
								states.push(currentState);
								codes.push(candidateCode);
								closeTags.push(matchedCloseTag[0] != null
										? matchedCloseTag[0]
										: validBbCodes.get(candidateCode).getEndTag());
								counts.replace(candidateCode, counts.get(candidateCode) + 1);
							}
						}
					}
				} else {
					if (inLiteralBlock(currentCode, outputContent)) {
						appendLiteral(output, inputChars, lastKnownFreshPosition, i - lastKnownFreshPosition);
					} else {
						output.append(inputChars, lastKnownFreshPosition, i - lastKnownFreshPosition);
					}
					lastKnownFreshPosition = i;
				}
			}
		}

		if (inLiteralBlock(currentCode, outputContent)) {
			appendLiteral(output, inputChars, lastKnownFreshPosition, length - lastKnownFreshPosition);
		} else {
			output.append(inputChars, lastKnownFreshPosition, length - lastKnownFreshPosition);
		}

		while (!codes.isEmpty()) {
			String code = codes.pop();
			output.append(closeTags.isEmpty()
					? validBbCodes.get(code).getEndTag()
					: closeTags.pop());
			states.pop();
		}

		return outputSanitizer.sanitize(output.toString());
	}

	private static final Pattern LITERAL_BR = Pattern.compile("<br\\s*/?>", Pattern.CASE_INSENSITIVE);

	private boolean inLiteralBlock(String currentCode, MutableBoolean outputContent) {
		return currentCode != null && !currentCode.isEmpty()
				&& !validBbCodes.get(currentCode).getProcessContentFlag()
				&& outputContent.booleanValue();
	}

	private static void appendLiteral(StringBuilder output, char[] chars, int from, int len) {
		if (len <= 0) {
			return;
		}
		String text = LITERAL_BR.matcher(new String(chars, from, len)).replaceAll("\n");
		output.append(escapeHtml(text));
	}

	public String processAttributes(BBCodeConfig bbCode, char[] attributes, MutableInt contentAttPos) {
		String parsed = processAttributes(bbCode, attributes, contentAttPos, null, new MutableBoolean(true));
		return parsed != null ? parsed : "[" + bbCode.getCode() + new String(attributes) + "]";
	}

	private ParsedAttributes parseAttributeValues(BBCodeConfig bbCode, String attributeText) {
		String[] allAttributeNames = bbCode.getAllAttributeNamesAsString().split(",");
		Map<String, String> attributeValues = new TreeMap<>();

		TreeMap<Integer, String> namesByPos = new TreeMap<>();
		for (String attName : allAttributeNames) {
			if (attName.isEmpty()) {
				continue;
			}
			if (attName.equals("=")) {
				if (attributeText.startsWith("=")) {
					namesByPos.put(0, attName);
				}
				continue;
			}
			int pos = attributeText.indexOf(attName);
			while (pos != -1) {
				if (pos == 0 || Character.isWhitespace(attributeText.charAt(pos - 1))) {
					namesByPos.put(pos, attName);
					break;
				}
				pos = attributeText.indexOf(attName, pos + 1);
			}
		}

		Integer[] positions = namesByPos.keySet().toArray(new Integer[0]);
		for (int i = 0; i < positions.length; i++) {
			int start = positions[i];
			String name = namesByPos.get(start);
			int valueStart = start + name.length();
			int valueEnd = (i + 1 < positions.length) ? positions[i + 1] : attributeText.length();
			String value = attributeText.substring(valueStart, valueEnd).trim();
			attributeValues.put(name, value);
		}

		StringBuilder attFormat = new StringBuilder();
		for (String attName : allAttributeNames) {
			if (attributeValues.containsKey(attName)) {
				attFormat.append(attName);
			}
		}
		return new ParsedAttributes(attFormat.toString(), attributeValues);
	}

	public String processAttributes(BBCodeConfig bbCode, char[] attributes, MutableInt contentAttPos,
			String[] matchedCloseTagOut, MutableBoolean outputContent) {
		ParsedAttributes parsed = parseAttributeValues(bbCode, new String(attributes));
		Map<String, String> attributeValues = parsed.attributeValues();

		if (!bbCode.getAttributeConfig().containsKey(parsed.attFormat())) {
			return null;
		}

		BBCodeAttributeMode attMode = bbCode.getAttributeConfig().get(parsed.attFormat());
		if (matchedCloseTagOut != null && matchedCloseTagOut.length > 0) {
			matchedCloseTagOut[0] = attMode.getCloseTag();
		}
		String output = attMode.getOpenTag();

		long namelessCount = attMode.getAttributes().stream()
				.filter(a -> "=".equals(a.getName())).count();
		String[] namelessParts = null;
		int namelessIdx = 0;

		for (int i = 0; i < attMode.getAttributes().size(); i++) {
			BBCodeAttribute attribute = attMode.getAttributes().get(i);
			String attName = attribute.getName();
			String rawValue;
			if ("=".equals(attName) && namelessCount > 1) {
				if (namelessParts == null) {
					String full = attributeValues.get("=");
					namelessParts = full == null ? new String[0] : full.split(",", -1);
				}
				rawValue = namelessIdx < namelessParts.length ? namelessParts[namelessIdx] : "";
				namelessIdx++;
			} else {
				rawValue = attributeValues.get(attName);
			}
			String value = attribute.transformValue(rawValue);
			output = output.replace(attribute.getAttributeIndex(), value);
		}

		if (output.contains("{{msg.")) {
			output = resolveQuoteMsgPlaceholders(output, attributeValues.get("msg="),
					attributeValues.get("author="));
		}

		if (attMode.getContentIsAttributeFlag()) {
			contentAttPos.setValue(output.indexOf("{{c}}"));

			if (!attMode.getOutputContentFlag()) {
				outputContent.setValue(false);
			}
		}

		return output.toString();
	}

	private String resolveQuoteMsgPlaceholders(String output, String msgIdString, String authorFallback) {
		QuotedMessageLookup.Resolved resolved = null;
		Integer msgId = null;
		Map<Integer, QuotedMessageLookup.Resolved> resolvedByMsgId = resolvedQuotes.get();
		if (resolvedByMsgId != null && msgIdString != null) {
			try {
				msgId = Integer.parseInt(msgIdString.trim());
				resolved = resolvedByMsgId.get(msgId);
			} catch (NumberFormatException ignore) {}
		}

		if (resolved == null || !resolved.permitted()) {
			String neutralAuthor = authorFallback != null && !authorFallback.isBlank()
					? escapeHtml(authorFallback.trim())
					: "(unavailable)";
			return output.replace("{{msg.author}}", neutralAuthor)
					.replace("{{msg.date}}", "")
					.replace("{{msg.link}}", "#");
		}

		String author = resolved.authorDisplayName() != null
				? escapeHtml(resolved.authorDisplayName())
				: "(unknown)";
		if (resolved.authorUserId() != null) {
			author = "<a class=\"bb-resource-link\" href=\"/user/profile/" + resolved.authorUserId()
					+ "\" data-resource=\"member\" data-user-id=\"" + resolved.authorUserId() + "\">"
					+ author + "</a>";
		}
		String date = resolved.createdTs() != null ? formatQuoteDate(resolved.createdTs()) : "";
		String link = resolved.threadId() != null && msgId != null
				? "/forum/thread/" + resolved.threadId() + "/" + resolved.page() + "#msg" + msgId
				: "#";

		return output.replace("{{msg.author}}", author)
				.replace("{{msg.date}}", date)
				.replace("{{msg.link}}", link);
	}

	private static String formatQuoteDate(OffsetDateTime ts) {
		LocalDate today = LocalDate.now();
		LocalDate postedDate = ts.toLocalDate();
		if (postedDate.equals(today)) {
			return "Today at " + QUOTE_DATE_FORMATTER.format(ts).toLowerCase();
		}
		if (postedDate.equals(today.minusDays(1))) {
			return "Yesterday at " + QUOTE_DATE_FORMATTER.format(ts).toLowerCase();
		}
		return QUOTE_DATE_FORMATTER_FULL.format(ts);
	}

	private static String escapeHtml(String value) {
		StringBuilder out = new StringBuilder(value.length());
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			switch (c) {
			case '<': out.append("&lt;"); break;
			case '>': out.append("&gt;"); break;
			case '"': out.append("&quot;"); break;
			case '\'': out.append("&#39;"); break;
			case '&': out.append("&amp;"); break;
			default: out.append(c);
			}
		}
		return out.toString();
	}

	public record TagToken(boolean closing, String code, String attributeText, int endIndex) {}

	public TagToken readTag(String input, int bracketIndex) {
		int length = input.length();
		int cursor = bracketIndex + 1;
		if (cursor >= length) {
			return null;
		}
		boolean closing = false;
		if (input.charAt(cursor) == '/') {
			closing = true;
			cursor++;
		}
		int codeStart = cursor;
		while (cursor < length) {
			char c = input.charAt(cursor);
			boolean codeChar = (Character.toLowerCase(c) >= 'a' && Character.toLowerCase(c) <= 'z')
					|| (c >= '0' && c <= '9');
			if (!codeChar) {
				break;
			}
			cursor++;
		}
		if (cursor == codeStart) {
			return null;
		}
		String code = input.substring(codeStart, cursor).toUpperCase();
		int attributeStart = cursor;
		while (cursor < length && input.charAt(cursor) != ']') {
			if (input.charAt(cursor) == '[') {
				return null;
			}
			cursor++;
		}
		if (cursor >= length) {
			return null;
		}
		return new TagToken(closing, code, input.substring(attributeStart, cursor), cursor + 1);
	}

	public boolean isLiteralCode(String code) {
		BBCodeConfig config = validBbCodes.get(code);
		return config != null && Boolean.FALSE.equals(config.getProcessContentFlag());
	}

	public int findLiteralClose(String input, int start, String code) {
		int cursor = start;
		int length = input.length();
		while (cursor < length) {
			if (input.charAt(cursor) == '[') {
				TagToken tag = readTag(input, cursor);
				if (tag != null && tag.closing() && code.equals(tag.code())) {
					return tag.endIndex();
				}
			}
			cursor++;
		}
		return -1;
	}

	public int findQuoteCloser(String input, int start) {
		int depth = 1;
		int cursor = start;
		int length = input.length();
		while (cursor < length) {
			if (input.charAt(cursor) != '[') {
				cursor++;
				continue;
			}
			TagToken tag = readTag(input, cursor);
			if (tag == null) {
				cursor++;
				continue;
			}
			if (!tag.closing() && isLiteralCode(tag.code())) {
				int close = findLiteralClose(input, tag.endIndex(), tag.code());
				if (close < 0) {
					return -1;
				}
				cursor = close;
				continue;
			}
			if ("QUOTE".equals(tag.code())) {
				if (tag.closing()) {
					depth--;
					if (depth == 0) {
						return cursor;
					}
				} else {
					depth++;
				}
			}
			cursor = tag.endIndex();
		}
		return -1;
	}

	private String spliceMsgQuotes(String input, OffsetDateTime quotingCreatedTs, QuoteScope scope,
			List<String> splicedBodies) {
		StringBuilder out = new StringBuilder(input.length());
		int cursor = 0;
		int length = input.length();
		while (cursor < length) {
			if (input.charAt(cursor) != '[') {
				out.append(input.charAt(cursor));
				cursor++;
				continue;
			}
			TagToken tag = readTag(input, cursor);
			if (tag == null) {
				out.append(input.charAt(cursor));
				cursor++;
				continue;
			}
			if (!tag.closing() && isLiteralCode(tag.code())) {
				int close = findLiteralClose(input, tag.endIndex(), tag.code());
				if (close < 0) {
					out.append(input, cursor, length);
					return out.toString();
				}
				out.append(input, cursor, close);
				cursor = close;
				continue;
			}
			if (!tag.closing() && "QUOTE".equals(tag.code())) {
				Integer msgId = extractQuoteMsgId(tag.attributeText());
				if (msgId != null) {
					int bodyStart = tag.endIndex();
					int closerStart = findQuoteCloser(input, bodyStart);
					if (closerStart < 0) {
						out.append(input, cursor, tag.endIndex());
						cursor = tag.endIndex();
						continue;
					}
					TagToken closer = readTag(input, closerStart);
					int closerEnd = closer != null ? closer.endIndex() : closerStart + "[/quote]".length();
					String body = input.substring(bodyStart, closerStart);
					out.append(input, cursor, bodyStart);
					out.append(decideQuoteBody(msgId, body, quotingCreatedTs, scope, splicedBodies));
					out.append(input, closerStart, closerEnd);
					cursor = closerEnd;
					continue;
				}
			}
			out.append(input, cursor, tag.endIndex());
			cursor = tag.endIndex();
		}
		return out.toString();
	}

	private String decideQuoteBody(Integer msgId, String body, OffsetDateTime quotingCreatedTs, QuoteScope scope,
			List<String> splicedBodies) {
		QuotedMessageLookup.Resolved resolved = scope.byMsgId().get(msgId);
		NavigableMap<OffsetDateTime, String> revisions =
				resolved != null && resolved.permitted() ? resolved.revisionsByCreatedTs() : null;
		Map.Entry<OffsetDateTime, String> floor = revisions == null ? null : revisions.floorEntry(quotingCreatedTs);
		if (floor != null) {
			if (floor.getValue() == null || floor.getValue().isBlank()) {
				return QUOTE_UNAVAILABLE_PLACEHOLDER;
			}
			String html = scope.renderedBodyHtml().get(new RevisionKey(msgId, floor.getKey()));
			if (html == null) {
				return QUOTE_UNAVAILABLE_PLACEHOLDER;
			}
			int index = splicedBodies.size();
			splicedBodies.add(html);
			return SENTINEL_OPEN + Integer.toString(index) + SENTINEL_CLOSE;
		}
		if (body == null || body.isBlank()) {
			return QUOTE_UNAVAILABLE_PLACEHOLDER;
		}
		return body;
	}

	private static String substituteSentinels(String html, List<String> splicedBodies) {
		Matcher matcher = SENTINEL_PATTERN.matcher(html);
		StringBuilder out = new StringBuilder(html.length());
		while (matcher.find()) {
			int index = Integer.parseInt(matcher.group(1));
			String replacement = index < splicedBodies.size() ? splicedBodies.get(index) : "";
			matcher.appendReplacement(out, Matcher.quoteReplacement(replacement));
		}
		matcher.appendTail(out);
		return out.toString();
	}

	private String spliceEmptyMsgQuoteFallback(String input,
			Map<Integer, QuotedMessageLookup.Resolved> resolvedByMsgId) {
		StringBuilder out = new StringBuilder(input.length());
		int cursor = 0;
		int length = input.length();
		while (cursor < length) {
			if (input.charAt(cursor) != '[') {
				out.append(input.charAt(cursor));
				cursor++;
				continue;
			}
			TagToken tag = readTag(input, cursor);
			if (tag == null) {
				out.append(input.charAt(cursor));
				cursor++;
				continue;
			}
			if (!tag.closing() && isLiteralCode(tag.code())) {
				int close = findLiteralClose(input, tag.endIndex(), tag.code());
				if (close < 0) {
					out.append(input, cursor, length);
					return out.toString();
				}
				out.append(input, cursor, close);
				cursor = close;
				continue;
			}
			if (!tag.closing() && "QUOTE".equals(tag.code())) {
				Integer msgId = extractQuoteMsgId(tag.attributeText());
				if (msgId != null) {
					int bodyStart = tag.endIndex();
					int closerStart = findQuoteCloser(input, bodyStart);
					if (closerStart >= 0 && input.substring(bodyStart, closerStart).isBlank()) {
						TagToken closer = readTag(input, closerStart);
						int closerEnd = closer != null ? closer.endIndex() : closerStart + "[/quote]".length();
						out.append(input, cursor, bodyStart);
						out.append(currentRevisionQuoteBody(msgId, resolvedByMsgId));
						out.append(input, closerStart, closerEnd);
						cursor = closerEnd;
						continue;
					}
				}
			}
			out.append(input, cursor, tag.endIndex());
			cursor = tag.endIndex();
		}
		return out.toString();
	}

	private String currentRevisionQuoteBody(Integer msgId,
			Map<Integer, QuotedMessageLookup.Resolved> resolvedByMsgId) {
		QuotedMessageLookup.Resolved resolved = resolvedByMsgId == null ? null : resolvedByMsgId.get(msgId);
		NavigableMap<OffsetDateTime, String> revisions =
				resolved != null && resolved.permitted() ? resolved.revisionsByCreatedTs() : null;
		if (revisions == null) {
			return QUOTE_UNAVAILABLE_PLACEHOLDER;
		}
		Map.Entry<OffsetDateTime, String> current = revisions.floorEntry(OffsetDateTime.now(ZoneOffset.UTC));
		if (current == null || current.getValue() == null || current.getValue().isBlank()) {
			return QUOTE_UNAVAILABLE_PLACEHOLDER;
		}
		return current.getValue();
	}

	@PostConstruct
	public void loadBbCodeConfig() {
		LOGGER.info("Loading Bbcode config...");

		validBbCodes = bbCodeDataProvider.getBbCodeConfig();

		LOGGER.info("Finished loading Bbcode config.");
	}

	public List<BBCodeDataProvider.BbCodeToggle> listBbCodes() {
		return bbCodeDataProvider.getBbCodeToggles();
	}

	public BBCodeDataProvider.BbCodeToggle setBbCodeEnabled(String code, boolean enabled) {
		BBCodeDataProvider.BbCodeToggle toggled = bbCodeDataProvider.setBbCodeEnabled(code, enabled)
				.orElseThrow(ZfgcNotFoundException::new);
		loadBbCodeConfig();
		return toggled;
	}

}
