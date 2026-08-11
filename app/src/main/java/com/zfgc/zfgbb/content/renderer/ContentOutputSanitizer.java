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
import java.util.Optional;
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

import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammar;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarHolder;
import com.zfgc.zfgbb.exception.InvalidBBCodeGrammarException;
import com.zfgc.zfgbb.model.forum.BBCodeAttribute;
import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;

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
				.addProtocols("iframe", "src", "https")
				.preserveRelativeLinks(true);
	}

	public String sanitize(String html) {
		if (html == null || html.isEmpty()) {
			return html;
		}
		Element body = cleanedBodyOf(html);
		enricher.enrichEveryTextNodeIn(body);
		return body.html();
	}

	Element cleanedBodyOf(String html) {
		Document.OutputSettings settings = new Document.OutputSettings().prettyPrint(false);
		Document clean = new Cleaner(SAFELIST).clean(
				Jsoup.parseBodyFragment(html, RELATIVE_LINK_BASE));
		clean.outputSettings(settings);
		for (Element element : clean.body().getAllElements()) {
			if (element.tagName().equals("iframe")) {
				Optional<String> embedUrl = normalizeYoutubeEmbed(element.attr("src"));
				if (embedUrl.isEmpty()) {
					element.remove();
					continue;
				}
				element.attr("src", embedUrl.get());
			}
			stripUnresolvableTarget(element, "href");
			rewriteLegacySpecialPageHref(element);
			stripUnresolvableTarget(element, "src");
			stripUnresolvableTarget(element, "cite");
			stripEveryClassTheRendererCannotEmit(element);
			sanitizeStyle(element);
		}
		return clean.body();
	}

	private static void rewriteLegacySpecialPageHref(Element element) {
		if (!element.normalName().equals("a"))
			return;
		String legacyHref = element.attr("href");
		if (!legacyHref.startsWith(LEGACY_SPECIAL_PAGE_PREFIX))
			return;
		String pageNameAndTail = legacyHref.substring(LEGACY_SPECIAL_PAGE_PREFIX.length());
		int pageNameEnd = 0;
		while (pageNameEnd < pageNameAndTail.length() && isAsciiLetter(pageNameAndTail.charAt(pageNameEnd)))
			pageNameEnd++;
		if (pageNameEnd == 0)
			return;
		element.attr("href", SPECIAL_PAGE_ROUTE_PREFIX
				+ pageNameAndTail.substring(0, pageNameEnd).toLowerCase(Locale.ROOT)
				+ pageNameAndTail.substring(pageNameEnd));
	}

	private static boolean isAsciiLetter(char candidate) {
		return (candidate >= 'a' && candidate <= 'z') || (candidate >= 'A' && candidate <= 'Z');
	}

	private static void stripUnresolvableTarget(Element element, String attributeName) {
		if (!element.hasAttr(attributeName)) {
			return;
		}
		safeHrefFor(element.attr(attributeName)).ifPresentOrElse(
				safe -> element.attr(attributeName, safe), () -> element.removeAttr(attributeName));
	}

	private static final Pattern SCHEMELESS_DOMAIN = Pattern.compile(
			"^[a-z0-9](?:[a-z0-9.-]*[a-z0-9])?\\.[a-z]{2,}(?:[:/?#].*)?$", Pattern.CASE_INSENSITIVE);

	private static final Pattern LINKABLE_URL_SCHEME = Pattern.compile("^(?:https?|ftp|mailto):",
			Pattern.CASE_INSENSITIVE);

	private static final String SCHEMELESS_DOMAIN_SCHEME = "https://";

	private static final String RELATIVE_LINK_BASE = "https://zfgbb.invalid/";

	public static Optional<String> safeHrefFor(String value) {
		if (value == null)
			return Optional.empty();
		String resolvable = valueABrowserWouldResolve(value);
		if (resolvable.isEmpty())
			return Optional.empty();
		if (resolvable.startsWith("#") || isSafeRelativeUrl(resolvable)
				|| LINKABLE_URL_SCHEME.matcher(resolvable).find())
			return Optional.of(resolvable);
		if (isSchemelessDomain(resolvable))
			return Optional.of(SCHEMELESS_DOMAIN_SCHEME + resolvable);
		return Optional.empty();
	}

	public static boolean isSafeRelativeUrl(String value) {
		return value != null && value.startsWith("/") && !value.startsWith("//") && value.indexOf('\\') < 0;
	}

	private static String valueABrowserWouldResolve(String value) {
		return value.replace("\t", "").replace("\n", "").replace("\r", "").trim();
	}

	private static boolean isSchemelessDomain(String value) {
		return value != null && SCHEMELESS_DOMAIN.matcher(value).matches();
	}

	Optional<String> normalizeYoutubeEmbed(String src) {
		if (src == null) {
			return Optional.empty();
		}
		URI parsed;
		try {
			parsed = new URI(src.trim());
		} catch (URISyntaxException notAnEmbedUrl) {
			return Optional.empty();
		}
		String path = parsed.getRawPath();
		if (!"https".equalsIgnoreCase(parsed.getScheme())
				|| parsed.getHost() == null
				|| !YOUTUBE_EMBED_HOSTS.contains(parsed.getHost().toLowerCase(Locale.ROOT))
				|| path == null
				|| !path.toLowerCase(Locale.ROOT).startsWith(YOUTUBE_EMBED_PATH_PREFIX)) {
			return Optional.empty();
		}
		String tail = path.substring(YOUTUBE_EMBED_PATH_PREFIX.length())
				+ (parsed.getRawQuery() == null ? "" : "?" + parsed.getRawQuery());
		try {
			tail = URLDecoder.decode(tail, StandardCharsets.UTF_8);
		} catch (IllegalArgumentException notPercentEncoded) {}
		if (YOUTUBE_VIDEO_ID.matcher(tail).matches()) {
			return Optional.of(youtubeEmbedPrefix + tail);
		}
		for (Pattern extractor : YOUTUBE_ID_EXTRACTORS) {
			Matcher videoId = extractor.matcher(tail);
			if (videoId.find()) {
				return Optional.of(youtubeEmbedPrefix + videoId.group(1));
			}
		}
		return Optional.empty();
	}

	private static void stripEveryClassTheRendererCannotEmit(Element element) {
		if (!element.hasAttr("class")) {
			return;
		}
		String kept = Arrays.stream(element.attr("class").split("\\s+"))
				.filter(token -> !token.isEmpty())
				.filter(RenderedTextEnricher::isAClassTheRendererCanEmit)
				.collect(Collectors.joining(" "));
		if (kept.isEmpty()) {
			element.removeAttr("class");
		} else {
			element.attr("class", kept);
		}
	}

	private void sanitizeStyle(Element element) {
		if (!element.hasAttr("style")) {
			return;
		}
		String cleaned = Arrays.stream(element.attr("style").split(";"))
				.map(String::trim)
				.filter(declaration -> !declaration.isEmpty())
				.filter(this::isSafeStyleDeclaration)
				.collect(Collectors.joining(";"));
		if (cleaned.isEmpty()) {
			element.removeAttr("style");
		} else {
			element.attr("style", cleaned);
		}
	}

	private boolean isSafeStyleDeclaration(String declaration) {
		int separator = declaration.indexOf(':');
		if (separator <= 0) {
			return false;
		}
		BBCodeGrammar.CustomPropertyBinding binding = grammarHolder.current().customProperties()
				.bindingByPropertyName().get(declaration.substring(0, separator).trim());
		return binding != null && binding.valuePolicy().admits(declaration.substring(separator + 1).trim());
	}
}
