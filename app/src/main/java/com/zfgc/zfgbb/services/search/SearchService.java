package com.zfgc.zfgbb.services.search;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.authorization.BoardVisibilityChokepoint;
import com.zfgc.zfgbb.dataprovider.cms.WikiDataProvider;
import com.zfgc.zfgbb.mappers.custom.SearchQueryMapper;
import com.zfgc.zfgbb.mappers.custom.SearchQueryMapper.Hit;
import com.zfgc.zfgbb.model.search.SearchGroup;
import com.zfgc.zfgbb.model.search.SearchHit;
import com.zfgc.zfgbb.model.search.SearchRealm;
import com.zfgc.zfgbb.model.search.SearchResults;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@BoardVisibilityChokepoint
public class SearchService {

	private static final int PER_TYPE_LIMIT = 6;
	private static final int SNIPPET_RADIUS = 80;

	public static final List<SearchRealm> REALMS = List.of(
			new SearchRealm("forum", "Forum Topics"),
			new SearchRealm("wiki", "Wiki Pages"),
			new SearchRealm("project", "CMS Projects"),
			new SearchRealm("resource", "CMS Resources"));

	private static final List<SearchRealm> REALM_FILTERS = Stream.concat(
			Stream.of(new SearchRealm("", "All")), REALMS.stream()).toList();

	private final SearchQueryMapper searchMapper;

	private final WikiDataProvider wikiPages;

	public List<SearchRealm> getRealms() {
		return REALM_FILTERS;
	}

	private static String label(String type) {
		return REALMS.stream().filter(realm -> realm.type().equals(type)).findFirst()
				.map(SearchRealm::label).orElse(type);
	}

	public SearchResults search(String query, List<String> types, List<Integer> permissionIds) {
		String trimmed = query == null ? "" : query.trim();
		if (trimmed.length() < 2) {
			return new SearchResults(trimmed, 0, List.of());
		}
		String pattern = "%" + trimmed.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_") + "%";
		boolean all = types == null || types.isEmpty();
		List<Integer> perms = permissionIds == null || permissionIds.isEmpty()
				? List.of(-1)
				: permissionIds;

		List<SearchGroup> groups = new ArrayList<>();
		int total = 0;
		if (all || types.contains("forum")) {
			SearchGroup forum = forumGroup(pattern, trimmed, perms);
			total += forum.getTotal();
			groups.add(forum);
		}
		if (all || types.contains("wiki")) {
			SearchGroup wiki = cappedGroup("wiki", searchMapper.searchWiki(pattern,
					wikiPages.theNamespacesNoReaderViewShows(), PER_TYPE_LIMIT + 1),
					hit -> new SearchHit("wiki", displayTitle(hit),
							snippet(hit.getBody(), trimmed), namespaceLabel(hit.getContext()),
							"/wiki/" + hit.getSlug()));
			total += wiki.getTotal();
			groups.add(wiki);
		}
		if (all || types.contains("project")) {
			SearchGroup projects = cappedGroup("project",
					searchMapper.searchProjects(pattern, PER_TYPE_LIMIT + 1),
					hit -> new SearchHit("project", hit.getTitle(),
							snippet(hit.getBody(), trimmed), byLine(hit.getContext()),
							"/content/projects/" + hit.getSlug()));
			total += projects.getTotal();
			groups.add(projects);
		}
		if (all || types.contains("resource")) {
			SearchGroup resources = cappedGroup("resource",
					searchMapper.searchResources(pattern, PER_TYPE_LIMIT + 1),
					hit -> new SearchHit("resource", hit.getTitle(),
							snippet(hit.getBody(), trimmed), byLine(hit.getContext()),
							"/content/resources/" + hit.getSlug()));
			total += resources.getTotal();
			groups.add(resources);
		}
		return new SearchResults(trimmed, total, groups);
	}

	private SearchGroup forumGroup(String pattern, String query, List<Integer> perms) {
		Map<String, SearchHit> byThread = new LinkedHashMap<>();
		List<Hit> threadHits = searchMapper.searchThreadNames(pattern, perms, PER_TYPE_LIMIT + 1);
		boolean more = threadHits.size() > PER_TYPE_LIMIT;
		for (Hit hit : threadHits.stream().limit(PER_TYPE_LIMIT).toList()) {
			byThread.put(hit.getRefId(), new SearchHit("forum", hit.getTitle(),
					null, hit.getContext(), "/forum/thread/" + hit.getSlug() + "/1"));
		}
		for (Hit hit : searchMapper.searchMessages(pattern, perms, PER_TYPE_LIMIT + 1)) {
			SearchHit existing = byThread.get(hit.getRefId());
			String snippet = snippet(hit.getBody(), query);
			if (existing != null) {
				existing.setSnippet(snippet);
			} else if (byThread.size() < PER_TYPE_LIMIT) {
				byThread.put(hit.getRefId(), new SearchHit("forum", hit.getTitle(),
						snippet, hit.getContext(), "/forum/thread/" + hit.getSlug() + "/1"));
			} else {
				more = true;
			}
		}
		List<SearchHit> hits = new ArrayList<>(byThread.values());
		SearchGroup group = new SearchGroup("forum", label("forum"), hits.size(), hits);
		group.setMore(more);
		return group;
	}

	private SearchGroup cappedGroup(String type, List<Hit> found,
			Function<Hit, SearchHit> toHit) {
		List<SearchHit> hits = new ArrayList<>(found.stream().limit(PER_TYPE_LIMIT).map(toHit).toList());
		SearchGroup group = new SearchGroup(type, label(type), hits.size(), hits);
		group.setMore(found.size() > PER_TYPE_LIMIT);
		return group;
	}

	private String byLine(String context) {
		return context == null || context.isBlank() ? null : "by " + context;
	}

	private String snippet(String body, String query) {
		if (body == null) {
			return null;
		}
		String plain = body
				.replaceAll("\\[[^\\]]*\\]", " ")
				.replaceAll("<[^>]*>", " ")
				.replaceAll("\\{\\{[^}]*\\}\\}", " ")
				.replaceAll("\\s+", " ")
				.trim();
		if (plain.isEmpty()) {
			return null;
		}
		int matchIndex = plain.toLowerCase().indexOf(query.toLowerCase());
		if (matchIndex < 0) {
			return plain.length() > SNIPPET_RADIUS * 2 ? plain.substring(0, SNIPPET_RADIUS * 2) + "…" : plain;
		}
		int start = Math.max(0, matchIndex - SNIPPET_RADIUS);
		int end = Math.min(plain.length(), matchIndex + query.length() + SNIPPET_RADIUS);
		String core = plain.substring(start, end);
		return (start > 0 ? "…" : "") + core + (end < plain.length() ? "…" : "");
	}

	private String displayTitle(Hit hit) {
		if (hit.getContext() != null && !"MAIN".equals(hit.getContext())) {
			return hit.getContext() + ":" + hit.getTitle();
		}
		return hit.getTitle();
	}

	private String namespaceLabel(String namespace) {
		return namespace == null || "MAIN".equals(namespace) ? "Article" : namespace;
	}
}
