package com.zfgc.zfgbb.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

import tools.jackson.databind.JsonNode;

class AccountDeletionDeepWipeTest extends PostgresIntegrationTest {

	private static final String PASSWORD = "password123";
	private static final Path CONTENT_ROOT = createContentRoot();

	@DynamicPropertySource
	static void accountDeletionProperties(DynamicPropertyRegistry r) {
		r.add("zfgbb.auth.lockout.failed-attempts", () -> "3");
		r.add("zfgbb.content.path", () -> CONTENT_ROOT.toString());
		r.add("zfgbb.content.images", () -> "images");
	}

	@BeforeEach
	void clearMail() {
		mailDispatcher.clear();
	}

	@Test
	void fullWipeDeletesContentPiiBlobsAndPassesFkCensus() throws Exception {
		String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
		Integer adminId = userIdOf(ADMIN_USER);
		String anchorThreadName = "Anchor " + suffix;
		postThread(adminToken, anchorThreadName);

		String userName = "wipeme_" + suffix;
		register(userName, PASSWORD);
		JsonNode loginJson = login(userName, PASSWORD);
		String accessToken = loginJson.get("accessToken").asString();
		String originalRefreshToken = loginJson.get("refreshToken").asString();
		Integer userId = userIdOf(userName);

		int threadId = postThread(accessToken, "Wipe my thread " + suffix);
		postReply(accessToken, threadId);
		Integer replyMessageId = jdbcTemplate.queryForObject(
				"select max(message_id) from zfgbb.message where thread_id = ? and owner_id = ?",
				Integer.class, threadId, userId);

		Integer attachmentResourceId = insertContentResource("ATC", userId, "evidence.txt", "txt", "text/plain");
		Path attachmentBlob = writeBlobFor(attachmentResourceId);
		jdbcTemplate.update(
				"insert into zfgbb.file_attachments (message_id, active_flag, content_resource_id) values (?, true, ?)",
				replyMessageId, attachmentResourceId);

		Integer avatarResourceId = insertContentResource("AVR", userId, "avatar.png", "png", "image/png");
		Path avatarBlob = writeBlobFor(avatarResourceId);
		Integer avatarId = jdbcTemplate.queryForObject(
				"insert into zfgbb.avatar (active_flag, content_resource_id) values (true, ?) returning avatar_id",
				Integer.class, avatarResourceId);
		jdbcTemplate.update(
				"""
						insert into zfgbb.user_bio_info (user_id, real_name, birth_date, location, signature, personal_text, custom_title, avatar_id)
						values (?, 'Real Name', date '1990-01-02', 'Hyrule', 'my signature', 'personal text', 'custom title', ?)
						on conflict (user_id) do update set real_name = excluded.real_name, birth_date = excluded.birth_date,
							location = excluded.location, signature = excluded.signature, personal_text = excluded.personal_text,
							custom_title = excluded.custom_title, avatar_id = excluded.avatar_id
						""",
				userId, avatarId);

		Integer wikiPageResourceId = insertContentResource("IMG", userId, "page-image.png", "png", "image/png");
		Path wikiPageBlob = writeBlobFor(wikiPageResourceId);
		Integer ownedWikiPageId = jdbcTemplate.queryForObject("""
				insert into zfgbb.wiki_page (namespace, title, slug, created_user_id, content_resource_id)
				values ('MAIN', ?, ?, ?, ?) returning wiki_page_id
				""", Integer.class, "Wipe Page " + suffix, "wipe-page-" + suffix, userId, wikiPageResourceId);
		jdbcTemplate.update(
				"""
						insert into zfgbb.wiki_page_revision (wiki_page_id, content, author_user_id, author_name, current_flag, status)
						values (?, 'subject content', ?, ?, false, 'APPROVED')
						""",
				ownedWikiPageId, userId, userName);
		jdbcTemplate.update(
				"""
						insert into zfgbb.wiki_page_revision (wiki_page_id, content, author_user_id, author_name, current_flag, status)
						values (?, 'admin content', ?, 'Test Admin', true, 'APPROVED')
						""",
				ownedWikiPageId, adminId);
		jdbcTemplate.update("insert into zfgbb.wiki_page_category (wiki_page_id, category_name) values (?, ?)",
				ownedWikiPageId, "WipeCategory" + suffix);
		jdbcTemplate.update(
				"insert into zfgbb.reaction (reactable_type, reactable_id, reactor_user_id, reaction_type_id) values ('WIKI_PAGE', ?, ?, 1)",
				ownedWikiPageId, adminId);
		jdbcTemplate.update(
				"insert into zfgbb.migrator_id_map (entity_type, legacy_id, zfgbb_id) values ('WIKI_PAGE', 91001, ?)",
				ownedWikiPageId);
		Integer retainedEntityId = jdbcTemplate.queryForObject("""
				insert into zfgbb.content_entity (entity_type, title, slug, created_user_id, wiki_page_id)
				values ('PROJECT', ?, ?, ?, ?) returning content_entity_id
				""", Integer.class, "Retained " + suffix, "retained-" + suffix, adminId, ownedWikiPageId);

		Integer templatePageId = jdbcTemplate.queryForObject("""
				insert into zfgbb.wiki_page (namespace, title, slug, created_user_id)
				values ('MAIN', ?, ?, ?) returning wiki_page_id
				""", Integer.class, "Template Page " + suffix, "template-page-" + suffix, userId);
		jdbcTemplate.update(
				"insert into zfgbb.content_template (code, body, wiki_page_id) values (?, 'template body', ?)",
				"tpl_" + suffix, templatePageId);

		Integer revisedOnlyPageId = jdbcTemplate.queryForObject("""
				insert into zfgbb.wiki_page (namespace, title, slug, created_user_id)
				values ('MAIN', ?, ?, ?) returning wiki_page_id
				""", Integer.class, "Revised Page " + suffix, "revised-page-" + suffix, adminId);
		Integer subjectRevisionId = jdbcTemplate.queryForObject(
				"""
						insert into zfgbb.wiki_page_revision (wiki_page_id, content, author_user_id, author_name, current_flag, status)
						values (?, 'revision by subject', ?, ?, true, 'APPROVED') returning wiki_page_revision_id
						""",
				Integer.class, revisedOnlyPageId, userId, userName);

		Integer previewResourceId = insertContentResource("IMG", userId, "preview.png", "png", "image/png");
		Path previewBlob = writeBlobFor(previewResourceId);
		Integer screenshotResourceId = insertContentResource("IMG", userId, "shot.png", "png", "image/png");
		Path screenshotBlob = writeBlobFor(screenshotResourceId);
		Integer downloadResourceId = insertContentResource("DL", userId, "demo.zip", "zip", "application/zip");
		Path downloadBlob = writeBlobFor(downloadResourceId);
		Integer ownedProjectId = jdbcTemplate.queryForObject(
				"""
						insert into zfgbb.content_entity (entity_type, title, slug, created_user_id, author_name, wiki_page_id, preview_content_resource_id)
						values ('PROJECT', ?, ?, ?, ?, ?, ?) returning content_entity_id
						""",
				Integer.class, "Wipe Project " + suffix, "wipe-project-" + suffix, userId, userName,
				ownedWikiPageId, previewResourceId);
		jdbcTemplate.update("insert into zfgbb.project (content_entity_id) values (?)", ownedProjectId);
		jdbcTemplate.update(
				"insert into zfgbb.project_screenshot (content_entity_id, content_resource_id) values (?, ?)",
				ownedProjectId, screenshotResourceId);
		jdbcTemplate.update(
				"insert into zfgbb.project_download (content_entity_id, content_resource_id) values (?, ?)",
				ownedProjectId, downloadResourceId);
		Integer collectionId = jdbcTemplate.queryForObject(
				"insert into zfgbb.content_collection (code, title) values (?, 'Featured') returning content_collection_id",
				Integer.class, "featured_" + suffix);
		jdbcTemplate.update(
				"insert into zfgbb.content_collection_item (content_collection_id, content_entity_id) values (?, ?)",
				collectionId, ownedProjectId);
		jdbcTemplate.update(
				"insert into zfgbb.reaction (reactable_type, reactable_id, reactor_user_id, reaction_type_id) values ('PROJECT', ?, ?, 1)",
				ownedProjectId, adminId);
		jdbcTemplate.update(
				"insert into zfgbb.migrator_id_map (entity_type, legacy_id, zfgbb_id) values ('PROJECT', 91002, ?)",
				ownedProjectId);

		Integer resourceDownloadResourceId = insertContentResource("DL", userId, "asset.zip", "zip", "application/zip");
		Path resourceDownloadBlob = writeBlobFor(resourceDownloadResourceId);
		Integer ownedResourceId = jdbcTemplate.queryForObject("""
				insert into zfgbb.content_entity (entity_type, title, slug, created_user_id, author_name)
				values ('RESOURCE', ?, ?, ?, ?) returning content_entity_id
				""", Integer.class, "Wipe Resource " + suffix, "wipe-resource-" + suffix, userId, userName);
		jdbcTemplate.update(
				"insert into zfgbb.resource (content_entity_id, download_content_resource_id) values (?, ?)",
				ownedResourceId, resourceDownloadResourceId);
		jdbcTemplate.update(
				"insert into zfgbb.reaction (reactable_type, reactable_id, reactor_user_id, reaction_type_id) values ('RESOURCE', ?, ?, 1)",
				ownedResourceId, adminId);

		Integer anchorMessageId = jdbcTemplate.queryForObject(
				"select max(message_id) from zfgbb.message where owner_id = ?", Integer.class, adminId);
		jdbcTemplate.update("""
				insert into zfgbb.reaction (reactable_type, reactable_id, reactor_user_id, reaction_type_id, comment)
				values ('MESSAGE', ?, ?, 1, 'self-identifying comment')
				""", anchorMessageId, userId);
		jdbcTemplate.update(
				"insert into zfgbb.reaction (reactable_type, reactable_id, reactor_user_id, reaction_type_id) values ('MESSAGE', ?, ?, 2)",
				replyMessageId, adminId);

		Integer ownedEmptyThreadId = jdbcTemplate.queryForObject(
				"insert into zfgbb.thread (thread_name, board_id, created_user_id) values (?, 1, ?) returning thread_id",
				Integer.class, "Owned empty thread " + suffix, userId);
		Integer unrelatedThreadId = jdbcTemplate.queryForObject(
				"insert into zfgbb.thread (thread_name, board_id, created_user_id) values (?, 1, ?) returning thread_id",
				Integer.class, "Unrelated empty thread " + suffix, adminId);
		Integer unrelatedPollId = jdbcTemplate.queryForObject(
				"insert into zfgbb.poll (poll_question, thread_id, created_user_id) values ('Survives?', ?, ?) returning poll_id",
				Integer.class, unrelatedThreadId, adminId);
		Integer unrelatedPollChoiceId = jdbcTemplate.queryForObject(
				"insert into zfgbb.poll_choice (poll_id, choice_text, active_flag, votes) values (?, 'Yes', true, 1) returning poll_choice_id",
				Integer.class, unrelatedPollId);
		jdbcTemplate.execute("""
				select setval(pg_get_serial_sequence('zfgbb.user_poll_choice', 'user_poll_choice_id'),
				  (select coalesce(max(user_poll_choice_id), 1) from zfgbb.user_poll_choice))
				""");
		jdbcTemplate.update("insert into zfgbb.user_poll_choice (user_id, poll_choice_id) values (?, ?)",
				adminId, unrelatedPollChoiceId);
		jdbcTemplate.update("insert into zfgbb.notification_subscription (user_id, thread_id) values (?, ?)",
				adminId, unrelatedThreadId);
		Integer unrelatedConversationId = jdbcTemplate.queryForObject("""
				insert into zfgbb.personal_message_conversation (subject, started_ts)
				values (?, current_timestamp) returning personal_message_conversation_id
				""", Integer.class, "Unrelated empty conversation " + suffix);

		String rotatedRefreshToken = refreshExpectingOk(originalRefreshToken);

		assertEquals(1, count("zfgbb.board_summary where board_id = 1 and latest_message_owner_id = " + userId),
				"the subject's reply must be the board's latest post before deletion");
		JsonNode boardBefore = boardNodeOf(fetchForum(adminToken), 1);
		assertEquals(userName, boardBefore.path("latestMessageUserName").asText(),
				"the forum cache must show the subject as the latest poster before deletion");

		mockMvc.perform(post("/users/account/delete/preview")
				.header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.messageCount").value(2))
				.andExpect(jsonPath("$.threadCount").value(2))
				.andExpect(jsonPath("$.contentResourceCount").value(7))
				.andExpect(jsonPath("$.wikiPageCount").value(2))
				.andExpect(jsonPath("$.projectCount").value(1))
				.andExpect(jsonPath("$.resourceCount").value(1))
				.andExpect(jsonPath("$.adminReplacementRequired").value(false));

		mockMvc.perform(post("/users/account/delete")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(deletionBody("WIPE", PASSWORD, userName)))
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.status").value("PENDING"));
		String confirmationToken = lastConfirmationToken();

		assertEquals(1, count("zfgbb.\"user\" where user_id = " + userId + " and active_flag = true"),
				"the account must stay untouched until the emailed link is used");
		assertEquals(2, count("zfgbb.message where owner_id = " + userId),
				"authored content must stay untouched until the emailed link is used");
		assertTrue(Files.exists(attachmentBlob), "the attachment blob must survive until confirmation");

		confirmAccountDeletion(confirmationToken, "10.99.1.1")
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("COMPLETED"));

		assertEquals(0, count("zfgbb.\"user\" where user_id = " + userId));
		assertEquals(0, count("zfgbb.message where owner_id = " + userId));
		assertEquals(0, count("zfgbb.thread where thread_id = " + threadId));
		assertEquals(0, count("zfgbb.file_attachments where content_resource_id = " + attachmentResourceId
				+ " or message_id = " + replyMessageId));
		assertEquals(0, count("zfgbb.content_resource where uploaded_user_id = " + userId));
		assertEquals(0, count("zfgbb.avatar where avatar_id = " + avatarId));
		assertEquals(0, count("zfgbb.user_bio_info where user_id = " + userId));
		assertEquals(0, count("zfgbb.user_contact_info where user_id = " + userId));
		assertEquals(0,
				count("zfgbb.email_address where email_address = '" + userName + "@fake-email.fake.tld.thing'"));
		assertFalse(Files.exists(attachmentBlob), "the attachment blob must be swept from disk");
		assertFalse(Files.exists(avatarBlob), "the avatar blob must be swept from disk");

		assertEquals(0, count("zfgbb.wiki_page where wiki_page_id = " + ownedWikiPageId),
				"an owned wiki page must be hard-deleted at page granularity");
		assertEquals(0, count("zfgbb.wiki_page_revision where wiki_page_id = " + ownedWikiPageId),
				"every author's revision on an owned page must go with the page");
		assertEquals(0, count("zfgbb.wiki_page_category where wiki_page_id = " + ownedWikiPageId));
		assertEquals(0,
				count("zfgbb.migrator_id_map where entity_type = 'WIKI_PAGE' and zfgbb_id = " + ownedWikiPageId));
		assertEquals(0,
				count("zfgbb.content_entity where content_entity_id in (" + ownedProjectId + ", " + ownedResourceId
						+ ")"),
				"owned projects and resources must be hard-deleted");
		assertEquals(0, count("zfgbb.project where content_entity_id = " + ownedProjectId));
		assertEquals(0, count("zfgbb.project_screenshot where content_entity_id = " + ownedProjectId));
		assertEquals(0, count("zfgbb.project_download where content_entity_id = " + ownedProjectId));
		assertEquals(0, count("zfgbb.resource where content_entity_id = " + ownedResourceId));
		assertEquals(0, count("zfgbb.content_collection_item where content_entity_id = " + ownedProjectId),
				"curated collection links to a deleted entity must be cleaned up");
		assertEquals(1, count("zfgbb.content_collection where content_collection_id = " + collectionId),
				"the collection itself is retained");
		assertEquals(0, count("zfgbb.migrator_id_map where entity_type = 'PROJECT' and zfgbb_id = " + ownedProjectId));
		assertEquals(1,
				count("zfgbb.content_entity where content_entity_id = " + retainedEntityId
						+ " and wiki_page_id is null"),
				"a retained entity linking a deleted page must have its wiki_page_id nulled");
		assertEquals(1,
				count("zfgbb.wiki_page where wiki_page_id = " + templatePageId + " and created_user_id is null"),
				"a template-linked owned page must be retained anonymized, never deleted");
		assertEquals(1, count("zfgbb.content_template where wiki_page_id = " + templatePageId),
				"the template link must never be silently nulled");
		assertEquals(1, count("zfgbb.moderation_log where action = 'ACCOUNT_DELETION_TEMPLATE_LINKED_WIKI_PAGE'"
				+ " and detail like 'wiki_page_id=" + templatePageId + " %'"),
				"the retained template-linked page must surface an operator-remediation outcome");
		assertEquals(1, count("zfgbb.wiki_page where wiki_page_id = " + revisedOnlyPageId),
				"a page the subject only revised must be retained");
		assertEquals(1, count("zfgbb.wiki_page_revision where wiki_page_revision_id = " + subjectRevisionId
				+ " and author_user_id is null and author_name = '[deleted]'"),
				"the subject's revision on a retained page must be kept with attribution scrubbed");
		assertEquals(1, count("zfgbb.reaction where reactable_type = 'MESSAGE' and reactable_id = " + anchorMessageId
				+ " and reactor_user_id is null and comment is null"),
				"a reaction the subject gave on retained content must be kept with reactor and comment scrubbed");
		assertFalse(Files.exists(wikiPageBlob), "the owned wiki page blob must be swept from disk");
		assertFalse(Files.exists(previewBlob), "the project preview blob must be swept from disk");
		assertFalse(Files.exists(screenshotBlob), "the project screenshot blob must be swept from disk");
		assertFalse(Files.exists(downloadBlob), "the project download blob must be swept from disk");
		assertFalse(Files.exists(resourceDownloadBlob), "the resource download blob must be swept from disk");

		assertEquals(0, count("zfgbb.thread where thread_id = " + ownedEmptyThreadId),
				"the subject's own empty thread must be garbage-collected");
		assertEquals(1, count("zfgbb.thread where thread_id = " + unrelatedThreadId),
				"another user's empty thread must survive the scoped GC");
		assertEquals(1, count("zfgbb.poll where poll_id = " + unrelatedPollId),
				"another user's poll on an unrelated empty thread must survive");
		assertEquals(1, count("zfgbb.user_poll_choice where poll_choice_id = " + unrelatedPollChoiceId
				+ " and user_id = " + adminId),
				"another user's poll vote must survive");
		assertEquals(1, count("zfgbb.notification_subscription where thread_id = " + unrelatedThreadId
				+ " and user_id = " + adminId),
				"another user's thread watch must survive");
		assertEquals(1, count("zfgbb.personal_message_conversation where personal_message_conversation_id = "
				+ unrelatedConversationId),
				"an unrelated empty conversation must survive the scoped GC");

		Map<String, Object> boardSummary = jdbcTemplate.queryForMap(
				"select latest_message_owner_id, thread_name, latest_message_user_name from zfgbb.board_summary where board_id = 1");
		assertEquals(adminId, boardSummary.get("latest_message_owner_id"),
				"board_summary must fall back to the prior latest poster after the wipe");
		assertEquals(anchorThreadName, boardSummary.get("thread_name"));
		JsonNode boardAfter = boardNodeOf(fetchForum(adminToken), 1);
		assertEquals(anchorThreadName, boardAfter.path("threadName").asText(),
				"the forum cache must be evicted so the index shows the surviving latest thread");
		assertEquals("Test Admin", boardAfter.path("latestMessageUserName").asText());

		assertNoRowReferencesUser(userId);
		assertEquals(0, count("zfgbb.reaction r where r.reactable_type = 'MESSAGE'"
				+ " and not exists (select 1 from zfgbb.message m where m.message_id = r.reactable_id)"));
		assertEquals(0, count("zfgbb.reaction r where r.reactable_type = 'WIKI_PAGE'"
				+ " and not exists (select 1 from zfgbb.wiki_page wp where wp.wiki_page_id = r.reactable_id)"));
		assertEquals(0, count("zfgbb.reaction r where r.reactable_type in ('PROJECT', 'RESOURCE')"
				+ " and not exists (select 1 from zfgbb.content_entity ce"
				+ " where ce.content_entity_id = r.reactable_id and ce.entity_type = r.reactable_type)"));
		assertEquals(0, count("zfgbb.migrator_id_map where entity_type = 'USER' and zfgbb_id = " + userId));
		assertEquals(1, count("zfgbb.account_deletion_request where user_id = " + userId
				+ " and status = 'COMPLETED' and purge_cursor = 'COMPLETED' and recorded_blob_paths is null"));
		assertEquals(1, count("zfgbb.account_deletion_audit where subject_user_id_snapshot = " + userId
				+ " and mode = 'PURGE' and initiated_by = 'SELF' and confirmed_ts is not null and executed_ts is not null"));

		assertPostDeletionTeardown(userName, PASSWORD, accessToken, originalRefreshToken, rotatedRefreshToken);
	}

