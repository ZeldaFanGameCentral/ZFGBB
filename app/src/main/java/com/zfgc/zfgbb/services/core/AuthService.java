package com.zfgc.zfgbb.services.core;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.config.loadoption.user.LoggedInUserLoadOptions;
import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.mappers.custom.LoginLockoutMapper;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.users.AuthCredentials;
import com.zfgc.zfgbb.model.users.ConsumedRefreshToken;
import com.zfgc.zfgbb.model.users.LoginResponse;
import com.zfgc.zfgbb.model.users.TokenPair;

@Service
@Transactional(noRollbackFor = { BadCredentialsException.class, LockedException.class, CredentialsExpiredException.class,
		DisabledException.class })
public class AuthService {

	private final UserDataProvider userDataProvider;
	private final LoginLockoutMapper loginLockoutMapper;
	private final AuthenticationManager loginAuthenticationManager;
	private final JwtService jwtService;
	private final RefreshTokenService refreshTokenService;
	private final int lockoutFailedAttempts;
	private final long lockoutDurationMinutes;
	private final long accessTokenTtlSeconds;

	public AuthService(UserDataProvider userDataProvider,
			LoginLockoutMapper loginLockoutMapper,
			@Qualifier("loginAuthenticationManager") AuthenticationManager loginAuthenticationManager,
			JwtService jwtService,
			RefreshTokenService refreshTokenService,
			@Value("${zfgbb.auth.lockout.failed-attempts}") int lockoutFailedAttempts,
			@Value("${zfgbb.auth.lockout.duration-minutes}") long lockoutDurationMinutes,
			@Value("${zfgbb.auth.jwt.access-ttl-minutes}") long accessTokenTtlMinutes) {
		this.userDataProvider = userDataProvider;
		this.loginLockoutMapper = loginLockoutMapper;
		this.loginAuthenticationManager = loginAuthenticationManager;
		this.jwtService = jwtService;
		this.refreshTokenService = refreshTokenService;
		this.lockoutFailedAttempts = lockoutFailedAttempts;
		this.lockoutDurationMinutes = lockoutDurationMinutes;
		this.accessTokenTtlSeconds = Duration.ofMinutes(accessTokenTtlMinutes).toSeconds();
	}

	public LoginResponse login(AuthCredentials credentials) {
		if (credentials == null)
			throw new BadCredentialsException("Username and password are required.");

		User principal = reauthenticate(credentials.getUsername(), credentials.getPassword());

		User user = userDataProvider.findUser(principal.getUserId(), new LoggedInUserLoadOptions())
				.orElseThrow(() -> new BadCredentialsException("Invalid username or password"));
		return issueLoginResponse(user, credentials.isStayLoggedIn());
	}

	public User reauthenticate(String username, String password) {
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password))
			throw new BadCredentialsException("Username and password are required.");

		Authentication authentication;
		try {
			authentication = loginAuthenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException wrongPassword) {
			userDataProvider.findByUserName(username).ifPresent(this::recordFailedLogin);
			throw wrongPassword;
		}

		User principal = (User) authentication.getPrincipal();
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		loginLockoutMapper.clearFailedLoginState(principal.getUserId(), now);
		return principal;
	}

	public LoginResponse issueLoginResponse(User user, boolean stayLoggedIn) {
		String accessToken = jwtService.issueAccessToken(user);
		String refreshToken = refreshTokenService.issue(user.getUserId(), stayLoggedIn);
		return new LoginResponse(accessToken, refreshToken, stayLoggedIn, accessTokenTtlSeconds, user);
	}

	public TokenPair refresh(String refreshToken) {
		ConsumedRefreshToken consumed = refreshTokenService.consume(refreshToken);
		User user = userDataProvider.findUser(consumed.userId(), new LoggedInUserLoadOptions())
				.orElseThrow(() -> new BadCredentialsException("Refresh token references unknown user"));
		if (!user.isEnabled())
			throw new BadCredentialsException("Refresh token references a disabled user");
		if (issuedBeforeTokenCutoff(consumed.issuedTs(), user.getTokensValidAfterTs()))
			throw new BadCredentialsException("Refresh token was issued before the token validity cutoff");
		String newAccess = jwtService.issueAccessToken(user);
		String newRefresh = consumed.existingSuccessorRaw() != null
				? consumed.existingSuccessorRaw()
				: refreshTokenService.issueSuccessor(user.getUserId(), consumed.stayLoggedIn(), consumed.familyId(),
						consumed.parentTokenId());
		return new TokenPair(newAccess, newRefresh, consumed.stayLoggedIn(), accessTokenTtlSeconds);
	}

	private boolean issuedBeforeTokenCutoff(OffsetDateTime issuedTs, OffsetDateTime tokensValidAfterTs) {
		if (tokensValidAfterTs == null)
			return false;
		return issuedTs == null || issuedTs.isBefore(tokensValidAfterTs);
	}

	public void logout(String refreshToken) {
		refreshTokenService.revoke(refreshToken);
	}

	private void recordFailedLogin(UserDbo dbo) {
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		OffsetDateTime lockUntil = now.plus(Duration.ofMinutes(lockoutDurationMinutes));
		loginLockoutMapper.recordFailedLoginAttempt(dbo.getUserId(), now, lockoutFailedAttempts, lockUntil);
	}
}
