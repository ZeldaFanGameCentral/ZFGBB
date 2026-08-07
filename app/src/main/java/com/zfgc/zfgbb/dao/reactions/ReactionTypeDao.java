package com.zfgc.zfgbb.dao.reactions;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.ReactionTypeDbo;
import com.zfgc.zfgbb.dbo.ReactionTypeDboExample;
import com.zfgc.zfgbb.mappers.ReactionTypeDboMapper;

@Repository
public class ReactionTypeDao extends KeyedDao<ReactionTypeDbo, ReactionTypeDboExample, Integer> {

	public ReactionTypeDao(ReactionTypeDboMapper mapper) {
		super(mapper);
	}
}
