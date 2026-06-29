package com.zfgc.zfgbb.auth;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.test.util.ReflectionTestUtils;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.zfgc.zfgbb.config.loadoption.user.BasicUserLoadOptions;
import com.zfgc.zfgbb.config.security.AccessCookieBearerHeaderFilter;
import com.zfgc.zfgbb.config.security.JwtConfig;
import com.zfgc.zfgbb.config.security.JwtUserAuthenticationConverter;
import com.zfgc.zfgbb.dao.users.UserRefreshTokenDao;
import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.dbo.UserRefreshTokenDbo;
import com.zfgc.zfgbb.dbo.UserRefreshTokenDboExample;
import com.zfgc.zfgbb.mappers.custom.RefreshTokenConsumeMapper;
import com.zfgc.zfgbb.mappers.custom.RefreshTokenFamilyMapper;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.services.core.AuthCookieService;
import com.zfgc.zfgbb.services.core.RefreshTokenService;

import jakarta.servlet.http.HttpServletRequest;

class TokenTest {

	@Nested
	class JwtConversion {

		private static final Integer SUBJECT_USER_ID = 5;

		private UserDataProvider userDataProvider;
		private JwtUserAuthenticationConverter converter;

		@BeforeEach
		void setup() {
			userDataProvider = mock(UserDataProvider.class);
			converter = new JwtUserAuthenticationConverter();
			ReflectionTestUtils.setField(converter, "userDataProvider", userDataProvider);
		}

		private User enabledUser() {
			User user = new User();
			user.setUserId(SUBJECT_USER_ID);
			user.setActiveFlag(true);
			List<Permission> permissions = new ArrayList<>();
			Permission permission = new Permission();
			permission.setPermissionCode("ZFGC_USER");
			permissions.add(permission);
			user.setPermissions(permissions);
			return user;
		}

		private Jwt.Builder jwtForSubject(Integer subject) {
			return Jwt.withTokenValue("token-value")
					.header("alg", "none")
					.subject(String.valueOf(subject));
		}

		private void stubLookup(User user) {
			when(userDataProvider.findUser(eq(SUBJECT_USER_ID), any(BasicUserLoadOptions.class)))
					.thenReturn(Optional.ofNullable(user));
		}

		@Test
		void unknownSubjectIsRejected() {
			stubLookup(null);
			Jwt jwt = jwtForSubject(SUBJECT_USER_ID).build();

			assertThrows(InvalidBearerTokenException.class, () -> converter.convert(jwt));
		}

		@Test
		void disabledUserIsRejected() {
			User user = enabledUser();
			user.setActiveFlag(false);
			stubLookup(user);
			Jwt jwt = jwtForSubject(SUBJECT_USER_ID).build();

			assertThrows(InvalidBearerTokenException.class, () -> converter.convert(jwt));
		}

		@Test
		void tokenIssuedBeforeCutoffIsRejected() {
			User user = enabledUser();
			user.setTokensValidAfterTs(OffsetDateTime.now(ZoneOffset.UTC));
			stubLookup(user);
			Jwt jwt = jwtForSubject(SUBJECT_USER_ID).issuedAt(Instant.now().minusSeconds(3600)).build();

			assertThrows(InvalidBearerTokenException.class, () -> converter.convert(jwt));
		}

		@Test
		void missingIssuedAtWithACutoffIsRejected() {
			User user = enabledUser();
			user.setTokensValidAfterTs(OffsetDateTime.now(ZoneOffset.UTC));
			stubLookup(user);
			Jwt jwt = jwtForSubject(SUBJECT_USER_ID).build();

			assertThrows(InvalidBearerTokenException.class, () -> converter.convert(jwt));
		}

		@Test
		void nullCutoffAcceptsAndCarriesUserAuthorities() {
			User user = enabledUser();
			user.setTokensValidAfterTs(null);
			stubLookup(user);
			Jwt jwt = jwtForSubject(SUBJECT_USER_ID).issuedAt(Instant.now()).build();

			AbstractAuthenticationToken authentication = converter.convert(jwt);

			assertSame(user, authentication.getPrincipal());
			assertSame(jwt, authentication.getCredentials());
			assertEquals(List.copyOf(user.getAuthorities()), List.copyOf(authentication.getAuthorities()));
		}

		@Test
		void tokenIssuedAfterCutoffIsAccepted() {
			User user = enabledUser();
			user.setTokensValidAfterTs(OffsetDateTime.now(ZoneOffset.UTC).minusHours(1));
			stubLookup(user);
			Jwt jwt = jwtForSubject(SUBJECT_USER_ID).issuedAt(Instant.now()).build();

			AbstractAuthenticationToken authentication = converter.convert(jwt);

			assertSame(user, authentication.getPrincipal());
		}
	}

	@Nested
	class CookieBearerBridge {

		private static final byte[] SIGNING_SECRET = "0123456789abcdef0123456789abcdef".getBytes(StandardCharsets.UTF_8);

		private AuthCookieService cookieService;
		private AccessCookieBearerHeaderFilter filter;

		@BeforeEach
		void setup() {
			cookieService = mock(AuthCookieService.class);
			filter = new AccessCookieBearerHeaderFilter(cookieService);
		}

