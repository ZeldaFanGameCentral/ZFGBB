package com.zfgc.zfgbb.system;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ConcurrentModificationException;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.parallel.Isolated;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.bbcode.AuthoredOpener;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarLoader;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarHolder;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeParser;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeDocument;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeNode;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeTag;
import com.zfgc.zfgbb.content.renderer.ContentRenderingService;
import com.zfgc.zfgbb.dao.CategoryDao;
import com.zfgc.zfgbb.dataprovider.forum.BBCodeDataProvider;
import com.zfgc.zfgbb.dbo.AttributeDataTypeDbo;
import com.zfgc.zfgbb.dbo.AttributeDataTypeDboExample;
import com.zfgc.zfgbb.dbo.BBCodeAttributeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDbo;
import com.zfgc.zfgbb.dbo.BBCodeConfigDbo;
import com.zfgc.zfgbb.dbo.CategoryDbo;
import com.zfgc.zfgbb.dbo.ContentTemplateDbo;
import com.zfgc.zfgbb.dbo.ContentTemplateDboExample;
import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import com.zfgc.zfgbb.dbo.PersonalMessageDbo;
import com.zfgc.zfgbb.dbo.PersonalMessageDboExample;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDbo;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDboExample;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.mappers.AttributeDataTypeDboMapper;
import com.zfgc.zfgbb.mappers.BBCodeAttributeDboMapper;
import com.zfgc.zfgbb.mappers.BBCodeAttributeModeDboMapper;
import com.zfgc.zfgbb.mappers.BBCodeConfigDboMapper;
import com.zfgc.zfgbb.mappers.ContentTemplateDboMapper;
import com.zfgc.zfgbb.mappers.MessageHistoryDboMapper;
import com.zfgc.zfgbb.mappers.PersonalMessageDboMapper;
import com.zfgc.zfgbb.mappers.UserDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageRevisionDboMapper;
import com.zfgc.zfgbb.exception.InvalidBBCodeGrammarException;
import com.zfgc.zfgbb.model.forum.AttributeDataType;
import com.zfgc.zfgbb.model.forum.BBCodeAttribute;
import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;
import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

import jakarta.servlet.http.Cookie;
import tools.jackson.databind.JsonNode;

class AdminTest extends PostgresIntegrationTest {

	@Autowired
	private UserDboMapper userDboMapper;

	private static Cookie accessCookie() {
		return new Cookie("zfgbb_access_token", "fake-jwt-value");
	}

	@Nested
	class OptimisticLocking {

		@Autowired
		private CategoryDao categoryDao;

		private CategoryDbo persistedCategory() {
			CategoryDbo category = new CategoryDbo();
			category.setCategoryName("Locking " + System.nanoTime());
			category.setCategoryOrder((short) 1);
			categoryDao.save(category);
			return categoryDao.find(category.getCategoryId()).orElseThrow();
		}

		@Test
		void aWriterHoldingAStaleCopyIsRejectedRatherThanSilentlyOverwritingTheWinner() {
			CategoryDbo staleCopy = persistedCategory();
			CategoryDbo winner = categoryDao.find(staleCopy.getCategoryId()).orElseThrow();

			winner.setCategoryName("written by the winner");
			categoryDao.save(winner);

			staleCopy.setCategoryName("written by the loser");
			assertThrows(ConcurrentModificationException.class, () -> categoryDao.save(staleCopy),
					"a save carrying the version the caller read must fail once another writer has "
							+ "moved the row, instead of silently losing that writer's update");

			assertEquals("written by the winner",
					categoryDao.find(staleCopy.getCategoryId()).orElseThrow().getCategoryName(),
					"the rejected write must not have reached the database");
		}

		@Test
		void aWriterHoldingTheCurrentVersionStillWinsAndAdvancesIt() {
			CategoryDbo current = persistedCategory();
			OffsetDateTime versionRead = current.getUpdatedTs();

			current.setCategoryName("first write");
			categoryDao.save(current);

			CategoryDbo reloaded = categoryDao.find(current.getCategoryId()).orElseThrow();
			assertEquals("first write", reloaded.getCategoryName());
			assertTrue(reloaded.getUpdatedTs().isAfter(versionRead),
					"the trigger must advance updated_ts, or the same version would keep matching and "
							+ "the guard would never fire");
		}

