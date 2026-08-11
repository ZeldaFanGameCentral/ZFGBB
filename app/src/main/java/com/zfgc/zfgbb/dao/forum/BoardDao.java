package com.zfgc.zfgbb.dao.forum;

import java.util.List;

import com.zfgc.zfgbb.dao.IdentityDao;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dbo.BoardDbo;
import com.zfgc.zfgbb.dbo.BoardDboExample;
import com.zfgc.zfgbb.mappers.BoardDboMapper;
import com.zfgc.zfgbb.mappers.custom.ForumLockMapper;

@Repository
public class BoardDao extends IdentityDao<BoardDbo, BoardDboExample> {

	private final ForumLockMapper forumLockMapper;

	public BoardDao(BoardDboMapper mapper, ForumLockMapper forumLockMapper) {
		super(mapper);
		this.forumLockMapper = forumLockMapper;
	}

	public List<Integer> lockForUpdate(List<Integer> boardIds) {
		return forumLockMapper.lockBoards(boardIds);
	}
}
