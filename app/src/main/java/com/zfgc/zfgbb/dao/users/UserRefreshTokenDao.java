package com.zfgc.zfgbb.dao.users;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.UserRefreshTokenDbo;
import com.zfgc.zfgbb.dbo.UserRefreshTokenDboExample;
import com.zfgc.zfgbb.mappers.UserRefreshTokenDboMapper;
import com.zfgc.zfgbb.mappers.custom.RefreshTokenConsumeMapper;

@Repository
public class UserRefreshTokenDao extends IdentityDao<UserRefreshTokenDbo, UserRefreshTokenDboExample> {

	private final RefreshTokenConsumeMapper refreshTokenConsumeMapper;

	public UserRefreshTokenDao(UserRefreshTokenDboMapper mapper,
			RefreshTokenConsumeMapper refreshTokenConsumeMapper) {
		super(mapper);
		this.refreshTokenConsumeMapper = refreshTokenConsumeMapper;
	}

	public int consume(Integer userRefreshTokenId, OffsetDateTime now) {
		return refreshTokenConsumeMapper.consumeToken(userRefreshTokenId, now);
	}
}
