package com.zfgc.zfgbb.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.zfgc.zfgbb.model.users.TokenPair;
import com.zfgc.zfgbb.services.core.AuthService;
import com.zfgc.zfgbb.services.core.RefreshTokenService;
import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

import jakarta.servlet.http.Cookie;
import tools.jackson.databind.JsonNode;

class SessionTest extends PostgresIntegrationTest {

	@Autowired
	private AuthService authService;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private PlatformTransactionManager transactionManager;

	private static Cookie accessCookie() {
		return new Cookie("zfgbb_access_token", "fake-jwt-value");
	}

	private int activeTokens(Integer userId) {
		Integer count = jdbcTemplate.queryForObject("select count(*) from zfgbb.user_refresh_token "
				+ "where user_id = ? and revoked_flag = false", Integer.class, userId);
		return count == null ? 0 : count;
	}

	private int failedLoginCount(String userName) {
		Integer value = jdbcTemplate.queryForObject(
				"select failed_login_count from zfgbb.\"user\" where user_name = ?", Integer.class, userName);
		return value == null ? 0 : value;
	}

	@Nested
	class CsrfChain {

		@Test
		void getSeedsXsrfCookieAtRootPath() throws Exception {
			Cookie xsrf = obtainXsrfCookie();
			assertEquals("/", xsrf.getPath());
			assertFalse(xsrf.isHttpOnly());
		}

		@Test
		void nonPublicGetEndpointRequiresAuth() throws Exception {
			mockMvc.perform(get("/thread/1/split"))
					.andExpect(status().isUnauthorized());
		}

