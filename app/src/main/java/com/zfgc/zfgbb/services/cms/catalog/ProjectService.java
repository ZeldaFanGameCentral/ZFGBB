package com.zfgc.zfgbb.services.cms.catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.services.cms.CmsPageRenderer;
import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.dataprovider.cms.ProjectDataProvider;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.cms.PagedResult;
import com.zfgc.zfgbb.model.cms.Project;
import com.zfgc.zfgbb.model.cms.ProjectShowcase;
import com.zfgc.zfgbb.content.renderer.templates.TemplateDataService;
import com.zfgc.zfgbb.content.renderer.templates.TemplateSource;
import com.zfgc.zfgbb.services.forum.ForumService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService implements TemplateDataService {

	private static final int FULL_CATALOG_PAGE_SIZE = Integer.MAX_VALUE;

	private final ProjectDataProvider projectDataProvider;

	private final CmsPageRenderer cmsPageRenderer;

	private final ForumService forumService;

	public PagedResult<Project> getProjects(String search, String status, String language, String author,
			Boolean hasDownload, String sort, Integer page, Integer pageSize) {
		CatalogListing listing = CatalogListing.of(page, pageSize);
		return projectDataProvider.getProjects(CatalogListing.blankToNull(search), CatalogListing.blankToNull(status),
				CatalogListing.blankToNull(language), CatalogListing.blankToNull(author), hasDownload, sort,
				listing.page(), listing.pageSize());
	}

	public Map<String, List<Map.Entry<String, Long>>> getFacets() {
		return projectDataProvider.getFacets();
	}

	@TemplateSource("/projects/{slug}")
	public Project getProject(String slug) {
		Project project = projectDataProvider.getProject(slug);
		cmsPageRenderer.renderPage(project.getPage(), ContentScope.PROJECT);
		project.setSummary(null);
		if (project.getPage() != null) {
			project.getPage().setContent(null);
		}
		return project;
	}

	public ProjectShowcase getProjectShowcase() {
		PagedResult<Project> catalog =
				projectDataProvider.getProjects(null, null, null, null, null, null, 1, FULL_CATALOG_PAGE_SIZE);
		List<Project> projects = catalog.getItems();
		int total = (int) catalog.getTotal();
		Optional<Project> featured = resolveFeatured(null);
		featured.ifPresent(project -> cmsPageRenderer.renderPage(project.getPage(), ContentScope.PROJECT));
		String excludeSlug = featured.map(Project::getSlug).orElse(null);
		List<Project> recent = rail(projects, "updated", CatalogListing.SHOWCASE_RECENT, excludeSlug);
		List<Project> random = rail(projects, "random", CatalogListing.SHOWCASE_RAIL, excludeSlug);
		List<Project> topRated = rail(projects, "rating", CatalogListing.SHOWCASE_RAIL, excludeSlug);
		List<Project> mostDownloaded = rail(projects, "downloads", CatalogListing.SHOWCASE_RAIL, excludeSlug);
		return new ProjectShowcase(featured.orElse(null), recent, random, topRated, mostDownloaded, total);
	}

	@TemplateSource("/projects/card")
	public Map<String, Object> getProjectCard(String slug) {
		Optional<Project> found = resolveFeaturedCard(slug);
		if (found.isEmpty())
			return Map.of("found", false);
		Project project = found.get();
		String summary = project.getSummaryText();
		if (summary == null) {
			summary = "";
		}
		if (summary.length() > 220) {
			summary = summary.substring(0, 220) + "…";
		}
		String stats = (project.getStatus() == null ? "" : project.getStatus())
				+ (project.getRating() != null && project.getVoteCount() != null && project.getVoteCount() > 0
						? " · ★ " + String.format(Locale.ROOT, "%.1f", project.getRating())
								+ " (" + project.getVoteCount() + " votes)"
						: "");
		Map<String, Object> card = new HashMap<>();
		card.put("found", true);
		card.put("title", project.getTitle());
		card.put("slug", project.getSlug());
		card.put("stats", stats);
		card.put("summary", summary);
		if (project.getPreviewContentResourceId() != null) {
			card.put("preview", project.getPreviewContentResourceId());
		}
		if (project.getAuthor() != null) {
			card.put("author", project.getAuthor());
		}
		if (project.getCreatedUserId() != null) {
			card.put("authorUserId", project.getCreatedUserId());
		}
		return card;
	}

	@TemplateSource("/projects/news")
	public Map<String, Object> getProjectNewsFeed(String slug, Integer limit) {
		Optional<Project> found = resolveFeatured(slug);
		int max = limit == null || limit < 1 ? 5 : limit;
		if (found.isEmpty())
			return Map.of("empty", true, "items", List.of());
		Project project = found.get();
		List<Map<String, Object>> items = projectDataProvider.getProjectNews(project.getId()).stream()
				.limit(max)
				.map(entry -> {
					Map<String, Object> item = new HashMap<>();
					item.put("threadId", entry.getThreadId());
					item.put("subject", entry.getSubject() == null || entry.getSubject().isBlank()
							? "News update" : entry.getSubject());
					item.put("date", entry.getPublishedTs() == null ? ""
							: " — " + entry.getPublishedTs().toLocalDate());
					return item;
				}).toList();
		return Map.of("empty", items.isEmpty(), "items", items);
	}

	public Project startProjectDiscussion(String slug, User user) {
		Project project = getProject(slug);
		if (project.getThreadId() != null) {
			return project;
		}
		Integer threadId = forumService.createDiscussionThread(project.getTitle(),
				"Discussion thread for [project=" + project.getSlug() + "]" + project.getTitle() + "[/project].",
				ContentFormat.BBCODE.name(), user);
		projectDataProvider.linkProjectThread(project.getId(), threadId);
		project.setThreadId(threadId);
		return project;
	}

	private Optional<Project> resolveFeatured(String slug) {
		return findProject(slug).or(() -> projectDataProvider.findFeaturedCollectionProject());
	}

	private Optional<Project> findProject(String slug) {
		if (slug == null || slug.isBlank())
			return Optional.empty();
		try {
			return Optional.of(projectDataProvider.getProject(slug.trim()));
		} catch (ZfgcNotFoundException notFound) {
			return Optional.empty();
		}
	}

	private Optional<Project> resolveFeaturedCard(String slug) {
		return findProjectCard(slug).or(() -> projectDataProvider.findFeaturedCollectionProjectCard());
	}

	private Optional<Project> findProjectCard(String slug) {
		if (slug == null || slug.isBlank())
			return Optional.empty();
		return projectDataProvider.getProjectCardData(slug.trim());
	}

	private List<Project> rail(List<Project> catalog, String sort, int limit, String excludeSlug) {
		return CatalogListing.rail(
				(railSort, railLimit) -> sortedCatalog(catalog, railSort).stream().limit(railLimit).toList(),
				Project::getSlug, sort, limit, excludeSlug);
	}

	private static List<Project> sortedCatalog(List<Project> catalog, String sort) {
		List<Project> sorted = new ArrayList<>(catalog);
		if (sort.equals("random")) {
			Collections.shuffle(sorted);
		} else {
			sorted.sort(catalogComparator(sort));
		}
		return sorted;
	}

	private static Comparator<Project> catalogComparator(String sort) {
		Comparator<Project> byTitle = Comparator.comparing(project -> {
			String value = project.getTitle();
			return value == null ? "" : value.toLowerCase();
		});
		return switch (sort == null ? "" : sort) {
			case "updated" -> nullsLastDesc(Project::getLastUpdatedTs).thenComparing(byTitle);
			case "downloads" -> nullsLastDesc(Project::getDownloadCount).thenComparing(byTitle);
			case "rating" -> nullsLastDesc(Project::getRating)
					.thenComparing(nullsLastDesc(Project::getVoteCount)).thenComparing(byTitle);
			default -> byTitle;
		};
	}

	private static <C extends Comparable<? super C>> Comparator<Project> nullsLastDesc(Function<Project, C> key) {
		return Comparator.comparing(key, Comparator.nullsLast(Comparator.reverseOrder()));
	}
}
