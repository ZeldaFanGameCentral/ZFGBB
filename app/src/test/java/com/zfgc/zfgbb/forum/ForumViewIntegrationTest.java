package com.zfgc.zfgbb.forum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zfgc.zfgbb.dbo.AttachmentBoardViewDbo;
import com.zfgc.zfgbb.dbo.AttachmentBoardViewDboExample;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import com.zfgc.zfgbb.dbo.RecentActivityViewDbo;
import com.zfgc.zfgbb.dbo.RecentActivityViewDboExample;
import com.zfgc.zfgbb.mappers.AttachmentBoardViewDboMapper;
import com.zfgc.zfgbb.mappers.BoardPermissionViewDboMapper;
import com.zfgc.zfgbb.mappers.RecentActivityViewDboMapper;
import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

class ForumViewIntegrationTest extends PostgresIntegrationTest {

	@Autowired
	private RecentActivityViewDboMapper recentActivityMapper;

	@Autowired
	private AttachmentBoardViewDboMapper attachmentBoardMapper;

	@Autowired
	private BoardPermissionViewDboMapper boardPermissionMapper;

	private String adminToken;

	@BeforeEach
	void loginAsAdmin() throws Exception {
		adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
	}

	@Test
	void recentActivityViewPreservesLatestPerThreadBoardFilteringAndTopNOrder() throws Exception {
		int firstThread = postThread(adminToken, "View first " + suffix, "first original");
		postReply(adminToken, firstThread, "first latest");
		int secondThread = postThread(adminToken, "View second " + suffix, "second original");
		postReply(adminToken, secondThread, "second latest");

		Integer firstBoard = boardId(firstThread);
		Integer secondBoard = jdbcTemplate.queryForObject(
				"select board_id from zfgbb.board where board_id <> ? order by board_id limit 1",
				Integer.class, firstBoard);
		jdbcTemplate.update("update zfgbb.thread set board_id = ? where thread_id = ?", secondBoard, secondThread);

		jdbcTemplate.update(
				"update zfgbb.message set created_ts = current_timestamp - interval '4 hours' where thread_id = ?",
				firstThread);
		jdbcTemplate.update("""
				update zfgbb.message set created_ts = current_timestamp - interval '2 hours'
				where message_id = (select max(message_id) from zfgbb.message where thread_id = ?)
				""", firstThread);
		jdbcTemplate.update(
				"update zfgbb.message set created_ts = current_timestamp - interval '3 hours' where thread_id = ?",
				secondThread);
		jdbcTemplate.update("""
				update zfgbb.message set created_ts = current_timestamp - interval '1 hour'
				where message_id = (select max(message_id) from zfgbb.message where thread_id = ?)
				""", secondThread);

		RecentActivityViewDboExample bothBoards = new RecentActivityViewDboExample();
		bothBoards.createCriteria()
				.andThreadIdIn(List.of(firstThread, secondThread))
				.andBoardIdIn(List.of(firstBoard, secondBoard));
		bothBoards.setOrderByClause("last_post_ts desc");
		List<RecentActivityViewDbo> rows = recentActivityMapper.selectByExample(bothBoards);

		assertEquals(List.of(secondThread, firstThread),
				rows.stream().map(RecentActivityViewDbo::getThreadId).toList());
		assertEquals(2, rows.stream().map(RecentActivityViewDbo::getThreadId).distinct().count());
		assertEquals(ADMIN_DISPLAY_NAME, rows.get(0).getLastPoster());
		assertTrue(rows.get(0).getLastPostTs().isAfter(rows.get(1).getLastPostTs()));

		RecentActivityViewDboExample oneBoard = new RecentActivityViewDboExample();
		oneBoard.createCriteria()
				.andThreadIdIn(List.of(firstThread, secondThread))
				.andBoardIdEqualTo(firstBoard);
		assertEquals(List.of(firstThread), recentActivityMapper.selectByExample(oneBoard).stream()
				.map(RecentActivityViewDbo::getThreadId).toList());

		bothBoards.setLimit(1);
		bothBoards.setOffset(0);
		assertEquals(List.of(secondThread), recentActivityMapper.selectByExampleWithLimits(bothBoards).stream()
				.map(RecentActivityViewDbo::getThreadId).toList());
	}

