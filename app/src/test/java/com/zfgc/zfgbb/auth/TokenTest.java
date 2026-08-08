package com.zfgc.zfgbb.auth;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.test.util.ReflectionTestUtils;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.zfgc.zfgbb.dataprovider.loadoption.UserLoadOptions;
import com.zfgc.zfgbb.web.filter.AccessCookieBearerHeaderFilter;
import com.zfgc.zfgbb.config.security.JwtConfig;
import com.zfgc.zfgbb.config.security.JwtProperties;
import com.zfgc.zfgbb.config.security.UserJwtAuthenticationConverter;
import com.zfgc.zfgbb.services.auth.TokenSubjectValidator;
import com.zfgc.zfgbb.dao.users.UserDao;
import com.zfgc.zfgbb.dao.users.UserRefreshTokenDao;
import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.dbo.UserRefreshTokenDbo;
import com.zfgc.zfgbb.dbo.UserRefreshTokenDboExample;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.users.ConsumedRefreshToken;
import com.zfgc.zfgbb.model.users.PasswordAlgo;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.services.auth.AuthCookieService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import com.zfgc.zfgbb.services.auth.ZfgcPasswordEncoder;
import com.zfgc.zfgbb.services.auth.AuthService;

import jakarta.servlet.http.HttpServletRequest;

class TokenTest {

	@Nested
	class JwtConversion {

		private static final Integer SUBJECT_USER_ID = 5;

		private UserDataProvider userDataProvider;
		private UserJwtAuthenticationConverter converter;

