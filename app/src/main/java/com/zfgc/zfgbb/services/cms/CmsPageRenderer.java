package com.zfgc.zfgbb.services.cms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.ContentRenderingService;
import com.zfgc.zfgbb.model.cms.WikiPage;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CmsPageRenderer {

	private static final String HEADING_SELECTOR = "h1,h2,h3,h4,h5,h6";

	private static final String TOC_MARKER_CLASS = "bb-toc";

	private static final String TOC_SUPPRESSION_MARKER_CLASS = "bb-notoc";

	private static final int HEADINGS_THAT_EARN_AN_UNMARKED_TOC = 4;

	private final ContentRenderingService contentRenderingService;

	public static String unprefixedSlug(String namespace, String slug) {
		if (namespace == null || slug == null)
			return slug;
		String prefix = namespace + ":";
		return slug.startsWith(prefix) ? slug.substring(prefix.length()) : slug;
	}

	public WikiPage previewPage(String namespace, String title, String slug, String content, String contentFormat,
			ContentScope scope) {
		WikiPage page = new WikiPage();
		page.setNamespace(namespace);
		page.setTitle(title);
		page.setSlug(slug);
		page.setContent(content);
		page.setContentFormat(contentFormat);
		renderPage(page, scope);
		return page;
	}

	public void renderPage(WikiPage page, ContentScope scope) {
		if (page == null || page.getContent() == null) {
			return;
		}
		ContentFormat format = ContentFormat.parse(page.getContentFormat()).orElse(ContentFormat.BBCODE);
		Map<String, String> context = new HashMap<>();
		if (page.getSlug() != null) {
			context.put("pageSlug", unprefixedSlug(page.getNamespace(), page.getSlug()));
		}
		if (page.getNamespace() != null) {
			context.put("pageNamespace", page.getNamespace());
		}
		if (page.getTitle() != null) {
			context.put("pageTitle", page.getTitle());
		}
		page.setContentParsed(contentRenderingService.renderWithTemplates(page.getContent(), format, scope, context));
		decorateHeadings(page);
	}

	private void decorateHeadings(WikiPage page) {
		String html = page.getContentParsed();
		if (html == null || html.isEmpty()) {
			return;
		}
		Document document = Jsoup.parseBodyFragment(html);
		document.outputSettings(new Document.OutputSettings().prettyPrint(false));
		Element body = document.body();
		Map<String, Integer> seen = new HashMap<>();
		List<WikiPage.Heading> headings = new ArrayList<>();
		for (Element heading : body.select(HEADING_SELECTOR)) {
			String text = heading.text();
			String id = dedupedId(seen, text);
			headings.add(new WikiPage.Heading(levelOf(heading), text, id));
			heading.attr("id", id);
		}
		page.setContentParsed(body.html());
		page.setHeadings(headings);
		page.setToc(carriesMarker(body, TOC_MARKER_CLASS)
				|| (headings.size() >= HEADINGS_THAT_EARN_AN_UNMARKED_TOC
						&& !carriesMarker(body, TOC_SUPPRESSION_MARKER_CLASS)));
	}

	private static boolean carriesMarker(Element body, String markerClass) {
		return body.selectFirst("." + markerClass) != null;
	}

	private static int levelOf(Element heading) {
		return Integer.parseInt(heading.normalName().substring(1));
	}

	private static String dedupedId(Map<String, Integer> seen, String text) {
		String base = text.toLowerCase(Locale.ROOT)
				.replaceAll("[^a-z0-9]+", "-")
				.replaceAll("(^-+|-+$)", "");
		int count = seen.merge(base, 1, Integer::sum);
		return count > 1 ? base + "-" + count : base;
	}
}
