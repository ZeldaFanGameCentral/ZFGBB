package com.zfgc.zfgbb.dao.cms;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.ContentEntityDbo;
import com.zfgc.zfgbb.dbo.ContentEntityDboExample;
import com.zfgc.zfgbb.mappers.ContentEntityDboMapper;

@Repository
public class ContentEntityDao extends IdentityDao<ContentEntityDbo, ContentEntityDboExample> {

	public ContentEntityDao(ContentEntityDboMapper mapper) {
		super(mapper);
	}
}
