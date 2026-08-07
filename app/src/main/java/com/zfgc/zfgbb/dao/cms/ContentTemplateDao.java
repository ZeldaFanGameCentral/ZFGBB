package com.zfgc.zfgbb.dao.cms;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.ContentTemplateDbo;
import com.zfgc.zfgbb.dbo.ContentTemplateDboExample;
import com.zfgc.zfgbb.mappers.ContentTemplateDboMapper;

@Repository
public class ContentTemplateDao extends IdentityDao<ContentTemplateDbo, ContentTemplateDboExample> {

	public ContentTemplateDao(ContentTemplateDboMapper mapper) {
		super(mapper);
	}
}
