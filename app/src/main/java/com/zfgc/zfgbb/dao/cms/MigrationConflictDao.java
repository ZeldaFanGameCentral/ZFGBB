package com.zfgc.zfgbb.dao.cms;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.MigrationConflictDbo;
import com.zfgc.zfgbb.dbo.MigrationConflictDboExample;
import com.zfgc.zfgbb.mappers.MigrationConflictDboMapper;

@Repository
public class MigrationConflictDao extends KeyedDao<MigrationConflictDbo, MigrationConflictDboExample, Integer> {

	public MigrationConflictDao(MigrationConflictDboMapper mapper) {
		super(mapper);
	}
}
