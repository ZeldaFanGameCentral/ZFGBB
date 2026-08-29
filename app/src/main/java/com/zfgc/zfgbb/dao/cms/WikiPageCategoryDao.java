package com.zfgc.zfgbb.dao.cms;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.WikiPageCategoryDbo;
import com.zfgc.zfgbb.dbo.WikiPageCategoryDboExample;
import com.zfgc.zfgbb.mappers.WikiPageCategoryDboMapper;

@Repository
public class WikiPageCategoryDao extends KeyedDao<WikiPageCategoryDbo, WikiPageCategoryDboExample, Integer> {

	public WikiPageCategoryDao(WikiPageCategoryDboMapper mapper) {
		super(mapper);
	}
}
