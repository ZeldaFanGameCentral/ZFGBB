package com.zfgc.zfgbb.dataprovider.cms;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.WikiPageCategoryDbo;
import com.zfgc.zfgbb.dbo.WikiPageCategoryDboExample;
import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDbo;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDboExample;
import com.zfgc.zfgbb.dbo.WikiRevisionRefDbo;
import com.zfgc.zfgbb.dbo.WikiRevisionRefDboExample;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.mappers.ContentResourceDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageCategoryDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageRevisionDboMapper;
import com.zfgc.zfgbb.mappers.WikiRevisionRefDboMapper;
import com.zfgc.zfgbb.mapstruct.cms.WikiFileRefMap;
import com.zfgc.zfgbb.mapstruct.cms.WikiPageMap;
import com.zfgc.zfgbb.mapstruct.cms.WikiPageRefMap;
import com.zfgc.zfgbb.mapstruct.cms.WikiRevisionRefMap;
import com.zfgc.zfgbb.model.cms.PagedResult;
import com.zfgc.zfgbb.model.cms.WikiPage;
import com.zfgc.zfgbb.model.cms.WikiPageRef;
import com.zfgc.zfgbb.model.cms.WikiRevisionRef;
import com.zfgc.zfgbb.wiki.WikiTitle;
import com.zfgc.zfgbb.services.cms.WikiNamespaceRegistry;

@Repository
public class WikiDataProvider {

	public static final String STATUS_PENDING = "PENDING";
	public static final String STATUS_APPROVED = "APPROVED";
	public static final String STATUS_REJECTED = "REJECTED";

	public static final String VIRTUAL_NAMESPACE = "Special";

	public static final Set<String> HIDDEN_NAMESPACES = Set.of(VIRTUAL_NAMESPACE, "MediaWiki");


	@Autowired
	private WikiPageDboMapper wikiPageMapper;

	@Autowired
	private WikiPageRevisionDboMapper wikiRevisionMapper;

	@Autowired
	private WikiRevisionRefDboMapper wikiRevisionRefMapper;

	@Autowired
	private WikiPageCategoryDboMapper categoryMapper;

	@Autowired
	private ContentResourceDboMapper contentResourceMapper;

	@Autowired
	private WikiPageMap wikiPageMap;

	@Autowired
	private WikiRevisionRefMap wikiRevisionRefMap;

	@Autowired
	private WikiPageRefMap wikiPageRefMap;

	@Autowired
	private WikiFileRefMap wikiFileRefMap;

	@Autowired
	private WikiNamespaceRegistry namespaceRegistry;

