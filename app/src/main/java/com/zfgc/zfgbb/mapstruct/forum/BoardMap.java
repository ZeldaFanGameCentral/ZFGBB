package com.zfgc.zfgbb.mapstruct.forum;

import java.util.List;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.BoardDbo;
import com.zfgc.zfgbb.dbo.BoardSummaryViewDbo;
import com.zfgc.zfgbb.dbo.ChildBoardViewDbo;
import com.zfgc.zfgbb.dbo.RecentActivityViewDbo;
import com.zfgc.zfgbb.dbo.CategoryDbo;
import com.zfgc.zfgbb.model.forum.Board;
import com.zfgc.zfgbb.model.forum.BoardSummary;
import com.zfgc.zfgbb.model.forum.ChildBoard;
import com.zfgc.zfgbb.model.forum.RecentActivity;
import com.zfgc.zfgbb.model.forum.Category;
import com.zfgc.zfgbb.model.forum.Forum;
import com.zfgc.zfgbb.model.users.Permission;

@Mapper(config=BBMapperConfig.class, builder=@Builder(disableBuilder=true))
public interface BoardMap {
	@Mapping(target="id", ignore=true)
	@Mapping(target="threadCount", ignore=true)
	@Mapping(target="stickyThreads", ignore=true)
	@Mapping(target="unStickyThreads", ignore=true)
	@Mapping(target="childBoards", ignore=true)
	@Mapping(target="boardPerms", ignore=true)
	@Mapping(target="permissions", ignore=true)
	Board toModel(BoardDbo dbo);

	@Mapping(target="childBoards", ignore=true)
	@Mapping(target="boardPerms", ignore=true)
	@Mapping(target="permissions", ignore=true)
	BoardSummary toModel(BoardSummaryViewDbo dbo);

	ChildBoard toModel(ChildBoardViewDbo dbo);

	RecentActivity toModel(RecentActivityViewDbo dbo);

	List<RecentActivity> toRecentActivityList(List<RecentActivityViewDbo> dbos);

	List<BoardSummary> toBoardSummaryList(List<BoardSummaryViewDbo> dbos);

	List<ChildBoard> toChildBoardList(List<ChildBoardViewDbo> dbos);

	@Mapping(target="id", ignore=true)
	@Mapping(target="parentCategoryId", ignore=true)
	@Mapping(target="boards", ignore=true)
	Category toModel(CategoryDbo dbo);

	@Mapping(target="categories", qualifiedByName="deepCopyCategory")
	@Mapping(target="boardPermissions", qualifiedByName="deepCopyPermission")
	@Mapping(target="permissions", ignore=true)
	Forum deepCopy(Forum forum);

	@Named("deepCopyCategory")
	@Mapping(target="boards", qualifiedByName="deepCopyBoardSummary")
	Category deepCopy(Category category);

	@Named("deepCopyBoardSummary")
	@Mapping(target="childBoards", qualifiedByName="deepCopyChildBoard")
	@Mapping(target="boardPerms", qualifiedByName="deepCopyPermission")
	@Mapping(target="permissions", ignore=true)
	BoardSummary deepCopy(BoardSummary boardSummary);

	@Named("deepCopyChildBoard")
	ChildBoard deepCopy(ChildBoard childBoard);

	@Named("deepCopyPermission")
	Permission deepCopy(Permission permission);
}