		@Test
		void savingARowThatNoLongerExistsReportsItAsMissingRatherThanAsAConflict() {
			CategoryDbo removed = persistedCategory();
			categoryDao.delete(removed.getCategoryId());

			removed.setCategoryName("written after deletion");
			assertThrows(ZfgcNotFoundException.class, () -> categoryDao.save(removed),
					"a vanished row and a concurrently-updated row both match zero rows, so save must "
							+ "tell them apart");
		}
	}

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void aTestConfigurationDeclaredElsewhereIsNotScannedIntoThisContext() {
		assertTrue(applicationContext.getBeansOfType(PlatformTest.ContextPoisoningMarker.class).isEmpty(),
				"PlatformTest declares a @TestConfiguration under com.zfgc.zfgbb, so the application's "
						+ "explicit @ComponentScan must keep Boot's TypeExcludeFilter; without it every test "
						+ "configuration in the tree is scanned into every integration context and can "
						+ "silently replace real beans");
	}

	@Nested
	class MigrationEndpointGuards {

		@Test
		void anonymousListConflictsRequiresAuth() throws Exception {
			mockMvc.perform(get("/admin/migrate/conflicts"))
					.andExpect(status().isUnauthorized());
		}

		@Test
		void anonymousScanConflictsIsUnauthorized() throws Exception {
			mockMvc.perform(post("/admin/migrate/conflicts/scan")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{}"))
					.andExpect(status().isUnauthorized());
		}

		@Test
		void cookieAuthedScanConflictsWithoutCsrfIsForbidden() throws Exception {
			mockMvc.perform(post("/admin/migrate/conflicts/scan")
					.cookie(accessCookie())
					.contentType(MediaType.APPLICATION_JSON)
					.content("{}"))
					.andExpect(status().isForbidden());
		}

		@Test
		void anonymousListJobsRequiresAuth() throws Exception {
			mockMvc.perform(get("/admin/migrate/jobs"))
					.andExpect(status().isUnauthorized());
		}

		@Test
		void anonymousSubmitJobIsUnauthorized() throws Exception {
			mockMvc.perform(post("/admin/migrate/jobs")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{}"))
					.andExpect(status().isUnauthorized());
		}

		@Test
		void cookieAuthedSubmitJobWithoutCsrfIsForbidden() throws Exception {
			mockMvc.perform(post("/admin/migrate/jobs")
					.cookie(accessCookie())
					.contentType(MediaType.APPLICATION_JSON)
					.content("{}"))
					.andExpect(status().isForbidden());
		}
	}

	@Nested
	class Console {

		@Autowired
		private BBCodeConfigDboMapper bbCodeConfigDboMapper;

		@Autowired
		private BBCodeAttributeModeDboMapper bbCodeAttributeModeDboMapper;

		@Autowired
		private BBCodeAttributeDboMapper bbCodeAttributeDboMapper;

		@Autowired
		private BBCodeGrammarLoader grammarLoader;

		@Autowired
		private BBCodeGrammarHolder grammarHolder;

		private BBCodeConfigDbo insertTag() {
			BBCodeConfigDbo tag = new BBCodeConfigDbo();
			tag.setCode("lane" + System.nanoTime());
			tag.setEndTag("</span>");
			tag.setProcessContentFlag(true);
			tag.setSelfClosingFlag(false);
			tag.setEnabledFlag(false);
			bbCodeConfigDboMapper.insert(tag);
			return tag;
		}

		private BBCodeAttributeModeDbo insertMode(BBCodeConfigDbo tag) {
			BBCodeAttributeModeDbo mode = new BBCodeAttributeModeDbo();
			mode.setBbCodeConfigId(tag.getBbCodeConfigId());
			mode.setContentIsAttributeFlag(false);
			mode.setOpenTag("<span class=\"bb-code-lane\">");
			mode.setCloseTag("</span>");
			mode.setOutputContentFlag(true);
			bbCodeAttributeModeDboMapper.insert(mode);
			return mode;
		}

		@Test
		void aNewTagInsertedThroughTheGeneratedMapperGetsAFreshIdAndCascadesOnDelete() {
			BBCodeConfigDbo tag = insertTag();
			assertTrue(tag.getBbCodeConfigId() > 1000,
					"the bbcode identity sequence must be seeded past the engine-seeded ids, "
							+ "or the first operator-created tag collides on primary key 1: "
							+ tag.getBbCodeConfigId());

			BBCodeAttributeModeDbo mode = insertMode(tag);
			BBCodeAttributeDbo attribute = new BBCodeAttributeDbo();
			attribute.setBbCodeAttributeModeId(mode.getBbCodeAttributeModeId());
			attribute.setAttributeIndex(0);
			attribute.setName("NAMELESS");
			attribute.setAttributeDataType("TEXT");
			bbCodeAttributeDboMapper.insert(attribute);

			assertEquals(tag.getCode(),
					bbCodeConfigDboMapper.selectByPrimaryKey(tag.getBbCodeConfigId()).getCode(),
					"a tag inserted through the generated mapper must read back");

			bbCodeConfigDboMapper.deleteByPrimaryKey(tag.getBbCodeConfigId());

			assertNull(bbCodeAttributeModeDboMapper.selectByPrimaryKey(mode.getBbCodeAttributeModeId()),
					"deleting a tag must cascade to its attribute modes");
			assertNull(bbCodeAttributeDboMapper.selectByPrimaryKey(attribute.getBbCodeAttributeId()),
					"deleting a tag must cascade to its attributes");
		}

		@Test
		void anAttributeDeclaringAnUnknownDataTypeCodeIsRejectedByTheDatabase() {
			BBCodeConfigDbo tag = insertTag();
			BBCodeAttributeModeDbo mode = insertMode(tag);
			BBCodeAttributeDbo attribute = new BBCodeAttributeDbo();
			attribute.setBbCodeAttributeModeId(mode.getBbCodeAttributeModeId());
			attribute.setAttributeIndex(0);
			attribute.setName("NAMELESS");
			attribute.setAttributeDataType("NOT_A_DATA_TYPE");

			try {
				assertThrows(DataAccessException.class, () -> bbCodeAttributeDboMapper.insert(attribute),
						"attribute_data_type must be constrained to the lookup table");
			} finally {
				bbCodeConfigDboMapper.deleteByPrimaryKey(tag.getBbCodeConfigId());
			}
		}

		@Test
		void adminListsUsersAndDeletesOneThroughTheConsole() throws Exception {
			String victimName = "condel_" + suffix;
			register(victimName, "password123");
			int victimId = userIdOf(victimName);
			String memberToken = login(victimName, "password123").get("accessToken").asString();
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();

			mockMvc.perform(get("/admin/users")
					.header("Authorization", "Bearer " + memberToken))
					.andExpect(status().isForbidden());

			MvcResult listResult = mockMvc.perform(get("/admin/users")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andReturn();
			boolean listed = false;
			for (JsonNode user : json.readTree(listResult.getResponse().getContentAsString()))
				if (victimId == user.get("userId").asInt())
					listed = true;
			assertTrue(listed, "the console listing must include the registered member");

			mockMvc.perform(post("/admin/users/delete")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"userId\": " + victimId + ", \"mode\": \"PURGE\"}"))
					.andExpect(status().isNoContent());
			assertNull(userDboMapper.selectByPrimaryKey(victimId),
					"a console purge must remove the account outright");
		}

		@Test
		void enablingATagWhoseGrammarCannotLoadLeavesTheRowDisabled() {
			BBCodeConfigDbo tag = new BBCodeConfigDbo();
			tag.setCode("lane" + System.nanoTime());
			tag.setEndTag("</span>");
			tag.setProcessContentFlag(true);
			tag.setSelfClosingFlag(false);
			tag.setEnabledFlag(false);
			tag.setSourceReferenceAttribute("msg");
			tag.setSourceReferenceResolver("no-such-resolver");
			bbCodeConfigDboMapper.insert(tag);
			insertMode(tag);

			try {
				assertThrows(InvalidBBCodeGrammarException.class,
						() -> grammarLoader.setBBCodeEnabled(tag.getCode(), true),
						"a tag declaring an unregistered source_reference_resolver must be refused");

				assertFalse(Boolean.TRUE.equals(
						bbCodeConfigDboMapper.selectByPrimaryKey(tag.getBbCodeConfigId()).getEnabledFlag()),
						"the toggle wrote the row and only then re-ran the grammar validator, so a refused tag "
								+ "stayed enabled in the database and every later boot failed in @PostConstruct "
								+ "with no way back through the console");
				assertDoesNotThrow(grammarLoader::loadBBCodeConfig,
						"whatever a refused toggle leaves behind must still boot");
			} finally {
				bbCodeConfigDboMapper.deleteByPrimaryKey(tag.getBbCodeConfigId());
				grammarLoader.loadBBCodeConfig();
			}
		}

		private void insertModeFilling(BBCodeConfigDbo tag, String attributeName, String dataTypeCode) {
			BBCodeAttributeModeDbo mode = new BBCodeAttributeModeDbo();
			mode.setBbCodeConfigId(tag.getBbCodeConfigId());
			mode.setContentIsAttributeFlag(false);
			mode.setOpenTag("<span style=\"--bb-lane: {{0}}\">");
			mode.setCloseTag("</span>");
			mode.setOutputContentFlag(true);
			bbCodeAttributeModeDboMapper.insert(mode);
			BBCodeAttributeDbo attribute = new BBCodeAttributeDbo();
			attribute.setBbCodeAttributeModeId(mode.getBbCodeAttributeModeId());
			attribute.setAttributeIndex(0);
			attribute.setName(attributeName);
			attribute.setAttributeDataType(dataTypeCode);
			bbCodeAttributeDboMapper.insert(attribute);
		}

		@Test
		void enablingATagWhoseCustomPropertyBindingsConflictLeavesTheRowDisabledAndTheEngineLive() {
			BBCodeConfigDbo tag = insertTag();
			insertModeFilling(tag, "one", "COLOR");
			insertModeFilling(tag, "two", "SIZE");

			try {
				InvalidBBCodeGrammarException refused = assertThrows(InvalidBBCodeGrammarException.class,
						() -> grammarLoader.setBBCodeEnabled(tag.getCode(), true),
						"two modes filling one --bb-* variable from two data types leave the sanitiser with no "
								+ "shape to enforce and must be refused");

				assertTrue(refused.getMessage().contains("--bb-lane"), refused.getMessage());
				assertFalse(Boolean.TRUE.equals(
						bbCodeConfigDboMapper.selectByPrimaryKey(tag.getBbCodeConfigId()).getEnabledFlag()),
						"the flag write has to roll back with the refusal");
				assertFalse(grammarHolder.current().configs().containsKey(tag.getCode().toUpperCase()),
						"the binding scan ran after the grammar was published, so a refusal rolled the database "
								+ "back while the refused candidate stayed live in memory until the next restart");
			} finally {
				bbCodeConfigDboMapper.deleteByPrimaryKey(tag.getBbCodeConfigId());
				grammarLoader.loadBBCodeConfig();
			}
		}

		@Test
		void theConsoleAnswersARefusedGrammarWithAFourHundredCarryingTheRefusal() throws Exception {
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
			BBCodeConfigDbo tag = new BBCodeConfigDbo();
			tag.setCode("lane" + System.nanoTime());
			tag.setEndTag("</span>");
			tag.setProcessContentFlag(true);
			tag.setSelfClosingFlag(false);
			tag.setEnabledFlag(false);
			tag.setSourceReferenceAttribute("msg");
			tag.setSourceReferenceResolver("no-such-resolver");
			bbCodeConfigDboMapper.insert(tag);
			insertMode(tag);

			try {
				MvcResult refusal = mockMvc.perform(put("/admin/bbcodes/" + tag.getCode())
						.header("Authorization", "Bearer " + adminToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"enabled\": true}"))
						.andExpect(status().isBadRequest())
						.andReturn();

				assertTrue(refusal.getResponse().getContentAsString().contains("no-such-resolver"),
						"InvalidBBCodeGrammarException had no handler, so a hand-edited grammar row the toggle "
								+ "re-validation refuses came back as a bare 500 with the reason only in the server "
								+ "log; the administrator who wrote the row has to be told which row it was: "
								+ refusal.getResponse().getContentAsString());
				assertFalse(Boolean.TRUE.equals(
						bbCodeConfigDboMapper.selectByPrimaryKey(tag.getBbCodeConfigId()).getEnabledFlag()),
						"and the flag write has to roll back with the refusal, or the console leaves a row behind "
								+ "that fails every later boot in @PostConstruct");
			} finally {
				bbCodeConfigDboMapper.deleteByPrimaryKey(tag.getBbCodeConfigId());
				grammarLoader.loadBBCodeConfig();
			}
		}

		@Test
		void adminTogglesABBCodeAndRenderingReflectsIt() throws Exception {
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
			String previewPayload = """
					{"content": "[move]toggle probe[/move]", "scope": "FORUM"}
					""";

			MvcResult listResult = mockMvc.perform(get("/admin/bbcodes")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andReturn();
			boolean moveListed = false;
			for (JsonNode toggle : json.readTree(listResult.getResponse().getContentAsString()))
				if ("move".equals(toggle.get("code").asString()))
					moveListed = true;
			assertTrue(moveListed, "the toggle console must list the seeded move bbcode");

			try {
				mockMvc.perform(put("/admin/bbcodes/move")
						.header("Authorization", "Bearer " + adminToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"enabled\": false}"))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.code").value("move"))
						.andExpect(jsonPath("$.enabled").value(false));

				MvcResult disabledResult = mockMvc.perform(post("/content/preview")
						.header("Authorization", "Bearer " + adminToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content(previewPayload))
						.andExpect(status().isOk())
						.andReturn();
				String disabledParsed = json.readTree(disabledResult.getResponse().getContentAsString())
						.get("contentParsed").asString();
				assertTrue(disabledParsed.contains("[move]"), "a disabled bbcode must stay literal");
				assertFalse(disabledParsed.contains("<marquee"), "a disabled bbcode must not render");
			} finally {
				mockMvc.perform(put("/admin/bbcodes/move")
						.header("Authorization", "Bearer " + adminToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"enabled\": true}"))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.enabled").value(true));
			}

			MvcResult enabledResult = mockMvc.perform(post("/content/preview")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content(previewPayload))
					.andExpect(status().isOk())
					.andReturn();
			String enabledParsed = json.readTree(enabledResult.getResponse().getContentAsString())
					.get("contentParsed").asString();
			assertTrue(enabledParsed.contains("<marquee"), "a re-enabled bbcode must render again");
		}
	}

	@Nested
	class AuthoringDefaults {

		@Test
		void theLookupTableOffersExactlyTheFormatsTheRendererCanProduce() {
			assertEquals(ContentFormat.authorableCodes().stream().sorted().toList(),
					testQueryHelperMapper.listContentFormatCodes(),
					"a content_format row the renderer cannot render is a trap: the foreign key would accept "
							+ "it, the renderer would silently fall back to bbcode, and an editor would offer "
							+ "it in a dropdown");
		}

		@Test
		void anAdminReadsAndWritesTheAuthoringDefaultAndAMemberCannot() throws Exception {
			String adminToken = getAdminToken();
			TestUser member = createUser("authdef_" + suffix);

			mockMvc.perform(get("/admin/site/authoring")
					.header("Authorization", "Bearer " + member.token()))
					.andExpect(status().isForbidden());
			mockMvc.perform(put("/admin/site/authoring")
					.header("Authorization", "Bearer " + member.token())
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"defaultContentFormat\": \"MARKDOWN\"}"))
					.andExpect(status().isForbidden());

			assertEquals("BBCODE", authoringConfig(adminToken).get("defaultContentFormat").asString());
			assertEquals(ContentFormat.authorableCodes(), contentFormatsOf(authoringConfig(adminToken)),
					"an editor cannot offer a format choice the console never tells it about");

			try {
				assertEquals("MARKDOWN", setAuthoringDefault(adminToken, "MARKDOWN")
						.get("defaultContentFormat").asString());

				MvcResult siteInfo = mockMvc.perform(get("/system/site"))
						.andExpect(status().isOk())
						.andReturn();
				JsonNode site = json.readTree(siteInfo.getResponse().getContentAsString());
				assertEquals("MARKDOWN", site.get("defaultContentFormat").asString(),
						"the anonymous site payload both editors already fetch must carry the default");
				assertEquals(ContentFormat.authorableCodes(), contentFormatsOf(site));

				mockMvc.perform(put("/admin/site/authoring")
						.header("Authorization", "Bearer " + adminToken)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"defaultContentFormat\": \"HTML\"}"))
						.andExpect(status().isBadRequest());
				assertEquals("MARKDOWN", authoringConfig(adminToken).get("defaultContentFormat").asString(),
						"a refused write must leave the stored default alone");
			} finally {
				setAuthoringDefault(adminToken, "BBCODE");
			}
		}

		private JsonNode authoringConfig(String token) throws Exception {
			MvcResult result = mockMvc.perform(get("/admin/site/authoring")
					.header("Authorization", "Bearer " + token))
					.andExpect(status().isOk())
					.andReturn();
			return json.readTree(result.getResponse().getContentAsString());
		}

		private JsonNode setAuthoringDefault(String token, String contentFormat) throws Exception {
			MvcResult result = mockMvc.perform(put("/admin/site/authoring")
					.header("Authorization", "Bearer " + token)
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"defaultContentFormat\": \"" + contentFormat + "\"}"))
					.andExpect(status().isOk())
					.andReturn();
			return json.readTree(result.getResponse().getContentAsString());
		}

		private List<String> contentFormatsOf(JsonNode payload) {
			List<String> codes = new ArrayList<>();
			for (JsonNode code : payload.get("contentFormats"))
				codes.add(code.asString());
			return codes;
		}
	}

