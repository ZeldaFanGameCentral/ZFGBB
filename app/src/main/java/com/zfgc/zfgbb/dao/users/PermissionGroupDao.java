package com.zfgc.zfgbb.dao.users;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.PermissionGroupDbo;
import com.zfgc.zfgbb.dbo.PermissionGroupDboExample;
import com.zfgc.zfgbb.mappers.PermissionGroupDboMapper;

@Repository
public class PermissionGroupDao extends IdentityDao<PermissionGroupDbo, PermissionGroupDboExample> {

	public PermissionGroupDao(PermissionGroupDboMapper mapper) {
		super(mapper);
	}

	public Set<Integer> starImageResourceIdsAmong(List<Integer> contentResourceIds) {
		PermissionGroupDboExample starred = new PermissionGroupDboExample();
		starred.createCriteria().andStarImageIn(contentResourceIds);
		return get(starred).stream().map(PermissionGroupDbo::getStarImage).collect(Collectors.toSet());
	}
}
