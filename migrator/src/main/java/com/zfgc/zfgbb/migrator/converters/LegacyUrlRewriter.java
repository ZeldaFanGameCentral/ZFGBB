package com.zfgc.zfgbb.migrator.converters;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LegacyUrlRewriter {

	private static final String DEFAULT_HOST_REGEX = "(?:www\\.)?zfgc\\.com";

	private static final Pattern URL_BBCODE = Pattern.compile(
			"\\[(url|iurl)=([^\\]]+)\\](.*?)\\[/(?:url|iurl)\\]",
			Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	private static final Pattern QUOTE_OPEN = Pattern.compile(
			"\\[quote([^\\]]*)\\]",
			Pattern.CASE_INSENSITIVE);

	private static final Pattern ATTACH_REF = Pattern.compile("\\[attach=(\\d+)\\]");





	private static final Pattern PARAM_TOPIC_MSG = Pattern.compile(
			"[?&;]topic=(\\d+)\\.msg(\\d+)", Pattern.CASE_INSENSITIVE);
	private static final Pattern PARAM_TOPIC = Pattern.compile(
			"[?&;]topic=(\\d+)", Pattern.CASE_INSENSITIVE);
	private static final Pattern PARAM_BOARD = Pattern.compile(
			"[?&;]board=(\\d+)", Pattern.CASE_INSENSITIVE);
	private static final Pattern PARAM_PROFILE_U = Pattern.compile(
			"action=profile(?:[;&][^]\\s]*)?[;&]u=(\\d+)", Pattern.CASE_INSENSITIVE);
	private static final Pattern PARAM_RESOURCE = Pattern.compile(
			"action=resources(?:[;&][^]\\s]*)?[;&]id=(\\d+)", Pattern.CASE_INSENSITIVE);
	private static final Pattern PARAM_GAME = Pattern.compile(
			"action=games(?:[;&][^]\\s]*)?[;&]id=(\\d+)", Pattern.CASE_INSENSITIVE);

	private static final Pattern QUOTE_LINK_TOPIC_MSG = Pattern.compile(
			"\\blink=topic=(\\d+)\\.msg(\\d+)(?:#msg\\d+)?", Pattern.CASE_INSENSITIVE);

	private static final Pattern WIKI_URL = Pattern.compile(
			"https?://wiki\\.zfgc\\.com/(?:index\\.php\\?title=|index\\.php/)?([^\\s\\[\\]\"'<>?#&]+)",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern WIKI_IMAGE_PATH = Pattern.compile(
			"^images/(?:thumb/)?[0-9a-f]/[0-9a-f]{2}/([^/]+)(?:/.*)?$", Pattern.CASE_INSENSITIVE);
	private static final Pattern BARE_WIKI_URL = Pattern.compile(
			"(?<![\\w\\]])(?<!\\[url=)(?<!\\[iurl=)https?://wiki\\.zfgc\\.com/[^\\s\\[\\]\"'<>]+",
			Pattern.CASE_INSENSITIVE);

	private static final LegacyUrlRewriter DEFAULT = new LegacyUrlRewriter(null, null);

	private final Pattern hostPattern;
	private final Pattern bareUrlPattern;
	private final String appBaseUrl;

	public LegacyUrlRewriter(String legacyHost, String appBaseUrl) {
		String hostAlternation = buildHostAlternation(legacyHost);
		this.hostPattern = Pattern.compile(
				"https?://" + hostAlternation,
				Pattern.CASE_INSENSITIVE);
		this.bareUrlPattern = Pattern.compile(
				"(?<![\\w\\]])(?<![^/]>)(?<!\\[url=)(?<!\\[iurl=)"
						+ "(?<!href=\")(?<!href=')(?<!src=\")(?<!src=')https?://" + hostAlternation
						+ "(?:/forum)?/?index\\.php(?:#\\??|\\?)[^\\s\\[\\]\"'<>]+",
				Pattern.CASE_INSENSITIVE);
		this.appBaseUrl = normalizeAppBaseUrl(appBaseUrl);
	}

	public static LegacyUrlRewriter forLegacyHost(String legacyHost) {
		return forLegacyHost(legacyHost, null);
	}

	public static LegacyUrlRewriter forLegacyHost(String legacyHost, String appBaseUrl) {
		boolean noLegacy = legacyHost == null || legacyHost.isBlank();
		boolean noBase = appBaseUrl == null || appBaseUrl.isBlank();
		if (noLegacy && noBase) {
			return DEFAULT;
		}
		return new LegacyUrlRewriter(legacyHost, appBaseUrl);
	}

	public static String rewrite(String body, LegacyIdMaps maps) {
		return DEFAULT.rewriteBody(body, maps);
	}

	public String rewriteBody(String body, LegacyIdMaps maps) {
		if (body == null) {
			return null;
		}
		String result = body;
		result = rewriteUrlBBCodes(result, maps);
		result = rewriteQuoteLink(result, maps);
		result = rewriteBareUrls(result, maps);
		result = rewriteBareWikiUrls(result);
		result = rewriteAttachRefs(result, maps.attachmentMap());
		return result;
	}

	String rewriteUrlBBCodes(String body, LegacyIdMaps maps) {
		Matcher urlMatcher = URL_BBCODE.matcher(body);
		StringBuilder out = new StringBuilder();
		while (urlMatcher.find()) {
			String url = urlMatcher.group(2).trim();
			String label = urlMatcher.group(3);
			String replacement = mapSmfUrlToBBCode(url, label, maps);
			if (replacement == null) {
				replacement = mapWikiUrlToBBCode(url, label);
			}
			if (replacement != null) {
				urlMatcher.appendReplacement(out, Matcher.quoteReplacement(replacement));
			} else {
				urlMatcher.appendReplacement(out, Matcher.quoteReplacement(urlMatcher.group()));
			}
		}
		urlMatcher.appendTail(out);
		return out.toString();
	}

	String rewriteQuoteLink(String body, LegacyIdMaps maps) {
		Matcher openMatcher = QUOTE_OPEN.matcher(body);
		StringBuilder out = new StringBuilder();
		while (openMatcher.find()) {
			String attrs = openMatcher.group(1);
			Matcher linkMatcher = QUOTE_LINK_TOPIC_MSG.matcher(attrs);
			if (linkMatcher.find()) {
				Integer threadId = remap(maps.threadMap(), linkMatcher.group(1));
				Integer msgId = remap(maps.messageMap(), linkMatcher.group(2));
				if (threadId == null || msgId == null) {
					openMatcher.appendReplacement(out, Matcher.quoteReplacement(openMatcher.group()));
					continue;
				}
				String rewrittenAttrs = linkMatcher.replaceFirst(
						" thread=" + threadId + " msg=" + msgId);
				String author = withoutRendererUnparseableBrackets(
						sliceSmfQuoteAttributes(rewrittenAttrs).get("author="));
				StringBuilder opener = new StringBuilder("[quote");
				if (author != null && !author.isEmpty()) {
					opener.append(" author=").append(author);
				}
				opener.append(" thread=").append(threadId).append(" msg=").append(msgId).append("]");
				openMatcher.appendReplacement(out, Matcher.quoteReplacement(opener.toString()));
			} else {
				openMatcher.appendReplacement(out, Matcher.quoteReplacement(openMatcher.group()));
			}
		}
		openMatcher.appendTail(out);
		return out.toString();
	}

	private static final List<String> SMF_QUOTE_ATTRIBUTE_NAMES =
			List.of("author=", "link=", "date=", "thread=", "msg=");

	static String withoutRendererUnparseableBrackets(String author) {
		if (author == null)
			return null;
		return author.replace("[", "").replace("]", "").trim();
	}

	static Map<String, String> sliceSmfQuoteAttributes(String attributeText) {
		NavigableMap<Integer, String> namesByPosition = new TreeMap<>();
		for (String name : SMF_QUOTE_ATTRIBUTE_NAMES) {
			int position = attributeText.indexOf(name);
			while (position != -1) {
				if (position == 0 || Character.isWhitespace(attributeText.charAt(position - 1))) {
					namesByPosition.put(position, name);
					break;
				}
				position = attributeText.indexOf(name, position + 1);
			}
		}
		Map<String, String> values = new LinkedHashMap<>();
		List<Integer> positions = List.copyOf(namesByPosition.keySet());
		for (int i = 0; i < positions.size(); i++) {
			int start = positions.get(i);
			String name = namesByPosition.get(start);
			int valueEnd = i + 1 < positions.size() ? positions.get(i + 1) : attributeText.length();
			values.put(name, attributeText.substring(start + name.length(), valueEnd).trim());
		}
		return values;
	}

	String rewriteBareUrls(String body, LegacyIdMaps maps) {
		Matcher bareUrlMatcher = bareUrlPattern.matcher(body);
		StringBuilder out = new StringBuilder();
		while (bareUrlMatcher.find()) {
			String url = bareUrlMatcher.group();
			String replacement = mapSmfUrlToBBCode(url, url, maps);
			if (replacement != null) {
				bareUrlMatcher.appendReplacement(out, Matcher.quoteReplacement(replacement));
			} else {
				bareUrlMatcher.appendReplacement(out, Matcher.quoteReplacement(url));
			}
		}
		bareUrlMatcher.appendTail(out);
		return out.toString();
	}

	static String rewriteAttachRefs(String body, Map<Integer, Integer> attachmentMap) {
		if (attachmentMap == null || attachmentMap.isEmpty()) {
			return body;
		}
		Set<Integer> alreadyRewritten = new HashSet<>(attachmentMap.values());
		alreadyRewritten.removeAll(attachmentMap.keySet());
		Matcher attachMatcher = ATTACH_REF.matcher(body);
		StringBuilder out = new StringBuilder();
		while (attachMatcher.find()) {
			Integer id = parseIntOrNull(attachMatcher.group(1));
			if (id == null) {
				attachMatcher.appendReplacement(out, Matcher.quoteReplacement(attachMatcher.group()));
				continue;
			}
			if (alreadyRewritten.contains(id)) {
				attachMatcher.appendReplacement(out, Matcher.quoteReplacement(attachMatcher.group()));
				continue;
			}
			Integer zfgbbId = attachmentMap.get(id);
			String replacement = zfgbbId != null ? "[attach=" + zfgbbId + "]" : attachMatcher.group();
			attachMatcher.appendReplacement(out, Matcher.quoteReplacement(replacement));
		}
		attachMatcher.appendTail(out);
		return out.toString();
	}

	private String mapSmfUrlToBBCode(String url, String label, LegacyIdMaps maps) {
		Matcher hostMatcher = hostPattern.matcher(url);
		if (!hostMatcher.find()) {
			return null;
		}
		String origin = appBaseUrl != null ? appBaseUrl : url.substring(0, hostMatcher.end());
		Matcher topicMsg = PARAM_TOPIC_MSG.matcher(url);
		if (topicMsg.find()) {
			Integer threadId = remap(maps.threadMap(), topicMsg.group(1));
			Integer msgId = remap(maps.messageMap(), topicMsg.group(2));
			if (threadId == null) {
				return null;
			}
			if (msgId == null) {
				return "[thread=" + threadId + "]"
						+ cleanLabel(label, url, origin + "/forum/thread/" + threadId + "/1")
						+ "[/thread]";
			}
			return "[thread=" + threadId + " msg=" + msgId + "]"
					+ cleanLabel(label, url, origin + "/forum/thread/" + threadId + "/1#msg" + msgId)
					+ "[/thread]";
		}
		Matcher topic = PARAM_TOPIC.matcher(url);
		if (topic.find()) {
			Integer threadId = remap(maps.threadMap(), topic.group(1));
			if (threadId == null) {
				return null;
			}
			return "[thread=" + threadId + "]"
					+ cleanLabel(label, url, origin + "/forum/thread/" + threadId + "/1")
					+ "[/thread]";
		}
		Matcher board = PARAM_BOARD.matcher(url);
		if (board.find()) {
			Integer boardId = remap(maps.boardMap(), board.group(1));
			if (boardId == null) {
				return null;
			}
			return "[board=" + boardId + "]"
					+ cleanLabel(label, url, origin + "/forum/board/" + boardId + "/1")
					+ "[/board]";
		}
		Matcher profile = PARAM_PROFILE_U.matcher(url);
		if (profile.find()) {
			Integer userId = remap(maps.userMap(), profile.group(1));
			if (userId == null) {
				return null;
			}
			return "[member=" + userId + "]"
					+ cleanLabel(label, url, origin + "/user/profile/" + userId)
					+ "[/member]";
		}
		Matcher resource = PARAM_RESOURCE.matcher(url);
		if (resource.find()) {
			String resourceId = resource.group(1);
			return "[resource=" + resourceId + "]"
					+ cleanLabel(label, url, "Resource #" + resourceId)
					+ "[/resource]";
		}
		Matcher game = PARAM_GAME.matcher(url);
		if (game.find()) {
			Integer projectId = maps.gameToProjectMap().get(Integer.valueOf(game.group(1)));
			if (projectId != null) {
				return "[project=" + projectId + "]"
						+ cleanLabel(label, url, "Project #" + projectId)
						+ "[/project]";
			}
		}
		return null;
	}

	String rewriteBareWikiUrls(String body) {
		Matcher bareMatcher = BARE_WIKI_URL.matcher(body);
		StringBuilder out = new StringBuilder();
		while (bareMatcher.find()) {
			String url = bareMatcher.group();
			String trimmedUrl = trimTrailingPunctuation(url);
			String tail = url.substring(trimmedUrl.length());
			String replacement = mapWikiUrlToBBCode(trimmedUrl, trimmedUrl);
			bareMatcher.appendReplacement(out,
					Matcher.quoteReplacement(replacement != null ? replacement + tail : url));
		}
		bareMatcher.appendTail(out);
		return out.toString();
	}

	static String mapWikiUrlToBBCode(String url, String label) {
		Matcher wikiMatcher = WIKI_URL.matcher(url.trim());
		if (!wikiMatcher.find()) {
			return null;
		}
		String slug = trimTrailingPunctuation(wikiMatcher.group(1));
		if (slug.isEmpty()) {
			return null;
		}
		Matcher image = WIKI_IMAGE_PATH.matcher(slug);
		if (image.matches()) {
			slug = "File:" + image.group(1);
		}
		return "[wiki=" + slug + "]"
				+ cleanLabel(label, url, slug.replace('_', ' '))
				+ "[/wiki]";
	}

	private static String trimTrailingPunctuation(String value) {
		int end = value.length();
		while (end > 0) {
			char c = value.charAt(end - 1);
			if (c == '.' || c == ',' || c == ';' || c == '!' || c == '?') {
				end--;
			} else if (c == ')' && value.lastIndexOf('(', end - 1) < 0) {
				end--;
			} else {
				break;
			}
		}
		return value.substring(0, end);
	}

	private static String cleanLabel(String label, String url, String fallback) {
		if (label == null) {
			return fallback;
		}
		String trimmed = label.trim();
		if (trimmed.isEmpty()) {
			return fallback;
		}
		if (trimmed.equalsIgnoreCase(url.trim())) {
			return fallback;
		}
		return label;
	}

	private static Integer remap(Map<Integer, Integer> map, String legacyId) {
		if (map == null) {
			return null;
		}
		Integer parsed = parseIntOrNull(legacyId);
		return parsed == null ? null : map.get(parsed);
	}

	private static Integer parseIntOrNull(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private static String buildHostAlternation(String legacyHost) {
		if (legacyHost == null || legacyHost.isBlank()) {
			return DEFAULT_HOST_REGEX;
		}
		return "(?:" + DEFAULT_HOST_REGEX + "|" + Pattern.quote(legacyHost.trim()) + ")";
	}

	private static String normalizeAppBaseUrl(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		String trimmed = value.trim();
		return trimmed.endsWith("/") ? trimmed.substring(0, trimmed.length() - 1) : trimmed;
	}
}
