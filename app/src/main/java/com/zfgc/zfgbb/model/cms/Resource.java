package com.zfgc.zfgbb.model.cms;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zfgc.zfgbb.model.BaseModel;
import com.zfgc.zfgbb.util.ZfgcStringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Resource extends BaseModel {

	@JsonIgnore
	private Integer resourceId;
	private String title;
	private String slug;
	private String resourceType;
	private String summary;
	private Long fileSize;
	private String downloadUrl;
	private Integer threadId;
	private Integer wikiPageId;
	private Integer downloadContentResourceId;
	private String downloadFilename;
	private Integer previewContentResourceId;
	private Integer viewCount;
	private Integer downloadCount;
	private String author;
	private Integer createdUserId;
	private OffsetDateTime publishedTs;
	private OffsetDateTime lastUpdatedTs;
	private Float rating;
	private Integer voteCount;
	private WikiPage page;

	@Override
	public Integer getId() {
		return resourceId;
	}

	@Override
	public void setId(Integer id) {
		resourceId = id;
	}

	public String getSummaryText() {
		return ZfgcStringUtils.toPlainSummary(summary);
	}
}