		private String signedTokenExpiringAt(Instant expiry) throws Exception {
			SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256),
					new JWTClaimsSet.Builder().expirationTime(Date.from(expiry)).build());
			jwt.sign(new MACSigner(SIGNING_SECRET));
			return jwt.serialize();
		}

		@Test
		void existingAuthorizationHeaderPassesThroughUnwrapped() throws Exception {
			MockHttpServletRequest request = new MockHttpServletRequest("GET", "/thread");
			request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer preexisting");
			MockFilterChain chain = new MockFilterChain();

			filter.doFilter(request, new MockHttpServletResponse(), chain);

			assertSame(request, chain.getRequest());
			verify(cookieService, never()).readAccessCookie(any());
		}

		@Test
		void missingAccessCookiePassesTheRequestThroughUnchanged() throws Exception {
			MockHttpServletRequest request = new MockHttpServletRequest("GET", "/thread");
			when(cookieService.readAccessCookie(request)).thenReturn(Optional.empty());
			MockFilterChain chain = new MockFilterChain();

			filter.doFilter(request, new MockHttpServletResponse(), chain);

			assertSame(request, chain.getRequest());
		}

		@Test
		void validAccessCookieIsPromotedToABearerAuthorizationHeader() throws Exception {
			MockHttpServletRequest request = new MockHttpServletRequest("GET", "/thread");
			when(cookieService.readAccessCookie(request)).thenReturn(Optional.of("opaque-access-token"));
			MockFilterChain chain = new MockFilterChain();

			filter.doFilter(request, new MockHttpServletResponse(), chain);

			HttpServletRequest forwarded = (HttpServletRequest) chain.getRequest();
			assertNotSame(request, forwarded);
			assertEquals("Bearer opaque-access-token", forwarded.getHeader(HttpHeaders.AUTHORIZATION));
		}

		@Test
		void expiredAccessCookieIsNotPromoted() throws Exception {
			MockHttpServletRequest request = new MockHttpServletRequest("GET", "/thread");
			String expiredToken = signedTokenExpiringAt(Instant.now().minusSeconds(3600));
			when(cookieService.readAccessCookie(request)).thenReturn(Optional.of(expiredToken));
			MockFilterChain chain = new MockFilterChain();

			filter.doFilter(request, new MockHttpServletResponse(), chain);

			assertSame(request, chain.getRequest());
		}

		@Test
		void unexpiredSignedAccessCookieIsPromoted() throws Exception {
			MockHttpServletRequest request = new MockHttpServletRequest("GET", "/thread");
			String liveToken = signedTokenExpiringAt(Instant.now().plusSeconds(3600));
			when(cookieService.readAccessCookie(request)).thenReturn(Optional.of(liveToken));
			MockFilterChain chain = new MockFilterChain();

			filter.doFilter(request, new MockHttpServletResponse(), chain);

			HttpServletRequest forwarded = (HttpServletRequest) chain.getRequest();
			assertNotSame(request, forwarded);
			assertEquals("Bearer " + liveToken, forwarded.getHeader(HttpHeaders.AUTHORIZATION));
		}
	}

	@Nested
	class Signing {
		private static final String PLACEHOLDER = "dev-only-secret-replace-me-with-32-or-more-bytes-please";

		@Test
		void dockerProfileRejectsRepositoryPlaceholder() {
			assertThrows(IllegalStateException.class, config("docker", PLACEHOLDER)::jwtSigningKey);
		}

		@Test
		void localProfileMayUseDevelopmentPlaceholder() {
			assertDoesNotThrow(config("local", PLACEHOLDER)::jwtSigningKey);
		}

		@Test
		void dockerProfileAcceptsStrongConfiguredSecret() {
			assertDoesNotThrow(config("docker", "a-production-secret-with-at-least-32-characters")::jwtSigningKey);
		}

		private JwtConfig config(String profile, String secret) {
			MockEnvironment environment = new MockEnvironment();
			environment.setActiveProfiles(profile);
			JwtConfig config = new JwtConfig(environment);
			ReflectionTestUtils.setField(config, "secret", secret);
			return config;
		}
	}

	@Nested
	class RefreshConsumption {

		@Test
		void tokenCanBeConsumedOnlyOnce() {
			OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
			UserRefreshTokenDbo token = new UserRefreshTokenDbo();
			token.setUserRefreshTokenId(11);
			token.setUserId(7);
			token.setIssuedTs(now.minusHours(1));
			token.setExpiresTs(now.plusHours(1));
			token.setRevokedFlag(false);

			RefreshTokenService service = new RefreshTokenService(new FixedTokenDao(token), new NoOpFamilyMapper(), 30, 24, 60);
			SingleSuccessConsumeMapper consumeMapper = new SingleSuccessConsumeMapper();
			ReflectionTestUtils.setField(service, "refreshTokenConsumeMapper", consumeMapper);

			assertEquals(7, service.consume("token").userId());
			assertThrows(BadCredentialsException.class, () -> service.consume("token"));
			assertEquals(2, consumeMapper.attempts);
		}

		private static final class FixedTokenDao extends UserRefreshTokenDao {
			private final UserRefreshTokenDbo token;

			FixedTokenDao(UserRefreshTokenDbo token) {
				super(null);
				this.token = token;
			}

			@Override
			public List<UserRefreshTokenDbo> get(UserRefreshTokenDboExample ex) {
				return List.of(token);
			}
		}

		private static final class SingleSuccessConsumeMapper implements RefreshTokenConsumeMapper {
			private int attempts;

			@Override
			public int consumeToken(Integer userRefreshTokenId, OffsetDateTime now) {
				attempts++;
				return attempts == 1 ? 1 : 0;
			}
		}

		private static final class NoOpFamilyMapper implements RefreshTokenFamilyMapper {
			@Override
			public int backlinkSuccessor(Integer parentId, Integer successorId, OffsetDateTime now) {
				return 0;
			}

			@Override
			public int revokeFamily(String familyId, OffsetDateTime now) {
				return 0;
			}
		}
	}
}
