package com.zfgc.zfgbb.dao.forum;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.mappers.custom.ForumSearchQueryMapper;
import com.zfgc.zfgbb.mappers.custom.SearchMatchRow;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ForumSearchDao {

	private final ForumSearchQueryMapper forumSearchQueryMapper;

	public List<SearchMatchRow> searchThreadNames(String pattern, List<Integer> permissionIds, int limit) {
		return forumSearchQueryMapper.searchThreadNames(pattern, permissionIds, limit);
	}

	public List<SearchMatchRow> searchMessages(String pattern, List<Integer> permissionIds, int limit) {
		return forumSearchQueryMapper.searchMessages(pattern, permissionIds, limit);
	}

	public int countMatchingThreads(String pattern, List<Integer> permissionIds) {
		return forumSearchQueryMapper.countMatchingThreads(pattern, permissionIds);
	}
}
