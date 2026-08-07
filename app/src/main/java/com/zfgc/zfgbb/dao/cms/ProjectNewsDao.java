package com.zfgc.zfgbb.dao.cms;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.ProjectNewsDbo;
import com.zfgc.zfgbb.dbo.ProjectNewsDboExample;
import com.zfgc.zfgbb.mappers.ProjectNewsDboMapper;

@Repository
public class ProjectNewsDao extends KeyedDao<ProjectNewsDbo, ProjectNewsDboExample, Integer> {

	public ProjectNewsDao(ProjectNewsDboMapper mapper) {
		super(mapper);
	}
}
