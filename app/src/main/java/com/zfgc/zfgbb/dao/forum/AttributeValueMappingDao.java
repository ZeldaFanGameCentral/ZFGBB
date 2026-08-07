package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.AttributeValueMappingDbo;
import com.zfgc.zfgbb.dbo.AttributeValueMappingDboExample;
import com.zfgc.zfgbb.mappers.AttributeValueMappingDboMapper;

@Repository
public class AttributeValueMappingDao extends KeyedDao<AttributeValueMappingDbo, AttributeValueMappingDboExample, Integer> {

	public AttributeValueMappingDao(AttributeValueMappingDboMapper mapper) {
		super(mapper);
	}
}
