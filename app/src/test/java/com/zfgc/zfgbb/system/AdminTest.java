package com.zfgc.zfgbb.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

import jakarta.servlet.http.Cookie;
import tools.jackson.databind.JsonNode;

class AdminTest extends PostgresIntegrationTest {

	private static Cookie accessCookie() {
		return new Cookie("zfgbb_access_token", "fake-jwt-value");
	}

	@Nested
	class MigrationEndpointGuards {

		@Test
		void anonymousListConflictsRequiresAuth() throws Exception {
			mockMvc.perform(get("/system/migrate/conflicts"))
					.andExpect(status().isUnauthorized());
		}

		@Test
		void anonymousScanConflictsIsUnauthorized() throws Exception {
			mockMvc.perform(post("/system/migrate/conflicts/scan")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{}"))
					.andExpect(status().isUnauthorized());
		}

		@Test
		void cookieAuthedScanConflictsWithoutCsrfIsForbidden() throws Exception {
			mockMvc.perform(post("/system/migrate/conflicts/scan")
					.cookie(accessCookie())
					.contentType(MediaType.APPLICATION_JSON)
					.content("{}"))
					.andExpect(status().isForbidden());
		}

		@Test
		void anonymousListJobsRequiresAuth() throws Exception {
			mockMvc.perform(get("/system/migrate/jobs"))
					.andExpect(status().isUnauthorized());
		}

		@Test
		void anonymousSubmitJobIsUnauthorized() throws Exception {
			mockMvc.perform(post("/system/migrate/jobs")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{}"))
					.andExpect(status().isUnauthorized());
		}

		@Test
		void cookieAuthedSubmitJobWithoutCsrfIsForbidden() throws Exception {
			mockMvc.perform(post("/system/migrate/jobs")
					.cookie(accessCookie())
					.contentType(MediaType.APPLICATION_JSON)
					.content("{}"))
					.andExpect(status().isForbidden());
		}
	}

	@Nested
	class Console {

		@Test
		void adminListsUsersAndDeletesOneThroughTheConsole() throws Exception {
			String victimName = "condel_" + suffix;
			register(victimName, "password123");
			int victimId = userIdOf(victimName);
			String memberToken = login(victimName, "password123").get("accessToken").asString();
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();

			mockMvc.perform(get("/system/users")
					.header("Authorization", "Bearer " + memberToken))
					.andExpect(status().isForbidden());

			MvcResult listResult = mockMvc.perform(get("/system/users")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andReturn();
			boolean listed = false;
			for (JsonNode user : json.readTree(listResult.getResponse().getContentAsString()))
				if (victimId == user.get("userId").asInt())
					listed = true;
			assertTrue(listed, "the console listing must include the registered member");

			mockMvc.perform(post("/system/users/delete")
					.header("Authorization", "Bearer " + adminToken)
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"userId\": " + victimId + ", \"mode\": \"PURGE\"}"))
					.andExpect(status().isNoContent());
			assertEquals(0, count("zfgbb.\"user\" where user_id = " + victimId),
					"a console purge must remove the account outright");
		}

		@Test
		void adminTogglesABbcodeAndRenderingReflectsIt() throws Exception {
			String adminToken = login(ADMIN_USER, ADMIN_PASSWORD).get("accessToken").asString();
			String previewPayload = """
					{"content": "[move]toggle probe[/move]", "scope": "FORUM"}
					""";

			MvcResult listResult = mockMvc.perform(get("/system/bbcodes")
					.header("Authorization", "Bearer " + adminToken))
					.andExpect(status().isOk())
					.andReturn();
			boolean moveListed = false;
			for (JsonNode toggle : json.readTree(listResult.getResponse().getContentAsString()))
				if ("move".equals(toggle.get("code").asText()))
					moveListed = true;
			assertTrue(moveListed, "the toggle console must list the seeded move bbcode");

			try {
				mockMvc.perform(put("/system/bbcodes/move")
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
				mockMvc.perform(put("/system/bbcodes/move")
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
}
