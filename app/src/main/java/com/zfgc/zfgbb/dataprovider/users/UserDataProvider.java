package com.zfgc.zfgbb.dataprovider.users;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.OffsetDateTime;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.users.BrUserPermissionDao;
import com.zfgc.zfgbb.dao.users.EmailAddressDao;
import com.zfgc.zfgbb.dao.users.UserBioInfoDao;
import com.zfgc.zfgbb.dao.users.UserDao;
import com.zfgc.zfgbb.config.loadoption.UserLoadOptions;
import com.zfgc.zfgbb.dbo.AwardDboExample;
import com.zfgc.zfgbb.dbo.UserAwardDbo;
import com.zfgc.zfgbb.dbo.BrUserPermissionDbo;
import com.zfgc.zfgbb.dbo.EmailAddressDbo;
import com.zfgc.zfgbb.dbo.EmailAddressDboExample;
import com.zfgc.zfgbb.dbo.UserBioInfoDbo;
import com.zfgc.zfgbb.dbo.UserContactInfoDbo;
import com.zfgc.zfgbb.dbo.UserContactInfoDboExample;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.dbo.UserSettingsDbo;
import com.zfgc.zfgbb.mappers.AwardDboMapper;
import com.zfgc.zfgbb.mappers.UserAwardDboMapper;
import com.zfgc.zfgbb.mappers.UserContactInfoDboMapper;
import com.zfgc.zfgbb.mappers.UserSettingsDboMapper;
import com.zfgc.zfgbb.mapstruct.users.EmailAddressMap;
import com.zfgc.zfgbb.mapstruct.users.UserBioInfoMap;
import com.zfgc.zfgbb.mapstruct.users.UserMap;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.users.Award;
import com.zfgc.zfgbb.model.users.EmailAddress;
import com.zfgc.zfgbb.model.users.UserSettings;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserDataProvider {

	private static final Integer ZFGC_USER_PERMISSION_ID = 1;

	private final UserDao userDao;

	private final BrUserPermissionDao brUserPermissionDao;

	private final EmailAddressDao emailDao;

	private final UserBioInfoDao bioInfoDao;

	private final UserContactInfoDboMapper userContactInfoDboMapper;

	private final UserMap userMap;

	private final UserBioInfoMap userBioInfoMap;

	private final EmailAddressMap emailAddressMap;

	private final UserSettingsDboMapper userSettingsMapper;

	private final AwardDboMapper awardMapper;

	private final UserAwardDboMapper userAwardMapper;

	private final UserProfileFacade userProfileFacade;

	public Optional<User> findUserForAuthentication(String userName) {
		if (userName == null || userName.isBlank())
			return Optional.empty();
		UserDboExample ex = new UserDboExample();
		ex.createCriteria().andUserNameEqualTo(userName);
		return userDao.get(ex).stream().findFirst()
				.map(dbo -> hydrateUser(dbo, UserLoadOptions.loggedIn()));
	}

	public Optional<User> findUser(Integer userId) {
		return findUser(userId, UserLoadOptions.basic());
	}

	public Optional<User> findUser(Integer userId, UserLoadOptions loadOptions) {
		if (userId == null) {
			return Optional.empty();
		}
		UserDboExample ex = new UserDboExample();
		ex.createCriteria().andUserIdEqualTo(userId).andActiveFlagEqualTo(true);
		return userDao.get(ex).stream().findFirst()
				.map(userDb -> hydrateUser(userDb, loadOptions));
	}

	private User hydrateUser(UserDbo userDb, UserLoadOptions loadOptions) {
		Map<Integer, User> users = userProfileFacade.loadUsersByIds(List.of(userDb.getUserId()), loadOptions);
		return users.get(userDb.getUserId());
	}



	public Map<Integer, User> findPublicAuthorsByIds(Collection<Integer> requestedUserIds) {
		UserLoadOptions loadOptions = UserLoadOptions.publicProfile();
		Map<Integer, User> users = userProfileFacade.loadUsersByIds(requestedUserIds, loadOptions);
		for (User author : users.values()) {
			author.retainPublicRankPermissions();
			if (author.getBioInfo() != null)
				author.getBioInfo().setSignature(null);
		}
		return users;
	}

	private UserSettings toSettings(UserSettingsDbo dbo) {
		if (dbo == null) {
			return new UserSettings();
		}
		return UserSettings.builder()
				.userId(dbo.getUserId())
				.theme(dbo.getTheme())
				.smileySet(dbo.getSmileySet())
				.notifyAnnouncementsFlag(dbo.getNotifyAnnouncementsFlag())
				.notifySendBodyFlag(dbo.getNotifySendBodyFlag())
				.sendHappyBirthdayFlag(dbo.getSendHappyBirthdayFlag())
				.build();
	}

	public UserSettings saveUserSettings(Integer userId, UserSettings settings) {
		UserSettingsDbo dbo = new UserSettingsDbo();
		dbo.setUserId(userId);
		dbo.setTheme(settings.getTheme());
		dbo.setSmileySet(settings.getSmileySet());
		dbo.setNotifyAnnouncementsFlag(settings.getNotifyAnnouncementsFlag());
		dbo.setNotifySendBodyFlag(settings.getNotifySendBodyFlag());
		dbo.setSendHappyBirthdayFlag(settings.getSendHappyBirthdayFlag());

		UserSettingsDbo existing = userSettingsMapper.selectByPrimaryKey(userId);
		if (existing == null) {
			userSettingsMapper.insertSelective(dbo);
		} else {
			dbo.setMigrationHash(existing.getMigrationHash());
			if (dbo.getNotifyAnnouncementsFlag() == null) {
				dbo.setNotifyAnnouncementsFlag(existing.getNotifyAnnouncementsFlag());
			}
			if (dbo.getNotifySendBodyFlag() == null) {
				dbo.setNotifySendBodyFlag(existing.getNotifySendBodyFlag());
			}
			if (dbo.getSendHappyBirthdayFlag() == null) {
				dbo.setSendHappyBirthdayFlag(existing.getSendHappyBirthdayFlag());
			}
			userSettingsMapper.updateByPrimaryKey(dbo);
		}
		return toSettings(userSettingsMapper.selectByPrimaryKey(userId));
	}

	public User createUser(User user) {
		UserDbo userDbo = userMap.toDbo(user);
		userDao.save(userDbo);
		return createUserAssociations(user, userDbo);
	}

	private User createUserAssociations(User user, UserDbo userDbo) {
		EmailAddressDbo emailDbo = emailAddressMap.toDbo(user.getEmail());
		emailDao.save(emailDbo);
		UserContactInfoDbo contactInfo = new UserContactInfoDbo();
		contactInfo.setUserId(userDbo.getUserId());
		contactInfo.setEmailAddressId(emailDbo.getEmailAddressId());
		contactInfo.setAllowEmailFlag(true);
		contactInfo.setAllowPmFlag(true);
		userContactInfoDboMapper.insertSelective(contactInfo);

		UserBioInfoDbo bioInfo = user.getBioInfo() != null
				? userBioInfoMap.toDbo(user.getBioInfo())
				: new UserBioInfoDbo();
		bioInfo.setUserId(userDbo.getUserId());
		bioInfoDao.insertSelective(bioInfo);

		BrUserPermissionDbo defaultPerm = new BrUserPermissionDbo();
		defaultPerm.setUserId(userDbo.getUserId());
		defaultPerm.setUserPermissionId(ZFGC_USER_PERMISSION_ID);
		brUserPermissionDao.insert(defaultPerm);

		return findUser(userDbo.getUserId(), UserLoadOptions.full())
				.orElseThrow(() -> new IllegalStateException("user disappeared after createUser"));
	}

	public User replaceUserIdentity(User identity, int userId) {
		if (identity == null || identity.getEmail() == null
				|| identity.getEmail().getEmailAddress() == null
				|| identity.getEmail().getEmailAddress().isBlank())
			throw new IllegalArgumentException(
					"Replacing a user identity requires an email address.");
		UserDbo existing = userDao.find(userId).orElseThrow(() -> new IllegalStateException(
				"Cannot replace the identity of user " + userId + " because that user does not exist."));
		UserDbo replacement = userMap.toDbo(identity);
		replacement.setUserId(userId);
		replacement.setMigrationHash(existing.getMigrationHash());
		replacement.setTokensValidAfterTs(identity.getPasswordChangedTs());
		userDao.save(replacement);
		replaceEmailAddress(userId, identity.getEmail());
		return findUser(userId, UserLoadOptions.full())
				.orElseThrow(() -> new IllegalStateException("user disappeared after replaceUserIdentity"));
	}

	private void replaceEmailAddress(int userId, EmailAddress requested) {
		Optional<UserContactInfoDbo> contactInfo = Optional
				.ofNullable(userContactInfoDboMapper.selectByPrimaryKey(userId));
		Optional<EmailAddressDbo> currentAddress = contactInfo
				.map(UserContactInfoDbo::getEmailAddressId)
				.flatMap(emailDao::find);
		if (currentAddress.filter(current -> requested.getEmailAddress().equals(current.getEmailAddress()))
				.isPresent())
			return;
		Optional<EmailAddressDbo> adoptable = findByEmail(requested.getEmailAddress())
				.filter(unclaimed -> !emailAddressIsClaimedBySomeUser(unclaimed.getEmailAddressId()));
		if (adoptable.isEmpty() && currentAddress.isPresent()
				&& !isEmailAddressSharedWithAnotherUser(currentAddress.get().getEmailAddressId(), userId)) {
			EmailAddressDbo owned = currentAddress.get();
			owned.setEmailAddress(requested.getEmailAddress());
			owned.setSpammerFlag(Boolean.TRUE.equals(requested.getSpammerFlag()));
			emailDao.save(owned);
			return;
		}
		EmailAddressDbo replacement = adoptable.orElseGet(() -> newEmailAddress(requested));
		replacement.setSpammerFlag(Boolean.TRUE.equals(requested.getSpammerFlag()));
		emailDao.save(replacement);
		UserContactInfoDbo link = contactInfo.orElseGet(UserContactInfoDbo::new);
		link.setUserId(userId);
		link.setEmailAddressId(replacement.getEmailAddressId());
		if (contactInfo.isPresent()) {
			userContactInfoDboMapper.updateByPrimaryKeySelective(link);
			return;
		}
		link.setAllowEmailFlag(true);
		link.setAllowPmFlag(true);
		userContactInfoDboMapper.insertSelective(link);
	}

	private EmailAddressDbo newEmailAddress(EmailAddress requested) {
		EmailAddressDbo created = emailAddressMap.toDbo(requested);
		created.setEmailAddressId(null);
		return created;
	}

	private boolean isEmailAddressSharedWithAnotherUser(Integer emailAddressId, int userId) {
		UserContactInfoDboExample ex = new UserContactInfoDboExample();
		ex.createCriteria().andEmailAddressIdEqualTo(emailAddressId).andUserIdNotEqualTo(userId);
		return !userContactInfoDboMapper.selectByExample(ex).isEmpty();
	}

	public boolean emailAddressIsClaimedBySomeUser(Integer emailAddressId) {
		UserContactInfoDboExample ex = new UserContactInfoDboExample();
		ex.createCriteria().andEmailAddressIdEqualTo(emailAddressId);
		return !userContactInfoDboMapper.selectByExample(ex).isEmpty();
	}

	public boolean emailAddressBelongsTo(Integer emailAddressId, int userId) {
		UserContactInfoDboExample ex = new UserContactInfoDboExample();
		ex.createCriteria().andEmailAddressIdEqualTo(emailAddressId).andUserIdEqualTo(userId);
		return !userContactInfoDboMapper.selectByExample(ex).isEmpty();
	}

	public Optional<UserDbo> findByUserName(String userName) {
		UserDboExample ex = new UserDboExample();
		ex.createCriteria().andUserNameEqualTo(userName);
		return userDao.get(ex).stream().findFirst();
	}

	public Optional<UserDbo> findBySsoKey(String ssoKey) {
		UserDboExample ex = new UserDboExample();
		ex.createCriteria().andSsoKeyEqualTo(ssoKey);
		return userDao.get(ex).stream().findFirst();
	}

	public List<Integer> findUserIdsHoldingIdentity(String identity) {
		if (identity == null || identity.isBlank())
			return List.of();
		UserDboExample ex = new UserDboExample();
		ex.createCriteria().andUserNameEqualTo(identity);
		ex.or().andSsoKeyEqualTo(identity);
		return userDao.get(ex).stream()
				.map(UserDbo::getUserId)
				.distinct()
				.sorted()
				.toList();
	}

	public int cutOffExistingTokensForAllUsers(OffsetDateTime cutoff) {
		UserDbo update = new UserDbo();
		update.setTokensValidAfterTs(cutoff);
		return userDao.updateWhere(update, new UserDboExample());
	}

	public Optional<EmailAddressDbo> findByEmail(String email) {
		EmailAddressDboExample ex = new EmailAddressDboExample();
		ex.createCriteria().andEmailAddressEqualTo(email);
		return emailDao.get(ex).stream().findFirst();
	}




	public List<Award> getAwardCatalog() {
		AwardDboExample awardEx = new AwardDboExample();
		awardEx.setOrderByClause("award_id");
		return awardMapper.selectByExample(awardEx).stream()
				.map(awardDbo -> {
					Award model = new Award();
					model.setAwardId(awardDbo.getAwardId());
					model.setCode(awardDbo.getCode());
					model.setName(awardDbo.getName());
					model.setDescription(awardDbo.getDescription());
					model.setIcon(awardDbo.getIcon());
					return model;
				})
				.toList();
	}

	public User grantAward(Integer userId, Integer awardId, String reason, Integer contentEntityId,
			Integer grantedByUserId) {
		UserAwardDbo dbo = new UserAwardDbo();
		dbo.setUserId(userId);
		dbo.setAwardId(awardId);
		dbo.setReason(reason);
		dbo.setContentEntityId(contentEntityId);
		dbo.setGrantedByUserId(grantedByUserId);
		userAwardMapper.insertSelective(dbo);
		return findUser(userId, UserLoadOptions.full())
				.orElseThrow(() -> new IllegalStateException("user disappeared after grantAward"));
	}
}
