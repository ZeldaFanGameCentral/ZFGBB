package com.zfgc.zfgbb.services.cms.wiki;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.dataprovider.cms.WikiNamespaceDataProvider;
import com.zfgc.zfgbb.dao.cms.WikiNamespaceDao;
import com.zfgc.zfgbb.mappers.custom.WikiNamespaceCustomMapper.ImportNamespaceRecord;
import com.zfgc.zfgbb.wiki.WikiNamespaceRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WikiNamespaceRegistry {

	private final WikiNamespaceDao wikiNamespaceDao;

	private final WikiNamespaceDataProvider namespaceData;

	public List<ImportNamespaceRecord> listImportNamespaces() {
		List<ImportNamespaceRecord> configured =
				wikiNamespaceDao.listImportNamespaces();
		for (ImportNamespaceRecord row : configured) {
			String holder = namespaceData.nameForRole(WikiNamespaceRole.ofMediaWikiNamespaceId(row.getSourceNamespaceId()));
			if (holder != null)
				row.setNamespaceName(holder);
		}
		return configured;
	}

	public List<ImportNamespaceRecord> saveImportNamespace(Integer sourceNamespaceId,
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
				throw new IllegalArgumentException("MediaWiki namespace " + sourceNamespaceId
						+ " is the " + role.name() + " namespace, already named '" + roleHolder + "'");
			WikiNamespaceRole heldByName = namespaceData.roleOf(name);
			if (heldByName != null && heldByName != role)
				throw new IllegalArgumentException("Namespace '" + name + "' already serves as this wiki's "
						+ heldByName.name() + " namespace, so it cannot also be MediaWiki namespace "
						+ sourceNamespaceId + " (" + role.name() + ").");
			if (roleHolder == null && namespaceData.assignEngineRole(name, role) == 0)
				throw new IllegalArgumentException("namespace '" + name
						+ "' does not exist, cannot bind MediaWiki namespace " + sourceNamespaceId
						+ " (" + role.name() + ")");
		}
		wikiNamespaceDao.upsertImportNamespace(sourceNamespaceId, name);
		return listImportNamespaces();
	}

	public List<ImportNamespaceRecord> removeImportNamespace(Integer sourceNamespaceId) {
		WikiNamespaceRole role = WikiNamespaceRole.ofMediaWikiNamespaceId(sourceNamespaceId);
		if (role != null)
			throw new IllegalArgumentException("MediaWiki namespace " + sourceNamespaceId + " is the "
					+ role.name() + " namespace and cannot be unmapped");
		wikiNamespaceDao.deleteImportNamespace(sourceNamespaceId);
		return listImportNamespaces();
	}
}
