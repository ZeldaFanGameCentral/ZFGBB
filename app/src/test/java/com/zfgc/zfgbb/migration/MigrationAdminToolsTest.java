package com.zfgc.zfgbb.migration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zfgc.zfgbb.migrator.web.QuoteStripOperations;
import com.zfgc.zfgbb.migrator.web.QuoteStripOperations.QuoteStripApplyResult;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.cms.Project;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.services.cms.CmsAdminService;
import com.zfgc.zfgbb.services.cms.ProjectService;
import com.zfgc.zfgbb.services.conversion.QuoteStripService;
import com.zfgc.zfgbb.services.system.SystemConfigService;

@Order(4)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MigrationAdminToolsTest extends MigrationE2E {

	private static final Pattern EMPTIED_QUOTE_SHELL =
			Pattern.compile("\\[quote[^\\]]*msg=\\d+[^\\]]*\\]\\[/quote\\]", Pattern.CASE_INSENSITIVE);

	@Autowired
	private ProjectService projectService;

	@Autowired
	private CmsAdminService cmsAdminService;

	@Autowired
	private SystemConfigService systemConfigService;

	@Autowired
	private QuoteStripService quoteStripService;

	@Test
	@Order(1)
	void discussionThreadsAndMergeCenter() {
		Integer boardId = jdbcTemplate.queryForObject("select min(board_id) from zfgbb.board", Integer.class);
		jdbcTemplate.update(
				"insert into zfgbb.br_board_permission (board_id, permission_id) values (?, 1) on conflict do nothing",
				boardId);
		systemConfigService.set(SystemConfigService.Keys.CMS_DISCUSSION_BOARD_ID, boardId.toString());
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
		try {
			User user = new User();
			user.setUserId(jdbcTemplate.queryForObject("select min(user_id) from zfgbb.\"user\"", Integer.class));
			Permission postPermission = new Permission();
			postPermission.setId(1);
			postPermission.setPermissionCode("ZFGC_USER");
			user.getPermissions().add(postPermission);

			Integer migratedCommentThread = jdbcTemplate.queryForObject(
					"select thread_id from zfgbb.project_view where slug = 'ocarina-of-time-3d'", Integer.class);
			assertEquals(migratedCommentThread,
					projectService.startProjectDiscussion("ocarina-of-time-3d", user).getThreadId(),
					"projects with migrated comment threads must reuse them");

			String unlinkedSlug = jdbcTemplate.queryForObject(
					"select slug from zfgbb.project_view where thread_id is null order by content_entity_id limit 1",
					String.class);
			Project unlinked = projectService.startProjectDiscussion(unlinkedSlug, user);
			assertTrue(unlinked.getThreadId() != null, "start discussion should create and link a thread");
			assertEquals(unlinked.getThreadId(),
					projectService.startProjectDiscussion(unlinkedSlug, user).getThreadId(),
					"second call must reuse the existing thread");
			String firstPost = jdbcTemplate.queryForObject(
					"select h.message_text from zfgbb.message_history h join zfgbb.message m on m.message_id = h.message_id "
							+ "where m.thread_id = ? and h.current_flag = true",
					String.class, unlinked.getThreadId());
			assertTrue(firstPost.contains("[project="), "first post should link back to the project: " + firstPost);
			assertEquals(Integer.valueOf(1), jdbcTemplate.queryForObject(
					"select post_in_thread from zfgbb.message where thread_id = ?", Integer.class,
					unlinked.getThreadId()),
					"first post must be 1-based so pagination can see it");

			List<CmsAdminService.MergeCandidate> candidates = cmsAdminService.getMergeCandidates();
			assertTrue(candidates.stream().noneMatch(c -> "Ocarina_of_Time".equals(c.targetSlug())),
					"corpus-linked articles are already adopted and must not resurface as candidates");
			CmsAdminService.MergeCandidate articleCandidate = candidates.stream()
					.filter(c -> c.sourceType().equals("PROJECT") && c.targetType().equals("WIKI_PAGE")
							&& "ocarina-of-time-3d".equals(c.sourceSlug())
							&& "Ocarina_of_Time_3D".equals(c.targetSlug()))
					.findFirst().orElseThrow(() -> new AssertionError("expected Ocarina of Time 3D wiki candidate"));
			CmsAdminService.MergeCandidate duplicateProject = candidates.stream()
					.filter(c -> c.sourceType().equals("PROJECT") && c.targetType().equals("PROJECT"))
					.findFirst().orElseThrow(() -> new AssertionError("expected duplicate-project candidate"));

			Integer entityPageId = jdbcTemplate.queryForObject(
					"select wiki_page_id from zfgbb.project_view where content_entity_id = ?", Integer.class,
					articleCandidate.sourceId());
			int pagesBeforeAdoption = count("zfgbb.wiki_page");
			cmsAdminService.apply(new CmsAdminService.MergeApplyRequest(
					"PROJECT", articleCandidate.sourceId(), "WIKI_PAGE", articleCandidate.targetId()));
			assertEquals(entityPageId, jdbcTemplate.queryForObject(
					"select wiki_page_id from zfgbb.project_view where content_entity_id = ?", Integer.class,
					articleCandidate.sourceId()),
					"the entity page stays the project's page");
			assertEquals(pagesBeforeAdoption, count("zfgbb.wiki_page"),
					"adoption keeps both pages (article becomes redirect)");
			assertEquals("Ocarina_of_Time_3D", jdbcTemplate.queryForObject(
					"select slug from zfgbb.wiki_page where redirect_to is not null and wiki_page_id = ?",
					String.class, articleCandidate.targetId()),
					"the article should redirect to the entity page");
			String adoptedContentParsed = projectService.getProject("ocarina-of-time-3d").getPage().getContentParsed();
			assertTrue(adoptedContentParsed.contains("Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
					"the project page should now render the adopted article content");
			assertFalse(adoptedContentParsed.contains("Testing It's"),
					"the adopted article must replace the pre-adoption entity content");

			Integer duplicatePageId = jdbcTemplate.queryForObject(
					"select wiki_page_id from zfgbb.project_view where content_entity_id = ?", Integer.class,
					duplicateProject.sourceId());
			cmsAdminService.apply(new CmsAdminService.MergeApplyRequest(
					"PROJECT", duplicateProject.sourceId(), "PROJECT", duplicateProject.targetId()));
			assertEquals(5, count("zfgbb.project"), "duplicate project should be merged away");
			assertEquals(pagesBeforeAdoption, count("zfgbb.wiki_page"),
					"merged project's page becomes a redirect, not a deletion");
			if (duplicatePageId != null) {
				assertNotNull(jdbcTemplate.queryForObject(
						"select redirect_to from zfgbb.wiki_page where wiki_page_id = ?",
						String.class, duplicatePageId),
						"merged project's page should redirect to the surviving project's page");
			}
			assertEquals(0, count("zfgbb.project_screenshot where content_entity_id = " + duplicateProject.sourceId()),
					"source screenshots must be re-pointed");
		} finally {
			RequestContextHolder.resetRequestAttributes();
		}
	}

	@Test
	@Order(2)
	void corpusQuoteStripApplyStripsFaithfulQuotesEndToEnd() {
		assertTrue(quoteStripService.report(null).plannedRows() > 0,
				"the migrated corpus must contain at least one faithful strippable quote");

		UUID runId = UUID.randomUUID();
		QuoteStripOperations.QuoteStripReport planned = quoteStripService.report(runId);
		assertEquals("PLANNED", planned.status());
		assertTrue(planned.plannedRows() > 0, "the migrated corpus must yield at least one strippable row");
		assertTrue(planned.plannedQuotes() > 0, "the plan must count at least one faithful quote to strip");

		QuoteStripApplyResult applied = quoteStripService.apply(runId);
		assertEquals(0, applied.refused());
		assertTrue(applied.applied() > 0, "apply must strip at least one faithful quote in place");
		assertEquals("APPLIED", quoteStripService.report(runId).status());

		Map<String, Object> auditRow = jdbcTemplate.queryForMap(
				"select message_history_id, before_text, after_text from zfgbb.quote_strip_audit"
						+ " where run_id = ? and status = 'APPLIED' order by message_history_id limit 1",
				runId);
		String beforeText = (String) auditRow.get("before_text");
		String afterText = (String) auditRow.get("after_text");
		assertTrue(afterText.length() < beforeText.length(),
				"the stripped body must have lost its embedded quote text");
		assertTrue(EMPTIED_QUOTE_SHELL.matcher(afterText).find(),
				"the emptied [quote msg=N][/quote] shell must remain in the stripped body");
		assertFalse(EMPTIED_QUOTE_SHELL.matcher(beforeText).find(),
				"the planned before-image must still carry the embedded quote text");
		assertEquals(afterText, jdbcTemplate.queryForObject(
				"select message_text from zfgbb.message_history where message_history_id = ?", String.class,
				auditRow.get("message_history_id")),
				"the live message_history body must match the applied after-image");
	}

	@Nested
	class Lease {

		@Test
		void expiredOwnerCanBeTakenOverWithoutAbaCompletion() {
			UUID runId = UUID.randomUUID();
			UUID staleOwner = UUID.randomUUID();
			jdbcTemplate.update("insert into zfgbb.quote_strip_run "
					+ "(run_id, status, lease_owner, lease_expires_ts, heartbeat_ts, attempt_no) "
					+ "values (?, 'APPLYING', ?, current_timestamp - interval '1 second', "
					+ "current_timestamp - interval '1 minute', 4)", runId, staleOwner);

			QuoteStripApplyResult result = quoteStripService.apply(runId);

			assertEquals(0, result.refused());
			assertEquals("APPLIED", jdbcTemplate.queryForObject(
					"select status from zfgbb.quote_strip_run where run_id = ?", String.class, runId));
			assertEquals(5, jdbcTemplate.queryForObject(
					"select attempt_no from zfgbb.quote_strip_run where run_id = ?", Integer.class, runId));
			assertEquals(0, count("zfgbb.quote_strip_run where run_id = '" + runId
					+ "' and lease_owner is not null"));
		}

		@Test
		void liveLeaseRefusesTakeoverAndEvenForcedPurge() {
			UUID runId = UUID.randomUUID();
			UUID owner = UUID.randomUUID();
			jdbcTemplate.update("insert into zfgbb.quote_strip_run "
					+ "(run_id, status, lease_owner, lease_expires_ts, heartbeat_ts, attempt_no) "
					+ "values (?, 'APPLYING', ?, current_timestamp + interval '2 minutes', current_timestamp, 1)",
					runId, owner);

			assertEquals(1, quoteStripService.apply(runId).refused());
			assertTrue(quoteStripService.purge(runId, true).refused());
			assertEquals(owner, jdbcTemplate.queryForObject(
					"select lease_owner from zfgbb.quote_strip_run where run_id = ?", UUID.class, runId));

			jdbcTemplate.update("update zfgbb.quote_strip_run set lease_expires_ts = current_timestamp - interval '1 second' "
					+ "where run_id = ?", runId);
			assertFalse(quoteStripService.purge(runId, true).refused());
			assertEquals(0, count("zfgbb.quote_strip_run where run_id = '" + runId + "'"));
		}
	}
}
