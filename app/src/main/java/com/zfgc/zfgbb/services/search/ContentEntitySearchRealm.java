package com.zfgc.zfgbb.services.search;

import java.util.List;
import java.util.Optional;

import com.zfgc.zfgbb.mappers.custom.ContentEntitySearchQueryMapper;
import com.zfgc.zfgbb.model.search.SearchHit;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContentEntitySearchRealm implements SearchRealmProvider {

	private final String code;

	private final String label;

	private final String entityType;

	private final String urlPrefix;

	private final ContentEntitySearchQueryMapper contentEntitySearchQueryMapper;

	private final SearchSnippets searchSnippets;

	@Override
	public String code() {
		return code;
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public int totalMatching(SearchCriteria criteria) {
		return contentEntitySearchQueryMapper.countMatchingContentEntities(entityType, criteria.pattern());
	}

	@Override
	public List<SearchHit> topMatches(SearchCriteria criteria) {
		return contentEntitySearchQueryMapper
				.searchContentEntities(entityType, criteria.pattern(), criteria.limit()).stream()
				.map(row -> new SearchHit(code, row.getTitle(),
						searchSnippets.storedSnippet(row, criteria.term()), byLine(row.getContext()),
						urlPrefix + row.getSlug()))
				.toList();
	}

	private static Optional<String> byLine(String authorName) {
		return Optional.ofNullable(authorName).filter(name -> !name.isBlank()).map(name -> "by " + name);
	}
}
