package com.zfgc.zfgbb.dao.users;

import java.util.stream.Collectors;
import java.util.Set;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.ReadDao;
import com.zfgc.zfgbb.dbo.UserPermissionViewDbo;
import com.zfgc.zfgbb.dbo.UserPermissionViewDboExample;
import com.zfgc.zfgbb.mappers.UserPermissionViewDboMapper;

@Repository
public class UserPermissionViewDao extends ReadDao<UserPermissionViewDbo, UserPermissionViewDboExample> {

	private static final String SITE_ADMIN = "ZFGC_SITE_ADMIN";

	public UserPermissionViewDao(UserPermissionViewDboMapper mapper) {
		super(mapper);
	}

	public boolean isSiteAdmin(Integer userId) {
		UserPermissionViewDboExample adminGrant = new UserPermissionViewDboExample();
		adminGrant.createCriteria().andUserIdEqualTo(userId).andPermissionCodeEqualTo(SITE_ADMIN);
		return exists(adminGrant);
	}

	public int countSiteAdmins() {
		return siteAdminUserIds().size();
	}

	public Set<Integer> siteAdminUserIds() {
		UserPermissionViewDboExample adminGrants = new UserPermissionViewDboExample();
		adminGrants.createCriteria().andPermissionCodeEqualTo(SITE_ADMIN);
		return get(adminGrants).stream().map(UserPermissionViewDbo::getUserId).collect(Collectors.toSet());
	}
}
