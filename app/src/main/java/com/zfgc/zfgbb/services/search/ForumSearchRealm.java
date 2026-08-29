package com.zfgc.zfgbb.services.search;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.authorization.BoardVisibilityChokepoint;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.dao.forum.ForumSearchDao;
import com.zfgc.zfgbb.mappers.custom.SearchMatchRow;
import com.zfgc.zfgbb.model.search.SearchHit;

import lombok.RequiredArgsConstructor;

@Component
@Order(10)
@RequiredArgsConstructor
@BoardVisibilityChokepoint
public class ForumSearchRealm implements SearchRealmProvider {

	private final ForumSearchDao forumSearchDao;

	private final SearchSnippets searchSnippets;

	@Override
	public String code() {
		return "forum";
	}

	@Override
	public String label() {
		return "Forum Topics";
	}

	@Override
	public int totalMatching(SearchCriteria criteria) {
		return forumSearchDao.countMatchingThreads(criteria.pattern(), criteria.permissionIds());
	}

	@Override
	public List<SearchHit> topMatches(SearchCriteria criteria) {
		Map<String, SearchHit> byThread = new LinkedHashMap<>();
		for (SearchMatchRow row : forumSearchDao.searchThreadNames(criteria.pattern(),
				criteria.permissionIds(), criteria.limit()))
			byThread.put(row.getRefId(), threadHit(row, Optional.empty()));
		for (SearchMatchRow row : forumSearchDao.searchMessages(criteria.pattern(),
				criteria.permissionIds(), criteria.limit()))
			if (byThread.containsKey(row.getRefId()) || byThread.size() < criteria.limit())
				byThread.put(row.getRefId(), threadHit(row,
						searchSnippets.renderedSnippet(row, criteria.term(), ContentScope.FORUM)));
		return List.copyOf(byThread.values());
	}

	private SearchHit threadHit(SearchMatchRow row, Optional<String> snippet) {
		return new SearchHit(code(), row.getTitle(), snippet, Optional.ofNullable(row.getContext()),
				"/forum/thread/" + row.getSlug() + "/1");
	}
}
