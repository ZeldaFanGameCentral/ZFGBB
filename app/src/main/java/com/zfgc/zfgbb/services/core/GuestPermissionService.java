package com.zfgc.zfgbb.services.core;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import com.zfgc.zfgbb.mappers.BoardPermissionViewDboMapper;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.users.Permission;

@Service
public class GuestPermissionService {

	@Autowired
	private BoardPermissionViewDboMapper boardPermissionViewDboMapper;

	@Cacheable("guestVisibleBoardIds")
	public List<Integer> guestVisibleBoardIds() {
		List<Integer> guestPerms = User.guest().getPermissions().stream()
				.map(Permission::getPermissionId).toList();
		BoardPermissionViewDboExample ex = new BoardPermissionViewDboExample();
		ex.createCriteria().andPermissionIdIn(guestPerms);
		return boardPermissionViewDboMapper.selectByExample(ex).stream()
				.map(BoardPermissionViewDbo::getBoardId).distinct().collect(Collectors.toList());
	}
}
