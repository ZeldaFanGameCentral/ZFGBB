package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDboExample;
import com.zfgc.zfgbb.mappers.BBCodeAttributeModeDboMapper;

@Repository
public class BBCodeAttributeModeDao extends IdentityDao<BBCodeAttributeModeDbo, BBCodeAttributeModeDboExample> {

	public BBCodeAttributeModeDao(BBCodeAttributeModeDboMapper mapper) {
		super(mapper);
	}
}
