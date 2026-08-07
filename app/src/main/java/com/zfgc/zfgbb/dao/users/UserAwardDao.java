package com.zfgc.zfgbb.dao.users;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.UserAwardDbo;
import com.zfgc.zfgbb.dbo.UserAwardDboExample;
import com.zfgc.zfgbb.mappers.UserAwardDboMapper;

@Repository
public class UserAwardDao extends IdentityDao<UserAwardDbo, UserAwardDboExample> {

	public UserAwardDao(UserAwardDboMapper mapper) {
		super(mapper);
	}
}
