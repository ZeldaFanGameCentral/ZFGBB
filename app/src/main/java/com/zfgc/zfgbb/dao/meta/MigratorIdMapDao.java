package com.zfgc.zfgbb.dao.meta;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.MigratorIdMapDbo;
import com.zfgc.zfgbb.dbo.MigratorIdMapDboExample;
import com.zfgc.zfgbb.mappers.MigratorIdMapDboMapper;

@Repository
public class MigratorIdMapDao extends KeyedDao<MigratorIdMapDbo, MigratorIdMapDboExample, Long> {

	public MigratorIdMapDao(MigratorIdMapDboMapper mapper) {
		super(mapper);
	}
}
