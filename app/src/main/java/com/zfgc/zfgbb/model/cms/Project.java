package com.zfgc.zfgbb.model.cms;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zfgc.zfgbb.model.BaseModel;
import com.zfgc.zfgbb.util.ZfgcStringUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Project extends BaseModel {

	@JsonIgnore
	private Integer projectId;
	private String title;
	private String slug;
	private String status;
	private Short progress;
	private String summary;
	private String language;
	private String requirements;
	private Integer threadId;
	private Integer wikiPageId;
	private Integer previewContentResourceId;
	private Integer viewCount;
	private Integer downloadCount;
	private String author;
	private Integer createdUserId;
	private java.time.OffsetDateTime publishedTs;
	private java.time.OffsetDateTime lastUpdatedTs;
	private Float rating;
	private Integer voteCount;
	private List<ProjectScreenshot> screenshots = new ArrayList<>();
	private List<ProjectDownload> downloads = new ArrayList<>();
	private List<String> tags = new ArrayList<>();
	private List<ProjectNews> news = new ArrayList<>();
	private TeamInfo team;
	private WikiPage page;

	@Override
	public Integer getId() {
		return projectId;
	}

	@Override
	public void setId(Integer id) {
		projectId = id;
	}

	public String getSummaryText() {
		return ZfgcStringUtils.toPlainSummary(summary);
	}
}
