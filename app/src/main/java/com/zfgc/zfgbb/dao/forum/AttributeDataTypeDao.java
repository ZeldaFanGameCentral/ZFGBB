package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.AttributeDataTypeDbo;
import com.zfgc.zfgbb.dbo.AttributeDataTypeDboExample;
import com.zfgc.zfgbb.mappers.AttributeDataTypeDboMapper;

@Repository
public class AttributeDataTypeDao extends KeyedDao<AttributeDataTypeDbo, AttributeDataTypeDboExample, String> {

	public AttributeDataTypeDao(AttributeDataTypeDboMapper mapper) {
		super(mapper);
	}
}
