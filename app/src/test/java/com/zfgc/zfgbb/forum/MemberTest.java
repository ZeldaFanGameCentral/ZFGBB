package com.zfgc.zfgbb.forum;

import com.zfgc.zfgbb.model.Securable;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.zfgc.zfgbb.dbo.*;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.mappers.*;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.services.contentstore.ContentService;
import com.zfgc.zfgbb.services.forum.ForumService;
import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

import tools.jackson.databind.JsonNode;

class MemberTest extends PostgresIntegrationTest {

	@Autowired protected MessageDboMapper messageDboMapper;
	@Autowired private ForumService forumService;
	@Autowired private BoardDboMapper boardDboMapper;
	@Autowired private BrBoardPermissionDboMapper brBoardPermissionDboMapper;
	@Autowired private BrUserPermissionDboMapper brUserPermissionDboMapper;
	@Autowired private ContentEntityDboMapper contentEntityDboMapper;
	@Autowired private ContentResourceDboMapper contentResourceDboMapper;
	@Autowired private ContentResourceTypeDboMapper contentResourceTypeDboMapper;
	@Autowired private ContentService contentService;
	@Autowired private FileAttachmentDboMapper fileAttachmentDboMapper;
	@Autowired private MessageHistoryDboMapper messageHistoryDboMapper;
	@Autowired private PermissionDboMapper permissionDboMapper;
	@Autowired private ProjectNewsDboMapper projectNewsDboMapper;
	@Autowired private ReactionTypeDboMapper reactionTypeDboMapper;
	@Autowired private SystemConfigDboMapper systemConfigDboMapper;
	@Autowired private ThreadDboMapper threadDboMapper;
	@Autowired private UserBioInfoDboMapper userBioInfoDboMapper;

	@Nested
	class Guest {

		private static final int VISIBLE_BOARD_ID = 1;
		private static final String OCARINA_SLUG = "ocarina-of-time";
		private static final int OCARINA_ENTITY_ID = 1;

		@Test
		void postCountExcludesHiddenAndRecycleBoards() throws Exception {
			TestUser owner = createUser("vis_" + suffix);
			String adminToken = getAdminToken();

			postThread(owner.token(), "Vis A " + suffix, "op body");
			int threadB = postThread(owner.token(), "Vis B " + suffix, "op body");
			postReply(owner.token(), threadB, "reply to recycle");

			int hiddenBoardId = insertBoard("Hidden " + suffix);
			grantBoardPermission(hiddenBoardId, permissionIdOf("ZFGC_USER"));
			int hiddenThreadId = insertThread("Hidden thread " + suffix, hiddenBoardId, owner.id());
			insertMessage(owner.id(), hiddenThreadId, hiddenBoardId);

			int recycledMessageId = messageIdAt(threadB, 2);
			mockMvc.perform(delete("/message/" + recycledMessageId)
					.header("Authorization", "Bearer " + owner.token()))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("RECYCLED"));

			mockMvc.perform(get("/users/" + owner.id()))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.bioInfo.postCount").value(2));

			mockMvc.perform(get("/users/" + owner.id())
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.bioInfo.postCount").value(2));
		}

