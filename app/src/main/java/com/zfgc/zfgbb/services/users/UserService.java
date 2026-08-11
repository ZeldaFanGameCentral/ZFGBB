package com.zfgc.zfgbb.services.users;

import java.util.List;
import java.io.IOException;
import java.text.Normalizer;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwsHeader;
import com.zfgc.zfgbb.services.auth.TokenSubjectValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.server.ResponseStatusException;
import com.zfgc.zfgbb.model.users.AccountDeletionPreview;
import com.zfgc.zfgbb.model.users.AccountDeletionRequest;
import com.zfgc.zfgbb.model.users.AccountDeletionState;
import com.zfgc.zfgbb.services.mail.MailDispatcher;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import com.zfgc.zfgbb.dataprovider.cms.CatalogDataProvider;
import com.zfgc.zfgbb.dataprovider.cms.MigrationConflictDataProvider;
import com.zfgc.zfgbb.dataprovider.cms.WikiDataProvider;
import com.zfgc.zfgbb.dataprovider.forum.ForumDataProvider;
import com.zfgc.zfgbb.dataprovider.forum.MessageDataProvider;
import com.zfgc.zfgbb.dataprovider.forum.ThreadDataProvider;
import com.zfgc.zfgbb.dataprovider.reactions.ReactionDataProvider;
import com.zfgc.zfgbb.model.cms.ReleasedResource;
import com.zfgc.zfgbb.model.users.DeletionMode;
import com.zfgc.zfgbb.services.auth.AuthService;
import com.zfgc.zfgbb.services.contentstore.ContentService;
import com.zfgc.zfgbb.services.forum.ForumService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.zfgc.zfgbb.authorization.access.ProfileAccessRules;
import com.zfgc.zfgbb.dataprovider.loadoption.UserLoadOptions;
import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.users.UserSummary;
import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.ContentRenderingService;
import com.zfgc.zfgbb.content.renderer.templates.TemplateDataService;
import com.zfgc.zfgbb.content.renderer.templates.TemplateSource;
import com.zfgc.zfgbb.model.users.UserSettings;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements TemplateDataService {

	private final UserDataProvider userDataProvider;

	private final MessageDataProvider messageDataProvider;

	private final ThreadDataProvider threadDataProvider;

	private final ForumDataProvider forumDataProvider;

	private final WikiDataProvider wikiDataProvider;

	private final CatalogDataProvider catalogDataProvider;

	private final ReactionDataProvider reactionDataProvider;

	private final MigrationConflictDataProvider migrationConflictDataProvider;

	private final AuthService authService;

	private final ContentService contentService;

	private final ForumService forumService;

	private final ObjectProvider<MailDispatcher> mailDispatcherProvider;

	private final JwtEncoder deletionTokenEncoder;

	private final JwtDecoder deletionTokenDecoder;

	private final TokenSubjectValidator tokenSubjects;

	@Value("${zfgbb.account-deletion.confirm-base-url:https://zfgc.com/account/delete/confirm}")
	private final String confirmBaseUrl;

	@Value("${zfgbb.account-deletion.confirm-ttl-hours:24}")
	private final long confirmTtlHours;

	private final ProfileAccessRules profileAccessRules;

	private final ContentRenderingService contentRenderingService;

	@TemplateSource("/user-profile/{userId}")
	public User loadUser(Integer userId, User requester) {
		User user = userDataProvider.findUser(userId, UserLoadOptions.full())
				.orElseThrow(ZfgcNotFoundException::new);
		if (user.getBioInfo() != null && user.getBioInfo().getSignature() != null)
			user.getBioInfo().setSignatureParsed(contentRenderingService.render(
					user.getBioInfo().getSignature(), ContentFormat.BBCODE, ContentScope.SIGNATURE));
		boolean privateView = profileAccessRules.canViewPrivateProfile(requester, userId);
		if (!privateView) {
			user.setEmail(null);
			user.setUserName(null);
			user.setSettings(null);
			user.retainPublicRankPermissions();
			if (user.getContactInfo() != null) {
				user.getContactInfo().setEmailAddress(null);
			}
			if (user.getBioInfo() != null) {
				user.getBioInfo().setSignature(null);
			}
		}
		return user;
	}

	public static final String REMEDIATION_SHARED_EMAIL = "ACCOUNT_DELETION_SHARED_EMAIL";

	public static final String REMEDIATION_TEMPLATE_LINKED_WIKI_PAGE =
			"ACCOUNT_DELETION_TEMPLATE_LINKED_WIKI_PAGE";

	private static final int ERASURE_CHUNK_SIZE = 500;

	public List<UserSummary> listUsers() {
		return userDataProvider.listUsers();
	}

	@Transactional
	public void deleteUser(Integer userId, DeletionMode mode, User requester) {
		guardDeletable(userId, requester);
		eraseUserRecords(userId, mode == null ? DeletionMode.ANONYMIZE : mode);
	}

	private void guardDeletable(Integer userId, User requester) {
		if (userId == null)
			throw new ZfgcInvalidRequestException("userId is required.");
		if (requester != null && userId.equals(requester.getUserId()))
			throw new ZfgcInvalidRequestException("You cannot delete your own account.");
		if (userDataProvider.findUser(userId).isEmpty())
			throw new ZfgcNotFoundException();
		if (userDataProvider.isSiteAdmin(userId))
			throw new ZfgcInvalidRequestException("Site administrators cannot be deleted.");
		if (userDataProvider.isSentinelUser(userId))
			throw new ZfgcInvalidRequestException("The deleted-user account cannot be deleted.");
	}

	@Transactional
	public void eraseUserRecords(Integer userId, DeletionMode mode) {
		Integer sentinelId = userDataProvider.ensureSentinelUser();
		String subjectUserName = userDataProvider.findUserName(userId).orElse(null);
		List<Integer> emailAddressIds = userDataProvider.findEmailAddressIds(userId);
		List<ReleasedResource> released = mode == DeletionMode.PURGE
				? purgeRecords(userId, sentinelId)
				: anonymizeRecords(userId, sentinelId);
		neutralizeIdentity(userId, subjectUserName, emailAddressIds);
		deleteBlobFilesAfterCommit(blobPathsOf(released));
		forumService.evictUnfilteredForumCache();
	}

	private List<ReleasedResource> purgeRecords(Integer userId, Integer sentinelId) {
		List<ReleasedResource> released = new ArrayList<>();
		while (true) {
			List<Integer> messageIds = messageDataProvider.findOwnedMessageIds(userId, ERASURE_CHUNK_SIZE);
			if (messageIds.isEmpty())
				break;
			List<Integer> touchedThreadIds = messageDataProvider.findThreadIdsForMessages(messageIds);
			released.addAll(messageDataProvider.purgeMessagesByIds(messageIds));
			threadDataProvider.gcThreadsEmptiedByDeletion(touchedThreadIds);
			messageDataProvider.resequencePostInThread(touchedThreadIds);
		}
		threadDataProvider.purgeOwnedPolls(userId);
		messageDataProvider.purgePersonalMessages(userId);
		scrubResidue(userId);
		threadDataProvider.purgeThreadsWithGc(userId, sentinelId);

		reportTemplateLinkedWikiPages(userId);
		released.addAll(wikiDataProvider.purgeOwnedWikiPages(userId));
		scrubRetainedContributions(userId);

		released.addAll(purgeBioInfoAndAvatar(userId));
		while (true) {
			List<Integer> resourceIds =
					userDataProvider.findOwnedUnreferencedContentResourceIds(userId, ERASURE_CHUNK_SIZE);
			if (resourceIds.isEmpty())
				break;
			released.addAll(catalogDataProvider.deleteContentResourceRows(resourceIds));
		}
		userDataProvider.deleteUserSettings(userId);
		userDataProvider.deleteUserWarnings(userId);
		forumDataProvider.deleteNotificationSubscriptions(userId);
		userDataProvider.reassignContentResources(userId, sentinelId);
		userDataProvider.deleteMigratorIdMapEntries("USER", List.of(userId));
		userDataProvider.deleteUserRow(userId);
		return released;
	}

	private List<ReleasedResource> anonymizeRecords(Integer userId, Integer sentinelId) {
		while (messageDataProvider.orphanOwnedMessagesBatch(userId, sentinelId, ERASURE_CHUNK_SIZE) > 0)
			continue;
		threadDataProvider.orphanOwnedPolls(userId, sentinelId);
		messageDataProvider.purgePersonalMessages(userId);
		scrubResidue(userId);
		threadDataProvider.orphanThreads(userId, sentinelId);

		scrubRetainedContributions(userId);

		forumDataProvider.deleteNotificationSubscriptions(userId);
		List<ReleasedResource> released = purgeBioInfoAndAvatar(userId);
		userDataProvider.reassignContentResources(userId, sentinelId);
		userDataProvider.deleteMigratorIdMapEntries("USER", List.of(userId));
		return released;
	}

	private void scrubResidue(Integer userId) {
		userDataProvider.scrubIssuedWarnings(userId);
		forumDataProvider.scrubModerationResidue(userId);
		migrationConflictDataProvider.nullConflictResolvers(userId);
		reactionDataProvider.scrubGivenReactions(userId);
	}

	private void scrubRetainedContributions(Integer userId) {
		wikiDataProvider.scrubRetainedWikiContributions(userId);
	}

	private void reportTemplateLinkedWikiPages(Integer userId) {
		for (Integer templateLinkedPageId : wikiDataProvider.findOwnedTemplateLinkedWikiPageIds(userId))
			recordOperatorRemediation(REMEDIATION_TEMPLATE_LINKED_WIKI_PAGE, "wiki_page_id=" + templateLinkedPageId
					+ " is referenced by a content_template and was retained anonymized; "
					+ "operator must re-home or unlink the template");
	}

	private void recordOperatorRemediation(String action, String detail) {
		log.warn("operator remediation required: {} {}", action, detail);
		forumDataProvider.recordOperatorRemediation(action, detail);
	}

	private List<ReleasedResource> purgeBioInfoAndAvatar(Integer userId) {
		Optional<Integer> avatarId = userDataProvider.findBioAvatarId(userId);
		userDataProvider.deleteBioInfo(userId);
		if (avatarId.isEmpty())
			return List.of();
		Optional<Integer> avatarResourceId = userDataProvider.findAvatarContentResourceId(avatarId.get());
		userDataProvider.deleteAvatar(avatarId.get());
		return avatarResourceId
				.map(resourceId -> catalogDataProvider.deleteContentResourcesIfUnreferenced(List.of(resourceId)))
				.orElse(List.of());
	}

	private void neutralizeIdentity(Integer userId, String subjectUserName, List<Integer> emailAddressIds) {
		userDataProvider.neutralizeIdentity(userId);
		authService.revokeAllForUser(userId);
		for (Integer retainedSharedAddressId : userDataProvider.releaseEmailAddresses(emailAddressIds))
			recordOperatorRemediation(REMEDIATION_SHARED_EMAIL, "email_address_id=" + retainedSharedAddressId
					+ " is shared with other accounts and was retained; operator remediation required");
		forumDataProvider.scrubMigratedModerationTargetsByName(subjectUserName);
	}

	private List<String> blobPathsOf(List<ReleasedResource> released) {
		List<String> blobPaths = new ArrayList<>();
		for (ReleasedResource resource : released) {
			try {
				blobPaths.add(contentService.storedFile(resource).toString());
			} catch (RuntimeException pathUnresolvable) {
				log.warn("content_resource_id {} has no resolvable stored path, so its blob is left behind",
						resource.contentResourceId(), pathUnresolvable);
			}
		}
		return blobPaths;
	}

	private void deleteBlobFilesAfterCommit(List<String> blobPaths) {
		if (blobPaths.isEmpty())
			return;
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
			@Override
			public void afterCommit() {
				for (String blobPath : blobPaths) {
					try {
						Files.deleteIfExists(Path.of(blobPath));
					} catch (IOException | RuntimeException blobDeleteFailure) {
						log.warn("orphan blob {} could not be deleted; operator sweep required", blobPath,
								blobDeleteFailure);
					}
				}
			}
		});
	}

	private static final int RATE_LIMIT_MAP_SWEEP_THRESHOLD = 1024;
	private static final Duration RATE_LIMIT_MAX_WINDOW = Duration.ofHours(1);
	private static final String DELETION_OUTSTANDING = "PENDING";
	private static final String DELETION_COMPLETED = "COMPLETED";
	private static final String DELETION_TOKEN_SUBJECT_PREFIX = "account-deletion:";
	private static final String DELETION_TOKEN_MODE_CLAIM = "mode";

	public record AccountDeletionConfirmOutcome(AccountDeletionState state, Integer subjectUserId) {}

	private final ConcurrentHashMap<String, Deque<Instant>> attemptWindows = new ConcurrentHashMap<>();

	@Transactional(noRollbackFor = ZfgcInvalidRequestException.class)
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
		assertSelfDeletionAllowed(userId);
		Instant issuedAt = principal.earliestAcceptableTokenIssuance(Instant.now());
		OffsetDateTime requestedTs = OffsetDateTime.ofInstant(issuedAt, ZoneOffset.UTC);
		OffsetDateTime expiresTs = requestedTs.plusHours(confirmTtlHours);
		userDataProvider.recordDeletionRequestedAudit(userId, mode.name(), requestedTs);
		dispatchConfirmationEmail(userId, mode.name(),
				mintConfirmationToken(userId, mode, issuedAt, expiresTs.toInstant()));
		return new AccountDeletionState(DELETION_OUTSTANDING, mode.name(), expiresTs);
	}

	public AccountDeletionPreview previewDeletion(User principal) {
		enforceRateLimit("preview:" + principal.getUserId(), 10, Duration.ofMinutes(1));
		Integer userId = principal.getUserId();
		return new AccountDeletionPreview(
				messageDataProvider.countOwnedMessages(userId),
				threadDataProvider.countOwnedThreads(userId),
				threadDataProvider.countOwnedPolls(userId),
				userDataProvider.countOwnedContentResources(userId),
				wikiDataProvider.countOwnedWikiPages(userId),
				0,
				0,
				messageDataProvider.countSentPersonalMessages(userId),
				userDataProvider.adminReplacementRequired(userId));
	}

	@Transactional(noRollbackFor = ZfgcInvalidRequestException.class)
	public AccountDeletionConfirmOutcome confirmDeletion(String rawToken, String clientIpKey) {
		enforceRateLimit("confirm:" + clientIpKey, 10, Duration.ofMinutes(1));
		if (rawToken == null || rawToken.isBlank())
			throw invalidConfirmationToken();
		Jwt token;
		try {
			token = deletionTokenDecoder.decode(rawToken.trim());
		} catch (JwtException unusableToken) {
			throw invalidConfirmationToken();
		}
		String subject = token.getSubject();
		if (subject == null || !subject.startsWith(DELETION_TOKEN_SUBJECT_PREFIX))
			throw invalidConfirmationToken();
		Integer userId;
		try {
			userId = Integer.valueOf(subject.substring(DELETION_TOKEN_SUBJECT_PREFIX.length()));
		} catch (NumberFormatException unusableSubject) {
			throw invalidConfirmationToken();
		}
		DeletionMode mode = deletionModeOf(token.getClaimAsString(DELETION_TOKEN_MODE_CLAIM));
		tokenSubjects.validSubject(userId, Optional.ofNullable(token.getIssuedAt()),
				rejected -> invalidConfirmationToken());
		assertSelfDeletionAllowed(userId);
		OffsetDateTime now = utcNow();
		String recipientEmailAddress = userDataProvider.findPrimaryEmailAddress(userId).orElse(null);
		userDataProvider.stampAuditConfirmed(userId, mode.name(),
				token.getIssuedAt() == null ? now : OffsetDateTime.ofInstant(token.getIssuedAt(), ZoneOffset.UTC),
				now);
		eraseUserRecords(userId, mode);
		userDataProvider.stampAuditExecuted(userId, utcNow());
		dispatchCompletionNotice(recipientEmailAddress);
		return new AccountDeletionConfirmOutcome(
				new AccountDeletionState(DELETION_COMPLETED, mode.name(), null), userId);
	}

	private String mintConfirmationToken(Integer userId, DeletionMode mode, Instant issuedAt, Instant expiresAt) {
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.subject(DELETION_TOKEN_SUBJECT_PREFIX + userId)
				.claim(DELETION_TOKEN_MODE_CLAIM, mode.name())
				.issuedAt(issuedAt)
				.expiresAt(expiresAt)
				.build();
		return deletionTokenEncoder
				.encode(JwtEncoderParameters.from(JwsHeader.with(() -> "HS256").build(), claims))
				.getTokenValue();
	}

	private void assertSelfDeletionAllowed(Integer userId) {
		if (userDataProvider.isSentinelUser(userId))
			throw new ZfgcInvalidRequestException("The deleted-user account cannot be deleted.");
		if (userDataProvider.isLastSiteAdmin(userId))
			throw new ZfgcInvalidRequestException("The last site administrator cannot delete their own account.");
	}

	private DeletionMode deletionModeOf(String mode) {
		return switch (mode == null ? "" : mode.toUpperCase(Locale.ROOT)) {
			case "PURGE", "WIPE", "FULL_WIPE" -> DeletionMode.PURGE;
			case "ANONYMIZE", "ORPHAN" -> DeletionMode.ANONYMIZE;
			default -> throw new ZfgcInvalidRequestException("Unknown deletion mode: " + mode);
		};
	}

	private void dispatchConfirmationEmail(Integer userId, String mode, String rawToken) {
		MailDispatcher dispatcher = mailDispatcherProvider.getIfAvailable();
		if (dispatcher == null)
			throw confirmationEmailUndeliverable();
		String recipientEmailAddress = userDataProvider.findPrimaryEmailAddress(userId)
				.orElseThrow(this::confirmationEmailUndeliverable);
		String confirmationLink = confirmBaseUrl + (confirmBaseUrl.contains("#") ? "&" : "#") + "token=" + rawToken;
		String modeDescription = deletionModeOf(mode) == DeletionMode.PURGE
				? "delete your posts and content"
				: "keep your posts but remove your name";
		String body = "A deletion of your account was requested, with the choice to " + modeDescription + ".\n\n"
				+ "Nothing will happen until you confirm by opening this link (valid for " + confirmTtlHours
				+ " hours):\n\n" + confirmationLink + "\n\n"
				+ "If you did not request this, someone else may have access to your account or email - secure "
				+ "them immediately. Nothing will happen unless this link is opened, and it cannot be recalled. "
				+ "Confirming is permanent and cannot be undone.";
		try {
			dispatcher.dispatch(new MailDispatcher.OutboundMail(recipientEmailAddress,
					"Confirm your account deletion", body));
		} catch (RuntimeException dispatchFailure) {
			log.warn("deletion confirmation email could not be sent for user {}", userId, dispatchFailure);
			throw confirmationEmailUndeliverable();
		}
	}

	private void dispatchCompletionNotice(String recipientEmailAddress) {
		if (recipientEmailAddress == null)
			return;
		MailDispatcher dispatcher = mailDispatcherProvider.getIfAvailable();
		if (dispatcher == null) {
			log.warn("no mail dispatcher is configured; the courtesy completion notice was not sent");
			return;
		}
		try {
			dispatcher.dispatch(new MailDispatcher.OutboundMail(recipientEmailAddress,
					"Your account has been deleted",
					"Your account deletion has been confirmed and is now being carried out. "
							+ "This action is permanent and cannot be reversed."));
		} catch (RuntimeException dispatchFailure) {
			log.warn("courtesy completion notice could not be sent", dispatchFailure);
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

	private static OffsetDateTime utcNow() {
		return OffsetDateTime.now(ZoneOffset.UTC);
	}
}
