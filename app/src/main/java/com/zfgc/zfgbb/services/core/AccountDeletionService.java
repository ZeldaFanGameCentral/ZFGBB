package com.zfgc.zfgbb.services.core;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.Normalizer;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.zfgc.zfgbb.dbo.AccountDeletionRequestDbo;
import com.zfgc.zfgbb.dbo.AccountDeletionRequestDboExample;
import com.zfgc.zfgbb.dbo.PollDboExample;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import com.zfgc.zfgbb.dbo.PersonalMessageDboExample;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.mappers.AccountDeletionRequestDboMapper;
import com.zfgc.zfgbb.mappers.PollDboMapper;
import com.zfgc.zfgbb.mappers.WikiPageDboMapper;
import com.zfgc.zfgbb.mappers.PersonalMessageDboMapper;
import com.zfgc.zfgbb.mappers.custom.UserDeletionMapper;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.users.AccountDeletionPreview;
import com.zfgc.zfgbb.model.users.AccountDeletionRequest;
import com.zfgc.zfgbb.model.users.AccountDeletionState;
import com.zfgc.zfgbb.model.users.DeletionMode;
import com.zfgc.zfgbb.services.core.deletion.CoreUserDataHandler;
import com.zfgc.zfgbb.services.core.deletion.UserDataHandler;
import com.zfgc.zfgbb.services.forum.ForumService;

import jakarta.annotation.PreDestroy;

@Service
public class AccountDeletionService {

	private static final Logger LOG = LoggerFactory.getLogger(AccountDeletionService.class);

	private static final SecureRandom CONFIRMATION_TOKEN_RNG = new SecureRandom();
	private static final int CONFIRMATION_TOKEN_BYTES = 32;
	private static final int RESEND_MAX_PER_WINDOW = 3;
	private static final Duration RESEND_MIN_SPACING = Duration.ofMinutes(5);
	private static final int RATE_LIMIT_MAP_SWEEP_THRESHOLD = 1024;
	private static final Duration RATE_LIMIT_MAX_WINDOW = Duration.ofHours(1);

	public record AccountDeletionConfirmOutcome(AccountDeletionState state, Integer subjectUserId) {}

	private final List<UserDataHandler> dataHandlers;
	private final CoreUserDataHandler coreUserDataHandler;
	private final AuthService authService;
	private final ForumService forumService;
	private final AccountDeletionRequestDboMapper deletionRequestMapper;
	private final ObjectProvider<MailDispatcher> mailDispatcherProvider;
	private final boolean purgeAsync;
	private final String confirmBaseUrl;
	private final long confirmTtlHours;
	private final PollDboMapper pollMapper;
	private final WikiPageDboMapper wikiPageMapper;
	private final PersonalMessageDboMapper personalMessageMapper;
	private final UserDeletionMapper deletionMapper;

	private final ConcurrentHashMap<String, Deque<Instant>> attemptWindows = new ConcurrentHashMap<>();

	private final ExecutorService purgeExecutor = Executors.newSingleThreadExecutor(runnable -> {
		Thread worker = new Thread(runnable, "account-deletion-purge");
		worker.setDaemon(true);
		return worker;
	});

	public AccountDeletionService(List<UserDataHandler> dataHandlers,
			CoreUserDataHandler coreUserDataHandler,
			AuthService authService,
			ForumService forumService,
			AccountDeletionRequestDboMapper deletionRequestMapper,
			ObjectProvider<MailDispatcher> mailDispatcherProvider,
			@Value("${zfgbb.account-deletion.purge-async:true}") boolean purgeAsync,
			@Value("${zfgbb.account-deletion.confirm-base-url:https://zfgc.com/account/delete/confirm}") String confirmBaseUrl,
			@Value("${zfgbb.account-deletion.confirm-ttl-hours:24}") long confirmTtlHours,
			PollDboMapper pollMapper, WikiPageDboMapper wikiPageMapper,
			PersonalMessageDboMapper personalMessageMapper,
			UserDeletionMapper deletionMapper) {
		this.dataHandlers = dataHandlers;
		this.coreUserDataHandler = coreUserDataHandler;
		this.authService = authService;
		this.forumService = forumService;
		this.deletionRequestMapper = deletionRequestMapper;
		this.mailDispatcherProvider = mailDispatcherProvider;
		this.purgeAsync = purgeAsync;
		this.confirmBaseUrl = confirmBaseUrl;
		this.confirmTtlHours = confirmTtlHours;
		this.pollMapper = pollMapper;
		this.wikiPageMapper = wikiPageMapper;
		this.personalMessageMapper = personalMessageMapper;
		this.deletionMapper = deletionMapper;
	}