	@Test
	void repeatedWrongPasswordsLockTheAccount() throws Exception {
		String userName = "lockme_" + suffix;
		register(userName, PASSWORD);
		String accessToken = login(userName, PASSWORD).get("accessToken").asString();
		Integer userId = userIdOf(userName);

		for (int attempt = 0; attempt < 3; attempt++)
			mockMvc.perform(post("/users/account/delete")
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(deletionBody("WIPE", "wrongpassword", userName)))
					.andExpect(status().isBadRequest());

		assertEquals(1, count("zfgbb.\"user\" where user_id = " + userId
				+ " and failed_login_count >= 3 and locked_until_ts is not null"),
				"wrong-password deletion requests must lock the account at the login threshold");

		mockMvc.perform(post("/users/account/delete")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(deletionBody("WIPE", PASSWORD, userName)))
				.andExpect(status().isBadRequest());
		assertEquals(0, count("zfgbb.account_deletion_request where user_id = " + userId),
				"a locked account must not be able to create a deletion request even with the right password");

		mockMvc.perform(post("/users/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\": \"" + userName + "\", \"password\": \"" + PASSWORD
						+ "\", \"useTokens\": true}"))
				.andExpect(status().isUnauthorized());
	}

	private void assertNoRowReferencesUser(Integer userId) {
		List<Map<String, Object>> referrers = jdbcTemplate.queryForList("""
				select c.conrelid::regclass::text as referrer_table, a.attname as referrer_column
				from pg_constraint c
				join pg_attribute a on a.attrelid = c.conrelid and a.attnum = any(c.conkey)
				where c.contype = 'f' and c.confrelid = 'zfgbb."user"'::regclass
				""");
		assertTrue(referrers.size() >= 15, "the live FK census must enumerate the user-referencing columns");
		for (Map<String, Object> referrer : referrers) {
			String referrerTable = (String) referrer.get("referrer_table");
			String referrerColumn = (String) referrer.get("referrer_column");
			assertEquals(0, count(referrerTable + " where " + referrerColumn + " = " + userId),
					referrerTable + "." + referrerColumn + " must not reference the wiped user");
		}
	}

	private Integer insertContentResource(String contentCode, Integer userId, String filename, String fileExt,
			String mimeType) {
		return jdbcTemplate.queryForObject(
				"""
						insert into zfgbb.content_resource (content_type_id, uploaded_user_id, filename, checksum, file_ext, mime_type)
						values ((select content_resource_type_id from zfgbb.content_resource_type where content_code = ?), ?, ?, 'test-checksum', ?, ?)
						returning content_resource_id
						""",
				Integer.class, contentCode, userId, filename, fileExt, mimeType);
	}

	private Path writeBlobFor(Integer contentResourceId) throws IOException {
		Path blobPath = CONTENT_ROOT.resolve("images").resolve(String.valueOf(contentResourceId));
		Files.createDirectories(blobPath.getParent());
		Files.writeString(blobPath, "blob-" + contentResourceId);
		return blobPath;
	}

	private static Path createContentRoot() {
		try {
			return Files.createTempDirectory("zfgbb-account-deletion-content");
		} catch (IOException contentRootFailure) {
			throw new IllegalStateException("could not create the content root temp directory", contentRootFailure);
		}
	}
}
