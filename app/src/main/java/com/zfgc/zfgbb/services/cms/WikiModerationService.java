package com.zfgc.zfgbb.services.cms;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.authorization.AuthorityTiers;
import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.ContentRenderer;
import com.zfgc.zfgbb.content.renderer.TemplateDataFetcher;
import com.zfgc.zfgbb.dataprovider.cms.WikiDataProvider;
import com.zfgc.zfgbb.dbo.ContentTemplateDbo;
import com.zfgc.zfgbb.dbo.ContentTemplateDboExample;
import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDbo;
import com.zfgc.zfgbb.mappers.ContentTemplateDboMapper;
import com.zfgc.zfgbb.mapstruct.cms.WikiRevisionRefMap;
import com.zfgc.zfgbb.migrator.converters.cms.CmsSupport;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.cms.WikiRevisionRef;
import com.zfgc.zfgbb.wiki.WikiTitle;

@Service
@Transactional
public class WikiModerationService {

	@Autowired
	private WikiDataProvider wikiDataProvider;

	@Autowired
	private ContentTemplateDboMapper contentTemplateMapper;

	@Autowired
	private ContentRenderer contentRenderer;

	@Autowired
	private TemplateDataFetcher templateDataFetcher;

	@Autowired
	private AuthorityTiers authorityTiers;

	@Autowired
	private WikiRevisionRefMap wikiRevisionRefMap;

	@Autowired
	private WikiNamespaceRegistry namespaceRegistry;

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

	public WikiRevisionRef submit(String slug, String content, String summary, User user) {
		if (slug == null || slug.isBlank() || content == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "slug and content are required");
		}
		if (authorityTiers.isReadOnly(user))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Read-only accounts cannot submit wiki revisions.");
		String trimmedSlug = namespaceRegistry.resolve(slug).path();
		if (isReservedNamespace(namespaceOf(trimmedSlug))) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"Namespace '" + namespaceOf(trimmedSlug) + "' is reserved and cannot be edited through the wiki");
		}
		if (("Site".equals(namespaceOf(trimmedSlug)) || "ZFGC".equals(namespaceOf(trimmedSlug)))
				&& !(user.hasPermission("WIKI_MODERATOR") || user.hasPermission("SITE_ADMIN")
						|| user.hasPermission("ZFGC_WIKI_MODERATOR") || user.hasPermission("ZFGC_SITE_ADMIN"))) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"The site namespace only accepts submissions from wiki moderators");
		}
		WikiPageDbo page = wikiDataProvider.findPage(trimmedSlug);
		if (page == null) {
			page = createPageFromSlug(trimmedSlug);
		}
		if ("Template".equals(page.getNamespace()) && systemOwnedTemplate(page)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Template '" + page.getTitle() + "' is system-owned and cannot be edited through the wiki");
		}
		if ("Template".equals(page.getNamespace()))
			requireResolvableSource(TemplateSourceDirective.parse(content));
		WikiPageRevisionDbo revision = wikiDataProvider.submitRevision(page.getWikiPageId(), content, summary,
				user.getUserId(), user.getDisplayName());
		return wikiRevisionRefMap.toRef(revision);
	}

	public List<WikiRevisionRef> getPendingRevisions() {
		return wikiDataProvider.getPendingRevisions();
	}

	public void approve(Integer revisionId) {
		WikiPageRevisionDbo revision = requirePending(revisionId);
		WikiPageDbo page = wikiDataProvider.getPage(revision.getWikiPageId());
		if (page == null || !"Template".equals(page.getNamespace())) {
			wikiDataProvider.approveRevision(revision);
			return;
		}
		TemplateSourceDirective directive = TemplateSourceDirective.parse(revision.getContent());
		requireResolvableSource(directive);
		wikiDataProvider.approveRevision(revision);
		publishTemplate(page, directive);
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
		String source = revision.getContent();
		Map<String, String> context = page == null ? Map.of()
				: Map.of("pageSlug", page.getSlug(), "pageNamespace", page.getNamespace(),
						"pageTitle", page.getTitle());
		ContentFormat format = "MARKDOWN".equals(revision.getContentFormat())
				? ContentFormat.MARKDOWN
				: ContentFormat.BBCODE;
		return Map.of("contentParsed", contentRenderer.renderWithTemplates(source, format, ContentScope.WIKI, context));
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

	private static final Set<String> RESERVED_NAMESPACES = Set.of("Special", "MediaWiki", "Project", "Resource");

	private static String namespaceOf(String slug) {
		int colon = slug.indexOf(':');
		return (colon > 0 && colon < slug.length() - 1) ? slug.substring(0, colon) : "MAIN";
	}

	private static boolean isReservedNamespace(String namespace) {
		return namespace != null && RESERVED_NAMESPACES.contains(namespace);
	}

	private WikiPageDbo createPageFromSlug(String slug) {
		WikiTitle canonical = namespaceRegistry.resolve(slug);
		String namespace = canonical.namespace();
		String title = canonical.title();
		if ("Template".equals(namespace) && systemOwnedTemplateCode(title)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Template '" + title + "' is system-owned and cannot be edited through the wiki");
		}
		return wikiDataProvider.createPage(namespace, title, canonical.path());
	}

	private boolean systemOwnedTemplate(WikiPageDbo page) {
		ContentTemplateDboExample owned = new ContentTemplateDboExample();
		owned.createCriteria().andWikiPageIdEqualTo(page.getWikiPageId());
		if (contentTemplateMapper.countByExample(owned) > 0)
			return false;
		return systemOwnedTemplateCode(page.getTitle());
	}

	private boolean systemOwnedTemplateCode(String title) {
		ContentTemplateDboExample ex = new ContentTemplateDboExample();
		ex.createCriteria().andCodeEqualTo(templateCode(title)).andWikiPageIdIsNull();
		return contentTemplateMapper.countByExample(ex) > 0;
	}

	private void requireResolvableSource(TemplateSourceDirective directive) {
		if (directive.source() == null || templateDataFetcher.canResolve(directive.source()))
			return;
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
				"Template source path '" + templateDataFetcher.pathPortion(directive.source())
						+ "' does not match any registered data source");
	}

	private void publishTemplate(WikiPageDbo page, TemplateSourceDirective directive) {
		String code = templateCode(page.getTitle());
		ContentTemplateDboExample ex = new ContentTemplateDboExample();
		ex.createCriteria().andWikiPageIdEqualTo(page.getWikiPageId()).andContentFormatEqualTo("BBCODE");
		ContentTemplateDbo existing = contentTemplateMapper.selectByExample(ex).stream().findFirst().orElse(null);
		if (existing == null) {
			ContentTemplateDbo row = new ContentTemplateDbo();
			row.setCode(code);
			row.setContentFormat("BBCODE");
			row.setScope("WIKI");
			row.setBody(directive.body());
			row.setSource(directive.source());
			row.setWikiPageId(page.getWikiPageId());
			contentTemplateMapper.insert(row);
			return;
		}
		if (existing.getWikiPageId() == null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Template '" + page.getTitle() + "' is system-owned");
		}
		existing.setBody(directive.body());
		if (directive.directivePresent())
			existing.setSource(directive.source());
		existing.setWikiPageId(page.getWikiPageId());
		contentTemplateMapper.updateByPrimaryKey(existing);
	}

	private String templateCode(String title) {
		return WikiTitle.normalizeTitle(CmsSupport.wikiTitleDisplay(title), namespaceRegistry.caseMode("Template"));
	}
}
