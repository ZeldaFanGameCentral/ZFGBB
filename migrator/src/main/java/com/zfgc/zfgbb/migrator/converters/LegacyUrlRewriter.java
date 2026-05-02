package com.zfgc.zfgbb.migrator.converters;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
				"(?<![=\\w\\]>])https?://" + hostAlternation
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
		result = rewriteUrlBbcodes(result, maps);
		result = rewriteQuoteLink(result, maps);
		result = rewriteBareUrls(result, maps);
		result = rewriteAttachRefs(result, maps.attachmentMap());
		return result;
	}

	String rewriteUrlBbcodes(String body, LegacyIdMaps maps) {
		Matcher m = URL_BBCODE.matcher(body);
		StringBuilder out = new StringBuilder();
		while (m.find()) {
			String url = m.group(2).trim();
			String label = m.group(3);
			String replacement = mapSmfUrlToBbcode(url, label, maps);
			if (replacement != null) {
				m.appendReplacement(out, Matcher.quoteReplacement(replacement));
			} else {
				m.appendReplacement(out, Matcher.quoteReplacement(m.group()));
			}
		}
		m.appendTail(out);
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
				rewrittenAttrs = rewrittenAttrs.replaceAll("\\bauthor=\\S+", "");
				rewrittenAttrs = rewrittenAttrs.replaceAll("\\bdate=\\d+", "");
				rewrittenAttrs = rewrittenAttrs.replaceAll("\\s+", " ").trim();
				openMatcher.appendReplacement(out,
						Matcher.quoteReplacement("[quote " + rewrittenAttrs + "]"));
			} else {
				openMatcher.appendReplacement(out, Matcher.quoteReplacement(openMatcher.group()));
			}
		}
		openMatcher.appendTail(out);
		return out.toString();
	}

	String rewriteBareUrls(String body, LegacyIdMaps maps) {
		Matcher m = bareUrlPattern.matcher(body);
		StringBuilder out = new StringBuilder();
		while (m.find()) {
			String url = m.group();
			String replacement = mapSmfUrlToBbcode(url, url, maps);
			if (replacement != null) {
				m.appendReplacement(out, Matcher.quoteReplacement(replacement));
			} else {
				m.appendReplacement(out, Matcher.quoteReplacement(url));
			}
		}
		m.appendTail(out);
		return out.toString();
	}

	static String rewriteAttachRefs(String body, Map<Integer, Integer> attachmentMap) {
		if (attachmentMap == null || attachmentMap.isEmpty()) {
			return body;
		}
		Set<Integer> alreadyMapped = new HashSet<>(attachmentMap.values());
		Matcher m = ATTACH_REF.matcher(body);
		StringBuilder out = new StringBuilder();
		while (m.find()) {
			int id = Integer.parseInt(m.group(1));
			if (alreadyMapped.contains(id)) {
				m.appendReplacement(out, Matcher.quoteReplacement(m.group()));
				continue;
			}
			Integer zfgbbId = attachmentMap.get(id);
			String replacement = zfgbbId != null ? "[attach=" + zfgbbId + "]" : m.group();
			m.appendReplacement(out, Matcher.quoteReplacement(replacement));
		}
		m.appendTail(out);
		return out.toString();
	}

	private String mapSmfUrlToBbcode(String url, String label, LegacyIdMaps maps) {
		Matcher hostMatcher = hostPattern.matcher(url);
		if (!hostMatcher.find()) {
			return null;
		}
		String origin = appBaseUrl != null ? appBaseUrl : url.substring(0, hostMatcher.end());
		Matcher topicMsg = PARAM_TOPIC_MSG.matcher(url);
		if (topicMsg.find()) {
			Integer threadId = remap(maps.threadMap(), topicMsg.group(1));
			Integer msgId = remap(maps.messageMap(), topicMsg.group(2));
			if (threadId == null || msgId == null) {
				return null;
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
			String gameId = game.group(1);
			return "[game=" + gameId + "]"
					+ cleanLabel(label, url, "Game #" + gameId)
					+ "[/game]";
		}
		return null;
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
		return map.get(Integer.parseInt(legacyId));
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
