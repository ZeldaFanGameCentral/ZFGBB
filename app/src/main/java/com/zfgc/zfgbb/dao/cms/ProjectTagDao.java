package com.zfgc.zfgbb.dao.cms;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.ProjectTagDbo;
import com.zfgc.zfgbb.dbo.ProjectTagDboExample;
import com.zfgc.zfgbb.mappers.ProjectTagDboMapper;
import com.zfgc.zfgbb.mappers.custom.ProjectMergeMapper;

@Repository
public class ProjectTagDao extends KeyedDao<ProjectTagDbo, ProjectTagDboExample, Integer> {

	private final ProjectMergeMapper projectMergeMapper;

	public ProjectTagDao(ProjectTagDboMapper mapper, ProjectMergeMapper projectMergeMapper) {
		super(mapper);
		this.projectMergeMapper = projectMergeMapper;
	}

	public int repoint(Integer targetId, Integer sourceId) {
		return projectMergeMapper.repointProjectTags(targetId, sourceId);
	}
}