	public WikiPage getWikiPage(String path, Integer revisionId) {
		WikiPageDbo dbo = findPage(path);
		if (dbo == null) {
			if (path != null && path.startsWith("Category:") && path.length() > 9) {
				WikiPage synthetic = new WikiPage();
				synthetic.setId(0);
				synthetic.setNamespace("Category");
				synthetic.setTitle(path.substring(9).replace('_', ' ').trim());
				synthetic.setSlug(path.substring(9));
				synthetic.setCategoryMembers(getCategoryMembers(synthetic.getTitle()));
				return synthetic;
			}
			throw new ZfgcNotFoundException();
		}
		WikiPage page = toWikiPage(dbo);
		if (revisionId != null) {
			WikiPageRevisionDbo rev = wikiRevisionMapper.selectByPrimaryKey(revisionId);
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
		return Optional.ofNullable(wikiPageMapper.selectByPrimaryKey(wikiPageId)).map(this::toWikiPage);
	}

	public Optional<WikiPage> getWikiPageQuietly(String slug) {
		return Optional.ofNullable(findPage(slug)).map(this::toWikiPage);
	}

	public List<String> getNamespaces() {
		Map<String, Long> counts = visiblePages().stream()
				.collect(Collectors.groupingBy(WikiPageDbo::getNamespace, Collectors.counting()));
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
		return wikiRevisionRefMapper.selectByExample(ex).stream()
				.map(WikiRevisionRefDbo::getWikiPageId).collect(Collectors.toSet());
	}

	private boolean isPublicPage(WikiPageDbo dbo, Set<Integer> publishedPageIds) {
		return dbo.getRedirectTo() != null || publishedPageIds.contains(dbo.getWikiPageId());
	}

	private List<WikiPageDbo> visiblePages() {
		Set<Integer> published = publishedPageIds();
		WikiPageDboExample ex = new WikiPageDboExample();
		ex.createCriteria().andNamespaceNotIn(List.copyOf(HIDDEN_NAMESPACES));
		return wikiPageMapper.selectByExample(ex).stream()
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
		return wikiRevisionRefMapper.selectByExample(ex).stream()
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
		List<WikiRevisionRefDbo> revs = wikiRevisionRefMapper.selectByExampleWithLimits(ex);
		List<Integer> pageIds = revs.stream().map(WikiRevisionRefDbo::getWikiPageId).distinct().toList();
		Map<Integer, WikiPageDbo> pages = new HashMap<>();
		if (!pageIds.isEmpty()) {
			WikiPageDboExample pageEx = new WikiPageDboExample();
			pageEx.createCriteria().andWikiPageIdIn(pageIds);
			wikiPageMapper.selectByExample(pageEx).forEach(p -> pages.put(p.getWikiPageId(), p));
		}
		return revs.stream().map(rev -> {
			WikiRevisionRef ref = toRevisionRef(rev);
			WikiPageDbo page = pages.get(rev.getWikiPageId());
			if (page != null) {
				ref.setPage(toPageRef(page));
			}
			return ref;
		}).filter(ref -> ref.getPage() != null).toList();
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
		return categoryMapper.selectByExample(new WikiPageCategoryDboExample()).stream()
				.collect(Collectors.groupingBy(
						WikiPageCategoryDbo::getCategoryName,
						TreeMap::new, Collectors.counting()))
				.entrySet().stream().toList();
	}

	public Map<String, Object> getWikiStatistics() {
		List<WikiPageDbo> pages = visiblePages();
		Map<String, Long> byNamespace = pages.stream().collect(Collectors.groupingBy(
				WikiPageDbo::getNamespace, TreeMap::new, Collectors.counting()));
		long categories = categoryMapper.selectByExample(new WikiPageCategoryDboExample()).stream()
				.map(WikiPageCategoryDbo::getCategoryName).distinct().count();
		return Map.of(
				"totalPages", pages.size(),
				"byNamespace", byNamespace,
				"categories", categories,
				"redirects", pages.stream().filter(p -> p.getRedirectTo() != null).count());
	}

	public List<WikiPageRef> getCategoryMembers(String categoryName) {
		WikiPageCategoryDboExample ex = new WikiPageCategoryDboExample();
		ex.createCriteria().andCategoryNameEqualTo(categoryName);
		List<Integer> pageIds = categoryMapper.selectByExample(ex).stream()
				.map(WikiPageCategoryDbo::getWikiPageId).distinct().toList();
		if (pageIds.isEmpty()) {
			return List.of();
		}
		WikiPageDboExample pageEx = new WikiPageDboExample();
		pageEx.createCriteria().andWikiPageIdIn(pageIds);
		return wikiPageMapper.selectByExample(pageEx).stream()
				.sorted(Comparator.comparing(dbo -> dbo.getTitle().toLowerCase()))
				.map(this::toPageRef).toList();
	}

	public WikiPageDbo findPage(String path) {
		WikiPageDboExample slug = new WikiPageDboExample();
		slug.createCriteria().andSlugEqualTo(path);
		WikiPageDbo byStableSlug = uniquePage(wikiPageMapper.selectByExample(slug), path);
		if (byStableSlug != null)
			return byStableSlug;
		WikiTitle title = namespaceRegistry == null ? WikiTitle.parse(path) : namespaceRegistry.resolve(path);
		WikiPageDboExample ex = new WikiPageDboExample();
		ex.createCriteria().andNamespaceEqualTo(title.namespace()).andTitleEqualTo(title.title());
		WikiPageDbo exact = uniquePage(wikiPageMapper.selectByExample(ex), path);
		if (exact != null)
			return exact;
		WikiPageDboExample alias = new WikiPageDboExample();
		alias.createCriteria().andTitleEqualTo(title.title());
		return uniquePage(wikiPageMapper.selectByExample(alias).stream()
				.filter(page -> page.getNamespace().equalsIgnoreCase(title.namespace())).toList(), path);
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
		wikiRevisionMapper.selectByExample(ex).stream().findFirst().ifPresent(rev -> {
			page.setContent(rev.getContent());
			page.setContentFormat(rev.getContentFormat());
		});
		WikiPageCategoryDboExample catEx = new WikiPageCategoryDboExample();
		catEx.createCriteria().andWikiPageIdEqualTo(dbo.getWikiPageId());
		page.setCategories(categoryMapper.selectByExample(catEx).stream()
				.map(WikiPageCategoryDbo::getCategoryName).sorted().toList());
		if ("Category".equals(dbo.getNamespace())) {
			page.setCategoryMembers(getCategoryMembers(dbo.getTitle()));
		}
		if (dbo.getContentResourceId() != null) {
			ContentResourceDbo file = contentResourceMapper.selectByPrimaryKey(dbo.getContentResourceId());
			if (file != null) {
				page.setFile(wikiFileRefMap.toRef(file));
			}
		}
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
		wikiPageMapper.insert(page);
		return page;
	}

	public WikiPageRevisionDbo getRevision(Integer revisionId) {
		return wikiRevisionMapper.selectByPrimaryKey(revisionId);
	}

	public WikiPageDbo getPage(Integer wikiPageId) {
		return wikiPageMapper.selectByPrimaryKey(wikiPageId);
	}

	public WikiPageRevisionDbo submitRevision(Integer wikiPageId, String content, String summary,
			Integer authorUserId, String authorName) {
		WikiPageRevisionDbo revision = new WikiPageRevisionDbo();
		revision.setWikiPageId(wikiPageId);
		revision.setContent(content);
		revision.setContentSize(content == null ? 0 : content.getBytes(java.nio.charset.StandardCharsets.UTF_8).length);
		revision.setContentFormat("BBCODE");
		revision.setCurrentFlag(false);
		revision.setStatus(STATUS_PENDING);
		revision.setAuthoredTs(OffsetDateTime.now(java.time.ZoneOffset.UTC));
		revision.setAuthorUserId(authorUserId);
		revision.setAuthorName(authorName);
		revision.setSummary(summary);
		wikiRevisionMapper.insert(revision);
		return revision;
	}

	public void approveRevision(WikiPageRevisionDbo revision) {
		WikiPageRevisionDboExample currentEx = new WikiPageRevisionDboExample();
		currentEx.createCriteria().andWikiPageIdEqualTo(revision.getWikiPageId()).andCurrentFlagEqualTo(true);
		for (WikiPageRevisionDbo old : wikiRevisionMapper.selectByExample(currentEx)) {
			old.setCurrentFlag(false);
			wikiRevisionMapper.updateByPrimaryKey(old);
		}
		revision.setCurrentFlag(true);
		revision.setStatus(STATUS_APPROVED);
		wikiRevisionMapper.updateByPrimaryKey(revision);
	}

	public void rejectRevision(WikiPageRevisionDbo revision) {
		revision.setStatus(STATUS_REJECTED);
		wikiRevisionMapper.updateByPrimaryKey(revision);
	}

	public List<WikiRevisionRef> getPendingRevisions() {
		WikiRevisionRefDboExample ex = new WikiRevisionRefDboExample();
		ex.createCriteria().andStatusEqualTo(STATUS_PENDING);
		List<WikiRevisionRefDbo> revs = wikiRevisionRefMapper.selectByExample(ex);
		Map<Integer, WikiPageDbo> pages = new HashMap<>();
		List<Integer> pageIds = revs.stream().map(WikiRevisionRefDbo::getWikiPageId).distinct().toList();
		if (!pageIds.isEmpty()) {
			WikiPageDboExample pageEx = new WikiPageDboExample();
			pageEx.createCriteria().andWikiPageIdIn(pageIds);
			wikiPageMapper.selectByExample(pageEx).forEach(p -> pages.put(p.getWikiPageId(), p));
		}
		return revs.stream()
				.sorted(Comparator.comparing(WikiRevisionRefDbo::getWikiPageRevisionId))
				.map(rev -> {
					WikiRevisionRef ref = toRevisionRef(rev);
					WikiPageDbo page = pages.get(rev.getWikiPageId());
					if (page != null) {
						ref.setPage(toPageRef(page));
					}
					return ref;
				}).toList();
	}
}
