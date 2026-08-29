package com.zfgc.zfgbb.dao.cms;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.mappers.custom.SearchMatchRow;
import com.zfgc.zfgbb.mappers.custom.WikiSearchQueryMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class WikiSearchDao {

	private final WikiSearchQueryMapper wikiSearchQueryMapper;

	public List<SearchMatchRow> searchWikiPages(String pattern, List<String> hiddenNamespaces, int limit) {
		return wikiSearchQueryMapper.searchWikiPages(pattern, hiddenNamespaces, limit);
	}

	public int countMatchingWikiPages(String pattern, List<String> hiddenNamespaces) {
		return wikiSearchQueryMapper.countMatchingWikiPages(pattern, hiddenNamespaces);
	}
}
