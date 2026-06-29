package com.zfgc.zfgbb.services.core;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.config.loadoption.user.FullUserLoadOptions;
import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.dbo.GenderLkupDboExample;
import com.zfgc.zfgbb.mappers.GenderLkupDboMapper;
import com.zfgc.zfgbb.mappers.custom.UserProfileMapper;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.exception.ZfgcUnauthorizedException;
import com.zfgc.zfgbb.model.users.Award;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.users.EmailAddress;
import com.zfgc.zfgbb.model.users.HashedPassword;
import com.zfgc.zfgbb.content.renderer.TemplateDataService;
import com.zfgc.zfgbb.content.renderer.TemplateSource;
import com.zfgc.zfgbb.model.users.RegistrationRequest;
import com.zfgc.zfgbb.model.users.UserSettings;
import com.zfgc.zfgbb.model.users.UpdateUserProfileRequest;

@Service
@Transactional
public class UserService implements TemplateDataService {

	private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9_]{3,32}$");
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final java.util.Set<String> VALID_THEMES =
			java.util.Set.of("MIDNIGHT", "KIKORI", "GORON", "SHEIK");
	private static final java.util.Set<String> RESERVED_IDENTITY_TOKENS =
			java.util.Set.of("[deleted]", "__deleted__");

	@Autowired
	private UserDataProvider userDataProvider;

	@Autowired
	private PasswordService passwordService;

	@Autowired
	private ProfileAccessRules profileAccessRules;

	@Autowired
	private UserProfileMapper userProfileMapper;

	@Autowired
	private GenderLkupDboMapper genderLkupDboMapper;

	public User createNewUser(RegistrationRequest req) {
		validate(req);

		if (userDataProvider.findByUserName(req.userName()).isPresent()) {
			throw new ZfgcInvalidRequestException("Username already taken.");
		}
		if (userDataProvider.findByEmail(req.email()).isPresent()) {
			throw new ZfgcInvalidRequestException("Email already registered.");
		}

		HashedPassword hashed = passwordService.hash(req.password());

		User user = User.builder()
				.userName(req.userName())
				.displayName(req.displayName())
				.ssoKey(req.userName())
				.activeFlag(true)
				.passwordHash(hashed.hash())
				.passwordAlgo(hashed.algo())
				.passwordSalt(hashed.salt())
				.passwordChangedTs(java.time.OffsetDateTime.now(java.time.ZoneOffset.UTC))
				.failedLoginCount(0)
				.email(EmailAddress.builder().emailAddress(req.email()).spammerFlag(false).build())
				.build();

		return userDataProvider.createUser(user);
	}

	@TemplateSource("/user-profile/{userId}")
	public User loadUser(Integer userId, User requester) {
		User user = userDataProvider.findUser(userId, new FullUserLoadOptions())
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

	public java.util.Set<String> profileAllowedActions(Integer userId, User requester) {
		userDataProvider.findUser(userId).orElseThrow(ZfgcNotFoundException::new);
		return profileAccessRules.permittedProfileActions(requester, userId);
	}

	public User saveUserProfile(Integer userId, UpdateUserProfileRequest request, User zfgcUser) {
		if (userId == null || request == null) {
			throw new ZfgcInvalidRequestException("User profile and userId are required.");
		}
		validateProfileUpdate(request);
		Integer lockedId = userProfileMapper.lockActiveUserId(userId);
		if (lockedId == null)
			throw new ZfgcNotFoundException();
		if (request.genderIdPresent() && request.genderId() != null) {
			GenderLkupDboExample genderExample = new GenderLkupDboExample();
			genderExample.createCriteria().andGenderIdEqualTo(request.genderId());
			if (genderLkupDboMapper.countByExample(genderExample) == 0)
				throw new ZfgcInvalidRequestException("Unknown gender.");
		}
		if (request.avatarIdPresent() && request.avatarId() != null
				&& !userProfileMapper.isAvatarAvailable(request.avatarId(), userId))
			throw new ZfgcInvalidRequestException("Avatar is not available.");
		if (request.displayNamePresent()) {
			int updated = userProfileMapper.updateDisplayName(request.displayName().trim(), userId);
			if (updated != 1) throw new ZfgcNotFoundException();
		}
		userProfileMapper.ensureUserBioInfoRow(userId);
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
		int updated = userProfileMapper.updateUserBioInfoSelective(userId,
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

	public java.util.List<Award> getAwardCatalog() {
		return userDataProvider.getAwardCatalog();
	}

	public User grantAward(Integer userId, GrantAwardRequest request, User requester) {
		if (request == null || request.awardId() == null) {
			throw new ZfgcInvalidRequestException("awardId is required.");
		}
		userDataProvider.findUser(userId).orElseThrow(ZfgcNotFoundException::new);
		return userDataProvider.grantAward(userId, request.awardId(), request.reason(),
				request.contentEntityId(), requester.getUserId());
	}

	public record GrantAwardRequest(Integer awardId, String reason, Integer contentEntityId) {}

	private boolean isReservedIdentity(String value) {
		if (value == null)
			return false;
		String normalized = java.text.Normalizer.normalize(value, java.text.Normalizer.Form.NFKC)
				.trim().toLowerCase(java.util.Locale.ROOT);
		return RESERVED_IDENTITY_TOKENS.contains(normalized);
	}

	private void validate(RegistrationRequest req) {
		if (req == null) {
			throw new ZfgcInvalidRequestException("Registration request is required.");
		}
		if (StringUtils.isBlank(req.userName()) || !USERNAME_PATTERN.matcher(req.userName()).matches()) {
			throw new ZfgcInvalidRequestException("Username must be 3-32 characters, letters/digits/underscore only.");
		}
		if (StringUtils.isBlank(req.displayName())) {
			throw new ZfgcInvalidRequestException("Display name is required.");
		}
		if (isReservedIdentity(req.userName()) || isReservedIdentity(req.displayName())) {
			throw new ZfgcInvalidRequestException("That username or display name is reserved.");
		}
		if (StringUtils.isBlank(req.email()) || !EMAIL_PATTERN.matcher(req.email()).matches()) {
			throw new ZfgcInvalidRequestException("A valid email address is required.");
		}
		if (req.password() == null || req.password().length() < MIN_PASSWORD_LENGTH) {
			throw new ZfgcInvalidRequestException(
					"Password must be at least " + MIN_PASSWORD_LENGTH + " characters.");
		}
	}
}
