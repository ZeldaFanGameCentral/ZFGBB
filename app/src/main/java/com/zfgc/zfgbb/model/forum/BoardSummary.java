package com.zfgc.zfgbb.model.forum;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.model.Securable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSummary implements Securable {
	private Integer boardId;
	private String description;
	private String boardName;
	private Long threadCount;
	private Long postCount;
	private Integer latestMessageId;
	private Integer latestThreadId;
	private Integer latestMessageOwnerId;
	private String latestMessageUserName;
	private Integer categoryId;
	private Integer parentBoardId;
	private String threadName;

	private List<ChildBoard> childBoards;

	@JsonIgnore
	private List<Permission> boardPerms = new ArrayList<>();

	private OffsetDateTime latestMessageCreatedTs;

	@Override
	@JsonIgnore
	public List<Permission> getPermissions() {
		return boardPerms;
	}
}
