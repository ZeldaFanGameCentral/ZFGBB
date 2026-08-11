package com.zfgc.zfgbb.testsupport;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.postgresql.PostgreSQLContainer;

import com.zfgc.zfgbb.testsupport.mappers.TestFixtureSetupMapper;

import tools.jackson.databind.JsonNode;

public abstract class PostgresIntegrationTest extends ZfgbbIntegrationTest {

	static final PostgreSQLContainer POSTGRES = devPostgres();
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
		testFixtureSetupMapper.resetCategorySequence();
		testFixtureSetupMapper.resetBoardSequence();
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
}
