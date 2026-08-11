package com.zfgc.zfgbb.dao.users;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.UserAggregateDbo;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.mappers.UserDboMapper;
import com.zfgc.zfgbb.mappers.custom.LoginLockoutMapper;
import com.zfgc.zfgbb.mappers.custom.UserProfileHydrationMapper;
import com.zfgc.zfgbb.mappers.custom.UserProfileMapper;

@Repository
public class UserDao extends IdentityDao<UserDbo, UserDboExample> {

	private final UserProfileHydrationMapper hydrationMapper;

	private final LoginLockoutMapper loginLockoutMapper;

	private final UserProfileMapper userProfileMapper;

	public UserDao(UserDboMapper mapper, UserProfileHydrationMapper hydrationMapper,
			LoginLockoutMapper loginLockoutMapper, UserProfileMapper userProfileMapper) {
		super(mapper);
		this.hydrationMapper = hydrationMapper;
		this.loginLockoutMapper = loginLockoutMapper;
		this.userProfileMapper = userProfileMapper;
	}

	public List<UserAggregateDbo> hydrate(List<Integer> userIds) {
		return hydrationMapper.hydrateUsers(userIds);
	}

	public int recordFailedLoginAttempt(Integer userId, OffsetDateTime now, int lockThreshold,
			OffsetDateTime lockUntil) {
		return loginLockoutMapper.recordFailedLoginAttempt(userId, now, lockThreshold, lockUntil);
	}

	public int clearFailedLoginState(Integer userId, OffsetDateTime now) {
		return loginLockoutMapper.clearFailedLoginState(userId, now);
	}

	public Optional<Integer> lockActiveUserId(Integer userId) {
		return Optional.ofNullable(userProfileMapper.lockActiveUserId(userId));
	}

	public int updateDisplayName(String displayName, Integer userId) {
		return userProfileMapper.updateDisplayName(displayName, userId);
	}
}
