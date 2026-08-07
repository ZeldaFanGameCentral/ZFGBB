package com.zfgc.zfgbb.content.renderer;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeRenderer;
import com.zfgc.zfgbb.content.renderer.markdown.MarkdownRenderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class ContentRenderingService {

	static final ContentScope THE_SURFACE_A_QUOTED_BODY_WAS_WRITTEN_ON = ContentScope.FORUM;

	private final BBCodeRenderer bbCodeRenderer;
	private final MarkdownRenderer markdownRenderer;
	private final ContentOutputSanitizer outputSanitizer;
	private final SourceReferenceService sourceReferenceService;

	public ContentRenderingService(BBCodeRenderer bbCodeRenderer, MarkdownRenderer markdownRenderer,
			ContentOutputSanitizer outputSanitizer, SourceReferenceService sourceReferenceService) {
		this.bbCodeRenderer = bbCodeRenderer;
		this.markdownRenderer = markdownRenderer;
		this.outputSanitizer = outputSanitizer;
		this.sourceReferenceService = sourceReferenceService;
		sourceReferenceService.registerSourceBodyRenderer(
				(rawBody, contentFormat, quotingCreatedTs) -> renderContent(contentFormat, rawBody, quotingCreatedTs,
						THE_SURFACE_A_QUOTED_BODY_WAS_WRITTEN_ON));
	}

	public record QuotingPost(String rawText, OffsetDateTime createdTs) {
	}

	public interface QuoteScope extends AutoCloseable {
		@Override
		void close();
	}

	public QuoteScope openQuoteScope(Collection<QuotingPost> posts, Set<Integer> visibleBoardIds) {
		SourceReferenceService.ScopeRestore restore = sourceReferenceService.openScope(posts, visibleBoardIds);
		return () -> sourceReferenceService.closeScope(restore);
	}

	public String render(String source, ContentFormat format, ContentScope scope) {
		return render(source, format, scope, null);
	}

	public String render(String source, ContentFormat format, ContentScope scope,
			OffsetDateTime quotingCreatedTs) {
		if (source == null) {
			return "";
		}
		return outputSanitizer.sanitize(renderContent(format, source, quotingCreatedTs, scope));
	}

	public String plainText(String source, ContentFormat format, ContentScope scope) {
		return visibleTextOf(render(source, format, scope));
	}

	static String visibleTextOf(String html) {
		Document parsed = Jsoup.parse(html);
		parsed.outputSettings().prettyPrint(false);
		return parsed.wholeText();
	}

	public String renderWithTemplates(String source, ContentFormat format, ContentScope scope,
			Map<String, String> context) {
		return renderWithTemplates(source, format, scope, context, null);
	}

	public String renderWithTemplates(String source, ContentFormat format, ContentScope scope,
			Map<String, String> context, OffsetDateTime quotingCreatedTs) {
		if (source == null) {
			return "";
		}
		return outputSanitizer.sanitize(format == ContentFormat.MARKDOWN
				? markdownRenderer.render(source, quotingCreatedTs, scope, context)
				: bbCodeRenderer.render(source, quotingCreatedTs, scope, context));
	}

	private String renderContent(ContentFormat format, String source,
			OffsetDateTime quotingCreatedTs, ContentScope scope) {
		return format == ContentFormat.MARKDOWN
				? markdownRenderer.render(source, quotingCreatedTs, scope, Map.of())
				: bbCodeRenderer.render(source, quotingCreatedTs, scope, Map.of());
	}

}