	@Nested
	class BBCodeGrammarConformance {

		@Autowired
		private BBCodeGrammarLoader grammarLoader;

		@Autowired
		private BBCodeGrammarHolder grammarHolder;

		@Autowired
		private MessageHistoryDboMapper messageHistoryDboMapper;

		@Autowired
		private WikiPageRevisionDboMapper wikiPageRevisionDboMapper;

		@Autowired
		private ContentTemplateDboMapper contentTemplateDboMapper;

		@Autowired
		private PersonalMessageDboMapper personalMessageDboMapper;

		static final String TEMPLATE_PLACEHOLDER_OPENER = "{{";

		static final int SHAPES_A_TEMPLATE_PLACEHOLDER_WRITES = 12;

		record AuthoredTag(String where, BBCodeConfig config, String attributeText,
				BBCodeConfig.ParsedAttributes parsed) {

			String code() {
				return config.getCode().toUpperCase(Locale.ROOT);
			}

			String asTheAuthorWroteIt() {
				return "[" + code().toLowerCase(Locale.ROOT) + attributeText + "]";
			}
		}

		private Map<String, BBCodeConfig> grammarByCode() {
			Map<String, BBCodeConfig> grammar = new TreeMap<>();
			for (BBCodeConfig config : grammarHolder.current().configs().values())
				grammar.put(config.getCode().toUpperCase(Locale.ROOT), config);
			return grammar;
		}

