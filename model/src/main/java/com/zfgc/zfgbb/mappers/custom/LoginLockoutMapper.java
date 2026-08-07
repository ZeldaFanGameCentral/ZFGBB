package com.zfgc.zfgbb.mappers.custom;

import java.time.OffsetDateTime;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface LoginLockoutMapper {

	@Update("""
			update zfgbb."user"
			set failed_login_count = (case
					when locked_until_ts is not null and locked_until_ts <= #{now} then 1
					else coalesce(failed_login_count, 0) + 1 end),
				locked_until_ts = (case
					when (case when locked_until_ts is not null and locked_until_ts <= #{now} then 1
							else coalesce(failed_login_count, 0) + 1 end) >= #{lockThreshold} then #{lockUntil}
					when locked_until_ts is not null and locked_until_ts <= #{now} then null
					else locked_until_ts end),
				updated_ts = #{now}
			where user_id = #{userId}
			""")
	int recordFailedLoginAttempt(@Param("userId") Integer userId, @Param("now") OffsetDateTime now,
			@Param("lockThreshold") int lockThreshold, @Param("lockUntil") OffsetDateTime lockUntil);

	@Update("""
			update zfgbb."user"
			set failed_login_count = 0, locked_until_ts = null, updated_ts = #{now}
			where user_id = #{userId} and (coalesce(failed_login_count, 0) > 0 or locked_until_ts is not null)
			""")
	int clearFailedLoginState(@Param("userId") Integer userId, @Param("now") OffsetDateTime now);
}
