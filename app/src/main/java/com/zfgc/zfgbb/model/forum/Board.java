package com.zfgc.zfgbb.model.forum;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zfgc.zfgbb.model.BaseModel;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.model.Securable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board extends BaseModel implements Securable {

	@JsonIgnore
	private Integer boardId;
	private String boardName;
	private String description;
	private Integer categoryId;
	private Integer parentBoardId;
	private Long threadCount;

	private List<Thread> stickyThreads;
	private List<Thread> unStickyThreads;
	private List<BoardSummary> childBoards;

	@JsonIgnore
	private List<Permission> boardPerms = new ArrayList<>();

	@Override
	@JsonIgnore
	public List<Permission> getPermissions() {
		return boardPerms;
	}

	@Override
	public Integer getId() {
		return boardId;
	}

	@Override
	public void setId(Integer id) {
		boardId = id;
	}

	public Long getPageCount() {
		if(threadCount == null) {
			return 1L;
		}
		return (long) Math.ceil(threadCount.doubleValue() / 10.0);
	}
}
