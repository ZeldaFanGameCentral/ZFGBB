package com.zfgc.zfgbb.model.forum;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zfgc.zfgbb.model.BaseModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category extends BaseModel {
	@JsonIgnore
	private Integer categoryId;
	private String categoryName;
	private String description;
	private Integer parentCategoryId;

	private List<BoardSummary> boards = new ArrayList<>();

	@Override
	public Integer getId() {
		return categoryId;
	}

	@Override
	public void setId(Integer id) {
		categoryId = id;
	}

	@Override
	@JsonIgnore
	public OffsetDateTime getCreatedTs() {
		return super.getCreatedTs();
	}

	@Override
	@JsonIgnore
	public OffsetDateTime getUpdatedTs() {
		return super.getUpdatedTs();
	}
}