		@Test
		void threadPageRendersEveryDistinctAuthorCardCorrectly() throws Exception {
			TestUser authorA = createUser("aAuth_" + suffix);
			TestUser authorB = createUser("bAuth_" + suffix);

			grantUserPermission(authorB.id(), permissionIdOf("ZFGC_SITE_MODERATOR"));

			setSignature(authorA.id(), "[b]author A signature[/b]");
			setSignature(authorB.id(), "[b]author B signature[/b]");

			int threadId = postThread(authorA.token(), "Multi author thread " + suffix, "opening body by A");
			postReply(authorB.token(), threadId, "reply body by B");
			postReply(authorA.token(), threadId, "second reply body by A");

			int hiddenBoardId = insertBoard("Hidden multi " + suffix);
			grantBoardPermission(hiddenBoardId, permissionIdOf("ZFGC_USER"));
			int hiddenThreadId = insertThread("Hidden multi thread " + suffix, hiddenBoardId, authorA.id());
			insertMessage(authorA.id(), hiddenThreadId, hiddenBoardId);

			int authorBMessageId = messageIdAt(threadId, 2);
			mockMvc.perform(post("/reactions")
					.header("Authorization", "Bearer " + authorA.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content("""
							{"reactableType": "MESSAGE", "reactableId": %d, "reactionTypeId": %d}
							""".formatted(authorBMessageId, anyReactionTypeId())))
					.andExpect(status().isOk());

			MvcResult result = mockMvc.perform(get("/thread/" + threadId)
					.param("page", "1")
					.param("pageSize", "10"))
					.andExpect(status().isOk())
					.andReturn();
			JsonNode messages = json.readTree(result.getResponse().getContentAsString()).get("messages");
			assertNotNull(messages, "the thread response must carry a messages array");

			JsonNode authorACard = authorCardForPost(messages, 1);
			JsonNode authorBCard = authorCardForPost(messages, 2);
			JsonNode authorASecondCard = authorCardForPost(messages, 3);

			assertEquals(authorA.userName(), authorACard.get("displayName").asString());
			assertEquals(authorB.userName(), authorBCard.get("displayName").asString());
			assertEquals(authorA.userName(), authorASecondCard.get("displayName").asString());

			assertTrue(permissionCodesOf(authorACard).isEmpty(),
					"a plain member's public card must expose no rank permissions after retain");
			assertEquals(Set.of("ZFGC_SITE_MODERATOR"), permissionCodesOf(authorBCard),
					"a batch-seeded author must be retained down to only public rank permissions");

			assertSignatureHiddenButParsed(authorACard);
			assertSignatureHiddenButParsed(authorBCard);

			assertEquals(2, authorACard.get("bioInfo").get("postCount").asInt(),
					"the guest-filtered post count must exclude the hidden-board post");
			assertEquals(1, authorBCard.get("bioInfo").get("postCount").asInt());

			assertTrue(authorACard.get("awards").isArray() && authorACard.get("awards").isEmpty(),
					"awards must serialize as an empty array, not null");
			assertTrue(authorBCard.get("awards").isArray() && authorBCard.get("awards").isEmpty());

			assertEquals(1, authorBCard.get("reactionSummary").get("reactionCount").asInt(),
					"a reaction on a batch-seeded author's message must surface in the reaction summary");
		}

		private JsonNode authorCardForPost(JsonNode messages, int postInThread) {
			for (JsonNode message : messages)
				if (message.hasNonNull("postInThread") && message.get("postInThread").asInt() == postInThread)
					return message.get("createdUser");
			throw new AssertionError("no message at post_in_thread " + postInThread);
		}

		private void assertSignatureHiddenButParsed(JsonNode createdUser) {
			JsonNode bioInfo = createdUser.get("bioInfo");
			assertNotNull(bioInfo, "a public author card must carry bio info");
			JsonNode signature = bioInfo.get("signature");
			assertTrue(signature == null || signature.isNull(), "the raw signature must be stripped from public cards");
			JsonNode signatureParsed = bioInfo.get("signatureParsed");
			assertTrue(signatureParsed != null && signatureParsed.isString() && !signatureParsed.asString().isBlank(),
					"the rendered signature must survive the strip");
		}

		private Set<String> permissionCodesOf(JsonNode createdUser) {
			Set<String> codes = new HashSet<>();
			JsonNode permissions = createdUser.get("permissions");
			if (permissions != null && !permissions.isNull())
				for (JsonNode permission : permissions)
					codes.add(permission.get("permissionCode").asString());
			return codes;
		}

		@Test
		void readOnlyOnlyBoardCountsTowardGuestBaseline() throws Exception {
			TestUser owner = createUser("ro_" + suffix);

			postThread(owner.token(), "Read only base " + suffix, "op body");

			int readOnlyBoardId = insertBoard("Read only " + suffix);
			grantBoardPermission(readOnlyBoardId, permissionIdOf("ZFGC_READ_ONLY"));
			int readOnlyThreadId = insertThread("Read only thread " + suffix, readOnlyBoardId, owner.id());
			insertMessage(owner.id(), readOnlyThreadId, readOnlyBoardId);

			mockMvc.perform(get("/users/" + owner.id()))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.bioInfo.postCount").value(2));
		}

		@Test
		void projectNewsHidesHiddenBoardThreadTitle() throws Exception {
			TestUser author = createUser("news_" + suffix);

			int publicThreadId = insertThread("PUBLIC_TITLE", VISIBLE_BOARD_ID, author.id());
			int hiddenBoardId = insertBoard("Secret board " + suffix);
			grantBoardPermission(hiddenBoardId, permissionIdOf("ZFGC_USER"));
			int secretThreadId = insertThread("SECRET_TITLE", hiddenBoardId, author.id());

			insertProjectNews(OCARINA_ENTITY_ID, publicThreadId, "Public update " + suffix, "public body");
			insertProjectNews(OCARINA_ENTITY_ID, secretThreadId, "Hidden update " + suffix, "hidden body");

			MvcResult result = mockMvc.perform(get("/projects/" + OCARINA_SLUG)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andReturn();
			String body = result.getResponse().getContentAsString();
			assertFalse(body.contains("SECRET_TITLE"), "hidden-board thread title must not leak in the response");

			JsonNode news = json.readTree(body).get("news");
			JsonNode publicEntry = newsEntryFor(news, publicThreadId);
			JsonNode secretEntry = newsEntryFor(news, secretThreadId);
			assertEquals("PUBLIC_TITLE", publicEntry.get("threadName").asString());
			assertTrue(secretEntry.get("threadName") == null || secretEntry.get("threadName").isNull(),
					"hidden-board news entry must not resolve a thread name");
			assertEquals(publicThreadId, publicEntry.get("threadId").asInt());
			assertEquals(secretThreadId, secretEntry.get("threadId").asInt());
		}

		@Test
		void hiddenBoardThreadIsSearchableOnlyWithTheBoardPermission() throws Exception {
			TestUser member = createUser("srch_" + suffix);

			String uniqueTitle = "SearchVisHidden" + suffix;
			int hiddenBoardId = insertBoard("Search hidden " + suffix);
			grantBoardPermission(hiddenBoardId, permissionIdOf("ZFGC_USER"));
			insertThread(uniqueTitle, hiddenBoardId, member.id());

			MvcResult anonymousResult = mockMvc.perform(get("/search").param("q", uniqueTitle))
					.andExpect(status().isOk())
					.andReturn();
			assertFalse(forumGroupHasThreadTitled(anonymousResult.getResponse().getContentAsString(), uniqueTitle),
					"anonymous search must not surface a thread from a board it lacks permission for");

			MvcResult memberResult = mockMvc.perform(get("/search")
					.param("q", uniqueTitle)
					.header("Authorization", "Bearer " + member.token()))
					.andExpect(status().isOk())
					.andReturn();
			assertTrue(forumGroupHasThreadTitled(memberResult.getResponse().getContentAsString(), uniqueTitle),
					"a member holding the board permission must find the hidden-board thread");
		}

		@Test
		void aWildcardTypedIntoSearchMatchesLiterallyInsteadOfMatchingEverything() throws Exception {
			TestUser member = createUser("srchpct_" + suffix);
			String literalWildcardTitle = "SearchPct100%Off" + suffix;
			String decoyTitle = "SearchPct100ZZOff" + suffix;
			int boardId = insertBoard("Search wildcard " + suffix);
			grantBoardPermission(boardId, permissionIdOf("ZFGC_USER"));
			insertThread(literalWildcardTitle, boardId, member.id());
			insertThread(decoyTitle, boardId, member.id());

			MvcResult result = mockMvc.perform(get("/search")
					.param("q", "SearchPct100%Off" + suffix)
					.header("Authorization", "Bearer " + member.token()))
					.andExpect(status().isOk())
					.andReturn();
			String body = result.getResponse().getContentAsString();

			assertTrue(forumGroupHasThreadTitled(body, literalWildcardTitle),
					"a % typed into the search box is content, not syntax: it must still find the "
							+ "thread whose title actually contains a percent sign");
			assertFalse(forumGroupHasThreadTitled(body, decoyTitle),
					"if % reached the database as a LIKE wildcard the decoy would match too, which is "
							+ "how a one-character query would return the entire forum");
		}

		@Test
		void anUnderscoreTypedIntoSearchMatchesLiterallyInsteadOfMatchingAnyCharacter()
				throws Exception {
			TestUser member = createUser("srchund_" + suffix);
			String literalUnderscoreTitle = "SearchUnd_Score" + suffix;
			String decoyTitle = "SearchUndXScore" + suffix;
			int boardId = insertBoard("Search underscore " + suffix);
			grantBoardPermission(boardId, permissionIdOf("ZFGC_USER"));
			insertThread(literalUnderscoreTitle, boardId, member.id());
			insertThread(decoyTitle, boardId, member.id());

			MvcResult result = mockMvc.perform(get("/search")
					.param("q", "SearchUnd_Score" + suffix)
					.header("Authorization", "Bearer " + member.token()))
					.andExpect(status().isOk())
					.andReturn();
			String body = result.getResponse().getContentAsString();

			assertTrue(forumGroupHasThreadTitled(body, literalUnderscoreTitle),
					"an underscore typed into the search box must match an underscore");
			assertFalse(forumGroupHasThreadTitled(body, decoyTitle),
					"an unescaped _ is a single-character LIKE wildcard, so the decoy matching would "
							+ "mean the escaping was lost");
		}

		@Test
		void aTitleTheValidatorAcceptsIsStoredWhole() throws Exception {
			TestUser member = createUser("title_" + suffix);
			String longTitle = "T".repeat(100);

			MvcResult created = mockMvc.perform(post("/thread")
					.param("boardId", String.valueOf(VISIBLE_BOARD_ID))
					.header("Authorization", "Bearer " + member.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content(json.writeValueAsString(Map.of("title", longTitle, "body", "body text"))))
					.andExpect(status().isCreated())
					.andReturn();

			assertEquals(longTitle,
					json.readTree(created.getResponse().getContentAsString()).get("threadName").asString(),
					"the request validator accepts 100 characters, so storage must not silently rewrite a "
							+ "65-100 character title to an ellipsised 64");
		}

		@Test
		void everyRealmTheSearchApiPublishesIsARealmSearchCanFilterBy() throws Exception {
			TestUser member = createUser("realm_" + suffix);
			String uniqueTitle = "SearchRealmProbe" + suffix;
			int boardId = insertBoard("Search realm " + suffix);
			grantBoardPermission(boardId, permissionIdOf("ZFGC_USER"));
			insertThread(uniqueTitle, boardId, member.id());

			JsonNode realms = json.readTree(mockMvc.perform(get("/search/realms"))
					.andExpect(status().isOk())
					.andReturn().getResponse().getContentAsString());

			for (JsonNode realm : realms) {
				String type = realm.get("type").asString();
				if (type.isEmpty())
					continue;
				MvcResult filtered = mockMvc.perform(get("/search")
						.param("q", uniqueTitle)
						.param("types", type)
						.header("Authorization", "Bearer " + member.token()))
						.andExpect(status().isOk())
						.andReturn();
				JsonNode groups = json.readTree(filtered.getResponse().getContentAsString()).get("groups");
				assertTrue(groups.size() == 1 && type.equals(groups.get(0).get("type").asString()),
						"filtering by the realm id the API itself publishes must run that realm's lane; a "
								+ "published id no lane matches silently returns nothing for '" + type + "': "
								+ groups);
			}
		}

		@Test
		void theRealmCatalogueLeadsWithTheAllFilterAndPublishesTheRealmsInPaletteOrder() throws Exception {
			JsonNode realms = json.readTree(mockMvc.perform(get("/search/realms"))
					.andExpect(status().isOk())
					.andReturn().getResponse().getContentAsString());

			List<String> published = new ArrayList<>();
			for (JsonNode realm : realms)
				published.add(realm.get("type").asString());

			assertEquals(List.of("", "forum", "wiki", "project", "resource"), published,
					"the search palette renders the filter row in the order this endpoint publishes it, and "
							+ "each realm bean now declares its own place in that row; a realm the container "
							+ "sorts somewhere else silently reshuffles the row nobody asserts otherwise");
		}

		@Test
		void searchSnippetsCarryTheTextTheRenderedPostShowsItsReader() throws Exception {
			TestUser member = createUser("snipok_" + suffix);

			String bbcodeTitle = "Snippet bbcode " + suffix;
			String bbcodeProbe = "SnippetProbeBbcode" + suffix;
			postThread(member.token(), bbcodeTitle,
					bbcodeProbe + " [b]bold words[/b] and [url=http://example.com]a link[/url] tail");

			String htmlTitle = "Snippet html " + suffix;
			String htmlProbe = "SnippetProbeHtml" + suffix;
			postThread(member.token(), htmlTitle, htmlProbe + " <p>paragraph words</p><br /> tail");

			String templateTitle = "Snippet template " + suffix;
			String templateProbe = "SnippetProbeTemplate" + suffix;
			postThread(member.token(), templateTitle, templateProbe + " {{Infobox|name=Link}} tail");

			assertEquals(bbcodeProbe + " bold words and a link tail",
					forumSnippetOf(member.token(), bbcodeProbe, bbcodeTitle),
					"the snippet is now the visible text of the rendered post, not a regex pass over the "
							+ "stored source; the value is what the old chain produced, but it now holds "
							+ "because the renderer turns [b] and [url] into elements whose text is 'bold "
							+ "words' and 'a link', not because a tag pattern was deleted");
			assertEquals(htmlProbe + " paragraph words tail",
					forumSnippetOf(member.token(), htmlProbe, htmlTitle),
					"raw html a migrated post carries survives rendering as real markup that the output "
							+ "sanitizer's safelist admits, so its visible text is the prose inside it; the "
							+ "old chain reached the same value by deleting anything <shaped like a tag>, "
							+ "which would have eaten a literal less-than sign the reader is meant to see");
			assertEquals(templateProbe + " {{Infobox|name=Link}} tail",
					forumSnippetOf(member.token(), templateProbe, templateTitle),
					"CHANGED DELIBERATELY, was '" + templateProbe + " tail': the engine invokes a template "
							+ "as [template=Name], and the migrator rewrites mediawiki's {{Name}} into that "
							+ "form on import, so a stored {{...}} run is text the reader sees. The old "
							+ "chain deleted it anyway and hid a line of the post; rendering shows the same "
							+ "text the page does. A real [template=Name] call now contributes the "
							+ "template's own rendered text, which the old chain could never show");
		}

		@Test
		void searchSnippetsShowUnreadableMarkupExactlyWhereThePostItselfDoes() throws Exception {
			TestUser member = createUser("snipbug_" + suffix);

			String wikiLinkTitle = "Snippet wikilink " + suffix;
			String wikiLinkProbe = "SnippetProbeWikiLink" + suffix;
			postThread(member.token(), wikiLinkTitle, wikiLinkProbe + " [[Master Sword]] tail");

			int quotedThreadId = postThread(member.token(), "Snippet quote source " + suffix, "the original words");
			int quotedMessageId = messageIdAt(quotedThreadId, 1);

			String quoteInlineTitle = "Snippet quote inline " + suffix;
			String quoteInlineProbe = "SnippetProbeQuoteInline" + suffix;
			postThread(member.token(), quoteInlineTitle, "[quote author=someoneelse msg=" + quotedMessageId
					+ "]quoted stranger words[/quote]" + quoteInlineProbe + " my own reply");

			String quoteShellTitle = "Snippet quote shell " + suffix;
			String quoteShellProbe = "SnippetProbeQuoteShell" + suffix;
			postThread(member.token(), quoteShellTitle, "[quote msg=" + quotedMessageId + "][/quote]"
					+ quoteShellProbe + " my own reply");

			assertEquals(wikiLinkProbe + " [[Master Sword]] tail",
					forumSnippetOf(member.token(), wikiLinkProbe, wikiLinkTitle),
					"THE PINNED BUG IS GONE, but not to the '" + wikiLinkProbe + " tail' the old pin "
							+ "predicted: the old bracket pass \\[[^\\]]*\\] was not nesting-aware and left a "
							+ "stranded ]. No bracket pass runs now, and the forum grammar has no code for a "
							+ "mediawiki link, so the reader sees [[Master Sword]] on the post itself and the "
							+ "snippet shows exactly that");
			assertEquals("[quote author=someoneelse msg=" + quotedMessageId + "]quoted stranger words[/quote]"
							+ quoteInlineProbe + " my own reply",
					forumSnippetOf(member.token(), quoteInlineProbe, quoteInlineTitle),
					"THE PINNED MISATTRIBUTION IS GONE: the old chain erased [quote ...] and [/quote] as two "
							+ "bare tags, leaving 'quoted stranger words' reading as this poster's own. The "
							+ "grammar declares no quote mode taking author plus msg and nothing else, so "
							+ "this markup is unrecognised, the post renders it literally, and the snippet "
							+ "shows the same literal text the reader sees. It is ugly because the post is "
							+ "ugly, and it no longer puts another member's words in this member's mouth");
			assertEquals("Quote from (unavailable) on (jump to message)" + quoteShellProbe + " my own reply",
					forumSnippetOf(member.token(), quoteShellProbe, quoteShellTitle),
					"CHANGED DELIBERATELY, was '" + quoteShellProbe + " my own reply': search renders inside "
							+ "an empty quote scope and never resolves a quoted message, because doing so "
							+ "would put a source lookup per hit on an anonymous endpoint to show another "
							+ "member's words. The empty scope is what keeps the body placeholder "
							+ "'(quoted message unavailable)' out of the snippet; the attribution header "
							+ "still renders, and with no source resolved it names the author "
							+ "'(unavailable)', which is the honest cost of that choice");
		}

		@Test
		void aHitCarryingNoSnippetOmitsTheKeyRatherThanSendingNull() throws Exception {
			TestUser member = createUser("wire_" + suffix);
			String titleOnlyProbe = "SearchWireProbe" + suffix;
			int boardId = insertBoard("Search wire " + suffix);
			grantBoardPermission(boardId, permissionIdOf("ZFGC_USER"));
			insertThread(titleOnlyProbe, boardId, member.id());

			JsonNode hit = forumHitTitled(member.token(), titleOnlyProbe, titleOnlyProbe);

			assertFalse(hit.has("snippet"),
					"snippet is an Optional<String> and the frontend reads it with valibot's v.optional, "
							+ "which accepts a missing key but REJECTS an explicit null; a thread matched by "
							+ "its name alone has no message body to snippet, so the key must not be on the "
							+ "wire at all: " + hit);
			assertTrue(hit.hasNonNull("context"),
					"the same hit's context is a present Optional and must serialize as its plain string, "
							+ "not as an Optional wrapper object: " + hit);
		}

		@Test
		void everyGroupTotalCountsEveryMatchNotJustTheHitsOnePageReturns() throws Exception {
			TestUser member = createUser("total_" + suffix);
			String totalProbe = "SearchTotalProbe" + suffix;
			int boardId = insertBoard("Search total " + suffix);
			grantBoardPermission(boardId, permissionIdOf("ZFGC_USER"));
			int matchingThreads = 9;
			for (int thread = 1; thread <= matchingThreads; thread++)
				insertThread(totalProbe + " thread " + thread, boardId, member.id());

			JsonNode results = searchResultsFor(member.token(), totalProbe);
			JsonNode forum = searchGroupTyped(results, "forum");

			assertEquals(matchingThreads, forum.get("total").asInt(),
					"the group total is what the UI prints as the match count, so it must be a real count "
							+ "of matching threads rather than the size of the capped page of hits");
			assertTrue(forum.get("hits").size() < matchingThreads,
					"the page of hits stays capped even though the total is honest: " + forum);
			assertEquals(matchingThreads, results.get("total").asInt(),
					"the response total is the sum of the group totals, so a probe only the forum lane "
							+ "matches must report every matching thread");
		}

		@Test
		void forumIndexCacheServesDeepCopiesAndEvictsOnWrites() throws Exception {
			int restrictedBoardId = insertBoard("Restricted Cache Probe " + suffix, 1, 99, null);
			grantBoardPermission(restrictedBoardId, permissionIdOf("ZFGC_SITE_ADMIN"));

			TestUser member = createUser("cache_" + suffix);
			String adminToken = getAdminToken();

			JsonNode adminFirstView = fetchForum(adminToken);
			assertTrue(boardIdsOf(adminFirstView).contains(restrictedBoardId),
					"admin must see the restricted board on the forum index");

			JsonNode memberView = fetchForum(member.token());
			assertFalse(boardIdsOf(memberView).contains(restrictedBoardId),
					"member must not see the restricted board on the forum index");

			JsonNode adminSecondView = fetchForum(adminToken);
			assertEquals(adminFirstView, adminSecondView,
					"a filtered member request must not corrupt the cached unfiltered forum");

			String evictionProbeName = "Eviction probe " + suffix;
			String threadBody = """
					{"title": "%s", "body": "Cache eviction probe post"}
					""".formatted(evictionProbeName);
			mockMvc.perform(post("/thread")
					.param("boardId", "1")
					.header("Authorization", "Bearer " + member.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content(threadBody))
					.andExpect(status().isCreated());

			JsonNode adminAfterPostView = fetchForum(adminToken);
			assertEquals(evictionProbeName, latestThreadNameOfBoard(adminAfterPostView, 1),
					"posting a thread must evict the forum cache so the index shows the new latest post");
		}

		@Test
		void anonymousWriteAttemptsAreRejectedEverywhere() throws Exception {
			String payload = """
					{"reactableType": "MESSAGE", "reactableId": 1, "reactionTypeId": 1}
					""";
			mockMvc.perform(post("/reactions")
					.contentType(MediaType.APPLICATION_JSON)
					.content(payload))
					.andExpect(status().isUnauthorized());

			mockMvc.perform(delete("/message/1"))
					.andExpect(status().isUnauthorized());
		}

		@Test
		void hiddenChildBoardSummaryDoesNotLeakToGuests() throws Exception {
			TestUser owner = createUser("childleak_" + suffix);
			String adminToken = getAdminToken();
			int siteAdminPermissionId = permissionIdOf("ZFGC_SITE_ADMIN");

			int parentBoardId = insertBoard("Parent leak " + suffix, 1, 99, null);
			grantBoardPermission(parentBoardId, permissionIdOf("ZFGC_GUEST"));
			grantBoardPermission(parentBoardId, siteAdminPermissionId);

			int hiddenChildId = insertChildBoard("Hidden child " + suffix, parentBoardId);
			grantBoardPermission(hiddenChildId, siteAdminPermissionId);
			String hiddenThreadTitle = "HIDDENCHILDLEAK" + suffix;
			int hiddenThreadId = insertThread(hiddenThreadTitle, hiddenChildId, owner.id());
			insertMessage(owner.id(), hiddenThreadId, hiddenChildId);

			int visibleChildId = insertChildBoard("Visible child " + suffix, parentBoardId);
			grantBoardPermission(visibleChildId, permissionIdOf("ZFGC_GUEST"));
			grantBoardPermission(visibleChildId, siteAdminPermissionId);
			int visibleThreadId = insertThread("VisibleChildThread" + suffix, visibleChildId, owner.id());
			insertMessage(owner.id(), visibleThreadId, visibleChildId);

			MvcResult anonymousResult = mockMvc.perform(get("/board/" + parentBoardId))
					.andExpect(status().isOk())
					.andReturn();
			String anonymousBody = anonymousResult.getResponse().getContentAsString();
			assertFalse(anonymousBody.contains(hiddenThreadTitle),
					"a hidden child board's latest thread title must not leak to guests");
			Set<Integer> anonymousChildIds = childBoardIdsOf(anonymousBody);
			assertFalse(anonymousChildIds.contains(hiddenChildId),
					"the hidden child board must not appear in the guest child list");
			assertTrue(anonymousChildIds.contains(visibleChildId),
					"the guest-readable sibling child must survive the filter");

			MvcResult adminResult = mockMvc.perform(get("/board/" + parentBoardId)
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andReturn();
			Set<Integer> adminChildIds = childBoardIdsOf(adminResult.getResponse().getContentAsString());
			assertTrue(adminChildIds.contains(hiddenChildId), "an admin must still see the hidden child board");
			assertTrue(adminChildIds.contains(visibleChildId), "an admin must still see the visible child board");

			forumService.evictUnfilteredForumCache();
			MvcResult anonymousForum = mockMvc.perform(get("/board/forum"))
					.andExpect(status().isOk())
					.andReturn();
			Set<Integer> anonymousForumChildIds =
					forumChildBoardIdsOf(anonymousForum.getResponse().getContentAsString());
			assertFalse(anonymousForumChildIds.contains(hiddenChildId),
					"the forum index serializes each board's child list too, so it must filter the "
							+ "hidden child board exactly like the board view does");
			assertTrue(anonymousForumChildIds.contains(visibleChildId),
					"the guest-readable sibling child must survive the forum index filter");

			MvcResult adminForum = mockMvc.perform(get("/board/forum")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andReturn();
			assertTrue(forumChildBoardIdsOf(adminForum.getResponse().getContentAsString()).contains(hiddenChildId),
					"filtering the forum index must copy rather than mutate the shared board cache, "
							+ "or the guest request above would strip the child board for everyone");
		}

		@Test
		void hiddenThreadReadsReturn404NotDistinguishableFromMissing() throws Exception {
			TestUser owner = createUser("hidthr_" + suffix);

			int hiddenBoardId = insertBoard("Hidden thread board " + suffix);
			grantBoardPermission(hiddenBoardId, permissionIdOf("ZFGC_SITE_ADMIN"));
			int hiddenThreadId = insertThread("Hidden thread " + suffix, hiddenBoardId, owner.id());
			insertMessage(owner.id(), hiddenThreadId, hiddenBoardId);
			int hiddenMessageId = messageIdAt(hiddenThreadId, 1);

			int missingThreadId = 2_000_000_000;

			mockMvc.perform(get("/thread/" + hiddenThreadId).param("page", "1").param("pageSize", "10"))
					.andExpect(status().isNotFound());
			mockMvc.perform(get("/thread/" + missingThreadId).param("page", "1").param("pageSize", "10"))
					.andExpect(status().isNotFound());
			mockMvc.perform(get("/thread/" + hiddenThreadId + "/allowed-actions"))
					.andExpect(status().isNotFound());
			mockMvc.perform(get("/message/" + hiddenMessageId + "/allowed-actions"))
					.andExpect(status().isNotFound());

			int visibleThreadId = insertThread("Visible thread " + suffix, VISIBLE_BOARD_ID, owner.id());
			insertMessage(owner.id(), visibleThreadId, VISIBLE_BOARD_ID);
			mockMvc.perform(get("/thread/" + visibleThreadId + "/allowed-actions"))
					.andExpect(status().isOk());
		}

		@Test
		void unreadableBoardIsNotFetchableDirectly() throws Exception {
			int hiddenBoardId = insertBoard("Unfetchable board " + suffix, 1, 99, null);
			grantBoardPermission(hiddenBoardId, permissionIdOf("ZFGC_SITE_ADMIN"));

			mockMvc.perform(get("/board/" + hiddenBoardId))
					.andExpect(status().isNotFound());

			MvcResult adminResult = mockMvc.perform(get("/board/" + hiddenBoardId)
					.header("Authorization", "Bearer " + getAdminToken()))
					.andExpect(status().isOk())
					.andReturn();
			assertFalse(adminResult.getResponse().getContentAsString().contains("permission"),
					"making a board Securable must not start serializing its permission list to clients");

			forumService.evictUnfilteredForumCache();
			MvcResult adminForum = mockMvc.perform(get("/board/forum")
					.header("Authorization", "Bearer " + getAdminToken()))
					.andExpect(status().isOk())
					.andReturn();
			assertFalse(adminForum.getResponse().getContentAsString().contains("permission"),
					"making a board summary Securable must not start serializing its permission list either");
		}

		@Test
		void attachmentOnAnUnreadableBoardIsNotFound() throws Exception {
			TestUser owner = createUser("attach_" + suffix);
			int hiddenBoardId = insertBoard("Attachment board " + suffix);
			grantBoardPermission(hiddenBoardId, permissionIdOf("ZFGC_SITE_ADMIN"));
			int hiddenThreadId = insertThread("Attachment thread " + suffix, hiddenBoardId, owner.id());
			insertMessage(owner.id(), hiddenThreadId, hiddenBoardId);
			int resourceId = insertAttachment(messageIdAt(hiddenThreadId, 1), owner.id());

			assertThrows(ZfgcNotFoundException.class,
					() -> contentService.authorizeAccess(resourceId, actorHolding("ZFGC_USER")),
					"an attachment must inherit the board permissions of the message it hangs off");
			contentService.authorizeAccess(resourceId, actorHolding("ZFGC_SITE_ADMIN"));
		}

		@Test
		void hugePageNumberOnBoardListingReturnsEmptyPageNot500() throws Exception {
			mockMvc.perform(get("/board/" + VISIBLE_BOARD_ID).param("page", "2000000000"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.unStickyThreads", hasSize(0)));
		}

		private Set<Integer> childBoardIdsOf(String boardResponseBody) {
			Set<Integer> childBoardIds = new HashSet<>();
			JsonNode childBoards = json.readTree(boardResponseBody).get("childBoards");
			if (childBoards != null && !childBoards.isNull())
				for (JsonNode childBoard : childBoards)
					childBoardIds.add(childBoard.get("boardId").asInt());
			return childBoardIds;
		}

		private Set<Integer> forumChildBoardIdsOf(String forumResponseBody) {
			Set<Integer> childBoardIds = new HashSet<>();
			for (JsonNode category : json.readTree(forumResponseBody).get("categories"))
				for (JsonNode board : category.get("boards")) {
					JsonNode childBoards = board.get("childBoards");
					if (childBoards == null || childBoards.isNull())
						continue;
					for (JsonNode childBoard : childBoards)
						childBoardIds.add(childBoard.get("boardId").asInt());
				}
			return childBoardIds;
		}

		private Set<Integer> boardIdsOf(JsonNode forum) {
			Set<Integer> boardIds = new HashSet<>();
			for (JsonNode category : forum.get("categories"))
				for (JsonNode board : category.get("boards"))
					boardIds.add(board.get("boardId").asInt());
			return boardIds;
		}

		private String latestThreadNameOfBoard(JsonNode forum, int boardId) {
			for (JsonNode category : forum.get("categories"))
				for (JsonNode board : category.get("boards"))
					if (board.get("boardId").asInt() == boardId)
						return board.get("threadName").asString();
			return null;
		}

		private boolean forumGroupHasThreadTitled(String responseBody, String title) {
			JsonNode groups = json.readTree(responseBody).get("groups");
			assertNotNull(groups, "search response must carry a groups array");
			for (JsonNode group : groups) {
				if (!"forum".equals(group.get("type").asString()))
					continue;
				for (JsonNode hit : group.get("hits"))
					if (hit.hasNonNull("title") && title.equals(hit.get("title").asString()))
						return true;
			}
			return false;
		}

		private JsonNode searchResultsFor(String token, String query) throws Exception {
			return json.readTree(mockMvc.perform(get("/search")
					.param("q", query)
					.header("Authorization", "Bearer " + token))
					.andExpect(status().isOk())
					.andReturn().getResponse().getContentAsString());
		}

		private JsonNode searchGroupTyped(JsonNode results, String type) {
			JsonNode groups = results.get("groups");
			assertNotNull(groups, "search response must carry a groups array");
			for (JsonNode group : groups)
				if (group.get("type").asString().equals(type))
					return group;
			throw new AssertionError("no " + type + " group in " + results);
		}

		private JsonNode forumHitTitled(String token, String query, String threadTitle) throws Exception {
			for (JsonNode hit : searchGroupTyped(searchResultsFor(token, query), "forum").get("hits"))
				if (hit.hasNonNull("title") && hit.get("title").asString().equals(threadTitle))
					return hit;
			throw new AssertionError("no forum hit titled " + threadTitle + " for query " + query);
		}

		private String forumSnippetOf(String token, String query, String threadTitle) throws Exception {
			JsonNode hit = forumHitTitled(token, query, threadTitle);
			return hit.hasNonNull("snippet") ? hit.get("snippet").asString() : null;
		}

		private JsonNode newsEntryFor(JsonNode news, int threadId) {
			assertNotNull(news, "project response must carry a news array");
			for (JsonNode entry : news)
				if (entry.hasNonNull("threadId") && entry.get("threadId").asInt() == threadId)
					return entry;
			throw new AssertionError("no project-news entry linked thread " + threadId);
		}

		private int insertChildBoard(String boardName, int parentBoardId) {
			return insertBoard(boardName, null, 0, parentBoardId);
		}

		private void insertProjectNews(int contentEntityId, int threadId, String subject, String body) {
			ProjectNewsDbo news = new ProjectNewsDbo();
			news.setContentEntityId(contentEntityId);
			news.setThreadId(threadId);
			news.setSubject(subject);
			news.setBody(body);
			news.setPublishedTs(OffsetDateTime.now());
			projectNewsDboMapper.insertSelective(news);
		}
	}

	@Nested
	class Posting {

		@Test
		void registerLoginThreadAndReply() throws Exception {
			TestUser user = createUser("it_" + suffix);


			String threadBody = """
					{"title": "Hello world %s", "body": "First post!"}
					""".formatted(suffix);
			mockMvc.perform(post("/thread")
					.param("boardId", "1")
					.header("Authorization", "Bearer " + user.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content(threadBody))
					.andExpect(status().isCreated());

			assertEquals(1, threadCount(criteria -> criteria.andThreadNameEqualTo("Hello world " + suffix)));
			MessageDboExample authoredScope = new MessageDboExample();
			authoredScope.createCriteria().andOwnerIdEqualTo(user.id());
			assertEquals(1, messageDboMapper.countByExample(authoredScope));
		}

		@Test
		void replyTemplateIsAddressedToTheRequestedThread() throws Exception {
			TestUser member = createUser("tmpl_" + suffix);
			int threadId = postThread(member.token(), "Template thread " + suffix, "op body");

			mockMvc.perform(get("/message/template")
					.param("threadId", String.valueOf(threadId))
					.header("Authorization", "Bearer " + member.token()))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.message.threadId").value(threadId))
					.andExpect(jsonPath("$.message.ownerId").value(member.id()))
					.andExpect(jsonPath("$.message.currentMessage.currentFlag").value(true))
					.andExpect(jsonPath("$.message.currentMessage.contentFormat").value("BBCODE"));
		}

		@Test
		void memberEditsTheirOwnPostAndTheRevisionIsRecorded() throws Exception {
			TestUser editor = createUser("edit_" + suffix);
			int threadId = postThread(editor.token(), "Editable " + suffix, "Original body");
			int messageId = messageIdAt(threadId, 1);

			mockMvc.perform(put("/message/" + messageId)
					.header("Authorization", "Bearer " + editor.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"body\": \"Edited body\"}"))
					.andExpect(status().isOk());

			assertEquals("Edited body", messageHistoryDboMapper.selectByExample(messageHistoryWhere(
					criteria -> criteria.andMessageIdEqualTo(messageId).andCurrentFlagEqualTo(true)))
					.get(0).getMessageText(),
					"editing must replace the current revision body");
			assertEquals(2, messageHistoryDboMapper.countByExample(
					messageHistoryWhere(criteria -> criteria.andMessageIdEqualTo(messageId))),
					"editing must record a new revision instead of overwriting history");
		}

		@Test
		void savingAndEditingAMessageReturnTheTimestampsTheDatabaseAssigned() throws Exception {
			TestUser author = createUser("mts_" + suffix);
			int threadId = postThread(author.token(), "Timestamped " + suffix, "First post!");

			mockMvc.perform(post("/message/" + threadId)
					.header("Authorization", "Bearer " + author.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"body\": \"a reply worth timestamping\"}"))
					.andExpect(status().isCreated())
					.andExpect(jsonPath("$.message.createdTs").isNotEmpty())
					.andExpect(jsonPath("$.message.updatedTs").isNotEmpty())
					.andExpect(jsonPath("$.message.currentMessage.createdTs").isNotEmpty())
					.andExpect(jsonPath("$.message.currentMessage.updatedTs").isNotEmpty());

			editMessage(author.token(), messageIdAt(threadId, 2), "an edited body")
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.message.currentMessage.createdTs").isNotEmpty())
					.andExpect(jsonPath("$.message.currentMessage.updatedTs").isNotEmpty());
		}

		@Test
		void messageEditsAreRefusedOnBoardsTheEditorCannotRead() throws Exception {
			TestUser owner = createUser("bedit_" + suffix);
			TestUser moderator = createUser("bmod_" + suffix);
			grantUserPermission(moderator.id(), permissionIdOf("ZFGC_FORUM_MODERATE"));
			String adminToken = getAdminToken();

			int threadId = postThread(owner.token(), "Board scoped edit " + suffix, "Original body");
			int messageId = messageIdAt(threadId, 1);

			String readableBoardEdit = "Moderator edit on a readable board";
			editMessage(moderator.token(), messageId, readableBoardEdit)
					.andExpect(status().isOk());
			assertEquals(readableBoardEdit, currentBodyOf(messageId),
					"a forum moderator must still edit posts on a board they hold a permission for");

			int restrictedBoardId = insertBoard("Restricted edit " + suffix);
			grantBoardPermission(restrictedBoardId, permissionIdOf("ZFGC_SITE_ADMIN"));
			moveThreadToBoard(threadId, restrictedBoardId);

			for (TestUser locked : List.of(moderator, owner))
				mockMvc.perform(get("/thread/" + threadId)
						.param("page", "1")
						.param("pageSize", "10")
						.header("Authorization", "Bearer " + locked.token()))
						.andExpect(status().isNotFound());

			long revisionsBefore = messageHistoryDboMapper.countByExample(
					messageHistoryWhere(criteria -> criteria.andMessageIdEqualTo(messageId)));

			editMessage(moderator.token(), messageId, "Moderator rewrite behind the board wall")
					.andExpect(status().isForbidden());
			assertEquals(readableBoardEdit, currentBodyOf(messageId),
					"ZFGC_FORUM_MODERATE is granted globally rather than per board, so the board "
							+ "permissions must still gate the write");

			editMessage(owner.token(), messageId, "Owner rewrite behind the board wall")
					.andExpect(status().isForbidden());
			assertEquals(readableBoardEdit, currentBodyOf(messageId),
					"still owning a post that was moved beyond the owner's board permissions must "
							+ "not re-open the write");

			assertEquals(revisionsBefore, messageHistoryDboMapper.countByExample(
					messageHistoryWhere(criteria -> criteria.andMessageIdEqualTo(messageId))),
					"a refused edit must not leave a revision behind");

			String restrictedBoardEdit = "Admin edit on the restricted board";
			editMessage(adminToken, messageId, restrictedBoardEdit)
					.andExpect(status().isOk());
			assertEquals(restrictedBoardEdit, currentBodyOf(messageId),
					"an editor holding the restricted board's permission must still be able to edit");
		}

		@Test
		void editingIsOfferedToAndPermittedForTheAuthorOnly() throws Exception {
			TestUser author = createUser("actauth_" + suffix);
			TestUser bystander = createUser("actbys_" + suffix);

			int threadId = postThread(author.token(), "Allowed actions " + suffix, "Original body");
			int messageId = messageIdAt(threadId, 1);

			assertTrue(allowedMessageActions(author.token(), messageId).contains("message.edit"),
					"the author of an open post must be offered the edit action");
			assertFalse(allowedMessageActions(bystander.token(), messageId).contains("message.edit"),
					"a member who neither wrote the post nor moderates must not be offered the edit action");

			String bodyBeforeTheRefusedEdit = currentBodyOf(messageId);
			editMessage(bystander.token(), messageId, "Bystander rewrite")
					.andExpect(status().isForbidden());
			assertEquals(bodyBeforeTheRefusedEdit, currentBodyOf(messageId),
					"a member must not be able to rewrite a post they did not write");
		}

		@Test
		void memberListsOwnPostHistoryPaged() throws Exception {
			TestUser member = createUser("hist_" + suffix);

			String openingBody = "history op body " + suffix;
			int threadId = postThread(member.token(), "History " + suffix, openingBody);
			for (int replyNumber = 2; replyNumber <= 4; replyNumber++)
				postReply(member.token(), threadId, "[b]history reply " + replyNumber + " " + suffix + "[/b]");

			MvcResult firstPageResult = mockMvc.perform(get("/message/user/" + member.id())
					.param("page", "1")
					.param("pageSize", "3")
					.header("Authorization", "Bearer " + member.token()))
					.andExpect(status().isOk())
					.andReturn();
			MvcResult secondPageResult = mockMvc.perform(get("/message/user/" + member.id())
					.param("page", "2")
					.param("pageSize", "3")
					.header("Authorization", "Bearer " + member.token()))
					.andExpect(status().isOk())
					.andReturn();

			JsonNode firstPage = json.readTree(firstPageResult.getResponse().getContentAsString());
			JsonNode secondPage = json.readTree(secondPageResult.getResponse().getContentAsString());

			assertTrue(firstPage.toString().contains("bb-code-b") && !firstPage.toString().contains("[b]"),
					"post history must return rendered BBCode, not the raw markup the member typed: "
							+ firstPage);
			assertEquals(3, firstPage.size(), "the first page must hold exactly pageSize messages");
			assertEquals(1, secondPage.size(), "the second page must hold the remaining message");

			Set<Integer> pagedMessageIds = new HashSet<>();
			for (JsonNode message : firstPage)
				pagedMessageIds.add(message.get("message").get("id").asInt());
			for (JsonNode message : secondPage)
				pagedMessageIds.add(message.get("message").get("id").asInt());
			assertEquals(4, pagedMessageIds.size(), "the two pages must partition the member's post history");
			assertTrue(secondPage.get(0).get("message").get("currentMessage").get("messageText").asString()
					.contains(openingBody),
					"newest-first paging must leave the opening post on the last page, rendered");
		}

		@Test
		void memberPreviewsBBCodeWithQuoteScope() throws Exception {
			TestUser member = createUser("prev_" + suffix);

			String sourceBody = "Preview quote source " + suffix;
			int threadId = postThread(member.token(), "Preview thread " + suffix, sourceBody);
			int sourceMessageId = messageIdAt(threadId, 1);

			String previewPayload = """
					{"content": "[quote msg=%d][/quote][b]bold preview[/b]", "scope": "FORUM"}
					""".formatted(sourceMessageId);
			MvcResult previewResult = mockMvc.perform(post("/content/preview")
					.header("Authorization", "Bearer " + member.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content(previewPayload))
					.andExpect(status().isOk())
					.andReturn();

			String contentParsed = json.readTree(previewResult.getResponse().getContentAsString())
					.get("contentParsed").asString();
			assertTrue(contentParsed.contains(sourceBody),
					"the preview must splice the quoted source body through the quote scope");
			assertTrue(contentParsed.contains("bb-code-b"), "the preview must render bbcode to html");
			assertFalse(contentParsed.contains("[b]"), "no raw bbcode may survive in the rendered preview");
		}

		@Test
		void quotingAMessageFromABoardTheViewerCannotSeeRevealsNothing() throws Exception {
			TestUser insider = createUser("qins_" + suffix);
			TestUser outsider = createUser("qout_" + suffix);

			int hiddenBoardId = insertBoard("Quote hidden " + suffix);
			grantBoardPermission(hiddenBoardId, permissionIdOf("ZFGC_SITE_ADMIN"));
			String secretBody = "SecretQuotedBody" + suffix;
			int hiddenThreadId = postThread(insider.token(), "Quote hidden thread " + suffix, secretBody);
			int hiddenMessageId = messageIdAt(hiddenThreadId, 1);
			moveIntoBoard(hiddenThreadId, hiddenMessageId, hiddenBoardId);

			String previewPayload = """
					{"content": "[quote author=Somebody thread=%d msg=%d][/quote]tail", "scope": "FORUM"}
					""".formatted(hiddenThreadId, hiddenMessageId);
			MvcResult previewResult = mockMvc.perform(post("/content/preview")
					.header("Authorization", "Bearer " + outsider.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content(previewPayload))
					.andExpect(status().isOk())
					.andReturn();

			String contentParsed = json.readTree(previewResult.getResponse().getContentAsString())
					.get("contentParsed").asString();
			assertFalse(contentParsed.contains(secretBody),
					"a quote of a board the viewer cannot read must never splice that board's body: "
							+ contentParsed);
			assertTrue(contentParsed.contains("tail"),
					"the unreadable quote degrades in place and the rest of the post still renders: "
							+ contentParsed);
		}

		@Test
		void aThreadTemplateAnswersForTheReaderNotForAGuest() throws Exception {
			TestUser insider = createUser("tmplin_" + suffix);
			TestUser outsider = createUser("tmplout_" + suffix);

			int restrictedBoardId = insertBoard("Template restricted " + suffix);
			grantBoardPermission(restrictedBoardId, permissionIdOf("ZFGC_SITE_ADMIN"));
			String leadBody = "TemplateLeadBody" + suffix;
			int threadId = postThread(insider.token(), "Template lead thread " + suffix, leadBody);
			moveIntoBoard(threadId, messageIdAt(threadId, 1), restrictedBoardId);
			grantBoardPermission(restrictedBoardId, permissionIdOf("ZFGC_USER"));

			String payload = """
					{"content": "[template=announcementlead]threadId=%d[/template]", "scope": "FORUM"}
					""".formatted(threadId);

			String forTheMember = json.readTree(mockMvc.perform(post("/content/preview")
					.header("Authorization", "Bearer " + outsider.token())
					.contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isOk()).andReturn().getResponse().getContentAsString())
					.get("contentParsed").asString();

			assertTrue(forTheMember.contains(leadBody),
					"a template whose data source takes the viewer must answer for the reader; while the "
							+ "fetcher hardcoded User.guest() this rendered blank for every logged-in member: "
							+ forTheMember);
		}

		@Test
		void aThreadTemplateRevealsNothingToAReaderWhoCannotSeeTheBoard() throws Exception {
			TestUser insider = createUser("tmplden_" + suffix);
			TestUser outsider = createUser("tmplno_" + suffix);

			int hiddenBoardId = insertBoard("Template hidden " + suffix);
			grantBoardPermission(hiddenBoardId, permissionIdOf("ZFGC_SITE_ADMIN"));
			String secretLead = "TemplateSecretLead" + suffix;
			int threadId = postThread(insider.token(), "Template hidden thread " + suffix, secretLead);
			moveIntoBoard(threadId, messageIdAt(threadId, 1), hiddenBoardId);

			String payload = """
					{"content": "[template=announcementlead]threadId=%d[/template]tail", "scope": "FORUM"}
					""".formatted(threadId);

			String forTheOutsider = json.readTree(mockMvc.perform(post("/content/preview")
					.header("Authorization", "Bearer " + outsider.token())
					.contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isOk()).andReturn().getResponse().getContentAsString())
					.get("contentParsed").asString();

			assertFalse(forTheOutsider.contains(secretLead),
					"passing the real viewer to template data sources must not widen what they can see; the "
							+ "source enforces its own permissions and an unreadable thread renders blank: "
							+ forTheOutsider);
			assertTrue(forTheOutsider.contains("tail"),
					"a blank widget degrades in place: " + forTheOutsider);
		}

		@Test
		void memberStartsDiscussionThread() throws Exception {
			setSystemConfig("cms_discussion_board_id", "1");
			TestUser member = createUser("disc_" + suffix);

			MvcResult projectResult = mockMvc.perform(post("/projects/majora-s-mask-3d/discussion")
					.header("Authorization", "Bearer " + member.token()))
					.andExpect(status().isOk())
					.andReturn();
			int projectThreadId = json.readTree(projectResult.getResponse().getContentAsString())
					.get("threadId").asInt();
			assertEquals(1, threadCount(criteria -> criteria.andThreadIdEqualTo(projectThreadId)
					.andBoardIdEqualTo(1)),
					"the project discussion thread must open on the configured discussion board");
			assertEquals(1, contentEntityCount(criteria -> criteria.andSlugEqualTo("majora-s-mask-3d")
					.andThreadIdEqualTo(projectThreadId)),
					"the project must link its freshly opened discussion thread");

			mockMvc.perform(post("/projects/majora-s-mask-3d/discussion")
					.header("Authorization", "Bearer " + member.token()))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.threadId").value(projectThreadId));

			MvcResult resourceResult = mockMvc.perform(post("/resources/eiji-aonuma-zelda-credits-list/discussion")
					.header("Authorization", "Bearer " + member.token()))
					.andExpect(status().isOk())
					.andReturn();
			int resourceThreadId = json.readTree(resourceResult.getResponse().getContentAsString())
					.get("threadId").asInt();
			assertEquals(1, threadCount(criteria -> criteria.andThreadIdEqualTo(resourceThreadId)
					.andBoardIdEqualTo(1)),
					"the resource discussion thread must open on the configured discussion board");
			assertEquals(1, contentEntityCount(criteria -> criteria
					.andSlugEqualTo("eiji-aonuma-zelda-credits-list").andThreadIdEqualTo(resourceThreadId)),
					"the resource must link its freshly opened discussion thread");
		}

		@Test
		void readOnlyMemberCannotStartCmsDiscussion() throws Exception {
			setSystemConfig("cms_discussion_board_id", "1");
			unlinkDiscussionThread("majora-s-mask-3d");

			TestUser member = createUser("rodisc_" + suffix);
			grantUserPermission(member.id(), 9);

			long board1ThreadsBefore = threadCount(criteria -> criteria.andBoardIdEqualTo(1));

			mockMvc.perform(post("/projects/majora-s-mask-3d/discussion")
					.header("Authorization", "Bearer " + member.token()))
					.andExpect(status().isForbidden());

			assertEquals(board1ThreadsBefore, threadCount(criteria -> criteria.andBoardIdEqualTo(1)),
					"a read-only member must not open a CMS discussion thread");
			assertEquals(1, contentEntityCount(criteria -> criteria.andSlugEqualTo("majora-s-mask-3d")
					.andThreadIdIsNull()),
					"the project must remain unlinked after the rejected read-only discussion attempt");
		}

		private ResultActions editMessage(String token, int messageId, String body) throws Exception {
			return mockMvc.perform(put("/message/" + messageId)
					.header("Authorization", "Bearer " + token)
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"body\": \"" + body + "\"}"));
		}

		@Test
		void aPostAuthoredAsMarkdownIsStoredAndRenderedThroughTheMarkdownLane() throws Exception {
			TestUser author = createUser("mdpost_" + suffix);
			int threadId = postThread(author.token(), "Markdown thread " + suffix, "opening **post** in bbcode");

			mockMvc.perform(post("/message/" + threadId)
					.header("Authorization", "Bearer " + author.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"body\": \"# heading\\n\\nsome **strong** words\", "
							+ "\"contentFormat\": \"MARKDOWN\"}"))
					.andExpect(status().isCreated());

			int markdownMessageId = messageIdAt(threadId, 2);
			assertEquals("MARKDOWN", currentRevisionContentFormat(markdownMessageId),
					"a post authored as markdown must persist its own format, not the engine default");

			JsonNode messages = threadMessages(threadId);
			assertTrue(renderedBodyOfPost(messages, 2).contains("<strong>strong</strong>"),
					"the markdown post must reach the markdown lane: " + renderedBodyOfPost(messages, 2));
			assertTrue(renderedBodyOfPost(messages, 2).contains("<h1"),
					"an ATX heading only renders as a heading through the markdown lane: "
							+ renderedBodyOfPost(messages, 2));
			assertFalse(renderedBodyOfPost(messages, 1).contains("<strong>"),
					"the bbcode post in the same thread must still render through the bbcode lane: "
							+ renderedBodyOfPost(messages, 1));
		}

		@Test
		void flippingTheSiteDefaultNeverRestampsOrRerendersPostsThatAlreadyHaveAFormat() throws Exception {
			TestUser author = createUser("mdflip_" + suffix);
			int threadId = postThread(author.token(), "Flip thread " + suffix, "**not** markdown [b]bbcode[/b]");
			int bbcodeMessageId = messageIdAt(threadId, 1);
			assertEquals("BBCODE", currentRevisionContentFormat(bbcodeMessageId));

			setSystemConfig("authoring_default_content_format", "MARKDOWN");
			try {
				editMessage(author.token(), bbcodeMessageId, "still **not** markdown [b]bbcode[/b]")
						.andExpect(status().isOk());

				assertEquals("BBCODE", currentRevisionContentFormat(bbcodeMessageId),
						"an edit that names no format must inherit the format the post already had; "
								+ "stamping the site default here would silently re-render existing bbcode");
				assertFalse(renderedBodyOfPost(threadMessages(threadId), 1).contains("<strong>"),
						"flipping the authoring default must not change how existing content renders");

				mockMvc.perform(post("/message/" + threadId)
						.header("Authorization", "Bearer " + author.token())
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"body\": \"brand new **markdown** post\"}"))
						.andExpect(status().isCreated());
				assertEquals("MARKDOWN", currentRevisionContentFormat(messageIdAt(threadId, 2)),
						"content with no predecessor takes the site authoring default");

				mockMvc.perform(get("/message/template")
						.param("threadId", String.valueOf(threadId))
						.header("Authorization", "Bearer " + author.token()))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.message.currentMessage.contentFormat").value("MARKDOWN"));
			} finally {
				setSystemConfig("authoring_default_content_format", "BBCODE");
			}
		}

		@Test
		void aRevisionNamingAFormatOutsideTheLookupTableIsRejectedByTheDatabase() throws Exception {
			TestUser author = createUser("mdfk_" + suffix);
			int threadId = postThread(author.token(), "Format key thread " + suffix, "op body");
			MessageHistoryDbo smuggled = new MessageHistoryDbo();
			smuggled.setMessageId(messageIdAt(threadId, 1));
			smuggled.setMessageText("a body in a format nothing can render");
			smuggled.setContentFormat("PARCHMENT");

			assertThrows(DataAccessException.class,
					() -> messageHistoryDboMapper.insertSelective(smuggled),
					"message_history.content_format must be constrained to the lookup table, or a revision "
							+ "no renderer knows about lands in the forum and silently renders as bbcode");
		}

		@Test
		void thePreviewEndpointRendersTheFormatTheEditorIsComposingIn() throws Exception {
			TestUser author = createUser("mdprev_" + suffix);

			assertTrue(previewedAs(author.token(), "some **strong** words", "MARKDOWN")
					.contains("<strong>strong</strong>"),
					"a markdown preview must reach the markdown lane, or the editor lies about what will "
							+ "be published");
			assertFalse(previewedAs(author.token(), "some **strong** words", "BBCODE")
					.contains("<strong>"),
					"a bbcode preview must leave markdown emphasis literal");
			assertFalse(previewedAs(author.token(), "some **strong** words", null).contains("<strong>"),
					"an unspecified preview format follows the site authoring default, which is bbcode here");
		}

		private String previewedAs(String token, String content, String contentFormat) throws Exception {
			String requestBody = contentFormat == null
					? "{\"content\": \"" + content + "\", \"scope\": \"FORUM\"}"
					: "{\"content\": \"" + content + "\", \"scope\": \"FORUM\", \"contentFormat\": \""
							+ contentFormat + "\"}";
			MvcResult result = mockMvc.perform(post("/content/preview")
					.header("Authorization", "Bearer " + token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(requestBody))
					.andExpect(status().isOk())
					.andReturn();
			return json.readTree(result.getResponse().getContentAsString()).get("contentParsed").asString();
		}

		@Test
		void theConvertEndpointRewritesTheSourceInBothDirections() throws Exception {
			TestUser author = createUser("mdconv_" + suffix);

			JsonNode toMarkdown = converted(author.token(),
					"[b]bold[/b] and 2 * 3\\n\\n[quote msg=7]kept[/quote]", "BBCODE", "MARKDOWN");
			assertEquals("**bold** and 2 \\* 3\n\n[quote msg=7]kept[/quote]",
					toMarkdown.get("content").asString(),
					"the toggle converts what markdown can express, escapes what it would otherwise read as "
							+ "markup, and leaves a source-referencing quote as bbcode");
			assertTrue(toMarkdown.get("notes").isEmpty(),
					"nothing is lost converting to markdown, because the markdown lane renders bbcode too");

			JsonNode toBBCode = converted(author.token(),
					"## Title\\n\\nsome **bold** and an `[b]example[/b]` span", "MARKDOWN", "BBCODE");
			assertEquals("[h2]Title[/h2]\n\nsome [b]bold[/b] and an `[b]example[/b]` span",
					toBBCode.get("content").asString());
			assertEquals(2, toBBCode.get("notes").size(),
					"markdown the bbcode lane will render literally has to be named, or the author only finds "
							+ "out after publishing: " + toBBCode.get("notes"));
		}

		@Test
		void theConvertEndpointReportsAPostTheOtherFormatWillNotRenderTheSameWay() throws Exception {
			TestUser author = createUser("mdconvnote_" + suffix);

			JsonNode converted = converted(author.token(),
					"[quote author=mgzero thread=3 msg=14]<br /> ???<br /><br />Obviously Stove, and that's not "
							+ "even an option!<br />[/quote]<br />Yes, but we're all Steve.",
					"BBCODE", "MARKDOWN");

			assertEquals("[quote author=mgzero thread=3 msg=14]<br /> ???<br /><br />Obviously Stove, and that's "
					+ "not even an option!<br />[/quote]<br />Yes, but we're all Steve.",
					converted.get("content").asString(),
					"the converter leaves this migrated post alone, which is correct and is exactly why the "
							+ "warning cannot come from predicting which constructs it rewrites");
			assertEquals(1, converted.get("notes").size(), converted.get("notes").toString());
			assertTrue(converted.get("notes").get(0).asString().contains("[quote]"),
					"the endpoint renders its own output and compares, so it can name the construct the other "
							+ "format prints as plain text: " + converted.get("notes"));
		}

		private JsonNode converted(String token, String content, String fromContentFormat, String toContentFormat)
				throws Exception {
			MvcResult result = mockMvc.perform(post("/content/convert")
					.header("Authorization", "Bearer " + token)
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"content\": \"" + content + "\", \"fromContentFormat\": \"" + fromContentFormat
							+ "\", \"toContentFormat\": \"" + toContentFormat + "\"}"))
					.andExpect(status().isOk())
					.andReturn();
			return json.readTree(result.getResponse().getContentAsString());
		}

		@Test
		void theConvertEndpointRefusesAFormatNothingCanAuthor() throws Exception {
			TestUser author = createUser("mdconvbad_" + suffix);

			mockMvc.perform(post("/content/convert")
					.header("Authorization", "Bearer " + author.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"content\": \"x\", \"fromContentFormat\": \"BBCODE\","
							+ " \"toContentFormat\": \"PARCHMENT\"}"))
					.andExpect(status().isBadRequest());

			mockMvc.perform(post("/content/convert")
					.header("Authorization", "Bearer " + author.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"content\": \"x\", \"toContentFormat\": \"MARKDOWN\"}"))
					.andExpect(status().isBadRequest());

			mockMvc.perform(post("/content/convert")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"content\": \"x\", \"fromContentFormat\": \"BBCODE\","
							+ " \"toContentFormat\": \"MARKDOWN\"}"))
					.andExpect(status().isUnauthorized());
		}

		@Test
		void aPostNamingAnUnrenderableFormatIsRefused() throws Exception {
			TestUser author = createUser("mdbad_" + suffix);
			int threadId = postThread(author.token(), "Bad format thread " + suffix, "op");

			mockMvc.perform(post("/message/" + threadId)
					.header("Authorization", "Bearer " + author.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"body\": \"<p>raw html</p>\", \"contentFormat\": \"HTML\"}"))
					.andExpect(status().isBadRequest());

			assertEquals(1, messageDboMapper.countByExample(messagesInThread(threadId)),
					"a refused format must not leave a post behind");
		}

		private MessageDboExample messagesInThread(int threadId) {
			MessageDboExample example = new MessageDboExample();
			example.createCriteria().andThreadIdEqualTo(threadId);
			return example;
		}

		private String currentRevisionContentFormat(int messageId) {
			return messageHistoryDboMapper.selectByExample(messageHistoryWhere(
					criteria -> criteria.andMessageIdEqualTo(messageId).andCurrentFlagEqualTo(true)))
					.get(0).getContentFormat();
		}

		private JsonNode threadMessages(int threadId) throws Exception {
			MvcResult result = mockMvc.perform(get("/thread/" + threadId)
					.param("page", "1").param("pageSize", "10"))
					.andExpect(status().isOk())
					.andReturn();
			return json.readTree(result.getResponse().getContentAsString()).get("messages");
		}

		private String renderedBodyOfPost(JsonNode messages, int postInThread) {
			for (JsonNode message : messages)
				if (message.get("postInThread").asInt() == postInThread)
					return message.get("currentMessage").get("messageText").asString();
			throw new AssertionError("post " + postInThread + " is not in the thread response");
		}

		private Set<String> allowedMessageActions(String token, int messageId) throws Exception {
			MvcResult result = mockMvc.perform(get("/message/" + messageId + "/allowed-actions")
					.header("Authorization", "Bearer " + token))
					.andExpect(status().isOk())
					.andReturn();
			Set<String> actions = new HashSet<>();
			for (JsonNode action : json.readTree(result.getResponse().getContentAsString()))
				actions.add(action.asString());
			return actions;
		}

		private String currentBodyOf(int messageId) {
			return messageHistoryDboMapper.selectByExample(messageHistoryWhere(
					criteria -> criteria.andMessageIdEqualTo(messageId).andCurrentFlagEqualTo(true)))
					.get(0).getMessageText();
		}

		private void moveThreadToBoard(int threadId, int boardId) {
			ThreadDbo relocatedThread = new ThreadDbo();
			relocatedThread.setThreadId(threadId);
			relocatedThread.setBoardId(boardId);
			threadDboMapper.updateByPrimaryKeySelective(relocatedThread);
			MessageDbo relocatedMessages = new MessageDbo();
			relocatedMessages.setBoardId(boardId);
			MessageDboExample inThread = new MessageDboExample();
			inThread.createCriteria().andThreadIdEqualTo(threadId);
			messageDboMapper.updateByExampleSelective(relocatedMessages, inThread);
			forumService.evictUnfilteredForumCache();
		}
	}

	@Nested
	class Reactions {

		@Test
		void authenticatedUserCanReactToAMessage() throws Exception {
			TestUser member = createUser("react_" + suffix);

			int threadId = postThread(member.token(), "React thread " + suffix, "op body");
			int messageId = messageIdAt(threadId, 1);
			int reactionTypeId = anyReactionTypeId();

			String payload = """
					{"reactableType": "MESSAGE", "reactableId": %d, "reactionTypeId": %d}
					""".formatted(messageId, reactionTypeId);

			mockMvc.perform(post("/reactions")
					.header("Authorization", "Bearer " + member.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content(payload))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.reactableType").value("MESSAGE"))
					.andExpect(jsonPath("$.reactableId").value(messageId))
					.andExpect(jsonPath("$.totalCount").value(1))
					.andExpect(jsonPath("$.userReactionTypeId").value(reactionTypeId));
		}

		@Test
		void readOnlyMemberCannotReact() throws Exception {
			TestUser member = createUser("roreact_" + suffix);

			int threadId = postThread(member.token(), "RO react thread " + suffix, "op body");
			int messageId = messageIdAt(threadId, 1);
			int reactionTypeId = anyReactionTypeId();

			String payload = """
					{"reactableType": "MESSAGE", "reactableId": %d, "reactionTypeId": %d}
					""".formatted(messageId, reactionTypeId);

			mockMvc.perform(post("/reactions")
					.header("Authorization", "Bearer " + member.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content(payload))
					.andExpect(status().isOk());

			grantUserPermission(member.id(), 9);

			mockMvc.perform(post("/reactions")
					.header("Authorization", "Bearer " + member.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content(payload))
					.andExpect(status().isForbidden());
		}

		@Test
		void reactingToAMessageOnAnUnreadableBoardIsRefused() throws Exception {
			TestUser member = createUser("hidreact_" + suffix);

			int hiddenBoardId = insertBoard("Hidden react board " + suffix);
			grantBoardPermission(hiddenBoardId, permissionIdOf("ZFGC_SITE_ADMIN"));
			int hiddenThreadId = insertThread("Hidden react thread " + suffix, hiddenBoardId, member.id());
			insertMessage(member.id(), hiddenThreadId, hiddenBoardId);
			int hiddenMessageId = messageIdAt(hiddenThreadId, 1);

			String payload = """
					{"reactableType": "MESSAGE", "reactableId": %d, "reactionTypeId": %d}
					""".formatted(hiddenMessageId, anyReactionTypeId());

			mockMvc.perform(post("/reactions")
					.header("Authorization", "Bearer " + member.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content(payload))
					.andExpect(status().isForbidden());
		}

		@Test
		void removingAReactionClearsItAndIsIdempotent() throws Exception {
			TestUser member = createUser("unreact_" + suffix);

			int threadId = postThread(member.token(), "Unreact thread " + suffix, "op body");
			int messageId = messageIdAt(threadId, 1);
			int reactionTypeId = anyReactionTypeId();

			String payload = """
					{"reactableType": "MESSAGE", "reactableId": %d, "reactionTypeId": %d}
					""".formatted(messageId, reactionTypeId);

			mockMvc.perform(post("/reactions")
					.header("Authorization", "Bearer " + member.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content(payload))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.totalCount").value(1));

			mockMvc.perform(delete("/reactions")
					.header("Authorization", "Bearer " + member.token())
					.param("reactableType", "MESSAGE")
					.param("reactableId", String.valueOf(messageId)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.totalCount").value(0))
					.andExpect(jsonPath("$.userReactionTypeId").doesNotExist());

			mockMvc.perform(delete("/reactions")
					.header("Authorization", "Bearer " + member.token())
					.param("reactableType", "MESSAGE")
					.param("reactableId", String.valueOf(messageId)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.totalCount").value(0));
		}

		@Test
		void unknownReactionTypeIsRejectedWithBadRequest() throws Exception {
			TestUser member = createUser("badtype_" + suffix);

			int threadId = postThread(member.token(), "Bad type thread " + suffix, "op body");
			int messageId = messageIdAt(threadId, 1);

			String payload = """
					{"reactableType": "MESSAGE", "reactableId": %d, "reactionTypeId": 999999}
					""".formatted(messageId);

			mockMvc.perform(post("/reactions")
					.header("Authorization", "Bearer " + member.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content(payload))
					.andExpect(status().isBadRequest());
		}
	}

	@Nested
	class PostingLimits {

		@Test
		void ownerCannotRestoreOrDoubleDeleteAndLockedThreadsBlockOwnerDeletes() throws Exception {
			TestUser owner = createUser("authz_" + suffix);
			String adminToken = getAdminToken();

			int threadId = postThread(owner.token(), "Authz matrix " + suffix, "OP");
			postReply(owner.token(), threadId, "first reply");
			postReply(owner.token(), threadId, "second reply");
			int firstReplyId = messageIdAt(threadId, 2);
			int secondReplyId = messageIdAt(threadId, 3);

			mockMvc.perform(put("/thread/" + threadId + "/lockToggle")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk());
			mockMvc.perform(delete("/message/" + firstReplyId)
					.header("Authorization", "Bearer " + owner.token()))
					.andExpect(status().isForbidden());
			mockMvc.perform(put("/thread/" + threadId + "/lockToggle")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk());

			mockMvc.perform(delete("/message/" + secondReplyId)
					.header("Authorization", "Bearer " + owner.token()))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.outcome").value("RECYCLED"));
			mockMvc.perform(delete("/message/" + secondReplyId)
					.header("Authorization", "Bearer " + owner.token()))
					.andExpect(status().isForbidden());

			int wrapperThreadId = messageDboMapper.selectByPrimaryKey(secondReplyId).getThreadId();
			mockMvc.perform(put("/message/" + secondReplyId + "/restore")
					.header("Authorization", "Bearer " + owner.token()))
					.andExpect(status().isForbidden());
			mockMvc.perform(put("/thread/" + wrapperThreadId + "/restore")
					.header("Authorization", "Bearer " + owner.token()))
					.andExpect(status().isForbidden());
		}

		@Test
		void memberCannotModifyAnotherMembersPost() throws Exception {
			TestUser owner = createUser("authz_" + suffix);
			TestUser stranger = createUser("strgr_" + suffix);

			int threadId = postThread(owner.token(), "Authz matrix " + suffix, "OP");
			postReply(owner.token(), threadId, "first reply");
			int firstReplyId = messageIdAt(threadId, 2);

			mockMvc.perform(delete("/message/" + firstReplyId)
					.header("Authorization", "Bearer " + stranger.token()))
					.andExpect(status().isForbidden());
		}
	}

	private int insertBoard(String boardName) {
		return insertBoard(boardName, null, 0, null);
	}

	private int insertBoard(String boardName, Integer categoryId, int seqno, Integer parentBoardId) {
		BoardDbo board = new BoardDbo();
		board.setBoardName(boardName);
		board.setCategoryId(categoryId);
		board.setSeqno(seqno);
		board.setParentBoardId(parentBoardId);
		boardDboMapper.insertSelective(board);
		assertNotNull(board.getBoardId());
		return board.getBoardId();
	}

	private void grantBoardPermission(int boardId, int permissionId) {
		BrBoardPermissionDbo grant = new BrBoardPermissionDbo();
		grant.setBoardId(boardId);
		grant.setPermissionId(permissionId);
		brBoardPermissionDboMapper.insertSelective(grant);
	}

	private int insertThread(String threadName, int boardId, int createdUserId) {
		ThreadDbo thread = new ThreadDbo();
		thread.setThreadName(threadName);
		thread.setBoardId(boardId);
		thread.setCreatedUserId(createdUserId);
		threadDboMapper.insertSelective(thread);
		assertNotNull(thread.getThreadId());
		return thread.getThreadId();
	}

	private void moveIntoBoard(int threadId, int messageId, int boardId) {
		ThreadDbo thread = new ThreadDbo();
		thread.setThreadId(threadId);
		thread.setBoardId(boardId);
		threadDboMapper.updateByPrimaryKeySelective(thread);
		MessageDbo message = new MessageDbo();
		message.setMessageId(messageId);
		message.setBoardId(boardId);
		messageDboMapper.updateByPrimaryKeySelective(message);
	}

	private void insertMessage(int ownerId, int threadId, int boardId) {
		MessageDbo message = new MessageDbo();
		message.setOwnerId(ownerId);
		message.setThreadId(threadId);
		message.setBoardId(boardId);
		message.setPostInThread(1);
		messageDboMapper.insertSelective(message);
	}

	private int insertAttachment(int messageId, int uploaderUserId) {
		Integer contentTypeId = contentResourceTypeDboMapper
				.selectByExample(new ContentResourceTypeDboExample()).stream()
				.map(ContentResourceTypeDbo::getContentResourceTypeId).min(Integer::compareTo).orElse(null);
		assertNotNull(contentTypeId, "the fixture must seed at least one content resource type");
		ContentResourceDbo resource = new ContentResourceDbo();
		resource.setContentTypeId(contentTypeId);
		resource.setUploadedUserId(uploaderUserId);
		resource.setFilename("attachment-" + suffix + ".zip");
		resource.setChecksum("checksum-" + suffix);
		resource.setFileExt("zip");
		resource.setMimeType("application/zip");
		contentResourceDboMapper.insertSelective(resource);
		assertNotNull(resource.getContentResourceId());
		FileAttachmentDbo attachment = new FileAttachmentDbo();
		attachment.setMessageId(messageId);
		attachment.setContentResourceId(resource.getContentResourceId());
		attachment.setActiveFlag(true);
		fileAttachmentDboMapper.insertSelective(attachment);
		return resource.getContentResourceId();
	}

	private int permissionIdOf(String permissionCode) {
		PermissionDboExample example = new PermissionDboExample();
		example.createCriteria().andPermissionCodeEqualTo(permissionCode);
		List<PermissionDbo> permissions = permissionDboMapper.selectByExample(example);
		assertEquals(1, permissions.size(), permissionCode + " must exist exactly once");
		return permissions.get(0).getPermissionId();
	}

	private User actorHolding(String... permissionCodes) {
		User actor = new User();
		for (String permissionCode : permissionCodes) {
			Permission permission = new Permission();
			permission.setPermissionId(permissionIdOf(permissionCode));
			permission.setPermissionCode(permissionCode);
			actor.getPermissions().add(permission);
		}
		return actor;
	}

	private int anyReactionTypeId() {
		Integer reactionTypeId = reactionTypeDboMapper.selectByExample(new ReactionTypeDboExample()).stream()
				.map(ReactionTypeDbo::getReactionTypeId).min(Integer::compareTo).orElse(null);
		assertNotNull(reactionTypeId);
		return reactionTypeId;
	}

	private void grantUserPermission(int userId, int permissionId) {
		BrUserPermissionDbo grant = new BrUserPermissionDbo();
		grant.setUserId(userId);
		grant.setUserPermissionId(permissionId);
		brUserPermissionDboMapper.insertSelective(grant);
	}

	private void setSignature(int userId, String signature) {
		UserBioInfoDbo bioInfo = new UserBioInfoDbo();
		bioInfo.setSignature(signature);
		UserBioInfoDboExample example = new UserBioInfoDboExample();
		example.createCriteria().andUserIdEqualTo(userId);
		userBioInfoDboMapper.updateByExampleSelective(bioInfo, example);
	}

	private void setSystemConfig(String configKey, String configValue) {
		SystemConfigDbo config = new SystemConfigDbo();
		config.setConfigKey(configKey);
		config.setConfigValue(configValue);
		if (systemConfigDboMapper.selectByPrimaryKey(configKey) == null)
			systemConfigDboMapper.insertSelective(config);
		else
			systemConfigDboMapper.updateByPrimaryKeySelective(config);
	}

	private void unlinkDiscussionThread(String slug) {
		ContentEntityDboExample example = new ContentEntityDboExample();
		example.createCriteria().andSlugEqualTo(slug);
		for (ContentEntityDbo entity : contentEntityDboMapper.selectByExample(example)) {
			entity.setThreadId(null);
			contentEntityDboMapper.updateByPrimaryKey(entity);
		}
	}

	private long threadCount(Consumer<ThreadDboExample.Criteria> criteria) {
		ThreadDboExample example = new ThreadDboExample();
		criteria.accept(example.createCriteria());
		return threadDboMapper.countByExample(example);
	}

	private long contentEntityCount(Consumer<ContentEntityDboExample.Criteria> criteria) {
		ContentEntityDboExample example = new ContentEntityDboExample();
		criteria.accept(example.createCriteria());
		return contentEntityDboMapper.countByExample(example);
	}

	private MessageHistoryDboExample messageHistoryWhere(Consumer<MessageHistoryDboExample.Criteria> criteria) {
		MessageHistoryDboExample example = new MessageHistoryDboExample();
		criteria.accept(example.createCriteria());
		return example;
	}
}
