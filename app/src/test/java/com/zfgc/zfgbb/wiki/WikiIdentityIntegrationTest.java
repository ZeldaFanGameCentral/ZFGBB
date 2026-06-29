package com.zfgc.zfgbb.wiki;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

class WikiIdentityIntegrationTest extends PostgresIntegrationTest {
	@Test
	void templateCaseModeTransitionRecanonicalizesFromPreservedLiteralCode() {
		jdbcTemplate.update("update zfgbb.wiki_namespace set case_mode='CASE_SENSITIVE' where name='Template'");
		try {
			assertEquals(1, jdbcTemplate.queryForObject("select count(*) from zfgbb.content_template "
					+ "where wiki_page_id is null and source_code='featuredproject' and code='featuredproject'",
					Integer.class));
			assertEquals(0, jdbcTemplate.queryForObject("select count(*) from zfgbb.content_template "
					+ "where wiki_page_id is null and code='Featuredproject'", Integer.class));
		} finally {
			jdbcTemplate.update("update zfgbb.wiki_namespace set case_mode='FIRST_LETTER' where name='Template'");
		}
	}

	@Test
	void flywayIdentitySchemaSupportsManyAliasesAndCaseSensitiveTitlePairs() {
		String namespace = "CaseTest" + suffix;
		String aliasOne = "AliasOne" + suffix;
		String aliasTwo = "AliasTwo" + suffix;
		jdbcTemplate.update("insert into zfgbb.wiki_namespace(name,case_mode) values (?,'CASE_SENSITIVE')", namespace);
		jdbcTemplate.update("insert into zfgbb.wiki_namespace_alias(alias,namespace_name) values (?,?),(?,?)",
				aliasOne, namespace, aliasTwo, namespace);
		try {
			assertEquals(2, jdbcTemplate.queryForObject(
					"select count(*) from zfgbb.wiki_namespace_alias where namespace_name=?", Integer.class, namespace));
			String lower = jdbcTemplate.queryForObject("select zfgbb.wiki_title_key(?,'Onlinegame','CASE_SENSITIVE')",
					String.class, namespace);
			String upper = jdbcTemplate.queryForObject("select zfgbb.wiki_title_key(?,'OnlineGame','CASE_SENSITIVE')",
					String.class, namespace);
			assertNotEquals(lower, upper);
			jdbcTemplate.update("insert into zfgbb.wiki_page(namespace,title,slug) values (?,?,?),(?,?,?)",
					namespace, "Onlinegame", namespace + ":Onlinegame", namespace, "OnlineGame", namespace + ":OnlineGame");
			assertEquals(2, jdbcTemplate.queryForObject(
					"select count(*) from zfgbb.wiki_page where namespace=?", Integer.class, namespace));
		} finally {
			jdbcTemplate.update("delete from zfgbb.wiki_page where namespace=?", namespace);
			jdbcTemplate.update("delete from zfgbb.wiki_namespace where name=?", namespace);
		}
	}
}
