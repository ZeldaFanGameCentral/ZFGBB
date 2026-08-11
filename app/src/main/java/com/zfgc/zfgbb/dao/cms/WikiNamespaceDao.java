package com.zfgc.zfgbb.dao.cms;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.WikiNamespaceDbo;
import com.zfgc.zfgbb.dbo.WikiNamespaceDboExample;
import com.zfgc.zfgbb.mappers.WikiNamespaceDboMapper;
import com.zfgc.zfgbb.mappers.custom.WikiNamespaceCustomMapper;
import com.zfgc.zfgbb.mappers.custom.WikiNamespaceCustomMapper.EditPolicyRecord;
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

	public long countDistinctCategories() {
		return wikiNamespaceCustomMapper.countDistinctCategories();
	}

	public int updateCaseMode(String name, String caseMode) {
		WikiNamespaceDbo row = new WikiNamespaceDbo();
		row.setCaseMode(caseMode);
		WikiNamespaceDboExample named = new WikiNamespaceDboExample();
		named.createCriteria().andNameEqualTo(name);
		return updateWhere(row, named);
	}

	public void insertNamespace(String name, String caseMode) {
		WikiNamespaceDbo row = new WikiNamespaceDbo();
		row.setName(name);
		row.setCaseMode(caseMode);
		insertSelective(row);
	}

	public int deleteNamespaceByName(String name) {
		WikiNamespaceDboExample named = new WikiNamespaceDboExample();
		named.createCriteria().andNameEqualTo(name);
		return deleteWhere(named);
	}
}
