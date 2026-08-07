package com.zfgc.zfgbb.services.search;

import java.util.List;
import java.util.Optional;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.dataprovider.cms.WikiDataProvider;
import com.zfgc.zfgbb.mappers.custom.SearchMatchRow;
import com.zfgc.zfgbb.mappers.custom.WikiSearchQueryMapper;
import com.zfgc.zfgbb.model.search.SearchHit;

import lombok.RequiredArgsConstructor;

@Component
@Order(20)
@RequiredArgsConstructor
public class WikiSearchRealm implements SearchRealmProvider {

	private static final String ARTICLE_NAMESPACE = "MAIN";

	private final WikiSearchQueryMapper wikiSearchQueryMapper;

	private final WikiDataProvider wikiPages;

	private final SearchSnippets searchSnippets;

	@Override
	public String code() {
		return "wiki";
	}

	@Override
	public String label() {
		return "Wiki Pages";
	}

	@Override
	public int totalMatching(SearchCriteria criteria) {
		return wikiSearchQueryMapper.countMatchingWikiPages(criteria.pattern(),
				wikiPages.theNamespacesNoReaderViewShows());
	}

	@Override
	public List<SearchHit> topMatches(SearchCriteria criteria) {
		return wikiSearchQueryMapper.searchWikiPages(criteria.pattern(),
				wikiPages.theNamespacesNoReaderViewShows(), criteria.limit()).stream()
				.map(row -> new SearchHit(code(), displayTitle(row),
						searchSnippets.renderedSnippet(row, criteria.term(), ContentScope.WIKI),
						Optional.of(namespaceLabel(row.getContext())), "/wiki/" + row.getSlug()))
				.toList();
	}

	private static String displayTitle(SearchMatchRow row) {
		if (row.getContext() != null && !row.getContext().equals(ARTICLE_NAMESPACE))
			return row.getContext() + ":" + row.getTitle();
		return row.getTitle();
	}

	private static String namespaceLabel(String namespace) {
		return namespace == null || namespace.equals(ARTICLE_NAMESPACE) ? "Article" : namespace;
	}
}
