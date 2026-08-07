package com.zfgc.zfgbb.dao.forum;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.FileAttachmentDbo;
import com.zfgc.zfgbb.dbo.FileAttachmentDboExample;
import com.zfgc.zfgbb.mappers.FileAttachmentDboMapper;

@Repository
public class FileAttachmentDao extends IdentityDao<FileAttachmentDbo, FileAttachmentDboExample> {

	public FileAttachmentDao(FileAttachmentDboMapper mapper) {
		super(mapper);
	}
}
