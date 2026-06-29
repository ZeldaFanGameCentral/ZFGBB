package com.zfgc.zfgbb.migrator.jobs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.dbo.MigratorIdMapDbo;
import com.zfgc.zfgbb.dbo.MigratorIdMapDboExample;
import com.zfgc.zfgbb.mappers.MigratorIdMapDboMapper;
import com.zfgc.zfgbb.mappers.MigratorIdMapMapper;

@Service
public class MigratorIdMapService {

	@Autowired
	private MigratorIdMapDboMapper migratorIdMapDboMapper;

	@Autowired
	private MigratorIdMapMapper migratorIdMapMapper;

	public void record(LegacyEntityType type, Integer legacyId, Integer zfgbbId) {
		migratorIdMapMapper.upsert(type.name(), legacyId, zfgbbId);
	}

	public Integer lookup(LegacyEntityType type, Integer legacyId) {
		Integer result = lookupOrNull(type, legacyId);
		if (result == null) {
			throw new IllegalStateException(
					"No id mapping for " + type + " legacy_id=" + legacyId
							+ " — converter ordering may be wrong, or the prior converter failed");
		}
		return result;
	}

	public Integer lookupOrNull(LegacyEntityType type, Integer legacyId) {
		if (legacyId == null) {
			return null;
		}
		MigratorIdMapDboExample example = new MigratorIdMapDboExample();
		example.createCriteria().andEntityTypeEqualTo(type.name()).andLegacyIdEqualTo(legacyId);
		List<MigratorIdMapDbo> rows = migratorIdMapDboMapper.selectByExample(example);
		return rows.isEmpty() ? null : rows.get(0).getZfgbbId();
	}

	public Map<Integer, Integer> getAllForType(LegacyEntityType type) {
		MigratorIdMapDboExample example = new MigratorIdMapDboExample();
		example.createCriteria().andEntityTypeEqualTo(type.name());
		Map<Integer, Integer> result = new HashMap<>();
		for (MigratorIdMapDbo row : migratorIdMapDboMapper.selectByExample(example))
			result.put(row.getLegacyId(), row.getZfgbbId());
		return result;
	}
}
