package com.zfgc.zfgbb.dao.cms;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.WikiNamespaceAliasDbo;
import com.zfgc.zfgbb.dbo.WikiNamespaceAliasDboExample;
import com.zfgc.zfgbb.mappers.WikiNamespaceAliasDboMapper;

@Repository
public class WikiNamespaceAliasDao extends KeyedDao<WikiNamespaceAliasDbo, WikiNamespaceAliasDboExample, String> {

	public WikiNamespaceAliasDao(WikiNamespaceAliasDboMapper mapper) {
		super(mapper);
	}

	public void insertAlias(String alias, String namespaceName) {
		WikiNamespaceAliasDbo row = new WikiNamespaceAliasDbo();
		row.setAlias(alias);
		row.setNamespaceName(namespaceName);
		insert(row);
	}

	public long countAliasesByNamespace(String namespaceName) {
		WikiNamespaceAliasDboExample byNamespace = new WikiNamespaceAliasDboExample();
		byNamespace.createCriteria().andNamespaceNameEqualTo(namespaceName);
		return count(byNamespace);
	}
}
