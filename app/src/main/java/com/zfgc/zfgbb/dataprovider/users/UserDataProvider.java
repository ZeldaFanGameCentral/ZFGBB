package com.zfgc.zfgbb.dataprovider.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.users.AvatarDao;
import com.zfgc.zfgbb.dao.users.BrUserPermissionDao;
import com.zfgc.zfgbb.dao.users.EmailAddressDao;
import com.zfgc.zfgbb.dao.users.UserBioInfoDao;
import com.zfgc.zfgbb.dao.users.UserContactInfoDao;
import com.zfgc.zfgbb.dao.users.UserDao;
import com.zfgc.zfgbb.config.loadoption.user.BasicUserLoadOptions;
import com.zfgc.zfgbb.config.loadoption.user.FullUserLoadOptions;
import com.zfgc.zfgbb.config.loadoption.user.LoggedInUserLoadOptions;
import com.zfgc.zfgbb.dao.UserPermissionViewDao;
import com.zfgc.zfgbb.dbo.AvatarDbo;
import com.zfgc.zfgbb.dbo.AvatarDboExample;
import com.zfgc.zfgbb.dbo.AwardDbo;
import com.zfgc.zfgbb.dbo.AwardDboExample;
import com.zfgc.zfgbb.dbo.UserAwardDbo;
import com.zfgc.zfgbb.dbo.UserAwardDboExample;
import com.zfgc.zfgbb.dbo.BrUserPermissionDbo;
import com.zfgc.zfgbb.dbo.EmailAddressDbo;
import com.zfgc.zfgbb.dbo.EmailAddressDboExample;
import com.zfgc.zfgbb.dbo.UserBioInfoDbo;
import com.zfgc.zfgbb.dbo.UserBioInfoDboExample;
import com.zfgc.zfgbb.dbo.UserContactInfoDbo;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.dbo.UserSettingsDbo;
import com.zfgc.zfgbb.mappers.AwardDboMapper;
import com.zfgc.zfgbb.mappers.UserAwardDboMapper;
import com.zfgc.zfgbb.mappers.UserSettingsDboMapper;
import com.zfgc.zfgbb.mapstruct.users.AvatarMap;
import com.zfgc.zfgbb.mapstruct.users.EmailAddressMap;
import com.zfgc.zfgbb.mapstruct.users.UserBioInfoMap;
import com.zfgc.zfgbb.mapstruct.users.UserContactInfoMap;
import com.zfgc.zfgbb.mapstruct.users.UserMap;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.users.Avatar;
import com.zfgc.zfgbb.model.users.Award;
import com.zfgc.zfgbb.model.users.EmailAddress;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.model.users.UserBioInfo;
import com.zfgc.zfgbb.model.users.UserContactInfo;
import com.zfgc.zfgbb.model.users.UserSettings;
import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.renderer.ContentRenderer;

@Repository
public class UserDataProvider {

	private static final Integer ZFGC_USER_PERMISSION_ID = 1;

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserPermissionViewDao userPermissionDao;

	@Autowired
	private BrUserPermissionDao brUserPermissionDao;

	@Autowired
	private EmailAddressDao emailDao;

	@Autowired
	private UserBioInfoDao bioInfoDao;

	@Autowired
	private AvatarDao avatarDao;

	@Autowired
	private UserContactInfoDao contactInfoDao;

	@Autowired
	private ContentRenderer contentRenderer;

	@Autowired
	private UserMap userMap;

	@Autowired
	private UserBioInfoMap userBioInfoMap;



	@Autowired
	private EmailAddressMap emailAddressMap;

	@Autowired
	private UserSettingsDboMapper userSettingsMapper;

	@Autowired
	private AwardDboMapper awardMapper;

	@Autowired
	private UserAwardDboMapper userAwardMapper;

	@Autowired
	private UserProfileFacade userProfileFacade;

	public Optional<User> findUser(String userName) {
		if (userName == null || userName.isBlank()) {
			return Optional.empty();
		}
		UserDboExample ex = new UserDboExample();
		ex.createCriteria().andUserNameEqualTo(userName).andActiveFlagEqualTo(true);
		return userDao.get(ex).stream().findFirst()
				.flatMap(dbo -> findUser(dbo.getUserId(), new LoggedInUserLoadOptions()));
	}

