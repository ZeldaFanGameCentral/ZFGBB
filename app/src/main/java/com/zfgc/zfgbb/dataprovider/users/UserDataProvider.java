package com.zfgc.zfgbb.dataprovider.users;

import com.zfgc.zfgbb.dao.meta.AdvisoryLockDao;
import java.util.UUID;
import com.zfgc.zfgbb.dao.cms.ContentResourceDao;
import com.zfgc.zfgbb.dao.forum.MessageDao;
import com.zfgc.zfgbb.dao.meta.MigratorIdMapDao;
import com.zfgc.zfgbb.dao.users.AccountDeletionAuditDao;
import com.zfgc.zfgbb.dbo.AccountDeletionAuditDbo;
import com.zfgc.zfgbb.dbo.AccountDeletionAuditDboExample;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import com.zfgc.zfgbb.dbo.MigratorIdMapDboExample;
import com.zfgc.zfgbb.dao.users.UserPermissionViewDao;
import com.zfgc.zfgbb.dao.users.UserReactionSummaryViewDao;
import com.zfgc.zfgbb.dao.users.UserRefreshTokenDao;
import com.zfgc.zfgbb.dbo.UserPermissionViewDbo;
import com.zfgc.zfgbb.dbo.UserReactionSummaryViewDbo;
import com.zfgc.zfgbb.dbo.UserReactionSummaryViewDboExample;
import com.zfgc.zfgbb.dbo.UserPermissionViewDboExample;
import com.zfgc.zfgbb.dbo.UserRefreshTokenDbo;
import com.zfgc.zfgbb.dbo.UserRefreshTokenDboExample;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.OffsetDateTime;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.users.AvatarDao;
import com.zfgc.zfgbb.dao.users.BrUserPermissionDao;
import com.zfgc.zfgbb.dao.users.EmailAddressDao;
import com.zfgc.zfgbb.dao.users.UserBioInfoDao;
import com.zfgc.zfgbb.dao.users.UserDao;
import com.zfgc.zfgbb.dao.users.UserPermissionGroupAssocDao;
import com.zfgc.zfgbb.dbo.BrUserPermissionDboExample;
import com.zfgc.zfgbb.dbo.UserBioInfoDboExample;
import com.zfgc.zfgbb.dbo.UserPermissionGroupAssocDboExample;
import com.zfgc.zfgbb.dbo.UserSettingsDboExample;
import com.zfgc.zfgbb.dbo.UserWarningDboExample;
import com.zfgc.zfgbb.model.users.UserSummary;
import com.zfgc.zfgbb.dataprovider.loadoption.UserLoadOptions;
import com.zfgc.zfgbb.dbo.BrUserPermissionDbo;
import com.zfgc.zfgbb.dbo.EmailAddressDbo;
import com.zfgc.zfgbb.dbo.EmailAddressDboExample;
import com.zfgc.zfgbb.dbo.UserBioInfoDbo;
import com.zfgc.zfgbb.dbo.UserContactInfoDbo;
import com.zfgc.zfgbb.dbo.UserContactInfoDboExample;
import com.zfgc.zfgbb.dbo.AvatarDbo;
import com.zfgc.zfgbb.dbo.AvatarDboExample;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.dbo.UserSettingsDbo;
import com.zfgc.zfgbb.dao.users.UserContactInfoDao;
import com.zfgc.zfgbb.dao.users.UserSettingsDao;
import com.zfgc.zfgbb.dao.users.UserWarningDao;
import com.zfgc.zfgbb.mapstruct.users.AvatarMap;
import com.zfgc.zfgbb.mapstruct.users.EmailAddressMap;
import com.zfgc.zfgbb.mapstruct.users.UserBioInfoMap;
import com.zfgc.zfgbb.mapstruct.users.UserContactInfoMap;
import com.zfgc.zfgbb.mapstruct.users.UserMap;
import com.zfgc.zfgbb.mapstruct.users.PermissionMap;
import com.zfgc.zfgbb.mapstruct.users.ReactionSummaryMap;
import com.zfgc.zfgbb.mapstruct.users.UserSettingsMap;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.model.users.ReactionSummary;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.users.UserBioInfo;
import com.zfgc.zfgbb.model.users.UserContactInfo;
import com.zfgc.zfgbb.model.users.EmailAddress;
import com.zfgc.zfgbb.model.users.UserSettings;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserDataProvider {

	private final AdvisoryLockDao advisoryLockDao;

	private static final String SITE_ADMIN_PERMISSION_CODE = "ZFGC_SITE_ADMIN";

	private static final int ANONYMIZATION_SENTINEL_USER_ID = 0;

	private static final Integer ZFGC_USER_PERMISSION_ID = 1;

	private final UserDao userDao;

	private final MessageDao messageDao;

	private final BrUserPermissionDao brUserPermissionDao;

	private final EmailAddressDao emailDao;

	private final UserBioInfoDao bioInfoDao;

	private final UserContactInfoDao userContactInfoDao;

	private final UserMap userMap;

	private final UserSettingsMap userSettingsMap;

	private final UserBioInfoMap userBioInfoMap;

	private final EmailAddressMap emailAddressMap;

	private final UserSettingsDao userSettingsDao;

	private final UserWarningDao userWarningDao;

	private final UserReactionSummaryViewDao userReactionSummaryViewDao;

	private final ReactionSummaryMap reactionSummaryMap;

	private final GuestPermissionDataProvider guestPermissionDataProvider;

	private final MigratorIdMapDao migratorIdMapDao;

	private final UserPermissionViewDao userPermissionViewDao;

	private final UserRefreshTokenDao userRefreshTokenDao;

	private final AccountDeletionAuditDao accountDeletionAuditDao;

	private final ContentResourceDao contentResourceDao;

	private final UserPermissionGroupAssocDao userPermissionGroupAssocDao;

	private final AvatarDao avatarDao;

	public static final String SENTINEL_SSO_KEY = "__deleted__";

	private static final String SENTINEL_DISPLAY_NAME = "[deleted]";

	private final PermissionMap permissionMap;

	private final AvatarMap avatarMap;

	private final UserContactInfoMap userContactInfoMap;

	public Optional<User> findUserForAuthentication(String userName) {
		if (userName == null || userName.isBlank())
			return Optional.empty();
		UserDboExample ex = new UserDboExample();
		ex.createCriteria().andUserNameEqualTo(userName);
		return userDao.getOne(ex)
				.flatMap(dbo -> hydrateUser(dbo, UserLoadOptions.loggedIn()));
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
		return userDao.getOne(ex)
				.flatMap(userDb -> hydrateUser(userDb, loadOptions));
	}

	private Optional<User> hydrateUser(UserDbo userDb, UserLoadOptions loadOptions) {
		Map<Integer, User> users = loadUsersByIds(List.of(userDb.getUserId()), loadOptions);
		return Optional.ofNullable(users.get(userDb.getUserId()));
	}

	private Map<Integer, User> loadUsersByIds(Collection<Integer> userIds, UserLoadOptions loadOptions) {
		if (userIds == null || userIds.isEmpty())
			return Collections.emptyMap();
		List<Integer> distinctIds = userIds.stream().filter(id -> id != null).distinct().toList();
		if (distinctIds.isEmpty())
			return Collections.emptyMap();

		UserDboExample userEx = new UserDboExample();
		userEx.createCriteria().andUserIdIn(distinctIds);
		List<UserDbo> userRows = userDao.get(userEx);
		if (userRows.isEmpty())
			return Collections.emptyMap();

		Map<Integer, UserBioInfoDbo> biosByUserId = Collections.emptyMap();
		if (loadOptions.loadBio()) {
			UserBioInfoDboExample bioEx = new UserBioInfoDboExample();
			bioEx.createCriteria().andUserIdIn(distinctIds);
			biosByUserId = bioInfoDao.get(bioEx).stream()
					.collect(Collectors.toMap(UserBioInfoDbo::getUserId, bio -> bio));
		}

		Map<Integer, UserContactInfoDbo> contactsByUserId = Collections.emptyMap();
		if (loadOptions.loadContactInfo()) {
			UserContactInfoDboExample contactEx = new UserContactInfoDboExample();
			contactEx.createCriteria().andUserIdIn(distinctIds);
			contactsByUserId = userContactInfoDao.get(contactEx).stream()
					.collect(Collectors.toMap(UserContactInfoDbo::getUserId, contact -> contact));
		}

		Map<Integer, UserSettingsDbo> settingsByUserId = Collections.emptyMap();
		if (loadOptions.loadSettings()) {
			UserSettingsDboExample settingsEx = new UserSettingsDboExample();
			settingsEx.createCriteria().andUserIdIn(distinctIds);
			settingsByUserId = userSettingsDao.get(settingsEx).stream()
					.collect(Collectors.toMap(UserSettingsDbo::getUserId, settings -> settings));
		}

		Map<Integer, AvatarDbo> avatarsById = Collections.emptyMap();
		if (loadOptions.loadAvatar()) {
			List<Integer> avatarIds = biosByUserId.values().stream()
					.map(UserBioInfoDbo::getAvatarId)
					.filter(id -> id != null)
					.distinct()
					.toList();
			if (!avatarIds.isEmpty()) {
				AvatarDboExample avatarEx = new AvatarDboExample();
				avatarEx.createCriteria().andAvatarIdIn(avatarIds).andActiveFlagEqualTo(true);
				avatarsById = avatarDao.get(avatarEx).stream()
						.collect(Collectors.toMap(AvatarDbo::getAvatarId, avatar -> avatar));
			}
		}

		Map<Integer, EmailAddressDbo> emailsById = Collections.emptyMap();
		List<Integer> emailAddressIds = contactsByUserId.values().stream()
				.map(UserContactInfoDbo::getEmailAddressId)
				.filter(id -> id != null)
				.distinct()
				.toList();
		if (!emailAddressIds.isEmpty()) {
			EmailAddressDboExample emailEx = new EmailAddressDboExample();
			emailEx.createCriteria().andEmailAddressIdIn(emailAddressIds);
			emailsById = emailDao.get(emailEx).stream()
					.collect(Collectors.toMap(EmailAddressDbo::getEmailAddressId, email -> email));
		}

		Map<Integer, List<Permission>> permissionsByUserId = Collections.emptyMap();
		if (loadOptions.loadPermissions()) {
			UserPermissionViewDboExample permissionEx = new UserPermissionViewDboExample();
			permissionEx.createCriteria().andUserIdIn(distinctIds);
			permissionsByUserId = userPermissionViewDao.get(permissionEx).stream()
					.collect(Collectors.groupingBy(UserPermissionViewDbo::getUserId,
							Collectors.mapping(permissionMap::toModel, Collectors.toList())));
		}

		Map<Integer, ReactionSummary> reactionByUserId = Collections.emptyMap();
		if (loadOptions.loadReactions()) {
			UserReactionSummaryViewDboExample reactionEx = new UserReactionSummaryViewDboExample();
			reactionEx.createCriteria().andUserIdIn(distinctIds);
			reactionByUserId = userReactionSummaryViewDao.get(reactionEx).stream()
					.collect(Collectors.toMap(UserReactionSummaryViewDbo::getUserId, reactionSummaryMap::toModel));
		}

		Map<Integer, Integer> postCountByOwnerId = Collections.emptyMap();
		if (loadOptions.loadBio())
			postCountByOwnerId = guestPermissionDataProvider.guestVisiblePostCountsByOwner(distinctIds);

		Map<Integer, User> users = new HashMap<>();
		for (UserDbo userRow : userRows) {
			Integer userId = userRow.getUserId();
			UserBioInfoDbo bioRow = biosByUserId.get(userId);
			UserBioInfo bioInfo = null;
			if (bioRow != null && loadOptions.loadBio()) {
				AvatarDbo avatarRow = bioRow.getAvatarId() != null ? avatarsById.get(bioRow.getAvatarId()) : null;
				bioInfo = userBioInfoMap.toModel(bioRow)
						.toBuilder()
						.postCount(postCountByOwnerId.getOrDefault(userId, 0))
						.avatar(avatarRow != null && loadOptions.loadAvatar() ? avatarMap.toModel(avatarRow) : null)
						.build();
			}

			UserContactInfoDbo contactRow = contactsByUserId.get(userId);
			EmailAddressDbo emailRow = contactRow != null ? emailsById.get(contactRow.getEmailAddressId()) : null;
			UserContactInfo contactInfo = null;
			if (contactRow != null && emailRow != null && loadOptions.loadContactInfo()) {
				contactInfo = userContactInfoMap.toModel(contactRow, emailRow);
			}

			UserSettings settings = null;
			if (loadOptions.loadSettings()) {
				UserSettingsDbo settingsRow = settingsByUserId.get(userId);
				settings = settingsRow != null
						? userSettingsMap.toModel(settingsRow)
						: new UserSettings();
			}

			User user = userMap.toModel(userRow)
					.toBuilder()
					.bioInfo(bioInfo)
					.contactInfo(contactInfo)
					.reactionSummary(reactionByUserId.get(userId))
					.awards(Collections.emptyList())
					.permissions(permissionsByUserId.getOrDefault(userId, Collections.emptyList()))
					.settings(settings)
					.build();

			users.put(userId, user);
		}
		return users;
	}

	public Map<Integer, User> findPublicAuthorsByIds(Collection<Integer> requestedUserIds) {
		UserLoadOptions loadOptions = UserLoadOptions.publicProfile();
		Map<Integer, User> users = loadUsersByIds(requestedUserIds, loadOptions);
		for (User author : users.values())
			author.retainPublicRankPermissions();
		return users;
	}

	public UserSettings saveUserSettings(Integer userId, UserSettings settings) {
		Optional<UserSettingsDbo> existing = userSettingsDao.find(userId);
		if (existing.isPresent()) {
			UserSettingsDbo current = existing.get();
			userSettingsMap.applyOnto(settings, current);
			userSettingsDao.update(current);
		} else {
			UserSettingsDbo created = new UserSettingsDbo();
			created.setUserId(userId);
			userSettingsMap.applyOnto(settings, created);
			userSettingsDao.insertSelective(created);
		}
		return userSettingsDao.find(userId).map(userSettingsMap::toModel).orElseGet(UserSettings::new);
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
		userContactInfoDao.insertSelective(contactInfo);

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
		Optional<UserContactInfoDbo> contactInfo = userContactInfoDao.find(userId);
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
			userContactInfoDao.updateSelective(link);
			return;
		}
		link.setAllowEmailFlag(true);
		link.setAllowPmFlag(true);
		userContactInfoDao.insertSelective(link);
	}

	private EmailAddressDbo newEmailAddress(EmailAddress requested) {
		EmailAddressDbo created = emailAddressMap.toDbo(requested);
		created.setEmailAddressId(null);
		return created;
	}

	private boolean isEmailAddressSharedWithAnotherUser(Integer emailAddressId, int userId) {
		UserContactInfoDboExample ex = new UserContactInfoDboExample();
		ex.createCriteria().andEmailAddressIdEqualTo(emailAddressId).andUserIdNotEqualTo(userId);
		return !userContactInfoDao.get(ex).isEmpty();
	}

	public boolean emailAddressIsClaimedBySomeUser(Integer emailAddressId) {
		UserContactInfoDboExample ex = new UserContactInfoDboExample();
		ex.createCriteria().andEmailAddressIdEqualTo(emailAddressId);
		return !userContactInfoDao.get(ex).isEmpty();
	}

	public boolean emailAddressBelongsTo(Integer emailAddressId, int userId) {
		UserContactInfoDboExample ex = new UserContactInfoDboExample();
		ex.createCriteria().andEmailAddressIdEqualTo(emailAddressId).andUserIdEqualTo(userId);
		return !userContactInfoDao.get(ex).isEmpty();
	}

	public Optional<UserDbo> findByUserName(String userName) {
		UserDboExample ex = new UserDboExample();
		ex.createCriteria().andUserNameEqualTo(userName);
		return userDao.getOne(ex);
	}

	public Optional<UserDbo> findBySsoKey(String ssoKey) {
		UserDboExample ex = new UserDboExample();
		ex.createCriteria().andSsoKeyEqualTo(ssoKey);
		return userDao.getOne(ex);
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
		return emailDao.getOne(ex);
	}

	public List<Integer> siteAdministratorIdsWithUsableCredentials() {
		UserPermissionViewDboExample siteAdministrators = new UserPermissionViewDboExample();
		siteAdministrators.createCriteria().andPermissionCodeEqualTo(SITE_ADMIN_PERMISSION_CODE)
				.andUserIdNotEqualTo(ANONYMIZATION_SENTINEL_USER_ID);
		List<Integer> candidates = userPermissionViewDao.get(siteAdministrators).stream()
				.map(UserPermissionViewDbo::getUserId)
				.distinct()
				.toList();
		if (candidates.isEmpty())
			return List.of();

		UserDboExample withPassword = new UserDboExample();
		withPassword.createCriteria().andUserIdIn(candidates)
				.andPasswordHashIsNotNull().andPasswordHashNotEqualTo("");
		Set<Integer> usable = userDao.get(withPassword).stream()
				.map(UserDbo::getUserId)
				.collect(Collectors.toCollection(LinkedHashSet::new));

		UserRefreshTokenDboExample withRefreshToken = new UserRefreshTokenDboExample();
		withRefreshToken.createCriteria().andUserIdIn(candidates);
		userRefreshTokenDao.get(withRefreshToken).stream()
				.map(UserRefreshTokenDbo::getUserId)
				.forEach(usable::add);

		return candidates.stream().filter(usable::contains).sorted().toList();
	}

	public boolean hasUsableCredentialsOutside(int anchorAdministratorId) {
		List<Integer> exempt = List.of(anchorAdministratorId, ANONYMIZATION_SENTINEL_USER_ID);

		UserDboExample foreignPassword = new UserDboExample();
		foreignPassword.createCriteria().andUserIdNotIn(exempt)
				.andPasswordHashIsNotNull().andPasswordHashNotEqualTo("");
		foreignPassword.or(foreignPassword.createCriteria().andUserIdNotIn(exempt)
				.andPasswordAlgoIsNotNull().andPasswordAlgoNotEqualTo(""));
		foreignPassword.or(foreignPassword.createCriteria().andUserIdNotIn(exempt)
				.andPasswordSaltIsNotNull().andPasswordSaltNotEqualTo(""));
		if (userDao.exists(foreignPassword))
			return true;

		UserRefreshTokenDboExample foreignRefreshToken = new UserRefreshTokenDboExample();
		foreignRefreshToken.createCriteria().andUserIdNotEqualTo(anchorAdministratorId);
		return userRefreshTokenDao.exists(foreignRefreshToken);
	}

	public Integer ensureSentinelUser() {
		Optional<Integer> existing = userDao.findUserIdBySsoKey(SENTINEL_SSO_KEY);
		if (existing.isPresent())
			return existing.get();
		UserDbo sentinel = new UserDbo();
		sentinel.setSsoKey(SENTINEL_SSO_KEY);
		sentinel.setUserName(SENTINEL_SSO_KEY);
		sentinel.setDisplayName(SENTINEL_DISPLAY_NAME);
		sentinel.setActiveFlag(false);
		sentinel.setFailedLoginCount(0);
		userDao.save(sentinel);
		return sentinel.getUserId();
	}

	public List<UserSummary> listUsers() {
		Set<Integer> siteAdminIds = userPermissionViewDao.siteAdminUserIds();
		UserDboExample everyUser = new UserDboExample();
		everyUser.setOrderByClause("user_id");
		return userDao.get(everyUser).stream().map(user -> {
			UserSummary summary = new UserSummary();
			summary.setUserId(user.getUserId());
			summary.setUserName(user.getUserName());
			summary.setDisplayName(user.getDisplayName());
			summary.setSiteAdmin(siteAdminIds.contains(user.getUserId()));
			return summary;
		}).toList();
	}

	public boolean isSentinelUser(Integer userId) {
		return userDao.findUserIdBySsoKey(SENTINEL_SSO_KEY).filter(userId::equals).isPresent();
	}

	public boolean isSiteAdmin(Integer userId) {
		return userPermissionViewDao.isSiteAdmin(userId);
	}

	public boolean isLastSiteAdmin(Integer userId) {
		advisoryLockDao.acquireAdminRosterLock();
		return userPermissionViewDao.isSiteAdmin(userId) && userPermissionViewDao.countSiteAdmins() <= 1;
	}

	public boolean adminReplacementRequired(Integer userId) {
		return userPermissionViewDao.isSiteAdmin(userId) && userPermissionViewDao.countSiteAdmins() <= 1;
	}

	public Optional<String> findUserName(Integer userId) {
		return userDao.findUserName(userId);
	}

	public Optional<String> findPrimaryEmailAddress(Integer userId) {
		return emailDao.findPrimaryEmailAddress(userId);
	}

	public List<Integer> findEmailAddressIds(Integer userId) {
		return userContactInfoDao.findEmailAddressIds(userId);
	}

	public void neutralizeIdentity(Integer userId) {
		userDao.neutralizeUserRow(userId, SENTINEL_SSO_KEY + userId);
		bioInfoDao.scrubUserBioInfo(userId);
		BrUserPermissionDboExample brUserPermissionExample = new BrUserPermissionDboExample();
		brUserPermissionExample.createCriteria().andUserIdEqualTo(userId);
		brUserPermissionDao.deleteWhere(brUserPermissionExample);
		UserPermissionGroupAssocDboExample userPermissionGroupAssocExample = new UserPermissionGroupAssocDboExample();
		userPermissionGroupAssocExample.createCriteria().andUserIdEqualTo(userId);
		userPermissionGroupAssocDao.deleteWhere(userPermissionGroupAssocExample);
		userContactInfoDao.deleteUserContactTypes(userId);
		UserSettingsDboExample userSettingsExample = new UserSettingsDboExample();
		userSettingsExample.createCriteria().andUserIdEqualTo(userId);
		userSettingsDao.deleteWhere(userSettingsExample);
		UserContactInfoDboExample userContactInfoExample = new UserContactInfoDboExample();
		userContactInfoExample.createCriteria().andUserIdEqualTo(userId);
		userContactInfoDao.deleteWhere(userContactInfoExample);
	}

	public List<Integer> releaseEmailAddresses(List<Integer> emailAddressIds) {
		List<Integer> retainedSharedAddressIds = new ArrayList<>();
		for (Integer emailAddressId : emailAddressIds.stream().distinct().toList()) {
			emailDao.deleteEmailAddressIfUnreferenced(emailAddressId);
			if (emailDao.existsWithPrimaryKey(emailAddressId))
				retainedSharedAddressIds.add(emailAddressId);
		}
		return retainedSharedAddressIds;
	}

	public Optional<Integer> findBioAvatarId(Integer userId) {
		return bioInfoDao.findBioAvatarId(userId);
	}

	public Optional<Integer> findAvatarContentResourceId(Integer avatarId) {
		return avatarDao.findAvatarContentResourceId(avatarId);
	}

	public void deleteBioInfo(Integer userId) {
		UserBioInfoDboExample bioInfoExample = new UserBioInfoDboExample();
		bioInfoExample.createCriteria().andUserIdEqualTo(userId);
		bioInfoDao.deleteWhere(bioInfoExample);
	}

	public void deleteAvatar(Integer avatarId) {
		avatarDao.delete(avatarId);
	}

	public void deleteUserSettings(Integer userId) {
		userSettingsDao.delete(userId);
	}

	public void deleteUserWarnings(Integer userId) {
		UserWarningDboExample subjectWarnings = new UserWarningDboExample();
		subjectWarnings.createCriteria().andUserIdEqualTo(userId);
		userWarningDao.deleteWhere(subjectWarnings);
	}

	public void deleteUserRow(Integer userId) {
		userDao.delete(userId);
	}

	public void scrubIssuedWarnings(Integer userId) {
		userWarningDao.scrubIssuedWarnings(userId);
	}

	public void deleteMigratorIdMapEntries(String entityType, List<Integer> zfgbbIds) {
		MigratorIdMapDboExample example = new MigratorIdMapDboExample();
		example.createCriteria().andEntityTypeEqualTo(entityType).andZfgbbIdIn(zfgbbIds);
		migratorIdMapDao.deleteWhere(example);
	}

	public void reassignContentResources(Integer userId, Integer sentinelId) {
		contentResourceDao.reassignContentResources(userId, sentinelId);
	}

	public List<Integer> findOwnedUnreferencedContentResourceIds(Integer userId, int chunkSize) {
		return contentResourceDao.findOwnedUnreferencedContentResourceIds(userId, chunkSize);
	}

	public void recordDeletionRequestedAudit(Integer userId, String mode, OffsetDateTime requestedTs) {
		AccountDeletionAuditDbo audit = findOrCreateOpenAuditRow(userId, mode, requestedTs);
		audit.setMode(mode);
		audit.setRequestedTs(requestedTs);
		accountDeletionAuditDao.save(audit);
	}

	public void stampAuditConfirmed(Integer userId, String mode, OffsetDateTime requestedTs, OffsetDateTime now) {
		AccountDeletionAuditDbo audit = findOrCreateOpenAuditRow(userId, mode,
				requestedTs != null ? requestedTs : now);
		if (audit.getConfirmedTs() != null)
			return;
		audit.setConfirmedTs(now);
		accountDeletionAuditDao.save(audit);
	}

	public void stampAuditExecuted(Integer userId, OffsetDateTime now) {
		AccountDeletionAuditDboExample ex = new AccountDeletionAuditDboExample();
		ex.createCriteria().andSubjectUserIdSnapshotEqualTo(userId).andExecutedTsIsNull();
		ex.setOrderByClause("deletion_id desc");
		accountDeletionAuditDao.getOne(ex).ifPresent(audit -> {
			audit.setExecutedTs(now);
			accountDeletionAuditDao.save(audit);
		});
	}

	private AccountDeletionAuditDbo findOrCreateOpenAuditRow(Integer userId, String mode, OffsetDateTime timestamp) {
		AccountDeletionAuditDboExample ex = new AccountDeletionAuditDboExample();
		ex.createCriteria().andSubjectUserIdSnapshotEqualTo(userId).andExecutedTsIsNull();
		ex.setOrderByClause("deletion_id desc");
		Optional<AccountDeletionAuditDbo> existing = accountDeletionAuditDao.getOne(ex);
		if (existing.isPresent())
			return existing.get();
		AccountDeletionAuditDbo audit = new AccountDeletionAuditDbo();
		audit.setSubjectUserIdSnapshot(userId);
		audit.setSubjectPseudonym(UUID.randomUUID().toString().replace("-", ""));
		audit.setMode(mode);
		audit.setInitiatedBy("SELF");
		audit.setRequestedTs(timestamp);
		audit.setMessageCount(messageDao.countOwnedMessages(userId));
		audit.setContentResourceCount(countOwnedContentResources(userId));
		audit.setCreatedTs(timestamp);
		accountDeletionAuditDao.insertSelective(audit);
		return audit;
	}

	public int countOwnedMessages(Integer userId) {
		return messageDao.countOwnedMessages(userId);
	}

	public int countOwnedContentResources(Integer userId) {
		ContentResourceDboExample ownedResourcesExample = new ContentResourceDboExample();
		ownedResourcesExample.createCriteria().andUploadedUserIdEqualTo(userId);
		return (int) contentResourceDao.count(ownedResourcesExample);
	}
}
