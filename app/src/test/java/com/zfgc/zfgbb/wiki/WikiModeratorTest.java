package com.zfgc.zfgbb.wiki;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

class WikiModeratorTest extends PostgresIntegrationTest {

	private String accessToken;

	@BeforeEach
	void adminLogin() throws Exception {
		accessToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
	}

	@Test
	void approvedDirectivePublishesSourceAndStrippedBodyAndRenders() throws Exception {
		String templateContent = "[source=/board/recent-activity?boardId={boardId}&limit={limit}]\n"
				+ "[b]Fresh threads[/b]\n{{#data}}{{threadName}}\n{{/data}}";
		int templateRevisionId = submitRevision("Template:Buzzfeed", templateContent);
		approveRevision(templateRevisionId).andExpect(status().isNoContent());

		Map<String, Object> row = jdbcTemplate.queryForMap(
				"select source, body from zfgbb.content_template where code = 'buzzfeed'");
		assertEquals("/board/recent-activity?boardId={boardId}&limit={limit}", row.get("source"));
		assertEquals("[b]Fresh threads[/b]\n{{#data}}{{threadName}}\n{{/data}}", row.get("body"));

		int pageRevisionId = submitRevision("Buzzfeed_demo", "[template=buzzfeed][/template]");
		MvcResult preview = mockMvc.perform(get("/wiki/meta/moderation/" + pageRevisionId + "/preview")
				.header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isOk())
				.andReturn();
		String contentParsed = json.readTree(preview.getResponse().getContentAsString())
				.get("contentParsed").asString();
		assertTrue(contentParsed.contains("Fresh threads"), contentParsed);
	}

	@Test
	void emptyDirectiveClearsThePublishedSource() throws Exception {
		int firstRevisionId = submitRevision("Template:Fadingfeed",
				"[source=/wiki/meta/statistics]\n{{pageCount}} pages");
		approveRevision(firstRevisionId).andExpect(status().isNoContent());
		assertEquals(1, count("zfgbb.content_template where code = 'fadingfeed' and source = '/wiki/meta/statistics'"));

		int secondRevisionId = submitRevision("Template:Fadingfeed", "[source=]\nstatic body only");
		approveRevision(secondRevisionId).andExpect(status().isNoContent());
		Map<String, Object> row = jdbcTemplate.queryForMap(
				"select source, body from zfgbb.content_template where code = 'fadingfeed'");
		assertEquals(null, row.get("source"));
		assertEquals("static body only", row.get("body"));
	}

	@Test
	void bodyOnlyEditLeavesTheSourceUnchanged() throws Exception {
		int firstRevisionId = submitRevision("Template:Steadyfeed",
				"[source=/wiki/meta/statistics]\n{{pageCount}} pages");
		approveRevision(firstRevisionId).andExpect(status().isNoContent());

		int secondRevisionId = submitRevision("Template:Steadyfeed", "reworded body without a directive");
		approveRevision(secondRevisionId).andExpect(status().isNoContent());
		Map<String, Object> row = jdbcTemplate.queryForMap(
				"select source, body from zfgbb.content_template where code = 'steadyfeed'");
		assertEquals("/wiki/meta/statistics", row.get("source"));
		assertEquals("reworded body without a directive", row.get("body"));
	}

	@Test
	void unknownSourcePathIsRejectedAtSubmit() throws Exception {
		mockMvc.perform(post("/wiki/meta/revisions")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(revisionBody("Template:Brokenfeed", "[source=/nope/nothing?x=1]\nbody")))
				.andExpect(status().isBadRequest());
	}

	@Test
	void unknownSourcePathIsRejectedAtApprove() throws Exception {
		Integer pageId = jdbcTemplate.queryForObject(
				"insert into zfgbb.wiki_page (namespace, title, slug) values ('Template', 'Sneakyfeed', 'Template:Sneakyfeed') returning wiki_page_id",
				Integer.class);
		Integer revisionId = jdbcTemplate.queryForObject(
				"insert into zfgbb.wiki_page_revision (wiki_page_id, content, status, author_name) "
						+ "values (?, '[source=/nope/nothing]' || chr(10) || 'body', 'PENDING', 'smuggler') returning wiki_page_revision_id",
				Integer.class, pageId);

		approveRevision(revisionId).andExpect(status().isBadRequest());
		assertEquals(0, count("zfgbb.content_template where code = 'sneakyfeed'"));
		assertEquals(1, count("zfgbb.wiki_page_revision where wiki_page_revision_id = " + revisionId
				+ " and status = 'PENDING'"));
	}

	@Test
	void readOnlyMemberCannotSubmitWikiRevision() throws Exception {
		String memberName = "wikiro_" + suffix;
		register(memberName, "password123");
		String memberToken = login(memberName, "password123").get("accessToken").asString();
		int memberId = userIdOf(memberName);
		String slug = "Readonlyprobe" + suffix;

		mockMvc.perform(post("/wiki/meta/revisions")
				.header("Authorization", "Bearer " + memberToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(revisionBody(slug, "ordinary member draft body")))
				.andExpect(status().isOk());

		jdbcTemplate.update(
				"insert into zfgbb.br_user_permission (user_id, user_permission_id) values (?, 9)", memberId);

		mockMvc.perform(post("/wiki/meta/revisions")
				.header("Authorization", "Bearer " + memberToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(revisionBody(slug, "read-only draft attempt")))
				.andExpect(status().isForbidden());
	}

	@Test
	void submitResponseCarriesConvergedAuthoredTsAndSize() throws Exception {
		String content = "converged drift-guard body";
		MvcResult result = mockMvc.perform(post("/wiki/meta/revisions")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(revisionBody("Convergeprobe" + suffix, content)))
				.andExpect(status().isOk())
				.andReturn();
		var ref = json.readTree(result.getResponse().getContentAsString());
		assertFalse(ref.get("authoredTs").isNull(), "authoredTs must be populated after convergence");
		assertEquals(content.getBytes(StandardCharsets.UTF_8).length, ref.get("size").asInt());
		assertFalse(ref.get("current").asBoolean());
	}

	private int submitRevision(String slug, String content) throws Exception {
		MvcResult result = mockMvc.perform(post("/wiki/meta/revisions")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(revisionBody(slug, content)))
				.andExpect(status().isOk())
				.andReturn();
		return json.readTree(result.getResponse().getContentAsString()).get("revisionId").asInt();
	}

	private String revisionBody(String slug, String content) {
		return json.writeValueAsString(Map.of("slug", slug, "content", content, "summary", "integration test"));
	}

	private ResultActions approveRevision(int revisionId) throws Exception {
		return mockMvc.perform(post("/wiki/meta/moderation/" + revisionId + "/approve")
				.header("Authorization", "Bearer " + accessToken));
	}
}
