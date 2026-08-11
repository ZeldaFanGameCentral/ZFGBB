package com.zfgc.zfgbb.dao.meta;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.MigratorAttachmentRefRewriteDbo;
import com.zfgc.zfgbb.dbo.MigratorAttachmentRefRewriteDboExample;
import com.zfgc.zfgbb.mappers.MigratorAttachmentRefRewriteDboMapper;

@Repository
public class MigratorAttachmentRefRewriteDao
		extends KeyedDao<MigratorAttachmentRefRewriteDbo, MigratorAttachmentRefRewriteDboExample, Integer> {

	public MigratorAttachmentRefRewriteDao(MigratorAttachmentRefRewriteDboMapper mapper) {
		super(mapper);
	}

	public int deleteForMessageHistories(List<Integer> messageHistoryIds) {
		MigratorAttachmentRefRewriteDboExample byHistories = new MigratorAttachmentRefRewriteDboExample();
		byHistories.createCriteria().andMessageHistoryIdIn(messageHistoryIds);
		return deleteWhere(byHistories);
	}
}
