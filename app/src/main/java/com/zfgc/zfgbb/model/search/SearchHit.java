package com.zfgc.zfgbb.model.search;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchHit {
	private String type;
	private String title;
	private String snippet;
	private String context;
	private String url;
}
