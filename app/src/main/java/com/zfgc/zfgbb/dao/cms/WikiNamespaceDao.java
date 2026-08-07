package com.zfgc.zfgbb.dao.cms;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.WikiNamespaceDbo;
import com.zfgc.zfgbb.dbo.WikiNamespaceDboExample;
import com.zfgc.zfgbb.mappers.WikiNamespaceDboMapper;
import com.zfgc.zfgbb.mappers.custom.WikiNamespaceCustomMapper;
import com.zfgc.zfgbb.mappers.custom.WikiNamespaceCustomMapper.EditPolicyRecord;
import com.zfgc.zfgbb.mappers.custom.WikiNamespaceCustomMapper.ImportNamespaceRecord;
import com.zfgc.zfgbb.mappers.custom.WikiNamespaceCustomMapper.NamespacePageCount;
import com.zfgc.zfgbb.mappers.custom.WikiNamespaceCustomMapper.NamespaceRecord;

@Repository
public class WikiNamespaceDao extends KeyedDao<WikiNamespaceDbo, WikiNamespaceDboExample, String> {

	private final WikiNamespaceCustomMapper wikiNamespaceCustomMapper;

	public WikiNamespaceDao(WikiNamespaceDboMapper mapper,
			WikiNamespaceCustomMapper wikiNamespaceCustomMapper) {
		super(mapper);
		this.wikiNamespaceCustomMapper = wikiNamespaceCustomMapper;
	}

	public List<NamespaceRecord> resolveNamespace(String prefix) {
		return wikiNamespaceCustomMapper.resolveNamespace(prefix);
	}

	public List<String> findMainCaseMode() {
		return wikiNamespaceCustomMapper.findMainCaseMode();
	}

	public List<String> findCaseModeByName(String namespace) {
		return wikiNamespaceCustomMapper.findCaseModeByName(namespace);
	}

	public List<EditPolicyRecord> findEditPolicyByName(String namespace) {
		return wikiNamespaceCustomMapper.findEditPolicyByName(namespace);
	}

	public List<NamespacePageCount> countVisiblePagesByNamespace(List<String> hiddenNamespaces) {
		return wikiNamespaceCustomMapper.countVisiblePagesByNamespace(hiddenNamespaces);
	}

	public int assignEngineRole(String name, String role) {
		return wikiNamespaceCustomMapper.assignEngineRole(name, role);
	}

	public int updateCaseMode(String name, String caseMode) {
		return wikiNamespaceCustomMapper.updateCaseMode(name, caseMode);
	}

	public int insertNamespace(String name, String caseMode) {
		return wikiNamespaceCustomMapper.insertNamespace(name, caseMode);
	}

	public int insertAlias(String alias, String namespaceName) {
		return wikiNamespaceCustomMapper.insertAlias(alias, namespaceName);
	}

	public int countAliasesByNamespace(String namespaceName) {
		return wikiNamespaceCustomMapper.countAliasesByNamespace(namespaceName);
	}

	public int deleteNamespaceByName(String name) {
		return wikiNamespaceCustomMapper.deleteNamespaceByName(name);
	}

	public List<ImportNamespaceRecord> listImportNamespaces() {
		return wikiNamespaceCustomMapper.listImportNamespaces();
	}

	public int upsertImportNamespace(Integer sourceNamespaceId, String namespaceName) {
		return wikiNamespaceCustomMapper.upsertImportNamespace(sourceNamespaceId, namespaceName);
	}

	public int deleteImportNamespace(Integer sourceNamespaceId) {
		return wikiNamespaceCustomMapper.deleteImportNamespace(sourceNamespaceId);
	}
}
