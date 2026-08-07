package com.zfgc.zfgbb.dao.users;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.AwardDbo;
import com.zfgc.zfgbb.dbo.AwardDboExample;
import com.zfgc.zfgbb.mappers.AwardDboMapper;

@Repository
public class AwardDao extends IdentityDao<AwardDbo, AwardDboExample> {

	public AwardDao(AwardDboMapper mapper) {
		super(mapper);
	}
}
