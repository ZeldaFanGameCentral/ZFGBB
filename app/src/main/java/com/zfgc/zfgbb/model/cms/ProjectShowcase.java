package com.zfgc.zfgbb.model.cms;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectShowcase {

	private Project featured;
	private List<Project> recent;
	private List<Project> random;
	private List<Project> topRated;
	private List<Project> mostDownloaded;
	private int totalProjects;
}
