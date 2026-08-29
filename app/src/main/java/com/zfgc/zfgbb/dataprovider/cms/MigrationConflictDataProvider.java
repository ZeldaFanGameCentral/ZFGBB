package com.zfgc.zfgbb.dataprovider.cms;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.cms.MigrationConflictDao;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MigrationConflictDataProvider {

	private final MigrationConflictDao migrationConflictDao;

	public void nullConflictResolvers(Integer userId) {
		migrationConflictDao.nullMigrationConflictResolvers(userId);
	}
}
