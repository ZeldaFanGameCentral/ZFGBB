package com.zfgc.zfgbb.services.core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.zfgc.zfgbb.dao.users.UserRefreshTokenDao;
import com.zfgc.zfgbb.dbo.UserRefreshTokenDbo;
import com.zfgc.zfgbb.dbo.UserRefreshTokenDboExample;
import com.zfgc.zfgbb.mappers.custom.RefreshTokenConsumeMapper;
import com.zfgc.zfgbb.mappers.custom.RefreshTokenFamilyMapper;
import com.zfgc.zfgbb.model.users.ConsumedRefreshToken;

@Service
// noRollbackFor: consume() throws when an already-used or expired refresh token is
// presented. That's an expected control-flow signal, not an error worth rolling
// back the surrounding tx (which would also undo legitimate writes higher up).
@Transactional(noRollbackFor = BadCredentialsException.class)
public class RefreshTokenService {

	private static final SecureRandom RNG = new SecureRandom();
	private static final int TOKEN_BYTES = 32;

	private final UserRefreshTokenDao dao;
	private final RefreshTokenFamilyMapper refreshTokenFamilyMapper;
	private final Duration rememberedTtl;
	private final Duration sessionTtl;
	private final Duration rotationGrace;
	private final Cache<Integer, String> successorCache;
	@Autowired
	private RefreshTokenConsumeMapper refreshTokenConsumeMapper;

	public RefreshTokenService(UserRefreshTokenDao dao,
			RefreshTokenFamilyMapper refreshTokenFamilyMapper,
			@Value("${zfgbb.auth.refresh.ttl-days}") long rememberedTtlDays,
			@Value("${zfgbb.auth.refresh.session-ttl-hours}") long sessionTtlHours,
			@Value("${zfgbb.auth.refresh.rotation-grace-seconds}") long rotationGraceSeconds) {
		this.dao = dao;
		this.refreshTokenFamilyMapper = refreshTokenFamilyMapper;
		this.rememberedTtl = Duration.ofDays(rememberedTtlDays);
		this.sessionTtl = Duration.ofHours(sessionTtlHours);
		if (rotationGraceSeconds < 0)
			throw new IllegalArgumentException("rotation grace must not be negative");
		this.rotationGrace = Duration.ofSeconds(rotationGraceSeconds);
		this.successorCache = CacheBuilder.newBuilder()
				.expireAfterWrite(rotationGrace)
				.maximumSize(100_000)
				.build();
	}

	public String issue(Integer userId, boolean stayLoggedIn) {
		String rawToken = generateRawToken();
		OffsetDateTime now = OffsetDateTime.now(java.time.ZoneOffset.UTC);
		Duration ttl = stayLoggedIn ? rememberedTtl : sessionTtl;
		UserRefreshTokenDbo dbo = new UserRefreshTokenDbo();
		dbo.setUserId(userId);
		dbo.setTokenHash(sha256Hex(rawToken));
		dbo.setIssuedTs(now);
		dbo.setExpiresTs(now.plus(ttl));
		dbo.setRevokedFlag(false);
		dbo.setFamilyId(UUID.randomUUID().toString());
		dbo.setSuccessorId(null);
		dao.save(dbo);
		return rawToken;
	}

