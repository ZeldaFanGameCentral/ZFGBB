package com.zfgc.zfgbb.dao.cms;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.ReadDao;
import com.zfgc.zfgbb.dbo.ProjectViewDbo;
import com.zfgc.zfgbb.dbo.ProjectViewDboExample;
import com.zfgc.zfgbb.mappers.ProjectViewDboMapper;
import com.zfgc.zfgbb.mappers.custom.CmsFacetMapper;

@Repository
public class ProjectViewDao extends ReadDao<ProjectViewDbo, ProjectViewDboExample> {

	private final CmsFacetMapper cmsFacetMapper;

	public ProjectViewDao(ProjectViewDboMapper mapper, CmsFacetMapper cmsFacetMapper) {
		super(mapper);
		this.cmsFacetMapper = cmsFacetMapper;
	}

	public List<CmsFacetMapper.FacetCount> countProjectLanguages() {
		return cmsFacetMapper.countProjectLanguages();
	}

	public List<CmsFacetMapper.FacetCount> countProjectStatuses() {
		return cmsFacetMapper.countProjectStatuses();
	}
}
