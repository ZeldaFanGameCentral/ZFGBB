package com.zfgc.zfgbb.migrator.web;

import java.util.List;
import java.util.Map;

import com.zfgc.zfgbb.migrator.jobs.JobType;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import lombok.Data;

@Data
public class MigrateJobRequest {
	private JobType type;
	private String smfHost;
	private Integer smfPort = 3306;
	private String smfDatabase;
	private String smfUser;
	private String smfPassword;
	private String smfTablePrefix;
	private String smfLegacyHost;
	private String wikiLegacyHost;
	private String appBaseUrl;
	private String attachmentsSourcePath;
	private String attachmentsTargetPath;
	private String avatarsSourcePath;
	private String cmsFilesSourcePath;
	private String wikiImagesSourcePath;
	private Boolean force;
	private Boolean createMemberWikiPages;
	private Integer discussionBoardId;
	private Integer resourcesBoardId;
	private Map<String, Integer> talkBoardIds;
	private Map<Integer, List<String>> groupPermissionMap;
	private Map<String, String> wikiNamespaceCaseModes;
	private Map<String, String> wikiNamespaceAliases;
	private Map<Integer, String> wikiNamespaceIds;

	@JsonAnySetter
	public void rejectUnknownParameter(String name, Object value) {
		throw new IllegalArgumentException("Unknown migration parameter: " + name);
	}
}
