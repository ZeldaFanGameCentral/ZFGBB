package com.zfgc.zfgbb.dataprovider.cms;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.model.cms.ReleasedResource;
import com.zfgc.zfgbb.dbo.MigratorIdMapDboExample;
import com.zfgc.zfgbb.dataprovider.reactions.ReactionDataProvider;
import com.zfgc.zfgbb.dao.meta.MigratorIdMapDao;
import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.dbo.WikiPageCategoryDbo;
import com.zfgc.zfgbb.dbo.WikiPageCategoryDboExample;
import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDbo;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDboExample;
import com.zfgc.zfgbb.dbo.WikiRevisionRefDbo;
import com.zfgc.zfgbb.dbo.WikiRevisionRefDboExample;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.dao.cms.ContentResourceDao;
import com.zfgc.zfgbb.dao.cms.WikiNamespaceDao;
import com.zfgc.zfgbb.dao.cms.WikiPageCategoryDao;
import com.zfgc.zfgbb.dao.cms.WikiPageDao;
import com.zfgc.zfgbb.dao.cms.WikiPageRevisionDao;
import com.zfgc.zfgbb.dao.cms.WikiRevisionRefDao;
import com.zfgc.zfgbb.mappers.custom.WikiNamespaceCustomMapper.NamespacePageCount;
import com.zfgc.zfgbb.mapstruct.cms.WikiFileRefMap;
import com.zfgc.zfgbb.mapstruct.cms.WikiPageMap;
import com.zfgc.zfgbb.mapstruct.cms.WikiPageRefMap;
import com.zfgc.zfgbb.mapstruct.cms.WikiRevisionMap;
import com.zfgc.zfgbb.mapstruct.cms.WikiRevisionRefMap;
import com.zfgc.zfgbb.model.cms.PagedResult;
import com.zfgc.zfgbb.model.cms.WikiPage;
import com.zfgc.zfgbb.model.cms.WikiPageRef;
import com.zfgc.zfgbb.model.cms.WikiRevision;
import com.zfgc.zfgbb.model.cms.WikiRevisionRef;
import com.zfgc.zfgbb.wiki.WikiNamespaceRole;
import com.zfgc.zfgbb.wiki.WikiTitle;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class WikiDataProvider {

	public static final String STATUS_PENDING = "PENDING";
	public static final String STATUS_APPROVED = "APPROVED";
	public static final String STATUS_REJECTED = "REJECTED";

	private final WikiPageDao wikiPageDao;

	private final MigratorIdMapDao migratorIdMapDao;

	private final CatalogDataProvider catalogDataProvider;

	private final ReactionDataProvider reactionDataProvider;

	private final WikiPageRevisionDao wikiPageRevisionDao;

	private final WikiRevisionRefDao wikiRevisionRefDao;

	private final WikiPageCategoryDao wikiPageCategoryDao;

	private final ContentResourceDao contentResourceDao;

	private final WikiNamespaceDao wikiNamespaceDao;

	private final WikiPageMap wikiPageMap;

	private final WikiRevisionRefMap wikiRevisionRefMap;

	private final WikiRevisionMap wikiRevisionMap;

	private final WikiPageRefMap wikiPageRefMap;

	private final WikiFileRefMap wikiFileRefMap;

	private final WikiNamespaceDataProvider namespaceData;

	private static final Map<WikiNamespaceRole, String> HIDDEN_ROLE_FALLBACKS = Map.of(
			WikiNamespaceRole.SPECIAL, "Special",
			WikiNamespaceRole.MEDIAWIKI, "MediaWiki");

	public static final Map<String, String> CMS_ENTITY_ROUTES = Map.of(
			"Project", "/content/projects",
			"Resource", "/content/resources");

	private static final List<String> NAMESPACES_OWNED_BY_THE_CMS =
			CMS_ENTITY_ROUTES.keySet().stream().sorted().toList();

	public List<String> namespacesHiddenFromReaders() {
		return Stream.concat(hiddenNamespaces().stream(), NAMESPACES_OWNED_BY_THE_CMS.stream())
				.distinct().toList();
	}

	private List<String> hiddenNamespaces() {
		return HIDDEN_ROLE_FALLBACKS.entrySet().stream()
				.flatMap(entry -> Stream.concat(namespaceData.nameForRole(entry.getKey()).stream(),
						Stream.of(entry.getValue())))
				.distinct().toList();
	}

	public WikiPage getWikiPage(String path, Integer revisionId) {
		Optional<WikiPageDbo> stored = findPageDbo(path);
		if (stored.isEmpty()) {
			return syntheticCategoryPage(path).orElseThrow(ZfgcNotFoundException::new);
		}
		WikiPageDbo dbo = stored.get();
		WikiPage page = toWikiPage(dbo);
		if (revisionId != null) {
			WikiPageRevisionDbo revision = wikiPageRevisionDao.find(revisionId)
					.filter(candidate -> dbo.getWikiPageId().equals(candidate.getWikiPageId()))
					.filter(candidate -> STATUS_APPROVED.equals(candidate.getStatus()))
					.orElseThrow(ZfgcNotFoundException::new);
			page.setContent(revision.getContent());
			page.setContentFormat(revision.getContentFormat());
			page.setRevision(toRevisionRef(revision));
		}
		return page;
	}

	public Optional<WikiPage> getWikiPage(Integer wikiPageId) {
		return wikiPageDao.find(wikiPageId).map(this::toWikiPage);
	}

	public Optional<WikiPage> getWikiPageQuietly(String slug) {
		return findPageDbo(slug).map(this::toWikiPage);
	}

	public List<String> getNamespaces() {
		Comparator<NamespacePageCount> mainFirstThenBusiest = Comparator
				.comparing((NamespacePageCount count) -> !count.getNamespace().equals("MAIN"))
				.thenComparing(NamespacePageCount::getPageCount, Comparator.reverseOrder())
				.thenComparing(NamespacePageCount::getNamespace);
		return wikiNamespaceDao.countVisiblePagesByNamespace(hiddenNamespaces()).stream()
				.sorted(mainFirstThenBusiest)
				.map(NamespacePageCount::getNamespace).toList();
	}

	private List<Integer> publishedPageIds() {
		WikiRevisionRefDboExample currentRevisionsExample = new WikiRevisionRefDboExample();
		currentRevisionsExample.createCriteria().andCurrentFlagEqualTo(true);
		return wikiRevisionRefDao.get(currentRevisionsExample).stream()
				.map(WikiRevisionRefDbo::getWikiPageId).distinct().toList();
	}

	private WikiPageDboExample visiblePagesExample() {
		List<Integer> published = publishedPageIds();
		List<String> hidden = hiddenNamespaces();
		WikiPageDboExample visiblePagesExample = new WikiPageDboExample();
		var redirects = visiblePagesExample.createCriteria().andRedirectToIsNotNull();
		if (!hidden.isEmpty())
			redirects.andNamespaceNotIn(hidden);
		if (!published.isEmpty()) {
			var withACurrentRevision = visiblePagesExample.or().andWikiPageIdIn(published);
			if (!hidden.isEmpty())
				withACurrentRevision.andNamespaceNotIn(hidden);
		}
		visiblePagesExample.setOrderByClause("lower(namespace), lower(title)");
		return visiblePagesExample;
	}

	public List<WikiRevisionRef> getWikiHistory(String path) {
		WikiPageDbo dbo = findPageDbo(path).orElseThrow(ZfgcNotFoundException::new);
		WikiRevisionRefDboExample pageRevisionsExample = new WikiRevisionRefDboExample();
		pageRevisionsExample.createCriteria().andWikiPageIdEqualTo(dbo.getWikiPageId());
		return wikiRevisionRefDao.get(pageRevisionsExample).stream()
				.sorted(Comparator
						.comparing((WikiRevisionRefDbo rev) -> rev.getAuthoredTs() == null
								? rev.getCreatedTs()
								: rev.getAuthoredTs(),
								Comparator.nullsLast(Comparator.reverseOrder()))
						.thenComparing(WikiRevisionRefDbo::getWikiPageRevisionId,
								Comparator.reverseOrder()))
				.map(this::toRevisionRef).toList();
	}

	public List<WikiRevisionRef> getWikiRecentChanges(int limit) {
		WikiRevisionRefDboExample recentChangesExample = new WikiRevisionRefDboExample();
		recentChangesExample.setOrderByClause("authored_ts desc nulls last, wiki_page_revision_id desc");
		recentChangesExample.setLimit(limit);
		recentChangesExample.setOffset(0);
		recentChangesExample.createCriteria().andAuthoredTsIsNotNull().andStatusEqualTo(STATUS_APPROVED);
		return withPageRefs(wikiRevisionRefDao.get(recentChangesExample)).stream()
				.filter(ref -> ref.getPage() != null)
				.toList();
	}

	private List<WikiRevisionRef> withPageRefs(List<WikiRevisionRefDbo> revisions) {
		List<Integer> pageIds = revisions.stream().map(WikiRevisionRefDbo::getWikiPageId).distinct().toList();
		Map<Integer, WikiPageDbo> pages = new HashMap<>();
		if (!pageIds.isEmpty()) {
			WikiPageDboExample pagesByIdExample = new WikiPageDboExample();
			pagesByIdExample.createCriteria().andWikiPageIdIn(pageIds);
			wikiPageDao.get(pagesByIdExample).forEach(page -> pages.put(page.getWikiPageId(), page));
		}
		return revisions.stream().map(revision -> {
			WikiRevisionRef ref = toRevisionRef(revision);
			WikiPageDbo page = pages.get(revision.getWikiPageId());
			if (page != null)
				ref.setPage(toPageRef(page));
			return ref;
		}).toList();
	}

	public PagedResult<WikiPageRef> getWikiPageIndex(String namespace, String search, int page, int pageSize) {
		WikiPageDboExample visiblePagesExample = visiblePagesExample();
		if (namespace == null && search == null) {
			long total = wikiPageDao.count(visiblePagesExample);
			long offset = (long) (page - 1) * pageSize;
			if (offset > Integer.MAX_VALUE)
				return new PagedResult<>(List.of(), total, page, pageSize);
			visiblePagesExample.setLimit(pageSize);
			visiblePagesExample.setOffset((int) offset);
			return new PagedResult<>(wikiPageDao.get(visiblePagesExample).stream().map(this::toPageRef).toList(),
					total, page, pageSize);
		}
		String needle = search == null ? null : search.trim().toLowerCase();
		List<WikiPageDbo> filtered = wikiPageDao.get(visiblePagesExample).stream()
				.filter(dbo -> namespace == null || namespace.equalsIgnoreCase(dbo.getNamespace()))
				.filter(dbo -> needle == null
						|| (dbo.getTitle() != null && dbo.getTitle().toLowerCase().contains(needle))
						|| (dbo.getSlug() != null && dbo.getSlug().toLowerCase().contains(needle)))
				.toList();
		List<WikiPageRef> items = filtered.stream()
				.skip((long) (page - 1) * pageSize).limit(pageSize)
				.map(this::toPageRef).toList();
		return new PagedResult<>(items, filtered.size(), page, pageSize);
	}

	public Optional<WikiPageRef> getRandomWikiPage() {
		WikiPageDboExample visiblePagesExample = visiblePagesExample();
		long visible = wikiPageDao.count(visiblePagesExample);
		if (visible == 0) {
			return Optional.empty();
		}
		visiblePagesExample.setLimit(1);
		visiblePagesExample.setOffset(ThreadLocalRandom.current().nextInt((int) Math.min(visible, Integer.MAX_VALUE)));
		return wikiPageDao.get(visiblePagesExample).stream().findFirst().map(this::toPageRef);
	}

	public List<Map.Entry<String, Long>> getWikiCategories() {
		return wikiPageCategoryDao.get(new WikiPageCategoryDboExample()).stream()
				.collect(Collectors.groupingBy(
						WikiPageCategoryDbo::getCategoryName,
						TreeMap::new, Collectors.counting()))
				.entrySet().stream().toList();
	}

	public Map<String, Object> getWikiStatistics() {
		Map<String, Long> byNamespace = new TreeMap<>();
		long totalPages = 0;
		long redirects = 0;
		for (NamespacePageCount count : wikiNamespaceDao.countVisiblePagesByNamespace(hiddenNamespaces())) {
			byNamespace.put(count.getNamespace(), count.getPageCount());
			totalPages += count.getPageCount();
			redirects += count.getRedirectCount();
		}
		return Map.of(
				"totalPages", totalPages,
				"byNamespace", byNamespace,
				"categories", wikiNamespaceDao.countDistinctCategories(),
				"redirects", redirects);
	}

	public List<WikiPageRef> getCategoryMembers(String categoryName) {
		WikiPageCategoryDboExample categoryMembersExample = new WikiPageCategoryDboExample();
		categoryMembersExample.createCriteria().andCategoryNameEqualTo(categoryName);
		List<Integer> pageIds = wikiPageCategoryDao.get(categoryMembersExample).stream()
				.map(WikiPageCategoryDbo::getWikiPageId).distinct().toList();
		if (pageIds.isEmpty()) {
			return List.of();
		}
		WikiPageDboExample categoryPagesExample = new WikiPageDboExample();
		categoryPagesExample.createCriteria().andWikiPageIdIn(pageIds);
		return wikiPageDao.get(categoryPagesExample).stream()
				.sorted(Comparator.comparing(dbo -> dbo.getTitle().toLowerCase()))
				.map(this::toPageRef).toList();
	}

	public Optional<WikiPage> findPage(String path) {
		return findPageDbo(path).map(wikiPageMap::toModel);
	}

	private Optional<WikiPage> syntheticCategoryPage(String path) {
		if (path == null)
			return Optional.empty();
		WikiTitle categoryTitle = namespaceData.resolve(path);
		if (!namespaceData.hasRole(categoryTitle.namespace(), WikiNamespaceRole.CATEGORY)
				|| categoryTitle.title() == null || categoryTitle.title().isBlank())
			return Optional.empty();
		WikiPage synthetic = new WikiPage();
		synthetic.setId(0);
		synthetic.setNamespace(categoryTitle.namespace());
		synthetic.setTitle(categoryTitle.title());
		synthetic.setSlug(categoryTitle.path());
		synthetic.setCategoryMembers(getCategoryMembers(synthetic.getTitle()));
		return Optional.of(synthetic);
	}

	private Optional<WikiPageDbo> findPageDbo(String path) {
		WikiPageDboExample bySlugExample = new WikiPageDboExample();
		bySlugExample.createCriteria().andSlugEqualTo(path);
		Optional<WikiPageDbo> byStableSlug = uniquePage(wikiPageDao.get(bySlugExample), path);
		WikiTitle title = namespaceData.resolve(path);
		if (byStableSlug.filter(page -> !shadowsRegisteredNamespace(page, title)).isPresent())
			return byStableSlug;
		WikiPageDboExample byNamespaceAndTitleExample = new WikiPageDboExample();
		byNamespaceAndTitleExample.createCriteria().andNamespaceEqualTo(title.namespace()).andTitleEqualTo(title.title());
		Optional<WikiPageDbo> exact = uniquePage(wikiPageDao.get(byNamespaceAndTitleExample), path);
		if (exact.isPresent())
			return exact;
		WikiPageDboExample byTitleExample = new WikiPageDboExample();
		byTitleExample.createCriteria().andTitleEqualTo(title.title());
		Optional<WikiPageDbo> byAlias = uniquePage(wikiPageDao.get(byTitleExample).stream()
				.filter(page -> page.getNamespace().equalsIgnoreCase(title.namespace())).toList(), path);
		return byAlias.or(() -> byStableSlug);
	}

	private boolean shadowsRegisteredNamespace(WikiPageDbo candidate, WikiTitle resolved) {
		return candidate.getNamespace().equals("MAIN") && !resolved.namespace().equals("MAIN");
	}

	private static Optional<WikiPageDbo> uniquePage(List<WikiPageDbo> matches, String requestedPath) {
		if (matches.size() > 1)
			throw new IllegalStateException("Ambiguous canonical wiki title '" + requestedPath
					+ "' matches page ids " + matches.stream().map(WikiPageDbo::getWikiPageId).sorted().toList());
		return matches.stream().findFirst();
	}

	private WikiPage toWikiPage(WikiPageDbo dbo) {
		WikiPage page = wikiPageMap.toModel(dbo);
		WikiPageRevisionDboExample currentRevisionExample = new WikiPageRevisionDboExample();
		currentRevisionExample.createCriteria().andWikiPageIdEqualTo(dbo.getWikiPageId()).andCurrentFlagEqualTo(true);
		wikiPageRevisionDao.getOne(currentRevisionExample).ifPresent(rev -> {
			page.setContent(rev.getContent());
			page.setContentFormat(rev.getContentFormat());
		});
		WikiPageCategoryDboExample pageCategoriesExample = new WikiPageCategoryDboExample();
		pageCategoriesExample.createCriteria().andWikiPageIdEqualTo(dbo.getWikiPageId());
		page.setCategories(wikiPageCategoryDao.get(pageCategoriesExample).stream()
				.map(WikiPageCategoryDbo::getCategoryName).sorted().toList());
		if (namespaceData.hasRole(dbo.getNamespace(), WikiNamespaceRole.CATEGORY)) {
			page.setCategoryMembers(getCategoryMembers(dbo.getTitle()));
		}
		if (dbo.getContentResourceId() != null)
			contentResourceDao.find(dbo.getContentResourceId())
					.ifPresent(file -> page.setFile(wikiFileRefMap.toRef(file)));
		return page;
	}

	private WikiPageRef toPageRef(WikiPageDbo dbo) {
		return wikiPageRefMap.toRef(dbo);
	}

	private WikiRevisionRef toRevisionRef(WikiPageRevisionDbo rev) {
		return wikiRevisionRefMap.toRef(rev);
	}

	private WikiRevisionRef toRevisionRef(WikiRevisionRefDbo rev) {
		return wikiRevisionRefMap.toRef(rev);
	}

	public WikiPage createPage(WikiTitle canonical, Integer createdUserId) {
		WikiPageDbo page = new WikiPageDbo();
		page.setNamespace(canonical.namespace());
		page.setTitle(canonical.title());
		page.setSlug(canonical.path());
		page.setCreatedUserId(createdUserId);
		wikiPageDao.insert(page);
		return wikiPageMap.toModel(page);
	}

	public Optional<WikiRevision> getRevision(Integer revisionId) {
		return wikiPageRevisionDao.find(revisionId).map(wikiRevisionMap::toModel);
	}

	public Optional<ContentFormat> contentFormatOfRevisionBeingSuperseded(Integer wikiPageId) {
		WikiPageRevisionDboExample liveRevision = new WikiPageRevisionDboExample();
		liveRevision.createCriteria().andWikiPageIdEqualTo(wikiPageId).andCurrentFlagEqualTo(true);
		Optional<ContentFormat> live = wikiPageRevisionDao.getOne(liveRevision)
				.flatMap(revision -> ContentFormat.parse(revision.getContentFormat()));
		if (live.isPresent())
			return live;
		WikiPageRevisionDboExample newestSubmission = new WikiPageRevisionDboExample();
		newestSubmission.setOrderByClause("wiki_page_revision_id desc");
		newestSubmission.setLimit(1);
		newestSubmission.setOffset(0);
		newestSubmission.createCriteria().andWikiPageIdEqualTo(wikiPageId);
		return wikiPageRevisionDao.getOne(newestSubmission)
				.flatMap(revision -> ContentFormat.parse(revision.getContentFormat()));
	}

	public Optional<WikiPage> getPage(Integer wikiPageId) {
		return wikiPageDao.find(wikiPageId).map(wikiPageMap::toModel);
	}

	public WikiRevisionRef submitRevision(Integer wikiPageId, String content, ContentFormat contentFormat,
			String summary, Integer authorUserId, String authorName) {
		WikiPageRevisionDbo revision = new WikiPageRevisionDbo();
		revision.setWikiPageId(wikiPageId);
		revision.setContent(content);
		revision.setContentSize(content == null ? 0 : content.getBytes(StandardCharsets.UTF_8).length);
		revision.setContentFormat(contentFormat.name());
		revision.setCurrentFlag(false);
		revision.setStatus(STATUS_PENDING);
		revision.setAuthoredTs(OffsetDateTime.now(ZoneOffset.UTC));
		revision.setAuthorUserId(authorUserId);
		revision.setAuthorName(authorName);
		revision.setSummary(summary);
		wikiPageRevisionDao.insert(revision);
		return wikiRevisionRefMap.toRef(revision);
	}

	public void approveRevision(Integer revisionId) {
		WikiPageRevisionDbo revision = wikiPageRevisionDao.find(revisionId)
				.orElseThrow(ZfgcNotFoundException::new);
		WikiPageRevisionDboExample supersededExample = new WikiPageRevisionDboExample();
		supersededExample.createCriteria().andWikiPageIdEqualTo(revision.getWikiPageId()).andCurrentFlagEqualTo(true);
		for (WikiPageRevisionDbo superseded : wikiPageRevisionDao.get(supersededExample)) {
			superseded.setCurrentFlag(false);
			wikiPageRevisionDao.save(superseded);
		}
		revision.setCurrentFlag(true);
		revision.setStatus(STATUS_APPROVED);
		wikiPageRevisionDao.save(revision);
	}

	public void rejectRevision(Integer revisionId) {
		WikiPageRevisionDbo revision = wikiPageRevisionDao.find(revisionId)
				.orElseThrow(ZfgcNotFoundException::new);
		revision.setStatus(STATUS_REJECTED);
		wikiPageRevisionDao.save(revision);
	}

	public List<WikiRevisionRef> getPendingRevisions() {
		WikiRevisionRefDboExample pendingRevisionsExample = new WikiRevisionRefDboExample();
		pendingRevisionsExample.createCriteria().andStatusEqualTo(STATUS_PENDING);
		return withPageRefs(wikiRevisionRefDao.get(pendingRevisionsExample).stream()
				.sorted(Comparator.comparing(WikiRevisionRefDbo::getWikiPageRevisionId))
				.toList());
	}

	public List<Integer> findOwnedTemplateLinkedWikiPageIds(Integer userId) {
		return wikiPageDao.findOwnedTemplateLinkedWikiPageIds(userId);
	}

	public List<ReleasedResource> purgeOwnedWikiPages(Integer userId) {
		List<Integer> pageIds = wikiPageDao.findOwnedHardDeletableWikiPageIds(userId);
		if (pageIds.isEmpty())
			return List.of();
		reactionDataProvider.deleteReactions("WIKI_PAGE", pageIds);
		List<Integer> releasedResourceIds = wikiPageDao.findWikiPageContentResourceIds(pageIds);
		WikiPageRevisionDboExample wikiRevisionsExample = new WikiPageRevisionDboExample();
		wikiRevisionsExample.createCriteria().andWikiPageIdIn(pageIds);
		wikiPageRevisionDao.deleteWhere(wikiRevisionsExample);
		MigratorIdMapDboExample migratorEntries = new MigratorIdMapDboExample();
		migratorEntries.createCriteria().andEntityTypeEqualTo("WIKI_PAGE").andZfgbbIdIn(pageIds);
		migratorIdMapDao.deleteWhere(migratorEntries);
		WikiPageDboExample wikiPagesExample = new WikiPageDboExample();
		wikiPagesExample.createCriteria().andWikiPageIdIn(pageIds);
		wikiPageDao.deleteWhere(wikiPagesExample);
		return catalogDataProvider.deleteContentResourcesIfUnreferenced(releasedResourceIds);
	}

	public int countOwnedWikiPages(Integer userId) {
		WikiPageDboExample ownedWikiPagesExample = new WikiPageDboExample();
		ownedWikiPagesExample.createCriteria().andCreatedUserIdEqualTo(userId);
		return (int) wikiPageDao.count(ownedWikiPagesExample);
	}

	public void scrubRetainedWikiContributions(Integer userId) {
		wikiPageDao.nullWikiPageCreators(userId);
		wikiPageRevisionDao.scrubRetainedWikiRevisions(userId);
	}
}
