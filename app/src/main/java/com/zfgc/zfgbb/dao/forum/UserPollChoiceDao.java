package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.UserPollChoiceDbo;
import com.zfgc.zfgbb.dbo.UserPollChoiceDboExample;
import com.zfgc.zfgbb.mappers.UserPollChoiceDboMapper;

@Repository
public class UserPollChoiceDao extends IdentityDao<UserPollChoiceDbo, UserPollChoiceDboExample> {

	public UserPollChoiceDao(UserPollChoiceDboMapper mapper) {
		super(mapper);
	}
}
