package com.zfgc.zfgbb.wiki;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.springframework.beans.factory.annotation.Autowired;
import com.zfgc.zfgbb.dbo.BrUserPermissionDbo;
import com.zfgc.zfgbb.dbo.ContentTemplateDbo;
import com.zfgc.zfgbb.dbo.ContentTemplateDboExample;
import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDbo;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDboExample;
import com.zfgc.zfgbb.mappers.BrUserPermissionDboMapper;
import com.zfgc.zfgbb.mappers.ContentTemplateDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageRevisionDboMapper;
import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

import tools.jackson.databind.JsonNode;

class WikiModeratorTest extends PostgresIntegrationTest {

	@Autowired
	private ContentTemplateDboMapper contentTemplateDboMapper;

	@Autowired
	private WikiPageDboMapper wikiPageDboMapper;

	@Autowired
	private WikiPageRevisionDboMapper wikiPageRevisionDboMapper;

	@Autowired
	private BrUserPermissionDboMapper brUserPermissionDboMapper;

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

		ContentTemplateDboExample ex = new ContentTemplateDboExample();
		ex.createCriteria().andCodeEqualTo("Buzzfeed");
		ContentTemplateDbo row = contentTemplateDboMapper.selectByExample(ex).get(0);
		assertEquals("/board/recent-activity?boardId={boardId}&limit={limit}", row.getSource());
		assertEquals("[b]Fresh threads[/b]\n{{#data}}{{threadName}}\n{{/data}}", row.getBody());

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

		ContentTemplateDboExample ex1 = new ContentTemplateDboExample();
		ex1.createCriteria().andCodeEqualTo("Fadingfeed").andSourceEqualTo("/wiki/meta/statistics");
		assertEquals(1, contentTemplateDboMapper.countByExample(ex1));

		int secondRevisionId = submitRevision("Template:Fadingfeed", "[source=]\nstatic body only");
		approveRevision(secondRevisionId).andExpect(status().isNoContent());