	public AccountDeletionState requestDeletion(User principal, AccountDeletionRequest submission) {
		Integer userId = principal.getUserId();
		enforceRateLimit("request:" + userId, 10, Duration.ofHours(1));
		if (submission == null)
			throw verificationFailure();
		DeletionMode mode = deletionModeOf(submission.mode());
		String normalizedPhrase = normalizePhrase(submission.confirmationPhrase());
		if (normalizedPhrase.isEmpty() || !normalizedPhrase.equals(normalizePhrase(principal.getUsername())))
			throw verificationFailure();
		try {
			authService.reauthenticate(principal.getUsername(), submission.password());
		} catch (AuthenticationException reauthenticationFailure) {
			throw verificationFailure();
		}
		coreUserDataHandler.assertSelfDeletionAllowed(userId);
		OffsetDateTime now = utcNow();
		Optional<AccountDeletionRequestDbo> pending = findPendingRequest(userId);
		if (pending.isPresent()) {
			AccountDeletionRequestDbo existing = pending.get();
			boolean expired = existing.getExpiresTs() == null || !existing.getExpiresTs().isAfter(now);
			if (!expired && deletionModeOf(existing.getMode()) == mode)
				return pendingState(existing);
			if (!transitionPendingTo(existing.getAccountDeletionRequestId(),
					CoreUserDataHandler.REQUEST_STATUS_SUPERSEDED, now)
					&& deletionAlreadyUnderway(existing.getAccountDeletionRequestId()))
				throw new ZfgcInvalidRequestException("This account deletion has already been confirmed.");
		}
		return createPendingRequest(userId, mode, now);
	}

