package com.zfgc.zfgbb.dataprovider.users;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.authorization.UnfilteredBoardRead;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import com.zfgc.zfgbb.dao.forum.BoardPermissionViewDao;
import com.zfgc.zfgbb.dao.forum.MessageDao;
import com.zfgc.zfgbb.mappers.custom.MessagePostCountMapper.OwnerPostCount;
import com.zfgc.zfgbb.model.users.User;
import lombok.RequiredArgsConstructor;

@Repository
@UnfilteredBoardRead("counts only guest-visible boards")
@RequiredArgsConstructor
public class GuestPermissionDataProvider {

	private final BoardPermissionViewDao boardPermissionViewDao;

	private final MessageDao messageDao;

	public List<Integer> guestVisibleBoardIds() {
		BoardPermissionViewDboExample ex = new BoardPermissionViewDboExample();
		ex.createCriteria().andPermissionIdIn(User.guest().permissionIds());
		return boardPermissionViewDao.get(ex).stream()
				.map(BoardPermissionViewDbo::getBoardId).distinct().collect(Collectors.toList());
	}

	public Map<Integer, Integer> guestVisiblePostCountsByOwner(Collection<Integer> ownerIds) {
		if (ownerIds.isEmpty())
			return Map.of();
		List<Integer> boardIds = guestVisibleBoardIds();
		if (boardIds.isEmpty())
			return Map.of();
		return messageDao.postCountsByOwnerWithinBoards(List.copyOf(ownerIds), boardIds).stream()
				.collect(Collectors.toMap(OwnerPostCount::getOwnerId, count -> (int) count.getPostCount()));
	}
}
