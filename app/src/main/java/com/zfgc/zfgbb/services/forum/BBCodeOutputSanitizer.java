package com.zfgc.zfgbb.services.forum;

import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

@Component
public class BBCodeOutputSanitizer {

	private static final Pattern ALLOWED_URL_SCHEME = Pattern.compile("^(?:https?|ftp|mailto):", Pattern.CASE_INSENSITIVE);
	private static final Safelist SAFELIST = buildSafelist();

	private static Safelist buildSafelist() {
		return Safelist.relaxed()
				.addTags("iframe", "marquee", "hr", "tt")
				.addAttributes(":all", "class", "style")
				.addAttributes("a", "data-resource", "data-thread-id", "data-msg-id",
						"data-board-id", "data-user-id", "data-resource-id", "data-game-id")
				.addAttributes("iframe", "src", "width", "height", "frameborder", "allow", "allowfullscreen")
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
		String cleaned = Jsoup.clean(html, "", SAFELIST, settings);
		return stripDisallowedUrls(cleaned, settings);
	}

	private String stripDisallowedUrls(String html, Document.OutputSettings settings) {
		Document doc = Jsoup.parseBodyFragment(html);
		doc.outputSettings(settings);
		for (Element el : doc.body().getAllElements()) {
			stripIfNotAllowed(el, "href");
			stripIfNotAllowed(el, "src");
			stripIfNotAllowed(el, "cite");
		}
		return doc.body().html();
	}

	private static void stripIfNotAllowed(Element el, String attr) {
		if (!el.hasAttr(attr)) {
			return;
		}
		if (isAllowedUrl(el.attr(attr).trim())) {
			return;
		}
		el.removeAttr(attr);
	}

	private static boolean isAllowedUrl(String value) {
		if (value.isEmpty()) {
			return false;
		}
		if (value.startsWith("/") && !value.startsWith("//")) {
			return true;
		}
		if (value.startsWith("#")) {
			return true;
		}
		return ALLOWED_URL_SCHEME.matcher(value).find();
	}
}
