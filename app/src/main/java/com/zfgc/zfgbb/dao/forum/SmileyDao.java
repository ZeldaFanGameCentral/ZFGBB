package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.SmileyDbo;
import com.zfgc.zfgbb.dbo.SmileyDboExample;
import com.zfgc.zfgbb.mappers.SmileyDboMapper;

@Repository
public class SmileyDao extends IdentityDao<SmileyDbo, SmileyDboExample> {

	public SmileyDao(SmileyDboMapper mapper) {
		super(mapper);
	}
}
