package com.zfgc.zfgbb.dao.meta;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.mappers.custom.AdvisoryLockMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AdvisoryLockDao {

	private final AdvisoryLockMapper advisoryLockMapper;

	public int acquireAdminRosterLock() {
		return advisoryLockMapper.acquireAdminRosterLock();
	}
}
