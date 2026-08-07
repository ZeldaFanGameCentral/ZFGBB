package com.zfgc.zfgbb.content.renderer;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammar;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarHolder;
import com.zfgc.zfgbb.exception.InvalidBBCodeGrammarException;
import com.zfgc.zfgbb.model.forum.BBCodeAttribute;
import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;
import com.zfgc.zfgbb.security.LinkPolicy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContentOutputSanitizer {

	private static final Set<String> YOUTUBE_EMBED_HOSTS = Set.of(
			"youtube.com", "www.youtube.com", "youtube-nocookie.com", "www.youtube-nocookie.com");
	private static final String YOUTUBE_EMBED_PATH_PREFIX = "/embed/";
	private static final Pattern YOUTUBE_VIDEO_ID = Pattern.compile("^[A-Za-z0-9_-]{6,20}$");
	private static final List<Pattern> YOUTUBE_ID_EXTRACTORS = List.of(
			Pattern.compile("[?&]v=([A-Za-z0-9_-]{6,20})"),
			Pattern.compile("/v/([A-Za-z0-9_-]{6,20})", Pattern.CASE_INSENSITIVE),
			Pattern.compile("youtu\\.be/([A-Za-z0-9_-]{6,20})", Pattern.CASE_INSENSITIVE));
	private static final String LEGACY_SPECIAL_PAGE_PREFIX = "/wiki/Special:";
	private static final String SPECIAL_PAGE_ROUTE_PREFIX = "/wiki/special/";

	@Value("${zfgbb.media.youtube-embed-url:https://www.youtube.com/embed/}")
	private String youtubeEmbedPrefix = "https://www.youtube.com/embed/";

	private final RenderedTextEnricher enricher;

	private final BBCodeGrammarHolder grammarHolder;

	private static final Pattern A_CUSTOM_PROPERTY_FILLED_FROM_A_SLOT =
			Pattern.compile("(--bb-[a-z0-9-]+)\\s*:\\s*(\\{\\{\\d+\\}\\})");

	public BBCodeGrammar.PreparedCustomProperties theCustomPropertiesPreparedFrom(Map<String, BBCodeConfig> candidateGrammar) {
		Map<String, BBCodeGrammar.CustomPropertyBinding> bound = new HashMap<>();
		for (BBCodeConfig config : candidateGrammar.values())
			for (BBCodeAttributeMode mode : config.getAttributeConfig().values())
				bindEveryCustomPropertyFilledBy(mode, bound);
		return new BBCodeGrammar.PreparedCustomProperties(Map.copyOf(bound));
	}

	private static void bindEveryCustomPropertyFilledBy(BBCodeAttributeMode mode,
			Map<String, BBCodeGrammar.CustomPropertyBinding> bound) {
		Matcher filled = A_CUSTOM_PROPERTY_FILLED_FROM_A_SLOT.matcher(mode.getOpenTag());
		while (filled.find())
			for (BBCodeAttribute attribute : mode.getAttributes())
				if (attribute.getAttributeIndex().equals(filled.group(2)))
					bindTheSlotFilling(filled.group(1), attribute, bound);
	}

	private static void bindTheSlotFilling(String customProperty, BBCodeAttribute attribute,
			Map<String, BBCodeGrammar.CustomPropertyBinding> bound) {
		BBCodeGrammar.CustomPropertyBinding alreadyBound = bound.get(customProperty);
		if (alreadyBound != null && alreadyBound.dataType() != attribute.getDataType())
			throw InvalidBBCodeGrammarException.customPropertyFilledFromTwoDataTypes(customProperty,
					alreadyBound.dataType(), attribute);
		bound.put(customProperty, new BBCodeGrammar.CustomPropertyBinding(attribute.getDataType(), attribute.getValuePolicy()));
	}

	private static final Safelist SAFELIST = buildSafelist();

	private static Safelist buildSafelist() {
		return Safelist.relaxed()
				.addTags("iframe", "marquee", "hr", "tt", "time")
				.addAttributes("time", "datetime")
				.addAttributes("iframe", "src", "width", "height", "frameborder", "allow", "allowfullscreen")
				.addAttributes(":all", "class")
				.addAttributes("a", "data-resource", "data-thread-id", "data-msg-id",
						"data-board-id", "data-user-id", "data-resource-id",
						"data-wiki-slug", "data-project-id", "data-attachment-id")
				.addAttributes("div", "data-resource", "data-template-name",
						"data-widget-title")
				.addAttributes("span", "style", "title")
				.removeProtocols("a", "href", "ftp", "http", "https", "mailto")
				.removeProtocols("blockquote", "cite", "http", "https")
				.removeProtocols("cite", "cite", "http", "https")
				.removeProtocols("img", "src", "http", "https")
				.removeProtocols("q", "cite", "http", "https");
	}

	public String sanitize(String html, ContentScope surface) {
		if (html == null || html.isEmpty()) {
			return html;
		}
		Element body = theCleanedBodyOf(html, surface);
		enricher.enrichEveryTextNodeIn(body);
		return body.html();
	}

	Element theCleanedBodyOf(String html, ContentScope surface) {
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
			rewriteLegacySpecialPageHref(el);
			stripIfNotAllowed(el, "src");
			stripIfNotAllowed(el, "cite");
			stripEveryClassTheRendererCannotEmit(el);
			sanitizeStyle(el, surface);
		}
		return clean.body();
	}

	private static void rewriteLegacySpecialPageHref(Element el) {
		if (!"a".equals(el.normalName()))
			return;
		String legacyHref = el.attr("href");
		if (!legacyHref.startsWith(LEGACY_SPECIAL_PAGE_PREFIX))
			return;
		String pageNameAndTail = legacyHref.substring(LEGACY_SPECIAL_PAGE_PREFIX.length());
		int pageNameEnd = 0;
		while (pageNameEnd < pageNameAndTail.length() && isAsciiLetter(pageNameAndTail.charAt(pageNameEnd)))
			pageNameEnd++;
		if (pageNameEnd == 0)
			return;
		el.attr("href", SPECIAL_PAGE_ROUTE_PREFIX
				+ pageNameAndTail.substring(0, pageNameEnd).toLowerCase(Locale.ROOT)
				+ pageNameAndTail.substring(pageNameEnd));
	}

	private static boolean isAsciiLetter(char candidate) {
		return (candidate >= 'a' && candidate <= 'z') || (candidate >= 'A' && candidate <= 'Z');
	}

	private static void stripIfNotAllowed(Element el, String attr) {
		if (!el.hasAttr(attr)) {
			return;
		}
		LinkPolicy.theSafeHrefFor(el.attr(attr))
				.ifPresentOrElse(safe -> el.attr(attr, safe), () -> el.removeAttr(attr));
	}

	public static final String DEFAULT_YOUTUBE_EMBED_PREFIX = "https://www.youtube.com/embed/";

	String normalizeYoutubeEmbed(String src) {
		return normalizeYoutubeEmbed(src, youtubeEmbedPrefix);
	}

	static String normalizeYoutubeEmbed(String src, String prefixUrl) {
		if (src == null) {
			return null;
		}
		URI parsed;
		try {
			parsed = new URI(src.trim());
		} catch (URISyntaxException notAnEmbedUrl) {
			return null;
		}
		String path = parsed.getRawPath();
		if (!"https".equalsIgnoreCase(parsed.getScheme())
				|| parsed.getHost() == null
				|| !YOUTUBE_EMBED_HOSTS.contains(parsed.getHost().toLowerCase(Locale.ROOT))
				|| path == null
				|| !path.toLowerCase(Locale.ROOT).startsWith(YOUTUBE_EMBED_PATH_PREFIX)) {
			return null;
		}
		String tail = path.substring(YOUTUBE_EMBED_PATH_PREFIX.length())
				+ (parsed.getRawQuery() == null ? "" : "?" + parsed.getRawQuery());
		try {
			tail = URLDecoder.decode(tail, StandardCharsets.UTF_8);
		} catch (IllegalArgumentException ignore) {}
		if (YOUTUBE_VIDEO_ID.matcher(tail).matches()) {
			return prefixUrl + tail;
		}
		for (Pattern extractor : YOUTUBE_ID_EXTRACTORS) {
			Matcher videoId = extractor.matcher(tail);
			if (videoId.find()) {
				return prefixUrl + videoId.group(1);
			}
		}
		return null;
	}

	private static void stripEveryClassTheRendererCannotEmit(Element el) {
		if (!el.hasAttr("class")) {
			return;
		}
		String kept = Arrays.stream(el.attr("class").split("\\s+"))
				.filter(token -> !token.isEmpty())
				.filter(RenderedTextEnricher::isAClassTheRendererCanEmit)
				.collect(Collectors.joining(" "));
		if (kept.isEmpty()) {
			el.removeAttr("class");
		} else {
			el.attr("class", kept);
		}
	}

	private void sanitizeStyle(Element el, ContentScope surface) {
		if (!el.hasAttr("style")) {
			return;
		}
		String cleaned = Arrays.stream(el.attr("style").split(";"))
				.map(String::trim)
				.filter(declaration -> !declaration.isEmpty())
				.filter(declaration -> isSafeStyleDeclaration(declaration, surface))
				.collect(Collectors.joining(";"));
		if (cleaned.isEmpty()) {
			el.removeAttr("style");
		} else {
			el.attr("style", cleaned);
		}
	}

	private boolean isSafeStyleDeclaration(String declaration, ContentScope surface) {
		int separator = declaration.indexOf(':');
		if (separator <= 0) {
			return false;
		}
		BBCodeGrammar.CustomPropertyBinding binding = grammarHolder.current(surface).customProperties()
				.theBindingOfEachCustomProperty().get(declaration.substring(0, separator).trim());
		return binding != null && binding.valuePolicy().admits(declaration.substring(separator + 1).trim());
	}
}
