package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.ListStyleTypeDbo;
import com.zfgc.zfgbb.dbo.ListStyleTypeDboExample;
import com.zfgc.zfgbb.mappers.ListStyleTypeDboMapper;

@Repository
public class ListStyleTypeDao extends KeyedDao<ListStyleTypeDbo, ListStyleTypeDboExample, String> {

	public ListStyleTypeDao(ListStyleTypeDboMapper mapper) {
		super(mapper);
	}
}
