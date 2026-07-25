package com.zfgc.zfgbb.services.users;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.config.loadoption.UserLoadOptions;
import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.dbo.GenderLkupDboExample;
import com.zfgc.zfgbb.mappers.GenderLkupDboMapper;
import com.zfgc.zfgbb.mappers.custom.UserProfileMapper;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.model.users.Award;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.content.renderer.templates.TemplateDataService;
import com.zfgc.zfgbb.content.renderer.templates.TemplateSource;
import com.zfgc.zfgbb.model.users.UserSettings;
import com.zfgc.zfgbb.model.users.UpdateUserProfileRequest;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements TemplateDataService {

	private static final Set<String> VALID_THEMES =
			Set.of("MIDNIGHT", "KIKORI", "GORON", "SHEIK");

	private final UserDataProvider userDataProvider;

	private final ProfileAccessRules profileAccessRules;

	private final UserProfileMapper userProfileMapper;

	private final GenderLkupDboMapper genderLkupDboMapper;

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


}
