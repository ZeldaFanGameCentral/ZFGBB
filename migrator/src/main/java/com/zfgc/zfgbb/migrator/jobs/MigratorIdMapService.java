package com.zfgc.zfgbb.migrator.jobs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class MigratorIdMapService {

	private final JdbcTemplate jdbc;

	public MigratorIdMapService(@Qualifier("dataSource") DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}

	public void record(LegacyEntityType type, Integer legacyId, Integer zfgbbId) {
		jdbc.update(
				"insert into zfgbb.migrator_id_map (entity_type, legacy_id, zfgbb_id) values (?, ?, ?) "
						+ "on conflict (entity_type, legacy_id) do update set zfgbb_id = excluded.zfgbb_id",
				type.name(), legacyId, zfgbbId);
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
		List<Integer> rows = jdbc.queryForList(
				"select zfgbb_id from zfgbb.migrator_id_map where entity_type = ? and legacy_id = ?",
				Integer.class, type.name(), legacyId);
		return rows.isEmpty() ? null : rows.get(0);
	}

	public Map<Integer, Integer> getAllForType(LegacyEntityType type) {
		Map<Integer, Integer> result = new HashMap<>();
		jdbc.query(
				"select legacy_id, zfgbb_id from zfgbb.migrator_id_map where entity_type = ?",
				rs -> { result.put(rs.getInt(1), rs.getInt(2)); },
				type.name());
		return result;
	}
}
