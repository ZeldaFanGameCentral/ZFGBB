package com.zfgc.zfgbb.dao.users;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.AvatarDbo;
import com.zfgc.zfgbb.dbo.AvatarDboExample;
import com.zfgc.zfgbb.mappers.AvatarDboMapper;
import com.zfgc.zfgbb.mappers.custom.UserProfileMapper;

@Repository
public class AvatarDao extends IdentityDao<AvatarDbo, AvatarDboExample> {

	private final UserProfileMapper userProfileMapper;

	public AvatarDao(AvatarDboMapper mapper, UserProfileMapper userProfileMapper) {
		super(mapper);
		this.userProfileMapper = userProfileMapper;
	}

	public boolean isAvailableTo(Integer avatarId, Integer userId) {
		return userProfileMapper.isAvatarAvailable(avatarId, userId);
	}
}
