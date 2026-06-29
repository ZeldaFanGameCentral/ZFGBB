package com.zfgc.zfgbb.migration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.TemplateExpander;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.cms.Project;
import com.zfgc.zfgbb.model.cms.WikiPage;
import com.zfgc.zfgbb.model.cms.WikiRevisionRef;
import com.zfgc.zfgbb.services.cms.ProjectService;
import com.zfgc.zfgbb.services.cms.WikiModerationService;
import com.zfgc.zfgbb.services.cms.WikiService;

@Order(3)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MediaWikiMigrationTest extends MigrationE2E {

	@Autowired
	private WikiService wikiService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private TemplateExpander templateExpander;

	@Autowired
	private WikiModerationService wikiModerationService;

	@Test
	@Order(1)
	void pagesRevisionsAndCategoriesMigrate() {
		assertEquals(preMigrationWikiPageCount + 34, count("zfgbb.wiki_page"),
				"migration adds the mediawiki source (incl. 2 member profile pages) + 2 File + 1 Special "
						+ "+ 6 project + 4 resource + 5 generated member pages on top of the seeded pages");
		assertEquals(preMigrationWikiRevisionCount + 34, count("zfgbb.wiki_page_revision"),
				"migration adds the migrated current and historical revisions on top of the seeded revisions");
		assertEquals(6, count("zfgbb.wiki_page where namespace = 'Project'"));
		assertEquals(4, count("zfgbb.wiki_page where namespace = 'Resource'"));
		assertSameCount(smf, "smf_1members", "zfgbb.wiki_page where namespace = 'User'");
		assertSameCount(smf, "smf_1members",
				"zfgbb.wiki_page_category c join zfgbb.wiki_page p on p.wiki_page_id = c.wiki_page_id "
						+ "where c.category_name = 'Members'");

		String masterSword = currentRevision("KOT:Master_Sword");
		assertTrue(masterSword.contains("[template=ItemInfobox]"), "wikitext should convert to [template] bbcode");
		assertTrue(masterSword.contains("[b]Master Sword[/b]"), masterSword);
		assertTrue(masterSword.contains("[[File:KoT Master Sword.jpg"),
				"template param values must stay raw for the expander");
		assertEquals(1, count("zfgbb.content_resource where filename = 'KoT_Master_Sword.jpg'"),
				"template-param image bytes must be preserved at migration time");

		String oot3dArticle = currentRevision("Ocarina_of_Time_3D");
		assertTrue(oot3dArticle.contains("[img]/content/"),
				"body image with a fixture file should resolve to a content url");
		assertFalse(oot3dArticle.contains("wiki-file:"),
				"all article body images have real fixture files and resolve");
		assertTrue(currentRevision("Broken_Image_Test").contains("wiki-file:"),
				"the dedicated fixture page's missing image keeps the unresolved marker");

		WikiPage masterSwordPage = wikiService.getWikiPage("KOT:Master_Sword", null);
		assertTrue(masterSwordPage.getContentParsed().contains("bb-code-b"),
				"read path should render stored BBCode to HTML");
		assertEquals("BBCODE", masterSwordPage.getContentFormat());
		assertTrue(masterSwordPage.getContentParsed().contains("bb-code-table"),
				"ItemInfobox should expand to the seeded table definition");
		assertTrue(masterSwordPage.getContentParsed().contains("2x Noble Sword"),
				"template params should substitute into the definition");
		assertFalse(masterSwordPage.getContentParsed().contains("[template="),
				"no unexpanded template blocks should remain for known templates");
		assertTrue(masterSwordPage.getContentParsed().contains("/content/"),
				"the infobox param image should resolve via content_resource filename lookup");
		assertEquals(List.of("KOT Items", "King Of Thieves"),
				masterSwordPage.getCategories().stream().sorted().toList(),
				"[[Category:...]] links should migrate into wiki_page_category");

		List<WikiRevisionRef> history = wikiService.getWikiHistory("KOT:Master_Sword");
		assertEquals(2, history.size(), "all source revisions should migrate: " + history);
		assertTrue(history.get(0).isCurrent(), "newest revision should be current");
		assertEquals("MidnightMoblin", history.get(0).getAuthorName());
		assertEquals(2024, history.get(0).getAuthoredTs().getYear(),
				"authored_ts should carry the legacy edit time");
		assertEquals("SheikahSlate", history.get(1).getAuthorName());
		assertEquals("Added damage numbers", history.get(1).getSummary());

		List<WikiRevisionRef> recentChanges = wikiService.getWikiRecentChanges();
		assertFalse(recentChanges.isEmpty(), "recent changes should surface migrated revisions");
		assertTrue(recentChanges.get(0).getPage() != null && recentChanges.get(0).getAuthoredTs() != null,
				"recent changes entries carry page refs and legacy timestamps");
		assertTrue(recentChanges.size() <= 30, "recent changes must be limited");

		WikiPage oldRevision = wikiService.getWikiPage("KOT:Master_Sword", history.get(1).getRevisionId(), true);
		assertFalse(oldRevision.getRevision().isCurrent());
		assertFalse(oldRevision.getContent().contains("2x Noble Sword"),
				"an old revision should serve its own converted content");
		assertTrue(oldRevision.getContentParsed().contains("shooting beams"), oldRevision.getContentParsed());

		WikiPage kotItems = wikiService.getWikiPage("Category:KOT_Items", null);
		assertEquals("Category", kotItems.getNamespace());
		assertTrue(kotItems.getCategoryMembers().stream().anyMatch(ref -> "Master Sword".equals(ref.getTitle())),
				"category pages should list their member pages even without a page row");
		assertTrue(wikiService.getWikiPageIndex(null, "master", 1, 50).getTotal() >= 1,
				"the all-pages index should find pages by title search");
		assertTrue(wikiService.getWikiCategories().stream().anyMatch(entry -> "KOT Items".equals(entry.getKey())),
				"the category index should list migrated categories");
		Map<String, Object> wikiStats = wikiService.getWikiStatistics();
		assertTrue((int) wikiStats.get("totalPages") >= 14, "statistics should count all pages: " + wikiStats);
		assertTrue(((Map<?, ?>) wikiStats.get("byNamespace")).containsKey("MAIN"),
				"statistics should break pages down by namespace: " + wikiStats);
	}

	@Test
	@Order(2)
	void corpusLinkedArticleIsAdoptedAsTheEntityPage() {
		WikiPage redirect = wikiService.getWikiPage("Ocarina_of_Time", null);
		assertEquals("ocarina-of-time", redirect.getRedirectTo(),
				"the wiki article about a linked project becomes a redirect to its entity page");
		WikiPage entity = wikiService.getWikiPage("Project:ocarina-of-time", null, true);
		assertTrue(entity.getCategories().contains("ZFGC Projects"),
				"categorylinks membership travels to the adopted entity page");
		assertTrue(entity.getContentParsed().contains("Staff Commentary"),
				"the entity page should carry the adopted wiki article content");
		assertTrue(entity.getContentParsed().contains("Game Information"),
				"the wiki view renders wiki-scoped infobox templates");
		Project project = projectService.getProject("ocarina-of-time");
		assertFalse(project.getPage().getContentParsed().contains("Game Information"),
				"the project view suppresses wiki-scoped templates");
		assertTrue(project.getPage().getContentParsed().contains("Staff Commentary"),
				"the project view still renders the article prose");
		String adoptedSummary = jdbcTemplate.queryForObject(
				"select summary from zfgbb.project_view where slug = 'ocarina-of-time'", String.class);
		assertTrue(adoptedSummary != null && !adoptedSummary.isBlank(),
				"adoption should leave the project with a usable summary");
		assertTrue(entity.getContent().contains("[thread="),
				"legacy forum urls in wiki content should rewrite to thread links: " + entity.getContent());
		assertFalse(entity.getContent().contains("index.php?topic=2.0"),
				"mapped legacy topic urls must not survive raw");
		assertEquals("KokiriKid", wikiService.getWikiHistory("Project:ocarina-of-time").get(0).getAuthorName(),
				"adopted revisions keep their wiki authorship");
		assertEquals(7, wikiService.getWikiPage("Category:ZFGC_Projects", null).getCategoryMembers().size(),
				"every Project-namespace entity page belongs to ZFGC Projects, plus the unadopted article");
	}

	@Test
	@Order(3)
	void templatesMigrateAndMainPageRenders() {
		assertEquals(3, count("zfgbb.content_template ct join zfgbb.wiki_page p on p.wiki_page_id = ct.wiki_page_id "
				+ "where p.migration_hash is not null"),
				"tasks + FeaturedProject + KOT:News link to migrated Template-namespace pages");
		assertEquals(List.of("featuredproject", "kot:news", "tasks"), jdbcTemplate.queryForList(
				"select distinct ct.code from zfgbb.content_template ct "
						+ "join zfgbb.wiki_page p on p.wiki_page_id = ct.wiki_page_id "
						+ "where p.migration_hash is not null order by ct.code",
				String.class),
				"FeaturedProject must be re-linked from its seeded page to the migrated one");
		assertEquals(preMigrationLinkedTemplateCount + 2, count("zfgbb.content_template where wiki_page_id is not null"),
				"migration adds only the tasks and KOT:News rows; FeaturedProject keeps its seeded row");
		String mainPage = wikiService.getWikiPage("Main_Page", null).getContentParsed();
		assertTrue(mainPage.contains("featured community project"),
				"Main Page should render the migrated FeaturedProject template");
		assertFalse(mainPage.contains("{{tasks}}") || mainPage.contains("{{FeaturedProject}}")
				|| mainPage.contains("{{KOT:News}}"),
				"Main Page transclusions must not survive as raw placeholders");

		String pageCount = templateExpander.expand("[template=PageCount][/template]",
				ContentFormat.BBCODE, ContentScope.WIKI);
		assertTrue(Integer.parseInt(pageCount.trim()) >= 14, "PageCount system template: " + pageCount);
		String catList = templateExpander.expand("[template=CategoryList]\n_1=KOT Items\n[/template]",
				ContentFormat.BBCODE, ContentScope.WIKI);
		assertTrue(catList.contains("[wiki=KOT:Master_Sword]Master Sword[/wiki]"),
				"CategoryList system template should link members: " + catList);
	}

	@Test
	@Order(4)
	void memberProfilePagesRenderTheUserProfileTemplate() {
		Integer grogUserId = jdbcTemplate.queryForObject(
				"select zfgbb_id from zfgbb.migrator_id_map where entity_type = 'USER' and legacy_id = 3",
				Integer.class);
		WikiPage grog = wikiService.getWikiPage("Thestig", null, true);
		assertTrue(grog.getContent().contains("userid=" + grogUserId),
				"UserProfile userid params must remap to migrated user ids: " + grog.getContent());
		assertTrue(grog.getContentParsed().contains("/user/profile/" + grogUserId),
				"the seeded Profile row should link the migrated member");
		assertFalse(grog.getContentParsed().contains("<img src=\"\""),
				"an avatar-less user must not render an empty avatar image");
		assertFalse(grog.getHeadings().isEmpty(), "rendered pages expose their heading outline");
		WikiPage.Heading firstHeading = grog.getHeadings().get(0);
		assertTrue(grog.getContentParsed().contains("id=\"" + firstHeading.id() + "\""),
				"heading ids must be injected into the rendered html: " + firstHeading);
		assertFalse(firstHeading.id().isBlank(), "heading ids must be non-blank");
		assertEquals(grog.getContentParsed().contains("bb-toc")
				|| (grog.getHeadings().size() >= 4 && !grog.getContentParsed().contains("bb-notoc")),
				grog.isToc(), "toc flag follows the marker/threshold rule");
	}

	@Test
	@Order(5)
	void filePagesAndTalkThreadsMigrate() {
		assertEquals(2, count("zfgbb.wiki_page where namespace = 'File'"),
				"File description pages migrate as wiki pages");
		Integer swordResource = jdbcTemplate.queryForObject(
				"select content_resource_id from zfgbb.wiki_page where namespace = 'File' "
						+ "and title = 'KoT Master Sword.jpg'", Integer.class);
		assertNotNull(swordResource, "a File page with a real image binds its content resource");
		assertEquals(2, wikiService.getWikiHistory("File:KoT_Master_Sword.jpg").size(),
				"File page revision history migrates");
		assertNull(jdbcTemplate.queryForObject(
				"select content_resource_id from zfgbb.wiki_page where namespace = 'File' "
						+ "and title = 'Lost Screenshot.png'", Integer.class),
				"a File page whose image is lost keeps a null resource");

		assertEquals(1, count("zfgbb.wiki_page where thread_id is not null"),
				"talk pages link discussion threads to their subject pages");
		Integer talkThread = jdbcTemplate.queryForObject(
				"select thread_id from zfgbb.wiki_page where namespace = 'User' and title = 'mgzero'",
				Integer.class);
		assertNotNull(talkThread, "user talk lands on the member page");
		assertTrue(jdbcTemplate.queryForObject(
				"select thread_name from zfgbb.thread where thread_id = " + talkThread, String.class)
				.startsWith("Talk: "), "talk threads are recognizable by name");
		assertEquals("General Discussion", jdbcTemplate.queryForObject(
				"select b.board_name from zfgbb.thread t join zfgbb.board b on b.board_id = t.board_id "
						+ "where t.thread_id = " + talkThread, String.class),
				"User talk routes to its mapped board");
		assertTrue(jdbcTemplate.queryForObject(
				"select h.message_text from zfgbb.message_history h join zfgbb.message m on m.message_id = h.message_id "
						+ "where m.thread_id = " + talkThread + " and h.current_flag", String.class)
				.contains("he rulez"),
				"talk content converts into the opening post");
		assertEquals(0, count("zfgbb.wiki_page where namespace like '%Talk'"),
				"talk pages do not survive as wiki pages");
	}

	@Test
	@Order(6)
	void moderationGatesWikiEdits() {
		User author = new User();
		author.setUserId(jdbcTemplate.queryForObject("select min(user_id) from zfgbb.\"user\"", Integer.class));
		author.setDisplayName("Moderation Tester");

		WikiRevisionRef draft = wikiModerationService.submit("Template:Testbox",
				"[b]{{name}}[/b] approved template", "new template", author);
		assertEquals("PENDING", draft.getStatus());
		assertTrue(templateExpander.expand("[template=Testbox]\nname=Zelda\n[/template]",
				ContentFormat.BBCODE, ContentScope.WIKI).contains("{{Testbox}}"),
				"unapproved templates must not render");
		assertTrue(wikiModerationService.getPendingRevisions().stream()
				.anyMatch(ref -> draft.getRevisionId().equals(ref.getRevisionId())),
				"submitted revisions appear in the moderation queue");
		String preview = (String) wikiModerationService.preview(draft.getRevisionId()).get("contentParsed");
		assertTrue(preview.contains("bb-code-b"), "preview should render the pending body: " + preview);

		wikiModerationService.approve(draft.getRevisionId());
		String rendered = templateExpander.expand("[template=Testbox]\nname=Zelda\n[/template]",
				ContentFormat.BBCODE, ContentScope.WIKI);
		assertTrue(rendered.contains("[b]Zelda[/b]"), "approved template renders with params: " + rendered);
		assertEquals(1, count("zfgbb.content_template where code = 'testbox' and wiki_page_id is not null"),
				"approval publishes a wiki-owned content_template row");

		String original = wikiService.getWikiPage("mgzero", null, true).getContent();
		WikiRevisionRef edit = wikiModerationService.submit("mgzero",
				original + "\nPending edit marker.", "expand bio", author);
		assertEquals(original, wikiService.getWikiPage("mgzero", null, true).getContent(),
				"pending edits must not change the live page");
		wikiModerationService.approve(edit.getRevisionId());
		assertTrue(wikiService.getWikiPage("mgzero", null, true).getContent().contains("Pending edit marker."),
				"approval makes the edit live");

		WikiRevisionRef vandalism = wikiModerationService.submit("mgzero", "vandalism", "bad", author);
		wikiModerationService.reject(vandalism.getRevisionId());
		assertTrue(wikiService.getWikiPage("mgzero", null, true).getContent().contains("Pending edit marker."),
				"rejected edits never go live");
		assertEquals("REJECTED", wikiService.getWikiHistory("mgzero").stream()
				.filter(ref -> vandalism.getRevisionId().equals(ref.getRevisionId()))
				.findFirst().orElseThrow().getStatus(),
				"rejected revisions stay in history with their status");

		assertThrows(ResponseStatusException.class,
				() -> wikiModerationService.submit("Template:Pagecount", "hijack", "nope", author),
				"system-owned templates are not wiki-editable");
	}

	private String currentRevision(String slug) {
		return jdbcTemplate.queryForObject(
				"select r.content from zfgbb.wiki_page p join zfgbb.wiki_page_revision r "
						+ "on r.wiki_page_id = p.wiki_page_id where p.slug = ? and r.current_flag",
				String.class, slug);
	}
}
