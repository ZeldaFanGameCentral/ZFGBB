package com.zfgc.zfgbb.model.cms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WikiRevision {
	private Integer revisionId;
	private Integer wikiPageId;
	private String content;
	private String contentFormat;
	private String status;
}