		@Test
		void anonymousMutationSkipsCsrfAndIsUnauthorized() throws Exception {
			mockMvc.perform(post("/thread")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{}"))
					.andExpect(status().isUnauthorized());
		}

		@Test
		void cookieAuthedMutationWithoutAnyCsrfTokenIsForbidden() throws Exception {
			mockMvc.perform(post("/thread")
					.cookie(accessCookie())
					.contentType(MediaType.APPLICATION_JSON)
					.content("{}"))
					.andExpect(status().isForbidden());
		}

		@Test
		void cookieAuthedMutationWithXsrfCookieButNoHeaderIsForbidden() throws Exception {
			Cookie xsrf = obtainXsrfCookie();
			mockMvc.perform(post("/thread")
					.cookie(accessCookie(), xsrf)
					.contentType(MediaType.APPLICATION_JSON)
					.content("{}"))
					.andExpect(status().isForbidden());
		}

		@Test
		void cookieAuthedMutationWithMatchingCsrfTokenPassesFilter() throws Exception {
			Cookie xsrf = obtainXsrfCookie();
			int status = mockMvc.perform(post("/thread")
					.cookie(accessCookie(), xsrf)
					.header("X-XSRF-TOKEN", xsrf.getValue())
					.contentType(MediaType.APPLICATION_JSON)
					.content("{}"))
					.andReturn().getResponse().getStatus();
			assertNotEquals(403, status, "matching CSRF token must pass the filter");
		}

		@Test
		void headerBearerRequestIsCsrfExempt() throws Exception {
			int status = mockMvc.perform(post("/thread")
					.header("Authorization", "Bearer fake-token-value")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{}"))
					.andReturn().getResponse().getStatus();
			assertNotEquals(403, status, "header-bearer requests bypass CSRF and fail later in auth");
		}
	}

	@Nested
	class RefreshRotation {

		@Test
		void concurrentRefreshConvergesOnOneSuccessorThenReuseRevokesFamily() throws Exception {
			String username = "refresh_race_" + UUID.randomUUID().toString().substring(0, 8);
			register(username, "password123");
			JsonNode login = login(username, "password123");
			String original = login.get("refreshToken").asString();
			Integer userId = jdbcTemplate.queryForObject("select user_id from zfgbb.user where user_name = ?",
					Integer.class, username);
			int baselineActive = activeTokens(userId);

			int contenders = 8;
			CountDownLatch ready = new CountDownLatch(contenders);
			CountDownLatch start = new CountDownLatch(1);
			ExecutorService pool = Executors.newFixedThreadPool(contenders);
			List<Future<Object>> attempts = new ArrayList<>();
			try {
				for (int i = 0; i < contenders; i++) {
					attempts.add(pool.submit(() -> {
						ready.countDown();
						start.await();
						try {
							return authService.refresh(original);
						} catch (BadCredentialsException rejected) {
							return rejected;
						}
					}));
				}
				assertTrue(ready.await(10, TimeUnit.SECONDS), "refresh contenders did not become ready");
				start.countDown();

				List<Object> outcomes = new ArrayList<>();
				for (Future<Object> attempt : attempts)
					outcomes.add(attempt.get(30, TimeUnit.SECONDS));
				List<TokenPair> pairs = outcomes.stream().filter(TokenPair.class::isInstance)
						.map(TokenPair.class::cast).toList();
				assertEquals(contenders, pairs.size(), outcomes.toString());
				assertEquals(0, outcomes.stream().filter(BadCredentialsException.class::isInstance).count(),
						outcomes.toString());
				List<String> distinctSuccessors = pairs.stream().map(TokenPair::refreshToken).distinct().toList();
				assertEquals(1, distinctSuccessors.size(),
						"every contender must converge on the same single successor");
				assertNotNull(distinctSuccessors.get(0));
				assertEquals(baselineActive, activeTokens(userId),
						"rotation must revoke one token and create exactly one successor");

				TokenPair secondGeneration = authService.refresh(distinctSuccessors.get(0));
				assertNotNull(secondGeneration.refreshToken());
				assertEquals(baselineActive, activeTokens(userId),
						"advancing the successor keeps exactly one live token");

				assertThrows(BadCredentialsException.class, () -> authService.refresh(original),
						"replaying the rotated original after its successor was consumed must be rejected");
				assertThrows(BadCredentialsException.class, () -> authService.refresh(secondGeneration.refreshToken()),
						"the whole family must be revoked once reuse is detected");
				assertEquals(0, activeTokens(userId), "reuse detection must revoke the entire token family");
			} finally {
				for (Future<Object> attempt : attempts)
					attempt.cancel(true);
				pool.shutdownNow();
				assertTrue(pool.awaitTermination(10, TimeUnit.SECONDS), "refresh executor did not terminate");
			}
		}

		@Test
		void reuseOfRotatedTokenAfterSuccessorUsedRevokesEntireFamily() throws Exception {
			String username = "refresh_reuse_" + UUID.randomUUID().toString().substring(0, 8);
			register(username, "password123");
			String tokenA = login(username, "password123").get("refreshToken").asString();
			Integer userId = jdbcTemplate.queryForObject("select user_id from zfgbb.user where user_name = ?",
					Integer.class, username);

			TokenPair tokenB = authService.refresh(tokenA);
			TokenPair tokenC = authService.refresh(tokenB.refreshToken());
			assertNotNull(tokenC.refreshToken());

			assertThrows(BadCredentialsException.class, () -> authService.refresh(tokenA),
					"reusing the rotated original after its successor was consumed must trigger reuse detection");
			assertThrows(BadCredentialsException.class, () -> authService.refresh(tokenC.refreshToken()),
					"the current live token must be revoked along with the rest of the family");
			assertEquals(0, activeTokens(userId), "reuse detection must revoke the entire token family");
		}

		@Test
		void benignDoubleSubmitWithinGraceReturnsSameSuccessor() throws Exception {
			String username = "refresh_benign_" + UUID.randomUUID().toString().substring(0, 8);
			register(username, "password123");
			String tokenA = login(username, "password123").get("refreshToken").asString();
			Integer userId = jdbcTemplate.queryForObject("select user_id from zfgbb.user where user_name = ?",
					Integer.class, username);
			int baselineActive = activeTokens(userId);

			TokenPair first = authService.refresh(tokenA);
			TokenPair second = authService.refresh(tokenA);
			assertEquals(first.refreshToken(), second.refreshToken(),
					"a benign double submit within the grace window must return the already-issued successor");
			assertEquals(baselineActive, activeTokens(userId),
					"a benign double submit must not mint a second successor");
		}

		@Test
		void transactionFailureAfterConsumeRollsBackTheCas() throws Exception {
			String username = "refresh_rollback_" + UUID.randomUUID().toString().substring(0, 8);
			register(username, "password123");
			String original = login(username, "password123").get("refreshToken").asString();

			TransactionTemplate transaction = new TransactionTemplate(transactionManager);
			assertThrows(IllegalStateException.class, () -> transaction.executeWithoutResult(status -> {
				refreshTokenService.consume(original);
				throw new IllegalStateException("simulated issuance failure");
			}));

			TokenPair retried = authService.refresh(original);
			assertNotNull(retried.refreshToken(), "rolled-back consumption must remain retryable");
			assertTrue(!retried.refreshToken().isBlank());
		}
	}

	@Nested
	class CredentialFlows {

		@Test
		void refreshRotatesAndLogoutRevokesTheFamily() throws Exception {
			String userName = "it_" + suffix;
			register(userName, "password123");

			JsonNode loginJson = login(userName, "password123");
			String refreshToken = loginJson.get("refreshToken").asString();
			assertEquals(900, loginJson.get("accessTokenTtlSeconds").asLong(),
					"login response must advertise the access-token TTL");
			Cookie xsrf = obtainXsrfCookie();

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
					.andExpect(jsonPath("$.accessTokenTtlSeconds").value(900))
					.andReturn();
			String newRefreshToken = json.readTree(refreshResult.getResponse().getContentAsString())
					.get("refreshToken").asString();

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
		void cookieVectorRefreshWithoutCsrfTokenSucceeds() throws Exception {
			String userName = "cv_" + suffix;
			register(userName, "password123");

			String loginBody = """
					{"username": "%s", "password": "password123", "stayLoggedIn": true}
					""".formatted(userName);
			MvcResult loginResult = mockMvc.perform(post("/users/auth/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(loginBody))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.accessTokenTtlSeconds").value(900))
					.andExpect(jsonPath("$.accessToken").doesNotExist())
					.andExpect(jsonPath("$.refreshToken").doesNotExist())
					.andReturn();
			Cookie accessCookie = loginResult.getResponse().getCookie("zfgbb_access_token");
			Cookie refreshCookie = loginResult.getResponse().getCookie("zfgbb_refresh_token");

			mockMvc.perform(post("/users/auth/refresh")
					.cookie(accessCookie, refreshCookie))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.accessTokenTtlSeconds").value(900))
					.andExpect(jsonPath("$.accessToken").doesNotExist())
					.andExpect(jsonPath("$.refreshToken").doesNotExist())
					.andExpect(cookie().exists("zfgbb_access_token"))
					.andExpect(cookie().exists("zfgbb_refresh_token"));
		}

		@Test
		void rotatedRefreshTokenReuseHonorsGraceWindow() throws Exception {
			String userName = "gr_" + suffix;
			register(userName, "password123");
			JsonNode loginJson = login(userName, "password123");
			String originalRefreshToken = loginJson.get("refreshToken").asString();
			Cookie xsrf = obtainXsrfCookie();

			String refreshBody = """
					{"refreshToken": "%s"}
					""".formatted(originalRefreshToken);
			mockMvc.perform(post("/users/auth/refresh")
					.cookie(xsrf)
					.header("X-XSRF-TOKEN", xsrf.getValue())
					.contentType(MediaType.APPLICATION_JSON)
					.content(refreshBody))
					.andExpect(status().isOk());

			mockMvc.perform(post("/users/auth/refresh")
					.cookie(xsrf)
					.header("X-XSRF-TOKEN", xsrf.getValue())
					.contentType(MediaType.APPLICATION_JSON)
					.content(refreshBody))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.accessToken").isString())
					.andExpect(jsonPath("$.refreshToken").isString());

			jdbcTemplate.update("""
					update zfgbb.user_refresh_token
					set rotated_ts = rotated_ts - interval '10 minutes'
					where rotated_ts is not null
					and user_id = (select user_id from zfgbb."user" where user_name = ?)
					""", userName);

			mockMvc.perform(post("/users/auth/refresh")
					.cookie(xsrf)
					.header("X-XSRF-TOKEN", xsrf.getValue())
					.contentType(MediaType.APPLICATION_JSON)
					.content(refreshBody))
					.andExpect(status().isUnauthorized());
		}

		@Test
		void lockoutAfterThresholdFailedAttemptsBlocksCorrectPassword() throws Exception {
			String lockoutUser = "lock_" + suffix;
			register(lockoutUser, "rightpassword");

			String wrongLogin = """
					{"username": "%s", "password": "wrongpassword"}
					""".formatted(lockoutUser);
			for (int i = 0; i < 10; i++) {
				mockMvc.perform(post("/users/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(wrongLogin))
						.andExpect(status().isUnauthorized());
			}

			String rightLogin = """
					{"username": "%s", "password": "rightpassword"}
					""".formatted(lockoutUser);
			mockMvc.perform(post("/users/auth/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(rightLogin))
					.andExpect(status().isUnauthorized());

			assertEquals(1,
					count("zfgbb.\"user\" where user_name = '" + lockoutUser + "' and locked_until_ts is not null"),
					"lockout must be persisted on the account");
		}

		@Test
		void badCredentialsLoginReturnsUnauthorized() throws Exception {
			String userName = "badcred_" + suffix;
			register(userName, "rightpassword");

			mockMvc.perform(post("/users/auth/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"username\": \"" + userName + "\", \"password\": \"wrongpassword\"}"))
					.andExpect(status().isUnauthorized());
		}

		@Test
		void lockoutCounterResetsAfterWindowLapses() throws Exception {
			String lapseUser = "lapse_" + suffix;
			register(lapseUser, "rightpassword");

			String wrongLogin = """
					{"username": "%s", "password": "wrongpassword"}
					""".formatted(lapseUser);
			for (int i = 0; i < 10; i++)
				mockMvc.perform(post("/users/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(wrongLogin))
						.andExpect(status().isUnauthorized());

			assertEquals(10, failedLoginCount(lapseUser), "the lockout threshold must have been reached");
			assertEquals(1,
					count("zfgbb.\"user\" where user_name = '" + lapseUser + "' and locked_until_ts is not null"),
					"the account must be locked after reaching the threshold");

			jdbcTemplate.update("update zfgbb.\"user\" set locked_until_ts = now() - interval '1 minute' "
					+ "where user_name = ?", lapseUser);

			mockMvc.perform(post("/users/auth/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(wrongLogin))
					.andExpect(status().isUnauthorized());

			assertEquals(1, failedLoginCount(lapseUser),
					"a lapsed lock must reset the counter to this single fresh attempt, not keep it pinned");
			assertEquals(0,
					count("zfgbb.\"user\" where user_name = '" + lapseUser + "' and locked_until_ts is not null"),
					"a lapsed lock must be cleared rather than immediately re-applied");

			String rightLogin = """
					{"username": "%s", "password": "rightpassword"}
					""".formatted(lapseUser);
			mockMvc.perform(post("/users/auth/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(rightLogin))
					.andExpect(status().isOk());

			assertEquals(0, failedLoginCount(lapseUser), "a successful login must clear the failed-login counter");
		}

		@Test
		void concurrentFailedLoginsIncrementAtomicallyAndLockOnce() throws Exception {
			String raceUser = "loginrace_" + suffix;
			register(raceUser, "rightpassword");
			Integer userId = userIdOf(raceUser);

			int contenders = 10;
			CountDownLatch ready = new CountDownLatch(contenders);
			CountDownLatch start = new CountDownLatch(1);
			ExecutorService pool = Executors.newFixedThreadPool(contenders);
			List<Future<Object>> attempts = new ArrayList<>();
			try {
				for (int i = 0; i < contenders; i++) {
					attempts.add(pool.submit(() -> {
						ready.countDown();
						start.await();
						try {
							return authService.reauthenticate(raceUser, "wrongpassword");
						} catch (RuntimeException rejected) {
							return rejected;
						}
					}));
				}
				assertTrue(ready.await(10, TimeUnit.SECONDS), "failed-login contenders did not become ready");
				start.countDown();

				List<Object> outcomes = new ArrayList<>();
				for (Future<Object> attempt : attempts)
					outcomes.add(attempt.get(30, TimeUnit.SECONDS));

				assertEquals(contenders, outcomes.stream().filter(BadCredentialsException.class::isInstance).count(),
						outcomes.toString());
				assertEquals(0, outcomes.stream().filter(ConcurrentModificationException.class::isInstance).count(),
						"atomic single-statement updates must never raise a concurrent-modification error");
				assertEquals(contenders, failedLoginCount(raceUser),
						"concurrent atomic increments must total exactly the contender count with no lost updates");
				assertEquals(1, count("zfgbb.\"user\" where user_id = " + userId + " and locked_until_ts is not null"),
						"reaching the threshold under contention must lock the account exactly once");
			} finally {
				for (Future<Object> attempt : attempts)
					attempt.cancel(true);
				pool.shutdownNow();
				assertTrue(pool.awaitTermination(10, TimeUnit.SECONDS), "failed-login executor did not terminate");
			}
		}
	}
}
