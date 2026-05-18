package com.zfgc.zfgbb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import jakarta.servlet.http.Cookie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class LoginRegisterPostFlowTest {

	@Container
	static PostgreSQLContainer pg = new PostgreSQLContainer("postgres:16")
			.withEnv("PGDATA", "/tmp/postgres-data");

	@DynamicPropertySource
	static void datasource(DynamicPropertyRegistry r) {
		r.add("spring.datasource.url", pg::getJdbcUrl);
		r.add("spring.datasource.username", pg::getUsername);
		r.add("spring.datasource.password", pg::getPassword);
		r.add("zfgbb.registration.enabled", () -> "true");
	}

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final String suffix = UUID.randomUUID().toString().substring(0, 8);
	private final String userName = "it_" + suffix;
	private final String email = "it_" + suffix + "@example.com";

	@BeforeEach
	void seedTestBoard() {
		jdbcTemplate.update("""
				insert into zfgbb.category (category_id, category_name)
				values (1, 'Test Category')
				on conflict (category_id) do nothing
				""");
		jdbcTemplate.update("""
				insert into zfgbb.board (board_id, board_name, description, category_id)
				values (1, 'Test Board', 'integration-test board', 1)
				on conflict (board_id) do nothing
				""");
		jdbcTemplate.update("""
				insert into zfgbb.br_board_permission (board_id, permission_id)
				values (1, 1)
				on conflict on constraint un_board_permission do nothing
				""");
	}

	@Test
	void register_login_thread_reply_refresh_logout() throws Exception {
		String registerBody = """
				{
				  "userName": "%s",
				  "displayName": "IT User",
				  "email": "%s",
				  "password": "password123"
				}
				""".formatted(userName, email);
		mockMvc.perform(post("/users/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(registerBody))
				.andExpect(status().isOk());

		String loginBody = """
				{"username": "%s", "password": "password123", "useTokens": true}
				""".formatted(userName);
		MvcResult loginResult = mockMvc.perform(post("/users/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginBody))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accessToken").isString())
				.andExpect(jsonPath("$.refreshToken").isString())
				.andExpect(jsonPath("$.user.userName").value(userName))
				.andReturn();
		JsonNode loginJson = objectMapper.readTree(loginResult.getResponse().getContentAsString());
		String accessToken = loginJson.get("accessToken").asText();
		String refreshToken = loginJson.get("refreshToken").asText();
		Cookie xsrf = loginResult.getResponse().getCookie("XSRF-TOKEN");

		String threadBody = """
				{
				  "threadName": "Hello world",
				  "boardId": 1,
				  "messages": [
				    {"currentMessage": {"unparsedText": "First post!"}}
				  ]
				}
				""";
		MvcResult threadResult = mockMvc.perform(post("/thread")
				.param("boardId", "1")
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(threadBody))
				.andExpect(status().isOk())
				.andReturn();
		JsonNode threadJson = objectMapper.readTree(threadResult.getResponse().getContentAsString());
		// Thread.threadId is @JsonIgnore'd; the public id field comes from
		// BaseModel.getId()
		int threadId = threadJson.get("id").asInt();

		String replyBody = """
				{
				  "threadId": %d,
				  "currentMessage": {"unparsedText": "A reply!"}
				}
				""".formatted(threadId);
		mockMvc.perform(post("/message/" + threadId)
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(replyBody))
				.andExpect(status().isOk());

		String refreshBody = """
				{"refreshToken": "%s"}
				""".formatted(refreshToken);
		MvcResult refreshResult = mockMvc.perform(post("/users/auth/refresh")
				.cookie(xsrf)
				.header("X-XSRF-TOKEN", xsrf.getValue())
				.contentType(MediaType.APPLICATION_JSON)
				.content(refreshBody))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accessToken").isString())
				.andExpect(jsonPath("$.refreshToken").isString())
				.andReturn();
		JsonNode refreshJson = objectMapper.readTree(refreshResult.getResponse().getContentAsString());
		String newRefreshToken = refreshJson.get("refreshToken").asText();

		mockMvc.perform(post("/users/auth/refresh")
				.cookie(xsrf)
				.header("X-XSRF-TOKEN", xsrf.getValue())
				.contentType(MediaType.APPLICATION_JSON)
				.content(refreshBody))
				.andExpect(status().isUnauthorized());

		String logoutBody = """
				{"refreshToken": "%s"}
				""".formatted(newRefreshToken);
		mockMvc.perform(post("/users/auth/logout")
				.cookie(xsrf)
				.header("X-XSRF-TOKEN", xsrf.getValue())
				.contentType(MediaType.APPLICATION_JSON)
				.content(logoutBody))
				.andExpect(status().isNoContent());

		mockMvc.perform(post("/users/auth/refresh")
				.cookie(xsrf)
				.header("X-XSRF-TOKEN", xsrf.getValue())
				.contentType(MediaType.APPLICATION_JSON)
				.content(logoutBody))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void lockout_after_threshold_failed_attempts_blocks_correct_password() throws Exception {
		// Register a fresh account for this test (separate from the main flow above so
		// tests are independent).
		String lockoutUser = "lock_" + suffix;
		String lockoutEmail = "lock_" + suffix + "@example.com";
		String registerBody = """
				{
				  "userName": "%s",
				  "displayName": "Lockout Test",
				  "email": "%s",
				  "password": "rightpassword"
				}
				""".formatted(lockoutUser, lockoutEmail);
		mockMvc.perform(post("/users/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(registerBody))
				.andExpect(status().isOk());

		String wrongLogin = """
				{"username": "%s", "password": "wrongpassword"}
				""".formatted(lockoutUser);

		// Threshold default is 10 (zfgbb.auth.lockout.failed-attempts). Trigger it.
		for (int i = 0; i < 10; i++) {
			mockMvc.perform(post("/users/auth/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(wrongLogin))
					.andExpect(status().isUnauthorized());
		}

		// 11th attempt — even with the *correct* password, the account is now locked.
		String rightLogin = """
				{"username": "%s", "password": "rightpassword"}
				""".formatted(lockoutUser);
		mockMvc.perform(post("/users/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(rightLogin))
				.andExpect(status().isUnauthorized());

		// Verify the lockout was actually persisted (not just a missing-credentials
		// happenstance).
		Integer count = jdbcTemplate.queryForObject(
				"select count(*) from zfgbb.user where user_name = ? and locked_until_ts is not null",
				Integer.class, lockoutUser);
		assert count != null && count == 1 : "Expected locked_until_ts to be set on the user; was not.";
	}
}
