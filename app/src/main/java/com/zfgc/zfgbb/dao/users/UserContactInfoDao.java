package com.zfgc.zfgbb.dao.users;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.UserContactInfoDbo;
import com.zfgc.zfgbb.dbo.UserContactInfoDboExample;
import com.zfgc.zfgbb.mappers.UserContactInfoDboMapper;

@Repository
public class UserContactInfoDao extends KeyedDao<UserContactInfoDbo, UserContactInfoDboExample, Integer> {

	private final UserContactTypesDao userContactTypesDao;

	public UserContactInfoDao(UserContactInfoDboMapper mapper,
			UserContactTypesDao userContactTypesDao) {
		super(mapper);
		this.userContactTypesDao = userContactTypesDao;
	}

	public int deleteUserContactTypes(Integer userId) {
		return userContactTypesDao.deleteForUser(userId);
	}

	public List<Integer> findEmailAddressIds(Integer userId) {
		UserContactInfoDboExample byUser = new UserContactInfoDboExample();
		byUser.createCriteria().andUserIdEqualTo(userId);
		return get(byUser).stream().map(UserContactInfoDbo::getEmailAddressId).toList();
	}
}
