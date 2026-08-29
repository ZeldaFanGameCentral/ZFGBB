package com.zfgc.zfgbb.dao.cms;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.ReadDao;
import com.zfgc.zfgbb.dbo.WikiRevisionRefDbo;
import com.zfgc.zfgbb.dbo.WikiRevisionRefDboExample;
import com.zfgc.zfgbb.mappers.WikiRevisionRefDboMapper;

@Repository
public class WikiRevisionRefDao extends ReadDao<WikiRevisionRefDbo, WikiRevisionRefDboExample> {

	public WikiRevisionRefDao(WikiRevisionRefDboMapper mapper) {
		super(mapper);
	}
}
