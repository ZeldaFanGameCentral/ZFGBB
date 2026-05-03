package com.zfgc.zfgbb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestPropertySource(properties = {
		"zfgbb.install.token=test-install-token-xyz"
})
class SystemInstallTest {

	@Container
	static PostgreSQLContainer pg = new PostgreSQLContainer("postgres:16")
			.withEnv("PGDATA", "/tmp/postgres-data");

	@DynamicPropertySource
	static void datasource(DynamicPropertyRegistry r) {
		r.add("spring.datasource.url", pg::getJdbcUrl);
		r.add("spring.datasource.username", pg::getUsername);
		r.add("spring.datasource.password", pg::getPassword);
	}

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final String suffix = UUID.randomUUID().toString().substring(0, 6);

	@Test
	void install_flow() throws Exception {
		// Status before install
		mockMvc.perform(get("/system/install/status"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(false));

		String adminUser = "admin_" + suffix;
		String installBody = """
				{
				  "adminUserName": "%s",
				  "adminDisplayName": "Site Admin",
				  "adminEmail": "%s@example.com",
				  "adminPassword": "adminpass123",
				  "siteName": "Test ZFGBB",
				  "applySampleData": false
				}
				""".formatted(adminUser, adminUser);

		// Without token: 404
		mockMvc.perform(post("/system/install")
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody))
				.andExpect(status().isNotFound());

		// Wrong token: 404
		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", "not-the-real-token")
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody))
				.andExpect(status().isNotFound());

		// Right token: 200, marker flipped, auth cookies set, no tokens in body
		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", "test-install-token-xyz")
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true))
				.andExpect(jsonPath("$.adminUserId").isNumber())
				.andExpect(jsonPath("$.siteName").value("Test ZFGBB"))
				.andExpect(jsonPath("$.sampleDataApplied").value(false))
				.andExpect(jsonPath("$.accessToken").doesNotExist())
				.andExpect(jsonPath("$.refreshToken").doesNotExist())
				.andExpect(cookie().exists("zfgbb_access_token"))
				.andExpect(cookie().exists("zfgbb_refresh_token"));

		// Status now reports installed=true with the chosen site name
		mockMvc.perform(get("/system/install/status"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true))
				.andExpect(jsonPath("$.siteName").value("Test ZFGBB"));

		// Second install attempt: 404 (marker is set)
		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", "test-install-token-xyz")
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody))
				.andExpect(status().isNotFound());

		// Admin has both ZFGC_USER (id=1) and ZFGC_SITE_ADMIN (id=10) permissions
		Integer permCount = jdbcTemplate.queryForObject("""
				select count(*) from zfgbb.br_user_permission p
				join zfgbb.user u on u.user_id = p.user_id
				where u.user_name = ? and p.user_permission_id in (1, 10)
				""", Integer.class, adminUser);
		assert permCount != null && permCount == 2
				: "Expected admin to have 2 perms (USER + SITE_ADMIN); got " + permCount;

		// Admin can log in
		String loginBody = """
				{"username": "%s", "password": "adminpass123", "useTokens": true}
				""".formatted(adminUser);
		mockMvc.perform(post("/users/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginBody))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accessToken").isString())
				.andExpect(jsonPath("$.user.userName").value(adminUser));
	}

	@Test
	void install_disabled_when_token_unset() throws Exception {
		mockMvc.perform(post("/system/install")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))
				.andExpect(status().isNotFound());
	}
}
