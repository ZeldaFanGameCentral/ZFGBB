package com.zfgc.zfgbb.services.cms.catalog;

import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.services.cms.CmsPageRenderer;
import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.dataprovider.cms.ResourceDataProvider;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.cms.PagedResult;
import com.zfgc.zfgbb.model.cms.Resource;
import com.zfgc.zfgbb.model.cms.ResourceShowcase;
import com.zfgc.zfgbb.services.forum.ForumService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ResourceService {

	private final ResourceDataProvider resourceDataProvider;

	private final CmsPageRenderer cmsPageRenderer;

	private final ForumService forumService;

	public PagedResult<Resource> getResources(String search, String type, String author, Boolean hasDownload,
			String sort, Integer page, Integer pageSize) {
		CatalogListing listing = CatalogListing.of(page, pageSize);
		return resourceDataProvider.getResources(StringUtils.trimToNull(search), StringUtils.trimToNull(type),
				StringUtils.trimToNull(author), hasDownload, sort, listing.page(), listing.pageSize());
	}

	public List<Map.Entry<String, Long>> getResourceTypes() {
		return resourceDataProvider.getResourceTypes();
	}

	public Resource getResource(String slug) {
		Resource resource = resourceDataProvider.getResource(slug);
		cmsPageRenderer.renderPage(resource.getPage(), ContentScope.RESOURCE);
		return resource;
	}

	public ResourceShowcase getResourceShowcase() {
		PagedResult<Resource> catalog = resourceDataProvider.getResources(null, null, null, null, null, 1,
				Integer.MAX_VALUE);
		int total = (int) catalog.getTotal();
		List<Resource> resources = catalog.getItems();
		Resource featured = resources.stream().min(comparator("rating")).orElse(null);
		if (featured != null) {
			featured = getResource(featured.getSlug());
		}
		String excludeSlug = featured == null ? null : featured.getSlug();
		List<Resource> recent = rail(resources, "newest", CatalogListing.SHOWCASE_RECENT, excludeSlug);
		List<Resource> random = rail(resources, "random", CatalogListing.SHOWCASE_RAIL, excludeSlug);
		List<Resource> topRated = rail(resources, "rating", CatalogListing.SHOWCASE_RAIL, excludeSlug);
		List<Resource> mostDownloaded = rail(resources, "downloads", CatalogListing.SHOWCASE_RAIL, excludeSlug);
		return new ResourceShowcase(featured, recent, random, topRated, mostDownloaded, total);
	}

	public Resource startResourceDiscussion(String slug, User user) {
		Resource resource = getResource(slug);
		if (resource.getThreadId() != null) {
			return resource;
		}
		Integer threadId = forumService.createDiscussionThread(resource.getTitle(),
				"Discussion thread for the resource [b]" + resource.getTitle() + "[/b].",
				ContentFormat.BBCODE.name(), user);
		resourceDataProvider.linkResourceThread(resource.getId(), threadId);
		resource.setThreadId(threadId);
		return resource;
	}

	private List<Resource> rail(List<Resource> catalog, String sort, int limit, String excludeSlug) {
		List<Resource> ordered = new ArrayList<>(catalog);
		if (sort.equals("random")) {
			Collections.shuffle(ordered);
		} else {
			ordered.sort(comparator(sort));
		}
		return ordered.stream()
				.filter(resource -> excludeSlug == null || !resource.getSlug().equals(excludeSlug))
				.limit(limit).toList();
	}

	private static Comparator<Resource> comparator(String sort) {
		Comparator<Resource> byTitle = Comparator.comparing(ResourceService::titleKey);
		return switch (sort == null ? "" : sort) {
			case "newest" -> nullsLastDesc(Resource::getPublishedTs).thenComparing(byTitle);
			case "rating" -> nullsLastDesc(Resource::getRating)
					.thenComparing(nullsLastDesc(Resource::getVoteCount)).thenComparing(byTitle);
			case "downloads" -> nullsLastDesc(Resource::getDownloadCount).thenComparing(byTitle);
			default -> byTitle;
		};
	}

	private static <C extends Comparable<? super C>> Comparator<Resource> nullsLastDesc(Function<Resource, C> key) {
		return Comparator.comparing(key, Comparator.nullsLast(Comparator.reverseOrder()));
	}

	private static String titleKey(Resource resource) {
		return resource.getTitle() == null ? "" : resource.getTitle().toLowerCase();
	}
}
