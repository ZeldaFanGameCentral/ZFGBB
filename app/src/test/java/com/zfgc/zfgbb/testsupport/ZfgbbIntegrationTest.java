package com.zfgc.zfgbb.testsupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Tag;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;

import com.zfgc.zfgbb.dbo.*;
import com.zfgc.zfgbb.mappers.*;
import com.zfgc.zfgbb.testsupport.mappers.TestQueryHelperMapper;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.servlet.http.Cookie;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Tag("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@MapperScan("com.zfgc.zfgbb.testsupport.mappers")
@TestPropertySource(properties = {
		"zfgbb.install.token=" + ZfgbbIntegrationTest.INSTALL_TOKEN,
		"zfgbb.registration.enabled=true",
		"zfgbb.account-deletion.purge-async=false",
		"spring.mail.host="
})
public abstract class ZfgbbIntegrationTest {

	public static final String INSTALL_TOKEN = "test-install-token";
	public static final String ADMIN_USER = "test_admin";
	public static final String ADMIN_DISPLAY_NAME = "Test Admin";
	public static final String ADMIN_PASSWORD = "adminpass123";

	private static final String PG_SERVICE = "postgresql";
	private static final String PG_DB = "zfgc_dev";
	private static final String PG_USER = "zfgbb_user";
	private static final String PG_PASSWORD = "123456";
	private static final String TEST_JWT_SECRET = "integration-test-jwt-secret-at-least-32-characters";

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected javax.sql.DataSource dataSource;

	@Autowired
	protected TestQueryHelperMapper testQueryHelperMapper;

	protected final ObjectMapper json = new ObjectMapper();

	private static final Map<ComposeContainer, Integer> HOST_PORTS = new IdentityHashMap<>();

	@SuppressWarnings("resource")
	protected static ComposeContainer devPostgres() {
		int port = freePort();
		ComposeContainer pg = new ComposeContainer(projectFile("docker-compose.yml"))
				.withEnv("COMPOSE_PROJECT_NAME", "zfgbb-test-" + UUID.randomUUID().toString().substring(0, 8))
				.withEnv("POSTGRES_PORT", String.valueOf(port))
				.withEnv("ZFGBB_AUTH_JWT_SECRET", TEST_JWT_SECRET)
				.withServices(PG_SERVICE)
				.withBuild(true)
				.waitingFor(PG_SERVICE, Wait.forLogMessage(".*database system is ready to accept connections.*", 2)
						.withStartupTimeout(Duration.ofMinutes(3)));
		registerHostPort(pg, port);
		return pg;
	}

	protected static void registerHostPort(ComposeContainer container, int port) {
		HOST_PORTS.put(container, port);
	}

	protected static int hostPort(ComposeContainer container) {
		return HOST_PORTS.get(container);
	}

	protected static void datasource(DynamicPropertyRegistry r, ComposeContainer pg) {
		r.add("spring.datasource.url", () -> pgUrl(pg));
		r.add("spring.datasource.username", () -> PG_USER);
		r.add("spring.datasource.password", () -> PG_PASSWORD);
		r.add("spring.flyway.url", () -> pgUrl(pg));
		r.add("spring.flyway.user", () -> PG_USER);
		r.add("spring.flyway.password", () -> PG_PASSWORD);
	}

	private static String pgUrl(ComposeContainer pg) {
		return "jdbc:postgresql://localhost:" + hostPort(pg) + "/" + PG_DB;
	}

	protected boolean isInstalled() {
		SystemConfigDbo installed = systemConfigDboMapper.selectByPrimaryKey("installed");
		return installed != null && "true".equals(installed.getConfigValue());
	}

	protected void installSampleData() throws Exception {
		if (isInstalled()) {
			return;
		}
		String body = """
				{
				  "adminUserName": "%s",
				  "adminDisplayName": "%s",
				  "adminEmail": "%s@fake-email.fake.tld.thing",
				  "adminPassword": "%s",
				  "siteName": "ZFGC Test",
				  "provisionRecycleBin": false
				}
				""".formatted(ADMIN_USER, ADMIN_DISPLAY_NAME, ADMIN_USER, ADMIN_PASSWORD);
		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isOk());
	}

	protected JsonNode register(String userName, String password) throws Exception {
		String body = """
				{
				  "userName": "%s",
				  "displayName": "%s",
				  "email": "%s@fake-email.fake.tld.thing",
				  "password": "%s"
				}
				""".formatted(userName, userName, userName, password);
		MvcResult result = mockMvc.perform(post("/users/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated())
				.andReturn();
		return json.readTree(result.getResponse().getContentAsString());
	}

	protected JsonNode login(String userName, String password) throws Exception {
		String body = """
				{"username": "%s", "password": "%s", "useTokens": true, "stayLoggedIn": false}
				""".formatted(userName, password);
		MvcResult result = mockMvc.perform(post("/users/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isOk())
				.andReturn();
		return json.readTree(result.getResponse().getContentAsString());
	}

	protected Cookie obtainXsrfCookie() throws Exception {
		MvcResult result = mockMvc.perform(get("/users/loggedInUser"))
				.andExpect(status().isOk())
				.andExpect(cookie().exists("XSRF-TOKEN"))
				.andReturn();
		return result.getResponse().getCookie("XSRF-TOKEN");
	}

	@Autowired
	private BoardDboMapper boardDboMapper;

	@Autowired
	private SystemConfigDboMapper systemConfigDboMapper;

	@Autowired
	private BrBoardPermissionDboMapper brBoardPermissionDboMapper;

	@Autowired
	private PermissionDboMapper permissionDboMapper;

	@Autowired
	private UserDboMapper baseUserDboMapper;

	@Autowired
	private MessageDboMapper baseMessageDboMapper;

	private UserDbo userNamed(String userName) {
		UserDboExample named = new UserDboExample();
		named.createCriteria().andUserNameEqualTo(userName);
		List<UserDbo> matches = baseUserDboMapper.selectByExample(named);
		assertTrue(matches.size() <= 1, "user_name must identify at most one account: " + userName);
		return matches.isEmpty() ? null : matches.get(0);
	}

	protected Integer findUserIdByName(String userName) {
		UserDbo user = userNamed(userName);
		return user == null ? null : user.getUserId();
	}

	protected String findDisplayNameByName(String userName) {
		UserDbo user = userNamed(userName);
		return user == null ? null : user.getDisplayName();
	}

	private List<MessageDbo> postsInThread(int threadId, Consumer<MessageDboExample.Criteria> predicate,
			String orderByClause) {
		MessageDboExample posts = new MessageDboExample();
		MessageDboExample.Criteria criteria = posts.createCriteria().andThreadIdEqualTo(threadId);
		predicate.accept(criteria);
		posts.setOrderByClause(orderByClause);
		return baseMessageDboMapper.selectByExample(posts);
	}

	protected Integer findMessageIdAtPosition(int threadId, int postInThread) {
		List<MessageDbo> matches = postsInThread(threadId,
				criteria -> criteria.andPostInThreadEqualTo(postInThread), "post_in_thread");
		assertTrue(matches.size() <= 1,
				"a thread must hold at most one post at position " + postInThread + ": " + matches.size());
		return matches.isEmpty() ? null : matches.get(0).getMessageId();
	}

	protected Integer findLatestMessageIdInThread(int threadId) {
		List<MessageDbo> posts = postsInThread(threadId, criteria -> { }, "post_in_thread desc");
		return posts.isEmpty() ? null : posts.get(0).getMessageId();
	}

	protected Integer findLatestMessageIdInThreadOwnedBy(int threadId, int ownerId) {
		List<MessageDbo> posts = postsInThread(threadId,
				criteria -> criteria.andOwnerIdEqualTo(ownerId), "post_in_thread desc");
		return posts.isEmpty() ? null : posts.get(0).getMessageId();
	}

	protected List<Integer> listPostPositionsInThread(int threadId) {
		return postsInThread(threadId, criteria -> { }, "post_in_thread").stream()
				.map(MessageDbo::getPostInThread).toList();
	}

	protected void assertRecycleBinProvisioned() {
		BoardDboExample ex = new BoardDboExample();
		ex.createCriteria().andBoardNameEqualTo("Recycle Bin");
		var boards = boardDboMapper.selectByExample(ex);
		assertEquals(1, boards.size(), "exactly one recycle board must be provisioned");

		SystemConfigDboExample cfgEx = new SystemConfigDboExample();
		cfgEx.createCriteria().andConfigKeyEqualTo("recycle_board_id")
				.andConfigValueEqualTo(String.valueOf(boards.get(0).getBoardId()));
		assertEquals(1, systemConfigDboMapper.countByExample(cfgEx),
				"recycle_board_id must point at the provisioned recycle board");

		PermissionDboExample moderationPermissionEx = new PermissionDboExample();
		moderationPermissionEx.createCriteria()
				.andPermissionCodeIn(List.of("ZFGC_SITE_ADMIN", "ZFGC_SITE_MODERATOR"));
		Set<Integer> moderationPermissionIds = permissionDboMapper.selectByExample(moderationPermissionEx)
				.stream().map(PermissionDbo::getPermissionId).collect(Collectors.toSet());
		assertEquals(2, moderationPermissionIds.size(),
				"ZFGC_SITE_ADMIN and ZFGC_SITE_MODERATOR permissions must both exist");

		BrBoardPermissionDboExample permEx = new BrBoardPermissionDboExample();
		permEx.createCriteria().andBoardIdEqualTo(boards.get(0).getBoardId());
		Set<Integer> recyclePermissionIds = brBoardPermissionDboMapper.selectByExample(permEx)
				.stream().map(BrBoardPermissionDbo::getPermissionId).collect(Collectors.toSet());
		assertEquals(moderationPermissionIds, recyclePermissionIds,
				"the recycle board is visible to admins and moderators only");
	}

	protected static int freePort() {
		try (ServerSocket s = new ServerSocket(0)) {
			return s.getLocalPort();
		} catch (IOException e) {
			throw new IllegalStateException("could not allocate a free host port", e);
		}
	}

	protected static File projectFile(String relativePath) {
		return resolveFromProjectRoot(relativePath).toFile();
	}

	protected static Path resolveFromProjectRoot(String relativePath) {
		Path current = new File("").getAbsoluteFile().toPath();
		while (current != null) {
			Path candidate = current.resolve(relativePath);
			if (Files.exists(candidate)) {
				return candidate;
			}
			current = current.getParent();
		}
		throw new IllegalStateException(relativePath + " not found walking up from " + new File("").getAbsolutePath());
	}
}
