package com.zfgc.zfgbb.model.forum;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecentActivity {
	private Integer threadId;
	private String threadName;
	private Integer boardId;
	private String boardName;
	private String lastPoster;
	private Integer lastPosterId;
	private OffsetDateTime lastPostTs;
}
