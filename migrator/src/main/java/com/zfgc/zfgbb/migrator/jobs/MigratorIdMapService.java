package com.zfgc.zfgbb.migrator.jobs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.dbo.MigratorIdMapDbo;
import com.zfgc.zfgbb.dbo.MigratorIdMapDboExample;
import com.zfgc.zfgbb.mappers.MigratorIdMapDboMapper;
import com.zfgc.zfgbb.mappers.MigratorIdMapMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MigratorIdMapService {

	private final MigratorIdMapDboMapper migratorIdMapDboMapper;

	private final MigratorIdMapMapper migratorIdMapMapper;

	public void record(LegacyEntityType type, Integer legacyId, Integer zfgbbId) {
		migratorIdMapMapper.upsert(type.name(), legacyId, zfgbbId);
	}

	public Integer lookup(LegacyEntityType type, Integer legacyId) {
		return find(type, legacyId).orElseThrow(() -> new IllegalStateException(
				"No id mapping for " + type + " legacy_id=" + legacyId
						+ " — converter ordering may be wrong, or the prior converter failed"));
	}

	public Integer lookupOrNull(LegacyEntityType type, Integer legacyId) {
		return find(type, legacyId).orElse(null);
	}

	public Optional<Integer> find(LegacyEntityType type, Integer legacyId) {
		if (legacyId == null)
			return Optional.empty();
		MigratorIdMapDboExample example = new MigratorIdMapDboExample();
		example.createCriteria().andEntityTypeEqualTo(type.name()).andLegacyIdEqualTo(legacyId);
		return migratorIdMapDboMapper.selectByExample(example).stream()
				.findFirst()
				.map(MigratorIdMapDbo::getZfgbbId);
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
