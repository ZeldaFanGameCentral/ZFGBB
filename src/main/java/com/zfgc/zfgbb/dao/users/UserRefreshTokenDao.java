package com.zfgc.zfgbb.dao.users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.AbstractDao;
import com.zfgc.zfgbb.dbo.UserRefreshTokenDbo;
import com.zfgc.zfgbb.dbo.UserRefreshTokenDboExample;
import com.zfgc.zfgbb.mappers.UserRefreshTokenDboMapper;

@Repository
public class UserRefreshTokenDao extends AbstractDao<UserRefreshTokenDboExample, UserRefreshTokenDboMapper, UserRefreshTokenDbo> {

	@Autowired
	public UserRefreshTokenDao(UserRefreshTokenDboMapper mapper) {
		super(mapper);
	}

	@Override
	public Optional<UserRefreshTokenDbo> get(Integer id) {
		return Optional.ofNullable(mapper.selectByPrimaryKey(id));
	}

	@Override
	public List<UserRefreshTokenDbo> get(UserRefreshTokenDboExample ex) {
		return mapper.selectByExample(ex);
	}

	@Override
	protected void update(UserRefreshTokenDbo toSave) {
		mapper.updateByPrimaryKey(toSave);
	}

	@Override
	protected void create(UserRefreshTokenDbo toCreate) {
		mapper.insert(toCreate);
	}
}
