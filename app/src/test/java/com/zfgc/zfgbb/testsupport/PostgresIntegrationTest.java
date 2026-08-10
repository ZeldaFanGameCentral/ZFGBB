package com.zfgc.zfgbb.testsupport;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.nio.file.Path;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.ComposeContainer;

import com.zfgc.zfgbb.services.mail.MailDispatcherConfig;
import com.zfgc.zfgbb.services.mail.MailDispatcher;
import com.zfgc.zfgbb.testsupport.mappers.TestFixtureSetupMapper;

import tools.jackson.databind.JsonNode;

public abstract class PostgresIntegrationTest extends ZfgbbIntegrationTest {

	protected static final Pattern CONFIRMATION_TOKEN_PATTERN = Pattern.compile("token=([A-Za-z0-9_.-]+)");

	static final ComposeContainer POSTGRES = devPostgres();
	private static final Path CONTENT_ROOT = Path.of(System.getProperty("java.io.tmpdir"),
			"zfgbb-postgres-integration-" + UUID.randomUUID());

	static {
		POSTGRES.start();
		Runtime.getRuntime().addShutdownHook(new Thread(POSTGRES::stop));
	}

	@DynamicPropertySource
	static void datasource(DynamicPropertyRegistry registry) {
		datasource(registry, POSTGRES);
		registry.add("zfgbb.content.path", CONTENT_ROOT::toString);
	}

	@Autowired
	protected MailDispatcherConfig.InMemoryMailDispatcher mailDispatcher;

	@Autowired
	protected TestFixtureSetupMapper testFixtureSetupMapper;

	protected final String suffix = UUID.randomUUID().toString().substring(0, 8);

	protected record TestUser(String userName, String token, int id) {}

	protected TestUser createUser(String userName) throws Exception {
		register(userName, "password123");
		String token = login(userName, "password123").get("accessToken").asString();
		return new TestUser(userName, token, userIdOf(userName));
	}

	protected String getAdminToken() throws Exception {
		return login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
	}

	@BeforeEach
	void ensureSampleDataInstalled() throws Exception {
		installSampleData();
		ensureOrdinaryIntegrationFixture();
	}

	private void ensureOrdinaryIntegrationFixture() {
		testFixtureSetupMapper.ensureDefaultCategory();
		testFixtureSetupMapper.ensureGeneralBoard();
		testFixtureSetupMapper.ensureRecycleBoard();
		testFixtureSetupMapper.resetBoardPermissions();
		testFixtureSetupMapper.grantGeneralBoardPermissions();
		testFixtureSetupMapper.grantRecycleBoardPermissions();
		testFixtureSetupMapper.setRecycleBoardConfig();
		testFixtureSetupMapper.ensureDefaultContentEntities();
		testFixtureSetupMapper.ensureDefaultProjects();
		testFixtureSetupMapper.ensureDefaultResources();
		testFixtureSetupMapper.resetCategorySequence();
		testFixtureSetupMapper.resetBoardSequence();
		testFixtureSetupMapper.resetContentEntitySequence();
	}

	protected int postThread(String token, String title, String body) throws Exception {
		String requestBody = """
				{"title": "%s", "body": "%s"}
				""".formatted(title, body);
		MvcResult result = mockMvc.perform(post("/thread")
				.param("boardId", "1")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated())
				.andReturn();
		return json.readTree(result.getResponse().getContentAsString()).get("id").asInt();
	}

	protected int postThread(String token, String title) throws Exception {
		return postThread(token, title, "First post!");
	}

	protected void postReply(String token, int threadId, String body) throws Exception {
		mockMvc.perform(post("/message/" + threadId)
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"body\": \"" + body + "\"}"))
				.andExpect(status().isCreated());
	}

	protected void postReply(String token, int threadId) throws Exception {
		postReply(token, threadId, "A reply!");
	}

	protected int postAndRecycle(String token, int threadId, String body) throws Exception {
		postReply(token, threadId, body);
		Integer messageId = findLatestMessageIdInThread(threadId);
		assertNotNull(messageId);
		mockMvc.perform(delete("/message/" + messageId)
				.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.outcome").value("RECYCLED"));
		return messageId;
	}

	protected int userIdOf(String userName) {
		Integer userId = findUserIdByName(userName);
		assertNotNull(userId);
		return userId;
	}

	protected int messageIdAt(int threadId, int postInThread) {
		Integer messageId = findMessageIdAtPosition(threadId, postInThread);
		assertNotNull(messageId);
		return messageId;
	}

	protected List<Integer> postPositionsIn(int threadId) {
		return listPostPositionsInThread(threadId);
	}

	protected JsonNode fetchForum(String accessToken) throws Exception {
		MvcResult result = mockMvc.perform(get("/board/forum")
				.header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isOk())
				.andReturn();
		return json.readTree(result.getResponse().getContentAsString());
	}

	protected JsonNode boardNodeOf(JsonNode forum, int boardId) {
		for (JsonNode category : forum.get("categories"))
			for (JsonNode board : category.get("boards"))
				if (board.get("boardId").asInt() == boardId)
					return board;
		throw new AssertionError("board " + boardId + " not present on the forum index");
	}

	protected String deletionBody(String mode, String password, String confirmationPhrase) {
		return """
				{"mode": "%s", "password": "%s", "confirmationPhrase": "%s"}
				""".formatted(mode, password, confirmationPhrase);
	}

	protected String lastConfirmationToken() {
		List<MailDispatcher.OutboundMail> sent = mailDispatcher.sentMessages();
		assertTrue(!sent.isEmpty(), "a confirmation email must have been dispatched");
		Matcher matcher = CONFIRMATION_TOKEN_PATTERN.matcher(sent.get(sent.size() - 1).body());
		assertTrue(matcher.find(), "the confirmation email must carry the tokenized link");
		return matcher.group(1);
	}

	protected ResultActions confirmAccountDeletion(String token, String clientIp) throws Exception {
		return mockMvc.perform(post("/users/account/delete/confirm")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"token\": \"" + token + "\"}")
				.with(request -> {
					request.setRemoteAddr(clientIp);
					return request;
				}));
	}

	protected String refreshExpectingOk(String refreshToken) throws Exception {
		MvcResult result = mockMvc.perform(post("/users/auth/refresh")
				.contentType(MediaType.APPLICATION_JSON)
				.content(refreshBody(refreshToken)))
				.andExpect(status().isOk())
				.andReturn();
		return json.readTree(result.getResponse().getContentAsString()).get("refreshToken").asString();
	}

	protected String refreshBody(String refreshToken) {
		return """
				{"refreshToken": "%s"}
				""".formatted(refreshToken);
	}

	protected void assertPostDeletionTeardown(String userName, String password, String accessToken,
			String originalRefreshToken, String rotatedRefreshToken) throws Exception {
		mockMvc.perform(post("/users/account/delete/preview")
				.header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isUnauthorized());
		mockMvc.perform(post("/users/auth/refresh")
				.contentType(MediaType.APPLICATION_JSON)
				.content(refreshBody(originalRefreshToken)))
				.andExpect(status().isUnauthorized());
		mockMvc.perform(post("/users/auth/refresh")
				.contentType(MediaType.APPLICATION_JSON)
				.content(refreshBody(rotatedRefreshToken)))
				.andExpect(status().isUnauthorized());
		mockMvc.perform(post("/users/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\": \"" + userName + "\", \"password\": \"" + password
						+ "\", \"useTokens\": true}"))
				.andExpect(status().isUnauthorized());
	}
}