	public AccountDeletionState resendConfirmation(User principal) {
		AccountDeletionRequestDbo pending = findPendingRequest(principal.getUserId())
				.orElseThrow(ZfgcNotFoundException::new);
		OffsetDateTime now = utcNow();
		if (pending.getExpiresTs() == null || !pending.getExpiresTs().isAfter(now))
			throw new ZfgcNotFoundException();
		if (pending.getLastSentTs() != null && pending.getLastSentTs().plus(RESEND_MIN_SPACING).isAfter(now))
			throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
					"Please wait a few minutes before requesting another confirmation email.");
		if (pending.getResendCount() != null && pending.getResendCount() >= RESEND_MAX_PER_WINDOW)
			throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
					"The confirmation email resend limit has been reached.");
		String rawToken = generateConfirmationToken();
		int nextResendCount = (pending.getResendCount() == null ? 0 : pending.getResendCount()) + 1;
		AccountDeletionRequestDbo rotation = new AccountDeletionRequestDbo();
		rotation.setTokenSha256(sha256Hex(rawToken));
		rotation.setExpiresTs(now.plusHours(confirmTtlHours));
		rotation.setResendCount(nextResendCount);
		rotation.setLastSentTs(now);
		rotation.setUpdatedTs(now);
		AccountDeletionRequestDboExample stillPending = new AccountDeletionRequestDboExample();
		stillPending.createCriteria()
				.andAccountDeletionRequestIdEqualTo(pending.getAccountDeletionRequestId())
				.andStatusEqualTo(CoreUserDataHandler.REQUEST_STATUS_PENDING);
		if (deletionRequestMapper.updateByExampleSelective(rotation, stillPending) != 1)
			throw new ZfgcNotFoundException();
		dispatchConfirmationEmail(pending.getUserId(), pending.getMode(), rawToken);
		return new AccountDeletionState(CoreUserDataHandler.REQUEST_STATUS_PENDING, pending.getMode(),
				rotation.getExpiresTs(), nextResendCount, now);
	}

	public AccountDeletionState cancelPendingDeletion(User principal) {
		Optional<AccountDeletionRequestDbo> pending = findPendingRequest(principal.getUserId());
		if (pending.isEmpty())
			return AccountDeletionState.none();
		AccountDeletionRequestDbo request = pending.get();
		if (transitionPendingTo(request.getAccountDeletionRequestId(),
				CoreUserDataHandler.REQUEST_STATUS_CANCELLED, utcNow()))
			return new AccountDeletionState(CoreUserDataHandler.REQUEST_STATUS_CANCELLED, request.getMode(),
					request.getExpiresTs(), request.getResendCount(), request.getLastSentTs());
		return Optional
				.ofNullable(deletionRequestMapper.selectByPrimaryKey(request.getAccountDeletionRequestId()))
				.map(this::pendingState)
				.orElseGet(AccountDeletionState::none);
	}

	public AccountDeletionState currentDeletionState(User principal) {
		return findPendingRequest(principal.getUserId())
				.map(this::pendingState)
				.orElseGet(AccountDeletionState::none);
	}

	public AccountDeletionPreview previewDeletion(User principal) {
		enforceRateLimit("preview:" + principal.getUserId(), 10, Duration.ofMinutes(1));
		PollDboExample ownedPollsExample = new PollDboExample();
		ownedPollsExample.createCriteria().andCreatedUserIdEqualTo(principal.getUserId());
		WikiPageDboExample ownedWikiPagesExample = new WikiPageDboExample();
		ownedWikiPagesExample.createCriteria().andCreatedUserIdEqualTo(principal.getUserId());
		PersonalMessageDboExample sentPersonalMessagesExample = new PersonalMessageDboExample();
		sentPersonalMessagesExample.createCriteria().andSenderUserIdEqualTo(principal.getUserId());
		return new AccountDeletionPreview(
				deletionMapper.countOwnedMessages(principal.getUserId()),
				deletionMapper.countOwnedThreads(principal.getUserId()),
				(int) pollMapper.countByExample(ownedPollsExample),
				coreUserDataHandler.countOwnedContentEntities(principal.getUserId(), "RESOURCE"),
				(int) wikiPageMapper.countByExample(ownedWikiPagesExample),
				coreUserDataHandler.countOwnedContentEntities(principal.getUserId(), "PROJECT"),
				coreUserDataHandler.countOwnedContentEntities(principal.getUserId(), "RESOURCE"),
				(int) personalMessageMapper.countByExample(sentPersonalMessagesExample),
				coreUserDataHandler.adminReplacementRequired(principal.getUserId()));
	}

	public AccountDeletionConfirmOutcome confirmDeletion(String rawToken, String clientIpKey) {
		enforceRateLimit("confirm:" + clientIpKey, 10, Duration.ofMinutes(1));
		if (rawToken == null || rawToken.isBlank())
			throw invalidConfirmationToken();
		String presentedHash = sha256Hex(rawToken.trim());
		AccountDeletionRequestDbo request = findRequestByTokenHash(presentedHash)
				.orElseThrow(this::invalidConfirmationToken);
		if (!MessageDigest.isEqual(request.getTokenSha256().getBytes(StandardCharsets.UTF_8),
				presentedHash.getBytes(StandardCharsets.UTF_8)))
			throw invalidConfirmationToken();
		OffsetDateTime now = utcNow();
		if (CoreUserDataHandler.REQUEST_STATUS_PENDING.equals(request.getStatus())) {
			if (request.getExpiresTs() == null || !request.getExpiresTs().isAfter(now))
				throw invalidConfirmationToken();
			if (casPendingToConfirmed(request.getAccountDeletionRequestId(), presentedHash, now) == 1) {
				executeConfirmedDeletion(request.getAccountDeletionRequestId());
				return confirmOutcome(request.getAccountDeletionRequestId());
			}
			request = deletionRequestMapper.selectByPrimaryKey(request.getAccountDeletionRequestId());
			if (request == null)
				throw invalidConfirmationToken();
		}
		return switch (request.getStatus()) {
			case CoreUserDataHandler.REQUEST_STATUS_CONFIRMED,
					CoreUserDataHandler.REQUEST_STATUS_EXECUTING -> {
				executeConfirmedDeletion(request.getAccountDeletionRequestId());
				yield confirmOutcome(request.getAccountDeletionRequestId());
			}
			case CoreUserDataHandler.REQUEST_STATUS_COMPLETED ->
				confirmOutcome(request.getAccountDeletionRequestId());
			default -> throw invalidConfirmationToken();
		};
	}

	public void executeConfirmedDeletion(Integer accountDeletionRequestId) {
		AccountDeletionRequestDbo request = deletionRequestMapper.selectByPrimaryKey(accountDeletionRequestId);
		if (request == null)
			throw new ZfgcNotFoundException();
		switch (request.getStatus()) {
			case CoreUserDataHandler.REQUEST_STATUS_COMPLETED -> {}
			case CoreUserDataHandler.REQUEST_STATUS_CONFIRMED -> {
				String recipientEmailAddress = coreUserDataHandler.findPrimaryEmailAddress(request.getUserId())
						.orElse(null);
				try {
					coreUserDataHandler.neutralizeAccount(accountDeletionRequestId);
				} catch (ZfgcInvalidRequestException deletionBlocked) {
					coreUserDataHandler.cancelDeletionRequest(accountDeletionRequestId);
					throw deletionBlocked;
				}
				forumService.evictUnfilteredForumCache();
				dispatchCompletionNotice(recipientEmailAddress);
				enqueuePurge(accountDeletionRequestId);
			}
			case CoreUserDataHandler.REQUEST_STATUS_EXECUTING -> enqueuePurge(accountDeletionRequestId);
			default -> throw new ZfgcInvalidRequestException("This deletion request is not confirmed.");
		}
	}

	@EventListener(ApplicationReadyEvent.class)
	public void resumeInterruptedDeletions() {
		AccountDeletionRequestDboExample ex = new AccountDeletionRequestDboExample();
		ex.createCriteria().andStatusIn(List.of(CoreUserDataHandler.REQUEST_STATUS_CONFIRMED,
				CoreUserDataHandler.REQUEST_STATUS_EXECUTING));
		for (AccountDeletionRequestDbo request : deletionRequestMapper.selectByExample(ex))
			purgeExecutor.submit(() -> {
				try {
					executeConfirmedDeletion(request.getAccountDeletionRequestId());
				} catch (RuntimeException resumeFailure) {
					LOG.error("failed to resume account deletion request {}",
							request.getAccountDeletionRequestId(), resumeFailure);
				}
			});
	}

	@PreDestroy
	public void shutdownPurgeExecutor() {
		purgeExecutor.shutdown();
	}

	private void dispatchCompletionNotice(String recipientEmailAddress) {
		if (recipientEmailAddress == null)
			return;
		Optional<MailDispatcher> dispatcher = Optional.ofNullable(mailDispatcherProvider.getIfAvailable());
		if (dispatcher.isEmpty()) {
			LOG.warn("no mail dispatcher is configured; the courtesy completion notice was not sent");
			return;
		}
		try {
			dispatcher.get().dispatch(new MailDispatcher.OutboundMail(recipientEmailAddress,
					"Your account has been deleted",
					"Your account deletion has been confirmed and is now being carried out. "
							+ "This action is permanent and cannot be reversed."));
		} catch (RuntimeException dispatchFailure) {
			LOG.warn("courtesy completion notice could not be sent", dispatchFailure);
		}
	}

	private void enqueuePurge(Integer accountDeletionRequestId) {
		if (purgeAsync) {
			purgeExecutor.submit(() -> runPurgeSafely(accountDeletionRequestId));
			return;
		}
		runPurgeSafely(accountDeletionRequestId);
	}

	private void runPurgeSafely(Integer accountDeletionRequestId) {
		try {
			runPurge(accountDeletionRequestId);
		} catch (RuntimeException purgeFailure) {
			LOG.error("account deletion purge failed for request {}; it will resume on restart",
					accountDeletionRequestId, purgeFailure);
		}
	}

	private void runPurge(Integer accountDeletionRequestId) {
		AccountDeletionRequestDbo request = deletionRequestMapper.selectByPrimaryKey(accountDeletionRequestId);
		if (request == null || CoreUserDataHandler.REQUEST_STATUS_COMPLETED.equals(request.getStatus()))
			return;
		if (!CoreUserDataHandler.REQUEST_STATUS_EXECUTING.equals(request.getStatus()))
			return;
		sweepRecordedBlobs(request);
		DeletionMode mode = deletionModeOf(request.getMode());
		Integer userId = request.getUserId();
		for (UserDataHandler handler : dataHandlers) {
			if (mode == DeletionMode.PURGE) {
				List<String> releasedBlobPaths = handler.backgroundPurge(accountDeletionRequestId, userId);
				deleteBlobFiles(releasedBlobPaths);
				coreUserDataHandler.clearRecordedBlobPaths(accountDeletionRequestId);
			} else {
				handler.backgroundAnonymize(userId);
			}
			forumService.evictUnfilteredForumCache();
		}
		coreUserDataHandler.completePurge(accountDeletionRequestId);
		forumService.evictUnfilteredForumCache();
	}

	private void sweepRecordedBlobs(AccountDeletionRequestDbo request) {
		String recordedBlobPaths = request.getRecordedBlobPaths();
		if (recordedBlobPaths == null || recordedBlobPaths.isBlank())
			return;
		deleteBlobFiles(recordedBlobPaths.lines().filter(line -> !line.isBlank()).toList());
		coreUserDataHandler.clearRecordedBlobPaths(request.getAccountDeletionRequestId());
	}

	private void deleteBlobFiles(List<String> blobPaths) {
		for (String blobPath : blobPaths) {
			try {
				Files.deleteIfExists(Path.of(blobPath));
			} catch (IOException | RuntimeException blobDeleteFailure) {
				LOG.warn("stored blob {} could not be deleted; a later sweep may reconcile it", blobPath,
						blobDeleteFailure);
			}
		}
	}

	private DeletionMode deletionModeOf(String mode) {
		return switch (mode == null ? "" : mode.toUpperCase(Locale.ROOT)) {
			case "PURGE", "WIPE", "FULL_WIPE" -> DeletionMode.PURGE;
			case "ANONYMIZE", "ORPHAN" -> DeletionMode.ANONYMIZE;
			default -> throw new ZfgcInvalidRequestException("Unknown deletion mode: " + mode);
		};
	}

	private Optional<AccountDeletionRequestDbo> findPendingRequest(Integer userId) {
		AccountDeletionRequestDboExample ex = new AccountDeletionRequestDboExample();
		ex.createCriteria().andUserIdEqualTo(userId)
				.andStatusEqualTo(CoreUserDataHandler.REQUEST_STATUS_PENDING);
		return deletionRequestMapper.selectByExample(ex).stream().findFirst();
	}

	private Optional<AccountDeletionRequestDbo> findRequestByTokenHash(String tokenSha256) {
		AccountDeletionRequestDboExample ex = new AccountDeletionRequestDboExample();
		ex.createCriteria().andTokenSha256EqualTo(tokenSha256);
		ex.setOrderByClause("account_deletion_request_id desc");
		return deletionRequestMapper.selectByExample(ex).stream().findFirst();
	}

	private AccountDeletionState createPendingRequest(Integer userId, DeletionMode mode, OffsetDateTime now) {
		String rawToken = generateConfirmationToken();
		AccountDeletionRequestDbo request = new AccountDeletionRequestDbo();
		request.setUserId(userId);
		request.setMode(mode.name());
		request.setStatus(CoreUserDataHandler.REQUEST_STATUS_PENDING);
		request.setTokenSha256(sha256Hex(rawToken));
		request.setRequestedTs(now);
		request.setExpiresTs(now.plusHours(confirmTtlHours));
		request.setResendCount(0);
		request.setLastSentTs(now);
		request.setCreatedTs(now);
		request.setUpdatedTs(now);
		try {
			deletionRequestMapper.insertSelective(request);
		} catch (DuplicateKeyException concurrentRequest) {
			return findPendingRequest(userId).map(this::pendingState)
					.orElseThrow(() -> concurrentRequest);
		}
		coreUserDataHandler.recordDeletionRequestedAudit(userId, mode.name(), now);
		try {
			dispatchConfirmationEmail(userId, mode.name(), rawToken);
		} catch (RuntimeException confirmationUndeliverable) {
			transitionPendingTo(request.getAccountDeletionRequestId(),
					CoreUserDataHandler.REQUEST_STATUS_CANCELLED, utcNow());
			throw confirmationUndeliverable;
		}
		return pendingState(request);
	}

	private boolean transitionPendingTo(Integer accountDeletionRequestId, String targetStatus, OffsetDateTime now) {
		AccountDeletionRequestDbo transition = new AccountDeletionRequestDbo();
		transition.setStatus(targetStatus);
		transition.setUpdatedTs(now);
		AccountDeletionRequestDboExample stillPending = new AccountDeletionRequestDboExample();
		stillPending.createCriteria()
				.andAccountDeletionRequestIdEqualTo(accountDeletionRequestId)
				.andStatusEqualTo(CoreUserDataHandler.REQUEST_STATUS_PENDING);
		return deletionRequestMapper.updateByExampleSelective(transition, stillPending) == 1;
	}

	private boolean deletionAlreadyUnderway(Integer accountDeletionRequestId) {
		AccountDeletionRequestDbo current = deletionRequestMapper.selectByPrimaryKey(accountDeletionRequestId);
		if (current == null)
			return false;
		return switch (current.getStatus()) {
			case CoreUserDataHandler.REQUEST_STATUS_CONFIRMED,
					CoreUserDataHandler.REQUEST_STATUS_EXECUTING,
					CoreUserDataHandler.REQUEST_STATUS_COMPLETED -> true;
			default -> false;
		};
	}

	private int casPendingToConfirmed(Integer accountDeletionRequestId, String tokenSha256, OffsetDateTime now) {
		AccountDeletionRequestDbo update = new AccountDeletionRequestDbo();
		update.setStatus(CoreUserDataHandler.REQUEST_STATUS_CONFIRMED);
		update.setConfirmedTs(now);
		update.setUpdatedTs(now);
		AccountDeletionRequestDboExample ex = new AccountDeletionRequestDboExample();
		ex.createCriteria().andAccountDeletionRequestIdEqualTo(accountDeletionRequestId)
				.andStatusEqualTo(CoreUserDataHandler.REQUEST_STATUS_PENDING)
				.andTokenSha256EqualTo(tokenSha256)
				.andExpiresTsGreaterThan(now);
		return deletionRequestMapper.updateByExampleSelective(update, ex);
	}

	private AccountDeletionConfirmOutcome confirmOutcome(Integer accountDeletionRequestId) {
		AccountDeletionRequestDbo request = deletionRequestMapper.selectByPrimaryKey(accountDeletionRequestId);
		if (request == null)
			throw invalidConfirmationToken();
		AccountDeletionState state = new AccountDeletionState(request.getStatus(), request.getMode(),
				request.getExpiresTs(), request.getResendCount(), request.getLastSentTs());
		return new AccountDeletionConfirmOutcome(state, request.getUserId());
	}

	private AccountDeletionState pendingState(AccountDeletionRequestDbo request) {
		return new AccountDeletionState(request.getStatus(), request.getMode(), request.getExpiresTs(),
				request.getResendCount(), request.getLastSentTs());
	}

	private void dispatchConfirmationEmail(Integer userId, String mode, String rawToken) {
		MailDispatcher dispatcher = mailDispatcherProvider.getIfAvailable();
		if (dispatcher == null)
			throw confirmationEmailUndeliverable();
		String recipientEmailAddress = coreUserDataHandler.findPrimaryEmailAddress(userId)
				.orElseThrow(this::confirmationEmailUndeliverable);
		String confirmationLink = confirmBaseUrl + (confirmBaseUrl.contains("#") ? "&" : "#") + "token=" + rawToken;
		String modeDescription = deletionModeOf(mode) == DeletionMode.PURGE
				? "delete your posts and content"
				: "keep your posts but remove your name";
		String body = "A deletion of your account was requested, with the choice to " + modeDescription + ".\n\n"
				+ "Nothing will happen until you confirm by opening this link (valid for " + confirmTtlHours
				+ " hours):\n\n" + confirmationLink + "\n\n"
				+ "If you did not request this, cancel the request from your account settings and change your "
				+ "password immediately. Confirming is permanent and cannot be undone.";
		try {
			dispatcher.dispatch(new MailDispatcher.OutboundMail(recipientEmailAddress,
					"Confirm your account deletion", body));
		} catch (RuntimeException dispatchFailure) {
			LOG.warn("deletion confirmation email could not be sent for user {}", userId, dispatchFailure);
			throw confirmationEmailUndeliverable();
		}
	}

	private ResponseStatusException confirmationEmailUndeliverable() {
		return new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
				"The confirmation email could not be sent. Please try again shortly.");
	}

	private void enforceRateLimit(String key, int maxAttempts, Duration window) {
		Instant now = Instant.now();
		Deque<Instant> attempts = attemptWindows.computeIfAbsent(key, ignored -> new ArrayDeque<>());
		synchronized (attempts) {
			while (!attempts.isEmpty() && attempts.peekFirst().isBefore(now.minus(window)))
				attempts.pollFirst();
			if (attempts.size() >= maxAttempts)
				throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
						"Too many attempts. Please try again later.");
			attempts.addLast(now);
		}
		if (attemptWindows.size() > RATE_LIMIT_MAP_SWEEP_THRESHOLD)
			evictStaleRateLimitWindows(now);
	}

	private void evictStaleRateLimitWindows(Instant now) {
		Instant staleBefore = now.minus(RATE_LIMIT_MAX_WINDOW);
		attemptWindows.entrySet().removeIf(entry -> {
			Deque<Instant> attempts = entry.getValue();
			synchronized (attempts) {
				return attempts.isEmpty() || attempts.peekLast().isBefore(staleBefore);
			}
		});
	}

	private String normalizePhrase(String phrase) {
		if (phrase == null)
			return "";
		return Normalizer.normalize(phrase, Normalizer.Form.NFKC).trim().toLowerCase(Locale.ROOT);
	}

	private ZfgcInvalidRequestException verificationFailure() {
		return new ZfgcInvalidRequestException("Account deletion could not be verified.");
	}

	private ZfgcInvalidRequestException invalidConfirmationToken() {
		return new ZfgcInvalidRequestException("This confirmation link is not valid.");
	}

	private static String generateConfirmationToken() {
		byte[] tokenBytes = new byte[CONFIRMATION_TOKEN_BYTES];
		CONFIRMATION_TOKEN_RNG.nextBytes(tokenBytes);
		return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
	}

	private static String sha256Hex(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashed = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			StringBuilder hex = new StringBuilder(hashed.length * 2);
			for (byte hashedByte : hashed)
				hex.append(String.format("%02x", hashedByte));
			return hex.toString();
		} catch (NoSuchAlgorithmException missingAlgorithm) {
			throw new IllegalStateException("SHA-256 not available", missingAlgorithm);
		}
	}

	private static OffsetDateTime utcNow() {
		return OffsetDateTime.now(ZoneOffset.UTC);
	}
}
