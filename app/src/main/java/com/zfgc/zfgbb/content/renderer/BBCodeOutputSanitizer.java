package com.zfgc.zfgbb.content.renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.dbo.SmileyDbo;
import com.zfgc.zfgbb.dbo.SmileyDboExample;
import com.zfgc.zfgbb.mappers.SmileyDboMapper;
import com.zfgc.zfgbb.util.ZfgcSecurityUtils;

import jakarta.annotation.PostConstruct;

@Component
public class BBCodeOutputSanitizer {

	private static final Pattern ALLOWED_URL_SCHEME = Pattern.compile("^(?:https?|ftp|mailto):", Pattern.CASE_INSENSITIVE);
	private static final Pattern YOUTUBE_EMBED_PREFIX = Pattern.compile(
			"^https://(?:www\\.)?youtube(?:-nocookie)?\\.com/embed/(.*)$", Pattern.CASE_INSENSITIVE);
	private static final Pattern YOUTUBE_VIDEO_ID = Pattern.compile("^[A-Za-z0-9_-]{6,20}$");
	private static final List<Pattern> YOUTUBE_ID_EXTRACTORS = List.of(
			Pattern.compile("[?&]v=([A-Za-z0-9_-]{6,20})"),
			Pattern.compile("/v/([A-Za-z0-9_-]{6,20})", Pattern.CASE_INSENSITIVE),
			Pattern.compile("youtu\\.be/([A-Za-z0-9_-]{6,20})", Pattern.CASE_INSENSITIVE));
	private static final Pattern BARE_URL = Pattern.compile(
			"(?i)\\b(?:https?://|ftp://|www\\.)[^\\s<>\\[\\]\"']+");
	private static final Pattern SCHEMELESS_DOMAIN = Pattern.compile(
			"^[a-z0-9](?:[a-z0-9.-]*[a-z0-9])?\\.[a-z]{2,}(?:[:/?#].*)?$", Pattern.CASE_INSENSITIVE);
	private static final Set<String> AUTOLINK_SKIP_TAGS = Set.of(
			"a", "pre", "code", "tt", "iframe", "script", "style", "textarea");

	public record SmileyToken(String code, String name, String label) {}

	@Autowired(required = false)
	private SmileyDboMapper smileyMapper;

	private List<SmileyToken> smileys = List.of();

	@PostConstruct
	public void loadSmilies() {
		if (smileyMapper == null) {
			return;
		}
		registerSmilies(smileyMapper.selectByExample(new SmileyDboExample()).stream()
				.filter(dbo -> !Boolean.TRUE.equals(dbo.getHiddenFlag()))
				.map(dbo -> new SmileyToken(dbo.getCode(), dbo.getName(),
						dbo.getLabel() == null ? dbo.getName() : dbo.getLabel()))
				.toList());
	}

	public void registerSmilies(List<SmileyToken> tokens) {
		List<SmileyToken> sorted = new ArrayList<>(tokens);
		sorted.sort((a, b) -> b.code().length() - a.code().length());
		smileys = List.copyOf(sorted);
	}
	private static final Set<String> ALLOWED_STYLE_PROPERTIES = Set.of(
			"color", "background-color", "font-size", "font-family", "text-shadow", "text-align", "list-style-type");
	private static final Safelist SAFELIST = buildSafelist();

	private static Safelist buildSafelist() {
		return Safelist.relaxed()
				.addTags("iframe", "marquee", "hr", "tt", "time")
				.addAttributes("time", "datetime")
				.addAttributes("iframe", "src", "width", "height", "frameborder", "allow", "allowfullscreen")
				.addAttributes(":all", "class")
				.addAttributes("a", "data-resource", "data-thread-id", "data-msg-id",
						"data-board-id", "data-user-id", "data-resource-id", "data-game-id",
						"data-wiki-slug", "data-project-id", "data-attachment-id")
				.addAttributes("div", "data-resource", "data-template-name",
						"data-widget-title")
				.addAttributes("span", "style")
				.addAttributes("ul", "style")
				.removeProtocols("a", "href", "ftp", "http", "https", "mailto")
				.removeProtocols("blockquote", "cite", "http", "https")
				.removeProtocols("cite", "cite", "http", "https")
				.removeProtocols("img", "src", "http", "https")
				.removeProtocols("q", "cite", "http", "https");
	}

