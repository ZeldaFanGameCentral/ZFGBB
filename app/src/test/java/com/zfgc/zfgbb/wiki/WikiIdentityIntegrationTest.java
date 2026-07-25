package com.zfgc.zfgbb.wiki;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

import org.springframework.beans.factory.annotation.Autowired;

import com.zfgc.zfgbb.dbo.ContentTemplateDboExample;
import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import com.zfgc.zfgbb.mappers.ContentTemplateDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageDboMapper;
import com.zfgc.zfgbb.mappers.custom.WikiNamespaceCustomMapper;
import com.zfgc.zfgbb.testsupport.mappers.TestSystemInfoMapper;

class WikiIdentityIntegrationTest extends PostgresIntegrationTest {

	@Autowired
	private ContentTemplateDboMapper contentTemplateDboMapper;

	@Autowired
	private WikiPageDboMapper wikiPageDboMapper;

	@Autowired
	private WikiNamespaceCustomMapper wikiNamespaceCustomMapper;

	@Autowired
	private TestSystemInfoMapper testSystemInfoMapper;

	@Test
	void templateCaseModeTransitionRecanonicalizesFromPreservedLiteralCode() {
		wikiNamespaceCustomMapper.updateCaseMode("Template", "CASE_SENSITIVE");
		try {
			ContentTemplateDboExample ex1 = new ContentTemplateDboExample();
			ex1.createCriteria().andWikiPageIdIsNull().andCodeEqualTo("featuredproject");
			assertEquals(1, contentTemplateDboMapper.countByExample(ex1));

			ContentTemplateDboExample ex2 = new ContentTemplateDboExample();
			ex2.createCriteria().andWikiPageIdIsNull().andCodeEqualTo("Featuredproject");
			assertEquals(0, contentTemplateDboMapper.countByExample(ex2));
		} finally {
			wikiNamespaceCustomMapper.updateCaseMode("Template", "FIRST_LETTER");
		}
	}

	@Test
	void flywayIdentitySchemaSupportsManyAliasesAndCaseSensitiveTitlePairs() {
		String namespace = "CaseTest" + suffix;
		String aliasOne = "AliasOne" + suffix;
		String aliasTwo = "AliasTwo" + suffix;
		wikiNamespaceCustomMapper.insertNamespace(namespace, "CASE_SENSITIVE");
		wikiNamespaceCustomMapper.insertAlias(aliasOne, namespace);
		wikiNamespaceCustomMapper.insertAlias(aliasTwo, namespace);
		try {
			assertEquals(2, wikiNamespaceCustomMapper.countAliasesByNamespace(namespace));
			String lower = testSystemInfoMapper.wikiTitleKey(namespace, "Onlinegame", "CASE_SENSITIVE");
			String upper = testSystemInfoMapper.wikiTitleKey(namespace, "OnlineGame", "CASE_SENSITIVE");
			assertNotEquals(lower, upper);

			WikiPageDbo page1 = new WikiPageDbo();
			page1.setNamespace(namespace);
			page1.setTitle("Onlinegame");
			page1.setSlug(namespace + ":Onlinegame");
			wikiPageDboMapper.insertSelective(page1);

			WikiPageDbo page2 = new WikiPageDbo();
			page2.setNamespace(namespace);
			page2.setTitle("OnlineGame");
			page2.setSlug(namespace + ":OnlineGame");
			wikiPageDboMapper.insertSelective(page2);

			WikiPageDboExample ex = new WikiPageDboExample();
			ex.createCriteria().andNamespaceEqualTo(namespace);
			assertEquals(2, wikiPageDboMapper.countByExample(ex));
		} finally {
			WikiPageDboExample pageEx = new WikiPageDboExample();
			pageEx.createCriteria().andNamespaceEqualTo(namespace);
			wikiPageDboMapper.deleteByExample(pageEx);
			wikiNamespaceCustomMapper.deleteNamespaceByName(namespace);
		}
	}
}
