package com.zfgc.zfgbb.model.search;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchResults {

	private String query;
	private int total;
	private List<SearchGroup> groups;
}
