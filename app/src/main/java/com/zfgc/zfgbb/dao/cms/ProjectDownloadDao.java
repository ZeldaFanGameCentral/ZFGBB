package com.zfgc.zfgbb.dao.cms;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.ProjectDownloadDbo;
import com.zfgc.zfgbb.dbo.ProjectDownloadDboExample;
import com.zfgc.zfgbb.mappers.ProjectDownloadDboMapper;

@Repository
public class ProjectDownloadDao extends IdentityDao<ProjectDownloadDbo, ProjectDownloadDboExample> {

	public ProjectDownloadDao(ProjectDownloadDboMapper mapper) {
		super(mapper);
	}
}
