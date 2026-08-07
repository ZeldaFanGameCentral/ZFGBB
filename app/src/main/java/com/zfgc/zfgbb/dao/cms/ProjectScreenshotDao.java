package com.zfgc.zfgbb.dao.cms;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.IdentityDao;
import com.zfgc.zfgbb.dbo.ProjectScreenshotDbo;
import com.zfgc.zfgbb.dbo.ProjectScreenshotDboExample;
import com.zfgc.zfgbb.mappers.ProjectScreenshotDboMapper;

@Repository
public class ProjectScreenshotDao extends IdentityDao<ProjectScreenshotDbo, ProjectScreenshotDboExample> {

	public ProjectScreenshotDao(ProjectScreenshotDboMapper mapper) {
		super(mapper);
	}
}
