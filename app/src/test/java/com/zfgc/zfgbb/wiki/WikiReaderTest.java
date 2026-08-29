package com.zfgc.zfgbb.wiki;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDbo;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDboExample;
import com.zfgc.zfgbb.mappers.WikiPageDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageRevisionDboMapper;
import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

class WikiReaderTest extends PostgresIntegrationTest {

	private static final String MARKDOWN_SOURCE = """
			# Markdown lane heading

			Inline **markdown strong** sitting next to [b]inline bbcode[/b].

			[align=center]a block level bbcode tag lifted out of its paragraph[/align]

			[quote]
			A quote body whose **markdown is parsed**.
			[/quote]
			""";

	@Autowired
	private WikiPageDboMapper wikiPageDboMapper;

	@Autowired
	private WikiPageRevisionDboMapper wikiPageRevisionDboMapper;

	@Test
	void markdownFormattedPageRendersThroughTheSpringWiredRendererInsteadOfFailing() throws Exception {
		String slug = "Markdown_Lane_" + suffix;
		Integer wikiPageId = publishMarkdownPage(slug);
		try {
			MvcResult response = mockMvc.perform(get("/wiki/" + slug))
					.andExpect(status().isOk())
					.andReturn();
			String body = response.getResponse().getContentAsString();
			assertEquals("MARKDOWN", json.readTree(body).get("contentFormat").asString(),
					"the fixture must reach the renderer as markdown, otherwise this test silently exercises the "
							+ "bbcode lane and stops covering the markdown wiring at all: " + body);
			String contentParsed = json.readTree(body).get("contentParsed").asString();

			assertTrue(contentParsed.contains("<h1 id=\"markdown-lane-heading\">Markdown lane heading</h1>"),
					"the markdown lane must turn an ATX heading into a decorated h1: " + contentParsed);
			assertTrue(contentParsed.contains("<strong>markdown strong</strong>"),
					"the markdown lane must render markdown emphasis: " + contentParsed);
			assertTrue(contentParsed.contains("<span class=\"bb-code-b\">inline bbcode</span>"),
					"inline bbcode inside markdown text is expanded through the grammar and the ast parser the "
							+ "bbcode bean owns, so it fails the moment those are reached by field read on a "
							+ "transaction proxy rather than by method call: " + contentParsed);
			assertTrue(contentParsed.contains(
					"<div class=\"bb-code-align bb-align-center\">a block level bbcode tag lifted out of its "
							+ "paragraph</div>"),
					"a block level bbcode tag inside a markdown paragraph is re-emitted by the bbcode node renderer "
							+ "the bbcode bean owns: " + contentParsed);
			assertTrue(contentParsed.contains("<div class=\"bb-code-quote\">"),
					"a bbcode block opened on its own line is recognised by the commonmark block factory, which "
							+ "reads the grammar off the bbcode bean: " + contentParsed);
			assertTrue(contentParsed.contains("<strong>markdown is parsed</strong>"),
					"markdown inside a bbcode block body must still be parsed as markdown: " + contentParsed);
		} finally {
			WikiPageRevisionDboExample revisionExample = new WikiPageRevisionDboExample();
			revisionExample.createCriteria().andWikiPageIdEqualTo(wikiPageId);
			wikiPageRevisionDboMapper.deleteByExample(revisionExample);
			WikiPageDboExample pageExample = new WikiPageDboExample();
			pageExample.createCriteria().andWikiPageIdEqualTo(wikiPageId);
			wikiPageDboMapper.deleteByExample(pageExample);
		}
	}

	private Integer publishMarkdownPage(String slug) {
		WikiPageDbo page = new WikiPageDbo();
		page.setNamespace("MAIN");
		page.setTitle(slug.replace('_', ' '));
		page.setSlug(slug);
		wikiPageDboMapper.insertSelective(page);

		WikiPageRevisionDbo revision = new WikiPageRevisionDbo();
		revision.setWikiPageId(page.getWikiPageId());
		revision.setContent(MARKDOWN_SOURCE);
		revision.setContentFormat("MARKDOWN");
		revision.setCurrentFlag(true);
		revision.setStatus("APPROVED");
		wikiPageRevisionDboMapper.insertSelective(revision);
		return page.getWikiPageId();
	}
}
