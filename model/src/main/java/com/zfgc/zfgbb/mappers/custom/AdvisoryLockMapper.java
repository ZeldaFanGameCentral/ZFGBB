package com.zfgc.zfgbb.mappers.custom;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdvisoryLockMapper {

	@Select("select 1 from (select pg_advisory_xact_lock(hashtext('zfgbb_admin_roster')::bigint)) lock_acquired")
	int acquireAdminRosterLock();
}
