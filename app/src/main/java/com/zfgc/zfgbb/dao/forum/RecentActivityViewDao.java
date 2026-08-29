package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.ReadDao;
import com.zfgc.zfgbb.dbo.RecentActivityViewDbo;
import com.zfgc.zfgbb.dbo.RecentActivityViewDboExample;
import com.zfgc.zfgbb.mappers.RecentActivityViewDboMapper;

@Repository
public class RecentActivityViewDao extends ReadDao<RecentActivityViewDbo, RecentActivityViewDboExample> {

	public RecentActivityViewDao(RecentActivityViewDboMapper mapper) {
		super(mapper);
	}
}
