package com.zfgc.zfgbb.dao.cms;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.ContentCollectionDbo;
import com.zfgc.zfgbb.dbo.ContentCollectionDboExample;
import com.zfgc.zfgbb.mappers.ContentCollectionDboMapper;

@Repository
public class ContentCollectionDao extends IdentityDao<ContentCollectionDbo, ContentCollectionDboExample> {

	public ContentCollectionDao(ContentCollectionDboMapper mapper) {
		super(mapper);
	}
}
