package com.zfgc.zfgbb.mappers.custom;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface ReactionUpsertMapper {

	@Insert("""
			insert into zfgbb.reaction (reactable_type, reactable_id, reactor_user_id, reaction_type_id)
			values (#{reactableType}, #{reactableId}, #{reactorUserId}, #{reactionTypeId})
			on conflict (reactable_type, reactable_id, reactor_user_id)
			do update set reaction_type_id = excluded.reaction_type_id, updated_ts = current_timestamp
			""")
	int upsertReaction(@Param("reactableType") String reactableType,
			@Param("reactableId") Integer reactableId,
			@Param("reactorUserId") Integer reactorUserId,
			@Param("reactionTypeId") Integer reactionTypeId);
}