	public String sanitize(String html) {
		if (html == null || html.isEmpty()) {
			return html;
		}
		Document.OutputSettings settings = new Document.OutputSettings().prettyPrint(false);
		Document clean = new Cleaner(SAFELIST).clean(Jsoup.parseBodyFragment(html));
		clean.outputSettings(settings);
		for (Element el : clean.body().getAllElements()) {
			if ("iframe".equals(el.tagName())) {
				String normalized = normalizeYoutubeEmbed(el.attr("src"));
				if (normalized == null) {
					el.remove();
					continue;
				}
				el.attr("src", normalized);
			}
			stripIfNotAllowed(el, "href");
			stripIfNotAllowed(el, "src");
			stripIfNotAllowed(el, "cite");
			sanitizeStyle(el);
		}
		autolink(clean.body());
		applySmilies(clean.body());
		return clean.body().html();
	}

	private static void autolink(Element body) {
		for (TextNode target : collectTextNodes(body)) {
			linkifyTextNode(target);
		}
	}

	private void applySmilies(Element body) {
		if (smileys.isEmpty()) {
			return;
		}
		for (TextNode target : collectTextNodes(body)) {
			smilifyTextNode(target);
		}
	}

	private static List<TextNode> collectTextNodes(Element body) {
		List<TextNode> targets = new ArrayList<>();
		NodeTraversor.traverse(new NodeVisitor() {
			@Override
			public void head(Node node, int depth) {
				if (node instanceof TextNode textNode && !textNode.isBlank() && !hasSkippedAncestor(textNode)) {
					targets.add(textNode);
				}
			}

			@Override
			public void tail(Node node, int depth) {
			}
		}, body);
		return targets;
	}

	private void smilifyTextNode(TextNode node) {
		String text = node.getWholeText();
		StringBuilder html = null;
		int i = 0;
		int last = 0;
		while (i < text.length()) {
			SmileyToken match = smileyAt(text, i);
			if (match == null) {
				i++;
				continue;
			}
			if (html == null) {
				html = new StringBuilder();
			}
			html.append(escape(text.substring(last, i)))
					.append("<span class=\"bb-smiley bb-smiley-").append(match.name())
					.append("\" title=\"").append(escape(match.label())).append("\">")
					.append(escape(match.code())).append("</span>");
			i += match.code().length();
			last = i;
		}
		if (html == null) {
			return;
		}
		html.append(escape(text.substring(last)));
		node.before(html.toString());
		node.remove();
	}

	private SmileyToken smileyAt(String text, int index) {
		if (index > 0 && Character.isLetterOrDigit(text.charAt(index - 1))) {
			return null;
		}
		for (SmileyToken token : smileys) {
			String code = token.code();
			if (!text.startsWith(code, index)) {
				continue;
			}
			int end = index + code.length();
			if (Character.isLetterOrDigit(code.charAt(code.length() - 1))
					&& end < text.length() && Character.isLetterOrDigit(text.charAt(end))) {
				continue;
			}
			return token;
		}
		return null;
	}

	private static boolean hasSkippedAncestor(Node node) {
		for (Node parent = node.parent(); parent != null; parent = parent.parent()) {
			if (parent instanceof Element element && AUTOLINK_SKIP_TAGS.contains(element.normalName())) {
				return true;
			}
		}
		return false;
	}

