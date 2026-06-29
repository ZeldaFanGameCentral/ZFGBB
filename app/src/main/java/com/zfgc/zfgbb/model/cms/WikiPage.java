package com.zfgc.zfgbb.model.cms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zfgc.zfgbb.model.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class WikiPage extends BaseModel {

	@JsonIgnore
	private Integer wikiPageId;
	private String namespace;
	private String title;
	private String slug;
	private String redirectTo;
	private String content;
	private String contentParsed;
	private String contentFormat;
	private List<String> categories = new ArrayList<>();
	private List<WikiPageRef> categoryMembers = new ArrayList<>();
	private WikiRevisionRef revision;
	private WikiFileRef file;
	private List<Heading> headings = new ArrayList<>();
	private boolean toc;
	private String entityUrl;

	public record Heading(int level, String text, String id) {
	}

	@Override
	public Integer getId() {
		return wikiPageId;
	}

	@Override
	public void setId(Integer id) {
		wikiPageId = id;
	}
}
