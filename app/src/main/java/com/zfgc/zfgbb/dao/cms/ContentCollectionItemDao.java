package com.zfgc.zfgbb.dao.cms;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.ContentCollectionItemDbo;
import com.zfgc.zfgbb.dbo.ContentCollectionItemDboExample;
import com.zfgc.zfgbb.mappers.ContentCollectionItemDboMapper;

@Repository
public class ContentCollectionItemDao extends IdentityDao<ContentCollectionItemDbo, ContentCollectionItemDboExample> {

	public ContentCollectionItemDao(ContentCollectionItemDboMapper mapper) {
		super(mapper);
	}
}