	@Test
	void attachmentBoardViewResolvesTheAttachmentsThreadBoard() throws Exception {
		int threadId = postThread(adminToken, "Attachment view " + suffix);
		Integer messageId = jdbcTemplate.queryForObject(
				"select max(message_id) from zfgbb.message where thread_id = ?", Integer.class, threadId);
		Integer resourceId = jdbcTemplate.queryForObject("""
				insert into zfgbb.content_resource
					(content_type_id, uploaded_user_id, filename, checksum, file_ext, mime_type)
				values (2, ?, ?, 'view-test', 'txt', 'text/plain')
				returning content_resource_id
				""", Integer.class, userIdOf(ADMIN_USER), "view-" + suffix + ".txt");
		jdbcTemplate.update("insert into zfgbb.file_attachments (content_resource_id, message_id) values (?, ?)",
				resourceId, messageId);
		assertEquals(0, jdbcTemplate.queryForObject(
				"select downloads from zfgbb.file_attachments where content_resource_id = ?",
				Integer.class, resourceId));

		AttachmentBoardViewDboExample example = new AttachmentBoardViewDboExample();
		example.createCriteria().andContentResourceIdEqualTo(resourceId);
		List<AttachmentBoardViewDbo> rows = attachmentBoardMapper.selectByExample(example);

		assertEquals(1, rows.size());
		assertEquals(boardId(threadId), rows.get(0).getBoardId());
	}

	@Test
	void generatedPermissionQueryMatchesTheOldRootAndChildUnionSet() {
		List<Integer> permissionIds = jdbcTemplate.queryForList(
				"select distinct permission_id from zfgbb.br_board_permission order by permission_id", Integer.class);
		String placeholders = String.join(",", permissionIds.stream().map(ignored -> "?").toList());
		List<Object> args = new ArrayList<>(permissionIds);
		Set<Integer> oldUnion = new LinkedHashSet<>(jdbcTemplate.queryForList("""
				select b.board_id
				from zfgbb.board b
				join zfgbb.br_board_permission bp on bp.board_id = b.board_id
				where b.parent_board_id is null and bp.permission_id in (%s)
				union
				select b.board_id
				from zfgbb.board b
				join zfgbb.br_board_permission bp on bp.board_id = b.board_id
				where b.parent_board_id is not null and bp.permission_id in (%s)
				""".formatted(placeholders, placeholders), Integer.class,
				concat(args, args).toArray()));

		BoardPermissionViewDboExample example = new BoardPermissionViewDboExample();
		example.createCriteria().andPermissionIdIn(permissionIds);
		List<Integer> rawBoardIds = boardPermissionMapper.selectByExample(example).stream()
				.map(BoardPermissionViewDbo::getBoardId).toList();
		Set<Integer> generatedDistinct = new LinkedHashSet<>(rawBoardIds);

		assertEquals(oldUnion, generatedDistinct);
		assertTrue(rawBoardIds.size() >= generatedDistinct.size());
		assertFalse(generatedDistinct.isEmpty());
	}

	@Test
	void generatedViewColumnsMatchTheFlywayContracts() {
		assertEquals(List.of("thread_id", "thread_name", "board_id", "board_name", "last_poster",
				"last_poster_id", "last_post_ts"), viewColumns("recent_activity_view"));
		assertEquals(List.of("board_id", "content_resource_id"), viewColumns("attachment_board_view"));
	}

	private Integer boardId(int threadId) {
		return jdbcTemplate.queryForObject("select board_id from zfgbb.thread where thread_id = ?", Integer.class,
				threadId);
	}

	private List<String> viewColumns(String viewName) {
		return jdbcTemplate.queryForList("""
				select column_name
				from information_schema.columns
				where table_schema = 'zfgbb' and table_name = ?
				order by ordinal_position
				""", String.class, viewName);
	}

	private static List<Object> concat(List<Object> first, List<Object> second) {
		List<Object> result = new ArrayList<>(first.size() + second.size());
		result.addAll(first);
		result.addAll(second);
		return result;
	}
}
