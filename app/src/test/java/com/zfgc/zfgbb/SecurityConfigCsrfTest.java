package com.zfgc.zfgbb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.servlet.http.Cookie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class SecurityConfigCsrfTest {

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

	private Cookie obtainXsrfCookie() throws Exception {
		MvcResult result = mockMvc.perform(get("/users/loggedInUser"))
				.andExpect(status().isOk())
				.andExpect(cookie().exists("XSRF-TOKEN"))
				.andReturn();
		return result.getResponse().getCookie("XSRF-TOKEN");
	}

	@Test
	void get_seeds_xsrf_cookie_at_root_path() throws Exception {
		Cookie xsrf = obtainXsrfCookie();
		org.junit.jupiter.api.Assertions.assertEquals("/", xsrf.getPath());
		org.junit.jupiter.api.Assertions.assertFalse(xsrf.isHttpOnly());
	}

	@Test
	void protected_mutation_without_csrf_token_is_forbidden() throws Exception {
		mockMvc.perform(post("/thread")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))
				.andExpect(status().isForbidden());
	}

	@Test
	void protected_mutation_with_cookie_but_no_header_is_forbidden() throws Exception {
		Cookie xsrf = obtainXsrfCookie();
		mockMvc.perform(post("/thread")
				.cookie(xsrf)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))
				.andExpect(status().isForbidden());
	}

	@Test
	void protected_mutation_with_matching_csrf_token_passes_filter() throws Exception {
		Cookie xsrf = obtainXsrfCookie();
		mockMvc.perform(post("/thread")
				.cookie(xsrf)
				.header("X-XSRF-TOKEN", xsrf.getValue())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))
				.andExpect(result -> {
					int status = result.getResponse().getStatus();
					if (status == 403) {
						throw new AssertionError("Request was rejected as CSRF even though token matched: " + status);
					}
				});
	}

	@Test
	void header_bearer_request_is_csrf_exempt() throws Exception {
		mockMvc.perform(post("/thread")
				.header("Authorization", "Bearer fake-token-value")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))
				.andExpect(result -> {
					int status = result.getResponse().getStatus();
					if (status == 403) {
						throw new AssertionError(
								"Header-bearer request was 403 — should bypass CSRF and fail later in auth (401). Got: "
										+ status);
					}
				});
	}

	@Test
	void cookie_bearer_request_without_csrf_header_is_forbidden() throws Exception {
		Cookie accessTokenCookie = new Cookie("zfgbb_access_token", "fake-jwt-value");
		mockMvc.perform(post("/thread")
				.cookie(accessTokenCookie)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))
				.andExpect(status().isForbidden());
	}
}
