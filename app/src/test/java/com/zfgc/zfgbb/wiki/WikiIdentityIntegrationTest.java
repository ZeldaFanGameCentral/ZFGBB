package com.zfgc.zfgbb.wiki;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

import org.springframework.beans.factory.annotation.Autowired;

import com.zfgc.zfgbb.dbo.ContentTemplateDboExample;
import com.zfgc.zfgbb.mappers.ContentTemplateDboMapper;
import com.zfgc.zfgbb.dao.cms.WikiNamespaceAliasDao;
import com.zfgc.zfgbb.dao.cms.WikiNamespaceDao;
import com.zfgc.zfgbb.testsupport.mappers.TestSystemInfoMapper;

class WikiIdentityIntegrationTest extends PostgresIntegrationTest {

	@Autowired
	private ContentTemplateDboMapper contentTemplateDboMapper;

	@Autowired
	private WikiNamespaceDao wikiNamespaceDao;

	@Autowired
	private WikiNamespaceAliasDao wikiNamespaceAliasDao;

	@Autowired
	private TestSystemInfoMapper testSystemInfoMapper;

	@Test
	void templateCaseModeTransitionRecanonicalizesFromPreservedLiteralCode() {
		wikiNamespaceDao.updateCaseMode("Template", "CASE_SENSITIVE");
		try {
			ContentTemplateDboExample ex1 = new ContentTemplateDboExample();
			ex1.createCriteria().andWikiPageIdIsNull().andCodeEqualTo("featuredproject");
			assertEquals(1, contentTemplateDboMapper.countByExample(ex1));

			ContentTemplateDboExample ex2 = new ContentTemplateDboExample();
			ex2.createCriteria().andWikiPageIdIsNull().andCodeEqualTo("Featuredproject");
			assertEquals(0, contentTemplateDboMapper.countByExample(ex2));
		} finally {
			wikiNamespaceDao.updateCaseMode("Template", "FIRST_LETTER");
		}
	}

	@Test
	void flywayIdentitySchemaSupportsManyAliasesAndCaseSensitiveTitlePairs() {
		String namespace = "CaseTest" + suffix;
		String aliasOne = "AliasOne" + suffix;
		String aliasTwo = "AliasTwo" + suffix;
		wikiNamespaceDao.insertNamespace(namespace, "CASE_SENSITIVE");
		wikiNamespaceAliasDao.insertAlias(aliasOne, namespace);
		wikiNamespaceAliasDao.insertAlias(aliasTwo, namespace);
		try {
			assertEquals(2, wikiNamespaceAliasDao.countAliasesByNamespace(namespace));
			String lower = testSystemInfoMapper.wikiTitleKey(namespace, "Onlinegame", "CASE_SENSITIVE");
			String upper = testSystemInfoMapper.wikiTitleKey(namespace, "OnlineGame", "CASE_SENSITIVE");
			assertNotEquals(lower, upper);
		} finally {
			wikiNamespaceDao.deleteNamespaceByName(namespace);
		}
	}
}
