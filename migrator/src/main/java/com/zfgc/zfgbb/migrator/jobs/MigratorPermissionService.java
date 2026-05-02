package com.zfgc.zfgbb.migrator.jobs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.dbo.BrBoardPermissionDbo;
import com.zfgc.zfgbb.dbo.BrBoardPermissionDboExample;
import com.zfgc.zfgbb.dbo.BrUserPermissionDbo;
import com.zfgc.zfgbb.dbo.BrUserPermissionDboExample;
import com.zfgc.zfgbb.dbo.PermissionDbo;
import com.zfgc.zfgbb.dbo.PermissionDboExample;
import com.zfgc.zfgbb.mappers.BrBoardPermissionDboMapper;
import com.zfgc.zfgbb.mappers.BrUserPermissionDboMapper;
import com.zfgc.zfgbb.mappers.PermissionDboMapper;

@Service
public class MigratorPermissionService {

	public static final List<String> DEFAULT_BOARD_PERMISSION_CODES =
			List.of("ZFGC_USER", "ZFGC_GUEST");

	public static final List<String> DEFAULT_USER_PERMISSION_CODES =
			List.of("ZFGC_USER");

	@Autowired
	private PermissionDboMapper permissionMapper;

	@Autowired
	private BrBoardPermissionDboMapper brBoardPermissionMapper;

	@Autowired
	private BrUserPermissionDboMapper brUserPermissionMapper;

	public Integer permissionIdByCode(String code) {
		PermissionDboExample ex = new PermissionDboExample();
		ex.createCriteria().andPermissionCodeEqualTo(code);
		List<PermissionDbo> matches = permissionMapper.selectByExample(ex);
		return matches.isEmpty() ? null : matches.get(0).getPermissionId();
	}

	public void grantBoardPermission(Integer boardId, String code) {
		Integer permissionId = permissionIdByCode(code);
		if (permissionId == null) {
			return;
		}
		BrBoardPermissionDboExample ex = new BrBoardPermissionDboExample();
		ex.createCriteria()
				.andBoardIdEqualTo(boardId)
				.andPermissionIdEqualTo(permissionId);
		if (brBoardPermissionMapper.countByExample(ex) > 0) {
			return;
		}
		BrBoardPermissionDbo row = new BrBoardPermissionDbo();
		row.setBoardId(boardId);
		row.setPermissionId(permissionId);
		brBoardPermissionMapper.insert(row);
	}

	public void grantDefaultBoardPermissions(Integer boardId) {
		DEFAULT_BOARD_PERMISSION_CODES.forEach(code -> grantBoardPermission(boardId, code));
	}

	public void grantUserPermission(Integer userId, String code) {
		Integer permissionId = permissionIdByCode(code);
		if (permissionId == null) {
			return;
		}
		BrUserPermissionDboExample ex = new BrUserPermissionDboExample();
		ex.createCriteria()
				.andUserIdEqualTo(userId)
				.andUserPermissionIdEqualTo(permissionId);
		if (brUserPermissionMapper.countByExample(ex) > 0) {
			return;
		}
		BrUserPermissionDbo row = new BrUserPermissionDbo();
		row.setUserId(userId);
		row.setUserPermissionId(permissionId);
		brUserPermissionMapper.insert(row);
	}

	public void grantDefaultUserPermissions(Integer userId) {
		DEFAULT_USER_PERMISSION_CODES.forEach(code -> grantUserPermission(userId, code));
	}
}
