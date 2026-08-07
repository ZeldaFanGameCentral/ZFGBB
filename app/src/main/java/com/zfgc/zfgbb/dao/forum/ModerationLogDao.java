package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.ModerationLogDbo;
import com.zfgc.zfgbb.dbo.ModerationLogDboExample;
import com.zfgc.zfgbb.mappers.ModerationLogDboMapper;

@Repository
public class ModerationLogDao extends IdentityDao<ModerationLogDbo, ModerationLogDboExample> {

	public ModerationLogDao(ModerationLogDboMapper mapper) {
		super(mapper);
	}
}
