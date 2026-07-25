package com.zfgc.zfgbb.services.cms.wiki;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.dataprovider.cms.WikiNamespaceDataProvider;
import com.zfgc.zfgbb.mappers.custom.WikiNamespaceCustomMapper;
import com.zfgc.zfgbb.wiki.WikiNamespaceRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WikiNamespaceRegistry {

	private final WikiNamespaceCustomMapper wikiNamespaceCustomMapper;

	private final WikiNamespaceDataProvider namespaceData;

	public List<WikiNamespaceCustomMapper.ImportNamespaceRecord> listImportNamespaces() {
		List<WikiNamespaceCustomMapper.ImportNamespaceRecord> configured =
				wikiNamespaceCustomMapper.listImportNamespaces();
		for (WikiNamespaceCustomMapper.ImportNamespaceRecord row : configured) {
			String holder = namespaceData.nameForRole(WikiNamespaceRole.ofMediaWikiNamespaceId(row.getSourceNamespaceId()));
			if (holder != null)
				row.setNamespaceName(holder);
		}
		return configured;
	}

	public List<WikiNamespaceCustomMapper.ImportNamespaceRecord> saveImportNamespace(Integer sourceNamespaceId,
			String namespaceName) {
		if (sourceNamespaceId == null || sourceNamespaceId < 0)
			throw new IllegalArgumentException("sourceNamespaceId must be zero or greater");
		String name = namespaceName == null ? "" : namespaceName.trim();
		if (name.isEmpty() || name.length() > 100 || name.contains(":"))
			throw new IllegalArgumentException(
					"namespaceName must be non-blank, at most 100 characters and must not contain ':'");
		WikiNamespaceRole role = WikiNamespaceRole.ofMediaWikiNamespaceId(sourceNamespaceId);
		if (role != null) {
			String roleHolder = namespaceData.nameForRole(role);
			if (roleHolder != null && !roleHolder.equalsIgnoreCase(name))
				throw new IllegalArgumentException("MediaWiki namespace " + sourceNamespaceId + " is the "
						+ role.name() + " namespace, which this wiki already calls '" + roleHolder
						+ "'. Rename that namespace instead of remapping the import, or the engine would "
						+ "stop recognising it.");
			WikiNamespaceRole heldByName = namespaceData.roleOf(name);
			if (heldByName != null && heldByName != role)
				throw new IllegalArgumentException("Namespace '" + name + "' already serves as this wiki's "
						+ heldByName.name() + " namespace, so it cannot also be MediaWiki namespace "
						+ sourceNamespaceId + " (" + role.name() + ").");
			if (roleHolder == null && namespaceData.assignEngineRole(name, role) == 0)
				throw new IllegalArgumentException("Namespace '" + name + "' does not exist yet, so it cannot be "
						+ "bound to MediaWiki namespace " + sourceNamespaceId + " (" + role.name()
						+ "). Create the namespace first.");
		}
		wikiNamespaceCustomMapper.upsertImportNamespace(sourceNamespaceId, name);
		return listImportNamespaces();
	}

	public List<WikiNamespaceCustomMapper.ImportNamespaceRecord> removeImportNamespace(Integer sourceNamespaceId) {
		WikiNamespaceRole role = WikiNamespaceRole.ofMediaWikiNamespaceId(sourceNamespaceId);
		if (role != null)
			throw new IllegalArgumentException("MediaWiki namespace " + sourceNamespaceId + " is the " + role.name()
					+ " namespace and cannot be unmapped; without it an import would file those pages under "
					+ "'NS" + sourceNamespaceId + "'. Rename the target namespace instead.");
		wikiNamespaceCustomMapper.deleteImportNamespace(sourceNamespaceId);
		return listImportNamespaces();
	}
}