	private static void linkifyTextNode(TextNode node) {
		String text = node.getWholeText();
		Matcher matcher = BARE_URL.matcher(text);
		if (!matcher.find()) {
			return;
		}
		StringBuilder html = new StringBuilder();
		int last = 0;
		matcher.reset();
		while (matcher.find()) {
			String raw = matcher.group();
			int end = trailingTrimIndex(raw);
			String url = raw.substring(0, end);
			html.append(escape(text.substring(last, matcher.start())));
			String href = url.regionMatches(true, 0, "www.", 0, 4) ? "http://" + url : url;
			html.append("<a href=\"").append(escape(href)).append("\">").append(escape(url)).append("</a>");
			last = matcher.start() + end;
		}
		html.append(escape(text.substring(last)));
		node.before(html.toString());
		node.remove();
	}

	private static int trailingTrimIndex(String url) {
		int end = url.length();
		while (end > 0) {
			char c = url.charAt(end - 1);
			if (c == '.' || c == ',' || c == ';' || c == ':' || c == '!' || c == '?' || c == '"' || c == '\'') {
				end--;
			} else if (c == ')' && url.indexOf('(') < 0) {
				end--;
			} else {
				break;
			}
		}
		return end;
	}

	private static String escape(String value) {
		StringBuilder out = new StringBuilder(value.length());
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			switch (c) {
			case '&': out.append("&amp;"); break;
			case '<': out.append("&lt;"); break;
			case '>': out.append("&gt;"); break;
			case '"': out.append("&quot;"); break;
			case '\'': out.append("&#39;"); break;
			default: out.append(c);
			}
		}
		return out.toString();
	}

	private static void stripIfNotAllowed(Element el, String attr) {
		if (!el.hasAttr(attr)) {
			return;
		}
		String value = el.attr(attr).trim();
		if ("href".equals(attr) && SCHEMELESS_DOMAIN.matcher(value).matches()) {
			el.attr(attr, "http://" + value);
			return;
		}
		if (isAllowedUrl(value)) {
			return;
		}
		el.removeAttr(attr);
	}

	static String normalizeYoutubeEmbed(String src) {
		if (src == null) {
			return null;
		}
		Matcher prefix = YOUTUBE_EMBED_PREFIX.matcher(src.trim());
		if (!prefix.matches()) {
			return null;
		}
		String tail = prefix.group(1);
		try {
			tail = java.net.URLDecoder.decode(tail, java.nio.charset.StandardCharsets.UTF_8);
		} catch (IllegalArgumentException ignore) {}
		if (YOUTUBE_VIDEO_ID.matcher(tail).matches()) {
			return "https://www.youtube.com/embed/" + tail;
		}
		for (Pattern extractor : YOUTUBE_ID_EXTRACTORS) {
			Matcher m = extractor.matcher(tail);
			if (m.find()) {
				return "https://www.youtube.com/embed/" + m.group(1);
			}
		}
		return null;
	}

	private static boolean isAllowedUrl(String value) {
		if (value.isEmpty()) {
			return false;
		}
		if (ZfgcSecurityUtils.isSafeRelativeUrl(value)) {
			return true;
		}
		if (value.startsWith("#")) {
			return true;
		}
		return ALLOWED_URL_SCHEME.matcher(value).find();
	}

	private static void sanitizeStyle(Element el) {
		if (!el.hasAttr("style")) {
			return;
		}
		String cleaned = Arrays.stream(el.attr("style").split(";"))
				.map(String::trim)
				.filter(declaration -> !declaration.isEmpty())
				.filter(BBCodeOutputSanitizer::isSafeStyleDeclaration)
				.collect(Collectors.joining("; "));
		if (cleaned.isEmpty()) {
			el.removeAttr("style");
		} else {
			el.attr("style", cleaned);
		}
	}

	private static boolean isSafeStyleDeclaration(String declaration) {
		int separator = declaration.indexOf(':');
		if (separator <= 0) {
			return false;
		}
		String property = declaration.substring(0, separator).trim().toLowerCase();
		String value = declaration.substring(separator + 1).trim().toLowerCase();
		if (!ALLOWED_STYLE_PROPERTIES.contains(property)) {
			return false;
		}
		return !value.contains("url(")
				&& !value.contains("expression")
				&& !value.contains("javascript:")
				&& !value.contains("/*")
				&& !value.contains("<")
				&& !value.contains(">");
	}
}
