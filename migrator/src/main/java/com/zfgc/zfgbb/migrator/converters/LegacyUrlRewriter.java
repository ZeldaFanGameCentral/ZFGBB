package com.zfgc.zfgbb.migrator.converters;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LegacyUrlRewriter {

	private static final Pattern URL_BBCODE = Pattern.compile(
			"\\[(url|iurl)=([^\\]]+)\\](.*?)\\[/(?:url|iurl)\\]",
			Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	private static final Pattern QUOTE_OPEN = Pattern.compile(
			"\\[quote([^\\]]*)\\]",
			Pattern.CASE_INSENSITIVE);

	private static final Pattern BARE_URL = Pattern.compile(
			"(?<![=\\w])https?://(?:www\\.)?zfgc\\.com(?:/forum)?/?index\\.php(?:#\\??|\\?)[^\\s\\[\\]\"'<>]+",
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

	private static final Pattern HOST_ZFGC = Pattern.compile(
			"https?://(?:www\\.)?zfgc\\.com", Pattern.CASE_INSENSITIVE);

	private LegacyUrlRewriter() {
	}

	public static String rewrite(String body, Map<Integer, Integer> smfToZfgbbAttachId) {
		if (body == null) {
			return null;
		}
		String result = body;
		result = rewriteUrlBbcodes(result);
		result = rewriteQuoteLink(result);
		result = rewriteBareUrls(result);
		result = rewriteAttachRefs(result, smfToZfgbbAttachId);
		return result;
	}

	static String rewriteUrlBbcodes(String body) {
		Matcher m = URL_BBCODE.matcher(body);
		StringBuilder out = new StringBuilder();
		while (m.find()) {
			String url = m.group(2).trim();
			String label = m.group(3);
			String replacement = mapSmfUrlToBbcode(url, label);
			if (replacement != null) {
				m.appendReplacement(out, Matcher.quoteReplacement(replacement));
			} else {
				m.appendReplacement(out, Matcher.quoteReplacement(m.group()));
			}
		}
		m.appendTail(out);
		return out.toString();
	}

	static String rewriteQuoteLink(String body) {
		Matcher openMatcher = QUOTE_OPEN.matcher(body);
		StringBuilder out = new StringBuilder();
		while (openMatcher.find()) {
			String attrs = openMatcher.group(1);
			Matcher linkMatcher = QUOTE_LINK_TOPIC_MSG.matcher(attrs);
			if (linkMatcher.find()) {
				String thread = linkMatcher.group(1);
				String msg = linkMatcher.group(2);
				String rewrittenAttrs = linkMatcher.replaceFirst(
						" thread=" + thread + " msg=" + msg);
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

	static String rewriteBareUrls(String body) {
		Matcher m = BARE_URL.matcher(body);
		StringBuilder out = new StringBuilder();
		while (m.find()) {
			String url = m.group();
			String replacement = mapSmfUrlToBbcode(url, url);
			if (replacement != null) {
				m.appendReplacement(out, Matcher.quoteReplacement(replacement));
			} else {
				m.appendReplacement(out, Matcher.quoteReplacement(url));
			}
		}
		m.appendTail(out);
		return out.toString();
	}

	static String rewriteAttachRefs(String body, Map<Integer, Integer> smfToZfgbbAttachId) {
		if (smfToZfgbbAttachId == null || smfToZfgbbAttachId.isEmpty()) {
			return body;
		}
		Matcher m = ATTACH_REF.matcher(body);
		StringBuilder out = new StringBuilder();
		while (m.find()) {
			int smfId = Integer.parseInt(m.group(1));
			Integer zfgbbId = smfToZfgbbAttachId.get(smfId);
			String replacement = zfgbbId != null ? "[attach=" + zfgbbId + "]" : m.group();
			m.appendReplacement(out, Matcher.quoteReplacement(replacement));
		}
		m.appendTail(out);
		return out.toString();
	}

	private static String mapSmfUrlToBbcode(String url, String label) {
		if (!HOST_ZFGC.matcher(url).find()) {
			return null;
		}
		Matcher topicMsg = PARAM_TOPIC_MSG.matcher(url);
		if (topicMsg.find()) {
			return "[thread=" + topicMsg.group(1) + " msg=" + topicMsg.group(2) + "]" + label + "[/thread]";
		}
		Matcher topic = PARAM_TOPIC.matcher(url);
		if (topic.find()) {
			return "[thread=" + topic.group(1) + "]" + label + "[/thread]";
		}
		Matcher board = PARAM_BOARD.matcher(url);
		if (board.find()) {
			return "[board=" + board.group(1) + "]" + label + "[/board]";
		}
		Matcher profile = PARAM_PROFILE_U.matcher(url);
		if (profile.find()) {
			return "[member=" + profile.group(1) + "]" + label + "[/member]";
		}
		Matcher resource = PARAM_RESOURCE.matcher(url);
		if (resource.find()) {
			return "[resource=" + resource.group(1) + "]" + label + "[/resource]";
		}
		Matcher game = PARAM_GAME.matcher(url);
		if (game.find()) {
			return "[game=" + game.group(1) + "]" + label + "[/game]";
		}
		return null;
	}
}
