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
import java.util.ArrayList;
import java.util.Optional;
import com.zfgc.zfgbb.dataprovider.cms.CatalogDataProvider;
import com.zfgc.zfgbb.dataprovider.cms.MigrationConflictDataProvider;
import com.zfgc.zfgbb.dataprovider.cms.ProjectDataProvider;
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
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.authorization.access.ProfileAccessRules;
import com.zfgc.zfgbb.dataprovider.loadoption.UserLoadOptions;
import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.dbo.GenderLkupDboExample;
import com.zfgc.zfgbb.dao.users.GenderLkupDao;
import com.zfgc.zfgbb.dao.users.AvatarDao;
import com.zfgc.zfgbb.dao.users.UserBioInfoDao;
import com.zfgc.zfgbb.dao.users.UserDao;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.model.users.Award;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.users.UserSummary;
import com.zfgc.zfgbb.content.renderer.templates.TemplateDataService;
import com.zfgc.zfgbb.content.renderer.templates.TemplateSource;
import com.zfgc.zfgbb.model.users.UserSettings;
import com.zfgc.zfgbb.model.users.UpdateUserProfileRequest;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements TemplateDataService {

	private static final Set<String> VALID_THEMES =
			Set.of("MIDNIGHT", "KIKORI", "GORON", "SHEIK");

	private final UserDataProvider userDataProvider;

	private final MessageDataProvider messageDataProvider;

	private final ThreadDataProvider threadDataProvider;

	private final ForumDataProvider forumDataProvider;

	private final WikiDataProvider wikiDataProvider;

	private final ProjectDataProvider projectDataProvider;

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

	private final UserDao userDao;

	private final AvatarDao avatarDao;

	private final UserBioInfoDao userBioInfoDao;

	private final GenderLkupDao genderLkupDao;

	@TemplateSource("/user-profile/{userId}")
	public User loadUser(Integer userId, User requester) {
		User user = userDataProvider.findUser(userId, UserLoadOptions.full())
				.orElseThrow(ZfgcNotFoundException::new);
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

	@Transactional
	public UserSettings saveUserSettings(Integer userId, UserSettings settings, User requester) {
		if (settings == null) {
			throw new ZfgcInvalidRequestException("Settings are required.");
		}
		if (settings.getTheme() != null && !VALID_THEMES.contains(settings.getTheme())) {
			throw new ZfgcInvalidRequestException("Unknown theme.");
		}
		userDataProvider.findUser(userId).orElseThrow(ZfgcNotFoundException::new);
		return userDataProvider.saveUserSettings(userId, settings);
	}

	public Set<String> profileAllowedActions(Integer userId, User requester) {
		userDataProvider.findUser(userId).orElseThrow(ZfgcNotFoundException::new);
		return profileAccessRules.permittedProfileActions(requester, userId);
	}

	@Transactional
	public User saveUserProfile(Integer userId, UpdateUserProfileRequest request, User zfgcUser) {
		if (userId == null || request == null) {
			throw new ZfgcInvalidRequestException("User profile and userId are required.");
		}
		validateProfileUpdate(request);
		userDao.lockActiveUserId(userId).orElseThrow(ZfgcNotFoundException::new);
		if (request.genderIdPresent() && request.genderId() != null) {
			GenderLkupDboExample genderExample = new GenderLkupDboExample();
			genderExample.createCriteria().andGenderIdEqualTo(request.genderId());
			if (!genderLkupDao.exists(genderExample))
				throw new ZfgcInvalidRequestException("Unknown gender.");
		}
		if (request.avatarIdPresent() && request.avatarId() != null
				&& !avatarDao.isAvailableTo(request.avatarId(), userId))
			throw new ZfgcInvalidRequestException("Avatar is not available.");
		if (request.displayNamePresent()) {
			int updated = userDao.updateDisplayName(request.displayName().trim(), userId);
			if (updated != 1) throw new ZfgcNotFoundException();
		}
		userBioInfoDao.ensureRow(userId);
		applyBioUpdate(userId, request);
		return loadUser(userId, zfgcUser);
	}

	private void validateProfileUpdate(UpdateUserProfileRequest request) {
		if (request.displayNamePresent() && (StringUtils.isBlank(request.displayName()) || request.displayName().length() > 64))
			throw new ZfgcInvalidRequestException("Display name must be 1-64 characters.");
		validateLength(request.personalText(), request.personalTextPresent(), 1000, "Personal text");
		validateLength(request.signature(), request.signaturePresent(), 10000, "Signature");
		validateLength(request.location(), request.locationPresent(), 255, "Location");
		validateLength(request.websiteTitle(), request.websiteTitlePresent(), 255, "Website title");
		validateLength(request.websiteUrl(), request.websiteUrlPresent(), 2048, "Website URL");
		if (request.hideEmailFlagPresent() && request.hideEmailFlag() == null)
			throw new ZfgcInvalidRequestException("Hide email must be true or false.");
		if (request.hideOnlineStatusPresent() && request.hideOnlineStatus() == null)
			throw new ZfgcInvalidRequestException("Hide online status must be true or false.");
		if (request.websiteUrlPresent() && StringUtils.isNotBlank(request.websiteUrl())) {
			try {
				java.net.URI uri = java.net.URI.create(request.websiteUrl());
				if (!("http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme()))
						|| uri.getHost() == null) throw new IllegalArgumentException();
			} catch (IllegalArgumentException invalid) {
				throw new ZfgcInvalidRequestException("Website URL must be an absolute HTTP or HTTPS URL.");
			}
		}
	}

	private void validateLength(String value, boolean present, int maximum, String label) {
		if (present && value != null && value.length() > maximum)
			throw new ZfgcInvalidRequestException(label + " is too long.");
	}

	private void applyBioUpdate(Integer userId, UpdateUserProfileRequest request) {
		int updated = userBioInfoDao.updateSelective(userId,
				request.personalTextPresent(), request.personalText(),
				request.signaturePresent(), request.signature(),
				request.locationPresent(), request.location(),
				request.birthDatePresent(), request.birthDate(),
				request.genderIdPresent(), request.genderId(),
				request.websiteTitlePresent(), request.websiteTitle(),
				request.websiteUrlPresent(), request.websiteUrl(),
				request.hideEmailFlagPresent(), request.hideEmailFlag(),
				request.hideOnlineStatusPresent(), request.hideOnlineStatus(),
				request.avatarIdPresent(), request.avatarId());
		if (updated != 1) throw new ZfgcNotFoundException();
	}

	public List<Award> getAwardCatalog() {
		return userDataProvider.getAwardCatalog();
	}

	@Transactional
	public User grantAward(Integer userId, GrantAwardRequest request, User requester) {
		if (request == null || request.awardId() == null) {
			throw new ZfgcInvalidRequestException("awardId is required.");
		}
		userDataProvider.findUser(userId).orElseThrow(ZfgcNotFoundException::new);
		return userDataProvider.grantAward(userId, request.awardId(), request.reason(),
				request.contentEntityId(), requester.getUserId());
	}

	public record GrantAwardRequest(Integer awardId, String reason, Integer contentEntityId) {}

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
		deleteReleasedBlobs(released);
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

		released.addAll(projectDataProvider.purgeOwnedContentEntities(userId));
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

		userDataProvider.deleteUserAwards(userId);
		forumDataProvider.deleteNotificationSubscriptions(userId);
		projectDataProvider.deleteTeamMemberships(userId);
		List<ReleasedResource> released = purgeBioInfoAndAvatar(userId);
		userDataProvider.reassignContentResources(userId, sentinelId);
		userDataProvider.deleteMigratorIdMapEntries("USER", List.of(userId));
		return released;
	}

	private void scrubResidue(Integer userId) {
		userDataProvider.scrubIssuedWarnings(userId);
		userDataProvider.scrubReceivedWarningMigrationHashes(userId);
		forumDataProvider.scrubModerationResidue(userId);
		migrationConflictDataProvider.nullConflictResolvers(userId);
		reactionDataProvider.scrubGivenReactions(userId);
		userDataProvider.nullAwardGranters(userId);
	}

	private void scrubRetainedContributions(Integer userId) {
		wikiDataProvider.scrubRetainedWikiContributions(userId);
		projectDataProvider.scrubRetainedContentContributions(userId);
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

	private void deleteReleasedBlobs(List<ReleasedResource> released) {
		for (ReleasedResource resource : released) {
			try {
				Files.deleteIfExists(contentService.storedFile(resource));
			} catch (IOException | RuntimeException blobDeleteFailure) {
				log.warn("stored blob for content_resource_id {} could not be deleted; a later sweep may "
						+ "reconcile it", resource.contentResourceId(), blobDeleteFailure);
			}
		}
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
				projectDataProvider.countOwnedContentEntities(userId, "PROJECT"),
				projectDataProvider.countOwnedContentEntities(userId, "RESOURCE"),
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
