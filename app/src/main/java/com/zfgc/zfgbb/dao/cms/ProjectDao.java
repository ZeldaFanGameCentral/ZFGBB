package com.zfgc.zfgbb.dao.cms;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.ProjectDbo;
import com.zfgc.zfgbb.dbo.ProjectDboExample;
import com.zfgc.zfgbb.mappers.ProjectDboMapper;

@Repository
public class ProjectDao extends KeyedDao<ProjectDbo, ProjectDboExample, Integer> {

	public ProjectDao(ProjectDboMapper mapper) {
		super(mapper);
	}
}
