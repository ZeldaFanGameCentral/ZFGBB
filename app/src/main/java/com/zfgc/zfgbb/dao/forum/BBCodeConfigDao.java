package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.BBCodeConfigDbo;
import com.zfgc.zfgbb.dbo.BBCodeConfigDboExample;
import com.zfgc.zfgbb.mappers.BBCodeConfigDboMapper;

@Repository
public class BBCodeConfigDao extends IdentityDao<BBCodeConfigDbo, BBCodeConfigDboExample> {

	public BBCodeConfigDao(BBCodeConfigDboMapper mapper) {
		super(mapper);
	}
}
