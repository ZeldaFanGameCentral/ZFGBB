package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.ReadDao;
import com.zfgc.zfgbb.dbo.AttachmentBoardViewDbo;
import com.zfgc.zfgbb.dbo.AttachmentBoardViewDboExample;
import com.zfgc.zfgbb.mappers.AttachmentBoardViewDboMapper;

@Repository
public class AttachmentBoardViewDao extends ReadDao<AttachmentBoardViewDbo, AttachmentBoardViewDboExample> {

	public AttachmentBoardViewDao(AttachmentBoardViewDboMapper mapper) {
		super(mapper);
	}
}
