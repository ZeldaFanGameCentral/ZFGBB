package com.zfgc.zfgbb.dao.cms;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDbo;
import com.zfgc.zfgbb.dbo.WikiPageRevisionDboExample;
import com.zfgc.zfgbb.mappers.WikiPageRevisionDboMapper;

@Repository
public class WikiPageRevisionDao extends IdentityDao<WikiPageRevisionDbo, WikiPageRevisionDboExample> {

	public WikiPageRevisionDao(WikiPageRevisionDboMapper mapper) {
		super(mapper);
	}
}
