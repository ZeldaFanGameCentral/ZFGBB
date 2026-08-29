package com.zfgc.zfgbb.model.forum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChildBoard {
	private Integer boardId;
	private String boardName;
	private Integer parentBoardId;
}
