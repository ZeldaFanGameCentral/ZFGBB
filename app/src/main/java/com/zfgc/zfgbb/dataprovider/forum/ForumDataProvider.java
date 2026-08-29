package com.zfgc.zfgbb.dataprovider.forum;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dbo.ModerationLogDboExample;
import com.zfgc.zfgbb.dbo.ModerationLogDbo;
import com.zfgc.zfgbb.dao.forum.ModerationLogDao;
import com.zfgc.zfgbb.dbo.NotificationSubscriptionDboExample;
import com.zfgc.zfgbb.dao.forum.NotificationSubscriptionDao;
import com.zfgc.zfgbb.authorization.BoardVisibilityChokepoint;
import com.zfgc.zfgbb.dao.forum.BoardDao;
import com.zfgc.zfgbb.dao.forum.CategoryDao;
import com.zfgc.zfgbb.dao.forum.ThreadDao;
import com.zfgc.zfgbb.dbo.BoardDbo;
import com.zfgc.zfgbb.dbo.BoardDboExample;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import com.zfgc.zfgbb.dbo.BoardSummaryViewDboExample;
import com.zfgc.zfgbb.dbo.CategoryDboExample;
import com.zfgc.zfgbb.dbo.ChildBoardViewDboExample;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.dao.forum.BoardPermissionViewDao;
import com.zfgc.zfgbb.dao.forum.BoardSummaryViewDao;
import com.zfgc.zfgbb.dao.forum.ChildBoardViewDao;
import com.zfgc.zfgbb.dao.forum.RecentActivityViewDao;
import com.zfgc.zfgbb.dbo.RecentActivityViewDbo;
import com.zfgc.zfgbb.dbo.RecentActivityViewDboExample;
import com.zfgc.zfgbb.mapstruct.forum.BoardMap;
import com.zfgc.zfgbb.model.forum.RecentActivity;
import com.zfgc.zfgbb.mapstruct.users.PermissionMap;
import com.zfgc.zfgbb.model.forum.Board;
import com.zfgc.zfgbb.model.forum.BoardSummary;
import com.zfgc.zfgbb.model.forum.Category;
import com.zfgc.zfgbb.model.forum.ChildBoard;
import com.zfgc.zfgbb.model.forum.Forum;
import com.zfgc.zfgbb.model.forum.Thread;
import com.zfgc.zfgbb.model.users.Permission;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@BoardVisibilityChokepoint
public class ForumDataProvider {

	private final BoardDao boardDao;

	private final ModerationLogDao moderationLogDao;

	private final NotificationSubscriptionDao notificationSubscriptionDao;

	private final CategoryDao categoryDao;

	private final ThreadDao threadDao;

	private final ThreadDataProvider threadDataProvider;

	private final BoardPermissionViewDao boardPermissionViewDao;

	private final BoardSummaryViewDao boardSummaryViewDao;

	private final ChildBoardViewDao childBoardViewDao;

	private final RecentActivityViewDao recentActivityViewDao;

	private final BoardMap boardMap;

	private final PermissionMap permissionMap;

	public Set<Integer> visibleBoardIds(List<Integer> permissionIds) {
		if (permissionIds == null || permissionIds.isEmpty()) {
			return Set.of();
		}
		BoardPermissionViewDboExample ex = new BoardPermissionViewDboExample();
		ex.createCriteria().andPermissionIdIn(permissionIds);
		return boardPermissionViewDao.get(ex).stream()
				.map(BoardPermissionViewDbo::getBoardId)
				.collect(Collectors.toSet());
	}

	public boolean boardExists(Integer boardId) {
		BoardDboExample example = new BoardDboExample();
		example.createCriteria().andBoardIdEqualTo(boardId);
		return boardDao.exists(example);
	}

	public List<Integer> lockBoards(List<Integer> orderedBoardIds) {
		return boardDao.lockForUpdate(orderedBoardIds);
	}

	public List<RecentActivity> getRecentActivity(Integer boardId, Collection<Integer> visibleBoardIds, int limit) {
		RecentActivityViewDboExample recentActivityEx = new RecentActivityViewDboExample();
		if (boardId != null) {
			recentActivityEx.createCriteria().andBoardIdEqualTo(boardId);
		} else {
			recentActivityEx.createCriteria().andBoardIdIn(new ArrayList<>(visibleBoardIds));
		}
		recentActivityEx.setOrderByClause("last_post_ts desc");
		recentActivityEx.setLimit(limit);
		recentActivityEx.setOffset(0);
		return boardMap.toRecentActivityList(recentActivityViewDao.get(recentActivityEx));
	}

	public Board getBoard(Integer boardId, Integer pageNumber, Integer pageSize) {
		Optional<BoardDbo> boardDbo = boardDao.find(boardId);
		Board board = boardDbo.map(boardMap::toModel).orElseThrow(() -> new ZfgcNotFoundException());
		
		List<Thread> threadsOnThisPage = threadDataProvider.getThreadsByBoardId(boardId, pageNumber, pageSize);

		board.setStickyThreads(threadsOnThisPage.stream()
				.filter(thread -> Boolean.TRUE.equals(thread.getPinnedFlag())).toList());
		board.setUnStickyThreads(threadsOnThisPage.stream()
				.filter(thread -> !Boolean.TRUE.equals(thread.getPinnedFlag())).toList());

		ThreadDboExample threadEx = new ThreadDboExample();
		threadEx.createCriteria().andBoardIdEqualTo(boardId);
		Long threadCount = threadDao.count(threadEx);
		board.setThreadCount(threadCount);

		board.setChildBoards(getBoardSummaries(board.getBoardId()));
		
		List<Permission> boardPerms = getBoardPermissions(board.getBoardId());
		board.setBoardPerms(boardPerms);
		
		return board;
	}
	
