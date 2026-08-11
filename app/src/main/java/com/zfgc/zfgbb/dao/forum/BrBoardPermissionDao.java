package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.BrBoardPermissionDbo;
import com.zfgc.zfgbb.dbo.BrBoardPermissionDboExample;
import com.zfgc.zfgbb.mappers.BrBoardPermissionDboMapper;

@Repository
public class BrBoardPermissionDao extends KeyedDao<BrBoardPermissionDbo, BrBoardPermissionDboExample, Integer> {

	public BrBoardPermissionDao(BrBoardPermissionDboMapper mapper) {
		super(mapper);
	}
}
