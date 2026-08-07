package com.zfgc.zfgbb.dao.cms;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.WikiPageDbo;
import com.zfgc.zfgbb.dbo.WikiPageDboExample;
import com.zfgc.zfgbb.mappers.WikiPageDboMapper;

@Repository
public class WikiPageDao extends IdentityDao<WikiPageDbo, WikiPageDboExample> {

	public WikiPageDao(WikiPageDboMapper mapper) {
		super(mapper);
	}
}
