package com.zfgc.zfgbb.dao.cms;

import java.util.Set;
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

	public int nullMigrationConflictResolvers(Integer userId) {
		MigrationConflictDbo orphaned = new MigrationConflictDbo();
		MigrationConflictDboExample resolvedByUser = new MigrationConflictDboExample();
		resolvedByUser.createCriteria().andResolvedByUserIdEqualTo(userId);
		return updateWhereSettingColumns(orphaned, Set.of("resolved_by_user_id"), resolvedByUser);
	}
}
