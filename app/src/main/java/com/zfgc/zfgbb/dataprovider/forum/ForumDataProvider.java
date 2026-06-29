package com.zfgc.zfgbb.dataprovider.forum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.BoardDao;
import com.zfgc.zfgbb.dao.BoardPermissionViewDao;
import com.zfgc.zfgbb.dao.CategoryDao;
import com.zfgc.zfgbb.dao.ThreadDao;
import com.zfgc.zfgbb.dbo.BoardDbo;
import com.zfgc.zfgbb.dbo.BoardDboExample;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import com.zfgc.zfgbb.dbo.BoardSummaryViewDboExample;
import com.zfgc.zfgbb.dbo.CategoryDboExample;
import com.zfgc.zfgbb.dbo.ChildBoardViewDboExample;
import com.zfgc.zfgbb.dbo.ThreadDboExample;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.mappers.BoardSummaryViewDboMapper;
import com.zfgc.zfgbb.mappers.ChildBoardViewDboMapper;
import com.zfgc.zfgbb.mapstruct.forum.BoardMap;
import com.zfgc.zfgbb.mapstruct.users.PermissionMap;
import com.zfgc.zfgbb.model.forum.Board;
import com.zfgc.zfgbb.model.forum.BoardSummary;
import com.zfgc.zfgbb.model.forum.Category;
import com.zfgc.zfgbb.model.forum.ChildBoard;
import com.zfgc.zfgbb.model.forum.Forum;
import com.zfgc.zfgbb.model.forum.Thread;
import com.zfgc.zfgbb.model.users.Permission;

@Repository
public class ForumDataProvider {
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private ThreadDao threadDao;
	
	@Autowired
	private ThreadDataProvider threadDataProvider;
	
	@Autowired
	private BoardPermissionViewDao boardPermissionDao;
	
	@Autowired
	private BoardSummaryViewDboMapper boardSummaryMapper;
	
	@Autowired
	private ChildBoardViewDboMapper childBoardMapper;

	@Autowired
	private BoardMap boardMap;

	@Autowired
	private PermissionMap permissionMap;

	public Board getBoard(Integer boardId, Integer pageNumber, Integer pageSize) {
		Optional<BoardDbo> boardDbo = boardDao.get(boardId);
		Board board = boardDbo.map(boardMap::toModel).orElseThrow(() -> new ZfgcNotFoundException());
		
		List<Thread> unstickyThreads = threadDataProvider.getThreadsByBoardId(boardId, pageNumber, pageSize, false);
		List<Thread> stickyThreads = threadDataProvider.getThreadsByBoardId(boardId, null, null, true);
		
		board.setStickyThreads(stickyThreads);
		board.setUnStickyThreads(unstickyThreads);
		
		ThreadDboExample threadEx = new ThreadDboExample();
		threadEx.createCriteria().andBoardIdEqualTo(boardId).andPinnedFlagEqualTo(false);
		Long threadCount = threadDao.getMapper().countByExample(threadEx);
		board.setThreadCount(threadCount);

		board.setChildBoards(getBoardSummaries(board.getBoardId()));
		
		List<Permission> boardPerms = getBoardPermissions(board.getBoardId());
		board.setBoardPerms(boardPerms);
		
		return board;
	}
	
	private List<BoardSummary> getBoardSummaries(Integer parentBoardId){
		return getBoardSummaries(Arrays.asList(parentBoardId));
	}
	
	private List<BoardSummary> getBoardSummaries(List<Integer> parentBoardId){
		BoardSummaryViewDboExample boardSummaryEx = new BoardSummaryViewDboExample();
		boardSummaryEx.createCriteria().andParentBoardIdIn(parentBoardId);
		List<BoardSummary> result = (boardSummaryMapper.selectByExample(boardSummaryEx).stream().map(boardMap::toModel).collect(Collectors.toList()));

		ChildBoardViewDboExample childBoardEx = new ChildBoardViewDboExample();
		childBoardEx.createCriteria().andParentBoardIdIn(parentBoardId);

		Map<Integer, List<ChildBoard>> childBoards = childBoardMapper.selectByExample(childBoardEx).stream()
																					.map(boardMap::toModel)
																					.collect(Collectors.groupingBy(ChildBoard::getParentBoardId));
		
		result.forEach(bs -> {
			bs.setChildBoards(childBoards.get(bs.getBoardId()));
		});
		
		
		return result;
	}
	
