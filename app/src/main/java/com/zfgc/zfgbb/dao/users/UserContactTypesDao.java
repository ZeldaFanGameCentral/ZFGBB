package com.zfgc.zfgbb.dao.users;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.UserContactTypesDbo;
import com.zfgc.zfgbb.dbo.UserContactTypesDboExample;
import com.zfgc.zfgbb.mappers.UserContactTypesDboMapper;

@Repository
public class UserContactTypesDao extends IdentityDao<UserContactTypesDbo, UserContactTypesDboExample> {

	public UserContactTypesDao(UserContactTypesDboMapper mapper) {
		super(mapper);
	}

	public int deleteForUser(Integer userId) {
		UserContactTypesDboExample byUser = new UserContactTypesDboExample();
		byUser.createCriteria().andUserIdEqualTo(userId);
		return deleteWhere(byUser);
	}
}
