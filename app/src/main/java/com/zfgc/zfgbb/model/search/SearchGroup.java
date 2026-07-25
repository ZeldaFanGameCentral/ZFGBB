package com.zfgc.zfgbb.model.search;

import java.util.List;

import lombok.Data;

@Data
public class SearchGroup {
	private String type;
	private String label;
	private int total;
	private boolean more;
	private List<SearchHit> hits;

	public SearchGroup(String type, String label, int total, List<SearchHit> hits) {
		this.type = type;
		this.label = label;
		this.total = total;
		this.hits = hits;
	}
}
