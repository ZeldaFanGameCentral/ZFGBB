package com.zfgc.zfgbb.dao.meta;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.MigratorIdMapDbo;
import com.zfgc.zfgbb.dbo.MigratorIdMapDboExample;
import com.zfgc.zfgbb.mappers.MigratorIdMapDboMapper;
import com.zfgc.zfgbb.mappers.MigratorIdMapMapper;

@Repository
public class MigratorIdMapDao extends KeyedDao<MigratorIdMapDbo, MigratorIdMapDboExample, Long> {

	private final MigratorIdMapMapper migratorIdMapMapper;

	public MigratorIdMapDao(MigratorIdMapDboMapper mapper, MigratorIdMapMapper migratorIdMapMapper) {
		super(mapper);
		this.migratorIdMapMapper = migratorIdMapMapper;
	}

	public int repoint(Integer targetId, Integer sourceId) {
		return migratorIdMapMapper.repointMigratorIdMap(targetId, sourceId);
	}
}
