package com.zfgc.zfgbb.migration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.templates.TemplateExpansion;
import com.zfgc.zfgbb.dbo.*;
import com.zfgc.zfgbb.mappers.*;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.cms.Project;
import com.zfgc.zfgbb.model.cms.WikiConfig.NavItem;
import com.zfgc.zfgbb.model.cms.WikiConfig.NavSection;
import com.zfgc.zfgbb.model.cms.WikiPage;
import com.zfgc.zfgbb.model.cms.WikiRevisionRef;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.dataprovider.cms.WikiNamespaceDataProvider;
import com.zfgc.zfgbb.services.cms.catalog.ProjectService;
import com.zfgc.zfgbb.services.cms.wiki.WikiConfigService;
import com.zfgc.zfgbb.services.cms.wiki.WikiModerationService;
import com.zfgc.zfgbb.services.cms.wiki.WikiNamespaceRegistry;
import com.zfgc.zfgbb.services.cms.wiki.WikiService;

@Order(3)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MediaWikiMigrationTest extends MigrationE2E {

	@Autowired
	private WikiService wikiService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private TemplateExpansion templateExpansion;

	@Autowired
	private WikiModerationService wikiModerationService;

	@Autowired
	private WikiConfigService wikiConfigService;

	@Autowired
	private WikiNamespaceRegistry namespaceRegistry;

	@Autowired
	private WikiNamespaceDataProvider namespaceData;

	@Autowired private ContentResourceDboMapper contentResourceDboMapper;
	@Autowired private MigratorIdMapDboMapper migratorIdMapDboMapper;
	@Autowired private ProjectViewDboMapper projectViewDboMapper;
	@Autowired private UserDboMapper userDboMapper;
	@Autowired private WikiImportNamespaceDboMapper wikiImportNamespaceDboMapper;
	@Autowired private WikiNamespaceAliasDboMapper wikiNamespaceAliasDboMapper;
	@Autowired private WikiNamespaceDboMapper wikiNamespaceDboMapper;
	@Autowired private WikiPageCategoryDboMapper wikiPageCategoryDboMapper;

	@Test
	@Order(1)
	void pagesRevisionsAndCategoriesMigrate() {
		assertEquals(preMigrationWikiPageCount + 62,
				wikiPageDboMapper.countByExample(new WikiPageDboExample()),
				"migration adds the mediawiki source (incl. 2 member profile pages) + 2 File + 1 Special "
						+ "+ project/resource and generated member pages on top of the seeded pages, plus the "
						+ "29 corpus articles imported to close the fixture's red links (4 MAIN, 4 Help, "
						+ "1 ZFGCpedia, 20 KOT)");
		assertEquals(preMigrationWikiRevisionCount + 62,
				wikiPageRevisionDboMapper.countByExample(new WikiPageRevisionDboExample()),
				"migration adds the migrated current and historical revisions on top of the seeded revisions");
		assertEquals(5, wikiPages(page -> page.andNamespaceEqualTo("Project")),
				"the duplicate project candidate shares its target's entity page");
		assertEquals(4, wikiPages(page -> page.andNamespaceEqualTo("Resource")));
		assertSameCount(smf, "smf_1members", wikiPages(page -> page.andNamespaceEqualTo("User")));
		assertSameCount(smf, "smf_1members",
				wikiPageCategories(category -> category.andCategoryNameEqualTo("Members")));

		String masterSword = currentRevision("KOT:Master_Sword");
		assertTrue(masterSword.contains("[template=ItemInfobox]"), "wikitext should convert to [template] bbcode");
		assertTrue(masterSword.contains("[b]Master Sword[/b]"), masterSword);
		assertTrue(masterSword.contains("[[File:KoT Master Sword.jpg"),
				"template param values must stay raw for the expander");
		ContentResourceDboExample swordImageExample = new ContentResourceDboExample();
		swordImageExample.createCriteria().andFilenameEqualTo("KoT_Master_Sword.jpg");
		assertEquals(1, contentResourceDboMapper.countByExample(swordImageExample),
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
		assertEquals("Project:ocarina-of-time", redirect.getRedirectTo(),
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
		String adoptedSummary = projectSummary("ocarina-of-time");
		assertTrue(adoptedSummary != null && !adoptedSummary.isBlank(),
				"adoption should leave the project with a usable summary");
		assertTrue(entity.getContent().contains("[thread="),
				"legacy forum urls in wiki content should rewrite to thread links: " + entity.getContent());
		assertFalse(entity.getContent().contains("index.php?topic=2.0"),
				"mapped legacy topic urls must not survive raw");
		assertEquals("KokiriKid", wikiService.getWikiHistory("Project:ocarina-of-time").get(0).getAuthorName(),
				"adopted revisions keep their wiki authorship");
		var zfgcProjectMembers = wikiService.getWikiPage("Category:ZFGC_Projects", null).getCategoryMembers();
		WikiPageRevisionDboExample emptyBodyExample = new WikiPageRevisionDboExample();
		emptyBodyExample.createCriteria().andCurrentFlagEqualTo(true).andContentEqualTo("")
				.andWikiPageIdIn(wikiPageIds(page -> page.andMigrationHashIsNotNull().andRedirectToIsNull()));
		assertEquals(0, wikiPageRevisionDboMapper.countByExample(emptyBodyExample),
				"every migrated article that is not a redirect must carry content; empty bodies mean "
						+ "the legacy fixture stopped loading partway through");
		assertEquals(7, zfgcProjectMembers.size(),
				"every distinct Project entity page belongs to ZFGC Projects, plus the unadopted article "
						+ "and the imported King Of Thieves article; members=" + zfgcProjectMembers);
	}

	@Test
	@Order(3)
	void templatesMigrateAndMainPageRenders() {
		List<Integer> migratedWikiPageIds = wikiPageIds(page -> page.andMigrationHashIsNotNull());
		assertEquals(3, contentTemplates(template -> template.andWikiPageIdIn(migratedWikiPageIds)),
				"tasks + FeaturedProject + KOT:News link to migrated Template-namespace pages");
		ContentTemplateDboExample migratedTemplateExample = new ContentTemplateDboExample();
		migratedTemplateExample.createCriteria().andWikiPageIdIn(migratedWikiPageIds);
		assertEquals(List.of("FeaturedProject", "KOT:News", "Tasks"),
				contentTemplateDboMapper.selectByExample(migratedTemplateExample).stream()
						.map(ContentTemplateDbo::getCode).distinct().sorted().toList(),
				"FeaturedProject must be re-linked from its seeded page to the migrated one");
		assertEquals(preMigrationLinkedTemplateCount + 3,
				contentTemplates(template -> template.andWikiPageIdIsNotNull()),
				"the reviewed overlay links Tasks, KOT:News, and FeaturedProject to their migrated pages");
		String mainPage = wikiService.getWikiPage("Main_Page", null).getContentParsed();
		assertTrue(mainPage.contains("featured community project"),
				"Main Page should render the migrated FeaturedProject template");
		assertFalse(mainPage.contains("{{tasks}}") || mainPage.contains("{{FeaturedProject}}")
				|| mainPage.contains("{{KOT:News}}"),
				"Main Page transclusions must not survive as raw placeholders");

		String pageCount = templateExpansion.expandSource("[template=PageCount][/template]", ContentFormat.BBCODE, ContentScope.WIKI, Map.of());
		assertTrue(Integer.parseInt(pageCount.trim()) >= 14, "PageCount system template: " + pageCount);
		String catList = templateExpansion.expandSource("[template=CategoryList]\n_1=KOT Items\n[/template]", ContentFormat.BBCODE, ContentScope.WIKI, Map.of());
		assertTrue(catList.contains("[wiki=KOT:Master_Sword]Master Sword[/wiki]"),
				"CategoryList system template should link members: " + catList);
	}

	@Test
	@Order(4)
	void memberProfilePagesRenderTheUserProfileTemplate() {
		MigratorIdMapDboExample grogMappingExample = new MigratorIdMapDboExample();
		grogMappingExample.createCriteria().andEntityTypeEqualTo("USER").andLegacyIdEqualTo(3);
		List<MigratorIdMapDbo> grogMappings = migratorIdMapDboMapper.selectByExample(grogMappingExample);
		Integer grogUserId = grogMappings.isEmpty() ? null : grogMappings.get(0).getZfgbbId();
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
		Document renderedGrog = Jsoup.parseBodyFragment(grog.getContentParsed());
		assertEquals(renderedGrog.selectFirst(".bb-toc") != null
				|| (grog.getHeadings().size() >= 4 && renderedGrog.selectFirst(".bb-notoc") == null),
				grog.isToc(),
				"toc flag follows the marker/threshold rule; the markers are element classes, so restating the "
						+ "rule as a substring search over the whole page would re-bless the defect page "
						+ "assembly just stopped exhibiting");
	}

	@Test
	@Order(5)
	void filePagesAndTalkThreadsMigrate() {
		assertEquals(2, wikiPages(page -> page.andNamespaceEqualTo("File")),
				"File description pages migrate as wiki pages");
		assertNotNull(filePageResourceId("KoT Master Sword.jpg"),
				"a File page with a real image binds its content resource");
		assertEquals(2, wikiService.getWikiHistory("File:KoT_Master_Sword.jpg").size(),
				"File page revision history migrates");
		assertEquals(1, wikiPages(page -> page.andNamespaceEqualTo("File")
				.andTitleEqualTo("Lost Screenshot.png")),
				"the File page whose image is lost must still exist to carry a null resource");
		assertNull(filePageResourceId("Lost Screenshot.png"),
				"a File page whose image is lost keeps a null resource");

		assertEquals(0, wikiPages(page -> page.andNamespaceLike("%Talk")),
				"talk pages do not survive as wiki pages");
	}

	@Test
	@Order(6)
	void moderationGatesWikiEdits() {
		User author = new User();
		author.setUserId(userDboMapper.selectByExample(new UserDboExample()).stream()
				.mapToInt(UserDbo::getUserId).min().orElseThrow());
		author.setDisplayName("Moderation Tester");
		Permission moderator = new Permission();
		moderator.setPermissionCode("ZFGC_WIKI_MODERATOR");
		author.setPermissions(List.of(moderator));

		WikiRevisionRef draft = wikiModerationService.submit("Template:Testbox",
				"[b]{{name}}[/b] approved template", null, "new template", author);
		assertEquals("PENDING", draft.getStatus());
		assertEquals("", templateExpansion.expandSource("[template=Testbox]\nname=Zelda\n[/template]", ContentFormat.BBCODE, ContentScope.WIKI, Map.of()),
				"unapproved templates must be treated as defined but unpublished");
		assertTrue(wikiModerationService.getPendingRevisions().stream()
				.anyMatch(ref -> draft.getRevisionId().equals(ref.getRevisionId())),
				"submitted revisions appear in the moderation queue");
		String preview = (String) wikiModerationService.preview(draft.getRevisionId()).get("contentParsed");
		assertTrue(preview.contains("bb-code-b"), "preview should render the pending body: " + preview);

		wikiModerationService.approve(draft.getRevisionId());
		String rendered = templateExpansion.expandSource("[template=Testbox]\nname=Zelda\n[/template]", ContentFormat.BBCODE, ContentScope.WIKI, Map.of());
		assertTrue(rendered.contains("[b]Zelda[/b]"), "approved template renders with params: " + rendered);
		assertEquals(1, contentTemplates(template -> template.andCodeEqualTo("Testbox")
				.andWikiPageIdIsNotNull()),
				"approval publishes a wiki-owned content_template row");

		String original = wikiService.getWikiPage("mgzero", null, true).getContent();
		WikiRevisionRef edit = wikiModerationService.submit("mgzero",
				original + "\nPending edit marker.", null, "expand bio", author);
		assertEquals(original, wikiService.getWikiPage("mgzero", null, true).getContent(),
				"pending edits must not change the live page");
		wikiModerationService.approve(edit.getRevisionId());
		assertTrue(wikiService.getWikiPage("mgzero", null, true).getContent().contains("Pending edit marker."),
				"approval makes the edit live");

		WikiRevisionRef vandalism = wikiModerationService.submit("mgzero", "vandalism", null, "bad", author);
		wikiModerationService.reject(vandalism.getRevisionId());
		assertTrue(wikiService.getWikiPage("mgzero", null, true).getContent().contains("Pending edit marker."),
				"rejected edits never go live");
		assertEquals("REJECTED", wikiService.getWikiHistory("mgzero").stream()
				.filter(ref -> vandalism.getRevisionId().equals(ref.getRevisionId()))
				.findFirst().orElseThrow().getStatus(),
				"rejected revisions stay in history with their status");

		assertEquals(0, contentTemplates(template -> template.andCodeEqualTo("Pagecount")
				.andWikiPageIdIsNotNull()),
				"Pagecount ships engine-owned");
		String engineRendered = templateExpansion.expandSource("[template=Pagecount][/template]", ContentFormat.BBCODE, ContentScope.WIKI, Map.of());
		assertFalse(engineRendered.isBlank(), "the engine-seeded template renders before anyone edits it");

		WikiRevisionRef adoption = wikiModerationService.submit("Template:Pagecount",
				"[b]{{name}}[/b] adopted", null, "adopt the engine template", author);
		assertEquals(engineRendered, templateExpansion.expandSource("[template=Pagecount][/template]", ContentFormat.BBCODE, ContentScope.WIKI, Map.of()),
				"a pending edit must not blank an engine-seeded template that is still engine-owned");

		wikiModerationService.approve(adoption.getRevisionId());
		assertEquals(2, contentTemplates(template -> template.andCodeEqualTo("Pagecount")
				.andWikiPageIdIsNotNull()),
				"provenance is the rule: editing an engine-seeded template adopts every format for the wiki");
		assertEquals(1, contentTemplates(template -> template.andCodeEqualTo("Pagecount")
				.andContentFormatEqualTo("BBCODE")),
				"adoption must reuse the seeded row, not fork a second row with the same code");

		testQueryHelperMapper.seedContentTemplate("Pagecount", "BBCODE", "ALL", null, "engine body");
		assertEquals(1, contentTemplates(template -> template.andCodeEqualTo("Pagecount")
				.andContentFormatEqualTo("BBCODE")),
				"re-running the engine seed must not fork a shadow row alongside an adopted template");
		assertEquals(0, contentTemplates(template -> template.andCodeEqualTo("Pagecount")
				.andWikiPageIdIsNull()),
				"once the wiki adopts a template the engine stops owning it in every format");
		assertEquals(1, contentTemplates(template -> template.andCodeEqualTo("Pagecount")
				.andContentFormatEqualTo("MARKDOWN").andBodyIsNotNull()),
				"adoption transfers sibling formats to the wiki; it must never destroy their bodies");
	}

	@Test
	@Order(8)
	void importConfigurationReportsTheNamespaceThatActuallyHoldsTheRole() {
		assertEquals("ZFGCpedia", namespaceRegistry.listImportNamespaces().stream()
				.filter(row -> row.getSourceNamespaceId() == 4).findFirst().orElseThrow().getNamespaceName(),
				"the admin view must report the namespace that holds META, not a stale configured name");
		assertEquals("ZFGCpedia", importNamespaceName(4),
				"and the migration must have corrected the stored configuration rather than silently ignoring it");
		assertEquals(0, wikiNamespaces(namespace -> namespace.andNameEqualTo("Meta")),
				"reconciling must not leave an orphan namespace behind");
	}

	@Test
	@Order(9)
	void underscoreNamespacesGetSpaceFormAliasesWithoutHardcodedNames() {
		assertEquals(1, wikiNamespaceAliases(alias -> alias.andAliasEqualTo("User talk")
				.andNamespaceNameEqualTo("User_talk")),
				"the space spelling is derived from the namespace name, not a compiled list");
		assertEquals(1, wikiNamespaceAliases(alias -> alias.andAliasEqualTo("ZFGCpedia talk")
				.andNamespaceNameEqualTo("ZFGCpedia_talk")),
				"an operator-named namespace gets the same treatment as an engine one");
		Set<String> namespaceNames = wikiNamespaceDboMapper.selectByExample(new WikiNamespaceDboExample())
				.stream().map(namespace -> namespace.getName().toLowerCase(Locale.ROOT))
				.collect(Collectors.toSet());
		assertEquals(0, wikiNamespaceAliasDboMapper.selectByExample(new WikiNamespaceAliasDboExample())
				.stream().filter(alias -> namespaceNames.contains(alias.getAlias().toLowerCase(Locale.ROOT)))
				.count(),
				"an alias must never collide with a real namespace name");
		assertEquals("User_talk", namespaceData.resolve("User talk:Sample").namespace(),
				"the space spelling resolves to the underscore namespace");
		assertEquals("ZFGCpedia_talk", namespaceData.resolve("ZFGCpedia talk:Sample").namespace(),
				"including for operator-named namespaces");
	}

	@Test
	@Order(13)
	void engineOnlyNamespacesStayHiddenEvenWhenTheirRoleIsUnassigned() {
		assertFalse(wikiConfigService.getConfig().namespaces().contains("MediaWiki"),
				"MediaWiki: is engine-only and must never be listed");
		assertFalse(wikiConfigService.getConfig().namespaces().contains("Special"),
				"Special: is engine-only and must never be listed");

		WikiNamespaceDboExample engineOnlyExample = new WikiNamespaceDboExample();
		engineOnlyExample.createCriteria().andEngineRoleIn(List.of("MEDIAWIKI", "SPECIAL"));
		for (WikiNamespaceDbo namespace : wikiNamespaceDboMapper.selectByExample(engineOnlyExample))
			assignEngineRole(namespace.getName(), null);
		try {
			assertFalse(wikiConfigService.getConfig().namespaces().contains("MediaWiki"),
					"hiding engine-only namespaces must fail closed when the role is unassigned");
			assertFalse(wikiConfigService.getConfig().namespaces().contains("Special"),
					"hiding engine-only namespaces must fail closed when the role is unassigned");
		}
		finally {
			assignEngineRole("MediaWiki", "MEDIAWIKI");
			assignEngineRole("Special", "SPECIAL");
		}
	}

	@Test
	@Order(10)
	void namespaceNamesComeFromEditableConfigNotFromCompiledConstants() {
		assertEquals(16, wikiImportNamespaceDboMapper.countByExample(new WikiImportNamespaceDboExample()),
				"MediaWiki's canonical namespace ids ship as editable seed rows");
		assertEquals("User_talk", importNamespaceName(3),
				"talk namespaces use MediaWiki's own naming");
		assertEquals(List.of(), camelCaseTalkNames(wikiImportNamespaceDboMapper
				.selectByExample(new WikiImportNamespaceDboExample()).stream()
				.map(WikiImportNamespaceDbo::getNamespaceName)),
				"no CamelCase talk namespace may survive (bare 'Talk' is MediaWiki's name for id 1)");

		assertEquals(List.of(), camelCaseTalkNames(wikiNamespaceDboMapper
				.selectByExample(new WikiNamespaceDboExample()).stream()
				.map(WikiNamespaceDbo::getName)),
				"the migrated wiki registry uses the same naming");
		assertTrue(currentRevision("KOT:Master_Sword") != null,
				"the KOT namespace, configured per-migration rather than seeded, still resolves");
	}

	@Test
	@Order(11)
	void everyMigratedNamespaceNameTracesBackToConfig() {
		WikiPageDboExample fallbackNamespaceExample = new WikiPageDboExample();
		fallbackNamespaceExample.createCriteria().andNamespaceLike("NS%");
		assertEquals(0, wikiPageDboMapper.selectByExample(fallbackNamespaceExample).stream()
				.filter(page -> page.getNamespace().matches("NS[0-9]+")).count(),
				"a page named NS<id> means the migrator found no configured name for that source "
						+ "namespace and fell back, which would silently ship an unnamed namespace");
	}

	@Test
	@Order(7)
	void sidebarMigratesAndDrivesTheWikiNav() {
		String sidebar = currentRevision("MediaWiki:Sidebar");
		assertNotNull(sidebar, "the corpus MediaWiki:Sidebar page must migrate");
		assertTrue(sidebar.contains("[li]navigation [list]"),
				"nested wikitext bullets must convert to nested bbcode lists: " + sidebar);
		assertFalse(sidebar.contains("**"), "no raw wikitext bullets may survive: " + sidebar);

		List<NavSection> nav = wikiConfigService.getConfig().nav();
		assertEquals(List.of("navigation", "Content", "ZFGC"),
				nav.stream().map(NavSection::title).toList(),
				"every sidebar section with items must reach the nav; TOOLBOX has none and is dropped");
		Map<String, String> targets = nav.stream().flatMap(section -> section.items().stream())
				.collect(Collectors.toMap(NavItem::label, NavItem::to));
		assertEquals("/wiki/Main_Page", targets.get("Main page"), "mainpage magic word resolves");
		assertEquals("/wiki/special/recentchanges", targets.get("Recent changes"),
				"recentchanges-url resolves to the app route, not a wiki page");
		assertEquals("/wiki/ZFGCpedia:Community_portal", targets.get("Community portal"),
				"portal-url points at the migrated ZFGCpedia page");
		assertEquals("/wiki/Category:Members", targets.get("List of Members"),
				"leading-colon category links keep a usable wiki target");

		assertEquals("/", targets.get("Home"), "legacy zfgc.com roots rewrite to the app home");
		assertEquals("/content/projects", targets.get("Projects"), "legacy cms urls rewrite to app routes");
		assertEquals("/content/resources", targets.get("Resources"), "legacy cms urls rewrite to app routes");
		assertEquals("/forum", targets.get("Community"), "the legacy forum url rewrites to /forum");
		assertFalse(targets.containsKey("Chat"),
				"a legacy url with no app route is dropped rather than rendered dead: " + targets);
		assertTrue(targets.values().stream().noneMatch(to -> to.contains("zfgc.com")),
				"no nav entry may point at the dead legacy host: " + targets);
		assertEquals(11, nav.stream().mapToInt(section -> section.items().size()).sum(),
				"all sidebar entries except the unmappable Chat link resolve");
	}

	@Test
	@Order(12)
	void metaNamespacesCarryTheirEditPolicyAfterMigration() {
		assertEquals(1, wikiNamespaces(namespace -> namespace.andNameEqualTo("ZFGCpedia")
				.andEditPermissionCodeEqualTo("ZFGC_WIKI_MODERATOR")),
				"the wiki meta namespace must stay moderator-gated; the Flyway seed cannot reach it, "
						+ "so the importer applies the policy implied by MediaWiki namespace id 4");
		assertEquals(1, wikiNamespaces(namespace -> namespace.andNameEqualTo("MediaWiki")
				.andEditPermissionCodeEqualTo("ZFGC_SITE_ADMIN").andSystemManagedEqualTo(false)),
				"MediaWiki: is admin-editable rather than hard-blocked");
		assertEquals(3, wikiNamespaces(namespace -> namespace.andSystemManagedEqualTo(true)),
				"Special, Project and Resource are system managed");
		assertEquals(1, wikiNamespaces(namespace -> namespace.andNameEqualTo("ZFGCpedia")
				.andEngineRoleEqualTo("META")),
				"a namespace the operator renamed still carries the engine role of MediaWiki namespace 4");
		assertEquals(1, wikiNamespaces(namespace -> namespace.andNameEqualTo("ZFGCpedia_talk")
				.andEngineRoleEqualTo("META_TALK")),
				"its talk namespace carries the paired role too");
		assertEquals(1, wikiNamespaces(namespace -> namespace.andEngineRoleEqualTo("TEMPLATE")),
				"exactly one namespace may hold the TEMPLATE role");
		assertEquals(0, wikiNamespaces(namespace -> namespace.andNameIn(List.of("Project", "Resource"))
				.andSystemManagedEqualTo(false)), "entity namespaces are never wiki-editable");
	}

	private List<String> camelCaseTalkNames(Stream<String> namespaceNames) {
		return namespaceNames.filter(name -> name.endsWith("Talk") && !name.equals("Talk")).toList();
	}

	private long wikiPageCategories(Consumer<WikiPageCategoryDboExample.Criteria> predicate) {
		WikiPageCategoryDboExample example = new WikiPageCategoryDboExample();
		predicate.accept(example.createCriteria());
		return wikiPageCategoryDboMapper.countByExample(example);
	}

	private long wikiPages(Consumer<WikiPageDboExample.Criteria> predicate) {
		WikiPageDboExample example = new WikiPageDboExample();
		predicate.accept(example.createCriteria());
		return wikiPageDboMapper.countByExample(example);
	}

	private List<Integer> wikiPageIds(Consumer<WikiPageDboExample.Criteria> predicate) {
		WikiPageDboExample example = new WikiPageDboExample();
		predicate.accept(example.createCriteria());
		return wikiPageDboMapper.selectByExample(example).stream()
				.map(WikiPageDbo::getWikiPageId).toList();
	}

	private long wikiNamespaces(Consumer<WikiNamespaceDboExample.Criteria> predicate) {
		WikiNamespaceDboExample example = new WikiNamespaceDboExample();
		predicate.accept(example.createCriteria());
		return wikiNamespaceDboMapper.countByExample(example);
	}

	private long wikiNamespaceAliases(Consumer<WikiNamespaceAliasDboExample.Criteria> predicate) {
		WikiNamespaceAliasDboExample example = new WikiNamespaceAliasDboExample();
		predicate.accept(example.createCriteria());
		return wikiNamespaceAliasDboMapper.countByExample(example);
	}

	private long contentTemplates(Consumer<ContentTemplateDboExample.Criteria> predicate) {
		ContentTemplateDboExample example = new ContentTemplateDboExample();
		predicate.accept(example.createCriteria());
		return contentTemplateDboMapper.countByExample(example);
	}

	private String importNamespaceName(int sourceNamespaceId) {
		WikiImportNamespaceDbo row = wikiImportNamespaceDboMapper.selectByPrimaryKey(sourceNamespaceId);
		return row == null ? null : row.getNamespaceName();
	}

	private Integer filePageResourceId(String title) {
		WikiPageDboExample example = new WikiPageDboExample();
		example.createCriteria().andNamespaceEqualTo("File").andTitleEqualTo(title);
		List<WikiPageDbo> pages = wikiPageDboMapper.selectByExample(example);
		return pages.isEmpty() ? null : pages.get(0).getContentResourceId();
	}

	private String projectSummary(String slug) {
		ProjectViewDboExample example = new ProjectViewDboExample();
		example.createCriteria().andSlugEqualTo(slug);
		List<ProjectViewDbo> views = projectViewDboMapper.selectByExample(example);
		return views.isEmpty() ? null : views.get(0).getSummary();
	}

	private void assignEngineRole(String namespaceName, String engineRole) {
		WikiNamespaceDbo namespace = wikiNamespaceDboMapper.selectByPrimaryKey(namespaceName);
		namespace.setEngineRole(engineRole);
		wikiNamespaceDboMapper.updateByPrimaryKey(namespace);
	}

	private String currentRevision(String slug) {
		List<Integer> pageIds = wikiPageIds(page -> page.andSlugEqualTo(slug));
		if (pageIds.isEmpty())
			return null;
		assertEquals(1, pageIds.size(),
				"slug is unique only within a namespace, so a second page for " + slug
						+ " means namespace resolution regressed");
		WikiPageRevisionDboExample example = new WikiPageRevisionDboExample();
		example.createCriteria().andWikiPageIdEqualTo(pageIds.get(0)).andCurrentFlagEqualTo(true);
		List<WikiPageRevisionDbo> revisions = wikiPageRevisionDboMapper.selectByExample(example);
		return revisions.isEmpty() ? null : revisions.get(0).getContent();
	}
}
