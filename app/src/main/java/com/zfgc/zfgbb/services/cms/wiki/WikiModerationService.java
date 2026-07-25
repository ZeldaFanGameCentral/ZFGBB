package com.zfgc.zfgbb.services.cms.wiki;

import java.util.List;
import java.util.Map;
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
import com.zfgc.zfgbb.dataprovider.cms.WikiDataProvider;
import com.zfgc.zfgbb.dataprovider.cms.WikiNamespaceDataProvider;
import com.zfgc.zfgbb.dbo.ContentTemplateDbo;
import com.zfgc.zfgbb.dbo.ContentTemplateDboExample;
import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDbo;
import com.zfgc.zfgbb.mappers.ContentTemplateDboMapper;
import com.zfgc.zfgbb.mapstruct.cms.WikiRevisionRefMap;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.cms.WikiPage;
import com.zfgc.zfgbb.model.cms.WikiRevisionRef;
import com.zfgc.zfgbb.services.system.AuthoringContentFormat;
import com.zfgc.zfgbb.wiki.WikiNamespaceRole;
import com.zfgc.zfgbb.wiki.WikiTitle;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class WikiModerationService {

	private final WikiDataProvider wikiDataProvider;

	private final ContentTemplateDboMapper contentTemplateMapper;

	private final CmsPageRenderer cmsPageRenderer;

	private final TemplateDataFetcher templateDataFetcher;

	private final AuthorityTiers authorityTiers;

	private final WikiRevisionRefMap wikiRevisionRefMap;

	private final WikiNamespaceDataProvider namespaceData;

	private final WikiAccessRules wikiAccessRules;

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
		if (authorityTiers.isReadOnly(user))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Read-only accounts cannot submit wiki revisions.");
		WikiTitle canonical = namespaceData.resolve(slug);
		if (canonical.title() == null || canonical.title().isBlank())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "slug must name a page, not just a namespace");
		wikiAccessRules.requireNamespaceEditable(canonical.namespace(), user);
		WikiPageDbo page = wikiDataProvider.findPage(canonical.path());
		if (page == null) {
			page = createPage(canonical);
		}
		if (namespaceData.hasRole(page.getNamespace(), WikiNamespaceRole.TEMPLATE))
			requireResolvableSource(TemplateSourceDirective.parse(content));
		Integer wikiPageId = page.getWikiPageId();
		ContentFormat contentFormat = authoringContentFormat.forSupersedingContent(requestedContentFormat,
				() -> wikiDataProvider.contentFormatOfRevisionBeingSuperseded(wikiPageId));
		WikiPageRevisionDbo revision = wikiDataProvider.submitRevision(wikiPageId, content, contentFormat,
				summary, user.getUserId(), user.getDisplayName());
		return wikiRevisionRefMap.toRef(revision);
	}

	public List<WikiRevisionRef> getPendingRevisions() {
		return wikiDataProvider.getPendingRevisions();
	}

	public void approve(Integer revisionId) {
		WikiPageRevisionDbo revision = requirePending(revisionId);
		WikiPageDbo page = wikiDataProvider.getPage(revision.getWikiPageId());
		if (page == null || !namespaceData.hasRole(page.getNamespace(), WikiNamespaceRole.TEMPLATE)) {
			wikiDataProvider.approveRevision(revision);
			return;
		}
		TemplateSourceDirective directive = TemplateSourceDirective.parse(revision.getContent());
		requireResolvableSource(directive);
		wikiDataProvider.approveRevision(revision);
		publishTemplate(page, directive, ContentFormat.parse(revision.getContentFormat())
				.orElse(ContentFormat.BBCODE));
	}

	public void reject(Integer revisionId) {
		wikiDataProvider.rejectRevision(requirePending(revisionId));
	}

	public Map<String, Object> preview(Integer revisionId) {
		WikiPageRevisionDbo revision = wikiDataProvider.getRevision(revisionId);
		if (revision == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such revision");
		}
		WikiPageDbo page = wikiDataProvider.getPage(revision.getWikiPageId());
		WikiPage rendered = cmsPageRenderer.previewPage(page == null ? null : page.getNamespace(),
				page == null ? null : page.getTitle(), page == null ? null : page.getSlug(), revision.getContent(),
				revision.getContentFormat(), ContentScope.WIKI);
		return Map.of("contentParsed", rendered.getContentParsed() == null ? "" : rendered.getContentParsed());
	}

	private WikiPageRevisionDbo requirePending(Integer revisionId) {
		WikiPageRevisionDbo revision = wikiDataProvider.getRevision(revisionId);
		if (revision == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such revision");
		}
		if (!WikiDataProvider.STATUS_PENDING.equals(revision.getStatus())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Revision is not pending");
		}
		return revision;
	}

	private WikiPageDbo createPage(WikiTitle canonical) {
		return wikiDataProvider.createPage(canonical.namespace(), canonical.title(), canonical.path());
	}

	private void requireResolvableSource(TemplateSourceDirective directive) {
		if (directive.source() == null || templateDataFetcher.canResolve(directive.source()))
			return;
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Template source path '" + templateDataFetcher.pathPortion(directive.source())
						+ "' does not match any registered data source");
	}

	private void publishTemplate(WikiPageDbo page, TemplateSourceDirective directive, ContentFormat contentFormat) {
		String code = namespaceData.templateCode(page.getTitle());
		ContentTemplateDboExample ex = new ContentTemplateDboExample();
		ex.createCriteria().andWikiPageIdEqualTo(page.getWikiPageId())
				.andContentFormatEqualTo(contentFormat.name());
		ContentTemplateDbo existing = contentTemplateMapper.selectByExample(ex).stream().findFirst().orElse(null);
		if (existing == null) {
			ContentTemplateDboExample seeded = new ContentTemplateDboExample();
			seeded.createCriteria().andCodeEqualTo(code).andContentFormatEqualTo(contentFormat.name())
					.andWikiPageIdIsNull();
			existing = contentTemplateMapper.selectByExample(seeded).stream().findFirst().orElse(null);
		}
		if (existing == null) {
			ContentTemplateDbo row = new ContentTemplateDbo();
			row.setCode(code);
			row.setContentFormat(contentFormat.name());
			row.setScope("WIKI");
			row.setBody(directive.body());
			row.setSource(directive.source());
			row.setWikiPageId(page.getWikiPageId());
			contentTemplateMapper.insert(row);
			return;
		}
		existing.setBody(directive.body());
		if (directive.directivePresent())
			existing.setSource(directive.source());
		existing.setWikiPageId(page.getWikiPageId());
		contentTemplateMapper.updateByPrimaryKey(existing);
		ContentTemplateDboExample otherFormats = new ContentTemplateDboExample();
		otherFormats.createCriteria().andCodeEqualTo(code).andWikiPageIdIsNull();
		for (ContentTemplateDbo sibling : contentTemplateMapper.selectByExample(otherFormats)) {
			sibling.setWikiPageId(page.getWikiPageId());
			contentTemplateMapper.updateByPrimaryKey(sibling);
		}
	}

}
