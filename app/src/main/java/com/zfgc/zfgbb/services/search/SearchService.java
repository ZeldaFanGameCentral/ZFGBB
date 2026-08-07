package com.zfgc.zfgbb.services.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.content.renderer.ContentRenderingService;
import com.zfgc.zfgbb.model.search.SearchGroup;
import com.zfgc.zfgbb.model.search.SearchRealm;
import com.zfgc.zfgbb.model.search.SearchResults;
import com.zfgc.zfgbb.services.search.SearchRealmProvider.SearchCriteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {

	private static final int PER_TYPE_LIMIT = 6;

	private static final int SHORTEST_SEARCHABLE_TERM = 2;

	private final List<SearchRealmProvider> realms;

	private final ContentRenderingService contentRenderingService;

	public List<SearchRealm> getRealms() {
		return Stream.concat(Stream.of(new SearchRealm("", "All")),
				realms.stream().map(realm -> new SearchRealm(realm.code(), realm.label()))).toList();
	}

	public SearchResults search(String query, List<String> types, List<Integer> permissionIds) {
		String trimmed = query == null ? "" : query.trim();
		if (trimmed.length() < SHORTEST_SEARCHABLE_TERM)
			return new SearchResults(trimmed, 0, List.of());
		SearchCriteria criteria = new SearchCriteria(trimmed,
				permissionIds == null || permissionIds.isEmpty() ? List.of(-1) : permissionIds,
				PER_TYPE_LIMIT);

		List<SearchGroup> groups = new ArrayList<>();
		try (ContentRenderingService.QuoteScope searchResolvesNoQuotedSource =
				contentRenderingService.openQuoteScope(List.of(), Set.of())) {
			for (SearchRealmProvider realm : realms)
				if (types.isEmpty() || types.contains(realm.code()))
					groups.add(new SearchGroup(realm.code(), realm.label(), realm.totalMatching(criteria),
							realm.topMatches(criteria)));
		}
		return new SearchResults(trimmed, groups.stream().mapToInt(SearchGroup::total).sum(), groups);
	}

}