		private List<AuthoredTag> everyOpeningTagStoredInContent() {
			List<AuthoredTag> tags = new ArrayList<>();
			for (MessageHistoryDbo revision : messageHistoryDboMapper.selectByExample(new MessageHistoryDboExample()))
				collectOpeningTags("message_history " + revision.getMessageHistoryId(), revision.getMessageText(),
						tags);
			for (WikiPageRevisionDbo revision : wikiPageRevisionDboMapper
					.selectByExample(new WikiPageRevisionDboExample()))
				collectOpeningTags("wiki_page_revision " + revision.getWikiPageRevisionId(), revision.getContent(),
						tags);
			for (ContentTemplateDbo template : contentTemplateDboMapper.selectByExample(new ContentTemplateDboExample()))
				collectOpeningTags("content_template " + template.getCode(), template.getBody(), tags);
			for (PersonalMessageDbo message : personalMessageDboMapper.selectByExample(new PersonalMessageDboExample()))
				collectOpeningTags("personal_message " + message.getPersonalMessageId(), message.getBody(), tags);
			return tags;
		}

		private void collectOpeningTags(String where, String content, List<AuthoredTag> tags) {
			if (content == null)
				return;
			BBCodeDocument document = BBCodeParser.parse(content, grammarByCode());
			for (AuthoredOpener opener : document.openersWhoseShapeMatchedNoMode())
				tags.add(new AuthoredTag(where, opener.config(), opener.attributeText(), opener.attributes()));
			for (BBCodeNode node : document.selfAndEveryDescendant())
				if (node instanceof BBCodeTag tag)
					tags.add(new AuthoredTag(where, tag.config(), tag.authoredSource().attributeText(),
							tag.parsedAttributes()));
		}

