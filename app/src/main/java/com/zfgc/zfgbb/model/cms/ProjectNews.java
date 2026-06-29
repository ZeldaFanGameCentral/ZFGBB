package com.zfgc.zfgbb.model.cms;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class ProjectNews {
	private Integer threadId;
	private String threadName;
	private String subject;
	private String body;
	private Integer authorUserId;
	private String authorName;
	private OffsetDateTime publishedTs;
}
