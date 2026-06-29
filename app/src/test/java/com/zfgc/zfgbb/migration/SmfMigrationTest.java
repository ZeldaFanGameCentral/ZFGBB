package com.zfgc.zfgbb.migration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.zfgc.zfgbb.migrator.jobs.Job;
import com.zfgc.zfgbb.migrator.jobs.JobState;
import com.zfgc.zfgbb.migrator.jobs.JobType;

@Order(1)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SmfMigrationTest extends MigrationE2E {

	private static final Map<Pattern, String> REF_SOURCES = Map.of(
			Pattern.compile("\\[attach=(\\d+)\\]"), "select file_attachment_id from zfgbb.file_attachments",
			Pattern.compile("\\[thread=(\\d+)(?:\\s|\\])"), "select thread_id from zfgbb.thread",
			Pattern.compile("\\[board=(\\d+)\\]"), "select board_id from zfgbb.board",
			Pattern.compile("\\[member=(\\d+)\\]"), "select user_id from zfgbb.\"user\"",
			Pattern.compile("msg=(\\d+)"), "select message_id from zfgbb.message");

	@Test
	@Order(1)
	void pipelineMatchesEverySourceCount() {
		assertEquals(JobType.SMF_INSTALLATION_PIPELINE.size(), smfJobs.size(),
				"Pipeline should submit one job per converter type");

		assertSameCount(smf, "smf_1members", "zfgbb.\"user\" where user_id <> 0");
		assertSameCount(smf, "smf_1categories", "zfgbb.category");
		assertSameCount(smf, "smf_1boards", "zfgbb.board");
		assertEquals(1, count("zfgbb.br_board_permission bp "
				+ "join zfgbb.permission p on p.permission_id = bp.permission_id "
				+ "join zfgbb.migrator_id_map m on m.zfgbb_id = bp.board_id "
				+ "where m.entity_type = 'BOARD' and m.legacy_id = 1 and p.permission_code = 'ZFGC_WIKI_MODERATOR'"),
				"BoardConverter honors the operator group->permission map: SMF group 9 -> ZFGC_WIKI_MODERATOR on board 1");
		assertEquals(1, count("zfgbb.br_board_permission bp "
				+ "join zfgbb.permission p on p.permission_id = bp.permission_id "
				+ "join zfgbb.migrator_id_map m on m.zfgbb_id = bp.board_id "
				+ "where m.entity_type = 'BOARD' and m.legacy_id = 1 and p.permission_code = 'ZFGC_GUEST'"),
				"reserved SMF guest group (-1) maps to ZFGC_GUEST on the migrated board");
		assertSourceMatchesTarget(smf, "select count(*) from smf_1topics",
				"select " + forumCountsAfterSmf.get("thread"));
		assertSourceMatchesTarget(smf, "select count(*) from smf_1messages",
				"select " + forumCountsAfterSmf.get("message"));
		assertSameCount(smf, "smf_1members", "zfgbb.user_bio_info");
		assertSameCount(smf, "smf_1members", "zfgbb.user_contact_info");
		assertSameCount(smf, "smf_1members", "zfgbb.email_address");
		assertSameCount(smf, "smf_1polls", "zfgbb.poll");
		assertSameCount(smf, "smf_1poll_choices", "zfgbb.poll_choice");
		assertSameCount(smf, "smf_1log_karma", "zfgbb.reaction where reactable_type = 'MESSAGE'");
		assertEquals(5, count("zfgbb.reaction_type"), "the reaction vocabulary is seeded");
		assertSourceMatchesTarget(smf, "select count(*) from smf_1log_karma where action = 1",
				"select count(*) from zfgbb.reaction r join zfgbb.reaction_type t on t.reaction_type_id = r.reaction_type_id "
						+ "where t.code = 'LIKE'");
		assertEquals(3, count("zfgbb.award"), "the award catalog is seeded");
		assertSameCount(smf, "smf_1attachments where id_msg > 0", "zfgbb.file_attachments");
		assertSameCount(smf,
				"smf_1members m where m.avatar != '' or exists "
						+ "(select 1 from smf_1attachments a where a.id_member = m.id_member and a.id_msg = 0)",
				"zfgbb.avatar");
		assertSameCount(smf, "smf_1membergroups", "zfgbb.permission_group");
		assertSameCount(smf, "smf_1personal_messages", "zfgbb.personal_message");
		assertSameCount(smf, "smf_1pm_recipients", "zfgbb.personal_message_recipient");
		assertSameCount(smf, "(select distinct id_pm_head from smf_1personal_messages) heads",
				"zfgbb.personal_message_conversation");
		assertSameCount(smf, "smf_1log_notify", "zfgbb.notification_subscription");
		assertSameCount(smf, "smf_1log_comments where comment_type = 'warning'", "zfgbb.user_warning");
		assertSourceMatchesTarget(smf,
				"select (select count(*) from smf_1log_actions where action != 'delete_member') "
						+ "+ (select count(*) from smf_1log_comments where comment_type = 'warning')",
				"select count(*) from zfgbb.moderation_log");
		assertSameCount(smf, "smf_1log_comments where comment_type = 'warning'",
				"zfgbb.moderation_log where action = 'WARN'");
		assertSourceMatchesTarget(smf,
				"select count(*) from (select poster_ip from smf_1messages union "
						+ "select postIP from smf_1game_comments union "
						+ "select postIP from smf_1resource_comments) ips",
				"select count(*) from zfgbb.ip_address");
		assertSourceMatchesTarget(smf,
				"select (select count(*) from smf_1messages) + (select count(*) from smf_1messages_history)",
				"select " + forumCountsAfterSmf.get("message_history"));
		assertSourceMatchesTarget(smf,
				"select count(*) from smf_1log_polls where id_member != 0",
				"select count(*) from zfgbb.user_poll_choice");

		assertNoOrphanRewrittenBbcodes();
		assertAllMigratedAttachmentsPresent();
	}

	@Test
	@Order(4)
	void socialDataCarriesStateAndRelationships() {
		assertEquals(2, count("zfgbb.personal_message where deleted_by_sender"),
				"the sender-deleted PMs keep their mailbox state");
		assertEquals(1, count("zfgbb.personal_message_recipient where bcc"),
				"the BCC recipient survives with the flag");
		assertEquals(3, count("zfgbb.personal_message_recipient where read_flag"),
				"read state migrates");
		assertEquals(1, count("zfgbb.personal_message_recipient where deleted_flag"),
				"recipient-side deletes migrate");
		assertEquals(2, count("zfgbb.personal_message m join zfgbb.personal_message_conversation c "
				+ "on c.personal_message_conversation_id = m.personal_message_conversation_id "
				+ "where c.subject = 'kamehameha'"),
				"the reply chain lands in one conversation without Re: prefixes");
		assertEquals(2, count("zfgbb.notification_subscription where board_id is not null"),
				"board watches migrate");
		assertEquals(3, count("zfgbb.notification_subscription where thread_id is not null"),
				"topic watches migrate");
		assertEquals(1, count("zfgbb.user_warning where points = 20"),
				"a warning carries its points on the per-warning ledger");
		assertEquals(0, count("zfgbb.moderation_log where action = 'DELETE_MEMBER'"),
				"spam-purge noise is not migrated");
		assertTrue(count("zfgbb.moderation_log where action = 'LOCK_THREAD' and thread_id is not null") >= 1,
				"mod actions keep their remapped thread references");
		assertEquals(2, count("zfgbb.permission_group where color is not null"),
				"group colors migrate");
		assertTrue(count("zfgbb.user_permission_group_assoc") >= 4,
				"primary, post, and additional group memberships all land");
		assertTrue(count("zfgbb.permission_group_assoc") > 0,
				"groups grant permissions via permission_group_assoc");
		assertEquals(1, jdbcTemplate.queryForObject(
				"select count(*) from zfgbb.br_user_permission bup "
						+ "join zfgbb.permission p on p.permission_id = bup.user_permission_id "
						+ "join zfgbb.migrator_id_map m on m.zfgbb_id = bup.user_id "
						+ "where m.entity_type = 'USER' and m.legacy_id = 3 and p.permission_code = 'ZFGC_WIKI_MODERATOR'",
				Integer.class),
				"the wiki-maintainer group expands into the enforced br_user_permission");
	}

	@Test
	@Order(2)
	void rerunningAttachmentsDoesNotDoubleRewrite() throws Exception {
		List<String> beforeBodies = attachBodies();
		int markersBefore = count("zfgbb.migrator_attachment_ref_rewrites");

		Job rerun = jobService.submit(JobType.ATTACHMENTS, params()).get(0);
		Job finished = waitForAllTerminal(List.of(rerun), Duration.ofMinutes(2)).get(0);
		assertEquals(JobState.COMPLETED, finished.getState(),
				"re-running ATTACHMENTS should COMPLETE; got " + finished.getState()
						+ " (error=" + finished.getError() + ")");

		assertEquals(beforeBodies, attachBodies(), "[attach=N] bodies must be unchanged on re-run");
		assertEquals(markersBefore, count("zfgbb.migrator_attachment_ref_rewrites"),
				"re-run should not add new rewrite markers");
	}

	private List<String> attachBodies() {
		return jdbcTemplate.queryForList(
				"select message_text from zfgbb.message_history where message_text like '%[attach=%' order by message_history_id",
				String.class);
	}

	private void assertNoOrphanRewrittenBbcodes() {
		List<String> bodies = jdbcTemplate.queryForList(
				"select message_text from zfgbb.message_history", String.class);
		REF_SOURCES.forEach((pattern, validIdsQuery) -> {
			Set<Integer> validIds = new HashSet<>(jdbcTemplate.queryForList(validIdsQuery, Integer.class));
			for (String body : bodies) {
				Matcher m = pattern.matcher(body);
				while (m.find()) {
					int id = Integer.parseInt(m.group(1));
					assertTrue(validIds.contains(id),
							pattern + " ref " + id + " does not resolve via `" + validIdsQuery + "`: " + body);
				}
			}
		});
	}

	private void assertAllMigratedAttachmentsPresent() {
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(
				"select cr.content_resource_id, cr.storage_dir, cr.filename from zfgbb.content_resource cr "
						+ "join zfgbb.file_attachments fa on fa.content_resource_id = cr.content_resource_id");
		for (Map<String, Object> row : rows) {
			assertEquals("forum/attachments", row.get("storage_dir"),
					"attachments must be organized under forum/attachments: " + row);
			assertTrue(Files.exists(contentTarget.resolve((String) row.get("storage_dir"))
					.resolve(String.valueOf(row.get("content_resource_id")))
					.resolve((String) row.get("filename"))),
					"attachment should keep its original filename on disk: " + row);
		}
	}
}