		private boolean aTemplatePlaceholderWroteThisShape(AuthoredTag tag) {
			return tag.attributeText().contains(TEMPLATE_PLACEHOLDER_OPENER);
		}

		@Test
		void everyShapeStoredContentWritesSelectsADeclaredAttributeMode() {
			List<AuthoredTag> stored = everyOpeningTagStoredInContent();
			Map<String, BBCodeConfig> grammar = grammarByCode();
			Map<String, AuthoredTag> shapesNoModeAccepts = new TreeMap<>();
			for (AuthoredTag tag : stored) {
				if (aTemplatePlaceholderWroteThisShape(tag))
					continue;
				BBCodeConfig config = grammar.get(tag.code());
				BBCodeConfig.ParsedAttributes parsed = tag.parsed();
				if (!config.getAttributeConfig().containsKey(parsed.attFormat()))
					shapesNoModeAccepts.putIfAbsent(
							tag.code() + " shape '" + parsed.attFormat() + "' matches no mode", tag);
				else if (!tag.attributeText().isBlank() && parsed.attributeValues().isEmpty())
					shapesNoModeAccepts.putIfAbsent(
							tag.code() + " reads '" + tag.attributeText() + "' as no attribute at all", tag);
			}

			assertFalse(stored.isEmpty(),
					"this audit only says something while the installed database actually stores bb code");
			assertTrue(shapesNoModeAccepts.isEmpty(),
					() -> "a shape the grammar does not accept renders as the author's raw markup, and a shape "
							+ "whose attributes the grammar reads as nothing falls back to the no-attribute mode "
							+ "and drops what the author wrote -- which is what [list=1] did for years while "
							+ "rendering a perfectly valid unstyled list. Either the grammar needs the mode or "
							+ "the scanner reads the shape wrongly: "
							+ shapesNoModeAccepts.entrySet().stream()
									.map(entry -> entry.getKey() + ", first written as "
											+ entry.getValue().asTheAuthorWroteIt() + " in "
											+ entry.getValue().where())
									.collect(Collectors.joining("; ")));
		}

