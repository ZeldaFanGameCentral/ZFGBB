package com.zfgc.zfgbb.migrator.jobs;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Job {
	private UUID id;
	private JobType type;
	private volatile JobState state;
	private volatile Instant submittedAt;
	private volatile Instant startedAt;
	private volatile Instant finishedAt;
	private volatile String error;

	@JsonIgnore private String smfJdbcUrl;
	@JsonIgnore private String smfUser;
	@JsonIgnore private String smfPassword;
	@JsonIgnore private String smfTablePrefix;
	@JsonIgnore private String smfLegacyHost;
	@JsonIgnore private String appBaseUrl;
	@JsonIgnore private String attachmentsSourcePath;
	@JsonIgnore private String attachmentsTargetPath;
	@JsonIgnore private String avatarsSourcePath;
	@JsonIgnore private String cmsFilesSourcePath;
	@JsonIgnore private String wikiImagesSourcePath;
	@JsonIgnore private boolean force;
	@JsonIgnore private boolean createMemberWikiPages;
	@JsonIgnore private Integer discussionBoardId;
	@JsonIgnore private Integer resourcesBoardId;
	@JsonIgnore private Map<String, Integer> talkBoardIds;
	@JsonIgnore private Map<Integer, List<String>> groupPermissionMap;
	private Map<String, String> wikiNamespaceCaseModes;
	private Map<String, String> wikiNamespaceAliases;
	private Map<Integer, String> wikiNamespaceIds;
}
