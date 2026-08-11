package com.zfgc.zfgbb.mappers.custom;

import java.time.OffsetDateTime;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface RefreshTokenConsumeMapper {

	@Update("""
			update zfgbb.user_refresh_token set revoked_flag = true, rotated_ts = #{now}, updated_ts = #{now}
			where user_refresh_token_id = #{userRefreshTokenId} and revoked_flag = false and expires_ts >= #{now}
			""")
	int consumeToken(@Param("userRefreshTokenId") Integer userRefreshTokenId, @Param("now") OffsetDateTime now);
}
