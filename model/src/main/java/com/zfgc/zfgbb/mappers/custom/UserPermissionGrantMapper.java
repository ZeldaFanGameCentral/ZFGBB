package com.zfgc.zfgbb.mappers.custom;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface UserPermissionGrantMapper {

	@Insert("""
			insert into zfgbb.br_user_permission (user_id, user_permission_id)
			values (#{userId}, #{userPermissionId})
			on conflict (user_id, user_permission_id) do nothing
			""")
	int grantPermissionIfAbsent(@Param("userId") Integer userId,
			@Param("userPermissionId") Integer userPermissionId);
}