		@Test
		void everyValueStoredContentWritesSurvivesItsDeclaredTransform() {
			Map<String, String> valuesThatResolveToNothing = new TreeMap<>();
			Map<String, BBCodeConfig> grammar = grammarByCode();
			for (AuthoredTag tag : everyOpeningTagStoredInContent()) {
				if (aTemplatePlaceholderWroteThisShape(tag))
					continue;
				BBCodeConfig config = grammar.get(tag.code());
				BBCodeConfig.ParsedAttributes parsed = tag.parsed();
				BBCodeAttributeMode mode = config.getAttributeConfig().get(parsed.attFormat());
				if (mode == null)
					continue;
				long namelessSlots = mode.getAttributes().stream()
						.filter(attribute -> BBCodeConfig.NAMELESS_ATTRIBUTE_NAME.equals(attribute.getName()))
						.count();
				int namelessSlotsFilled = 0;
				for (BBCodeAttribute attribute : mode.getAttributes()) {
					String raw = BBCodeConfig.NAMELESS_ATTRIBUTE_NAME.equals(attribute.getName())
							&& namelessSlots > 1
									? parsed.namelessValueAt(namelessSlotsFilled++)
									: parsed.attributeValues().get(attribute.getName());
					if (raw == null || raw.isBlank()
							|| !mode.getOpenTag().contains(attribute.getAttributeIndex()))
						continue;
					if (attribute.transformValue(raw).isEmpty())
						valuesThatResolveToNothing.putIfAbsent(
								tag.code() + " " + attribute.getName() + "'" + raw + "'",
								tag.asTheAuthorWroteIt() + " in " + tag.where());
				}
			}

			assertTrue(valuesThatResolveToNothing.isEmpty(),
					() -> "an authored value that its declared data type rejects is silently replaced by an empty "
							+ "string, so the tag renders with no colour, no size or no destination and the post "
							+ "looks broken with nothing logged: " + valuesThatResolveToNothing);
		}

