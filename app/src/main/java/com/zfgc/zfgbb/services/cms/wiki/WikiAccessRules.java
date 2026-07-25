package com.zfgc.zfgbb.services.cms.wiki;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.authorization.AuthorityTiers;
import com.zfgc.zfgbb.dataprovider.cms.WikiNamespaceDataProvider;
import com.zfgc.zfgbb.mappers.custom.WikiNamespaceCustomMapper.EditPolicyRecord;
import com.zfgc.zfgbb.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WikiAccessRules {

	private final AuthorityTiers authorityTiers;

	private final WikiNamespaceDataProvider namespaceData;

	public boolean isEditableBy(String namespace, User user) {
		return editableUnder(namespaceData.editPolicy(namespace).orElse(null), user);
	}

	public boolean canViewerEdit(String namespace, User user) {
		return user != null && authorityTiers.authenticated(user) && !authorityTiers.isReadOnly(user)
				&& isEditableBy(namespace, user);
	}

	public void requireNamespaceEditable(String namespace, User user) {
		EditPolicyRecord policy = namespaceData.editPolicy(namespace).orElse(null);
		if (editableUnder(policy, user))
			return;
		if (policy != null && policy.systemManaged())
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Namespace '" + namespace
					+ "' is system managed and cannot be edited through the wiki");
		String required = policy == null ? null : policy.editPermissionCode();
		throw new ResponseStatusException(HttpStatus.FORBIDDEN, required == null || required.isBlank()
				? "You do not have permission to edit the '" + namespace + "' namespace"
				: "Namespace '" + namespace + "' requires the " + required.trim() + " permission");
	}

	private boolean editableUnder(EditPolicyRecord policy, User user) {
		if (policy == null || policy.systemManaged())
			return false;
		String required = policy.editPermissionCode();
		if (required == null || required.isBlank())
			return true;
		return user != null && authorityTiers.hasRole(user, "ROLE_" + required.trim());
	}
}
