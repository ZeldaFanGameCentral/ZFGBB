package com.zfgc.zfgbb.system;

import static org.junit.jupiter.api.Assertions.assertTrue;
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

class SystemInstallBareRecycleBinTest extends ZfgbbIntegrationTest {

	@Container
	static ComposeContainer pg = devPostgres();

	@DynamicPropertySource
	static void props(DynamicPropertyRegistry r) {
		datasource(r, pg);
	}

	@Test
	void bareInstallWithRecycleBinEnabledProvisionsBoardAndConfig() throws Exception {
		mockMvc.perform(post("/system/install")
				.header("X-Install-Token", INSTALL_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "adminUserName": "%s",
						  "adminDisplayName": "Test Admin",
						  "adminEmail": "%s@fake-email.fake.tld.thing",
						  "adminPassword": "%s",
						  "siteName": "ZFGC Bare",
						  "provisionRecycleBin": true
						}
						""".formatted(ADMIN_USER, ADMIN_USER, ADMIN_PASSWORD)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.installed").value(true));

		assertTrue(count("zfgbb.category") >= 1,
				"a category is created so the recycle board has a home on a bare install");
		assertRecycleBinProvisioned();
	}
}
