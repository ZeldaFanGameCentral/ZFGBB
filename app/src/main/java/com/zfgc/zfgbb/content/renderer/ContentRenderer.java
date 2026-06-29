package com.zfgc.zfgbb.content.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.model.cms.WikiPage;
import org.springframework.stereotype.Component;

@Component
public class ContentRenderer {

	private final BBCodeService bbCodeService;
	private final MarkdownRenderer markdownRenderer;
	private final TemplateExpander templateExpander;

	public ContentRenderer(BBCodeService bbCodeService, MarkdownRenderer markdownRenderer,
			TemplateExpander templateExpander) {
		this.bbCodeService = bbCodeService;
		this.markdownRenderer = markdownRenderer;
		this.templateExpander = templateExpander;
	}

	private static final Pattern SPECIAL_LINK = Pattern.compile("/wiki/Special:([A-Za-z]+)([^\"]*)");

	public String render(String source, ContentFormat format) {
		return render(source, format, null);
	}

	public String render(String source, ContentFormat format, java.time.OffsetDateTime quotingCreatedTs) {
		if (source == null) {
			return "";
		}
		String html = format == ContentFormat.MARKDOWN
				? markdownRenderer.render(source)
				: bbCodeService.parseText(source, quotingCreatedTs);
		return normalizeSpecialLinks(html);
	}

	private static String normalizeSpecialLinks(String html) {
		if (html == null || !html.contains("/wiki/Special:")) {
			return html;
		}
		return SPECIAL_LINK.matcher(html).replaceAll(match ->
				Matcher.quoteReplacement("/wiki/special/" + match.group(1).toLowerCase(Locale.ROOT)
						+ match.group(2)));
	}

	public String renderWithTemplates(String source, ContentFormat format, ContentScope scope,
			Map<String, String> context) {
		return renderWithTemplates(source, format, scope, context, null);
	}

	public String renderWithTemplates(String source, ContentFormat format, ContentScope scope,
			Map<String, String> context, java.time.OffsetDateTime quotingCreatedTs) {
		if (source != null) {
			source = templateExpander.expand(source, format, scope, context);
		}
		return render(source, format, quotingCreatedTs);
	}

	public void renderPage(WikiPage page, ContentScope scope) {
		if (page == null || page.getContent() == null) {
			return;
		}
		ContentFormat format = "MARKDOWN".equals(page.getContentFormat())
				? ContentFormat.MARKDOWN
				: ContentFormat.BBCODE;
		Map<String, String> context = new HashMap<>();
		if (page.getSlug() != null) {
			context.put("pageSlug", page.getSlug());
		}
		if (page.getNamespace() != null) {
			context.put("pageNamespace", page.getNamespace());
		}
		if (page.getTitle() != null) {
			context.put("pageTitle", page.getTitle());
		}
		page.setContentParsed(renderWithTemplates(page.getContent(), format, scope, context));
		decorateHeadings(page);
	}

	private static final Pattern HEADING = Pattern.compile("<h([1-6])([^>]*)>(.*?)</h\\1>", Pattern.DOTALL);
	private static final Pattern TAG = Pattern.compile("<[^>]+>");
	private static final Pattern HEX_ENTITY = Pattern.compile("&#x([0-9a-fA-F]+);");
	private static final Pattern DEC_ENTITY = Pattern.compile("&#(\\d+);");

	private void decorateHeadings(WikiPage page) {
		String html = page.getContentParsed();
		if (html == null || html.isEmpty()) {
			return;
		}
		Map<String, Integer> seen = new HashMap<>();
		List<WikiPage.Heading> headings = new ArrayList<>();
		Matcher headingMatcher = HEADING.matcher(html);
		StringBuilder out = new StringBuilder();
		while (headingMatcher.find()) {
			int level = Integer.parseInt(headingMatcher.group(1));
			String attrs = headingMatcher.group(2);
			String inner = headingMatcher.group(3);
			String text = decodeEntities(TAG.matcher(inner).replaceAll("")).trim();
			String id = dedupedId(seen, text);
			headings.add(new WikiPage.Heading(level, text, id));
			String replacement = attrs.contains("id=")
					? headingMatcher.group()
					: "<h" + level + attrs + " id=\"" + id + "\">" + inner + "</h" + level + ">";
			headingMatcher.appendReplacement(out, Matcher.quoteReplacement(replacement));
		}
		headingMatcher.appendTail(out);
		page.setContentParsed(out.toString());
		page.setHeadings(headings);
		page.setToc(html.contains("bb-toc") || (headings.size() >= 4 && !html.contains("bb-notoc")));
	}

	private static String dedupedId(Map<String, Integer> seen, String text) {
		String base = text.toLowerCase(Locale.ROOT)
				.replaceAll("[^a-z0-9]+", "-")
				.replaceAll("(^-+|-+$)", "");
		int count = seen.merge(base, 1, Integer::sum);
		return count > 1 ? base + "-" + count : base;
	}

	private static String decodeEntities(String text) {
		String result = HEX_ENTITY.matcher(text).replaceAll(match -> decodeCodePoint(match.group(0), match.group(1), 16));
		result = DEC_ENTITY.matcher(result).replaceAll(match -> decodeCodePoint(match.group(0), match.group(1), 10));
		return result.replace("&quot;", "\"")
				.replace("&apos;", "'")
				.replace("&lt;", "<")
				.replace("&gt;", ">")
				.replace("&nbsp;", " ")
				.replace("&amp;", "&");
	}

	private static String decodeCodePoint(String original, String digits, int radix) {
		try {
			int codePoint = Integer.parseInt(digits, radix);
			if (codePoint < 0 || codePoint > 0x10FFFF) {
				return Matcher.quoteReplacement(original);
			}
			return Matcher.quoteReplacement(new String(Character.toChars(codePoint)));
		} catch (NumberFormatException e) {
			return Matcher.quoteReplacement(original);
		}
	}
}