		ContentTemplateDboExample ex2 = new ContentTemplateDboExample();
		ex2.createCriteria().andCodeEqualTo("Fadingfeed");
		ContentTemplateDbo row = contentTemplateDboMapper.selectByExample(ex2).get(0);
		assertNull(row.getSource());
		assertEquals("static body only", row.getBody());
	}

	@Test
	void bodyOnlyEditLeavesTheSourceUnchanged() throws Exception {
		int firstRevisionId = submitRevision("Template:Steadyfeed",
				"[source=/wiki/meta/statistics]\n{{pageCount}} pages");
		approveRevision(firstRevisionId).andExpect(status().isNoContent());

		int secondRevisionId = submitRevision("Template:Steadyfeed", "reworded body without a directive");
		approveRevision(secondRevisionId).andExpect(status().isNoContent());

		ContentTemplateDboExample ex = new ContentTemplateDboExample();
		ex.createCriteria().andCodeEqualTo("Steadyfeed");
		ContentTemplateDbo row = contentTemplateDboMapper.selectByExample(ex).get(0);
		assertEquals("/wiki/meta/statistics", row.getSource());
		assertEquals("reworded body without a directive", row.getBody());
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
		WikiPageDbo page = new WikiPageDbo();
		page.setNamespace("Template");
		page.setTitle("Sneakyfeed");
		page.setSlug("Template:Sneakyfeed");
		wikiPageDboMapper.insertSelective(page);
		int pageId = page.getWikiPageId();

		WikiPageRevisionDbo rev = new WikiPageRevisionDbo();
		rev.setWikiPageId(pageId);
		rev.setContent("[source=/nope/nothing]\nbody");
		rev.setStatus("PENDING");
		rev.setAuthorName("smuggler");
		wikiPageRevisionDboMapper.insertSelective(rev);
		int revisionId = rev.getWikiPageRevisionId();

		approveRevision(revisionId).andExpect(status().isBadRequest());

		ContentTemplateDboExample exCode = new ContentTemplateDboExample();
		exCode.createCriteria().andCodeEqualTo("Sneakyfeed");
		assertEquals(0, contentTemplateDboMapper.countByExample(exCode));

		WikiPageRevisionDboExample exRev = new WikiPageRevisionDboExample();
		exRev.createCriteria().andWikiPageRevisionIdEqualTo(revisionId).andStatusEqualTo("PENDING");
		assertEquals(1, wikiPageRevisionDboMapper.countByExample(exRev));
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

		BrUserPermissionDbo perm = new BrUserPermissionDbo();
		perm.setUserId(memberId);
		perm.setUserPermissionId(9);
		brUserPermissionDboMapper.insert(perm);

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

	@Test
	void namespaceEditPolicyIsDataDrivenPerNamespaceAndActorTier() throws Exception {
		String memberName = "wikins_" + suffix;
		register(memberName, "password123");
		String memberToken = login(memberName, "password123").get("accessToken").asString();

		for (String systemManaged : List.of("Project", "Resource", "Special")) {
			submitAs(accessToken, systemManaged + ":Probe" + suffix)
					.andExpect(status().isForbidden());
		}
		submitAs(accessToken, "MediaWiki:Probe" + suffix).andExpect(status().isOk());
		submitAs(memberToken, "MediaWiki:Probe2" + suffix).andExpect(status().isForbidden());
		for (String open : List.of("MAIN", "Help", "User")) {
			String slug = "MAIN".equals(open) ? "Openprobe" + suffix : open + ":Openprobe" + suffix;
			submitAs(memberToken, slug).andExpect(status().isOk());
		}
		submitAs(memberToken, "Site:Gatedprobe" + suffix).andExpect(status().isForbidden());
		submitAs(accessToken, "Site:Gatedprobe" + suffix).andExpect(status().isOk());
		submitAs(accessToken, "MediaWiki_talk:Probe" + suffix).andExpect(status().isForbidden());
	}

	@Test
	void everyNamespaceEditRejectionSaysWhichRuleRejectedIt() throws Exception {
		String memberName = "wikiwhy_" + suffix;
		register(memberName, "password123");
		String memberToken = login(memberName, "password123").get("accessToken").asString();

		assertEquals("Namespace 'Project' is system managed and cannot be edited through the wiki",
				rejectionReason(accessToken, "Project:Whyprobe" + suffix));
		assertEquals("Namespace 'Site' requires the ZFGC_WIKI_MODERATOR permission",
				rejectionReason(memberToken, "Site:Whyprobe" + suffix));
		assertEquals("Namespace 'MediaWiki' requires the ZFGC_SITE_ADMIN permission",
				rejectionReason(memberToken, "MediaWiki:Whyprobe" + suffix));
		assertEquals("You do not have permission to edit the 'MediaWiki_talk' namespace",
				rejectionReason(accessToken, "MediaWiki_talk:Whyprobe" + suffix));
	}

	private String rejectionReason(String token, String slug) throws Exception {
		return submitAs(token, slug)
				.andExpect(status().isForbidden())
				.andReturn()
				.getResponse()
				.getErrorMessage();
	}

	@Test
	void namespaceOnlySlugCannotSquatASystemManagedNamespace() throws Exception {
		for (String namespace : List.of("Project", "Resource", "Special", "MediaWiki", "Template", "Help")) {
			submitAs(accessToken, namespace + ":").andExpect(status().isBadRequest());
			WikiPageDboExample squat = new WikiPageDboExample();
			squat.createCriteria().andNamespaceEqualTo(namespace).andTitleEqualTo("");
			assertEquals(0, wikiPageDboMapper.countByExample(squat),
					"a namespace-only slug must never create a page in " + namespace);
		}
	}

	@Test
	void editableFlagMatchesWhatSubmitActuallyAllows() throws Exception {
		String entitySlug = "Project:probe" + suffix;
		insertPage("Project", "Probe" + suffix, entitySlug);
		assertFalse(editableFlag(entitySlug), "system managed namespaces are never editable");

		String openSlug = "Help:Probe" + suffix;
		insertPage("Help", "Probe" + suffix, openSlug);
		assertTrue(editableFlag(openSlug), "ordinary namespaces stay editable for an admin");

		String templateSlug = "Template:Stub" + suffix;
		insertPage("Template", "Stub", templateSlug);
		assertTrue(editableFlag(templateSlug),
				"provenance is the rule: an engine-seeded template stays editable by a moderator");
		submitAs(accessToken, templateSlug).andExpect(status().isOk());

		String memberName = "wikitpl_" + suffix;
		register(memberName, "password123");
		String memberToken = login(memberName, "password123").get("accessToken").asString();
		submitAs(memberToken, "Template:Stub2" + suffix).andExpect(status().isForbidden());
		assertFalse(editableFlagAs(memberToken, templateSlug),
				"a gated namespace must not advertise an edit affordance it will reject");
		assertTrue(editableFlagAs(memberToken, openSlug),
				"the flag tracks the viewer, not the namespace alone");
	}

	private void insertPage(String namespace, String title, String slug) {
		WikiPageDbo page = new WikiPageDbo();
		page.setNamespace(namespace);
		page.setTitle(title);
		page.setSlug(slug);
		wikiPageDboMapper.insertSelective(page);
	}

	private boolean editableFlag(String slug) throws Exception {
		return editableFlagAs(accessToken, slug);
	}

	private boolean editableFlagAs(String token, String slug) throws Exception {
		MvcResult result = mockMvc.perform(get("/wiki/" + slug)
				.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andReturn();
		return json.readTree(result.getResponse().getContentAsString()).get("editable").asBoolean();
	}

	private ResultActions submitAs(String token, String slug) throws Exception {
		return mockMvc.perform(post("/wiki/meta/revisions")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(revisionBody(slug, "namespace policy probe")));
	}

	@Test
	void aPageAuthoredAsMarkdownThroughTheApiRendersThroughTheMarkdownLane() throws Exception {
		String slug = "Markdown_authored_" + suffix;
		approveRevision(submitRevision(slug, "# authored heading\n\nwith **markdown strong**", "MARKDOWN"))
				.andExpect(status().isNoContent());

		assertEquals("MARKDOWN", currentRevisionContentFormat(slug),
				"the submitted format must reach the stored revision");
		String contentParsed = fetchedPage(slug).get("contentParsed").asString();
		assertEquals("MARKDOWN", fetchedPage(slug).get("contentFormat").asString(),
				"the page must advertise the format it was authored in");
		assertTrue(contentParsed.contains("<h1 id=\"authored-heading\">authored heading</h1>"),
				"an ATX heading only becomes a heading through the markdown lane: " + contentParsed);
		assertTrue(contentParsed.contains("<strong>markdown strong</strong>"),
				"markdown emphasis must be rendered, not left literal: " + contentParsed);
	}

	@Test
	void anEditNamingNoFormatKeepsThePageInTheFormatItWasAlreadyAuthoredIn() throws Exception {
		String bbcodeSlug = "Formatinherit_bb_" + suffix;
		approveRevision(submitRevision(bbcodeSlug, "[b]bbcode body[/b] with **literal asterisks**"))
				.andExpect(status().isNoContent());
		String markdownSlug = "Formatinherit_md_" + suffix;
		approveRevision(submitRevision(markdownSlug, "**markdown body**", "MARKDOWN"))
				.andExpect(status().isNoContent());

		setAuthoringDefault("MARKDOWN");
		try {
			approveRevision(submitRevision(bbcodeSlug, "[b]still bbcode[/b] with **literal asterisks**"))
					.andExpect(status().isNoContent());
			assertEquals("BBCODE", currentRevisionContentFormat(bbcodeSlug),
					"a bbcode page edited while the site default is markdown must stay bbcode, or flipping "
							+ "the setting silently re-renders every page the next time somebody fixes a typo");
			assertFalse(fetchedPage(bbcodeSlug).get("contentParsed").asString().contains("<strong>"),
					"the bbcode page must keep rendering through the bbcode lane");

			approveRevision(submitRevision(markdownSlug, "**still markdown**"))
					.andExpect(status().isNoContent());
			assertEquals("MARKDOWN", currentRevisionContentFormat(markdownSlug),
					"inheritance must follow the page, not the site default");

			String freshSlug = "Formatdefault_" + suffix;
			approveRevision(submitRevision(freshSlug, "**a brand new page**"))
					.andExpect(status().isNoContent());
			assertEquals("MARKDOWN", currentRevisionContentFormat(freshSlug),
					"a page with no predecessor takes the site authoring default");
		} finally {
			setAuthoringDefault("BBCODE");
		}
	}

	@Test
	void aRejectedRevisionDoesNotHandItsFormatToTheNextEditOfThePage() throws Exception {
		String slug = "Formatinherit_rejected_" + suffix;
		approveRevision(submitRevision(slug, "[b]the live body[/b] with **literal asterisks**"))
				.andExpect(status().isNoContent());
		rejectRevision(submitRevision(slug, "**a markdown rewrite nobody accepted**", "MARKDOWN"))
				.andExpect(status().isNoContent());

		approveRevision(submitRevision(slug, "[b]an ordinary edit[/b] with **literal asterisks**"))
				.andExpect(status().isNoContent());

		assertEquals("BBCODE", currentRevisionContentFormat(slug),
				"an edit inherits the format of the revision it supersedes, which is the live one; "
						+ "inheriting from a rejected submission would silently convert the page on behalf "
						+ "of an author whose work was thrown out");
		assertFalse(fetchedPage(slug).get("contentParsed").asString().contains("<strong>"),
				"the page must keep rendering through the bbcode lane");
	}

	@Test
	void anApprovedMarkdownTemplateIsPublishedUnderTheFormatItWasAuthoredIn() throws Exception {
		approveRevision(submitRevision("Template:Mdbox" + suffix, "**{{name}}** in markdown", "MARKDOWN"))
				.andExpect(status().isNoContent());

		ContentTemplateDboExample published = new ContentTemplateDboExample();
		published.createCriteria().andCodeEqualTo("Mdbox" + suffix);
		ContentTemplateDbo row = contentTemplateDboMapper.selectByExample(published).get(0);
		assertEquals("MARKDOWN", row.getContentFormat(),
				"a template body written in markdown must be published as the markdown variant; filing it "
						+ "as bbcode makes every bbcode page render markdown source as literal text");
		assertEquals("**{{name}}** in markdown", row.getBody());
	}

	@Test
	void aBBCodePageStillReceivesTheBodyOfATemplateThatOnlyExistsInMarkdown() throws Exception {
		String code = "Mdonly" + suffix;
		approveRevision(submitRevision("Template:" + code, "the body of a markdown only template", "MARKDOWN"))
				.andExpect(status().isNoContent());

		int consumingRevisionId = submitRevision("Mdonly_consumer_" + suffix,
				"[b]a bbcode page[/b] [template=" + code + "][/template]");
		MvcResult preview = mockMvc.perform(get("/wiki/meta/moderation/" + consumingRevisionId + "/preview")
				.header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isOk())
				.andReturn();

		String contentParsed = json.readTree(preview.getResponse().getContentAsString())
				.get("contentParsed").asString();
		assertTrue(contentParsed.contains("the body of a markdown only template"),
				"a template authored in the other format must still be expanded; expanding it to nothing "
						+ "deletes the content the page referenced without telling anybody: " + contentParsed);
	}

	@Test
	void convertingAWikiPageIsCheckedThroughTheTemplatesTheWikiLaneWouldExpand() throws Exception {
		String pageThatIsBuiltOutOfATemplate = "[template=UserProfile]\\n[/template]";

		JsonNode inTheWiki = converted(pageThatIsBuiltOutOfATemplate, "WIKI");
		JsonNode outsideTheWiki = converted(pageThatIsBuiltOutOfATemplate, "FORUM");

		assertEquals(pageThatIsBuiltOutOfATemplate.replace("\\n", "\n"),
				inTheWiki.get("content").asString(),
				"there is nothing in this page for the converter to rewrite, which is the point: what changes "
						+ "when the format flips is inside the template's expansion");
		assertEquals(1, inTheWiki.get("notes").size(),
				"the check has to render the page the way the wiki renders it -- through template expansion -- "
						+ "or it is blind exactly where wiki content is most structured, and the author is told "
						+ "nothing about a page that will not survive the flip: " + inTheWiki.get("notes"));
		assertEquals(0, outsideTheWiki.get("notes").size(),
				"this template is scoped to the wiki, so outside it nothing expands and there is nothing to "
						+ "warn about; a check that ignored the scope would warn about a page that is fine, and "
						+ "notes nobody can act on are notes nobody reads: " + outsideTheWiki.get("notes"));
		assertEquals(0, convertedWithNoScope(pageThatIsBuiltOutOfATemplate).get("notes").size(),
				"a request that names no scope is a forum request, the same reading /content/preview has had "
						+ "all along; the two endpoints answer about the same render or the editor's warning "
						+ "describes a page nobody is looking at");
	}

	@Test
	void aWikiPageWhoseTemplateSurvivesTheFlipIsNotWarnedAbout() throws Exception {
		JsonNode inTheWiki = converted("[template=Stub][/template]", "WIKI");

		assertEquals(0, inTheWiki.get("notes").size(),
				"this template is wiki-scoped like the one above and does expand here, and its expansion reads "
						+ "the same in both lanes. Both sides of the check have to be rendered the same way for "
						+ "that to come out clean: rendering the page one way and its conversion another turns "
						+ "every templated page into a warning: " + inTheWiki.get("notes"));
	}

	@Test
	void aPreviewRendersUnderTheSurfaceItNamesRatherThanFallingBackToTheWiki() throws Exception {
		String wikiScopedTemplate = "[template=Stub][/template]";

		String inTheWiki = previewed(wikiScopedTemplate, "WIKI");
		String inAProject = previewed(wikiScopedTemplate, "PROJECT");

		assertNotEquals(inTheWiki, inAProject,
				"Stub is wiki-scoped, so it expands on the wiki and nowhere else; a preview that named PROJECT "
						+ "and still rendered the wiki's expansion would be showing the author content the saved "
						+ "project page will not have, because ProjectService renders under PROJECT");
	}

	private String previewed(String content, String scope) throws Exception {
		MvcResult result = mockMvc.perform(post("/content/preview")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"content\": \"" + content + "\", \"scope\": \"" + scope + "\"}"))
				.andExpect(status().isOk())
				.andReturn();
		return json.readTree(result.getResponse().getContentAsString()).get("contentParsed").asString();
	}

	private JsonNode convertedWithNoScope(String content) throws Exception {
		MvcResult result = mockMvc.perform(post("/content/convert")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"content\": \"" + content
						+ "\", \"fromContentFormat\": \"BBCODE\", \"toContentFormat\": \"MARKDOWN\"}"))
				.andExpect(status().isOk())
				.andReturn();
		return json.readTree(result.getResponse().getContentAsString());
	}

	private JsonNode converted(String content, String scope) throws Exception {
		MvcResult result = mockMvc.perform(post("/content/convert")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"content\": \"" + content + "\", \"scope\": \"" + scope
						+ "\", \"fromContentFormat\": \"BBCODE\", \"toContentFormat\": \"MARKDOWN\"}"))
				.andExpect(status().isOk())
				.andReturn();
		return json.readTree(result.getResponse().getContentAsString());
	}

	@Test
	void aRevisionNamingAnUnrenderableFormatIsRefusedAndRollsBackThePageItHadAlreadyCreated()
			throws Exception {
		String slug = "Formatrefused_" + suffix;

		mockMvc.perform(post("/wiki/meta/revisions")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(revisionBody(slug, "<p>raw html</p>", "HTML")))
				.andExpect(status().isBadRequest());

		WikiPageDboExample created = new WikiPageDboExample();
		created.createCriteria().andSlugEqualTo(slug);
		assertEquals(0, wikiPageDboMapper.countByExample(created),
				"the submission creates the page row before it resolves the format, so the refusal has to "
						+ "roll the whole submission back rather than leave an empty page behind");
	}

	private void setAuthoringDefault(String contentFormat) throws Exception {
		mockMvc.perform(put("/admin/site/authoring")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"defaultContentFormat\": \"" + contentFormat + "\"}"))
				.andExpect(status().isOk());
	}

	private String currentRevisionContentFormat(String slug) {
		WikiPageDboExample pageExample = new WikiPageDboExample();
		pageExample.createCriteria().andSlugEqualTo(slug);
		Integer wikiPageId = wikiPageDboMapper.selectByExample(pageExample).get(0).getWikiPageId();
		WikiPageRevisionDboExample revisionExample = new WikiPageRevisionDboExample();
		revisionExample.createCriteria().andWikiPageIdEqualTo(wikiPageId).andCurrentFlagEqualTo(true);
		return wikiPageRevisionDboMapper.selectByExample(revisionExample).get(0).getContentFormat();
	}

	private JsonNode fetchedPage(String slug) throws Exception {
		MvcResult result = mockMvc.perform(get("/wiki/" + slug))
				.andExpect(status().isOk())
				.andReturn();
		return json.readTree(result.getResponse().getContentAsString());
	}

	private int submitRevision(String slug, String content) throws Exception {
		return submitRevision(slug, content, null);
	}

	private int submitRevision(String slug, String content, String contentFormat) throws Exception {
		MvcResult result = mockMvc.perform(post("/wiki/meta/revisions")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(revisionBody(slug, content, contentFormat)))
				.andExpect(status().isOk())
				.andReturn();
		return json.readTree(result.getResponse().getContentAsString()).get("revisionId").asInt();
	}

	private String revisionBody(String slug, String content) {
		return revisionBody(slug, content, null);
	}

	private String revisionBody(String slug, String content, String contentFormat) {
		Map<String, String> body = new LinkedHashMap<>(
				Map.of("slug", slug, "content", content, "summary", "integration test"));
		if (contentFormat != null)
			body.put("contentFormat", contentFormat);
		return json.writeValueAsString(body);
	}

	private ResultActions approveRevision(int revisionId) throws Exception {
		return mockMvc.perform(post("/wiki/meta/moderation/" + revisionId + "/approve")
				.header("Authorization", "Bearer " + accessToken));
	}

	private ResultActions rejectRevision(int revisionId) throws Exception {
		return mockMvc.perform(post("/wiki/meta/moderation/" + revisionId + "/reject")
				.header("Authorization", "Bearer " + accessToken));
	}
}
