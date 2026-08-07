package com.zfgc.zfgbb.testsupport;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;

import com.zfgc.zfgbb.config.security.MethodSecurityConfig;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.users.Permission;

public final class AccessControlFixtures {

	public static final int MEMBER_ID = 10;
	public static final int OTHER_ID = 99;

	private AccessControlFixtures() {}

	public static RoleHierarchy roleHierarchy() {
		return MethodSecurityConfig.roleHierarchy();
	}

	public static User user(Integer userId, String... permissionCodes) {
		List<Permission> granted = new ArrayList<>();
		for (String code : permissionCodes) {
			Permission permission = new Permission();
			permission.setPermissionCode(code);
			granted.add(permission);
		}
		return User.builder().userId(userId).permissions(granted).build();
	}

	public static User member() {
		return user(MEMBER_ID, "ZFGC_USER");
	}

	public static User moderator() {
		return user(11, "ZFGC_SITE_MODERATOR");
	}

	public static User siteAdmin() {
		return user(12, "ZFGC_SITE_ADMIN");
	}

	public static User readOnlyMember() {
		return user(13, "ZFGC_USER", "ZFGC_READ_ONLY");
	}

	public static User profileAdmin() {
		return user(14, "ZFGC_PROFILE_ADMIN");
	}

	public static User guest() {
		return user(-1, "ZFGC_GUEST", "ZFGC_READ_ONLY");
	}
}
