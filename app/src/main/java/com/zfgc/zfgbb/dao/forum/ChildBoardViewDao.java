package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.ReadDao;
import com.zfgc.zfgbb.dbo.ChildBoardViewDbo;
import com.zfgc.zfgbb.dbo.ChildBoardViewDboExample;
import com.zfgc.zfgbb.mappers.ChildBoardViewDboMapper;

@Repository
public class ChildBoardViewDao extends ReadDao<ChildBoardViewDbo, ChildBoardViewDboExample> {

	public ChildBoardViewDao(ChildBoardViewDboMapper mapper) {
		super(mapper);
	}
}