		@Autowired
		private AttributeDataTypeDboMapper attributeDataTypeDboMapper;

		@Autowired
		private ContentRenderingService contentRenderingService;

		@Autowired
		private BBCodeDataProvider bbCodeDataProvider;

		private AttributeDataTypeDbo dataTypeRow(String code) {
			AttributeDataTypeDboExample selectTheType = new AttributeDataTypeDboExample();
			selectTheType.createCriteria().andCodeEqualTo(code);
			return attributeDataTypeDboMapper.selectByExample(selectTheType).get(0);
		}

		@Test
		void aValidationPatternThatWillNotCompileRejectsEveryValueOfItsTypeWithoutStoppingTheEngine() throws Exception {
			AttributeDataTypeDbo shipped = dataTypeRow("INTEGER");
			String shippedPattern = shipped.getValidationPattern();
			shipped.setValidationPattern("^\\d+(unclosed");
			attributeDataTypeDboMapper.updateByPrimaryKey(shipped);
			try {
				assertDoesNotThrow(() -> grammarLoader.loadBBCodeConfig(),
						"a grammar row an administrator can edit must not be able to stop the engine loading; "
								+ "boot happens before anyone can fix the row, so a throw here is an outage");
				assertFalse(contentRenderingService
						.render("[img width=10]a.png[/img]", ContentFormat.BBCODE, ContentScope.FORUM)
						.contains("width=\"10\""),
						"there is no Java pattern left to fall back to, so a row nobody can compile has to mean "
								+ "the type accepts nothing -- silently accepting everything would let an "
								+ "unvalidated value reach the markup the row exists to guard");
				mockMvc.perform(put("/admin/bbcodes/b")
						.header("Authorization", "Bearer " + getAdminToken())
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"enabled\": true}"))
						.andExpect(status().isOk());
			} finally {
				AttributeDataTypeDbo restored = dataTypeRow("INTEGER");
				restored.setValidationPattern(shippedPattern);
				attributeDataTypeDboMapper.updateByPrimaryKey(restored);
				grammarLoader.loadBBCodeConfig();
			}
		}

