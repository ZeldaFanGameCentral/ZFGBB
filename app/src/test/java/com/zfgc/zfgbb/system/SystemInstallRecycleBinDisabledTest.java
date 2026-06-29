package com.zfgc.zfgbb.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.junit.jupiter.Container;

import com.zfgc.zfgbb.testsupport.ZfgbbIntegrationTest;

class SystemInstallRecycleBinDisabledTest extends ZfgbbIntegrationTest {

	@Container
	static ComposeContainer pg = devPostgres();

	@DynamicPropertySource
	static void props(DynamicPropertyRegistry r) {
		datasource(r, pg);
	}

	@Test
	void decliningTheRecycleBinUnsetsThePackSeededDesignation() throws Exception {
		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "adminUserName": "%s",
						  "adminDisplayName": "Test Admin",
						  "adminEmail": "%s@fake-email.fake.tld.thing",
						  "adminPassword": "%s",
						  "siteName": "ZFGC Test",
						  "contentPack": "zfgc",
						  "provisionRecycleBin": false
						}
						""".formatted(ADMIN_USER, ADMIN_USER, ADMIN_PASSWORD)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true));

		assertEquals(0, count("zfgbb.system_config where config_key = 'recycle_board_id'"),
				"declining the option unsets the pack-seeded designation");
		assertEquals(1, count("zfgbb.board where board_name = 'Recycle Bin'"),
				"the pack-created board stays behind, inert");
	}
}
