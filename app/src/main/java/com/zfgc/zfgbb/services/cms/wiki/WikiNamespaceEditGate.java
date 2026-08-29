package com.zfgc.zfgbb.services.cms.wiki;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.authorization.access.WikiAccessRules;
import com.zfgc.zfgbb.authorization.access.WikiAccessRules.NamespaceEditDenial;
import com.zfgc.zfgbb.authorization.access.WikiAccessRules.NamespaceEditPolicy;
import com.zfgc.zfgbb.dataprovider.cms.WikiNamespaceDataProvider;
import com.zfgc.zfgbb.model.users.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WikiNamespaceEditGate {

	private final WikiAccessRules wikiAccessRules;

	private final WikiNamespaceDataProvider namespaceData;

	public boolean canViewerEdit(String namespace, User user) {
		return wikiAccessRules.canViewerEdit(user, () -> editPolicy(namespace));
	}

	public void requireNamespaceEditable(String namespace, User user) {
		Optional<NamespaceEditDenial> denial =
				wikiAccessRules.namespaceEditDenial(namespace, user, editPolicy(namespace));
		if (denial.isPresent())
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, denial.get().message());
	}

	private Optional<NamespaceEditPolicy> editPolicy(String namespace) {
		return namespaceData.editPolicy(namespace)
				.map(policy -> new NamespaceEditPolicy(policy.isSystemManaged(),
						Optional.ofNullable(policy.getEditPermissionCode())));
	}
}