		@BeforeEach
		void setup() {
			userDataProvider = mock(UserDataProvider.class);
			converter = new UserJwtAuthenticationConverter(new TokenSubjectValidator(userDataProvider));
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
			when(userDataProvider.findUser(eq(SUBJECT_USER_ID), any(UserLoadOptions.class)))
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
		void tokenIssuedExactlyAtCutoffIsRejected() {
			Instant cutoff = Instant.now().truncatedTo(ChronoUnit.SECONDS);
			User user = enabledUser();
			user.setTokensValidAfterTs(OffsetDateTime.ofInstant(cutoff, ZoneOffset.UTC));
			stubLookup(user);
			Jwt jwt = jwtForSubject(SUBJECT_USER_ID).issuedAt(cutoff).build();

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
	class RefreshCutoff {

		private static final Integer TOKEN_HOLDER_USER_ID = 9;
		private static final String PRESENTED_REFRESH_TOKEN = "presented-refresh-token";
		private static final String FAMILY_ID = "family-id";
		private static final Integer PARENT_TOKEN_ID = 41;

		private UserDataProvider userDataProvider;
		private JwtEncoder accessTokenEncoder;
		private AuthService authService;

		@BeforeEach
		void setup() {
			userDataProvider = mock(UserDataProvider.class);
			accessTokenEncoder = mock(JwtEncoder.class);
			Jwt encodedAccessToken = mock(Jwt.class);
			when(encodedAccessToken.getTokenValue()).thenReturn("reissued-access-token");
			when(accessTokenEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(encodedAccessToken);
			authService = spy(new AuthService(userDataProvider, new TokenSubjectValidator(userDataProvider),
					mock(UserDao.class),
					mock(AuthenticationManager.class), accessTokenEncoder, mock(UserRefreshTokenDao.class),
					30, 24, 60, 10, 15, 15));
		}

		private void stubTokenHolderWithCutoff(OffsetDateTime tokensValidAfterTs) {
			User holder = new User();
			holder.setUserId(TOKEN_HOLDER_USER_ID);
			holder.setActiveFlag(true);
			holder.setTokensValidAfterTs(tokensValidAfterTs);
			when(userDataProvider.findUser(eq(TOKEN_HOLDER_USER_ID), any(UserLoadOptions.class)))
					.thenReturn(Optional.of(holder));
		}

		private void stubConsumptionIssuedAt(OffsetDateTime issuedTs) {
			doReturn(new ConsumedRefreshToken(TOKEN_HOLDER_USER_ID, false, issuedTs, FAMILY_ID,
					PARENT_TOKEN_ID, null)).when(authService).consume(PRESENTED_REFRESH_TOKEN);
			doReturn("rotated-refresh-token").when(authService)
					.issueSuccessor(TOKEN_HOLDER_USER_ID, false, FAMILY_ID, PARENT_TOKEN_ID);
		}

		private void assertRotationWasRefused() {
			assertThrows(BadCredentialsException.class, () -> authService.refresh(PRESENTED_REFRESH_TOKEN));
			verify(authService, never()).issueSuccessor(any(), anyBoolean(), any(), any());
			verify(accessTokenEncoder, never()).encode(any(JwtEncoderParameters.class));
		}

		@Test
		void refreshTokenIssuedBeforeTheCutoffIsRefusedWithoutMintingAnything() {
			OffsetDateTime cutoff = OffsetDateTime.now(ZoneOffset.UTC);
			stubTokenHolderWithCutoff(cutoff);
			stubConsumptionIssuedAt(cutoff.minusHours(1));

			assertRotationWasRefused();
		}

		@Test
		void refreshTokenIssuedExactlyAtTheCutoffIsRefused() {
			OffsetDateTime cutoff = OffsetDateTime.now(ZoneOffset.UTC);
			stubTokenHolderWithCutoff(cutoff);
			stubConsumptionIssuedAt(cutoff);

			assertRotationWasRefused();
		}

		@Test
		void refreshTokenWithoutAnIssuedTimestampIsRefusedWhenTheHolderHasACutoff() {
			stubTokenHolderWithCutoff(OffsetDateTime.now(ZoneOffset.UTC));
			stubConsumptionIssuedAt(null);

			assertRotationWasRefused();
		}

		@Test
		void refreshTokenIssuedAfterTheCutoffIsRotated() {
			OffsetDateTime cutoff = OffsetDateTime.now(ZoneOffset.UTC).minusHours(1);
			stubTokenHolderWithCutoff(cutoff);
			stubConsumptionIssuedAt(cutoff.plusMinutes(1));

			assertEquals("rotated-refresh-token", authService.refresh(PRESENTED_REFRESH_TOKEN).refreshToken());
		}

		@Test
		void aHolderWithoutACutoffRotatesEvenAnAncientRefreshToken() {
			stubTokenHolderWithCutoff(null);
			stubConsumptionIssuedAt(OffsetDateTime.now(ZoneOffset.UTC).minusYears(5));

			assertEquals("rotated-refresh-token", authService.refresh(PRESENTED_REFRESH_TOKEN).refreshToken());
		}
	}

	@Nested
	class LegacyPasswordHashes {

		private static final String SHA1_OF_ABC = "a9993e364706816aba3e25717850c26c9cd0d89d";
		private static final String SHA1_OF_NOTHING = "da39a3ee5e6b4b0d3255bfef95601890afd80709";
		private static final String SHA1_OF_PASSWORD123_SALTY = "5f0d825c2820b3b82f944498dcbe232d2467a199";

		private final ZfgcPasswordEncoder passwordEncoder = new ZfgcPasswordEncoder();

		@Test
		void smfDigestsHexEncodeExactlyAsTheLegacyForumStoredThem() {
			assertTrue(passwordEncoder.verify("abc", SHA1_OF_ABC, PasswordAlgo.SMF2_SHA1, ""),
					"digest bytes at or above 0x80 must hex-encode unsigned, not as sign-extended values");
			assertTrue(passwordEncoder.verify("", SHA1_OF_NOTHING, PasswordAlgo.SMF2_SHA1, ""),
					"digest bytes below 0x10 must keep their leading zero rather than collapse to one nibble");
			assertTrue(passwordEncoder.verify("password123", SHA1_OF_PASSWORD123_SALTY, PasswordAlgo.SMF2_SHA1,
					"salty"), "the salt must be appended to the raw password before hashing");
		}

		@Test
		void smfDigestsRejectTheWrongPasswordAndRefuseToRunWithoutASalt() {
			assertFalse(passwordEncoder.verify("abd", SHA1_OF_ABC, PasswordAlgo.SMF2_SHA1, ""),
					"a one-character difference must not verify");
			assertFalse(passwordEncoder.verify("abc", SHA1_OF_ABC, PasswordAlgo.SMF2_SHA1, null),
					"a legacy hash with no recorded salt is unverifiable rather than salt-free");
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

		@Test
		void installBootstrapPathsIgnoreAccessCookiesIncludingUnderAContextPath() throws Exception {
			MockHttpServletRequest install = new MockHttpServletRequest("POST", "/zfgbb/system/install");
			install.setContextPath("/zfgbb");
			MockFilterChain installChain = new MockFilterChain();

			filter.doFilter(install, new MockHttpServletResponse(), installChain);

			assertSame(install, installChain.getRequest());

			MockHttpServletRequest status = new MockHttpServletRequest("GET", "/zfgbb/system/site");
			status.setContextPath("/zfgbb");
			MockFilterChain statusChain = new MockFilterChain();

			filter.doFilter(status, new MockHttpServletResponse(), statusChain);

			assertSame(status, statusChain.getRequest());
			verify(cookieService, never()).readAccessCookie(any());
		}

		@Test
		void installPrefixLookalikeStillPromotesAccessCookie() throws Exception {
			MockHttpServletRequest request = new MockHttpServletRequest("POST", "/system/installer");
			when(cookieService.readAccessCookie(request)).thenReturn(Optional.of("opaque-access-token"));
			MockFilterChain chain = new MockFilterChain();

			filter.doFilter(request, new MockHttpServletResponse(), chain);

			HttpServletRequest forwarded = (HttpServletRequest) chain.getRequest();
			assertNotSame(request, forwarded);
			assertEquals("Bearer opaque-access-token", forwarded.getHeader(HttpHeaders.AUTHORIZATION));
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
			JwtConfig config = new JwtConfig(new JwtProperties(secret), environment);
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

			FixedTokenDao refreshTokenDao = new FixedTokenDao(token);
			AuthService service = new AuthService(mock(UserDataProvider.class),
					new TokenSubjectValidator(mock(UserDataProvider.class)), mock(UserDao.class),
					mock(AuthenticationManager.class), mock(JwtEncoder.class), refreshTokenDao,
					30, 24, 60, 10, 15, 15);

			assertEquals(7, service.consume("token").userId());
			assertThrows(BadCredentialsException.class, () -> service.consume("token"));
			assertEquals(2, refreshTokenDao.consumeAttempts);
		}

		private static final class FixedTokenDao extends UserRefreshTokenDao {
			private final UserRefreshTokenDbo token;

			private int consumeAttempts;

			FixedTokenDao(UserRefreshTokenDbo token) {
				super(null, null);
				this.token = token;
			}

			@Override
			public List<UserRefreshTokenDbo> get(UserRefreshTokenDboExample ex) {
				return List.of(token);
			}

			@Override
			public int consume(Integer userRefreshTokenId, OffsetDateTime now) {
				consumeAttempts++;
				return consumeAttempts == 1 ? 1 : 0;
			}
		}
	}
}