	public Optional<User> findUserForAuthentication(String userName) {
		if (userName == null || userName.isBlank())
			return Optional.empty();
		UserDboExample ex = new UserDboExample();
		ex.createCriteria().andUserNameEqualTo(userName);
		return userDao.get(ex).stream().findFirst()
				.map(dbo -> hydrateUser(dbo, new LoggedInUserLoadOptions()));
	}

	public Optional<User> findUser(Integer userId) {
		return findUser(userId, new BasicUserLoadOptions());
	}

	public Optional<User> findUser(Integer userId, BasicUserLoadOptions loadOptions) {
		if (userId == null) {
			return Optional.empty();
		}
		UserDboExample ex = new UserDboExample();
		ex.createCriteria().andUserIdEqualTo(userId).andActiveFlagEqualTo(true);
		return userDao.get(ex).stream().findFirst()
				.map(userDb -> hydrateUser(userDb, loadOptions));
	}

	private User hydrateUser(UserDbo userDb, BasicUserLoadOptions loadOptions) {
		Map<Integer, User> users = userProfileFacade.loadUsersByIds(List.of(userDb.getUserId()), loadOptions);
		return users.get(userDb.getUserId());
	}



	public Map<Integer, User> findPublicAuthorsByIds(Collection<Integer> requestedUserIds) {
		BasicUserLoadOptions loadOptions = new BasicUserLoadOptions();
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

		EmailAddressDbo emailDbo = emailAddressMap.toDbo(user.getEmail());
		emailDao.save(emailDbo);
		UserContactInfoDbo contactInfo = new UserContactInfoDbo();
		contactInfo.setUserId(userDbo.getUserId());
		contactInfo.setEmailAddressId(emailDbo.getEmailAddressId());
		contactInfo.setAllowEmailFlag(true);
		contactInfo.setAllowPmFlag(true);
		contactInfoDao.getMapper().insertSelective(contactInfo);

		UserBioInfoDbo bioInfo = user.getBioInfo() != null
				? userBioInfoMap.toDbo(user.getBioInfo())
				: new UserBioInfoDbo();
		bioInfo.setUserId(userDbo.getUserId());
		bioInfoDao.getMapper().insertSelective(bioInfo);

		BrUserPermissionDbo defaultPerm = new BrUserPermissionDbo();
		defaultPerm.setUserId(userDbo.getUserId());
		defaultPerm.setUserPermissionId(ZFGC_USER_PERMISSION_ID);
		brUserPermissionDao.save(defaultPerm);

		return findUser(userDbo.getUserId(), new FullUserLoadOptions())
				.orElseThrow(() -> new IllegalStateException("user disappeared after createUser"));
	}

	public Optional<UserDbo> findByUserName(String userName) {
		UserDboExample ex = new UserDboExample();
		ex.createCriteria().andUserNameEqualTo(userName);
		return userDao.get(ex).stream().findFirst();
	}

	public Optional<EmailAddressDbo> findByEmail(String email) {
		EmailAddressDboExample ex = new EmailAddressDboExample();
		ex.createCriteria().andEmailAddressEqualTo(email);
		return emailDao.get(ex).stream().findFirst();
	}

	public User saveUserProfile(User user) {
		UserDbo userDbo = userMap.toDbo(user);
		userDbo = userDao.save(userDbo);

		if (user.getBioInfo() != null) {
			UserBioInfoDbo bioInfoDbo = userBioInfoMap.toDbo(user.getBioInfo());
			bioInfoDao.save(bioInfoDbo);
		}

		return findUser(userDbo.getUserId(), new BasicUserLoadOptions())
				.orElseThrow(() -> new IllegalStateException("user disappeared after saveUserProfile"));
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
		return findUser(userId, new FullUserLoadOptions())
				.orElseThrow(() -> new IllegalStateException("user disappeared after grantAward"));
	}
}