	private List<BoardSummary> getBoardSummariesByCategory(List<Integer> categoryId){
		BoardSummaryViewDboExample boardSummaryEx = new BoardSummaryViewDboExample();
		boardSummaryEx.createCriteria().andCategoryIdIn(categoryId);
		List<BoardSummary> result = (boardSummaryMapper.selectByExample(boardSummaryEx).stream().map(boardMap::toModel).collect(Collectors.toList()));
		
		if(!result.isEmpty()) {
			ChildBoardViewDboExample childBoardEx = new ChildBoardViewDboExample();
			childBoardEx.createCriteria().andParentBoardIdIn(result.stream().map(BoardSummary::getBoardId).collect(Collectors.toList()));

			Map<Integer, List<ChildBoard>> childBoards = childBoardMapper.selectByExample(childBoardEx).stream()
																						.map(boardMap::toModel)
																						.collect(Collectors.groupingBy(ChildBoard::getParentBoardId));
			
			result.forEach(bs -> {
				bs.setChildBoards(childBoards.get(bs.getBoardId()));
			});
		}
		
		
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

	private List<BoardSummary> getTopLevelBoardSummaries(){
		BoardSummaryViewDboExample boardSummaryEx = new BoardSummaryViewDboExample();
		boardSummaryEx.createCriteria().andParentBoardIdIsNull();
		List<BoardSummary> result = boardSummaryMapper.selectByExample(boardSummaryEx).stream()
				.map(boardMap::toModel).collect(Collectors.toList());

		if (result.isEmpty()) {
			return result;
		}

		ChildBoardViewDboExample childBoardEx = new ChildBoardViewDboExample();
		childBoardEx.createCriteria().andParentBoardIdIn(result.stream().map(BoardSummary::getBoardId).toList());

		Map<Integer, List<ChildBoard>> childBoards = childBoardMapper.selectByExample(childBoardEx).stream()
				.map(boardMap::toModel)
				.collect(Collectors.groupingBy(ChildBoard::getParentBoardId));

		result.forEach(bs -> bs.setChildBoards(childBoards.get(bs.getBoardId())));

		return result;
	}
	
	public List<Permission> getBoardPermissions(Integer boardId){
		BoardPermissionViewDboExample bEx = new BoardPermissionViewDboExample();
		bEx.createCriteria().andBoardIdEqualTo(boardId);
		return boardPermissionDao.get(bEx).stream().map(permissionMap::toModel).collect(Collectors.toList());
	}
	
	public Map<Integer, List<Permission>> getBoardPermissions(List<Integer> boardIds){
		BoardPermissionViewDboExample bEx = new BoardPermissionViewDboExample();
		bEx.createCriteria().andBoardIdIn(boardIds);
		Map<Integer, List<BoardPermissionViewDbo>> result = boardPermissionDao.get(bEx).stream()
						  				  									  .collect(Collectors.groupingBy(BoardPermissionViewDbo::getBoardId));
		
		Map<Integer, List<Permission>> response = new HashMap<>();
		result.keySet().forEach(k -> {
			List<Permission> permissions = result.get(k).stream().map(permissionMap::toModel).collect(Collectors.toList());
			response.put(k, permissions);
		});
		
		return response;
		
	}
	
	public List<Board> getBoardsByParent(Integer parentBoardId){
		BoardDboExample bEx = new BoardDboExample();
		bEx.createCriteria().andParentBoardIdEqualTo(parentBoardId);
		
		List<Board> result = boardDao.get(bEx).stream().map(boardMap::toModel).collect(Collectors.toList());
		result.forEach(b -> {
			ThreadDboExample tEx = new ThreadDboExample();
			tEx.createCriteria().andBoardIdEqualTo(b.getBoardId());
			
			b.setThreadCount(threadDao.getMapper().countByExample(tEx));
		});
		
		return result;
		
	}
	
}