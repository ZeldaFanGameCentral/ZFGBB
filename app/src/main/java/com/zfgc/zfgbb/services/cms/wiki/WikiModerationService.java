package com.zfgc.zfgbb.services.cms.wiki;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.services.cms.CmsPageRenderer;
import com.zfgc.zfgbb.authorization.AuthorityTiers;
import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.templates.TemplateDataFetcher;
import com.zfgc.zfgbb.dataprovider.cms.ContentTemplateDataProvider;
import com.zfgc.zfgbb.dataprovider.cms.WikiDataProvider;
import com.zfgc.zfgbb.dataprovider.cms.WikiNamespaceDataProvider;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.cms.WikiPage;
import com.zfgc.zfgbb.model.cms.WikiRevision;
import com.zfgc.zfgbb.model.cms.WikiRevisionRef;
import com.zfgc.zfgbb.services.contentstore.AuthoringContentFormat;
import com.zfgc.zfgbb.wiki.WikiNamespaceRole;
import com.zfgc.zfgbb.wiki.WikiTitle;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class WikiModerationService {

	private final WikiDataProvider wikiDataProvider;

	private final ContentTemplateDataProvider contentTemplateDataProvider;

	private final CmsPageRenderer cmsPageRenderer;

	private final TemplateDataFetcher templateDataFetcher;

	private final AuthorityTiers authorityTiers;

	private final WikiNamespaceDataProvider namespaceData;

	private final WikiNamespaceEditGate wikiNamespaceEditGate;

	private final AuthoringContentFormat authoringContentFormat;

	public record TemplateSourceDirective(String source, String body, boolean directivePresent) {

		private static final Pattern DIRECTIVE_LINE = Pattern.compile("^\\[source=([^\\]\\r\\n]*)\\][ \\t]*(?:\\r?\\n|$)");

		public static TemplateSourceDirective parse(String content) {
			String safeContent = content == null ? "" : content;
			Matcher directiveMatcher = DIRECTIVE_LINE.matcher(safeContent);
			if (!directiveMatcher.find())
				return new TemplateSourceDirective(null, safeContent, false);
			String value = directiveMatcher.group(1).trim();
			return new TemplateSourceDirective(value.isEmpty() ? null : value,
					safeContent.substring(directiveMatcher.end()), true);
		}
	}

	public WikiRevisionRef submit(String slug, String content, String requestedContentFormat, String summary,
			User user) {
		if (slug == null || slug.isBlank() || content == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "slug and content are required");
		}
		if (content.length() > AuthoringContentFormat.MAX_AUTHORED_CONTENT_LENGTH) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "content is too long");
		}
		if (authorityTiers.isReadOnly(user))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Read-only accounts cannot submit wiki revisions.");
		WikiTitle canonical = namespaceData.resolve(slug);
		if (canonical.title() == null || canonical.title().isBlank())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "slug must name a page, not just a namespace");
		wikiNamespaceEditGate.requireNamespaceEditable(canonical.namespace(), user);
		WikiPage page = wikiDataProvider.findPage(canonical.path())
				.orElseGet(() -> wikiDataProvider.createPage(canonical, user.getUserId()));
		if (namespaceData.hasRole(page.getNamespace(), WikiNamespaceRole.TEMPLATE))
			requireResolvableSource(TemplateSourceDirective.parse(content));
		Integer wikiPageId = page.getWikiPageId();
		ContentFormat contentFormat = authoringContentFormat.forSupersedingContent(requestedContentFormat,
				() -> wikiDataProvider.contentFormatOfRevisionBeingSuperseded(wikiPageId));
		return wikiDataProvider.submitRevision(wikiPageId, content, contentFormat,
				summary, user.getUserId(), user.getDisplayName());
	}

	public List<WikiRevisionRef> getPendingRevisions() {
		return wikiDataProvider.getPendingRevisions();
	}

	public void approve(Integer revisionId) {
		WikiRevision revision = requirePending(revisionId);
		Optional<WikiPage> templatePage = wikiDataProvider.getPage(revision.getWikiPageId())
				.filter(page -> namespaceData.hasRole(page.getNamespace(), WikiNamespaceRole.TEMPLATE));
		if (templatePage.isEmpty()) {
			wikiDataProvider.approveRevision(revisionId);
			return;
		}
		TemplateSourceDirective directive = TemplateSourceDirective.parse(revision.getContent());
		requireResolvableSource(directive);
		wikiDataProvider.approveRevision(revisionId);
		publishTemplate(templatePage.get(), directive, ContentFormat.parse(revision.getContentFormat())
				.orElse(ContentFormat.BBCODE));
	}

	public void reject(Integer revisionId) {
		requirePending(revisionId);
		wikiDataProvider.rejectRevision(revisionId);
	}

	public Map<String, Object> preview(Integer revisionId) {
		WikiRevision revision = requireRevision(revisionId);
		Optional<WikiPage> page = wikiDataProvider.getPage(revision.getWikiPageId());
		WikiPage rendered = cmsPageRenderer.previewPage(
				page.map(WikiPage::getNamespace).orElse(null),
				page.map(WikiPage::getTitle).orElse(null),
				page.map(WikiPage::getSlug).orElse(null),
				revision.getContent(), revision.getContentFormat(), ContentScope.WIKI);
		return Map.of("contentParsed", rendered.getContentParsed() == null ? "" : rendered.getContentParsed());
	}

	private WikiRevision requireRevision(Integer revisionId) {
		return wikiDataProvider.getRevision(revisionId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such revision"));
	}

	private WikiRevision requirePending(Integer revisionId) {
		WikiRevision revision = requireRevision(revisionId);
		if (!WikiDataProvider.STATUS_PENDING.equals(revision.getStatus())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Revision is not pending");
		}
		return revision;
	}

	private void requireResolvableSource(TemplateSourceDirective directive) {
		if (directive.source() == null || templateDataFetcher.canResolve(directive.source()))
			return;
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Template source path '" + templateDataFetcher.pathPortion(directive.source())
						+ "' does not match any registered data source");
	}

	private void publishTemplate(WikiPage page, TemplateSourceDirective directive, ContentFormat contentFormat) {
		contentTemplateDataProvider.publishWikiTemplate(namespaceData.templateCode(page.getTitle()),
				page.getWikiPageId(), contentFormat, directive.body(), directive.source(),
				directive.directivePresent());
	}

}
