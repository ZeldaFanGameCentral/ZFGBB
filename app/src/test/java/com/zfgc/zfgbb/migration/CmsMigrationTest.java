package com.zfgc.zfgbb.migration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.model.cms.Project;
import com.zfgc.zfgbb.services.cms.ProjectService;
import com.zfgc.zfgbb.services.cms.ResourceService;
import com.zfgc.zfgbb.services.core.ContentService;
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

	@Test
	@Order(1)
	void pipelineMigratesProjectsAndResources() {
		assertEquals(JobType.CMS_INSTALLATION_PIPELINE.size(), cmsJobs.size(),
				"Pipeline should submit one job per CMS converter type");

		assertEquals(6, count("zfgbb.project"), "5 ci_projects + 1 z3 game with no project match");
		assertEquals(4, count("zfgbb.resource"), "2 ci+smf deduped + 1 smf-only + 1 ci-only credits list");
		assertEquals(3, count("zfgbb.content_collection"), "z3 plus the fixture-jam and potm curated collections");
		assertEquals(6, count("zfgbb.content_collection_item"),
				"z3 games (2) + fixture-jam (2 of 3 migrated) + potm history (2)");
		assertEquals(0, count("zfgbb.project_view where wiki_page_id is null"), "every project owns a wiki page");
		assertEquals(0, count("zfgbb.resource_view where wiki_page_id is null"), "every resource owns a wiki page");
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
		String oot3dSummary = jdbcTemplate.queryForObject(
				"select summary from zfgbb.project_view where slug = 'ocarina-of-time-3d'", String.class);
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
		assertEquals(1, count("zfgbb.team"), "fixture team should migrate");
		assertEquals(2, count("zfgbb.team_member"), "both fixture team members should migrate");
		assertEquals(3, count("zfgbb.tag"), "all ci tags migrate, including unused vocabulary");
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
		assertEquals(1, resourceService.getResources(null, "AUDIO", null, null, null, null, null).getTotal(),
				"type filter should match the Kondo pack only");
		assertEquals(2, projectService.getProjects(null, null, null, null, null, null, 1, 2).getItems().size(),
				"page size should cap items while total stays 6");
		assertEquals(2, projectService.getProjects(null, null, null, "gm112", null, null, null, null).getTotal(),
				"author filter should match the linked display name");
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
		Integer oot3dThread = jdbcTemplate.queryForObject(
				"select thread_id from zfgbb.project_view where slug = 'ocarina-of-time-3d'", Integer.class);
		assertNotNull(oot3dThread, "commented projects get a linked discussion thread");
		assertEquals(3, count("zfgbb.message where thread_id = " + oot3dThread),
				"each z3 game comment becomes a post");
		assertTrue(jdbcTemplate.queryForObject(
				"select h.message_text from zfgbb.message_history h join zfgbb.message m on m.message_id = h.message_id "
						+ "where m.thread_id = " + oot3dThread + " and m.post_in_thread = 2 and h.current_flag",
				String.class).contains("elit's"),
				"comment bodies unescape SMF entities");
		assertEquals(2, count("zfgbb.resource_view where thread_id is not null"),
				"commented resources get linked threads");
		assertEquals(2, count("zfgbb.message m join zfgbb.resource_view r on r.thread_id = m.thread_id "
				+ "where r.slug = 'eiji-aonuma-photo-collection'"),
				"resource comment chains keep every post");
		assertEquals(0, count("zfgbb.thread where thread_name = ''"),
				"comment threads carry entity titles");

		java.util.List<java.util.Map<String, Object>> potm = jdbcTemplate.queryForList(
				"select p.slug, i.ordinal, i.awarded_ts from zfgbb.content_collection_item i "
						+ "join zfgbb.content_collection c on c.content_collection_id = i.content_collection_id "
						+ "join zfgbb.content_entity p on p.content_entity_id = i.content_entity_id "
						+ "where c.code = 'potm' order by i.ordinal");
		assertEquals(2, potm.size(), "both potm awards migrate: " + potm);
		assertEquals("ocarina-of-time", potm.get(0).get("slug"), "latest award leads the collection");
		assertNotNull(potm.get(0).get("awarded_ts"), "awards carry their dates");
	}

	@Test
	@Order(6)
	void searchSpansMigratedContent() {
		List<Integer> allBoardPerms = jdbcTemplate.queryForList(
				"select distinct permission_id from zfgbb.br_board_permission", Integer.class);
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

	@Test
	@Order(7)
	void rerunningCmsPipelineIsIdempotent() throws Exception {
		int wikiPageCountBeforeRerun = count("zfgbb.wiki_page");
		assertAllCompleted(waitForAllTerminal(
				jobService.submit(JobType.MIGRATE_CMS_INSTALLATION, params()), Duration.ofMinutes(2)));
		assertEquals(wikiPageCountBeforeRerun, count("zfgbb.wiki_page"), "re-run must not duplicate pages");
		assertEquals(0, count("(select slug from zfgbb.wiki_page group by slug having count(*) > 1) duplicated_slugs"),
				"re-run must not duplicate slugs");
		assertEquals(6, count("zfgbb.project"), "re-run must not duplicate projects");
		assertEquals(4, count("zfgbb.resource"), "re-run must not duplicate resources");
		assertCmsAssets();
	}

	private void assertCmsAssets() {
		assertEquals(23, count("zfgbb.content_resource where content_type_id in (3, 4)"),
				"manifest previews/screenshots/demos + wiki article images (Broken_Image_Test's is intentionally missing)");
		assertEquals(6, count("zfgbb.project_view where preview_content_resource_id is not null"),
				"every project carries its boxart preview");
		assertEquals(3, count("zfgbb.project_screenshot"),
				"2 ci gallery shots (Majora's beta shot file is lost) + game 169's one");
		assertEquals(5, count("zfgbb.project_download"),
				"4 ci demo zips (Majora's 2011 slice file is lost) + game 169's demo");
		assertEquals(4, count("zfgbb.resource_view where download_content_resource_id is not null"),
				"registry fills Aonuma + Kondo + Miyamoto; the credits list ships its own zip");
		assertEquals(3, count("zfgbb.resource_view where preview_content_resource_id is not null"),
				"the credits list intentionally has no preview");

		List<java.util.Map<String, Object>> cmsAssets = jdbcTemplate.queryForList(
				"select content_resource_id, storage_dir, filename from zfgbb.content_resource "
						+ "where content_type_id in (3, 4)");
		for (java.util.Map<String, Object> asset : cmsAssets) {
			assertTrue(List.of("projects", "resources", "wiki").contains(asset.get("storage_dir")),
					"cms assets must carry a domain storage dir: " + asset);
			assertTrue(Files.exists(contentTarget.resolve((String) asset.get("storage_dir"))
					.resolve(String.valueOf(asset.get("content_resource_id")))
					.resolve((String) asset.get("filename"))),
					"cms asset should keep its original filename on disk: " + asset);
		}
	}
}
