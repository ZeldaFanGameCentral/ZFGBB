package com.zfgc.zfgbb.authorization;

import java.io.Serializable;
import java.util.List;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.model.User;

@Component
public class ZfgbbPermissionEvaluator implements PermissionEvaluator {

	private final List<ResourceAccessRules> resourceAccessRules;

	public ZfgbbPermissionEvaluator(List<ResourceAccessRules> resourceAccessRules) {
		this.resourceAccessRules = resourceAccessRules;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		throw new UnsupportedOperationException(
				"hasPermission(target, permission) has no ResourceAccessRules binding and would deny every actor "
						+ "silently; use hasPermission(id, 'RESOURCE_TYPE', 'action') instead");
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		if (authentication == null || targetId == null
				|| !(authentication.getPrincipal() instanceof User actor) || !(targetId instanceof Number))
			return false;
		for (ResourceAccessRules rules : resourceAccessRules) {
			if (rules.supports(targetType))
				return rules.allows(actor, targetType, ((Number) targetId).intValue(), String.valueOf(permission));
		}
		return false;
	}
}
