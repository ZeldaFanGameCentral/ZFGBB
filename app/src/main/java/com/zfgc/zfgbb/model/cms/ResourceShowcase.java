package com.zfgc.zfgbb.model.cms;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceShowcase {

	private Resource featured;
	private List<Resource> recent;
	private List<Resource> random;
	private List<Resource> topRated;
	private List<Resource> mostDownloaded;
	private int totalResources;
}
