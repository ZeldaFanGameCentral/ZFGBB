package com.zfgc.zfgbb.dataprovider.cms;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

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
import com.zfgc.zfgbb.mapstruct.cms.WikiRevisionRefMap;
import com.zfgc.zfgbb.model.cms.PagedResult;
import com.zfgc.zfgbb.model.cms.WikiPage;
import com.zfgc.zfgbb.model.cms.WikiPageRef;
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

	private final WikiPageRevisionDao wikiPageRevisionDao;

	private final WikiRevisionRefDao wikiRevisionRefDao;

	private final WikiPageCategoryDao wikiPageCategoryDao;

	private final ContentResourceDao contentResourceDao;

	private final WikiNamespaceDao wikiNamespaceDao;

	private final WikiPageMap wikiPageMap;

	private final WikiRevisionRefMap wikiRevisionRefMap;

	private final WikiPageRefMap wikiPageRefMap;

	private final WikiFileRefMap wikiFileRefMap;

	private final WikiNamespaceDataProvider namespaceData;

	private static final Map<WikiNamespaceRole, String> HIDDEN_ROLE_FALLBACKS = Map.of(
			WikiNamespaceRole.SPECIAL, "Special",
			WikiNamespaceRole.MEDIAWIKI, "MediaWiki");

	private static final List<String> NAMESPACES_OWNED_BY_THE_CMS = List.of("Project", "Resource", "ZFGC");

	public List<String> theNamespacesNoReaderViewShows() {
		return Stream.concat(hiddenNamespaces().stream(), NAMESPACES_OWNED_BY_THE_CMS.stream())
				.distinct().toList();
	}

	private List<String> hiddenNamespaces() {
		return HIDDEN_ROLE_FALLBACKS.entrySet().stream()
				.flatMap(entry -> Stream.of(namespaceData.nameForRole(entry.getKey()), entry.getValue()))
				.filter(Objects::nonNull).distinct().toList();
	}

	public WikiPage getWikiPage(String path, Integer revisionId) {
		WikiPageDbo dbo = findPage(path);
		if (dbo == null) {
			WikiTitle categoryTitle = path == null ? null : namespaceData.resolve(path);
			if (categoryTitle != null
					&& namespaceData.hasRole(categoryTitle.namespace(), WikiNamespaceRole.CATEGORY)
					&& categoryTitle.title() != null && !categoryTitle.title().isBlank()) {
				WikiPage synthetic = new WikiPage();
				synthetic.setId(0);
				synthetic.setNamespace(categoryTitle.namespace());
				synthetic.setTitle(categoryTitle.title());
				synthetic.setSlug(categoryTitle.title().replace(' ', '_'));
				synthetic.setCategoryMembers(getCategoryMembers(synthetic.getTitle()));
				return synthetic;
			}
			throw new ZfgcNotFoundException();
		}
		WikiPage page = toWikiPage(dbo);
		if (revisionId != null) {
			WikiPageRevisionDbo rev = wikiPageRevisionDao.find(revisionId).orElse(null);
			if (rev == null || !dbo.getWikiPageId().equals(rev.getWikiPageId())
					|| !STATUS_APPROVED.equals(rev.getStatus())) {
				throw new ZfgcNotFoundException();
			}
			page.setContent(rev.getContent());
			page.setContentFormat(rev.getContentFormat());
			page.setRevision(toRevisionRef(rev));
		}
		return page;
	}

	public Optional<WikiPage> getWikiPage(Integer wikiPageId) {
		return wikiPageDao.find(wikiPageId).map(this::toWikiPage);
	}

	public Optional<WikiPage> getWikiPageQuietly(String slug) {
		return Optional.ofNullable(findPage(slug)).map(this::toWikiPage);
	}

	public List<String> getNamespaces() {
		Map<String, Long> counts = wikiNamespaceDao
				.countVisiblePagesByNamespace(hiddenNamespaces()).stream()
				.collect(Collectors.toMap(
						NamespacePageCount::getNamespace,
						NamespacePageCount::getPageCount));
		return counts.entrySet().stream()
				.sorted((entryA, entryB) -> {
					if ("MAIN".equals(entryA.getKey())) {
						return "MAIN".equals(entryB.getKey()) ? 0 : -1;
					}
					if ("MAIN".equals(entryB.getKey())) {
						return 1;
					}
					int byCount = Long.compare(entryB.getValue(), entryA.getValue());
					return byCount != 0 ? byCount : entryA.getKey().compareTo(entryB.getKey());
				})
				.map(Map.Entry::getKey).toList();
	}

	private Set<Integer> publishedPageIds() {
		WikiRevisionRefDboExample ex = new WikiRevisionRefDboExample();
		ex.createCriteria().andCurrentFlagEqualTo(true);
		return wikiRevisionRefDao.get(ex).stream()
				.map(WikiRevisionRefDbo::getWikiPageId).collect(Collectors.toSet());
	}

	private boolean isPublicPage(WikiPageDbo dbo, Set<Integer> publishedPageIds) {
		return dbo.getRedirectTo() != null || publishedPageIds.contains(dbo.getWikiPageId());
	}

	private List<WikiPageDbo> visiblePages() {
		Set<Integer> published = publishedPageIds();
		WikiPageDboExample ex = new WikiPageDboExample();
		List<String> hidden = hiddenNamespaces();
		var criteria = ex.createCriteria();
		if (!hidden.isEmpty())
			criteria.andNamespaceNotIn(hidden);
		return wikiPageDao.get(ex).stream()
				.filter(dbo -> isPublicPage(dbo, published))
				.toList();
	}

	public List<WikiRevisionRef> getWikiHistory(String path) {
		WikiPageDbo dbo = findPage(path);
		if (dbo == null) {
			throw new ZfgcNotFoundException();
		}
		WikiRevisionRefDboExample ex = new WikiRevisionRefDboExample();
		ex.createCriteria().andWikiPageIdEqualTo(dbo.getWikiPageId());
		return wikiRevisionRefDao.get(ex).stream()
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
		WikiRevisionRefDboExample ex = new WikiRevisionRefDboExample();
		ex.setOrderByClause("authored_ts desc nulls last, wiki_page_revision_id desc");
		ex.setLimit(limit);
		ex.setOffset(0);
		ex.createCriteria().andAuthoredTsIsNotNull().andStatusEqualTo(STATUS_APPROVED);
		return withPageRefs(wikiRevisionRefDao.get(ex)).stream()
				.filter(ref -> ref.getPage() != null)
				.toList();
	}

	private List<WikiRevisionRef> withPageRefs(List<WikiRevisionRefDbo> revisions) {
		List<Integer> pageIds = revisions.stream().map(WikiRevisionRefDbo::getWikiPageId).distinct().toList();
		Map<Integer, WikiPageDbo> pages = new HashMap<>();
		if (!pageIds.isEmpty()) {
			WikiPageDboExample pageEx = new WikiPageDboExample();
			pageEx.createCriteria().andWikiPageIdIn(pageIds);
			wikiPageDao.get(pageEx).forEach(page -> pages.put(page.getWikiPageId(), page));
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
		List<WikiPageDbo> filtered = visiblePages().stream()
				.filter(dbo -> namespace == null || namespace.equalsIgnoreCase(dbo.getNamespace()))
				.filter(dbo -> search == null || CatalogDataProvider.containsIgnoreCase(dbo.getTitle(), search)
						|| CatalogDataProvider.containsIgnoreCase(dbo.getSlug(), search))
				.sorted(Comparator.comparing(dbo -> (dbo.getNamespace() + " " + dbo.getTitle()).toLowerCase()))
				.toList();
		List<WikiPageRef> items = CatalogDataProvider.pageSlice(filtered, page, pageSize)
				.map(this::toPageRef).toList();
		return new PagedResult<>(items, filtered.size(), page, pageSize);
	}

	public WikiPageRef getRandomWikiPage() {
		List<WikiPageDbo> visible = visiblePages();
		if (visible.isEmpty()) {
			return null;
		}
		return toPageRef(visible.get(ThreadLocalRandom.current().nextInt(visible.size())));
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
		WikiPageCategoryDboExample ex = new WikiPageCategoryDboExample();
		ex.createCriteria().andCategoryNameEqualTo(categoryName);
		List<Integer> pageIds = wikiPageCategoryDao.get(ex).stream()
				.map(WikiPageCategoryDbo::getWikiPageId).distinct().toList();
		if (pageIds.isEmpty()) {
			return List.of();
		}
		WikiPageDboExample pageEx = new WikiPageDboExample();
		pageEx.createCriteria().andWikiPageIdIn(pageIds);
		return wikiPageDao.get(pageEx).stream()
				.sorted(Comparator.comparing(dbo -> dbo.getTitle().toLowerCase()))
				.map(this::toPageRef).toList();
	}

	public WikiPageDbo findPage(String path) {
		WikiPageDboExample slug = new WikiPageDboExample();
		slug.createCriteria().andSlugEqualTo(path);
		WikiPageDbo byStableSlug = uniquePage(wikiPageDao.get(slug), path);
		WikiTitle title = namespaceData == null ? WikiTitle.parse(path) : namespaceData.resolve(path);
		if (byStableSlug != null && !shadowsRegisteredNamespace(byStableSlug, title))
			return byStableSlug;
		WikiPageDboExample ex = new WikiPageDboExample();
		ex.createCriteria().andNamespaceEqualTo(title.namespace()).andTitleEqualTo(title.title());
		WikiPageDbo exact = uniquePage(wikiPageDao.get(ex), path);
		if (exact != null)
			return exact;
		WikiPageDboExample alias = new WikiPageDboExample();
		alias.createCriteria().andTitleEqualTo(title.title());
		WikiPageDbo byAlias = uniquePage(wikiPageDao.get(alias).stream()
				.filter(page -> page.getNamespace().equalsIgnoreCase(title.namespace())).toList(), path);
		return byAlias != null ? byAlias : byStableSlug;
	}

	private boolean shadowsRegisteredNamespace(WikiPageDbo candidate, WikiTitle resolved) {
		return "MAIN".equals(candidate.getNamespace()) && !"MAIN".equals(resolved.namespace());
	}

	private static WikiPageDbo uniquePage(List<WikiPageDbo> matches, String requestedPath) {
		if (matches.size() > 1)
			throw new IllegalStateException("Ambiguous canonical wiki title '" + requestedPath
					+ "' matches page ids " + matches.stream().map(WikiPageDbo::getWikiPageId).sorted().toList());
		return matches.isEmpty() ? null : matches.get(0);
	}

	private WikiPage toWikiPage(WikiPageDbo dbo) {
		WikiPage page = wikiPageMap.toModel(dbo);
		WikiPageRevisionDboExample ex = new WikiPageRevisionDboExample();
		ex.createCriteria().andWikiPageIdEqualTo(dbo.getWikiPageId()).andCurrentFlagEqualTo(true);
		wikiPageRevisionDao.getOne(ex).ifPresent(rev -> {
			page.setContent(rev.getContent());
			page.setContentFormat(rev.getContentFormat());
		});
		WikiPageCategoryDboExample catEx = new WikiPageCategoryDboExample();
		catEx.createCriteria().andWikiPageIdEqualTo(dbo.getWikiPageId());
		page.setCategories(wikiPageCategoryDao.get(catEx).stream()
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

	public WikiPageDbo createPage(String namespace, String title, String slug) {
		WikiPageDbo page = new WikiPageDbo();
		page.setNamespace(namespace);
		page.setTitle(title);
		page.setSlug(slug);
		wikiPageDao.insert(page);
		return page;
	}

	public WikiPageRevisionDbo getRevision(Integer revisionId) {
		return wikiPageRevisionDao.find(revisionId).orElse(null);
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

	public WikiPageDbo getPage(Integer wikiPageId) {
		return wikiPageDao.find(wikiPageId).orElse(null);
	}

	public WikiPageRevisionDbo submitRevision(Integer wikiPageId, String content, ContentFormat contentFormat,
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
		return revision;
	}

	public void approveRevision(WikiPageRevisionDbo revision) {
		WikiPageRevisionDboExample currentEx = new WikiPageRevisionDboExample();
		currentEx.createCriteria().andWikiPageIdEqualTo(revision.getWikiPageId()).andCurrentFlagEqualTo(true);
		for (WikiPageRevisionDbo old : wikiPageRevisionDao.get(currentEx)) {
			old.setCurrentFlag(false);
			wikiPageRevisionDao.save(old);
		}
		revision.setCurrentFlag(true);
		revision.setStatus(STATUS_APPROVED);
		wikiPageRevisionDao.save(revision);
	}

	public void rejectRevision(WikiPageRevisionDbo revision) {
		revision.setStatus(STATUS_REJECTED);
		wikiPageRevisionDao.save(revision);
	}

	public List<WikiRevisionRef> getPendingRevisions() {
		WikiRevisionRefDboExample ex = new WikiRevisionRefDboExample();
		ex.createCriteria().andStatusEqualTo(STATUS_PENDING);
		return withPageRefs(wikiRevisionRefDao.get(ex).stream()
				.sorted(Comparator.comparing(WikiRevisionRefDbo::getWikiPageRevisionId))
				.toList());
	}
}