	public ConsumedRefreshToken consume(String rawToken) {
		UserRefreshTokenDbo tokenRecord = lookup(rawToken)
				.orElseThrow(() -> new BadCredentialsException("Invalid refresh token."));

		OffsetDateTime now = OffsetDateTime.now(java.time.ZoneOffset.UTC);
		if (tokenRecord.getExpiresTs() == null || tokenRecord.getExpiresTs().isBefore(now))
			throw new BadCredentialsException("Refresh token has expired.");

		// Original lifetime > sessionTtl ⇒ this token was issued under "stay logged in",
		// so the rotated cookie should also be persistent.
		Duration originalTtl = Duration.between(tokenRecord.getIssuedTs(), tokenRecord.getExpiresTs());
		boolean stayLoggedIn = originalTtl.compareTo(sessionTtl) > 0;

		int consumed = refreshTokenConsumeMapper.consumeToken(tokenRecord.getUserRefreshTokenId(), now);
		if (consumed == 1)
			return new ConsumedRefreshToken(tokenRecord.getUserId(), stayLoggedIn, tokenRecord.getIssuedTs(),
					tokenRecord.getFamilyId(), tokenRecord.getUserRefreshTokenId(), null);

		UserRefreshTokenDbo current = lookup(rawToken)
				.orElseThrow(() -> new BadCredentialsException("Refresh token has already been used or revoked."));
		if (current.getRotatedTs() == null)
			throw new BadCredentialsException("Refresh token has already been used or revoked.");

		boolean withinGrace = current.getRotatedTs().isAfter(now.minus(rotationGrace));
		if (withinGrace && successorIsLive(current.getSuccessorId(), now)) {
			String existingSuccessorRaw = successorCache.getIfPresent(current.getUserRefreshTokenId());
			if (existingSuccessorRaw == null)
				throw new BadCredentialsException("Refresh token successor is no longer available.");
			return new ConsumedRefreshToken(current.getUserId(), stayLoggedIn, current.getIssuedTs(),
					current.getFamilyId(), current.getUserRefreshTokenId(), existingSuccessorRaw);
		}

		if (current.getSuccessorId() == null)
			throw new BadCredentialsException("Refresh token has already been used or revoked.");

		refreshTokenFamilyMapper.revokeFamily(current.getFamilyId(), now);
		throw new BadCredentialsException("Refresh token reuse detected; token family revoked.");
	}

	public String issueSuccessor(Integer userId, boolean stayLoggedIn, String familyId, Integer parentTokenId) {
		String rawSuccessor = generateRawToken();
		OffsetDateTime now = OffsetDateTime.now(java.time.ZoneOffset.UTC);
		Duration ttl = stayLoggedIn ? rememberedTtl : sessionTtl;
		UserRefreshTokenDbo dbo = new UserRefreshTokenDbo();
		dbo.setUserId(userId);
		dbo.setTokenHash(sha256Hex(rawSuccessor));
		dbo.setIssuedTs(now);
		dbo.setExpiresTs(now.plus(ttl));
		dbo.setRevokedFlag(false);
		dbo.setFamilyId(familyId);
		dbo.setSuccessorId(null);
		UserRefreshTokenDbo successor = dao.save(dbo);
		refreshTokenFamilyMapper.backlinkSuccessor(parentTokenId, successor.getUserRefreshTokenId(), now);
		successorCache.put(parentTokenId, rawSuccessor);
		return rawSuccessor;
	}

	private boolean successorIsLive(Integer successorId, OffsetDateTime now) {
		if (successorId == null)
			return false;
		UserRefreshTokenDbo successor = dao.get(successorId).orElse(null);
		if (successor == null)
			return false;
		return Boolean.FALSE.equals(successor.getRevokedFlag())
				&& successor.getExpiresTs() != null && !successor.getExpiresTs().isBefore(now);
	}

	public void revoke(String rawToken) {
		lookup(rawToken).ifPresent(dbo -> {
			dbo.setRevokedFlag(true);
			dbo.setRotatedTs(null);
			dao.save(dbo);
		});
	}

	public void revokeAllForUser(Integer userId) {
		UserRefreshTokenDboExample ex = new UserRefreshTokenDboExample();
		ex.createCriteria().andUserIdEqualTo(userId).andRevokedFlagEqualTo(false);
		for (UserRefreshTokenDbo dbo : dao.get(ex)) {
			dbo.setRevokedFlag(true);
			dbo.setRotatedTs(null);
			dao.save(dbo);
		}
	}

	private java.util.Optional<UserRefreshTokenDbo> lookup(String rawToken) {
		if (rawToken == null) {
			return java.util.Optional.empty();
		}
		UserRefreshTokenDboExample ex = new UserRefreshTokenDboExample();
		ex.createCriteria().andTokenHashEqualTo(sha256Hex(rawToken));
		return dao.get(ex).stream().findFirst();
	}

	private static String generateRawToken() {
		byte[] bytes = new byte[TOKEN_BYTES];
		RNG.nextBytes(bytes);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}

	private static String sha256Hex(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder(digest.length * 2);
			for (byte b : digest) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("SHA-256 not available", e);
		}
	}
}
