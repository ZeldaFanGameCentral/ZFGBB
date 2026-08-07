package com.zfgc.zfgbb.dao.cms;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.TagDbo;
import com.zfgc.zfgbb.dbo.TagDboExample;
import com.zfgc.zfgbb.mappers.TagDboMapper;

@Repository
public class TagDao extends KeyedDao<TagDbo, TagDboExample, Integer> {

	public TagDao(TagDboMapper mapper) {
		super(mapper);
	}
}
