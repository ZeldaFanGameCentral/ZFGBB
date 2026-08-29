package com.zfgc.zfgbb.services.search;

import com.zfgc.zfgbb.persistence.LikePatterns;
import java.util.List;

import com.zfgc.zfgbb.model.search.SearchHit;

public interface SearchRealmProvider {

	record SearchCriteria(String term, String pattern, List<Integer> permissionIds, int limit) {

		SearchCriteria(String term, List<Integer> permissionIds, int limit) {
			this(term, LikePatterns.contains(term), permissionIds, limit);
		}
	}

	String code();

	String label();

	int totalMatching(SearchCriteria criteria);

	List<SearchHit> topMatches(SearchCriteria criteria);
}
