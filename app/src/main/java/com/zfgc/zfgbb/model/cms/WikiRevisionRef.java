package com.zfgc.zfgbb.model.cms;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class WikiRevisionRef {
	private Integer revisionId;
	private WikiPageRef page;
	private OffsetDateTime authoredTs;
	private String authorName;
	private String summary;
	private Integer size;
	private boolean current;
	private String status;
}
