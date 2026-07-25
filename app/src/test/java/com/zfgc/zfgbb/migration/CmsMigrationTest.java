package com.zfgc.zfgbb.migration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import com.zfgc.zfgbb.dbo.*;
import com.zfgc.zfgbb.mappers.*;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.model.cms.Project;
import com.zfgc.zfgbb.services.cms.catalog.ProjectService;
import com.zfgc.zfgbb.services.cms.catalog.ResourceService;
import com.zfgc.zfgbb.services.contentstore.ContentService;
import com.zfgc.zfgbb.services.search.SearchService;

@Order(2)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CmsMigrationTest extends MigrationE2E {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ContentService contentService;

	@Autowired
	private SearchService searchService;

	@Autowired private BrBoardPermissionDboMapper brBoardPermissionDboMapper;
	@Autowired private ContentCollectionDboMapper contentCollectionDboMapper;
	@Autowired private ContentCollectionItemDboMapper contentCollectionItemDboMapper;
	@Autowired private ContentEntityDboMapper contentEntityDboMapper;
	@Autowired private ContentResourceDboMapper contentResourceDboMapper;
	@Autowired private MigratorIdMapDboMapper migratorIdMapDboMapper;
	@Autowired private ProjectDboMapper projectDboMapper;
	@Autowired private ProjectDownloadDboMapper projectDownloadDboMapper;
	@Autowired private ProjectScreenshotDboMapper projectScreenshotDboMapper;
	@Autowired private ProjectViewDboMapper projectViewDboMapper;
	@Autowired private ResourceDboMapper resourceDboMapper;
	@Autowired private ResourceViewDboMapper resourceViewDboMapper;
	@Autowired private TagDboMapper tagDboMapper;
	@Autowired private TeamDboMapper teamDboMapper;
	@Autowired private TeamMemberDboMapper teamMemberDboMapper;
	@Autowired private UserDboMapper userDboMapper;

	@Test
	@Order(1)
	void pipelineMigratesProjectsAndResources() {
		assertEquals(JobType.CMS_INSTALLATION_PIPELINE.size(), cmsJobs.size(),
				"Pipeline should submit one job per CMS converter type");

		assertEquals(6, projectDboMapper.countByExample(new ProjectDboExample()),
				"5 ci_projects + 1 z3 game with no project match");
		assertEquals(4, resourceDboMapper.countByExample(new ResourceDboExample()),
				"2 ci+smf deduped + 1 smf-only + 1 ci-only credits list");
		assertEquals(3, contentCollectionDboMapper.countByExample(new ContentCollectionDboExample()),
				"z3 plus the fixture-jam and potm curated collections");
		assertEquals(6, contentCollectionItemDboMapper.countByExample(new ContentCollectionItemDboExample()),
				"z3 games (2) + fixture-jam (2 of 3 migrated) + potm history (2)");
		assertEquals(0, projectViews(view -> view.andWikiPageIdIsNull()), "every project owns a wiki page");
		assertEquals(0, resourceViews(view -> view.andWikiPageIdIsNull()), "every resource owns a wiki page");
		assertCmsAssets();
	}

	@Test
	@Order(2)
	void projectDetailCarriesMigratedAssetsAndMetadata() {
		Project oot = projectService.getProject("ocarina-of-time");
		assertEquals(0, oot.getScreenshots().size(),
				"the adopted article's single image is deduped into the preview slot");
		assertEquals(1, oot.getDownloads().size());
		assertEquals("oot_demo.zip", oot.getDownloads().get(0).getFilename(),
				"download payload should carry the stored filename");
		List<ContentService.ArchiveEntry> entries =
				contentService.getArchiveEntries(oot.getDownloads().get(0).getContentResourceId());
		assertEquals(1, entries.size(), "manifest demo zip carries a single readme");
		assertTrue(entries.stream().anyMatch(entry -> entry.name().equals("readme.txt")), entries.toString());
		assertTrue(oot.getPage() != null && oot.getPage().getContentParsed() != null,
				"project detail should include its rendered wiki page");
		assertEquals("gm112", oot.getAuthor(), "project detail should carry the author's display name");
		assertEquals(2023, oot.getPublishedTs().getYear(),
				"published date falls back to ci last_updated when time_created is 0");
		assertEquals(5.0f, oot.getRating(), "ci rating should migrate");
		assertEquals(64, oot.getVoteCount(), "ci votes should migrate");
		assertEquals(List.of("engine", "zelda"), oot.getTags().stream().sorted().toList(),
				"ci tags should migrate onto the project");
		assertEquals(1, oot.getNews().size(), "ci project news links its forum topic");

		Project oot3d = projectService.getProject("ocarina-of-time-3d");
		String oot3dSummary = projectView("ocarina-of-time-3d").getSummary();
		assertTrue(oot3dSummary.contains("Testing It's"),
				"PHP addslashes artifacts must be stripped: " + oot3dSummary);
		assertFalse(oot3dSummary.contains("\\'"), oot3dSummary);
		assertEquals(1, oot3d.getScreenshots().size(), "game screenshot should migrate from files/games");
		assertEquals(1, oot3d.getDownloads().size(), "game download should migrate from files/games");
		assertEquals("oot3d_demo.zip", oot3d.getDownloads().get(0).getFilename());
		assertEquals(2023, oot3d.getDownloads().get(0).getPublishedTs().getYear(),
				"game download publish dates should migrate from postTime");
		assertEquals(1, oot3d.getNews().size(), "z3 game news should migrate as text entries");
		assertEquals("Engine update", oot3d.getNews().get(0).getSubject());

		assertEquals("NaviBot", projectService.getProject("majora-s-mask-3d").getAuthor(),
				"unmigrated members fall back to the legacy member_name");
		assertEquals(1, teamDboMapper.countByExample(new TeamDboExample()), "fixture team should migrate");
		assertEquals(2, teamMemberDboMapper.countByExample(new TeamMemberDboExample()),
				"both fixture team members should migrate");
		assertEquals(3, tagDboMapper.countByExample(new TagDboExample()),
				"all ci tags migrate, including unused vocabulary");
	}

	@Test
	@Order(3)
	void resourcesFillAssetsFromTheSmfRegistry() {
		assertEquals("aonuma_photos.zip",
				resourceService.getResource("eiji-aonuma-photo-collection").getDownloadFilename(),
				"smf resource_downloads should fill the download the ci row lost");
		assertTrue(resourceService.getResource("eiji-aonuma-photo-collection").getPage().getContentParsed()
				.contains("Lorem ipsum dolor sit amet"),
				"ci resource descriptions must reach the entity page");
		assertEquals("miyamoto_photo.jpg",
				resourceService.getResource("shigeru-miyamoto-photo-archive").getDownloadFilename(),
				"smf-only resources should get assets from resource_downloads");
		assertEquals("gm112", resourceService.getResource("koji-kondo-zelda-themes").getAuthor(),
				"resource authors resolve through the migrated member mapping");
	}

	@Test
	@Order(4)
	void catalogFiltersAndShowcase() {
		assertEquals(6, projectService.getProjects(null, null, null, null, null, null, null, null).getTotal());
		assertEquals(4, resourceService.getResources(null, null, null, null, null, null, null).getTotal());
		assertEquals(1, projectService.getProjects("wind", null, null, null, null, null, null, null).getTotal(),
				"title search should match The Wind Waker");
		assertEquals(1, projectService.getProjects("WIND", null, null, null, null, null, null, null).getTotal(),
				"project title search should be case-insensitive");
		assertEquals(1, resourceService.getResources("pHoTo CoLlEcTiOn", null, null, null, null, null, null).getTotal(),
				"resource title search should be case-insensitive");
		assertEquals(1, resourceService.getResources(null, "AUDIO", null, null, null, null, null).getTotal(),
				"type filter should match the Kondo pack only");
		assertEquals(2, projectService.getProjects(null, null, null, null, null, null, 1, 2).getItems().size(),
				"page size should cap items while total stays 6");
		assertEquals(1, projectService.getProjects(null, null, null, "gm112", null, null, null, null).getTotal(),
				"author filter should match the linked display name");
		assertEquals(1, projectService.getProjects(null, null, null, "GM112", null, null, null, null).getTotal(),
				"project author filter should be case-insensitive");
		assertEquals(2, resourceService.getResources(null, null, "GM112", null, null, null, null).getTotal(),
				"resource author filter should be case-insensitive");
		ContentEntityDboExample wildEx = new ContentEntityDboExample();
		wildEx.createCriteria().andSlugIn(List.of("ocarina-of-time", "eiji-aonuma-photo-collection"));
		for (ContentEntityDbo entity : contentEntityDboMapper.selectByExample(wildEx)) {
			entity.setTitle(entity.getTitle() + " 100%_\\ Mix");
			contentEntityDboMapper.updateByPrimaryKeySelective(entity);
		}
		assertEquals(1, projectService.getProjects("100%_\\ mix", null, null, null, null, null, null, null).getTotal(),
				"project search should treat wildcard characters and backslash literally");
		assertEquals(1, resourceService.getResources("100%_\\ MIX", null, null, null, null, null, null).getTotal(),
				"resource search should treat wildcard characters and backslash literally");
		for (ContentEntityDbo entity : contentEntityDboMapper.selectByExample(wildEx)) {
			entity.setTitle(entity.getTitle().replace(" 100%_\\ Mix", ""));
			contentEntityDboMapper.updateByPrimaryKeySelective(entity);
		}
		assertEquals(3, projectService.getProjects(null, null, "Game Maker", null, null, null, null, null).getTotal(),
				"language facet filter");
		assertEquals(5, projectService.getProjects(null, null, null, null, Boolean.TRUE, null, null, null).getTotal(),
				"projects with a surviving download file");
		assertEquals(1, projectService.getProjects(null, null, null, null, Boolean.FALSE, null, null, null).getTotal(),
				"projects with no surviving download files");
		long wip = projectService.getProjects(null, "WIP", null, null, null, null, null, null).getTotal();
		assertEquals(6 - wip, projectService.getProjects(null, "-WIP", null, null, null, null, null, null).getTotal(),
				"excluding a status complements including it");
		long concept = projectService.getProjects(null, "CONCEPT", null, null, null, null, null, null).getTotal();
		assertEquals(wip + concept,
				projectService.getProjects(null, "WIP,CONCEPT", null, null, null, null, null, null).getTotal(),
				"multi-include unions statuses");
		assertEquals("Ocarina of Time",
				projectService.getProjects(null, null, null, null, null, "rating", null, null).getItems().get(0).getTitle(),
				"rating sort puts the 5-star 64-vote project first");

		var projectShowcase = projectService.getProjectShowcase();
		assertTrue(projectShowcase.getTotalProjects() > 0, "showcase reports the project count");
		assertNotNull(projectShowcase.getFeatured(), "the curated POTM collection surfaces as featured");
		assertEquals("ocarina-of-time", projectShowcase.getFeatured().getSlug(),
				"featured resolves to the POTM collection's project");
		assertFalse(projectShowcase.getRecent().isEmpty(), "showcase lists recent projects for the carousel");
		assertTrue(projectShowcase.getRecent().stream()
				.noneMatch(p -> p.getSlug().equals(projectShowcase.getFeatured().getSlug())),
				"the featured project is not duplicated in the recent carousel");
		var resourceShowcase = resourceService.getResourceShowcase();
		assertTrue(resourceShowcase.getTotalResources() > 0, "resource showcase reports the resource count");
		assertFalse(resourceShowcase.getRecent().isEmpty(), "resource showcase lists recent resources");
	}

	@Test
	@Order(5)
	void legacyCommentsBecomeDiscussionThreads() {
		Integer oot3dThread = projectView("ocarina-of-time-3d").getThreadId();
		assertNotNull(oot3dThread, "commented projects get a linked discussion thread");
		assertEquals(3, messages(message -> message.andThreadIdEqualTo(oot3dThread)),
				"each z3 game comment becomes a post");
		assertEquals("gm112", displayName(threadDboMapper.selectByPrimaryKey(oot3dThread).getCreatedUserId()),
				"comment discussion threads retain their starter");
		MessageDboExample commentPostExample = new MessageDboExample();
		commentPostExample.createCriteria().andThreadIdEqualTo(oot3dThread);
		assertEquals(List.of("gm112", "testmember", "MG-Zero"),
				messageDboMapper.selectByExample(commentPostExample).stream()
						.sorted(Comparator.comparing(MessageDbo::getPostInThread))
						.map(message -> displayName(message.getOwnerId())).toList(),
				"comment posts retain their authors");
		MessageHistoryDboExample secondPostBodyExample = new MessageHistoryDboExample();
		secondPostBodyExample.createCriteria().andCurrentFlagEqualTo(true)
				.andMessageIdEqualTo(findMessageIdAtPosition(oot3dThread, 2));
		assertTrue(messageHistoryDboMapper.selectByExample(secondPostBodyExample).get(0).getMessageText()
				.contains("elit's"), "comment bodies unescape SMF entities");
		assertEquals(2, resourceViews(view -> view.andThreadIdIsNotNull()),
				"commented resources get linked threads");
		ResourceViewDboExample aonumaExample = new ResourceViewDboExample();
		aonumaExample.createCriteria().andSlugEqualTo("eiji-aonuma-photo-collection");
		Integer aonumaThread = resourceViewDboMapper.selectByExample(aonumaExample).get(0).getThreadId();
		assertEquals(2, messages(message -> message.andThreadIdEqualTo(aonumaThread)),
				"resource comment chains keep every post");
		MigratorIdMapDboExample orphanCommentExample = new MigratorIdMapDboExample();
		orphanCommentExample.createCriteria().andEntityTypeEqualTo("RESOURCE_COMMENT").andLegacyIdEqualTo(2);
		Integer orphanCommentMessageId = migratorIdMapDboMapper.selectByExample(orphanCommentExample)
				.get(0).getZfgbbId();
		assertEquals(0, messageDboMapper.selectByPrimaryKey(orphanCommentMessageId).getOwnerId(),
				"missing legacy users resolve to the deleted-user sentinel");
		assertEquals(0, threads(thread -> thread.andThreadNameEqualTo("")),
				"comment threads carry entity titles");

		ContentCollectionDboExample potmCollectionExample = new ContentCollectionDboExample();
		potmCollectionExample.createCriteria().andCodeEqualTo("potm");
		ContentCollectionItemDboExample potmItemExample = new ContentCollectionItemDboExample();
		potmItemExample.createCriteria().andContentCollectionIdEqualTo(contentCollectionDboMapper
				.selectByExample(potmCollectionExample).get(0).getContentCollectionId());
		List<ContentCollectionItemDbo> potm = contentCollectionItemDboMapper.selectByExample(potmItemExample)
				.stream().sorted(Comparator.comparing(ContentCollectionItemDbo::getOrdinal)).toList();
		assertEquals(2, potm.size(), "both potm awards migrate: "
				+ potm.stream().map(award -> entitySlug(award.getContentEntityId())).toList());
		assertEquals("ocarina-of-time", entitySlug(potm.get(0).getContentEntityId()),
				"latest award leads the collection");
		assertNotNull(potm.get(0).getAwardedTs(), "awards carry their dates");
	}

	@Test
	@Order(6)
	void searchSpansMigratedContent() {
		List<Integer> allBoardPerms = brBoardPermissionDboMapper
				.selectByExample(new BrBoardPermissionDboExample()).stream()
				.map(BrBoardPermissionDbo::getPermissionId).distinct().toList();
		var privileged = searchService.search("Machines", List.of("forum"), allBoardPerms);
		assertTrue(privileged.getGroups().get(0).getTotal() > 0,
				"a user holding every board permission should get forum hits");
		var unprivileged = searchService.search("Machines", List.of("forum"), List.of(-999));
		assertEquals(0, unprivileged.getGroups().get(0).getTotal(),
				"forum search must return nothing for a permission set no board grants");
		var wikiSearch = searchService.search("Master Sword", List.of("wiki"), List.of(-999));
		assertTrue(wikiSearch.getTotal() > 0,
				"public wiki search does not depend on board permissions: " + wikiSearch);
	}

	private void assertCmsAssets() {
		ContentResourceDboExample cmsAssetExample = new ContentResourceDboExample();
		cmsAssetExample.createCriteria().andContentTypeIdIn(List.of(3, 4));
		assertEquals(23, contentResourceDboMapper.countByExample(cmsAssetExample),
				"manifest previews/screenshots/demos + wiki article images (Broken_Image_Test's is intentionally missing)");
		assertEquals(6, projectViews(view -> view.andPreviewContentResourceIdIsNotNull()),
				"every project carries its boxart preview");
		assertEquals(3, projectScreenshotDboMapper.countByExample(new ProjectScreenshotDboExample()),
				"2 ci gallery shots (Majora's beta shot file is lost) + game 169's one");
		assertEquals(5, projectDownloadDboMapper.countByExample(new ProjectDownloadDboExample()),
				"4 ci demo zips (Majora's 2011 slice file is lost) + game 169's demo");
		assertEquals(4, resourceViews(view -> view.andDownloadContentResourceIdIsNotNull()),
				"registry fills Aonuma + Kondo + Miyamoto; the credits list ships its own zip");
		assertEquals(3, resourceViews(view -> view.andPreviewContentResourceIdIsNotNull()),
				"the credits list intentionally has no preview");

		for (ContentResourceDbo asset : contentResourceDboMapper.selectByExample(cmsAssetExample)) {
			String describedAsset = asset.getContentResourceId() + " "
					+ asset.getStorageDir() + "/" + asset.getFilename();
			assertTrue(List.of("projects", "resources", "wiki").contains(asset.getStorageDir()),
					"cms assets must carry a domain storage dir: " + describedAsset);
			assertTrue(Files.exists(contentTarget.resolve(asset.getStorageDir())
					.resolve(String.valueOf(asset.getContentResourceId()))
					.resolve(asset.getFilename())),
					"cms asset should keep its original filename on disk: " + describedAsset);
		}
	}

	private long projectViews(Consumer<ProjectViewDboExample.Criteria> predicate) {
		ProjectViewDboExample example = new ProjectViewDboExample();
		predicate.accept(example.createCriteria());
		return projectViewDboMapper.countByExample(example);
	}

	private long resourceViews(Consumer<ResourceViewDboExample.Criteria> predicate) {
		ResourceViewDboExample example = new ResourceViewDboExample();
		predicate.accept(example.createCriteria());
		return resourceViewDboMapper.countByExample(example);
	}

	private long messages(Consumer<MessageDboExample.Criteria> predicate) {
		MessageDboExample example = new MessageDboExample();
		predicate.accept(example.createCriteria());
		return messageDboMapper.countByExample(example);
	}

	private long threads(Consumer<ThreadDboExample.Criteria> predicate) {
		ThreadDboExample example = new ThreadDboExample();
		predicate.accept(example.createCriteria());
		return threadDboMapper.countByExample(example);
	}

	private ProjectViewDbo projectView(String slug) {
		ProjectViewDboExample example = new ProjectViewDboExample();
		example.createCriteria().andSlugEqualTo(slug);
		return projectViewDboMapper.selectByExample(example).get(0);
	}

	private String displayName(Integer userId) {
		UserDbo user = userDboMapper.selectByPrimaryKey(userId);
		return user == null ? null : user.getDisplayName();
	}

	private String entitySlug(Integer contentEntityId) {
		return contentEntityDboMapper.selectByPrimaryKey(contentEntityId).getSlug();
	}
}
