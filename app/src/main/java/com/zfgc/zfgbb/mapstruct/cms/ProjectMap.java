package com.zfgc.zfgbb.mapstruct.cms;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.ProjectViewDbo;
import com.zfgc.zfgbb.dbo.ProjectScreenshotDbo;
import com.zfgc.zfgbb.dbo.ProjectDownloadDbo;
import com.zfgc.zfgbb.dbo.ProjectNewsDbo;
import com.zfgc.zfgbb.dbo.TeamDbo;
import com.zfgc.zfgbb.dbo.TeamMemberDbo;
import com.zfgc.zfgbb.model.cms.Project;
import com.zfgc.zfgbb.model.cms.ProjectScreenshot;
import com.zfgc.zfgbb.model.cms.ProjectDownload;
import com.zfgc.zfgbb.model.cms.ProjectNews;
import com.zfgc.zfgbb.model.cms.TeamInfo;
import com.zfgc.zfgbb.model.cms.TeamMember;

@Mapper(config=BBMapperConfig.class, builder=@Builder(disableBuilder=true))
public interface ProjectMap {
	@Mapping(target="id", source="contentEntityId")
	@Mapping(target="projectId", ignore=true)
	@Mapping(target="author", ignore=true)
	@Mapping(target="screenshots", ignore=true)
	@Mapping(target="downloads", ignore=true)
	@Mapping(target="tags", ignore=true)
	@Mapping(target="news", ignore=true)
	@Mapping(target="team", ignore=true)
	@Mapping(target="page", ignore=true)
	Project toModel(ProjectViewDbo dbo);

	ProjectScreenshot toModel(ProjectScreenshotDbo dbo);

	@Mapping(target="filename", ignore=true)
	ProjectDownload toModel(ProjectDownloadDbo dbo);

	@Mapping(target="threadName", source="threadName")
	ProjectNews toNews(ProjectNewsDbo dbo, String threadName);

	@Mapping(target="members", ignore=true)
	TeamInfo toTeam(TeamDbo dbo);

	@Mapping(target="displayName", source="displayName")
	TeamMember toTeamMember(TeamMemberDbo dbo, String displayName);
}
