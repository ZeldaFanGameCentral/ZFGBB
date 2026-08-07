package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.ReadDao;
import com.zfgc.zfgbb.dbo.BoardSummaryViewDbo;
import com.zfgc.zfgbb.dbo.BoardSummaryViewDboExample;
import com.zfgc.zfgbb.mappers.BoardSummaryViewDboMapper;

@Repository
public class BoardSummaryViewDao extends ReadDao<BoardSummaryViewDbo, BoardSummaryViewDboExample> {

	public BoardSummaryViewDao(BoardSummaryViewDboMapper mapper) {
		super(mapper);
	}
}
