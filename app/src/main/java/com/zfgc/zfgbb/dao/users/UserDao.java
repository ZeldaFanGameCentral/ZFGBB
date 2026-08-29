package com.zfgc.zfgbb.dao.users;

import java.util.Set;
import java.util.List;
import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.mappers.UserDboMapper;

@Repository
public class UserDao extends IdentityDao<UserDbo, UserDboExample> {

	private static final String DELETED_NAME = "[deleted]";

	public UserDao(UserDboMapper mapper) {
		super(mapper);
	}

	public int recordFailedLoginAttempt(Integer userId, OffsetDateTime now, int lockThreshold,
			OffsetDateTime lockUntil) {
		Optional<UserDbo> lockedRow = lockForUpdate(userId);
		if (lockedRow.isEmpty())
			return 0;
		UserDbo row = lockedRow.get();
		boolean lockLapsed = row.getLockedUntilTs() != null && !row.getLockedUntilTs().isAfter(now);
		int failedLoginCount = lockLapsed ? 1
				: (row.getFailedLoginCount() == null ? 0 : row.getFailedLoginCount()) + 1;
		row.setFailedLoginCount(failedLoginCount);
		if (failedLoginCount >= lockThreshold)
			row.setLockedUntilTs(lockUntil);
		else if (lockLapsed)
			row.setLockedUntilTs(null);
		save(row);
		return 1;
	}

	public int clearFailedLoginState(Integer userId, OffsetDateTime now) {
		Optional<UserDbo> lockedRow = lockForUpdate(userId);
		if (lockedRow.isEmpty())
			return 0;
		UserDbo row = lockedRow.get();
		boolean dirty = (row.getFailedLoginCount() != null && row.getFailedLoginCount() > 0)
				|| row.getLockedUntilTs() != null;
		if (!dirty)
			return 0;
		row.setFailedLoginCount(0);
		row.setLockedUntilTs(null);
		save(row);
		return 1;
	}

	public int updateDisplayName(String displayName, Integer userId) {
		UserDbo row = new UserDbo();
		row.setDisplayName(displayName);
		UserDboExample example = new UserDboExample();
		example.createCriteria().andUserIdEqualTo(userId);
		return updateWhere(row, example);
	}

	public Optional<Integer> findUserIdBySsoKey(String ssoKey) {
		UserDboExample bySsoKey = new UserDboExample();
		bySsoKey.createCriteria().andSsoKeyEqualTo(ssoKey);
		return getOne(bySsoKey).map(UserDbo::getUserId);
	}

	public Optional<String> findUserName(Integer userId) {
		UserDboExample byId = new UserDboExample();
		byId.createCriteria().andUserIdEqualTo(userId);
		return getOne(byId).map(UserDbo::getUserName);
	}

	public int neutralizeUserRow(Integer userId, String ssoKeyToken) {
		UserDbo neutralized = new UserDbo();
		neutralized.setDisplayName(DELETED_NAME);
		neutralized.setUserName(DELETED_NAME);
		neutralized.setSsoKey(ssoKeyToken);
		neutralized.setFailedLoginCount(0);
		neutralized.setActiveFlag(false);
		neutralized.setTokensValidAfterTs(OffsetDateTime.now());
		UserDboExample byId = new UserDboExample();
		byId.createCriteria().andUserIdEqualTo(userId);
		return updateWhereSettingColumns(neutralized, Set.of("display_name", "user_name", "sso_key",
				"migration_hash", "password_hash", "password_algo", "password_salt", "password_changed_ts",
				"locked_until_ts", "failed_login_count", "active_flag", "tokens_valid_after_ts"), byId);
	}
}
