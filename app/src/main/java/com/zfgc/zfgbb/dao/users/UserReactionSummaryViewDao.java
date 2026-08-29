package com.zfgc.zfgbb.dao.users;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.ReadDao;
import com.zfgc.zfgbb.dbo.UserReactionSummaryViewDbo;
import com.zfgc.zfgbb.dbo.UserReactionSummaryViewDboExample;
import com.zfgc.zfgbb.mappers.UserReactionSummaryViewDboMapper;

@Repository
public class UserReactionSummaryViewDao extends ReadDao<UserReactionSummaryViewDbo, UserReactionSummaryViewDboExample> {

	public UserReactionSummaryViewDao(UserReactionSummaryViewDboMapper mapper) {
		super(mapper);
	}
}
