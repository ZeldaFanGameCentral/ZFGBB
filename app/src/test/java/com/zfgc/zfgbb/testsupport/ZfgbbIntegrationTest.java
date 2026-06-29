package com.zfgc.zfgbb.testsupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
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

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	protected final ObjectMapper json = new ObjectMapper();

	private static final Map<ComposeContainer, Integer> HOST_PORTS = new IdentityHashMap<>();

	@SuppressWarnings("resource")
	protected static ComposeContainer devPostgres() {
		int port = freePort();
		ComposeContainer pg = new ComposeContainer(projectFile("docker-compose.yml"))
				.withEnv("COMPOSE_PROJECT_NAME", "zfgbb-test-" + UUID.randomUUID().toString().substring(0, 8))
				.withEnv("POSTGRES_PORT", String.valueOf(port))
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
		Integer installed = jdbcTemplate.queryForObject(
				"select count(*) from zfgbb.system_config where config_key = 'installed' and config_value = 'true'",
				Integer.class);
		return installed != null && installed > 0;
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
				  "contentPack": "zfgc",
				  "provisionRecycleBin": true
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
				.andExpect(status().isOk())
				.andReturn();
		return json.readTree(result.getResponse().getContentAsString());
	}

	protected JsonNode login(String userName, String password) throws Exception {
		String body = """
				{"username": "%s", "password": "%s", "useTokens": true}
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

	protected int count(String fromAndWhere) {
		Integer value = jdbcTemplate.queryForObject("select count(*) from " + fromAndWhere, Integer.class);
		return value == null ? 0 : value;
	}

	protected void assertRecycleBinProvisioned() {
		assertEquals(1, count("zfgbb.board where board_name = 'Recycle Bin'"),
				"exactly one recycle board must be provisioned");
		assertEquals(1, count("""
				zfgbb.system_config c
				join zfgbb.board b on b.board_id = c.config_value::integer
				where c.config_key = 'recycle_board_id' and b.board_name = 'Recycle Bin'"""),
				"recycle_board_id must point at the provisioned recycle board");
		assertEquals(2, count("""
				zfgbb.br_board_permission bp
				join zfgbb.board b on b.board_id = bp.board_id
				where b.board_name = 'Recycle Bin'"""));
		assertEquals(2, count("""
				zfgbb.br_board_permission bp
				join zfgbb.board b on b.board_id = bp.board_id
				where b.board_name = 'Recycle Bin'
					and bp.permission_id in (
						select permission_id from zfgbb.permission
						where permission_code in ('ZFGC_SITE_ADMIN', 'ZFGC_SITE_MODERATOR'))"""),
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
