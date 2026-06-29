package com.zfgc.zfgbb.mappers.custom;

import java.time.OffsetDateTime;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface RefreshTokenFamilyMapper {

	@Update("update zfgbb.user_refresh_token set successor_id = #{successorId}, updated_ts = #{now} where user_refresh_token_id = #{parentId}")
	int backlinkSuccessor(@Param("parentId") Integer parentId, @Param("successorId") Integer successorId, @Param("now") OffsetDateTime now);

	@Update("update zfgbb.user_refresh_token set revoked_flag = true, rotated_ts = null, updated_ts = #{now} where family_id = #{familyId}")
	int revokeFamily(@Param("familyId") String familyId, @Param("now") OffsetDateTime now);
}
