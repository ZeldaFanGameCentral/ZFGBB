package com.zfgc.zfgbb.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface MigratorIdMapMapper {

	@Insert("""
			insert into zfgbb.migrator_id_map (entity_type, legacy_id, zfgbb_id)
			values (#{entityType}, #{legacyId}, #{zfgbbId})
			on conflict (entity_type, legacy_id) do update set zfgbb_id = excluded.zfgbb_id
			""")
	int upsert(@Param("entityType") String entityType, @Param("legacyId") Integer legacyId,
			@Param("zfgbbId") Integer zfgbbId);

	@Update("""
			update zfgbb.migrator_id_map set zfgbb_id = #{targetId}
			where zfgbb_id = #{sourceId} and entity_type in ('PROJECT', 'GAME')
			""")
	int repointMigratorIdMap(@Param("targetId") Integer targetId, @Param("sourceId") Integer sourceId);
}
