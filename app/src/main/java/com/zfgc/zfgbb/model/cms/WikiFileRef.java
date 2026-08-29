package com.zfgc.zfgbb.model.cms;

import lombok.Data;

@Data
public class WikiFileRef {
	private Integer contentResourceId;
	private String filename;
	private String mimeType;
	private Long fileSize;
}
