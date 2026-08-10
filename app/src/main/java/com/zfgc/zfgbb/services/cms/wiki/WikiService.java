package com.zfgc.zfgbb.services.cms.wiki;

import java.util.Optional;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.services.cms.CmsPageRenderer;
import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.dataprovider.cms.WikiDataProvider;
import com.zfgc.zfgbb.dataprovider.cms.WikiNamespaceDataProvider;
import com.zfgc.zfgbb.model.cms.PagedResult;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.cms.WikiPage;
import com.zfgc.zfgbb.content.renderer.templates.TemplateDataService;
import com.zfgc.zfgbb.content.renderer.templates.TemplateSource;
import com.zfgc.zfgbb.model.cms.WikiPageRef;
import com.zfgc.zfgbb.model.cms.WikiRevisionRef;
import com.zfgc.zfgbb.wiki.WikiTitle;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class WikiService implements TemplateDataService {

	private static final Map<String, String> ENTITY_ROUTES = Map.of(
			"Project", "/content/projects",
			"Resource", "/content/resources");

	private final WikiDataProvider wikiDataProvider;

	private final CmsPageRenderer cmsPageRenderer;

	private final WikiNamespaceDataProvider namespaceData;

	private final WikiNamespaceEditGate wikiNamespaceEditGate;

	public String previewContent(String slug, String content, ContentFormat contentFormat, ContentScope scope) {
		WikiTitle canonical = slug == null || slug.isBlank() ? null : namespaceData.resolve(slug);
		WikiPage rendered = cmsPageRenderer.previewPage(
				canonical == null ? null : canonical.namespace(),
				canonical == null ? null : canonical.title(),
				canonical == null ? null : canonical.path(),
				content, contentFormat.name(), scope);
		return rendered.getContentParsed() == null ? "" : rendered.getContentParsed();
	}

	public WikiPage getWikiPage(String slug, Integer revisionId) {
		return getWikiPage(slug, revisionId, false, null);
	}

	public WikiPage getWikiPage(String slug, Integer revisionId, boolean includeSource) {
		return getWikiPage(slug, revisionId, includeSource, null);
	}

	public WikiPage getWikiPage(String slug, Integer revisionId, boolean includeSource, User viewer) {
		WikiPage page = wikiDataProvider.getWikiPage(slug, revisionId);
		cmsPageRenderer.renderPage(page, ContentScope.WIKI);
		String entityBase = ENTITY_ROUTES.get(page.getNamespace());
		if (entityBase != null && page.getSlug() != null) {
			page.setEntityUrl(entityBase + "/" + CmsPageRenderer.unprefixedSlug(page.getNamespace(), page.getSlug()));
		}
		String editNamespace = namespaceData.resolve(page.getSlug()).namespace();
		page.setEditable(wikiNamespaceEditGate.canViewerEdit(editNamespace, viewer));
		if (!includeSource) {
			page.setContent(null);
		}
		return page;
	}

	public List<WikiRevisionRef> getWikiHistory(String slug) {
		return wikiDataProvider.getWikiHistory(slug);
	}

	public List<WikiRevisionRef> getWikiHistory(String slug, boolean includeUnapproved) {
		List<WikiRevisionRef> history = wikiDataProvider.getWikiHistory(slug);
		if (includeUnapproved) {
			return history;
		}
		return history.stream()
				.filter(ref -> WikiDataProvider.STATUS_APPROVED.equals(ref.getStatus()))
				.toList();
	}

	public List<WikiRevisionRef> getWikiRecentChanges() {
		return wikiDataProvider.getWikiRecentChanges(30);
	}

	public PagedResult<WikiPageRef> getWikiPageIndex(String namespace, String search, Integer page,
			Integer pageSize) {
		return wikiDataProvider.getWikiPageIndex(
				namespace == null || namespace.isBlank() ? null : namespace,
				search == null || search.isBlank() ? null : search,
				page == null || page < 1 ? 1 : page,
				pageSize == null || pageSize < 1 || pageSize > 200 ? 50 : pageSize);
	}

	public Optional<WikiPageRef> getRandomWikiPage() {
		return wikiDataProvider.getRandomWikiPage();
	}

	public List<Map.Entry<String, Long>> getWikiCategories() {
		return wikiDataProvider.getWikiCategories();
	}

	@TemplateSource("/wiki/meta/statistics")
	public Map<String, Object> getWikiStatistics() {
		return wikiDataProvider.getWikiStatistics();
	}

	@TemplateSource("/wiki/meta/category")
	public Map<String, Object> getCategoryPages(String name) {
		List<WikiPageRef> members = wikiDataProvider.getCategoryMembers(name == null ? "" : name);
		return Map.of("name", name == null ? "" : name, "empty", members.isEmpty(), "pages", members);
	}
}
