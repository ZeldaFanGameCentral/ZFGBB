package com.zfgc.zfgbb.dao.users;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.BrUserPermissionDbo;
import com.zfgc.zfgbb.dbo.BrUserPermissionDboExample;
import com.zfgc.zfgbb.mappers.BrUserPermissionDboMapper;
import com.zfgc.zfgbb.mappers.custom.UserPermissionGrantMapper;

@Repository
public class BrUserPermissionDao extends KeyedDao<BrUserPermissionDbo, BrUserPermissionDboExample, Integer> {

	private final UserPermissionGrantMapper userPermissionGrantMapper;

	public BrUserPermissionDao(BrUserPermissionDboMapper mapper,
			UserPermissionGrantMapper userPermissionGrantMapper) {
		super(mapper);
		this.userPermissionGrantMapper = userPermissionGrantMapper;
	}

	public int grantIfAbsent(Integer userId, Integer userPermissionId) {
		return userPermissionGrantMapper.grantPermissionIfAbsent(userId, userPermissionId);
	}
}