	private List<BoardSummary> getBoardSummaries(Integer parentBoardId) {
		BoardSummaryViewDboExample childrenOf = new BoardSummaryViewDboExample();
		childrenOf.createCriteria().andParentBoardIdIn(Arrays.asList(parentBoardId));
		return boardSummariesWithChildBoards(childrenOf);
	}

	private List<BoardSummary> boardSummariesWithChildBoards(BoardSummaryViewDboExample boardSummaryEx) {
		List<BoardSummary> result = boardMap.toBoardSummaryList(boardSummaryViewDao.get(boardSummaryEx));

		List<Integer> loadedBoardIds = result.stream().map(BoardSummary::getBoardId).toList();
		if (loadedBoardIds.isEmpty())
			return result;

		ChildBoardViewDboExample childBoardEx = new ChildBoardViewDboExample();
		childBoardEx.createCriteria().andParentBoardIdIn(loadedBoardIds);
		Map<Integer, List<ChildBoard>> childBoards =
				boardMap.toChildBoardList(childBoardViewDao.get(childBoardEx)).stream()
						.collect(Collectors.groupingBy(ChildBoard::getParentBoardId));

		for (BoardSummary summary : result)
			summary.setChildBoards(childBoards.get(summary.getBoardId()));

		return result;
	}

	public Forum getForum() {
		Forum forum = new Forum();

		List<Category> categories = getTopLevelCategories();
		forum.setCategories(categories);

		List<Integer> boardIds = new ArrayList<>();
		categories.stream().filter(category -> category.getBoards() != null).forEach(category -> {
			boardIds.addAll(category.getBoards().stream().map(BoardSummary::getBoardId).toList());
		});

		if (!boardIds.isEmpty()) {
			Map<Integer, List<Permission>> perms = getBoardPermissions(boardIds);
			categories.stream().filter(category -> category.getBoards() != null).forEach(category -> {
				category.getBoards().forEach(b -> {
					b.setBoardPerms(perms.get(b.getBoardId()));
				});
			});
		}
		return forum;
	}

	private List<Category> getTopLevelCategories(){
		CategoryDboExample categoryEx = new CategoryDboExample();
		categoryEx.createCriteria().andParentBoardIdIsNull();
		categoryEx.setOrderByClause("category_order asc, category_id asc");
		List<Category> categories = categoryDao.get(categoryEx).stream().map(boardMap::toModel).collect(Collectors.toList());

		Map<Integer, List<BoardSummary>> summaries = getTopLevelBoardSummaries().stream()
				.filter(summary -> summary.getCategoryId() != null)
				.collect(Collectors.groupingBy(BoardSummary::getCategoryId));

		categories.forEach(category -> category.setBoards(summaries.get(category.getCategoryId())));

		return categories;
	}

	private List<BoardSummary> getTopLevelBoardSummaries() {
		BoardSummaryViewDboExample topLevel = new BoardSummaryViewDboExample();
		topLevel.createCriteria().andParentBoardIdIsNull();
		return boardSummariesWithChildBoards(topLevel);
	}
	
	public List<Permission> getBoardPermissions(Integer boardId){
		BoardPermissionViewDboExample bEx = new BoardPermissionViewDboExample();
		bEx.createCriteria().andBoardIdEqualTo(boardId);
		return boardPermissionViewDao.get(bEx).stream().map(permissionMap::toModel).collect(Collectors.toList());
	}
	
	public Map<Integer, List<Permission>> getBoardPermissions(List<Integer> boardIds){
		BoardPermissionViewDboExample bEx = new BoardPermissionViewDboExample();
		bEx.createCriteria().andBoardIdIn(boardIds);
		Map<Integer, List<BoardPermissionViewDbo>> result = boardPermissionViewDao.get(bEx).stream()
						  				  									  .collect(Collectors.groupingBy(BoardPermissionViewDbo::getBoardId));
		
		Map<Integer, List<Permission>> response = new HashMap<>();
		result.keySet().forEach(k -> {
			List<Permission> permissions = result.get(k).stream().map(permissionMap::toModel).collect(Collectors.toList());
			response.put(k, permissions);
		});
		
		return response;

	}

	public void deleteNotificationSubscriptions(Integer userId) {
		NotificationSubscriptionDboExample notificationSubscriptionExample = new NotificationSubscriptionDboExample();
		notificationSubscriptionExample.createCriteria().andUserIdEqualTo(userId);
		notificationSubscriptionDao.deleteWhere(notificationSubscriptionExample);
	}

	public void scrubModerationResidue(Integer userId) {
		moderationLogDao.nullModerationLogActors(userId);
		moderationLogDao.scrubModerationLogTargets(userId);
	}

	public void scrubMigratedModerationTargetsByName(String subjectUserName) {
		if (subjectUserName == null || subjectUserName.isBlank())
			return;
		moderationLogDao.scrubModerationLogTargetsByName(subjectUserName);
	}

	public void recordOperatorRemediation(String action, String detail) {
		ModerationLogDboExample ex = new ModerationLogDboExample();
		ex.createCriteria().andActionEqualTo(action).andDetailEqualTo(detail);
		if (!moderationLogDao.get(ex).isEmpty())
			return;
		ModerationLogDbo entry = new ModerationLogDbo();
		entry.setAction(action);
		entry.setDetail(detail);
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		entry.setLoggedTs(now);
		entry.setCreatedTs(now);
		moderationLogDao.insertSelective(entry);
	}
}