		@Test
		void everyDataTypeGetsItsValuePolicyFromTheDatabaseBecauseJavaNoLongerShipsOne() {
			assertEquals(Set.of(AttributeDataType.values()),
					bbCodeDataProvider.compileTheDeclaredValuePolicies().keySet(),
					"every value policy is a row now; a type with no row rejects every value of it on a live "
							+ "board, and there is no Java constant left to mask that");
		}

		@Test
		void theTemplatePlaceholderExclusionIsCountedSoItCannotGrowInSilence() {
			List<AuthoredTag> excluded = everyOpeningTagStoredInContent().stream()
					.filter(this::aTemplatePlaceholderWroteThisShape)
					.toList();
			List<String> writtenBySeededTemplates = excluded.stream()
					.filter(tag -> tag.where().startsWith("content_template "))
					.map(AuthoredTag::asTheAuthorWroteIt)
					.toList();
			List<String> writtenAnywhereElse = excluded.stream()
					.filter(tag -> !tag.where().startsWith("content_template "))
					.map(tag -> tag.asTheAuthorWroteIt() + " in " + tag.where())
					.toList();

			assertEquals(SHAPES_A_TEMPLATE_PLACEHOLDER_WRITES, writtenBySeededTemplates.size(),
					() -> "a mustache placeholder is not a bb code attribute value: it is expanded before the "
							+ "renderer ever sees it, so auditing it would fail on shapes production never parses. "
							+ "The exclusion is scoped to exactly these seeded template bodies, and it may not "
							+ "quietly widen into cover for a real defect: "
							+ String.join(", ", writtenBySeededTemplates));
			assertEquals(List.of(), writtenAnywhereElse,
					"only a seeded template body may carry an unexpanded placeholder; one stored in a post or a "
							+ "wiki revision is content the renderer really does parse, so excluding it would "
							+ "silently exempt the very shapes this audit exists to check");
		}
	}

	@Nested
	@Isolated
	class StatementTimeout {

		@Autowired
		private SqlSessionFactory sqlSessionFactory;

		@Value("${zfgbb.mybatis.statement-timeout-seconds}")
		private int configuredStatementTimeoutSeconds;

		@Test
		void everyOrdinaryMapperStatementInheritsTheConfiguredTimeout() {
			Configuration configuration = sqlSessionFactory.getConfiguration();

			assertEquals(180, configuredStatementTimeoutSeconds,
					"the shipped default must restore the 180 second intent of the never-loaded "
							+ "mybatis-config.xml");
			assertEquals(Integer.valueOf(configuredStatementTimeoutSeconds),
					configuration.getDefaultStatementTimeout(),
					"zfgbb.mybatis.statement-timeout-seconds must reach the live MyBatis "
							+ "configuration the request path executes through");
			assertNull(configuration.getMappedStatement(
					"com.zfgc.zfgbb.mappers.ThreadDboMapper.selectByExample").getTimeout(),
					"ordinary generated mapper statements must carry no override, so they fall "
							+ "back to the configured default on every execution");
		}

		@Test
		void aSlowRequestPathQueryIsCancelledAtTheConfiguredTimeout() {
			Configuration configuration = sqlSessionFactory.getConfiguration();
			Integer shipped = configuration.getDefaultStatementTimeout();
			configuration.setDefaultStatementTimeout(1);
			Instant started = Instant.now();
			try {
				DataAccessException cancelled = assertThrows(DataAccessException.class,
						() -> testQueryHelperMapper.sleepSeconds(30));
				assertTrue(rootCauseMessage(cancelled).contains("canceling statement due to user request"),
						() -> "the database must have cancelled the slow statement, but it failed with: "
								+ rootCauseMessage(cancelled));
			} finally {
				configuration.setDefaultStatementTimeout(shipped);
			}

			assertTrue(Duration.between(started, Instant.now()).compareTo(Duration.ofSeconds(15)) < 0,
					"a 30 second query under a 1 second timeout must be cut off, not waited out");
			assertEquals(Integer.valueOf(1), testQueryHelperMapper.sleepSeconds(0),
					"the pool must hand back a usable connection after a cancelled statement");
		}

		private String rootCauseMessage(Throwable failure) {
			Throwable root = failure;
			while (root.getCause() != null && root.getCause() != root)
				root = root.getCause();
			return String.valueOf(root.getMessage());
		}
	}
}
