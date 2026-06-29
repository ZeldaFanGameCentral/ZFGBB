package com.zfgc.zfgbb.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.junit.jupiter.Container;

import com.zfgc.zfgbb.dbo.InstallRunDbo;
import com.zfgc.zfgbb.mappers.InstallRunDboMapper;
import com.zfgc.zfgbb.testsupport.ZfgbbIntegrationTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SystemInstallTest extends ZfgbbIntegrationTest {

	@Container
	static ComposeContainer pg = devPostgres();

	@Autowired
	private InstallRunDboMapper installRunDboMapper;

	@DynamicPropertySource
	static void props(DynamicPropertyRegistry r) {
		datasource(r, pg);
	}

	@Test
	@Order(1)
	void installRequiresTheToken() throws Exception {
		mockMvc.perform(get("/system/install/status"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(false));

		mockMvc.perform(post("/system/install")
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody()))
				.andExpect(status().isNotFound());

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", "not-the-real-token")
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody()))
				.andExpect(status().isNotFound());
	}

	@Test
	@Order(2)
	void installCreatesAdminAndAppliesSampleData() throws Exception {
		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true))
				.andExpect(jsonPath("$.adminUserId").value(1))
				.andExpect(jsonPath("$.siteName").value("ZFGC Test"))
				.andExpect(jsonPath("$.contentPack").value("zfgc"))
				.andExpect(jsonPath("$.accessToken").doesNotExist())
				.andExpect(jsonPath("$.refreshToken").doesNotExist())
				.andExpect(cookie().exists("zfgbb_access_token"))
				.andExpect(cookie().exists("zfgbb_refresh_token"));

		mockMvc.perform(get("/system/install/status"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true))
				.andExpect(jsonPath("$.siteName").value("ZFGC Test"));

		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(installBody()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true));
	}

	@Test
	@Order(3)
	void sampleDataMatchesTheSeedManifest() {
		assertEquals(2, count("zfgbb.br_user_permission where user_id = 1 and user_permission_id in (1, 10)"),
				"admin carries ZFGC_USER + ZFGC_SITE_ADMIN");
		assertTrue(count("zfgbb.thread") > 0);
		assertTrue(count("zfgbb.message") > 0);
		assertTrue(count("zfgbb.project") > 0);
		assertTrue(count("zfgbb.resource") > 0);
		assertTrue(count("zfgbb.wiki_page") > 0);
		assertEquals(1, count("zfgbb.poll"));
		assertTrue(count("zfgbb.content_template where wiki_page_id is not null") >= 3,
				"migrated Main Page templates are linked to wiki pages");
		assertEquals(count("zfgbb.\"user\" where user_id > 1"),
				count("zfgbb.wiki_page where namespace = 'User'"),
				"every seeded member has a wiki page");
	}

	@Test
	@Order(4)
	void adminCanLogIn() throws Exception {
		login(ADMIN_USER, ADMIN_PASSWORD);
	}

	@Test
	@Order(5)
	void recycleBinDesignationComesFromThePackWithoutDuplication() {
		assertEquals(1, count("zfgbb.board where board_name = 'Recycle Bin'"),
				"pack V3 provisions exactly one recycle board; install must not add another");
		assertRecycleBinProvisioned();
	}

	@Test
	@Order(6)
	void installRunRowIsReadableThroughTheGeneratedSmallintPrimaryKeyMapper() {
		InstallRunDbo installRunRow = installRunDboMapper.selectByPrimaryKey((short) 1);
		assertNotNull(installRunRow, "seeded install_run row must be selectable by its smallint primary key");
		assertEquals("INSTALLED", installRunRow.getState());
		assertEquals("INSTALLED", installRunRow.getLastCompletedState());
		assertEquals(Integer.valueOf(1), installRunRow.getAdminUserId());
		assertNotNull(installRunRow.getRequestFingerprint(), "claim() persisted a request fingerprint");
		assertNull(installRunRow.getLastError());
	}

	private static String installBody() {
		return """
				{
				  "adminUserName": "%s",
				  "adminDisplayName": "Test Admin",
				  "adminEmail": "%s@fake-email.fake.tld.thing",
				  "adminPassword": "%s",
				  "siteName": "ZFGC Test",
				  "contentPack": "zfgc",
				  "provisionRecycleBin": true
				}
				""".formatted(ADMIN_USER, ADMIN_USER, ADMIN_PASSWORD);
	}
}
