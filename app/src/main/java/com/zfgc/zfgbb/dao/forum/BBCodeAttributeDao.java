package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.BBCodeAttributeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeDboExample;
import com.zfgc.zfgbb.mappers.BBCodeAttributeDboMapper;

@Repository
public class BBCodeAttributeDao extends IdentityDao<BBCodeAttributeDbo, BBCodeAttributeDboExample> {

	public BBCodeAttributeDao(BBCodeAttributeDboMapper mapper) {
		super(mapper);
	}
}
