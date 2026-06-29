package com.zfgc.zfgbb.model.cms;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class ProjectDownload {
	private Integer contentResourceId;
	private String label;
	private String url;
	private String filename;
	private Long fileSize;
	private OffsetDateTime publishedTs;
	private Integer ordinal;
}
